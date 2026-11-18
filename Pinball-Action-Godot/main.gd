extends Control

const RomVideo = preload("res://rom_video.gd")
const Z80 = preload("res://z80.gd")
const P1 = {"left":8,"right":16,"launch":1,"shake":4}
const SYS = {"coin":1,"start":4,"start2":8}

var machine
var cpu
var viewport: SubViewport
var status: Label
var touch := {}
var keyboard := {}
var pulse := {"left":0,"right":0,"launch":0,"shake":0,"coin":0,"start":0,"start2":0}
var queued_start := ""
var queued_frames := 0
var played_frames := 0
var sound_commands := 0

func _ready() -> void:
	var background := ColorRect.new()
	background.color = Color("#10091c")
	add_child(background)
	background.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	var title := Label.new()
	title.text = "PINBALL ACTION"
	title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	title.add_theme_font_size_override("font_size", 23)
	add_child(title)
	title.set_anchors_and_offsets_preset(Control.PRESET_TOP_WIDE)
	title.offset_top = 10
	title.offset_bottom = 43
	viewport = SubViewport.new()
	viewport.size = Vector2i(224,256)
	viewport.render_target_update_mode = SubViewport.UPDATE_DISABLED
	add_child(viewport)
	machine = RomVideo.new()
	viewport.add_child(machine)
	var display := TextureRect.new()
	display.texture = viewport.get_texture()
	display.expand_mode = TextureRect.EXPAND_IGNORE_SIZE
	display.stretch_mode = TextureRect.STRETCH_KEEP_ASPECT_CENTERED
	display.texture_filter = CanvasItem.TEXTURE_FILTER_NEAREST
	display.mouse_filter = Control.MOUSE_FILTER_IGNORE
	add_child(display)
	display.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	display.offset_left = 5
	display.offset_right = -5
	display.offset_top = 105
	display.offset_bottom = -116
	var header := HBoxContainer.new()
	add_child(header)
	header.set_anchors_and_offsets_preset(Control.PRESET_TOP_WIDE)
	header.offset_left=8
	header.offset_right=-8
	header.offset_top=47
	header.offset_bottom=91
	_button(header,"FICHA","coin")
	_button(header,"P1","start")
	_button(header,"P2","start2")
	var controls := HBoxContainer.new()
	add_child(controls)
	controls.set_anchors_and_offsets_preset(Control.PRESET_BOTTOM_WIDE)
	controls.offset_top=-108
	controls.offset_bottom=-40
	controls.offset_left=6
	controls.offset_right=-6
	_button(controls,"ESQ.","left")
	var center := VBoxContainer.new()
	center.size_flags_horizontal=Control.SIZE_EXPAND_FILL
	controls.add_child(center)
	_button(center,"LANÇAR","launch")
	_button(center,"SHAKE","shake")
	_button(controls,"DIR.","right")
	status=Label.new()
	status.horizontal_alignment=HORIZONTAL_ALIGNMENT_CENTER
	status.add_theme_font_size_override("font_size",11)
	add_child(status)
	status.set_anchors_and_offsets_preset(Control.PRESET_BOTTOM_WIDE)
	status.offset_top=-36
	status.offset_bottom=-7
	status.text="V0.2 GODOT · som em desenvolvimento"
	if not machine.load_snapshot():
		status.text="Erro: ROM indisponível"
		set_physics_process(false)
		return
	var doc = JSON.parse_string(FileAccess.get_file_as_string("res://data/rom_snapshot.json"))
	var state = JSON.parse_string(doc["state"])
	cpu=Z80.new(machine.ram.duplicate(),state)
	cpu.sound_command.connect(_sound_command)
	viewport.render_target_update_mode=SubViewport.UPDATE_ONCE

func _button(parent: Container, label: String, action: String) -> void:
	var button := Button.new()
	button.text=label
	button.focus_mode=Control.FOCUS_NONE
	button.size_flags_horizontal=Control.SIZE_EXPAND_FILL
	button.size_flags_vertical=Control.SIZE_EXPAND_FILL
	parent.add_child(button)
	button.button_down.connect(func(): _press(action,true,touch))
	button.button_up.connect(func(): _press(action,false,touch))

func _sound_command(_value: int) -> void:
	sound_commands+=1

func _press(k: String, down: bool, source: Dictionary) -> void:
	if cpu==null:
		return
	var was=bool(source.get(k,false))
	source[k]=down
	if down and not was:
		if k=="coin":
			pulse[k]=maxi(pulse[k],8)
		elif k=="start" or k=="start2":
			var required=2 if k=="start2" else 1
			if cpu.mem[0xc013]>=required:
				pulse[k]=18
			else:
				queued_start=k
				queued_frames=0
				pulse["coin"]=14
		elif k=="launch":
			pulse[k]=maxi(pulse[k],18)
		elif k=="shake":
			pulse[k]=maxi(pulse[k],8)

func _unhandled_key_input(event: InputEvent) -> void:
	if not event is InputEventKey:
		return
	var key_event := event as InputEventKey
	var bindings := {KEY_LEFT:"left",KEY_A:"left",KEY_RIGHT:"right",KEY_D:"right",KEY_SPACE:"launch",KEY_DOWN:"launch",KEY_UP:"shake",KEY_S:"shake",KEY_C:"coin",KEY_ENTER:"start",KEY_1:"start",KEY_2:"start2"}
	var code=key_event.physical_keycode if key_event.physical_keycode!=0 else key_event.keycode
	if bindings.has(code):
		_press(bindings[code],key_event.pressed,keyboard)
		get_viewport().set_input_as_handled()

func _notification(what: int) -> void:
	if what==NOTIFICATION_APPLICATION_FOCUS_OUT:
		touch.clear()
		keyboard.clear()
		for k in pulse:
			pulse[k]=0
		queued_start=""

func _physics_process(_delta: float) -> void:
	if cpu==null:
		return
	var p1=0
	var sys=0
	for k in P1:
		if touch.get(k,false) or keyboard.get(k,false) or pulse[k]>0:
			p1|=P1[k]
	for k in SYS:
		if pulse[k]>0 or (k=="coin" and (touch.get(k,false) or keyboard.get(k,false))):
			sys|=SYS[k]
	cpu.p1=p1
	cpu.system=sys
	cpu.tick()
	for k in pulse:
		pulse[k]=maxi(0,pulse[k]-1)
	if queued_start!="":
		queued_frames+=1
		var required=2 if queued_start=="start2" else 1
		if cpu.mem[0xc013]>=required:
			pulse[queued_start]=18
			queued_start=""
		elif queued_frames%50==0:
			pulse["coin"]=14
		if queued_frames>400:
			queued_start=""
	played_frames+=1
	if cpu.video_changed:
		machine.update_ram(cpu.mem,cpu.scroll,cpu.flipped)
		cpu.video_changed=false
		viewport.render_target_update_mode=SubViewport.UPDATE_ONCE
	if played_frames%30==0:
		status.text="V0.2 GODOT · créditos %d · áudio pendente" % cpu.mem[0xc013]
