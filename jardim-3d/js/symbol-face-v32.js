/* Jardim Mágico 3D V3.2 — superfícies com orientação UV garantida.
 * Um símbolo não é colocado nas seis faces de um cubo: apenas numa
 * frente virada para a câmara, sem espelhos nem rotação de textura.
 * Os vértices da frente estão definidos em sentido anti-horário:
 * [0,0] canto inferior esquerdo e [1,1] canto superior direito.
 */
export const FRONT_UVS = Object.freeze([0,0, 1,0, 1,1, 0,1]);
export const FRONT_INDICES = Object.freeze([0,1,2, 0,2,3]);
export function createSymbolFace({pc,app,node,material,name='Símbolo HD'}){
 const mesh=new pc.Mesh(app.graphicsDevice);
 mesh.setPositions([
   -.5,-.5,0,
    .5,-.5,0,
    .5, .5,0,
   -.5, .5,0
 ]);
 mesh.setNormals([0,0,1, 0,0,1, 0,0,1, 0,0,1]);
 mesh.setUvs(0,FRONT_UVS);
 mesh.setIndices(FRONT_INDICES);
 mesh.update(pc.PRIMITIVE_TRIANGLES);
 const instance=new pc.MeshInstance(mesh,material);
 const face=new pc.Entity(name);
 face.addComponent('render',{
   meshInstances:[instance],
   castShadows:false,
   receiveShadows:false
 });
 face.setLocalPosition(0,0,.58);
 face.setLocalScale(.98,.98,1);
 node.addChild(face);
 return face;
}
export function createVibrantMaterial({pc,app,canvas,opacity=true}){
 const tex=new pc.Texture(app.graphicsDevice,{
   width:canvas.width,height:canvas.height,
   format:pc.PIXELFORMAT_RGBA8,mipmaps:true
 });
 tex.setSource(canvas);
 const m=new pc.StandardMaterial();
 // Não aplicar cores de iluminação à textura artística; a cor vem da arte.
 m.diffuse=new pc.Color(0,0,0);
 m.emissive=new pc.Color(1,1,1);
 m.emissiveMap=tex;
 m.emissiveIntensity=1.1;
 m.useMetalness=false;
 m.opacity=1;
 if(opacity){
   m.opacityMap=tex;
   m.opacityMapChannel='a';
   m.blendType=pc.BLEND_NORMAL;
   m.depthWrite=false;
 }
 m.cull=pc.CULLFACE_NONE;
 m.update();
 return m;
}
