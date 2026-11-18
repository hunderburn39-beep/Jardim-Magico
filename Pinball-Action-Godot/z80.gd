extends RefCounted
## Port of PBZ80 from the V8.89 arcade implementation. Not cycle-exact.
signal sound_command(value: int)
const FS=128
const FZ=64
const FH=16
const FP=4
const FN=2
const FC=1
const F3=8
const F5=32
var mem: PackedByteArray
var r: Array[int] = [0,0,0,0,0,0,0,0]
var alt: Array[int] = [0,0,0,0,0,0,0,0]
var f:int=0
var fa:int=0
var aa:int=0
var sp:int=65535
var pc:int=0
var ix:int=0
var iy:int=0
var i:int=0
var rreg:int=0
var halted:bool=false
var iff:bool=false
var iff2:bool=false
var nmi_enable:int=0
var scroll:int=0
var flipped:int=0
var p1:int=0
var p2:int=0
var system:int=0
var dsw1:int=0x40
var dsw2:int=0
var video_changed:bool=true
var steps:int=0
var parity: Array[int] = []
var sound_bus: Object = null

func _init(memory: PackedByteArray, state: Dictionary):
	mem=memory
	for k in range(8):
		r[k]=int(state.get("r",r)[k])
		alt[k]=int(state.get("r_alt",alt)[k])
	f=int(state.get("f",0))
	fa=int(state.get("fa",0))
	aa=int(state.get("aa",0))
	sp=int(state.get("sp",65535))
	pc=int(state.get("pc",0))
	ix=int(state.get("ix",0))
	iy=int(state.get("iy",0))
	i=int(state.get("i",0))
	rreg=int(state.get("rreg",0))
	iff=bool(state.get("iff",false))
	iff2=bool(state.get("iff2",false))
	nmi_enable=int(state.get("nmi_enabled",0))
	scroll=int(state.get("scroll",0))
	flipped=int(state.get("flipped",0))
	for k in range(256):
		var v=k
		var n=0
		while v:
			n^=v&1
			v>>=1
		parity.append(FP if n==0 else 0)

func read8(a:int)->int:
	a&=65535
	if sound_bus != null:
		return sound_bus.read_mem(a, mem)
	match a:
		0xe600:return p1
		0xe601:return p2
		0xe602:return system
		0xe604:return dsw1
		0xe605:return dsw2
		0xe606:return 255
	return mem[a]

func wr(a:int,v:int)->void:
	a&=65535
	v&=255
	if sound_bus != null:
		if a>=0x4000 and a<0x4800:mem[a]=v
		return
	match a:
		0xe600:
			nmi_enable=v&1
			return
		0xe604:
			if flipped!=v:
				flipped=v
				video_changed=true
			return
		0xe606:
			if scroll!=v:
				scroll=v
				video_changed=true
			return
		0xe800:
			sound_command.emit(v)
			return
	if a>=0xc000 and mem[a]!=v:
		if (a>=0xd000 and a<0xe080) or (a>=0xe400 and a<0xe600):
			video_changed=true
		mem[a]=v

func fetch()->int:
	var v=read8(pc)
	pc=(pc+1)&65535
	return v

func f16()->int:
	var lo=fetch()
	return lo|(fetch()<<8)

func push(v:int)->void:
	sp=(sp-1)&65535
	wr(sp,v>>8)
	sp=(sp-1)&65535
	wr(sp,v)

func pop()->int:
	var v=read8(sp)
	sp=(sp+1)&65535
	v|=read8(sp)<<8
	sp=(sp+1)&65535
	return v

# idx: 0=HL, 1=IX, 2=IY.
func r16(p:int,idx:int=0)->int:
	match p:
		0:return (r[0]<<8)|r[1]
		1:return (r[2]<<8)|r[3]
		2:return ix if idx==1 else iy if idx==2 else (r[4]<<8)|r[5]
	return sp

func s16(p:int,v:int,idx:int=0)->void:
	v&=65535
	match p:
		0:
			r[0]=v>>8
			r[1]=v&255
		1:
			r[2]=v>>8
			r[3]=v&255
		2:
			if idx==1:
				ix=v
			elif idx==2:
				iy=v
			else:
				r[4]=v>>8
				r[5]=v&255
		_:
			sp=v

func rr(k:int,idx:int=0,disp:int=0)->int:
	if k==6:return read8(r16(2,idx)+disp)
	if idx!=0 and (k==4 or k==5):
		var v=r16(2,idx)
		return (v>>8)&255 if k==4 else v&255
	return r[k]

