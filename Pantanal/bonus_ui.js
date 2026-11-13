/* Pantanal v2.14 — quatro bónus em fila, recriados a partir do APK 2.06.
 * Referência: MainActivity.CallBonus / ShowMorango / ShowCaldeirao /
 * ShowAbobora / ShowRoleta / ShowDados. Sem apostas em dinheiro real.
 */
(function(root,factory){'use strict';
 const bonus=factory();
 if(typeof module==='object'&&module.exports)module.exports=bonus;
 if(root)root.PantanalBonusUI=bonus;
})(typeof globalThis==='undefined'?null:globalThis,function(){'use strict';
 function wait(ms){return new Promise(resolve=>setTimeout(resolve,ms));}
 function elem(type,text,style){
   const e=document.createElement(type);
   if(text!=null)e.textContent=String(text);
   if(style)e.style.cssText=style;
   return e;
 }
 const engine=()=>globalThis.PantanalApkEngine;
 const surface='position:absolute;inset:0;z-index:45;box-sizing:border-box;background:rgba(9,5,2,.93);display:flex;align-items:center;justify-content:center;';
 const panelStyle='box-sizing:border-box;max-width:760px;width:95%;min-height:355px;max-height:95%;overflow:auto;padding:25px 18px;border:6px ridge #e9b761;border-radius:22px;text-align:center;box-shadow:0 10px 48px #000;background:radial-gradient(ellipse at 40% 17%,#875225,#321907 76%);color:#ffecc4;font:26px Arial,sans-serif;';
 const buttonStyle='appearance:none;-webkit-appearance:none;border:4px solid #eaba70;cursor:pointer;border-radius:16px;background:radial-gradient(ellipse at 34% 25%,#be7c37,#643014 77%);box-shadow:0 6px 4px #120500;color:#fff2b2;font:900 32px Arial,sans-serif;touch-action:manipulation;user-select:none;';
 function open(game,title,subtitle,bg){
   const layer=elem('div',null,surface),panel=elem('div',null,panelStyle);
   layer.id='pantanalBonusModal';
   if(bg){
     const art=elem('div',null,'position:absolute;inset:0;background-image:url("'+bg+'");background-size:cover;background-position:center;opacity:.22;pointer-events:none;');
     layer.appendChild(art);
   }
   panel.style.zIndex='1';
   const h=elem('h2',title,'margin:3px 0 10px;font:900 40px Georgia,serif;color:#ffe7a2;text-shadow:0 3px 3px #160900;');
   const sub=elem('div',subtitle,'font:bold 22px Arial,sans-serif;margin-bottom:20px;');
   const grid=elem('div',null,'display:grid;grid-template-columns:repeat(5,1fr);gap:11px;justify-items:stretch;margin:12px 0 22px;');
   const feedback=elem('div',null,'font:bold 25px Arial,sans-serif;min-height:40px;color:#ffeaa1;');
   panel.append(h,sub,grid,feedback);layer.append(panel);
   game.appendChild(layer);
   return {layer,panel,grid,feedback};
 }
 function finish(state,delta,multiplier,hooks){
   if(!Number.isFinite(delta))throw new TypeError('Prémio inválido');
   const add=Math.max(0,Math.round(delta));
   state.lastWin+=add;
   state.credits+=add;
   state.totalPaid+=add;
   state.maxWin=Math.max(state.maxWin,state.lastWin);
   if(state.history[0]){
     state.history[0].total=state.lastWin;
     state.history[0].bonus=true;
     if(multiplier)state.history[0].multiplier=multiplier;
   }
   hooks.save();hooks.update();
   if(add)hooks.play('big');
   hooks.showStatus('BÓNUS: +'+hooks.money(add)+' • TOTAL '+hooks.money(state.lastWin));
 }
 function pickButton({grid,count,icon,assetUrl,cols=5}){
   grid.style.gridTemplateColumns='repeat('+cols+',minmax(0,1fr))';
   return Array.from({length:count},(_,i)=>{
     const b=elem('button',icon,buttonStyle+'width:100%;height:105px;font-size:42px;');
     b.type='button';b.setAttribute('aria-label','Escolher número '+(i+1));b.dataset.choice=String(i);
     if(assetUrl){b.style.backgroundImage='linear-gradient(#0004,#0005),url("'+assetUrl+'")';b.style.backgroundSize='cover';b.style.backgroundPosition='center';}
     grid.appendChild(b);return b;
   });
 }
 async function selectOne(buttons,lockAll=true){
   return new Promise(resolve=>{
     let selected=false;
     buttons.forEach((btn,i)=>btn.addEventListener('click',()=>{
       if(selected)return;selected=true;
       if(lockAll)buttons.forEach(x=>x.disabled=true);
       else btn.disabled=true;
       btn.style.transform='translateY(12px) scale(1.1)';
       btn.style.filter='brightness(1.4)';
       resolve(i);
     },{once:true}));
   });
 }
 async function straw(ctx,units){
   const {game,state,hooks,rng,asset}=ctx,eng=engine(),panel=open(game,'BÓNUS DOS MORANGOS','Escolhe morangos até aparecer o fim do bónus',asset('05_telab.png'));
   const cards=pickButton({grid:panel.grid,count:12,icon:'🍓',cols:4});
   cards.forEach(b=>{b.style.height='80px';b.style.fontSize='44px';});
   const paymentMode=ctx.nextDifficulty?ctx.nextDifficulty().active:ctx.difficulty?.active;
   const deck=eng.strawberryDeck(rng,{
      paymentMode,triggerCount:units,existingBonus:state.moraBns||0
   });let running=true,accum=0,remaining=12;
   while(running&&remaining>0){
     const active=cards.filter(b=>!b.disabled);
     const originalIdx=await selectOne(active,false);
     const card=active[originalIdx],idx=Number(card.dataset.choice);
     const category=deck[idx],prize=eng.strawberryPay(category,ctx.betPerLine,units);
     card.textContent=category===6?'FIM':hooks.money(prize);
     card.style.fontSize='23px';
     if(category===6){running=false;panel.feedback.textContent='FIM DA ESCOLHA · +'+hooks.money(accum);}
     else {accum+=prize;panel.feedback.textContent='MORANGOS · '+hooks.money(accum)+' CRÉDITOS';hooks.play('line');}
     remaining--;
     if(running)await wait(250);
   }
   finish(state,accum,0,hooks);await wait(1400);panel.layer.remove();
 }
 async function pumpkin(ctx,units,layer){
   const eng=engine(),deck=eng.pumpkinDeck(ctx.rng);
   layer.panel.firstChild.textContent='BÓNUS DAS 5 ABÓBORAS';
   layer.panel.children[1].textContent='Escolhe uma abóbora para revelar o prémio';
   layer.grid.replaceChildren();
   const cards=pickButton({grid:layer.grid,count:5,icon:'🎃',assetUrl:ctx.asset('05_anb.png')});
   const index=await selectOne(cards),category=deck[index];
   const amount=eng.pumpkinPay(category,ctx.betPerLine,units);
   cards[index].style.backgroundImage='none';
   cards[index].textContent=ctx.hooks.money(amount);
   cards[index].style.fontSize='26px';
   layer.feedback.textContent='ABÓBORAS · +'+ctx.hooks.money(amount);
   await wait(650);
   return amount;
 }
 async function cauldron(ctx,units){
   const {game,state,hooks,rng,asset}=ctx,eng=engine();
   const layer=open(game,'BÓNUS DO CALDEIRÃO','Escolhe um dos 5 caldeirões',asset('05_telab3.png'));
   const deck=eng.cauldronDeck(rng);
   const cards=pickButton({grid:layer.grid,count:5,icon:'🏺',assetUrl:asset('05_cal.png')});
   const chosen=await selectOne(cards);let category=deck[chosen],prize=0;
   // No APK, o modo de dificuldade pode trocar a opção alta por uma
   // das três primeiras. A reprodução completa da lógica permanece auditável.
   const restriction=(ctx.nextDifficulty?ctx.nextDifficulty():ctx.difficulty)?.active===false;
   if(restriction&&category>2){
     const low=deck.findIndex((v,i)=>i!==chosen&&v<3);
     if(low>=0)category=deck[low];
   }
   if(category===8){
     layer.feedback.textContent='ABÓBORAS DESBLOQUEADAS!';
     await wait(650);
     prize=await pumpkin(ctx,units,layer);
   }else{
     prize=eng.cauldronPay(category,ctx.betPerLine,units);
     cards[chosen].style.backgroundImage='none';
     cards[chosen].textContent=hooks.money(prize);
     cards[chosen].style.fontSize='26px';
     layer.feedback.textContent='CALDEIRÃO · +'+hooks.money(prize);
   }
   finish(state,prize,0,hooks);await wait(1200);layer.layer.remove();
 }
 async function dice(ctx){
   const {game,state,hooks,asset,rng}=ctx;
   const layer=open(game,'BÓNUS DOS DADOS','Escolhe um dos 5 dados',asset('05_telab3.png'));
   const cards=pickButton({grid:layer.grid,count:5,icon:'🎲',assetUrl:asset('dado.png')});
   const chosen=await selectOne(cards);
   const roll=engine().diceRoll(rng,(ctx.nextDifficulty?ctx.nextDifficulty():ctx.difficulty)?.active);
   const prior=state.lastWin,final=prior*roll.multiplier;
   cards[chosen].style.backgroundImage='none';
   cards[chosen].textContent='×'+roll.multiplier;
   layer.feedback.textContent='DADO ×'+roll.multiplier+' · '+hooks.money(final)+' CRÉDITOS';
   finish(state,final-prior,roll.multiplier,hooks);
   await wait(1800);layer.layer.remove();
 }
 async function wheel(ctx){
   const {game,state,hooks,asset,rng}=ctx;
   const layer=open(game,'ROLETA DO PANTANAL','Carrega para rodar a roleta',asset('05_telab2b.png'));
   layer.grid.style.cssText='position:relative;height:270px;display:flex;align-items:center;justify-content:center;gap:16px;';
   const wheel=elem('div',null,'width:250px;height:250px;border-radius:50%;border:8px solid #dfa74f;box-shadow:0 0 27px #f8d16d77;background:#6e320b url("'+asset('05_roleta.png')+'") no-repeat center/contain;transition:transform 4.2s cubic-bezier(.12,.65,.12,1);');
   const pointer=elem('div','▼','position:absolute;top:-12px;left:50%;transform:translateX(-50%);z-index:3;color:#ffea97;text-shadow:0 2px 4px #000;font-size:35px;font-weight:bold;');
   layer.grid.append(wheel,pointer);
   const button=elem('button','GIRAR ROLETA',buttonStyle+'padding:14px 26px;font-size:26px;margin:10px auto;display:block;');
   layer.panel.insertBefore(button,layer.feedback);
   await new Promise(resolve=>button.addEventListener('click',resolve,{once:true}));
   button.disabled=true;button.textContent='A RODAR...';
   const roll=engine().wheelRoll(rng,(ctx.nextDifficulty?ctx.nextDifficulty():ctx.difficulty)?.active,state.lastWin);
   hooks.play('wheel');
   const degrees=7*360+(360-roll.sector*15)%360;
   wheel.style.transform='rotate('+degrees+'deg)';
   await wait(4250);
   const prior=state.lastWin,final=prior*roll.multiplier;
   layer.feedback.textContent='CASA '+roll.displaySector+' · ×'+roll.multiplier+' · '+hooks.money(final)+' CRÉDITOS';
   finish(state,final-prior,roll.multiplier,hooks);
   button.textContent='PRÉMIO ×'+roll.multiplier;
   await wait(1500);layer.layer.remove();
 }
 async function launch(opts){
   const {bonus,state,hooks}=opts;
   const b={...{strawberry:0,cauldron:0,wheel:0,dice:0},...(bonus||{})};
   const ctx={...opts,game:document.getElementById('game'),rng:opts.rng||Math.random};
   if(!ctx.game)throw new Error('Área do Pantanal inexistente');
   hooks.setBusy(true);
   try{
     if(b.strawberry>0)await straw(ctx,b.strawberry);
     if(b.cauldron>0)await cauldron(ctx,b.cauldron);
     for(let i=0;i<Math.min(5,b.wheel);i++)await wheel(ctx);
     for(let i=0;i<Math.min(5,b.dice);i++)await dice(ctx);
   }finally{
     document.getElementById('pantanalBonusModal')?.remove();
     hooks.setBusy(false);
   }
 }
 return Object.freeze({launch});
});
