import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {join,dirname} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
assert.match(html,/Stake Preview V1\.8\.4/);
assert.match(html,/V1\.8\.4 • TAMBORES MAIS LENTOS/);
assert.match(html,/player\.loop=name==="spin"/,'Recorded spinning audio loops until the reels stop');
assert.match(html,/\.cell\.spinning img,[^\n]*animation-duration:\.20s/);
assert.match(html,/\.cell\.landing\{animation-duration:\.34s\}/);
const raw=html.match(/const REEL_SPIN_PROFILE=Object\.freeze\([\s\S]*?\nasync function animateCascade/);
assert.ok(raw,'The game must expose the actual reel animation routine');
const source=raw[0].slice(0,-'async function animateCascade'.length);
new vm.Script(source);
const endBoard=Array.from({length:5},(_,c)=>[200+c,200+c,200+c]);
async function scenario(isTurbo,skipAt=0){
  const pauses=[],played=[],drawn=[];
  let stopCount=0,previewCount=0;
  const cells=Array.from({length:15},()=>({classList:{add(){}}}));
  const columns=Array.from({length:5},()=>({querySelectorAll:()=>cells.slice(0,3),classList:{add(){}}}));
  const ctx=vm.createContext({
    document:{querySelectorAll(selector){return selector==='.cell'?cells:selector==='.column'?columns:[]}},
    turbo:isTurbo,skipAnimations:false,
    board(){previewCount++;return Array.from({length:5},(_,c)=>[c,c,c])},
    draw(b){drawn.push(JSON.stringify(b))},
    playRecorded(name){played.push(name)},
    stopSpinEffect(){stopCount++},
    pause(ms){pauses.push(ms);if(skipAt&&pauses.length===skipAt)ctx.skipAnimations=true;return Promise.resolve()},
    Object,Math,console
  });
  vm.runInContext(source,ctx);
  ctx.finalBoard=endBoard;
  await vm.runInContext('animateReelStop(finalBoard)',ctx);
  assert.equal(drawn.at(-1),JSON.stringify(endBoard),'Always display the precommitted final result');
  assert.equal(stopCount,1,'Spinning audio is stopped exactly once even on PASSAR');
  assert.equal(played[0],'spin','Spinning audio begins once');
  return {pauses,played,drawn,previewCount,totalMs:pauses.reduce((a,b)=>a+b,0)};
}
const normal=await scenario(false);
assert.equal(normal.pauses.length,16,'11 visible frames followed by five stops');
assert.deepEqual(normal.pauses.slice(0,11),Array(11).fill(125));
assert.deepEqual(normal.pauses.slice(11),[175,215,255,295,335],'One after another, the reels stop progressively more slowly');
assert.equal(normal.totalMs,2650,'Normal spin is 2.65 seconds');
assert.ok(normal.totalMs>670*3,'Normal spin is at least three times slower than V1.8.3');
assert.deepEqual(normal.played.slice(1),Array(5).fill('stop'));
const turbo=await scenario(true);
assert.equal(turbo.totalMs,135,'TURBO stays quick');
assert.equal(turbo.pauses.length,6,'One preview frame and five fast stops in TURBO');
assert.equal(turbo.played.filter(x=>x==='stop').length,5);
const skipped=await scenario(false,3);
assert.equal(skipped.pauses.length,3,'PASSAR breaks running animation immediately');
assert.equal(skipped.played.filter(x=>x==='stop').length,0,'No phantom reel-stop sounds after PASSAR');
assert.ok(skipped.totalMs<normal.totalMs);
console.log('Stake Preview V1.8.4: normal rotation 2650 ms, TURBO 135 ms, five sequential stops, PASSAR and precommitted results verified.');