func ww(k:int,v:int,idx:int=0,disp:int=0)->void:
	v&=255
	if k==6:
		wr(r16(2,idx)+disp,v)
	elif idx!=0 and (k==4 or k==5):
		var n=r16(2,idx)
		s16(2,((v<<8)|(n&255)) if k==4 else ((n&65280)|v),idx)
	else:
		r[k]=v

func aflags(x:int,y:int,res:int,add:bool)->int:
	var z=res&255
	var fl=(z&(FS|F3|F5))|(FZ if z==0 else 0)
	if add:
		fl|=FH if (x&15)+(y&15)>15 else 0
		fl|=FP if (~(x^y)&(x^z)&128)!=0 else 0
		fl|=FC if res>255 else 0
	else:
		fl|=FN|(FH if (x&15)<(y&15) else 0)|(FP if ((x^y)&(x^z)&128)!=0 else 0)|(FC if res<0 else 0)
	f=fl
	return z

func alu(op:int,v:int)->void:
	var a=r[7]
	if op==0 or op==1:
		var carry=1 if op==1 and (f&FC)!=0 else 0
		r[7]=aflags(a,v+carry,a+v+carry,true)
		if (a&15)+(v&15)+carry>15:f|=FH
		if (~(a^v)&(a^r[7])&128)!=0:f|=FP
	elif op==2 or op==3 or op==7:
		var carry=1 if op==3 and (f&FC)!=0 else 0
		var z=aflags(a,(v+carry)&255,a-v-carry,false)
		if op!=7:r[7]=z
		if (a&15)<(v&15)+carry:f|=FH
		if ((a^v)&(a^z)&128)!=0:f|=FP
	else:
		var h=FH if op==4 else 0
		var res=(a&v) if op==4 else (a^v) if op==5 else (a|v)
		r[7]=res
		f=(res&(FS|F3|F5))|(FZ if res==0 else 0)|parity[res]|h

func incdec(v:int,down:bool=false)->int:
	var z=(v+(-1 if down else 1))&255
	var fl=(f&FC)|(z&(FS|F3|F5))|(FZ if z==0 else 0)
	if down:
		fl|=FN|(FH if (v&15)==0 else 0)|(FP if v==128 else 0)
	else:
		fl|=(FH if (v&15)==15 else 0)|(FP if v==127 else 0)
	f=fl
	return z

func condition(y:int)->bool:
	var mask=FZ if y<2 else FC if y<4 else FP if y<6 else FS
	return ((f&mask)!=0)==((y&1)!=0)

func jr(d:int)->void:
	pc=(pc+(d if d<128 else d-256))&65535

func cbop(op:int,idx:int=0,disp:int=0)->void:
	var x=op>>6
	var y=(op>>3)&7
	var z=op&7
	var k=6 if idx!=0 else z
	var v=rr(k,idx,disp)
	var res=0
	var carry=0
	if x==0:
		var c=f&FC
		match y:
			0:
				res=((v<<1)|(v>>7))&255
				carry=v>>7
			1:
				res=(v>>1)|((v&1)<<7)
				carry=v&1
			2:
				res=((v<<1)|c)&255
				carry=v>>7
			3:
				res=(v>>1)|(c<<7)
				carry=v&1
			4:
				res=(v<<1)&255
				carry=v>>7
			5:
				res=(v>>1)|(v&128)
				carry=v&1
			6:
				res=((v<<1)|1)&255
				carry=v>>7
			7:
				res=v>>1
				carry=v&1
		f=(res&(FS|F3|F5))|(FZ if res==0 else 0)|parity[res]|carry
		ww(k,res,idx,disp)
		if idx!=0 and z!=6:ww(z,res)
	elif x==1:
		f=(f&FC)|FH|(0 if (v&(1<<y))!=0 else FZ|FP)|(FS if y==7 and (v&128)!=0 else 0)|(v&(F3|F5))
	else:
		res=(v&~(1<<y)) if x==2 else (v|(1<<y))
		ww(k,res,idx,disp)
		if idx!=0 and z!=6:ww(z,res)

