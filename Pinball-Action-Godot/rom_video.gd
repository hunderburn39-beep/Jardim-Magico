extends Node2D
## ROM-native video core. The live machine must supply its RAM and registers.
## Coordinates and draw order correspond to the V8.89 JavaScript original mode.

const NONE := 65535
const W := 256
const CELLS := 65536

var ram := PackedByteArray()
var atlases: Array[PackedByteArray] = []
var palette := PackedByteArray()
var palette_texture: ImageTexture
var palette_material: ShaderMaterial
var bg_index := PackedInt32Array()
var fg_index := PackedInt32Array()
var bg_texture: ImageTexture
var fg_texture: ImageTexture
var bg_key := PackedByteArray()
var fg_key := PackedByteArray()
var pal_key := PackedByteArray()
var flip_screen := false
var scroll_reg := 0
var sprite_cache: Dictionary = {}
var sprite_order: Array[String] = []
var commands: Array[Dictionary] = []
var initialized := false
var counters := {"board_uploads": 0, "front_uploads": 0, "sprite_uploads": 0, "sprite_hits": 0, "palette_uploads": 0, "tiles_decoded": 0}

func load_snapshot(path: String = "res://data/rom_snapshot.json") -> bool:
	var doc = JSON.parse_string(FileAccess.get_file_as_string(path))
	if not doc is Dictionary:
		push_error("Missing or invalid ROM snapshot")
		return false
	for key in ["bg", "fg", "sp", "snapshot", "state"]:
		if not doc.has(key):
			return false
	var bg := Marshalls.base64_to_raw(doc["bg"])
	var fg := Marshalls.base64_to_raw(doc["fg"])
	var sp := Marshalls.base64_to_raw(doc["sp"])
	var mem := Marshalls.base64_to_raw(doc["snapshot"])
	var state = JSON.parse_string(doc["state"])
	if bg.size() != 65536 or fg.size() != 24576 or sp.size() != 24576 or mem.size() != 65536 or not state is Dictionary:
		push_error("Unexpected ROM dimensions")
		return false
	var s8: Array[int] = []
	var y8: Array[int] = []
	for n in range(8):
		s8.append(n)
		y8.append(n * 8)
	var s16 := _extended(s8, 64)
	var y16 := _extended(y8, 128)
	var s32 := _extended(s16, 256)
	var y32 := _extended(y16, 512)
	atlases = [
		_decode_atlas(bg, 2048, 8, 4, s8, y8, 64),
		_decode_atlas(fg, 1024, 8, 3, s8, y8, 64),
		_decode_atlas(sp, 256, 16, 3, s16, y16, 256),
		_decode_atlas(sp, 32, 32, 3, s32, y32, 1024, 4096)
	]
	# Map hardware (x,y) to the original portrait viewport (240-y,x).
	position = Vector2(240, 0)
	rotation = PI / 2.0
	texture_filter = CanvasItem.TEXTURE_FILTER_NEAREST
	bg_index.resize(CELLS)
	fg_index.resize(CELLS)
	palette.resize(1024)
	palette_material = ShaderMaterial.new()
	palette_material.shader = preload("res://rom_palette.gdshader")
	material = palette_material
	initialized = false
	return update_ram(mem, int(state["scroll"]), int(state["flipped"]))

func _extended(values: Array[int], offset: int) -> Array[int]:
	var out: Array[int] = values.duplicate()
	for v in values:
		out.append(v + offset)
	return out

func _decode_atlas(bytes: PackedByteArray, count: int, size: int, planes: int, xs: Array[int], ys: Array[int], stride_bits: int, offset: int = 0) -> PackedByteArray:
	var out := PackedByteArray()
	out.resize(count * size * size)
	var plane_stride := int(bytes.size() / planes) * 8
	for code in range(count):
		for y in range(size):
			for x in range(size):
				var pen := 0
				var base := offset * 8 + code * stride_bits + xs[x] + ys[y]
				for plane in range(planes):
					var bit := base + plane * plane_stride
					pen |= ((bytes[bit >> 3] >> (7 - (bit & 7))) & 1) << (planes - 1 - plane)
				out[(code * size + y) * size + x] = pen
	return out

