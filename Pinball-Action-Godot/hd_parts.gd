extends RefCounted
## Independent artwork for mechanical ROM sprites. The playfield itself is untouched.
## RGBA data carries palette index, material tone, reserved byte, alpha.
## Ball pixels use channel G=255 for direct steel shading. Every other sprite
## retains its native pen and occupied cell; no fictional animation frames.
const HD:=4

static func supports(atlas_id:int,code:int)->bool:
	return (atlas_id==2 and code>=5 and code<=14) or (atlas_id==3 and code>=1 and code<=10)

static func bounds(pens:PackedByteArray,size:int,code:int)->Dictionary:
	var start:=code*size*size
	var low:=Vector2i(size,size)
	var high:=Vector2i(-1,-1)
	var cells:=0
	var sum:=Vector2.ZERO
	for y in range(size):
		for x in range(size):
			if pens[start+y*size+x]==0:continue
			low.x=mini(low.x,x)
			low.y=mini(low.y,y)
			high.x=maxi(high.x,x)
			high.y=maxi(high.y,y)
			cells+=1
			sum+=Vector2(x+0.5,y+0.5)
	return {"low":low,"high":high,"count":cells,"center":sum/maxi(cells,1)}

static func make(pens:PackedByteArray,size:int,code:int,atlas_id:int,base:int)->Image:
	var info:=bounds(pens,size,code)
	var side:=HD*size
	var bytes:=PackedByteArray()
	bytes.resize(side*side*4)
	if int(info["count"])==0:
		return Image.create_from_data(side,side,false,Image.FORMAT_RGBA8,bytes)
	var low:Vector2i=info["low"]
	var high:Vector2i=info["high"]
	var center:Vector2=info["center"]
	if atlas_id==2 and code==5:
		_draw_ball(bytes,side,low,high,int(info["count"]))
	else:
		var axis:=_principal_axis(pens,size,code,center)
		var bumper:=atlas_id==3 and code>=7 and code<=10
		_draw_mechanical(bytes,side,pens,size,code,base,center,axis,low,high,bumper)
	return Image.create_from_data(side,side,false,Image.FORMAT_RGBA8,bytes)

static func _draw_ball(bytes:PackedByteArray,side:int,low:Vector2i,high:Vector2i,count:int)->void:
	# Real spherical surface, not a blurred enlargement of the sprite.
	# The diameter is bounded by the occupied cells on both native axes.
	var center:Vector2=(Vector2(low)+Vector2(high)+Vector2.ONE)*0.5
	var span:=Vector2(high-low+Vector2i.ONE)
	var radius:=minf(minf(span.x,span.y)*0.5-0.12,sqrt(float(count)/PI)*0.98)
	if radius<=0.0:return
	var light:=Vector3(-0.48,0.55,0.68).normalized()
	for y in range(side):
		for x in range(side):
			var q:Vector2=(Vector2(x+0.5,y+0.5)/HD-center)/radius
			var distance:=q.length()
			var coverage:=clampf((1.0-distance)*radius*HD+0.5,0.0,1.0)
			if coverage<=0.0:continue
			var normal:=Vector3(q.x,q.y,sqrt(maxf(0.0,1.0-q.length_squared())))
			var diffuse:=maxf(0.0,normal.dot(light))
			var shine:=pow(maxf(0.0,normal.dot(Vector3(-0.32,0.37,0.87).normalized())),16.0)
			var bounce:=pow(maxf(0.0,normal.dot(Vector3(0.5,-0.7,0.45).normalized())),8.0)
			var steel:=clampf(0.32+0.5*diffuse+0.30*shine+0.15*bounce,0.0,1.0)
			var dst:=(y*side+x)*4
			bytes[dst]=roundi(steel*255.0)
			bytes[dst+1]=255
			bytes[dst+3]=roundi(coverage*255.0)

static func _principal_axis(pens:PackedByteArray,size:int,code:int,center:Vector2)->Vector2:
	var xx:=0.0
	var yy:=0.0
	var xy:=0.0
	var start:=code*size*size
	for y in range(size):
		for x in range(size):
			if pens[start+y*size+x]==0:continue
			var q:=Vector2(x+0.5,y+0.5)-center
			xx+=q.x*q.x
			yy+=q.y*q.y
			xy+=q.x*q.y
	var angle:=0.5*atan2(2.0*xy,xx-yy)
	return Vector2(cos(angle),sin(angle))

static func _draw_mechanical(bytes:PackedByteArray,side:int,pens:PackedByteArray,size:int,code:int,base:int,center:Vector2,axis:Vector2,low:Vector2i,high:Vector2i,bumper:bool)->void:
	var start:=code*size*size
	var across:=Vector2(-axis.y,axis.x)
	var width:=0.5
	for y in range(size):
		for x in range(size):
			if pens[start+y*size+x]!=0:
				width=maxf(width,absf((Vector2(x+0.5,y+0.5)-center).dot(across))+0.5)
	var span:=Vector2(high-low+Vector2i.ONE)
	var radius:=maxf(1.0,minf(span.x,span.y)*0.5)
	for y in range(side):
		for x in range(side):
			var p:=Vector2(x+0.5,y+0.5)/HD
			var native:=Vector2i(p.floor())
			var pen:=pens[start+native.y*size+native.x]
			if pen==0:continue
			var q:=p-center
			var shade:=0.0
			if bumper:
				# Radial enamel dome and a narrow metallic rim, preserving
				# the original pen layers and light animation state.
				var r:=q.length()/radius
				var highlight:=exp(-pow((r-0.79)/0.055,2.0))
				var glint:=exp(-q.distance_squared_to(Vector2(-0.23,0.23)*radius)/(radius*radius*0.20))
				shade=0.23*highlight+0.25*glint-0.16*clampf(r,0.0,1.0)
			else:
				# Chrome/rubber profile follows this exact native flipper pose.
				# No independent angular animation or new hitbox is created.
				var v:=clampf(q.dot(across)/width,-1.0,1.0)
				var ridge:=exp(-pow((v+0.37)/0.16,2.0))
				var edge:=exp(-pow((absf(v)-0.87)/0.10,2.0))
				shade=0.38*ridge+0.13*edge-0.16*absf(v)
			# Source ink remains the original palette entry, including
			# inner markings. Dark native pens get a restrained finish.
			if pen==1:shade*=0.45
			var dst:=(y*side+x)*4
			bytes[dst]=base+pen
			bytes[dst+1]=clampi(roundi(64.0+shade*63.0),1,127)
			bytes[dst+3]=255
