/* Modal keyboard navigation, with the game behind the dialog made inert. */
export function createDialogFocus(doc){
 let active=null,previous=null;
 const saved=new Map();
 const selector='button:not([disabled]),a[href],input:not([disabled]),select:not([disabled]),textarea:not([disabled]),[tabindex="0"]';
 const visible=el=>!el.closest('[hidden]')&&el.getClientRects().length>0;
 const controls=()=>active?[...active.querySelectorAll(selector)].filter(visible):[];
 const restore=()=>{
  for(const [node,value] of saved)node.inert=value;
  saved.clear();
 };
 doc.addEventListener('keydown',event=>{
  if(!active||event.key!=='Tab')return;
  const items=controls();
  if(!items.length){event.preventDefault();return}
  const index=items.indexOf(doc.activeElement);
  if(event.shiftKey&&(index<=0)){
   event.preventDefault();items.at(-1).focus();
  }else if(!event.shiftKey&&(index===-1||index===items.length-1)){
   event.preventDefault();items[0].focus();
  }
 });
 return {
  open(layer){
   if(active===layer)return;
   if(!active)previous=doc.activeElement;
   restore();active=layer;
   for(const node of layer.parentElement.children){
    if(node===layer)continue;
    saved.set(node,node.inert);node.inert=true;
   }
   doc.defaultView.requestAnimationFrame(()=>{
    if(active!==layer||layer.hidden)return;
    const first=controls().find(el=>el.matches('input'))||controls()[0];
    first?.focus();
   });
  },
  close(layer){
   if(active!==layer)return;
   active=null;restore();
   if(previous?.isConnected&&!previous.disabled)previous.focus();
   previous=null;
  }
 };
}
