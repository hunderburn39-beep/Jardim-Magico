extends Node
## Worker synthesizes original PCM; the main thread alone touches Godot audio.
const Board=preload("res://sound_board.gd")
const RATE:=22050.0
const BATCH:=256
const QUEUE_LIMIT:=2048
var board
var player:AudioStreamPlayer
var playback:AudioStreamGeneratorPlayback
var thread:=Thread.new()
var mutex:=Mutex.new()
var quitting:=false
var pcm:=PackedVector2Array()
var inbox:Array[int]=[]
var booted:=false
var muted:=false

func _ready()->void:
	board=Board.new()
	if not board.setup(RATE):
		push_warning("Original sound ROM could not load")
		return
	var stream:=AudioStreamGenerator.new()
	stream.mix_rate=RATE
	stream.buffer_length=0.12
	player=AudioStreamPlayer.new()
	player.stream=stream
	add_child(player)
	player.play()
	playback=player.get_stream_playback() as AudioStreamGeneratorPlayback
	if playback==null:
		board.close()
		return
	var err:=thread.start(_worker)
	if err!=OK:
		board.close()
		player.stop()
		return
	booted=true

func command(value:int)->void:
	if not booted:return
	mutex.lock()
	inbox.append(value&255)
	# Prevent an unbounded command queue if the audio device stops.
	if inbox.size()>256:inbox.pop_front()
	mutex.unlock()

func set_muted(value:bool)->void:
	muted=value
	if player!=null:player.volume_db=-80.0 if muted else 0.0

func _worker()->void:
	while true:
		mutex.lock()
		var stop:=quitting
		var can_make:=pcm.size()<=QUEUE_LIMIT-BATCH
		var messages:Array[int]=inbox.duplicate()
		inbox.clear()
		mutex.unlock()
		if stop:return
		for message in messages:board.command(message)
		if not can_make:
			OS.delay_msec(2)
			continue
		var batch:=PackedVector2Array()
		batch.resize(BATCH)
		for j in range(BATCH):
			var v:float=board.sample()
			batch[j]=Vector2(v,v)
		mutex.lock()
		pcm.append_array(batch)
		mutex.unlock()

func _process(_delta:float)->void:
	if not booted or playback==null:return
	var available:=playback.get_frames_available()
	if available<=0:return
	mutex.lock()
	var count:=mini(available,pcm.size())
	var chunk:=pcm.slice(0,count)
	pcm=pcm.slice(count)
	mutex.unlock()
	if count>0:playback.push_buffer(chunk)

func _exit_tree()->void:
	mutex.lock()
	quitting=true
	mutex.unlock()
	if thread.is_started():thread.wait_to_finish()
	if board!=null:board.close()
