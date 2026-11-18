extends SceneTree
const Video=preload("res://rom_video.gd")
const Parts=preload("res://hd_parts.gd")
func _initialize()->void:
	call_deferred("_run")
func fail(message:String)->void:
	push_error(message)
	quit(1)
func _run()->void:
	var video=Video.new()
	root.add_child(video)
	if not video.load_snapshot():
		fail("ROM failed to load")
		return
	var original:String=video.reference_frame_hash()
	var checked:=0
	for atlas_id in [2,3]:
		var size:=16 if atlas_id==2 else 32
		var pens:PackedByteArray=video.atlases[atlas_id]
		for code in range(5,15) if atlas_id==2 else range(1,11):
			var info:=Parts.bounds(pens,size,code)
			var image:=Parts.make(pens,size,code,atlas_id,16)
			var data:=image.get_data()
			if image.get_width()!=size*4 or image.get_height()!=size*4:
				fail("Incorrect HD sprite dimensions")
				return
			var opaque:=0
			var low:Vector2i=info["low"]
			var high:Vector2i=info["high"]
			for y in range(size*4):
				for x in range(size*4):
					var k:=(y*size*4+x)*4
					var nx:=x>>2
					var ny:=y>>2
					var pen:=pens[(code*size+ny)*size+nx]
					if data[k+3]!=0:
						opaque+=1
						if nx<low.x or nx>high.x or ny<low.y or ny>high.y:
							fail("HD art enlarged the native bounds")
							return
						if atlas_id==2 and code==5:
							if data[k+1]!=255:
								fail("Ball material marker missing")
								return
						elif pen==0 or data[k]!=16+pen or data[k+3]!=255:
							fail("Native mechanical ink or silhouette altered")
							return
					elif pen!=0 and not (atlas_id==2 and code==5):
						fail("Native mechanical cell disappeared")
						return
			if int(info["count"])>0 and opaque==0:
				fail("Nonempty sprite rendered empty")
				return
			checked+=1
	video.set_hd(true)
	if video.reference_frame_hash()!=original:
		fail("HD mode changed source board or sprite RAM")
		return
	video.set_hd(false)
	if video.reference_frame_hash()!=original:
		fail("Original mode restoration failed")
		return
	print("Verified ",checked," HD sprite frames and immutable original board data")
	quit(0)
