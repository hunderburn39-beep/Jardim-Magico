extends RefCounted
## Original 3 MHz sound Z80, 2 active CTC channels and 3 x AY-3-8910.
const CPU=preload("res://z80.gd")
const AY=preload("res://ay8910.gd")
const LOW=[4,10,7,6,4,4,7,4,4,11,7,6,4,4,7,4,8,10,7,6,4,4,7,4,12,11,7,6,4,4,7,4,7,10,16,6,4,4,7,4,7,11,16,6,4,4,7,4,7,10,13,6,11,11,10,4,7,11,13,6,4,4,7,4]
const HIGH=[5,10,10,10,10,11,7,11,5,10,10,8,10,17,7,11,5,10,10,11,10,11,7,11,5,4,10,11,10,4,7,11,5,10,10,19,10,11,7,11,5,4,10,4,10,8,7,11,5,10,10,4,10,11,7,11,5,6,10,4,10,4,7,11]
var cpu
var ay: Array = []
var ctc: Array[Dictionary] = []
var pending := [0,0,0,0]
var vector_base := 0
var in_service := -1
var latch := 0
var waiting := -1
var ready := false
var debt := 0.0
var cycles_per_sample:float
var dc := 0.0
var volume := 0.78
var frames := 0
var commands := 0

func setup(rate:float)->bool:
	var doc=JSON.parse_string(FileAccess.get_file_as_string("res://data/sound_rom.json"))
	if not doc is Dictionary:return false
	var rom:=Marshalls.base64_to_raw(doc["rom"])
	if rom.size()!=8192:return false
	var mem:=PackedByteArray()
	mem.resize(65536)
	for n in range(rom.size()):mem[n]=rom[n]
	cpu=CPU.new(mem,{"sp":65535})
	cpu.sound_bus=self
	cycles_per_sample=3000000.0/rate
	for n in range(3):ay.append(AY.new(rate))
	for n in range(4):
		ctc.append({"control":0,"constant":0,"pending_const":false,"counter":false,"prescale":16,"enabled":false,"cycles":0})
	return true

func close()->void:
	if cpu!=null:
		cpu.sound_bus=null
		cpu=null

func read_mem(addr:int,mem:PackedByteArray)->int:
	if addr==0x8000:return latch
	if addr>=0x2000 and addr<0x4000:return 255
	if addr>=0x4800:return 255
	return mem[addr]

func input_port(_port:int)->int:
	return 255

func output_port(port:int,value:int)->void:
	port&=255
	value&=255
	if port<4:
		var c:Dictionary=ctc[port]
		if c["pending_const"]:
			c["constant"]=value if value!=0 else 256
			c["pending_const"]=false
			c["cycles"]=0
			c["enabled"]=true
		elif (value&1)==0:
			if port==0:vector_base=value&248
		else:
			c["control"]=value
			c["counter"]=(value&64)!=0
			c["prescale"]=256 if (value&32)!=0 else 16
			c["pending_const"]=(value&4)!=0
			c["enabled"]=false
			c["cycles"]=0
		return
	if port==0x10 or port==0x11:ay[0].write(port,value)
	elif port==0x20 or port==0x21:ay[1].write(port,value)
	elif port==0x30 or port==0x31:ay[2].write(port,value)

func command(value:int)->void:
	latch=value&255
	commands+=1
	if ready:trigger0()
	else:waiting=latch

func trigger0()->void:
	var c:Dictionary=ctc[0]
	if c["enabled"] and c["counter"]:
		c["cycles"]+=1
		if c["cycles"]>=c["constant"]:
			c["cycles"]=0
			if (c["control"]&128)!=0:pending[0]=1

func timer(cycles:int)->void:
	for ch in range(2):
		var c:Dictionary=ctc[ch]
		if not c["enabled"] or c["counter"]:continue
		c["cycles"]+=cycles
		var period:int=c["prescale"]*c["constant"]
		if period>0 and c["cycles"]>=period:
			c["cycles"]%=period
			if (c["control"]&128)!=0:pending[ch]=1

func base_cycles(op:int)->int:
	if op<0x40:return LOW[op]
	if op<0x80:return 4 if op==0x76 else 7 if (op&7)==6 or ((op>>3)&7)==6 else 4
	if op<0xc0:return 7 if (op&7)==6 else 4
	return HIGH[op-0xc0]

func flag_condition(flags:int,index:int)->bool:
	var mask=64 if index<2 else 1 if index<4 else 4 if index<6 else 128
	return ((flags&mask)!=0)==((index&1)!=0)

func instruction_cycles(mem:PackedByteArray,pc:int,next_pc:int,flags:int)->int:
	var op:=mem[pc&65535]
	if op==0x10:return 8 if next_pc==((pc+2)&65535) else 13
	if op in [0x20,0x28,0x30,0x38]:return 7 if next_pc==((pc+2)&65535) else 12
	if op>=0xc0 and (op&0xc7)==0xc0:return 11 if flag_condition(flags,(op>>3)&7) else 5
	if op>=0xc4 and (op&0xc7)==0xc4:return 17 if flag_condition(flags,(op>>3)&7) else 10
	if op==0xcb:
		var cb:=mem[(pc+1)&65535]
		return 8 if (cb&7)!=6 else 12 if (cb&0xc0)==0x40 else 15
	if op==0xed:
		var ed:=mem[(pc+1)&65535]
		var x:=ed>>6
		var z:=ed&7
		var y:=(ed>>3)&7
		if x==1:return [12,12,15,20,8,14,8,9][z]
		if x==2 and y>=4 and z<=3:return 21 if next_pc==pc else 16
		return 8
	if op==0xdd or op==0xfd:
		var indexed:=mem[(pc+1)&65535]
		if indexed==0xcb or indexed in [0x34,0x35,0xe3]:return 23
		if indexed==0x36:return 19
		if indexed==0x22 or indexed==0x2a:return 20
		if indexed==0xe9:return 8
		if indexed==0xf9:return 10
		if indexed>=0x40 and indexed<0xc0 and ((indexed&7)==6 or ((indexed>>3)&7)==6):return 19
		return base_cycles(indexed)+4
	return base_cycles(op)

func run_cpu()->void:
	debt+=cycles_per_sample
	var max_steps:=80
	while debt>0.0 and max_steps>0:
		max_steps-=1
		if not ready and ctc[0]["enabled"] and ctc[1]["enabled"] and cpu.iff:
			ready=true
			if waiting>=0:
				latch=waiting
				waiting=-1
				trigger0()
		if cpu.iff:
			for ch in range(2):
				if pending[ch]>0 and (in_service<0 or ch<in_service):
					if cpu.irq((vector_base+2*ch)&255):
						pending[ch]-=1
						in_service=ch
					break
		var pc:int=cpu.pc
		var op:int=cpu.mem[pc]
		var flags:int=cpu.f
		if not cpu.halted:cpu.step()
		var cycles:int=4 if cpu.halted and pc==cpu.pc else instruction_cycles(cpu.mem,pc,cpu.pc,flags)
		if op==0xed and cpu.mem[(pc+1)&65535]==0x4d:in_service=-1
		timer(cycles)
		debt-=cycles

func sample()->float:
	run_cpu()
	var x:float=(ay[0].sample()+ay[1].sample()+ay[2].sample())/9.0
	dc+=0.0005*(x-dc)
	frames+=1
	return clampf((x-dc)*volume*3.2,-1.0,1.0)
