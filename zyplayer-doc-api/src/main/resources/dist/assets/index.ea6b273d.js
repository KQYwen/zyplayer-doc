import{W as commonjsGlobal}from"./vendor.d3f949c4.js";var mavonEditor={exports:{}};(function(module,exports){(function(c,d){module.exports=d()})(commonjsGlobal,function(){return function(c){function d(t){if(i[t])return i[t].exports;var r=i[t]={i:t,l:!1,exports:{}};return c[t].call(r.exports,r,r.exports,d),r.l=!0,r.exports}var i={};return d.m=c,d.c=i,d.i=function(t){return t},d.d=function(t,r,e){d.o(t,r)||Object.defineProperty(t,r,{configurable:!1,enumerable:!0,get:e})},d.n=function(t){var r=t&&t.__esModule?function(){return t.default}:function(){return t};return d.d(r,"a",r),r},d.o=function(t,r){return Object.prototype.hasOwnProperty.call(t,r)},d.p="",d(d.s=69)}([function(c,d,i){function t(j){return Object.prototype.toString.call(j)}function r(j){return t(j)==="[object String]"}function e(j,M){return k.call(j,M)}function o(j){return Array.prototype.slice.call(arguments,1).forEach(function(M){if(M){if(typeof M!="object")throw new TypeError(M+"must be object");Object.keys(M).forEach(function(R){j[R]=M[R]})}}),j}function n(j,M,R){return[].concat(j.slice(0,M),R,j.slice(M+1))}function s(j){return!(j>=55296&&j<=57343)&&!(j>=64976&&j<=65007)&&(65535&j)!=65535&&(65535&j)!=65534&&!(j>=0&&j<=8)&&j!==11&&!(j>=14&&j<=31)&&!(j>=127&&j<=159)&&!(j>1114111)}function l(j){if(j>65535){j-=65536;var M=55296+(j>>10),R=56320+(1023&j);return String.fromCharCode(M,R)}return String.fromCharCode(j)}function u(j,M){var R=0;return e(D,M)?D[M]:M.charCodeAt(0)===35&&E.test(M)&&(R=M[1].toLowerCase()==="x"?parseInt(M.slice(2),16):parseInt(M.slice(1),10),s(R))?l(R):j}function a(j){return j.indexOf("\\")<0?j:j.replace(b,"$1")}function p(j){return j.indexOf("\\")<0&&j.indexOf("&")<0?j:j.replace(x,function(M,R,W){return R||u(M,W)})}function _(j){return A[j]}function v(j){return T.test(j)?j.replace(L,_):j}function g(j){return j.replace($,"\\$&")}function C(j){switch(j){case 9:case 32:return!0}return!1}function m(j){if(j>=8192&&j<=8202)return!0;switch(j){case 9:case 10:case 11:case 12:case 13:case 32:case 160:case 5760:case 8239:case 8287:case 12288:return!0}return!1}function h(j){return z.test(j)}function w(j){switch(j){case 33:case 34:case 35:case 36:case 37:case 38:case 39:case 40:case 41:case 42:case 43:case 44:case 45:case 46:case 47:case 58:case 59:case 60:case 61:case 62:case 63:case 64:case 91:case 92:case 93:case 94:case 95:case 96:case 123:case 124:case 125:case 126:return!0;default:return!1}}function f(j){return j=j.trim().replace(/\s+/g," "),"\u1E9E".toLowerCase()==="\u1E7E"&&(j=j.replace(/ẞ/g,"\xDF")),j.toLowerCase().toUpperCase()}var k=Object.prototype.hasOwnProperty,b=/\\([!"#$%&'()*+,\-.\/:;<=>?@[\\\]^_`{|}~])/g,y=/&([a-z#][a-z0-9]{1,31});/gi,x=new RegExp(b.source+"|"+y.source,"gi"),E=/^#((?:x[a-f0-9]{1,8}|[0-9]{1,8}))/i,D=i(53),T=/[&<>"]/,L=/[&<>"]/g,A={"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;"},$=/[.?*+^$[\]\\(){}|-]/g,z=i(34);d.lib={},d.lib.mdurl=i(57),d.lib.ucmicro=i(196),d.assign=o,d.isString=r,d.has=e,d.unescapeMd=a,d.unescapeAll=p,d.isValidEntityCode=s,d.fromCodePoint=l,d.escapeHtml=v,d.arrayReplaceAt=n,d.isSpace=C,d.isWhiteSpace=m,d.isMdAsciiPunct=w,d.isPunctChar=h,d.escapeRE=g,d.normalizeReference=f},function(c,d){var i=c.exports=typeof window!="undefined"&&window.Math==Math?window:typeof self!="undefined"&&self.Math==Math?self:Function("return this")();typeof __g=="number"&&(__g=i)},function(c,d){var i={}.hasOwnProperty;c.exports=function(t,r){return i.call(t,r)}},function(c,d,i){c.exports=!i(11)(function(){return Object.defineProperty({},"a",{get:function(){return 7}}).a!=7})},function(c,d,i){var t=i(5),r=i(13);c.exports=i(3)?function(e,o,n){return t.f(e,o,r(1,n))}:function(e,o,n){return e[o]=n,e}},function(c,d,i){var t=i(9),r=i(43),e=i(28),o=Object.defineProperty;d.f=i(3)?Object.defineProperty:function(n,s,l){if(t(n),s=e(s,!0),t(l),r)try{return o(n,s,l)}catch{}if("get"in l||"set"in l)throw TypeError("Accessors not supported!");return"value"in l&&(n[s]=l.value),n}},function(c,d,i){var t=i(88),r=i(19);c.exports=function(e){return t(r(e))}},function(c,d,i){var t=i(26)("wks"),r=i(14),e=i(1).Symbol,o=typeof e=="function";(c.exports=function(n){return t[n]||(t[n]=o&&e[n]||(o?e:r)("Symbol."+n))}).store=t},function(c,d){c.exports=function(i){return typeof i=="object"?i!==null:typeof i=="function"}},function(c,d,i){var t=i(8);c.exports=function(r){if(!t(r))throw TypeError(r+" is not an object!");return r}},function(c,d){var i=c.exports={version:"2.6.12"};typeof __e=="number"&&(__e=i)},function(c,d){c.exports=function(i){try{return!!i()}catch{return!0}}},function(c,d){c.exports=!0},function(c,d){c.exports=function(i,t){return{enumerable:!(1&i),configurable:!(2&i),writable:!(4&i),value:t}}},function(c,d){var i=0,t=Math.random();c.exports=function(r){return"Symbol(".concat(r===void 0?"":r,")_",(++i+t).toString(36))}},function(c,d){function i(r,e){var o=r[1]||"",n=r[3];if(!n)return o;if(e&&typeof btoa=="function"){var s=t(n);return[o].concat(n.sources.map(function(l){return"/*# sourceURL="+n.sourceRoot+l+" */"})).concat([s]).join(`
`)}return[o].join(`
`)}function t(r){return"/*# sourceMappingURL=data:application/json;charset=utf-8;base64,"+btoa(unescape(encodeURIComponent(JSON.stringify(r))))+" */"}c.exports=function(r){var e=[];return e.toString=function(){return this.map(function(o){var n=i(o,r);return o[2]?"@media "+o[2]+"{"+n+"}":n}).join("")},e.i=function(o,n){typeof o=="string"&&(o=[[null,o,""]]);for(var s={},l=0;l<this.length;l++){var u=this[l][0];typeof u=="number"&&(s[u]=!0)}for(l=0;l<o.length;l++){var a=o[l];typeof a[0]=="number"&&s[a[0]]||(n&&!a[2]?a[2]=n:n&&(a[2]="("+a[2]+") and ("+n+")"),e.push(a))}},e}},function(c,d){c.exports=function(i,t,r,e,o){var n,s=i=i||{},l=typeof i.default;l!=="object"&&l!=="function"||(n=i,s=i.default);var u=typeof s=="function"?s.options:s;t&&(u.render=t.render,u.staticRenderFns=t.staticRenderFns),e&&(u._scopeId=e);var a;if(o?(a=function(v){v=v||this.$vnode&&this.$vnode.ssrContext||this.parent&&this.parent.$vnode&&this.parent.$vnode.ssrContext,v||typeof __VUE_SSR_CONTEXT__=="undefined"||(v=__VUE_SSR_CONTEXT__),r&&r.call(this,v),v&&v._registeredComponents&&v._registeredComponents.add(o)},u._ssrRegister=a):r&&(a=r),a){var p=u.functional,_=p?u.render:u.beforeCreate;p?u.render=function(v,g){return a.call(g),_(v,g)}:u.beforeCreate=_?[].concat(_,a):[a]}return{esModule:n,exports:s,options:u}}},function(c,d,i){function t(f){for(var k=0;k<f.length;k++){var b=f[k],y=u[b.id];if(y){y.refs++;for(var x=0;x<y.parts.length;x++)y.parts[x](b.parts[x]);for(;x<b.parts.length;x++)y.parts.push(e(b.parts[x]));y.parts.length>b.parts.length&&(y.parts.length=b.parts.length)}else{for(var E=[],x=0;x<b.parts.length;x++)E.push(e(b.parts[x]));u[b.id]={id:b.id,refs:1,parts:E}}}}function r(){var f=document.createElement("style");return f.type="text/css",a.appendChild(f),f}function e(f){var k,b,y=document.querySelector("style["+m+'~="'+f.id+'"]');if(y){if(v)return g;y.parentNode.removeChild(y)}if(h){var x=_++;y=p||(p=r()),k=o.bind(null,y,x,!1),b=o.bind(null,y,x,!0)}else y=r(),k=n.bind(null,y),b=function(){y.parentNode.removeChild(y)};return k(f),function(E){if(E){if(E.css===f.css&&E.media===f.media&&E.sourceMap===f.sourceMap)return;k(f=E)}else b()}}function o(f,k,b,y){var x=b?"":y.css;if(f.styleSheet)f.styleSheet.cssText=w(k,x);else{var E=document.createTextNode(x),D=f.childNodes;D[k]&&f.removeChild(D[k]),D.length?f.insertBefore(E,D[k]):f.appendChild(E)}}function n(f,k){var b=k.css,y=k.media,x=k.sourceMap;if(y&&f.setAttribute("media",y),C.ssrId&&f.setAttribute(m,k.id),x&&(b+=`
/*# sourceURL=`+x.sources[0]+" */",b+=`
/*# sourceMappingURL=data:application/json;base64,`+btoa(unescape(encodeURIComponent(JSON.stringify(x))))+" */"),f.styleSheet)f.styleSheet.cssText=b;else{for(;f.firstChild;)f.removeChild(f.firstChild);f.appendChild(document.createTextNode(b))}}var s=typeof document!="undefined";if(typeof DEBUG!="undefined"&&DEBUG&&!s)throw new Error("vue-style-loader cannot be used in a non-browser environment. Use { target: 'node' } in your Webpack config to indicate a server-rendering environment.");var l=i(206),u={},a=s&&(document.head||document.getElementsByTagName("head")[0]),p=null,_=0,v=!1,g=function(){},C=null,m="data-vue-ssr-id",h=typeof navigator!="undefined"&&/msie [6-9]\b/.test(navigator.userAgent.toLowerCase());c.exports=function(f,k,b,y){v=b,C=y||{};var x=l(f,k);return t(x),function(E){for(var D=[],T=0;T<x.length;T++){var L=x[T],A=u[L.id];A.refs--,D.push(A)}E?(x=l(f,E),t(x)):x=[];for(var T=0;T<D.length;T++){var A=D[T];if(A.refs===0){for(var $=0;$<A.parts.length;$++)A.parts[$]();delete u[A.id]}}}};var w=function(){var f=[];return function(k,b){return f[k]=b,f.filter(Boolean).join(`
`)}}()},function(c,d,i){function t(m,h,w,f,k){return m!=="*"||h!=="*"||w.substring(f-2,f-1)!=="*"||w.substring(k+1,k+2)!=="*"}function r(m,h){typeof h!="function"&&(h=function(){});var w=document.querySelectorAll("script[src='"+m+"']");if(w.length>0)return w[0].addEventListener("load",function(){h()}),void h();var f=document.createElement("script"),k=document.getElementsByTagName("head")[0];f.type="text/javascript",f.charset="UTF-8",f.src=m,f.addEventListener?f.addEventListener("load",function(){h()},!1):f.attachEvent&&f.attachEvent("onreadystatechange",function(){window.event.srcElement.readyState==="loaded"&&h()}),k.appendChild(f)}function e(m,h,w){if(typeof h!="function"&&(h=function(){}),document.querySelectorAll("link[href='"+m+"']").length>0)return void h();if(w){var f=document.querySelectorAll("link#"+w);if(f.length)return void(f[0].href=m)}var k=document.createElement("link"),b=document.getElementsByTagName("head")[0];k.rel="stylesheet",k.href=m,w&&(k.id=w),k.addEventListener?k.addEventListener("load",function(){h()},!1):k.attachEvent&&k.attachEvent("onreadystatechange",function(){window.event.srcElement.readyState==="loaded"&&h()}),b.appendChild(k)}i.d(d,"g",function(){return o}),i.d(d,"i",function(){return n}),i.d(d,"j",function(){return s}),i.d(d,"k",function(){return l}),i.d(d,"h",function(){return u}),i.d(d,"l",function(){return a}),i.d(d,"m",function(){return p}),i.d(d,"e",function(){return _}),i.d(d,"f",function(){return v}),i.d(d,"b",function(){return g}),d.d=r,d.c=e,i.d(d,"a",function(){return C});var o=function(m,h,w){var f=h.prefix,k=h.subfix,b=h.str;if(h.type,m.focus(),typeof m.selectionStart=="number"&&typeof m.selectionEnd=="number"){var y=m.selectionStart,x=m.selectionEnd,E=m.value;y===x?(m.value=E.substring(0,y)+f+b+k+E.substring(x,E.length),m.selectionStart=y+f.length,m.selectionEnd=y+(b.length+f.length)):E.substring(y-f.length,y)===f&&E.substring(x,x+k.length)===k&&t(f,k,E,y,x)?(m.value=E.substring(0,y-f.length)+E.substring(y,x)+E.substring(x+k.length,E.length),m.selectionStart=y-f.length,m.selectionEnd=x-f.length):(m.value=E.substring(0,y)+f+E.substring(y,x)+k+E.substring(x,E.length),m.selectionStart=y+f.length,m.selectionEnd=y+(x-y+f.length))}else alert("Error: Browser version is too low");w.d_value=m.value,m.focus()},n=function(m){var h=m.getTextareaDom();if(typeof h.selectionStart=="number"&&typeof h.selectionEnd=="number"){var w=h.selectionStart,f=h.selectionEnd,k=h.value;if(w===f)h.value=k.substring(0,w)+"1. "+k.substring(f,k.length),h.selectionEnd=h.selectionStart=w+3;else{for(var b=w;b>0&&k.substring(b-1,b)!==`
`;)b--;for(var y=k.substring(b,f),x=y.split(`
`),E=0;E<x.length;E++)x[E]=E+1+". "+x[E];var D=x.join(`
`);h.value=k.substring(0,b)+D+k.substring(f,k.length),h.selectionStart=b,h.selectionEnd=f+D.length-y.length}}else alert("Error: Browser version is too low");m.d_value=h.value,h.focus()},s=function(m){var h=m.getTextareaDom();if(typeof h.selectionStart=="number"&&typeof h.selectionEnd=="number"){for(var w=h.selectionStart,f=h.selectionEnd,k=h.value,b=w;b>0&&k.substring(b-1,b)!==`
`;)b--;for(var y=f;y<k.length&&k.substring(y,y+1)!==`
`;)y++;y<k.length&&y++,h.value=k.substring(0,b)+k.substring(y,k.length),h.selectionEnd=h.selectionStart=b===0?0:b-1}else alert("Error: Browser version is too low");m.d_value=h.value,h.focus()},l=function(m){var h=m.getTextareaDom();if(typeof h.selectionStart=="number"&&typeof h.selectionEnd=="number"){var w=h.selectionStart,f=h.selectionEnd,k=h.value;if(w===f)h.value=k.substring(0,w)+"- "+k.substring(f,k.length),h.selectionEnd=h.selectionStart=w+2;else{for(var b=w;b>0&&k.substring(b-1,b)!==`
`;)b--;var y=k.substring(b,f),x=y.replace(/\n/g,`
- `);x="- "+x,h.value=k.substring(0,b)+x+k.substring(f,k.length),h.selectionStart=b,h.selectionEnd=f+x.length-y.length}}else alert("Error: Browser version is too low");m.d_value=h.value,h.focus()},u=function(m,h){h=h?new Array(h).fill(" ").join(""):"	";var w=m.getTextareaDom();if(typeof w.selectionStart=="number"&&typeof w.selectionEnd=="number"){var f=w.selectionStart,k=w.selectionEnd,b=w.value,y=b.substring(0,f).split(`
`).pop();if(y.match(/^\s*[0-9]+\.\s+\S*/)){var x=y.replace(/(\d+)/,1);w.value=b.substring(0,f-x.length)+h+x+b.substring(k,b.length)}else y.match(/^\s*-\s+\S*/)?w.value=b.substring(0,f-y.length)+h+y+b.substring(k,b.length):w.value=b.substring(0,f)+h+b.substring(k,b.length);w.selectionStart=w.selectionEnd=f+h.length}else alert("Error: Browser version is too low");m.d_value=w.value,w.focus()},a=function(m,h){var w=new RegExp(h?"\\s{"+h+"}":"	");console.log("regTab:",w);var f=m.getTextareaDom();if(typeof f.selectionStart=="number"&&typeof f.selectionEnd=="number"){var k=f.selectionStart,b=f.selectionEnd,y=f.value,x=y.substring(0,k).split(`
`).pop();x.search(w)>=0&&(f.value=y.substring(0,k-x.length)+x.replace(w,"")+y.substring(b,y.length),f.selectionStart=f.selectionEnd=k-(h||1))}else alert("Error: Browser version is too low");m.d_value=f.value,f.focus()},p=function(m,h){var w=m.getTextareaDom();if(typeof w.selectionStart=="number"&&typeof w.selectionEnd=="number"){var f=w.selectionStart,k=w.selectionEnd,b=w.value,y=b.substring(0,f).split(`
`).pop(),x=y.match(/^\s*(?:[0-9]+\.|-)\s+\S+/);if(x){h.preventDefault();var E=x.shift().match(/^\s*(?:[0-9]+\.|-)\s/).shift();if(E.search(/-/)>=0)w.value=b.substring(0,f)+`
`+E+b.substring(k,b.length),w.selectionStart=w.selectionEnd=f+E.length+1;else{var D=E.replace(/(\d+)/,parseInt(E)+1);w.value=b.substring(0,f)+`
`+D+b.substring(k,b.length),w.selectionStart=w.selectionEnd=f+D.length+1}}else{var T=y.match(/^\s*(?:[0-9]+\.|-)\s+$/);if(T){h.preventDefault();var L=T.shift().length;w.value=b.substring(0,f-L)+`
`+b.substring(k,b.length),w.selectionStart=w.selectionEnd=f-L}}}else alert("Error: Browser version is too low");m.d_value=w.value,w.focus()},_=function(m,h){var w=void 0;w=m.$refs.navigationContent,w.innerHTML=m.d_render;var f=w.children;if(f.length)for(var k=0;k<f.length;k++)(function(b,y,x){/^H[1-6]{1}$/.exec(b.tagName)?b.onclick=function(){var E=m.$refs.vShowContent,D=m.$refs.vNoteEdit;m.s_subfield?m.s_preview_switch&&(D.scrollTop=E.children[y].offsetTop*(D.scrollHeight-D.offsetHeight)/(E.scrollHeight-E.offsetHeight)):m.s_preview_switch&&(E.scrollTop=E.children[y].offsetTop)}:b.style.display="none"})(f[k],k)},v=function(m,h){var w=m.srcElement?m.srcElement:m.target,f=w.scrollTop/(w.scrollHeight-w.offsetHeight);h.edit_scroll_height>=0&&w.scrollHeight!==h.edit_scroll_height&&w.scrollHeight-w.offsetHeight-w.scrollTop<=30&&(h.$refs.vNoteEdit.scrollTop=w.scrollHeight-w.offsetHeight,f=1),h.edit_scroll_height=w.scrollHeight,h.$refs.vShowContent.scrollHeight>h.$refs.vShowContent.offsetHeight&&(h.$refs.vShowContent.scrollTop=(h.$refs.vShowContent.scrollHeight-h.$refs.vShowContent.offsetHeight)*f)},g=function(m){m.$el.addEventListener("fullscreenchange",function(h){m.$toolbar_right_read_change_status()},!1),m.$el.addEventListener("mozfullscreenchange",function(h){m.$toolbar_right_read_change_status()},!1),m.$el.addEventListener("webkitfullscreenchange",function(h){m.$toolbar_right_read_change_status()},!1),m.$el.addEventListener("msfullscreenchange",function(h){m.$toolbar_right_read_change_status()},!1)},C=function(m){m.$refs.vShowContent.addEventListener("click",function(h){h=h||window.event;var w=h.srcElement?h.srcElement:h.target;w.tagName==="IMG"&&(m.imageClick!=null?m.imageClick(w):m.d_preview_imgsrc=w.src)})}},function(c,d){c.exports=function(i){if(i==null)throw TypeError("Can't call method on  "+i);return i}},function(c,d){c.exports="constructor,hasOwnProperty,isPrototypeOf,propertyIsEnumerable,toLocaleString,toString,valueOf".split(",")},function(c,d){c.exports={}},function(c,d,i){var t=i(48),r=i(20);c.exports=Object.keys||function(e){return t(e,r)}},function(c,d){d.f={}.propertyIsEnumerable},function(c,d,i){var t=i(5).f,r=i(2),e=i(7)("toStringTag");c.exports=function(o,n,s){o&&!r(o=s?o:o.prototype,e)&&t(o,e,{configurable:!0,value:n})}},function(c,d,i){var t=i(26)("keys"),r=i(14);c.exports=function(e){return t[e]||(t[e]=r(e))}},function(c,d,i){var t=i(10),r=i(1),e=r["__core-js_shared__"]||(r["__core-js_shared__"]={});(c.exports=function(o,n){return e[o]||(e[o]=n!==void 0?n:{})})("versions",[]).push({version:t.version,mode:i(12)?"pure":"global",copyright:"\xA9 2020 Denis Pushkarev (zloirock.ru)"})},function(c,d){var i=Math.ceil,t=Math.floor;c.exports=function(r){return isNaN(r=+r)?0:(r>0?t:i)(r)}},function(c,d,i){var t=i(8);c.exports=function(r,e){if(!t(r))return r;var o,n;if(e&&typeof(o=r.toString)=="function"&&!t(n=o.call(r))||typeof(o=r.valueOf)=="function"&&!t(n=o.call(r))||!e&&typeof(o=r.toString)=="function"&&!t(n=o.call(r)))return n;throw TypeError("Can't convert object to primitive value")}},function(c,d,i){var t=i(1),r=i(10),e=i(12),o=i(30),n=i(5).f;c.exports=function(s){var l=r.Symbol||(r.Symbol=e?{}:t.Symbol||{});s.charAt(0)=="_"||s in l||n(l,s,{value:o.f(s)})}},function(c,d,i){d.f=i(7)},function(c,d,i){function t(n,s){return new e(s).process(n)}var r=i(51),e=i(111);d=c.exports=t,d.FilterCSS=e;for(var o in r)d[o]=r[o];typeof window!="undefined"&&(window.filterCSS=c.exports)},function(c,d,i){function t(){this.__rules__=[],this.__cache__=null}t.prototype.__find__=function(r){for(var e=0;e<this.__rules__.length;e++)if(this.__rules__[e].name===r)return e;return-1},t.prototype.__compile__=function(){var r=this,e=[""];r.__rules__.forEach(function(o){o.enabled&&o.alt.forEach(function(n){e.indexOf(n)<0&&e.push(n)})}),r.__cache__={},e.forEach(function(o){r.__cache__[o]=[],r.__rules__.forEach(function(n){n.enabled&&(o&&n.alt.indexOf(o)<0||r.__cache__[o].push(n.fn))})})},t.prototype.at=function(r,e,o){var n=this.__find__(r),s=o||{};if(n===-1)throw new Error("Parser rule not found: "+r);this.__rules__[n].fn=e,this.__rules__[n].alt=s.alt||[],this.__cache__=null},t.prototype.before=function(r,e,o,n){var s=this.__find__(r),l=n||{};if(s===-1)throw new Error("Parser rule not found: "+r);this.__rules__.splice(s,0,{name:e,enabled:!0,fn:o,alt:l.alt||[]}),this.__cache__=null},t.prototype.after=function(r,e,o,n){var s=this.__find__(r),l=n||{};if(s===-1)throw new Error("Parser rule not found: "+r);this.__rules__.splice(s+1,0,{name:e,enabled:!0,fn:o,alt:l.alt||[]}),this.__cache__=null},t.prototype.push=function(r,e,o){var n=o||{};this.__rules__.push({name:r,enabled:!0,fn:e,alt:n.alt||[]}),this.__cache__=null},t.prototype.enable=function(r,e){Array.isArray(r)||(r=[r]);var o=[];return r.forEach(function(n){var s=this.__find__(n);if(s<0){if(e)return;throw new Error("Rules manager: invalid rule name "+n)}this.__rules__[s].enabled=!0,o.push(n)},this),this.__cache__=null,o},t.prototype.enableOnly=function(r,e){Array.isArray(r)||(r=[r]),this.__rules__.forEach(function(o){o.enabled=!1}),this.enable(r,e)},t.prototype.disable=function(r,e){Array.isArray(r)||(r=[r]);var o=[];return r.forEach(function(n){var s=this.__find__(n);if(s<0){if(e)return;throw new Error("Rules manager: invalid rule name "+n)}this.__rules__[s].enabled=!1,o.push(n)},this),this.__cache__=null,o},t.prototype.getRules=function(r){return this.__cache__===null&&this.__compile__(),this.__cache__[r]||[]},c.exports=t},function(c,d,i){function t(r,e,o){this.type=r,this.tag=e,this.attrs=null,this.map=null,this.nesting=o,this.level=0,this.children=null,this.content="",this.markup="",this.info="",this.meta=null,this.block=!1,this.hidden=!1}t.prototype.attrIndex=function(r){var e,o,n;if(!this.attrs)return-1;for(e=this.attrs,o=0,n=e.length;o<n;o++)if(e[o][0]===r)return o;return-1},t.prototype.attrPush=function(r){this.attrs?this.attrs.push(r):this.attrs=[r]},t.prototype.attrSet=function(r,e){var o=this.attrIndex(r),n=[r,e];o<0?this.attrPush(n):this.attrs[o]=n},t.prototype.attrGet=function(r){var e=this.attrIndex(r),o=null;return e>=0&&(o=this.attrs[e][1]),o},t.prototype.attrJoin=function(r,e){var o=this.attrIndex(r);o<0?this.attrPush([r,e]):this.attrs[o][1]=this.attrs[o][1]+" "+e},c.exports=t},function(c,d){c.exports=/[!-#%-\*,-\/:;\?@\[-\]_\{\}\xA1\xA7\xAB\xB6\xB7\xBB\xBF\u037E\u0387\u055A-\u055F\u0589\u058A\u05BE\u05C0\u05C3\u05C6\u05F3\u05F4\u0609\u060A\u060C\u060D\u061B\u061E\u061F\u066A-\u066D\u06D4\u0700-\u070D\u07F7-\u07F9\u0830-\u083E\u085E\u0964\u0965\u0970\u09FD\u0A76\u0AF0\u0C84\u0DF4\u0E4F\u0E5A\u0E5B\u0F04-\u0F12\u0F14\u0F3A-\u0F3D\u0F85\u0FD0-\u0FD4\u0FD9\u0FDA\u104A-\u104F\u10FB\u1360-\u1368\u1400\u166D\u166E\u169B\u169C\u16EB-\u16ED\u1735\u1736\u17D4-\u17D6\u17D8-\u17DA\u1800-\u180A\u1944\u1945\u1A1E\u1A1F\u1AA0-\u1AA6\u1AA8-\u1AAD\u1B5A-\u1B60\u1BFC-\u1BFF\u1C3B-\u1C3F\u1C7E\u1C7F\u1CC0-\u1CC7\u1CD3\u2010-\u2027\u2030-\u2043\u2045-\u2051\u2053-\u205E\u207D\u207E\u208D\u208E\u2308-\u230B\u2329\u232A\u2768-\u2775\u27C5\u27C6\u27E6-\u27EF\u2983-\u2998\u29D8-\u29DB\u29FC\u29FD\u2CF9-\u2CFC\u2CFE\u2CFF\u2D70\u2E00-\u2E2E\u2E30-\u2E4E\u3001-\u3003\u3008-\u3011\u3014-\u301F\u3030\u303D\u30A0\u30FB\uA4FE\uA4FF\uA60D-\uA60F\uA673\uA67E\uA6F2-\uA6F7\uA874-\uA877\uA8CE\uA8CF\uA8F8-\uA8FA\uA8FC\uA92E\uA92F\uA95F\uA9C1-\uA9CD\uA9DE\uA9DF\uAA5C-\uAA5F\uAADE\uAADF\uAAF0\uAAF1\uABEB\uFD3E\uFD3F\uFE10-\uFE19\uFE30-\uFE52\uFE54-\uFE61\uFE63\uFE68\uFE6A\uFE6B\uFF01-\uFF03\uFF05-\uFF0A\uFF0C-\uFF0F\uFF1A\uFF1B\uFF1F\uFF20\uFF3B-\uFF3D\uFF3F\uFF5B\uFF5D\uFF5F-\uFF65]|\uD800[\uDD00-\uDD02\uDF9F\uDFD0]|\uD801\uDD6F|\uD802[\uDC57\uDD1F\uDD3F\uDE50-\uDE58\uDE7F\uDEF0-\uDEF6\uDF39-\uDF3F\uDF99-\uDF9C]|\uD803[\uDF55-\uDF59]|\uD804[\uDC47-\uDC4D\uDCBB\uDCBC\uDCBE-\uDCC1\uDD40-\uDD43\uDD74\uDD75\uDDC5-\uDDC8\uDDCD\uDDDB\uDDDD-\uDDDF\uDE38-\uDE3D\uDEA9]|\uD805[\uDC4B-\uDC4F\uDC5B\uDC5D\uDCC6\uDDC1-\uDDD7\uDE41-\uDE43\uDE60-\uDE6C\uDF3C-\uDF3E]|\uD806[\uDC3B\uDE3F-\uDE46\uDE9A-\uDE9C\uDE9E-\uDEA2]|\uD807[\uDC41-\uDC45\uDC70\uDC71\uDEF7\uDEF8]|\uD809[\uDC70-\uDC74]|\uD81A[\uDE6E\uDE6F\uDEF5\uDF37-\uDF3B\uDF44]|\uD81B[\uDE97-\uDE9A]|\uD82F\uDC9F|\uD836[\uDE87-\uDE8B]|\uD83A[\uDD5E\uDD5F]/},function(c,d){c.exports={indexOf:function(i,t){var r,e;if(Array.prototype.indexOf)return i.indexOf(t);for(r=0,e=i.length;r<e;r++)if(i[r]===t)return r;return-1},forEach:function(i,t,r){var e,o;if(Array.prototype.forEach)return i.forEach(t,r);for(e=0,o=i.length;e<o;e++)t.call(r,i[e],e,i)},trim:function(i){return String.prototype.trim?i.trim():i.replace(/(^\s*)|(\s*$)/g,"")},spaceIndex:function(i){var t=/\s|\n|\t/,r=t.exec(i);return r?r.index:-1}}},function(c,d,i){function t(e){i(203)}var r=i(16)(i(66),i(200),t,"data-v-548e2160",null);r.options.__file="D:\\work\\songwang\\yuangongji\\mavonEditor\\src\\components\\md-toolbar-left.vue",r.esModule&&Object.keys(r.esModule).some(function(e){return e!=="default"&&e.substr(0,2)!=="__"})&&console.error("named exports are not supported in *.vue files."),r.options.functional&&console.error("[vue-loader] md-toolbar-left.vue: functional components are not supported with templates, they should use render functions."),c.exports=r.exports},function(c,d,i){var t=i(16)(i(67),i(198),null,null,null);t.options.__file="D:\\work\\songwang\\yuangongji\\mavonEditor\\src\\components\\md-toolbar-right.vue",t.esModule&&Object.keys(t.esModule).some(function(r){return r!=="default"&&r.substr(0,2)!=="__"})&&console.error("named exports are not supported in *.vue files."),t.options.functional&&console.error("[vue-loader] md-toolbar-right.vue: functional components are not supported with templates, they should use render functions."),c.exports=t.exports},function(c,d,i){d.a={"1c":"1c",abnf:"abnf",accesslog:"accesslog",actionscript:"actionscript",as:"actionscript",ada:"ada",apache:"apache",apacheconf:"apache",applescript:"applescript",osascript:"applescript",arduino:"arduino",armasm:"armasm",arm:"armasm",asciidoc:"asciidoc",adoc:"asciidoc",aspectj:"aspectj",autohotkey:"autohotkey",ahk:"autohotkey",autoit:"autoit",avrasm:"avrasm",awk:"awk",axapta:"axapta",bash:"bash",sh:"bash",zsh:"bash",basic:"basic",bnf:"bnf",brainfuck:"brainfuck",bf:"brainfuck",cal:"cal",capnproto:"capnproto",capnp:"capnproto",ceylon:"ceylon",clean:"clean",icl:"clean",dcl:"clean","clojure-repl":"clojure-repl",clojure:"clojure",clj:"clojure",cmake:"cmake","cmake.in":"cmake",coffeescript:"coffeescript",coffee:"coffeescript",cson:"coffeescript",iced:"coffeescript",coq:"coq",cos:"cos",cls:"cos",cpp:"cpp",c:"cpp",cc:"cpp",h:"cpp","c++":"cpp","h++":"cpp",hpp:"cpp",crmsh:"crmsh",crm:"crmsh",pcmk:"crmsh",crystal:"crystal",cr:"crystal",cs:"cs",csharp:"cs",csp:"csp",css:"css",d:"d",dart:"dart",delphi:"delphi",dpr:"delphi",dfm:"delphi",pas:"delphi",pascal:"delphi",freepascal:"delphi",lazarus:"delphi",lpr:"delphi",lfm:"delphi",diff:"diff",patch:"diff",django:"django",jinja:"django",dns:"dns",bind:"dns",zone:"dns",dockerfile:"dockerfile",docker:"dockerfile",dos:"dos",bat:"dos",cmd:"dos",dsconfig:"dsconfig",dts:"dts",dust:"dust",dst:"dust",ebnf:"ebnf",elixir:"elixir",elm:"elm",erb:"erb","erlang-repl":"erlang-repl",erlang:"erlang",erl:"erlang",excel:"excel",xlsx:"excel",xls:"excel",fix:"fix",flix:"flix",fortran:"fortran",f90:"fortran",f95:"fortran",fsharp:"fsharp",fs:"fsharp",gams:"gams",gms:"gams",gauss:"gauss",gss:"gauss",gcode:"gcode",nc:"gcode",gherkin:"gherkin",feature:"gherkin",glsl:"glsl",go:"go",golang:"go",golo:"golo",gradle:"gradle",groovy:"groovy",haml:"haml",handlebars:"handlebars",hbs:"handlebars","html.hbs":"handlebars","html.handlebars":"handlebars",haskell:"haskell",hs:"haskell",haxe:"haxe",hx:"haxe",hsp:"hsp",htmlbars:"htmlbars",http:"http",https:"http",hy:"hy",hylang:"hy",inform7:"inform7",i7:"inform7",ini:"ini",toml:"ini",irpf90:"irpf90",java:"java",jsp:"java",javascript:"javascript",js:"javascript",jsx:"javascript","jboss-cli":"jboss-cli","wildfly-cli":"jboss-cli",json:"json","julia-repl":"julia-repl",julia:"julia",kotlin:"kotlin",lasso:"lasso",ls:"livescript",lassoscript:"lasso",ldif:"ldif",leaf:"leaf",less:"less",lisp:"lisp",livecodeserver:"livecodeserver",livescript:"livescript",llvm:"llvm",lsl:"lsl",lua:"lua",makefile:"makefile",mk:"makefile",mak:"makefile",markdown:"markdown",md:"markdown",mkdown:"markdown",mkd:"markdown",mathematica:"mathematica",mma:"mathematica",matlab:"matlab",maxima:"maxima",mel:"mel",mercury:"mercury",m:"mercury",moo:"mercury",mipsasm:"mipsasm",mips:"mipsasm",mizar:"mizar",mojolicious:"mojolicious",monkey:"monkey",moonscript:"moonscript",moon:"moonscript",n1ql:"n1ql",nginx:"nginx",nginxconf:"nginx",nimrod:"nimrod",nim:"nimrod",nix:"nix",nixos:"nix",nsis:"nsis",objectivec:"objectivec",mm:"objectivec",objc:"objectivec","obj-c":"objectivec",ocaml:"ocaml",ml:"sml",openscad:"openscad",scad:"openscad",oxygene:"oxygene",parser3:"parser3",perl:"perl",pl:"perl",pm:"perl",pf:"pf","pf.conf":"pf",php:"php",php3:"php",php4:"php",php5:"php",php6:"php",pony:"pony",powershell:"powershell",ps:"powershell",processing:"processing",profile:"profile",prolog:"prolog",protobuf:"protobuf",puppet:"puppet",pp:"puppet",purebasic:"purebasic",pb:"purebasic",pbi:"purebasic",python:"python",py:"python",gyp:"python",q:"q",k:"q",kdb:"q",qml:"qml",qt:"qml",r:"r",rib:"rib",roboconf:"roboconf",graph:"roboconf",instances:"roboconf",routeros:"routeros",mikrotik:"routeros",rsl:"rsl",ruby:"ruby",rb:"ruby",gemspec:"ruby",podspec:"ruby",thor:"ruby",irb:"ruby",ruleslanguage:"ruleslanguage",rust:"rust",rs:"rust",scala:"scala",scheme:"scheme",scilab:"scilab",sci:"scilab",scss:"scss",shell:"shell",console:"shell",smali:"smali",smalltalk:"smalltalk",st:"smalltalk",sml:"sml",sqf:"sqf",sql:"sql",stan:"stan",stata:"stata",do:"stata",ado:"stata",step21:"step21",p21:"step21",step:"step21",stp:"step21",stylus:"stylus",styl:"stylus",subunit:"subunit",swift:"swift",taggerscript:"taggerscript",tap:"tap",tcl:"tcl",tk:"tcl",tex:"tex",thrift:"thrift",tp:"tp",twig:"twig",craftcms:"twig",typescript:"typescript",ts:"typescript",vala:"vala",vbnet:"vbnet",vb:"vbnet","vbscript-html":"vbscript-html",vbscript:"vbscript",vbs:"vbscript",verilog:"verilog",v:"verilog",sv:"verilog",svh:"verilog",vhdl:"vhdl",vim:"vim",x86asm:"x86asm",xl:"xl",tao:"xl",xml:"xml",html:"xml",xhtml:"xml",rss:"xml",atom:"xml",xjb:"xml",xsd:"xml",xsl:"xml",plist:"xml",xquery:"xquery",xpath:"xquery",xq:"xquery",yaml:"yaml",yml:"yaml",YAML:"yaml",zephir:"zephir",zep:"zephir"}},function(c,d,i){function t(l){return l&&l.__esModule?l:{default:l}}d.__esModule=!0;var r=i(79),e=t(r),o=i(78),n=t(o),s=typeof n.default=="function"&&typeof e.default=="symbol"?function(l){return typeof l}:function(l){return l&&typeof n.default=="function"&&l.constructor===n.default&&l!==n.default.prototype?"symbol":typeof l};d.default=typeof n.default=="function"&&s(e.default)==="symbol"?function(l){return l===void 0?"undefined":s(l)}:function(l){return l&&typeof n.default=="function"&&l.constructor===n.default&&l!==n.default.prototype?"symbol":l===void 0?"undefined":s(l)}},function(c,d){var i={}.toString;c.exports=function(t){return i.call(t).slice(8,-1)}},function(c,d,i){var t=i(8),r=i(1).document,e=t(r)&&t(r.createElement);c.exports=function(o){return e?r.createElement(o):{}}},function(c,d,i){var t=i(1),r=i(10),e=i(85),o=i(4),n=i(2),s=function(l,u,a){var p,_,v,g=l&s.F,C=l&s.G,m=l&s.S,h=l&s.P,w=l&s.B,f=l&s.W,k=C?r:r[u]||(r[u]={}),b=k.prototype,y=C?t:m?t[u]:(t[u]||{}).prototype;C&&(a=u);for(p in a)(_=!g&&y&&y[p]!==void 0)&&n(k,p)||(v=_?y[p]:a[p],k[p]=C&&typeof y[p]!="function"?a[p]:w&&_?e(v,t):f&&y[p]==v?function(x){var E=function(D,T,L){if(this instanceof x){switch(arguments.length){case 0:return new x;case 1:return new x(D);case 2:return new x(D,T)}return new x(D,T,L)}return x.apply(this,arguments)};return E.prototype=x.prototype,E}(v):h&&typeof v=="function"?e(Function.call,v):v,h&&((k.virtual||(k.virtual={}))[p]=v,l&s.R&&b&&!b[p]&&o(b,p,v)))};s.F=1,s.G=2,s.S=4,s.P=8,s.B=16,s.W=32,s.U=64,s.R=128,c.exports=s},function(c,d,i){c.exports=!i(3)&&!i(11)(function(){return Object.defineProperty(i(41)("div"),"a",{get:function(){return 7}}).a!=7})},function(c,d,i){var t=i(12),r=i(42),e=i(49),o=i(4),n=i(21),s=i(90),l=i(24),u=i(96),a=i(7)("iterator"),p=!([].keys&&"next"in[].keys()),_=function(){return this};c.exports=function(v,g,C,m,h,w,f){s(C,g,m);var k,b,y,x=function(M){if(!p&&M in L)return L[M];switch(M){case"keys":case"values":return function(){return new C(this,M)}}return function(){return new C(this,M)}},E=g+" Iterator",D=h=="values",T=!1,L=v.prototype,A=L[a]||L["@@iterator"]||h&&L[h],$=A||x(h),z=h?D?x("entries"):$:void 0,j=g=="Array"&&L.entries||A;if(j&&(y=u(j.call(new v)))!==Object.prototype&&y.next&&(l(y,E,!0),t||typeof y[a]=="function"||o(y,a,_)),D&&A&&A.name!=="values"&&(T=!0,$=function(){return A.call(this)}),t&&!f||!p&&!T&&L[a]||o(L,a,$),n[g]=$,n[E]=_,h)if(k={values:D?$:x("values"),keys:w?$:x("keys"),entries:z},f)for(b in k)b in L||e(L,b,k[b]);else r(r.P+r.F*(p||T),g,k);return k}},function(c,d,i){var t=i(9),r=i(93),e=i(20),o=i(25)("IE_PROTO"),n=function(){},s=function(){var l,u=i(41)("iframe"),a=e.length;for(u.style.display="none",i(87).appendChild(u),u.src="javascript:",l=u.contentWindow.document,l.open(),l.write("<script>document.F=Object<\/script>"),l.close(),s=l.F;a--;)delete s.prototype[e[a]];return s()};c.exports=Object.create||function(l,u){var a;return l!==null?(n.prototype=t(l),a=new n,n.prototype=null,a[o]=l):a=s(),u===void 0?a:r(a,u)}},function(c,d,i){var t=i(48),r=i(20).concat("length","prototype");d.f=Object.getOwnPropertyNames||function(e){return t(e,r)}},function(c,d){d.f=Object.getOwnPropertySymbols},function(c,d,i){var t=i(2),r=i(6),e=i(84)(!1),o=i(25)("IE_PROTO");c.exports=function(n,s){var l,u=r(n),a=0,p=[];for(l in u)l!=o&&t(u,l)&&p.push(l);for(;s.length>a;)t(u,l=s[a++])&&(~e(p,l)||p.push(l));return p}},function(c,d,i){c.exports=i(4)},function(c,d,i){var t=i(19);c.exports=function(r){return Object(t(r))}},function(c,d){function i(){var n={};return n["align-content"]=!1,n["align-items"]=!1,n["align-self"]=!1,n["alignment-adjust"]=!1,n["alignment-baseline"]=!1,n.all=!1,n["anchor-point"]=!1,n.animation=!1,n["animation-delay"]=!1,n["animation-direction"]=!1,n["animation-duration"]=!1,n["animation-fill-mode"]=!1,n["animation-iteration-count"]=!1,n["animation-name"]=!1,n["animation-play-state"]=!1,n["animation-timing-function"]=!1,n.azimuth=!1,n["backface-visibility"]=!1,n.background=!0,n["background-attachment"]=!0,n["background-clip"]=!0,n["background-color"]=!0,n["background-image"]=!0,n["background-origin"]=!0,n["background-position"]=!0,n["background-repeat"]=!0,n["background-size"]=!0,n["baseline-shift"]=!1,n.binding=!1,n.bleed=!1,n["bookmark-label"]=!1,n["bookmark-level"]=!1,n["bookmark-state"]=!1,n.border=!0,n["border-bottom"]=!0,n["border-bottom-color"]=!0,n["border-bottom-left-radius"]=!0,n["border-bottom-right-radius"]=!0,n["border-bottom-style"]=!0,n["border-bottom-width"]=!0,n["border-collapse"]=!0,n["border-color"]=!0,n["border-image"]=!0,n["border-image-outset"]=!0,n["border-image-repeat"]=!0,n["border-image-slice"]=!0,n["border-image-source"]=!0,n["border-image-width"]=!0,n["border-left"]=!0,n["border-left-color"]=!0,n["border-left-style"]=!0,n["border-left-width"]=!0,n["border-radius"]=!0,n["border-right"]=!0,n["border-right-color"]=!0,n["border-right-style"]=!0,n["border-right-width"]=!0,n["border-spacing"]=!0,n["border-style"]=!0,n["border-top"]=!0,n["border-top-color"]=!0,n["border-top-left-radius"]=!0,n["border-top-right-radius"]=!0,n["border-top-style"]=!0,n["border-top-width"]=!0,n["border-width"]=!0,n.bottom=!1,n["box-decoration-break"]=!0,n["box-shadow"]=!0,n["box-sizing"]=!0,n["box-snap"]=!0,n["box-suppress"]=!0,n["break-after"]=!0,n["break-before"]=!0,n["break-inside"]=!0,n["caption-side"]=!1,n.chains=!1,n.clear=!0,n.clip=!1,n["clip-path"]=!1,n["clip-rule"]=!1,n.color=!0,n["color-interpolation-filters"]=!0,n["column-count"]=!1,n["column-fill"]=!1,n["column-gap"]=!1,n["column-rule"]=!1,n["column-rule-color"]=!1,n["column-rule-style"]=!1,n["column-rule-width"]=!1,n["column-span"]=!1,n["column-width"]=!1,n.columns=!1,n.contain=!1,n.content=!1,n["counter-increment"]=!1,n["counter-reset"]=!1,n["counter-set"]=!1,n.crop=!1,n.cue=!1,n["cue-after"]=!1,n["cue-before"]=!1,n.cursor=!1,n.direction=!1,n.display=!0,n["display-inside"]=!0,n["display-list"]=!0,n["display-outside"]=!0,n["dominant-baseline"]=!1,n.elevation=!1,n["empty-cells"]=!1,n.filter=!1,n.flex=!1,n["flex-basis"]=!1,n["flex-direction"]=!1,n["flex-flow"]=!1,n["flex-grow"]=!1,n["flex-shrink"]=!1,n["flex-wrap"]=!1,n.float=!1,n["float-offset"]=!1,n["flood-color"]=!1,n["flood-opacity"]=!1,n["flow-from"]=!1,n["flow-into"]=!1,n.font=!0,n["font-family"]=!0,n["font-feature-settings"]=!0,n["font-kerning"]=!0,n["font-language-override"]=!0,n["font-size"]=!0,n["font-size-adjust"]=!0,n["font-stretch"]=!0,n["font-style"]=!0,n["font-synthesis"]=!0,n["font-variant"]=!0,n["font-variant-alternates"]=!0,n["font-variant-caps"]=!0,n["font-variant-east-asian"]=!0,n["font-variant-ligatures"]=!0,n["font-variant-numeric"]=!0,n["font-variant-position"]=!0,n["font-weight"]=!0,n.grid=!1,n["grid-area"]=!1,n["grid-auto-columns"]=!1,n["grid-auto-flow"]=!1,n["grid-auto-rows"]=!1,n["grid-column"]=!1,n["grid-column-end"]=!1,n["grid-column-start"]=!1,n["grid-row"]=!1,n["grid-row-end"]=!1,n["grid-row-start"]=!1,n["grid-template"]=!1,n["grid-template-areas"]=!1,n["grid-template-columns"]=!1,n["grid-template-rows"]=!1,n["hanging-punctuation"]=!1,n.height=!0,n.hyphens=!1,n.icon=!1,n["image-orientation"]=!1,n["image-resolution"]=!1,n["ime-mode"]=!1,n["initial-letters"]=!1,n["inline-box-align"]=!1,n["justify-content"]=!1,n["justify-items"]=!1,n["justify-self"]=!1,n.left=!1,n["letter-spacing"]=!0,n["lighting-color"]=!0,n["line-box-contain"]=!1,n["line-break"]=!1,n["line-grid"]=!1,n["line-height"]=!1,n["line-snap"]=!1,n["line-stacking"]=!1,n["line-stacking-ruby"]=!1,n["line-stacking-shift"]=!1,n["line-stacking-strategy"]=!1,n["list-style"]=!0,n["list-style-image"]=!0,n["list-style-position"]=!0,n["list-style-type"]=!0,n.margin=!0,n["margin-bottom"]=!0,n["margin-left"]=!0,n["margin-right"]=!0,n["margin-top"]=!0,n["marker-offset"]=!1,n["marker-side"]=!1,n.marks=!1,n.mask=!1,n["mask-box"]=!1,n["mask-box-outset"]=!1,n["mask-box-repeat"]=!1,n["mask-box-slice"]=!1,n["mask-box-source"]=!1,n["mask-box-width"]=!1,n["mask-clip"]=!1,n["mask-image"]=!1,n["mask-origin"]=!1,n["mask-position"]=!1,n["mask-repeat"]=!1,n["mask-size"]=!1,n["mask-source-type"]=!1,n["mask-type"]=!1,n["max-height"]=!0,n["max-lines"]=!1,n["max-width"]=!0,n["min-height"]=!0,n["min-width"]=!0,n["move-to"]=!1,n["nav-down"]=!1,n["nav-index"]=!1,n["nav-left"]=!1,n["nav-right"]=!1,n["nav-up"]=!1,n["object-fit"]=!1,n["object-position"]=!1,n.opacity=!1,n.order=!1,n.orphans=!1,n.outline=!1,n["outline-color"]=!1,n["outline-offset"]=!1,n["outline-style"]=!1,n["outline-width"]=!1,n.overflow=!1,n["overflow-wrap"]=!1,n["overflow-x"]=!1,n["overflow-y"]=!1,n.padding=!0,n["padding-bottom"]=!0,n["padding-left"]=!0,n["padding-right"]=!0,n["padding-top"]=!0,n.page=!1,n["page-break-after"]=!1,n["page-break-before"]=!1,n["page-break-inside"]=!1,n["page-policy"]=!1,n.pause=!1,n["pause-after"]=!1,n["pause-before"]=!1,n.perspective=!1,n["perspective-origin"]=!1,n.pitch=!1,n["pitch-range"]=!1,n["play-during"]=!1,n.position=!1,n["presentation-level"]=!1,n.quotes=!1,n["region-fragment"]=!1,n.resize=!1,n.rest=!1,n["rest-after"]=!1,n["rest-before"]=!1,n.richness=!1,n.right=!1,n.rotation=!1,n["rotation-point"]=!1,n["ruby-align"]=!1,n["ruby-merge"]=!1,n["ruby-position"]=!1,n["shape-image-threshold"]=!1,n["shape-outside"]=!1,n["shape-margin"]=!1,n.size=!1,n.speak=!1,n["speak-as"]=!1,n["speak-header"]=!1,n["speak-numeral"]=!1,n["speak-punctuation"]=!1,n["speech-rate"]=!1,n.stress=!1,n["string-set"]=!1,n["tab-size"]=!1,n["table-layout"]=!1,n["text-align"]=!0,n["text-align-last"]=!0,n["text-combine-upright"]=!0,n["text-decoration"]=!0,n["text-decoration-color"]=!0,n["text-decoration-line"]=!0,n["text-decoration-skip"]=!0,n["text-decoration-style"]=!0,n["text-emphasis"]=!0,n["text-emphasis-color"]=!0,n["text-emphasis-position"]=!0,n["text-emphasis-style"]=!0,n["text-height"]=!0,n["text-indent"]=!0,n["text-justify"]=!0,n["text-orientation"]=!0,n["text-overflow"]=!0,n["text-shadow"]=!0,n["text-space-collapse"]=!0,n["text-transform"]=!0,n["text-underline-position"]=!0,n["text-wrap"]=!0,n.top=!1,n.transform=!1,n["transform-origin"]=!1,n["transform-style"]=!1,n.transition=!1,n["transition-delay"]=!1,n["transition-duration"]=!1,n["transition-property"]=!1,n["transition-timing-function"]=!1,n["unicode-bidi"]=!1,n["vertical-align"]=!1,n.visibility=!1,n["voice-balance"]=!1,n["voice-duration"]=!1,n["voice-family"]=!1,n["voice-pitch"]=!1,n["voice-range"]=!1,n["voice-rate"]=!1,n["voice-stress"]=!1,n["voice-volume"]=!1,n.volume=!1,n["white-space"]=!1,n.widows=!1,n.width=!0,n["will-change"]=!1,n["word-break"]=!0,n["word-spacing"]=!0,n["word-wrap"]=!0,n["wrap-flow"]=!1,n["wrap-through"]=!1,n["writing-mode"]=!1,n["z-index"]=!1,n}function t(n,s,l){}function r(n,s,l){}function e(n,s){return o.test(s)?"":s}var o=/javascript\s*\:/gim;d.whiteList=i(),d.getDefaultWhiteList=i,d.onAttr=t,d.onIgnoreAttr=r,d.safeAttrValue=e},function(c,d){c.exports={indexOf:function(i,t){var r,e;if(Array.prototype.indexOf)return i.indexOf(t);for(r=0,e=i.length;r<e;r++)if(i[r]===t)return r;return-1},forEach:function(i,t,r){var e,o;if(Array.prototype.forEach)return i.forEach(t,r);for(e=0,o=i.length;e<o;e++)t.call(r,i[e],e,i)},trim:function(i){return String.prototype.trim?i.trim():i.replace(/(^\s*)|(\s*$)/g,"")},trimRight:function(i){return String.prototype.trimRight?i.trimRight():i.replace(/(\s*$)/g,"")}}},function(c,d,i){c.exports=i(181)},function(c,d,i){var t=`<[A-Za-z][A-Za-z0-9\\-]*(?:\\s+[a-zA-Z_:][a-zA-Z0-9:._-]*(?:\\s*=\\s*(?:[^"'=<>\`\\x00-\\x20]+|'[^']*'|"[^"]*"))?)*\\s*\\/?>`,r="<\\/[A-Za-z][A-Za-z0-9\\-]*\\s*>",e=new RegExp("^(?:"+t+"|"+r+"|<!---->|<!--(?:-?[^>-])(?:-?[^-])*-->|<[?].*?[?]>|<![A-Z]+\\s+[^>]*>|<!\\[CDATA\\[[\\s\\S]*?\\]\\]>)"),o=new RegExp("^(?:"+t+"|"+r+")");c.exports.HTML_TAG_RE=e,c.exports.HTML_OPEN_CLOSE_TAG_RE=o},function(c,d,i){function t(r,e){var o,n,s,l,u,a,p=e.length;for(o=p-1;o>=0;o--)n=e[o],n.marker!==95&&n.marker!==42||n.end!==-1&&(s=e[n.end],a=o>0&&e[o-1].end===n.end+1&&e[o-1].token===n.token-1&&e[n.end+1].token===s.token+1&&e[o-1].marker===n.marker,u=String.fromCharCode(n.marker),l=r.tokens[n.token],l.type=a?"strong_open":"em_open",l.tag=a?"strong":"em",l.nesting=1,l.markup=a?u+u:u,l.content="",l=r.tokens[s.token],l.type=a?"strong_close":"em_close",l.tag=a?"strong":"em",l.nesting=-1,l.markup=a?u+u:u,l.content="",a&&(r.tokens[e[o-1].token].content="",r.tokens[e[n.end+1].token].content="",o--))}c.exports.tokenize=function(r,e){var o,n,s,l=r.pos,u=r.src.charCodeAt(l);if(e||u!==95&&u!==42)return!1;for(n=r.scanDelims(r.pos,u===42),o=0;o<n.length;o++)s=r.push("text","",0),s.content=String.fromCharCode(u),r.delimiters.push({marker:u,length:n.length,jump:o,token:r.tokens.length-1,end:-1,open:n.can_open,close:n.can_close});return r.pos+=n.length,!0},c.exports.postProcess=function(r){var e,o=r.tokens_meta,n=r.tokens_meta.length;for(t(r,r.delimiters),e=0;e<n;e++)o[e]&&o[e].delimiters&&t(r,o[e].delimiters)}},function(c,d,i){function t(r,e){var o,n,s,l,u,a=[],p=e.length;for(o=0;o<p;o++)s=e[o],s.marker===126&&s.end!==-1&&(l=e[s.end],u=r.tokens[s.token],u.type="s_open",u.tag="s",u.nesting=1,u.markup="~~",u.content="",u=r.tokens[l.token],u.type="s_close",u.tag="s",u.nesting=-1,u.markup="~~",u.content="",r.tokens[l.token-1].type==="text"&&r.tokens[l.token-1].content==="~"&&a.push(l.token-1));for(;a.length;){for(o=a.pop(),n=o+1;n<r.tokens.length&&r.tokens[n].type==="s_close";)n++;n--,o!==n&&(u=r.tokens[n],r.tokens[n]=r.tokens[o],r.tokens[o]=u)}}c.exports.tokenize=function(r,e){var o,n,s,l,u,a=r.pos,p=r.src.charCodeAt(a);if(e||p!==126||(n=r.scanDelims(r.pos,!0),l=n.length,u=String.fromCharCode(p),l<2))return!1;for(l%2&&(s=r.push("text","",0),s.content=u,l--),o=0;o<l;o+=2)s=r.push("text","",0),s.content=u+u,r.delimiters.push({marker:p,length:0,jump:o,token:r.tokens.length-1,end:-1,open:n.can_open,close:n.can_close});return r.pos+=n.length,!0},c.exports.postProcess=function(r){var e,o=r.tokens_meta,n=r.tokens_meta.length;for(t(r,r.delimiters),e=0;e<n;e++)o[e]&&o[e].delimiters&&t(r,o[e].delimiters)}},function(c,d,i){c.exports.encode=i(183),c.exports.decode=i(182),c.exports.format=i(184),c.exports.parse=i(185)},function(c,d){c.exports=/[\0-\x1F\x7F-\x9F]/},function(c,d){c.exports=/[ \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000]/},function(c,d){c.exports=/[\0-\uD7FF\uE000-\uFFFF]|[\uD800-\uDBFF][\uDC00-\uDFFF]|[\uD800-\uDBFF](?![\uDC00-\uDFFF])|(?:[^\uD800-\uDBFF]|^)[\uDC00-\uDFFF]/},function(c,d,i){function t(){return{a:["target","href","title"],abbr:["title"],address:[],area:["shape","coords","href","alt"],article:[],aside:[],audio:["autoplay","controls","loop","preload","src"],b:[],bdi:["dir"],bdo:["dir"],big:[],blockquote:["cite"],br:[],caption:[],center:[],cite:[],code:[],col:["align","valign","span","width"],colgroup:["align","valign","span","width"],dd:[],del:["datetime"],details:["open"],div:[],dl:[],dt:[],em:[],font:["color","size","face"],footer:[],h1:[],h2:[],h3:[],h4:[],h5:[],h6:[],header:[],hr:[],i:[],img:["src","alt","title","width","height"],ins:["datetime"],li:[],mark:[],nav:[],ol:[],p:[],pre:[],s:[],section:[],small:[],span:[],sub:[],sup:[],strong:[],table:["width","border","align","valign"],tbody:["align","valign"],td:["width","rowspan","colspan","align","valign"],tfoot:["align","valign"],th:["width","rowspan","colspan","align","valign"],thead:["align","valign"],tr:["rowspan","align","valign"],tt:[],u:[],ul:[],video:["autoplay","controls","loop","preload","src","height","width"]}}function r(S,O,q){}function e(S,O,q){}function o(S,O,q){}function n(S,O,q){}function s(S){return S.replace(E,"&lt;").replace(D,"&gt;")}function l(S,O,q,I){if(q=g(q),O==="href"||O==="src"){if((q=y.trim(q))==="#")return"#";if(q.substr(0,7)!=="http://"&&q.substr(0,8)!=="https://"&&q.substr(0,7)!=="mailto:"&&q.substr(0,4)!=="tel:"&&q.substr(0,11)!=="data:image/"&&q.substr(0,6)!=="ftp://"&&q.substr(0,2)!=="./"&&q.substr(0,3)!=="../"&&q[0]!=="#"&&q[0]!=="/")return""}else if(O==="background"){if(j.lastIndex=0,j.test(q))return""}else if(O==="style"){if(M.lastIndex=0,M.test(q)||(R.lastIndex=0,R.test(q)&&(j.lastIndex=0,j.test(q))))return"";I!==!1&&(I=I||x,q=I.process(q))}return q=C(q)}function u(S){return S.replace(T,"&quot;")}function a(S){return S.replace(L,'"')}function p(S){return S.replace(A,function(O,q){return q[0]==="x"||q[0]==="X"?String.fromCharCode(parseInt(q.substr(1),16)):String.fromCharCode(parseInt(q,10))})}function _(S){return S.replace($,":").replace(z," ")}function v(S){for(var O="",q=0,I=S.length;q<I;q++)O+=S.charCodeAt(q)<32?" ":S.charAt(q);return y.trim(O)}function g(S){return S=a(S),S=p(S),S=_(S),S=v(S)}function C(S){return S=u(S),S=s(S)}function m(){return""}function h(S,O){function q(N){return!!I||y.indexOf(S,N)!==-1}typeof O!="function"&&(O=function(){});var I=!Array.isArray(S),B=[],U=!1;return{onIgnoreTag:function(N,V,K){if(q(N)){if(K.isClosing){var G="[/removed]",Q=K.position+G.length;return B.push([U!==!1?U:K.position,Q]),U=!1,G}return U||(U=K.position),"[removed]"}return O(N,V,K)},remove:function(N){var V="",K=0;return y.forEach(B,function(G){V+=N.slice(K,G[0]),K=G[1]}),V+=N.slice(K)}}}function w(S){return S.replace(W,"")}function f(S){var O=S.split("");return O=O.filter(function(q){var I=q.charCodeAt(0);return I!==127&&(!(I<=31)||I===10||I===13)}),O.join("")}var k=i(31).FilterCSS,b=i(31).getDefaultWhiteList,y=i(35),x=new k,E=/</g,D=/>/g,T=/"/g,L=/&quot;/g,A=/&#([a-zA-Z0-9]*);?/gim,$=/&colon;?/gim,z=/&newline;?/gim,j=/((j\s*a\s*v\s*a|v\s*b|l\s*i\s*v\s*e)\s*s\s*c\s*r\s*i\s*p\s*t\s*|m\s*o\s*c\s*h\s*a)\:/gi,M=/e\s*x\s*p\s*r\s*e\s*s\s*s\s*i\s*o\s*n\s*\(.*/gi,R=/u\s*r\s*l\s*\(.*/gi,W=/<!--[\s\S]*?-->/g;d.whiteList=t(),d.getDefaultWhiteList=t,d.onTag=r,d.onIgnoreTag=e,d.onTagAttr=o,d.onIgnoreTagAttr=n,d.safeAttrValue=l,d.escapeHtml=s,d.escapeQuote=u,d.unescapeQuote=a,d.escapeHtmlEntities=p,d.escapeDangerHtml5Entities=_,d.clearNonPrintableCharacter=v,d.friendlyAttrValue=g,d.escapeAttrValue=C,d.onIgnoreTagStripAll=m,d.StripTagBody=h,d.stripCommentTag=w,d.stripBlankChar=f,d.cssFilter=x,d.getDefaultCSSWhiteList=b},function(c,d,i){function t(_){var v=a.spaceIndex(_);if(v===-1)var g=_.slice(1,-1);else var g=_.slice(1,v+1);return g=a.trim(g).toLowerCase(),g.slice(0,1)==="/"&&(g=g.slice(1)),g.slice(-1)==="/"&&(g=g.slice(0,-1)),g}function r(_){return _.slice(0,2)==="</"}function e(_,v,g){var C="",m=0,h=!1,w=!1,f=0,k=_.length,b="",y="";t:for(f=0;f<k;f++){var x=_.charAt(f);if(h===!1){if(x==="<"){h=f;continue}}else if(w===!1){if(x==="<"){C+=g(_.slice(m,f)),h=f,m=f;continue}if(x===">"){C+=g(_.slice(m,h)),y=_.slice(h,f+1),b=t(y),C+=v(h,C.length,b,y,r(y)),m=f+1,h=!1;continue}if(x==='"'||x==="'")for(var E=1,D=_.charAt(f-E);D===" "||D==="=";){if(D==="="){w=x;continue t}D=_.charAt(f-++E)}}else if(x===w){w=!1;continue}}return m<_.length&&(C+=g(_.substr(m))),C}function o(_,v){function g(x,E){if(x=a.trim(x),x=x.replace(p,"").toLowerCase(),!(x.length<1)){var D=v(x,E||"");D&&m.push(D)}}for(var C=0,m=[],h=!1,w=_.length,f=0;f<w;f++){var k,b,y=_.charAt(f);if(h!==!1||y!=="=")if(h===!1||f!==C||y!=='"'&&y!=="'"||_.charAt(f-1)!=="="){if(/\s|\n|\t/.test(y)){if(_=_.replace(/\s|\n|\t/g," "),h===!1){if((b=n(_,f))===-1){k=a.trim(_.slice(C,f)),g(k),h=!1,C=f+1;continue}f=b-1;continue}if((b=s(_,f-1))===-1){k=a.trim(_.slice(C,f)),k=u(k),g(h,k),h=!1,C=f+1;continue}}}else{if((b=_.indexOf(y,f+1))===-1)break;k=a.trim(_.slice(C+1,b)),g(h,k),h=!1,f=b,C=f+1}else h=_.slice(C,f),C=f+1}return C<_.length&&(h===!1?g(_.slice(C)):g(h,u(a.trim(_.slice(C))))),a.trim(m.join(" "))}function n(_,v){for(;v<_.length;v++){var g=_[v];if(g!==" ")return g==="="?v:-1}}function s(_,v){for(;v>0;v--){var g=_[v];if(g!==" ")return g==="="?v:-1}}function l(_){return _[0]==='"'&&_[_.length-1]==='"'||_[0]==="'"&&_[_.length-1]==="'"}function u(_){return l(_)?_.substr(1,_.length-2):_}var a=i(35),p=/[^a-zA-Z0-9_:\.\-]/gim;d.parseTag=e,d.parseAttr=o},function(c,d,i){function t(e){i(204),i(205)}var r=i(16)(i(68),i(201),t,"data-v-7a63e4b3",null);r.options.__file="D:\\work\\songwang\\yuangongji\\mavonEditor\\src\\mavon-editor.vue",r.esModule&&Object.keys(r.esModule).some(function(e){return e!=="default"&&e.substr(0,2)!=="__"})&&console.error("named exports are not supported in *.vue files."),r.options.functional&&console.error("[vue-loader] mavon-editor.vue: functional components are not supported with templates, they should use render functions."),c.exports=r.exports},function(c,d,i){var t=i(197),r={autoTextarea:t,install:function(e){e.component("auto-textarea",t)}};c.exports=r},function(c,d,i){Object.defineProperty(d,"__esModule",{value:!0}),d.default={data:function(){var t=this;return{temp_value:function(){return t.value}(),s_autofocus:function(){if(t.autofocus)return"autofocus"}()}},created:function(){},props:{fullHeight:{type:Boolean,default:!1},autofocus:{type:Boolean,default:!1},value:{type:String,default:""},placeholder:{type:String,default:""},border:{type:Boolean,default:!1},resize:{type:Boolean,default:!1},onchange:{type:Function,default:null},fontSize:{type:String,default:"14px"},lineHeight:{type:String,default:"18px"}},methods:{change:function(t){this.onchange&&this.onchange(this.temp_value,t)}},watch:{value:function(t,r){this.temp_value=t},temp_value:function(t,r){this.$emit("input",t)}}}},function(c,d,i){Object.defineProperty(d,"__esModule",{value:!0}),d.default={name:"s-md-toolbar-left",props:{editable:{type:Boolean,default:!0},transition:{type:Boolean,default:!0},toolbars:{type:Object,required:!0},d_words:{type:Object,required:!0},image_filter:{type:Function,default:null}},data:function(){return{img_file:[[0,null]],img_timer:null,header_timer:null,s_img_dropdown_open:!1,s_header_dropdown_open:!1,s_img_link_open:!1,trigger:null,num:0,link_text:"",link_addr:"",link_type:"link"}},methods:{$imgLinkAdd:function(){this.$emit("toolbar_left_addlink",this.link_type,this.link_text,this.link_addr),this.s_img_link_open=!1},$toggle_imgLinkAdd:function(t){var r=this;this.link_type=t,this.link_text=this.link_addr="",this.s_img_link_open=!0,this.$nextTick(function(){r.$refs.linkTextInput.focus()}),this.s_img_dropdown_open=!1},$imgFileListClick:function(t){this.$emit("imgTouch",this.img_file[t])},$changeUrl:function(t,r){this.img_file[t][0]=r},$imgFileAdd:function(t){this.img_file.push([++this.num,t]),this.$emit("imgAdd",this.num,t),this.s_img_dropdown_open=!1},$imgFilesAdd:function(t){for(var r=typeof this.image_filter=="function",e=0;e<t.length;e++)r&&this.image_filter(t[e])===!0?this.$imgFileAdd(t[e]):!r&&t[e].type.match(/^image\//i)&&this.$imgFileAdd(t[e])},$imgAdd:function(t){this.$imgFilesAdd(t.target.files),t.target.value=""},$imgDel:function(t){this.$emit("imgDel",this.img_file[t]),this.img_file.splice(t,1),this.num--,this.s_img_dropdown_open=!1},isEqualName:function(t,r){return!(!this.img_file[r][1]||this.img_file[r][1].name!=t&&this.img_file[r][1]._name!=t)},$imgDelByFilename:function(t){for(var r=0;this.img_file.length>r;){if(this.img_file[r][1]==t||this.isEqualName(t,r))return this.$imgDel(r),!0;r+=1}return!1},$imgAddByFilename:function(t,r){for(var e=0;e<this.img_file.length;e++)if(this.img_file[e][0]==t)return!1;return this.img_file[0][0]=t,this.img_file[0][1]=r,this.img_file[0][2]=t,this.img_file.unshift(["./"+this.num,null]),this.$emit("imgAdd",this.img_file[1][0],r,!1),!0},$imgAddByUrl:function(t,r){for(var e=0;e<this.img_file.length;e++)if(this.img_file[e][0]==t)return!1;return this.img_file[0][0]=t,this.img_file[0][1]=r,this.img_file.unshift(["./"+this.num,null]),!0},$imgUpdateByFilename:function(t,r){for(var e=0;e<this.img_file.length;e++)if(this.img_file[e][0]==t||this.isEqualName(t,e))return this.img_file[e][1]=r,this.$emit("imgAdd",t,r,!1),!0;return!1},$mouseenter_img_dropdown:function(){this.editable&&(clearTimeout(this.img_timer),this.s_img_dropdown_open=!0)},$mouseleave_img_dropdown:function(){var t=this;this.img_timer=setTimeout(function(){t.s_img_dropdown_open=!1},200)},$mouseenter_header_dropdown:function(){this.editable&&(clearTimeout(this.header_timer),this.s_header_dropdown_open=!0)},$mouseleave_header_dropdown:function(){var t=this;this.header_timer=setTimeout(function(){t.s_header_dropdown_open=!1},200)},$clicks:function(t){this.editable&&this.$emit("toolbar_left_click",t)},$click_header:function(t){this.$emit("toolbar_left_click",t),this.s_header_dropdown_open=!1},handleClose:function(t){this.s_img_dropdown_open=!1}}}},function(c,d,i){Object.defineProperty(d,"__esModule",{value:!0}),d.default={name:"s-md-toolbar-right",props:{s_subfield:{type:Boolean,required:!0},toolbars:{type:Object,required:!0},s_preview_switch:{type:Boolean,required:!0},s_fullScreen:{type:Boolean,required:!0},s_html_code:{type:Boolean,required:!0},s_navigation:{type:Boolean,required:!0},d_words:{type:Object,required:!0}},methods:{$clicks:function(t){this.$emit("toolbar_right_click",t)}}}},function(module,__webpack_exports__,__webpack_require__){Object.defineProperty(__webpack_exports__,"__esModule",{value:!0});var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_typeof__=__webpack_require__(39),__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_typeof___default=__webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_typeof__),__WEBPACK_IMPORTED_MODULE_1_auto_textarea__=__webpack_require__(64);__webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_auto_textarea__);var __WEBPACK_IMPORTED_MODULE_2__lib_core_keydown_listen_js__=__webpack_require__(73),__WEBPACK_IMPORTED_MODULE_3__lib_core_hljs_lang_hljs_css_js__=__webpack_require__(72);__webpack_require__(38);var __WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__=__webpack_require__(18),__WEBPACK_IMPORTED_MODULE_6__lib_util_js__=__webpack_require__(77),__WEBPACK_IMPORTED_MODULE_7__lib_toolbar_left_click_js__=__webpack_require__(75),__WEBPACK_IMPORTED_MODULE_8__lib_toolbar_right_click_js__=__webpack_require__(76),__WEBPACK_IMPORTED_MODULE_9__lib_config_js__=__webpack_require__(70);__webpack_require__(71);var __WEBPACK_IMPORTED_MODULE_11__lib_mixins_markdown_js__=__webpack_require__(74),__WEBPACK_IMPORTED_MODULE_12__components_md_toolbar_left_vue__=__webpack_require__(36),__WEBPACK_IMPORTED_MODULE_12__components_md_toolbar_left_vue___default=__webpack_require__.n(__WEBPACK_IMPORTED_MODULE_12__components_md_toolbar_left_vue__),__WEBPACK_IMPORTED_MODULE_13__components_md_toolbar_right_vue__=__webpack_require__(37),__WEBPACK_IMPORTED_MODULE_13__components_md_toolbar_right_vue___default=__webpack_require__.n(__WEBPACK_IMPORTED_MODULE_13__components_md_toolbar_right_vue__),__WEBPACK_IMPORTED_MODULE_14__lib_font_css_fontello_css__=__webpack_require__(114);__webpack_require__.n(__WEBPACK_IMPORTED_MODULE_14__lib_font_css_fontello_css__);var __WEBPACK_IMPORTED_MODULE_15__lib_css_md_css__=__webpack_require__(113);__webpack_require__.n(__WEBPACK_IMPORTED_MODULE_15__lib_css_md_css__);var xss=__webpack_require__(209);__webpack_exports__.default={mixins:[__WEBPACK_IMPORTED_MODULE_11__lib_mixins_markdown_js__.a],props:{scrollStyle:{type:Boolean,default:!0},boxShadow:{type:Boolean,default:!0},transition:{type:Boolean,default:!0},autofocus:{type:Boolean,default:!0},fontSize:{type:String,default:"14px"},toolbarsBackground:{type:String,default:"#ffffff"},editorBackground:{type:String,default:"#ffffff"},previewBackground:{type:String,default:"#fbfbfb"},boxShadowStyle:{type:String,default:"0 2px 12px 0 rgba(0, 0, 0, 0.1)"},help:{type:String,default:null},value:{type:String,default:""},language:{type:String,default:"zh-CN"},subfield:{type:Boolean,default:!0},navigation:{type:Boolean,default:!1},defaultOpen:{type:String,default:null},editable:{type:Boolean,default:!0},toolbarsFlag:{type:Boolean,default:!0},toolbars:{type:Object,default:function(){return __WEBPACK_IMPORTED_MODULE_9__lib_config_js__.a.toolbars}},xssOptions:{type:Object,default:function(){return null}},codeStyle:{type:String,default:function(){return"github"}},placeholder:{type:String,default:null},ishljs:{type:Boolean,default:!0},externalLink:{type:[Object,Boolean],default:!0},imageFilter:{type:Function,default:null},imageClick:{type:Function,default:null},tabSize:{type:Number,default:0},shortCut:{type:Boolean,default:!0}},data:function(){var c=this;return{s_right_click_menu_show:!1,right_click_menu_top:0,right_click_menu_left:0,s_subfield:function(){return c.subfield}(),s_autofocus:!0,s_navigation:function(){return c.navigation}(),s_scrollStyle:function(){return c.scrollStyle}(),d_value:"",d_render:"",s_preview_switch:function(){var d=c.defaultOpen;return d||(d=c.subfield?"preview":"edit"),d==="preview"}(),s_fullScreen:!1,s_help:!1,s_html_code:!1,d_help:null,d_words:null,edit_scroll_height:-1,s_readmodel:!1,s_table_enter:!1,d_history:function(){var d=[];return d.push(c.value),d}(),d_history_index:0,currentTimeout:"",d_image_file:[],d_preview_imgsrc:null,s_external_link:{markdown_css:function(){return"https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/2.9.0/github-markdown.min.css"},hljs_js:function(){return"https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"},hljs_lang:function(d){return"https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/languages/"+d+".min.js"},hljs_css:function(d){return __WEBPACK_IMPORTED_MODULE_3__lib_core_hljs_lang_hljs_css_js__.a[d]?"https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/"+d+".min.css":""},katex_js:function(){return"https://cdnjs.cloudflare.com/ajax/libs/KaTeX/0.8.3/katex.min.js"},katex_css:function(){return"https://cdnjs.cloudflare.com/ajax/libs/KaTeX/0.8.3/katex.min.css"}},p_external_link:{},textarea_selectionEnd:0,textarea_selectionEnds:[0]}},created:function(){var c=this;this.initLanguage(),this.initExternalFuc(),this.$nextTick(function(){c.editableTextarea()})},mounted:function(){var c=this;this.$el.addEventListener("paste",function(d){c.$paste(d)}),this.$el.addEventListener("drop",function(d){c.$drag(d)}),__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_2__lib_core_keydown_listen_js__.a)(this),__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.a)(this),this.autofocus&&this.getTextareaDom().focus(),__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.b)(this),this.d_value=this.value||"",document.body.appendChild(this.$refs.help),this.loadExternalLink("markdown_css","css"),this.loadExternalLink("katex_css","css"),this.loadExternalLink("katex_js","js",function(){c.iRender(!0)}),this.loadExternalLink("hljs_js","js",function(){c.iRender(!0)}),__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_typeof___default()(c.externalLink)==="object"&&typeof c.externalLink.markdown_css=="function"||c.codeStyleChange(c.codeStyle,!0)},beforeDestroy:function(){document.body.removeChild(this.$refs.help)},getMarkdownIt:function(){return this.mixins[0].data().markdownIt},methods:{loadExternalLink:function(c,d,i){if(typeof this.p_external_link[c]!="function")return void(this.p_external_link[c]!=0&&console.error("external_link."+c,"is not a function, if you want to disabled this error log, set external_link."+c,"to function or false"));var t={css:__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.c,js:__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.d};t.hasOwnProperty(d)&&t[d](this.p_external_link[c](),i)},initExternalFuc:function(){for(var c=this,d=["markdown_css","hljs_js","hljs_css","hljs_lang","katex_js","katex_css"],i=__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_typeof___default()(c.externalLink),t=i==="object",r=i==="boolean",e=0;e<d.length;e++)r&&!c.externalLink||t&&c.externalLink[d[e]]===!1?c.p_external_link[d[e]]=!1:t&&typeof c.externalLink[d[e]]=="function"?c.p_external_link[d[e]]=c.externalLink[d[e]]:c.p_external_link[d[e]]=c.s_external_link[d[e]]},textAreaFocus:function(){this.$refs.vNoteTextarea.$refs.vTextarea.focus()},$drag:function(c){var d=c.dataTransfer;if(d){var i=d.files;i.length>0&&(c.preventDefault(),this.$refs.toolbar_left.$imgFilesAdd(i))}},$paste:function(c){var d=c.clipboardData;if(d){var i=d.items;if(!i)return;for(var t=d.types||[],r=null,e=0;e<t.length;e++)if(t[e]==="Files"){r=i[e];break}if(r&&r.kind==="file"){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_6__lib_util_js__.a)(c);var o=r.getAsFile();this.$refs.toolbar_left.$imgFilesAdd([o])}}},$imgTouch:function(c){},$imgDel:function(c){this.markdownIt.image_del(c[1]);var d=c[0],i=new RegExp("\\!\\["+c[1]._name+"\\]\\("+d+"\\)","g");this.d_value=this.d_value.replace(i,""),this.iRender(),this.$emit("imgDel",c)},$imgAdd:function(c,d,i){i===void 0&&(i=!0);var t=this;if(this.__rFilter==null&&(this.__rFilter=/^image\//i),this.__oFReader=new FileReader,this.__oFReader.onload=function(e){t.markdownIt.image_add(c,e.target.result),d.miniurl=e.target.result,i===!0&&(d._name=d.name.replace(/[\[\]\(\)\+\{\}&\|\\\*^%$#@\-]/g,""),t.insertText(t.getTextareaDom(),{prefix:"!["+d._name+"]("+c+")",subfix:"",str:""}),t.$nextTick(function(){t.$emit("imgAdd",c,d)}))},d){var r=d;this.__rFilter.test(r.type)&&this.__oFReader.readAsDataURL(r)}},$imgUpdateByUrl:function(c,d){var i=this;this.markdownIt.image_add(c,d),this.$nextTick(function(){i.d_render=this.markdownIt.render(this.d_value)})},$imgAddByUrl:function(c,d){return!!this.$refs.toolbar_left.$imgAddByUrl(c,d)&&(this.$imgUpdateByUrl(c,d),!0)},$img2Url:function $img2Url(fileIndex,url){var reg_str="/(!\\[[^\\[]*?\\](?=\\())\\(\\s*("+fileIndex+")\\s*\\)/g",reg=eval(reg_str);this.d_value=this.d_value.replace(reg,"$1("+url+")"),this.$refs.toolbar_left.$changeUrl(fileIndex,url),this.iRender()},$imglst2Url:function(c){if(c instanceof Array)for(var d=0;d<c.length;d++)this.$img2Url(c[d][0],c[d][1])},toolbar_left_click:function(c){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_7__lib_toolbar_left_click_js__.a)(c,this)},toolbar_left_addlink:function(c,d,i){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_7__lib_toolbar_left_click_js__.b)(c,d,i,this)},toolbar_right_click:function(c){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_8__lib_toolbar_right_click_js__.a)(c,this)},getNavigation:function(c,d){return __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.e)(c,d)},change:function(c,d){this.$emit("change",c,d)},fullscreen:function(c,d){this.$emit("fullScreen",c,d)},readmodel:function(c,d){this.$emit("readModel",c,d)},previewtoggle:function(c,d){this.$emit("previewToggle",c,d)},subfieldtoggle:function(c,d){this.$emit("subfieldToggle",c,d)},htmlcode:function(c,d){this.$emit("htmlCode",c,d)},helptoggle:function(c,d){this.$emit("helpToggle",c,d)},save:function(c,d){this.$emit("save",c,d)},navigationtoggle:function(c,d){this.$emit("navigationToggle",c,d)},$toolbar_right_read_change_status:function(){this.s_readmodel=!this.s_readmodel,this.readmodel&&this.readmodel(this.s_readmodel,this.d_value),this.s_readmodel&&this.toolbars.navigation&&this.getNavigation(this,!0)},$v_edit_scroll:function(c){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.f)(c,this)},getTextareaDom:function(){return this.$refs.vNoteTextarea.$refs.vTextarea},insertText:function(c,d){var i=d.prefix,t=d.subfix,r=d.str,e=d.type;__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.g)(c,{prefix:i,subfix:t,str:r,type:e},this)},insertTab:function(){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.h)(this,this.tabSize)},insertOl:function(){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.i)(this)},removeLine:function(){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.j)(this)},insertUl:function(){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.k)(this)},unInsertTab:function(){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.l)(this,this.tabSize)},insertEnter:function(c){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.m)(this,c)},saveHistory:function(){this.d_history.splice(this.d_history_index+1,this.d_history.length),this.d_history.push(this.d_value),this.textarea_selectionEnds.splice(this.d_history_index+1,this.textarea_selectionEnds.length),this.textarea_selectionEnds.push(this.textarea_selectionEnd),this.d_history_index=this.d_history.length-1},saveSelectionEndsHistory:function(){var c=this.$refs.vNoteTextarea&&this.$refs.vNoteTextarea.$el.querySelector("textarea");this.textarea_selectionEnd=c?c.selectionEnd:this.textarea_selectionEnd},initLanguage:function(){var c=__WEBPACK_IMPORTED_MODULE_9__lib_config_js__.a.langList.indexOf(this.language)>=0?this.language:"zh-CN",d=this;d.$render(__WEBPACK_IMPORTED_MODULE_9__lib_config_js__.a["help_"+c],function(i){d.d_help=i}),this.d_words=__WEBPACK_IMPORTED_MODULE_9__lib_config_js__.a["words_"+c]},editableTextarea:function(){var c=this.$refs.vNoteTextarea.$refs.vTextarea;this.editable?c.removeAttribute("disabled"):c.setAttribute("disabled","disabled")},codeStyleChange:function(c,d){if(d=d||!1,typeof this.p_external_link.hljs_css!="function")return void(this.p_external_link.hljs_css!=0&&console.error("external_link.hljs_css is not a function, if you want to disabled this error log, set external_link.hljs_css to function or false"));var i=this.p_external_link.hljs_css(c);i.length===0&&d&&(console.warn("hljs color scheme",c,"do not exist, loading default github"),i=this.p_external_link.hljs_css("github")),i.length>0?__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.c)(i,null,"md-code-style"):console.warn("hljs color scheme",c,"do not exist, hljs color scheme will not change")},iRender:function(c){var d=this;this.$render(d.d_value,function(i){d.d_render=i,c||d.change&&d.change(d.d_value,d.d_render),d.s_navigation&&__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.e)(d,!1),d.$emit("input",d.d_value),d.d_value!==d.d_history[d.d_history_index]&&(window.clearTimeout(d.currentTimeout),d.currentTimeout=setTimeout(function(){d.saveHistory()},500))})},$emptyHistory:function(){this.d_history=[this.d_value],this.d_history_index=0}},watch:{d_value:function(c,d){this.saveSelectionEndsHistory(),this.iRender()},value:function(c,d){this.xssOptions&&(c=xss(c,this.xssOptions)),c!==this.d_value&&(this.d_value=c)},subfield:function(c,d){this.s_subfield=c},d_history_index:function(){this.d_history_index>20&&(this.d_history.shift(),this.d_history_index=this.d_history_index-1),this.d_value=this.d_history[this.d_history_index]},language:function(c){this.initLanguage()},editable:function(){this.editableTextarea()},defaultOpen:function(c){var d=c;return d||(d=this.subfield?"preview":"edit"),this.s_preview_switch=d==="preview"},codeStyle:function(c){this.codeStyleChange(c)}},components:{"v-autoTextarea":__WEBPACK_IMPORTED_MODULE_1_auto_textarea__.autoTextarea,"v-md-toolbar-left":__WEBPACK_IMPORTED_MODULE_12__components_md_toolbar_left_vue___default.a,"v-md-toolbar-right":__WEBPACK_IMPORTED_MODULE_13__components_md_toolbar_right_vue___default.a}}},function(c,d,i){var t=i(63),r={markdownIt:t.mixins[0].data().markdownIt,mavonEditor:t,LeftToolbar:i(36),RightToolbar:i(37),install:function(e){e.component("mavon-editor",t)}};c.exports=r},function(c,d,i){i.d(d,"a",function(){return W});var t=i(193),r=i.n(t),e=i(194),o=i.n(e),n=i(188),s=i.n(n),l=i(189),u=i.n(l),a=i(191),p=i.n(a),_=i(192),v=i.n(_),g=i(187),C=i.n(g),m=i(190),h=i.n(m),w=i(217),f=i.n(w),k=i(218),b=i.n(k),y=i(212),x=i.n(y),E=i(213),D=i.n(E),T=i(215),L=i.n(T),A=i(216),$=i.n(A),z=i(211),j=i.n(z),M=i(214),R=i.n(M),W={"help_zh-CN":r.a,"help_zh-TW":o.a,"help_pt-BR":p.a,help_en:s.a,help_fr:u.a,help_ru:v.a,help_de:C.a,help_ja:h.a,"words_zh-CN":f.a,"words_zh-TW":b.a,"words_pt-BR":L.a,words_en:x.a,words_fr:D.a,words_ru:$.a,words_de:j.a,words_ja:R.a,langList:["en","zh-CN","zh-TW","fr","pt-BR","ru","de","ja"],toolbars:{bold:!0,italic:!0,header:!0,underline:!0,strikethrough:!0,mark:!0,superscript:!0,subscript:!0,quote:!0,ol:!0,ul:!0,link:!0,imagelink:!0,code:!0,table:!0,undo:!0,redo:!0,trash:!0,save:!0,alignleft:!0,aligncenter:!0,alignright:!0,navigation:!0,subfield:!0,fullscreen:!0,readmodel:!0,htmlcode:!0,help:!0,preview:!0}}},function(c,d,i){i(18)},function(c,d,i){d.a={agate:1,androidstudio:1,"arduino-light":1,arta:1,ascetic:1,"atelier-cave-dark":1,"atelier-cave-light":1,"atelier-dune-dark":1,"atelier-dune-light":1,"atelier-estuary-dark":1,"atelier-estuary-light":1,"atelier-forest-dark":1,"atelier-forest-light":1,"atelier-heath-dark":1,"atelier-heath-light":1,"atelier-lakeside-dark":1,"atelier-lakeside-light":1,"atelier-plateau-dark":1,"atelier-plateau-light":1,"atelier-savanna-dark":1,"atelier-savanna-light":1,"atelier-seaside-dark":1,"atelier-seaside-light":1,"atelier-sulphurpool-dark":1,"atelier-sulphurpool-light":1,"atom-one-dark":1,"atom-one-light":1,"brown-paper":1,"codepen-embed":1,"color-brewer":1,darcula:1,dark:1,darkula:1,default:1,docco:1,dracula:1,far:1,foundation:1,"github-gist":1,github:1,googlecode:1,grayscale:1,"gruvbox-dark":1,"gruvbox-light":1,hopscotch:1,hybrid:1,idea:1,"ir-black":1,"kimbie.dark":1,"kimbie.light":1,magula:1,"mono-blue":1,"monokai-sublime":1,monokai:1,obsidian:1,ocean:1,"paraiso-dark":1,"paraiso-light":1,pojoaque:1,purebasic:1,qtcreator_dark:1,qtcreator_light:1,railscasts:1,rainbow:1,routeros:1,"school-book":1,"solarized-dark":1,"solarized-light":1,sunburst:1,"tomorrow-night-blue":1,"tomorrow-night-bright":1,"tomorrow-night-eighties":1,"tomorrow-night":1,tomorrow:1,vs:1,vs2015:1,xcode:1,xt256:1,zenburn:1}},function(c,d,i){i.d(d,"a",function(){return r});var t={F8:119,F9:120,F10:121,F11:122,F12:123,B:66,I:73,H:72,U:85,D:68,M:77,Q:81,O:79,L:76,S:83,Z:90,Y:89,C:67,T:84,R:82,DELETE:8,TAB:9,ENTER:13,ONE:97,TWO:98,THREE:99,FOUR:100,FIVE:101,SIX:102,_ONE:49,_TWO:50,_THREE:51,_FOUR:52,_FIVE:53,_SIX:54},r=function(e){e.shortCut&&e.$el.addEventListener("keydown",function(o){if(o.ctrlKey||o.metaKey||o.altKey||o.shiftKey)if(!o.ctrlKey&&!o.metaKey||o.altKey||o.shiftKey){if((o.ctrlKey||o.metaKey)&&o.altKey&&!o.shiftKey)switch(o.keyCode){case t.S:o.preventDefault(),e.toolbar_left_click("superscript");break;case t.U:o.preventDefault(),e.toolbar_left_click("ul");break;case t.L:o.preventDefault(),e.toolbar_left_click("imagelink");break;case t.C:o.preventDefault(),e.toolbar_left_click("code");break;case t.T:o.preventDefault(),e.toolbar_left_click("table")}else if((o.ctrlKey||o.metaKey)&&o.shiftKey&&!o.altKey)switch(o.keyCode){case t.S:o.preventDefault(),e.toolbar_left_click("subscript");break;case t.D:o.preventDefault(),e.toolbar_left_click("strikethrough");break;case t.L:o.preventDefault(),e.toolbar_left_click("alignleft");break;case t.R:o.preventDefault(),e.toolbar_left_click("alignright");break;case t.C:o.preventDefault(),e.toolbar_left_click("aligncenter")}else if(!o.ctrlKey&&!o.metaKey&&o.shiftKey&&!o.altKey)switch(o.keyCode){case t.TAB:e.$refs.toolbar_left.s_img_link_open||(o.preventDefault(),e.unInsertTab())}}else switch(o.keyCode){case t.B:o.preventDefault(),e.toolbar_left_click("bold");break;case t.I:o.preventDefault(),e.toolbar_left_click("italic");break;case t.H:o.preventDefault(),e.toolbar_left_click("header");break;case t.U:o.preventDefault(),e.toolbar_left_click("underline");break;case t.D:o.preventDefault(),e.toolbar_left_click("removeLine");break;case t.M:o.preventDefault(),e.toolbar_left_click("mark");break;case t.Q:o.preventDefault(),e.toolbar_left_click("quote");break;case t.O:o.preventDefault(),e.toolbar_left_click("ol");break;case t.L:o.preventDefault(),e.toolbar_left_click("link");break;case t.S:o.preventDefault(),e.toolbar_left_click("save");break;case t.Z:o.preventDefault(),e.toolbar_left_click("undo");break;case t.Y:o.preventDefault(),e.toolbar_left_click("redo");break;case t.DELETE:o.preventDefault(),e.toolbar_left_click("trash");break;case t.ONE:o.preventDefault(),e.toolbar_left_click("header1");break;case t.TWO:o.preventDefault(),e.toolbar_left_click("header2");break;case t.THREE:o.preventDefault(),e.toolbar_left_click("header3");break;case t.FOUR:o.preventDefault(),e.toolbar_left_click("header4");break;case t.FIVE:o.preventDefault(),e.toolbar_left_click("header5");break;case t.SIX:o.preventDefault(),e.toolbar_left_click("header6");break;case t._ONE:o.preventDefault(),e.toolbar_left_click("header1");break;case t._TWO:o.preventDefault(),e.toolbar_left_click("header2");break;case t._THREE:o.preventDefault(),e.toolbar_left_click("header3");break;case t._FOUR:o.preventDefault(),e.toolbar_left_click("header4");break;case t._FIVE:o.preventDefault(),e.toolbar_left_click("header5");break;case t._SIX:o.preventDefault(),e.toolbar_left_click("header6")}else switch(o.keyCode){case t.F8:e.toolbars.navigation&&(o.preventDefault(),e.toolbar_right_click("navigation"));break;case t.F9:e.toolbars.preview&&(o.preventDefault(),e.toolbar_right_click("preview"));break;case t.F10:e.toolbars.fullscreen&&(o.preventDefault(),e.toolbar_right_click("fullscreen"));break;case t.F11:e.toolbars.readmodel&&(o.preventDefault(),e.toolbar_right_click("read"));break;case t.F12:e.toolbars.subfield&&(o.preventDefault(),e.toolbar_right_click("subfield"));break;case t.TAB:e.$refs.toolbar_left.s_img_link_open||(o.preventDefault(),e.insertTab());break;case t.ENTER:e.$refs.toolbar_left.s_img_link_open?(o.preventDefault(),e.$refs.toolbar_left.$imgLinkAdd()):e.insertEnter(o)}})}},function(c,d,i){var t=i(38),r=i(18),e={html:!0,xhtmlOut:!0,breaks:!0,langPrefix:"lang-",linkify:!1,typographer:!0,quotes:"\u201C\u201D\u2018\u2019"},o=i(136)(e),n=i(120),s=i(132),l=i(133),u=i(119),a=i(117),p=i(126),_=i(129),v=i(131),g=i(134),C=i(118),m=i(135),h=o.renderer.rules.link_open||function(E,D,T,L,A){return A.renderToken(E,D,T)};o.renderer.rules.link_open=function(E,D,T,L,A){var $=E[D].attrIndex("href");if(E[D].attrs[$][1].startsWith("#"))return h(E,D,T,L,A);var z=E[D].attrIndex("target");return z<0?E[D].attrPush(["target","_blank"]):E[D].attrs[z][1]="_blank",h(E,D,T,L,A)};var w=i(127),f=i(130),k=i(128),b={},y=[],x={hljs:"auto",highlighted:!0,langCheck:function(E){E&&t.a[E]&&!b[E]&&(b[E]=1,y.push(t.a[E]))}};o.use(w,x).use(n).use(l).use(s).use(C).use(C,"hljs-left").use(C,"hljs-center").use(C,"hljs-right").use(u).use(a).use(p).use(_).use(v).use(C).use(k).use(f).use(g).use(m),d.a={data:function(){return{markdownIt:o}},mounted:function(){x.highlighted=this.ishljs},methods:{$render:function(E,D){var T=this;b={},y=[];var L=o.render(E);this.ishljs&&y.length>0&&T.$_render(E,D,L),D(L)},$_render:function(E,D,T){for(var L=this,A=0,$=0;$<y.length;$++){var z=L.p_external_link.hljs_lang(y[$]);i.i(r.d)(z,function(){(A+=1)===y.length&&(T=o.render(E),D(T))})}}},watch:{ishljs:function(E){x.highlighted=E}}}},function(c,d,i){function t(p){p.d_history_index>0&&p.d_history_index--,p.$nextTick(function(){var _=p.textarea_selectionEnds[p.d_history_index];p.getTextareaDom().selectionStart=_,p.getTextareaDom().selectionEnd=_}),p.getTextareaDom().focus()}function r(p){p.d_history_index<p.d_history.length-1&&p.d_history_index++,p.$nextTick(function(){var _=p.textarea_selectionEnds[p.d_history_index];p.getTextareaDom().selectionStart=_,p.getTextareaDom().selectionEnd=_}),p.getTextareaDom().focus()}function e(p){p.d_value="",p.getTextareaDom().focus()}function o(p){p.save(p.d_value,p.d_render)}function n(p){p.insertOl()}function s(p){p.insertUl()}function l(p){p.removeLine()}i.d(d,"b",function(){return u}),i.d(d,"a",function(){return a});var u=function(p,_,v,g){var C={prefix:p==="link"?"["+_+"](":"!["+_+"](",subfix:")",str:v};g.insertText(g.getTextareaDom(),C)},a=function(p,_){var v={bold:{prefix:"**",subfix:"**",str:_.d_words.tl_bold},italic:{prefix:"*",subfix:"*",str:_.d_words.tl_italic},header:{prefix:"# ",subfix:"",str:_.d_words.tl_header},header1:{prefix:"# ",subfix:"",str:_.d_words.tl_header_one},header2:{prefix:"## ",subfix:"",str:_.d_words.tl_header_two},header3:{prefix:"### ",subfix:"",str:_.d_words.tl_header_three},header4:{prefix:"#### ",subfix:"",str:_.d_words.tl_header_four},header5:{prefix:"##### ",subfix:"",str:_.d_words.tl_header_five},header6:{prefix:"###### ",subfix:"",str:_.d_words.tl_header_six},underline:{prefix:"++",subfix:"++",str:_.d_words.tl_underline},strikethrough:{prefix:"~~",subfix:"~~",str:_.d_words.tl_strikethrough},mark:{prefix:"==",subfix:"==",str:_.d_words.tl_mark},superscript:{prefix:"^",subfix:"^",str:_.d_words.tl_superscript},subscript:{prefix:"~",subfix:"~",str:_.d_words.tl_subscript},quote:{prefix:"> ",subfix:"",str:_.d_words.tl_quote},link:{prefix:"[](",subfix:")",str:_.d_words.tl_link},imagelink:{prefix:"![](",subfix:")",str:_.d_words.tl_image},code:{prefix:"```\n",subfix:"\n\n```\n",str:"language"},table:{prefix:"",subfix:"",str:`|column1|column2|column3|
|-|-|-|
|content1|content2|content3|
`},aligncenter:{prefix:`::: hljs-center

`,subfix:`

:::
`,str:_.d_words.tl_aligncenter},alignright:{prefix:`::: hljs-right

`,subfix:`

:::
`,str:_.d_words.tl_alignright},alignleft:{prefix:`::: hljs-left

`,subfix:`

:::
`,str:_.d_words.tl_alignleft}};v.hasOwnProperty(p)&&_.insertText(_.getTextareaDom(),v[p]);var g={undo:t,redo:r,trash:e,save:o,ol:n,ul:s,removeLine:l};g.hasOwnProperty(p)&&g[p](_)}},function(c,d,i){function t(a){a.s_html_code=!a.s_html_code,a.htmlcode&&a.htmlcode(a.s_html_code,a.d_value)}function r(a){a.s_help=!a.s_help,a.helptoggle&&a.helptoggle(a.s_help,a.d_value)}function e(a){var p=a.$refs.vReadModel;p.requestFullscreen?p.requestFullscreen():p.mozRequestFullScreen?p.mozRequestFullScreen():p.webkitRequestFullscreen?p.webkitRequestFullscreen():p.msRequestFullscreen&&p.msRequestFullscreen()}function o(a){a.s_preview_switch=!a.s_preview_switch,a.previewtoggle&&a.previewtoggle(a.s_preview_switch,a.d_value)}function n(a){a.s_fullScreen=!a.s_fullScreen,a.fullscreen&&a.fullscreen(a.s_fullScreen,a.d_value)}function s(a){a.s_subfield=!a.s_subfield,a.s_preview_switch=a.s_subfield,a.previewtoggle&&a.previewtoggle(a.s_preview_switch,a.d_value),a.subfieldtoggle&&a.subfieldtoggle(a.s_subfield,a.d_value)}function l(a){a.s_navigation=!a.s_navigation,a.s_navigation&&(a.s_preview_switch=!0),a.navigationtoggle&&a.navigationtoggle(a.s_navigation,a.d_value),a.s_navigation&&a.getNavigation(a,!1)}i.d(d,"a",function(){return u});var u=function(a,p){var _={help:r,html:t,read:e,preview:o,fullscreen:n,navigation:l,subfield:s};_.hasOwnProperty(a)&&_[a](p)}},function(c,d,i){function t(e){e&&(e.preventDefault&&e.preventDefault(),e.stopPropagation&&e.stopPropagation())}d.a=t;var r=i(39);i.n(r)},function(c,d,i){c.exports={default:i(80),__esModule:!0}},function(c,d,i){c.exports={default:i(81),__esModule:!0}},function(c,d,i){i(103),i(101),i(104),i(105),c.exports=i(10).Symbol},function(c,d,i){i(102),i(106),c.exports=i(30).f("iterator")},function(c,d){c.exports=function(i){if(typeof i!="function")throw TypeError(i+" is not a function!");return i}},function(c,d){c.exports=function(){}},function(c,d,i){var t=i(6),r=i(99),e=i(98);c.exports=function(o){return function(n,s,l){var u,a=t(n),p=r(a.length),_=e(l,p);if(o&&s!=s){for(;p>_;)if((u=a[_++])!=u)return!0}else for(;p>_;_++)if((o||_ in a)&&a[_]===s)return o||_||0;return!o&&-1}}},function(c,d,i){var t=i(82);c.exports=function(r,e,o){if(t(r),e===void 0)return r;switch(o){case 1:return function(n){return r.call(e,n)};case 2:return function(n,s){return r.call(e,n,s)};case 3:return function(n,s,l){return r.call(e,n,s,l)}}return function(){return r.apply(e,arguments)}}},function(c,d,i){var t=i(22),r=i(47),e=i(23);c.exports=function(o){var n=t(o),s=r.f;if(s)for(var l,u=s(o),a=e.f,p=0;u.length>p;)a.call(o,l=u[p++])&&n.push(l);return n}},function(c,d,i){var t=i(1).document;c.exports=t&&t.documentElement},function(c,d,i){var t=i(40);c.exports=Object("z").propertyIsEnumerable(0)?Object:function(r){return t(r)=="String"?r.split(""):Object(r)}},function(c,d,i){var t=i(40);c.exports=Array.isArray||function(r){return t(r)=="Array"}},function(c,d,i){var t=i(45),r=i(13),e=i(24),o={};i(4)(o,i(7)("iterator"),function(){return this}),c.exports=function(n,s,l){n.prototype=t(o,{next:r(1,l)}),e(n,s+" Iterator")}},function(c,d){c.exports=function(i,t){return{value:t,done:!!i}}},function(c,d,i){var t=i(14)("meta"),r=i(8),e=i(2),o=i(5).f,n=0,s=Object.isExtensible||function(){return!0},l=!i(11)(function(){return s(Object.preventExtensions({}))}),u=function(g){o(g,t,{value:{i:"O"+ ++n,w:{}}})},a=function(g,C){if(!r(g))return typeof g=="symbol"?g:(typeof g=="string"?"S":"P")+g;if(!e(g,t)){if(!s(g))return"F";if(!C)return"E";u(g)}return g[t].i},p=function(g,C){if(!e(g,t)){if(!s(g))return!0;if(!C)return!1;u(g)}return g[t].w},_=function(g){return l&&v.NEED&&s(g)&&!e(g,t)&&u(g),g},v=c.exports={KEY:t,NEED:!1,fastKey:a,getWeak:p,onFreeze:_}},function(c,d,i){var t=i(5),r=i(9),e=i(22);c.exports=i(3)?Object.defineProperties:function(o,n){r(o);for(var s,l=e(n),u=l.length,a=0;u>a;)t.f(o,s=l[a++],n[s]);return o}},function(c,d,i){var t=i(23),r=i(13),e=i(6),o=i(28),n=i(2),s=i(43),l=Object.getOwnPropertyDescriptor;d.f=i(3)?l:function(u,a){if(u=e(u),a=o(a,!0),s)try{return l(u,a)}catch{}if(n(u,a))return r(!t.f.call(u,a),u[a])}},function(c,d,i){var t=i(6),r=i(46).f,e={}.toString,o=typeof window=="object"&&window&&Object.getOwnPropertyNames?Object.getOwnPropertyNames(window):[],n=function(s){try{return r(s)}catch{return o.slice()}};c.exports.f=function(s){return o&&e.call(s)=="[object Window]"?n(s):r(t(s))}},function(c,d,i){var t=i(2),r=i(50),e=i(25)("IE_PROTO"),o=Object.prototype;c.exports=Object.getPrototypeOf||function(n){return n=r(n),t(n,e)?n[e]:typeof n.constructor=="function"&&n instanceof n.constructor?n.constructor.prototype:n instanceof Object?o:null}},function(c,d,i){var t=i(27),r=i(19);c.exports=function(e){return function(o,n){var s,l,u=String(r(o)),a=t(n),p=u.length;return a<0||a>=p?e?"":void 0:(s=u.charCodeAt(a),s<55296||s>56319||a+1===p||(l=u.charCodeAt(a+1))<56320||l>57343?e?u.charAt(a):s:e?u.slice(a,a+2):l-56320+(s-55296<<10)+65536)}}},function(c,d,i){var t=i(27),r=Math.max,e=Math.min;c.exports=function(o,n){return o=t(o),o<0?r(o+n,0):e(o,n)}},function(c,d,i){var t=i(27),r=Math.min;c.exports=function(e){return e>0?r(t(e),9007199254740991):0}},function(c,d,i){var t=i(83),r=i(91),e=i(21),o=i(6);c.exports=i(44)(Array,"Array",function(n,s){this._t=o(n),this._i=0,this._k=s},function(){var n=this._t,s=this._k,l=this._i++;return!n||l>=n.length?(this._t=void 0,r(1)):s=="keys"?r(0,l):s=="values"?r(0,n[l]):r(0,[l,n[l]])},"values"),e.Arguments=e.Array,t("keys"),t("values"),t("entries")},function(c,d){},function(c,d,i){var t=i(97)(!0);i(44)(String,"String",function(r){this._t=String(r),this._i=0},function(){var r,e=this._t,o=this._i;return o>=e.length?{value:void 0,done:!0}:(r=t(e,o),this._i+=r.length,{value:r,done:!1})})},function(c,d,i){var t=i(1),r=i(2),e=i(3),o=i(42),n=i(49),s=i(92).KEY,l=i(11),u=i(26),a=i(24),p=i(14),_=i(7),v=i(30),g=i(29),C=i(86),m=i(89),h=i(9),w=i(8),f=i(50),k=i(6),b=i(28),y=i(13),x=i(45),E=i(95),D=i(94),T=i(47),L=i(5),A=i(22),$=D.f,z=L.f,j=E.f,M=t.Symbol,R=t.JSON,W=R&&R.stringify,S=_("_hidden"),O=_("toPrimitive"),q={}.propertyIsEnumerable,I=u("symbol-registry"),B=u("symbols"),U=u("op-symbols"),N=Object.prototype,V=typeof M=="function"&&!!T.f,K=t.QObject,G=!K||!K.prototype||!K.prototype.findChild,Q=e&&l(function(){return x(z({},"a",{get:function(){return z(this,"a",{value:7}).a}})).a!=7})?function(F,P,H){var X=$(N,P);X&&delete N[P],z(F,P,H),X&&F!==N&&z(N,P,X)}:z,J=function(F){var P=B[F]=x(M.prototype);return P._k=F,P},Y=V&&typeof M.iterator=="symbol"?function(F){return typeof F=="symbol"}:function(F){return F instanceof M},Z=function(F,P,H){return F===N&&Z(U,P,H),h(F),P=b(P,!0),h(H),r(B,P)?(H.enumerable?(r(F,S)&&F[S][P]&&(F[S][P]=!1),H=x(H,{enumerable:y(0,!1)})):(r(F,S)||z(F,S,y(1,{})),F[S][P]=!0),Q(F,P,H)):z(F,P,H)},nt=function(F,P){h(F);for(var H,X=C(P=k(P)),tt=0,ot=X.length;ot>tt;)Z(F,H=X[tt++],P[H]);return F},et=function(F,P){return P===void 0?x(F):nt(x(F),P)},rt=function(F){var P=q.call(this,F=b(F,!0));return!(this===N&&r(B,F)&&!r(U,F))&&(!(P||!r(this,F)||!r(B,F)||r(this,S)&&this[S][F])||P)},at=function(F,P){if(F=k(F),P=b(P,!0),F!==N||!r(B,P)||r(U,P)){var H=$(F,P);return!H||!r(B,P)||r(F,S)&&F[S][P]||(H.enumerable=!0),H}},st=function(F){for(var P,H=j(k(F)),X=[],tt=0;H.length>tt;)r(B,P=H[tt++])||P==S||P==s||X.push(P);return X},lt=function(F){for(var P,H=F===N,X=j(H?U:k(F)),tt=[],ot=0;X.length>ot;)!r(B,P=X[ot++])||H&&!r(N,P)||tt.push(B[P]);return tt};V||(M=function(){if(this instanceof M)throw TypeError("Symbol is not a constructor!");var F=p(arguments.length>0?arguments[0]:void 0),P=function(H){this===N&&P.call(U,H),r(this,S)&&r(this[S],F)&&(this[S][F]=!1),Q(this,F,y(1,H))};return e&&G&&Q(N,F,{configurable:!0,set:P}),J(F)},n(M.prototype,"toString",function(){return this._k}),D.f=at,L.f=Z,i(46).f=E.f=st,i(23).f=rt,T.f=lt,e&&!i(12)&&n(N,"propertyIsEnumerable",rt,!0),v.f=function(F){return J(_(F))}),o(o.G+o.W+o.F*!V,{Symbol:M});for(var ct="hasInstance,isConcatSpreadable,iterator,match,replace,search,species,split,toPrimitive,toStringTag,unscopables".split(","),ut=0;ct.length>ut;)_(ct[ut++]);for(var pt=A(_.store),_t=0;pt.length>_t;)g(pt[_t++]);o(o.S+o.F*!V,"Symbol",{for:function(F){return r(I,F+="")?I[F]:I[F]=M(F)},keyFor:function(F){if(!Y(F))throw TypeError(F+" is not a symbol!");for(var P in I)if(I[P]===F)return P},useSetter:function(){G=!0},useSimple:function(){G=!1}}),o(o.S+o.F*!V,"Object",{create:et,defineProperty:Z,defineProperties:nt,getOwnPropertyDescriptor:at,getOwnPropertyNames:st,getOwnPropertySymbols:lt});var dt=l(function(){T.f(1)});o(o.S+o.F*dt,"Object",{getOwnPropertySymbols:function(F){return T.f(f(F))}}),R&&o(o.S+o.F*(!V||l(function(){var F=M();return W([F])!="[null]"||W({a:F})!="{}"||W(Object(F))!="{}"})),"JSON",{stringify:function(F){for(var P,H,X=[F],tt=1;arguments.length>tt;)X.push(arguments[tt++]);if(H=P=X[1],(w(P)||F!==void 0)&&!Y(F))return m(P)||(P=function(ot,it){if(typeof H=="function"&&(it=H.call(this,ot,it)),!Y(it))return it}),X[1]=P,W.apply(R,X)}}),M.prototype[O]||i(4)(M.prototype,O,M.prototype.valueOf),a(M,"Symbol"),a(Math,"Math",!0),a(t.JSON,"JSON",!0)},function(c,d,i){i(29)("asyncIterator")},function(c,d,i){i(29)("observable")},function(c,d,i){i(100);for(var t=i(1),r=i(4),e=i(21),o=i(7)("toStringTag"),n="CSSRuleList,CSSStyleDeclaration,CSSValueList,ClientRectList,DOMRectList,DOMStringList,DOMTokenList,DataTransferItemList,FileList,HTMLAllCollection,HTMLCollection,HTMLFormElement,HTMLSelectElement,MediaList,MimeTypeArray,NamedNodeMap,NodeList,PaintRequestList,Plugin,PluginArray,SVGLengthList,SVGNumberList,SVGPathSegList,SVGPointList,SVGStringList,SVGTransformList,SourceBufferList,StyleSheetList,TextTrackCueList,TextTrackList,TouchList".split(","),s=0;s<n.length;s++){var l=n[s],u=t[l],a=u&&u.prototype;a&&!a[o]&&r(a,o,l),e[l]=e.Array}},function(c,d,i){d=c.exports=i(15)(!1),d.push([c.i,`
.auto-textarea-wrapper {
  position: relative;
  width: 100%;
  margin: 0;
  padding: 0;
  line-height: normal;
}
.auto-textarea-wrapper .auto-textarea-block {
  display: block;
  white-space: pre-wrap;
  word-wrap: break-word !important;
  visibility: hidden;
  overflow: hidden;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-size: 100%;
}
.auto-textarea-wrapper .auto-textarea-input {
  font-family: Menlo, "Ubuntu Mono", Consolas, "Courier New", "Microsoft Yahei", "Hiragino Sans GB", "WenQuanYi Micro Hei", sans-serif;
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  margin: 0;
  padding: 0;
  overflow-y: hidden;
  color: #2c3e50;
}
.auto-textarea-wrapper .auto-textarea-input.no-border {
  outline: 0 none;
  border: none !important;
}
.auto-textarea-wrapper .auto-textarea-input.no-resize {
  resize: none;
}
`,""])},function(c,d,i){d=c.exports=i(15)(!1),d.push([c.i,`
.op-icon.dropdown-wrapper.dropdown[data-v-548e2160] {
  position: relative;
}
.op-icon.dropdown-wrapper.dropdown[type=button][data-v-548e2160] {
  -webkit-appearance: unset;
}
.op-icon.dropdown-wrapper.dropdown .popup-dropdown[data-v-548e2160] {
  position: absolute;
  display: block;
  background: #fff;
  top: 32px;
  left: -45px;
  min-width: 130px;
  z-index: 1600;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}
.op-icon.dropdown-wrapper.dropdown .popup-dropdown .dropdown-item[data-v-548e2160]:first-child {
  border-top-left-radius: 3px;
  border-top-right-radius: 3px;
}
.op-icon.dropdown-wrapper.dropdown .popup-dropdown .dropdown-item[data-v-548e2160]:last-child {
  border-bottom-left-radius: 3px;
  border-bottom-right-radius: 3px;
}
.op-icon.dropdown-wrapper.dropdown .popup-dropdown.op-header[data-v-548e2160] {
  left: -30px;
  min-width: 90px;
}
.op-icon.dropdown-wrapper.dropdown .popup-dropdown.fade-enter-active[data-v-548e2160],
.op-icon.dropdown-wrapper.dropdown .popup-dropdown.fade-leave-active[data-v-548e2160] {
  opacity: 1;
}
.op-icon.dropdown-wrapper.dropdown .popup-dropdown.fade-enter[data-v-548e2160],
.op-icon.dropdown-wrapper.dropdown .popup-dropdown.fade-leave-active[data-v-548e2160] {
  opacity: 0;
}
.op-icon.dropdown-wrapper.dropdown .popup-dropdown.transition[data-v-548e2160],
.op-icon.dropdown-wrapper.dropdown .popup-dropdown.transition .dropdown-item[data-v-548e2160] {
  -webkit-transition: all 0.2s linear 0s;
  transition: all 0.2s linear 0s;
}
.op-icon.dropdown-wrapper.dropdown .dropdown-item[data-v-548e2160] {
  height: 40px;
  line-height: 40px;
  font-size: 14px;
  color: #606266;
  position: relative;
}
.op-icon.dropdown-wrapper.dropdown .dropdown-item[data-v-548e2160]:hover {
  color: #303133;
  background-color: #e9e9eb;
}
.op-icon.dropdown-wrapper.dropdown .dropdown-item input[data-v-548e2160] {
  position: absolute;
  font-size: 100px;
  right: 0;
  top: 0;
  opacity: 0;
  cursor: pointer;
}
.op-icon.dropdown-wrapper.dropdown .dropdown-images[data-v-548e2160] {
  box-sizing: border-box;
}
.op-icon.dropdown-wrapper.dropdown .dropdown-images button[data-v-548e2160] {
  position: absolute;
  top: -1px;
  right: 5px;
  font-size: 14px;
}
.op-icon.dropdown-wrapper.dropdown .dropdown-images button[data-v-548e2160]:hover {
  color: #f56c6c;
  background-color: transparent;
}
.op-icon.dropdown-wrapper.dropdown .dropdown-images span[data-v-548e2160] {
  display: inline-block;
  width: 80px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.op-icon.dropdown-wrapper.dropdown .dropdown-images:hover .image-show[data-v-548e2160] {
  display: block !important;
}
.op-icon.dropdown-wrapper.dropdown .dropdown-images .image-show[data-v-548e2160] {
  display: none;
  position: absolute;
  left: -128px;
  top: 0;
  width: 120px;
  height: 90px;
  object-fit: contain;
  border: 1px solid #f2f6fc;
}
.op-icon.dropdown-wrapper.dropdown .dropdown-images .image-show.transition[data-v-548e2160] {
  -webkit-transition: all 0.2s linear 0s;
  transition: all 0.2s linear 0s;
}
.op-icon.dropdown-wrapper.dropdown .dropdown-images.transition[data-v-548e2160] {
  -webkit-transition: all 0.2s linear 0s;
  transition: all 0.2s linear 0s;
}
.add-image-link-wrapper[data-v-548e2160] {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: rgba(0,0,0,0.7);
  z-index: 1600;
  -webkit-transition: all 0.1s linear 0s;
  transition: all 0.1s linear 0s;
}
.add-image-link-wrapper.fade-enter-active[data-v-548e2160],
.add-image-link-wrapper.fade-leave-active[data-v-548e2160] {
  opacity: 1;
}
.add-image-link-wrapper.fade-enter[data-v-548e2160],
.add-image-link-wrapper.fade-leave-active[data-v-548e2160] {
  opacity: 0;
}
.add-image-link-wrapper .add-image-link[data-v-548e2160] {
  position: fixed;
  box-sizing: border-box;
  text-align: center;
  width: 24%;
  left: 38%;
  height: auto;
  padding: 40px;
  top: 25%;
  -webkit-transition: all 0.1s linear 0s;
  transition: all 0.1s linear 0s;
  z-index: 3;
  background: #fff;
  border-radius: 2px;
}
@media only screen and (max-width: 1500px) {
.add-image-link-wrapper .add-image-link[data-v-548e2160] {
    width: 34%;
    left: 33%;
}
}
@media only screen and (max-width: 1000px) {
.add-image-link-wrapper .add-image-link[data-v-548e2160] {
    width: 50%;
    left: 25%;
}
}
@media only screen and (max-width: 600px) {
.add-image-link-wrapper .add-image-link[data-v-548e2160] {
    width: 80%;
    left: 10%;
}
}
.add-image-link-wrapper .add-image-link i[data-v-548e2160] {
  font-size: 24px;
  position: absolute;
  right: 8px;
  top: 6px;
  color: rgba(0,0,0,0.7);
  cursor: pointer;
}
.add-image-link-wrapper .add-image-link .title[data-v-548e2160] {
  font-size: 20px;
  margin-bottom: 30px;
  margin-top: 10px;
  font-weight: 500 !important;
}
.add-image-link-wrapper .add-image-link .input-wrapper[data-v-548e2160] {
  margin-top: 10px;
  width: 80%;
  border: 1px solid #eeece8;
  text-align: left;
  margin-left: 10%;
  height: 35px;
}
.add-image-link-wrapper .add-image-link .input-wrapper input[data-v-548e2160] {
  height: 32px;
  line-height: 32px;
  font-size: 15px;
  width: 90%;
  margin-left: 8px;
  border: none;
  outline: none;
}
.add-image-link-wrapper .add-image-link .op-btn[data-v-548e2160] {
  width: 100px;
  height: 35px;
  display: inline-block;
  margin-top: 30px;
  cursor: pointer;
  text-align: center;
  line-height: 35px;
  opacity: 0.9;
  border-radius: 2px;
  letter-spacing: 1px;
  font-size: 15px;
}
.add-image-link-wrapper .add-image-link .op-btn.sure[data-v-548e2160] {
  background: #2185d0;
  color: #fff;
  margin-left: 5%;
}
.add-image-link-wrapper .add-image-link .op-btn.sure[data-v-548e2160]:hover {
  opacity: 1;
}
.add-image-link-wrapper .add-image-link .op-btn.cancel[data-v-548e2160] {
  border: 1px solid #bcbcbc;
  color: #bcbcbc;
}
.add-image-link-wrapper .add-image-link .op-btn.cancel[data-v-548e2160]:hover {
  color: #000;
}
`,""])},function(c,d,i){d=c.exports=i(15)(!1),d.push([c.i,`
textarea:disabled {
  background-color: #fff;
}
.v-note-wrapper {
  position: relative;
  min-width: 300px;
  min-height: 300px;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  -webkit-flex-direction: column;
      -ms-flex-direction: column;
          flex-direction: column;
  background-color: #fff;
  z-index: 1500;
  text-align: left;
  border: 1px solid #f2f6fc;
  border-radius: 4px;
}
.v-note-wrapper.fullscreen {
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  top: 0;
  margin: 0;
  height: auto;
  z-index: 1501;
}
.v-note-wrapper .v-note-op {
  padding: 1px;
  width: 100%;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  white-space: pre-line;
  -webkit-box-flex: 0;
  -webkit-flex: none;
      -ms-flex: none;
          flex: none;
  min-height: 40px;
  -webkit-user-select: none;
      -ms-user-select: none;
          user-select: none;
  border-bottom: 1px solid #f2f6fc;
  border-radius: 4px 4px 0 0;
  background-color: #fff;
  z-index: 1;
}
.v-note-wrapper .v-note-op .v-left-item,
.v-note-wrapper .v-note-op .v-right-item {
  -webkit-box-flex: 1;
  -webkit-flex: 1;
      -ms-flex: 1;
          flex: 1;
  min-height: 40px;
  box-sizing: border-box;
}
.v-note-wrapper .v-note-op .v-left-item .op-icon-divider,
.v-note-wrapper .v-note-op .v-right-item .op-icon-divider {
  height: 40px;
  border-left: 1px solid #e5e5e5;
  margin: 0 6px 0 4px;
}
.v-note-wrapper .v-note-op .v-left-item .op-icon,
.v-note-wrapper .v-note-op .v-right-item .op-icon {
  box-sizing: border-box;
  display: inline-block;
  cursor: pointer;
  height: 28px;
  width: 28px;
  margin: 6px 0 5px 0px;
  font-size: 14px;
  padding: 4.5px 6px 5px 3.5px;
  color: #757575;
  border-radius: 5px;
  text-align: center;
  background: none;
  border: none;
  outline: none;
  line-height: 1;
}
.v-note-wrapper .v-note-op .v-left-item .op-icon.dropdown-wrapper,
.v-note-wrapper .v-note-op .v-right-item .op-icon.dropdown-wrapper {
  line-height: 18px;
}
.v-note-wrapper .v-note-op .v-left-item .op-icon.selected,
.v-note-wrapper .v-note-op .v-right-item .op-icon.selected {
  color: rgba(0,0,0,0.8);
  background: #eaeaea;
}
.v-note-wrapper .v-note-op .v-left-item .op-icon:hover,
.v-note-wrapper .v-note-op .v-right-item .op-icon:hover {
  color: rgba(0,0,0,0.8);
  background: #e9e9eb;
}
.v-note-wrapper .v-note-op .v-left-item.transition .op-icon,
.v-note-wrapper .v-note-op .v-right-item.transition .op-icon {
  -webkit-transition: all 0.2s linear 0s;
  transition: all 0.2s linear 0s;
}
.v-note-wrapper .v-note-op .v-right-item {
  text-align: right;
  padding-right: 6px;
  max-width: 30%;
}
.v-note-wrapper .v-note-op .v-left-item {
  text-align: left;
  padding-left: 6px;
}
.v-note-wrapper .v-note-panel {
  position: relative;
  border-top: none;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-flex: 1;
  -webkit-flex: 1;
      -ms-flex: 1;
          flex: 1;
  width: 100%;
  box-sizing: border-box;
  overflow: hidden;
}
.v-note-wrapper .v-note-panel .v-note-edit.divarea-wrapper {
  -webkit-box-flex: 0;
  -webkit-flex: 0 0 50%;
      -ms-flex: 0 0 50%;
          flex: 0 0 50%;
  width: 50%;
  padding: 0;
  overflow-y: scroll;
  overflow-x: hidden;
  box-sizing: border-box;
  cursor: text;
  border-bottom-left-radius: 4px;
}
.v-note-wrapper .v-note-panel .v-note-edit.divarea-wrapper.scroll-style::-webkit-scrollbar {
  width: 6px;
  background-color: #e5e5e5;
}
.v-note-wrapper .v-note-panel .v-note-edit.divarea-wrapper.scroll-style::-webkit-scrollbar-thumb {
  background-color: #b7b7b7;
  border-radius: 4px;
}
.v-note-wrapper .v-note-panel .v-note-edit.divarea-wrapper.scroll-style::-webkit-scrollbar-thumb:hover {
  background-color: #a1a1a1;
}
.v-note-wrapper .v-note-panel .v-note-edit.divarea-wrapper.scroll-style::-webkit-scrollbar-thumb:active {
  background-color: #a1a1a1;
}
.v-note-wrapper .v-note-panel .v-note-edit.divarea-wrapper.scroll-style::-webkit-scrollbar-track {
  -webkit-box-shadow: 0 0 0px #808080 inset;
}
.v-note-wrapper .v-note-panel .v-note-edit.divarea-wrapper.scroll-style-border-radius::-webkit-scrollbar {
  border-bottom-right-radius: 4px;
}
.v-note-wrapper .v-note-panel .v-note-edit.divarea-wrapper.transition {
  -webkit-transition: all 0.2s linear 0s;
  transition: all 0.2s linear 0s;
}
.v-note-wrapper .v-note-panel .v-note-edit.divarea-wrapper.single-edit {
  width: 100%;
  -webkit-box-flex: 0;
  -webkit-flex: 0 0 100%;
      -ms-flex: 0 0 100%;
          flex: 0 0 100%;
  overflow-y: auto;
}
.v-note-wrapper .v-note-panel .v-note-edit.divarea-wrapper.single-show {
  width: 0;
  -webkit-box-flex: 0;
  -webkit-flex: 0 0 0;
      -ms-flex: 0 0 0px;
          flex: 0 0 0;
  display: none;
}
.v-note-wrapper .v-note-panel .v-note-edit.divarea-wrapper .content-div {
  width: 100%;
  padding: 20px 25px;
  box-sizing: border-box;
  outline: 0 none;
  border: none !important;
  color: #2c3e50;
  font-size: 16px;
}
.v-note-wrapper .v-note-panel .v-note-edit.divarea-wrapper .content-input-wrapper {
  width: 100%;
  padding: 8px 25px 15px 25px;
}
.v-note-wrapper .v-note-panel .v-note-show {
  -webkit-box-flex: 0;
  -webkit-flex: 0 0 50%;
      -ms-flex: 0 0 50%;
          flex: 0 0 50%;
  width: 50%;
  overflow-y: auto;
  padding: 0 0;
  -webkit-transition: all 0.2s linear 0s;
  transition: all 0.2s linear 0s;
}
.v-note-wrapper .v-note-panel .v-note-show.single-show {
  -webkit-box-flex: 0;
  -webkit-flex: 0 0 100%;
      -ms-flex: 0 0 100%;
          flex: 0 0 100%;
  width: 100%;
}
.v-note-wrapper .v-note-panel .v-note-show .v-show-content,
.v-note-wrapper .v-note-panel .v-note-show .v-show-content-html {
  width: 100%;
  height: 100%;
  padding: 8px 25px 15px 25px;
  overflow-y: auto;
  box-sizing: border-box;
  overflow-x: hidden;
}
.v-note-wrapper .v-note-panel .v-note-show .v-show-content.scroll-style::-webkit-scrollbar,
.v-note-wrapper .v-note-panel .v-note-show .v-show-content-html.scroll-style::-webkit-scrollbar {
  width: 6px;
  background-color: #e5e5e5;
}
.v-note-wrapper .v-note-panel .v-note-show .v-show-content.scroll-style::-webkit-scrollbar-thumb,
.v-note-wrapper .v-note-panel .v-note-show .v-show-content-html.scroll-style::-webkit-scrollbar-thumb {
  background-color: #b7b7b7;
  border-radius: 4px;
}
.v-note-wrapper .v-note-panel .v-note-show .v-show-content.scroll-style::-webkit-scrollbar-thumb:hover,
.v-note-wrapper .v-note-panel .v-note-show .v-show-content-html.scroll-style::-webkit-scrollbar-thumb:hover {
  background-color: #a1a1a1;
}
.v-note-wrapper .v-note-panel .v-note-show .v-show-content.scroll-style::-webkit-scrollbar-thumb:active,
.v-note-wrapper .v-note-panel .v-note-show .v-show-content-html.scroll-style::-webkit-scrollbar-thumb:active {
  background-color: #a1a1a1;
}
.v-note-wrapper .v-note-panel .v-note-show .v-show-content.scroll-style::-webkit-scrollbar-track,
.v-note-wrapper .v-note-panel .v-note-show .v-show-content-html.scroll-style::-webkit-scrollbar-track {
  -webkit-box-shadow: 0 0 0px #808080 inset;
}
.v-note-wrapper .v-note-panel .v-note-show .v-show-content.scroll-style-border-radius::-webkit-scrollbar,
.v-note-wrapper .v-note-panel .v-note-show .v-show-content-html.scroll-style-border-radius::-webkit-scrollbar {
  border-bottom-right-radius: 4px;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  position: absolute;
  width: 250px;
  right: 0;
  top: 0;
  bottom: 0;
  height: 100%;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  -webkit-flex-direction: column;
      -ms-flex-direction: column;
          flex-direction: column;
  background-color: rgba(255,255,255,0.98);
  border-left: 1px solid #f2f6fc;
  border-right: 1px solid #f2f6fc;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper.transition {
  -webkit-transition: all 0.1s linear 0s;
  transition: all 0.1s linear 0s;
}
@media only screen and (max-width: 768px) {
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper {
    width: 50%;
}
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper.slideTop-enter-active,
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper.slideTop-leave-active {
  height: 100%;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper.slideTop-enter,
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper.slideTop-leave-active {
  height: 0;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-title {
  height: 50px;
  width: 100%;
  border-bottom: 1px solid #f2f6fc;
  -webkit-box-flex: 0;
  -webkit-flex: none;
      -ms-flex: none;
          flex: none;
  line-height: 50px;
  font-size: 16px;
  box-sizing: border-box;
  padding: 0 12px 0 18px;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-title .v-note-navigation-close {
  float: right;
  color: #606266;
  font-size: 18px;
  cursor: pointer;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-title .v-note-navigation-close:hover {
  color: #303133;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content {
  overflow-y: auto;
  -webkit-box-flex: 1;
  -webkit-flex: 1;
      -ms-flex: 1;
          flex: 1;
  padding: 8px 0;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content.scroll-style::-webkit-scrollbar {
  width: 6px;
  background-color: #e5e5e5;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content.scroll-style::-webkit-scrollbar-thumb {
  background-color: #b7b7b7;
  border-radius: 4px;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content.scroll-style::-webkit-scrollbar-thumb:hover {
  background-color: #a1a1a1;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content.scroll-style::-webkit-scrollbar-thumb:active {
  background-color: #a1a1a1;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content.scroll-style::-webkit-scrollbar-track {
  -webkit-box-shadow: 0 0 0px #808080 inset;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content.scroll-style-border-radius::-webkit-scrollbar {
  border-bottom-right-radius: 4px;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h1,
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h2,
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h3,
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h4,
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h5,
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h6 {
  margin: 2px 0;
  font-weight: 500;
  font-size: 17px;
  color: #2185d0;
  cursor: pointer;
  line-height: normal;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  padding: 0 12px;
  border-bottom: none;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h1:hover,
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h2:hover,
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h3:hover,
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h4:hover,
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h5:hover,
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h6:hover {
  color: #483d8b;
  -webkit-text-decoration-line: underline;
          text-decoration-line: underline;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h2 {
  padding-left: 27px;
  font-size: 17px;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h3 {
  padding-left: 42px;
  font-size: 17px;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h4 {
  padding-left: 58px;
  font-size: 15px;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h5 {
  padding-left: 72px;
  font-size: 15px;
}
.v-note-wrapper .v-note-panel .v-note-navigation-wrapper .v-note-navigation-content h6 {
  padding-left: 87px;
  font-size: 15px;
}
.v-note-wrapper .v-note-read-model {
  position: relative;
  display: none;
  width: 100%;
  height: 100%;
  background: #fbfbfb;
  padding: 30px 8% 50px 8%;
  overflow-y: auto;
  box-sizing: border-box;
}
.v-note-wrapper .v-note-read-model.scroll-style::-webkit-scrollbar {
  width: 6px;
  background-color: #e5e5e5;
}
.v-note-wrapper .v-note-read-model.scroll-style::-webkit-scrollbar-thumb {
  background-color: #b7b7b7;
  border-radius: 4px;
}
.v-note-wrapper .v-note-read-model.scroll-style::-webkit-scrollbar-thumb:hover {
  background-color: #a1a1a1;
}
.v-note-wrapper .v-note-read-model.scroll-style::-webkit-scrollbar-thumb:active {
  background-color: #a1a1a1;
}
.v-note-wrapper .v-note-read-model.scroll-style::-webkit-scrollbar-track {
  -webkit-box-shadow: 0 0 0px #808080 inset;
}
.v-note-wrapper .v-note-read-model.scroll-style-border-radius::-webkit-scrollbar {
  border-bottom-right-radius: 4px;
}
.v-note-wrapper .v-note-read-model.show {
  display: block;
}
.v-note-wrapper.shadow {
  border: none;
}
.v-note-help-wrapper {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: rgba(0,0,0,0.7);
  z-index: 1600;
  -webkit-transition: all 0.1s linear 0s;
  transition: all 0.1s linear 0s;
}
.v-note-help-wrapper.fade-enter-active,
.v-note-help-wrapper.fade-leave-active {
  opacity: 1;
}
.v-note-help-wrapper.fade-enter,
.v-note-help-wrapper.fade-leave-active {
  opacity: 0;
}
.v-note-help-wrapper .v-note-help-content {
  position: relative;
  width: 60%;
  max-width: 800px;
  margin: 30px auto;
  height: 90%;
  min-width: 320px;
  -webkit-transition: all 0.1s linear 0s;
  transition: all 0.1s linear 0s;
  z-index: 3;
  border: 1px solid #f2f6fc;
}
.v-note-help-wrapper .v-note-help-content.shadow {
  border: none;
  box-shadow: 0 0px 5px rgba(0,0,0,0.157), 0 0px 5px rgba(0,0,0,0.227);
}
.v-note-help-wrapper .v-note-help-content i {
  font-size: 28px;
  position: absolute;
  right: 15px;
  top: 8px;
  color: rgba(0,0,0,0.7);
  cursor: pointer;
}
.v-note-help-wrapper .v-note-help-content i:hover {
  color: #000;
}
.v-note-help-wrapper .v-note-help-content .v-note-help-show {
  width: 100%;
  height: 100%;
  font-size: 18px;
  background: #fbfbfb;
  overflow-y: auto;
  padding: 2% 6%;
}
.v-note-help-wrapper .v-note-help-content .v-note-help-show.scroll-style::-webkit-scrollbar {
  width: 6px;
  background-color: #e5e5e5;
}
.v-note-help-wrapper .v-note-help-content .v-note-help-show.scroll-style::-webkit-scrollbar-thumb {
  background-color: #b7b7b7;
  border-radius: 4px;
}
.v-note-help-wrapper .v-note-help-content .v-note-help-show.scroll-style::-webkit-scrollbar-thumb:hover {
  background-color: #a1a1a1;
}
.v-note-help-wrapper .v-note-help-content .v-note-help-show.scroll-style::-webkit-scrollbar-thumb:active {
  background-color: #a1a1a1;
}
.v-note-help-wrapper .v-note-help-content .v-note-help-show.scroll-style::-webkit-scrollbar-track {
  -webkit-box-shadow: 0 0 0px #808080 inset;
}
.v-note-help-wrapper .v-note-help-content .v-note-help-show.scroll-style-border-radius::-webkit-scrollbar {
  border-bottom-right-radius: 4px;
}
.v-note-img-wrapper {
  position: fixed;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-pack: center;
  -webkit-justify-content: center;
      -ms-flex-pack: center;
          justify-content: center;
  -webkit-box-align: center;
  -webkit-align-items: center;
      -ms-flex-align: center;
          align-items: center;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: rgba(0,0,0,0.7);
  z-index: 1600;
  -webkit-transition: all 0.1s linear 0s;
  transition: all 0.1s linear 0s;
}
.v-note-img-wrapper.fade-enter-active,
.v-note-img-wrapper.fade-leave-active {
  opacity: 1;
}
.v-note-img-wrapper.fade-enter,
.v-note-img-wrapper.fade-leave-active {
  opacity: 0;
}
.v-note-img-wrapper img {
  -webkit-box-flex: 0;
  -webkit-flex: 0 0 auto;
      -ms-flex: 0 0 auto;
          flex: 0 0 auto;
  z-index: 3;
}
.v-note-img-wrapper i {
  font-size: 28px;
  position: absolute;
  right: 15px;
  top: 8px;
  color: rgba(255,255,255,0.7);
  cursor: pointer;
}
.v-note-img-wrapper i:hover {
  color: #fff;
}
`,""])},function(c,d,i){d=c.exports=i(15)(!1),d.push([c.i,`
.auto-textarea-wrapper[data-v-7a63e4b3] {
    height: 100%;
}
`,""])},function(c,d,i){function t(s){return s==null}function r(s){var l={};for(var u in s)l[u]=s[u];return l}function e(s){s=r(s||{}),s.whiteList=s.whiteList||o.whiteList,s.onAttr=s.onAttr||o.onAttr,s.onIgnoreAttr=s.onIgnoreAttr||o.onIgnoreAttr,s.safeAttrValue=s.safeAttrValue||o.safeAttrValue,this.options=s}var o=i(51),n=i(112);i(52),e.prototype.process=function(s){if(s=s||"",!(s=s.toString()))return"";var l=this,u=l.options,a=u.whiteList,p=u.onAttr,_=u.onIgnoreAttr,v=u.safeAttrValue;return n(s,function(g,C,m,h,w){var f=a[m],k=!1;if(f===!0?k=f:typeof f=="function"?k=f(h):f instanceof RegExp&&(k=f.test(h)),k!==!0&&(k=!1),h=v(m,h)){var b={position:C,sourcePosition:g,source:w,isWhite:k};if(k){var y=p(m,h,b);return t(y)?m+":"+h:y}var y=_(m,h,b);return t(y)?void 0:y}})},c.exports=e},function(c,d,i){function t(e,o){function n(){if(!l){var g=r.trim(e.slice(u,a)),C=g.indexOf(":");if(C!==-1){var m=r.trim(g.slice(0,C)),h=r.trim(g.slice(C+1));if(m){var w=o(u,p.length,m,h,g);w&&(p+=w+"; ")}}}u=a+1}e=r.trimRight(e),e[e.length-1]!==";"&&(e+=";");for(var s=e.length,l=!1,u=0,a=0,p="";a<s;a++){var _=e[a];if(_==="/"&&e[a+1]==="*"){var v=e.indexOf("*/",a+2);if(v===-1)break;a=v+1,u=a+1,l=!1}else _==="("?l=!0:_===")"?l=!1:_===";"?l||n():_===`
`&&n()}return r.trim(p)}var r=i(52);c.exports=t},function(c,d){},function(c,d){},function(c,d,i){function t(b){return Array.prototype.slice.call(arguments,1).forEach(function(y){y&&Object.keys(y).forEach(function(x){b[x]=y[x]})}),b}function r(b){return Object.prototype.toString.call(b)}function e(b){return r(b)==="[object String]"}function o(b){return r(b)==="[object Object]"}function n(b){return r(b)==="[object RegExp]"}function s(b){return r(b)==="[object Function]"}function l(b){return b.replace(/[.?*+^$[\]\\(){}|-]/g,"\\$&")}function u(b){return Object.keys(b||{}).reduce(function(y,x){return y||h.hasOwnProperty(x)},!1)}function a(b){b.__index__=-1,b.__text_cache__=""}function p(b){return function(y,x){var E=y.slice(x);return b.test(E)?E.match(b)[0].length:0}}function _(){return function(b,y){y.normalize(b)}}function v(b){function y(A){return A.replace("%TLDS%",E.src_tlds)}function x(A,$){throw new Error('(LinkifyIt) Invalid schema "'+A+'": '+$)}var E=b.re=i(116)(b.__opts__),D=b.__tlds__.slice();b.onCompile(),b.__tlds_replaced__||D.push(f),D.push(E.src_xn),E.src_tlds=D.join("|"),E.email_fuzzy=RegExp(y(E.tpl_email_fuzzy),"i"),E.link_fuzzy=RegExp(y(E.tpl_link_fuzzy),"i"),E.link_no_ip_fuzzy=RegExp(y(E.tpl_link_no_ip_fuzzy),"i"),E.host_fuzzy_test=RegExp(y(E.tpl_host_fuzzy_test),"i");var T=[];b.__compiled__={},Object.keys(b.__schemas__).forEach(function(A){var $=b.__schemas__[A];if($!==null){var z={validate:null,link:null};return b.__compiled__[A]=z,o($)?(n($.validate)?z.validate=p($.validate):s($.validate)?z.validate=$.validate:x(A,$),void(s($.normalize)?z.normalize=$.normalize:$.normalize?x(A,$):z.normalize=_())):e($)?void T.push(A):void x(A,$)}}),T.forEach(function(A){b.__compiled__[b.__schemas__[A]]&&(b.__compiled__[A].validate=b.__compiled__[b.__schemas__[A]].validate,b.__compiled__[A].normalize=b.__compiled__[b.__schemas__[A]].normalize)}),b.__compiled__[""]={validate:null,normalize:_()};var L=Object.keys(b.__compiled__).filter(function(A){return A.length>0&&b.__compiled__[A]}).map(l).join("|");b.re.schema_test=RegExp("(^|(?!_)(?:[><\uFF5C]|"+E.src_ZPCc+"))("+L+")","i"),b.re.schema_search=RegExp("(^|(?!_)(?:[><\uFF5C]|"+E.src_ZPCc+"))("+L+")","ig"),b.re.pretest=RegExp("("+b.re.schema_test.source+")|("+b.re.host_fuzzy_test.source+")|@","i"),a(b)}function g(b,y){var x=b.__index__,E=b.__last_index__,D=b.__text_cache__.slice(x,E);this.schema=b.__schema__.toLowerCase(),this.index=x+y,this.lastIndex=E+y,this.raw=D,this.text=D,this.url=D}function C(b,y){var x=new g(b,y);return b.__compiled__[x.schema].normalize(x,b),x}function m(b,y){if(!(this instanceof m))return new m(b,y);y||u(b)&&(y=b,b={}),this.__opts__=t({},h,y),this.__index__=-1,this.__last_index__=-1,this.__schema__="",this.__text_cache__="",this.__schemas__=t({},w,b),this.__compiled__={},this.__tlds__=k,this.__tlds_replaced__=!1,this.re={},v(this)}var h={fuzzyLink:!0,fuzzyEmail:!0,fuzzyIP:!1},w={"http:":{validate:function(b,y,x){var E=b.slice(y);return x.re.http||(x.re.http=new RegExp("^\\/\\/"+x.re.src_auth+x.re.src_host_port_strict+x.re.src_path,"i")),x.re.http.test(E)?E.match(x.re.http)[0].length:0}},"https:":"http:","ftp:":"http:","//":{validate:function(b,y,x){var E=b.slice(y);return x.re.no_http||(x.re.no_http=new RegExp("^"+x.re.src_auth+"(?:localhost|(?:(?:"+x.re.src_domain+")\\.)+"+x.re.src_domain_root+")"+x.re.src_port+x.re.src_host_terminator+x.re.src_path,"i")),x.re.no_http.test(E)?y>=3&&b[y-3]===":"||y>=3&&b[y-3]==="/"?0:E.match(x.re.no_http)[0].length:0}},"mailto:":{validate:function(b,y,x){var E=b.slice(y);return x.re.mailto||(x.re.mailto=new RegExp("^"+x.re.src_email_name+"@"+x.re.src_host_strict,"i")),x.re.mailto.test(E)?E.match(x.re.mailto)[0].length:0}}},f="a[cdefgilmnoqrstuwxz]|b[abdefghijmnorstvwyz]|c[acdfghiklmnoruvwxyz]|d[ejkmoz]|e[cegrstu]|f[ijkmor]|g[abdefghilmnpqrstuwy]|h[kmnrtu]|i[delmnoqrst]|j[emop]|k[eghimnprwyz]|l[abcikrstuvy]|m[acdeghklmnopqrstuvwxyz]|n[acefgilopruz]|om|p[aefghklmnrstwy]|qa|r[eosuw]|s[abcdeghijklmnortuvxyz]|t[cdfghjklmnortvwz]|u[agksyz]|v[aceginu]|w[fs]|y[et]|z[amw]",k="biz|com|edu|gov|net|org|pro|web|xxx|aero|asia|coop|info|museum|name|shop|\u0440\u0444".split("|");m.prototype.add=function(b,y){return this.__schemas__[b]=y,v(this),this},m.prototype.set=function(b){return this.__opts__=t(this.__opts__,b),this},m.prototype.test=function(b){if(this.__text_cache__=b,this.__index__=-1,!b.length)return!1;var y,x,E,D,T,L,A,$;if(this.re.schema_test.test(b)){for(A=this.re.schema_search,A.lastIndex=0;(y=A.exec(b))!==null;)if(D=this.testSchemaAt(b,y[2],A.lastIndex)){this.__schema__=y[2],this.__index__=y.index+y[1].length,this.__last_index__=y.index+y[0].length+D;break}}return this.__opts__.fuzzyLink&&this.__compiled__["http:"]&&($=b.search(this.re.host_fuzzy_test))>=0&&(this.__index__<0||$<this.__index__)&&(x=b.match(this.__opts__.fuzzyIP?this.re.link_fuzzy:this.re.link_no_ip_fuzzy))!==null&&(T=x.index+x[1].length,(this.__index__<0||T<this.__index__)&&(this.__schema__="",this.__index__=T,this.__last_index__=x.index+x[0].length)),this.__opts__.fuzzyEmail&&this.__compiled__["mailto:"]&&b.indexOf("@")>=0&&(E=b.match(this.re.email_fuzzy))!==null&&(T=E.index+E[1].length,L=E.index+E[0].length,(this.__index__<0||T<this.__index__||T===this.__index__&&L>this.__last_index__)&&(this.__schema__="mailto:",this.__index__=T,this.__last_index__=L)),this.__index__>=0},m.prototype.pretest=function(b){return this.re.pretest.test(b)},m.prototype.testSchemaAt=function(b,y,x){return this.__compiled__[y.toLowerCase()]?this.__compiled__[y.toLowerCase()].validate(b,x,this):0},m.prototype.match=function(b){var y=0,x=[];this.__index__>=0&&this.__text_cache__===b&&(x.push(C(this,y)),y=this.__last_index__);for(var E=y?b.slice(y):b;this.test(E);)x.push(C(this,y)),E=E.slice(this.__last_index__),y+=this.__last_index__;return x.length?x:null},m.prototype.tlds=function(b,y){return b=Array.isArray(b)?b:[b],y?(this.__tlds__=this.__tlds__.concat(b).sort().filter(function(x,E,D){return x!==D[E-1]}).reverse(),v(this),this):(this.__tlds__=b.slice(),this.__tlds_replaced__=!0,v(this),this)},m.prototype.normalize=function(b){b.schema||(b.url="http://"+b.url),b.schema!=="mailto:"||/^mailto:/i.test(b.url)||(b.url="mailto:"+b.url)},m.prototype.onCompile=function(){},c.exports=m},function(c,d,i){c.exports=function(t){var r={};return r.src_Any=i(60).source,r.src_Cc=i(58).source,r.src_Z=i(59).source,r.src_P=i(34).source,r.src_ZPCc=[r.src_Z,r.src_P,r.src_Cc].join("|"),r.src_ZCc=[r.src_Z,r.src_Cc].join("|"),r.src_pseudo_letter="(?:(?![><\uFF5C]|"+r.src_ZPCc+")"+r.src_Any+")",r.src_ip4="(?:(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)",r.src_auth="(?:(?:(?!"+r.src_ZCc+"|[@/\\[\\]()]).)+@)?",r.src_port="(?::(?:6(?:[0-4]\\d{3}|5(?:[0-4]\\d{2}|5(?:[0-2]\\d|3[0-5])))|[1-5]?\\d{1,4}))?",r.src_host_terminator="(?=$|[><\uFF5C]|"+r.src_ZPCc+")(?!-|_|:\\d|\\.-|\\.(?!$|"+r.src_ZPCc+"))",r.src_path="(?:[/?#](?:(?!"+r.src_ZCc+`|[><\uFF5C]|[()[\\]{}.,"'?!\\-]).|\\[(?:(?!`+r.src_ZCc+"|\\]).)*\\]|\\((?:(?!"+r.src_ZCc+"|[)]).)*\\)|\\{(?:(?!"+r.src_ZCc+'|[}]).)*\\}|\\"(?:(?!'+r.src_ZCc+`|["]).)+\\"|\\'(?:(?!`+r.src_ZCc+"|[']).)+\\'|\\'(?="+r.src_pseudo_letter+"|[-]).|\\.{2,4}[a-zA-Z0-9%/]|\\.(?!"+r.src_ZCc+"|[.]).|"+(t&&t["---"]?"\\-(?!--(?:[^-]|$))(?:-*)|":"\\-+|")+"\\,(?!"+r.src_ZCc+").|\\!(?!"+r.src_ZCc+"|[!]).|\\?(?!"+r.src_ZCc+"|[?]).)+|\\/)?",r.src_email_name='[\\-;:&=\\+\\$,\\.a-zA-Z0-9_][\\-;:&=\\+\\$,\\"\\.a-zA-Z0-9_]*',r.src_xn="xn--[a-z0-9\\-]{1,59}",r.src_domain_root="(?:"+r.src_xn+"|"+r.src_pseudo_letter+"{1,63})",r.src_domain="(?:"+r.src_xn+"|(?:"+r.src_pseudo_letter+")|(?:"+r.src_pseudo_letter+"(?:-|"+r.src_pseudo_letter+"){0,61}"+r.src_pseudo_letter+"))",r.src_host="(?:(?:(?:(?:"+r.src_domain+")\\.)*"+r.src_domain+"))",r.tpl_host_fuzzy="(?:"+r.src_ip4+"|(?:(?:(?:"+r.src_domain+")\\.)+(?:%TLDS%)))",r.tpl_host_no_ip_fuzzy="(?:(?:(?:"+r.src_domain+")\\.)+(?:%TLDS%))",r.src_host_strict=r.src_host+r.src_host_terminator,r.tpl_host_fuzzy_strict=r.tpl_host_fuzzy+r.src_host_terminator,r.src_host_port_strict=r.src_host+r.src_port+r.src_host_terminator,r.tpl_host_port_fuzzy_strict=r.tpl_host_fuzzy+r.src_port+r.src_host_terminator,r.tpl_host_port_no_ip_fuzzy_strict=r.tpl_host_no_ip_fuzzy+r.src_port+r.src_host_terminator,r.tpl_host_fuzzy_test="localhost|www\\.|\\.\\d{1,3}\\.|(?:\\.(?:%TLDS%)(?:"+r.src_ZPCc+"|>|$))",r.tpl_email_fuzzy='(^|[><\uFF5C]|"|\\(|'+r.src_ZCc+")("+r.src_email_name+"@"+r.tpl_host_fuzzy_strict+")",r.tpl_link_fuzzy="(^|(?![.:/\\-_@])(?:[$+<=>^`|\uFF5C]|"+r.src_ZPCc+"))((?![$+<=>^`|\uFF5C])"+r.tpl_host_port_fuzzy_strict+r.src_path+")",r.tpl_link_no_ip_fuzzy="(^|(?![.:/\\-_@])(?:[$+<=>^`|\uFF5C]|"+r.src_ZPCc+"))((?![$+<=>^`|\uFF5C])"+r.tpl_host_port_no_ip_fuzzy_strict+r.src_path+")",r}},function(c,d,i){c.exports=function(t){function r(a,p,_,v){var g,C,m,h,w,f=a.bMarks[p]+a.tShift[p],k=a.eMarks[p];if(f+2>=k||a.src.charCodeAt(f++)!==42||a.src.charCodeAt(f++)!==91)return!1;for(h=f;f<k;f++){if((m=a.src.charCodeAt(f))===91)return!1;if(m===93){w=f;break}m===92&&f++}return!(w<0||a.src.charCodeAt(w+1)!==58)&&(!!v||(g=a.src.slice(h,w).replace(/\\(.)/g,"$1"),C=a.src.slice(w+2,k).trim(),g.length!==0&&C.length!==0&&(a.env.abbreviations||(a.env.abbreviations={}),a.env.abbreviations[":"+g]===void 0&&(a.env.abbreviations[":"+g]=C),a.line=p+1,!0)))}function e(a){var p,_,v,g,C,m,h,w,f,k,b,y,x,E=a.tokens;if(a.env.abbreviations){for(y=new RegExp("(?:"+Object.keys(a.env.abbreviations).map(function(D){return D.substr(1)}).sort(function(D,T){return T.length-D.length}).map(o).join("|")+")"),b="(^|"+l+"|"+u+"|["+s.split("").map(o).join("")+"])("+Object.keys(a.env.abbreviations).map(function(D){return D.substr(1)}).sort(function(D,T){return T.length-D.length}).map(o).join("|")+")($|"+l+"|"+u+"|["+s.split("").map(o).join("")+"])",f=new RegExp(b,"g"),_=0,v=E.length;_<v;_++)if(E[_].type==="inline"){for(g=E[_].children,p=g.length-1;p>=0;p--)if(x=g[p],x.type==="text"&&(w=0,m=x.content,f.lastIndex=0,h=[],y.test(m))){for(;k=f.exec(m);)(k.index>0||k[1].length>0)&&(C=new a.Token("text","",0),C.content=m.slice(w,k.index+k[1].length),h.push(C)),C=new a.Token("abbr_open","abbr",1),C.attrs=[["title",a.env.abbreviations[":"+k[2]]]],h.push(C),C=new a.Token("text","",0),C.content=k[2],h.push(C),C=new a.Token("abbr_close","abbr",-1),h.push(C),f.lastIndex-=k[3].length,w=f.lastIndex;h.length&&(w<m.length&&(C=new a.Token("text","",0),C.content=m.slice(w),h.push(C)),E[_].children=g=n(g,p,h))}}}}var o=t.utils.escapeRE,n=t.utils.arrayReplaceAt,s=" \r\n$+<=>^`|~",l=t.utils.lib.ucmicro.P.source,u=t.utils.lib.ucmicro.Z.source;t.block.ruler.before("reference","abbr_def",r,{alt:["paragraph","reference"]}),t.core.ruler.after("linkify","abbr_replace",e)}},function(c,d,i){c.exports=function(t,r,e){function o(g){return g.trim().split(" ",2)[0]===r}function n(g,C,m,h,w){return g[C].nesting===1&&g[C].attrPush(["class",r]),w.renderToken(g,C,m,h,w)}function s(g,C,m,h){var w,f,k,b,y,x,E,D,T=!1,L=g.bMarks[C]+g.tShift[C],A=g.eMarks[C];if(a!==g.src.charCodeAt(L))return!1;for(w=L+1;w<=A&&u[(w-L)%p]===g.src[w];w++);if((k=Math.floor((w-L)/p))<l||(w-=(w-L)%p,b=g.src.slice(L,w),y=g.src.slice(w,A),!_(y)))return!1;if(h)return!0;for(f=C;!(++f>=m)&&(L=g.bMarks[f]+g.tShift[f],A=g.eMarks[f],!(L<A&&g.sCount[f]<g.blkIndent));)if(a===g.src.charCodeAt(L)&&!(g.sCount[f]-g.blkIndent>=4)){for(w=L+1;w<=A&&u[(w-L)%p]===g.src[w];w++);if(!(Math.floor((w-L)/p)<k||(w-=(w-L)%p,(w=g.skipSpaces(w))<A))){T=!0;break}}return E=g.parentType,D=g.lineMax,g.parentType="container",g.lineMax=f,x=g.push("container_"+r+"_open","div",1),x.markup=b,x.block=!0,x.info=y,x.map=[C,f],g.md.block.tokenize(g,C+1,f),x=g.push("container_"+r+"_close","div",-1),x.markup=g.src.slice(L,w),x.block=!0,g.parentType=E,g.lineMax=D,g.line=f+(T?1:0),!0}e=e||{};var l=3,u=e.marker||":",a=u.charCodeAt(0),p=u.length,_=e.validate||o,v=e.render||n;t.block.ruler.before("fence","container_"+r,s,{alt:["paragraph","reference","blockquote","list"]}),t.renderer.rules["container_"+r+"_open"]=v,t.renderer.rules["container_"+r+"_close"]=v}},function(c,d,i){c.exports=function(t){function r(s,l){var u,a,p=s.bMarks[l]+s.tShift[l],_=s.eMarks[l];return p>=_||(a=s.src.charCodeAt(p++))!==126&&a!==58?-1:(u=s.skipSpaces(p),p===u||u>=_?-1:p)}function e(s,l){var u,a,p=s.level+2;for(u=l+2,a=s.tokens.length-2;u<a;u++)s.tokens[u].level===p&&s.tokens[u].type==="paragraph_open"&&(s.tokens[u+2].hidden=!0,s.tokens[u].hidden=!0,u+=2)}function o(s,l,u,a){var p,_,v,g,C,m,h,w,f,k,b,y,x,E,D,T,L,A,$,z;if(a)return!(s.ddIndent<0)&&r(s,l)>=0;if((f=l+1)>=u||s.isEmpty(f)&&++f>=u||s.sCount[f]<s.blkIndent||(_=r(s,f))<0)return!1;h=s.tokens.length,$=!0,z=s.push("dl_open","dl",1),z.map=m=[l,0],g=l,v=f;t:for(;;){for(A=!1,z=s.push("dt_open","dt",1),z.map=[g,g],z=s.push("inline","",0),z.map=[g,g],z.content=s.getLines(g,g+1,s.blkIndent,!1).trim(),z.children=[],z=s.push("dt_close","dt",-1);;){for(z=s.push("dd_open","dd",1),z.map=C=[f,0],L=_,w=s.eMarks[v],k=s.sCount[v]+_-(s.bMarks[v]+s.tShift[v]);L<w&&(p=s.src.charCodeAt(L),n(p));)p===9?k+=4-k%4:k++,L++;if(_=L,T=s.tight,b=s.ddIndent,y=s.blkIndent,D=s.tShift[v],E=s.sCount[v],x=s.parentType,s.blkIndent=s.ddIndent=s.sCount[v]+2,s.tShift[v]=_-s.bMarks[v],s.sCount[v]=k,s.tight=!0,s.parentType="deflist",s.md.block.tokenize(s,v,u,!0),s.tight&&!A||($=!1),A=s.line-v>1&&s.isEmpty(s.line-1),s.tShift[v]=D,s.sCount[v]=E,s.tight=T,s.parentType=x,s.blkIndent=y,s.ddIndent=b,z=s.push("dd_close","dd",-1),C[1]=f=s.line,f>=u||s.sCount[f]<s.blkIndent)break t;if((_=r(s,f))<0)break;v=f}if(f>=u||(g=f,s.isEmpty(g))||s.sCount[g]<s.blkIndent||(v=g+1)>=u||(s.isEmpty(v)&&v++,v>=u)||s.sCount[v]<s.blkIndent||(_=r(s,v))<0)break}return z=s.push("dl_close","dl",-1),m[1]=f,s.line=f,$&&e(s,h),!0}var n=t.utils.isSpace;t.block.ruler.before("paragraph","deflist",o,{alt:["paragraph","reference","blockquote"]})}},function(c,d,i){var t=i(121),r=i(122),e=i(124),o=i(125),n=i(123);c.exports=function(s,l){var u={defs:t,shortcuts:r,enabled:[]},a=n(s.utils.assign({},u,l||{}));s.renderer.rules.emoji=e,s.core.ruler.push("emoji",o(s,a.defs,a.shortcuts,a.scanRE,a.replaceRE))}},function(c,d){c.exports={100:"\u{1F4AF}",1234:"\u{1F522}",grinning:"\u{1F600}",smiley:"\u{1F603}",smile:"\u{1F604}",grin:"\u{1F601}",laughing:"\u{1F606}",satisfied:"\u{1F606}",sweat_smile:"\u{1F605}",joy:"\u{1F602}",rofl:"\u{1F923}",relaxed:"\u263A\uFE0F",blush:"\u{1F60A}",innocent:"\u{1F607}",slightly_smiling_face:"\u{1F642}",upside_down_face:"\u{1F643}",wink:"\u{1F609}",relieved:"\u{1F60C}",heart_eyes:"\u{1F60D}",kissing_heart:"\u{1F618}",kissing:"\u{1F617}",kissing_smiling_eyes:"\u{1F619}",kissing_closed_eyes:"\u{1F61A}",yum:"\u{1F60B}",stuck_out_tongue_winking_eye:"\u{1F61C}",stuck_out_tongue_closed_eyes:"\u{1F61D}",stuck_out_tongue:"\u{1F61B}",money_mouth_face:"\u{1F911}",hugs:"\u{1F917}",nerd_face:"\u{1F913}",sunglasses:"\u{1F60E}",clown_face:"\u{1F921}",cowboy_hat_face:"\u{1F920}",smirk:"\u{1F60F}",unamused:"\u{1F612}",disappointed:"\u{1F61E}",pensive:"\u{1F614}",worried:"\u{1F61F}",confused:"\u{1F615}",slightly_frowning_face:"\u{1F641}",frowning_face:"\u2639\uFE0F",persevere:"\u{1F623}",confounded:"\u{1F616}",tired_face:"\u{1F62B}",weary:"\u{1F629}",triumph:"\u{1F624}",angry:"\u{1F620}",rage:"\u{1F621}",pout:"\u{1F621}",no_mouth:"\u{1F636}",neutral_face:"\u{1F610}",expressionless:"\u{1F611}",hushed:"\u{1F62F}",frowning:"\u{1F626}",anguished:"\u{1F627}",open_mouth:"\u{1F62E}",astonished:"\u{1F632}",dizzy_face:"\u{1F635}",flushed:"\u{1F633}",scream:"\u{1F631}",fearful:"\u{1F628}",cold_sweat:"\u{1F630}",cry:"\u{1F622}",disappointed_relieved:"\u{1F625}",drooling_face:"\u{1F924}",sob:"\u{1F62D}",sweat:"\u{1F613}",sleepy:"\u{1F62A}",sleeping:"\u{1F634}",roll_eyes:"\u{1F644}",thinking:"\u{1F914}",lying_face:"\u{1F925}",grimacing:"\u{1F62C}",zipper_mouth_face:"\u{1F910}",nauseated_face:"\u{1F922}",sneezing_face:"\u{1F927}",mask:"\u{1F637}",face_with_thermometer:"\u{1F912}",face_with_head_bandage:"\u{1F915}",smiling_imp:"\u{1F608}",imp:"\u{1F47F}",japanese_ogre:"\u{1F479}",japanese_goblin:"\u{1F47A}",hankey:"\u{1F4A9}",poop:"\u{1F4A9}",shit:"\u{1F4A9}",ghost:"\u{1F47B}",skull:"\u{1F480}",skull_and_crossbones:"\u2620\uFE0F",alien:"\u{1F47D}",space_invader:"\u{1F47E}",robot:"\u{1F916}",jack_o_lantern:"\u{1F383}",smiley_cat:"\u{1F63A}",smile_cat:"\u{1F638}",joy_cat:"\u{1F639}",heart_eyes_cat:"\u{1F63B}",smirk_cat:"\u{1F63C}",kissing_cat:"\u{1F63D}",scream_cat:"\u{1F640}",crying_cat_face:"\u{1F63F}",pouting_cat:"\u{1F63E}",open_hands:"\u{1F450}",raised_hands:"\u{1F64C}",clap:"\u{1F44F}",pray:"\u{1F64F}",handshake:"\u{1F91D}","+1":"\u{1F44D}",thumbsup:"\u{1F44D}","-1":"\u{1F44E}",thumbsdown:"\u{1F44E}",fist_oncoming:"\u{1F44A}",facepunch:"\u{1F44A}",punch:"\u{1F44A}",fist_raised:"\u270A",fist:"\u270A",fist_left:"\u{1F91B}",fist_right:"\u{1F91C}",crossed_fingers:"\u{1F91E}",v:"\u270C\uFE0F",metal:"\u{1F918}",ok_hand:"\u{1F44C}",point_left:"\u{1F448}",point_right:"\u{1F449}",point_up_2:"\u{1F446}",point_down:"\u{1F447}",point_up:"\u261D\uFE0F",hand:"\u270B",raised_hand:"\u270B",raised_back_of_hand:"\u{1F91A}",raised_hand_with_fingers_splayed:"\u{1F590}",vulcan_salute:"\u{1F596}",wave:"\u{1F44B}",call_me_hand:"\u{1F919}",muscle:"\u{1F4AA}",middle_finger:"\u{1F595}",fu:"\u{1F595}",writing_hand:"\u270D\uFE0F",selfie:"\u{1F933}",nail_care:"\u{1F485}",ring:"\u{1F48D}",lipstick:"\u{1F484}",kiss:"\u{1F48B}",lips:"\u{1F444}",tongue:"\u{1F445}",ear:"\u{1F442}",nose:"\u{1F443}",footprints:"\u{1F463}",eye:"\u{1F441}",eyes:"\u{1F440}",speaking_head:"\u{1F5E3}",bust_in_silhouette:"\u{1F464}",busts_in_silhouette:"\u{1F465}",baby:"\u{1F476}",boy:"\u{1F466}",girl:"\u{1F467}",man:"\u{1F468}",woman:"\u{1F469}",blonde_woman:"\u{1F471}\u200D\u2640",blonde_man:"\u{1F471}",person_with_blond_hair:"\u{1F471}",older_man:"\u{1F474}",older_woman:"\u{1F475}",man_with_gua_pi_mao:"\u{1F472}",woman_with_turban:"\u{1F473}\u200D\u2640",man_with_turban:"\u{1F473}",policewoman:"\u{1F46E}\u200D\u2640",policeman:"\u{1F46E}",cop:"\u{1F46E}",construction_worker_woman:"\u{1F477}\u200D\u2640",construction_worker_man:"\u{1F477}",construction_worker:"\u{1F477}",guardswoman:"\u{1F482}\u200D\u2640",guardsman:"\u{1F482}",female_detective:"\u{1F575}\uFE0F\u200D\u2640\uFE0F",male_detective:"\u{1F575}",detective:"\u{1F575}",woman_health_worker:"\u{1F469}\u200D\u2695",man_health_worker:"\u{1F468}\u200D\u2695",woman_farmer:"\u{1F469}\u200D\u{1F33E}",man_farmer:"\u{1F468}\u200D\u{1F33E}",woman_cook:"\u{1F469}\u200D\u{1F373}",man_cook:"\u{1F468}\u200D\u{1F373}",woman_student:"\u{1F469}\u200D\u{1F393}",man_student:"\u{1F468}\u200D\u{1F393}",woman_singer:"\u{1F469}\u200D\u{1F3A4}",man_singer:"\u{1F468}\u200D\u{1F3A4}",woman_teacher:"\u{1F469}\u200D\u{1F3EB}",man_teacher:"\u{1F468}\u200D\u{1F3EB}",woman_factory_worker:"\u{1F469}\u200D\u{1F3ED}",man_factory_worker:"\u{1F468}\u200D\u{1F3ED}",woman_technologist:"\u{1F469}\u200D\u{1F4BB}",man_technologist:"\u{1F468}\u200D\u{1F4BB}",woman_office_worker:"\u{1F469}\u200D\u{1F4BC}",man_office_worker:"\u{1F468}\u200D\u{1F4BC}",woman_mechanic:"\u{1F469}\u200D\u{1F527}",man_mechanic:"\u{1F468}\u200D\u{1F527}",woman_scientist:"\u{1F469}\u200D\u{1F52C}",man_scientist:"\u{1F468}\u200D\u{1F52C}",woman_artist:"\u{1F469}\u200D\u{1F3A8}",man_artist:"\u{1F468}\u200D\u{1F3A8}",woman_firefighter:"\u{1F469}\u200D\u{1F692}",man_firefighter:"\u{1F468}\u200D\u{1F692}",woman_pilot:"\u{1F469}\u200D\u2708",man_pilot:"\u{1F468}\u200D\u2708",woman_astronaut:"\u{1F469}\u200D\u{1F680}",man_astronaut:"\u{1F468}\u200D\u{1F680}",woman_judge:"\u{1F469}\u200D\u2696",man_judge:"\u{1F468}\u200D\u2696",mrs_claus:"\u{1F936}",santa:"\u{1F385}",princess:"\u{1F478}",prince:"\u{1F934}",bride_with_veil:"\u{1F470}",man_in_tuxedo:"\u{1F935}",angel:"\u{1F47C}",pregnant_woman:"\u{1F930}",bowing_woman:"\u{1F647}\u200D\u2640",bowing_man:"\u{1F647}",bow:"\u{1F647}",tipping_hand_woman:"\u{1F481}",information_desk_person:"\u{1F481}",sassy_woman:"\u{1F481}",tipping_hand_man:"\u{1F481}\u200D\u2642",sassy_man:"\u{1F481}\u200D\u2642",no_good_woman:"\u{1F645}",no_good:"\u{1F645}",ng_woman:"\u{1F645}",no_good_man:"\u{1F645}\u200D\u2642",ng_man:"\u{1F645}\u200D\u2642",ok_woman:"\u{1F646}",ok_man:"\u{1F646}\u200D\u2642",raising_hand_woman:"\u{1F64B}",raising_hand:"\u{1F64B}",raising_hand_man:"\u{1F64B}\u200D\u2642",woman_facepalming:"\u{1F926}\u200D\u2640",man_facepalming:"\u{1F926}\u200D\u2642",woman_shrugging:"\u{1F937}\u200D\u2640",man_shrugging:"\u{1F937}\u200D\u2642",pouting_woman:"\u{1F64E}",person_with_pouting_face:"\u{1F64E}",pouting_man:"\u{1F64E}\u200D\u2642",frowning_woman:"\u{1F64D}",person_frowning:"\u{1F64D}",frowning_man:"\u{1F64D}\u200D\u2642",haircut_woman:"\u{1F487}",haircut:"\u{1F487}",haircut_man:"\u{1F487}\u200D\u2642",massage_woman:"\u{1F486}",massage:"\u{1F486}",massage_man:"\u{1F486}\u200D\u2642",business_suit_levitating:"\u{1F574}",dancer:"\u{1F483}",man_dancing:"\u{1F57A}",dancing_women:"\u{1F46F}",dancers:"\u{1F46F}",dancing_men:"\u{1F46F}\u200D\u2642",walking_woman:"\u{1F6B6}\u200D\u2640",walking_man:"\u{1F6B6}",walking:"\u{1F6B6}",running_woman:"\u{1F3C3}\u200D\u2640",running_man:"\u{1F3C3}",runner:"\u{1F3C3}",running:"\u{1F3C3}",couple:"\u{1F46B}",two_women_holding_hands:"\u{1F46D}",two_men_holding_hands:"\u{1F46C}",couple_with_heart_woman_man:"\u{1F491}",couple_with_heart:"\u{1F491}",couple_with_heart_woman_woman:"\u{1F469}\u200D\u2764\uFE0F\u200D\u{1F469}",couple_with_heart_man_man:"\u{1F468}\u200D\u2764\uFE0F\u200D\u{1F468}",couplekiss_man_woman:"\u{1F48F}",couplekiss_woman_woman:"\u{1F469}\u200D\u2764\uFE0F\u200D\u{1F48B}\u200D\u{1F469}",couplekiss_man_man:"\u{1F468}\u200D\u2764\uFE0F\u200D\u{1F48B}\u200D\u{1F468}",family_man_woman_boy:"\u{1F46A}",family:"\u{1F46A}",family_man_woman_girl:"\u{1F468}\u200D\u{1F469}\u200D\u{1F467}",family_man_woman_girl_boy:"\u{1F468}\u200D\u{1F469}\u200D\u{1F467}\u200D\u{1F466}",family_man_woman_boy_boy:"\u{1F468}\u200D\u{1F469}\u200D\u{1F466}\u200D\u{1F466}",family_man_woman_girl_girl:"\u{1F468}\u200D\u{1F469}\u200D\u{1F467}\u200D\u{1F467}",family_woman_woman_boy:"\u{1F469}\u200D\u{1F469}\u200D\u{1F466}",family_woman_woman_girl:"\u{1F469}\u200D\u{1F469}\u200D\u{1F467}",family_woman_woman_girl_boy:"\u{1F469}\u200D\u{1F469}\u200D\u{1F467}\u200D\u{1F466}",family_woman_woman_boy_boy:"\u{1F469}\u200D\u{1F469}\u200D\u{1F466}\u200D\u{1F466}",family_woman_woman_girl_girl:"\u{1F469}\u200D\u{1F469}\u200D\u{1F467}\u200D\u{1F467}",family_man_man_boy:"\u{1F468}\u200D\u{1F468}\u200D\u{1F466}",family_man_man_girl:"\u{1F468}\u200D\u{1F468}\u200D\u{1F467}",family_man_man_girl_boy:"\u{1F468}\u200D\u{1F468}\u200D\u{1F467}\u200D\u{1F466}",family_man_man_boy_boy:"\u{1F468}\u200D\u{1F468}\u200D\u{1F466}\u200D\u{1F466}",family_man_man_girl_girl:"\u{1F468}\u200D\u{1F468}\u200D\u{1F467}\u200D\u{1F467}",family_woman_boy:"\u{1F469}\u200D\u{1F466}",family_woman_girl:"\u{1F469}\u200D\u{1F467}",family_woman_girl_boy:"\u{1F469}\u200D\u{1F467}\u200D\u{1F466}",family_woman_boy_boy:"\u{1F469}\u200D\u{1F466}\u200D\u{1F466}",family_woman_girl_girl:"\u{1F469}\u200D\u{1F467}\u200D\u{1F467}",family_man_boy:"\u{1F468}\u200D\u{1F466}",family_man_girl:"\u{1F468}\u200D\u{1F467}",family_man_girl_boy:"\u{1F468}\u200D\u{1F467}\u200D\u{1F466}",family_man_boy_boy:"\u{1F468}\u200D\u{1F466}\u200D\u{1F466}",family_man_girl_girl:"\u{1F468}\u200D\u{1F467}\u200D\u{1F467}",womans_clothes:"\u{1F45A}",shirt:"\u{1F455}",tshirt:"\u{1F455}",jeans:"\u{1F456}",necktie:"\u{1F454}",dress:"\u{1F457}",bikini:"\u{1F459}",kimono:"\u{1F458}",high_heel:"\u{1F460}",sandal:"\u{1F461}",boot:"\u{1F462}",mans_shoe:"\u{1F45E}",shoe:"\u{1F45E}",athletic_shoe:"\u{1F45F}",womans_hat:"\u{1F452}",tophat:"\u{1F3A9}",mortar_board:"\u{1F393}",crown:"\u{1F451}",rescue_worker_helmet:"\u26D1",school_satchel:"\u{1F392}",pouch:"\u{1F45D}",purse:"\u{1F45B}",handbag:"\u{1F45C}",briefcase:"\u{1F4BC}",eyeglasses:"\u{1F453}",dark_sunglasses:"\u{1F576}",closed_umbrella:"\u{1F302}",open_umbrella:"\u2602\uFE0F",dog:"\u{1F436}",cat:"\u{1F431}",mouse:"\u{1F42D}",hamster:"\u{1F439}",rabbit:"\u{1F430}",fox_face:"\u{1F98A}",bear:"\u{1F43B}",panda_face:"\u{1F43C}",koala:"\u{1F428}",tiger:"\u{1F42F}",lion:"\u{1F981}",cow:"\u{1F42E}",pig:"\u{1F437}",pig_nose:"\u{1F43D}",frog:"\u{1F438}",monkey_face:"\u{1F435}",see_no_evil:"\u{1F648}",hear_no_evil:"\u{1F649}",speak_no_evil:"\u{1F64A}",monkey:"\u{1F412}",chicken:"\u{1F414}",penguin:"\u{1F427}",bird:"\u{1F426}",baby_chick:"\u{1F424}",hatching_chick:"\u{1F423}",hatched_chick:"\u{1F425}",duck:"\u{1F986}",eagle:"\u{1F985}",owl:"\u{1F989}",bat:"\u{1F987}",wolf:"\u{1F43A}",boar:"\u{1F417}",horse:"\u{1F434}",unicorn:"\u{1F984}",bee:"\u{1F41D}",honeybee:"\u{1F41D}",bug:"\u{1F41B}",butterfly:"\u{1F98B}",snail:"\u{1F40C}",shell:"\u{1F41A}",beetle:"\u{1F41E}",ant:"\u{1F41C}",spider:"\u{1F577}",spider_web:"\u{1F578}",turtle:"\u{1F422}",snake:"\u{1F40D}",lizard:"\u{1F98E}",scorpion:"\u{1F982}",crab:"\u{1F980}",squid:"\u{1F991}",octopus:"\u{1F419}",shrimp:"\u{1F990}",tropical_fish:"\u{1F420}",fish:"\u{1F41F}",blowfish:"\u{1F421}",dolphin:"\u{1F42C}",flipper:"\u{1F42C}",shark:"\u{1F988}",whale:"\u{1F433}",whale2:"\u{1F40B}",crocodile:"\u{1F40A}",leopard:"\u{1F406}",tiger2:"\u{1F405}",water_buffalo:"\u{1F403}",ox:"\u{1F402}",cow2:"\u{1F404}",deer:"\u{1F98C}",dromedary_camel:"\u{1F42A}",camel:"\u{1F42B}",elephant:"\u{1F418}",rhinoceros:"\u{1F98F}",gorilla:"\u{1F98D}",racehorse:"\u{1F40E}",pig2:"\u{1F416}",goat:"\u{1F410}",ram:"\u{1F40F}",sheep:"\u{1F411}",dog2:"\u{1F415}",poodle:"\u{1F429}",cat2:"\u{1F408}",rooster:"\u{1F413}",turkey:"\u{1F983}",dove:"\u{1F54A}",rabbit2:"\u{1F407}",mouse2:"\u{1F401}",rat:"\u{1F400}",chipmunk:"\u{1F43F}",feet:"\u{1F43E}",paw_prints:"\u{1F43E}",dragon:"\u{1F409}",dragon_face:"\u{1F432}",cactus:"\u{1F335}",christmas_tree:"\u{1F384}",evergreen_tree:"\u{1F332}",deciduous_tree:"\u{1F333}",palm_tree:"\u{1F334}",seedling:"\u{1F331}",herb:"\u{1F33F}",shamrock:"\u2618\uFE0F",four_leaf_clover:"\u{1F340}",bamboo:"\u{1F38D}",tanabata_tree:"\u{1F38B}",leaves:"\u{1F343}",fallen_leaf:"\u{1F342}",maple_leaf:"\u{1F341}",mushroom:"\u{1F344}",ear_of_rice:"\u{1F33E}",bouquet:"\u{1F490}",tulip:"\u{1F337}",rose:"\u{1F339}",wilted_flower:"\u{1F940}",sunflower:"\u{1F33B}",blossom:"\u{1F33C}",cherry_blossom:"\u{1F338}",hibiscus:"\u{1F33A}",earth_americas:"\u{1F30E}",earth_africa:"\u{1F30D}",earth_asia:"\u{1F30F}",full_moon:"\u{1F315}",waning_gibbous_moon:"\u{1F316}",last_quarter_moon:"\u{1F317}",waning_crescent_moon:"\u{1F318}",new_moon:"\u{1F311}",waxing_crescent_moon:"\u{1F312}",first_quarter_moon:"\u{1F313}",moon:"\u{1F314}",waxing_gibbous_moon:"\u{1F314}",new_moon_with_face:"\u{1F31A}",full_moon_with_face:"\u{1F31D}",sun_with_face:"\u{1F31E}",first_quarter_moon_with_face:"\u{1F31B}",last_quarter_moon_with_face:"\u{1F31C}",crescent_moon:"\u{1F319}",dizzy:"\u{1F4AB}",star:"\u2B50\uFE0F",star2:"\u{1F31F}",sparkles:"\u2728",zap:"\u26A1\uFE0F",fire:"\u{1F525}",boom:"\u{1F4A5}",collision:"\u{1F4A5}",comet:"\u2604",sunny:"\u2600\uFE0F",sun_behind_small_cloud:"\u{1F324}",partly_sunny:"\u26C5\uFE0F",sun_behind_large_cloud:"\u{1F325}",sun_behind_rain_cloud:"\u{1F326}",rainbow:"\u{1F308}",cloud:"\u2601\uFE0F",cloud_with_rain:"\u{1F327}",cloud_with_lightning_and_rain:"\u26C8",cloud_with_lightning:"\u{1F329}",cloud_with_snow:"\u{1F328}",snowman_with_snow:"\u2603\uFE0F",snowman:"\u26C4\uFE0F",snowflake:"\u2744\uFE0F",wind_face:"\u{1F32C}",dash:"\u{1F4A8}",tornado:"\u{1F32A}",fog:"\u{1F32B}",ocean:"\u{1F30A}",droplet:"\u{1F4A7}",sweat_drops:"\u{1F4A6}",umbrella:"\u2614\uFE0F",green_apple:"\u{1F34F}",apple:"\u{1F34E}",pear:"\u{1F350}",tangerine:"\u{1F34A}",orange:"\u{1F34A}",mandarin:"\u{1F34A}",lemon:"\u{1F34B}",banana:"\u{1F34C}",watermelon:"\u{1F349}",grapes:"\u{1F347}",strawberry:"\u{1F353}",melon:"\u{1F348}",cherries:"\u{1F352}",peach:"\u{1F351}",pineapple:"\u{1F34D}",kiwi_fruit:"\u{1F95D}",avocado:"\u{1F951}",tomato:"\u{1F345}",eggplant:"\u{1F346}",cucumber:"\u{1F952}",carrot:"\u{1F955}",corn:"\u{1F33D}",hot_pepper:"\u{1F336}",potato:"\u{1F954}",sweet_potato:"\u{1F360}",chestnut:"\u{1F330}",peanuts:"\u{1F95C}",honey_pot:"\u{1F36F}",croissant:"\u{1F950}",bread:"\u{1F35E}",baguette_bread:"\u{1F956}",cheese:"\u{1F9C0}",egg:"\u{1F95A}",fried_egg:"\u{1F373}",bacon:"\u{1F953}",pancakes:"\u{1F95E}",fried_shrimp:"\u{1F364}",poultry_leg:"\u{1F357}",meat_on_bone:"\u{1F356}",pizza:"\u{1F355}",hotdog:"\u{1F32D}",hamburger:"\u{1F354}",fries:"\u{1F35F}",stuffed_flatbread:"\u{1F959}",taco:"\u{1F32E}",burrito:"\u{1F32F}",green_salad:"\u{1F957}",shallow_pan_of_food:"\u{1F958}",spaghetti:"\u{1F35D}",ramen:"\u{1F35C}",stew:"\u{1F372}",fish_cake:"\u{1F365}",sushi:"\u{1F363}",bento:"\u{1F371}",curry:"\u{1F35B}",rice:"\u{1F35A}",rice_ball:"\u{1F359}",rice_cracker:"\u{1F358}",oden:"\u{1F362}",dango:"\u{1F361}",shaved_ice:"\u{1F367}",ice_cream:"\u{1F368}",icecream:"\u{1F366}",cake:"\u{1F370}",birthday:"\u{1F382}",custard:"\u{1F36E}",lollipop:"\u{1F36D}",candy:"\u{1F36C}",chocolate_bar:"\u{1F36B}",popcorn:"\u{1F37F}",doughnut:"\u{1F369}",cookie:"\u{1F36A}",milk_glass:"\u{1F95B}",baby_bottle:"\u{1F37C}",coffee:"\u2615\uFE0F",tea:"\u{1F375}",sake:"\u{1F376}",beer:"\u{1F37A}",beers:"\u{1F37B}",clinking_glasses:"\u{1F942}",wine_glass:"\u{1F377}",tumbler_glass:"\u{1F943}",cocktail:"\u{1F378}",tropical_drink:"\u{1F379}",champagne:"\u{1F37E}",spoon:"\u{1F944}",fork_and_knife:"\u{1F374}",plate_with_cutlery:"\u{1F37D}",soccer:"\u26BD\uFE0F",basketball:"\u{1F3C0}",football:"\u{1F3C8}",baseball:"\u26BE\uFE0F",tennis:"\u{1F3BE}",volleyball:"\u{1F3D0}",rugby_football:"\u{1F3C9}","8ball":"\u{1F3B1}",ping_pong:"\u{1F3D3}",badminton:"\u{1F3F8}",goal_net:"\u{1F945}",ice_hockey:"\u{1F3D2}",field_hockey:"\u{1F3D1}",cricket:"\u{1F3CF}",golf:"\u26F3\uFE0F",bow_and_arrow:"\u{1F3F9}",fishing_pole_and_fish:"\u{1F3A3}",boxing_glove:"\u{1F94A}",martial_arts_uniform:"\u{1F94B}",ice_skate:"\u26F8",ski:"\u{1F3BF}",skier:"\u26F7",snowboarder:"\u{1F3C2}",weight_lifting_woman:"\u{1F3CB}\uFE0F\u200D\u2640\uFE0F",weight_lifting_man:"\u{1F3CB}",person_fencing:"\u{1F93A}",women_wrestling:"\u{1F93C}\u200D\u2640",men_wrestling:"\u{1F93C}\u200D\u2642",woman_cartwheeling:"\u{1F938}\u200D\u2640",man_cartwheeling:"\u{1F938}\u200D\u2642",basketball_woman:"\u26F9\uFE0F\u200D\u2640\uFE0F",basketball_man:"\u26F9",woman_playing_handball:"\u{1F93E}\u200D\u2640",man_playing_handball:"\u{1F93E}\u200D\u2642",golfing_woman:"\u{1F3CC}\uFE0F\u200D\u2640\uFE0F",golfing_man:"\u{1F3CC}",surfing_woman:"\u{1F3C4}\u200D\u2640",surfing_man:"\u{1F3C4}",surfer:"\u{1F3C4}",swimming_woman:"\u{1F3CA}\u200D\u2640",swimming_man:"\u{1F3CA}",swimmer:"\u{1F3CA}",woman_playing_water_polo:"\u{1F93D}\u200D\u2640",man_playing_water_polo:"\u{1F93D}\u200D\u2642",rowing_woman:"\u{1F6A3}\u200D\u2640",rowing_man:"\u{1F6A3}",rowboat:"\u{1F6A3}",horse_racing:"\u{1F3C7}",biking_woman:"\u{1F6B4}\u200D\u2640",biking_man:"\u{1F6B4}",bicyclist:"\u{1F6B4}",mountain_biking_woman:"\u{1F6B5}\u200D\u2640",mountain_biking_man:"\u{1F6B5}",mountain_bicyclist:"\u{1F6B5}",running_shirt_with_sash:"\u{1F3BD}",medal_sports:"\u{1F3C5}",medal_military:"\u{1F396}","1st_place_medal":"\u{1F947}","2nd_place_medal":"\u{1F948}","3rd_place_medal":"\u{1F949}",trophy:"\u{1F3C6}",rosette:"\u{1F3F5}",reminder_ribbon:"\u{1F397}",ticket:"\u{1F3AB}",tickets:"\u{1F39F}",circus_tent:"\u{1F3AA}",woman_juggling:"\u{1F939}\u200D\u2640",man_juggling:"\u{1F939}\u200D\u2642",performing_arts:"\u{1F3AD}",art:"\u{1F3A8}",clapper:"\u{1F3AC}",microphone:"\u{1F3A4}",headphones:"\u{1F3A7}",musical_score:"\u{1F3BC}",musical_keyboard:"\u{1F3B9}",drum:"\u{1F941}",saxophone:"\u{1F3B7}",trumpet:"\u{1F3BA}",guitar:"\u{1F3B8}",violin:"\u{1F3BB}",game_die:"\u{1F3B2}",dart:"\u{1F3AF}",bowling:"\u{1F3B3}",video_game:"\u{1F3AE}",slot_machine:"\u{1F3B0}",car:"\u{1F697}",red_car:"\u{1F697}",taxi:"\u{1F695}",blue_car:"\u{1F699}",bus:"\u{1F68C}",trolleybus:"\u{1F68E}",racing_car:"\u{1F3CE}",police_car:"\u{1F693}",ambulance:"\u{1F691}",fire_engine:"\u{1F692}",minibus:"\u{1F690}",truck:"\u{1F69A}",articulated_lorry:"\u{1F69B}",tractor:"\u{1F69C}",kick_scooter:"\u{1F6F4}",bike:"\u{1F6B2}",motor_scooter:"\u{1F6F5}",motorcycle:"\u{1F3CD}",rotating_light:"\u{1F6A8}",oncoming_police_car:"\u{1F694}",oncoming_bus:"\u{1F68D}",oncoming_automobile:"\u{1F698}",oncoming_taxi:"\u{1F696}",aerial_tramway:"\u{1F6A1}",mountain_cableway:"\u{1F6A0}",suspension_railway:"\u{1F69F}",railway_car:"\u{1F683}",train:"\u{1F68B}",mountain_railway:"\u{1F69E}",monorail:"\u{1F69D}",bullettrain_side:"\u{1F684}",bullettrain_front:"\u{1F685}",light_rail:"\u{1F688}",steam_locomotive:"\u{1F682}",train2:"\u{1F686}",metro:"\u{1F687}",tram:"\u{1F68A}",station:"\u{1F689}",helicopter:"\u{1F681}",small_airplane:"\u{1F6E9}",airplane:"\u2708\uFE0F",flight_departure:"\u{1F6EB}",flight_arrival:"\u{1F6EC}",rocket:"\u{1F680}",artificial_satellite:"\u{1F6F0}",seat:"\u{1F4BA}",canoe:"\u{1F6F6}",boat:"\u26F5\uFE0F",sailboat:"\u26F5\uFE0F",motor_boat:"\u{1F6E5}",speedboat:"\u{1F6A4}",passenger_ship:"\u{1F6F3}",ferry:"\u26F4",ship:"\u{1F6A2}",anchor:"\u2693\uFE0F",construction:"\u{1F6A7}",fuelpump:"\u26FD\uFE0F",busstop:"\u{1F68F}",vertical_traffic_light:"\u{1F6A6}",traffic_light:"\u{1F6A5}",world_map:"\u{1F5FA}",moyai:"\u{1F5FF}",statue_of_liberty:"\u{1F5FD}",fountain:"\u26F2\uFE0F",tokyo_tower:"\u{1F5FC}",european_castle:"\u{1F3F0}",japanese_castle:"\u{1F3EF}",stadium:"\u{1F3DF}",ferris_wheel:"\u{1F3A1}",roller_coaster:"\u{1F3A2}",carousel_horse:"\u{1F3A0}",parasol_on_ground:"\u26F1",beach_umbrella:"\u{1F3D6}",desert_island:"\u{1F3DD}",mountain:"\u26F0",mountain_snow:"\u{1F3D4}",mount_fuji:"\u{1F5FB}",volcano:"\u{1F30B}",desert:"\u{1F3DC}",camping:"\u{1F3D5}",tent:"\u26FA\uFE0F",railway_track:"\u{1F6E4}",motorway:"\u{1F6E3}",building_construction:"\u{1F3D7}",factory:"\u{1F3ED}",house:"\u{1F3E0}",house_with_garden:"\u{1F3E1}",houses:"\u{1F3D8}",derelict_house:"\u{1F3DA}",office:"\u{1F3E2}",department_store:"\u{1F3EC}",post_office:"\u{1F3E3}",european_post_office:"\u{1F3E4}",hospital:"\u{1F3E5}",bank:"\u{1F3E6}",hotel:"\u{1F3E8}",convenience_store:"\u{1F3EA}",school:"\u{1F3EB}",love_hotel:"\u{1F3E9}",wedding:"\u{1F492}",classical_building:"\u{1F3DB}",church:"\u26EA\uFE0F",mosque:"\u{1F54C}",synagogue:"\u{1F54D}",kaaba:"\u{1F54B}",shinto_shrine:"\u26E9",japan:"\u{1F5FE}",rice_scene:"\u{1F391}",national_park:"\u{1F3DE}",sunrise:"\u{1F305}",sunrise_over_mountains:"\u{1F304}",stars:"\u{1F320}",sparkler:"\u{1F387}",fireworks:"\u{1F386}",city_sunrise:"\u{1F307}",city_sunset:"\u{1F306}",cityscape:"\u{1F3D9}",night_with_stars:"\u{1F303}",milky_way:"\u{1F30C}",bridge_at_night:"\u{1F309}",foggy:"\u{1F301}",watch:"\u231A\uFE0F",iphone:"\u{1F4F1}",calling:"\u{1F4F2}",computer:"\u{1F4BB}",keyboard:"\u2328\uFE0F",desktop_computer:"\u{1F5A5}",printer:"\u{1F5A8}",computer_mouse:"\u{1F5B1}",trackball:"\u{1F5B2}",joystick:"\u{1F579}",clamp:"\u{1F5DC}",minidisc:"\u{1F4BD}",floppy_disk:"\u{1F4BE}",cd:"\u{1F4BF}",dvd:"\u{1F4C0}",vhs:"\u{1F4FC}",camera:"\u{1F4F7}",camera_flash:"\u{1F4F8}",video_camera:"\u{1F4F9}",movie_camera:"\u{1F3A5}",film_projector:"\u{1F4FD}",film_strip:"\u{1F39E}",telephone_receiver:"\u{1F4DE}",phone:"\u260E\uFE0F",telephone:"\u260E\uFE0F",pager:"\u{1F4DF}",fax:"\u{1F4E0}",tv:"\u{1F4FA}",radio:"\u{1F4FB}",studio_microphone:"\u{1F399}",level_slider:"\u{1F39A}",control_knobs:"\u{1F39B}",stopwatch:"\u23F1",timer_clock:"\u23F2",alarm_clock:"\u23F0",mantelpiece_clock:"\u{1F570}",hourglass:"\u231B\uFE0F",hourglass_flowing_sand:"\u23F3",satellite:"\u{1F4E1}",battery:"\u{1F50B}",electric_plug:"\u{1F50C}",bulb:"\u{1F4A1}",flashlight:"\u{1F526}",candle:"\u{1F56F}",wastebasket:"\u{1F5D1}",oil_drum:"\u{1F6E2}",money_with_wings:"\u{1F4B8}",dollar:"\u{1F4B5}",yen:"\u{1F4B4}",euro:"\u{1F4B6}",pound:"\u{1F4B7}",moneybag:"\u{1F4B0}",credit_card:"\u{1F4B3}",gem:"\u{1F48E}",balance_scale:"\u2696\uFE0F",wrench:"\u{1F527}",hammer:"\u{1F528}",hammer_and_pick:"\u2692",hammer_and_wrench:"\u{1F6E0}",pick:"\u26CF",nut_and_bolt:"\u{1F529}",gear:"\u2699\uFE0F",chains:"\u26D3",gun:"\u{1F52B}",bomb:"\u{1F4A3}",hocho:"\u{1F52A}",knife:"\u{1F52A}",dagger:"\u{1F5E1}",crossed_swords:"\u2694\uFE0F",shield:"\u{1F6E1}",smoking:"\u{1F6AC}",coffin:"\u26B0\uFE0F",funeral_urn:"\u26B1\uFE0F",amphora:"\u{1F3FA}",crystal_ball:"\u{1F52E}",prayer_beads:"\u{1F4FF}",barber:"\u{1F488}",alembic:"\u2697\uFE0F",telescope:"\u{1F52D}",microscope:"\u{1F52C}",hole:"\u{1F573}",pill:"\u{1F48A}",syringe:"\u{1F489}",thermometer:"\u{1F321}",toilet:"\u{1F6BD}",potable_water:"\u{1F6B0}",shower:"\u{1F6BF}",bathtub:"\u{1F6C1}",bath:"\u{1F6C0}",bellhop_bell:"\u{1F6CE}",key:"\u{1F511}",old_key:"\u{1F5DD}",door:"\u{1F6AA}",couch_and_lamp:"\u{1F6CB}",bed:"\u{1F6CF}",sleeping_bed:"\u{1F6CC}",framed_picture:"\u{1F5BC}",shopping:"\u{1F6CD}",shopping_cart:"\u{1F6D2}",gift:"\u{1F381}",balloon:"\u{1F388}",flags:"\u{1F38F}",ribbon:"\u{1F380}",confetti_ball:"\u{1F38A}",tada:"\u{1F389}",dolls:"\u{1F38E}",izakaya_lantern:"\u{1F3EE}",lantern:"\u{1F3EE}",wind_chime:"\u{1F390}",email:"\u2709\uFE0F",envelope:"\u2709\uFE0F",envelope_with_arrow:"\u{1F4E9}",incoming_envelope:"\u{1F4E8}","e-mail":"\u{1F4E7}",love_letter:"\u{1F48C}",inbox_tray:"\u{1F4E5}",outbox_tray:"\u{1F4E4}",package:"\u{1F4E6}",label:"\u{1F3F7}",mailbox_closed:"\u{1F4EA}",mailbox:"\u{1F4EB}",mailbox_with_mail:"\u{1F4EC}",mailbox_with_no_mail:"\u{1F4ED}",postbox:"\u{1F4EE}",postal_horn:"\u{1F4EF}",scroll:"\u{1F4DC}",page_with_curl:"\u{1F4C3}",page_facing_up:"\u{1F4C4}",bookmark_tabs:"\u{1F4D1}",bar_chart:"\u{1F4CA}",chart_with_upwards_trend:"\u{1F4C8}",chart_with_downwards_trend:"\u{1F4C9}",spiral_notepad:"\u{1F5D2}",spiral_calendar:"\u{1F5D3}",calendar:"\u{1F4C6}",date:"\u{1F4C5}",card_index:"\u{1F4C7}",card_file_box:"\u{1F5C3}",ballot_box:"\u{1F5F3}",file_cabinet:"\u{1F5C4}",clipboard:"\u{1F4CB}",file_folder:"\u{1F4C1}",open_file_folder:"\u{1F4C2}",card_index_dividers:"\u{1F5C2}",newspaper_roll:"\u{1F5DE}",newspaper:"\u{1F4F0}",notebook:"\u{1F4D3}",notebook_with_decorative_cover:"\u{1F4D4}",ledger:"\u{1F4D2}",closed_book:"\u{1F4D5}",green_book:"\u{1F4D7}",blue_book:"\u{1F4D8}",orange_book:"\u{1F4D9}",books:"\u{1F4DA}",book:"\u{1F4D6}",open_book:"\u{1F4D6}",bookmark:"\u{1F516}",link:"\u{1F517}",paperclip:"\u{1F4CE}",paperclips:"\u{1F587}",triangular_ruler:"\u{1F4D0}",straight_ruler:"\u{1F4CF}",pushpin:"\u{1F4CC}",round_pushpin:"\u{1F4CD}",scissors:"\u2702\uFE0F",pen:"\u{1F58A}",fountain_pen:"\u{1F58B}",black_nib:"\u2712\uFE0F",paintbrush:"\u{1F58C}",crayon:"\u{1F58D}",memo:"\u{1F4DD}",pencil:"\u{1F4DD}",pencil2:"\u270F\uFE0F",mag:"\u{1F50D}",mag_right:"\u{1F50E}",lock_with_ink_pen:"\u{1F50F}",closed_lock_with_key:"\u{1F510}",lock:"\u{1F512}",unlock:"\u{1F513}",heart:"\u2764\uFE0F",yellow_heart:"\u{1F49B}",green_heart:"\u{1F49A}",blue_heart:"\u{1F499}",purple_heart:"\u{1F49C}",black_heart:"\u{1F5A4}",broken_heart:"\u{1F494}",heavy_heart_exclamation:"\u2763\uFE0F",two_hearts:"\u{1F495}",revolving_hearts:"\u{1F49E}",heartbeat:"\u{1F493}",heartpulse:"\u{1F497}",sparkling_heart:"\u{1F496}",cupid:"\u{1F498}",gift_heart:"\u{1F49D}",heart_decoration:"\u{1F49F}",peace_symbol:"\u262E\uFE0F",latin_cross:"\u271D\uFE0F",star_and_crescent:"\u262A\uFE0F",om:"\u{1F549}",wheel_of_dharma:"\u2638\uFE0F",star_of_david:"\u2721\uFE0F",six_pointed_star:"\u{1F52F}",menorah:"\u{1F54E}",yin_yang:"\u262F\uFE0F",orthodox_cross:"\u2626\uFE0F",place_of_worship:"\u{1F6D0}",ophiuchus:"\u26CE",aries:"\u2648\uFE0F",taurus:"\u2649\uFE0F",gemini:"\u264A\uFE0F",cancer:"\u264B\uFE0F",leo:"\u264C\uFE0F",virgo:"\u264D\uFE0F",libra:"\u264E\uFE0F",scorpius:"\u264F\uFE0F",sagittarius:"\u2650\uFE0F",capricorn:"\u2651\uFE0F",aquarius:"\u2652\uFE0F",pisces:"\u2653\uFE0F",id:"\u{1F194}",atom_symbol:"\u269B\uFE0F",accept:"\u{1F251}",radioactive:"\u2622\uFE0F",biohazard:"\u2623\uFE0F",mobile_phone_off:"\u{1F4F4}",vibration_mode:"\u{1F4F3}",eight_pointed_black_star:"\u2734\uFE0F",vs:"\u{1F19A}",white_flower:"\u{1F4AE}",ideograph_advantage:"\u{1F250}",secret:"\u3299\uFE0F",congratulations:"\u3297\uFE0F",u6e80:"\u{1F235}",a:"\u{1F170}\uFE0F",b:"\u{1F171}\uFE0F",ab:"\u{1F18E}",cl:"\u{1F191}",o2:"\u{1F17E}\uFE0F",sos:"\u{1F198}",x:"\u274C",o:"\u2B55\uFE0F",stop_sign:"\u{1F6D1}",no_entry:"\u26D4\uFE0F",name_badge:"\u{1F4DB}",no_entry_sign:"\u{1F6AB}",anger:"\u{1F4A2}",hotsprings:"\u2668\uFE0F",no_pedestrians:"\u{1F6B7}",do_not_litter:"\u{1F6AF}",no_bicycles:"\u{1F6B3}","non-potable_water":"\u{1F6B1}",underage:"\u{1F51E}",no_mobile_phones:"\u{1F4F5}",no_smoking:"\u{1F6AD}",exclamation:"\u2757\uFE0F",heavy_exclamation_mark:"\u2757\uFE0F",grey_exclamation:"\u2755",question:"\u2753",grey_question:"\u2754",bangbang:"\u203C\uFE0F",interrobang:"\u2049\uFE0F",low_brightness:"\u{1F505}",high_brightness:"\u{1F506}",part_alternation_mark:"\u303D\uFE0F",warning:"\u26A0\uFE0F",children_crossing:"\u{1F6B8}",trident:"\u{1F531}",fleur_de_lis:"\u269C\uFE0F",beginner:"\u{1F530}",recycle:"\u267B\uFE0F",white_check_mark:"\u2705",chart:"\u{1F4B9}",sparkle:"\u2747\uFE0F",eight_spoked_asterisk:"\u2733\uFE0F",negative_squared_cross_mark:"\u274E",globe_with_meridians:"\u{1F310}",diamond_shape_with_a_dot_inside:"\u{1F4A0}",m:"\u24C2\uFE0F",cyclone:"\u{1F300}",zzz:"\u{1F4A4}",atm:"\u{1F3E7}",wc:"\u{1F6BE}",wheelchair:"\u267F\uFE0F",parking:"\u{1F17F}\uFE0F",sa:"\u{1F202}\uFE0F",passport_control:"\u{1F6C2}",customs:"\u{1F6C3}",baggage_claim:"\u{1F6C4}",left_luggage:"\u{1F6C5}",mens:"\u{1F6B9}",womens:"\u{1F6BA}",baby_symbol:"\u{1F6BC}",restroom:"\u{1F6BB}",put_litter_in_its_place:"\u{1F6AE}",cinema:"\u{1F3A6}",signal_strength:"\u{1F4F6}",koko:"\u{1F201}",symbols:"\u{1F523}",information_source:"\u2139\uFE0F",abc:"\u{1F524}",abcd:"\u{1F521}",capital_abcd:"\u{1F520}",ng:"\u{1F196}",ok:"\u{1F197}",up:"\u{1F199}",cool:"\u{1F192}",new:"\u{1F195}",free:"\u{1F193}",zero:"0\uFE0F\u20E3",one:"1\uFE0F\u20E3",two:"2\uFE0F\u20E3",three:"3\uFE0F\u20E3",four:"4\uFE0F\u20E3",five:"5\uFE0F\u20E3",six:"6\uFE0F\u20E3",seven:"7\uFE0F\u20E3",eight:"8\uFE0F\u20E3",nine:"9\uFE0F\u20E3",keycap_ten:"\u{1F51F}",hash:"#\uFE0F\u20E3",asterisk:"*\uFE0F\u20E3",arrow_forward:"\u25B6\uFE0F",pause_button:"\u23F8",play_or_pause_button:"\u23EF",stop_button:"\u23F9",record_button:"\u23FA",next_track_button:"\u23ED",previous_track_button:"\u23EE",fast_forward:"\u23E9",rewind:"\u23EA",arrow_double_up:"\u23EB",arrow_double_down:"\u23EC",arrow_backward:"\u25C0\uFE0F",arrow_up_small:"\u{1F53C}",arrow_down_small:"\u{1F53D}",arrow_right:"\u27A1\uFE0F",arrow_left:"\u2B05\uFE0F",arrow_up:"\u2B06\uFE0F",arrow_down:"\u2B07\uFE0F",arrow_upper_right:"\u2197\uFE0F",arrow_lower_right:"\u2198\uFE0F",arrow_lower_left:"\u2199\uFE0F",arrow_upper_left:"\u2196\uFE0F",arrow_up_down:"\u2195\uFE0F",left_right_arrow:"\u2194\uFE0F",arrow_right_hook:"\u21AA\uFE0F",leftwards_arrow_with_hook:"\u21A9\uFE0F",arrow_heading_up:"\u2934\uFE0F",arrow_heading_down:"\u2935\uFE0F",twisted_rightwards_arrows:"\u{1F500}",repeat:"\u{1F501}",repeat_one:"\u{1F502}",arrows_counterclockwise:"\u{1F504}",arrows_clockwise:"\u{1F503}",musical_note:"\u{1F3B5}",notes:"\u{1F3B6}",heavy_plus_sign:"\u2795",heavy_minus_sign:"\u2796",heavy_division_sign:"\u2797",heavy_multiplication_x:"\u2716\uFE0F",heavy_dollar_sign:"\u{1F4B2}",currency_exchange:"\u{1F4B1}",tm:"\u2122\uFE0F",copyright:"\xA9\uFE0F",registered:"\xAE\uFE0F",wavy_dash:"\u3030\uFE0F",curly_loop:"\u27B0",loop:"\u27BF",end:"\u{1F51A}",back:"\u{1F519}",on:"\u{1F51B}",top:"\u{1F51D}",soon:"\u{1F51C}",heavy_check_mark:"\u2714\uFE0F",ballot_box_with_check:"\u2611\uFE0F",radio_button:"\u{1F518}",white_circle:"\u26AA\uFE0F",black_circle:"\u26AB\uFE0F",red_circle:"\u{1F534}",large_blue_circle:"\u{1F535}",small_red_triangle:"\u{1F53A}",small_red_triangle_down:"\u{1F53B}",small_orange_diamond:"\u{1F538}",small_blue_diamond:"\u{1F539}",large_orange_diamond:"\u{1F536}",large_blue_diamond:"\u{1F537}",white_square_button:"\u{1F533}",black_square_button:"\u{1F532}",black_small_square:"\u25AA\uFE0F",white_small_square:"\u25AB\uFE0F",black_medium_small_square:"\u25FE\uFE0F",white_medium_small_square:"\u25FD\uFE0F",black_medium_square:"\u25FC\uFE0F",white_medium_square:"\u25FB\uFE0F",black_large_square:"\u2B1B\uFE0F",white_large_square:"\u2B1C\uFE0F",speaker:"\u{1F508}",mute:"\u{1F507}",sound:"\u{1F509}",loud_sound:"\u{1F50A}",bell:"\u{1F514}",no_bell:"\u{1F515}",mega:"\u{1F4E3}",loudspeaker:"\u{1F4E2}",eye_speech_bubble:"\u{1F441}\u200D\u{1F5E8}",speech_balloon:"\u{1F4AC}",thought_balloon:"\u{1F4AD}",right_anger_bubble:"\u{1F5EF}",spades:"\u2660\uFE0F",clubs:"\u2663\uFE0F",hearts:"\u2665\uFE0F",diamonds:"\u2666\uFE0F",black_joker:"\u{1F0CF}",flower_playing_cards:"\u{1F3B4}",mahjong:"\u{1F004}\uFE0F",clock1:"\u{1F550}",clock2:"\u{1F551}",clock3:"\u{1F552}",clock4:"\u{1F553}",clock5:"\u{1F554}",clock6:"\u{1F555}",clock7:"\u{1F556}",clock8:"\u{1F557}",clock9:"\u{1F558}",clock10:"\u{1F559}",clock11:"\u{1F55A}",clock12:"\u{1F55B}",clock130:"\u{1F55C}",clock230:"\u{1F55D}",clock330:"\u{1F55E}",clock430:"\u{1F55F}",clock530:"\u{1F560}",clock630:"\u{1F561}",clock730:"\u{1F562}",clock830:"\u{1F563}",clock930:"\u{1F564}",clock1030:"\u{1F565}",clock1130:"\u{1F566}",clock1230:"\u{1F567}",white_flag:"\u{1F3F3}\uFE0F",black_flag:"\u{1F3F4}",checkered_flag:"\u{1F3C1}",triangular_flag_on_post:"\u{1F6A9}",rainbow_flag:"\u{1F3F3}\uFE0F\u200D\u{1F308}",afghanistan:"\u{1F1E6}\u{1F1EB}",aland_islands:"\u{1F1E6}\u{1F1FD}",albania:"\u{1F1E6}\u{1F1F1}",algeria:"\u{1F1E9}\u{1F1FF}",american_samoa:"\u{1F1E6}\u{1F1F8}",andorra:"\u{1F1E6}\u{1F1E9}",angola:"\u{1F1E6}\u{1F1F4}",anguilla:"\u{1F1E6}\u{1F1EE}",antarctica:"\u{1F1E6}\u{1F1F6}",antigua_barbuda:"\u{1F1E6}\u{1F1EC}",argentina:"\u{1F1E6}\u{1F1F7}",armenia:"\u{1F1E6}\u{1F1F2}",aruba:"\u{1F1E6}\u{1F1FC}",australia:"\u{1F1E6}\u{1F1FA}",austria:"\u{1F1E6}\u{1F1F9}",azerbaijan:"\u{1F1E6}\u{1F1FF}",bahamas:"\u{1F1E7}\u{1F1F8}",bahrain:"\u{1F1E7}\u{1F1ED}",bangladesh:"\u{1F1E7}\u{1F1E9}",barbados:"\u{1F1E7}\u{1F1E7}",belarus:"\u{1F1E7}\u{1F1FE}",belgium:"\u{1F1E7}\u{1F1EA}",belize:"\u{1F1E7}\u{1F1FF}",benin:"\u{1F1E7}\u{1F1EF}",bermuda:"\u{1F1E7}\u{1F1F2}",bhutan:"\u{1F1E7}\u{1F1F9}",bolivia:"\u{1F1E7}\u{1F1F4}",caribbean_netherlands:"\u{1F1E7}\u{1F1F6}",bosnia_herzegovina:"\u{1F1E7}\u{1F1E6}",botswana:"\u{1F1E7}\u{1F1FC}",brazil:"\u{1F1E7}\u{1F1F7}",british_indian_ocean_territory:"\u{1F1EE}\u{1F1F4}",british_virgin_islands:"\u{1F1FB}\u{1F1EC}",brunei:"\u{1F1E7}\u{1F1F3}",bulgaria:"\u{1F1E7}\u{1F1EC}",burkina_faso:"\u{1F1E7}\u{1F1EB}",burundi:"\u{1F1E7}\u{1F1EE}",cape_verde:"\u{1F1E8}\u{1F1FB}",cambodia:"\u{1F1F0}\u{1F1ED}",cameroon:"\u{1F1E8}\u{1F1F2}",canada:"\u{1F1E8}\u{1F1E6}",canary_islands:"\u{1F1EE}\u{1F1E8}",cayman_islands:"\u{1F1F0}\u{1F1FE}",central_african_republic:"\u{1F1E8}\u{1F1EB}",chad:"\u{1F1F9}\u{1F1E9}",chile:"\u{1F1E8}\u{1F1F1}",cn:"\u{1F1E8}\u{1F1F3}",christmas_island:"\u{1F1E8}\u{1F1FD}",cocos_islands:"\u{1F1E8}\u{1F1E8}",colombia:"\u{1F1E8}\u{1F1F4}",comoros:"\u{1F1F0}\u{1F1F2}",congo_brazzaville:"\u{1F1E8}\u{1F1EC}",congo_kinshasa:"\u{1F1E8}\u{1F1E9}",cook_islands:"\u{1F1E8}\u{1F1F0}",costa_rica:"\u{1F1E8}\u{1F1F7}",cote_divoire:"\u{1F1E8}\u{1F1EE}",croatia:"\u{1F1ED}\u{1F1F7}",cuba:"\u{1F1E8}\u{1F1FA}",curacao:"\u{1F1E8}\u{1F1FC}",cyprus:"\u{1F1E8}\u{1F1FE}",czech_republic:"\u{1F1E8}\u{1F1FF}",denmark:"\u{1F1E9}\u{1F1F0}",djibouti:"\u{1F1E9}\u{1F1EF}",dominica:"\u{1F1E9}\u{1F1F2}",dominican_republic:"\u{1F1E9}\u{1F1F4}",ecuador:"\u{1F1EA}\u{1F1E8}",egypt:"\u{1F1EA}\u{1F1EC}",el_salvador:"\u{1F1F8}\u{1F1FB}",equatorial_guinea:"\u{1F1EC}\u{1F1F6}",eritrea:"\u{1F1EA}\u{1F1F7}",estonia:"\u{1F1EA}\u{1F1EA}",ethiopia:"\u{1F1EA}\u{1F1F9}",eu:"\u{1F1EA}\u{1F1FA}",european_union:"\u{1F1EA}\u{1F1FA}",falkland_islands:"\u{1F1EB}\u{1F1F0}",faroe_islands:"\u{1F1EB}\u{1F1F4}",fiji:"\u{1F1EB}\u{1F1EF}",finland:"\u{1F1EB}\u{1F1EE}",fr:"\u{1F1EB}\u{1F1F7}",french_guiana:"\u{1F1EC}\u{1F1EB}",french_polynesia:"\u{1F1F5}\u{1F1EB}",french_southern_territories:"\u{1F1F9}\u{1F1EB}",gabon:"\u{1F1EC}\u{1F1E6}",gambia:"\u{1F1EC}\u{1F1F2}",georgia:"\u{1F1EC}\u{1F1EA}",de:"\u{1F1E9}\u{1F1EA}",ghana:"\u{1F1EC}\u{1F1ED}",gibraltar:"\u{1F1EC}\u{1F1EE}",greece:"\u{1F1EC}\u{1F1F7}",greenland:"\u{1F1EC}\u{1F1F1}",grenada:"\u{1F1EC}\u{1F1E9}",guadeloupe:"\u{1F1EC}\u{1F1F5}",guam:"\u{1F1EC}\u{1F1FA}",guatemala:"\u{1F1EC}\u{1F1F9}",guernsey:"\u{1F1EC}\u{1F1EC}",guinea:"\u{1F1EC}\u{1F1F3}",guinea_bissau:"\u{1F1EC}\u{1F1FC}",guyana:"\u{1F1EC}\u{1F1FE}",haiti:"\u{1F1ED}\u{1F1F9}",honduras:"\u{1F1ED}\u{1F1F3}",hong_kong:"\u{1F1ED}\u{1F1F0}",hungary:"\u{1F1ED}\u{1F1FA}",iceland:"\u{1F1EE}\u{1F1F8}",india:"\u{1F1EE}\u{1F1F3}",indonesia:"\u{1F1EE}\u{1F1E9}",iran:"\u{1F1EE}\u{1F1F7}",iraq:"\u{1F1EE}\u{1F1F6}",ireland:"\u{1F1EE}\u{1F1EA}",isle_of_man:"\u{1F1EE}\u{1F1F2}",israel:"\u{1F1EE}\u{1F1F1}",it:"\u{1F1EE}\u{1F1F9}",jamaica:"\u{1F1EF}\u{1F1F2}",jp:"\u{1F1EF}\u{1F1F5}",crossed_flags:"\u{1F38C}",jersey:"\u{1F1EF}\u{1F1EA}",jordan:"\u{1F1EF}\u{1F1F4}",kazakhstan:"\u{1F1F0}\u{1F1FF}",kenya:"\u{1F1F0}\u{1F1EA}",kiribati:"\u{1F1F0}\u{1F1EE}",kosovo:"\u{1F1FD}\u{1F1F0}",kuwait:"\u{1F1F0}\u{1F1FC}",kyrgyzstan:"\u{1F1F0}\u{1F1EC}",laos:"\u{1F1F1}\u{1F1E6}",latvia:"\u{1F1F1}\u{1F1FB}",lebanon:"\u{1F1F1}\u{1F1E7}",lesotho:"\u{1F1F1}\u{1F1F8}",liberia:"\u{1F1F1}\u{1F1F7}",libya:"\u{1F1F1}\u{1F1FE}",liechtenstein:"\u{1F1F1}\u{1F1EE}",lithuania:"\u{1F1F1}\u{1F1F9}",luxembourg:"\u{1F1F1}\u{1F1FA}",macau:"\u{1F1F2}\u{1F1F4}",macedonia:"\u{1F1F2}\u{1F1F0}",madagascar:"\u{1F1F2}\u{1F1EC}",malawi:"\u{1F1F2}\u{1F1FC}",malaysia:"\u{1F1F2}\u{1F1FE}",maldives:"\u{1F1F2}\u{1F1FB}",mali:"\u{1F1F2}\u{1F1F1}",malta:"\u{1F1F2}\u{1F1F9}",marshall_islands:"\u{1F1F2}\u{1F1ED}",martinique:"\u{1F1F2}\u{1F1F6}",mauritania:"\u{1F1F2}\u{1F1F7}",mauritius:"\u{1F1F2}\u{1F1FA}",mayotte:"\u{1F1FE}\u{1F1F9}",mexico:"\u{1F1F2}\u{1F1FD}",micronesia:"\u{1F1EB}\u{1F1F2}",moldova:"\u{1F1F2}\u{1F1E9}",monaco:"\u{1F1F2}\u{1F1E8}",mongolia:"\u{1F1F2}\u{1F1F3}",montenegro:"\u{1F1F2}\u{1F1EA}",montserrat:"\u{1F1F2}\u{1F1F8}",morocco:"\u{1F1F2}\u{1F1E6}",mozambique:"\u{1F1F2}\u{1F1FF}",myanmar:"\u{1F1F2}\u{1F1F2}",namibia:"\u{1F1F3}\u{1F1E6}",nauru:"\u{1F1F3}\u{1F1F7}",nepal:"\u{1F1F3}\u{1F1F5}",netherlands:"\u{1F1F3}\u{1F1F1}",new_caledonia:"\u{1F1F3}\u{1F1E8}",new_zealand:"\u{1F1F3}\u{1F1FF}",nicaragua:"\u{1F1F3}\u{1F1EE}",niger:"\u{1F1F3}\u{1F1EA}",nigeria:"\u{1F1F3}\u{1F1EC}",niue:"\u{1F1F3}\u{1F1FA}",norfolk_island:"\u{1F1F3}\u{1F1EB}",northern_mariana_islands:"\u{1F1F2}\u{1F1F5}",north_korea:"\u{1F1F0}\u{1F1F5}",norway:"\u{1F1F3}\u{1F1F4}",oman:"\u{1F1F4}\u{1F1F2}",pakistan:"\u{1F1F5}\u{1F1F0}",palau:"\u{1F1F5}\u{1F1FC}",palestinian_territories:"\u{1F1F5}\u{1F1F8}",panama:"\u{1F1F5}\u{1F1E6}",papua_new_guinea:"\u{1F1F5}\u{1F1EC}",paraguay:"\u{1F1F5}\u{1F1FE}",peru:"\u{1F1F5}\u{1F1EA}",philippines:"\u{1F1F5}\u{1F1ED}",pitcairn_islands:"\u{1F1F5}\u{1F1F3}",poland:"\u{1F1F5}\u{1F1F1}",portugal:"\u{1F1F5}\u{1F1F9}",puerto_rico:"\u{1F1F5}\u{1F1F7}",qatar:"\u{1F1F6}\u{1F1E6}",reunion:"\u{1F1F7}\u{1F1EA}",romania:"\u{1F1F7}\u{1F1F4}",ru:"\u{1F1F7}\u{1F1FA}",rwanda:"\u{1F1F7}\u{1F1FC}",st_barthelemy:"\u{1F1E7}\u{1F1F1}",st_helena:"\u{1F1F8}\u{1F1ED}",st_kitts_nevis:"\u{1F1F0}\u{1F1F3}",st_lucia:"\u{1F1F1}\u{1F1E8}",st_pierre_miquelon:"\u{1F1F5}\u{1F1F2}",st_vincent_grenadines:"\u{1F1FB}\u{1F1E8}",samoa:"\u{1F1FC}\u{1F1F8}",san_marino:"\u{1F1F8}\u{1F1F2}",sao_tome_principe:"\u{1F1F8}\u{1F1F9}",saudi_arabia:"\u{1F1F8}\u{1F1E6}",senegal:"\u{1F1F8}\u{1F1F3}",serbia:"\u{1F1F7}\u{1F1F8}",seychelles:"\u{1F1F8}\u{1F1E8}",sierra_leone:"\u{1F1F8}\u{1F1F1}",singapore:"\u{1F1F8}\u{1F1EC}",sint_maarten:"\u{1F1F8}\u{1F1FD}",slovakia:"\u{1F1F8}\u{1F1F0}",slovenia:"\u{1F1F8}\u{1F1EE}",solomon_islands:"\u{1F1F8}\u{1F1E7}",somalia:"\u{1F1F8}\u{1F1F4}",south_africa:"\u{1F1FF}\u{1F1E6}",south_georgia_south_sandwich_islands:"\u{1F1EC}\u{1F1F8}",kr:"\u{1F1F0}\u{1F1F7}",south_sudan:"\u{1F1F8}\u{1F1F8}",es:"\u{1F1EA}\u{1F1F8}",sri_lanka:"\u{1F1F1}\u{1F1F0}",sudan:"\u{1F1F8}\u{1F1E9}",suriname:"\u{1F1F8}\u{1F1F7}",swaziland:"\u{1F1F8}\u{1F1FF}",sweden:"\u{1F1F8}\u{1F1EA}",switzerland:"\u{1F1E8}\u{1F1ED}",syria:"\u{1F1F8}\u{1F1FE}",taiwan:"\u{1F1F9}\u{1F1FC}",tajikistan:"\u{1F1F9}\u{1F1EF}",tanzania:"\u{1F1F9}\u{1F1FF}",thailand:"\u{1F1F9}\u{1F1ED}",timor_leste:"\u{1F1F9}\u{1F1F1}",togo:"\u{1F1F9}\u{1F1EC}",tokelau:"\u{1F1F9}\u{1F1F0}",tonga:"\u{1F1F9}\u{1F1F4}",trinidad_tobago:"\u{1F1F9}\u{1F1F9}",tunisia:"\u{1F1F9}\u{1F1F3}",tr:"\u{1F1F9}\u{1F1F7}",turkmenistan:"\u{1F1F9}\u{1F1F2}",turks_caicos_islands:"\u{1F1F9}\u{1F1E8}",tuvalu:"\u{1F1F9}\u{1F1FB}",uganda:"\u{1F1FA}\u{1F1EC}",ukraine:"\u{1F1FA}\u{1F1E6}",united_arab_emirates:"\u{1F1E6}\u{1F1EA}",gb:"\u{1F1EC}\u{1F1E7}",uk:"\u{1F1EC}\u{1F1E7}",us:"\u{1F1FA}\u{1F1F8}",us_virgin_islands:"\u{1F1FB}\u{1F1EE}",uruguay:"\u{1F1FA}\u{1F1FE}",uzbekistan:"\u{1F1FA}\u{1F1FF}",vanuatu:"\u{1F1FB}\u{1F1FA}",vatican_city:"\u{1F1FB}\u{1F1E6}",venezuela:"\u{1F1FB}\u{1F1EA}",vietnam:"\u{1F1FB}\u{1F1F3}",wallis_futuna:"\u{1F1FC}\u{1F1EB}",western_sahara:"\u{1F1EA}\u{1F1ED}",yemen:"\u{1F1FE}\u{1F1EA}",zambia:"\u{1F1FF}\u{1F1F2}",zimbabwe:"\u{1F1FF}\u{1F1FC}"}},function(c,d,i){c.exports={angry:[">:(",">:-("],blush:[':")',':-")'],broken_heart:["</3","<\\3"],confused:[":/",":-/"],cry:[":'(",":'-(",":,(",":,-("],frowning:[":(",":-("],heart:["<3"],imp:["]:(","]:-("],innocent:["o:)","O:)","o:-)","O:-)","0:)","0:-)"],joy:[":')",":'-)",":,)",":,-)",":'D",":'-D",":,D",":,-D"],kissing:[":*",":-*"],laughing:["x-)","X-)"],neutral_face:[":|",":-|"],open_mouth:[":o",":-o",":O",":-O"],rage:[":@",":-@"],smile:[":D",":-D"],smiley:[":)",":-)"],smiling_imp:["]:)","]:-)"],sob:[":,'(",":,'-(",";(",";-("],stuck_out_tongue:[":P",":-P"],sunglasses:["8-)","B-)"],sweat:[",:(",",:-("],sweat_smile:[",:)",",:-)"],unamused:[":s",":-S",":z",":-Z",":$",":-$"],wink:[";)",";-)"]}},function(c,d,i){function t(r){return r.replace(/[.?*+^$[\]\\(){}|-]/g,"\\$&")}c.exports=function(r){var e,o=r.defs;r.enabled.length&&(o=Object.keys(o).reduce(function(u,a){return r.enabled.indexOf(a)>=0&&(u[a]=o[a]),u},{})),e=Object.keys(r.shortcuts).reduce(function(u,a){return o[a]?Array.isArray(r.shortcuts[a])?(r.shortcuts[a].forEach(function(p){u[p]=a}),u):(u[r.shortcuts[a]]=a,u):u},{});var n=Object.keys(o).map(function(u){return":"+u+":"}).concat(Object.keys(e)).sort().reverse().map(function(u){return t(u)}).join("|"),s=RegExp(n),l=RegExp(n,"g");return{defs:o,shortcuts:e,scanRE:s,replaceRE:l}}},function(c,d,i){c.exports=function(t,r){return t[r].content}},function(c,d,i){c.exports=function(t,r,e,o,n){function s(p,_,v){var g,C=0,m=[];return p.replace(n,function(h,w,f){var k;if(e.hasOwnProperty(h)){if(k=e[h],w>0&&!a.test(f[w-1])||w+h.length<f.length&&!a.test(f[w+h.length]))return}else k=h.slice(1,-1);w>C&&(g=new v("text","",0),g.content=p.slice(C,w),m.push(g)),g=new v("emoji","",0),g.markup=k,g.content=r[k],m.push(g),C=w+h.length}),C<p.length&&(g=new v("text","",0),g.content=p.slice(C),m.push(g)),m}var l=t.utils.arrayReplaceAt,u=t.utils.lib.ucmicro,a=new RegExp([u.Z.source,u.P.source,u.Cc.source].join("|"));return function(p){var _,v,g,C,m,h=p.tokens,w=0;for(v=0,g=h.length;v<g;v++)if(h[v].type==="inline")for(C=h[v].children,_=C.length-1;_>=0;_--)m=C[_],m.type!=="link_open"&&m.type!=="link_close"||m.info==="auto"&&(w-=m.nesting),m.type==="text"&&w===0&&o.test(m.content)&&(h[v].children=C=l(C,_,s(m.content,m.level,p.Token)))}}},function(c,d,i){function t(a,p,_,v){var g=Number(a[p].meta.id+1).toString(),C="";return typeof v.docId=="string"&&(C="-"+v.docId+"-"),C+g}function r(a,p){var _=Number(a[p].meta.id+1).toString();return a[p].meta.subId>0&&(_+=":"+a[p].meta.subId),"["+_+"]"}function e(a,p,_,v,g){var C=g.rules.footnote_anchor_name(a,p,_,v,g),m=g.rules.footnote_caption(a,p,_,v,g),h=C;return a[p].meta.subId>0&&(h+=":"+a[p].meta.subId),'<sup class="footnote-ref"><a href="#fn'+C+'" id="fnref'+h+'">'+m+"</a></sup>"}function o(a,p,_){return(_.xhtmlOut?`<hr class="footnotes-sep" />
`:`<hr class="footnotes-sep">
`)+`<section class="footnotes">
<ol class="footnotes-list">
`}function n(){return`</ol>
</section>
`}function s(a,p,_,v,g){var C=g.rules.footnote_anchor_name(a,p,_,v,g);return a[p].meta.subId>0&&(C+=":"+a[p].meta.subId),'<li id="fn'+C+'" class="footnote-item">'}function l(){return`</li>
`}function u(a,p,_,v,g){var C=g.rules.footnote_anchor_name(a,p,_,v,g);return a[p].meta.subId>0&&(C+=":"+a[p].meta.subId),' <a href="#fnref'+C+'" class="footnote-backref">\u21A9\uFE0E</a>'}c.exports=function(a){function p(h,w,f,k){var b,y,x,E,D,T,L,A,$,z,j,M=h.bMarks[w]+h.tShift[w],R=h.eMarks[w];if(M+4>R||h.src.charCodeAt(M)!==91||h.src.charCodeAt(M+1)!==94)return!1;for(D=M+2;D<R;D++){if(h.src.charCodeAt(D)===32)return!1;if(h.src.charCodeAt(D)===93)break}if(D===M+2||D+1>=R||h.src.charCodeAt(++D)!==58)return!1;if(k)return!0;for(D++,h.env.footnotes||(h.env.footnotes={}),h.env.footnotes.refs||(h.env.footnotes.refs={}),T=h.src.slice(M+2,D-2),h.env.footnotes.refs[":"+T]=-1,L=new h.Token("footnote_reference_open","",1),L.meta={label:T},L.level=h.level++,h.tokens.push(L),b=h.bMarks[w],y=h.tShift[w],x=h.sCount[w],E=h.parentType,j=D,A=$=h.sCount[w]+D-(h.bMarks[w]+h.tShift[w]);D<R&&(z=h.src.charCodeAt(D),m(z));)z===9?$+=4-$%4:$++,D++;return h.tShift[w]=D-j,h.sCount[w]=$-A,h.bMarks[w]=j,h.blkIndent+=4,h.parentType="footnote",h.sCount[w]<h.blkIndent&&(h.sCount[w]+=h.blkIndent),h.md.block.tokenize(h,w,f,!0),h.parentType=E,h.blkIndent-=4,h.tShift[w]=y,h.sCount[w]=x,h.bMarks[w]=b,L=new h.Token("footnote_reference_close","",-1),L.level=--h.level,h.tokens.push(L),!0}function _(h,w){var f,k,b,y,x,E=h.posMax,D=h.pos;return!(D+2>=E)&&h.src.charCodeAt(D)===94&&h.src.charCodeAt(D+1)===91&&(f=D+2,!((k=C(h,D+1))<0)&&(w||(h.env.footnotes||(h.env.footnotes={}),h.env.footnotes.list||(h.env.footnotes.list=[]),b=h.env.footnotes.list.length,h.md.inline.parse(h.src.slice(f,k),h.md,h.env,x=[]),y=h.push("footnote_ref","",0),y.meta={id:b},h.env.footnotes.list[b]={content:h.src.slice(f,k),tokens:x}),h.pos=k+1,h.posMax=E,!0))}function v(h,w){var f,k,b,y,x,E=h.posMax,D=h.pos;if(D+3>E||!h.env.footnotes||!h.env.footnotes.refs||h.src.charCodeAt(D)!==91||h.src.charCodeAt(D+1)!==94)return!1;for(k=D+2;k<E;k++){if(h.src.charCodeAt(k)===32||h.src.charCodeAt(k)===10)return!1;if(h.src.charCodeAt(k)===93)break}return k!==D+2&&!(k>=E)&&(k++,f=h.src.slice(D+2,k-1),h.env.footnotes.refs[":"+f]!==void 0&&(w||(h.env.footnotes.list||(h.env.footnotes.list=[]),h.env.footnotes.refs[":"+f]<0?(b=h.env.footnotes.list.length,h.env.footnotes.list[b]={label:f,count:0},h.env.footnotes.refs[":"+f]=b):b=h.env.footnotes.refs[":"+f],y=h.env.footnotes.list[b].count,h.env.footnotes.list[b].count++,x=h.push("footnote_ref","",0),x.meta={id:b,subId:y,label:f}),h.pos=k,h.posMax=E,!0))}function g(h){var w,f,k,b,y,x,E,D,T,L,A=!1,$={};if(h.env.footnotes&&(h.tokens=h.tokens.filter(function(z){return z.type==="footnote_reference_open"?(A=!0,T=[],L=z.meta.label,!1):z.type==="footnote_reference_close"?(A=!1,$[":"+L]=T,!1):(A&&T.push(z),!A)}),h.env.footnotes.list)){for(x=h.env.footnotes.list,E=new h.Token("footnote_block_open","",1),h.tokens.push(E),w=0,f=x.length;w<f;w++){for(E=new h.Token("footnote_open","",1),E.meta={id:w,label:x[w].label},h.tokens.push(E),x[w].tokens?(D=[],E=new h.Token("paragraph_open","p",1),E.block=!0,D.push(E),E=new h.Token("inline","",0),E.children=x[w].tokens,E.content=x[w].content,D.push(E),E=new h.Token("paragraph_close","p",-1),E.block=!0,D.push(E)):x[w].label&&(D=$[":"+x[w].label]),h.tokens=h.tokens.concat(D),y=h.tokens[h.tokens.length-1].type==="paragraph_close"?h.tokens.pop():null,b=x[w].count>0?x[w].count:1,k=0;k<b;k++)E=new h.Token("footnote_anchor","",0),E.meta={id:w,subId:k,label:x[w].label},h.tokens.push(E);y&&h.tokens.push(y),E=new h.Token("footnote_close","",-1),h.tokens.push(E)}E=new h.Token("footnote_block_close","",-1),h.tokens.push(E)}}var C=a.helpers.parseLinkLabel,m=a.utils.isSpace;a.renderer.rules.footnote_ref=e,a.renderer.rules.footnote_block_open=o,a.renderer.rules.footnote_block_close=n,a.renderer.rules.footnote_open=s,a.renderer.rules.footnote_close=l,a.renderer.rules.footnote_anchor=u,a.renderer.rules.footnote_caption=r,a.renderer.rules.footnote_anchor_name=t,a.block.ruler.before("reference","footnote_def",p,{alt:["paragraph","reference"]}),a.inline.ruler.after("image","footnote_inline",_),a.inline.ruler.after("footnote_inline","footnote_ref",v),a.core.ruler.after("inline","footnote_tail",g)}},function(c,d){var i=function(t,r){r=r||{},r.highlighted===void 0&&(r.highlighted=!0),r.hljs===void 0&&(r.hljs="auto"),typeof r.langCheck!="function"&&(r.langCheck=function(){}),t.options.highlight=function(e,o){var n=r.hljs;if(r.hljs==="auto"&&(n=window.hljs),r.highlighted&&o&&n){if(n.getLanguage(o))return'<pre><div class="hljs"><code class="'+t.options.langPrefix+o+'">'+n.highlight(o,e,!0).value+"</code></div></pre>";typeof r.langCheck=="function"&&r.langCheck(o)}return'<pre><code class="'+t.options.langPrefix+o+'">'+t.utils.escapeHtml(e)+"</code></pre>"}};c.exports=i},function(c,d){c.exports=function(i,t){i.image_add=function(e,o){i.__image instanceof Object||(i.__image={}),i.__image[e]=o},i.image_del=function(e){i.__image instanceof Object||(i.__image={}),delete i.__image[e]};var r=i.renderer.rules.image;i.renderer.rules.image=function(e,o,n,s,l){var u=e[o].attrs;if(i.__image instanceof Object){for(var a=0;a<u.length;a++)if(u[a][0]=="src"&&i.__image.hasOwnProperty(e[o].attrs[a][1])){u.push(["rel",u[a][1]]),u[a][1]=i.__image[e[o].attrs[a][1]];break}}return r(e,o,n,s,l)}}},function(c,d,i){c.exports=function(t){function r(o,n){var s,l,u,a,p,_=o.pos,v=o.src.charCodeAt(_);if(n||v!==43||(l=o.scanDelims(o.pos,!0),a=l.length,p=String.fromCharCode(v),a<2))return!1;for(a%2&&(u=o.push("text","",0),u.content=p,a--),s=0;s<a;s+=2)u=o.push("text","",0),u.content=p+p,o.delimiters.push({marker:v,jump:s,token:o.tokens.length-1,level:o.level,end:-1,open:l.can_open,close:l.can_close});return o.pos+=l.length,!0}function e(o){var n,s,l,u,a,p=[],_=o.delimiters,v=o.delimiters.length;for(n=0;n<v;n++)l=_[n],l.marker===43&&l.end!==-1&&(u=_[l.end],a=o.tokens[l.token],a.type="ins_open",a.tag="ins",a.nesting=1,a.markup="++",a.content="",a=o.tokens[u.token],a.type="ins_close",a.tag="ins",a.nesting=-1,a.markup="++",a.content="",o.tokens[u.token-1].type==="text"&&o.tokens[u.token-1].content==="+"&&p.push(u.token-1));for(;p.length;){for(n=p.pop(),s=n+1;s<o.tokens.length&&o.tokens[s].type==="ins_close";)s++;s--,n!==s&&(a=o.tokens[s],o.tokens[s]=o.tokens[n],o.tokens[n]=a)}}t.inline.ruler.before("emphasis","ins",r),t.inline.ruler2.before("emphasis","ins",e)}},function(c,d,i){function t(n,s){var l,u,a=n.posMax,p=!0,_=!0;return l=s>0?n.src.charCodeAt(s-1):-1,u=s+1<=a?n.src.charCodeAt(s+1):-1,(l===32||l===9||u>=48&&u<=57)&&(_=!1),u!==32&&u!==9||(p=!1),{can_open:p,can_close:_}}function r(n,s){if(!o&&window.katex&&(o=window.katex),!o)return!1;var l,u,a,p,_;if(n.src[n.pos]!=="$")return!1;if(p=t(n,n.pos),!p.can_open)return s||(n.pending+="$"),n.pos+=1,!0;for(l=n.pos+1,u=l;(u=n.src.indexOf("$",u))!==-1;){for(_=u-1;n.src[_]==="\\";)_-=1;if((u-_)%2==1)break;u+=1}return u===-1?(s||(n.pending+="$"),n.pos=l,!0):u-l==0?(s||(n.pending+="$$"),n.pos=l+1,!0):(p=t(n,u),p.can_close?(s||(a=n.push("math_inline","math",0),a.markup="$",a.content=n.src.slice(l,u)),n.pos=u+1,!0):(s||(n.pending+="$"),n.pos=l,!0))}function e(n,s,l,u){if(!o&&window.katex&&(o=window.katex),!o)return!1;var a,p,_,v,g,C=!1,m=n.bMarks[s]+n.tShift[s],h=n.eMarks[s];if(m+2>h||n.src.slice(m,m+2)!=="$$")return!1;if(m+=2,a=n.src.slice(m,h),u)return!0;for(a.trim().slice(-2)==="$$"&&(a=a.trim().slice(0,-2),C=!0),_=s;!C&&!(++_>=l)&&(m=n.bMarks[_]+n.tShift[_],h=n.eMarks[_],!(m<h&&n.tShift[_]<n.blkIndent));)n.src.slice(m,h).trim().slice(-2)==="$$"&&(v=n.src.slice(0,h).lastIndexOf("$$"),p=n.src.slice(m,v),C=!0);return n.line=_+1,g=n.push("math_block","math",0),g.block=!0,g.content=(a&&a.trim()?a+`
`:"")+n.getLines(s+1,_,n.tShift[s],!0)+(p&&p.trim()?p:""),g.map=[s,n.line],g.markup="$$",!0}var o=null;c.exports=function(n,s){s=s||{};var l=function(_){!o&&window.katex&&(o=window.katex),s.displayMode=!1;try{return o.renderToString(_,s)}catch(v){return s.throwOnError&&console.log(v),_}},u=function(_,v){return l(_[v].content)},a=function(_){!o&&window.katex&&(o=window.katex),s.displayMode=!0;try{return"<p>"+o.renderToString(_,s)+"</p>"}catch(v){return s.throwOnError&&console.log(v),_}},p=function(_,v){return a(_[v].content)+`
`};n.inline.ruler.after("escape","math_inline",r),n.block.ruler.after("blockquote","math_block",e,{alt:["paragraph","reference","blockquote","list"]}),n.renderer.rules.math_inline=u,n.renderer.rules.math_block=p}},function(c,d,i){c.exports=function(t){function r(o,n){var s,l,u,a,p,_=o.pos,v=o.src.charCodeAt(_);if(n||v!==61||(l=o.scanDelims(o.pos,!0),a=l.length,p=String.fromCharCode(v),a<2))return!1;for(a%2&&(u=o.push("text","",0),u.content=p,a--),s=0;s<a;s+=2)u=o.push("text","",0),u.content=p+p,o.delimiters.push({marker:v,jump:s,token:o.tokens.length-1,level:o.level,end:-1,open:l.can_open,close:l.can_close});return o.pos+=l.length,!0}function e(o){var n,s,l,u,a,p=[],_=o.delimiters,v=o.delimiters.length;for(n=0;n<v;n++)l=_[n],l.marker===61&&l.end!==-1&&(u=_[l.end],a=o.tokens[l.token],a.type="mark_open",a.tag="mark",a.nesting=1,a.markup="==",a.content="",a=o.tokens[u.token],a.type="mark_close",a.tag="mark",a.nesting=-1,a.markup="==",a.content="",o.tokens[u.token-1].type==="text"&&o.tokens[u.token-1].content==="="&&p.push(u.token-1));for(;p.length;){for(n=p.pop(),s=n+1;s<o.tokens.length&&o.tokens[s].type==="mark_close";)s++;s--,n!==s&&(a=o.tokens[s],o.tokens[s]=o.tokens[n],o.tokens[n]=a)}}t.inline.ruler.before("emphasis","mark",r),t.inline.ruler2.before("emphasis","mark",e)}},function(c,d,i){function t(e,o){var n,s,l,u=e.posMax,a=e.pos;if(e.src.charCodeAt(a)!==126||o||a+2>=u)return!1;for(e.pos=a+1;e.pos<u;){if(e.src.charCodeAt(e.pos)===126){n=!0;break}e.md.inline.skipToken(e)}return n&&a+1!==e.pos?(s=e.src.slice(a+1,e.pos),s.match(/(^|[^\\])(\\\\)*\s/)?(e.pos=a,!1):(e.posMax=e.pos,e.pos=a+1,l=e.push("sub_open","sub",1),l.markup="~",l=e.push("text","",0),l.content=s.replace(r,"$1"),l=e.push("sub_close","sub",-1),l.markup="~",e.pos=e.posMax+1,e.posMax=u,!0)):(e.pos=a,!1)}var r=/\\([ \\!"#$%&'()*+,.\/:;<=>?@[\]^_`{|}~-])/g;c.exports=function(e){e.inline.ruler.after("emphasis","sub",t)}},function(c,d,i){function t(e,o){var n,s,l,u=e.posMax,a=e.pos;if(e.src.charCodeAt(a)!==94||o||a+2>=u)return!1;for(e.pos=a+1;e.pos<u;){if(e.src.charCodeAt(e.pos)===94){n=!0;break}e.md.inline.skipToken(e)}return n&&a+1!==e.pos?(s=e.src.slice(a+1,e.pos),s.match(/(^|[^\\])(\\\\)*\s/)?(e.pos=a,!1):(e.posMax=e.pos,e.pos=a+1,l=e.push("sup_open","sup",1),l.markup="^",l=e.push("text","",0),l.content=s.replace(r,"$1"),l=e.push("sup_close","sup",-1),l.markup="^",e.pos=e.posMax+1,e.posMax=u,!0)):(e.pos=a,!1)}var r=/\\([ \\!"#$%&'()*+,.\/:;<=>?@[\]^_`{|}~-])/g;c.exports=function(e){e.inline.ruler.after("emphasis","sup",t)}},function(c,d){function i(m,h,w){var f=m.attrIndex(h),k=[h,w];f<0?m.attrPush(k):m.attrs[f]=k}function t(m,h){for(var w=m[h].level-1,f=h-1;f>=0;f--)if(m[f].level===w)return f;return-1}function r(m,h){return u(m[h])&&a(m[h-1])&&p(m[h-2])&&_(m[h])}function e(m,h){if(m.children.unshift(o(m,h)),m.children[1].content=m.children[1].content.slice(3),m.content=m.content.slice(3),g)if(C){m.children.pop();var w="task-item-"+Math.ceil(1e7*Math.random()-1e3);m.children[0].content=m.children[0].content.slice(0,-1)+' id="'+w+'">',m.children.push(l(m.content,w,h))}else m.children.unshift(n(h)),m.children.push(s(h))}function o(m,h){var w=new h("html_inline","",0),f=v?' disabled="" ':"";return m.content.indexOf("[ ] ")===0?w.content='<input class="task-list-item-checkbox"'+f+'type="checkbox">':m.content.indexOf("[x] ")!==0&&m.content.indexOf("[X] ")!==0||(w.content='<input class="task-list-item-checkbox" checked=""'+f+'type="checkbox">'),w}function n(m){var h=new m("html_inline","",0);return h.content="<label>",h}function s(m){var h=new m("html_inline","",0);return h.content="</label>",h}function l(m,h,w){var f=new w("html_inline","",0);return f.content='<label class="task-list-item-label" for="'+h+'">'+m+"</label>",f.attrs=[{for:h}],f}function u(m){return m.type==="inline"}function a(m){return m.type==="paragraph_open"}function p(m){return m.type==="list_item_open"}function _(m){return m.content.indexOf("[ ] ")===0||m.content.indexOf("[x] ")===0||m.content.indexOf("[X] ")===0}var v=!0,g=!1,C=!1;c.exports=function(m,h){h&&(v=!h.enabled,g=!!h.label,C=!!h.labelAfter),m.core.ruler.after("inline","github-task-lists",function(w){for(var f=w.tokens,k=2;k<f.length;k++)r(f,k)&&(e(f[k],w.Token),i(f[k-2],"class","task-list-item"+(v?"":" enabled")),i(f[t(f,k-2)],"class","contains-task-list"))})}},function(c,d,i){c.exports=function(t){function r(l,u){for(;l.src.indexOf(`
`)>=0&&l.src.indexOf(`
`)<l.src.indexOf("@[toc]");)l.tokens.slice(-1)[0].type==="softbreak"&&(l.src=l.src.split(`
`).slice(1).join(`
`),l.pos=0);var a;if(l.src.charCodeAt(l.pos)!==64||l.src.charCodeAt(l.pos+1)!==91)return!1;var p=o.exec(l.src);if(!p||(p=p.filter(function(C){return C}),p.length<1)||u)return!1;a=l.push("toc_open","toc",1),a.markup="@[toc]",a=l.push("toc_body","",0);var _=n;p.length>1&&(_=p.pop()),a.content=_,a=l.push("toc_close","toc",-1);var v=0,g=l.src.indexOf(`
`);return v=g!==-1?l.pos+g:l.pos+l.posMax+1,l.pos=v,!0}var e,o=/^@\[toc\](?:\((?:\s+)?([^\)]+)(?:\s+)?\)?)?(?:\s+?)?$/im,n="Table of Contents",s=function(l){return l.replace(/[^\w\s]/gi,"").split(" ").join("_")};t.renderer.rules.heading_open=function(l,u){var a=l[u].tag,p=l[u+1];return p.type==="inline"?"<"+a+'><a id="'+(s(p.content)+"_"+p.map[0])+'"></a>':"</h1>"},t.renderer.rules.toc_open=function(l,u){return""},t.renderer.rules.toc_close=function(l,u){return""},t.renderer.rules.toc_body=function(l,u){for(var a=[],p=e.tokens,_=p.length,v=0;v<_;v++)if(p[v].type==="heading_close"){var g=p[v],C=p[v-1];C.type==="inline"&&a.push({level:+g.tag.substr(1,1),anchor:s(C.content)+"_"+C.map[0],content:C.content})}var m=0,h=a.map(function(w){var f=[];if(w.level>m)for(var k=w.level-m,b=0;b<k;b++)f.push("<ul>"),m++;else if(w.level<m)for(var k=m-w.level,b=0;b<k;b++)f.push("</ul>"),m--;return f=f.concat(['<li><a href="#',w.anchor,'">',w.content,"</a></li>"]),f.join("")});return"<h3>"+l[u].content+"</h3>"+h.join("")+new Array(m+1).join("</ul>")},t.core.ruler.push("grab_state",function(l){e=l}),t.inline.ruler.after("emphasis","toc",r)}},function(c,d,i){c.exports=i(142)},function(c,d,i){c.exports=["address","article","aside","base","basefont","blockquote","body","caption","center","col","colgroup","dd","details","dialog","dir","div","dl","dt","fieldset","figcaption","figure","footer","form","frame","frameset","h1","h2","h3","h4","h5","h6","head","header","hr","html","iframe","legend","li","link","main","menu","menuitem","meta","nav","noframes","ol","optgroup","option","p","param","section","source","summary","table","tbody","td","tfoot","th","thead","title","tr","track","ul"]},function(c,d,i){d.parseLinkLabel=i(140),d.parseLinkDestination=i(139),d.parseLinkTitle=i(141)},function(c,d,i){var t=i(0).unescapeAll;c.exports=function(r,e,o){var n,s,l=e,u={ok:!1,pos:0,lines:0,str:""};if(r.charCodeAt(e)===60){for(e++;e<o;){if((n=r.charCodeAt(e))===10)return u;if(n===62)return u.pos=e+1,u.str=t(r.slice(l+1,e)),u.ok=!0,u;n===92&&e+1<o?e+=2:e++}return u}for(s=0;e<o&&(n=r.charCodeAt(e))!==32&&!(n<32||n===127);)if(n===92&&e+1<o)e+=2;else{if(n===40&&s++,n===41){if(s===0)break;s--}e++}return l===e||s!==0||(u.str=t(r.slice(l,e)),u.lines=0,u.pos=e,u.ok=!0),u}},function(c,d,i){c.exports=function(t,r,e){var o,n,s,l,u=-1,a=t.posMax,p=t.pos;for(t.pos=r+1,o=1;t.pos<a;){if((s=t.src.charCodeAt(t.pos))===93&&--o==0){n=!0;break}if(l=t.pos,t.md.inline.skipToken(t),s===91){if(l===t.pos-1)o++;else if(e)return t.pos=p,-1}}return n&&(u=t.pos),t.pos=p,u}},function(c,d,i){var t=i(0).unescapeAll;c.exports=function(r,e,o){var n,s,l=0,u=e,a={ok:!1,pos:0,lines:0,str:""};if(e>=o||(s=r.charCodeAt(e))!==34&&s!==39&&s!==40)return a;for(e++,s===40&&(s=41);e<o;){if((n=r.charCodeAt(e))===s)return a.pos=e+1,a.lines=l,a.str=t(r.slice(u+1,e)),a.ok=!0,a;n===10?l++:n===92&&e+1<o&&(e++,r.charCodeAt(e)===10&&l++),e++}return a}},function(c,d,i){function t(f){var k=f.trim().toLowerCase();return!m.test(k)||!!h.test(k)}function r(f){var k=v.parse(f,!0);if(k.hostname&&(!k.protocol||w.indexOf(k.protocol)>=0))try{k.hostname=g.toASCII(k.hostname)}catch{}return v.encode(v.format(k))}function e(f){var k=v.parse(f,!0);if(k.hostname&&(!k.protocol||w.indexOf(k.protocol)>=0))try{k.hostname=g.toUnicode(k.hostname)}catch{}return v.decode(v.format(k))}function o(f,k){if(!(this instanceof o))return new o(f,k);k||n.isString(f)||(k=f||{},f="default"),this.inline=new p,this.block=new a,this.core=new u,this.renderer=new l,this.linkify=new _,this.validateLink=t,this.normalizeLink=r,this.normalizeLinkText=e,this.utils=n,this.helpers=n.assign({},s),this.options={},this.configure(f),k&&this.set(k)}var n=i(0),s=i(138),l=i(149),u=i(144),a=i(143),p=i(145),_=i(115),v=i(57),g=i(186),C={default:i(147),zero:i(148),commonmark:i(146)},m=/^(vbscript|javascript|file|data):/,h=/^data:image\/(gif|png|jpeg|webp);/,w=["http:","https:","mailto:"];o.prototype.set=function(f){return n.assign(this.options,f),this},o.prototype.configure=function(f){var k,b=this;if(n.isString(f)&&(k=f,!(f=C[k])))throw new Error('Wrong `markdown-it` preset "'+k+'", check name');if(!f)throw new Error("Wrong `markdown-it` preset, can't be empty");return f.options&&b.set(f.options),f.components&&Object.keys(f.components).forEach(function(y){f.components[y].rules&&b[y].ruler.enableOnly(f.components[y].rules),f.components[y].rules2&&b[y].ruler2.enableOnly(f.components[y].rules2)}),this},o.prototype.enable=function(f,k){var b=[];Array.isArray(f)||(f=[f]),["core","block","inline"].forEach(function(x){b=b.concat(this[x].ruler.enable(f,!0))},this),b=b.concat(this.inline.ruler2.enable(f,!0));var y=f.filter(function(x){return b.indexOf(x)<0});if(y.length&&!k)throw new Error("MarkdownIt. Failed to enable unknown rule(s): "+y);return this},o.prototype.disable=function(f,k){var b=[];Array.isArray(f)||(f=[f]),["core","block","inline"].forEach(function(x){b=b.concat(this[x].ruler.disable(f,!0))},this),b=b.concat(this.inline.ruler2.disable(f,!0));var y=f.filter(function(x){return b.indexOf(x)<0});if(y.length&&!k)throw new Error("MarkdownIt. Failed to disable unknown rule(s): "+y);return this},o.prototype.use=function(f){var k=[this].concat(Array.prototype.slice.call(arguments,1));return f.apply(f,k),this},o.prototype.parse=function(f,k){if(typeof f!="string")throw new Error("Input data should be a String");var b=new this.core.State(f,this,k);return this.core.process(b),b.tokens},o.prototype.render=function(f,k){return k=k||{},this.renderer.render(this.parse(f,k),this.options,k)},o.prototype.parseInline=function(f,k){var b=new this.core.State(f,this,k);return b.inlineMode=!0,this.core.process(b),b.tokens},o.prototype.renderInline=function(f,k){return k=k||{},this.renderer.render(this.parseInline(f,k),this.options,k)},c.exports=o},function(c,d,i){function t(){this.ruler=new r;for(var o=0;o<e.length;o++)this.ruler.push(e[o][0],e[o][1],{alt:(e[o][2]||[]).slice()})}var r=i(32),e=[["table",i(161),["paragraph","reference"]],["code",i(151)],["fence",i(152),["paragraph","reference","blockquote","list"]],["blockquote",i(150),["paragraph","reference","blockquote","list"]],["hr",i(154),["paragraph","reference","blockquote","list"]],["list",i(157),["paragraph","reference","blockquote"]],["reference",i(159)],["heading",i(153),["paragraph","reference","blockquote"]],["lheading",i(156)],["html_block",i(155),["paragraph","reference","blockquote"]],["paragraph",i(158)]];t.prototype.tokenize=function(o,n,s){for(var l,u=this.ruler.getRules(""),a=u.length,p=n,_=!1,v=o.md.options.maxNesting;p<s&&(o.line=p=o.skipEmptyLines(p),!(p>=s))&&!(o.sCount[p]<o.blkIndent);){if(o.level>=v){o.line=s;break}for(l=0;l<a&&!u[l](o,p,s,!1);l++);o.tight=!_,o.isEmpty(o.line-1)&&(_=!0),(p=o.line)<s&&o.isEmpty(p)&&(_=!0,p++,o.line=p)}},t.prototype.parse=function(o,n,s,l){var u;o&&(u=new this.State(o,n,s,l),this.tokenize(u,u.line,u.lineMax))},t.prototype.State=i(160),c.exports=t},function(c,d,i){function t(){this.ruler=new r;for(var o=0;o<e.length;o++)this.ruler.push(e[o][0],e[o][1])}var r=i(32),e=[["normalize",i(165)],["block",i(162)],["inline",i(163)],["linkify",i(164)],["replacements",i(166)],["smartquotes",i(167)]];t.prototype.process=function(o){var n,s,l;for(l=this.ruler.getRules(""),n=0,s=l.length;n<s;n++)l[n](o)},t.prototype.State=i(168),c.exports=t},function(c,d,i){function t(){var n;for(this.ruler=new r,n=0;n<e.length;n++)this.ruler.push(e[n][0],e[n][1]);for(this.ruler2=new r,n=0;n<o.length;n++)this.ruler2.push(o[n][0],o[n][1])}var r=i(32),e=[["text",i(179)],["newline",i(177)],["escape",i(173)],["backticks",i(170)],["strikethrough",i(56).tokenize],["emphasis",i(55).tokenize],["link",i(176)],["image",i(175)],["autolink",i(169)],["html_inline",i(174)],["entity",i(172)]],o=[["balance_pairs",i(171)],["strikethrough",i(56).postProcess],["emphasis",i(55).postProcess],["text_collapse",i(180)]];t.prototype.skipToken=function(n){var s,l,u=n.pos,a=this.ruler.getRules(""),p=a.length,_=n.md.options.maxNesting,v=n.cache;if(v[u]!==void 0)return void(n.pos=v[u]);if(n.level<_)for(l=0;l<p&&(n.level++,s=a[l](n,!0),n.level--,!s);l++);else n.pos=n.posMax;s||n.pos++,v[u]=n.pos},t.prototype.tokenize=function(n){for(var s,l,u=this.ruler.getRules(""),a=u.length,p=n.posMax,_=n.md.options.maxNesting;n.pos<p;){if(n.level<_)for(l=0;l<a&&!(s=u[l](n,!1));l++);if(s){if(n.pos>=p)break}else n.pending+=n.src[n.pos++]}n.pending&&n.pushPending()},t.prototype.parse=function(n,s,l,u){var a,p,_,v=new this.State(n,s,l,u);for(this.tokenize(v),p=this.ruler2.getRules(""),_=p.length,a=0;a<_;a++)p[a](v)},t.prototype.State=i(178),c.exports=t},function(c,d,i){c.exports={options:{html:!0,xhtmlOut:!0,breaks:!1,langPrefix:"language-",linkify:!1,typographer:!1,quotes:"\u201C\u201D\u2018\u2019",highlight:null,maxNesting:20},components:{core:{rules:["normalize","block","inline"]},block:{rules:["blockquote","code","fence","heading","hr","html_block","lheading","list","reference","paragraph"]},inline:{rules:["autolink","backticks","emphasis","entity","escape","html_inline","image","link","newline","text"],rules2:["balance_pairs","emphasis","text_collapse"]}}}},function(c,d,i){c.exports={options:{html:!1,xhtmlOut:!1,breaks:!1,langPrefix:"language-",linkify:!1,typographer:!1,quotes:"\u201C\u201D\u2018\u2019",highlight:null,maxNesting:100},components:{core:{},block:{},inline:{}}}},function(c,d,i){c.exports={options:{html:!1,xhtmlOut:!1,breaks:!1,langPrefix:"language-",linkify:!1,typographer:!1,quotes:"\u201C\u201D\u2018\u2019",highlight:null,maxNesting:20},components:{core:{rules:["normalize","block","inline"]},block:{rules:["paragraph"]},inline:{rules:["text"],rules2:["balance_pairs","text_collapse"]}}}},function(c,d,i){function t(){this.rules=r({},n)}var r=i(0).assign,e=i(0).unescapeAll,o=i(0).escapeHtml,n={};n.code_inline=function(s,l,u,a,p){var _=s[l];return"<code"+p.renderAttrs(_)+">"+o(s[l].content)+"</code>"},n.code_block=function(s,l,u,a,p){var _=s[l];return"<pre"+p.renderAttrs(_)+"><code>"+o(s[l].content)+`</code></pre>
`},n.fence=function(s,l,u,a,p){var _,v,g,C,m=s[l],h=m.info?e(m.info).trim():"",w="";return h&&(w=h.split(/\s+/g)[0]),_=u.highlight&&u.highlight(m.content,w)||o(m.content),_.indexOf("<pre")===0?_+`
`:h?(v=m.attrIndex("class"),g=m.attrs?m.attrs.slice():[],v<0?g.push(["class",u.langPrefix+w]):g[v][1]+=" "+u.langPrefix+w,C={attrs:g},"<pre><code"+p.renderAttrs(C)+">"+_+`</code></pre>
`):"<pre><code"+p.renderAttrs(m)+">"+_+`</code></pre>
`},n.image=function(s,l,u,a,p){var _=s[l];return _.attrs[_.attrIndex("alt")][1]=p.renderInlineAsText(_.children,u,a),p.renderToken(s,l,u)},n.hardbreak=function(s,l,u){return u.xhtmlOut?`<br />
`:`<br>
`},n.softbreak=function(s,l,u){return u.breaks?u.xhtmlOut?`<br />
`:`<br>
`:`
`},n.text=function(s,l){return o(s[l].content)},n.html_block=function(s,l){return s[l].content},n.html_inline=function(s,l){return s[l].content},t.prototype.renderAttrs=function(s){var l,u,a;if(!s.attrs)return"";for(a="",l=0,u=s.attrs.length;l<u;l++)a+=" "+o(s.attrs[l][0])+'="'+o(s.attrs[l][1])+'"';return a},t.prototype.renderToken=function(s,l,u){var a,p="",_=!1,v=s[l];return v.hidden?"":(v.block&&v.nesting!==-1&&l&&s[l-1].hidden&&(p+=`
`),p+=(v.nesting===-1?"</":"<")+v.tag,p+=this.renderAttrs(v),v.nesting===0&&u.xhtmlOut&&(p+=" /"),v.block&&(_=!0,v.nesting===1&&l+1<s.length&&(a=s[l+1],(a.type==="inline"||a.hidden||a.nesting===-1&&a.tag===v.tag)&&(_=!1))),p+=_?`>
`:">")},t.prototype.renderInline=function(s,l,u){for(var a,p="",_=this.rules,v=0,g=s.length;v<g;v++)a=s[v].type,_[a]!==void 0?p+=_[a](s,v,l,u,this):p+=this.renderToken(s,v,l);return p},t.prototype.renderInlineAsText=function(s,l,u){for(var a="",p=0,_=s.length;p<_;p++)s[p].type==="text"?a+=s[p].content:s[p].type==="image"&&(a+=this.renderInlineAsText(s[p].children,l,u));return a},t.prototype.render=function(s,l,u){var a,p,_,v="",g=this.rules;for(a=0,p=s.length;a<p;a++)_=s[a].type,_==="inline"?v+=this.renderInline(s[a].children,l,u):g[_]!==void 0?v+=g[s[a].type](s,a,l,u,this):v+=this.renderToken(s,a,l,u);return v},c.exports=t},function(c,d,i){var t=i(0).isSpace;c.exports=function(r,e,o,n){var s,l,u,a,p,_,v,g,C,m,h,w,f,k,b,y,x,E,D,T,L=r.lineMax,A=r.bMarks[e]+r.tShift[e],$=r.eMarks[e];if(r.sCount[e]-r.blkIndent>=4||r.src.charCodeAt(A++)!==62)return!1;if(n)return!0;for(a=C=r.sCount[e]+A-(r.bMarks[e]+r.tShift[e]),r.src.charCodeAt(A)===32?(A++,a++,C++,s=!1,y=!0):r.src.charCodeAt(A)===9?(y=!0,(r.bsCount[e]+C)%4==3?(A++,a++,C++,s=!1):s=!0):y=!1,m=[r.bMarks[e]],r.bMarks[e]=A;A<$&&(l=r.src.charCodeAt(A),t(l));)l===9?C+=4-(C+r.bsCount[e]+(s?1:0))%4:C++,A++;for(h=[r.bsCount[e]],r.bsCount[e]=r.sCount[e]+1+(y?1:0),_=A>=$,k=[r.sCount[e]],r.sCount[e]=C-a,b=[r.tShift[e]],r.tShift[e]=A-r.bMarks[e],E=r.md.block.ruler.getRules("blockquote"),f=r.parentType,r.parentType="blockquote",T=!1,g=e+1;g<o&&(r.sCount[g]<r.blkIndent&&(T=!0),A=r.bMarks[g]+r.tShift[g],$=r.eMarks[g],!(A>=$));g++)if(r.src.charCodeAt(A++)!==62||T){if(_)break;for(x=!1,u=0,p=E.length;u<p;u++)if(E[u](r,g,o,!0)){x=!0;break}if(x){r.lineMax=g,r.blkIndent!==0&&(m.push(r.bMarks[g]),h.push(r.bsCount[g]),b.push(r.tShift[g]),k.push(r.sCount[g]),r.sCount[g]-=r.blkIndent);break}m.push(r.bMarks[g]),h.push(r.bsCount[g]),b.push(r.tShift[g]),k.push(r.sCount[g]),r.sCount[g]=-1}else{for(a=C=r.sCount[g]+A-(r.bMarks[g]+r.tShift[g]),r.src.charCodeAt(A)===32?(A++,a++,C++,s=!1,y=!0):r.src.charCodeAt(A)===9?(y=!0,(r.bsCount[g]+C)%4==3?(A++,a++,C++,s=!1):s=!0):y=!1,m.push(r.bMarks[g]),r.bMarks[g]=A;A<$&&(l=r.src.charCodeAt(A),t(l));)l===9?C+=4-(C+r.bsCount[g]+(s?1:0))%4:C++,A++;_=A>=$,h.push(r.bsCount[g]),r.bsCount[g]=r.sCount[g]+1+(y?1:0),k.push(r.sCount[g]),r.sCount[g]=C-a,b.push(r.tShift[g]),r.tShift[g]=A-r.bMarks[g]}for(w=r.blkIndent,r.blkIndent=0,D=r.push("blockquote_open","blockquote",1),D.markup=">",D.map=v=[e,0],r.md.block.tokenize(r,e,g),D=r.push("blockquote_close","blockquote",-1),D.markup=">",r.lineMax=L,r.parentType=f,v[1]=r.line,u=0;u<b.length;u++)r.bMarks[u+e]=m[u],r.tShift[u+e]=b[u],r.sCount[u+e]=k[u],r.bsCount[u+e]=h[u];return r.blkIndent=w,!0}},function(c,d,i){c.exports=function(t,r,e){var o,n,s;if(t.sCount[r]-t.blkIndent<4)return!1;for(n=o=r+1;o<e;)if(t.isEmpty(o))o++;else{if(!(t.sCount[o]-t.blkIndent>=4))break;o++,n=o}return t.line=n,s=t.push("code_block","code",0),s.content=t.getLines(r,n,4+t.blkIndent,!0),s.map=[r,t.line],!0}},function(c,d,i){c.exports=function(t,r,e,o){var n,s,l,u,a,p,_,v=!1,g=t.bMarks[r]+t.tShift[r],C=t.eMarks[r];if(t.sCount[r]-t.blkIndent>=4||g+3>C||(n=t.src.charCodeAt(g))!==126&&n!==96||(a=g,g=t.skipChars(g,n),(s=g-a)<3)||(_=t.src.slice(a,g),l=t.src.slice(g,C),n===96&&l.indexOf(String.fromCharCode(n))>=0))return!1;if(o)return!0;for(u=r;!(++u>=e)&&(g=a=t.bMarks[u]+t.tShift[u],C=t.eMarks[u],!(g<C&&t.sCount[u]<t.blkIndent));)if(t.src.charCodeAt(g)===n&&!(t.sCount[u]-t.blkIndent>=4||(g=t.skipChars(g,n))-a<s||(g=t.skipSpaces(g))<C)){v=!0;break}return s=t.sCount[r],t.line=u+(v?1:0),p=t.push("fence","code",0),p.info=l,p.content=t.getLines(r+1,u,s,!0),p.markup=_,p.map=[r,t.line],!0}},function(c,d,i){var t=i(0).isSpace;c.exports=function(r,e,o,n){var s,l,u,a,p=r.bMarks[e]+r.tShift[e],_=r.eMarks[e];if(r.sCount[e]-r.blkIndent>=4||(s=r.src.charCodeAt(p))!==35||p>=_)return!1;for(l=1,s=r.src.charCodeAt(++p);s===35&&p<_&&l<=6;)l++,s=r.src.charCodeAt(++p);return!(l>6||p<_&&!t(s))&&(!!n||(_=r.skipSpacesBack(_,p),u=r.skipCharsBack(_,35,p),u>p&&t(r.src.charCodeAt(u-1))&&(_=u),r.line=e+1,a=r.push("heading_open","h"+String(l),1),a.markup="########".slice(0,l),a.map=[e,r.line],a=r.push("inline","",0),a.content=r.src.slice(p,_).trim(),a.map=[e,r.line],a.children=[],a=r.push("heading_close","h"+String(l),-1),a.markup="########".slice(0,l),!0))}},function(c,d,i){var t=i(0).isSpace;c.exports=function(r,e,o,n){var s,l,u,a,p=r.bMarks[e]+r.tShift[e],_=r.eMarks[e];if(r.sCount[e]-r.blkIndent>=4||(s=r.src.charCodeAt(p++))!==42&&s!==45&&s!==95)return!1;for(l=1;p<_;){if((u=r.src.charCodeAt(p++))!==s&&!t(u))return!1;u===s&&l++}return!(l<3)&&(!!n||(r.line=e+1,a=r.push("hr","hr",0),a.map=[e,r.line],a.markup=Array(l+1).join(String.fromCharCode(s)),!0))}},function(c,d,i){var t=i(137),r=i(54).HTML_OPEN_CLOSE_TAG_RE,e=[[/^<(script|pre|style)(?=(\s|>|$))/i,/<\/(script|pre|style)>/i,!0],[/^<!--/,/-->/,!0],[/^<\?/,/\?>/,!0],[/^<![A-Z]/,/>/,!0],[/^<!\[CDATA\[/,/\]\]>/,!0],[new RegExp("^</?("+t.join("|")+")(?=(\\s|/?>|$))","i"),/^$/,!0],[new RegExp(r.source+"\\s*$"),/^$/,!1]];c.exports=function(o,n,s,l){var u,a,p,_,v=o.bMarks[n]+o.tShift[n],g=o.eMarks[n];if(o.sCount[n]-o.blkIndent>=4||!o.md.options.html||o.src.charCodeAt(v)!==60)return!1;for(_=o.src.slice(v,g),u=0;u<e.length&&!e[u][0].test(_);u++);if(u===e.length)return!1;if(l)return e[u][2];if(a=n+1,!e[u][1].test(_)){for(;a<s&&!(o.sCount[a]<o.blkIndent);a++)if(v=o.bMarks[a]+o.tShift[a],g=o.eMarks[a],_=o.src.slice(v,g),e[u][1].test(_)){_.length!==0&&a++;break}}return o.line=a,p=o.push("html_block","",0),p.map=[n,a],p.content=o.getLines(n,a,o.blkIndent,!0),!0}},function(c,d,i){c.exports=function(t,r,e){var o,n,s,l,u,a,p,_,v,g,C=r+1,m=t.md.block.ruler.getRules("paragraph");if(t.sCount[r]-t.blkIndent>=4)return!1;for(g=t.parentType,t.parentType="paragraph";C<e&&!t.isEmpty(C);C++)if(!(t.sCount[C]-t.blkIndent>3)){if(t.sCount[C]>=t.blkIndent&&(a=t.bMarks[C]+t.tShift[C],p=t.eMarks[C],a<p&&((v=t.src.charCodeAt(a))===45||v===61)&&(a=t.skipChars(a,v),(a=t.skipSpaces(a))>=p))){_=v===61?1:2;break}if(!(t.sCount[C]<0)){for(n=!1,s=0,l=m.length;s<l;s++)if(m[s](t,C,e,!0)){n=!0;break}if(n)break}}return!!_&&(o=t.getLines(r,C,t.blkIndent,!1).trim(),t.line=C+1,u=t.push("heading_open","h"+String(_),1),u.markup=String.fromCharCode(v),u.map=[r,t.line],u=t.push("inline","",0),u.content=o,u.map=[r,t.line-1],u.children=[],u=t.push("heading_close","h"+String(_),-1),u.markup=String.fromCharCode(v),t.parentType=g,!0)}},function(c,d,i){function t(n,s){var l,u,a,p;return u=n.bMarks[s]+n.tShift[s],a=n.eMarks[s],l=n.src.charCodeAt(u++),l!==42&&l!==45&&l!==43||u<a&&(p=n.src.charCodeAt(u),!o(p))?-1:u}function r(n,s){var l,u=n.bMarks[s]+n.tShift[s],a=u,p=n.eMarks[s];if(a+1>=p||(l=n.src.charCodeAt(a++))<48||l>57)return-1;for(;;){if(a>=p)return-1;l=n.src.charCodeAt(a++);{if(!(l>=48&&l<=57)){if(l===41||l===46)break;return-1}if(a-u>=10)return-1}}return a<p&&(l=n.src.charCodeAt(a),!o(l))?-1:a}function e(n,s){var l,u,a=n.level+2;for(l=s+2,u=n.tokens.length-2;l<u;l++)n.tokens[l].level===a&&n.tokens[l].type==="paragraph_open"&&(n.tokens[l+2].hidden=!0,n.tokens[l].hidden=!0,l+=2)}var o=i(0).isSpace;c.exports=function(n,s,l,u){var a,p,_,v,g,C,m,h,w,f,k,b,y,x,E,D,T,L,A,$,z,j,M,R,W,S,O,q,I=!1,B=!0;if(n.sCount[s]-n.blkIndent>=4||n.listIndent>=0&&n.sCount[s]-n.listIndent>=4&&n.sCount[s]<n.blkIndent)return!1;if(u&&n.parentType==="paragraph"&&n.tShift[s]>=n.blkIndent&&(I=!0),(M=r(n,s))>=0){if(m=!0,W=n.bMarks[s]+n.tShift[s],y=Number(n.src.substr(W,M-W-1)),I&&y!==1)return!1}else{if(!((M=t(n,s))>=0))return!1;m=!1}if(I&&n.skipSpaces(M)>=n.eMarks[s])return!1;if(b=n.src.charCodeAt(M-1),u)return!0;for(k=n.tokens.length,m?(q=n.push("ordered_list_open","ol",1),y!==1&&(q.attrs=[["start",y]])):q=n.push("bullet_list_open","ul",1),q.map=f=[s,0],q.markup=String.fromCharCode(b),E=s,R=!1,O=n.md.block.ruler.getRules("list"),L=n.parentType,n.parentType="list";E<l;){for(j=M,x=n.eMarks[E],C=D=n.sCount[E]+M-(n.bMarks[s]+n.tShift[s]);j<x;){if((a=n.src.charCodeAt(j))===9)D+=4-(D+n.bsCount[E])%4;else{if(a!==32)break;D++}j++}if(p=j,g=p>=x?1:D-C,g>4&&(g=1),v=C+g,q=n.push("list_item_open","li",1),q.markup=String.fromCharCode(b),q.map=h=[s,0],z=n.tight,$=n.tShift[s],A=n.sCount[s],T=n.listIndent,n.listIndent=n.blkIndent,n.blkIndent=v,n.tight=!0,n.tShift[s]=p-n.bMarks[s],n.sCount[s]=D,p>=x&&n.isEmpty(s+1)?n.line=Math.min(n.line+2,l):n.md.block.tokenize(n,s,l,!0),n.tight&&!R||(B=!1),R=n.line-s>1&&n.isEmpty(n.line-1),n.blkIndent=n.listIndent,n.listIndent=T,n.tShift[s]=$,n.sCount[s]=A,n.tight=z,q=n.push("list_item_close","li",-1),q.markup=String.fromCharCode(b),E=s=n.line,h[1]=E,p=n.bMarks[s],E>=l||n.sCount[E]<n.blkIndent||n.sCount[s]-n.blkIndent>=4)break;for(S=!1,_=0,w=O.length;_<w;_++)if(O[_](n,E,l,!0)){S=!0;break}if(S)break;if(m){if((M=r(n,E))<0)break}else if((M=t(n,E))<0)break;if(b!==n.src.charCodeAt(M-1))break}return q=m?n.push("ordered_list_close","ol",-1):n.push("bullet_list_close","ul",-1),q.markup=String.fromCharCode(b),f[1]=E,n.line=E,n.parentType=L,B&&e(n,k),!0}},function(c,d,i){c.exports=function(t,r){var e,o,n,s,l,u,a=r+1,p=t.md.block.ruler.getRules("paragraph"),_=t.lineMax;for(u=t.parentType,t.parentType="paragraph";a<_&&!t.isEmpty(a);a++)if(!(t.sCount[a]-t.blkIndent>3||t.sCount[a]<0)){for(o=!1,n=0,s=p.length;n<s;n++)if(p[n](t,a,_,!0)){o=!0;break}if(o)break}return e=t.getLines(r,a,t.blkIndent,!1).trim(),t.line=a,l=t.push("paragraph_open","p",1),l.map=[r,t.line],l=t.push("inline","",0),l.content=e,l.map=[r,t.line],l.children=[],l=t.push("paragraph_close","p",-1),t.parentType=u,!0}},function(c,d,i){var t=i(0).normalizeReference,r=i(0).isSpace;c.exports=function(e,o,n,s){var l,u,a,p,_,v,g,C,m,h,w,f,k,b,y,x,E=0,D=e.bMarks[o]+e.tShift[o],T=e.eMarks[o],L=o+1;if(e.sCount[o]-e.blkIndent>=4||e.src.charCodeAt(D)!==91)return!1;for(;++D<T;)if(e.src.charCodeAt(D)===93&&e.src.charCodeAt(D-1)!==92){if(D+1===T||e.src.charCodeAt(D+1)!==58)return!1;break}for(p=e.lineMax,y=e.md.block.ruler.getRules("reference"),h=e.parentType,e.parentType="reference";L<p&&!e.isEmpty(L);L++)if(!(e.sCount[L]-e.blkIndent>3||e.sCount[L]<0)){for(b=!1,v=0,g=y.length;v<g;v++)if(y[v](e,L,p,!0)){b=!0;break}if(b)break}for(k=e.getLines(o,L,e.blkIndent,!1).trim(),T=k.length,D=1;D<T;D++){if((l=k.charCodeAt(D))===91)return!1;if(l===93){m=D;break}(l===10||l===92&&++D<T&&k.charCodeAt(D)===10)&&E++}if(m<0||k.charCodeAt(m+1)!==58)return!1;for(D=m+2;D<T;D++)if((l=k.charCodeAt(D))===10)E++;else if(!r(l))break;if(w=e.md.helpers.parseLinkDestination(k,D,T),!w.ok||(_=e.md.normalizeLink(w.str),!e.md.validateLink(_)))return!1;for(D=w.pos,E+=w.lines,u=D,a=E,f=D;D<T;D++)if((l=k.charCodeAt(D))===10)E++;else if(!r(l))break;for(w=e.md.helpers.parseLinkTitle(k,D,T),D<T&&f!==D&&w.ok?(x=w.str,D=w.pos,E+=w.lines):(x="",D=u,E=a);D<T&&(l=k.charCodeAt(D),r(l));)D++;if(D<T&&k.charCodeAt(D)!==10&&x)for(x="",D=u,E=a;D<T&&(l=k.charCodeAt(D),r(l));)D++;return!(D<T&&k.charCodeAt(D)!==10)&&!!(C=t(k.slice(1,m)))&&(!!s||(e.env.references===void 0&&(e.env.references={}),e.env.references[C]===void 0&&(e.env.references[C]={title:x,href:_}),e.parentType=h,e.line=o+E+1,!0))}},function(c,d,i){function t(o,n,s,l){var u,a,p,_,v,g,C,m;for(this.src=o,this.md=n,this.env=s,this.tokens=l,this.bMarks=[],this.eMarks=[],this.tShift=[],this.sCount=[],this.bsCount=[],this.blkIndent=0,this.line=0,this.lineMax=0,this.tight=!1,this.ddIndent=-1,this.listIndent=-1,this.parentType="root",this.level=0,this.result="",a=this.src,m=!1,p=_=g=C=0,v=a.length;_<v;_++){if(u=a.charCodeAt(_),!m){if(e(u)){g++,u===9?C+=4-C%4:C++;continue}m=!0}u!==10&&_!==v-1||(u!==10&&_++,this.bMarks.push(p),this.eMarks.push(_),this.tShift.push(g),this.sCount.push(C),this.bsCount.push(0),m=!1,g=0,C=0,p=_+1)}this.bMarks.push(a.length),this.eMarks.push(a.length),this.tShift.push(0),this.sCount.push(0),this.bsCount.push(0),this.lineMax=this.bMarks.length-1}var r=i(33),e=i(0).isSpace;t.prototype.push=function(o,n,s){var l=new r(o,n,s);return l.block=!0,s<0&&this.level--,l.level=this.level,s>0&&this.level++,this.tokens.push(l),l},t.prototype.isEmpty=function(o){return this.bMarks[o]+this.tShift[o]>=this.eMarks[o]},t.prototype.skipEmptyLines=function(o){for(var n=this.lineMax;o<n&&!(this.bMarks[o]+this.tShift[o]<this.eMarks[o]);o++);return o},t.prototype.skipSpaces=function(o){for(var n,s=this.src.length;o<s&&(n=this.src.charCodeAt(o),e(n));o++);return o},t.prototype.skipSpacesBack=function(o,n){if(o<=n)return o;for(;o>n;)if(!e(this.src.charCodeAt(--o)))return o+1;return o},t.prototype.skipChars=function(o,n){for(var s=this.src.length;o<s&&this.src.charCodeAt(o)===n;o++);return o},t.prototype.skipCharsBack=function(o,n,s){if(o<=s)return o;for(;o>s;)if(n!==this.src.charCodeAt(--o))return o+1;return o},t.prototype.getLines=function(o,n,s,l){var u,a,p,_,v,g,C,m=o;if(o>=n)return"";for(g=new Array(n-o),u=0;m<n;m++,u++){for(a=0,C=_=this.bMarks[m],v=m+1<n||l?this.eMarks[m]+1:this.eMarks[m];_<v&&a<s;){if(p=this.src.charCodeAt(_),e(p))p===9?a+=4-(a+this.bsCount[m])%4:a++;else{if(!(_-C<this.tShift[m]))break;a++}_++}g[u]=a>s?new Array(a-s+1).join(" ")+this.src.slice(_,v):this.src.slice(_,v)}return g.join("")},t.prototype.Token=r,c.exports=t},function(c,d,i){function t(o,n){var s=o.bMarks[n]+o.blkIndent,l=o.eMarks[n];return o.src.substr(s,l-s)}function r(o){var n,s=[],l=0,u=o.length,a=0,p=0,_=!1,v=0;for(n=o.charCodeAt(l);l<u;)n===96?_?(_=!1,v=l):a%2==0&&(_=!0,v=l):n!==124||a%2!=0||_||(s.push(o.substring(p,l)),p=l+1),n===92?a++:a=0,l++,l===u&&_&&(_=!1,l=v+1),n=o.charCodeAt(l);return s.push(o.substring(p)),s}var e=i(0).isSpace;c.exports=function(o,n,s,l){var u,a,p,_,v,g,C,m,h,w,f,k;if(n+2>s||(v=n+1,o.sCount[v]<o.blkIndent)||o.sCount[v]-o.blkIndent>=4||(p=o.bMarks[v]+o.tShift[v])>=o.eMarks[v]||(u=o.src.charCodeAt(p++))!==124&&u!==45&&u!==58)return!1;for(;p<o.eMarks[v];){if((u=o.src.charCodeAt(p))!==124&&u!==45&&u!==58&&!e(u))return!1;p++}for(a=t(o,n+1),g=a.split("|"),h=[],_=0;_<g.length;_++){if(!(w=g[_].trim())){if(_===0||_===g.length-1)continue;return!1}if(!/^:?-+:?$/.test(w))return!1;w.charCodeAt(w.length-1)===58?h.push(w.charCodeAt(0)===58?"center":"right"):w.charCodeAt(0)===58?h.push("left"):h.push("")}if(a=t(o,n).trim(),a.indexOf("|")===-1||o.sCount[n]-o.blkIndent>=4||(g=r(a.replace(/^\||\|$/g,"")),(C=g.length)>h.length))return!1;if(l)return!0;for(m=o.push("table_open","table",1),m.map=f=[n,0],m=o.push("thead_open","thead",1),m.map=[n,n+1],m=o.push("tr_open","tr",1),m.map=[n,n+1],_=0;_<g.length;_++)m=o.push("th_open","th",1),m.map=[n,n+1],h[_]&&(m.attrs=[["style","text-align:"+h[_]]]),m=o.push("inline","",0),m.content=g[_].trim(),m.map=[n,n+1],m.children=[],m=o.push("th_close","th",-1);for(m=o.push("tr_close","tr",-1),m=o.push("thead_close","thead",-1),m=o.push("tbody_open","tbody",1),m.map=k=[n+2,0],v=n+2;v<s&&!(o.sCount[v]<o.blkIndent)&&(a=t(o,v).trim(),a.indexOf("|")!==-1)&&!(o.sCount[v]-o.blkIndent>=4);v++){for(g=r(a.replace(/^\||\|$/g,"")),m=o.push("tr_open","tr",1),_=0;_<C;_++)m=o.push("td_open","td",1),h[_]&&(m.attrs=[["style","text-align:"+h[_]]]),m=o.push("inline","",0),m.content=g[_]?g[_].trim():"",m.children=[],m=o.push("td_close","td",-1);m=o.push("tr_close","tr",-1)}return m=o.push("tbody_close","tbody",-1),m=o.push("table_close","table",-1),f[1]=k[1]=v,o.line=v,!0}},function(c,d,i){c.exports=function(t){var r;t.inlineMode?(r=new t.Token("inline","",0),r.content=t.src,r.map=[0,1],r.children=[],t.tokens.push(r)):t.md.block.parse(t.src,t.md,t.env,t.tokens)}},function(c,d,i){c.exports=function(t){var r,e,o,n=t.tokens;for(e=0,o=n.length;e<o;e++)r=n[e],r.type==="inline"&&t.md.inline.parse(r.content,t.md,t.env,r.children)}},function(c,d,i){function t(o){return/^<a[>\s]/i.test(o)}function r(o){return/^<\/a\s*>/i.test(o)}var e=i(0).arrayReplaceAt;c.exports=function(o){var n,s,l,u,a,p,_,v,g,C,m,h,w,f,k,b,y,x=o.tokens;if(o.md.options.linkify){for(s=0,l=x.length;s<l;s++)if(x[s].type==="inline"&&o.md.linkify.pretest(x[s].content))for(u=x[s].children,w=0,n=u.length-1;n>=0;n--)if(p=u[n],p.type!=="link_close"){if(p.type==="html_inline"&&(t(p.content)&&w>0&&w--,r(p.content)&&w++),!(w>0)&&p.type==="text"&&o.md.linkify.test(p.content)){for(g=p.content,y=o.md.linkify.match(g),_=[],h=p.level,m=0,v=0;v<y.length;v++)f=y[v].url,k=o.md.normalizeLink(f),o.md.validateLink(k)&&(b=y[v].text,b=y[v].schema?y[v].schema!=="mailto:"||/^mailto:/i.test(b)?o.md.normalizeLinkText(b):o.md.normalizeLinkText("mailto:"+b).replace(/^mailto:/,""):o.md.normalizeLinkText("http://"+b).replace(/^http:\/\//,""),C=y[v].index,C>m&&(a=new o.Token("text","",0),a.content=g.slice(m,C),a.level=h,_.push(a)),a=new o.Token("link_open","a",1),a.attrs=[["href",k]],a.level=h++,a.markup="linkify",a.info="auto",_.push(a),a=new o.Token("text","",0),a.content=b,a.level=h,_.push(a),a=new o.Token("link_close","a",-1),a.level=--h,a.markup="linkify",a.info="auto",_.push(a),m=y[v].lastIndex);m<g.length&&(a=new o.Token("text","",0),a.content=g.slice(m),a.level=h,_.push(a)),x[s].children=u=e(u,n,_)}}else for(n--;u[n].level!==p.level&&u[n].type!=="link_open";)n--}}},function(c,d,i){var t=/\r\n?|\n/g,r=/\0/g;c.exports=function(e){var o;o=e.src.replace(t,`
`),o=o.replace(r,"\uFFFD"),e.src=o}},function(c,d,i){function t(u,a){return l[a.toLowerCase()]}function r(u){var a,p,_=0;for(a=u.length-1;a>=0;a--)p=u[a],p.type!=="text"||_||(p.content=p.content.replace(s,t)),p.type==="link_open"&&p.info==="auto"&&_--,p.type==="link_close"&&p.info==="auto"&&_++}function e(u){var a,p,_=0;for(a=u.length-1;a>=0;a--)p=u[a],p.type!=="text"||_||o.test(p.content)&&(p.content=p.content.replace(/\+-/g,"\xB1").replace(/\.{2,}/g,"\u2026").replace(/([?!])…/g,"$1..").replace(/([?!]){4,}/g,"$1$1$1").replace(/,{2,}/g,",").replace(/(^|[^-])---([^-]|$)/gm,"$1\u2014$2").replace(/(^|\s)--(\s|$)/gm,"$1\u2013$2").replace(/(^|[^-\s])--([^-\s]|$)/gm,"$1\u2013$2")),p.type==="link_open"&&p.info==="auto"&&_--,p.type==="link_close"&&p.info==="auto"&&_++}var o=/\+-|\.\.|\?\?\?\?|!!!!|,,|--/,n=/\((c|tm|r|p)\)/i,s=/\((c|tm|r|p)\)/gi,l={c:"\xA9",r:"\xAE",p:"\xA7",tm:"\u2122"};c.exports=function(u){var a;if(u.md.options.typographer)for(a=u.tokens.length-1;a>=0;a--)u.tokens[a].type==="inline"&&(n.test(u.tokens[a].content)&&r(u.tokens[a].children),o.test(u.tokens[a].content)&&e(u.tokens[a].children))}},function(c,d,i){function t(a,p,_){return a.substr(0,p)+_+a.substr(p+1)}function r(a,p){var _,v,g,C,m,h,w,f,k,b,y,x,E,D,T,L,A,$,z,j,M;for(z=[],_=0;_<a.length;_++){for(v=a[_],w=a[_].level,A=z.length-1;A>=0&&!(z[A].level<=w);A--);if(z.length=A+1,v.type==="text"){g=v.content,m=0,h=g.length;t:for(;m<h&&(l.lastIndex=m,C=l.exec(g));){if(T=L=!0,m=C.index+1,$=C[0]==="'",k=32,C.index-1>=0)k=g.charCodeAt(C.index-1);else for(A=_-1;A>=0&&a[A].type!=="softbreak"&&a[A].type!=="hardbreak";A--)if(a[A].type==="text"){k=a[A].content.charCodeAt(a[A].content.length-1);break}if(b=32,m<h)b=g.charCodeAt(m);else for(A=_+1;A<a.length&&a[A].type!=="softbreak"&&a[A].type!=="hardbreak";A++)if(a[A].type==="text"){b=a[A].content.charCodeAt(0);break}if(y=n(k)||o(String.fromCharCode(k)),x=n(b)||o(String.fromCharCode(b)),E=e(k),D=e(b),D?T=!1:x&&(E||y||(T=!1)),E?L=!1:y&&(D||x||(L=!1)),b===34&&C[0]==='"'&&k>=48&&k<=57&&(L=T=!1),T&&L&&(T=!1,L=x),T||L){if(L){for(A=z.length-1;A>=0&&(f=z[A],!(z[A].level<w));A--)if(f.single===$&&z[A].level===w){f=z[A],$?(j=p.md.options.quotes[2],M=p.md.options.quotes[3]):(j=p.md.options.quotes[0],M=p.md.options.quotes[1]),v.content=t(v.content,C.index,M),a[f.token].content=t(a[f.token].content,f.pos,j),m+=M.length-1,f.token===_&&(m+=j.length-1),g=v.content,h=g.length,z.length=A;continue t}}T?z.push({token:_,pos:C.index,single:$,level:w}):L&&$&&(v.content=t(v.content,C.index,u))}else $&&(v.content=t(v.content,C.index,u))}}}}var e=i(0).isWhiteSpace,o=i(0).isPunctChar,n=i(0).isMdAsciiPunct,s=/['"]/,l=/['"]/g,u="\u2019";c.exports=function(a){var p;if(a.md.options.typographer)for(p=a.tokens.length-1;p>=0;p--)a.tokens[p].type==="inline"&&s.test(a.tokens[p].content)&&r(a.tokens[p].children,a)}},function(c,d,i){function t(e,o,n){this.src=e,this.env=n,this.tokens=[],this.inlineMode=!1,this.md=o}var r=i(33);t.prototype.Token=r,c.exports=t},function(c,d,i){var t=/^<([a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*)>/,r=/^<([a-zA-Z][a-zA-Z0-9+.\-]{1,31}):([^<>\x00-\x20]*)>/;c.exports=function(e,o){var n,s,l,u,a,p,_=e.pos;return e.src.charCodeAt(_)===60&&(n=e.src.slice(_),!(n.indexOf(">")<0)&&(r.test(n)?(s=n.match(r),u=s[0].slice(1,-1),a=e.md.normalizeLink(u),!!e.md.validateLink(a)&&(o||(p=e.push("link_open","a",1),p.attrs=[["href",a]],p.markup="autolink",p.info="auto",p=e.push("text","",0),p.content=e.md.normalizeLinkText(u),p=e.push("link_close","a",-1),p.markup="autolink",p.info="auto"),e.pos+=s[0].length,!0)):!!t.test(n)&&(l=n.match(t),u=l[0].slice(1,-1),a=e.md.normalizeLink("mailto:"+u),!!e.md.validateLink(a)&&(o||(p=e.push("link_open","a",1),p.attrs=[["href",a]],p.markup="autolink",p.info="auto",p=e.push("text","",0),p.content=e.md.normalizeLinkText(u),p=e.push("link_close","a",-1),p.markup="autolink",p.info="auto"),e.pos+=l[0].length,!0))))}},function(c,d,i){c.exports=function(t,r){var e,o,n,s,l,u,a=t.pos;if(t.src.charCodeAt(a)!==96)return!1;for(e=a,a++,o=t.posMax;a<o&&t.src.charCodeAt(a)===96;)a++;for(n=t.src.slice(e,a),s=l=a;(s=t.src.indexOf("`",l))!==-1;){for(l=s+1;l<o&&t.src.charCodeAt(l)===96;)l++;if(l-s===n.length)return r||(u=t.push("code_inline","code",0),u.markup=n,u.content=t.src.slice(a,s).replace(/\n/g," ").replace(/^ (.+) $/,"$1")),t.pos=l,!0}return r||(t.pending+=n),t.pos+=n.length,!0}},function(c,d,i){function t(r,e){var o,n,s,l,u,a,p,_,v={},g=e.length;for(o=0;o<g;o++)if(s=e[o],s.length=s.length||0,s.close){for(v.hasOwnProperty(s.marker)||(v[s.marker]=[-1,-1,-1]),u=v[s.marker][s.length%3],a=-1,n=o-s.jump-1;n>u;n-=l.jump+1)if(l=e[n],l.marker===s.marker&&(a===-1&&(a=n),l.open&&l.end<0&&l.level===s.level&&(p=!1,(l.close||s.open)&&(l.length+s.length)%3==0&&(l.length%3==0&&s.length%3==0||(p=!0)),!p))){_=n>0&&!e[n-1].open?e[n-1].jump+1:0,s.jump=o-n+_,s.open=!1,l.end=o,l.jump=_,l.close=!1,a=-1;break}a!==-1&&(v[s.marker][(s.length||0)%3]=a)}}c.exports=function(r){var e,o=r.tokens_meta,n=r.tokens_meta.length;for(t(r,r.delimiters),e=0;e<n;e++)o[e]&&o[e].delimiters&&t(r,o[e].delimiters)}},function(c,d,i){var t=i(53),r=i(0).has,e=i(0).isValidEntityCode,o=i(0).fromCodePoint,n=/^&#((?:x[a-f0-9]{1,6}|[0-9]{1,7}));/i,s=/^&([a-z][a-z0-9]{1,31});/i;c.exports=function(l,u){var a,p,_=l.pos,v=l.posMax;if(l.src.charCodeAt(_)!==38)return!1;if(_+1<v){if(l.src.charCodeAt(_+1)===35){if(p=l.src.slice(_).match(n))return u||(a=p[1][0].toLowerCase()==="x"?parseInt(p[1].slice(1),16):parseInt(p[1],10),l.pending+=o(e(a)?a:65533)),l.pos+=p[0].length,!0}else if((p=l.src.slice(_).match(s))&&r(t,p[1]))return u||(l.pending+=t[p[1]]),l.pos+=p[0].length,!0}return u||(l.pending+="&"),l.pos++,!0}},function(c,d,i){for(var t=i(0).isSpace,r=[],e=0;e<256;e++)r.push(0);"\\!\"#$%&'()*+,./:;<=>?@[]^_`{|}~-".split("").forEach(function(o){r[o.charCodeAt(0)]=1}),c.exports=function(o,n){var s,l=o.pos,u=o.posMax;if(o.src.charCodeAt(l)!==92)return!1;if(++l<u){if((s=o.src.charCodeAt(l))<256&&r[s]!==0)return n||(o.pending+=o.src[l]),o.pos+=2,!0;if(s===10){for(n||o.push("hardbreak","br",0),l++;l<u&&(s=o.src.charCodeAt(l),t(s));)l++;return o.pos=l,!0}}return n||(o.pending+="\\"),o.pos++,!0}},function(c,d,i){function t(e){var o=32|e;return o>=97&&o<=122}var r=i(54).HTML_TAG_RE;c.exports=function(e,o){var n,s,l,u,a=e.pos;return!!e.md.options.html&&(l=e.posMax,!(e.src.charCodeAt(a)!==60||a+2>=l)&&!((n=e.src.charCodeAt(a+1))!==33&&n!==63&&n!==47&&!t(n))&&!!(s=e.src.slice(a).match(r))&&(o||(u=e.push("html_inline","",0),u.content=e.src.slice(a,a+s[0].length)),e.pos+=s[0].length,!0))}},function(c,d,i){var t=i(0).normalizeReference,r=i(0).isSpace;c.exports=function(e,o){var n,s,l,u,a,p,_,v,g,C,m,h,w,f="",k=e.pos,b=e.posMax;if(e.src.charCodeAt(e.pos)!==33||e.src.charCodeAt(e.pos+1)!==91||(p=e.pos+2,(a=e.md.helpers.parseLinkLabel(e,e.pos+1,!1))<0))return!1;if((_=a+1)<b&&e.src.charCodeAt(_)===40){for(_++;_<b&&(s=e.src.charCodeAt(_),r(s)||s===10);_++);if(_>=b)return!1;for(w=_,g=e.md.helpers.parseLinkDestination(e.src,_,e.posMax),g.ok&&(f=e.md.normalizeLink(g.str),e.md.validateLink(f)?_=g.pos:f=""),w=_;_<b&&(s=e.src.charCodeAt(_),r(s)||s===10);_++);if(g=e.md.helpers.parseLinkTitle(e.src,_,e.posMax),_<b&&w!==_&&g.ok)for(C=g.str,_=g.pos;_<b&&(s=e.src.charCodeAt(_),r(s)||s===10);_++);else C="";if(_>=b||e.src.charCodeAt(_)!==41)return e.pos=k,!1;_++}else{if(e.env.references===void 0)return!1;if(_<b&&e.src.charCodeAt(_)===91?(w=_+1,_=e.md.helpers.parseLinkLabel(e,_),_>=0?u=e.src.slice(w,_++):_=a+1):_=a+1,u||(u=e.src.slice(p,a)),!(v=e.env.references[t(u)]))return e.pos=k,!1;f=v.href,C=v.title}return o||(l=e.src.slice(p,a),e.md.inline.parse(l,e.md,e.env,h=[]),m=e.push("image","img",0),m.attrs=n=[["src",f],["alt",""]],m.children=h,m.content=l,C&&n.push(["title",C])),e.pos=_,e.posMax=b,!0}},function(c,d,i){var t=i(0).normalizeReference,r=i(0).isSpace;c.exports=function(e,o){var n,s,l,u,a,p,_,v,g,C,m="",h=e.pos,w=e.posMax,f=e.pos,k=!0;if(e.src.charCodeAt(e.pos)!==91||(a=e.pos+1,(u=e.md.helpers.parseLinkLabel(e,e.pos,!0))<0))return!1;if((p=u+1)<w&&e.src.charCodeAt(p)===40){for(k=!1,p++;p<w&&(s=e.src.charCodeAt(p),r(s)||s===10);p++);if(p>=w)return!1;for(f=p,_=e.md.helpers.parseLinkDestination(e.src,p,e.posMax),_.ok&&(m=e.md.normalizeLink(_.str),e.md.validateLink(m)?p=_.pos:m=""),f=p;p<w&&(s=e.src.charCodeAt(p),r(s)||s===10);p++);if(_=e.md.helpers.parseLinkTitle(e.src,p,e.posMax),p<w&&f!==p&&_.ok)for(g=_.str,p=_.pos;p<w&&(s=e.src.charCodeAt(p),r(s)||s===10);p++);else g="";(p>=w||e.src.charCodeAt(p)!==41)&&(k=!0),p++}if(k){if(e.env.references===void 0)return!1;if(p<w&&e.src.charCodeAt(p)===91?(f=p+1,p=e.md.helpers.parseLinkLabel(e,p),p>=0?l=e.src.slice(f,p++):p=u+1):p=u+1,l||(l=e.src.slice(a,u)),!(v=e.env.references[t(l)]))return e.pos=h,!1;m=v.href,g=v.title}return o||(e.pos=a,e.posMax=u,C=e.push("link_open","a",1),C.attrs=n=[["href",m]],g&&n.push(["title",g]),e.md.inline.tokenize(e),C=e.push("link_close","a",-1)),e.pos=p,e.posMax=w,!0}},function(c,d,i){var t=i(0).isSpace;c.exports=function(r,e){var o,n,s=r.pos;if(r.src.charCodeAt(s)!==10)return!1;for(o=r.pending.length-1,n=r.posMax,e||(o>=0&&r.pending.charCodeAt(o)===32?o>=1&&r.pending.charCodeAt(o-1)===32?(r.pending=r.pending.replace(/ +$/,""),r.push("hardbreak","br",0)):(r.pending=r.pending.slice(0,-1),r.push("softbreak","br",0)):r.push("softbreak","br",0)),s++;s<n&&t(r.src.charCodeAt(s));)s++;return r.pos=s,!0}},function(c,d,i){function t(s,l,u,a){this.src=s,this.env=u,this.md=l,this.tokens=a,this.tokens_meta=Array(a.length),this.pos=0,this.posMax=this.src.length,this.level=0,this.pending="",this.pendingLevel=0,this.cache={},this.delimiters=[],this._prev_delimiters=[]}var r=i(33),e=i(0).isWhiteSpace,o=i(0).isPunctChar,n=i(0).isMdAsciiPunct;t.prototype.pushPending=function(){var s=new r("text","",0);return s.content=this.pending,s.level=this.pendingLevel,this.tokens.push(s),this.pending="",s},t.prototype.push=function(s,l,u){this.pending&&this.pushPending();var a=new r(s,l,u),p=null;return u<0&&(this.level--,this.delimiters=this._prev_delimiters.pop()),a.level=this.level,u>0&&(this.level++,this._prev_delimiters.push(this.delimiters),this.delimiters=[],p={delimiters:this.delimiters}),this.pendingLevel=this.level,this.tokens.push(a),this.tokens_meta.push(p),a},t.prototype.scanDelims=function(s,l){var u,a,p,_,v,g,C,m,h,w=s,f=!0,k=!0,b=this.posMax,y=this.src.charCodeAt(s);for(u=s>0?this.src.charCodeAt(s-1):32;w<b&&this.src.charCodeAt(w)===y;)w++;return p=w-s,a=w<b?this.src.charCodeAt(w):32,C=n(u)||o(String.fromCharCode(u)),h=n(a)||o(String.fromCharCode(a)),g=e(u),m=e(a),m?f=!1:h&&(g||C||(f=!1)),g?k=!1:C&&(m||h||(k=!1)),l?(_=f,v=k):(_=f&&(!k||C),v=k&&(!f||h)),{can_open:_,can_close:v,length:p}},t.prototype.Token=r,c.exports=t},function(c,d,i){function t(r){switch(r){case 10:case 33:case 35:case 36:case 37:case 38:case 42:case 43:case 45:case 58:case 60:case 61:case 62:case 64:case 91:case 92:case 93:case 94:case 95:case 96:case 123:case 125:case 126:return!0;default:return!1}}c.exports=function(r,e){for(var o=r.pos;o<r.posMax&&!t(r.src.charCodeAt(o));)o++;return o!==r.pos&&(e||(r.pending+=r.src.slice(r.pos,o)),r.pos=o,!0)}},function(c,d,i){c.exports=function(t){var r,e,o=0,n=t.tokens,s=t.tokens.length;for(r=e=0;r<s;r++)n[r].nesting<0&&o--,n[r].level=o,n[r].nesting>0&&o++,n[r].type==="text"&&r+1<s&&n[r+1].type==="text"?n[r+1].content=n[r].content+n[r+1].content:(r!==e&&(n[e]=n[r]),e++);r!==e&&(n.length=e)}},function(c,d){c.exports={Aacute:"\xC1",aacute:"\xE1",Abreve:"\u0102",abreve:"\u0103",ac:"\u223E",acd:"\u223F",acE:"\u223E\u0333",Acirc:"\xC2",acirc:"\xE2",acute:"\xB4",Acy:"\u0410",acy:"\u0430",AElig:"\xC6",aelig:"\xE6",af:"\u2061",Afr:"\u{1D504}",afr:"\u{1D51E}",Agrave:"\xC0",agrave:"\xE0",alefsym:"\u2135",aleph:"\u2135",Alpha:"\u0391",alpha:"\u03B1",Amacr:"\u0100",amacr:"\u0101",amalg:"\u2A3F",amp:"&",AMP:"&",andand:"\u2A55",And:"\u2A53",and:"\u2227",andd:"\u2A5C",andslope:"\u2A58",andv:"\u2A5A",ang:"\u2220",ange:"\u29A4",angle:"\u2220",angmsdaa:"\u29A8",angmsdab:"\u29A9",angmsdac:"\u29AA",angmsdad:"\u29AB",angmsdae:"\u29AC",angmsdaf:"\u29AD",angmsdag:"\u29AE",angmsdah:"\u29AF",angmsd:"\u2221",angrt:"\u221F",angrtvb:"\u22BE",angrtvbd:"\u299D",angsph:"\u2222",angst:"\xC5",angzarr:"\u237C",Aogon:"\u0104",aogon:"\u0105",Aopf:"\u{1D538}",aopf:"\u{1D552}",apacir:"\u2A6F",ap:"\u2248",apE:"\u2A70",ape:"\u224A",apid:"\u224B",apos:"'",ApplyFunction:"\u2061",approx:"\u2248",approxeq:"\u224A",Aring:"\xC5",aring:"\xE5",Ascr:"\u{1D49C}",ascr:"\u{1D4B6}",Assign:"\u2254",ast:"*",asymp:"\u2248",asympeq:"\u224D",Atilde:"\xC3",atilde:"\xE3",Auml:"\xC4",auml:"\xE4",awconint:"\u2233",awint:"\u2A11",backcong:"\u224C",backepsilon:"\u03F6",backprime:"\u2035",backsim:"\u223D",backsimeq:"\u22CD",Backslash:"\u2216",Barv:"\u2AE7",barvee:"\u22BD",barwed:"\u2305",Barwed:"\u2306",barwedge:"\u2305",bbrk:"\u23B5",bbrktbrk:"\u23B6",bcong:"\u224C",Bcy:"\u0411",bcy:"\u0431",bdquo:"\u201E",becaus:"\u2235",because:"\u2235",Because:"\u2235",bemptyv:"\u29B0",bepsi:"\u03F6",bernou:"\u212C",Bernoullis:"\u212C",Beta:"\u0392",beta:"\u03B2",beth:"\u2136",between:"\u226C",Bfr:"\u{1D505}",bfr:"\u{1D51F}",bigcap:"\u22C2",bigcirc:"\u25EF",bigcup:"\u22C3",bigodot:"\u2A00",bigoplus:"\u2A01",bigotimes:"\u2A02",bigsqcup:"\u2A06",bigstar:"\u2605",bigtriangledown:"\u25BD",bigtriangleup:"\u25B3",biguplus:"\u2A04",bigvee:"\u22C1",bigwedge:"\u22C0",bkarow:"\u290D",blacklozenge:"\u29EB",blacksquare:"\u25AA",blacktriangle:"\u25B4",blacktriangledown:"\u25BE",blacktriangleleft:"\u25C2",blacktriangleright:"\u25B8",blank:"\u2423",blk12:"\u2592",blk14:"\u2591",blk34:"\u2593",block:"\u2588",bne:"=\u20E5",bnequiv:"\u2261\u20E5",bNot:"\u2AED",bnot:"\u2310",Bopf:"\u{1D539}",bopf:"\u{1D553}",bot:"\u22A5",bottom:"\u22A5",bowtie:"\u22C8",boxbox:"\u29C9",boxdl:"\u2510",boxdL:"\u2555",boxDl:"\u2556",boxDL:"\u2557",boxdr:"\u250C",boxdR:"\u2552",boxDr:"\u2553",boxDR:"\u2554",boxh:"\u2500",boxH:"\u2550",boxhd:"\u252C",boxHd:"\u2564",boxhD:"\u2565",boxHD:"\u2566",boxhu:"\u2534",boxHu:"\u2567",boxhU:"\u2568",boxHU:"\u2569",boxminus:"\u229F",boxplus:"\u229E",boxtimes:"\u22A0",boxul:"\u2518",boxuL:"\u255B",boxUl:"\u255C",boxUL:"\u255D",boxur:"\u2514",boxuR:"\u2558",boxUr:"\u2559",boxUR:"\u255A",boxv:"\u2502",boxV:"\u2551",boxvh:"\u253C",boxvH:"\u256A",boxVh:"\u256B",boxVH:"\u256C",boxvl:"\u2524",boxvL:"\u2561",boxVl:"\u2562",boxVL:"\u2563",boxvr:"\u251C",boxvR:"\u255E",boxVr:"\u255F",boxVR:"\u2560",bprime:"\u2035",breve:"\u02D8",Breve:"\u02D8",brvbar:"\xA6",bscr:"\u{1D4B7}",Bscr:"\u212C",bsemi:"\u204F",bsim:"\u223D",bsime:"\u22CD",bsolb:"\u29C5",bsol:"\\",bsolhsub:"\u27C8",bull:"\u2022",bullet:"\u2022",bump:"\u224E",bumpE:"\u2AAE",bumpe:"\u224F",Bumpeq:"\u224E",bumpeq:"\u224F",Cacute:"\u0106",cacute:"\u0107",capand:"\u2A44",capbrcup:"\u2A49",capcap:"\u2A4B",cap:"\u2229",Cap:"\u22D2",capcup:"\u2A47",capdot:"\u2A40",CapitalDifferentialD:"\u2145",caps:"\u2229\uFE00",caret:"\u2041",caron:"\u02C7",Cayleys:"\u212D",ccaps:"\u2A4D",Ccaron:"\u010C",ccaron:"\u010D",Ccedil:"\xC7",ccedil:"\xE7",Ccirc:"\u0108",ccirc:"\u0109",Cconint:"\u2230",ccups:"\u2A4C",ccupssm:"\u2A50",Cdot:"\u010A",cdot:"\u010B",cedil:"\xB8",Cedilla:"\xB8",cemptyv:"\u29B2",cent:"\xA2",centerdot:"\xB7",CenterDot:"\xB7",cfr:"\u{1D520}",Cfr:"\u212D",CHcy:"\u0427",chcy:"\u0447",check:"\u2713",checkmark:"\u2713",Chi:"\u03A7",chi:"\u03C7",circ:"\u02C6",circeq:"\u2257",circlearrowleft:"\u21BA",circlearrowright:"\u21BB",circledast:"\u229B",circledcirc:"\u229A",circleddash:"\u229D",CircleDot:"\u2299",circledR:"\xAE",circledS:"\u24C8",CircleMinus:"\u2296",CirclePlus:"\u2295",CircleTimes:"\u2297",cir:"\u25CB",cirE:"\u29C3",cire:"\u2257",cirfnint:"\u2A10",cirmid:"\u2AEF",cirscir:"\u29C2",ClockwiseContourIntegral:"\u2232",CloseCurlyDoubleQuote:"\u201D",CloseCurlyQuote:"\u2019",clubs:"\u2663",clubsuit:"\u2663",colon:":",Colon:"\u2237",Colone:"\u2A74",colone:"\u2254",coloneq:"\u2254",comma:",",commat:"@",comp:"\u2201",compfn:"\u2218",complement:"\u2201",complexes:"\u2102",cong:"\u2245",congdot:"\u2A6D",Congruent:"\u2261",conint:"\u222E",Conint:"\u222F",ContourIntegral:"\u222E",copf:"\u{1D554}",Copf:"\u2102",coprod:"\u2210",Coproduct:"\u2210",copy:"\xA9",COPY:"\xA9",copysr:"\u2117",CounterClockwiseContourIntegral:"\u2233",crarr:"\u21B5",cross:"\u2717",Cross:"\u2A2F",Cscr:"\u{1D49E}",cscr:"\u{1D4B8}",csub:"\u2ACF",csube:"\u2AD1",csup:"\u2AD0",csupe:"\u2AD2",ctdot:"\u22EF",cudarrl:"\u2938",cudarrr:"\u2935",cuepr:"\u22DE",cuesc:"\u22DF",cularr:"\u21B6",cularrp:"\u293D",cupbrcap:"\u2A48",cupcap:"\u2A46",CupCap:"\u224D",cup:"\u222A",Cup:"\u22D3",cupcup:"\u2A4A",cupdot:"\u228D",cupor:"\u2A45",cups:"\u222A\uFE00",curarr:"\u21B7",curarrm:"\u293C",curlyeqprec:"\u22DE",curlyeqsucc:"\u22DF",curlyvee:"\u22CE",curlywedge:"\u22CF",curren:"\xA4",curvearrowleft:"\u21B6",curvearrowright:"\u21B7",cuvee:"\u22CE",cuwed:"\u22CF",cwconint:"\u2232",cwint:"\u2231",cylcty:"\u232D",dagger:"\u2020",Dagger:"\u2021",daleth:"\u2138",darr:"\u2193",Darr:"\u21A1",dArr:"\u21D3",dash:"\u2010",Dashv:"\u2AE4",dashv:"\u22A3",dbkarow:"\u290F",dblac:"\u02DD",Dcaron:"\u010E",dcaron:"\u010F",Dcy:"\u0414",dcy:"\u0434",ddagger:"\u2021",ddarr:"\u21CA",DD:"\u2145",dd:"\u2146",DDotrahd:"\u2911",ddotseq:"\u2A77",deg:"\xB0",Del:"\u2207",Delta:"\u0394",delta:"\u03B4",demptyv:"\u29B1",dfisht:"\u297F",Dfr:"\u{1D507}",dfr:"\u{1D521}",dHar:"\u2965",dharl:"\u21C3",dharr:"\u21C2",DiacriticalAcute:"\xB4",DiacriticalDot:"\u02D9",DiacriticalDoubleAcute:"\u02DD",DiacriticalGrave:"`",DiacriticalTilde:"\u02DC",diam:"\u22C4",diamond:"\u22C4",Diamond:"\u22C4",diamondsuit:"\u2666",diams:"\u2666",die:"\xA8",DifferentialD:"\u2146",digamma:"\u03DD",disin:"\u22F2",div:"\xF7",divide:"\xF7",divideontimes:"\u22C7",divonx:"\u22C7",DJcy:"\u0402",djcy:"\u0452",dlcorn:"\u231E",dlcrop:"\u230D",dollar:"$",Dopf:"\u{1D53B}",dopf:"\u{1D555}",Dot:"\xA8",dot:"\u02D9",DotDot:"\u20DC",doteq:"\u2250",doteqdot:"\u2251",DotEqual:"\u2250",dotminus:"\u2238",dotplus:"\u2214",dotsquare:"\u22A1",doublebarwedge:"\u2306",DoubleContourIntegral:"\u222F",DoubleDot:"\xA8",DoubleDownArrow:"\u21D3",DoubleLeftArrow:"\u21D0",DoubleLeftRightArrow:"\u21D4",DoubleLeftTee:"\u2AE4",DoubleLongLeftArrow:"\u27F8",DoubleLongLeftRightArrow:"\u27FA",DoubleLongRightArrow:"\u27F9",DoubleRightArrow:"\u21D2",DoubleRightTee:"\u22A8",DoubleUpArrow:"\u21D1",DoubleUpDownArrow:"\u21D5",DoubleVerticalBar:"\u2225",DownArrowBar:"\u2913",downarrow:"\u2193",DownArrow:"\u2193",Downarrow:"\u21D3",DownArrowUpArrow:"\u21F5",DownBreve:"\u0311",downdownarrows:"\u21CA",downharpoonleft:"\u21C3",downharpoonright:"\u21C2",DownLeftRightVector:"\u2950",DownLeftTeeVector:"\u295E",DownLeftVectorBar:"\u2956",DownLeftVector:"\u21BD",DownRightTeeVector:"\u295F",DownRightVectorBar:"\u2957",DownRightVector:"\u21C1",DownTeeArrow:"\u21A7",DownTee:"\u22A4",drbkarow:"\u2910",drcorn:"\u231F",drcrop:"\u230C",Dscr:"\u{1D49F}",dscr:"\u{1D4B9}",DScy:"\u0405",dscy:"\u0455",dsol:"\u29F6",Dstrok:"\u0110",dstrok:"\u0111",dtdot:"\u22F1",dtri:"\u25BF",dtrif:"\u25BE",duarr:"\u21F5",duhar:"\u296F",dwangle:"\u29A6",DZcy:"\u040F",dzcy:"\u045F",dzigrarr:"\u27FF",Eacute:"\xC9",eacute:"\xE9",easter:"\u2A6E",Ecaron:"\u011A",ecaron:"\u011B",Ecirc:"\xCA",ecirc:"\xEA",ecir:"\u2256",ecolon:"\u2255",Ecy:"\u042D",ecy:"\u044D",eDDot:"\u2A77",Edot:"\u0116",edot:"\u0117",eDot:"\u2251",ee:"\u2147",efDot:"\u2252",Efr:"\u{1D508}",efr:"\u{1D522}",eg:"\u2A9A",Egrave:"\xC8",egrave:"\xE8",egs:"\u2A96",egsdot:"\u2A98",el:"\u2A99",Element:"\u2208",elinters:"\u23E7",ell:"\u2113",els:"\u2A95",elsdot:"\u2A97",Emacr:"\u0112",emacr:"\u0113",empty:"\u2205",emptyset:"\u2205",EmptySmallSquare:"\u25FB",emptyv:"\u2205",EmptyVerySmallSquare:"\u25AB",emsp13:"\u2004",emsp14:"\u2005",emsp:"\u2003",ENG:"\u014A",eng:"\u014B",ensp:"\u2002",Eogon:"\u0118",eogon:"\u0119",Eopf:"\u{1D53C}",eopf:"\u{1D556}",epar:"\u22D5",eparsl:"\u29E3",eplus:"\u2A71",epsi:"\u03B5",Epsilon:"\u0395",epsilon:"\u03B5",epsiv:"\u03F5",eqcirc:"\u2256",eqcolon:"\u2255",eqsim:"\u2242",eqslantgtr:"\u2A96",eqslantless:"\u2A95",Equal:"\u2A75",equals:"=",EqualTilde:"\u2242",equest:"\u225F",Equilibrium:"\u21CC",equiv:"\u2261",equivDD:"\u2A78",eqvparsl:"\u29E5",erarr:"\u2971",erDot:"\u2253",escr:"\u212F",Escr:"\u2130",esdot:"\u2250",Esim:"\u2A73",esim:"\u2242",Eta:"\u0397",eta:"\u03B7",ETH:"\xD0",eth:"\xF0",Euml:"\xCB",euml:"\xEB",euro:"\u20AC",excl:"!",exist:"\u2203",Exists:"\u2203",expectation:"\u2130",exponentiale:"\u2147",ExponentialE:"\u2147",fallingdotseq:"\u2252",Fcy:"\u0424",fcy:"\u0444",female:"\u2640",ffilig:"\uFB03",fflig:"\uFB00",ffllig:"\uFB04",Ffr:"\u{1D509}",ffr:"\u{1D523}",filig:"\uFB01",FilledSmallSquare:"\u25FC",FilledVerySmallSquare:"\u25AA",fjlig:"fj",flat:"\u266D",fllig:"\uFB02",fltns:"\u25B1",fnof:"\u0192",Fopf:"\u{1D53D}",fopf:"\u{1D557}",forall:"\u2200",ForAll:"\u2200",fork:"\u22D4",forkv:"\u2AD9",Fouriertrf:"\u2131",fpartint:"\u2A0D",frac12:"\xBD",frac13:"\u2153",frac14:"\xBC",frac15:"\u2155",frac16:"\u2159",frac18:"\u215B",frac23:"\u2154",frac25:"\u2156",frac34:"\xBE",frac35:"\u2157",frac38:"\u215C",frac45:"\u2158",frac56:"\u215A",frac58:"\u215D",frac78:"\u215E",frasl:"\u2044",frown:"\u2322",fscr:"\u{1D4BB}",Fscr:"\u2131",gacute:"\u01F5",Gamma:"\u0393",gamma:"\u03B3",Gammad:"\u03DC",gammad:"\u03DD",gap:"\u2A86",Gbreve:"\u011E",gbreve:"\u011F",Gcedil:"\u0122",Gcirc:"\u011C",gcirc:"\u011D",Gcy:"\u0413",gcy:"\u0433",Gdot:"\u0120",gdot:"\u0121",ge:"\u2265",gE:"\u2267",gEl:"\u2A8C",gel:"\u22DB",geq:"\u2265",geqq:"\u2267",geqslant:"\u2A7E",gescc:"\u2AA9",ges:"\u2A7E",gesdot:"\u2A80",gesdoto:"\u2A82",gesdotol:"\u2A84",gesl:"\u22DB\uFE00",gesles:"\u2A94",Gfr:"\u{1D50A}",gfr:"\u{1D524}",gg:"\u226B",Gg:"\u22D9",ggg:"\u22D9",gimel:"\u2137",GJcy:"\u0403",gjcy:"\u0453",gla:"\u2AA5",gl:"\u2277",glE:"\u2A92",glj:"\u2AA4",gnap:"\u2A8A",gnapprox:"\u2A8A",gne:"\u2A88",gnE:"\u2269",gneq:"\u2A88",gneqq:"\u2269",gnsim:"\u22E7",Gopf:"\u{1D53E}",gopf:"\u{1D558}",grave:"`",GreaterEqual:"\u2265",GreaterEqualLess:"\u22DB",GreaterFullEqual:"\u2267",GreaterGreater:"\u2AA2",GreaterLess:"\u2277",GreaterSlantEqual:"\u2A7E",GreaterTilde:"\u2273",Gscr:"\u{1D4A2}",gscr:"\u210A",gsim:"\u2273",gsime:"\u2A8E",gsiml:"\u2A90",gtcc:"\u2AA7",gtcir:"\u2A7A",gt:">",GT:">",Gt:"\u226B",gtdot:"\u22D7",gtlPar:"\u2995",gtquest:"\u2A7C",gtrapprox:"\u2A86",gtrarr:"\u2978",gtrdot:"\u22D7",gtreqless:"\u22DB",gtreqqless:"\u2A8C",gtrless:"\u2277",gtrsim:"\u2273",gvertneqq:"\u2269\uFE00",gvnE:"\u2269\uFE00",Hacek:"\u02C7",hairsp:"\u200A",half:"\xBD",hamilt:"\u210B",HARDcy:"\u042A",hardcy:"\u044A",harrcir:"\u2948",harr:"\u2194",hArr:"\u21D4",harrw:"\u21AD",Hat:"^",hbar:"\u210F",Hcirc:"\u0124",hcirc:"\u0125",hearts:"\u2665",heartsuit:"\u2665",hellip:"\u2026",hercon:"\u22B9",hfr:"\u{1D525}",Hfr:"\u210C",HilbertSpace:"\u210B",hksearow:"\u2925",hkswarow:"\u2926",hoarr:"\u21FF",homtht:"\u223B",hookleftarrow:"\u21A9",hookrightarrow:"\u21AA",hopf:"\u{1D559}",Hopf:"\u210D",horbar:"\u2015",HorizontalLine:"\u2500",hscr:"\u{1D4BD}",Hscr:"\u210B",hslash:"\u210F",Hstrok:"\u0126",hstrok:"\u0127",HumpDownHump:"\u224E",HumpEqual:"\u224F",hybull:"\u2043",hyphen:"\u2010",Iacute:"\xCD",iacute:"\xED",ic:"\u2063",Icirc:"\xCE",icirc:"\xEE",Icy:"\u0418",icy:"\u0438",Idot:"\u0130",IEcy:"\u0415",iecy:"\u0435",iexcl:"\xA1",iff:"\u21D4",ifr:"\u{1D526}",Ifr:"\u2111",Igrave:"\xCC",igrave:"\xEC",ii:"\u2148",iiiint:"\u2A0C",iiint:"\u222D",iinfin:"\u29DC",iiota:"\u2129",IJlig:"\u0132",ijlig:"\u0133",Imacr:"\u012A",imacr:"\u012B",image:"\u2111",ImaginaryI:"\u2148",imagline:"\u2110",imagpart:"\u2111",imath:"\u0131",Im:"\u2111",imof:"\u22B7",imped:"\u01B5",Implies:"\u21D2",incare:"\u2105",in:"\u2208",infin:"\u221E",infintie:"\u29DD",inodot:"\u0131",intcal:"\u22BA",int:"\u222B",Int:"\u222C",integers:"\u2124",Integral:"\u222B",intercal:"\u22BA",Intersection:"\u22C2",intlarhk:"\u2A17",intprod:"\u2A3C",InvisibleComma:"\u2063",InvisibleTimes:"\u2062",IOcy:"\u0401",iocy:"\u0451",Iogon:"\u012E",iogon:"\u012F",Iopf:"\u{1D540}",iopf:"\u{1D55A}",Iota:"\u0399",iota:"\u03B9",iprod:"\u2A3C",iquest:"\xBF",iscr:"\u{1D4BE}",Iscr:"\u2110",isin:"\u2208",isindot:"\u22F5",isinE:"\u22F9",isins:"\u22F4",isinsv:"\u22F3",isinv:"\u2208",it:"\u2062",Itilde:"\u0128",itilde:"\u0129",Iukcy:"\u0406",iukcy:"\u0456",Iuml:"\xCF",iuml:"\xEF",Jcirc:"\u0134",jcirc:"\u0135",Jcy:"\u0419",jcy:"\u0439",Jfr:"\u{1D50D}",jfr:"\u{1D527}",jmath:"\u0237",Jopf:"\u{1D541}",jopf:"\u{1D55B}",Jscr:"\u{1D4A5}",jscr:"\u{1D4BF}",Jsercy:"\u0408",jsercy:"\u0458",Jukcy:"\u0404",jukcy:"\u0454",Kappa:"\u039A",kappa:"\u03BA",kappav:"\u03F0",Kcedil:"\u0136",kcedil:"\u0137",Kcy:"\u041A",kcy:"\u043A",Kfr:"\u{1D50E}",kfr:"\u{1D528}",kgreen:"\u0138",KHcy:"\u0425",khcy:"\u0445",KJcy:"\u040C",kjcy:"\u045C",Kopf:"\u{1D542}",kopf:"\u{1D55C}",Kscr:"\u{1D4A6}",kscr:"\u{1D4C0}",lAarr:"\u21DA",Lacute:"\u0139",lacute:"\u013A",laemptyv:"\u29B4",lagran:"\u2112",Lambda:"\u039B",lambda:"\u03BB",lang:"\u27E8",Lang:"\u27EA",langd:"\u2991",langle:"\u27E8",lap:"\u2A85",Laplacetrf:"\u2112",laquo:"\xAB",larrb:"\u21E4",larrbfs:"\u291F",larr:"\u2190",Larr:"\u219E",lArr:"\u21D0",larrfs:"\u291D",larrhk:"\u21A9",larrlp:"\u21AB",larrpl:"\u2939",larrsim:"\u2973",larrtl:"\u21A2",latail:"\u2919",lAtail:"\u291B",lat:"\u2AAB",late:"\u2AAD",lates:"\u2AAD\uFE00",lbarr:"\u290C",lBarr:"\u290E",lbbrk:"\u2772",lbrace:"{",lbrack:"[",lbrke:"\u298B",lbrksld:"\u298F",lbrkslu:"\u298D",Lcaron:"\u013D",lcaron:"\u013E",Lcedil:"\u013B",lcedil:"\u013C",lceil:"\u2308",lcub:"{",Lcy:"\u041B",lcy:"\u043B",ldca:"\u2936",ldquo:"\u201C",ldquor:"\u201E",ldrdhar:"\u2967",ldrushar:"\u294B",ldsh:"\u21B2",le:"\u2264",lE:"\u2266",LeftAngleBracket:"\u27E8",LeftArrowBar:"\u21E4",leftarrow:"\u2190",LeftArrow:"\u2190",Leftarrow:"\u21D0",LeftArrowRightArrow:"\u21C6",leftarrowtail:"\u21A2",LeftCeiling:"\u2308",LeftDoubleBracket:"\u27E6",LeftDownTeeVector:"\u2961",LeftDownVectorBar:"\u2959",LeftDownVector:"\u21C3",LeftFloor:"\u230A",leftharpoondown:"\u21BD",leftharpoonup:"\u21BC",leftleftarrows:"\u21C7",leftrightarrow:"\u2194",LeftRightArrow:"\u2194",Leftrightarrow:"\u21D4",leftrightarrows:"\u21C6",leftrightharpoons:"\u21CB",leftrightsquigarrow:"\u21AD",LeftRightVector:"\u294E",LeftTeeArrow:"\u21A4",LeftTee:"\u22A3",LeftTeeVector:"\u295A",leftthreetimes:"\u22CB",LeftTriangleBar:"\u29CF",LeftTriangle:"\u22B2",LeftTriangleEqual:"\u22B4",LeftUpDownVector:"\u2951",LeftUpTeeVector:"\u2960",LeftUpVectorBar:"\u2958",LeftUpVector:"\u21BF",LeftVectorBar:"\u2952",LeftVector:"\u21BC",lEg:"\u2A8B",leg:"\u22DA",leq:"\u2264",leqq:"\u2266",leqslant:"\u2A7D",lescc:"\u2AA8",les:"\u2A7D",lesdot:"\u2A7F",lesdoto:"\u2A81",lesdotor:"\u2A83",lesg:"\u22DA\uFE00",lesges:"\u2A93",lessapprox:"\u2A85",lessdot:"\u22D6",lesseqgtr:"\u22DA",lesseqqgtr:"\u2A8B",LessEqualGreater:"\u22DA",LessFullEqual:"\u2266",LessGreater:"\u2276",lessgtr:"\u2276",LessLess:"\u2AA1",lesssim:"\u2272",LessSlantEqual:"\u2A7D",LessTilde:"\u2272",lfisht:"\u297C",lfloor:"\u230A",Lfr:"\u{1D50F}",lfr:"\u{1D529}",lg:"\u2276",lgE:"\u2A91",lHar:"\u2962",lhard:"\u21BD",lharu:"\u21BC",lharul:"\u296A",lhblk:"\u2584",LJcy:"\u0409",ljcy:"\u0459",llarr:"\u21C7",ll:"\u226A",Ll:"\u22D8",llcorner:"\u231E",Lleftarrow:"\u21DA",llhard:"\u296B",lltri:"\u25FA",Lmidot:"\u013F",lmidot:"\u0140",lmoustache:"\u23B0",lmoust:"\u23B0",lnap:"\u2A89",lnapprox:"\u2A89",lne:"\u2A87",lnE:"\u2268",lneq:"\u2A87",lneqq:"\u2268",lnsim:"\u22E6",loang:"\u27EC",loarr:"\u21FD",lobrk:"\u27E6",longleftarrow:"\u27F5",LongLeftArrow:"\u27F5",Longleftarrow:"\u27F8",longleftrightarrow:"\u27F7",LongLeftRightArrow:"\u27F7",Longleftrightarrow:"\u27FA",longmapsto:"\u27FC",longrightarrow:"\u27F6",LongRightArrow:"\u27F6",Longrightarrow:"\u27F9",looparrowleft:"\u21AB",looparrowright:"\u21AC",lopar:"\u2985",Lopf:"\u{1D543}",lopf:"\u{1D55D}",loplus:"\u2A2D",lotimes:"\u2A34",lowast:"\u2217",lowbar:"_",LowerLeftArrow:"\u2199",LowerRightArrow:"\u2198",loz:"\u25CA",lozenge:"\u25CA",lozf:"\u29EB",lpar:"(",lparlt:"\u2993",lrarr:"\u21C6",lrcorner:"\u231F",lrhar:"\u21CB",lrhard:"\u296D",lrm:"\u200E",lrtri:"\u22BF",lsaquo:"\u2039",lscr:"\u{1D4C1}",Lscr:"\u2112",lsh:"\u21B0",Lsh:"\u21B0",lsim:"\u2272",lsime:"\u2A8D",lsimg:"\u2A8F",lsqb:"[",lsquo:"\u2018",lsquor:"\u201A",Lstrok:"\u0141",lstrok:"\u0142",ltcc:"\u2AA6",ltcir:"\u2A79",lt:"<",LT:"<",Lt:"\u226A",ltdot:"\u22D6",lthree:"\u22CB",ltimes:"\u22C9",ltlarr:"\u2976",ltquest:"\u2A7B",ltri:"\u25C3",ltrie:"\u22B4",ltrif:"\u25C2",ltrPar:"\u2996",lurdshar:"\u294A",luruhar:"\u2966",lvertneqq:"\u2268\uFE00",lvnE:"\u2268\uFE00",macr:"\xAF",male:"\u2642",malt:"\u2720",maltese:"\u2720",Map:"\u2905",map:"\u21A6",mapsto:"\u21A6",mapstodown:"\u21A7",mapstoleft:"\u21A4",mapstoup:"\u21A5",marker:"\u25AE",mcomma:"\u2A29",Mcy:"\u041C",mcy:"\u043C",mdash:"\u2014",mDDot:"\u223A",measuredangle:"\u2221",MediumSpace:"\u205F",Mellintrf:"\u2133",Mfr:"\u{1D510}",mfr:"\u{1D52A}",mho:"\u2127",micro:"\xB5",midast:"*",midcir:"\u2AF0",mid:"\u2223",middot:"\xB7",minusb:"\u229F",minus:"\u2212",minusd:"\u2238",minusdu:"\u2A2A",MinusPlus:"\u2213",mlcp:"\u2ADB",mldr:"\u2026",mnplus:"\u2213",models:"\u22A7",Mopf:"\u{1D544}",mopf:"\u{1D55E}",mp:"\u2213",mscr:"\u{1D4C2}",Mscr:"\u2133",mstpos:"\u223E",Mu:"\u039C",mu:"\u03BC",multimap:"\u22B8",mumap:"\u22B8",nabla:"\u2207",Nacute:"\u0143",nacute:"\u0144",nang:"\u2220\u20D2",nap:"\u2249",napE:"\u2A70\u0338",napid:"\u224B\u0338",napos:"\u0149",napprox:"\u2249",natural:"\u266E",naturals:"\u2115",natur:"\u266E",nbsp:"\xA0",nbump:"\u224E\u0338",nbumpe:"\u224F\u0338",ncap:"\u2A43",Ncaron:"\u0147",ncaron:"\u0148",Ncedil:"\u0145",ncedil:"\u0146",ncong:"\u2247",ncongdot:"\u2A6D\u0338",ncup:"\u2A42",Ncy:"\u041D",ncy:"\u043D",ndash:"\u2013",nearhk:"\u2924",nearr:"\u2197",neArr:"\u21D7",nearrow:"\u2197",ne:"\u2260",nedot:"\u2250\u0338",NegativeMediumSpace:"\u200B",NegativeThickSpace:"\u200B",NegativeThinSpace:"\u200B",NegativeVeryThinSpace:"\u200B",nequiv:"\u2262",nesear:"\u2928",nesim:"\u2242\u0338",NestedGreaterGreater:"\u226B",NestedLessLess:"\u226A",NewLine:`
`,nexist:"\u2204",nexists:"\u2204",Nfr:"\u{1D511}",nfr:"\u{1D52B}",ngE:"\u2267\u0338",nge:"\u2271",ngeq:"\u2271",ngeqq:"\u2267\u0338",ngeqslant:"\u2A7E\u0338",nges:"\u2A7E\u0338",nGg:"\u22D9\u0338",ngsim:"\u2275",nGt:"\u226B\u20D2",ngt:"\u226F",ngtr:"\u226F",nGtv:"\u226B\u0338",nharr:"\u21AE",nhArr:"\u21CE",nhpar:"\u2AF2",ni:"\u220B",nis:"\u22FC",nisd:"\u22FA",niv:"\u220B",NJcy:"\u040A",njcy:"\u045A",nlarr:"\u219A",nlArr:"\u21CD",nldr:"\u2025",nlE:"\u2266\u0338",nle:"\u2270",nleftarrow:"\u219A",nLeftarrow:"\u21CD",nleftrightarrow:"\u21AE",nLeftrightarrow:"\u21CE",nleq:"\u2270",nleqq:"\u2266\u0338",nleqslant:"\u2A7D\u0338",nles:"\u2A7D\u0338",nless:"\u226E",nLl:"\u22D8\u0338",nlsim:"\u2274",nLt:"\u226A\u20D2",nlt:"\u226E",nltri:"\u22EA",nltrie:"\u22EC",nLtv:"\u226A\u0338",nmid:"\u2224",NoBreak:"\u2060",NonBreakingSpace:"\xA0",nopf:"\u{1D55F}",Nopf:"\u2115",Not:"\u2AEC",not:"\xAC",NotCongruent:"\u2262",NotCupCap:"\u226D",NotDoubleVerticalBar:"\u2226",NotElement:"\u2209",NotEqual:"\u2260",NotEqualTilde:"\u2242\u0338",NotExists:"\u2204",NotGreater:"\u226F",NotGreaterEqual:"\u2271",NotGreaterFullEqual:"\u2267\u0338",NotGreaterGreater:"\u226B\u0338",NotGreaterLess:"\u2279",NotGreaterSlantEqual:"\u2A7E\u0338",NotGreaterTilde:"\u2275",NotHumpDownHump:"\u224E\u0338",NotHumpEqual:"\u224F\u0338",notin:"\u2209",notindot:"\u22F5\u0338",notinE:"\u22F9\u0338",notinva:"\u2209",notinvb:"\u22F7",notinvc:"\u22F6",NotLeftTriangleBar:"\u29CF\u0338",NotLeftTriangle:"\u22EA",NotLeftTriangleEqual:"\u22EC",NotLess:"\u226E",NotLessEqual:"\u2270",NotLessGreater:"\u2278",NotLessLess:"\u226A\u0338",NotLessSlantEqual:"\u2A7D\u0338",NotLessTilde:"\u2274",NotNestedGreaterGreater:"\u2AA2\u0338",NotNestedLessLess:"\u2AA1\u0338",notni:"\u220C",notniva:"\u220C",notnivb:"\u22FE",notnivc:"\u22FD",NotPrecedes:"\u2280",NotPrecedesEqual:"\u2AAF\u0338",NotPrecedesSlantEqual:"\u22E0",NotReverseElement:"\u220C",NotRightTriangleBar:"\u29D0\u0338",NotRightTriangle:"\u22EB",NotRightTriangleEqual:"\u22ED",NotSquareSubset:"\u228F\u0338",NotSquareSubsetEqual:"\u22E2",NotSquareSuperset:"\u2290\u0338",NotSquareSupersetEqual:"\u22E3",NotSubset:"\u2282\u20D2",NotSubsetEqual:"\u2288",NotSucceeds:"\u2281",NotSucceedsEqual:"\u2AB0\u0338",NotSucceedsSlantEqual:"\u22E1",NotSucceedsTilde:"\u227F\u0338",NotSuperset:"\u2283\u20D2",NotSupersetEqual:"\u2289",NotTilde:"\u2241",NotTildeEqual:"\u2244",NotTildeFullEqual:"\u2247",NotTildeTilde:"\u2249",NotVerticalBar:"\u2224",nparallel:"\u2226",npar:"\u2226",nparsl:"\u2AFD\u20E5",npart:"\u2202\u0338",npolint:"\u2A14",npr:"\u2280",nprcue:"\u22E0",nprec:"\u2280",npreceq:"\u2AAF\u0338",npre:"\u2AAF\u0338",nrarrc:"\u2933\u0338",nrarr:"\u219B",nrArr:"\u21CF",nrarrw:"\u219D\u0338",nrightarrow:"\u219B",nRightarrow:"\u21CF",nrtri:"\u22EB",nrtrie:"\u22ED",nsc:"\u2281",nsccue:"\u22E1",nsce:"\u2AB0\u0338",Nscr:"\u{1D4A9}",nscr:"\u{1D4C3}",nshortmid:"\u2224",nshortparallel:"\u2226",nsim:"\u2241",nsime:"\u2244",nsimeq:"\u2244",nsmid:"\u2224",nspar:"\u2226",nsqsube:"\u22E2",nsqsupe:"\u22E3",nsub:"\u2284",nsubE:"\u2AC5\u0338",nsube:"\u2288",nsubset:"\u2282\u20D2",nsubseteq:"\u2288",nsubseteqq:"\u2AC5\u0338",nsucc:"\u2281",nsucceq:"\u2AB0\u0338",nsup:"\u2285",nsupE:"\u2AC6\u0338",nsupe:"\u2289",nsupset:"\u2283\u20D2",nsupseteq:"\u2289",nsupseteqq:"\u2AC6\u0338",ntgl:"\u2279",Ntilde:"\xD1",ntilde:"\xF1",ntlg:"\u2278",ntriangleleft:"\u22EA",ntrianglelefteq:"\u22EC",ntriangleright:"\u22EB",ntrianglerighteq:"\u22ED",Nu:"\u039D",nu:"\u03BD",num:"#",numero:"\u2116",numsp:"\u2007",nvap:"\u224D\u20D2",nvdash:"\u22AC",nvDash:"\u22AD",nVdash:"\u22AE",nVDash:"\u22AF",nvge:"\u2265\u20D2",nvgt:">\u20D2",nvHarr:"\u2904",nvinfin:"\u29DE",nvlArr:"\u2902",nvle:"\u2264\u20D2",nvlt:"<\u20D2",nvltrie:"\u22B4\u20D2",nvrArr:"\u2903",nvrtrie:"\u22B5\u20D2",nvsim:"\u223C\u20D2",nwarhk:"\u2923",nwarr:"\u2196",nwArr:"\u21D6",nwarrow:"\u2196",nwnear:"\u2927",Oacute:"\xD3",oacute:"\xF3",oast:"\u229B",Ocirc:"\xD4",ocirc:"\xF4",ocir:"\u229A",Ocy:"\u041E",ocy:"\u043E",odash:"\u229D",Odblac:"\u0150",odblac:"\u0151",odiv:"\u2A38",odot:"\u2299",odsold:"\u29BC",OElig:"\u0152",oelig:"\u0153",ofcir:"\u29BF",Ofr:"\u{1D512}",ofr:"\u{1D52C}",ogon:"\u02DB",Ograve:"\xD2",ograve:"\xF2",ogt:"\u29C1",ohbar:"\u29B5",ohm:"\u03A9",oint:"\u222E",olarr:"\u21BA",olcir:"\u29BE",olcross:"\u29BB",oline:"\u203E",olt:"\u29C0",Omacr:"\u014C",omacr:"\u014D",Omega:"\u03A9",omega:"\u03C9",Omicron:"\u039F",omicron:"\u03BF",omid:"\u29B6",ominus:"\u2296",Oopf:"\u{1D546}",oopf:"\u{1D560}",opar:"\u29B7",OpenCurlyDoubleQuote:"\u201C",OpenCurlyQuote:"\u2018",operp:"\u29B9",oplus:"\u2295",orarr:"\u21BB",Or:"\u2A54",or:"\u2228",ord:"\u2A5D",order:"\u2134",orderof:"\u2134",ordf:"\xAA",ordm:"\xBA",origof:"\u22B6",oror:"\u2A56",orslope:"\u2A57",orv:"\u2A5B",oS:"\u24C8",Oscr:"\u{1D4AA}",oscr:"\u2134",Oslash:"\xD8",oslash:"\xF8",osol:"\u2298",Otilde:"\xD5",otilde:"\xF5",otimesas:"\u2A36",Otimes:"\u2A37",otimes:"\u2297",Ouml:"\xD6",ouml:"\xF6",ovbar:"\u233D",OverBar:"\u203E",OverBrace:"\u23DE",OverBracket:"\u23B4",OverParenthesis:"\u23DC",para:"\xB6",parallel:"\u2225",par:"\u2225",parsim:"\u2AF3",parsl:"\u2AFD",part:"\u2202",PartialD:"\u2202",Pcy:"\u041F",pcy:"\u043F",percnt:"%",period:".",permil:"\u2030",perp:"\u22A5",pertenk:"\u2031",Pfr:"\u{1D513}",pfr:"\u{1D52D}",Phi:"\u03A6",phi:"\u03C6",phiv:"\u03D5",phmmat:"\u2133",phone:"\u260E",Pi:"\u03A0",pi:"\u03C0",pitchfork:"\u22D4",piv:"\u03D6",planck:"\u210F",planckh:"\u210E",plankv:"\u210F",plusacir:"\u2A23",plusb:"\u229E",pluscir:"\u2A22",plus:"+",plusdo:"\u2214",plusdu:"\u2A25",pluse:"\u2A72",PlusMinus:"\xB1",plusmn:"\xB1",plussim:"\u2A26",plustwo:"\u2A27",pm:"\xB1",Poincareplane:"\u210C",pointint:"\u2A15",popf:"\u{1D561}",Popf:"\u2119",pound:"\xA3",prap:"\u2AB7",Pr:"\u2ABB",pr:"\u227A",prcue:"\u227C",precapprox:"\u2AB7",prec:"\u227A",preccurlyeq:"\u227C",Precedes:"\u227A",PrecedesEqual:"\u2AAF",PrecedesSlantEqual:"\u227C",PrecedesTilde:"\u227E",preceq:"\u2AAF",precnapprox:"\u2AB9",precneqq:"\u2AB5",precnsim:"\u22E8",pre:"\u2AAF",prE:"\u2AB3",precsim:"\u227E",prime:"\u2032",Prime:"\u2033",primes:"\u2119",prnap:"\u2AB9",prnE:"\u2AB5",prnsim:"\u22E8",prod:"\u220F",Product:"\u220F",profalar:"\u232E",profline:"\u2312",profsurf:"\u2313",prop:"\u221D",Proportional:"\u221D",Proportion:"\u2237",propto:"\u221D",prsim:"\u227E",prurel:"\u22B0",Pscr:"\u{1D4AB}",pscr:"\u{1D4C5}",Psi:"\u03A8",psi:"\u03C8",puncsp:"\u2008",Qfr:"\u{1D514}",qfr:"\u{1D52E}",qint:"\u2A0C",qopf:"\u{1D562}",Qopf:"\u211A",qprime:"\u2057",Qscr:"\u{1D4AC}",qscr:"\u{1D4C6}",quaternions:"\u210D",quatint:"\u2A16",quest:"?",questeq:"\u225F",quot:'"',QUOT:'"',rAarr:"\u21DB",race:"\u223D\u0331",Racute:"\u0154",racute:"\u0155",radic:"\u221A",raemptyv:"\u29B3",rang:"\u27E9",Rang:"\u27EB",rangd:"\u2992",range:"\u29A5",rangle:"\u27E9",raquo:"\xBB",rarrap:"\u2975",rarrb:"\u21E5",rarrbfs:"\u2920",rarrc:"\u2933",rarr:"\u2192",Rarr:"\u21A0",rArr:"\u21D2",rarrfs:"\u291E",rarrhk:"\u21AA",rarrlp:"\u21AC",rarrpl:"\u2945",rarrsim:"\u2974",Rarrtl:"\u2916",rarrtl:"\u21A3",rarrw:"\u219D",ratail:"\u291A",rAtail:"\u291C",ratio:"\u2236",rationals:"\u211A",rbarr:"\u290D",rBarr:"\u290F",RBarr:"\u2910",rbbrk:"\u2773",rbrace:"}",rbrack:"]",rbrke:"\u298C",rbrksld:"\u298E",rbrkslu:"\u2990",Rcaron:"\u0158",rcaron:"\u0159",Rcedil:"\u0156",rcedil:"\u0157",rceil:"\u2309",rcub:"}",Rcy:"\u0420",rcy:"\u0440",rdca:"\u2937",rdldhar:"\u2969",rdquo:"\u201D",rdquor:"\u201D",rdsh:"\u21B3",real:"\u211C",realine:"\u211B",realpart:"\u211C",reals:"\u211D",Re:"\u211C",rect:"\u25AD",reg:"\xAE",REG:"\xAE",ReverseElement:"\u220B",ReverseEquilibrium:"\u21CB",ReverseUpEquilibrium:"\u296F",rfisht:"\u297D",rfloor:"\u230B",rfr:"\u{1D52F}",Rfr:"\u211C",rHar:"\u2964",rhard:"\u21C1",rharu:"\u21C0",rharul:"\u296C",Rho:"\u03A1",rho:"\u03C1",rhov:"\u03F1",RightAngleBracket:"\u27E9",RightArrowBar:"\u21E5",rightarrow:"\u2192",RightArrow:"\u2192",Rightarrow:"\u21D2",RightArrowLeftArrow:"\u21C4",rightarrowtail:"\u21A3",RightCeiling:"\u2309",RightDoubleBracket:"\u27E7",RightDownTeeVector:"\u295D",RightDownVectorBar:"\u2955",RightDownVector:"\u21C2",RightFloor:"\u230B",rightharpoondown:"\u21C1",rightharpoonup:"\u21C0",rightleftarrows:"\u21C4",rightleftharpoons:"\u21CC",rightrightarrows:"\u21C9",rightsquigarrow:"\u219D",RightTeeArrow:"\u21A6",RightTee:"\u22A2",RightTeeVector:"\u295B",rightthreetimes:"\u22CC",RightTriangleBar:"\u29D0",RightTriangle:"\u22B3",RightTriangleEqual:"\u22B5",RightUpDownVector:"\u294F",RightUpTeeVector:"\u295C",RightUpVectorBar:"\u2954",RightUpVector:"\u21BE",RightVectorBar:"\u2953",RightVector:"\u21C0",ring:"\u02DA",risingdotseq:"\u2253",rlarr:"\u21C4",rlhar:"\u21CC",rlm:"\u200F",rmoustache:"\u23B1",rmoust:"\u23B1",rnmid:"\u2AEE",roang:"\u27ED",roarr:"\u21FE",robrk:"\u27E7",ropar:"\u2986",ropf:"\u{1D563}",Ropf:"\u211D",roplus:"\u2A2E",rotimes:"\u2A35",RoundImplies:"\u2970",rpar:")",rpargt:"\u2994",rppolint:"\u2A12",rrarr:"\u21C9",Rrightarrow:"\u21DB",rsaquo:"\u203A",rscr:"\u{1D4C7}",Rscr:"\u211B",rsh:"\u21B1",Rsh:"\u21B1",rsqb:"]",rsquo:"\u2019",rsquor:"\u2019",rthree:"\u22CC",rtimes:"\u22CA",rtri:"\u25B9",rtrie:"\u22B5",rtrif:"\u25B8",rtriltri:"\u29CE",RuleDelayed:"\u29F4",ruluhar:"\u2968",rx:"\u211E",Sacute:"\u015A",sacute:"\u015B",sbquo:"\u201A",scap:"\u2AB8",Scaron:"\u0160",scaron:"\u0161",Sc:"\u2ABC",sc:"\u227B",sccue:"\u227D",sce:"\u2AB0",scE:"\u2AB4",Scedil:"\u015E",scedil:"\u015F",Scirc:"\u015C",scirc:"\u015D",scnap:"\u2ABA",scnE:"\u2AB6",scnsim:"\u22E9",scpolint:"\u2A13",scsim:"\u227F",Scy:"\u0421",scy:"\u0441",sdotb:"\u22A1",sdot:"\u22C5",sdote:"\u2A66",searhk:"\u2925",searr:"\u2198",seArr:"\u21D8",searrow:"\u2198",sect:"\xA7",semi:";",seswar:"\u2929",setminus:"\u2216",setmn:"\u2216",sext:"\u2736",Sfr:"\u{1D516}",sfr:"\u{1D530}",sfrown:"\u2322",sharp:"\u266F",SHCHcy:"\u0429",shchcy:"\u0449",SHcy:"\u0428",shcy:"\u0448",ShortDownArrow:"\u2193",ShortLeftArrow:"\u2190",shortmid:"\u2223",shortparallel:"\u2225",ShortRightArrow:"\u2192",ShortUpArrow:"\u2191",shy:"\xAD",Sigma:"\u03A3",sigma:"\u03C3",sigmaf:"\u03C2",sigmav:"\u03C2",sim:"\u223C",simdot:"\u2A6A",sime:"\u2243",simeq:"\u2243",simg:"\u2A9E",simgE:"\u2AA0",siml:"\u2A9D",simlE:"\u2A9F",simne:"\u2246",simplus:"\u2A24",simrarr:"\u2972",slarr:"\u2190",SmallCircle:"\u2218",smallsetminus:"\u2216",smashp:"\u2A33",smeparsl:"\u29E4",smid:"\u2223",smile:"\u2323",smt:"\u2AAA",smte:"\u2AAC",smtes:"\u2AAC\uFE00",SOFTcy:"\u042C",softcy:"\u044C",solbar:"\u233F",solb:"\u29C4",sol:"/",Sopf:"\u{1D54A}",sopf:"\u{1D564}",spades:"\u2660",spadesuit:"\u2660",spar:"\u2225",sqcap:"\u2293",sqcaps:"\u2293\uFE00",sqcup:"\u2294",sqcups:"\u2294\uFE00",Sqrt:"\u221A",sqsub:"\u228F",sqsube:"\u2291",sqsubset:"\u228F",sqsubseteq:"\u2291",sqsup:"\u2290",sqsupe:"\u2292",sqsupset:"\u2290",sqsupseteq:"\u2292",square:"\u25A1",Square:"\u25A1",SquareIntersection:"\u2293",SquareSubset:"\u228F",SquareSubsetEqual:"\u2291",SquareSuperset:"\u2290",SquareSupersetEqual:"\u2292",SquareUnion:"\u2294",squarf:"\u25AA",squ:"\u25A1",squf:"\u25AA",srarr:"\u2192",Sscr:"\u{1D4AE}",sscr:"\u{1D4C8}",ssetmn:"\u2216",ssmile:"\u2323",sstarf:"\u22C6",Star:"\u22C6",star:"\u2606",starf:"\u2605",straightepsilon:"\u03F5",straightphi:"\u03D5",strns:"\xAF",sub:"\u2282",Sub:"\u22D0",subdot:"\u2ABD",subE:"\u2AC5",sube:"\u2286",subedot:"\u2AC3",submult:"\u2AC1",subnE:"\u2ACB",subne:"\u228A",subplus:"\u2ABF",subrarr:"\u2979",subset:"\u2282",Subset:"\u22D0",subseteq:"\u2286",subseteqq:"\u2AC5",SubsetEqual:"\u2286",subsetneq:"\u228A",subsetneqq:"\u2ACB",subsim:"\u2AC7",subsub:"\u2AD5",subsup:"\u2AD3",succapprox:"\u2AB8",succ:"\u227B",succcurlyeq:"\u227D",Succeeds:"\u227B",SucceedsEqual:"\u2AB0",SucceedsSlantEqual:"\u227D",SucceedsTilde:"\u227F",succeq:"\u2AB0",succnapprox:"\u2ABA",succneqq:"\u2AB6",succnsim:"\u22E9",succsim:"\u227F",SuchThat:"\u220B",sum:"\u2211",Sum:"\u2211",sung:"\u266A",sup1:"\xB9",sup2:"\xB2",sup3:"\xB3",sup:"\u2283",Sup:"\u22D1",supdot:"\u2ABE",supdsub:"\u2AD8",supE:"\u2AC6",supe:"\u2287",supedot:"\u2AC4",Superset:"\u2283",SupersetEqual:"\u2287",suphsol:"\u27C9",suphsub:"\u2AD7",suplarr:"\u297B",supmult:"\u2AC2",supnE:"\u2ACC",supne:"\u228B",supplus:"\u2AC0",supset:"\u2283",Supset:"\u22D1",supseteq:"\u2287",supseteqq:"\u2AC6",supsetneq:"\u228B",supsetneqq:"\u2ACC",supsim:"\u2AC8",supsub:"\u2AD4",supsup:"\u2AD6",swarhk:"\u2926",swarr:"\u2199",swArr:"\u21D9",swarrow:"\u2199",swnwar:"\u292A",szlig:"\xDF",Tab:"	",target:"\u2316",Tau:"\u03A4",tau:"\u03C4",tbrk:"\u23B4",Tcaron:"\u0164",tcaron:"\u0165",Tcedil:"\u0162",tcedil:"\u0163",Tcy:"\u0422",tcy:"\u0442",tdot:"\u20DB",telrec:"\u2315",Tfr:"\u{1D517}",tfr:"\u{1D531}",there4:"\u2234",therefore:"\u2234",Therefore:"\u2234",Theta:"\u0398",theta:"\u03B8",thetasym:"\u03D1",thetav:"\u03D1",thickapprox:"\u2248",thicksim:"\u223C",ThickSpace:"\u205F\u200A",ThinSpace:"\u2009",thinsp:"\u2009",thkap:"\u2248",thksim:"\u223C",THORN:"\xDE",thorn:"\xFE",tilde:"\u02DC",Tilde:"\u223C",TildeEqual:"\u2243",TildeFullEqual:"\u2245",TildeTilde:"\u2248",timesbar:"\u2A31",timesb:"\u22A0",times:"\xD7",timesd:"\u2A30",tint:"\u222D",toea:"\u2928",topbot:"\u2336",topcir:"\u2AF1",top:"\u22A4",Topf:"\u{1D54B}",topf:"\u{1D565}",topfork:"\u2ADA",tosa:"\u2929",tprime:"\u2034",trade:"\u2122",TRADE:"\u2122",triangle:"\u25B5",triangledown:"\u25BF",triangleleft:"\u25C3",trianglelefteq:"\u22B4",triangleq:"\u225C",triangleright:"\u25B9",trianglerighteq:"\u22B5",tridot:"\u25EC",trie:"\u225C",triminus:"\u2A3A",TripleDot:"\u20DB",triplus:"\u2A39",trisb:"\u29CD",tritime:"\u2A3B",trpezium:"\u23E2",Tscr:"\u{1D4AF}",tscr:"\u{1D4C9}",TScy:"\u0426",tscy:"\u0446",TSHcy:"\u040B",tshcy:"\u045B",Tstrok:"\u0166",tstrok:"\u0167",twixt:"\u226C",twoheadleftarrow:"\u219E",twoheadrightarrow:"\u21A0",Uacute:"\xDA",uacute:"\xFA",uarr:"\u2191",Uarr:"\u219F",uArr:"\u21D1",Uarrocir:"\u2949",Ubrcy:"\u040E",ubrcy:"\u045E",Ubreve:"\u016C",ubreve:"\u016D",Ucirc:"\xDB",ucirc:"\xFB",Ucy:"\u0423",ucy:"\u0443",udarr:"\u21C5",Udblac:"\u0170",udblac:"\u0171",udhar:"\u296E",ufisht:"\u297E",Ufr:"\u{1D518}",ufr:"\u{1D532}",Ugrave:"\xD9",ugrave:"\xF9",uHar:"\u2963",uharl:"\u21BF",uharr:"\u21BE",uhblk:"\u2580",ulcorn:"\u231C",ulcorner:"\u231C",ulcrop:"\u230F",ultri:"\u25F8",Umacr:"\u016A",umacr:"\u016B",uml:"\xA8",UnderBar:"_",UnderBrace:"\u23DF",UnderBracket:"\u23B5",UnderParenthesis:"\u23DD",Union:"\u22C3",UnionPlus:"\u228E",Uogon:"\u0172",uogon:"\u0173",Uopf:"\u{1D54C}",uopf:"\u{1D566}",UpArrowBar:"\u2912",uparrow:"\u2191",UpArrow:"\u2191",Uparrow:"\u21D1",UpArrowDownArrow:"\u21C5",updownarrow:"\u2195",UpDownArrow:"\u2195",Updownarrow:"\u21D5",UpEquilibrium:"\u296E",upharpoonleft:"\u21BF",upharpoonright:"\u21BE",uplus:"\u228E",UpperLeftArrow:"\u2196",UpperRightArrow:"\u2197",upsi:"\u03C5",Upsi:"\u03D2",upsih:"\u03D2",Upsilon:"\u03A5",upsilon:"\u03C5",UpTeeArrow:"\u21A5",UpTee:"\u22A5",upuparrows:"\u21C8",urcorn:"\u231D",urcorner:"\u231D",urcrop:"\u230E",Uring:"\u016E",uring:"\u016F",urtri:"\u25F9",Uscr:"\u{1D4B0}",uscr:"\u{1D4CA}",utdot:"\u22F0",Utilde:"\u0168",utilde:"\u0169",utri:"\u25B5",utrif:"\u25B4",uuarr:"\u21C8",Uuml:"\xDC",uuml:"\xFC",uwangle:"\u29A7",vangrt:"\u299C",varepsilon:"\u03F5",varkappa:"\u03F0",varnothing:"\u2205",varphi:"\u03D5",varpi:"\u03D6",varpropto:"\u221D",varr:"\u2195",vArr:"\u21D5",varrho:"\u03F1",varsigma:"\u03C2",varsubsetneq:"\u228A\uFE00",varsubsetneqq:"\u2ACB\uFE00",varsupsetneq:"\u228B\uFE00",varsupsetneqq:"\u2ACC\uFE00",vartheta:"\u03D1",vartriangleleft:"\u22B2",vartriangleright:"\u22B3",vBar:"\u2AE8",Vbar:"\u2AEB",vBarv:"\u2AE9",Vcy:"\u0412",vcy:"\u0432",vdash:"\u22A2",vDash:"\u22A8",Vdash:"\u22A9",VDash:"\u22AB",Vdashl:"\u2AE6",veebar:"\u22BB",vee:"\u2228",Vee:"\u22C1",veeeq:"\u225A",vellip:"\u22EE",verbar:"|",Verbar:"\u2016",vert:"|",Vert:"\u2016",VerticalBar:"\u2223",VerticalLine:"|",VerticalSeparator:"\u2758",VerticalTilde:"\u2240",VeryThinSpace:"\u200A",Vfr:"\u{1D519}",vfr:"\u{1D533}",vltri:"\u22B2",vnsub:"\u2282\u20D2",vnsup:"\u2283\u20D2",Vopf:"\u{1D54D}",vopf:"\u{1D567}",vprop:"\u221D",vrtri:"\u22B3",Vscr:"\u{1D4B1}",vscr:"\u{1D4CB}",vsubnE:"\u2ACB\uFE00",vsubne:"\u228A\uFE00",vsupnE:"\u2ACC\uFE00",vsupne:"\u228B\uFE00",Vvdash:"\u22AA",vzigzag:"\u299A",Wcirc:"\u0174",wcirc:"\u0175",wedbar:"\u2A5F",wedge:"\u2227",Wedge:"\u22C0",wedgeq:"\u2259",weierp:"\u2118",Wfr:"\u{1D51A}",wfr:"\u{1D534}",Wopf:"\u{1D54E}",wopf:"\u{1D568}",wp:"\u2118",wr:"\u2240",wreath:"\u2240",Wscr:"\u{1D4B2}",wscr:"\u{1D4CC}",xcap:"\u22C2",xcirc:"\u25EF",xcup:"\u22C3",xdtri:"\u25BD",Xfr:"\u{1D51B}",xfr:"\u{1D535}",xharr:"\u27F7",xhArr:"\u27FA",Xi:"\u039E",xi:"\u03BE",xlarr:"\u27F5",xlArr:"\u27F8",xmap:"\u27FC",xnis:"\u22FB",xodot:"\u2A00",Xopf:"\u{1D54F}",xopf:"\u{1D569}",xoplus:"\u2A01",xotime:"\u2A02",xrarr:"\u27F6",xrArr:"\u27F9",Xscr:"\u{1D4B3}",xscr:"\u{1D4CD}",xsqcup:"\u2A06",xuplus:"\u2A04",xutri:"\u25B3",xvee:"\u22C1",xwedge:"\u22C0",Yacute:"\xDD",yacute:"\xFD",YAcy:"\u042F",yacy:"\u044F",Ycirc:"\u0176",ycirc:"\u0177",Ycy:"\u042B",ycy:"\u044B",yen:"\xA5",Yfr:"\u{1D51C}",yfr:"\u{1D536}",YIcy:"\u0407",yicy:"\u0457",Yopf:"\u{1D550}",yopf:"\u{1D56A}",Yscr:"\u{1D4B4}",yscr:"\u{1D4CE}",YUcy:"\u042E",yucy:"\u044E",yuml:"\xFF",Yuml:"\u0178",Zacute:"\u0179",zacute:"\u017A",Zcaron:"\u017D",zcaron:"\u017E",Zcy:"\u0417",zcy:"\u0437",Zdot:"\u017B",zdot:"\u017C",zeetrf:"\u2128",ZeroWidthSpace:"\u200B",Zeta:"\u0396",zeta:"\u03B6",zfr:"\u{1D537}",Zfr:"\u2128",ZHcy:"\u0416",zhcy:"\u0436",zigrarr:"\u21DD",zopf:"\u{1D56B}",Zopf:"\u2124",Zscr:"\u{1D4B5}",zscr:"\u{1D4CF}",zwj:"\u200D",zwnj:"\u200C"}},function(c,d,i){function t(o){var n,s,l=e[o];if(l)return l;for(l=e[o]=[],n=0;n<128;n++)s=String.fromCharCode(n),l.push(s);for(n=0;n<o.length;n++)s=o.charCodeAt(n),l[s]="%"+("0"+s.toString(16).toUpperCase()).slice(-2);return l}function r(o,n){var s;return typeof n!="string"&&(n=r.defaultChars),s=t(n),o.replace(/(%[a-f0-9]{2})+/gi,function(l){var u,a,p,_,v,g,C,m="";for(u=0,a=l.length;u<a;u+=3)p=parseInt(l.slice(u+1,u+3),16),p<128?m+=s[p]:(224&p)==192&&u+3<a&&(192&(_=parseInt(l.slice(u+4,u+6),16)))==128?(C=p<<6&1984|63&_,m+=C<128?"\uFFFD\uFFFD":String.fromCharCode(C),u+=3):(240&p)==224&&u+6<a&&(_=parseInt(l.slice(u+4,u+6),16),v=parseInt(l.slice(u+7,u+9),16),(192&_)==128&&(192&v)==128)?(C=p<<12&61440|_<<6&4032|63&v,m+=C<2048||C>=55296&&C<=57343?"\uFFFD\uFFFD\uFFFD":String.fromCharCode(C),u+=6):(248&p)==240&&u+9<a&&(_=parseInt(l.slice(u+4,u+6),16),v=parseInt(l.slice(u+7,u+9),16),g=parseInt(l.slice(u+10,u+12),16),(192&_)==128&&(192&v)==128&&(192&g)==128)?(C=p<<18&1835008|_<<12&258048|v<<6&4032|63&g,C<65536||C>1114111?m+="\uFFFD\uFFFD\uFFFD\uFFFD":(C-=65536,m+=String.fromCharCode(55296+(C>>10),56320+(1023&C))),u+=9):m+="\uFFFD";return m})}var e={};r.defaultChars=";/?:@&=+$,#",r.componentChars="",c.exports=r},function(c,d,i){function t(o){var n,s,l=e[o];if(l)return l;for(l=e[o]=[],n=0;n<128;n++)s=String.fromCharCode(n),/^[0-9a-z]$/i.test(s)?l.push(s):l.push("%"+("0"+n.toString(16).toUpperCase()).slice(-2));for(n=0;n<o.length;n++)l[o.charCodeAt(n)]=o[n];return l}function r(o,n,s){var l,u,a,p,_,v="";for(typeof n!="string"&&(s=n,n=r.defaultChars),s===void 0&&(s=!0),_=t(n),l=0,u=o.length;l<u;l++)if(a=o.charCodeAt(l),s&&a===37&&l+2<u&&/^[0-9a-f]{2}$/i.test(o.slice(l+1,l+3)))v+=o.slice(l,l+3),l+=2;else if(a<128)v+=_[a];else if(a>=55296&&a<=57343){if(a>=55296&&a<=56319&&l+1<u&&(p=o.charCodeAt(l+1))>=56320&&p<=57343){v+=encodeURIComponent(o[l]+o[l+1]),l++;continue}v+="%EF%BF%BD"}else v+=encodeURIComponent(o[l]);return v}var e={};r.defaultChars=";/?:@&=+$,-_.!~*'()#",r.componentChars="-_.!~*'()",c.exports=r},function(c,d,i){c.exports=function(t){var r="";return r+=t.protocol||"",r+=t.slashes?"//":"",r+=t.auth?t.auth+"@":"",t.hostname&&t.hostname.indexOf(":")!==-1?r+="["+t.hostname+"]":r+=t.hostname||"",r+=t.port?":"+t.port:"",r+=t.pathname||"",r+=t.search||"",r+=t.hash||""}},function(c,d,i){function t(){this.protocol=null,this.slashes=null,this.auth=null,this.port=null,this.hostname=null,this.hash=null,this.search=null,this.pathname=null}function r(m,h){if(m&&m instanceof t)return m;var w=new t;return w.parse(m,h),w}var e=/^([a-z0-9.+-]+:)/i,o=/:[0-9]*$/,n=/^(\/\/?(?!\/)[^\?\s]*)(\?[^\s]*)?$/,s=["<",">",'"',"`"," ","\r",`
`,"	"],l=["{","}","|","\\","^","`"].concat(s),u=["'"].concat(l),a=["%","/","?",";","#"].concat(u),p=["/","?","#"],_=/^[+a-z0-9A-Z_-]{0,63}$/,v=/^([+a-z0-9A-Z_-]{0,63})(.*)$/,g={javascript:!0,"javascript:":!0},C={http:!0,https:!0,ftp:!0,gopher:!0,file:!0,"http:":!0,"https:":!0,"ftp:":!0,"gopher:":!0,"file:":!0};t.prototype.parse=function(m,h){var w,f,k,b,y,x=m;if(x=x.trim(),!h&&m.split("#").length===1){var E=n.exec(x);if(E)return this.pathname=E[1],E[2]&&(this.search=E[2]),this}var D=e.exec(x);if(D&&(D=D[0],k=D.toLowerCase(),this.protocol=D,x=x.substr(D.length)),(h||D||x.match(/^\/\/[^@\/]+@[^@\/]+/))&&(!(y=x.substr(0,2)==="//")||D&&g[D]||(x=x.substr(2),this.slashes=!0)),!g[D]&&(y||D&&!C[D])){var T=-1;for(w=0;w<p.length;w++)(b=x.indexOf(p[w]))!==-1&&(T===-1||b<T)&&(T=b);var L,A;for(A=T===-1?x.lastIndexOf("@"):x.lastIndexOf("@",T),A!==-1&&(L=x.slice(0,A),x=x.slice(A+1),this.auth=L),T=-1,w=0;w<a.length;w++)(b=x.indexOf(a[w]))!==-1&&(T===-1||b<T)&&(T=b);T===-1&&(T=x.length),x[T-1]===":"&&T--;var $=x.slice(0,T);x=x.slice(T),this.parseHost($),this.hostname=this.hostname||"";var z=this.hostname[0]==="["&&this.hostname[this.hostname.length-1]==="]";if(!z){var j=this.hostname.split(/\./);for(w=0,f=j.length;w<f;w++){var M=j[w];if(M&&!M.match(_)){for(var R="",W=0,S=M.length;W<S;W++)M.charCodeAt(W)>127?R+="x":R+=M[W];if(!R.match(_)){var O=j.slice(0,w),q=j.slice(w+1),I=M.match(v);I&&(O.push(I[1]),q.unshift(I[2])),q.length&&(x=q.join(".")+x),this.hostname=O.join(".");break}}}}this.hostname.length>255&&(this.hostname=""),z&&(this.hostname=this.hostname.substr(1,this.hostname.length-2))}var B=x.indexOf("#");B!==-1&&(this.hash=x.substr(B),x=x.slice(0,B));var U=x.indexOf("?");return U!==-1&&(this.search=x.substr(U),x=x.slice(0,U)),x&&(this.pathname=x),C[k]&&this.hostname&&!this.pathname&&(this.pathname=""),this},t.prototype.parseHost=function(m){var h=o.exec(m);h&&(h=h[0],h!==":"&&(this.port=h.substr(1)),m=m.substr(0,m.length-h.length)),m&&(this.hostname=m)},c.exports=r},function(c,d,i){(function(t,r){var e;(function(o){function n(S){throw new RangeError(j[S])}function s(S,O){for(var q=S.length,I=[];q--;)I[q]=O(S[q]);return I}function l(S,O){var q=S.split("@"),I="";return q.length>1&&(I=q[0]+"@",S=q[1]),S=S.replace(z,"."),I+s(S.split("."),O).join(".")}function u(S){for(var O,q,I=[],B=0,U=S.length;B<U;)O=S.charCodeAt(B++),O>=55296&&O<=56319&&B<U?(q=S.charCodeAt(B++),(64512&q)==56320?I.push(((1023&O)<<10)+(1023&q)+65536):(I.push(O),B--)):I.push(O);return I}function a(S){return s(S,function(O){var q="";return O>65535&&(O-=65536,q+=W(O>>>10&1023|55296),O=56320|1023&O),q+=W(O)}).join("")}function p(S){return S-48<10?S-22:S-65<26?S-65:S-97<26?S-97:k}function _(S,O){return S+22+75*(S<26)-((O!=0)<<5)}function v(S,O,q){var I=0;for(S=q?R(S/E):S>>1,S+=R(S/O);S>M*y>>1;I+=k)S=R(S/M);return R(I+(M+1)*S/(S+x))}function g(S){var O,q,I,B,U,N,V,K,G,Q,J=[],Y=S.length,Z=0,nt=T,et=D;for(q=S.lastIndexOf(L),q<0&&(q=0),I=0;I<q;++I)S.charCodeAt(I)>=128&&n("not-basic"),J.push(S.charCodeAt(I));for(B=q>0?q+1:0;B<Y;){for(U=Z,N=1,V=k;B>=Y&&n("invalid-input"),K=p(S.charCodeAt(B++)),(K>=k||K>R((f-Z)/N))&&n("overflow"),Z+=K*N,G=V<=et?b:V>=et+y?y:V-et,!(K<G);V+=k)Q=k-G,N>R(f/Q)&&n("overflow"),N*=Q;O=J.length+1,et=v(Z-U,O,U==0),R(Z/O)>f-nt&&n("overflow"),nt+=R(Z/O),Z%=O,J.splice(Z++,0,nt)}return a(J)}function C(S){var O,q,I,B,U,N,V,K,G,Q,J,Y,Z,nt,et,rt=[];for(S=u(S),Y=S.length,O=T,q=0,U=D,N=0;N<Y;++N)(J=S[N])<128&&rt.push(W(J));for(I=B=rt.length,B&&rt.push(L);I<Y;){for(V=f,N=0;N<Y;++N)(J=S[N])>=O&&J<V&&(V=J);for(Z=I+1,V-O>R((f-q)/Z)&&n("overflow"),q+=(V-O)*Z,O=V,N=0;N<Y;++N)if(J=S[N],J<O&&++q>f&&n("overflow"),J==O){for(K=q,G=k;Q=G<=U?b:G>=U+y?y:G-U,!(K<Q);G+=k)et=K-Q,nt=k-Q,rt.push(W(_(Q+et%nt,0))),K=R(et/nt);rt.push(W(_(K,0))),U=v(q,Z,I==B),q=0,++I}++q,++O}return rt.join("")}function m(S){return l(S,function(O){return A.test(O)?g(O.slice(4).toLowerCase()):O})}function h(S){return l(S,function(O){return $.test(O)?"xn--"+C(O):O})}typeof d=="object"&&d&&d.nodeType,typeof t=="object"&&t&&t.nodeType;var w,f=2147483647,k=36,b=1,y=26,x=38,E=700,D=72,T=128,L="-",A=/^xn--/,$=/[^\x20-\x7E]/,z=/[\x2E\u3002\uFF0E\uFF61]/g,j={overflow:"Overflow: input needs wider integers to process","not-basic":"Illegal input >= 0x80 (not a basic code point)","invalid-input":"Invalid input"},M=k-b,R=Math.floor,W=String.fromCharCode;w={version:"1.4.1",ucs2:{decode:u,encode:a},decode:g,encode:C,toASCII:h,toUnicode:m},(e=function(){return w}.call(d,i,d,t))!==void 0&&(t.exports=e)})()}).call(d,i(208)(c),i(207))},function(c,d){c.exports=`@[toc](Catalog)

Markdown Handbuch
===
> Details: [http://commonmark.org/help/](http://commonmark.org/help/)

## **Fett**
\`\`\`
**fett**
__fett__
\`\`\`
## *Kursiv*
\`\`\`
*kursiv*
_kursiv_
\`\`\`
## \xDCberschriften
\`\`\`
# h1 #
h1
====
## h2 ##
h2
----
### h3 ###
#### h4 ####
##### h5 #####
###### h6 ######
\`\`\`
## Trennlinien
\`\`\`
***
---
\`\`\`
****
## ^Hoch^gestellt & ~Tief~gestellt
\`\`\`
hochgestellt x^2^
tiefgestellt H~2~0
\`\`\`
## ++Unterstrichen++ & ~~Durchgestrichen~~
\`\`\`
++unterstrichen++
~~durchgestrichen~~
\`\`\`
## ==Markiert==
\`\`\`
==markiert==
\`\`\`
## Zitat

\`\`\`
> zitat 1
>> zitat 2
>>> zitat 3
...
\`\`\`

## Liste
\`\`\`
ol
1.
2.
3.
...

ul
-
-
...
\`\`\`

## Todo Liste

- [x] aufgabe 1
- [ ] aufgabe 2

\`\`\`
- [x] aufgabe 1
- [ ] aufgabe 2
\`\`\`

## Link
\`\`\`
Text Link
[Text](www.baidu.com)

Link mit Bild
![Text](http://www.image.com)
\`\`\`
## Code
\\\`\`\` Typ

Codeblock

\\\`\`\`

\\\` code \\\`

\`\`\`c++
int main()
{
    printf("hello world!");
}
\`\`\`
\`code\`

## Tabelle
\`\`\`
| th1 | th2 | th3 |
| :--  | :--: | ----: |
| links | mitte | rechts |
\`\`\`
| th1 | th2 | th3 |
| :--  | :--: | ----: |
| links | mitte | rechts |
| ---------------------- | ------------- | ----------------- |
## Fu\xDFnote
\`\`\`
hallo[^hallo]
\`\`\`

Schau zum unteren Rand[^hallo]

[^hallo]: fussnote

## Emojis
Details: [https://www.webpagefx.com/tools/emoji-cheat-sheet/](https://www.webpagefx.com/tools/emoji-cheat-sheet/)
\`\`\`
:laughing:
:blush:
:smiley:
:)
...
\`\`\`
:laughing::blush::smiley::)

## $\\KaTeX$ Mathematik

Formeln lassen sich darstellen z.b. \uFF1A$x_i + y_i = z_i$ und $\\sum_{i=1}^n a_i=0$
Formeln k\xF6nnen auf einer eigenen Zeile gerendert werden
$$\\sum_{i=1}^n a_i=0$$
Details: [katex](http://www.intmath.com/cg5/katex-mathjax-comparison.php)\u548C[katex function](https://github.com/Khan/KaTeX/wiki/Function-Support-in-KaTeX)\u4EE5\u53CA[latex](https://math.meta.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference)

## Layout

::: hljs-left
\`::: hljs-left\`
\`links\`
\`:::\`
:::

::: hljs-center
\`::: hljs-center\`
\`mitte\`
\`:::\`
:::

::: hljs-right
\`::: hljs-right\`
\`rechts\`
\`:::\`
:::

## Liste von Definitionen

Term 1

:   Definition 1

Term 2 mit *inline markup*

:   Definition 2

        { ein wenig code, teil von Definition 2 }

    Dritter Absatz von Definition 2.

\`\`\`
Term 1

:   Definition 1

Term 2 mit *inline markup*

:   Definition 2

        { ein wenig code, teil von Definition 2 }

    Dritter Absatz von Definition 2.

\`\`\`

## Abk\xFCrzungen
*[HTML]: Hyper Text Markup Language
*[W3C]:  World Wide Web Consortium
Die HTML Spezifikation
wird gepflegt vom W3C.
\`\`\`
*[HTML]: Hyper Text Markup Language
*[W3C]:  World Wide Web Consortium
Die HTML Spezifikation
wird gepflegt vom W3C.
\`\`\`
`},function(c,d){c.exports=`@[toc](Catalog)

Markdown Guide
===
> Detailed: [http://commonmark.org/help/](http://commonmark.org/help/)

## **Bold**
\`\`\`
**bold**
__bold__
\`\`\`
## *Italic*
\`\`\`
*italic*
_italic_
\`\`\`
## Header
\`\`\`
# h1 #
h1
====
## h2 ##
h2
----
### h3 ###
#### h4 ####
##### h5 #####
###### h6 ######
\`\`\`
## Dividing line
\`\`\`
***
---
\`\`\`
****
## ^Super^script & ~Sub~script
\`\`\`
super x^2^
sub H~2~0
\`\`\`
## ++Underline++ & ~~Strikethrough~~
\`\`\`
++underline++
~~strikethrough~~
\`\`\`
## ==Mark==
\`\`\`
==mark==
\`\`\`
## Quote

\`\`\`
> quote 1
>> quote 2
>>> quote 3
...
\`\`\`

## List
\`\`\`
ol
1.
2.
3.
...

ul
-
-
...
\`\`\`

## Todo List

- [x] task 1
- [ ] task 2

\`\`\`
- [x] task 1
- [ ] task 2
\`\`\`

## Link
\`\`\`
Text Link
[Text](www.baidu.com)

Image Link
![Text](http://www.image.com)
\`\`\`
## Code
\\\`\`\` type

code block

\\\`\`\`

\\\` code \\\`

\`\`\`c++
int main()
{
    printf("hello world!");
}
\`\`\`
\`code\`

## Table
\`\`\`
| th1 | th2 | th3 |
| :--  | :--: | ----: |
| left | center | right |
\`\`\`
| th1 | th2 | th3 |
| :--  | :--: | ----: |
| left | center | right |
| ---------------------- | ------------- | ----------------- |
## Footnote
\`\`\`
hello[^hello]
\`\`\`

Look at the bottom[^hello]

[^hello]: footnote

## Emojis
Detailed: [https://www.webpagefx.com/tools/emoji-cheat-sheet/](https://www.webpagefx.com/tools/emoji-cheat-sheet/)
\`\`\`
:laughing:
:blush:
:smiley:
:)
...
\`\`\`
:laughing::blush::smiley::)

## $\\KaTeX$ Mathematics

We can render formulas for example\uFF1A$x_i + y_i = z_i$ and $\\sum_{i=1}^n a_i=0$
We can also single-line rendering
$$\\sum_{i=1}^n a_i=0$$
Detailed: [katex](http://www.intmath.com/cg5/katex-mathjax-comparison.php)\u548C[katex function](https://github.com/Khan/KaTeX/wiki/Function-Support-in-KaTeX)\u4EE5\u53CA[latex](https://math.meta.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference)

## Layout

::: hljs-left
\`::: hljs-left\`
\`left\`
\`:::\`
:::

::: hljs-center
\`::: hljs-center\`
\`center\`
\`:::\`
:::

::: hljs-right
\`::: hljs-right\`
\`right\`
\`:::\`
:::

## deflist

Term 1

:   Definition 1

Term 2 with *inline markup*

:   Definition 2

        { some code, part of Definition 2 }

    Third paragraph of definition 2.

\`\`\`
Term 1

:   Definition 1

Term 2 with *inline markup*

:   Definition 2

        { some code, part of Definition 2 }

    Third paragraph of definition 2.

\`\`\`

## abbr
*[HTML]: Hyper Text Markup Language
*[W3C]:  World Wide Web Consortium
The HTML specification
is maintained by the W3C.
\`\`\`
*[HTML]: Hyper Text Markup Language
*[W3C]:  World Wide Web Consortium
The HTML specification
is maintained by the W3C.
\`\`\`
`},function(c,d){c.exports=`@[toc](Catalogue)

Guide Markdown
==============
> D\xE9tail : [http://commonmark.org/help/](http://commonmark.org/help/)

## **Bold**
\`\`\`
**bold**
__bold__
\`\`\`
## *Italic*
\`\`\`
*italic*
_italic_
\`\`\`
## Header
\`\`\`
# h1 #
h1
====
## h2 ##
h2
----
### h3 ###
#### h4 ####
##### h5 #####
###### h6 ######
\`\`\`
## Dividing line
\`\`\`
***
---
\`\`\`
****
## ^Super^script & ~Sub~script
\`\`\`
super x^2^
sub H~2~0
\`\`\`
## ++Underline++ & ~~Strikethrough~~
\`\`\`
++underline++
~~strikethrough~~
\`\`\`
## ==Mark==
\`\`\`
==mark==
\`\`\`
## Quote

\`\`\`
> quote 1
>> quote 2
>>> quote 3
...
\`\`\`

## List
\`\`\`
ol
1.
2.
3.
...

ul
-
-
...
\`\`\`
## Link

## Todo List

- [x] \xC9quipe 1
- [ ] \xC9quipe 2

\`\`\`
- [x] \xC9quipe 1
- [ ] \xC9quipe 2
\`\`\`

\`\`\`
Text Link
[Text](www.baidu.com)

Image Link
![Text](http://www.image.com)
\`\`\`
## Code
\\\`\`\` type

code block

\\\`\`\`

\\\` code \\\`

\`\`\`c++
int main()
{
    printf("hello world!");
}
\`\`\`
\`code\`

## Table
\`\`\`
| th1 | th2 | th3 |
| :--  | :--: | ----: |
| left | center | right |
\`\`\`
| th1 | th2 | th3 |
| :--  | :--: | ----: |
| left | center | right |
| ---------------------- | ------------- | ----------------- |
## Footnote
\`\`\`
hello[^hello]
\`\`\`

Look at the bottom[^hello]

[^hello]: footnote

## Emojis
Detailed: [https://www.webpagefx.com/tools/emoji-cheat-sheet/](https://www.webpagefx.com/tools/emoji-cheat-sheet/)
\`\`\`
:laughing:
:blush:
:smiley:
:)
...
\`\`\`
:laughing::blush::smiley::)

## $\\KaTeX$ Mathematics

We can render formulas for example\uFF1A$x_i + y_i = z_i$ and $\\sum_{i=1}^n a_i=0$
We can also single-line rendering
$$\\sum_{i=1}^n a_i=0$$
Detailed: [katex](http://www.intmath.com/cg5/katex-mathjax-comparison.php)\u548C[katex function](https://github.com/Khan/KaTeX/wiki/Function-Support-in-KaTeX)\u4EE5\u53CA[latex](https://math.meta.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference)

## Layout

::: hljs-left
\`::: hljs-left\`
\`left\`
\`:::\`
:::

::: hljs-center
\`::: hljs-center\`
\`center\`
\`:::\`
:::

::: hljs-right
\`::: hljs-right\`
\`right\`
\`:::\`
:::

## deflist

Term 1

:   Definition 1

Term 2 with *inline markup*

:   Definition 2

        { some code, part of Definition 2 }

    Third paragraph of definition 2.

\`\`\`
Term 1

:   Definition 1

Term 2 with *inline markup*

:   Definition 2

        { some code, part of Definition 2 }

    Third paragraph of definition 2.

\`\`\`

## abbr
*[HTML]: Hyper Text Markup Language
*[W3C]:  World Wide Web Consortium
The HTML specification
is maintained by the W3C.
\`\`\`
*[HTML]: Hyper Text Markup Language
*[W3C]:  World Wide Web Consortium
The HTML specification
is maintained by the W3C.
\`\`\`
`},function(c,d){c.exports=`@[toc](\u76EE\u6B21)

Markdown \u6587\u6CD5\u30AC\u30A4\u30C9
===
> Detailed: [http://commonmark.org/help/](http://commonmark.org/help/)

## **\u592A\u5B57**
\`\`\`
**\u592A\u5B57**
__\u592A\u5B57__
\`\`\`
## *\u659C\u4F53*
\`\`\`
*\u659C\u4F53*
_\u659C\u4F53_
\`\`\`
## \u898B\u51FA\u3057
\`\`\`
# h1 #
h1
====
## h2 ##
h2
----
### h3 ###
#### h4 ####
##### h5 #####
###### h6 ######
\`\`\`
## \u6A2A\u7DDA
\`\`\`
***
---
\`\`\`
****
## ^\u4E0A\u4ED8\u304D^\u6587\u5B57 & ~\u4E0B\u4ED8\u304D~\u6587\u5B57
\`\`\`
super x^2^
sub H~2~0
\`\`\`
## ++\u4E0B\u7DDA++ & ~~\u53D6\u308A\u6D88\u3057\u7DDA~~
\`\`\`
++underline++
~~strikethrough~~
\`\`\`
## ==\u86CD\u5149\u30DA\u30F3==
\`\`\`
==mark==
\`\`\`
## \u5F15\u7528

\`\`\`
> quote 1
>> quote 2
>>> quote 3
...
\`\`\`

## \u30EA\u30B9\u30C8
\`\`\`
\u756A\u53F7\u4ED8\u304D\u30EA\u30B9\u30C8
1.
2.
3.
...

\u7B87\u6761\u66F8\u304D\u30EA\u30B9\u30C8
-
-
...
\`\`\`

## Todo\u30EA\u30B9\u30C8

- [x] task 1
- [ ] task 2

\`\`\`
- [x] task 1
- [ ] task 2
\`\`\`

## \u30EA\u30F3\u30AF
\`\`\`
Text Link
[Text](www.baidu.com)

Image Link
![Text](http://www.image.com)
\`\`\`
## \u30B3\u30FC\u30C9
\\\`\`\` type

code block

\\\`\`\`

\\\` code \\\`

\`\`\`c++
int main()
{
    printf("hello world!");
}
\`\`\`
\`code\`

## \u8868
\`\`\`
| th1 | th2 | th3 |
| :--  | :--: | ----: |
| left | center | right |
\`\`\`
| th1 | th2 | th3 |
| :--  | :--: | ----: |
| left | center | right |
| ---------------------- | ------------- | ----------------- |

## \u811A\u6CE8
\`\`\`
hello[^hello]
\`\`\`

Look at the bottom[^hello]

[^hello]: footnote

## \u7D75\u6587\u5B57
> Detailed: [https://www.webpagefx.com/tools/emoji-cheat-sheet/](https://www.webpagefx.com/tools/emoji-cheat-sheet/)
\`\`\`
:laughing:
:blush:
:smiley:
:)
...
\`\`\`
:laughing::blush::smiley::)

## $\\KaTeX$ \u6570\u5F0F
> Detailed: [KaTeX\u30DE\u30CB\u30E5\u30A2\u30EB](http://www.intmath.com/cg5/katex-mathjax-comparison.php)\u3001[KaTeX function](https://github.com/Khan/KaTeX/wiki/Function-Support-in-KaTeX)\u3001[LaTeX\u30DE\u30CB\u30E5\u30A2\u30EB](https://math.meta.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference)

We can render formulas for example\uFF1A$x_i + y_i = z_i$ and $\\sum_{i=1}^n a_i=0$  
We can also single-line rendering
$$\\sum_{i=1}^n a_i=0$$

## \u30EC\u30A4\u30A2\u30A6\u30C8

::: hljs-left
\`::: hljs-left\`
\`left\`
\`:::\`
:::

::: hljs-center
\`::: hljs-center\`
\`center\`
\`:::\`
:::

::: hljs-right
\`::: hljs-right\`
\`right\`
\`:::\`
:::

## \u5B9A\u7FA9\u30EA\u30B9\u30C8

Term 1

:   Definition 1

Term 2 with *inline markup*

:   Definition 2

        { some code, part of Definition 2 }

    Third paragraph of definition 2.

\`\`\`
Term 1

:   Definition 1

Term 2 with *inline markup*

:   Definition 2

        { some code, part of Definition 2 }

    Third paragraph of definition 2.

\`\`\`

## abbr
*[HTML]: Hyper Text Markup Language
*[W3C]:  World Wide Web Consortium
The HTML specification
is maintained by the W3C.
\`\`\`
*[HTML]: Hyper Text Markup Language
*[W3C]:  World Wide Web Consortium
The HTML specification
is maintained by the W3C.
\`\`\`
`},function(c,d){c.exports=`@[toc](Directory)

Guia Markdown
===
> Detalhes: [http://commonmark.org/help/](http://commonmark.org/help/)

## **Negrito**
\`\`\`
**negrito**
__negrito__
\`\`\`
## *It\xE1lico*
\`\`\`
*it\xE1lico*
_it\xE1lico_
\`\`\`
## Cabe\xE7alho
\`\`\`
# h1 #
h1
====
## h2 ##
h2
----
### h3 ###
#### h4 ####
##### h5 #####
###### h6 ######
\`\`\`
## Linha Divisora
\`\`\`
***
---
\`\`\`
****
## ^Sobre^scrito & ~Sub~scrito
\`\`\`
sobre x^2^
sub H~2~0
\`\`\`
## ++Sublinhar++ & ~~Tachar~~
\`\`\`
++sublinhar++
~~tachar~~
\`\`\`
## ==Marcador==
\`\`\`
==marcador==
\`\`\`
## Cita\xE7\xE3o

\`\`\`
> cita\xE7\xE3o 1
>> cita\xE7\xE3o 2
>>> cita\xE7\xE3o 3
...
\`\`\`

## Listas
\`\`\`
lista Numerada
1.
2.
3.
...

lista com marcadores
-
-
...
\`\`\`

## Todo Listas

- [x] Tarefa 1
- [ ] Tarefa 2

\`\`\`
- [x] Tarefa 1
- [ ] Tarefa 2
\`\`\`

## Link
\`\`\`
Link Texto
[Text](www.baidu.com)

Link Imagem
![Text](http://www.image.com)
\`\`\`
## C\xF3digo
\\\`\`\` tipo

bloco de c\xF3digo

\\\`\`\`

\\\` c\xF3digo \\\`

\`\`\`c++
int main()
{
    printf("hello world!");
}
\`\`\`
\`code\`

## Tabela
\`\`\`
| th1 | th2 | th3 |
| :--  | :--: | ----: |
| esquerda | centro | direita |
\`\`\`
| th1 | th2 | th3 |
| :--  | :--: | ----: |
| esquerda | centro | direita |
| ---------------------- | ------------- | ----------------- |
## Rodap\xE9
\`\`\`
ol\xE1[^ol\xE1]
\`\`\`

Olhe para baixo[^ol\xE1]

[^ol\xE1]: rodap\xE9

## Emojis
Detalhes: [https://www.webpagefx.com/tools/emoji-cheat-sheet/](https://www.webpagefx.com/tools/emoji-cheat-sheet/)
\`\`\`
:laughing:
:blush:
:smiley:
:)
...
\`\`\`
:laughing::blush::smiley::)

## $\\KaTeX$ Mathematics

Podemos mostrar f\xF3rmulas por exemplo\uFF1A$x_i + y_i = z_i$ and $\\sum_{i=1}^n a_i=0$
Podemos tamb\xE9m mostrar em uma \xFAnica linha:
$$\\sum_{i=1}^n a_i=0$$
Detalhes: [katex](http://www.intmath.com/cg5/katex-mathjax-comparison.php)\u548C[katex function](https://github.com/Khan/KaTeX/wiki/Function-Support-in-KaTeX)\u4EE5\u53CA[latex](https://math.meta.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference)

## Layout

::: hljs-left
\`::: hljs-left\`
\`esquerda\`
\`:::\`
:::

::: hljs-center
\`::: hljs-center\`
\`centro\`
\`:::\`
:::

::: hljs-right
\`::: hljs-right\`
\`direita\`
\`:::\`
:::

## Defini\xE7\xF5es

Termo 1

:   Defini\xE7\xE3o 1

Termo 2 com *markup inline*

:   Defini\xE7\xE3o 2

        { um pouco de c\xF3digo, parte da Defini\xE7\xE3o 2 }

    Terceiro par\xE1grafo da defini\xE7\xE3o 2.

\`\`\`
Termo 1

:   Defini\xE7\xE3o 1

Termo 2 com *markup inline*

:   Defini\xE7\xE3o 2

        { um pouco de c\xF3digo, parte da Defini\xE7\xE3o 2 }

    Terceiro par\xE1grafo da defini\xE7\xE3o 2.

\`\`\`

## Abrevia\xE7\xF5es
*[HTML]: Hyper Text Markup Language
*[W3C]:  World Wide Web Consortium
A especifica\xE7\xE3o HTML
\xE9 mantida pela W3C.
\`\`\`
*[HTML]: Hyper Text Markup Language
*[W3C]:  World Wide Web Consortium
The HTML specification
\xE9 mantida pela W3C.
\`\`\`
`},function(c,d){c.exports=`@[toc](Catalog)  
  
Markdown \u043F\u043E\u043C\u043E\u0449\u044C  
===  
> \u041F\u043E\u0434\u0440\u043E\u0431\u043D\u0435\u0435: [http://commonmark.org/help/](http://commonmark.org/help/)  
  
## **\u041F\u043E\u043B\u0443\u0436\u0438\u0440\u043D\u044B\u0439**  
\`\`\`  
**\u041F\u043E\u043B\u0443\u0436\u0438\u0440\u043D\u044B\u0439**  
__\u041F\u043E\u043B\u0443\u0436\u0438\u0440\u043D\u044B\u0439__  
\`\`\`  
## *\u041A\u0443\u0440\u0441\u0438\u0432*  
\`\`\`  
*\u041A\u0443\u0440\u0441\u0438\u0432*  
_\u041A\u0443\u0440\u0441\u0438\u0432_  
\`\`\`  
## \u0417\u0430\u0433\u043E\u043B\u043E\u0432\u043E\u043A  
\`\`\`  
# h1 #  
h1  
====  
## h2 ##  
h2  
----  
### h3 ###  
#### h4 ####  
##### h5 #####  
###### h6 ######  
\`\`\`  
## \u0420\u0430\u0437\u0434\u0435\u043B\u0438\u0442\u0435\u043B\u044C\u043D\u0430\u044F \u043B\u0438\u043D\u0438\u044F  
\`\`\`  
***  
---  
\`\`\`  
****  
## ^\u0412\u0435\u0440\u0445\u043D\u0438\u0439^\u0438\u043D\u0434\u0435\u043A\u0441 & ~\u041D\u0438\u0436\u043D\u0438\u0439~\u0438\u043D\u0434\u0435\u043A\u0441  
\`\`\`  
\u0432\u0435\u0440\u0445\u043D\u0438\u0439 x^2^  
\u043D\u0438\u0436\u043D\u0438\u0439 H~2~0  
\`\`\`  
## ++\u041F\u043E\u0434\u0447\u0435\u0440\u043A\u043D\u0443\u0442\u044B\u0439++ & ~~\u0417\u0430\u0447\u0435\u0440\u043A\u043D\u0443\u0442\u044B\u0439~~  
\`\`\`  
++\u041F\u043E\u0434\u0447\u0435\u0440\u043A\u043D\u0443\u0442\u044B\u0439++  
~~\u0417\u0430\u0447\u0435\u0440\u043A\u043D\u0443\u0442\u044B\u0439~~  
\`\`\`  
## ==\u041E\u0442\u043C\u0435\u0442\u043A\u0430==  
\`\`\`  
==\u041E\u0442\u043C\u0435\u0442\u043A\u0430==  
\`\`\`  
## \u0426\u0438\u0442\u0430\u0442\u0430  
  
\`\`\`  
> \u0426\u0438\u0442\u0430\u0442\u0430  
>> \u0426\u0438\u0442\u0430\u0442\u0430 2  
>>> \u0426\u0438\u0442\u0430\u0442\u0430 3  
...  
\`\`\`  
  
## \u0421\u043F\u0438\u0441\u043E\u043A  
\`\`\`  
ol  
1.  
2.  
3.  
...  
  
ul  
-  
-  
...  
\`\`\`  
  
## \u0421\u043F\u0438\u0441\u043E\u043A \u0437\u0430\u0434\u0430\u0447  
  
- [x] \u0417\u0430\u0434\u0430\u0447\u0430 1  
- [ ] \u0417\u0430\u0434\u0430\u0447\u0430 2  
  
\`\`\`  
- [x] \u0417\u0430\u0434\u0430\u0447\u0430 1  
- [ ] \u0417\u0430\u0434\u0430\u0447\u0430 2  
\`\`\`  
  
## \u0421\u0441\u044B\u043B\u043A\u0430  
\`\`\`  
\u0421\u0441\u044B\u043B\u043A\u0430  
[\u0422\u0435\u043A\u0441\u0442](www.baidu.com)  
  
\u0421\u0441\u044B\u043B\u043A\u0430 \u0438\u0437\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u044F  
![\u0422\u0435\u043A\u0441\u0442](http://www.image.com)  
\`\`\`  
## \u041A\u043E\u0434  
\\\`\`\` type  
  
code block  
  
\\\`\`\`  
  
\\\` code \\\`  
  
\`\`\`c++  
int main()  
{  
 printf("hello world!");}  
\`\`\`  
\`code\`  
  
## \u0422\u0430\u0431\u043B\u0438\u0446\u0430  
\`\`\`  
| th1 | th2 | th3 |  
| :--  | :--: | ----: |  
| left | center | right |  
\`\`\`  
| th1 | th2 | th3 |  
| :--  | :--: | ----: |  
| left | center | right |  
| ---------------------- | ------------- | ----------------- |  
## \u0421\u043D\u043E\u0441\u043A\u0430  
\`\`\`  
\u041F\u0440\u0438\u0432\u0435\u0442[^\u041F\u0440\u0438\u0432\u0435\u0442]  
\`\`\`  
  
\u0422\u0443\u0442 \u0447\u0442\u043E-\u0442\u043E \u043D\u0435\u043F\u043E\u043D\u044F\u0442\u043D\u043E\u0435[^\u041F\u0440\u0438\u0432\u0435\u0442]  
  
[^\u041F\u0440\u0438\u0432\u0435\u0442]: \u0410 \u0442\u0443\u0442 \u043E\u0431\u044A\u044F\u0441\u043D\u0435\u043D\u0438\u0435  
  
## Emojis  
\u041F\u043E\u0434\u0440\u043E\u0431\u043D\u0435\u0435: [https://www.webpagefx.com/tools/emoji-cheat-sheet/](https://www.webpagefx.com/tools/emoji-cheat-sheet/)  
\`\`\`  
:laughing:  
:blush:  
:smiley:  
:)  
...  
\`\`\`  
:laughing::blush::smiley::)  
  
## $\\KaTeX$ Mathematics  
  
\u041C\u043E\u0436\u043D\u043E \u0432\u044B\u0432\u043E\u0434\u0438\u0442\u044C \u0442\u0430\u043A\u0438\u0435 \u0444\u043E\u0440\u043C\u0443\u043B\u044B\uFF1A$x_i + y_i = z_i$ and $\\sum_{i=1}^n a_i=0$  
\u0410 \u0442\u0430\u043A\u0436\u0435 \u0432 \u043E\u0434\u043D\u0443 \u0441\u0442\u0440\u043E\u043A\u0443:
$$\\sum_{i=1}^n a_i=0$$  
\u041F\u043E\u0434\u0440\u043E\u0431\u043D\u0435\u0435: 
- [katex](http://www.intmath.com/cg5/katex-mathjax-comparison.php)
- [katex function](https://github.com/Khan/KaTeX/wiki/Function-Support-in-KaTeX)
- [latex](https://math.meta.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference)  
  
## \u0420\u0430\u0437\u043C\u0435\u0442\u043A\u0430
  
::: hljs-left  
\`::: hljs-left\`  
\`left\`  
\`:::\`  
:::  
  
::: hljs-center  
\`::: hljs-center\`  
\`center\`  
\`:::\`  
:::  
  
::: hljs-right  
\`::: hljs-right\`  
\`right\`  
\`:::\`  
:::  
  
## \u0421\u043F\u0438\u0441\u043E\u043A \u043E\u043F\u0440\u0435\u0434\u0435\u043B\u0435\u043D\u0438\u0439
  
\u0422\u0435\u0440\u043C\u0438\u043D 1  
  
:   \u041E\u043F\u0440\u0435\u0434\u0435\u043B\u0435\u043D\u0438\u0435 1  
  
\u0422\u0435\u0440\u043C\u0438\u043D  2 \u0441 \u0438\u0441\u043F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u043D\u0438\u0435\u043C *\u0440\u0430\u0437\u043C\u0435\u0442\u043A\u0438*
  
:   \u041E\u043F\u0440\u0435\u0434\u0435\u043B\u0435\u043D\u0438\u0435 2  
  
 { \u041A\u0430\u043A\u043E\u0439-\u043D\u0438\u0431\u0443\u0434\u044C \u043A\u043E\u0434, \u0447\u0430\u0441\u0442\u044C \u043E\u043F\u0440\u0435\u0434\u0435\u043B\u0435\u043D\u0438\u044F 2 }  
 \u0422\u0440\u0435\u0442\u0438\u0439 \u043F\u0430\u0440\u0430\u0433\u0440\u0430\u0444 \u043E\u043F\u0440\u0435\u0434\u0435\u043B\u0435\u043D\u0438\u044F 2.  
\`\`\`  
\u0422\u0435\u0440\u043C\u0438\u043D 1  
  
:   \u041E\u043F\u0440\u0435\u0434\u0435\u043B\u0435\u043D\u0438\u0435 1  
  
\u0422\u0435\u0440\u043C\u0438\u043D  2 \u0441 \u0438\u0441\u043F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u043D\u0438\u0435\u043C *\u0440\u0430\u0437\u043C\u0435\u0442\u043A\u0438*
  
:   \u041E\u043F\u0440\u0435\u0434\u0435\u043B\u0435\u043D\u0438\u0435 2  
  
 { \u041A\u0430\u043A\u043E\u0439-\u043D\u0438\u0431\u0443\u0434\u044C \u043A\u043E\u0434, \u0447\u0430\u0441\u0442\u044C \u043E\u043F\u0440\u0435\u0434\u0435\u043B\u0435\u043D\u0438\u044F 2 }  
 \u0422\u0440\u0435\u0442\u0438\u0439 \u043F\u0430\u0440\u0430\u0433\u0440\u0430\u0444 \u043E\u043F\u0440\u0435\u0434\u0435\u043B\u0435\u043D\u0438\u044F 2.  
\`\`\`  
  
## \u0421\u043E\u043A\u0440\u0430\u0449\u0435\u043D\u0438\u044F
*[HTML]: Hyper Text Markup Language  
*[W3C]:  World Wide Web Consortium  
The HTML specification  
is maintained by the W3C.  
\`\`\`  
*[HTML]: Hyper Text Markup Language  
*[W3C]:  World Wide Web Consortium  
The HTML specification  
is maintained by the W3C.  
\`\`\`
`},function(c,d){c.exports=`@[toc](\u76EE\u5F55)

Markdown \u8BED\u6CD5\u7B80\u4ECB
=============
> [\u8BED\u6CD5\u8BE6\u89E3](http://commonmark.org/help/)

## **\u76EE\u5F55**
\`\`\`
@[toc](\u76EE\u5F55)
\`\`\`

## **\u7C97\u4F53**
\`\`\`
**\u7C97\u4F53**
__\u7C97\u4F53__
\`\`\`
## *\u659C\u4F53*
\`\`\`
*\u659C\u4F53*
_\u659C\u4F53_
\`\`\`
## \u6807\u9898
\`\`\`
# \u4E00\u7EA7\u6807\u9898 #
\u4E00\u7EA7\u6807\u9898
====
## \u4E8C\u7EA7\u6807\u9898 ##
\u4E8C\u7EA7\u6807\u9898
----
### \u4E09\u7EA7\u6807\u9898 ###
#### \u56DB\u7EA7\u6807\u9898 ####
##### \u4E94\u7EA7\u6807\u9898 #####
###### \u516D\u7EA7\u6807\u9898 ######
\`\`\`
## \u5206\u5272\u7EBF
\`\`\`
***
---
\`\`\`
****
## ^\u4E0A^\u89D2~\u4E0B~\u6807
\`\`\`
\u4E0A\u89D2\u6807 x^2^
\u4E0B\u89D2\u6807 H~2~0
\`\`\`
## ++\u4E0B\u5212\u7EBF++ ~~\u4E2D\u5212\u7EBF~~
\`\`\`
++\u4E0B\u5212\u7EBF++
~~\u4E2D\u5212\u7EBF~~
\`\`\`
## ==\u6807\u8BB0==
\`\`\`
==\u6807\u8BB0==
\`\`\`
## \u6BB5\u843D\u5F15\u7528
\`\`\`
> \u4E00\u7EA7
>> \u4E8C\u7EA7
>>> \u4E09\u7EA7
...
\`\`\`

## \u5217\u8868
\`\`\`
\u6709\u5E8F\u5217\u8868
1.
2.
3.
...
\u65E0\u5E8F\u5217\u8868
-
-
...
\`\`\`

## \u4EFB\u52A1\u5217\u8868

- [x] \u5DF2\u5B8C\u6210\u4EFB\u52A1
- [ ] \u672A\u5B8C\u6210\u4EFB\u52A1

\`\`\`
- [x] \u5DF2\u5B8C\u6210\u4EFB\u52A1
- [ ] \u672A\u5B8C\u6210\u4EFB\u52A1
\`\`\`

## \u94FE\u63A5
\`\`\`
[\u94FE\u63A5](www.baidu.com)
![\u56FE\u7247\u63CF\u8FF0](http://www.image.com)
\`\`\`
## \u4EE3\u7801\u6BB5\u843D
\\\`\`\` type

\u4EE3\u7801\u6BB5\u843D

\\\`\`\`

\\\` \u4EE3\u7801\u5757 \\\`

\`\`\`c++
int main()
{
    printf("hello world!");
}
\`\`\`
\`code\`
## \u8868\u683C(table)
\`\`\`
| \u6807\u98981 | \u6807\u98982 | \u6807\u98983 |
| :--  | :--: | ----: |
| \u5DE6\u5BF9\u9F50 | \u5C45\u4E2D | \u53F3\u5BF9\u9F50 |
| ---------------------- | ------------- | ----------------- |
\`\`\`
| \u6807\u98981 | \u6807\u98982 | \u6807\u98983 |
| :--  | :--: | ----: |
| \u5DE6\u5BF9\u9F50 | \u5C45\u4E2D | \u53F3\u5BF9\u9F50 |
| ---------------------- | ------------- | ----------------- |
## \u811A\u6CE8(footnote)
\`\`\`
hello[^hello]
\`\`\`

\u89C1\u5E95\u90E8\u811A\u6CE8[^hello]

[^hello]: \u4E00\u4E2A\u6CE8\u811A

## \u8868\u60C5(emoji)
[\u53C2\u8003\u7F51\u7AD9: https://www.webpagefx.com/tools/emoji-cheat-sheet/](https://www.webpagefx.com/tools/emoji-cheat-sheet/)
\`\`\`
:laughing:
:blush:
:smiley:
:)
...
\`\`\`
:laughing::blush::smiley::)

## $\\KaTeX$\u516C\u5F0F

\u6211\u4EEC\u53EF\u4EE5\u6E32\u67D3\u516C\u5F0F\u4F8B\u5982\uFF1A$x_i + y_i = z_i$\u548C$\\sum_{i=1}^n a_i=0$
\u6211\u4EEC\u4E5F\u53EF\u4EE5\u5355\u884C\u6E32\u67D3
$$\\sum_{i=1}^n a_i=0$$
\u5177\u4F53\u53EF\u53C2\u7167[katex\u6587\u6863](http://www.intmath.com/cg5/katex-mathjax-comparison.php)\u548C[katex\u652F\u6301\u7684\u51FD\u6570](https://github.com/Khan/KaTeX/wiki/Function-Support-in-KaTeX)\u4EE5\u53CA[latex\u6587\u6863](https://math.meta.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference)

## \u5E03\u5C40

::: hljs-left
\`::: hljs-left\`
\`\u5C45\u5DE6\`
\`:::\`
:::

::: hljs-center
\`::: hljs-center\`
\`\u5C45\u4E2D\`
\`:::\`
:::

::: hljs-right
\`::: hljs-right\`
\`\u5C45\u53F3\`
\`:::\`
:::

## \u5B9A\u4E49

\u672F\u8BED\u4E00

:   \u5B9A\u4E49\u4E00

\u5305\u542B\u6709*\u884C\u5185\u6807\u8BB0*\u7684\u672F\u8BED\u4E8C

:   \u5B9A\u4E49\u4E8C

        {\u4E00\u4E9B\u5B9A\u4E49\u4E8C\u7684\u6587\u5B57\u6216\u4EE3\u7801}

    \u5B9A\u4E49\u4E8C\u7684\u7B2C\u4E09\u6BB5

\`\`\`
\u672F\u8BED\u4E00

:   \u5B9A\u4E49\u4E00

\u5305\u542B\u6709*\u884C\u5185\u6807\u8BB0*\u7684\u672F\u8BED\u4E8C

:   \u5B9A\u4E49\u4E8C

        {\u4E00\u4E9B\u5B9A\u4E49\u4E8C\u7684\u6587\u5B57\u6216\u4EE3\u7801}

    \u5B9A\u4E49\u4E8C\u7684\u7B2C\u4E09\u6BB5

\`\`\`

## abbr
*[HTML]: Hyper Text Markup Language
*[W3C]:  World Wide Web Consortium
HTML \u89C4\u8303\u7531 W3C \u7EF4\u62A4
\`\`\`
*[HTML]: Hyper Text Markup Language
*[W3C]:  World Wide Web Consortium
HTML \u89C4\u8303\u7531 W3C \u7EF4\u62A4
\`\`\`

`},function(c,d){c.exports=`@[toc](\u76EE\u9304)

Markdown \u8A9E\u6CD5\u7C21\u4ECB
=============
> [\u8A9E\u6CD5\u8A73\u89E3](http://commonmark.org/help/)

## **\u7C97\u9AD4**
\`\`\`
**\u7C97\u9AD4**
__\u7C97\u9AD4__
\`\`\`
## *\u659C\u9AD4*
\`\`\`
*\u659C\u9AD4*
_\u659C\u9AD4_
\`\`\`
## \u6A19\u984C
\`\`\`
# \u4E00\u7D1A\u6A19\u984C #
\u4E00\u7D1A\u6A19\u984C
====
## \u4E8C\u7D1A\u6A19\u984C ##
\u4E8C\u7D1A\u6A19\u984C
----
### \u4E09\u7D1A\u6A19\u984C ###
#### \u56DB\u7D1A\u6A19\u984C ####
##### \u4E94\u7D1A\u6A19\u984C #####
###### \u516D\u7D1A\u6A19\u984C ######
\`\`\`
## \u5206\u5272\u7DDA
\`\`\`
***
---
\`\`\`
****
## ^\u4E0A^\u89D2~\u4E0B~\u6A19
\`\`\`
\u4E0A\u89D2\u6A19 x^2^
\u4E0B\u89D2\u6A19 H~2~0
\`\`\`
## ++\u4E0B\u5283\u7DDA++ ~~\u4E2D\u5283\u7DDA~~
\`\`\`
++\u4E0B\u5283\u7DDA++
~~\u4E2D\u5283\u7DDA~~
\`\`\`
## ==\u6A19\u8A18==
\`\`\`
==\u6A19\u8A18==
\`\`\`
## \u6BB5\u843D\u5F15\u7528
\`\`\`
> \u4E00\u7D1A
>> \u4E8C\u7D1A
>>> \u4E09\u7D1A
...
\`\`\`

## \u5217\u8868
\`\`\`
\u6709\u5E8F\u5217\u8868
1.
2.
3.
...
\u7121\u5E8F\u5217\u8868
-
-
...
\`\`\`

## \u4EFB\u52D9\u5217\u8868

- [x] \u5DF2\u5B8C\u6210\u4EFB\u52D9
- [ ] \u672A\u5B8C\u6210\u4EFB\u52D9

\`\`\`
- [x] \u5DF2\u5B8C\u6210\u4EFB\u52D9
- [ ] \u672A\u5B8C\u6210\u4EFB\u52D9
\`\`\`

## \u93C8\u63A5
\`\`\`
[\u93C8\u63A5](www.baidu.com)
![\u5716\u7247\u63CF\u8FF0](http://www.image.com)
\`\`\`
## \u4EE3\u78BC\u6BB5\u843D
\\\`\`\` type

\u4EE3\u78BC\u6BB5\u843D

\\\`\`\`

\\\` \u4EE3\u78BC\u584A \\\`

\`\`\`c++
int main()
{
    printf("hello world!");
}
\`\`\`
\`code\`
## \u8868\u683C(table)
\`\`\`
| \u6A19\u984C1 | \u6A19\u984C2 | \u6A19\u984C3 |
| :--  | :--: | ----: |
| \u5DE6\u5C0D\u9F4A | \u5C45\u4E2D | \u53F3\u5C0D\u9F4A |
| ---------------------- | ------------- | ----------------- |
\`\`\`
| \u6A19\u984C1 | \u6A19\u984C2 | \u6A19\u984C3 |
| :--  | :--: | ----: |
| \u5DE6\u5C0D\u9F4A | \u5C45\u4E2D | \u53F3\u5C0D\u9F4A |
| ---------------------- | ------------- | ----------------- |
## \u8173\u8A3B(footnote)
\`\`\`
hello[^hello]
\`\`\`

\u898B\u5E95\u90E8\u8173\u8A3B[^hello]

[^hello]: \u4E00\u500B\u8A3B\u8173

## \u8868\u60C5(emoji)
[\u53C3\u8003\u7DB2\u7AD9: https://www.webpagefx.com/tools/emoji-cheat-sheet/](https://www.webpagefx.com/tools/emoji-cheat-sheet/)
\`\`\`
:laughing:
:blush:
:smiley:
:)
...
\`\`\`
:laughing::blush::smiley::)

## $\\KaTeX$\u516C\u5F0F

\u6211\u5011\u53EF\u4EE5\u6E32\u67D3\u516C\u5F0F\u4F8B\u5982\uFF1A$x_i + y_i = z_i$\u548C$\\sum_{i=1}^n a_i=0$
\u6211\u5011\u4E5F\u53EF\u4EE5\u55AE\u884C\u6E32\u67D3
$$\\sum_{i=1}^n a_i=0$$
\u5177\u9AD4\u53EF\u53C3\u7167[katex\u6587\u6A94](http://www.intmath.com/cg5/katex-mathjax-comparison.php)\u548C[katex\u652F\u6301\u7684\u51FD\u6578](https://github.com/Khan/KaTeX/wiki/Function-Support-in-KaTeX)\u4EE5\u53CA[latex\u6587\u6A94](https://math.meta.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference)

## \u5E03\u5C40

::: hljs-left
\`::: hljs-left\`
\`\u5C45\u5DE6\`
\`:::\`
:::

::: hljs-center
\`::: hljs-center\`
\`\u5C45\u4E2D\`
\`:::\`
:::

::: hljs-right
\`::: hljs-right\`
\`\u5C45\u53F3\`
\`:::\`
:::

## \u5B9A\u7FA9

\u8853\u8A9E\u4E00

:   \u5B9A\u7FA9\u4E00

\u5305\u542B\u6709*\u884C\u5167\u6A19\u8A18*\u7684\u8853\u8A9E\u4E8C

:   \u5B9A\u7FA9\u4E8C

        {\u4E00\u4E9B\u5B9A\u7FA9\u4E8C\u7684\u6587\u5B57\u6216\u4EE3\u78BC}

    \u5B9A\u7FA9\u4E8C\u7684\u7B2C\u4E09\u6BB5

\`\`\`
\u8853\u8A9E\u4E00

:   \u5B9A\u7FA9\u4E00

\u5305\u542B\u6709*\u884C\u5167\u6A19\u8A18*\u7684\u8853\u8A9E\u4E8C

:   \u5B9A\u7FA9\u4E8C

        {\u4E00\u4E9B\u5B9A\u7FA9\u4E8C\u7684\u6587\u5B57\u6216\u4EE3\u78BC}

    \u5B9A\u7FA9\u4E8C\u7684\u7B2C\u4E09\u6BB5

\`\`\`

## abbr
*[HTML]: Hyper Text Markup Language
*[W3C]:  World Wide Web Consortium
HTML \u898F\u7BC4\u7531 W3C \u7DAD\u8B77
\`\`\`
*[HTML]: Hyper Text Markup Language
*[W3C]:  World Wide Web Consortium
HTML \u898F\u7BC4\u7531 W3C \u7DAD\u8B77
\`\`\`

`},function(c,d){c.exports=/[\xAD\u0600-\u0605\u061C\u06DD\u070F\u08E2\u180E\u200B-\u200F\u202A-\u202E\u2060-\u2064\u2066-\u206F\uFEFF\uFFF9-\uFFFB]|\uD804[\uDCBD\uDCCD]|\uD82F[\uDCA0-\uDCA3]|\uD834[\uDD73-\uDD7A]|\uDB40[\uDC01\uDC20-\uDC7F]/},function(c,d,i){d.Any=i(60),d.Cc=i(58),d.Cf=i(195),d.P=i(34),d.Z=i(59)},function(c,d,i){function t(e){i(202)}var r=i(16)(i(65),i(199),t,null,null);r.options.__file="D:\\work\\songwang\\yuangongji\\mavonEditor\\node_modules\\auto-textarea\\auto-textarea.vue",r.esModule&&Object.keys(r.esModule).some(function(e){return e!=="default"&&e.substr(0,2)!=="__"})&&console.error("named exports are not supported in *.vue files."),r.options.functional&&console.error("[vue-loader] auto-textarea.vue: functional components are not supported with templates, they should use render functions."),c.exports=r.exports},function(c,d,i){c.exports={render:function(){var t=this,r=t.$createElement,e=t._self._c||r;return e("div",{staticClass:"v-right-item"},[t._t("right-toolbar-before"),t._v(" "),t.toolbars.navigation?e("button",{directives:[{name:"show",rawName:"v-show",value:!t.s_navigation,expression:"!s_navigation"}],staticClass:"op-icon fa fa-mavon-bars",attrs:{type:"button","aria-hidden":"true",title:t.d_words.tl_navigation_on+" (F8)"},on:{click:function(o){return t.$clicks("navigation")}}}):t._e(),t._v(" "),t.toolbars.navigation?e("button",{directives:[{name:"show",rawName:"v-show",value:t.s_navigation,expression:"s_navigation"}],staticClass:"op-icon fa fa-mavon-bars selected",attrs:{type:"button","aria-hidden":"true",title:t.d_words.tl_navigation_off+" (F8)"},on:{click:function(o){return t.$clicks("navigation")}}}):t._e(),t._v(" "),t.toolbars.preview?e("button",{directives:[{name:"show",rawName:"v-show",value:t.s_preview_switch,expression:"s_preview_switch"}],staticClass:"op-icon fa fa-mavon-eye-slash selected",attrs:{type:"button","aria-hidden":"true",title:t.d_words.tl_edit+" (F9)"},on:{click:function(o){return t.$clicks("preview")}}}):t._e(),t._v(" "),t.toolbars.preview?e("button",{directives:[{name:"show",rawName:"v-show",value:!t.s_preview_switch,expression:"!s_preview_switch"}],staticClass:"op-icon fa fa-mavon-eye",attrs:{type:"button","aria-hidden":"true",title:t.d_words.tl_preview+" (F9)"},on:{click:function(o){return t.$clicks("preview")}}}):t._e(),t._v(" "),t.toolbars.fullscreen?e("button",{directives:[{name:"show",rawName:"v-show",value:!t.s_fullScreen,expression:"!s_fullScreen"}],staticClass:"op-icon fa fa-mavon-arrows-alt",attrs:{type:"button",title:t.d_words.tl_fullscreen_on+" (F10)","aria-hidden":"true"},on:{click:function(o){return t.$clicks("fullscreen")}}}):t._e(),t._v(" "),t.toolbars.fullscreen?e("button",{directives:[{name:"show",rawName:"v-show",value:t.s_fullScreen,expression:"s_fullScreen"}],staticClass:"op-icon fa fa-mavon-compress selected",attrs:{type:"button",title:t.d_words.tl_fullscreen_off+" (F10)","aria-hidden":"true"},on:{click:function(o){return t.$clicks("fullscreen")}}}):t._e(),t._v(" "),t.toolbars.readmodel?e("button",{staticClass:"op-icon fa fa-mavon-window-maximize",attrs:{type:"button","aria-hidden":"true",title:t.d_words.tl_read+" (F11)"},on:{click:function(o){return t.$clicks("read")}}}):t._e(),t._v(" "),t.toolbars.subfield?e("button",{staticClass:"op-icon fa fa-mavon-columns",class:{selected:t.s_subfield},attrs:{type:"button","aria-hidden":"true",title:(t.s_subfield?t.d_words.tl_single_column:t.d_words.tl_double_column)+" (F12)"},on:{click:function(o){return t.$clicks("subfield")}}}):t._e(),t._v(" "),t.toolbars.help&&t.toolbars.htmlcode&&t.toolbars.readmodel&&t.toolbars.fullscreen&&t.toolbars.subfield&&t.toolbars.navigation?e("span",{staticClass:"op-icon-divider"}):t._e(),t._v(" "),t.toolbars.htmlcode?e("button",{directives:[{name:"show",rawName:"v-show",value:!t.s_html_code,expression:"!s_html_code"}],staticClass:"op-icon fa fa-mavon-code",attrs:{type:"button",title:t.d_words.tl_html_on,"aria-hidden":"true"},on:{click:function(o){return t.$clicks("html")}}}):t._e(),t._v(" "),t.toolbars.htmlcode?e("button",{directives:[{name:"show",rawName:"v-show",value:t.s_html_code,expression:"s_html_code"}],staticClass:"op-icon fa fa-mavon-code selected",attrs:{type:"button",title:t.d_words.tl_html_off,"aria-hidden":"true"},on:{click:function(o){return t.$clicks("html")}}}):t._e(),t._v(" "),t.toolbars.help?e("button",{staticClass:"op-icon fa fa-mavon-question-circle",staticStyle:{"font-size":"17px",padding:"5px 6px 5px 3px"},attrs:{type:"button",title:t.d_words.tl_help,"aria-hidden":"true"},on:{click:function(o){return t.$clicks("help")}}}):t._e(),t._v(" "),t._t("right-toolbar-after")],2)},staticRenderFns:[]},c.exports.render._withStripped=!0},function(c,d,i){c.exports={render:function(){var t=this,r=t.$createElement,e=t._self._c||r;return e("div",{staticClass:"auto-textarea-wrapper",style:{fontSize:t.fontSize,lineHeight:t.lineHeight,height:t.fullHeight?"100%":"auto"}},[e("pre",{staticClass:"auto-textarea-block",style:{fontSize:t.fontSize,lineHeight:t.lineHeight,minHeight:t.fullHeight?"100%":"auto"}},[e("br"),t._v(t._s(t.temp_value)+" ")]),t._v(" "),e("textarea",{directives:[{name:"model",rawName:"v-model",value:t.temp_value,expression:"temp_value"}],ref:"vTextarea",staticClass:"auto-textarea-input",class:{"no-border":!t.border,"no-resize":!t.resize},style:{fontSize:t.fontSize,lineHeight:t.lineHeight},attrs:{autofocus:t.s_autofocus,spellcheck:"false",placeholder:t.placeholder},domProps:{value:t.temp_value},on:{keyup:t.change,input:function(o){o.target.composing||(t.temp_value=o.target.value)}}})])},staticRenderFns:[]},c.exports.render._withStripped=!0},function(c,d,i){c.exports={render:function(){var t=this,r=t.$createElement,e=t._self._c||r;return e("div",{staticClass:"v-left-item"},[t._t("left-toolbar-before"),t._v(" "),t.toolbars.bold?e("button",{staticClass:"op-icon fa fa-mavon-bold",attrs:{disabled:!t.editable,type:"button","aria-hidden":"true",title:t.d_words.tl_bold+" (ctrl+b)"},on:{click:function(o){return t.$clicks("bold")}}}):t._e(),t._v(" "),t.toolbars.italic?e("button",{staticClass:"op-icon fa fa-mavon-italic",attrs:{disabled:!t.editable,type:"button","aria-hidden":"true",title:t.d_words.tl_italic+" (ctrl+i)"},on:{click:function(o){return t.$clicks("italic")}}}):t._e(),t._v(" "),t.toolbars.header?e("div",{staticClass:"op-icon fa fa-mavon-header dropdown dropdown-wrapper",class:{selected:t.s_header_dropdown_open},attrs:{disabled:!t.editable,type:"button","aria-hidden":"true",title:t.d_words.tl_header+" (ctrl+h)"},on:{mouseleave:t.$mouseleave_header_dropdown,mouseenter:t.$mouseenter_header_dropdown}},[e("transition",{attrs:{name:"fade"}},[e("div",{directives:[{name:"show",rawName:"v-show",value:t.s_header_dropdown_open,expression:"s_header_dropdown_open"}],staticClass:"op-header popup-dropdown",class:{transition:t.transition},on:{mouseenter:t.$mouseenter_header_dropdown,mouseleave:t.$mouseleave_header_dropdown}},[e("div",{staticClass:"dropdown-item",attrs:{title:"#"},on:{click:function(o){return o.stopPropagation(),t.$click_header("header1")}}},[e("span",[t._v(t._s(t.d_words.tl_header_one))])]),t._v(" "),e("div",{staticClass:"dropdown-item",attrs:{title:"## "},on:{click:function(o){return o.stopPropagation(),t.$click_header("header2")}}},[e("span",[t._v(t._s(t.d_words.tl_header_two))])]),t._v(" "),e("div",{staticClass:"dropdown-item",attrs:{title:"### "},on:{click:function(o){return o.stopPropagation(),t.$click_header("header3")}}},[e("span",[t._v(t._s(t.d_words.tl_header_three))])]),t._v(" "),e("div",{staticClass:"dropdown-item",attrs:{title:"#### "},on:{click:function(o){return o.stopPropagation(),t.$click_header("header4")}}},[e("span",[t._v(t._s(t.d_words.tl_header_four))])]),t._v(" "),e("div",{staticClass:"dropdown-item",attrs:{title:"##### "},on:{click:function(o){return o.stopPropagation(),t.$click_header("header5")}}},[e("span",[t._v(t._s(t.d_words.tl_header_five))])]),t._v(" "),e("div",{staticClass:"dropdown-item",attrs:{title:"###### "},on:{click:function(o){return o.stopPropagation(),t.$click_header("header6")}}},[e("span",[t._v(t._s(t.d_words.tl_header_six))])])])])],1):t._e(),t._v(" "),t.toolbars.header||t.toolbars.italic||t.toolbars.bold?e("span",{staticClass:"op-icon-divider"}):t._e(),t._v(" "),t.toolbars.underline?e("button",{staticClass:"op-icon fa fa-mavon-underline",attrs:{disabled:!t.editable,type:"button",title:t.d_words.tl_underline+" (ctrl+u)","aria-hidden":"true"},on:{click:function(o){return t.$clicks("underline")}}}):t._e(),t._v(" "),t.toolbars.strikethrough?e("button",{staticClass:"op-icon fa fa-mavon-strikethrough",attrs:{disabled:!t.editable,type:"button",title:t.d_words.tl_strikethrough+" (ctrl+shift+d)","aria-hidden":"true"},on:{click:function(o){return t.$clicks("strikethrough")}}}):t._e(),t._v(" "),t.toolbars.mark?e("button",{staticClass:"op-icon fa fa-mavon-thumb-tack",attrs:{disabled:!t.editable,type:"button",title:t.d_words.tl_mark+" (ctrl+m)","aria-hidden":"true"},on:{click:function(o){return t.$clicks("mark")}}}):t._e(),t._v(" "),t.toolbars.superscript?e("button",{staticClass:"op-icon fa fa-mavon-superscript",attrs:{disabled:!t.editable,type:"button","aria-hidden":"true",title:t.d_words.tl_superscript+" (ctrl+alt+s)"},on:{click:function(o){return t.$clicks("superscript")}}}):t._e(),t._v(" "),t.toolbars.subscript?e("button",{staticClass:"op-icon fa fa-mavon-subscript",attrs:{disabled:!t.editable,type:"button","aria-hidden":"true",title:t.d_words.tl_subscript+" (ctrl+shift+s)"},on:{click:function(o){return t.$clicks("subscript")}}}):t._e(),t._v(" "),t.toolbars.alignleft?e("button",{staticClass:"op-icon fa fa-mavon-align-left",attrs:{disabled:!t.editable,type:"button","aria-hidden":"true",title:t.d_words.tl_alignleft+" (ctrl+l)"},on:{click:function(o){return t.$clicks("alignleft")}}}):t._e(),t._v(" "),t.toolbars.aligncenter?e("button",{staticClass:"op-icon fa fa-mavon-align-center",attrs:{disabled:!t.editable,type:"button","aria-hidden":"true",title:t.d_words.tl_aligncenter+" (ctrl+e)"},on:{click:function(o){return t.$clicks("aligncenter")}}}):t._e(),t._v(" "),t.toolbars.alignright?e("button",{staticClass:"op-icon fa fa-mavon-align-right",attrs:{disabled:!t.editable,type:"button","aria-hidden":"true",title:t.d_words.tl_alignright+" (ctrl+r)"},on:{click:function(o){return t.$clicks("alignright")}}}):t._e(),t._v(" "),t.toolbars.superscript||t.toolbars.subscript||t.toolbars.underline||t.toolbars.strikethrough||t.toolbars.mark?e("span",{staticClass:"op-icon-divider"}):t._e(),t._v(" "),t.toolbars.quote?e("button",{staticClass:"op-icon fa fa-mavon-quote-left",attrs:{disabled:!t.editable,type:"button","aria-hidden":"true",title:t.d_words.tl_quote+" (ctrl+q)"},on:{click:function(o){return t.$clicks("quote")}}}):t._e(),t._v(" "),t.toolbars.ol?e("button",{staticClass:"op-icon fa fa-mavon-list-ol",attrs:{disabled:!t.editable,type:"button","aria-hidden":"true",title:t.d_words.tl_ol+" (ctrl+o)"},on:{click:function(o){return t.$clicks("ol")}}}):t._e(),t._v(" "),t.toolbars.ul?e("button",{staticClass:"op-icon fa fa-mavon-list-ul",attrs:{disabled:!t.editable,type:"button","aria-hidden":"true",title:t.d_words.tl_ul+" (ctrl+alt+u)"},on:{click:function(o){return t.$clicks("ul")}}}):t._e(),t._v(" "),t.toolbars.ul||t.toolbars.ol||t.toolbars.quote?e("span",{staticClass:"op-icon-divider"}):t._e(),t._v(" "),t.toolbars.link?e("button",{staticClass:"op-icon fa fa-mavon-link",attrs:{disabled:!t.editable,type:"button","aria-hidden":"true",title:t.d_words.tl_link+" (ctrl+l)"},on:{click:function(o){return o.stopPropagation(),t.$toggle_imgLinkAdd("link")}}}):t._e(),t._v(" "),t.toolbars.imagelink?e("div",{staticClass:"op-icon fa fa-mavon-picture-o dropdown dropdown-wrapper",class:{selected:t.s_img_dropdown_open},attrs:{disabled:!t.editable,type:"button","aria-hidden":"true"},on:{mouseleave:t.$mouseleave_img_dropdown,mouseenter:t.$mouseenter_img_dropdown}},[e("transition",{attrs:{name:"fade"}},[e("div",{directives:[{name:"show",rawName:"v-show",value:t.s_img_dropdown_open,expression:"s_img_dropdown_open"}],staticClass:"op-image popup-dropdown",class:{transition:t.transition},on:{mouseleave:t.$mouseleave_img_dropdown,mouseenter:t.$mouseenter_img_dropdown}},[e("div",{staticClass:"dropdown-item",on:{click:function(o){return o.stopPropagation(),t.$toggle_imgLinkAdd("imagelink")}}},[e("span",[t._v(t._s(t.d_words.tl_image))])]),t._v(" "),e("div",{staticClass:"dropdown-item",staticStyle:{overflow:"hidden"}},[e("input",{attrs:{type:"file",accept:"image/gif,image/jpeg,image/jpg,image/png,image/svg",multiple:"multiple"},on:{change:function(o){return t.$imgAdd(o)}}}),t._v(t._s(t.d_words.tl_upload)+`
                `)]),t._v(" "),t._l(t.img_file,function(o,n){return o&&o[1]?e("div",{key:n,staticClass:"dropdown-item dropdown-images",attrs:{title:o[1].name},on:{click:function(s){return s.stopPropagation(),t.$imgFileListClick(n)}}},[e("span",[t._v(t._s(o[1].name))]),t._v(" "),e("button",{staticClass:"op-icon fa fa-mavon-times",attrs:{slot:"right",type:"button","aria-hidden":"true",title:t.d_words.tl_upload_remove},on:{click:function(s){return s.stopPropagation(),t.$imgDel(n)}},slot:"right"}),t._v(" "),e("img",{staticClass:"image-show",class:{transition:t.transition},attrs:{src:o[1].miniurl,alt:"none"}})]):t._e()})],2)])],1):t._e(),t._v(" "),t.toolbars.code?e("button",{staticClass:"op-icon fa fa-mavon-code",attrs:{disabled:!t.editable,type:"button","aria-hidden":"true",title:t.d_words.tl_code+" (ctrl+alt+c)"},on:{click:function(o){return t.$clicks("code")}}}):t._e(),t._v(" "),t.toolbars.table?e("button",{staticClass:"op-icon fa fa-mavon-table",attrs:{disabled:!t.editable,type:"button","aria-hidden":"true",title:t.d_words.tl_table+" (ctrl+alt+t)"},on:{click:function(o){return t.$clicks("table")}}}):t._e(),t._v(" "),t.toolbars.link||t.toolbars.imagelink||t.toolbars.code||t.toolbars.table?e("span",{staticClass:"op-icon-divider"}):t._e(),t._v(" "),t.toolbars.undo?e("button",{staticClass:"op-icon fa fa-mavon-undo",attrs:{type:"button","aria-hidden":"true",title:t.d_words.tl_undo+" (ctrl+z)"},on:{click:function(o){return t.$clicks("undo")}}}):t._e(),t._v(" "),t.toolbars.redo?e("button",{staticClass:"op-icon fa fa-mavon-repeat",attrs:{type:"button","aria-hidden":"true",title:t.d_words.tl_redo+" (ctrl+y)"},on:{click:function(o){return t.$clicks("redo")}}}):t._e(),t._v(" "),t.toolbars.trash?e("button",{staticClass:"op-icon fa fa-mavon-trash-o",attrs:{type:"button","aria-hidden":"true",title:t.d_words.tl_trash+" (ctrl+breakspace)"},on:{click:function(o){return t.$clicks("trash")}}}):t._e(),t._v(" "),t.toolbars.save?e("button",{staticClass:"op-icon fa fa-mavon-floppy-o",attrs:{type:"button","aria-hidden":"true",title:t.d_words.tl_save+" (ctrl+s)"},on:{click:function(o){return t.$clicks("save")}}}):t._e(),t._v(" "),t._t("left-toolbar-after"),t._v(" "),e("transition",{attrs:{name:"fade"}},[t.s_img_link_open?e("div",{staticClass:"add-image-link-wrapper"},[e("div",{staticClass:"add-image-link"},[e("i",{staticClass:"fa fa-mavon-times",attrs:{"aria-hidden":"true"},on:{click:function(o){o.stopPropagation(),o.preventDefault(),t.s_img_link_open=!1}}}),t._v(" "),e("h3",{staticClass:"title"},[t._v(t._s(t.link_type=="link"?t.d_words.tl_popup_link_title:t.d_words.tl_popup_img_link_title))]),t._v(" "),e("div",{staticClass:"link-text input-wrapper"},[e("input",{directives:[{name:"model",rawName:"v-model",value:t.link_text,expression:"link_text"}],ref:"linkTextInput",attrs:{type:"text",placeholder:t.link_type=="link"?t.d_words.tl_popup_link_text:t.d_words.tl_popup_img_link_text},domProps:{value:t.link_text},on:{input:function(o){o.target.composing||(t.link_text=o.target.value)}}})]),t._v(" "),e("div",{staticClass:"link-addr input-wrapper"},[e("input",{directives:[{name:"model",rawName:"v-model",value:t.link_addr,expression:"link_addr"}],attrs:{type:"text",placeholder:t.link_type=="link"?t.d_words.tl_popup_link_addr:t.d_words.tl_popup_img_link_addr},domProps:{value:t.link_addr},on:{input:function(o){o.target.composing||(t.link_addr=o.target.value)}}})]),t._v(" "),e("div",{staticClass:"op-btn cancel",on:{click:function(o){o.stopPropagation(),t.s_img_link_open=!1}}},[t._v(t._s(t.d_words.tl_popup_link_cancel))]),t._v(" "),e("div",{staticClass:"op-btn sure",on:{click:function(o){return o.stopPropagation(),t.$imgLinkAdd()}}},[t._v(t._s(t.d_words.tl_popup_link_sure))])])]):t._e()])],2)},staticRenderFns:[]},c.exports.render._withStripped=!0},function(c,d,i){c.exports={render:function(){var t=this,r=t.$createElement,e=t._self._c||r;return e("div",{staticClass:"v-note-wrapper markdown-body",class:[{fullscreen:t.s_fullScreen,shadow:t.boxShadow}],style:{"box-shadow":t.boxShadow?t.boxShadowStyle:""}},[e("div",{directives:[{name:"show",rawName:"v-show",value:t.toolbarsFlag,expression:"toolbarsFlag"}],staticClass:"v-note-op",style:{background:t.toolbarsBackground}},[e("v-md-toolbar-left",{ref:"toolbar_left",class:{transition:t.transition},attrs:{editable:t.editable,transition:t.transition,d_words:t.d_words,toolbars:t.toolbars,image_filter:t.imageFilter},on:{toolbar_left_click:t.toolbar_left_click,toolbar_left_addlink:t.toolbar_left_addlink,imgAdd:t.$imgAdd,imgDel:t.$imgDel,imgTouch:t.$imgTouch}},[t._t("left-toolbar-before",null,{slot:"left-toolbar-before"}),t._v(" "),t._t("left-toolbar-after",null,{slot:"left-toolbar-after"})],2),t._v(" "),e("v-md-toolbar-right",{ref:"toolbar_right",class:{transition:t.transition},attrs:{d_words:t.d_words,toolbars:t.toolbars,s_subfield:t.s_subfield,s_preview_switch:t.s_preview_switch,s_fullScreen:t.s_fullScreen,s_html_code:t.s_html_code,s_navigation:t.s_navigation},on:{toolbar_right_click:t.toolbar_right_click}},[t._t("right-toolbar-before",null,{slot:"right-toolbar-before"}),t._v(" "),t._t("right-toolbar-after",null,{slot:"right-toolbar-after"})],2)],1),t._v(" "),e("div",{staticClass:"v-note-panel"},[e("div",{ref:"vNoteEdit",staticClass:"v-note-edit divarea-wrapper",class:{"scroll-style":t.s_scrollStyle,"scroll-style-border-radius":t.s_scrollStyle&&!t.s_preview_switch&&!t.s_html_code,"single-edit":!t.s_preview_switch&&!t.s_html_code,"single-show":!t.s_subfield&&t.s_preview_switch||!t.s_subfield&&t.s_html_code,transition:t.transition},on:{scroll:t.$v_edit_scroll,click:t.textAreaFocus}},[e("div",{staticClass:"content-input-wrapper",style:{"background-color":t.editorBackground}},[e("v-autoTextarea",{ref:"vNoteTextarea",staticClass:"content-input",style:{"background-color":t.editorBackground},attrs:{placeholder:t.placeholder?t.placeholder:t.d_words.start_editor,fontSize:t.fontSize,lineHeight:"1.5",fullHeight:""},model:{value:t.d_value,callback:function(o){t.d_value=o},expression:"d_value"}})],1)]),t._v(" "),e("div",{directives:[{name:"show",rawName:"v-show",value:t.s_preview_switch||t.s_html_code,expression:"s_preview_switch || s_html_code"}],staticClass:"v-note-show",class:{"single-show":!t.s_subfield&&t.s_preview_switch||!t.s_subfield&&t.s_html_code}},[e("div",{directives:[{name:"show",rawName:"v-show",value:!t.s_html_code,expression:"!s_html_code"}],ref:"vShowContent",staticClass:"v-show-content",class:{"scroll-style":t.s_scrollStyle,"scroll-style-border-radius":t.s_scrollStyle},style:{"background-color":t.previewBackground},domProps:{innerHTML:t._s(t.d_render)}}),t._v(" "),e("div",{directives:[{name:"show",rawName:"v-show",value:t.s_html_code,expression:"s_html_code"}],staticClass:"v-show-content-html",class:{"scroll-style":t.s_scrollStyle,"scroll-style-border-radius":t.s_scrollStyle},style:{"background-color":t.previewBackground}},[t._v(`
                `+t._s(t.d_render)+`
            `)])]),t._v(" "),e("transition",{attrs:{name:"slideTop"}},[e("div",{directives:[{name:"show",rawName:"v-show",value:t.s_navigation,expression:"s_navigation"}],staticClass:"v-note-navigation-wrapper",class:{transition:t.transition}},[e("div",{staticClass:"v-note-navigation-title"},[t._v(`
                    `+t._s(t.d_words.navigation_title)),e("i",{staticClass:"fa fa-mavon-times v-note-navigation-close",attrs:{"aria-hidden":"true"},on:{click:function(o){return t.toolbar_right_click("navigation")}}})]),t._v(" "),e("div",{ref:"navigationContent",staticClass:"v-note-navigation-content",class:{"scroll-style":t.s_scrollStyle}})])])],1),t._v(" "),e("transition",{attrs:{name:"fade"}},[e("div",{ref:"help"},[t.s_help?e("div",{staticClass:"v-note-help-wrapper",on:{click:function(o){return o.target!==o.currentTarget?null:t.toolbar_right_click("help")}}},[e("div",{staticClass:"v-note-help-content markdown-body",class:{shadow:t.boxShadow}},[e("i",{staticClass:"fa fa-mavon-times",attrs:{"aria-hidden":"true"},on:{click:function(o){return o.stopPropagation(),o.preventDefault(),t.toolbar_right_click("help")}}}),t._v(" "),e("div",{staticClass:"scroll-style v-note-help-show",domProps:{innerHTML:t._s(t.d_help)}})])]):t._e()])]),t._v(" "),e("transition",{attrs:{name:"fade"}},[t.d_preview_imgsrc?e("div",{staticClass:"v-note-img-wrapper",on:{click:function(o){t.d_preview_imgsrc=null}}},[e("img",{attrs:{src:t.d_preview_imgsrc,alt:"none"}})]):t._e()]),t._v(" "),e("div",{ref:"vReadModel",staticClass:"v-note-read-model scroll-style",class:{show:t.s_readmodel}},[e("div",{ref:"vNoteReadContent",staticClass:"v-note-read-content",domProps:{innerHTML:t._s(t.d_render)}})])],1)},staticRenderFns:[]},c.exports.render._withStripped=!0},function(c,d,i){var t=i(107);typeof t=="string"&&(t=[[c.i,t,""]]),t.locals&&(c.exports=t.locals),i(17)("c0faed68",t,!1,{})},function(c,d,i){var t=i(108);typeof t=="string"&&(t=[[c.i,t,""]]),t.locals&&(c.exports=t.locals),i(17)("118de024",t,!1,{})},function(c,d,i){var t=i(109);typeof t=="string"&&(t=[[c.i,t,""]]),t.locals&&(c.exports=t.locals),i(17)("2f84471f",t,!1,{})},function(c,d,i){var t=i(110);typeof t=="string"&&(t=[[c.i,t,""]]),t.locals&&(c.exports=t.locals),i(17)("6daa4aa0",t,!1,{})},function(c,d){c.exports=function(i,t){for(var r=[],e={},o=0;o<t.length;o++){var n=t[o],s=n[0],l=n[1],u=n[2],a=n[3],p={id:i+":"+o,css:l,media:u,sourceMap:a};e[s]?e[s].parts.push(p):r.push(e[s]={id:s,parts:[p]})}return r}},function(c,d){var i;i=function(){return this}();try{i=i||Function("return this")()||(0,eval)("this")}catch{typeof window=="object"&&(i=window)}c.exports=i},function(c,d){c.exports=function(i){return i.webpackPolyfill||(i.deprecate=function(){},i.paths=[],i.children||(i.children=[]),Object.defineProperty(i,"loaded",{enumerable:!0,get:function(){return i.l}}),Object.defineProperty(i,"id",{enumerable:!0,get:function(){return i.i}}),i.webpackPolyfill=1),i}},function(c,d,i){function t(s,l){return new o(l).process(s)}var r=i(61),e=i(62),o=i(210);d=c.exports=t,d.filterXSS=t,d.FilterXSS=o;for(var n in r)d[n]=r[n];for(var n in e)d[n]=e[n];typeof window!="undefined"&&(window.filterXSS=c.exports),function(){return typeof self!="undefined"&&typeof DedicatedWorkerGlobalScope!="undefined"&&self instanceof DedicatedWorkerGlobalScope}()&&(self.filterXSS=c.exports)},function(c,d,i){function t(_){return _==null}function r(_){var v=p.spaceIndex(_);if(v===-1)return{html:"",closing:_[_.length-2]==="/"};_=p.trim(_.slice(v+1,-1));var g=_[_.length-1]==="/";return g&&(_=p.trim(_.slice(0,-1))),{html:_,closing:g}}function e(_){var v={};for(var g in _)v[g]=_[g];return v}function o(_){_=e(_||{}),_.stripIgnoreTag&&(_.onIgnoreTag&&console.error('Notes: cannot use these two options "stripIgnoreTag" and "onIgnoreTag" at the same time'),_.onIgnoreTag=s.onIgnoreTagStripAll),_.whiteList=_.whiteList||s.whiteList,_.onTag=_.onTag||s.onTag,_.onTagAttr=_.onTagAttr||s.onTagAttr,_.onIgnoreTag=_.onIgnoreTag||s.onIgnoreTag,_.onIgnoreTagAttr=_.onIgnoreTagAttr||s.onIgnoreTagAttr,_.safeAttrValue=_.safeAttrValue||s.safeAttrValue,_.escapeHtml=_.escapeHtml||s.escapeHtml,this.options=_,_.css===!1?this.cssFilter=!1:(_.css=_.css||{},this.cssFilter=new n(_.css))}var n=i(31).FilterCSS,s=i(61),l=i(62),u=l.parseTag,a=l.parseAttr,p=i(35);o.prototype.process=function(_){if(_=_||"",!(_=_.toString()))return"";var v=this,g=v.options,C=g.whiteList,m=g.onTag,h=g.onIgnoreTag,w=g.onTagAttr,f=g.onIgnoreTagAttr,k=g.safeAttrValue,b=g.escapeHtml,y=v.cssFilter;g.stripBlankChar&&(_=s.stripBlankChar(_)),g.allowCommentTag||(_=s.stripCommentTag(_));var x=!1;if(g.stripIgnoreTagBody){var x=s.StripTagBody(g.stripIgnoreTagBody,h);h=x.onIgnoreTag}var E=u(_,function(D,T,L,A,$){var z={sourcePosition:D,position:T,isClosing:$,isWhite:C.hasOwnProperty(L)},j=m(L,A,z);if(!t(j))return j;if(z.isWhite){if(z.isClosing)return"</"+L+">";var M=r(A),R=C[L],W=a(M.html,function(O,q){var I=p.indexOf(R,O)!==-1,B=w(L,O,q,I);if(!t(B))return B;if(I)return q=k(L,O,q,y),q?O+'="'+q+'"':O;var B=f(L,O,q,I);return t(B)?void 0:B}),A="<"+L;return W&&(A+=" "+W),M.closing&&(A+=" /"),A+=">"}var j=h(L,A,z);return t(j)?b(A):j},b);return x&&(E=x.remove(E)),E},c.exports=o},function(c,d){c.exports={start_editor:"Bearbeitung beginnen...",navigation_title:"Navigation",tl_bold:"Fett",tl_italic:"Kursiv",tl_header:"\xDCberschrift",tl_header_one:"\xDCberschrift 1",tl_header_two:"\xDCberschrift 2",tl_header_three:"\xDCberschrift 3",tl_header_four:"\xDCberschrift 4",tl_header_five:"\xDCberschrift 5",tl_header_six:"\xDCberschrift 6",tl_underline:"Unterstrichen",tl_strikethrough:"Durchgestrichen",tl_mark:"Markiert",tl_superscript:"Hochgestellt",tl_subscript:"Tiefgestellt",tl_quote:"Zitat",tl_ol:"Ol",tl_ul:"Ul",tl_link:"Link",tl_image:"Link mit Bild",tl_code:"Code",tl_table:"Tabelle",tl_undo:"R\xFCckg\xE4ngig",tl_redo:"Wiederherstellen",tl_trash:"M\xFClleimer",tl_save:"Speichern",tl_navigation_on:"Navigation AN",tl_navigation_off:"Navigation AUS",tl_preview:"Vorschau",tl_aligncenter:"Text zentrieren",tl_alignleft:"Nach links ausrichten",tl_alignright:"Nach rechts ausrichten",tl_edit:"Bearbeiten",tl_single_column:"Einspaltig",tl_double_column:"Zweispaltig",tl_fullscreen_on:"Vollbild AN",tl_fullscreen_off:"Vollbild AUS",tl_read:"Lesemodus",tl_html_on:"HTML AN",tl_html_off:"HTML AUS",tl_help:"Markdown Handbuch",tl_upload:"Bilder-Upload",tl_upload_remove:"Entfernen",tl_popup_link_title:"Link hinzuf\xFCgen",tl_popup_link_text:"Text des Links",tl_popup_link_addr:"Linkziel",tl_popup_img_link_title:"Bild hinzuf\xFCgen",tl_popup_img_link_text:"Text des Bildes",tl_popup_img_link_addr:"Link auf Bild",tl_popup_link_sure:"Ja",tl_popup_link_cancel:"Abbruch"}},function(c,d){c.exports={start_editor:"Begin editing...",navigation_title:"Navigation",tl_bold:"Bold",tl_italic:"Italic",tl_header:"Header",tl_header_one:"Header 1",tl_header_two:"Header 2",tl_header_three:"Header 3",tl_header_four:"Header 4",tl_header_five:"Header 5",tl_header_six:"Header 6",tl_underline:"Underline",tl_strikethrough:"Strikethrough",tl_mark:"Mark",tl_superscript:"Superscript",tl_subscript:"Subscript",tl_quote:"Quote",tl_ol:"Ol",tl_ul:"Ul",tl_link:"Link",tl_image:"Image Link",tl_code:"Code",tl_table:"Table",tl_undo:"Undo",tl_redo:"Redo",tl_trash:"Trash",tl_save:"Save",tl_navigation_on:"Navigation ON",tl_navigation_off:"Navigation OFF",tl_preview:"Preview",tl_aligncenter:"Center text",tl_alignleft:"Clamp text to the left",tl_alignright:"Clamp text to the right",tl_edit:"Edit",tl_single_column:"Single Column",tl_double_column:"Double Columns",tl_fullscreen_on:"FullScreen ON",tl_fullscreen_off:"FullScreen OFF",tl_read:"Read Model",tl_html_on:"HTML ON",tl_html_off:"HTML OFF",tl_help:"Markdown Guide",tl_upload:"Upload Images",tl_upload_remove:"Remove",tl_popup_link_title:"Add Link",tl_popup_link_text:"Link text",tl_popup_link_addr:"Link address",tl_popup_img_link_title:"Add Image",tl_popup_img_link_text:"Image Text",tl_popup_img_link_addr:"Image Link",tl_popup_link_sure:"Sure",tl_popup_link_cancel:"Cancel"}},function(c,d){c.exports={start_editor:"D\xE9but d'\xE9dition...",navigation_title:"Navigation",tl_bold:"Gras",tl_italic:"Italique",tl_header:"Ent\xEAte",tl_header_one:"Ent\xEAte 1",tl_header_two:"Ent\xEAte 2",tl_header_three:"Ent\xEAte 3",tl_header_four:"Ent\xEAte 4",tl_header_five:"Ent\xEAte 5",tl_header_six:"Ent\xEAte 6",tl_underline:"Soulign\xE9",tl_strikethrough:"Barr\xE9",tl_mark:"Mark",tl_superscript:"Exposant",tl_subscript:"Sous-exposant",tl_quote:"Quote",tl_ol:"Liste ",tl_ul:"Puce",tl_link:"Lien",tl_image:"Image Lien",tl_code:"Code",tl_table:"Table",tl_undo:"Annuler",tl_redo:"Refaire",tl_trash:"Supprimer",tl_save:"Sauver",tl_navigation_on:"Activer la navigation",tl_navigation_off:"D\xE9sactiver le navigation",tl_preview:"Previsualis\xE9",tl_aligncenter:"Center le texte",tl_alignleft:"F\xE9rer le texte \xE0 gauche",tl_alignright:"F\xE9rer le texte \xE0 droite",tl_edit:"Editer",tl_single_column:"Seule Colonne",tl_double_column:"Colonnes Doubles",tl_fullscreen_on:"Activer le mode plein \xE9cran",tl_fullscreen_off:"D\xE9sactiver le mode plein \xE9cran",tl_read:"Lire le mod\xE8le",tl_html_on:"Activer le mode HTML",tl_html_off:"D\xE9sactiver le mode HTML",tl_help:"Guide Markdown",tl_upload:"T\xE9l\xE9charger les images",tl_upload_remove:"Supprimer",tl_popup_link_title:"Ajouter un lien",tl_popup_link_text:"Description",tl_popup_link_addr:"Link",tl_popup_img_link_title:"Ajouter une image",tl_popup_img_link_text:"Description",tl_popup_img_link_addr:"Link",tl_popup_link_sure:"s\xFBr",tl_popup_link_cancel:"Annuler"}},function(c,d){c.exports={start_editor:"\u7DE8\u96C6\u3092\u59CB\u3081\u3066\u306D\uFF01",navigation_title:"\u30CA\u30D3\u30B2\u30FC\u30B7\u30E7\u30F3",tl_bold:"\u592A\u5B57",tl_italic:"\u659C\u4F53",tl_header:"\u898B\u51FA\u3057",tl_header_one:"\u898B\u51FA\u30571",tl_header_two:"\u898B\u51FA\u30572",tl_header_three:"\u898B\u51FA\u30573",tl_header_four:"\u898B\u51FA\u30574",tl_header_five:"\u898B\u51FA\u30575",tl_header_six:"\u898B\u51FA\u30576",tl_underline:"\u4E0B\u7DDA",tl_strikethrough:"\u53D6\u308A\u6D88\u3057\u7DDA",tl_mark:"\u86CD\u5149\u30DA\u30F3",tl_superscript:"\u4E0A\u4ED8\u304D\u6587\u5B57",tl_subscript:"\u4E0B\u4ED8\u304D\u6587\u5B57",tl_quote:"\u5F15\u7528",tl_ol:"\u756A\u53F7\u4ED8\u304D\u30EA\u30B9\u30C8",tl_ul:"\u7B87\u6761\u66F8\u304D\u30EA\u30B9\u30C8",tl_link:"\u30CF\u30A4\u30D1\u30FC\u30EA\u30F3\u30AF",tl_image:"\u753B\u50CF\u306E\u30EA\u30F3\u30AF",tl_code:"\u30B3\u30FC\u30C9\u306E\u633F\u5165",tl_table:"\u8868\u306E\u633F\u5165",tl_undo:"\u623B\u308B",tl_redo:"\u9032\u3080",tl_trash:"\u524A\u9664",tl_save:"\u4FDD\u5B58",tl_navigation_on:"\u30CA\u30D3\u30B2\u30FC\u30B7\u30E7\u30F3\u3092\u8868\u793A",tl_navigation_off:"\u30CA\u30D3\u30B2\u30FC\u30B7\u30E7\u30F3\u3092\u975E\u8868\u793A",tl_preview:"\u30D7\u30EC\u30D3\u30E5\u30FC",tl_aligncenter:"\u4E2D\u592E\u63C3\u3048",tl_alignleft:"\u5DE6\u63C3\u3048",tl_alignright:"\u53F3\u63C3\u3048",tl_edit:"\u7DE8\u96C6",tl_single_column:"\u4E00\u5217",tl_double_column:"\u4E8C\u5217",tl_fullscreen_on:"\u5168\u753B\u9762\u8868\u793A",tl_fullscreen_off:"\u5168\u753B\u9762\u8868\u793A\u306E\u7D42\u4E86",tl_read:"\u30E2\u30C7\u30EB\u306E\u8AAD\u307F\u8FBC\u307F",tl_html_on:"HTML\u3067\u8868\u793A",tl_html_off:"HTML\u8868\u793A\u306E\u7D42\u4E86",tl_help:"\u30D8\u30EB\u30D7",tl_upload:"\u753B\u50CF\u3092\u30A2\u30C3\u30D7\u30ED\u30FC\u30C9",tl_upload_remove:"\u753B\u50CF\u306E\u524A\u9664",tl_popup_link_title:"\u30EA\u30F3\u30AF\u306E\u8FFD\u52A0",tl_popup_link_text:"\u30EA\u30F3\u30AF\u30C6\u30AD\u30B9\u30C8",tl_popup_link_addr:"\u30EA\u30F3\u30AF\u5148\u306EURL",tl_popup_img_link_title:"\u753B\u50CF\u306E\u8FFD\u52A0",tl_popup_img_link_text:"\u753B\u50CF\u30BF\u30A4\u30C8\u30EB",tl_popup_img_link_addr:"\u753B\u50CFURL",tl_popup_link_sure:"OK",tl_popup_link_cancel:"\u623B\u308B"}},function(c,d){c.exports={start_editor:"Come\xE7ar edi\xE7\xE3o...",navigation_title:"Navega\xE7\xE3o",tl_bold:"Negrito",tl_italic:"It\xE1lico",tl_header:"Cabe\xE7alho",tl_header_one:"Cabe\xE7alho 1",tl_header_two:"Cabe\xE7alho 2",tl_header_three:"Cabe\xE7alho 3",tl_header_four:"Cabe\xE7alho 4",tl_header_five:"Cabe\xE7alho 5",tl_header_six:"Cabe\xE7alho 6",tl_underline:"Sublinhar",tl_strikethrough:"Tachar",tl_mark:"Marca\xE7\xE3o",tl_superscript:"Sobrescrito",tl_subscript:"Subscrito",tl_quote:"Cita\xE7\xE3o",tl_ol:"Lista Numerada",tl_ul:"Lista com marcadores",tl_link:"Link",tl_image:"Link de imagem",tl_code:"C\xF3digo",tl_table:"Tabela",tl_undo:"Desfazer",tl_redo:"Refazer",tl_trash:"Lixo",tl_save:"Salvar",tl_navigation_on:"Mostrar Navega\xE7\xE3o",tl_navigation_off:"Esconder Navega\xE7\xE3o",tl_preview:"Preview",tl_aligncenter:"Alinhar no centro",tl_alignleft:"Alinhar \xE0 esquerda",tl_alignright:"Alinhar \xE0 direita",tl_edit:"Editar",tl_single_column:"Coluna \xDAnica",tl_double_column:"Duas Colunas",tl_fullscreen_on:"Ligar Tela Cheia",tl_fullscreen_off:"Desligar Tela Cheia",tl_read:"Modo de Leitura",tl_html_on:"Ligar HTML",tl_html_off:"Desligar HTML",tl_help:"Guia Markdown",tl_upload:"Upload de Imagens",tl_upload_remove:"Remover",tl_popup_link_title:"Adicionar Link",tl_popup_link_text:"Descri\xE7\xE3o",tl_popup_link_addr:"Link",tl_popup_img_link_title:"Adicionar fotos",tl_popup_img_link_text:"Descri\xE7\xE3o",tl_popup_img_link_addr:"Link",tl_popup_link_sure:"Confirmar",tl_popup_link_cancel:"Cancelar"}},function(c,d){c.exports={start_editor:"\u041D\u0430\u0447\u043D\u0438\u0442\u0435 \u0440\u0435\u0434\u0430\u043A\u0442\u0438\u0440\u043E\u0432\u0430\u043D\u0438\u0435...",navigation_title:"\u041D\u0430\u0432\u0438\u0433\u0430\u0446\u0438\u044F",tl_bold:"\u041F\u043E\u043B\u0443\u0436\u0438\u0440\u043D\u044B\u0439",tl_italic:"\u041A\u0443\u0440\u0441\u0438\u0432",tl_header:"\u0417\u0430\u0433\u043E\u043B\u043E\u0432\u043A\u0438",tl_header_one:"\u0417\u0430\u0433\u043E\u043B\u043E\u0432\u043E\u043A 1",tl_header_two:"\u0417\u0430\u0433\u043E\u043B\u043E\u0432\u043E\u043A 2",tl_header_three:"\u0417\u0430\u0433\u043E\u043B\u043E\u0432\u043E\u043A 3",tl_header_four:"\u0417\u0430\u0433\u043E\u043B\u043E\u0432\u043E\u043A 4",tl_header_five:"\u0417\u0430\u0433\u043E\u043B\u043E\u0432\u043E\u043A 5",tl_header_six:"\u0417\u0430\u0433\u043E\u043B\u043E\u0432\u043E\u043A 6",tl_underline:"\u041F\u043E\u0434\u0447\u0435\u0440\u043A\u043D\u0443\u0442\u044B\u0439",tl_strikethrough:"\u0417\u0430\u0447\u0435\u0440\u043A\u043D\u0443\u0442\u044B\u0439",tl_mark:"\u041E\u0442\u043C\u0435\u0442\u043A\u0430",tl_superscript:"\u0412\u0435\u0440\u0445\u043D\u0438\u0439 \u0438\u043D\u0434\u0435\u043A\u0441",tl_subscript:"\u041D\u0438\u0436\u043D\u0438\u0439 \u0438\u043D\u0434\u0435\u043A\u0441",tl_quote:"\u0426\u0438\u0442\u0430\u0442\u0430",tl_ol:"\u041D\u0443\u043C\u0435\u0440\u043E\u0432\u0430\u043D\u043D\u044B\u0439 \u0441\u043F\u0438\u0441\u043E\u043A",tl_ul:"\u0421\u043F\u0438\u0441\u043E\u043A",tl_link:"\u0421\u0441\u044B\u043B\u043A\u0430",tl_image:"\u0421\u0441\u044B\u043B\u043A\u0430 \u0438\u0437\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u044F",tl_code:"\u041A\u043E\u0434",tl_table:"\u0422\u0430\u0431\u043B\u0438\u0446\u0430",tl_undo:"\u041E\u0442\u043C\u0435\u043D\u0438\u0442\u044C",tl_redo:"\u0412\u0435\u0440\u043D\u0443\u0442\u044C",tl_trash:"\u0423\u0434\u0430\u043B\u0438\u0442\u044C",tl_save:"\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C",tl_navigation_on:"\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C \u043D\u0430\u0432\u0438\u0433\u0430\u0446\u0438\u044E",tl_navigation_off:"\u0421\u043A\u0440\u044B\u0442\u044C \u043D\u0430\u0432\u0438\u0433\u0430\u0446\u0438\u044E",tl_preview:"\u041F\u0440\u0435\u0434\u043F\u0440\u043E\u0441\u043C\u043E\u0442\u0440",tl_aligncenter:"\u0412\u044B\u0440\u043E\u0432\u043D\u044F\u0442\u044C \u043F\u043E \u0446\u0435\u043D\u0442\u0440\u0443",tl_alignleft:"\u0412\u044B\u0440\u043E\u0432\u043D\u044F\u0442\u044C \u043F\u043E \u043B\u0435\u0432\u043E\u043C\u0443 \u043A\u0440\u0430\u044E",tl_alignright:"\u0412\u044B\u0440\u043E\u0432\u043D\u044F\u0442\u044C \u043F\u043E \u043F\u0440\u0430\u0432\u043E\u043C\u0443 \u043A\u0440\u0430\u044E",tl_edit:"\u0420\u0435\u0434\u0430\u043A\u0442\u043E\u0440",tl_single_column:"\u041E\u0434\u043D\u043E \u043F\u043E\u043B\u0435",tl_double_column:"\u0414\u0432\u0430 \u043F\u043E\u043B\u044F",tl_fullscreen_on:"\u041F\u043E\u043B\u043D\u043E\u044D\u043A\u0440\u0430\u043D\u043D\u044B\u0439 \u0440\u0435\u0436\u0438\u043C",tl_fullscreen_off:"\u0412\u044B\u043A\u043B\u044E\u0447\u0438\u0442\u044C \u043F\u043E\u043B\u043D\u043E\u044D\u043A\u0440\u0430\u043D\u043D\u044B\u0439 \u0440\u0435\u0436\u0438\u043C",tl_read:"\u0420\u0435\u0436\u0438\u043C \u0447\u0442\u0435\u043D\u0438\u044F",tl_html_on:"\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C HTML",tl_html_off:"\u0423\u0431\u0440\u0430\u0442\u044C HTML",tl_help:"Markdown \u043F\u043E\u043C\u043E\u0449\u044C",tl_upload:"\u0417\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044C \u0438\u0437\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u0435",tl_upload_remove:"\u0423\u0434\u0430\u043B\u0438\u0442\u044C",tl_popup_link_title:"\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0441\u0441\u044B\u043B\u043A\u0443",tl_popup_link_text:"\u0422\u0435\u043A\u0441\u0442 \u0441\u0441\u044B\u043B\u043A\u0438",tl_popup_link_addr:"\u0410\u0434\u0440\u0435\u0441 \u0441\u0441\u044B\u043B\u043A\u0438",tl_popup_img_link_title:"\u041B\u043E\u043A\u0430\u043B\u044C\u043D\u043E\u0435 \u0438\u0437\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u0435",tl_popup_img_link_text:"\u0422\u0435\u043A\u0441\u0442 \u0438\u0437\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u044F",tl_popup_img_link_addr:"\u0421\u0441\u044B\u043B\u043A\u0430 \u0438\u0437\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u044F",tl_popup_link_sure:"\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C",tl_popup_link_cancel:"\u041E\u0442\u043C\u0435\u043D\u0438\u0442\u044C"}},function(c,d){c.exports={start_editor:"\u5F00\u59CB\u7F16\u8F91...",navigation_title:"\u5BFC\u822A\u76EE\u5F55",tl_bold:"\u7C97\u4F53",tl_italic:"\u659C\u4F53",tl_header:"\u6807\u9898",tl_header_one:"\u4E00\u7EA7\u6807\u9898",tl_header_two:"\u4E8C\u7EA7\u6807\u9898",tl_header_three:"\u4E09\u7EA7\u6807\u9898",tl_header_four:"\u56DB\u7EA7\u6807\u9898",tl_header_five:"\u4E94\u7EA7\u6807\u9898",tl_header_six:"\u516D\u7EA7\u6807\u9898",tl_underline:"\u4E0B\u5212\u7EBF",tl_strikethrough:"\u4E2D\u5212\u7EBF",tl_mark:"\u6807\u8BB0",tl_superscript:"\u4E0A\u89D2\u6807",tl_subscript:"\u4E0B\u89D2\u6807",tl_quote:"\u6BB5\u843D\u5F15\u7528",tl_ol:"\u6709\u5E8F\u5217\u8868",tl_ul:"\u65E0\u5E8F\u5217\u8868",tl_link:"\u94FE\u63A5",tl_image:"\u6DFB\u52A0\u56FE\u7247\u94FE\u63A5",tl_code:"\u4EE3\u7801\u5757",tl_table:"\u8868\u683C",tl_undo:"\u4E0A\u4E00\u6B65",tl_redo:"\u4E0B\u4E00\u6B65",tl_trash:"\u6E05\u7A7A",tl_save:"\u4FDD\u5B58",tl_navigation_on:"\u5F00\u542F\u6807\u9898\u5BFC\u822A",tl_navigation_off:"\u5173\u95ED\u6807\u9898\u5BFC\u822A",tl_preview:"\u9884\u89C8",tl_aligncenter:"\u5C45\u4E2D",tl_alignleft:"\u5C45\u5DE6",tl_alignright:"\u5C45\u53F3",tl_edit:"\u7F16\u8F91",tl_single_column:"\u5355\u680F",tl_double_column:"\u53CC\u680F",tl_fullscreen_on:"\u5168\u5C4F\u7F16\u8F91",tl_fullscreen_off:"\u9000\u51FA\u5168\u5C4F",tl_read:"\u6C89\u6D78\u5F0F\u9605\u8BFB",tl_html_on:"\u67E5\u770Bhtml\u6587\u672C",tl_html_off:"\u8FD4\u56DEmarkdown\u6587\u672C",tl_help:"markdown\u8BED\u6CD5\u5E2E\u52A9",tl_upload:"\u4E0A\u4F20\u56FE\u7247",tl_upload_remove:"\u5220\u9664",tl_popup_link_title:"\u6DFB\u52A0\u94FE\u63A5",tl_popup_link_text:"\u94FE\u63A5\u63CF\u8FF0",tl_popup_link_addr:"\u94FE\u63A5\u5730\u5740",tl_popup_img_link_title:"\u6DFB\u52A0\u56FE\u7247",tl_popup_img_link_text:"\u56FE\u7247\u63CF\u8FF0",tl_popup_img_link_addr:"\u56FE\u7247\u94FE\u63A5",tl_popup_link_sure:"\u786E\u5B9A",tl_popup_link_cancel:"\u53D6\u6D88"}},function(c,d){c.exports={start_editor:"\u958B\u59CB\u7DE8\u8F2F...",navigation_title:"\u5C0E\u822A\u76EE\u9304",tl_bold:"\u7C97\u9AD4",tl_italic:"\u659C\u9AD4",tl_header:"\u6A19\u984C",tl_header_one:"\u4E00\u7D1A\u6A19\u984C",tl_header_two:"\u4E8C\u7D1A\u6A19\u984C",tl_header_three:"\u4E09\u7D1A\u6A19\u984C",tl_header_four:"\u56DB\u7D1A\u6A19\u984C",tl_header_five:"\u4E94\u7D1A\u6A19\u984C",tl_header_six:"\u516D\u7D1A\u6A19\u984C",tl_underline:"\u4E0B\u5283\u7DDA",tl_strikethrough:"\u4E2D\u5283\u7DDA",tl_mark:"\u6A19\u8A18",tl_superscript:"\u4E0A\u89D2\u6A19",tl_subscript:"\u4E0B\u89D2\u6A19",tl_quote:"\u6BB5\u843D\u5F15\u7528",tl_ol:"\u6709\u5E8F\u5217\u8868",tl_ul:"\u7121\u5E8F\u5217\u8868",tl_link:"\u93C8\u63A5",tl_image:"\u6DFB\u52A0\u5716\u7247\u93C8\u63A5",tl_code:"\u4EE3\u78BC\u584A",tl_table:"\u8868\u683C",tl_undo:"\u4E0A\u4E00\u6B65",tl_redo:"\u4E0B\u4E00\u6B65",tl_trash:"\u6E05\u7A7A",tl_save:"\u4FDD\u5B58",tl_navigation_on:"\u958B\u555F\u6A19\u984C\u5C0E\u822A",tl_navigation_off:"\u95DC\u9589\u6A19\u984C\u5C0E\u822A",tl_preview:"\u9810\u89BD",tl_aligncenter:"\u5C45\u4E2D",tl_alignleft:"\u5C45\u5DE6",tl_alignright:"\u5C45\u53F3",tl_edit:"\u7DE8\u8F2F",tl_single_column:"\u55AE\u6B04",tl_double_column:"\u96D9\u6B04",tl_fullscreen_on:"\u5168\u5C4F\u7DE8\u8F2F",tl_fullscreen_off:"\u9000\u51FA\u5168\u5C4F",tl_read:"\u6C88\u6D78\u5F0F\u95B1\u8B80",tl_html_on:"\u67E5\u770Bhtml\u6587\u672C",tl_html_off:"\u8FD4\u56DEmarkdown\u6587\u672C",tl_help:"markdown\u8A9E\u6CD5\u5E6B\u52A9",tl_upload:"\u4E0A\u50B3\u5716\u7247",tl_upload_remove:"\u522A\u9664",tl_popup_link_title:"\u6DFB\u52A0\u93C8\u63A5",tl_popup_link_text:"\u93C8\u63A5\u63CF\u8FF0",tl_popup_link_addr:"\u93C8\u63A5\u5730\u5740",tl_popup_img_link_title:"\u6DFB\u52A0\u5716\u7247",tl_popup_img_link_text:"\u5716\u7247\u63CF\u8FF0",tl_popup_img_link_addr:"\u5716\u7247\u93C8\u63A5",tl_popup_link_sure:"\u78BA\u5B9A",tl_popup_link_cancel:"\u53D6\u6D88"}}])})})(mavonEditor);var githubMarkdown_min="",index="";export{mavonEditor as m};
