package com.google.android.gms.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.internal.fd;
import com.google.android.gms.internal.fi;
import com.google.android.gms.internal.gk;
import com.google.android.gms.internal.gr;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/* JADX INFO: loaded from: classes.dex */
public final class GooglePlayServicesUtil {
    static final byte[][] Aj = {ag("0\u0082\u0004C0\u0082\u0003+ \u0003\u0002\u0001\u0002\u0002\t\u0000Âà\u0087FdJ0\u008d0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0004\u0005\u00000t1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android0\u001e\u0017\r080821231334Z\u0017\r360107231334Z0t1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android0\u0082\u0001 0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0082\u0001\r\u00000\u0082\u0001\b\u0002\u0082\u0001\u0001\u0000«V.\u0000Ø;¢\b®\n\u0096o\u0012N)Ú\u0011ò«VÐ\u008fXâÌ©\u0013\u0003é·TÓrö@§\u001b\u001dË\u0013\tgbNFV§wj\u0092\u0019=²å¿·$©\u001ew\u0018\u008b\u000ejG¤;3Ù`\u009bw\u00181EÌß{.XftÉáV[\u001fLjYU¿òQ¦=«ùÅ\\'\"\"Rèuäø\u0015Jd_\u0089qhÀ±¿Æ\u0012ê¿xWi»4ªy\u0084Ü~.¢vL®\u0083\u0007ØÁqT×î_d¥\u001aD¦\u0002ÂI\u0005AWÜ\u0002Í_\\\u000eUûï\u0085\u0019ûã'ð±Q\u0016\u0092Å o\u0019Ñ\u0083\u0085õÄÛÂÖ¹?hÌ)yÇ\u000e\u0018«\u0093\u0086k;ÕÛ\u0089\u0099U*\u000e;L\u0099ßXû\u0091\u008bíÁ\u0082º5à\u0003Á´±\rÒD¨î$ÿý38r«R!\u0098^Ú°ü\r\u000b\u0014[j¡\u0092\u0085\u008ey\u0002\u0001\u0003£\u0081Ù0\u0081Ö0\u001d\u0006\u0003U\u001d\u000e\u0004\u0016\u0004\u0014Ç}\u008cÂ!\u0017V%\u009a\u007fÓ\u0082ßkã\u0098ä×\u0086¥0\u0081¦\u0006\u0003U\u001d#\u0004\u0081\u009e0\u0081\u009b\u0080\u0014Ç}\u008cÂ!\u0017V%\u009a\u007fÓ\u0082ßkã\u0098ä×\u0086¥¡x¤v0t1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android\u0082\t\u0000Âà\u0087FdJ0\u008d0\f\u0006\u0003U\u001d\u0013\u0004\u00050\u0003\u0001\u0001ÿ0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0004\u0005\u0000\u0003\u0082\u0001\u0001\u0000mÒRÎï\u00850,6\nªÎ\u0093\u009bÏòÌ©\u0004»]z\u0016aø®F²\u0099B\u0004ÐÿJhÇí\u001aS\u001eÄYZb<æ\u0007c±g)zzãW\u0012Ä\u0007ò\bðË\u0010\u0094)\u0012M{\u0010b\u0019À\u0084Ê>³ù\u00ad_¸qï\u0092&\u009a\u008bâ\u008bñmDÈÙ \u008el²ð\u0005»?âË\u0096D~\u0086\u008es\u0010v\u00adE³?`\tê\u0019Áaæ&Aª\u0099'\u001dýR(ÅÅ\u0087\u0087]Û\u007fE'XÖaöÌ\fÌ·5.BLÄ6\\R52÷2Q7Y<JãAôÛAíÚ\r\u000b\u0010q§Ä@ðþ\u009e \u001c¶'ÊgCiÐ\u0084½/Ù\u0011ÿ\u0006Í¿,ú\u0010Ü\u000f\u0089:ãWb\u0091\u0090HÇïÆLqD\u0017\u0083B÷\u0005\u0081ÉÞW:õ[9\r×ý¹A\u00861\u0089]_u\u009f0\u0011&\u0087ÿb\u0014\u0010Ài0\u008a"), ag("0\u0082\u0004¨0\u0082\u0003\u0090 \u0003\u0002\u0001\u0002\u0002\t\u0000Õ\u0085¸l}ÓNõ0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0004\u0005\u00000\u0081\u00941\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android1\"0 \u0006\t*\u0086H\u0086÷\r\u0001\t\u0001\u0016\u0013android@android.com0\u001e\u0017\r080415233656Z\u0017\r350901233656Z0\u0081\u00941\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android1\"0 \u0006\t*\u0086H\u0086÷\r\u0001\t\u0001\u0016\u0013android@android.com0\u0082\u0001 0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0082\u0001\r\u00000\u0082\u0001\b\u0002\u0082\u0001\u0001\u0000ÖÎ.\b\n¿â1MÑ\u008d³ÏÓ\u0018\\´=3ú\ftá½¶ÑÛ\u0089\u0013ö,\\9ßVøF\u0081=e¾ÀóÊBk\u0007Å¨íZ9\u0090ÁgçkÉ\u0099¹'\u0089K\u008f\u000b\"\u0000\u0019\u0094©)\u0015årÅm*0\u001b£oÅü\u0011:ÖË\u009et5¡m#«}úîáeäß\u001f\n\u008d½§\n\u0086\u009dQlN\u009d\u0005\u0011\u0096Ê|\fU\u007f\u0017[ÃuùHÅj®\u0086\b\u009b¤O\u008a¦¤Ý\u009a}¿,\n5\"\u0082\u00ad\u0006¸Ì\u0018^±Uyîøm\b\u000b\u001da\u0089Àù¯\u0098±ÂëÑ\u0007êE«Ûh£Ç\u0083\u008a^T\u0088ÇlSÔ\u000b\u0012\u001dç»Ó\u000eb\f\u0018\u008aáªaÛ¼\u0087Ý<d_/UóÔÃuì@p©?qQØ6pÁj\u0097\u001a¾^òÑ\u0018\u0090á¸®ó)\u008cðf¿\u009eláD¬\u009aèm\u001c\u001b\u000f\u0002\u0001\u0003£\u0081ü0\u0081ù0\u001d\u0006\u0003U\u001d\u000e\u0004\u0016\u0004\u0014\u008d\u001cÅ¾\u0095LC<a\u0086:\u0015°L¼\u0003òOà²0\u0081É\u0006\u0003U\u001d#\u0004\u0081Á0\u0081¾\u0080\u0014\u008d\u001cÅ¾\u0095LC<a\u0086:\u0015°L¼\u0003òOà²¡\u0081\u009a¤\u0081\u00970\u0081\u00941\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android1\"0 \u0006\t*\u0086H\u0086÷\r\u0001\t\u0001\u0016\u0013android@android.com\u0082\t\u0000Õ\u0085¸l}ÓNõ0\f\u0006\u0003U\u001d\u0013\u0004\u00050\u0003\u0001\u0001ÿ0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0004\u0005\u0000\u0003\u0082\u0001\u0001\u0000\u0019Ó\fñ\u0005ûx\u0092?L\r}Ò##=@\u0096zÏÎ\u0000\b\u001d[×ÆéÖí k\u000e\u0011 \u0095\u0006Al¢D\u0093\u0099\u0013ÒkJ àõ$ÊÒ»\\nL¡\u0001j\u0015\u0091n¡ì]ÉZ^:\u0001\u00006ô\u0092HÕ\u0010\u009b¿.\u001ea\u0081\u0086g:;åm¯\u000bw±Â)ãÂUãèL\u0090]#\u0087ïº\tËñ; +NZ\"É2cHJ#Òü)ú\u009f\u00199u\u00973¯Øª\u0016\u000fB\u0096ÂÐ\u0016>\u0081\u0082\u0085\u009cfCéÁ\u0096/ Á\u008333[À\u0090ÿ\u009ak\"ÞÑ\u00adDB)¥9©Nï\u00ad«ÐeÎÒK>QåÝ{fx{ï\u0012þ\u0097û¤\u0084Ä#ûOøÌIL\u0002ðõ\u0005\u0016\u0012ÿe)9>\u008eFêÅ»!òwÁQª_*¦'Ñè\u009d§\n¶\u00035iÞ;\u0098\u0097¿ÿ|©Ú>\u0012Cö\u000b")};
    static final byte[][] Ak = {ag("0\u0082\u0002R0\u0082\u0001»\u0002\u0004I4\u0098~0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0004\u0005\u00000p1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u000b0\t\u0006\u0003U\u0004\b\u0013\u0002CA1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle, Inc1\u00140\u0012\u0006\u0003U\u0004\u000b\u0013\u000bGoogle, Inc1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Unknown0\u001e\u0017\r081202020758Z\u0017\r360419020758Z0p1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u000b0\t\u0006\u0003U\u0004\b\u0013\u0002CA1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle, Inc1\u00140\u0012\u0006\u0003U\u0004\u000b\u0013\u000bGoogle, Inc1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Unknown0\u0081\u009f0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0081\u008d\u00000\u0081\u0089\u0002\u0081\u0081\u0000\u009fH\u0003\u0019\u0090ù±G&8N\u0004SÑ\u008f\u008c\u000b¿\u008dÇ{%\u0004¤± |LlDº¼\u0000\u00adÆa\u000f¦¶«-¨\u000e3òîñk&£ö¸[\u009aúÊ\u0090\u009fû¾³ôÉO~\u0081\"§\u0098àë§\\í=Ò)úseô\u0015\u0016AZ©Áa}Õ\u0083Î\u0019ºè »Ø\u0085ü\u0017©´½&@\u0080Q!ªÛ\u0093wÞ´\u0000\u00138\u0014\u0018\u0088.Å\"\u0082üX\r\u0002\u0003\u0001\u0000\u00010\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0004\u0005\u0000\u0003\u0081\u0081\u0000@\u0086f\u009eÖ1ÚC\u0084ÝÐaÒ&às¹\u008cÄ¹\u009døµä¾\u009e<¾\u0097P\u001e\u0083ß\u001co©YÀÎ`\\OÒ¬m\u001c\u0084ÎÞ Glº±\u009bèò :ÿw\u0017\u00ade-\u008fÌ\u0089\u0007\bÑ!m¨DWY&IàéÓÄ»Lõ\u008d¡\u009d±ÔüA¼¹XOdæ_A\r\u0005)ý[h\u0083\u008c\u0014\u001d\n\u009bÑÛ\u0011\u0091Ë*\r÷\u0090ê\f±-³¤"), ag("0\u0082\u0004¨0\u0082\u0003\u0090 \u0003\u0002\u0001\u0002\u0002\t\u0000\u0084~OòÖµÞ\u008e0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0005\u0005\u00000\u0081\u00941\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android1\"0 \u0006\t*\u0086H\u0086÷\r\u0001\t\u0001\u0016\u0013android@android.com0\u001e\u0017\r100120010135Z\u0017\r370607010135Z0\u0081\u00941\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android1\"0 \u0006\t*\u0086H\u0086÷\r\u0001\t\u0001\u0016\u0013android@android.com0\u0082\u0001 0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0082\u0001\r\u00000\u0082\u0001\b\u0002\u0082\u0001\u0001\u0000Ø(q|6Ñ\u0017\u000fÔM\n{\u000f\u0007\u0011&è[¿ß3°4`\u0000Z\u0094Ìûe¥Û ²Cß`±\u0091¿\u009d\u0006ß\u001d\u008a\\\n3âÑcõ\u0013ß\u001d\"SAê<3y\"è\\\u0002ì4ÎÙL¸\u0007#¦#ÿK¯û´åïæw;>¢¾¸¼²\u0002gÏç\u0085Q\u001f\u0083.ù\u0087«u\u0094þ\u001e)Ï¼M\b:\u001f\u0012R\u0000ws\u0096ò\u0016[i{\u0000£ Á:Ì0\u008a\u0093ò!cÁn\u009c=J²\u0014\u009f6LEÀC\u00142p9ñÚ\t`\u0093ñ³ü\u0018¶V\u0010\u0095Æ\"_Ç\u0010+\u0098|o\u0013¤]$ãàÅN\u0085\u009dgã[g\b'\u0013ÒÖðWÝ4WÑ\u009fÄþ\u008dÝì\u008c:O?\u0097#\u0005\u0019§\n(64¬5\u0081£J½¡}\u0084Z\n\t\u0085ûø\u0006\u000b\u0003j'x`\u0081cú\f7¹çò¡\u000ev¼w\u0002\u0001\u0003£\u0081ü0\u0081ù0\u001d\u0006\u0003U\u001d\u000e\u0004\u0016\u0004\u0014µÇù\u0012ox\r:ûÊess?õ\"k\u009b\u001770\u0081É\u0006\u0003U\u001d#\u0004\u0081Á0\u0081¾\u0080\u0014µÇù\u0012ox\r:ûÊess?õ\"k\u009b\u00177¡\u0081\u009a¤\u0081\u00970\u0081\u00941\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android1\"0 \u0006\t*\u0086H\u0086÷\r\u0001\t\u0001\u0016\u0013android@android.com\u0082\t\u0000\u0084~OòÖµÞ\u008e0\f\u0006\u0003U\u001d\u0013\u0004\u00050\u0003\u0001\u0001ÿ0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0005\u0005\u0000\u0003\u0082\u0001\u0001\u0000L>§e}&æ»×\u0011\f\u008f\u0019ß\u001f\u008d¡\t}3\u0086\u000fiÞ¿ÊÛF£~\u0087å³\u000f»4{\u001cuU¼»<\u0099T\u0014\u0080F\u0096_\u009cy*\u0002ÐÛå¦Ga³yG«kÿ°ºÆ¢Á Íøbøw©g\rýo\u0006.@nÎ\u0018\u0006\f`I\u008dü6\u009f'\u0011q\u0098åoË¡Ræ\u0005\u008dÎ\u0094ÎY\u001fÄô©\u0098+3ºØ\u0019mwoU·Ð\u001aÏ1Ý×\fì·\u0089xv\u0006e\u0010ùI¥RJ11³ËeAÏ\u008b5B\u000e¼ÄR%Y\u0096?Bfi\u0005rfbO³\u0098ÏÛR\u0017\u0088\u001d\u0011\u001cn\u0003F\u0016øQ!\u0018Ð¢¦\u009d\u0013×\u0092ðÍ\u0011ÛÕ\u008e#\u0083ZT¥JÂQçÒ,Dj?î\u0014\u0012\u0010éDGK@c\u0007»&\u0084+OkÓU\u0082\u001cs\u0096Qÿ¢`[\u0005â$\u0095×\u0015Øz\u0091ö")};
    static final byte[][] Al = {ag("0\u0082\u0002§0\u0082\u0002e \u0003\u0002\u0001\u0002\u0002\u0004P\u0005|B0\u000b\u0006\u0007*\u0086HÎ8\u0004\u0003\u0005\u0000071\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00160\u0014\u0006\u0003U\u0004\u0003\u0013\rAndroid Debug0\u001e\u0017\r120717145250Z\u0017\r220715145250Z071\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00160\u0014\u0006\u0003U\u0004\u0003\u0013\rAndroid Debug0\u0082\u0001·0\u0082\u0001,\u0006\u0007*\u0086HÎ8\u0004\u00010\u0082\u0001\u001f\u0002\u0081\u0081\u0000ý\u007fS\u0081\u001du\u0012)RßJ\u009c.ìäçö\u0011·R<ïD\u0000Ã\u001e?\u0080¶Q&iE]@\"QûY=\u008dXú¿Åõº0öË\u009bUl×\u0081;\u0080\u001d4oòf`·k\u0099P¥¤\u009f\u009fè\u0004{\u0010\"ÂO»©×þ·Æ\u001bø;WçÆ¨¦\u0015\u000f\u0004û\u0083öÓÅ\u001eÃ\u00025T\u0013Z\u0016\u00912öuó®+a×*ïò\"\u0003\u0019\u009dÑH\u0001Ç\u0002\u0015\u0000\u0097`P\u008f\u0015#\u000bÌ²\u0092¹\u0082¢ë\u0084\u000bðX\u001cõ\u0002\u0081\u0081\u0000÷á \u0085Ö\u009b=ÞË¼«\\6¸W¹y\u0094¯»ú:ê\u0082ùWL\u000b=\u0007\u0082gQYW\u008eºÔYOæq\u0007\u0010\u0081\u0080´I\u0016q#èL(\u0016\u0013·Ï\t2\u008cÈ¦á<\u0016z\u008bT|\u008d(à£®\u001e+³¦u\u0091n£\u007f\u000bú!5bñûbz\u0001$;Ì¤ñ¾¨Q\u0090\u0089¨\u0083ßáZå\u009f\u0006\u0092\u008bf^\u0080{U%d\u0001L;þÏI*\u0003\u0081\u0084\u0000\u0002\u0081\u0080jÑ\u001b×ÕfÒzô9À.Ah¬ýE´¾\u0085¼\u0099\u008c{\u009b\u008e\u001cwTi?\u008c\rB\u008a¤üá\u0010\u0084\u00818BO¦\u008cÑ0RNïöñ78c\u0082/¦7)\u008bþMF ¸feîðA\u00179\u0001\u0003[\u001c\u0080j£\u0018\u0018\r0:¨Ì\u009eY#àjo«úuh<E;²\u0007w|òýçÏ±\u009b\u001408\u0014ª\u001d÷´=[\"+W\u0006´\u008b\u00940\u000b\u0006\u0007*\u0086HÎ8\u0004\u0003\u0005\u0000\u0003/\u00000,\u0002\u0014\tÒÑ°G\u0002)µ¾Ò\u0090&aÑ\u0012òpÅæ\u001d\u0002\u0014gP\u0002\u0006§\u0080Pºx®Ç\u0017O\u0016\u0004\u007f\u0084ê¢÷")};
    static final byte[][] Am = {ag("0\u0082\u0004L0\u0082\u00034 \u0003\u0002\u0001\u0002\u0002\t\u0000¨Í\u0017É=¥Ù\u00900\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0005\u0005\u00000w1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00130\u0011\u0006\u0003U\u0004\u0003\u0013\nGoogle NFC0\u001e\u0017\r110324010653Z\u0017\r380809010653Z0w1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00130\u0011\u0006\u0003U\u0004\u0003\u0013\nGoogle NFC0\u0082\u0001 0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0082\u0001\r\u00000\u0082\u0001\b\u0002\u0082\u0001\u0001\u0000Ã\u000f\u0088\u00adÙ´\u0092\tj,XjZ\u009a\u00805kú\u0002iXøÿ\f]úõ\u009fI&\u008aØpÞè!¥>\u001f[\u0017\u000fÉbE£É\u0082§ËE'\u0005;ã^4ó\u0096ÒK\"\u0091ì\fR\u008dn&\u0092teàhuêb\u001f\u007fù\u008c@ã4[ I\u0007Ì\u0093Tt:ÍªÎeV_HºtÍA!ÍÈvß5\"ºÛ\t\\ Ù4Åj>\\9>åðà/\u008fàb\u001f\u0091\u008d\u001f5¨$\u0089%,o¦¶3\u0092§hk>Ha-\u0006©ÏoI¿ñ\u001d]\u0096(\u009c\u009dþ\u0014¬WbC\u0096\u0097Ý)êý¹\u0081\rã&5\u0013©\u0005¬\u008e\u008e¯ \u0090~Fu\nZ·¿\u009aw&/G°?Z<nm{Q4?iÇ÷%÷\u000bÌ\u001bJÕ\u0092%\u000bpZ\u0086æè>â®7þW\u0001¼½²oîýÿö\u000fj[ßµ¶G\u0093\u0002\u0001\u0003£\u0081Ü0\u0081Ù0\u001d\u0006\u0003U\u001d\u000e\u0004\u0016\u0004\u0014\u001cÎÎ\u000eêMÁ\u0012\u001fÇQ_\r\n\frà\u008cÉm0\u0081©\u0006\u0003U\u001d#\u0004\u0081¡0\u0081\u009e\u0080\u0014\u001cÎÎ\u000eêMÁ\u0012\u001fÇQ_\r\n\frà\u008cÉm¡{¤y0w1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00130\u0011\u0006\u0003U\u0004\u0003\u0013\nGoogle NFC\u0082\t\u0000¨Í\u0017É=¥Ù\u00900\f\u0006\u0003U\u001d\u0013\u0004\u00050\u0003\u0001\u0001ÿ0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0005\u0005\u0000\u0003\u0082\u0001\u0001\u0000¤pÇ(áÓ\u001b\u0006Ù¯jçhµe\u0004lW\u0080k\u0098CrI1×]L¡\f2\u0015 Ó<Ïí*¦Tb#L\u009eù¶ù\u0010Ìgk\u0099Ë\u007f\u0098\u0095ÖÀgcWO»x3\u0012uÜ\\ó\u008fº©\u0018×\u0093\u008c\u0005\u001fû¢\u00adèó\u0003ÍèÙæ\u008a\u0004\u008d\u001fÛ\u009e|\u009f*I²\"Æ\u008fÿB+ñUi¸^îí°J£\bsÛæK\u009c\u009etøòÂöÄ\u0001$ª¨Ñx\r\u0018Q+T\nÝ(³éX\u0019q¤\u0017\rØhÏ_1äG\u0012²Â;µ\u00107×ï\u009f\u0087¦å½³^,ëk°\"cl\u0017¥j\u0096¼zP%\u008c\u000bÒí{1UZ\u0018E.\u00172\u001a\rR\u0083\u008c\u0082ö?t-tÿyXj\\»\u007f¯q\u0098¨KÏtC\u0010éé'Y\u007f\u0000¢=Ð\u0006`\u0080\f\"8Ù\u000b/³rßÛºu½\u0085."), ag("0\u0082\u0004L0\u0082\u00034 \u0003\u0002\u0001\u0002\u0002\t\u0000Þv\u0095\u0004\u001dvPÀ0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0005\u0005\u00000w1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00130\u0011\u0006\u0003U\u0004\u0003\u0013\nGoogle NFC0\u001e\u0017\r110324010324Z\u0017\r380809010324Z0w1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00130\u0011\u0006\u0003U\u0004\u0003\u0013\nGoogle NFC0\u0082\u0001 0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0082\u0001\r\u00000\u0082\u0001\b\u0002\u0082\u0001\u0001\u0000æÿ=ïé*¡\rqë\u000f¦@\u008bÀ6·âCîíh¦¤v=Ç¥*1u|ÚÆ\u001få\u0010»sÇ\u0016ä\u0000\u0001\u0004&[4\u007fÎÎôÄ+ñá7\u009dÐ¨vð(\"\u007f»Áù½Õ×\u0013²ö©5£yÒË©Éo\u0092ÒÐx|\u0011ñë\u0019T\u0080\b¦ r³K\u0091\u0083lú\ná'g\u0080é\u0000u0\u0016i\u0086¡\u001c\u009cïFÎ÷Ç\u0004\u0080mÞ\u00941û`(M\u0012\n°çÞ\u001dc?\u0007h}F\u008cQ\u0013\u009aÿýÆ¼\u009a |©\u0004¸¾\u001d ª{N\u0097uoC`d\u0088¾\\®<hè»yBÍõ\u0016\u0007É0¢üÚe[uÐu\u009cº\u0089\u00ad\u0006ç9½\u000b¢\u009b\u001f@B\u0096ÂÀ¨Z\u0084\u007fZ°ÐgÆÃì\u009cI! B¬c§å;Tle´`\u0080´ãæ\u0080â>\u001fwÏçöÞtK\u001ae\u0002\u0001\u0003£\u0081Ü0\u0081Ù0\u001d\u0006\u0003U\u001d\u000e\u0004\u0016\u0004\u0014¢è\u0090d°]\b\u0086\\4Û\u0093\n\u009d\u0084\u0000P\u0011zì0\u0081©\u0006\u0003U\u001d#\u0004\u0081¡0\u0081\u009e\u0080\u0014¢è\u0090d°]\b\u0086\\4Û\u0093\n\u009d\u0084\u0000P\u0011zì¡{¤y0w1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00130\u0011\u0006\u0003U\u0004\u0003\u0013\nGoogle NFC\u0082\t\u0000Þv\u0095\u0004\u001dvPÀ0\f\u0006\u0003U\u001d\u0013\u0004\u00050\u0003\u0001\u0001ÿ0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0005\u0005\u0000\u0003\u0082\u0001\u0001\u00007q\u0087\fè|<Rê\u0084\u0089\u00920ÆébÙKM_\u0012\u0093Â]\u0088&\u0015Aý\u0090µU]\u0012\u0085Îó¸1,?]ö\u0091¨ªàL¹\u0081³\u0005ä'ý\u001d-\u009e\u0019\u0087áÒ\u0090xñ<\u0084R\u0099\u000f\u0018!\u0098\u0002cØÔ½6Q\u0093HØØº&Ø¹\u009f¿\tõý>»\u000e£ÂðÉ7o\u001e\u001fÊvó¦¤\u0005B\u009d\b\u001bu*z\u0090·Vé«DÚA«Èáèø\u008aÂu\u008d§CûsæPq\u009aW\u0084\fËkzÝ!¹\u009fÆ\u0081äVá\u0087,\"=\\\u0007JßUö«Ú&\u008c-\u008bdê\n\u0088EîÍ\u0096\u008f\u0092´\u0093\u0012~uÇSÃÿ0ËÆxµ\u001c\u009fR\u0096\u0014rñ}¢\n\rÆ'J¢F44Á©¶\u0014ßi}\u008fõÊ\u0081\u0001ç¢\\}³û\u0005]eV\u009c\u0004°\u001d8\u009c«ºW³¡p>ÂçJ\u0088Ó4")};
    static final byte[][] An = {ag("0\u0082\u0005a0\u0082\u0003K\u0002\u0006\u0001D\u009e\u0091\u0096Ó0\u000b\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u00050v1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00120\u0010\u0006\u0003U\u0004\u0003\u0013\tClockWork0\u001e\u0017\r140307220225Z\u0017\r380119031407Z0v1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00120\u0010\u0006\u0003U\u0004\u0003\u0013\tClockWork0\u0082\u0002\"0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0082\u0002\u000f\u00000\u0082\u0002\n\u0002\u0082\u0002\u0001\u0000º<\u007f9\u000bþY\u008ab¼ü\u008b<\u0094Æ'Z\u0099\u0015íÜÝ7:Uj\u0099\u000bâýC÷\u009f\u0018³\u0001Ò@'ãr\u007f\tÎýâ\u009c|&°\u008a Þ6}\u001aßãN§\u008f®7ó\u0090õà&rzN\b(;ïvøöC¼\u0015'6 H?É·\u0091«R<ó½\u0086{f-*'L\u0000Ø\u0090ç\u009d\u0011è°&_í©uÜÈåB\u0099\u0089\u008e\u0090\u0013jbq\u008c.\u000b/9yQÛ$±W¡¿çÅkÎJ8\u000b%ú¹&c>¨\u0094\u00048à¶\u0094\u000b¹\u009e\u0089~.ú\u0005<2)\u009bÙao¤½\u0096\u0082!{7C\u001fÍØ\u0082í!§òðF\u007fà\u0095\u001c¼Z\u0098bãJ\u0015káZ\u0017ÿ\u0002\u0017\u0098dDÖ\u0013±\u001e×_\u0083\u0080\u0018î´ý\u0094ä\u008fZã\u001cä¯¤68¶\u0097,\u0085\\ÒÛ\n\u0001Ä2a(äÅ\u0019z¾¬ÌmÂè\u00ad¤B_\u000f\u0090Õ¥¥X$a¿x\u0011á.Î\u000eê\u0006\u0003?\u0096T9íàqÿÄl òß¾##:\u007fdÁÎ\t\u00ad¡ËÎkö¼¢.\u009b\u0098\u009cJÀÉj\u009dluOì\u0018qØ{\u0010\u0098Á Þ`¼}wÞ0ÕN¸GÎk\u0012|\u0019\u001e§\u0093o\nFÁFó6¹4êºZ_\u001c\u0003d·R\u0096UD2Pýcªå{ë«à&?\t\bM\u0019D\u0006\f:Ù»º\u007fyôÞ<+-7º³\rK¹\u0011ÜQià¯\u0095RôÓ\u008e=³òË\u0080\u001cR\u0002Rpa¿\u0001°BÐ~\u0089ä\u008f\u0011©ª 'ðD\u0095\u009eÚ(ÅÝØSW§\u001e9»\u0082Q³Wëor\u0018üÌ\u0017\u0018\u0081¦0gF1àU\u00949\u001azg\u009aòZ b\u0001Ö\"¸Ð\tÝ\u0011Õ\u0006¢\u0003\u000f$'®gØ\u001b47yy\u0002\u0003\u0001\u0000\u00010\u000b\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0005\u0003\u0082\u0002\u0001\u0000¤Ä\u0096\u00964aÈ\u00955¥±\nÍ\u0001$7j\u0089Ú'C\u009d¬0\u0003Hg\u000b +\u00adã?/º*\u0007d\u0003µ\u000bèqÊ*²\u009b¾½»Ä\u0006Û\t9AÉ\u008c\u0017j\u000eFÿ\u0090ÿ\u0000\u0016\u0016\u0004D\u0080nÜ\u0082á0þ\u0010\u0086\u001eã\u0005\u009d·~=\u009d©¢\u00ad4©Ò´Ú\u001b&ýZ[p\u001cÕlþédzä\u0014;\u0097¦|\u0002\u0080e±\u007f\u009e\u0014ò2¥ï\u0017ád¡I\u0017\u0092\u0096\u0094\u001c0½Z6«øóBÈã¯¼oICs\u0007}j\u009c\u0011×9\"\rZ×µ\u0019/\u009b\u001cþ\u0096\u008fJr±¸Tuàé\u0088¾hr\u0088fe±+ôîÃ\"VTõáò+\u008bëU\u008e¾fw\u008bÖ_\t\u0091-ù^\u0080\u009dþï\u000fÇêÊ]\u000e¾\u001dA\u0004\u001fç Ë2\u009b0~9.\u0013\u0097ñ 9Ti0\u0084\u008b\u007f\u0002\u0017@\u0089-öÇ rçß8ºÃ×\"5oæT\u007fj|W\u008aßgÉ=+5\u0088\u0093T5ðù¡\u0013Î-ìÍm¡\u009dÃKA\u0082ì®Ö ëR\u00850%Åà\u0004ì´Q¼EáHZÌ6\u007f¶I\u0092¯YLU\u001b\u000bÉ8ËÖ\u001aÕgY\u0090 ÷:eá©È¤\u0088Û¬\u0083\u001eë\u0091\u008f\f\u0092)\t^ÞA\u0005{<®êN\u0016Å¹EK\u0092âY\u008a\u0011´\u0094¢\u001f?z¿\u0083Àgô\u0018.\u0098A\u009b¤ä\u0093\u008a4\u0080ð\u0086/í¯WrJU3W\u008fÚ_³ÍüùT\u0080\u007fÿØÉQwçu\u0004¦B¾\\Û á\u0000eü|h\u0012\u009cí'³¨\u0004×¤ÍÙ\fÓìË\u0005¨È\u008a`\u009aÐV N´\u008a\u001e\u0005\u0095ý9\\\u001f§{³\u001d¥$4^\n½N\u0001µ\u0006\u0082OêºBÓ-Ô\u0092g>ÏÀ\u0017\u009d\u0086\u001d&éÍ\\FïÐ"), ag("0\u0082\u0003¿0\u0082\u0002§ \u0003\u0002\u0001\u0002\u0002\t\u0000Ú\u0098ÃÙ\u0015sÓï0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0005\u0005\u00000v1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\f\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\f\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\f\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\f\u0007Android1\u00120\u0010\u0006\u0003U\u0004\u0003\f\tClockWork0\u001e\u0017\r140307220118Z\u0017\r410723220118Z0v1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\f\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\f\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\f\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\f\u0007Android1\u00120\u0010\u0006\u0003U\u0004\u0003\f\tClockWork0\u0082\u0001\"0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0082\u0001\u000f\u00000\u0082\u0001\n\u0002\u0082\u0001\u0001\u0000Ü\u001doK(í80\u0014²\u009c\u0082öÚÿÓ\u001dÞ{\u008c\u001ec\b@e\u000bX±e£j®¶,qS\u0095.\u0004E\t¯\u0082\u001f\u0094º\u009fO\u0018dÃ§µÖSÌ\u0000\u0015\u009d\u0000\u0010áåfú7ªÿ\u00186]®{J\u0085Ý±ó\u0083ÌGp¢>\u0095b\u0091þµrÁi1Z¯Nôê¥®\u0086\u001fÍÖçåêÔ1\u0013tFF\f|(û2,\u0092\u0095\\\\z¨\u0095wÃp?\u0097à\u0098·~¶ n¬krê \u00ad!\n°*\u001fÜüvbttA©?<ê\u008a\u0016ô\u008c\u0097\"Áã2A2~ÂÉ÷01.\u008d\u001bïî)\u000bE\u001a4\u0089,¬ï[\u0014rÖÙ~ùT(Ì\u008aÕï\u0004¸Äñõ\rÒBÕ]rXf\u0085P[^K\u001b\u001eY\u00ad\u008c\u001d\u0085/ \u0082H\u0015g;ÆæC)ìÄêÔÛ\u0084d©k1\u0083\u009f\u009fÛÉ\u0007\u0002\u0003\u0001\u0000\u0001£P0N0\u001d\u0006\u0003U\u001d\u000e\u0004\u0016\u0004\u0014\u0084\u0085G\u0010\u0084¤<³êø?«!b \u0095\u0000Î,z0\u001f\u0006\u0003U\u001d#\u0004\u00180\u0016\u0080\u0014\u0084\u0085G\u0010\u0084¤<³êø?«!b \u0095\u0000Î,z0\f\u0006\u0003U\u001d\u0013\u0004\u00050\u0003\u0001\u0001ÿ0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0005\u0005\u0000\u0003\u0082\u0001\u0001\u0000\u00079b\u000b¢}*\u000fT\u0088C\u00ad\u001b`\u008e\u001c)Ù\u0001(\u0081êü?Ö(__bj\u0097>ðWæ\u0097î²¬\\¢æ\u0005Ê=3õ\u0090\u0099k\u008b\u00002ÄGæP\u000f%½\u0017Êù\u0095\u00039\u0083@ÈîlÜµ;íä±òHçÐ \u0099\u009e\u0081çÊê¥2ÏÚ\u0099þJ¥í@@ND÷[ïÒ\u007fÊÛ5¸²\u001b\u0094xF^\u0017\"òzû+\u000bn\u0015\u008eDÄ«\fOe{\u0019×}\u008fSÉÏ¹î-OE¶Tà\u0012¼\u008dé\u0081äÂâÃÓ\u009eQ\u0093\u0003Ø®M,ÁÈb\u008dxW®u?\u001d{\u0002£§\u0005xÆ\u0005ã\u0005\u001cl\u001d©I\u001aÎ\u0013»\u0088Ð}\u0081}Ô\u0094&Q\u0097\u0084®\u0096\u0095¤G5\r\u0089ë@^\u0090ò«óf®/ÊXÒö¿\u001d¿K\u001cH\u009eà \u0001TßÏ\u0002%\u0012õ¡Ç\"\u009es\u001dãðGÖø")};
    static final byte[][] Ao = {ag("0\u0082\u0003m0\u0082\u0002W\u0002\u0006\u0001=døÖ³0\u000b\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u00050|1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00150\u0013\u0006\u0003U\u0004\n\u0013\fGoogle, Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007GoogleX1\u00170\u0015\u0006\u0003U\u0004\u0003\u0013\u000eGlass Platform0\u001e\u0017\r130313181742Z\u0017\r380119031407Z0|1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00150\u0013\u0006\u0003U\u0004\n\u0013\fGoogle, Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007GoogleX1\u00170\u0015\u0006\u0003U\u0004\u0003\u0013\u000eGlass Platform0\u0082\u0001\"0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0082\u0001\u000f\u00000\u0082\u0001\n\u0002\u0082\u0001\u0001\u0000¡3¦Òi¯Ø6ã®Ü-¤\u00ad\u008e9¿øâ\u0019æH3´\u0018µ\u0084=5lì\u0014¸\u009eG\u0097F´\u0098\u000b\u008f\u0083ß\u001eB¡þôÑ\u0091õ×B\fÀ\u0085Ø\u009b+çÚe\tÄ¯?ÛÎ/PTïìçA©é\u0091RZ\u0013#\u0003ÿÎ\u0089\u0015D¦\u008c9»ÊØt¯ ¼¼õ\u0016ö\u008fYÚ7Á\u00ad\u009e/è£ÄZæ\u001e\u0086Ê\u008b¨W¢\u0005C9í¿o\u0096ð@nÂ\u0081A½\buq\u0089c).\u008as©)\u0004ù=ìk½@c¼¥>y¸\u0096©Cp|¹üA;X\u000f0ðGE´õ7\u009f\u00ad§æ\u0087\u0081\u008f\u001aÿüw\u000bNÓ<>\u0081<ttb\u001ez\u00adÔw\u00ad\u0005ÜuL\u0084\r3\rÁXõæá\u0087õ¢`<Q&¬Bô\u0018Û xF)à\u0085\r§ë\u0006\u00925Ù\u0098ºGµE\fZ¢dØ\u008a/\u0095\u0002\u0003\u0001\u0000\u00010\u000b\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0005\u0003\u0082\u0001\u0001\u0000\u0086è\u0015JôØôu°ã[ðÒR\u0006c\bLÏÑ\u0086r%éKþÁJ\u001f¾7ErpÀ\u0085ÿVöVÁP\u007f\u0089éË¹Ùl\u0087\u001c;\u000b¡æ<êõÔæ«\u0099C*Ü±\u0097\u0013Wòc´\u0082è\u0096\u0088Í®Ð¼\u008bp}ó\u0016í.©¶VxÔMþíä/°#¯cc±NSÄ\u0081B\u008d²+\u0086ø\u0093«\u008dÀ¿i\u0087\u0089\u0005÷ó[(\u0012Kæ\u0098c\u001dF\f9_5éu«FÞ\f?ß0Ï\u000f\u0007ÙE\u00ad}Ç¨d;ICà.&[\u0010\u008btÕùKùX\u0085ìÚúªoÅ¾¨Ìfý!»\u0090²nÏ\u009aeéø.µ{gìÁéx·Ú'\u0017\u0088æÖ\u001dç\u0080¦l!¿ÂY¶ÞÉ½zµòÓÛÅ\u0013\u0005Ú\u009fÚ¨ïâ)<¢\u0017:ì#\u0084\b`pNw\u0093\u0085ÉÀ¦b0§"), ag("0\u0082\u0003É0\u0082\u0002± \u0003\u0002\u0001\u0002\u0002\t\u0000Ãi \u00adßt\u009dÇ0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0005\u0005\u00000|1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\f\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\f\rMountain View1\u00150\u0013\u0006\u0003U\u0004\n\f\fGoogle, Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\f\u0007GoogleX1\u00170\u0015\u0006\u0003U\u0004\u0003\f\u000eGlass Platform0\u001e\u0017\r130226205628Z\u0017\r400714205628Z0|1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\f\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\f\rMountain View1\u00150\u0013\u0006\u0003U\u0004\n\f\fGoogle, Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\f\u0007GoogleX1\u00170\u0015\u0006\u0003U\u0004\u0003\f\u000eGlass Platform0\u0082\u0001 0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0082\u0001\r\u00000\u0082\u0001\b\u0002\u0082\u0001\u0001\u0000¯\u0092ÊGêä\u0092Ð;\u001e\bà\t\u0097w¢ø\u0006Æ¢\u0017\u001dí§[pâ:¢ñ¹¿h\u008c¥/?v,¾Æ:\bÑBZÃ\u001bé2m\u0001\u001e|\u0006Î¡ÈJëp?Ð9\u0097*1\u0006²}\u0098\u0004^|áT\u0004K\"Ê¥\u001d[õù±$â\"ºsA-ÔY0h\u0082,Fg1°Y¯¦¤èÝ?^µ\u007f@øº\u0011](G@)×\u001f1å»°ê^0 ä\u0086uµ¤ý4\u0017\nÔ.ØPë9T,+éµm5\u008f\u0092þ¶²¨\u00984iúKæ+È¤|è\u0000\u0003l®ðõ3sÉX\u0015¸ÊÙs[¿·\u0000e\u0084h¾mæw\u0010-E¿¶9z\u0094\u009f:\u001f\u0001%¸\u0015\u0005\u008f\u0005·«Ý\u0088\u0093Â`\u001f~ñX\u0096Õ(á\u00ad;pmhE\u000f%:\u0013íyÀ\u0002l¨\u0084\u0013\u0011Õ\u008fË\u0093\u0084ß·\u0002\u0001\u0003£P0N0\u001d\u0006\u0003U\u001d\u000e\u0004\u0016\u0004\u0014óSB\u001f\u000fÍ{#j_\u0084µ\u001ffWc\u0019Ð{\u008f0\u001f\u0006\u0003U\u001d#\u0004\u00180\u0016\u0080\u0014óSB\u001f\u000fÍ{#j_\u0084µ\u001ffWc\u0019Ð{\u008f0\f\u0006\u0003U\u001d\u0013\u0004\u00050\u0003\u0001\u0001ÿ0\r\u0006\t*\u0086H\u0086÷\r\u0001\u0001\u0005\u0005\u0000\u0003\u0082\u0001\u0001\u0000©\u0090X±/\u0007ï\u0014}CÅ=ÂÉèá\u0001¥b\bfF\u0091¼\u0004·\u001d÷yxÜ!\u0089/ü|\u0003ú¯Y%;è\u0095õ2Â_]uºæïº´XRp(hk\u0001B'¥A·C§³/\u008a4Ñ[Yô \u0015X%ý¼\u009eì>ÒtÎìÇ\u0001Ë«[Ug\"3wn÷ä´â\u0001R\u000eFÕEI\\\u0014Ày\u00ad}\u001fý\u009cã¢±Ê\u001bQ[ +7[÷/\u008a6\u000fó\u0097\u0095-\u0082\u00ad`S:Ú×Å~#dRE¥ºÊÊBTý!³d\u009b!_\u0004vòé\u0086I\u0019÷\u0088W \u008b\u0013Xv\nF!î\u0003UÙ»@h\u000fÚó±\u008e¨\u008d\u0090f\u0004+Ë@\u00853àw,Öãª\u001e\u0083 Æñ\u0002\u0004úå[£áÙßëÔ@Uü\u0007¨æ\u00ad\u008d;Ô\u00117ö/_f£÷\u0018$õO({Úø?")};
    private static final byte[][] Ap = a(Aj, Ak, Al, Am, An, Ao);
    private static final byte[][] Aq = {Aj[0], Ak[0], Am[0], An[0], Ao[0]};
    public static boolean Ar = false;
    public static boolean As = false;
    private static int At = -1;
    private static final Object Au = new Object();
    public static final String GMS_ERROR_DIALOG = "GooglePlayServicesErrorDialog";
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 4452000;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    private GooglePlayServicesUtil() {
    }