func edop()->void:
	var op=fetch()
	var x=op>>6
	var y=(op>>3)&7
	var z=op&7
	var p=y>>1
	var q=y&1
	if x==1:
		match z:
			0:
				var v:int=255 if sound_bus==null else sound_bus.input_port(r[1])
				if y!=6:ww(y,v)
				f=(f&FC)|(v&(FS|F3|F5))|(FZ if v==0 else 0)|parity[v]
			1:
				if sound_bus!=null:sound_bus.output_port(r[1],0 if y==6 else r[y])
			2:
				var v=r16(p)
				var a=r16(2)
				var carry=f&FC
				var res=a+v+carry if q!=0 else a-v-carry
				var zr=res&65535
				s16(2,zr)
				var fl=(zr>>8)&(FS|F3|F5)
				if zr==0:fl|=FZ
				if q==0:fl|=FN
				if (res>65535 if q!=0 else res<0):fl|=FC
				if ((a&4095)+(v&4095)+carry>4095 if q!=0 else (a&4095)<(v&4095)+carry):fl|=FH
				if ((~(a^v)&(a^zr)&32768)!=0 if q!=0 else ((a^v)&(a^zr)&32768)!=0):fl|=FP
				f=fl
			3:
				var addr=f16()
				if q!=0:s16(p,read8(addr)|(read8(addr+1)<<8))
				else:
					var v=r16(p)
					wr(addr,v)
					wr(addr+1,v>>8)
			4:r[7]=aflags(0,r[7],-r[7],false)
			5:
				pc=pop()
				iff=iff2
			7:
				if y==0:i=r[7]
				elif y==1:rreg=r[7]
				elif y==2 or y==3:
					r[7]=i if y==2 else rreg
					var v=r[7]
					f=(f&FC)|(v&(FS|F3|F5))|(FZ if v==0 else 0)|(FP if iff2 else 0)
	elif x==2 and y>=4 and z<=3:
		var d=-1 if y==5 or y==7 else 1
		var repeat=y>=6
		if z==0:
			var v=read8(r16(2))
			wr(r16(1),v)
			s16(2,r16(2)+d)
			s16(1,r16(1)+d)
			s16(0,r16(0)-1)
			f=(f&(FS|FZ|FC))|(FP if r16(0)!=0 else 0)|((r[7]+v)&(F3|F5))
			if repeat and r16(0)!=0:pc=(pc-2)&65535
		elif z==1:
			var v=read8(r16(2))
			s16(2,r16(2)+d)
			s16(0,r16(0)-1)
			var cf=f&FC
			aflags(r[7],v,r[7]-v,false)
			f=(f&~FC)|cf
			if r16(0)!=0:f|=FP
			else:f&=~FP
			if repeat and r16(0)!=0 and (f&FZ)==0:pc=(pc-2)&65535
		else:
			var addr=r16(2)
			if z==2:wr(addr,255)
			s16(2,addr+d)
			r[0]=(r[0]-1)&255
			f=(FZ if r[0]==0 else 0)|(FN if z==3 else 0)
			if repeat and r[0]!=0:pc=(pc-2)&65535

func step()->void:
	var op=fetch()
	var idx=0
	while op==0xdd or op==0xfd:
		idx=1 if op==0xdd else 2
		op=fetch()
	steps+=1
	var disp=0
	if op==0xcb:
		if idx!=0:
			disp=fetch()
			if disp>127:disp-=256
		cbop(fetch(),idx,disp)
		return
	if op==0xed:
		edop()
		return
	var x=op>>6
	var y=(op>>3)&7
	var z=op&7
	var p=y>>1
	var q=y&1
	if idx!=0 and ((x==1 and op!=0x76 and (y==6 or z==6)) or (x==2 and z==6) or (x==0 and z>=4 and z<=6 and y==6)):
		disp=fetch()
		if disp>127:disp-=256
	if x==0:
		low_op(y,z,p,q,idx,disp)
	elif x==1:
		if op==0x76:
			halted=true
		elif idx!=0 and (y==6 or z==6):
			var v=rr(z,0 if z==4 or z==5 else idx,disp)
			ww(y,v,0 if y==4 or y==5 else idx,disp)
		else:
			ww(y,rr(z,idx),idx)
	elif x==2:
		alu(y,rr(z,idx,disp))
	else:
		high_op(y,z,p,q,idx)

