extends RefCounted
## AY-3-8910, matching the V8.89 original worklet's 1.5 MHz clock and DAC.
const DAC = [0.0,0.0106,0.0150,0.0222,0.0320,0.0466,0.0665,0.1039,0.1237,0.1986,0.2803,0.3548,0.4702,0.6030,0.7530,0.9250]
var rate: float
var reg := PackedByteArray()
var address := 0
var phase := PackedFloat64Array([0.0,0.0,0.0])
var noise_phase := 0.0
var lfsr := 0x1ffff
var noise_bit := 1
var env_phase := 0.0
var env_tick := 0
var env_continue := 0
var env_attack := 0
var env_alternate := 0
var env_hold := 0
var env_level := 0
var env_done := false

func _init(sample_rate:float):
	rate=sample_rate
	reg.resize(16)

func write(port:int,value:int)->void:
	if (port&1)==0:
		address=value&15
		return
	reg[address]=value&255
	if address==13:
		env_phase=0.0
		env_tick=0
		env_continue=(value>>3)&1
		env_attack=(value>>2)&1
		env_alternate=(value>>1)&1
		env_hold=value&1
		env_done=false

func sample()->float:
	var noise_period:=maxi(1,reg[6]&31)
	noise_phase+=1500000.0/(16.0*noise_period*rate)
	while noise_phase>=1.0:
		noise_phase-=1.0
		var feedback=(lfsr^(lfsr>>3))&1
		lfsr=(lfsr>>1)|(feedback<<16)
		noise_bit=lfsr&1
	var env_period:=maxi(1,(reg[12]<<8)|reg[11])
	env_phase+=1500000.0/(16.0*env_period*rate)
	while env_phase>=1.0:
		env_phase-=1.0
		if not env_done:
			env_tick+=1
			if env_tick>=16:
				env_tick=0
				if env_continue==0:
					env_done=true
					env_level=0
				elif env_hold!=0:
					env_done=true
					env_level=15 if env_attack!=0 else 0
				elif env_alternate!=0:
					env_attack^=1
	if not env_done:
		env_level=env_tick if env_attack!=0 else 15-env_tick
	var total:=0.0
	for ch in range(3):
		var period:=maxi(1,((reg[ch*2+1]&15)<<8)|reg[ch*2])
		phase[ch]+=1500000.0/(16.0*period*rate)
		if phase[ch]>=1.0:
			phase[ch]-=floorf(phase[ch])
		var tone_ok:bool=(reg[7]&(1<<ch))!=0 or phase[ch]<0.5
		var noise_ok:bool=(reg[7]&(1<<(ch+3)))!=0 or noise_bit!=0
		if not tone_ok or not noise_ok:
			continue
		var v:=reg[ch+8]
		total+=float(DAC[env_level if (v&16)!=0 else v&15])/0.925
	return total