func update_ram(mem: PackedByteArray, scroll: int, flipped: int) -> bool:
	if mem.size() != 65536 or atlases.size() != 4:
		return false
	ram = mem
	var next_flip := (flipped & 1) != 0
	var next_bg := mem.slice(0xd800, 0xe000)
	var next_fg := mem.slice(0xd000, 0xd800)
	var next_pal := mem.slice(0xe400, 0xe600)
	var flip_changed := not initialized or next_flip != flip_screen
	var bg_changed := flip_changed or next_bg != bg_key
	var fg_changed := flip_changed or next_fg != fg_key
	# Palette updates upload only 256 colours. Indexed tile/sprite textures
	# need no reconstruction, even while lamps blink or change colour.
	flip_screen = next_flip
	scroll_reg = scroll
	if bg_changed:
		bg_index = _build_layer(true, next_bg, bg_key, bg_index, flip_changed)
	if fg_changed:
		fg_index = _build_layer(false, next_fg, fg_key, fg_index, flip_changed)
	if not initialized or next_pal != pal_key:
		_decode_palette()
		_upload_palette()
	if bg_changed:
		bg_texture = _upload_layer(bg_texture, bg_index)
		counters["board_uploads"] += 1
	if fg_changed:
		fg_texture = _upload_layer(fg_texture, fg_index)
		counters["front_uploads"] += 1
	bg_key = next_bg
	fg_key = next_fg
	pal_key = next_pal
	commands = _sprite_commands()
	initialized = true
	queue_redraw()
	return true

func _decode_palette() -> void:
	for index in range(256):
		var a := ram[0xe400 + index * 2]
		var b := ram[0xe401 + index * 2]
		palette[index * 4] = (a & 15) * 17
		palette[index * 4 + 1] = ((a >> 4) & 15) * 17
		palette[index * 4 + 2] = (b & 15) * 17
		palette[index * 4 + 3] = 255

func _upload_palette() -> void:
	var image := Image.create_from_data(256, 1, false, Image.FORMAT_RGBA8, palette)
	if palette_texture == null:
		palette_texture = ImageTexture.create_from_image(image)
	else:
		palette_texture.update(image)
	palette_material.set_shader_parameter("rom_palette", palette_texture)
	counters["palette_uploads"] += 1

func _build_layer(is_bg: bool, current: PackedByteArray, previous: PackedByteArray, out: PackedInt32Array, full: bool) -> PackedInt32Array:
	out.resize(CELLS)
	var pens: PackedByteArray = atlases[0 if is_bg else 1]
	var vram := 0xd800 if is_bg else 0xd000
	var cram := 0xdc00 if is_bg else 0xd400
	var mask := 0x70 if is_bg else 0x30
	var count := 2048 if is_bg else 1024
	for i in range(1024):
		if not full and current[i] == previous[i] and current[i+1024] == previous[i+1024]:
			continue
		counters["tiles_decoded"] += 1
		var attr := ram[cram + i]
		var code := (ram[vram + i] + 16 * (attr & mask)) % count
		var base_color := 128 + 16 * (attr & 7) if is_bg else 8 * (attr & 15)
		var fx := not is_bg and (attr & 64) != 0
		var fy := (attr & 128) != 0
		var px := (i & 31) * 8
		var py := (i >> 5) * 8
		if flip_screen:
			px = 248 - px
			py = 248 - py
			fx = not fx
			fy = not fy
		for y in range(8):
			for x in range(8):
				var pen := pens[code * 64 + (7-y if fy else y) * 8 + (7-x if fx else x)]
				out[(py+y)*256+px+x] = NONE if not is_bg and pen == 0 else base_color + pen
	return out

func _rgba(indices: PackedInt32Array) -> PackedByteArray:
	var rgba := PackedByteArray()
	rgba.resize(indices.size() * 4)
	for i in range(indices.size()):
		var index := indices[i]
		if index == NONE:
			continue
		var src := index * 4
		var dst := i * 4
		rgba[dst] = palette[src]
		rgba[dst+1] = palette[src+1]
		rgba[dst+2] = palette[src+2]
		rgba[dst+3] = 255
	return rgba

func _indexed_rgba(indices: PackedInt32Array) -> PackedByteArray:
	var bytes := PackedByteArray()
	bytes.resize(indices.size() * 4)
	for j in range(indices.size()):
		var index := indices[j]
		if index != NONE:
			bytes[j*4] = index
			bytes[j*4+3] = 255
	return bytes

func _upload_layer(texture: ImageTexture, indices: PackedInt32Array) -> ImageTexture:
	var image := Image.create_from_data(W, W, false, Image.FORMAT_RGBA8, _indexed_rgba(indices))
	if texture == null:
		return ImageTexture.create_from_image(image)
	texture.update(image)
	return texture

func _scroll() -> int:
	return (scroll_reg - 3) * (-1 if flip_screen else 1)

