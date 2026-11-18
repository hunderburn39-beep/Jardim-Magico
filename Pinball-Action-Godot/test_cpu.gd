extends SceneTree
const Z80=preload("res://z80.gd")
const RomVideo=preload("res://rom_video.gd")
var sound_count=0
func _initialize() -> void:
	call_deferred("_run")
func _sound(_v:int)->void:
	sound_count+=1
func _run()->void:
	var doc=JSON.parse_string(FileAccess.get_file_as_string("res://data/rom_snapshot.json"))
	var fixture=JSON.parse_string(FileAccess.get_file_as_string("res://data/cpu_reference.json"))
	if not doc is Dictionary or not fixture is Dictionary:
		quit(1)
		return
	var machine=Z80.new(Marshalls.base64_to_raw(doc["snapshot"]),JSON.parse_string(doc["state"]))
	machine.sound_command.connect(_sound)
	var video=RomVideo.new()
	var expected={}
	for c in fixture["checkpoints"]:
		expected[int(c["frame"])]=c
	for n in range(1,361):
		machine.system=1 if n>=30 and n<44 else 4 if n>=85 and n<103 else 0
		machine.p1=1 if n>=170 and n<190 else 8 if n>=210 and n<230 else 16 if n>=250 and n<270 else 0
		machine.tick()
		if expected.has(n):
			var c=expected[n]
			var actual={"pc":machine.pc,"sp":machine.sp,"r":machine.r,"f":machine.f,"steps":machine.steps,"scroll":machine.scroll,"flipped":machine.flipped,"credits":machine.mem[0xc013],"ram_hash":video.fnv1a(machine.mem),"sound_count":sound_count}
			for k in actual:
				if actual[k]!=c[k]:
					push_error("V0.2 CPU mismatch frame %d field %s: got %s expected %s" % [n,k,str(actual[k]),str(c[k])])
					video.free()
					quit(1)
					return
			print("CPU replay frame ",n,": OK")
	video.free()
	print("360-frame ROM replay passed")
	quit(0)
