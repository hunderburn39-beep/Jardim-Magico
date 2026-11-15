# Servidor de telemetria do Faraó Eterno

Este servidor é **opcional**. O jogo publicado em GitHub Pages continua a funcionar sem ele. O servidor guarda os relatórios das máquinas, mas **não calcula resultados, não executa rodadas e não movimenta dinheiro real**.

## 1. Instalação

Requisitos: Node.js 20+ e uma máquina/serviço com **disco persistente**, endereço **HTTPS** e acesso de rede. No GitHub Pages não é possível executar este backend. Não instalar numa plataforma com armazenamento efémero sem montar um volume persistente.

1. Abre o jogo e entra em **Administração → Ligação ao servidor**. Copia o **ID desta máquina**, por exemplo `farao-xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx`.
2. Cria um token aleatório *diferente para cada máquina* (mínimo 16 caracteres) e um segundo token para consulta administrativa. Guarda-os fora do repositório Git.
3. No servidor, configura as variáveis de ambiente. Em Linux/macOS, por exemplo:

```bash
export FARAO_MACHINE_TOKENS_JSON='{"farao-ID-DA-TUA-MAQUINA":"TOKEN_FORTE_ALEATORIO_DA_MAQUINA"}'
export FARAO_ADMIN_TOKEN='OUTRO_TOKEN_FORTE_PARA_RELATORIOS'
export FARAO_ALLOWED_ORIGINS='https://hunderburn39-beep.github.io'
export FARAO_DATA_DIR='/var/lib/farao-eterno'
export PORT=8080
node Farao-Eterno/server/server.mjs
```

4. Expõe o serviço através de HTTPS, por exemplo usando um proxy inverso Caddy/Nginx. Não exponhas diretamente a porta HTTP sem proteção TLS.
5. No menu administrativo do jogo, introduz a URL HTTPS pública do backend e o token atribuído à máquina. Carrega em **Ligar / guardar**, depois **Testar ligação** e **Enviar dados agora**.

Sem esses passos, o painel diz **Sem servidor configurado**. O jogo não cria uma ligação fictícia.

## 2. Dados enviados

- Identificador da máquina e data/hora da última fotografia
- Saldo em créditos simulados, fundo de prémios e lucros por levantar
- Contadores de depósitos, levantamentos, apostas, bónus e prémios
- Taxa/perfil e percentagens atuais
- Últimas 20 ações resumidas e últimos 40 movimentos de auditoria
- Últimos 24 períodos arquivados dos contadores

**Não são enviados** PINs, tokens, grelhas completas, imagens nem gravações. Os tokens são guardados no armazenamento local do navegador para autenticar os pedidos, o que não é seguro para operações financeiras reais. O cliente não é resistente a manipulação de dados.

São efetuados envios após alterações, aproximadamente a cada 45 segundos e ao recuperar a ligação. Quando não existe rede, o jogo guarda o estado local; no próximo envio transmite o **estado atual**, incluindo períodos arquivados. O servidor mantém os snapshots em `data/machines/` e um histórico diário JSONL em `data/events/`. Configura backups e rotação dos ficheiros de eventos.

## 3. API

Todos os endpoints exigem token. Os endpoints de máquina usam:

```
Authorization: Bearer TOKEN_DA_MAQUINA
X-Machine-ID: farao-ID-DA-TUA-MAQUINA
```

- `GET /api/v1/health` — confirmar ligação e credenciais
- `POST /api/v1/snapshots` — guardar a fotografia atual
- `GET /api/v1/admin/machines` — listar máquinas autorizadas com dados (token administrativo)
- `GET /api/v1/admin/machines/:id` — consulta do último snapshot (token administrativo)

Exemplo de consulta privada:

```bash
curl -H "Authorization: Bearer $FARAO_ADMIN_TOKEN" \
  https://teu-servidor.exemplo/api/v1/admin/machines
```

A API limita o tamanho das mensagens e os pedidos dos browsers à origem permitida. O certificado HTTPS e o controlo de acessos externos ficam a cargo do teu alojamento.

## 4. Contadores e atualizações automáticas

**Limpar contadores** (admin): solicita o PIN e a palavra LIMPAR. Inicia um novo período e coloca os totais estatísticos e os contadores de giros a zero; **não apaga** o saldo, os cofres, os créditos de lucro, a numeração de jogadas nem o histórico. Arquiva até 24 períodos e inclui-os nos relatórios JSON e nos snapshots remotos. Não elimina dados já enviados ao servidor.

**Atualizações automáticas**: a página consulta o seu próprio `index.html` no GitHub Pages a cada 3 minutos e compara as versões dos ficheiros JS/CSS. Se estiverem diferentes, **apenas recarrega quando não existe rodada/bónus/resultado pendente e não há janelas abertas**. Não aceita código remoto proveniente do servidor de telemetria. Publicar o código atualizado no GitHub Pages e atualizar a versão dos JS/CSS no `index.html` continua a ser obrigatório.

## 5. Limites de segurança

Este sistema é para **testes e créditos virtuais**. Os códigos locais da aplicação não são autenticação segura, o cliente controla a contabilidade local e o servidor recebe os números que o navegador declara. Isto não constitui contabilidade auditável ou sistema de pagamento real. Para operação comercial com dinheiro seriam necessárias autenticação e autorização de servidor, contabilidade transacional imutável, RNG e pagamentos validados no backend, certificação e conformidade regulatória.
