# V2.1 — acesso local e arquitetura futura

A demonstração utiliza exclusivamente créditos fictícios em IndexedDB/localStorage. O PIN de 6 algarismos apenas protege a interface do acesso casual no mesmo dispositivo. É guardada uma derivação PBKDF2/SHA256 com sal aleatório, 180000 iterações, sem PIN legível. Após 4 erros há um bloqueio local de 45 segundos, e a sessão expira após 8 minutos sem renovação. O PIN não entra nos backups JSON.

**O PIN não constitui autenticação real.** Quem controla o JavaScript ou os dados do navegador pode contorná-lo. Se esqueceres o PIN, pode ser necessário apagar os dados do site e perder a sessão; guarda um backup JSON previamente.

`js/storage-gateway-v21.js` isola as operações `connect, load, save, history, exportData, restoreData, clear` da interface. Neste momento encaminha-as exclusivamente à base local. Não existe backend, API remota, sincronização ou conta de operador.

Uma futura base central precisa de autenticação e autorização no servidor, transações e movimentos idempotentes, saldos e prémios calculados no backend, resolução de conflitos, auditoria e backups. Uma utilização a dinheiro real exigiria licenciamento e certificação próprios.
