/* V9: independent arcade session state; no ROM contents in this file.
 * Rules researched from the 1995 operator manual and Shmups Wiki.
 * Stage IDs are research labels, not proof that their graphics/AI are implemented.
 */
(function(root){
'use strict';
const roster=Object.freeze([
 {id:'p38',name:'P-38 Lightning',shot:'straight',option:'homing',charge:'concentrated',bomb:'explosion'},
 {id:'p51',name:'P-51 Mustang',shot:'wide',option:'straight',charge:'orbit',bomb:'bombing-run'},
 {id:'spitfire',name:'Spitfire Mk.VII',shot:'wide',option:'drill',charge:'split',bomb:'flame'},
 {id:'bf109',name:'Messerschmitt Bf-109',shot:'straight',option:'tracking',charge:'concentrated',bomb:'bombing-run'},
 {id:'zero',name:'Zero Fighter',shot:'wide',option:'mine',charge:'lock-on',bomb:'lightning'},
 {id:'shinden',name:'J7W Shinden',shot:'narrow',option:'straight',charge:'side',bomb:'energy'}
].map(Object.freeze));
const randomStages=Object.freeze(['usa','japan','germany','ussr']);
const fixedStages=Object.freeze(['fixed5','fixed6','fixed7','fixed8']);
function route(rng){
 const a=randomStages.slice();
 for(let i=a.length-1;i>0;i--){const j=Math.floor(rng()*(i+1));[a[i],a[j]]=[a[j],a[i]];}
 return a.concat(fixedStages);
}
function create(rng=Math.random){
 let credits=0,mode='title',loop=0,index=0,selected=null,continues=0,order=[],results=[];
 function state(){return Object.freeze({credits,mode,loop,index,selected,continues,stage:order[index]??null,order:order.slice(),results:results.slice()});}
 function coin(){credits=Math.min(99,credits+1);return state();}
 function start(){
  if(mode!=='title'||credits===0)return false;
  credits--;mode='select';loop=1;index=0;selected=null;continues=0;order=route(rng);results=[];return true;
 }
 function choose(id){if(mode!=='select'||!roster.some(p=>p.id===id))return false;selected=id;mode='play';return true;}
 function clear(metrics={}){
  if(mode!=='play')return false;
  results.push(Object.freeze({loop,stage:index+1,id:order[index],time:Math.max(0,Number(metrics.time)||0),gold:Math.max(0,Number(metrics.gold)||0),shootDown:Math.max(0,Number(metrics.shootDown)||0)}));
  if(index<7){index++;return true;}
  mode=loop===1?'results':'ending';return true;
 }
 function nextLoop(){if(mode!=='results')return false;loop=2;index=0;order=route(rng);mode='play';return true;}
 function lose(){if(mode!=='play')return false;mode=loop===1?'continue':'gameover';return true;}
 function resume(){if(mode!=='continue'||credits===0)return false;credits--;continues++;mode='play';return true;}
 function decline(){if(mode!=='continue')return false;mode='gameover';return true;}
 function title(){mode='title';loop=0;index=0;selected=null;order=[];results=[];continues=0;return state();}
 return Object.freeze({state,coin,start,choose,clear,nextLoop,lose,resume,decline,title});
}
root.StrikersArcade=Object.freeze({roster,randomStages,fixedStages,create,route});
})(typeof window==='object'?window:globalThis);