func low_op(y:int,z:int,p:int,q:int,idx:int,disp:int)->void:
	match z:
		0:
			match y:
				0:pass
				1:
					var a=r[7]
					r[7]=aa
					aa=a
					var old=f
					f=fa
					fa=old
				2:
					var v=fetch()
					r[0]=(r[0]-1)&255
					if r[0]!=0:jr(v)
				3:jr(fetch())
				_:
					var v=fetch()
					if condition(y-4):jr(v)
		1:
			if q==0:s16(p,f16(),idx)
			else:
				var a=r16(2,idx)
				var v=r16(p,idx)
				var res=a+v
				s16(2,res,idx)
				f=(f&(FS|FZ|FP))|((res>>8)&(F3|F5))|(FH if (a&4095)+(v&4095)>4095 else 0)|(FC if res>65535 else 0)
		2:
			if p<2:
				if q!=0:r[7]=read8(r16(p))
				else:wr(r16(p),r[7])
			elif p==2:
				var addr=f16()
				if q!=0:s16(2,read8(addr)|(read8(addr+1)<<8),idx)
				else:
					var v=r16(2,idx)
					wr(addr,v)
					wr(addr+1,v>>8)
			else:
				var addr=f16()
				if q!=0:r[7]=read8(addr)
				else:wr(addr,r[7])
		3:s16(p,r16(p,idx)+(-1 if q!=0 else 1),idx)
		4:ww(y,incdec(rr(y,idx,disp)),idx,disp)
		5:ww(y,incdec(rr(y,idx,disp),true),idx,disp)
		6:ww(y,fetch(),idx,disp)
		7:acc_ops(y)

func acc_ops(y:int)->void:
	var a=r[7]
	var oldc=(f&FC)!=0
	var v=0
	var c=0
	match y:
		0:
			v=((a<<1)|(a>>7))&255
			c=a>>7
		1:
			v=(a>>1)|((a&1)<<7)
			c=a&1
		2:
			v=((a<<1)|(1 if oldc else 0))&255
			c=a>>7
		3:
			v=(a>>1)|(128 if oldc else 0)
			c=a&1
		4:
			var dif=(6 if (a&15)>9 or (f&FH)!=0 else 0)+(0x60 if a>153 or oldc else 0)
			v=(a-dif if (f&FN)!=0 else a+dif)&255
			f=(f&FN)|(v&FS)|(FZ if v==0 else 0)|parity[v]|(v&(F3|F5))|(FC if oldc or (dif&0x60)!=0 else 0)|(FH if ((a^v)&16)!=0 else 0)
			r[7]=v
			return
		5:
			r[7]=a^255
			f=(f&(FS|FZ|FP|FC))|FH|FN|(r[7]&(F3|F5))
			return
		6:
			f=(f&(FS|FZ|FP))|(a&(F3|F5))|FC
			return
		7:
			f=(f&(FS|FZ|FP))|(a&(F3|F5))|(FH if oldc else FC)
			return
	r[7]=v
	f=(f&(FS|FZ|FP))|(v&(F3|F5))|c

func high_op(y:int,z:int,p:int,q:int,idx:int)->void:
	match z:
		0:
			if condition(y):pc=pop()
		1:
			if q==0:
				var v=pop()
				if p==3:
					r[7]=v>>8
					f=v&255
				else:s16(p,v,idx)
			else:
				match p:
					0:pc=pop()
					1:
						for j in range(6):
							var v=r[j]
							r[j]=alt[j]
							alt[j]=v
					2:pc=r16(2,idx)
					3:sp=r16(2,idx)
		2:
			var addr=f16()
			if condition(y):pc=addr
		3:
			match y:
				0:pc=f16()
				1:pass
				2:
					var port=fetch()
					if sound_bus!=null:sound_bus.output_port(port,r[7])
				3:
					var port=fetch()
					r[7]=255 if sound_bus==null else sound_bus.input_port(port)
				4:
					var v=pop()
					var old=r16(2,idx)
					s16(2,v,idx)
					push(old)
				5:
					var v=r16(2,idx)
					s16(2,r16(1),idx)
					s16(1,v)
				6:
					iff=false
					iff2=false
				7:
					iff=true
					iff2=true
		4:
			var addr=f16()
			if condition(y):
				push(pc)
				pc=addr
		5:
			if q==0:
				push((r[7]<<8)|f if p==3 else r16(p,idx))
			elif p==0:
				var addr=f16()
				push(pc)
				pc=addr
		6:alu(y,fetch())
		7:
			push(pc)
			pc=y*8

func nmi()->void:
	if nmi_enable!=0:
		halted=false
		iff2=iff
		iff=false
		push(pc)
		pc=0x66

func tick()->void:
	for k in range(6500):
		if halted:break
		step()
	nmi()

func irq(vector:int)->bool:
	if not iff:return false
	halted=false
	iff=false
	iff2=false
	push(pc)
	var addr=((i<<8)|(vector&255))&65535
	pc=read8(addr)|(read8(addr+1)<<8)
	return true
