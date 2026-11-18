"""Validate the World ROM without publishing or extracting its bytes.
Usage: python audit-rom.py /path/to/s1945.zip
"""
import hashlib
import struct
import sys
import zipfile
import zlib

EXPECTED = {'2s.u40':0x9b10062a,'3s.u41':0xf87e871a,'3-u63.bin':0x42d40ae1,'u34.bin':0xaaf83e23,'u20.bin':0x28a27fee,'u22.bin':0xca152a32,'u21.bin':0xc5d60ea9,'u23.bin':0x48710332,'u1.bin':0xdee22654,'u61.bin':0xa839cf47}
with zipfile.ZipFile(sys.argv[1]) as z:
    for name, crc in EXPECTED.items():
        assert zlib.crc32(z.read(name)) == crc, name
    a,b=z.read('2s.u40'),z.read('3s.u41')
p=bytearray(2*len(a))
p[0::4],p[1::4],p[2::4],p[3::4]=a[1::2],a[0::2],b[1::2],b[0::2]
assert hashlib.sha256(p).hexdigest()=='7f944fd9b26e96b7c646bbe2576088a4ce305f5fd3c7c9707c0b16e2db4387f7'
w=lambda a:struct.unpack_from('>H',p,a)[0]
l=lambda a:struct.unpack_from('>I',p,a)[0]
expected=0; defs={}
for a in range(0x1f9c4,0x242cc,4):
    h,lo=w(a),w(a+2)
    code=lo|((h&1)<<16); nx=(h>>8&7)+1; ny=(h>>12&7)+1
    assert not h&~0x7701 and code==expected, hex(a)
    defs[code]=(nx,ny); expected+=nx*ny
assert len(defs)==4674 and expected==94689
for i,(name,base,config_addr) in enumerate(zip(['p38','p51','spitfire','bf109','zero','shinden'],[5511,5903,5943,5983,6462,6502],[0x3d6cc,0x3d8dc,0x3dc9c,0x3e06c,0x3e27c,0x3e63c])):
    q=l(0x3d6b4+4*i); assert q==config_addr
    t=l(q+0x14); assert t==0x3b384+i*160
    records=[struct.unpack_from('>4H',p,t+8*j) for j in range(20)]
    for j,(flags,code,rx,ry) in enumerate(records):
        assert flags==8*(j%2) and code==base+4*(j//2) and defs[code]==(2,2)
        assert rx==0x3ff1 and ry==[0x3fed,0x3fec,0x3fea,0x3fea,0x3fea,0x3fee][i]
    print(name, 'configuration',hex(q),'20 records / 10 images')
assert [(l(0x59654+i*4)-0x1b944)//32 for i in range(16)]==[15]+list(range(15))
print('PASS: 10 original CRCs, program SHA256, 4674 descriptors, 120 aircraft records, 16 palette pointers')

# Verify native UI descriptor starts and dimensions against the real program.
ui={1439:(1,8),1447:(1,5),1452:(1,4),1458:(1,5),1463:(1,4),1467:(1,4),1471:(1,5),1478:(1,3),1485:(1,8),1493:(1,2),1505:(8,8),1569:(1,6),1575:(2,3),1581:(1,5),1586:(1,2),1588:(1,2),1592:(1,2),1665:(1,2),1669:(1,2)}
for code,dimensions in ui.items():
    assert defs[code]==dimensions,(code,defs[code])
for code in range(1495,1505):
    assert defs[code]==(1,1)
print('PASS: 29 UI sprite assembly sizes, including the original title publisher and ending')
