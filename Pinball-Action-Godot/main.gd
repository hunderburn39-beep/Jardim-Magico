extends Control

const RomVideo = preload("res://rom_video.gd")

func _ready() -> void:
	var background := ColorRect.new()
	background.color = Color("#10091c")
	background.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	add_child(background)
	var title := Label.new()
	title.text = "PINBALL ACTION"
	title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	title.add_theme_font_size_override("font_size", 28)
	title.set_anchors_and_offsets_preset(Control.PRESET_TOP_WIDE)
	title.offset_top = 24
	title.offset_bottom = 60
	add_child(title)
	var viewport := SubViewport.new()
	viewport.size = Vector2i(224, 256)
	viewport.render_target_update_mode = SubViewport.UPDATE_ONCE
	add_child(viewport)
	var machine := RomVideo.new()
	viewport.add_child(machine)
	var display := TextureRect.new()
	display.texture = viewport.get_texture()
	display.expand_mode = TextureRect.EXPAND_IGNORE_SIZE
	display.stretch_mode = TextureRect.STRETCH_KEEP_ASPECT_CENTERED
	display.texture_filter = CanvasItem.TEXTURE_FILTER_NEAREST
	add_child(display)
	display.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	display.offset_left = 12
	display.offset_right = -12
	display.offset_top = 72
	display.offset_bottom = -132
	var status := Label.new()
	status.text = "V0.1 GODOT · diagnóstico da ROM\nFotograma original — sem jogo activo"
	status.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	status.vertical_alignment = VERTICAL_ALIGNMENT_CENTER
	status.set_anchors_and_offsets_preset(Control.PRESET_BOTTOM_WIDE)
	status.offset_top = -110
	status.offset_bottom = -25
	add_child(status)
	if not machine.load_snapshot():
		status.text = "Falha ao carregar os dados da ROM"
		return
	viewport.render_target_update_mode = SubViewport.UPDATE_ONCE
	var got: Dictionary = machine.audit()
	var expected = JSON.parse_string(FileAccess.get_file_as_string("res://data/reference.json"))
	if not expected is Dictionary:
		status.text = "Referência de diagnóstico indisponível"
		return
	var valid: bool = got["atlas_hashes"] == expected["atlas_hashes"] and got["initial_frame_rgba_fnv1a"] == expected["initial_frame_rgba_fnv1a"] and got["initial_sprite_commands"] == int(expected["initial_sprite_commands"])
	print("ROM AUDIT: ", got, " match=", valid)
	if not valid:
		status.text = "ROM AUDIT: divergência — consultar consola"