    public static boolean a(PackageManager packageManager, PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (c(packageManager)) {
            return a(packageInfo, Ap) != null;
        }
        boolean z = a(packageInfo, Aq) != null;
        if (z || a(packageInfo, Ap) == null) {
            return z;
        }
        Log.w("GooglePlayServicesUtil", "Test-keys aren't accepted on this build.");
        return z;
    }

    public static boolean a(Resources resources) {
        if (resources == null) {
            return false;
        }
        return (gr.fu() && ((resources.getConfiguration().screenLayout & 15) > 3)) || b(resources);
    }

    private static byte[] a(PackageInfo packageInfo, byte[]... bArr) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
            if (packageInfo.signatures.length != 1) {
                Log.w("GooglePlayServicesUtil", "Package has more than one signature.");
                return null;
            }
            try {
                try {
                    ((X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(packageInfo.signatures[0].toByteArray()))).checkValidity();
                    byte[] byteArray = packageInfo.signatures[0].toByteArray();
                    for (byte[] bArr2 : bArr) {
                        if (Arrays.equals(bArr2, byteArray)) {
                            return bArr2;
                        }
                    }
                    if (Log.isLoggable("GooglePlayServicesUtil", 2)) {
                        Log.v("GooglePlayServicesUtil", "Signature not valid.  Found: \n" + Base64.encodeToString(byteArray, 0));
                    }
                    return null;
                } catch (CertificateExpiredException e) {
                    Log.w("GooglePlayServicesUtil", "Certificate has expired.");
                    return null;
                } catch (CertificateNotYetValidException e2) {
                    Log.w("GooglePlayServicesUtil", "Certificate is not yet valid.");
                    return null;
                }
            } catch (CertificateException e3) {
                Log.w("GooglePlayServicesUtil", "Could not generate certificate.");
                return null;
            }
        } catch (CertificateException e4) {
            Log.w("GooglePlayServicesUtil", "Could not get certificate instance.");
            return null;
        }
    }

    private static byte[][] a(byte[][]... bArr) {
        int length = 0;
        for (byte[][] bArr2 : bArr) {
            length += bArr2.length;
        }
        byte[][] bArr3 = new byte[length][];
        int length2 = bArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length2) {
            byte[][] bArr4 = bArr[i];
            int i3 = i2;
            int i4 = 0;
            while (i4 < bArr4.length) {
                bArr3[i3] = bArr4[i4];
                i4++;
                i3++;
            }
            i++;
            i2 = i3;
        }
        return bArr3;
    }

    private static byte[] ag(String str) {
        try {
            return str.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static Intent b(Context context, int i) {
        switch (i) {
            case 1:
            case 2:
                return fi.ao(GOOGLE_PLAY_SERVICES_PACKAGE);
            case 3:
                return fi.am(GOOGLE_PLAY_SERVICES_PACKAGE);
            case 12:
                return fi.eS();
            default:
                return null;
        }
    }

    public static boolean b(PackageManager packageManager) {
        synchronized (Au) {
            if (At == -1) {
                try {
                    if (a(packageManager.getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 64), Ap[1]) != null) {
                        At = 1;
                    } else {
                        At = 0;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    At = 0;
                }
            }
        }
        return At != 0;
    }

    public static boolean b(PackageManager packageManager, String str) {
        try {
            return a(packageManager, packageManager.getPackageInfo(str, 64));
        } catch (PackageManager.NameNotFoundException e) {
            if (Log.isLoggable("GooglePlayServicesUtil", 3)) {
                Log.d("GooglePlayServicesUtil", "Package manager can't find package " + str + ", defaulting to false");
            }
            return false;
        }
    }

    private static boolean b(Resources resources) {
        Configuration configuration = resources.getConfiguration();
        return gr.fw() && (configuration.screenLayout & 15) <= 3 && configuration.smallestScreenWidthDp >= 600;
    }

    public static String c(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case 1:
                return a(context.getResources()) ? resources.getString(R.string.common_google_play_services_install_text_tablet) : resources.getString(R.string.common_google_play_services_install_text_phone);
            case 2:
                return resources.getString(R.string.common_google_play_services_update_text);
            case 3:
                return resources.getString(R.string.common_google_play_services_enable_text);
            case 4:
            case 6:
            case 8:
            case 10:
            case 11:
            default:
                return resources.getString(R.string.common_google_play_services_unknown_issue);
            case 5:
                return resources.getString(R.string.common_google_play_services_invalid_account_text);
            case 7:
                return resources.getString(R.string.common_google_play_services_network_error_text);
            case 9:
                return resources.getString(R.string.common_google_play_services_unsupported_text);
            case 12:
                return resources.getString(R.string.common_google_play_services_unsupported_date_text);
        }
    }

    public static boolean c(PackageManager packageManager) {
        return b(packageManager) || !dX();
    }

    public static String d(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case 1:
                return resources.getString(R.string.common_google_play_services_install_button);
            case 2:
                return resources.getString(R.string.common_google_play_services_update_button);
            case 3:
                return resources.getString(R.string.common_google_play_services_enable_button);
            default:
                return resources.getString(android.R.string.ok);
        }
    }

    public static boolean dX() {
        return Ar ? As : "user".equals(Build.TYPE);
    }

    public static String e(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case 1:
                return resources.getString(R.string.common_google_play_services_notification_needs_installation_title);
            case 2:
                return resources.getString(R.string.common_google_play_services_notification_needs_update_title);
            case 3:
                return resources.getString(R.string.common_google_play_services_needs_enabling_title);
            case 4:
            case 6:
            case 8:
            case 10:
            case 11:
            default:
                return resources.getString(R.string.common_google_play_services_unknown_issue);
            case 5:
                return resources.getString(R.string.common_google_play_services_invalid_account_text);
            case 7:
                return resources.getString(R.string.common_google_play_services_network_error_text);
            case 9:
                return resources.getString(R.string.common_google_play_services_unsupported_text);
            case 12:
                return resources.getString(R.string.common_google_play_services_unsupported_date_text);
        }
    }

    public static Dialog getErrorDialog(int errorCode, Activity activity, int requestCode) {
        return getErrorDialog(errorCode, activity, requestCode, null);
    }

    public static Dialog getErrorDialog(int errorCode, Activity activity, int requestCode, DialogInterface.OnCancelListener cancelListener) {
        AlertDialog.Builder message = new AlertDialog.Builder(activity).setMessage(c(activity, errorCode));
        if (cancelListener != null) {
            message.setOnCancelListener(cancelListener);
        }
        fd fdVar = new fd(activity, b(activity, errorCode), requestCode);
        String strD = d(activity, errorCode);
        if (strD != null) {
            message.setPositiveButton(strD, fdVar);
        }
        switch (errorCode) {
            case 0:
                return null;
            case 1:
                return message.setTitle(R.string.common_google_play_services_install_title).create();
            case 2:
                return message.setTitle(R.string.common_google_play_services_update_title).create();
            case 3:
                return message.setTitle(R.string.common_google_play_services_enable_title).create();
            case 4:
            case 6:
                return message.create();
            case 5:
                Log.e("GooglePlayServicesUtil", "An invalid account was specified when connecting. Please provide a valid account.");
                return message.setTitle(R.string.common_google_play_services_invalid_account_title).create();
            case 7:
                Log.e("GooglePlayServicesUtil", "Network error occurred. Please retry request later.");
                return message.setTitle(R.string.common_google_play_services_network_error_title).create();
            case 8:
                Log.e("GooglePlayServicesUtil", "Internal error occurred. Please see logs for detailed information");
                return message.create();
            case 9:
                Log.e("GooglePlayServicesUtil", "Google Play services is invalid. Cannot recover.");
                return message.setTitle(R.string.common_google_play_services_unsupported_title).create();
            case 10:
                Log.e("GooglePlayServicesUtil", "Developer error occurred. Please see logs for detailed information");
                return message.create();
            case 11:
                Log.e("GooglePlayServicesUtil", "The application is not licensed to the user.");
                return message.create();
            case 12:
                Log.e("GooglePlayServicesUtil", "The date of the device is not valid.");
                return message.setTitle(R.string.common_google_play_services_unsupported_title).create();
            default:
                Log.e("GooglePlayServicesUtil", "Unexpected error code " + errorCode);
                return message.create();
        }
    }

    public static PendingIntent getErrorPendingIntent(int errorCode, Context context, int requestCode) {
        Intent intentB = b(context, errorCode);
        if (intentB == null) {
            return null;
        }
        return PendingIntent.getActivity(context, requestCode, intentB, DriveFile.MODE_READ_ONLY);
    }

    public static String getErrorString(int errorCode) {
        switch (errorCode) {
            case 0:
                return "SUCCESS";
            case 1:
                return "SERVICE_MISSING";
            case 2:
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            case 3:
                return "SERVICE_DISABLED";
            case 4:
                return "SIGN_IN_REQUIRED";
            case 5:
                return "INVALID_ACCOUNT";
            case 6:
                return "RESOLUTION_REQUIRED";
            case 7:
                return "NETWORK_ERROR";
            case 8:
                return "INTERNAL_ERROR";
            case 9:
                return "SERVICE_INVALID";
            case 10:
                return "DEVELOPER_ERROR";
            case 11:
                return "LICENSE_CHECK_FAILED";
            case 12:
                return "DATE_INVALID";
            default:
                return "UNKNOWN_ERROR_CODE";
        }
    }

    public static String getOpenSourceSoftwareLicenseInfo(Context context) {
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(new Uri.Builder().scheme("android.resource").authority(GOOGLE_PLAY_SERVICES_PACKAGE).appendPath("raw").appendPath("oss_notice").build());
            try {
                String next = new Scanner(inputStreamOpenInputStream).useDelimiter("\\A").next();
            } catch (NoSuchElementException e) {
                return null;
            } finally {
                if (inputStreamOpenInputStream != null) {
                    inputStreamOpenInputStream.close();
                }
            }
        } catch (Exception e2) {
            return null;
        }
    }

    public static Context getRemoteContext(Context context) {
        try {
            return context.createPackageContext(GOOGLE_PLAY_SERVICES_PACKAGE, 3);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static Resources getRemoteResource(Context context) {
        try {
            return context.getPackageManager().getResourcesForApplication(GOOGLE_PLAY_SERVICES_PACKAGE);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static int isGooglePlayServicesAvailable(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            context.getResources().getString(R.string.common_google_play_services_unknown_issue);
        } catch (Throwable th) {
            Log.e("GooglePlayServicesUtil", "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
        }
        if (System.currentTimeMillis() < 1227312000288L) {
            return 12;
        }
        t(context);
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 64);
            if (!gk.y(context)) {
                try {
                    byte[] bArrA = a(packageManager.getPackageInfo(GOOGLE_PLAY_STORE_PACKAGE, 64), Aj);
                    if (bArrA == null) {
                        Log.w("GooglePlayServicesUtil", "Google Play Store signature invalid.");
                        return 9;
                    }
                    if (a(packageInfo, bArrA) == null) {
                        Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                        return 9;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    Log.w("GooglePlayServicesUtil", "Google Play Store is missing.");
                    return 9;
                }
            } else if (a(packageInfo, Aj) == null) {
                Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                return 9;
            }
            if (packageInfo.versionCode < 4452000) {
                Log.w("GooglePlayServicesUtil", "Google Play services out of date.  Requires 4452000 but found " + packageInfo.versionCode);
                return 2;
            }
            try {
                return !packageManager.getApplicationInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 0).enabled ? 3 : 0;
            } catch (PackageManager.NameNotFoundException e2) {
                Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.");
                e2.printStackTrace();
                return 1;
            }
        } catch (PackageManager.NameNotFoundException e3) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 1;
        }
    }

    public static boolean isUserRecoverableError(int errorCode) {
        switch (errorCode) {
            case 1:
            case 2:
            case 3:
            case 9:
            case 12:
                return true;
            default:
                return false;
        }
    }

    public static void s(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        int iIsGooglePlayServicesAvailable = isGooglePlayServicesAvailable(context);
        if (iIsGooglePlayServicesAvailable != 0) {
            Intent intentB = b(context, iIsGooglePlayServicesAvailable);
            Log.e("GooglePlayServicesUtil", "GooglePlayServices not available due to error " + iIsGooglePlayServicesAvailable);
            if (intentB != null) {
                throw new GooglePlayServicesRepairableException(iIsGooglePlayServicesAvailable, "Google Play Services not available", intentB);
            }
            throw new GooglePlayServicesNotAvailableException(iIsGooglePlayServicesAvailable);
        }
    }

    public static boolean showErrorDialogFragment(int errorCode, Activity activity, int requestCode) {
        return showErrorDialogFragment(errorCode, activity, requestCode, null);
    }

    public static boolean showErrorDialogFragment(int errorCode, Activity activity, int requestCode, DialogInterface.OnCancelListener cancelListener) {
        boolean z = false;
        Dialog errorDialog = getErrorDialog(errorCode, activity, requestCode, cancelListener);
        if (errorDialog == null) {
            return false;
        }
        try {
            z = activity instanceof FragmentActivity;
        } catch (NoClassDefFoundError e) {
        }
        if (z) {
            SupportErrorDialogFragment.newInstance(errorDialog, cancelListener).show(((FragmentActivity) activity).getSupportFragmentManager(), GMS_ERROR_DIALOG);
        } else {
            if (!gr.fu()) {
                throw new RuntimeException("This Activity does not support Fragments.");
            }
            ErrorDialogFragment.newInstance(errorDialog, cancelListener).show(activity.getFragmentManager(), GMS_ERROR_DIALOG);
        }
        return true;
    }

    public static void showErrorNotification(int errorCode, Context context) {
        Resources resources = context.getResources();
        Notification notification = new Notification(android.R.drawable.stat_sys_warning, resources.getString(R.string.common_google_play_services_notification_ticker), System.currentTimeMillis());
        notification.flags |= 16;
        notification.setLatestEventInfo(context, e(context, errorCode), resources.getString(R.string.common_google_play_services_error_notification_requested_by_msg, u(context)), getErrorPendingIntent(errorCode, context, 0));
        ((NotificationManager) context.getSystemService("notification")).notify(39789, notification);
    }

    private static void t(Context context) {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
        } catch (PackageManager.NameNotFoundException e) {
            Log.wtf("GooglePlayServicesUtil", "This should never happen.", e);
        }
        Bundle bundle = applicationInfo.metaData;
        if (bundle == null) {
            throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
        }
        int i = bundle.getInt("com.google.android.gms.version");
        if (i != 4452000) {
            throw new IllegalStateException("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected 4452000 but found " + i + ".  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
        }
    }

    private static String u(Context context) {
        ApplicationInfo applicationInfo;
        String str = context.getApplicationInfo().name;
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        String packageName = context.getPackageName();
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        return applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo).toString() : packageName;
    }
}