func _sprite_commands() -> Array[Dictionary]:
	var out: Array[Dictionary] = []
	for o in range(124, -1, -4):
		if o > 0 and (ram[0xe000 + o - 4] & 128) != 0:
			continue
		var code := ram[0xe000+o]
		var attr := ram[0xe001+o]
		var sy := ram[0xe002+o]
		var sx := ram[0xe003+o]
		if (code | attr | sy | sx) == 0:
			continue
		var big := (code & 128) != 0
		var size := 32 if big else 16
		var x := sx
		var y := (225 if big else 241) - sy
		var fx := (attr & 64) != 0
		var fy := (attr & 128) != 0
		if flip_screen:
			x = (224 if big else 240) - x
			y = (225 if big else 241) - y
			fx = not fx
			fy = not fy
		x -= _scroll()
		out.append({"atlas":3 if big else 2, "code":code % (32 if big else 256), "size":size, "x":x, "y":y, "fx":fx, "fy":fy, "base":8*(attr&15)})
	return out

func _sprite_texture(cmd: Dictionary) -> ImageTexture:
	var size: int = cmd["size"]
	var base: int = cmd["base"]
	var code: int = cmd["code"]
	var atlas_id: int = cmd["atlas"]
	var key := "%d:%d:%d" % [atlas_id, code, base]
	if sprite_cache.has(key):
		counters["sprite_hits"] += 1
		sprite_order.erase(key)
		sprite_order.append(key)
		return sprite_cache[key]
	var pens: PackedByteArray = atlases[atlas_id]
	var indices := PackedInt32Array()
	indices.resize(size*size)
	for i in range(indices.size()):
		var pen := pens[code*size*size+i]
		indices[i] = NONE if pen == 0 else base + pen
	var texture := ImageTexture.create_from_image(Image.create_from_data(size,size,false,Image.FORMAT_RGBA8,_indexed_rgba(indices)))
	sprite_cache[key] = texture
	sprite_order.append(key)
	counters["sprite_uploads"] += 1
	if sprite_order.size() > 192:
		sprite_cache.erase(sprite_order.pop_front())
	return texture

func _draw_layer(texture: ImageTexture, shift: int) -> void:
	draw_texture(texture, Vector2(-shift,0))
	if shift != 0:
		draw_texture(texture, Vector2(256-shift,0))

func _draw() -> void:
	if not initialized:
		return
	var shift := posmod(_scroll(),256)
	draw_set_transform(Vector2.ZERO)
	_draw_layer(bg_texture,shift)
	for cmd in commands:
		var size: int = cmd["size"]
		var x: int = cmd["x"]
		var y: int = cmd["y"]
		var fx: bool = cmd["fx"]
		var fy: bool = cmd["fy"]
		if x+size <= 0 or x >= 256 or y+size <= 16 or y >= 240:
			continue
		draw_set_transform(Vector2(x+(size if fx else 0),y+(size if fy else 0)),0.0,Vector2(-1 if fx else 1,-1 if fy else 1))
		draw_texture(_sprite_texture(cmd),Vector2.ZERO)
	draw_set_transform(Vector2.ZERO)
	_draw_layer(fg_texture,shift)

func fnv1a(bytes: PackedByteArray) -> String:
	var h: int = 2166136261
	for value in bytes:
		h = ((h ^ value) * 16777619) & 0xffffffff
	return "%08x" % h

## Independent CPU compositing diagnostic, not the GPU game rendering path.
func reference_frame_hash() -> String:
	var frame := PackedInt32Array()
	frame.resize(CELLS)
	var shift := posmod(_scroll(),256)
	for y in range(256):
		for x in range(256):
			frame[y*256+x] = bg_index[y*256+((x+shift)&255)]
	for cmd in commands:
		var size: int = cmd["size"]
		var pens: PackedByteArray = atlases[cmd["atlas"]]
		var start: int = cmd["code"]*size*size
		for dy in range(size):
			var y: int = cmd["y"]+dy
			if y < 0 or y >= 256:
				continue
			for dx in range(size):
				var x: int = cmd["x"]+dx
				if x < 0 or x >= 256:
					continue
				var pen := pens[start+(size-1-dy if cmd["fy"] else dy)*size+(size-1-dx if cmd["fx"] else dx)]
				if pen != 0:
					frame[y*256+x] = cmd["base"]+pen
	for y in range(256):
		for x in range(256):
			var p := fg_index[y*256+((x+shift)&255)]
			if p != NONE:
				frame[y*256+x] = p
	var portrait := PackedInt32Array()
	portrait.resize(224*256)
	for y in range(256):
		for x in range(224):
			portrait[y*224+x] = frame[(239-x)*256+y]
	return fnv1a(_rgba(portrait))

func audit() -> Dictionary:
	var hashes: Array[String] = []
	for a in atlases:
		hashes.append(fnv1a(a))
	return {"atlas_hashes": hashes, "initial_frame_rgba_fnv1a":reference_frame_hash(), "initial_sprite_commands":commands.size()}
