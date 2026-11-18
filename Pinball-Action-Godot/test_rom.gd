extends SceneTree

const RomVideo = preload("res://rom_video.gd")

func _initialize() -> void:
	var machine := RomVideo.new()
	root.add_child.call_deferred(machine)
	call_deferred("_run", machine)

func _run(machine) -> void:
	if not machine.load_snapshot():
		push_error("ROM load failed")
		quit(1)
		return
	var expected = JSON.parse_string(FileAccess.get_file_as_string("res://data/reference.json"))
	if not expected is Dictionary:
		push_error("Missing reference")
		quit(1)
		return
	var actual: Dictionary = machine.audit()
	for key in actual:
		if key == "initial_sprite_commands":
			if actual[key] != int(expected[key]):
				push_error("Sprite count mismatch")
				quit(1)
				return
		elif actual[key] != expected[key]:
			push_error("ROM audit mismatch: " + key)
			quit(1)
			return
	var before: Dictionary = machine.counters.duplicate()
	machine.update_ram(machine.ram, machine.scroll_reg, 1 if machine.flip_screen else 0)
	if before["board_uploads"] != machine.counters["board_uploads"] or before["front_uploads"] != machine.counters["front_uploads"]:
		push_error("Unchanged RAM should not upload layers")
		quit(1)
		return
	var scroll: int = machine.scroll_reg
	var flags := 1 if machine.flip_screen else 0
	machine.update_ram(machine.ram, scroll + 8, flags)
	if before["board_uploads"] != machine.counters["board_uploads"] or before["front_uploads"] != machine.counters["front_uploads"]:
		push_error("Scroll should not upload unchanged RAM layers")
		quit(1)
		return
	machine.update_ram(machine.ram, scroll, flags)
	if machine.reference_frame_hash() != expected["initial_frame_rgba_fnv1a"]:
		push_error("Scroll restoration mismatch")
		quit(1)
		return
	print("ROM reference, unchanged-layer and scroll regressions passed: ", actual)
	quit(0)
