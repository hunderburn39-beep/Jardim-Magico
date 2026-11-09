/* CI static server, including byte ranges for Safari/WebKit audio streaming. */
import {createServer} from 'node:http';
import {createReadStream} from 'node:fs';
import {stat} from 'node:fs/promises';
import {resolve,join,extname,sep} from 'node:path';
const root=resolve('.');
const types={'.html':'text/html; charset=utf-8','.js':'text/javascript; charset=utf-8',
 '.mjs':'text/javascript; charset=utf-8','.css':'text/css; charset=utf-8',
 '.json':'application/json','.svg':'image/svg+xml','.png':'image/png',
 '.webp':'image/webp','.jpg':'image/jpeg','.jpeg':'image/jpeg',
 '.mp3':'audio/mpeg','.ogg':'audio/ogg','.wav':'audio/wav'};
createServer(async(req,res)=>{
 try{
  if(!['GET','HEAD'].includes(req.method)){res.writeHead(405);return res.end()}
  const path=decodeURIComponent(new URL(req.url,'http://127.0.0.1').pathname);
  if(path.split('/').some(p=>p.startsWith('.'))){res.writeHead(403);return res.end()}
  let file=resolve(root,'.'+path);
  if(file!==root&&!file.startsWith(root+sep)){res.writeHead(403);return res.end()}
  let info=await stat(file);
  if(info.isDirectory()){file=join(file,'index.html');info=await stat(file)}
  if(!info.isFile())throw Error('Not a file');
  let start=0,end=info.size-1,status=200;
  const headers={'Content-Type':types[extname(file)]||'application/octet-stream',
   'Accept-Ranges':'bytes','Cache-Control':'no-cache'};
  if(req.headers.range){
   const match=/^bytes=(\d*)-(\d*)$/.exec(req.headers.range);
   if(!match||(!match[1]&&!match[2])){res.writeHead(416,{'Content-Range':'bytes */'+info.size});return res.end()}
   if(!match[1])start=Math.max(0,info.size-Number(match[2]));
   else{start=Number(match[1]);if(match[2])end=Math.min(end,Number(match[2]))}
   if(start>end||start>=info.size){res.writeHead(416,{'Content-Range':'bytes */'+info.size});return res.end()}
   status=206;headers['Content-Range']='bytes '+start+'-'+end+'/'+info.size;
  }
  headers['Content-Length']=Math.max(0,end-start+1);
  res.writeHead(status,headers);
  if(req.method==='HEAD'||info.size===0)return res.end();
  createReadStream(file,{start,end}).on('error',()=>res.destroy()).pipe(res);
 }catch{if(!res.headersSent)res.writeHead(404);res.end()}
}).listen(Number(process.env.PORT)||8123,'127.0.0.1');
