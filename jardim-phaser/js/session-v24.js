/* V2.4: visual state of the demo session, never financial calculations. */
export function sessionState({ready=false,writerReady=false,mutating=false,saveIssue=false,paused=false,mode='base'}={}){
 if(!ready)return {kind:'boot',message:'',action:null};
 if(!writerReady)return {kind:'blocked',message:'Sessão em modo de leitura. Outra aba pode estar ativa.',action:'TENTAR RECUPERAR'};
 if(saveIssue)return {kind:'error',message:'Falhou a gravação local. Revê a sessão antes de jogar.',action:'REVER SESSÃO'};
 if(mutating)return {kind:'saving',message:'A guardar os créditos fictícios…',action:null};
 if(paused&&mode==='free')return {kind:'paused',message:'Free Spins pausadas até tocares em Retomar.',action:'RETOMAR FREE SPINS'};
 return {kind:'ok',message:'',action:null};
}
export function canResumeFree({ready,writerReady,paused,mode,hidden,running,mutating}={}){
 return !!(ready&&writerReady&&paused&&mode==='free'&&!hidden&&!running&&!mutating);
}
