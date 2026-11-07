/* Contrato de dados V2.1: apenas driver local, nenhum backend. */
export const STORAGE_METHODS=Object.freeze(['connect','load','save','history','exportData','restoreData','clear','acquireLease','renewLease','releaseLease','checkpoint']);
export function createStorageGateway(driver){
 if(!driver||typeof driver!=='object')throw TypeError('Driver em falta.');
 for(const m of STORAGE_METHODS)if(typeof driver[m]!=='function')throw TypeError('Driver sem '+m);
 const api={provider:'local-demo',isCentralized:false,
  get mode(){return driver.mode},get lastError(){return driver.lastError}};
 for(const method of STORAGE_METHODS)api[method]=(...args)=>driver[method](...args);
 return Object.freeze(api);
}
