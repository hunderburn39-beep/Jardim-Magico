/* Pinball Action — PixiJS 8.14.3 WebGL display adapter.
 * Original board coordinates, draw order and rotation are unchanged. */
(function(){
 'use strict';
 const library='https://cdn.jsdelivr.net/npm/pixi.js@8.14.3/dist/pixi.min.js';
 const load=()=>new Promise((resolve,reject)=>{
  if(window.PIXI){resolve(window.PIXI);return;}
  const script=document.createElement('script');
  script.src=library;script.async=true;
  script.onload=()=>window.PIXI?resolve(window.PIXI):reject(new Error('PixiJS missing'));
  script.onerror=()=>reject(new Error('PixiJS download failed'));
  document.head.appendChild(script);
 });
 class PinballPixi {
  constructor(original){
   this.original=original;this.ready=false;this.enabled=false;
   this.images=new Map();this.pending=[];this.pool=[];
   this.scale=4;this.status='loading';
  }
  async init(onReady,onFailure){
   try{
    const P=this.P=await load();
    const app=this.app=new P.Application();
    await app.init({width:224*this.scale,height:256*this.scale,preference:'webgl',antialias:true,autoStart:false,sharedTicker:false,backgroundColor:0,backgroundAlpha:1,preserveDrawingBuffer:true});
    app.stop();
    const root=this.root=new P.Container();
    root.position.set(240*this.scale,0);root.rotation=Math.PI/2;root.scale.set(this.scale);
    app.stage.addChild(root);
    this.back=new P.Container();this.motion=new P.Container();this.front=new P.Container();
    root.addChild(this.back,this.motion,this.front);
    for(const layer of [this.back,this.front]){
     layer.addChild(new P.Sprite(P.Texture.WHITE),new P.Sprite(P.Texture.WHITE));
    }
    const view=app.canvas;view.id='game-pixi';view.setAttribute('aria-hidden','true');
    view.style.cssText='position:absolute;inset:0;width:100%;height:100%;display:block;visibility:hidden;pointer-events:none;image-rendering:auto';
    this.original.parentElement.style.position='relative';
    this.original.parentElement.appendChild(view);
    view.addEventListener('webglcontextlost',e=>{e.preventDefault();this.ready=false;this.status='context-lost';this.show(false);onFailure?.();});
    view.addEventListener('webglcontextrestored',()=>{this.ready=true;this.status='webgl';onReady?.();});
    this.ready=true;this.status='webgl';onReady?.();
   }catch(e){this.status='canvas-fallback';this.ready=false;this.show(false);console.warn('Pinball Pixi fallback',e);onFailure?.();}
  }
  show(on){
   this.enabled=!!on&&this.ready;
   if(this.app?.canvas)this.app.canvas.style.visibility=this.enabled?'visible':'hidden';
   this.original.style.visibility=this.enabled?'hidden':'visible';
  }
  texture(image){
   let t=this.images.get(image);
   if(!t){t=this.P.Texture.from(image);t.source.scaleMode='linear';this.images.set(image,t);}
   return t;
  }
  invalidateSprites(){
   for(const [canvas,t] of this.images){
    if(canvas===this.boardImage||canvas===this.frontImage)continue;
    this.pending.push(t);this.images.delete(canvas);
   }
  }
  layers(board,front,bgChanged,fgChanged){
   this.boardImage=board;this.frontImage=front;
   const b=this.texture(board),f=this.texture(front);
   if(bgChanged)b.source.update();if(fgChanged)f.source.update();
   for(const [layer,t] of [[this.back,b],[this.front,f]])for(const s of layer.children){
    s.texture=t;s.scale.set(256/t.width,256/t.height);
   }
  }
  draw(board,front,bgChanged,fgChanged,scroll,commands,getArt){
   this.layers(board,front,bgChanged,fgChanged);
   for(const layer of [this.back,this.front]){
    layer.children[0].position.set(-scroll,0);
    layer.children[1].position.set(256-scroll,0);layer.children[1].visible=scroll!==0;
   }
   for(let i=0;i<commands.length;i++){
    const o=commands[i];let s=this.pool[i];
    if(!s){s=new this.P.Sprite(this.P.Texture.WHITE);this.pool.push(s);this.motion.addChild(s);}
    const t=this.texture(getArt(o));s.texture=t;s.visible=true;
    s.position.set(o.xx+(o.flipX?o.size:0),o.yy+(o.flipY?o.size:0));
    s.scale.set((o.flipX?-1:1)*o.size/t.width,(o.flipY?-1:1)*o.size/t.height);
   }
   for(let i=commands.length;i<this.pool.length;i++){this.pool[i].visible=false;this.pool[i].texture=this.P.Texture.WHITE;}
   this.app.render();
   for(const t of this.pending)t.destroy(true);
   this.pending.length=0;this.show(true);
  }
  image(){return this.enabled?this.app.canvas.toDataURL():this.original.toDataURL();}
 }
 window.PinballPixi=PinballPixi;
})();
