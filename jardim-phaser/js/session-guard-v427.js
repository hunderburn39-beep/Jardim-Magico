/* V4.2.7: só autorizar novas ações sobre créditos DEMO após sincronização. */
export function canWriteDemoSession({
 writerReady=false,pendingReload=false,releasing=false,
 suspended=false,rendererUnavailable=false,hidden=false
}={}){
 return !!writerReady&&!pendingReload&&!releasing&&!suspended&&
  !rendererUnavailable&&!hidden;
}
