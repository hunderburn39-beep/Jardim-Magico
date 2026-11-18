extends SceneTree
const Board=preload("res://sound_board.gd")
func _initialize()->void:
	call_deferred("_run")
func _run()->void:
	var fixture=JSON.parse_string(FileAccess.get_file_as_string("res://data/audio_reference.json"))
	var board=Board.new()
	if not fixture is Dictionary or not board.setup(float(fixture["rate"])):
		push_error("Sound ROM setup failed")
		quit(1)
		return
	board.command(int(fixture["command"]))
	var sum:=0.0
	var nonzero:=0
	var n:=0
	for checkpoint in fixture["checkpoints"]:
		while n<int(checkpoint["frame"]):
			var sample:float=board.sample()
			n+=1
			sum+=sample*sample
			if absf(sample)>0.00000001:nonzero+=1
		var rms:=sqrt(sum/n)
		var valid:bool=board.ready==checkpoint["ready"] and board.cpu.pc==int(checkpoint["pc"]) and board.cpu.sp==int(checkpoint["sp"]) and nonzero==int(checkpoint["nonzero"]) and absf(rms-float(checkpoint["rms"]))<0.000001
		for chip in range(3):
			for reg in range(16):
				if board.ay[chip].reg[reg]!=int(checkpoint["registers"][chip][reg]):valid=false
		if not valid:
			push_error("Audio mismatch at sample %d: PC=%d RMS=%f audible=%d" % [n,board.cpu.pc,rms,nonzero])
			board.close()
			quit(1)
			return
		print("Original sound replay checkpoint ",n," RMS ",rms)
	board.close()
	print("Original title audio regression passed")
	quit(0)
