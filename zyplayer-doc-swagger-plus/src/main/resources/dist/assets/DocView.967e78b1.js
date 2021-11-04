import{N as message,V as commonjsGlobal,r as resolveComponent,T as resolveDirective,o as openBlock,c as createBlock,w as withCtx,a as createVNode,p as createTextVNode,t as toDisplayString,x as withDirectives,e as createBaseVNode,b as createElementBlock,F as Fragment,n as createCommentVNode,W as CloseOutlined,X as UploadOutlined,Q as ref,d as renderList,Y as toRefs,Z as watch,O as useStore,y as vShow,$ as useRoute,R as onMounted}from"./vendor.1f907179.js";import{_ as _export_sfc}from"./main.b5589b09.js";import{z as zyplayerApi}from"./custom.93dbced2.js";let notNeedHandleTypeArr=["file","string","integer","long","double","object","number","boolean","ref"];var swaggerAnalysis={getRequestParamList(c,_){if(!c)return[];let i=1,e=[];for(let o=0;o<c.length;o++){let n=c[o],r=n.description||"",t=n.type,a=n.format,l=n["x-example"],p,s,u;t==="array"?this.isSchemaRef(n.items)?(p=this.getSchemaRef(n.items),s=this.getParamDefinitions(p,_,i,{},0)):n.schema?this.isSchemaRef(n.schema.items)?(p=this.getSchemaRef(n.schema.items),s=this.getParamDefinitions(p,_,i,{},0)):n.schema.type&&(p=n.schema.type):n.items&&n.items.type?p=n.items.type:this.logMessage("001",t,n):t?notNeedHandleTypeArr.indexOf(t)>=0||this.logMessage("003",t,n):n.schema?this.isSchemaRef(n.schema)?(t=this.getSchemaRef(n.schema),s=this.getParamDefinitions(t,_,i,{},0)):n.schema.type?(t=n.schema.type,n.schema.additionalProperties?(u={},s=this.getAdditionalProperties(n.schema.additionalProperties,u,_,i,{},0),a=u.type):n.schema.items?this.isSchemaRef(n.schema.items)?(p=this.getSchemaRef(n.schema.items),s=this.getParamDefinitions(p,_,i,{},0)):n.schema.items.type?p=n.schema.items.type:this.log("0014",t,n):this.log("0011",t,n)):this.logMessage("0013",t,n):n.items&&n.items.type?(t=n.items.type,n.items.additionalProperties?(u={},s=this.getAdditionalProperties(n.items.additionalProperties,u,_,i,{},0),a=u.type):this.logMessage("0012",t,n)):this.logMessage("002",t,n),l&&(r=r?r+"\uFF0C":"",r+="\u4F8B\uFF1A"+l),n.enum&&n.enum.length>0&&(r=r||"\u679A\u4E3E\u7C7B\u578B",r+="\uFF0C\u53EF\u9009\u503C\uFF1A"+n.enum.join("\u3001")),e.push({type:t,key:i,in:n.in,name:n.name,subType:p,required:n.required?"\u662F":"\u5426",format:a,enum:n.enum,example:l,collectionFormat:n.collectionFormat,description:r,additional:u,children:s}),i++}return e},getResponseParamList(c,_){let i=[],e=1;return Object.keys(c).forEach(o=>{let n=c[o],r,t;this.isSchemaRef(n.schema)&&(r=this.getSchemaRef(n.schema),t=this.getParamDefinitions(r,_,e,{},0)),i.push({code:o,type:r,key:e,desc:n.description,schemas:t}),e++}),i},isSchemaRef(c){return!!(c&&(c.originalRef||c.$ref))},getSchemaRef(c){return c.originalRef?c.originalRef:c.$ref&&c.$ref.indexOf("#/definitions/")===0?c.$ref.replace("#/definitions/",""):(this.logMessage("9467","",c),"")},getParamDefinitions(c,_,i,e,o){let n=_[c];if(o>=10||!n||(e[c]=(e[c]||0)+1,e[c]>2))return;let r=[],t=n.type,a=n.properties,l=1;if(t==="object"){let p={};Object.keys(a).forEach(s=>{let u=a[s],d=u.type,v=u.format,f=u.description||"",y=u["x-example"],m,h,w,g=i+"_"+l,k;Object.keys(p).forEach(b=>{e[b]=void 0}),d==="array"?this.isSchemaRef(u.items)?(m=this.getSchemaRef(u.items),k=this.getParamDefinitions(m,_,g,e,o+1)):u.items&&u.items.type?m=u.items.type:this.logMessage("004",d,u):d==="object"?u.additionalProperties?(h={},k=this.getAdditionalProperties(u.additionalProperties,h,_,g,e,o+1),v=h.type):this.log("0041",d,u):d?notNeedHandleTypeArr.indexOf(d)>=0||this.logMessage("006",d,u):this.isSchemaRef(u)?(d=this.getSchemaRef(u),k=this.getParamDefinitions(d,_,g,e,o+1)):this.logMessage("005",d,u),y&&(f=f?f+"\uFF0C":"",f+="\u4F8B\uFF1A"+y),u.items&&u.items.enum&&u.items.enum.length>0&&(w=u.items.enum,f=f||"\u679A\u4E3E\u7C7B\u578B",f+="\uFF0C\u53EF\u9009\u503C\uFF1A"+u.items.enum.join("\u3001")),r.push({type:d,name:s,key:g,subType:m,format:v,description:f,enum:w,additional:h,example:y,children:k}),l++,p[d]=1})}return r.length>0?r:void 0},getAdditionalProperties(c,_,i,e,o,n){if(this.isSchemaRef(c))return _.type=this.getSchemaRef(c),_.children=this.getParamDefinitions(_.type,i,e,o,n+1),_.additional;if(c.additionalProperties)return _.type=c.type,_.additional={},this.getAdditionalProperties(c.additionalProperties,_.additional,i,e,o,n+1);if(c.type==="array")if(_.type=c.type,this.isSchemaRef(c.items)){let r=this.getSchemaRef(c.items),t=this.getParamDefinitions(r,i,e,o,n+1);return _.additional={type:r,children:t},t}else this.logMessage("007","",c);else _.type=c.type,notNeedHandleTypeArr.indexOf(_.type)>=0||this.logMessage("008","",c)},log(c,_,i){console.log(c+"-\u9047\u5230\u672A\u5904\u7406\u7684\u7C7B\u578B\uFF0C\u8BF7\u8054\u7CFB\u5F00\u53D1\u4EBA\u5458\u4FEE\u6539\uFF1A"+_,i)},logMessage(c,_,i){console.log(c+"-\u9047\u5230\u672A\u5904\u7406\u7684\u7C7B\u578B\uFF0C\u8BF7\u8054\u7CFB\u5F00\u53D1\u4EBA\u5458\u4FEE\u6539\uFF1A"+_,i),message.error(c+"-\u9047\u5230\u672A\u5904\u7406\u7684\u7C7B\u578B\uFF0C\u8BF7\u8054\u7CFB\u5F00\u53D1\u4EBA\u5458\u4FEE\u6539\uFF1A"+_)}},mavonEditor={exports:{}};(function(module,exports){(function(c,_){module.exports=_()})(commonjsGlobal,function(){return function(c){function _(e){if(i[e])return i[e].exports;var o=i[e]={i:e,l:!1,exports:{}};return c[e].call(o.exports,o,o.exports,_),o.l=!0,o.exports}var i={};return _.m=c,_.c=i,_.i=function(e){return e},_.d=function(e,o,n){_.o(e,o)||Object.defineProperty(e,o,{configurable:!1,enumerable:!0,get:n})},_.n=function(e){var o=e&&e.__esModule?function(){return e.default}:function(){return e};return _.d(o,"a",o),o},_.o=function(e,o){return Object.prototype.hasOwnProperty.call(e,o)},_.p="",_(_.s=69)}([function(c,_,i){function e(q){return Object.prototype.toString.call(q)}function o(q){return e(q)==="[object String]"}function n(q,M){return k.call(q,M)}function r(q){return Array.prototype.slice.call(arguments,1).forEach(function(M){if(M){if(typeof M!="object")throw new TypeError(M+"must be object");Object.keys(M).forEach(function(z){q[z]=M[z]})}}),q}function t(q,M,z){return[].concat(q.slice(0,M),z,q.slice(M+1))}function a(q){return!(q>=55296&&q<=57343)&&!(q>=64976&&q<=65007)&&(65535&q)!=65535&&(65535&q)!=65534&&!(q>=0&&q<=8)&&q!==11&&!(q>=14&&q<=31)&&!(q>=127&&q<=159)&&!(q>1114111)}function l(q){if(q>65535){q-=65536;var M=55296+(q>>10),z=56320+(1023&q);return String.fromCharCode(M,z)}return String.fromCharCode(q)}function p(q,M){var z=0;return n(D,M)?D[M]:M.charCodeAt(0)===35&&E.test(M)&&(z=M[1].toLowerCase()==="x"?parseInt(M.slice(2),16):parseInt(M.slice(1),10),a(z))?l(z):q}function s(q){return q.indexOf("\\")<0?q:q.replace(b,"$1")}function u(q){return q.indexOf("\\")<0&&q.indexOf("&")<0?q:q.replace(x,function(M,z,U){return z||p(M,U)})}function d(q){return T[q]}function v(q){return A.test(q)?q.replace(S,d):q}function f(q){return q.replace(I,"\\$&")}function y(q){switch(q){case 9:case 32:return!0}return!1}function m(q){if(q>=8192&&q<=8202)return!0;switch(q){case 9:case 10:case 11:case 12:case 13:case 32:case 160:case 5760:case 8239:case 8287:case 12288:return!0}return!1}function h(q){return $.test(q)}function w(q){switch(q){case 33:case 34:case 35:case 36:case 37:case 38:case 39:case 40:case 41:case 42:case 43:case 44:case 45:case 46:case 47:case 58:case 59:case 60:case 61:case 62:case 63:case 64:case 91:case 92:case 93:case 94:case 95:case 96:case 123:case 124:case 125:case 126:return!0;default:return!1}}function g(q){return q=q.trim().replace(/\s+/g," "),"\u1E9E".toLowerCase()==="\u1E7E"&&(q=q.replace(/ẞ/g,"\xDF")),q.toLowerCase().toUpperCase()}var k=Object.prototype.hasOwnProperty,b=/\\([!"#$%&'()*+,\-.\/:;<=>?@[\\\]^_`{|}~])/g,C=/&([a-z#][a-z0-9]{1,31});/gi,x=new RegExp(b.source+"|"+C.source,"gi"),E=/^#((?:x[a-f0-9]{1,8}|[0-9]{1,8}))/i,D=i(53),A=/[&<>"]/,S=/[&<>"]/g,T={"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;"},I=/[.?*+^$[\]\\(){}|-]/g,$=i(34);_.lib={},_.lib.mdurl=i(57),_.lib.ucmicro=i(196),_.assign=r,_.isString=o,_.has=n,_.unescapeMd=s,_.unescapeAll=u,_.isValidEntityCode=a,_.fromCodePoint=l,_.escapeHtml=v,_.arrayReplaceAt=t,_.isSpace=y,_.isWhiteSpace=m,_.isMdAsciiPunct=w,_.isPunctChar=h,_.escapeRE=f,_.normalizeReference=g},function(c,_){var i=c.exports=typeof window!="undefined"&&window.Math==Math?window:typeof self!="undefined"&&self.Math==Math?self:Function("return this")();typeof __g=="number"&&(__g=i)},function(c,_){var i={}.hasOwnProperty;c.exports=function(e,o){return i.call(e,o)}},function(c,_,i){c.exports=!i(11)(function(){return Object.defineProperty({},"a",{get:function(){return 7}}).a!=7})},function(c,_,i){var e=i(5),o=i(13);c.exports=i(3)?function(n,r,t){return e.f(n,r,o(1,t))}:function(n,r,t){return n[r]=t,n}},function(c,_,i){var e=i(9),o=i(43),n=i(28),r=Object.defineProperty;_.f=i(3)?Object.defineProperty:function(t,a,l){if(e(t),a=n(a,!0),e(l),o)try{return r(t,a,l)}catch{}if("get"in l||"set"in l)throw TypeError("Accessors not supported!");return"value"in l&&(t[a]=l.value),t}},function(c,_,i){var e=i(88),o=i(19);c.exports=function(n){return e(o(n))}},function(c,_,i){var e=i(26)("wks"),o=i(14),n=i(1).Symbol,r=typeof n=="function";(c.exports=function(t){return e[t]||(e[t]=r&&n[t]||(r?n:o)("Symbol."+t))}).store=e},function(c,_){c.exports=function(i){return typeof i=="object"?i!==null:typeof i=="function"}},function(c,_,i){var e=i(8);c.exports=function(o){if(!e(o))throw TypeError(o+" is not an object!");return o}},function(c,_){var i=c.exports={version:"2.6.12"};typeof __e=="number"&&(__e=i)},function(c,_){c.exports=function(i){try{return!!i()}catch{return!0}}},function(c,_){c.exports=!0},function(c,_){c.exports=function(i,e){return{enumerable:!(1&i),configurable:!(2&i),writable:!(4&i),value:e}}},function(c,_){var i=0,e=Math.random();c.exports=function(o){return"Symbol(".concat(o===void 0?"":o,")_",(++i+e).toString(36))}},function(c,_){function i(o,n){var r=o[1]||"",t=o[3];if(!t)return r;if(n&&typeof btoa=="function"){var a=e(t);return[r].concat(t.sources.map(function(l){return"/*# sourceURL="+t.sourceRoot+l+" */"})).concat([a]).join(`
`)}return[r].join(`
`)}function e(o){return"/*# sourceMappingURL=data:application/json;charset=utf-8;base64,"+btoa(unescape(encodeURIComponent(JSON.stringify(o))))+" */"}c.exports=function(o){var n=[];return n.toString=function(){return this.map(function(r){var t=i(r,o);return r[2]?"@media "+r[2]+"{"+t+"}":t}).join("")},n.i=function(r,t){typeof r=="string"&&(r=[[null,r,""]]);for(var a={},l=0;l<this.length;l++){var p=this[l][0];typeof p=="number"&&(a[p]=!0)}for(l=0;l<r.length;l++){var s=r[l];typeof s[0]=="number"&&a[s[0]]||(t&&!s[2]?s[2]=t:t&&(s[2]="("+s[2]+") and ("+t+")"),n.push(s))}},n}},function(c,_){c.exports=function(i,e,o,n,r){var t,a=i=i||{},l=typeof i.default;l!=="object"&&l!=="function"||(t=i,a=i.default);var p=typeof a=="function"?a.options:a;e&&(p.render=e.render,p.staticRenderFns=e.staticRenderFns),n&&(p._scopeId=n);var s;if(r?(s=function(v){v=v||this.$vnode&&this.$vnode.ssrContext||this.parent&&this.parent.$vnode&&this.parent.$vnode.ssrContext,v||typeof __VUE_SSR_CONTEXT__=="undefined"||(v=__VUE_SSR_CONTEXT__),o&&o.call(this,v),v&&v._registeredComponents&&v._registeredComponents.add(r)},p._ssrRegister=s):o&&(s=o),s){var u=p.functional,d=u?p.render:p.beforeCreate;u?p.render=function(v,f){return s.call(f),d(v,f)}:p.beforeCreate=d?[].concat(d,s):[s]}return{esModule:t,exports:a,options:p}}},function(c,_,i){function e(g){for(var k=0;k<g.length;k++){var b=g[k],C=p[b.id];if(C){C.refs++;for(var x=0;x<C.parts.length;x++)C.parts[x](b.parts[x]);for(;x<b.parts.length;x++)C.parts.push(n(b.parts[x]));C.parts.length>b.parts.length&&(C.parts.length=b.parts.length)}else{for(var E=[],x=0;x<b.parts.length;x++)E.push(n(b.parts[x]));p[b.id]={id:b.id,refs:1,parts:E}}}}function o(){var g=document.createElement("style");return g.type="text/css",s.appendChild(g),g}function n(g){var k,b,C=document.querySelector("style["+m+'~="'+g.id+'"]');if(C){if(v)return f;C.parentNode.removeChild(C)}if(h){var x=d++;C=u||(u=o()),k=r.bind(null,C,x,!1),b=r.bind(null,C,x,!0)}else C=o(),k=t.bind(null,C),b=function(){C.parentNode.removeChild(C)};return k(g),function(E){if(E){if(E.css===g.css&&E.media===g.media&&E.sourceMap===g.sourceMap)return;k(g=E)}else b()}}function r(g,k,b,C){var x=b?"":C.css;if(g.styleSheet)g.styleSheet.cssText=w(k,x);else{var E=document.createTextNode(x),D=g.childNodes;D[k]&&g.removeChild(D[k]),D.length?g.insertBefore(E,D[k]):g.appendChild(E)}}function t(g,k){var b=k.css,C=k.media,x=k.sourceMap;if(C&&g.setAttribute("media",C),y.ssrId&&g.setAttribute(m,k.id),x&&(b+=`
/*# sourceURL=`+x.sources[0]+" */",b+=`
/*# sourceMappingURL=data:application/json;base64,`+btoa(unescape(encodeURIComponent(JSON.stringify(x))))+" */"),g.styleSheet)g.styleSheet.cssText=b;else{for(;g.firstChild;)g.removeChild(g.firstChild);g.appendChild(document.createTextNode(b))}}var a=typeof document!="undefined";if(typeof DEBUG!="undefined"&&DEBUG&&!a)throw new Error("vue-style-loader cannot be used in a non-browser environment. Use { target: 'node' } in your Webpack config to indicate a server-rendering environment.");var l=i(206),p={},s=a&&(document.head||document.getElementsByTagName("head")[0]),u=null,d=0,v=!1,f=function(){},y=null,m="data-vue-ssr-id",h=typeof navigator!="undefined"&&/msie [6-9]\b/.test(navigator.userAgent.toLowerCase());c.exports=function(g,k,b,C){v=b,y=C||{};var x=l(g,k);return e(x),function(E){for(var D=[],A=0;A<x.length;A++){var S=x[A],T=p[S.id];T.refs--,D.push(T)}E?(x=l(g,E),e(x)):x=[];for(var A=0;A<D.length;A++){var T=D[A];if(T.refs===0){for(var I=0;I<T.parts.length;I++)T.parts[I]();delete p[T.id]}}}};var w=function(){var g=[];return function(k,b){return g[k]=b,g.filter(Boolean).join(`
`)}}()},function(c,_,i){function e(m,h,w,g,k){return m!=="*"||h!=="*"||w.substring(g-2,g-1)!=="*"||w.substring(k+1,k+2)!=="*"}function o(m,h){typeof h!="function"&&(h=function(){});var w=document.querySelectorAll("script[src='"+m+"']");if(w.length>0)return w[0].addEventListener("load",function(){h()}),void h();var g=document.createElement("script"),k=document.getElementsByTagName("head")[0];g.type="text/javascript",g.charset="UTF-8",g.src=m,g.addEventListener?g.addEventListener("load",function(){h()},!1):g.attachEvent&&g.attachEvent("onreadystatechange",function(){window.event.srcElement.readyState==="loaded"&&h()}),k.appendChild(g)}function n(m,h,w){if(typeof h!="function"&&(h=function(){}),document.querySelectorAll("link[href='"+m+"']").length>0)return void h();if(w){var g=document.querySelectorAll("link#"+w);if(g.length)return void(g[0].href=m)}var k=document.createElement("link"),b=document.getElementsByTagName("head")[0];k.rel="stylesheet",k.href=m,w&&(k.id=w),k.addEventListener?k.addEventListener("load",function(){h()},!1):k.attachEvent&&k.attachEvent("onreadystatechange",function(){window.event.srcElement.readyState==="loaded"&&h()}),b.appendChild(k)}i.d(_,"g",function(){return r}),i.d(_,"i",function(){return t}),i.d(_,"j",function(){return a}),i.d(_,"k",function(){return l}),i.d(_,"h",function(){return p}),i.d(_,"l",function(){return s}),i.d(_,"m",function(){return u}),i.d(_,"e",function(){return d}),i.d(_,"f",function(){return v}),i.d(_,"b",function(){return f}),_.d=o,_.c=n,i.d(_,"a",function(){return y});var r=function(m,h,w){var g=h.prefix,k=h.subfix,b=h.str;if(h.type,m.focus(),typeof m.selectionStart=="number"&&typeof m.selectionEnd=="number"){var C=m.selectionStart,x=m.selectionEnd,E=m.value;C===x?(m.value=E.substring(0,C)+g+b+k+E.substring(x,E.length),m.selectionStart=C+g.length,m.selectionEnd=C+(b.length+g.length)):E.substring(C-g.length,C)===g&&E.substring(x,x+k.length)===k&&e(g,k,E,C,x)?(m.value=E.substring(0,C-g.length)+E.substring(C,x)+E.substring(x+k.length,E.length),m.selectionStart=C-g.length,m.selectionEnd=x-g.length):(m.value=E.substring(0,C)+g+E.substring(C,x)+k+E.substring(x,E.length),m.selectionStart=C+g.length,m.selectionEnd=C+(x-C+g.length))}else alert("Error: Browser version is too low");w.d_value=m.value,m.focus()},t=function(m){var h=m.getTextareaDom();if(typeof h.selectionStart=="number"&&typeof h.selectionEnd=="number"){var w=h.selectionStart,g=h.selectionEnd,k=h.value;if(w===g)h.value=k.substring(0,w)+"1. "+k.substring(g,k.length),h.selectionEnd=h.selectionStart=w+3;else{for(var b=w;b>0&&k.substring(b-1,b)!==`
`;)b--;for(var C=k.substring(b,g),x=C.split(`
`),E=0;E<x.length;E++)x[E]=E+1+". "+x[E];var D=x.join(`
`);h.value=k.substring(0,b)+D+k.substring(g,k.length),h.selectionStart=b,h.selectionEnd=g+D.length-C.length}}else alert("Error: Browser version is too low");m.d_value=h.value,h.focus()},a=function(m){var h=m.getTextareaDom();if(typeof h.selectionStart=="number"&&typeof h.selectionEnd=="number"){for(var w=h.selectionStart,g=h.selectionEnd,k=h.value,b=w;b>0&&k.substring(b-1,b)!==`
`;)b--;for(var C=g;C<k.length&&k.substring(C,C+1)!==`
`;)C++;C<k.length&&C++,h.value=k.substring(0,b)+k.substring(C,k.length),h.selectionEnd=h.selectionStart=b===0?0:b-1}else alert("Error: Browser version is too low");m.d_value=h.value,h.focus()},l=function(m){var h=m.getTextareaDom();if(typeof h.selectionStart=="number"&&typeof h.selectionEnd=="number"){var w=h.selectionStart,g=h.selectionEnd,k=h.value;if(w===g)h.value=k.substring(0,w)+"- "+k.substring(g,k.length),h.selectionEnd=h.selectionStart=w+2;else{for(var b=w;b>0&&k.substring(b-1,b)!==`
`;)b--;var C=k.substring(b,g),x=C.replace(/\n/g,`
- `);x="- "+x,h.value=k.substring(0,b)+x+k.substring(g,k.length),h.selectionStart=b,h.selectionEnd=g+x.length-C.length}}else alert("Error: Browser version is too low");m.d_value=h.value,h.focus()},p=function(m,h){h=h?new Array(h).fill(" ").join(""):"	";var w=m.getTextareaDom();if(typeof w.selectionStart=="number"&&typeof w.selectionEnd=="number"){var g=w.selectionStart,k=w.selectionEnd,b=w.value,C=b.substring(0,g).split(`
`).pop();if(C.match(/^\s*[0-9]+\.\s+\S*/)){var x=C.replace(/(\d+)/,1);w.value=b.substring(0,g-x.length)+h+x+b.substring(k,b.length)}else C.match(/^\s*-\s+\S*/)?w.value=b.substring(0,g-C.length)+h+C+b.substring(k,b.length):w.value=b.substring(0,g)+h+b.substring(k,b.length);w.selectionStart=w.selectionEnd=g+h.length}else alert("Error: Browser version is too low");m.d_value=w.value,w.focus()},s=function(m,h){var w=new RegExp(h?"\\s{"+h+"}":"	");console.log("regTab:",w);var g=m.getTextareaDom();if(typeof g.selectionStart=="number"&&typeof g.selectionEnd=="number"){var k=g.selectionStart,b=g.selectionEnd,C=g.value,x=C.substring(0,k).split(`
`).pop();x.search(w)>=0&&(g.value=C.substring(0,k-x.length)+x.replace(w,"")+C.substring(b,C.length),g.selectionStart=g.selectionEnd=k-(h||1))}else alert("Error: Browser version is too low");m.d_value=g.value,g.focus()},u=function(m,h){var w=m.getTextareaDom();if(typeof w.selectionStart=="number"&&typeof w.selectionEnd=="number"){var g=w.selectionStart,k=w.selectionEnd,b=w.value,C=b.substring(0,g).split(`
`).pop(),x=C.match(/^\s*(?:[0-9]+\.|-)\s+\S+/);if(x){h.preventDefault();var E=x.shift().match(/^\s*(?:[0-9]+\.|-)\s/).shift();if(E.search(/-/)>=0)w.value=b.substring(0,g)+`
`+E+b.substring(k,b.length),w.selectionStart=w.selectionEnd=g+E.length+1;else{var D=E.replace(/(\d+)/,parseInt(E)+1);w.value=b.substring(0,g)+`
`+D+b.substring(k,b.length),w.selectionStart=w.selectionEnd=g+D.length+1}}else{var A=C.match(/^\s*(?:[0-9]+\.|-)\s+$/);if(A){h.preventDefault();var S=A.shift().length;w.value=b.substring(0,g-S)+`
`+b.substring(k,b.length),w.selectionStart=w.selectionEnd=g-S}}}else alert("Error: Browser version is too low");m.d_value=w.value,w.focus()},d=function(m,h){var w=void 0;w=m.$refs.navigationContent,w.innerHTML=m.d_render;var g=w.children;if(g.length)for(var k=0;k<g.length;k++)(function(b,C,x){/^H[1-6]{1}$/.exec(b.tagName)?b.onclick=function(){var E=m.$refs.vShowContent,D=m.$refs.vNoteEdit;m.s_subfield?m.s_preview_switch&&(D.scrollTop=E.children[C].offsetTop*(D.scrollHeight-D.offsetHeight)/(E.scrollHeight-E.offsetHeight)):m.s_preview_switch&&(E.scrollTop=E.children[C].offsetTop)}:b.style.display="none"})(g[k],k)},v=function(m,h){var w=m.srcElement?m.srcElement:m.target,g=w.scrollTop/(w.scrollHeight-w.offsetHeight);h.edit_scroll_height>=0&&w.scrollHeight!==h.edit_scroll_height&&w.scrollHeight-w.offsetHeight-w.scrollTop<=30&&(h.$refs.vNoteEdit.scrollTop=w.scrollHeight-w.offsetHeight,g=1),h.edit_scroll_height=w.scrollHeight,h.$refs.vShowContent.scrollHeight>h.$refs.vShowContent.offsetHeight&&(h.$refs.vShowContent.scrollTop=(h.$refs.vShowContent.scrollHeight-h.$refs.vShowContent.offsetHeight)*g)},f=function(m){m.$el.addEventListener("fullscreenchange",function(h){m.$toolbar_right_read_change_status()},!1),m.$el.addEventListener("mozfullscreenchange",function(h){m.$toolbar_right_read_change_status()},!1),m.$el.addEventListener("webkitfullscreenchange",function(h){m.$toolbar_right_read_change_status()},!1),m.$el.addEventListener("msfullscreenchange",function(h){m.$toolbar_right_read_change_status()},!1)},y=function(m){m.$refs.vShowContent.addEventListener("click",function(h){h=h||window.event;var w=h.srcElement?h.srcElement:h.target;w.tagName==="IMG"&&(m.imageClick!=null?m.imageClick(w):m.d_preview_imgsrc=w.src)})}},function(c,_){c.exports=function(i){if(i==null)throw TypeError("Can't call method on  "+i);return i}},function(c,_){c.exports="constructor,hasOwnProperty,isPrototypeOf,propertyIsEnumerable,toLocaleString,toString,valueOf".split(",")},function(c,_){c.exports={}},function(c,_,i){var e=i(48),o=i(20);c.exports=Object.keys||function(n){return e(n,o)}},function(c,_){_.f={}.propertyIsEnumerable},function(c,_,i){var e=i(5).f,o=i(2),n=i(7)("toStringTag");c.exports=function(r,t,a){r&&!o(r=a?r:r.prototype,n)&&e(r,n,{configurable:!0,value:t})}},function(c,_,i){var e=i(26)("keys"),o=i(14);c.exports=function(n){return e[n]||(e[n]=o(n))}},function(c,_,i){var e=i(10),o=i(1),n=o["__core-js_shared__"]||(o["__core-js_shared__"]={});(c.exports=function(r,t){return n[r]||(n[r]=t!==void 0?t:{})})("versions",[]).push({version:e.version,mode:i(12)?"pure":"global",copyright:"\xA9 2020 Denis Pushkarev (zloirock.ru)"})},function(c,_){var i=Math.ceil,e=Math.floor;c.exports=function(o){return isNaN(o=+o)?0:(o>0?e:i)(o)}},function(c,_,i){var e=i(8);c.exports=function(o,n){if(!e(o))return o;var r,t;if(n&&typeof(r=o.toString)=="function"&&!e(t=r.call(o))||typeof(r=o.valueOf)=="function"&&!e(t=r.call(o))||!n&&typeof(r=o.toString)=="function"&&!e(t=r.call(o)))return t;throw TypeError("Can't convert object to primitive value")}},function(c,_,i){var e=i(1),o=i(10),n=i(12),r=i(30),t=i(5).f;c.exports=function(a){var l=o.Symbol||(o.Symbol=n?{}:e.Symbol||{});a.charAt(0)=="_"||a in l||t(l,a,{value:r.f(a)})}},function(c,_,i){_.f=i(7)},function(c,_,i){function e(t,a){return new n(a).process(t)}var o=i(51),n=i(111);_=c.exports=e,_.FilterCSS=n;for(var r in o)_[r]=o[r];typeof window!="undefined"&&(window.filterCSS=c.exports)},function(c,_,i){function e(){this.__rules__=[],this.__cache__=null}e.prototype.__find__=function(o){for(var n=0;n<this.__rules__.length;n++)if(this.__rules__[n].name===o)return n;return-1},e.prototype.__compile__=function(){var o=this,n=[""];o.__rules__.forEach(function(r){r.enabled&&r.alt.forEach(function(t){n.indexOf(t)<0&&n.push(t)})}),o.__cache__={},n.forEach(function(r){o.__cache__[r]=[],o.__rules__.forEach(function(t){t.enabled&&(r&&t.alt.indexOf(r)<0||o.__cache__[r].push(t.fn))})})},e.prototype.at=function(o,n,r){var t=this.__find__(o),a=r||{};if(t===-1)throw new Error("Parser rule not found: "+o);this.__rules__[t].fn=n,this.__rules__[t].alt=a.alt||[],this.__cache__=null},e.prototype.before=function(o,n,r,t){var a=this.__find__(o),l=t||{};if(a===-1)throw new Error("Parser rule not found: "+o);this.__rules__.splice(a,0,{name:n,enabled:!0,fn:r,alt:l.alt||[]}),this.__cache__=null},e.prototype.after=function(o,n,r,t){var a=this.__find__(o),l=t||{};if(a===-1)throw new Error("Parser rule not found: "+o);this.__rules__.splice(a+1,0,{name:n,enabled:!0,fn:r,alt:l.alt||[]}),this.__cache__=null},e.prototype.push=function(o,n,r){var t=r||{};this.__rules__.push({name:o,enabled:!0,fn:n,alt:t.alt||[]}),this.__cache__=null},e.prototype.enable=function(o,n){Array.isArray(o)||(o=[o]);var r=[];return o.forEach(function(t){var a=this.__find__(t);if(a<0){if(n)return;throw new Error("Rules manager: invalid rule name "+t)}this.__rules__[a].enabled=!0,r.push(t)},this),this.__cache__=null,r},e.prototype.enableOnly=function(o,n){Array.isArray(o)||(o=[o]),this.__rules__.forEach(function(r){r.enabled=!1}),this.enable(o,n)},e.prototype.disable=function(o,n){Array.isArray(o)||(o=[o]);var r=[];return o.forEach(function(t){var a=this.__find__(t);if(a<0){if(n)return;throw new Error("Rules manager: invalid rule name "+t)}this.__rules__[a].enabled=!1,r.push(t)},this),this.__cache__=null,r},e.prototype.getRules=function(o){return this.__cache__===null&&this.__compile__(),this.__cache__[o]||[]},c.exports=e},function(c,_,i){function e(o,n,r){this.type=o,this.tag=n,this.attrs=null,this.map=null,this.nesting=r,this.level=0,this.children=null,this.content="",this.markup="",this.info="",this.meta=null,this.block=!1,this.hidden=!1}e.prototype.attrIndex=function(o){var n,r,t;if(!this.attrs)return-1;for(n=this.attrs,r=0,t=n.length;r<t;r++)if(n[r][0]===o)return r;return-1},e.prototype.attrPush=function(o){this.attrs?this.attrs.push(o):this.attrs=[o]},e.prototype.attrSet=function(o,n){var r=this.attrIndex(o),t=[o,n];r<0?this.attrPush(t):this.attrs[r]=t},e.prototype.attrGet=function(o){var n=this.attrIndex(o),r=null;return n>=0&&(r=this.attrs[n][1]),r},e.prototype.attrJoin=function(o,n){var r=this.attrIndex(o);r<0?this.attrPush([o,n]):this.attrs[r][1]=this.attrs[r][1]+" "+n},c.exports=e},function(c,_){c.exports=/[!-#%-\*,-\/:;\?@\[-\]_\{\}\xA1\xA7\xAB\xB6\xB7\xBB\xBF\u037E\u0387\u055A-\u055F\u0589\u058A\u05BE\u05C0\u05C3\u05C6\u05F3\u05F4\u0609\u060A\u060C\u060D\u061B\u061E\u061F\u066A-\u066D\u06D4\u0700-\u070D\u07F7-\u07F9\u0830-\u083E\u085E\u0964\u0965\u0970\u09FD\u0A76\u0AF0\u0C84\u0DF4\u0E4F\u0E5A\u0E5B\u0F04-\u0F12\u0F14\u0F3A-\u0F3D\u0F85\u0FD0-\u0FD4\u0FD9\u0FDA\u104A-\u104F\u10FB\u1360-\u1368\u1400\u166D\u166E\u169B\u169C\u16EB-\u16ED\u1735\u1736\u17D4-\u17D6\u17D8-\u17DA\u1800-\u180A\u1944\u1945\u1A1E\u1A1F\u1AA0-\u1AA6\u1AA8-\u1AAD\u1B5A-\u1B60\u1BFC-\u1BFF\u1C3B-\u1C3F\u1C7E\u1C7F\u1CC0-\u1CC7\u1CD3\u2010-\u2027\u2030-\u2043\u2045-\u2051\u2053-\u205E\u207D\u207E\u208D\u208E\u2308-\u230B\u2329\u232A\u2768-\u2775\u27C5\u27C6\u27E6-\u27EF\u2983-\u2998\u29D8-\u29DB\u29FC\u29FD\u2CF9-\u2CFC\u2CFE\u2CFF\u2D70\u2E00-\u2E2E\u2E30-\u2E4E\u3001-\u3003\u3008-\u3011\u3014-\u301F\u3030\u303D\u30A0\u30FB\uA4FE\uA4FF\uA60D-\uA60F\uA673\uA67E\uA6F2-\uA6F7\uA874-\uA877\uA8CE\uA8CF\uA8F8-\uA8FA\uA8FC\uA92E\uA92F\uA95F\uA9C1-\uA9CD\uA9DE\uA9DF\uAA5C-\uAA5F\uAADE\uAADF\uAAF0\uAAF1\uABEB\uFD3E\uFD3F\uFE10-\uFE19\uFE30-\uFE52\uFE54-\uFE61\uFE63\uFE68\uFE6A\uFE6B\uFF01-\uFF03\uFF05-\uFF0A\uFF0C-\uFF0F\uFF1A\uFF1B\uFF1F\uFF20\uFF3B-\uFF3D\uFF3F\uFF5B\uFF5D\uFF5F-\uFF65]|\uD800[\uDD00-\uDD02\uDF9F\uDFD0]|\uD801\uDD6F|\uD802[\uDC57\uDD1F\uDD3F\uDE50-\uDE58\uDE7F\uDEF0-\uDEF6\uDF39-\uDF3F\uDF99-\uDF9C]|\uD803[\uDF55-\uDF59]|\uD804[\uDC47-\uDC4D\uDCBB\uDCBC\uDCBE-\uDCC1\uDD40-\uDD43\uDD74\uDD75\uDDC5-\uDDC8\uDDCD\uDDDB\uDDDD-\uDDDF\uDE38-\uDE3D\uDEA9]|\uD805[\uDC4B-\uDC4F\uDC5B\uDC5D\uDCC6\uDDC1-\uDDD7\uDE41-\uDE43\uDE60-\uDE6C\uDF3C-\uDF3E]|\uD806[\uDC3B\uDE3F-\uDE46\uDE9A-\uDE9C\uDE9E-\uDEA2]|\uD807[\uDC41-\uDC45\uDC70\uDC71\uDEF7\uDEF8]|\uD809[\uDC70-\uDC74]|\uD81A[\uDE6E\uDE6F\uDEF5\uDF37-\uDF3B\uDF44]|\uD81B[\uDE97-\uDE9A]|\uD82F\uDC9F|\uD836[\uDE87-\uDE8B]|\uD83A[\uDD5E\uDD5F]/},function(c,_){c.exports={indexOf:function(i,e){var o,n;if(Array.prototype.indexOf)return i.indexOf(e);for(o=0,n=i.length;o<n;o++)if(i[o]===e)return o;return-1},forEach:function(i,e,o){var n,r;if(Array.prototype.forEach)return i.forEach(e,o);for(n=0,r=i.length;n<r;n++)e.call(o,i[n],n,i)},trim:function(i){return String.prototype.trim?i.trim():i.replace(/(^\s*)|(\s*$)/g,"")},spaceIndex:function(i){var e=/\s|\n|\t/,o=e.exec(i);return o?o.index:-1}}},function(c,_,i){function e(n){i(203)}var o=i(16)(i(66),i(200),e,"data-v-548e2160",null);o.options.__file="D:\\work\\songwang\\yuangongji\\mavonEditor\\src\\components\\md-toolbar-left.vue",o.esModule&&Object.keys(o.esModule).some(function(n){return n!=="default"&&n.substr(0,2)!=="__"})&&console.error("named exports are not supported in *.vue files."),o.options.functional&&console.error("[vue-loader] md-toolbar-left.vue: functional components are not supported with templates, they should use render functions."),c.exports=o.exports},function(c,_,i){var e=i(16)(i(67),i(198),null,null,null);e.options.__file="D:\\work\\songwang\\yuangongji\\mavonEditor\\src\\components\\md-toolbar-right.vue",e.esModule&&Object.keys(e.esModule).some(function(o){return o!=="default"&&o.substr(0,2)!=="__"})&&console.error("named exports are not supported in *.vue files."),e.options.functional&&console.error("[vue-loader] md-toolbar-right.vue: functional components are not supported with templates, they should use render functions."),c.exports=e.exports},function(c,_,i){_.a={"1c":"1c",abnf:"abnf",accesslog:"accesslog",actionscript:"actionscript",as:"actionscript",ada:"ada",apache:"apache",apacheconf:"apache",applescript:"applescript",osascript:"applescript",arduino:"arduino",armasm:"armasm",arm:"armasm",asciidoc:"asciidoc",adoc:"asciidoc",aspectj:"aspectj",autohotkey:"autohotkey",ahk:"autohotkey",autoit:"autoit",avrasm:"avrasm",awk:"awk",axapta:"axapta",bash:"bash",sh:"bash",zsh:"bash",basic:"basic",bnf:"bnf",brainfuck:"brainfuck",bf:"brainfuck",cal:"cal",capnproto:"capnproto",capnp:"capnproto",ceylon:"ceylon",clean:"clean",icl:"clean",dcl:"clean","clojure-repl":"clojure-repl",clojure:"clojure",clj:"clojure",cmake:"cmake","cmake.in":"cmake",coffeescript:"coffeescript",coffee:"coffeescript",cson:"coffeescript",iced:"coffeescript",coq:"coq",cos:"cos",cls:"cos",cpp:"cpp",c:"cpp",cc:"cpp",h:"cpp","c++":"cpp","h++":"cpp",hpp:"cpp",crmsh:"crmsh",crm:"crmsh",pcmk:"crmsh",crystal:"crystal",cr:"crystal",cs:"cs",csharp:"cs",csp:"csp",css:"css",d:"d",dart:"dart",delphi:"delphi",dpr:"delphi",dfm:"delphi",pas:"delphi",pascal:"delphi",freepascal:"delphi",lazarus:"delphi",lpr:"delphi",lfm:"delphi",diff:"diff",patch:"diff",django:"django",jinja:"django",dns:"dns",bind:"dns",zone:"dns",dockerfile:"dockerfile",docker:"dockerfile",dos:"dos",bat:"dos",cmd:"dos",dsconfig:"dsconfig",dts:"dts",dust:"dust",dst:"dust",ebnf:"ebnf",elixir:"elixir",elm:"elm",erb:"erb","erlang-repl":"erlang-repl",erlang:"erlang",erl:"erlang",excel:"excel",xlsx:"excel",xls:"excel",fix:"fix",flix:"flix",fortran:"fortran",f90:"fortran",f95:"fortran",fsharp:"fsharp",fs:"fsharp",gams:"gams",gms:"gams",gauss:"gauss",gss:"gauss",gcode:"gcode",nc:"gcode",gherkin:"gherkin",feature:"gherkin",glsl:"glsl",go:"go",golang:"go",golo:"golo",gradle:"gradle",groovy:"groovy",haml:"haml",handlebars:"handlebars",hbs:"handlebars","html.hbs":"handlebars","html.handlebars":"handlebars",haskell:"haskell",hs:"haskell",haxe:"haxe",hx:"haxe",hsp:"hsp",htmlbars:"htmlbars",http:"http",https:"http",hy:"hy",hylang:"hy",inform7:"inform7",i7:"inform7",ini:"ini",toml:"ini",irpf90:"irpf90",java:"java",jsp:"java",javascript:"javascript",js:"javascript",jsx:"javascript","jboss-cli":"jboss-cli","wildfly-cli":"jboss-cli",json:"json","julia-repl":"julia-repl",julia:"julia",kotlin:"kotlin",lasso:"lasso",ls:"livescript",lassoscript:"lasso",ldif:"ldif",leaf:"leaf",less:"less",lisp:"lisp",livecodeserver:"livecodeserver",livescript:"livescript",llvm:"llvm",lsl:"lsl",lua:"lua",makefile:"makefile",mk:"makefile",mak:"makefile",markdown:"markdown",md:"markdown",mkdown:"markdown",mkd:"markdown",mathematica:"mathematica",mma:"mathematica",matlab:"matlab",maxima:"maxima",mel:"mel",mercury:"mercury",m:"mercury",moo:"mercury",mipsasm:"mipsasm",mips:"mipsasm",mizar:"mizar",mojolicious:"mojolicious",monkey:"monkey",moonscript:"moonscript",moon:"moonscript",n1ql:"n1ql",nginx:"nginx",nginxconf:"nginx",nimrod:"nimrod",nim:"nimrod",nix:"nix",nixos:"nix",nsis:"nsis",objectivec:"objectivec",mm:"objectivec",objc:"objectivec","obj-c":"objectivec",ocaml:"ocaml",ml:"sml",openscad:"openscad",scad:"openscad",oxygene:"oxygene",parser3:"parser3",perl:"perl",pl:"perl",pm:"perl",pf:"pf","pf.conf":"pf",php:"php",php3:"php",php4:"php",php5:"php",php6:"php",pony:"pony",powershell:"powershell",ps:"powershell",processing:"processing",profile:"profile",prolog:"prolog",protobuf:"protobuf",puppet:"puppet",pp:"puppet",purebasic:"purebasic",pb:"purebasic",pbi:"purebasic",python:"python",py:"python",gyp:"python",q:"q",k:"q",kdb:"q",qml:"qml",qt:"qml",r:"r",rib:"rib",roboconf:"roboconf",graph:"roboconf",instances:"roboconf",routeros:"routeros",mikrotik:"routeros",rsl:"rsl",ruby:"ruby",rb:"ruby",gemspec:"ruby",podspec:"ruby",thor:"ruby",irb:"ruby",ruleslanguage:"ruleslanguage",rust:"rust",rs:"rust",scala:"scala",scheme:"scheme",scilab:"scilab",sci:"scilab",scss:"scss",shell:"shell",console:"shell",smali:"smali",smalltalk:"smalltalk",st:"smalltalk",sml:"sml",sqf:"sqf",sql:"sql",stan:"stan",stata:"stata",do:"stata",ado:"stata",step21:"step21",p21:"step21",step:"step21",stp:"step21",stylus:"stylus",styl:"stylus",subunit:"subunit",swift:"swift",taggerscript:"taggerscript",tap:"tap",tcl:"tcl",tk:"tcl",tex:"tex",thrift:"thrift",tp:"tp",twig:"twig",craftcms:"twig",typescript:"typescript",ts:"typescript",vala:"vala",vbnet:"vbnet",vb:"vbnet","vbscript-html":"vbscript-html",vbscript:"vbscript",vbs:"vbscript",verilog:"verilog",v:"verilog",sv:"verilog",svh:"verilog",vhdl:"vhdl",vim:"vim",x86asm:"x86asm",xl:"xl",tao:"xl",xml:"xml",html:"xml",xhtml:"xml",rss:"xml",atom:"xml",xjb:"xml",xsd:"xml",xsl:"xml",plist:"xml",xquery:"xquery",xpath:"xquery",xq:"xquery",yaml:"yaml",yml:"yaml",YAML:"yaml",zephir:"zephir",zep:"zephir"}},function(c,_,i){function e(l){return l&&l.__esModule?l:{default:l}}_.__esModule=!0;var o=i(79),n=e(o),r=i(78),t=e(r),a=typeof t.default=="function"&&typeof n.default=="symbol"?function(l){return typeof l}:function(l){return l&&typeof t.default=="function"&&l.constructor===t.default&&l!==t.default.prototype?"symbol":typeof l};_.default=typeof t.default=="function"&&a(n.default)==="symbol"?function(l){return l===void 0?"undefined":a(l)}:function(l){return l&&typeof t.default=="function"&&l.constructor===t.default&&l!==t.default.prototype?"symbol":l===void 0?"undefined":a(l)}},function(c,_){var i={}.toString;c.exports=function(e){return i.call(e).slice(8,-1)}},function(c,_,i){var e=i(8),o=i(1).document,n=e(o)&&e(o.createElement);c.exports=function(r){return n?o.createElement(r):{}}},function(c,_,i){var e=i(1),o=i(10),n=i(85),r=i(4),t=i(2),a=function(l,p,s){var u,d,v,f=l&a.F,y=l&a.G,m=l&a.S,h=l&a.P,w=l&a.B,g=l&a.W,k=y?o:o[p]||(o[p]={}),b=k.prototype,C=y?e:m?e[p]:(e[p]||{}).prototype;y&&(s=p);for(u in s)(d=!f&&C&&C[u]!==void 0)&&t(k,u)||(v=d?C[u]:s[u],k[u]=y&&typeof C[u]!="function"?s[u]:w&&d?n(v,e):g&&C[u]==v?function(x){var E=function(D,A,S){if(this instanceof x){switch(arguments.length){case 0:return new x;case 1:return new x(D);case 2:return new x(D,A)}return new x(D,A,S)}return x.apply(this,arguments)};return E.prototype=x.prototype,E}(v):h&&typeof v=="function"?n(Function.call,v):v,h&&((k.virtual||(k.virtual={}))[u]=v,l&a.R&&b&&!b[u]&&r(b,u,v)))};a.F=1,a.G=2,a.S=4,a.P=8,a.B=16,a.W=32,a.U=64,a.R=128,c.exports=a},function(c,_,i){c.exports=!i(3)&&!i(11)(function(){return Object.defineProperty(i(41)("div"),"a",{get:function(){return 7}}).a!=7})},function(c,_,i){var e=i(12),o=i(42),n=i(49),r=i(4),t=i(21),a=i(90),l=i(24),p=i(96),s=i(7)("iterator"),u=!([].keys&&"next"in[].keys()),d=function(){return this};c.exports=function(v,f,y,m,h,w,g){a(y,f,m);var k,b,C,x=function(M){if(!u&&M in S)return S[M];switch(M){case"keys":case"values":return function(){return new y(this,M)}}return function(){return new y(this,M)}},E=f+" Iterator",D=h=="values",A=!1,S=v.prototype,T=S[s]||S["@@iterator"]||h&&S[h],I=T||x(h),$=h?D?x("entries"):I:void 0,q=f=="Array"&&S.entries||T;if(q&&(C=p(q.call(new v)))!==Object.prototype&&C.next&&(l(C,E,!0),e||typeof C[s]=="function"||r(C,s,d)),D&&T&&T.name!=="values"&&(A=!0,I=function(){return T.call(this)}),e&&!g||!u&&!A&&S[s]||r(S,s,I),t[f]=I,t[E]=d,h)if(k={values:D?I:x("values"),keys:w?I:x("keys"),entries:$},g)for(b in k)b in S||n(S,b,k[b]);else o(o.P+o.F*(u||A),f,k);return k}},function(c,_,i){var e=i(9),o=i(93),n=i(20),r=i(25)("IE_PROTO"),t=function(){},a=function(){var l,p=i(41)("iframe"),s=n.length;for(p.style.display="none",i(87).appendChild(p),p.src="javascript:",l=p.contentWindow.document,l.open(),l.write("<script>document.F=Object<\/script>"),l.close(),a=l.F;s--;)delete a.prototype[n[s]];return a()};c.exports=Object.create||function(l,p){var s;return l!==null?(t.prototype=e(l),s=new t,t.prototype=null,s[r]=l):s=a(),p===void 0?s:o(s,p)}},function(c,_,i){var e=i(48),o=i(20).concat("length","prototype");_.f=Object.getOwnPropertyNames||function(n){return e(n,o)}},function(c,_){_.f=Object.getOwnPropertySymbols},function(c,_,i){var e=i(2),o=i(6),n=i(84)(!1),r=i(25)("IE_PROTO");c.exports=function(t,a){var l,p=o(t),s=0,u=[];for(l in p)l!=r&&e(p,l)&&u.push(l);for(;a.length>s;)e(p,l=a[s++])&&(~n(u,l)||u.push(l));return u}},function(c,_,i){c.exports=i(4)},function(c,_,i){var e=i(19);c.exports=function(o){return Object(e(o))}},function(c,_){function i(){var t={};return t["align-content"]=!1,t["align-items"]=!1,t["align-self"]=!1,t["alignment-adjust"]=!1,t["alignment-baseline"]=!1,t.all=!1,t["anchor-point"]=!1,t.animation=!1,t["animation-delay"]=!1,t["animation-direction"]=!1,t["animation-duration"]=!1,t["animation-fill-mode"]=!1,t["animation-iteration-count"]=!1,t["animation-name"]=!1,t["animation-play-state"]=!1,t["animation-timing-function"]=!1,t.azimuth=!1,t["backface-visibility"]=!1,t.background=!0,t["background-attachment"]=!0,t["background-clip"]=!0,t["background-color"]=!0,t["background-image"]=!0,t["background-origin"]=!0,t["background-position"]=!0,t["background-repeat"]=!0,t["background-size"]=!0,t["baseline-shift"]=!1,t.binding=!1,t.bleed=!1,t["bookmark-label"]=!1,t["bookmark-level"]=!1,t["bookmark-state"]=!1,t.border=!0,t["border-bottom"]=!0,t["border-bottom-color"]=!0,t["border-bottom-left-radius"]=!0,t["border-bottom-right-radius"]=!0,t["border-bottom-style"]=!0,t["border-bottom-width"]=!0,t["border-collapse"]=!0,t["border-color"]=!0,t["border-image"]=!0,t["border-image-outset"]=!0,t["border-image-repeat"]=!0,t["border-image-slice"]=!0,t["border-image-source"]=!0,t["border-image-width"]=!0,t["border-left"]=!0,t["border-left-color"]=!0,t["border-left-style"]=!0,t["border-left-width"]=!0,t["border-radius"]=!0,t["border-right"]=!0,t["border-right-color"]=!0,t["border-right-style"]=!0,t["border-right-width"]=!0,t["border-spacing"]=!0,t["border-style"]=!0,t["border-top"]=!0,t["border-top-color"]=!0,t["border-top-left-radius"]=!0,t["border-top-right-radius"]=!0,t["border-top-style"]=!0,t["border-top-width"]=!0,t["border-width"]=!0,t.bottom=!1,t["box-decoration-break"]=!0,t["box-shadow"]=!0,t["box-sizing"]=!0,t["box-snap"]=!0,t["box-suppress"]=!0,t["break-after"]=!0,t["break-before"]=!0,t["break-inside"]=!0,t["caption-side"]=!1,t.chains=!1,t.clear=!0,t.clip=!1,t["clip-path"]=!1,t["clip-rule"]=!1,t.color=!0,t["color-interpolation-filters"]=!0,t["column-count"]=!1,t["column-fill"]=!1,t["column-gap"]=!1,t["column-rule"]=!1,t["column-rule-color"]=!1,t["column-rule-style"]=!1,t["column-rule-width"]=!1,t["column-span"]=!1,t["column-width"]=!1,t.columns=!1,t.contain=!1,t.content=!1,t["counter-increment"]=!1,t["counter-reset"]=!1,t["counter-set"]=!1,t.crop=!1,t.cue=!1,t["cue-after"]=!1,t["cue-before"]=!1,t.cursor=!1,t.direction=!1,t.display=!0,t["display-inside"]=!0,t["display-list"]=!0,t["display-outside"]=!0,t["dominant-baseline"]=!1,t.elevation=!1,t["empty-cells"]=!1,t.filter=!1,t.flex=!1,t["flex-basis"]=!1,t["flex-direction"]=!1,t["flex-flow"]=!1,t["flex-grow"]=!1,t["flex-shrink"]=!1,t["flex-wrap"]=!1,t.float=!1,t["float-offset"]=!1,t["flood-color"]=!1,t["flood-opacity"]=!1,t["flow-from"]=!1,t["flow-into"]=!1,t.font=!0,t["font-family"]=!0,t["font-feature-settings"]=!0,t["font-kerning"]=!0,t["font-language-override"]=!0,t["font-size"]=!0,t["font-size-adjust"]=!0,t["font-stretch"]=!0,t["font-style"]=!0,t["font-synthesis"]=!0,t["font-variant"]=!0,t["font-variant-alternates"]=!0,t["font-variant-caps"]=!0,t["font-variant-east-asian"]=!0,t["font-variant-ligatures"]=!0,t["font-variant-numeric"]=!0,t["font-variant-position"]=!0,t["font-weight"]=!0,t.grid=!1,t["grid-area"]=!1,t["grid-auto-columns"]=!1,t["grid-auto-flow"]=!1,t["grid-auto-rows"]=!1,t["grid-column"]=!1,t["grid-column-end"]=!1,t["grid-column-start"]=!1,t["grid-row"]=!1,t["grid-row-end"]=!1,t["grid-row-start"]=!1,t["grid-template"]=!1,t["grid-template-areas"]=!1,t["grid-template-columns"]=!1,t["grid-template-rows"]=!1,t["hanging-punctuation"]=!1,t.height=!0,t.hyphens=!1,t.icon=!1,t["image-orientation"]=!1,t["image-resolution"]=!1,t["ime-mode"]=!1,t["initial-letters"]=!1,t["inline-box-align"]=!1,t["justify-content"]=!1,t["justify-items"]=!1,t["justify-self"]=!1,t.left=!1,t["letter-spacing"]=!0,t["lighting-color"]=!0,t["line-box-contain"]=!1,t["line-break"]=!1,t["line-grid"]=!1,t["line-height"]=!1,t["line-snap"]=!1,t["line-stacking"]=!1,t["line-stacking-ruby"]=!1,t["line-stacking-shift"]=!1,t["line-stacking-strategy"]=!1,t["list-style"]=!0,t["list-style-image"]=!0,t["list-style-position"]=!0,t["list-style-type"]=!0,t.margin=!0,t["margin-bottom"]=!0,t["margin-left"]=!0,t["margin-right"]=!0,t["margin-top"]=!0,t["marker-offset"]=!1,t["marker-side"]=!1,t.marks=!1,t.mask=!1,t["mask-box"]=!1,t["mask-box-outset"]=!1,t["mask-box-repeat"]=!1,t["mask-box-slice"]=!1,t["mask-box-source"]=!1,t["mask-box-width"]=!1,t["mask-clip"]=!1,t["mask-image"]=!1,t["mask-origin"]=!1,t["mask-position"]=!1,t["mask-repeat"]=!1,t["mask-size"]=!1,t["mask-source-type"]=!1,t["mask-type"]=!1,t["max-height"]=!0,t["max-lines"]=!1,t["max-width"]=!0,t["min-height"]=!0,t["min-width"]=!0,t["move-to"]=!1,t["nav-down"]=!1,t["nav-index"]=!1,t["nav-left"]=!1,t["nav-right"]=!1,t["nav-up"]=!1,t["object-fit"]=!1,t["object-position"]=!1,t.opacity=!1,t.order=!1,t.orphans=!1,t.outline=!1,t["outline-color"]=!1,t["outline-offset"]=!1,t["outline-style"]=!1,t["outline-width"]=!1,t.overflow=!1,t["overflow-wrap"]=!1,t["overflow-x"]=!1,t["overflow-y"]=!1,t.padding=!0,t["padding-bottom"]=!0,t["padding-left"]=!0,t["padding-right"]=!0,t["padding-top"]=!0,t.page=!1,t["page-break-after"]=!1,t["page-break-before"]=!1,t["page-break-inside"]=!1,t["page-policy"]=!1,t.pause=!1,t["pause-after"]=!1,t["pause-before"]=!1,t.perspective=!1,t["perspective-origin"]=!1,t.pitch=!1,t["pitch-range"]=!1,t["play-during"]=!1,t.position=!1,t["presentation-level"]=!1,t.quotes=!1,t["region-fragment"]=!1,t.resize=!1,t.rest=!1,t["rest-after"]=!1,t["rest-before"]=!1,t.richness=!1,t.right=!1,t.rotation=!1,t["rotation-point"]=!1,t["ruby-align"]=!1,t["ruby-merge"]=!1,t["ruby-position"]=!1,t["shape-image-threshold"]=!1,t["shape-outside"]=!1,t["shape-margin"]=!1,t.size=!1,t.speak=!1,t["speak-as"]=!1,t["speak-header"]=!1,t["speak-numeral"]=!1,t["speak-punctuation"]=!1,t["speech-rate"]=!1,t.stress=!1,t["string-set"]=!1,t["tab-size"]=!1,t["table-layout"]=!1,t["text-align"]=!0,t["text-align-last"]=!0,t["text-combine-upright"]=!0,t["text-decoration"]=!0,t["text-decoration-color"]=!0,t["text-decoration-line"]=!0,t["text-decoration-skip"]=!0,t["text-decoration-style"]=!0,t["text-emphasis"]=!0,t["text-emphasis-color"]=!0,t["text-emphasis-position"]=!0,t["text-emphasis-style"]=!0,t["text-height"]=!0,t["text-indent"]=!0,t["text-justify"]=!0,t["text-orientation"]=!0,t["text-overflow"]=!0,t["text-shadow"]=!0,t["text-space-collapse"]=!0,t["text-transform"]=!0,t["text-underline-position"]=!0,t["text-wrap"]=!0,t.top=!1,t.transform=!1,t["transform-origin"]=!1,t["transform-style"]=!1,t.transition=!1,t["transition-delay"]=!1,t["transition-duration"]=!1,t["transition-property"]=!1,t["transition-timing-function"]=!1,t["unicode-bidi"]=!1,t["vertical-align"]=!1,t.visibility=!1,t["voice-balance"]=!1,t["voice-duration"]=!1,t["voice-family"]=!1,t["voice-pitch"]=!1,t["voice-range"]=!1,t["voice-rate"]=!1,t["voice-stress"]=!1,t["voice-volume"]=!1,t.volume=!1,t["white-space"]=!1,t.widows=!1,t.width=!0,t["will-change"]=!1,t["word-break"]=!0,t["word-spacing"]=!0,t["word-wrap"]=!0,t["wrap-flow"]=!1,t["wrap-through"]=!1,t["writing-mode"]=!1,t["z-index"]=!1,t}function e(t,a,l){}function o(t,a,l){}function n(t,a){return r.test(a)?"":a}var r=/javascript\s*\:/gim;_.whiteList=i(),_.getDefaultWhiteList=i,_.onAttr=e,_.onIgnoreAttr=o,_.safeAttrValue=n},function(c,_){c.exports={indexOf:function(i,e){var o,n;if(Array.prototype.indexOf)return i.indexOf(e);for(o=0,n=i.length;o<n;o++)if(i[o]===e)return o;return-1},forEach:function(i,e,o){var n,r;if(Array.prototype.forEach)return i.forEach(e,o);for(n=0,r=i.length;n<r;n++)e.call(o,i[n],n,i)},trim:function(i){return String.prototype.trim?i.trim():i.replace(/(^\s*)|(\s*$)/g,"")},trimRight:function(i){return String.prototype.trimRight?i.trimRight():i.replace(/(\s*$)/g,"")}}},function(c,_,i){c.exports=i(181)},function(c,_,i){var e=`<[A-Za-z][A-Za-z0-9\\-]*(?:\\s+[a-zA-Z_:][a-zA-Z0-9:._-]*(?:\\s*=\\s*(?:[^"'=<>\`\\x00-\\x20]+|'[^']*'|"[^"]*"))?)*\\s*\\/?>`,o="<\\/[A-Za-z][A-Za-z0-9\\-]*\\s*>",n=new RegExp("^(?:"+e+"|"+o+"|<!---->|<!--(?:-?[^>-])(?:-?[^-])*-->|<[?].*?[?]>|<![A-Z]+\\s+[^>]*>|<!\\[CDATA\\[[\\s\\S]*?\\]\\]>)"),r=new RegExp("^(?:"+e+"|"+o+")");c.exports.HTML_TAG_RE=n,c.exports.HTML_OPEN_CLOSE_TAG_RE=r},function(c,_,i){function e(o,n){var r,t,a,l,p,s,u=n.length;for(r=u-1;r>=0;r--)t=n[r],t.marker!==95&&t.marker!==42||t.end!==-1&&(a=n[t.end],s=r>0&&n[r-1].end===t.end+1&&n[r-1].token===t.token-1&&n[t.end+1].token===a.token+1&&n[r-1].marker===t.marker,p=String.fromCharCode(t.marker),l=o.tokens[t.token],l.type=s?"strong_open":"em_open",l.tag=s?"strong":"em",l.nesting=1,l.markup=s?p+p:p,l.content="",l=o.tokens[a.token],l.type=s?"strong_close":"em_close",l.tag=s?"strong":"em",l.nesting=-1,l.markup=s?p+p:p,l.content="",s&&(o.tokens[n[r-1].token].content="",o.tokens[n[t.end+1].token].content="",r--))}c.exports.tokenize=function(o,n){var r,t,a,l=o.pos,p=o.src.charCodeAt(l);if(n||p!==95&&p!==42)return!1;for(t=o.scanDelims(o.pos,p===42),r=0;r<t.length;r++)a=o.push("text","",0),a.content=String.fromCharCode(p),o.delimiters.push({marker:p,length:t.length,jump:r,token:o.tokens.length-1,end:-1,open:t.can_open,close:t.can_close});return o.pos+=t.length,!0},c.exports.postProcess=function(o){var n,r=o.tokens_meta,t=o.tokens_meta.length;for(e(o,o.delimiters),n=0;n<t;n++)r[n]&&r[n].delimiters&&e(o,r[n].delimiters)}},function(c,_,i){function e(o,n){var r,t,a,l,p,s=[],u=n.length;for(r=0;r<u;r++)a=n[r],a.marker===126&&a.end!==-1&&(l=n[a.end],p=o.tokens[a.token],p.type="s_open",p.tag="s",p.nesting=1,p.markup="~~",p.content="",p=o.tokens[l.token],p.type="s_close",p.tag="s",p.nesting=-1,p.markup="~~",p.content="",o.tokens[l.token-1].type==="text"&&o.tokens[l.token-1].content==="~"&&s.push(l.token-1));for(;s.length;){for(r=s.pop(),t=r+1;t<o.tokens.length&&o.tokens[t].type==="s_close";)t++;t--,r!==t&&(p=o.tokens[t],o.tokens[t]=o.tokens[r],o.tokens[r]=p)}}c.exports.tokenize=function(o,n){var r,t,a,l,p,s=o.pos,u=o.src.charCodeAt(s);if(n||u!==126||(t=o.scanDelims(o.pos,!0),l=t.length,p=String.fromCharCode(u),l<2))return!1;for(l%2&&(a=o.push("text","",0),a.content=p,l--),r=0;r<l;r+=2)a=o.push("text","",0),a.content=p+p,o.delimiters.push({marker:u,length:0,jump:r,token:o.tokens.length-1,end:-1,open:t.can_open,close:t.can_close});return o.pos+=t.length,!0},c.exports.postProcess=function(o){var n,r=o.tokens_meta,t=o.tokens_meta.length;for(e(o,o.delimiters),n=0;n<t;n++)r[n]&&r[n].delimiters&&e(o,r[n].delimiters)}},function(c,_,i){c.exports.encode=i(183),c.exports.decode=i(182),c.exports.format=i(184),c.exports.parse=i(185)},function(c,_){c.exports=/[\0-\x1F\x7F-\x9F]/},function(c,_){c.exports=/[ \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000]/},function(c,_){c.exports=/[\0-\uD7FF\uE000-\uFFFF]|[\uD800-\uDBFF][\uDC00-\uDFFF]|[\uD800-\uDBFF](?![\uDC00-\uDFFF])|(?:[^\uD800-\uDBFF]|^)[\uDC00-\uDFFF]/},function(c,_,i){function e(){return{a:["target","href","title"],abbr:["title"],address:[],area:["shape","coords","href","alt"],article:[],aside:[],audio:["autoplay","controls","loop","preload","src"],b:[],bdi:["dir"],bdo:["dir"],big:[],blockquote:["cite"],br:[],caption:[],center:[],cite:[],code:[],col:["align","valign","span","width"],colgroup:["align","valign","span","width"],dd:[],del:["datetime"],details:["open"],div:[],dl:[],dt:[],em:[],font:["color","size","face"],footer:[],h1:[],h2:[],h3:[],h4:[],h5:[],h6:[],header:[],hr:[],i:[],img:["src","alt","title","width","height"],ins:["datetime"],li:[],mark:[],nav:[],ol:[],p:[],pre:[],s:[],section:[],small:[],span:[],sub:[],sup:[],strong:[],table:["width","border","align","valign"],tbody:["align","valign"],td:["width","rowspan","colspan","align","valign"],tfoot:["align","valign"],th:["width","rowspan","colspan","align","valign"],thead:["align","valign"],tr:["rowspan","align","valign"],tt:[],u:[],ul:[],video:["autoplay","controls","loop","preload","src","height","width"]}}function o(L,P,j){}function n(L,P,j){}function r(L,P,j){}function t(L,P,j){}function a(L){return L.replace(E,"&lt;").replace(D,"&gt;")}function l(L,P,j,O){if(j=f(j),P==="href"||P==="src"){if((j=C.trim(j))==="#")return"#";if(j.substr(0,7)!=="http://"&&j.substr(0,8)!=="https://"&&j.substr(0,7)!=="mailto:"&&j.substr(0,4)!=="tel:"&&j.substr(0,11)!=="data:image/"&&j.substr(0,6)!=="ftp://"&&j.substr(0,2)!=="./"&&j.substr(0,3)!=="../"&&j[0]!=="#"&&j[0]!=="/")return""}else if(P==="background"){if(q.lastIndex=0,q.test(j))return""}else if(P==="style"){if(M.lastIndex=0,M.test(j)||(z.lastIndex=0,z.test(j)&&(q.lastIndex=0,q.test(j))))return"";O!==!1&&(O=O||x,j=O.process(j))}return j=y(j)}function p(L){return L.replace(A,"&quot;")}function s(L){return L.replace(S,'"')}function u(L){return L.replace(T,function(P,j){return j[0]==="x"||j[0]==="X"?String.fromCharCode(parseInt(j.substr(1),16)):String.fromCharCode(parseInt(j,10))})}function d(L){return L.replace(I,":").replace($," ")}function v(L){for(var P="",j=0,O=L.length;j<O;j++)P+=L.charCodeAt(j)<32?" ":L.charAt(j);return C.trim(P)}function f(L){return L=s(L),L=u(L),L=d(L),L=v(L)}function y(L){return L=p(L),L=a(L)}function m(){return""}function h(L,P){function j(N){return!!O||C.indexOf(L,N)!==-1}typeof P!="function"&&(P=function(){});var O=!Array.isArray(L),B=[],H=!1;return{onIgnoreTag:function(N,K,W){if(j(N)){if(W.isClosing){var G="[/removed]",Y=W.position+G.length;return B.push([H!==!1?H:W.position,Y]),H=!1,G}return H||(H=W.position),"[removed]"}return P(N,K,W)},remove:function(N){var K="",W=0;return C.forEach(B,function(G){K+=N.slice(W,G[0]),W=G[1]}),K+=N.slice(W)}}}function w(L){return L.replace(U,"")}function g(L){var P=L.split("");return P=P.filter(function(j){var O=j.charCodeAt(0);return O!==127&&(!(O<=31)||O===10||O===13)}),P.join("")}var k=i(31).FilterCSS,b=i(31).getDefaultWhiteList,C=i(35),x=new k,E=/</g,D=/>/g,A=/"/g,S=/&quot;/g,T=/&#([a-zA-Z0-9]*);?/gim,I=/&colon;?/gim,$=/&newline;?/gim,q=/((j\s*a\s*v\s*a|v\s*b|l\s*i\s*v\s*e)\s*s\s*c\s*r\s*i\s*p\s*t\s*|m\s*o\s*c\s*h\s*a)\:/gi,M=/e\s*x\s*p\s*r\s*e\s*s\s*s\s*i\s*o\s*n\s*\(.*/gi,z=/u\s*r\s*l\s*\(.*/gi,U=/<!--[\s\S]*?-->/g;_.whiteList=e(),_.getDefaultWhiteList=e,_.onTag=o,_.onIgnoreTag=n,_.onTagAttr=r,_.onIgnoreTagAttr=t,_.safeAttrValue=l,_.escapeHtml=a,_.escapeQuote=p,_.unescapeQuote=s,_.escapeHtmlEntities=u,_.escapeDangerHtml5Entities=d,_.clearNonPrintableCharacter=v,_.friendlyAttrValue=f,_.escapeAttrValue=y,_.onIgnoreTagStripAll=m,_.StripTagBody=h,_.stripCommentTag=w,_.stripBlankChar=g,_.cssFilter=x,_.getDefaultCSSWhiteList=b},function(c,_,i){function e(d){var v=s.spaceIndex(d);if(v===-1)var f=d.slice(1,-1);else var f=d.slice(1,v+1);return f=s.trim(f).toLowerCase(),f.slice(0,1)==="/"&&(f=f.slice(1)),f.slice(-1)==="/"&&(f=f.slice(0,-1)),f}function o(d){return d.slice(0,2)==="</"}function n(d,v,f){var y="",m=0,h=!1,w=!1,g=0,k=d.length,b="",C="";e:for(g=0;g<k;g++){var x=d.charAt(g);if(h===!1){if(x==="<"){h=g;continue}}else if(w===!1){if(x==="<"){y+=f(d.slice(m,g)),h=g,m=g;continue}if(x===">"){y+=f(d.slice(m,h)),C=d.slice(h,g+1),b=e(C),y+=v(h,y.length,b,C,o(C)),m=g+1,h=!1;continue}if(x==='"'||x==="'")for(var E=1,D=d.charAt(g-E);D===" "||D==="=";){if(D==="="){w=x;continue e}D=d.charAt(g-++E)}}else if(x===w){w=!1;continue}}return m<d.length&&(y+=f(d.substr(m))),y}function r(d,v){function f(x,E){if(x=s.trim(x),x=x.replace(u,"").toLowerCase(),!(x.length<1)){var D=v(x,E||"");D&&m.push(D)}}for(var y=0,m=[],h=!1,w=d.length,g=0;g<w;g++){var k,b,C=d.charAt(g);if(h!==!1||C!=="=")if(h===!1||g!==y||C!=='"'&&C!=="'"||d.charAt(g-1)!=="="){if(/\s|\n|\t/.test(C)){if(d=d.replace(/\s|\n|\t/g," "),h===!1){if((b=t(d,g))===-1){k=s.trim(d.slice(y,g)),f(k),h=!1,y=g+1;continue}g=b-1;continue}if((b=a(d,g-1))===-1){k=s.trim(d.slice(y,g)),k=p(k),f(h,k),h=!1,y=g+1;continue}}}else{if((b=d.indexOf(C,g+1))===-1)break;k=s.trim(d.slice(y+1,b)),f(h,k),h=!1,g=b,y=g+1}else h=d.slice(y,g),y=g+1}return y<d.length&&(h===!1?f(d.slice(y)):f(h,p(s.trim(d.slice(y))))),s.trim(m.join(" "))}function t(d,v){for(;v<d.length;v++){var f=d[v];if(f!==" ")return f==="="?v:-1}}function a(d,v){for(;v>0;v--){var f=d[v];if(f!==" ")return f==="="?v:-1}}function l(d){return d[0]==='"'&&d[d.length-1]==='"'||d[0]==="'"&&d[d.length-1]==="'"}function p(d){return l(d)?d.substr(1,d.length-2):d}var s=i(35),u=/[^a-zA-Z0-9_:\.\-]/gim;_.parseTag=n,_.parseAttr=r},function(c,_,i){function e(n){i(204),i(205)}var o=i(16)(i(68),i(201),e,"data-v-7a63e4b3",null);o.options.__file="D:\\work\\songwang\\yuangongji\\mavonEditor\\src\\mavon-editor.vue",o.esModule&&Object.keys(o.esModule).some(function(n){return n!=="default"&&n.substr(0,2)!=="__"})&&console.error("named exports are not supported in *.vue files."),o.options.functional&&console.error("[vue-loader] mavon-editor.vue: functional components are not supported with templates, they should use render functions."),c.exports=o.exports},function(c,_,i){var e=i(197),o={autoTextarea:e,install:function(n){n.component("auto-textarea",e)}};c.exports=o},function(c,_,i){Object.defineProperty(_,"__esModule",{value:!0}),_.default={data:function(){var e=this;return{temp_value:function(){return e.value}(),s_autofocus:function(){if(e.autofocus)return"autofocus"}()}},created:function(){},props:{fullHeight:{type:Boolean,default:!1},autofocus:{type:Boolean,default:!1},value:{type:String,default:""},placeholder:{type:String,default:""},border:{type:Boolean,default:!1},resize:{type:Boolean,default:!1},onchange:{type:Function,default:null},fontSize:{type:String,default:"14px"},lineHeight:{type:String,default:"18px"}},methods:{change:function(e){this.onchange&&this.onchange(this.temp_value,e)}},watch:{value:function(e,o){this.temp_value=e},temp_value:function(e,o){this.$emit("input",e)}}}},function(c,_,i){Object.defineProperty(_,"__esModule",{value:!0}),_.default={name:"s-md-toolbar-left",props:{editable:{type:Boolean,default:!0},transition:{type:Boolean,default:!0},toolbars:{type:Object,required:!0},d_words:{type:Object,required:!0},image_filter:{type:Function,default:null}},data:function(){return{img_file:[[0,null]],img_timer:null,header_timer:null,s_img_dropdown_open:!1,s_header_dropdown_open:!1,s_img_link_open:!1,trigger:null,num:0,link_text:"",link_addr:"",link_type:"link"}},methods:{$imgLinkAdd:function(){this.$emit("toolbar_left_addlink",this.link_type,this.link_text,this.link_addr),this.s_img_link_open=!1},$toggle_imgLinkAdd:function(e){var o=this;this.link_type=e,this.link_text=this.link_addr="",this.s_img_link_open=!0,this.$nextTick(function(){o.$refs.linkTextInput.focus()}),this.s_img_dropdown_open=!1},$imgFileListClick:function(e){this.$emit("imgTouch",this.img_file[e])},$changeUrl:function(e,o){this.img_file[e][0]=o},$imgFileAdd:function(e){this.img_file.push([++this.num,e]),this.$emit("imgAdd",this.num,e),this.s_img_dropdown_open=!1},$imgFilesAdd:function(e){for(var o=typeof this.image_filter=="function",n=0;n<e.length;n++)o&&this.image_filter(e[n])===!0?this.$imgFileAdd(e[n]):!o&&e[n].type.match(/^image\//i)&&this.$imgFileAdd(e[n])},$imgAdd:function(e){this.$imgFilesAdd(e.target.files),e.target.value=""},$imgDel:function(e){this.$emit("imgDel",this.img_file[e]),this.img_file.splice(e,1),this.num--,this.s_img_dropdown_open=!1},isEqualName:function(e,o){return!(!this.img_file[o][1]||this.img_file[o][1].name!=e&&this.img_file[o][1]._name!=e)},$imgDelByFilename:function(e){for(var o=0;this.img_file.length>o;){if(this.img_file[o][1]==e||this.isEqualName(e,o))return this.$imgDel(o),!0;o+=1}return!1},$imgAddByFilename:function(e,o){for(var n=0;n<this.img_file.length;n++)if(this.img_file[n][0]==e)return!1;return this.img_file[0][0]=e,this.img_file[0][1]=o,this.img_file[0][2]=e,this.img_file.unshift(["./"+this.num,null]),this.$emit("imgAdd",this.img_file[1][0],o,!1),!0},$imgAddByUrl:function(e,o){for(var n=0;n<this.img_file.length;n++)if(this.img_file[n][0]==e)return!1;return this.img_file[0][0]=e,this.img_file[0][1]=o,this.img_file.unshift(["./"+this.num,null]),!0},$imgUpdateByFilename:function(e,o){for(var n=0;n<this.img_file.length;n++)if(this.img_file[n][0]==e||this.isEqualName(e,n))return this.img_file[n][1]=o,this.$emit("imgAdd",e,o,!1),!0;return!1},$mouseenter_img_dropdown:function(){this.editable&&(clearTimeout(this.img_timer),this.s_img_dropdown_open=!0)},$mouseleave_img_dropdown:function(){var e=this;this.img_timer=setTimeout(function(){e.s_img_dropdown_open=!1},200)},$mouseenter_header_dropdown:function(){this.editable&&(clearTimeout(this.header_timer),this.s_header_dropdown_open=!0)},$mouseleave_header_dropdown:function(){var e=this;this.header_timer=setTimeout(function(){e.s_header_dropdown_open=!1},200)},$clicks:function(e){this.editable&&this.$emit("toolbar_left_click",e)},$click_header:function(e){this.$emit("toolbar_left_click",e),this.s_header_dropdown_open=!1},handleClose:function(e){this.s_img_dropdown_open=!1}}}},function(c,_,i){Object.defineProperty(_,"__esModule",{value:!0}),_.default={name:"s-md-toolbar-right",props:{s_subfield:{type:Boolean,required:!0},toolbars:{type:Object,required:!0},s_preview_switch:{type:Boolean,required:!0},s_fullScreen:{type:Boolean,required:!0},s_html_code:{type:Boolean,required:!0},s_navigation:{type:Boolean,required:!0},d_words:{type:Object,required:!0}},methods:{$clicks:function(e){this.$emit("toolbar_right_click",e)}}}},function(module,__webpack_exports__,__webpack_require__){Object.defineProperty(__webpack_exports__,"__esModule",{value:!0});var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_typeof__=__webpack_require__(39),__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_typeof___default=__webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_typeof__),__WEBPACK_IMPORTED_MODULE_1_auto_textarea__=__webpack_require__(64);__webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_auto_textarea__);var __WEBPACK_IMPORTED_MODULE_2__lib_core_keydown_listen_js__=__webpack_require__(73),__WEBPACK_IMPORTED_MODULE_3__lib_core_hljs_lang_hljs_css_js__=__webpack_require__(72);__webpack_require__(38);var __WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__=__webpack_require__(18),__WEBPACK_IMPORTED_MODULE_6__lib_util_js__=__webpack_require__(77),__WEBPACK_IMPORTED_MODULE_7__lib_toolbar_left_click_js__=__webpack_require__(75),__WEBPACK_IMPORTED_MODULE_8__lib_toolbar_right_click_js__=__webpack_require__(76),__WEBPACK_IMPORTED_MODULE_9__lib_config_js__=__webpack_require__(70);__webpack_require__(71);var __WEBPACK_IMPORTED_MODULE_11__lib_mixins_markdown_js__=__webpack_require__(74),__WEBPACK_IMPORTED_MODULE_12__components_md_toolbar_left_vue__=__webpack_require__(36),__WEBPACK_IMPORTED_MODULE_12__components_md_toolbar_left_vue___default=__webpack_require__.n(__WEBPACK_IMPORTED_MODULE_12__components_md_toolbar_left_vue__),__WEBPACK_IMPORTED_MODULE_13__components_md_toolbar_right_vue__=__webpack_require__(37),__WEBPACK_IMPORTED_MODULE_13__components_md_toolbar_right_vue___default=__webpack_require__.n(__WEBPACK_IMPORTED_MODULE_13__components_md_toolbar_right_vue__),__WEBPACK_IMPORTED_MODULE_14__lib_font_css_fontello_css__=__webpack_require__(114);__webpack_require__.n(__WEBPACK_IMPORTED_MODULE_14__lib_font_css_fontello_css__);var __WEBPACK_IMPORTED_MODULE_15__lib_css_md_css__=__webpack_require__(113);__webpack_require__.n(__WEBPACK_IMPORTED_MODULE_15__lib_css_md_css__);var xss=__webpack_require__(209);__webpack_exports__.default={mixins:[__WEBPACK_IMPORTED_MODULE_11__lib_mixins_markdown_js__.a],props:{scrollStyle:{type:Boolean,default:!0},boxShadow:{type:Boolean,default:!0},transition:{type:Boolean,default:!0},autofocus:{type:Boolean,default:!0},fontSize:{type:String,default:"14px"},toolbarsBackground:{type:String,default:"#ffffff"},editorBackground:{type:String,default:"#ffffff"},previewBackground:{type:String,default:"#fbfbfb"},boxShadowStyle:{type:String,default:"0 2px 12px 0 rgba(0, 0, 0, 0.1)"},help:{type:String,default:null},value:{type:String,default:""},language:{type:String,default:"zh-CN"},subfield:{type:Boolean,default:!0},navigation:{type:Boolean,default:!1},defaultOpen:{type:String,default:null},editable:{type:Boolean,default:!0},toolbarsFlag:{type:Boolean,default:!0},toolbars:{type:Object,default:function(){return __WEBPACK_IMPORTED_MODULE_9__lib_config_js__.a.toolbars}},xssOptions:{type:Object,default:function(){return null}},codeStyle:{type:String,default:function(){return"github"}},placeholder:{type:String,default:null},ishljs:{type:Boolean,default:!0},externalLink:{type:[Object,Boolean],default:!0},imageFilter:{type:Function,default:null},imageClick:{type:Function,default:null},tabSize:{type:Number,default:0},shortCut:{type:Boolean,default:!0}},data:function(){var c=this;return{s_right_click_menu_show:!1,right_click_menu_top:0,right_click_menu_left:0,s_subfield:function(){return c.subfield}(),s_autofocus:!0,s_navigation:function(){return c.navigation}(),s_scrollStyle:function(){return c.scrollStyle}(),d_value:"",d_render:"",s_preview_switch:function(){var _=c.defaultOpen;return _||(_=c.subfield?"preview":"edit"),_==="preview"}(),s_fullScreen:!1,s_help:!1,s_html_code:!1,d_help:null,d_words:null,edit_scroll_height:-1,s_readmodel:!1,s_table_enter:!1,d_history:function(){var _=[];return _.push(c.value),_}(),d_history_index:0,currentTimeout:"",d_image_file:[],d_preview_imgsrc:null,s_external_link:{markdown_css:function(){return"https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/2.9.0/github-markdown.min.css"},hljs_js:function(){return"https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"},hljs_lang:function(_){return"https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/languages/"+_+".min.js"},hljs_css:function(_){return __WEBPACK_IMPORTED_MODULE_3__lib_core_hljs_lang_hljs_css_js__.a[_]?"https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/"+_+".min.css":""},katex_js:function(){return"https://cdnjs.cloudflare.com/ajax/libs/KaTeX/0.8.3/katex.min.js"},katex_css:function(){return"https://cdnjs.cloudflare.com/ajax/libs/KaTeX/0.8.3/katex.min.css"}},p_external_link:{},textarea_selectionEnd:0,textarea_selectionEnds:[0]}},created:function(){var c=this;this.initLanguage(),this.initExternalFuc(),this.$nextTick(function(){c.editableTextarea()})},mounted:function(){var c=this;this.$el.addEventListener("paste",function(_){c.$paste(_)}),this.$el.addEventListener("drop",function(_){c.$drag(_)}),__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_2__lib_core_keydown_listen_js__.a)(this),__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.a)(this),this.autofocus&&this.getTextareaDom().focus(),__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.b)(this),this.d_value=this.value||"",document.body.appendChild(this.$refs.help),this.loadExternalLink("markdown_css","css"),this.loadExternalLink("katex_css","css"),this.loadExternalLink("katex_js","js",function(){c.iRender(!0)}),this.loadExternalLink("hljs_js","js",function(){c.iRender(!0)}),__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_typeof___default()(c.externalLink)==="object"&&typeof c.externalLink.markdown_css=="function"||c.codeStyleChange(c.codeStyle,!0)},beforeDestroy:function(){document.body.removeChild(this.$refs.help)},getMarkdownIt:function(){return this.mixins[0].data().markdownIt},methods:{loadExternalLink:function(c,_,i){if(typeof this.p_external_link[c]!="function")return void(this.p_external_link[c]!=0&&console.error("external_link."+c,"is not a function, if you want to disabled this error log, set external_link."+c,"to function or false"));var e={css:__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.c,js:__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.d};e.hasOwnProperty(_)&&e[_](this.p_external_link[c](),i)},initExternalFuc:function(){for(var c=this,_=["markdown_css","hljs_js","hljs_css","hljs_lang","katex_js","katex_css"],i=__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_typeof___default()(c.externalLink),e=i==="object",o=i==="boolean",n=0;n<_.length;n++)o&&!c.externalLink||e&&c.externalLink[_[n]]===!1?c.p_external_link[_[n]]=!1:e&&typeof c.externalLink[_[n]]=="function"?c.p_external_link[_[n]]=c.externalLink[_[n]]:c.p_external_link[_[n]]=c.s_external_link[_[n]]},textAreaFocus:function(){this.$refs.vNoteTextarea.$refs.vTextarea.focus()},$drag:function(c){var _=c.dataTransfer;if(_){var i=_.files;i.length>0&&(c.preventDefault(),this.$refs.toolbar_left.$imgFilesAdd(i))}},$paste:function(c){var _=c.clipboardData;if(_){var i=_.items;if(!i)return;for(var e=_.types||[],o=null,n=0;n<e.length;n++)if(e[n]==="Files"){o=i[n];break}if(o&&o.kind==="file"){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_6__lib_util_js__.a)(c);var r=o.getAsFile();this.$refs.toolbar_left.$imgFilesAdd([r])}}},$imgTouch:function(c){},$imgDel:function(c){this.markdownIt.image_del(c[1]);var _=c[0],i=new RegExp("\\!\\["+c[1]._name+"\\]\\("+_+"\\)","g");this.d_value=this.d_value.replace(i,""),this.iRender(),this.$emit("imgDel",c)},$imgAdd:function(c,_,i){i===void 0&&(i=!0);var e=this;if(this.__rFilter==null&&(this.__rFilter=/^image\//i),this.__oFReader=new FileReader,this.__oFReader.onload=function(n){e.markdownIt.image_add(c,n.target.result),_.miniurl=n.target.result,i===!0&&(_._name=_.name.replace(/[\[\]\(\)\+\{\}&\|\\\*^%$#@\-]/g,""),e.insertText(e.getTextareaDom(),{prefix:"!["+_._name+"]("+c+")",subfix:"",str:""}),e.$nextTick(function(){e.$emit("imgAdd",c,_)}))},_){var o=_;this.__rFilter.test(o.type)&&this.__oFReader.readAsDataURL(o)}},$imgUpdateByUrl:function(c,_){var i=this;this.markdownIt.image_add(c,_),this.$nextTick(function(){i.d_render=this.markdownIt.render(this.d_value)})},$imgAddByUrl:function(c,_){return!!this.$refs.toolbar_left.$imgAddByUrl(c,_)&&(this.$imgUpdateByUrl(c,_),!0)},$img2Url:function $img2Url(fileIndex,url){var reg_str="/(!\\[[^\\[]*?\\](?=\\())\\(\\s*("+fileIndex+")\\s*\\)/g",reg=eval(reg_str);this.d_value=this.d_value.replace(reg,"$1("+url+")"),this.$refs.toolbar_left.$changeUrl(fileIndex,url),this.iRender()},$imglst2Url:function(c){if(c instanceof Array)for(var _=0;_<c.length;_++)this.$img2Url(c[_][0],c[_][1])},toolbar_left_click:function(c){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_7__lib_toolbar_left_click_js__.a)(c,this)},toolbar_left_addlink:function(c,_,i){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_7__lib_toolbar_left_click_js__.b)(c,_,i,this)},toolbar_right_click:function(c){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_8__lib_toolbar_right_click_js__.a)(c,this)},getNavigation:function(c,_){return __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.e)(c,_)},change:function(c,_){this.$emit("change",c,_)},fullscreen:function(c,_){this.$emit("fullScreen",c,_)},readmodel:function(c,_){this.$emit("readModel",c,_)},previewtoggle:function(c,_){this.$emit("previewToggle",c,_)},subfieldtoggle:function(c,_){this.$emit("subfieldToggle",c,_)},htmlcode:function(c,_){this.$emit("htmlCode",c,_)},helptoggle:function(c,_){this.$emit("helpToggle",c,_)},save:function(c,_){this.$emit("save",c,_)},navigationtoggle:function(c,_){this.$emit("navigationToggle",c,_)},$toolbar_right_read_change_status:function(){this.s_readmodel=!this.s_readmodel,this.readmodel&&this.readmodel(this.s_readmodel,this.d_value),this.s_readmodel&&this.toolbars.navigation&&this.getNavigation(this,!0)},$v_edit_scroll:function(c){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.f)(c,this)},getTextareaDom:function(){return this.$refs.vNoteTextarea.$refs.vTextarea},insertText:function(c,_){var i=_.prefix,e=_.subfix,o=_.str,n=_.type;__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.g)(c,{prefix:i,subfix:e,str:o,type:n},this)},insertTab:function(){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.h)(this,this.tabSize)},insertOl:function(){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.i)(this)},removeLine:function(){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.j)(this)},insertUl:function(){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.k)(this)},unInsertTab:function(){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.l)(this,this.tabSize)},insertEnter:function(c){__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.m)(this,c)},saveHistory:function(){this.d_history.splice(this.d_history_index+1,this.d_history.length),this.d_history.push(this.d_value),this.textarea_selectionEnds.splice(this.d_history_index+1,this.textarea_selectionEnds.length),this.textarea_selectionEnds.push(this.textarea_selectionEnd),this.d_history_index=this.d_history.length-1},saveSelectionEndsHistory:function(){var c=this.$refs.vNoteTextarea&&this.$refs.vNoteTextarea.$el.querySelector("textarea");this.textarea_selectionEnd=c?c.selectionEnd:this.textarea_selectionEnd},initLanguage:function(){var c=__WEBPACK_IMPORTED_MODULE_9__lib_config_js__.a.langList.indexOf(this.language)>=0?this.language:"zh-CN",_=this;_.$render(__WEBPACK_IMPORTED_MODULE_9__lib_config_js__.a["help_"+c],function(i){_.d_help=i}),this.d_words=__WEBPACK_IMPORTED_MODULE_9__lib_config_js__.a["words_"+c]},editableTextarea:function(){var c=this.$refs.vNoteTextarea.$refs.vTextarea;this.editable?c.removeAttribute("disabled"):c.setAttribute("disabled","disabled")},codeStyleChange:function(c,_){if(_=_||!1,typeof this.p_external_link.hljs_css!="function")return void(this.p_external_link.hljs_css!=0&&console.error("external_link.hljs_css is not a function, if you want to disabled this error log, set external_link.hljs_css to function or false"));var i=this.p_external_link.hljs_css(c);i.length===0&&_&&(console.warn("hljs color scheme",c,"do not exist, loading default github"),i=this.p_external_link.hljs_css("github")),i.length>0?__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.c)(i,null,"md-code-style"):console.warn("hljs color scheme",c,"do not exist, hljs color scheme will not change")},iRender:function(c){var _=this;this.$render(_.d_value,function(i){_.d_render=i,c||_.change&&_.change(_.d_value,_.d_render),_.s_navigation&&__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_5__lib_core_extra_function_js__.e)(_,!1),_.$emit("input",_.d_value),_.d_value!==_.d_history[_.d_history_index]&&(window.clearTimeout(_.currentTimeout),_.currentTimeout=setTimeout(function(){_.saveHistory()},500))})},$emptyHistory:function(){this.d_history=[this.d_value],this.d_history_index=0}},watch:{d_value:function(c,_){this.saveSelectionEndsHistory(),this.iRender()},value:function(c,_){this.xssOptions&&(c=xss(c,this.xssOptions)),c!==this.d_value&&(this.d_value=c)},subfield:function(c,_){this.s_subfield=c},d_history_index:function(){this.d_history_index>20&&(this.d_history.shift(),this.d_history_index=this.d_history_index-1),this.d_value=this.d_history[this.d_history_index]},language:function(c){this.initLanguage()},editable:function(){this.editableTextarea()},defaultOpen:function(c){var _=c;return _||(_=this.subfield?"preview":"edit"),this.s_preview_switch=_==="preview"},codeStyle:function(c){this.codeStyleChange(c)}},components:{"v-autoTextarea":__WEBPACK_IMPORTED_MODULE_1_auto_textarea__.autoTextarea,"v-md-toolbar-left":__WEBPACK_IMPORTED_MODULE_12__components_md_toolbar_left_vue___default.a,"v-md-toolbar-right":__WEBPACK_IMPORTED_MODULE_13__components_md_toolbar_right_vue___default.a}}},function(c,_,i){var e=i(63),o={markdownIt:e.mixins[0].data().markdownIt,mavonEditor:e,LeftToolbar:i(36),RightToolbar:i(37),install:function(n){n.component("mavon-editor",e)}};c.exports=o},function(c,_,i){i.d(_,"a",function(){return U});var e=i(193),o=i.n(e),n=i(194),r=i.n(n),t=i(188),a=i.n(t),l=i(189),p=i.n(l),s=i(191),u=i.n(s),d=i(192),v=i.n(d),f=i(187),y=i.n(f),m=i(190),h=i.n(m),w=i(217),g=i.n(w),k=i(218),b=i.n(k),C=i(212),x=i.n(C),E=i(213),D=i.n(E),A=i(215),S=i.n(A),T=i(216),I=i.n(T),$=i(211),q=i.n($),M=i(214),z=i.n(M),U={"help_zh-CN":o.a,"help_zh-TW":r.a,"help_pt-BR":u.a,help_en:a.a,help_fr:p.a,help_ru:v.a,help_de:y.a,help_ja:h.a,"words_zh-CN":g.a,"words_zh-TW":b.a,"words_pt-BR":S.a,words_en:x.a,words_fr:D.a,words_ru:I.a,words_de:q.a,words_ja:z.a,langList:["en","zh-CN","zh-TW","fr","pt-BR","ru","de","ja"],toolbars:{bold:!0,italic:!0,header:!0,underline:!0,strikethrough:!0,mark:!0,superscript:!0,subscript:!0,quote:!0,ol:!0,ul:!0,link:!0,imagelink:!0,code:!0,table:!0,undo:!0,redo:!0,trash:!0,save:!0,alignleft:!0,aligncenter:!0,alignright:!0,navigation:!0,subfield:!0,fullscreen:!0,readmodel:!0,htmlcode:!0,help:!0,preview:!0}}},function(c,_,i){i(18)},function(c,_,i){_.a={agate:1,androidstudio:1,"arduino-light":1,arta:1,ascetic:1,"atelier-cave-dark":1,"atelier-cave-light":1,"atelier-dune-dark":1,"atelier-dune-light":1,"atelier-estuary-dark":1,"atelier-estuary-light":1,"atelier-forest-dark":1,"atelier-forest-light":1,"atelier-heath-dark":1,"atelier-heath-light":1,"atelier-lakeside-dark":1,"atelier-lakeside-light":1,"atelier-plateau-dark":1,"atelier-plateau-light":1,"atelier-savanna-dark":1,"atelier-savanna-light":1,"atelier-seaside-dark":1,"atelier-seaside-light":1,"atelier-sulphurpool-dark":1,"atelier-sulphurpool-light":1,"atom-one-dark":1,"atom-one-light":1,"brown-paper":1,"codepen-embed":1,"color-brewer":1,darcula:1,dark:1,darkula:1,default:1,docco:1,dracula:1,far:1,foundation:1,"github-gist":1,github:1,googlecode:1,grayscale:1,"gruvbox-dark":1,"gruvbox-light":1,hopscotch:1,hybrid:1,idea:1,"ir-black":1,"kimbie.dark":1,"kimbie.light":1,magula:1,"mono-blue":1,"monokai-sublime":1,monokai:1,obsidian:1,ocean:1,"paraiso-dark":1,"paraiso-light":1,pojoaque:1,purebasic:1,qtcreator_dark:1,qtcreator_light:1,railscasts:1,rainbow:1,routeros:1,"school-book":1,"solarized-dark":1,"solarized-light":1,sunburst:1,"tomorrow-night-blue":1,"tomorrow-night-bright":1,"tomorrow-night-eighties":1,"tomorrow-night":1,tomorrow:1,vs:1,vs2015:1,xcode:1,xt256:1,zenburn:1}},function(c,_,i){i.d(_,"a",function(){return o});var e={F8:119,F9:120,F10:121,F11:122,F12:123,B:66,I:73,H:72,U:85,D:68,M:77,Q:81,O:79,L:76,S:83,Z:90,Y:89,C:67,T:84,R:82,DELETE:8,TAB:9,ENTER:13,ONE:97,TWO:98,THREE:99,FOUR:100,FIVE:101,SIX:102,_ONE:49,_TWO:50,_THREE:51,_FOUR:52,_FIVE:53,_SIX:54},o=function(n){n.shortCut&&n.$el.addEventListener("keydown",function(r){if(r.ctrlKey||r.metaKey||r.altKey||r.shiftKey)if(!r.ctrlKey&&!r.metaKey||r.altKey||r.shiftKey){if((r.ctrlKey||r.metaKey)&&r.altKey&&!r.shiftKey)switch(r.keyCode){case e.S:r.preventDefault(),n.toolbar_left_click("superscript");break;case e.U:r.preventDefault(),n.toolbar_left_click("ul");break;case e.L:r.preventDefault(),n.toolbar_left_click("imagelink");break;case e.C:r.preventDefault(),n.toolbar_left_click("code");break;case e.T:r.preventDefault(),n.toolbar_left_click("table")}else if((r.ctrlKey||r.metaKey)&&r.shiftKey&&!r.altKey)switch(r.keyCode){case e.S:r.preventDefault(),n.toolbar_left_click("subscript");break;case e.D:r.preventDefault(),n.toolbar_left_click("strikethrough");break;case e.L:r.preventDefault(),n.toolbar_left_click("alignleft");break;case e.R:r.preventDefault(),n.toolbar_left_click("alignright");break;case e.C:r.preventDefault(),n.toolbar_left_click("aligncenter")}else if(!r.ctrlKey&&!r.metaKey&&r.shiftKey&&!r.altKey)switch(r.keyCode){case e.TAB:n.$refs.toolbar_left.s_img_link_open||(r.preventDefault(),n.unInsertTab())}}else switch(r.keyCode){case e.B:r.preventDefault(),n.toolbar_left_click("bold");break;case e.I:r.preventDefault(),n.toolbar_left_click("italic");break;case e.H:r.preventDefault(),n.toolbar_left_click("header");break;case e.U:r.preventDefault(),n.toolbar_left_click("underline");break;case e.D:r.preventDefault(),n.toolbar_left_click("removeLine");break;case e.M:r.preventDefault(),n.toolbar_left_click("mark");break;case e.Q:r.preventDefault(),n.toolbar_left_click("quote");break;case e.O:r.preventDefault(),n.toolbar_left_click("ol");break;case e.L:r.preventDefault(),n.toolbar_left_click("link");break;case e.S:r.preventDefault(),n.toolbar_left_click("save");break;case e.Z:r.preventDefault(),n.toolbar_left_click("undo");break;case e.Y:r.preventDefault(),n.toolbar_left_click("redo");break;case e.DELETE:r.preventDefault(),n.toolbar_left_click("trash");break;case e.ONE:r.preventDefault(),n.toolbar_left_click("header1");break;case e.TWO:r.preventDefault(),n.toolbar_left_click("header2");break;case e.THREE:r.preventDefault(),n.toolbar_left_click("header3");break;case e.FOUR:r.preventDefault(),n.toolbar_left_click("header4");break;case e.FIVE:r.preventDefault(),n.toolbar_left_click("header5");break;case e.SIX:r.preventDefault(),n.toolbar_left_click("header6");break;case e._ONE:r.preventDefault(),n.toolbar_left_click("header1");break;case e._TWO:r.preventDefault(),n.toolbar_left_click("header2");break;case e._THREE:r.preventDefault(),n.toolbar_left_click("header3");break;case e._FOUR:r.preventDefault(),n.toolbar_left_click("header4");break;case e._FIVE:r.preventDefault(),n.toolbar_left_click("header5");break;case e._SIX:r.preventDefault(),n.toolbar_left_click("header6")}else switch(r.keyCode){case e.F8:n.toolbars.navigation&&(r.preventDefault(),n.toolbar_right_click("navigation"));break;case e.F9:n.toolbars.preview&&(r.preventDefault(),n.toolbar_right_click("preview"));break;case e.F10:n.toolbars.fullscreen&&(r.preventDefault(),n.toolbar_right_click("fullscreen"));break;case e.F11:n.toolbars.readmodel&&(r.preventDefault(),n.toolbar_right_click("read"));break;case e.F12:n.toolbars.subfield&&(r.preventDefault(),n.toolbar_right_click("subfield"));break;case e.TAB:n.$refs.toolbar_left.s_img_link_open||(r.preventDefault(),n.insertTab());break;case e.ENTER:n.$refs.toolbar_left.s_img_link_open?(r.preventDefault(),n.$refs.toolbar_left.$imgLinkAdd()):n.insertEnter(r)}})}},function(c,_,i){var e=i(38),o=i(18),n={html:!0,xhtmlOut:!0,breaks:!0,langPrefix:"lang-",linkify:!1,typographer:!0,quotes:"\u201C\u201D\u2018\u2019"},r=i(136)(n),t=i(120),a=i(132),l=i(133),p=i(119),s=i(117),u=i(126),d=i(129),v=i(131),f=i(134),y=i(118),m=i(135),h=r.renderer.rules.link_open||function(E,D,A,S,T){return T.renderToken(E,D,A)};r.renderer.rules.link_open=function(E,D,A,S,T){var I=E[D].attrIndex("href");if(E[D].attrs[I][1].startsWith("#"))return h(E,D,A,S,T);var $=E[D].attrIndex("target");return $<0?E[D].attrPush(["target","_blank"]):E[D].attrs[$][1]="_blank",h(E,D,A,S,T)};var w=i(127),g=i(130),k=i(128),b={},C=[],x={hljs:"auto",highlighted:!0,langCheck:function(E){E&&e.a[E]&&!b[E]&&(b[E]=1,C.push(e.a[E]))}};r.use(w,x).use(t).use(l).use(a).use(y).use(y,"hljs-left").use(y,"hljs-center").use(y,"hljs-right").use(p).use(s).use(u).use(d).use(v).use(y).use(k).use(g).use(f).use(m),_.a={data:function(){return{markdownIt:r}},mounted:function(){x.highlighted=this.ishljs},methods:{$render:function(E,D){var A=this;b={},C=[];var S=r.render(E);this.ishljs&&C.length>0&&A.$_render(E,D,S),D(S)},$_render:function(E,D,A){for(var S=this,T=0,I=0;I<C.length;I++){var $=S.p_external_link.hljs_lang(C[I]);i.i(o.d)($,function(){(T+=1)===C.length&&(A=r.render(E),D(A))})}}},watch:{ishljs:function(E){x.highlighted=E}}}},function(c,_,i){function e(u){u.d_history_index>0&&u.d_history_index--,u.$nextTick(function(){var d=u.textarea_selectionEnds[u.d_history_index];u.getTextareaDom().selectionStart=d,u.getTextareaDom().selectionEnd=d}),u.getTextareaDom().focus()}function o(u){u.d_history_index<u.d_history.length-1&&u.d_history_index++,u.$nextTick(function(){var d=u.textarea_selectionEnds[u.d_history_index];u.getTextareaDom().selectionStart=d,u.getTextareaDom().selectionEnd=d}),u.getTextareaDom().focus()}function n(u){u.d_value="",u.getTextareaDom().focus()}function r(u){u.save(u.d_value,u.d_render)}function t(u){u.insertOl()}function a(u){u.insertUl()}function l(u){u.removeLine()}i.d(_,"b",function(){return p}),i.d(_,"a",function(){return s});var p=function(u,d,v,f){var y={prefix:u==="link"?"["+d+"](":"!["+d+"](",subfix:")",str:v};f.insertText(f.getTextareaDom(),y)},s=function(u,d){var v={bold:{prefix:"**",subfix:"**",str:d.d_words.tl_bold},italic:{prefix:"*",subfix:"*",str:d.d_words.tl_italic},header:{prefix:"# ",subfix:"",str:d.d_words.tl_header},header1:{prefix:"# ",subfix:"",str:d.d_words.tl_header_one},header2:{prefix:"## ",subfix:"",str:d.d_words.tl_header_two},header3:{prefix:"### ",subfix:"",str:d.d_words.tl_header_three},header4:{prefix:"#### ",subfix:"",str:d.d_words.tl_header_four},header5:{prefix:"##### ",subfix:"",str:d.d_words.tl_header_five},header6:{prefix:"###### ",subfix:"",str:d.d_words.tl_header_six},underline:{prefix:"++",subfix:"++",str:d.d_words.tl_underline},strikethrough:{prefix:"~~",subfix:"~~",str:d.d_words.tl_strikethrough},mark:{prefix:"==",subfix:"==",str:d.d_words.tl_mark},superscript:{prefix:"^",subfix:"^",str:d.d_words.tl_superscript},subscript:{prefix:"~",subfix:"~",str:d.d_words.tl_subscript},quote:{prefix:"> ",subfix:"",str:d.d_words.tl_quote},link:{prefix:"[](",subfix:")",str:d.d_words.tl_link},imagelink:{prefix:"![](",subfix:")",str:d.d_words.tl_image},code:{prefix:"```\n",subfix:"\n\n```\n",str:"language"},table:{prefix:"",subfix:"",str:`|column1|column2|column3|
|-|-|-|
|content1|content2|content3|
`},aligncenter:{prefix:`::: hljs-center

`,subfix:`

:::
`,str:d.d_words.tl_aligncenter},alignright:{prefix:`::: hljs-right

`,subfix:`

:::
`,str:d.d_words.tl_alignright},alignleft:{prefix:`::: hljs-left

`,subfix:`

:::
`,str:d.d_words.tl_alignleft}};v.hasOwnProperty(u)&&d.insertText(d.getTextareaDom(),v[u]);var f={undo:e,redo:o,trash:n,save:r,ol:t,ul:a,removeLine:l};f.hasOwnProperty(u)&&f[u](d)}},function(c,_,i){function e(s){s.s_html_code=!s.s_html_code,s.htmlcode&&s.htmlcode(s.s_html_code,s.d_value)}function o(s){s.s_help=!s.s_help,s.helptoggle&&s.helptoggle(s.s_help,s.d_value)}function n(s){var u=s.$refs.vReadModel;u.requestFullscreen?u.requestFullscreen():u.mozRequestFullScreen?u.mozRequestFullScreen():u.webkitRequestFullscreen?u.webkitRequestFullscreen():u.msRequestFullscreen&&u.msRequestFullscreen()}function r(s){s.s_preview_switch=!s.s_preview_switch,s.previewtoggle&&s.previewtoggle(s.s_preview_switch,s.d_value)}function t(s){s.s_fullScreen=!s.s_fullScreen,s.fullscreen&&s.fullscreen(s.s_fullScreen,s.d_value)}function a(s){s.s_subfield=!s.s_subfield,s.s_preview_switch=s.s_subfield,s.previewtoggle&&s.previewtoggle(s.s_preview_switch,s.d_value),s.subfieldtoggle&&s.subfieldtoggle(s.s_subfield,s.d_value)}function l(s){s.s_navigation=!s.s_navigation,s.s_navigation&&(s.s_preview_switch=!0),s.navigationtoggle&&s.navigationtoggle(s.s_navigation,s.d_value),s.s_navigation&&s.getNavigation(s,!1)}i.d(_,"a",function(){return p});var p=function(s,u){var d={help:o,html:e,read:n,preview:r,fullscreen:t,navigation:l,subfield:a};d.hasOwnProperty(s)&&d[s](u)}},function(c,_,i){function e(n){n&&(n.preventDefault&&n.preventDefault(),n.stopPropagation&&n.stopPropagation())}_.a=e;var o=i(39);i.n(o)},function(c,_,i){c.exports={default:i(80),__esModule:!0}},function(c,_,i){c.exports={default:i(81),__esModule:!0}},function(c,_,i){i(103),i(101),i(104),i(105),c.exports=i(10).Symbol},function(c,_,i){i(102),i(106),c.exports=i(30).f("iterator")},function(c,_){c.exports=function(i){if(typeof i!="function")throw TypeError(i+" is not a function!");return i}},function(c,_){c.exports=function(){}},function(c,_,i){var e=i(6),o=i(99),n=i(98);c.exports=function(r){return function(t,a,l){var p,s=e(t),u=o(s.length),d=n(l,u);if(r&&a!=a){for(;u>d;)if((p=s[d++])!=p)return!0}else for(;u>d;d++)if((r||d in s)&&s[d]===a)return r||d||0;return!r&&-1}}},function(c,_,i){var e=i(82);c.exports=function(o,n,r){if(e(o),n===void 0)return o;switch(r){case 1:return function(t){return o.call(n,t)};case 2:return function(t,a){return o.call(n,t,a)};case 3:return function(t,a,l){return o.call(n,t,a,l)}}return function(){return o.apply(n,arguments)}}},function(c,_,i){var e=i(22),o=i(47),n=i(23);c.exports=function(r){var t=e(r),a=o.f;if(a)for(var l,p=a(r),s=n.f,u=0;p.length>u;)s.call(r,l=p[u++])&&t.push(l);return t}},function(c,_,i){var e=i(1).document;c.exports=e&&e.documentElement},function(c,_,i){var e=i(40);c.exports=Object("z").propertyIsEnumerable(0)?Object:function(o){return e(o)=="String"?o.split(""):Object(o)}},function(c,_,i){var e=i(40);c.exports=Array.isArray||function(o){return e(o)=="Array"}},function(c,_,i){var e=i(45),o=i(13),n=i(24),r={};i(4)(r,i(7)("iterator"),function(){return this}),c.exports=function(t,a,l){t.prototype=e(r,{next:o(1,l)}),n(t,a+" Iterator")}},function(c,_){c.exports=function(i,e){return{value:e,done:!!i}}},function(c,_,i){var e=i(14)("meta"),o=i(8),n=i(2),r=i(5).f,t=0,a=Object.isExtensible||function(){return!0},l=!i(11)(function(){return a(Object.preventExtensions({}))}),p=function(f){r(f,e,{value:{i:"O"+ ++t,w:{}}})},s=function(f,y){if(!o(f))return typeof f=="symbol"?f:(typeof f=="string"?"S":"P")+f;if(!n(f,e)){if(!a(f))return"F";if(!y)return"E";p(f)}return f[e].i},u=function(f,y){if(!n(f,e)){if(!a(f))return!0;if(!y)return!1;p(f)}return f[e].w},d=function(f){return l&&v.NEED&&a(f)&&!n(f,e)&&p(f),f},v=c.exports={KEY:e,NEED:!1,fastKey:s,getWeak:u,onFreeze:d}},function(c,_,i){var e=i(5),o=i(9),n=i(22);c.exports=i(3)?Object.defineProperties:function(r,t){o(r);for(var a,l=n(t),p=l.length,s=0;p>s;)e.f(r,a=l[s++],t[a]);return r}},function(c,_,i){var e=i(23),o=i(13),n=i(6),r=i(28),t=i(2),a=i(43),l=Object.getOwnPropertyDescriptor;_.f=i(3)?l:function(p,s){if(p=n(p),s=r(s,!0),a)try{return l(p,s)}catch{}if(t(p,s))return o(!e.f.call(p,s),p[s])}},function(c,_,i){var e=i(6),o=i(46).f,n={}.toString,r=typeof window=="object"&&window&&Object.getOwnPropertyNames?Object.getOwnPropertyNames(window):[],t=function(a){try{return o(a)}catch{return r.slice()}};c.exports.f=function(a){return r&&n.call(a)=="[object Window]"?t(a):o(e(a))}},function(c,_,i){var e=i(2),o=i(50),n=i(25)("IE_PROTO"),r=Object.prototype;c.exports=Object.getPrototypeOf||function(t){return t=o(t),e(t,n)?t[n]:typeof t.constructor=="function"&&t instanceof t.constructor?t.constructor.prototype:t instanceof Object?r:null}},function(c,_,i){var e=i(27),o=i(19);c.exports=function(n){return function(r,t){var a,l,p=String(o(r)),s=e(t),u=p.length;return s<0||s>=u?n?"":void 0:(a=p.charCodeAt(s),a<55296||a>56319||s+1===u||(l=p.charCodeAt(s+1))<56320||l>57343?n?p.charAt(s):a:n?p.slice(s,s+2):l-56320+(a-55296<<10)+65536)}}},function(c,_,i){var e=i(27),o=Math.max,n=Math.min;c.exports=function(r,t){return r=e(r),r<0?o(r+t,0):n(r,t)}},function(c,_,i){var e=i(27),o=Math.min;c.exports=function(n){return n>0?o(e(n),9007199254740991):0}},function(c,_,i){var e=i(83),o=i(91),n=i(21),r=i(6);c.exports=i(44)(Array,"Array",function(t,a){this._t=r(t),this._i=0,this._k=a},function(){var t=this._t,a=this._k,l=this._i++;return!t||l>=t.length?(this._t=void 0,o(1)):a=="keys"?o(0,l):a=="values"?o(0,t[l]):o(0,[l,t[l]])},"values"),n.Arguments=n.Array,e("keys"),e("values"),e("entries")},function(c,_){},function(c,_,i){var e=i(97)(!0);i(44)(String,"String",function(o){this._t=String(o),this._i=0},function(){var o,n=this._t,r=this._i;return r>=n.length?{value:void 0,done:!0}:(o=e(n,r),this._i+=o.length,{value:o,done:!1})})},function(c,_,i){var e=i(1),o=i(2),n=i(3),r=i(42),t=i(49),a=i(92).KEY,l=i(11),p=i(26),s=i(24),u=i(14),d=i(7),v=i(30),f=i(29),y=i(86),m=i(89),h=i(9),w=i(8),g=i(50),k=i(6),b=i(28),C=i(13),x=i(45),E=i(95),D=i(94),A=i(47),S=i(5),T=i(22),I=D.f,$=S.f,q=E.f,M=e.Symbol,z=e.JSON,U=z&&z.stringify,L=d("_hidden"),P=d("toPrimitive"),j={}.propertyIsEnumerable,O=p("symbol-registry"),B=p("symbols"),H=p("op-symbols"),N=Object.prototype,K=typeof M=="function"&&!!A.f,W=e.QObject,G=!W||!W.prototype||!W.prototype.findChild,Y=n&&l(function(){return x($({},"a",{get:function(){return $(this,"a",{value:7}).a}})).a!=7})?function(F,R,V){var X=I(N,R);X&&delete N[R],$(F,R,V),X&&F!==N&&$(N,R,X)}:$,J=function(F){var R=B[F]=x(M.prototype);return R._k=F,R},Q=K&&typeof M.iterator=="symbol"?function(F){return typeof F=="symbol"}:function(F){return F instanceof M},Z=function(F,R,V){return F===N&&Z(H,R,V),h(F),R=b(R,!0),h(V),o(B,R)?(V.enumerable?(o(F,L)&&F[L][R]&&(F[L][R]=!1),V=x(V,{enumerable:C(0,!1)})):(o(F,L)||$(F,L,C(1,{})),F[L][R]=!0),Y(F,R,V)):$(F,R,V)},te=function(F,R){h(F);for(var V,X=y(R=k(R)),ee=0,oe=X.length;oe>ee;)Z(F,V=X[ee++],R[V]);return F},ne=function(F,R){return R===void 0?x(F):te(x(F),R)},re=function(F){var R=j.call(this,F=b(F,!0));return!(this===N&&o(B,F)&&!o(H,F))&&(!(R||!o(this,F)||!o(B,F)||o(this,L)&&this[L][F])||R)},ae=function(F,R){if(F=k(F),R=b(R,!0),F!==N||!o(B,R)||o(H,R)){var V=I(F,R);return!V||!o(B,R)||o(F,L)&&F[L][R]||(V.enumerable=!0),V}},se=function(F){for(var R,V=q(k(F)),X=[],ee=0;V.length>ee;)o(B,R=V[ee++])||R==L||R==a||X.push(R);return X},le=function(F){for(var R,V=F===N,X=q(V?H:k(F)),ee=[],oe=0;X.length>oe;)!o(B,R=X[oe++])||V&&!o(N,R)||ee.push(B[R]);return ee};K||(M=function(){if(this instanceof M)throw TypeError("Symbol is not a constructor!");var F=u(arguments.length>0?arguments[0]:void 0),R=function(V){this===N&&R.call(H,V),o(this,L)&&o(this[L],F)&&(this[L][F]=!1),Y(this,F,C(1,V))};return n&&G&&Y(N,F,{configurable:!0,set:R}),J(F)},t(M.prototype,"toString",function(){return this._k}),D.f=ae,S.f=Z,i(46).f=E.f=se,i(23).f=re,A.f=le,n&&!i(12)&&t(N,"propertyIsEnumerable",re,!0),v.f=function(F){return J(d(F))}),r(r.G+r.W+r.F*!K,{Symbol:M});for(var ce="hasInstance,isConcatSpreadable,iterator,match,replace,search,species,split,toPrimitive,toStringTag,unscopables".split(","),ue=0;ce.length>ue;)d(ce[ue++]);for(var pe=T(d.store),de=0;pe.length>de;)f(pe[de++]);r(r.S+r.F*!K,"Symbol",{for:function(F){return o(O,F+="")?O[F]:O[F]=M(F)},keyFor:function(F){if(!Q(F))throw TypeError(F+" is not a symbol!");for(var R in O)if(O[R]===F)return R},useSetter:function(){G=!0},useSimple:function(){G=!1}}),r(r.S+r.F*!K,"Object",{create:ne,defineProperty:Z,defineProperties:te,getOwnPropertyDescriptor:ae,getOwnPropertyNames:se,getOwnPropertySymbols:le});var _e=l(function(){A.f(1)});r(r.S+r.F*_e,"Object",{getOwnPropertySymbols:function(F){return A.f(g(F))}}),z&&r(r.S+r.F*(!K||l(function(){var F=M();return U([F])!="[null]"||U({a:F})!="{}"||U(Object(F))!="{}"})),"JSON",{stringify:function(F){for(var R,V,X=[F],ee=1;arguments.length>ee;)X.push(arguments[ee++]);if(V=R=X[1],(w(R)||F!==void 0)&&!Q(F))return m(R)||(R=function(oe,ie){if(typeof V=="function"&&(ie=V.call(this,oe,ie)),!Q(ie))return ie}),X[1]=R,U.apply(z,X)}}),M.prototype[P]||i(4)(M.prototype,P,M.prototype.valueOf),s(M,"Symbol"),s(Math,"Math",!0),s(e.JSON,"JSON",!0)},function(c,_,i){i(29)("asyncIterator")},function(c,_,i){i(29)("observable")},function(c,_,i){i(100);for(var e=i(1),o=i(4),n=i(21),r=i(7)("toStringTag"),t="CSSRuleList,CSSStyleDeclaration,CSSValueList,ClientRectList,DOMRectList,DOMStringList,DOMTokenList,DataTransferItemList,FileList,HTMLAllCollection,HTMLCollection,HTMLFormElement,HTMLSelectElement,MediaList,MimeTypeArray,NamedNodeMap,NodeList,PaintRequestList,Plugin,PluginArray,SVGLengthList,SVGNumberList,SVGPathSegList,SVGPointList,SVGStringList,SVGTransformList,SourceBufferList,StyleSheetList,TextTrackCueList,TextTrackList,TouchList".split(","),a=0;a<t.length;a++){var l=t[a],p=e[l],s=p&&p.prototype;s&&!s[r]&&o(s,r,l),n[l]=n.Array}},function(c,_,i){_=c.exports=i(15)(!1),_.push([c.i,`
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
`,""])},function(c,_,i){_=c.exports=i(15)(!1),_.push([c.i,`
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
`,""])},function(c,_,i){_=c.exports=i(15)(!1),_.push([c.i,`
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
`,""])},function(c,_,i){_=c.exports=i(15)(!1),_.push([c.i,`
.auto-textarea-wrapper[data-v-7a63e4b3] {
    height: 100%;
}
`,""])},function(c,_,i){function e(a){return a==null}function o(a){var l={};for(var p in a)l[p]=a[p];return l}function n(a){a=o(a||{}),a.whiteList=a.whiteList||r.whiteList,a.onAttr=a.onAttr||r.onAttr,a.onIgnoreAttr=a.onIgnoreAttr||r.onIgnoreAttr,a.safeAttrValue=a.safeAttrValue||r.safeAttrValue,this.options=a}var r=i(51),t=i(112);i(52),n.prototype.process=function(a){if(a=a||"",!(a=a.toString()))return"";var l=this,p=l.options,s=p.whiteList,u=p.onAttr,d=p.onIgnoreAttr,v=p.safeAttrValue;return t(a,function(f,y,m,h,w){var g=s[m],k=!1;if(g===!0?k=g:typeof g=="function"?k=g(h):g instanceof RegExp&&(k=g.test(h)),k!==!0&&(k=!1),h=v(m,h)){var b={position:y,sourcePosition:f,source:w,isWhite:k};if(k){var C=u(m,h,b);return e(C)?m+":"+h:C}var C=d(m,h,b);return e(C)?void 0:C}})},c.exports=n},function(c,_,i){function e(n,r){function t(){if(!l){var f=o.trim(n.slice(p,s)),y=f.indexOf(":");if(y!==-1){var m=o.trim(f.slice(0,y)),h=o.trim(f.slice(y+1));if(m){var w=r(p,u.length,m,h,f);w&&(u+=w+"; ")}}}p=s+1}n=o.trimRight(n),n[n.length-1]!==";"&&(n+=";");for(var a=n.length,l=!1,p=0,s=0,u="";s<a;s++){var d=n[s];if(d==="/"&&n[s+1]==="*"){var v=n.indexOf("*/",s+2);if(v===-1)break;s=v+1,p=s+1,l=!1}else d==="("?l=!0:d===")"?l=!1:d===";"?l||t():d===`
`&&t()}return o.trim(u)}var o=i(52);c.exports=e},function(c,_){},function(c,_){},function(c,_,i){function e(b){return Array.prototype.slice.call(arguments,1).forEach(function(C){C&&Object.keys(C).forEach(function(x){b[x]=C[x]})}),b}function o(b){return Object.prototype.toString.call(b)}function n(b){return o(b)==="[object String]"}function r(b){return o(b)==="[object Object]"}function t(b){return o(b)==="[object RegExp]"}function a(b){return o(b)==="[object Function]"}function l(b){return b.replace(/[.?*+^$[\]\\(){}|-]/g,"\\$&")}function p(b){return Object.keys(b||{}).reduce(function(C,x){return C||h.hasOwnProperty(x)},!1)}function s(b){b.__index__=-1,b.__text_cache__=""}function u(b){return function(C,x){var E=C.slice(x);return b.test(E)?E.match(b)[0].length:0}}function d(){return function(b,C){C.normalize(b)}}function v(b){function C(T){return T.replace("%TLDS%",E.src_tlds)}function x(T,I){throw new Error('(LinkifyIt) Invalid schema "'+T+'": '+I)}var E=b.re=i(116)(b.__opts__),D=b.__tlds__.slice();b.onCompile(),b.__tlds_replaced__||D.push(g),D.push(E.src_xn),E.src_tlds=D.join("|"),E.email_fuzzy=RegExp(C(E.tpl_email_fuzzy),"i"),E.link_fuzzy=RegExp(C(E.tpl_link_fuzzy),"i"),E.link_no_ip_fuzzy=RegExp(C(E.tpl_link_no_ip_fuzzy),"i"),E.host_fuzzy_test=RegExp(C(E.tpl_host_fuzzy_test),"i");var A=[];b.__compiled__={},Object.keys(b.__schemas__).forEach(function(T){var I=b.__schemas__[T];if(I!==null){var $={validate:null,link:null};return b.__compiled__[T]=$,r(I)?(t(I.validate)?$.validate=u(I.validate):a(I.validate)?$.validate=I.validate:x(T,I),void(a(I.normalize)?$.normalize=I.normalize:I.normalize?x(T,I):$.normalize=d())):n(I)?void A.push(T):void x(T,I)}}),A.forEach(function(T){b.__compiled__[b.__schemas__[T]]&&(b.__compiled__[T].validate=b.__compiled__[b.__schemas__[T]].validate,b.__compiled__[T].normalize=b.__compiled__[b.__schemas__[T]].normalize)}),b.__compiled__[""]={validate:null,normalize:d()};var S=Object.keys(b.__compiled__).filter(function(T){return T.length>0&&b.__compiled__[T]}).map(l).join("|");b.re.schema_test=RegExp("(^|(?!_)(?:[><\uFF5C]|"+E.src_ZPCc+"))("+S+")","i"),b.re.schema_search=RegExp("(^|(?!_)(?:[><\uFF5C]|"+E.src_ZPCc+"))("+S+")","ig"),b.re.pretest=RegExp("("+b.re.schema_test.source+")|("+b.re.host_fuzzy_test.source+")|@","i"),s(b)}function f(b,C){var x=b.__index__,E=b.__last_index__,D=b.__text_cache__.slice(x,E);this.schema=b.__schema__.toLowerCase(),this.index=x+C,this.lastIndex=E+C,this.raw=D,this.text=D,this.url=D}function y(b,C){var x=new f(b,C);return b.__compiled__[x.schema].normalize(x,b),x}function m(b,C){if(!(this instanceof m))return new m(b,C);C||p(b)&&(C=b,b={}),this.__opts__=e({},h,C),this.__index__=-1,this.__last_index__=-1,this.__schema__="",this.__text_cache__="",this.__schemas__=e({},w,b),this.__compiled__={},this.__tlds__=k,this.__tlds_replaced__=!1,this.re={},v(this)}var h={fuzzyLink:!0,fuzzyEmail:!0,fuzzyIP:!1},w={"http:":{validate:function(b,C,x){var E=b.slice(C);return x.re.http||(x.re.http=new RegExp("^\\/\\/"+x.re.src_auth+x.re.src_host_port_strict+x.re.src_path,"i")),x.re.http.test(E)?E.match(x.re.http)[0].length:0}},"https:":"http:","ftp:":"http:","//":{validate:function(b,C,x){var E=b.slice(C);return x.re.no_http||(x.re.no_http=new RegExp("^"+x.re.src_auth+"(?:localhost|(?:(?:"+x.re.src_domain+")\\.)+"+x.re.src_domain_root+")"+x.re.src_port+x.re.src_host_terminator+x.re.src_path,"i")),x.re.no_http.test(E)?C>=3&&b[C-3]===":"||C>=3&&b[C-3]==="/"?0:E.match(x.re.no_http)[0].length:0}},"mailto:":{validate:function(b,C,x){var E=b.slice(C);return x.re.mailto||(x.re.mailto=new RegExp("^"+x.re.src_email_name+"@"+x.re.src_host_strict,"i")),x.re.mailto.test(E)?E.match(x.re.mailto)[0].length:0}}},g="a[cdefgilmnoqrstuwxz]|b[abdefghijmnorstvwyz]|c[acdfghiklmnoruvwxyz]|d[ejkmoz]|e[cegrstu]|f[ijkmor]|g[abdefghilmnpqrstuwy]|h[kmnrtu]|i[delmnoqrst]|j[emop]|k[eghimnprwyz]|l[abcikrstuvy]|m[acdeghklmnopqrstuvwxyz]|n[acefgilopruz]|om|p[aefghklmnrstwy]|qa|r[eosuw]|s[abcdeghijklmnortuvxyz]|t[cdfghjklmnortvwz]|u[agksyz]|v[aceginu]|w[fs]|y[et]|z[amw]",k="biz|com|edu|gov|net|org|pro|web|xxx|aero|asia|coop|info|museum|name|shop|\u0440\u0444".split("|");m.prototype.add=function(b,C){return this.__schemas__[b]=C,v(this),this},m.prototype.set=function(b){return this.__opts__=e(this.__opts__,b),this},m.prototype.test=function(b){if(this.__text_cache__=b,this.__index__=-1,!b.length)return!1;var C,x,E,D,A,S,T,I;if(this.re.schema_test.test(b)){for(T=this.re.schema_search,T.lastIndex=0;(C=T.exec(b))!==null;)if(D=this.testSchemaAt(b,C[2],T.lastIndex)){this.__schema__=C[2],this.__index__=C.index+C[1].length,this.__last_index__=C.index+C[0].length+D;break}}return this.__opts__.fuzzyLink&&this.__compiled__["http:"]&&(I=b.search(this.re.host_fuzzy_test))>=0&&(this.__index__<0||I<this.__index__)&&(x=b.match(this.__opts__.fuzzyIP?this.re.link_fuzzy:this.re.link_no_ip_fuzzy))!==null&&(A=x.index+x[1].length,(this.__index__<0||A<this.__index__)&&(this.__schema__="",this.__index__=A,this.__last_index__=x.index+x[0].length)),this.__opts__.fuzzyEmail&&this.__compiled__["mailto:"]&&b.indexOf("@")>=0&&(E=b.match(this.re.email_fuzzy))!==null&&(A=E.index+E[1].length,S=E.index+E[0].length,(this.__index__<0||A<this.__index__||A===this.__index__&&S>this.__last_index__)&&(this.__schema__="mailto:",this.__index__=A,this.__last_index__=S)),this.__index__>=0},m.prototype.pretest=function(b){return this.re.pretest.test(b)},m.prototype.testSchemaAt=function(b,C,x){return this.__compiled__[C.toLowerCase()]?this.__compiled__[C.toLowerCase()].validate(b,x,this):0},m.prototype.match=function(b){var C=0,x=[];this.__index__>=0&&this.__text_cache__===b&&(x.push(y(this,C)),C=this.__last_index__);for(var E=C?b.slice(C):b;this.test(E);)x.push(y(this,C)),E=E.slice(this.__last_index__),C+=this.__last_index__;return x.length?x:null},m.prototype.tlds=function(b,C){return b=Array.isArray(b)?b:[b],C?(this.__tlds__=this.__tlds__.concat(b).sort().filter(function(x,E,D){return x!==D[E-1]}).reverse(),v(this),this):(this.__tlds__=b.slice(),this.__tlds_replaced__=!0,v(this),this)},m.prototype.normalize=function(b){b.schema||(b.url="http://"+b.url),b.schema!=="mailto:"||/^mailto:/i.test(b.url)||(b.url="mailto:"+b.url)},m.prototype.onCompile=function(){},c.exports=m},function(c,_,i){c.exports=function(e){var o={};return o.src_Any=i(60).source,o.src_Cc=i(58).source,o.src_Z=i(59).source,o.src_P=i(34).source,o.src_ZPCc=[o.src_Z,o.src_P,o.src_Cc].join("|"),o.src_ZCc=[o.src_Z,o.src_Cc].join("|"),o.src_pseudo_letter="(?:(?![><\uFF5C]|"+o.src_ZPCc+")"+o.src_Any+")",o.src_ip4="(?:(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)",o.src_auth="(?:(?:(?!"+o.src_ZCc+"|[@/\\[\\]()]).)+@)?",o.src_port="(?::(?:6(?:[0-4]\\d{3}|5(?:[0-4]\\d{2}|5(?:[0-2]\\d|3[0-5])))|[1-5]?\\d{1,4}))?",o.src_host_terminator="(?=$|[><\uFF5C]|"+o.src_ZPCc+")(?!-|_|:\\d|\\.-|\\.(?!$|"+o.src_ZPCc+"))",o.src_path="(?:[/?#](?:(?!"+o.src_ZCc+`|[><\uFF5C]|[()[\\]{}.,"'?!\\-]).|\\[(?:(?!`+o.src_ZCc+"|\\]).)*\\]|\\((?:(?!"+o.src_ZCc+"|[)]).)*\\)|\\{(?:(?!"+o.src_ZCc+'|[}]).)*\\}|\\"(?:(?!'+o.src_ZCc+`|["]).)+\\"|\\'(?:(?!`+o.src_ZCc+"|[']).)+\\'|\\'(?="+o.src_pseudo_letter+"|[-]).|\\.{2,4}[a-zA-Z0-9%/]|\\.(?!"+o.src_ZCc+"|[.]).|"+(e&&e["---"]?"\\-(?!--(?:[^-]|$))(?:-*)|":"\\-+|")+"\\,(?!"+o.src_ZCc+").|\\!(?!"+o.src_ZCc+"|[!]).|\\?(?!"+o.src_ZCc+"|[?]).)+|\\/)?",o.src_email_name='[\\-;:&=\\+\\$,\\.a-zA-Z0-9_][\\-;:&=\\+\\$,\\"\\.a-zA-Z0-9_]*',o.src_xn="xn--[a-z0-9\\-]{1,59}",o.src_domain_root="(?:"+o.src_xn+"|"+o.src_pseudo_letter+"{1,63})",o.src_domain="(?:"+o.src_xn+"|(?:"+o.src_pseudo_letter+")|(?:"+o.src_pseudo_letter+"(?:-|"+o.src_pseudo_letter+"){0,61}"+o.src_pseudo_letter+"))",o.src_host="(?:(?:(?:(?:"+o.src_domain+")\\.)*"+o.src_domain+"))",o.tpl_host_fuzzy="(?:"+o.src_ip4+"|(?:(?:(?:"+o.src_domain+")\\.)+(?:%TLDS%)))",o.tpl_host_no_ip_fuzzy="(?:(?:(?:"+o.src_domain+")\\.)+(?:%TLDS%))",o.src_host_strict=o.src_host+o.src_host_terminator,o.tpl_host_fuzzy_strict=o.tpl_host_fuzzy+o.src_host_terminator,o.src_host_port_strict=o.src_host+o.src_port+o.src_host_terminator,o.tpl_host_port_fuzzy_strict=o.tpl_host_fuzzy+o.src_port+o.src_host_terminator,o.tpl_host_port_no_ip_fuzzy_strict=o.tpl_host_no_ip_fuzzy+o.src_port+o.src_host_terminator,o.tpl_host_fuzzy_test="localhost|www\\.|\\.\\d{1,3}\\.|(?:\\.(?:%TLDS%)(?:"+o.src_ZPCc+"|>|$))",o.tpl_email_fuzzy='(^|[><\uFF5C]|"|\\(|'+o.src_ZCc+")("+o.src_email_name+"@"+o.tpl_host_fuzzy_strict+")",o.tpl_link_fuzzy="(^|(?![.:/\\-_@])(?:[$+<=>^`|\uFF5C]|"+o.src_ZPCc+"))((?![$+<=>^`|\uFF5C])"+o.tpl_host_port_fuzzy_strict+o.src_path+")",o.tpl_link_no_ip_fuzzy="(^|(?![.:/\\-_@])(?:[$+<=>^`|\uFF5C]|"+o.src_ZPCc+"))((?![$+<=>^`|\uFF5C])"+o.tpl_host_port_no_ip_fuzzy_strict+o.src_path+")",o}},function(c,_,i){c.exports=function(e){function o(s,u,d,v){var f,y,m,h,w,g=s.bMarks[u]+s.tShift[u],k=s.eMarks[u];if(g+2>=k||s.src.charCodeAt(g++)!==42||s.src.charCodeAt(g++)!==91)return!1;for(h=g;g<k;g++){if((m=s.src.charCodeAt(g))===91)return!1;if(m===93){w=g;break}m===92&&g++}return!(w<0||s.src.charCodeAt(w+1)!==58)&&(!!v||(f=s.src.slice(h,w).replace(/\\(.)/g,"$1"),y=s.src.slice(w+2,k).trim(),f.length!==0&&y.length!==0&&(s.env.abbreviations||(s.env.abbreviations={}),s.env.abbreviations[":"+f]===void 0&&(s.env.abbreviations[":"+f]=y),s.line=u+1,!0)))}function n(s){var u,d,v,f,y,m,h,w,g,k,b,C,x,E=s.tokens;if(s.env.abbreviations){for(C=new RegExp("(?:"+Object.keys(s.env.abbreviations).map(function(D){return D.substr(1)}).sort(function(D,A){return A.length-D.length}).map(r).join("|")+")"),b="(^|"+l+"|"+p+"|["+a.split("").map(r).join("")+"])("+Object.keys(s.env.abbreviations).map(function(D){return D.substr(1)}).sort(function(D,A){return A.length-D.length}).map(r).join("|")+")($|"+l+"|"+p+"|["+a.split("").map(r).join("")+"])",g=new RegExp(b,"g"),d=0,v=E.length;d<v;d++)if(E[d].type==="inline"){for(f=E[d].children,u=f.length-1;u>=0;u--)if(x=f[u],x.type==="text"&&(w=0,m=x.content,g.lastIndex=0,h=[],C.test(m))){for(;k=g.exec(m);)(k.index>0||k[1].length>0)&&(y=new s.Token("text","",0),y.content=m.slice(w,k.index+k[1].length),h.push(y)),y=new s.Token("abbr_open","abbr",1),y.attrs=[["title",s.env.abbreviations[":"+k[2]]]],h.push(y),y=new s.Token("text","",0),y.content=k[2],h.push(y),y=new s.Token("abbr_close","abbr",-1),h.push(y),g.lastIndex-=k[3].length,w=g.lastIndex;h.length&&(w<m.length&&(y=new s.Token("text","",0),y.content=m.slice(w),h.push(y)),E[d].children=f=t(f,u,h))}}}}var r=e.utils.escapeRE,t=e.utils.arrayReplaceAt,a=" \r\n$+<=>^`|~",l=e.utils.lib.ucmicro.P.source,p=e.utils.lib.ucmicro.Z.source;e.block.ruler.before("reference","abbr_def",o,{alt:["paragraph","reference"]}),e.core.ruler.after("linkify","abbr_replace",n)}},function(c,_,i){c.exports=function(e,o,n){function r(f){return f.trim().split(" ",2)[0]===o}function t(f,y,m,h,w){return f[y].nesting===1&&f[y].attrPush(["class",o]),w.renderToken(f,y,m,h,w)}function a(f,y,m,h){var w,g,k,b,C,x,E,D,A=!1,S=f.bMarks[y]+f.tShift[y],T=f.eMarks[y];if(s!==f.src.charCodeAt(S))return!1;for(w=S+1;w<=T&&p[(w-S)%u]===f.src[w];w++);if((k=Math.floor((w-S)/u))<l||(w-=(w-S)%u,b=f.src.slice(S,w),C=f.src.slice(w,T),!d(C)))return!1;if(h)return!0;for(g=y;!(++g>=m)&&(S=f.bMarks[g]+f.tShift[g],T=f.eMarks[g],!(S<T&&f.sCount[g]<f.blkIndent));)if(s===f.src.charCodeAt(S)&&!(f.sCount[g]-f.blkIndent>=4)){for(w=S+1;w<=T&&p[(w-S)%u]===f.src[w];w++);if(!(Math.floor((w-S)/u)<k||(w-=(w-S)%u,(w=f.skipSpaces(w))<T))){A=!0;break}}return E=f.parentType,D=f.lineMax,f.parentType="container",f.lineMax=g,x=f.push("container_"+o+"_open","div",1),x.markup=b,x.block=!0,x.info=C,x.map=[y,g],f.md.block.tokenize(f,y+1,g),x=f.push("container_"+o+"_close","div",-1),x.markup=f.src.slice(S,w),x.block=!0,f.parentType=E,f.lineMax=D,f.line=g+(A?1:0),!0}n=n||{};var l=3,p=n.marker||":",s=p.charCodeAt(0),u=p.length,d=n.validate||r,v=n.render||t;e.block.ruler.before("fence","container_"+o,a,{alt:["paragraph","reference","blockquote","list"]}),e.renderer.rules["container_"+o+"_open"]=v,e.renderer.rules["container_"+o+"_close"]=v}},function(c,_,i){c.exports=function(e){function o(a,l){var p,s,u=a.bMarks[l]+a.tShift[l],d=a.eMarks[l];return u>=d||(s=a.src.charCodeAt(u++))!==126&&s!==58?-1:(p=a.skipSpaces(u),u===p||p>=d?-1:u)}function n(a,l){var p,s,u=a.level+2;for(p=l+2,s=a.tokens.length-2;p<s;p++)a.tokens[p].level===u&&a.tokens[p].type==="paragraph_open"&&(a.tokens[p+2].hidden=!0,a.tokens[p].hidden=!0,p+=2)}function r(a,l,p,s){var u,d,v,f,y,m,h,w,g,k,b,C,x,E,D,A,S,T,I,$;if(s)return!(a.ddIndent<0)&&o(a,l)>=0;if((g=l+1)>=p||a.isEmpty(g)&&++g>=p||a.sCount[g]<a.blkIndent||(d=o(a,g))<0)return!1;h=a.tokens.length,I=!0,$=a.push("dl_open","dl",1),$.map=m=[l,0],f=l,v=g;e:for(;;){for(T=!1,$=a.push("dt_open","dt",1),$.map=[f,f],$=a.push("inline","",0),$.map=[f,f],$.content=a.getLines(f,f+1,a.blkIndent,!1).trim(),$.children=[],$=a.push("dt_close","dt",-1);;){for($=a.push("dd_open","dd",1),$.map=y=[g,0],S=d,w=a.eMarks[v],k=a.sCount[v]+d-(a.bMarks[v]+a.tShift[v]);S<w&&(u=a.src.charCodeAt(S),t(u));)u===9?k+=4-k%4:k++,S++;if(d=S,A=a.tight,b=a.ddIndent,C=a.blkIndent,D=a.tShift[v],E=a.sCount[v],x=a.parentType,a.blkIndent=a.ddIndent=a.sCount[v]+2,a.tShift[v]=d-a.bMarks[v],a.sCount[v]=k,a.tight=!0,a.parentType="deflist",a.md.block.tokenize(a,v,p,!0),a.tight&&!T||(I=!1),T=a.line-v>1&&a.isEmpty(a.line-1),a.tShift[v]=D,a.sCount[v]=E,a.tight=A,a.parentType=x,a.blkIndent=C,a.ddIndent=b,$=a.push("dd_close","dd",-1),y[1]=g=a.line,g>=p||a.sCount[g]<a.blkIndent)break e;if((d=o(a,g))<0)break;v=g}if(g>=p||(f=g,a.isEmpty(f))||a.sCount[f]<a.blkIndent||(v=f+1)>=p||(a.isEmpty(v)&&v++,v>=p)||a.sCount[v]<a.blkIndent||(d=o(a,v))<0)break}return $=a.push("dl_close","dl",-1),m[1]=g,a.line=g,I&&n(a,h),!0}var t=e.utils.isSpace;e.block.ruler.before("paragraph","deflist",r,{alt:["paragraph","reference","blockquote"]})}},function(c,_,i){var e=i(121),o=i(122),n=i(124),r=i(125),t=i(123);c.exports=function(a,l){var p={defs:e,shortcuts:o,enabled:[]},s=t(a.utils.assign({},p,l||{}));a.renderer.rules.emoji=n,a.core.ruler.push("emoji",r(a,s.defs,s.shortcuts,s.scanRE,s.replaceRE))}},function(c,_){c.exports={100:"\u{1F4AF}",1234:"\u{1F522}",grinning:"\u{1F600}",smiley:"\u{1F603}",smile:"\u{1F604}",grin:"\u{1F601}",laughing:"\u{1F606}",satisfied:"\u{1F606}",sweat_smile:"\u{1F605}",joy:"\u{1F602}",rofl:"\u{1F923}",relaxed:"\u263A\uFE0F",blush:"\u{1F60A}",innocent:"\u{1F607}",slightly_smiling_face:"\u{1F642}",upside_down_face:"\u{1F643}",wink:"\u{1F609}",relieved:"\u{1F60C}",heart_eyes:"\u{1F60D}",kissing_heart:"\u{1F618}",kissing:"\u{1F617}",kissing_smiling_eyes:"\u{1F619}",kissing_closed_eyes:"\u{1F61A}",yum:"\u{1F60B}",stuck_out_tongue_winking_eye:"\u{1F61C}",stuck_out_tongue_closed_eyes:"\u{1F61D}",stuck_out_tongue:"\u{1F61B}",money_mouth_face:"\u{1F911}",hugs:"\u{1F917}",nerd_face:"\u{1F913}",sunglasses:"\u{1F60E}",clown_face:"\u{1F921}",cowboy_hat_face:"\u{1F920}",smirk:"\u{1F60F}",unamused:"\u{1F612}",disappointed:"\u{1F61E}",pensive:"\u{1F614}",worried:"\u{1F61F}",confused:"\u{1F615}",slightly_frowning_face:"\u{1F641}",frowning_face:"\u2639\uFE0F",persevere:"\u{1F623}",confounded:"\u{1F616}",tired_face:"\u{1F62B}",weary:"\u{1F629}",triumph:"\u{1F624}",angry:"\u{1F620}",rage:"\u{1F621}",pout:"\u{1F621}",no_mouth:"\u{1F636}",neutral_face:"\u{1F610}",expressionless:"\u{1F611}",hushed:"\u{1F62F}",frowning:"\u{1F626}",anguished:"\u{1F627}",open_mouth:"\u{1F62E}",astonished:"\u{1F632}",dizzy_face:"\u{1F635}",flushed:"\u{1F633}",scream:"\u{1F631}",fearful:"\u{1F628}",cold_sweat:"\u{1F630}",cry:"\u{1F622}",disappointed_relieved:"\u{1F625}",drooling_face:"\u{1F924}",sob:"\u{1F62D}",sweat:"\u{1F613}",sleepy:"\u{1F62A}",sleeping:"\u{1F634}",roll_eyes:"\u{1F644}",thinking:"\u{1F914}",lying_face:"\u{1F925}",grimacing:"\u{1F62C}",zipper_mouth_face:"\u{1F910}",nauseated_face:"\u{1F922}",sneezing_face:"\u{1F927}",mask:"\u{1F637}",face_with_thermometer:"\u{1F912}",face_with_head_bandage:"\u{1F915}",smiling_imp:"\u{1F608}",imp:"\u{1F47F}",japanese_ogre:"\u{1F479}",japanese_goblin:"\u{1F47A}",hankey:"\u{1F4A9}",poop:"\u{1F4A9}",shit:"\u{1F4A9}",ghost:"\u{1F47B}",skull:"\u{1F480}",skull_and_crossbones:"\u2620\uFE0F",alien:"\u{1F47D}",space_invader:"\u{1F47E}",robot:"\u{1F916}",jack_o_lantern:"\u{1F383}",smiley_cat:"\u{1F63A}",smile_cat:"\u{1F638}",joy_cat:"\u{1F639}",heart_eyes_cat:"\u{1F63B}",smirk_cat:"\u{1F63C}",kissing_cat:"\u{1F63D}",scream_cat:"\u{1F640}",crying_cat_face:"\u{1F63F}",pouting_cat:"\u{1F63E}",open_hands:"\u{1F450}",raised_hands:"\u{1F64C}",clap:"\u{1F44F}",pray:"\u{1F64F}",handshake:"\u{1F91D}","+1":"\u{1F44D}",thumbsup:"\u{1F44D}","-1":"\u{1F44E}",thumbsdown:"\u{1F44E}",fist_oncoming:"\u{1F44A}",facepunch:"\u{1F44A}",punch:"\u{1F44A}",fist_raised:"\u270A",fist:"\u270A",fist_left:"\u{1F91B}",fist_right:"\u{1F91C}",crossed_fingers:"\u{1F91E}",v:"\u270C\uFE0F",metal:"\u{1F918}",ok_hand:"\u{1F44C}",point_left:"\u{1F448}",point_right:"\u{1F449}",point_up_2:"\u{1F446}",point_down:"\u{1F447}",point_up:"\u261D\uFE0F",hand:"\u270B",raised_hand:"\u270B",raised_back_of_hand:"\u{1F91A}",raised_hand_with_fingers_splayed:"\u{1F590}",vulcan_salute:"\u{1F596}",wave:"\u{1F44B}",call_me_hand:"\u{1F919}",muscle:"\u{1F4AA}",middle_finger:"\u{1F595}",fu:"\u{1F595}",writing_hand:"\u270D\uFE0F",selfie:"\u{1F933}",nail_care:"\u{1F485}",ring:"\u{1F48D}",lipstick:"\u{1F484}",kiss:"\u{1F48B}",lips:"\u{1F444}",tongue:"\u{1F445}",ear:"\u{1F442}",nose:"\u{1F443}",footprints:"\u{1F463}",eye:"\u{1F441}",eyes:"\u{1F440}",speaking_head:"\u{1F5E3}",bust_in_silhouette:"\u{1F464}",busts_in_silhouette:"\u{1F465}",baby:"\u{1F476}",boy:"\u{1F466}",girl:"\u{1F467}",man:"\u{1F468}",woman:"\u{1F469}",blonde_woman:"\u{1F471}\u200D\u2640",blonde_man:"\u{1F471}",person_with_blond_hair:"\u{1F471}",older_man:"\u{1F474}",older_woman:"\u{1F475}",man_with_gua_pi_mao:"\u{1F472}",woman_with_turban:"\u{1F473}\u200D\u2640",man_with_turban:"\u{1F473}",policewoman:"\u{1F46E}\u200D\u2640",policeman:"\u{1F46E}",cop:"\u{1F46E}",construction_worker_woman:"\u{1F477}\u200D\u2640",construction_worker_man:"\u{1F477}",construction_worker:"\u{1F477}",guardswoman:"\u{1F482}\u200D\u2640",guardsman:"\u{1F482}",female_detective:"\u{1F575}\uFE0F\u200D\u2640\uFE0F",male_detective:"\u{1F575}",detective:"\u{1F575}",woman_health_worker:"\u{1F469}\u200D\u2695",man_health_worker:"\u{1F468}\u200D\u2695",woman_farmer:"\u{1F469}\u200D\u{1F33E}",man_farmer:"\u{1F468}\u200D\u{1F33E}",woman_cook:"\u{1F469}\u200D\u{1F373}",man_cook:"\u{1F468}\u200D\u{1F373}",woman_student:"\u{1F469}\u200D\u{1F393}",man_student:"\u{1F468}\u200D\u{1F393}",woman_singer:"\u{1F469}\u200D\u{1F3A4}",man_singer:"\u{1F468}\u200D\u{1F3A4}",woman_teacher:"\u{1F469}\u200D\u{1F3EB}",man_teacher:"\u{1F468}\u200D\u{1F3EB}",woman_factory_worker:"\u{1F469}\u200D\u{1F3ED}",man_factory_worker:"\u{1F468}\u200D\u{1F3ED}",woman_technologist:"\u{1F469}\u200D\u{1F4BB}",man_technologist:"\u{1F468}\u200D\u{1F4BB}",woman_office_worker:"\u{1F469}\u200D\u{1F4BC}",man_office_worker:"\u{1F468}\u200D\u{1F4BC}",woman_mechanic:"\u{1F469}\u200D\u{1F527}",man_mechanic:"\u{1F468}\u200D\u{1F527}",woman_scientist:"\u{1F469}\u200D\u{1F52C}",man_scientist:"\u{1F468}\u200D\u{1F52C}",woman_artist:"\u{1F469}\u200D\u{1F3A8}",man_artist:"\u{1F468}\u200D\u{1F3A8}",woman_firefighter:"\u{1F469}\u200D\u{1F692}",man_firefighter:"\u{1F468}\u200D\u{1F692}",woman_pilot:"\u{1F469}\u200D\u2708",man_pilot:"\u{1F468}\u200D\u2708",woman_astronaut:"\u{1F469}\u200D\u{1F680}",man_astronaut:"\u{1F468}\u200D\u{1F680}",woman_judge:"\u{1F469}\u200D\u2696",man_judge:"\u{1F468}\u200D\u2696",mrs_claus:"\u{1F936}",santa:"\u{1F385}",princess:"\u{1F478}",prince:"\u{1F934}",bride_with_veil:"\u{1F470}",man_in_tuxedo:"\u{1F935}",angel:"\u{1F47C}",pregnant_woman:"\u{1F930}",bowing_woman:"\u{1F647}\u200D\u2640",bowing_man:"\u{1F647}",bow:"\u{1F647}",tipping_hand_woman:"\u{1F481}",information_desk_person:"\u{1F481}",sassy_woman:"\u{1F481}",tipping_hand_man:"\u{1F481}\u200D\u2642",sassy_man:"\u{1F481}\u200D\u2642",no_good_woman:"\u{1F645}",no_good:"\u{1F645}",ng_woman:"\u{1F645}",no_good_man:"\u{1F645}\u200D\u2642",ng_man:"\u{1F645}\u200D\u2642",ok_woman:"\u{1F646}",ok_man:"\u{1F646}\u200D\u2642",raising_hand_woman:"\u{1F64B}",raising_hand:"\u{1F64B}",raising_hand_man:"\u{1F64B}\u200D\u2642",woman_facepalming:"\u{1F926}\u200D\u2640",man_facepalming:"\u{1F926}\u200D\u2642",woman_shrugging:"\u{1F937}\u200D\u2640",man_shrugging:"\u{1F937}\u200D\u2642",pouting_woman:"\u{1F64E}",person_with_pouting_face:"\u{1F64E}",pouting_man:"\u{1F64E}\u200D\u2642",frowning_woman:"\u{1F64D}",person_frowning:"\u{1F64D}",frowning_man:"\u{1F64D}\u200D\u2642",haircut_woman:"\u{1F487}",haircut:"\u{1F487}",haircut_man:"\u{1F487}\u200D\u2642",massage_woman:"\u{1F486}",massage:"\u{1F486}",massage_man:"\u{1F486}\u200D\u2642",business_suit_levitating:"\u{1F574}",dancer:"\u{1F483}",man_dancing:"\u{1F57A}",dancing_women:"\u{1F46F}",dancers:"\u{1F46F}",dancing_men:"\u{1F46F}\u200D\u2642",walking_woman:"\u{1F6B6}\u200D\u2640",walking_man:"\u{1F6B6}",walking:"\u{1F6B6}",running_woman:"\u{1F3C3}\u200D\u2640",running_man:"\u{1F3C3}",runner:"\u{1F3C3}",running:"\u{1F3C3}",couple:"\u{1F46B}",two_women_holding_hands:"\u{1F46D}",two_men_holding_hands:"\u{1F46C}",couple_with_heart_woman_man:"\u{1F491}",couple_with_heart:"\u{1F491}",couple_with_heart_woman_woman:"\u{1F469}\u200D\u2764\uFE0F\u200D\u{1F469}",couple_with_heart_man_man:"\u{1F468}\u200D\u2764\uFE0F\u200D\u{1F468}",couplekiss_man_woman:"\u{1F48F}",couplekiss_woman_woman:"\u{1F469}\u200D\u2764\uFE0F\u200D\u{1F48B}\u200D\u{1F469}",couplekiss_man_man:"\u{1F468}\u200D\u2764\uFE0F\u200D\u{1F48B}\u200D\u{1F468}",family_man_woman_boy:"\u{1F46A}",family:"\u{1F46A}",family_man_woman_girl:"\u{1F468}\u200D\u{1F469}\u200D\u{1F467}",family_man_woman_girl_boy:"\u{1F468}\u200D\u{1F469}\u200D\u{1F467}\u200D\u{1F466}",family_man_woman_boy_boy:"\u{1F468}\u200D\u{1F469}\u200D\u{1F466}\u200D\u{1F466}",family_man_woman_girl_girl:"\u{1F468}\u200D\u{1F469}\u200D\u{1F467}\u200D\u{1F467}",family_woman_woman_boy:"\u{1F469}\u200D\u{1F469}\u200D\u{1F466}",family_woman_woman_girl:"\u{1F469}\u200D\u{1F469}\u200D\u{1F467}",family_woman_woman_girl_boy:"\u{1F469}\u200D\u{1F469}\u200D\u{1F467}\u200D\u{1F466}",family_woman_woman_boy_boy:"\u{1F469}\u200D\u{1F469}\u200D\u{1F466}\u200D\u{1F466}",family_woman_woman_girl_girl:"\u{1F469}\u200D\u{1F469}\u200D\u{1F467}\u200D\u{1F467}",family_man_man_boy:"\u{1F468}\u200D\u{1F468}\u200D\u{1F466}",family_man_man_girl:"\u{1F468}\u200D\u{1F468}\u200D\u{1F467}",family_man_man_girl_boy:"\u{1F468}\u200D\u{1F468}\u200D\u{1F467}\u200D\u{1F466}",family_man_man_boy_boy:"\u{1F468}\u200D\u{1F468}\u200D\u{1F466}\u200D\u{1F466}",family_man_man_girl_girl:"\u{1F468}\u200D\u{1F468}\u200D\u{1F467}\u200D\u{1F467}",family_woman_boy:"\u{1F469}\u200D\u{1F466}",family_woman_girl:"\u{1F469}\u200D\u{1F467}",family_woman_girl_boy:"\u{1F469}\u200D\u{1F467}\u200D\u{1F466}",family_woman_boy_boy:"\u{1F469}\u200D\u{1F466}\u200D\u{1F466}",family_woman_girl_girl:"\u{1F469}\u200D\u{1F467}\u200D\u{1F467}",family_man_boy:"\u{1F468}\u200D\u{1F466}",family_man_girl:"\u{1F468}\u200D\u{1F467}",family_man_girl_boy:"\u{1F468}\u200D\u{1F467}\u200D\u{1F466}",family_man_boy_boy:"\u{1F468}\u200D\u{1F466}\u200D\u{1F466}",family_man_girl_girl:"\u{1F468}\u200D\u{1F467}\u200D\u{1F467}",womans_clothes:"\u{1F45A}",shirt:"\u{1F455}",tshirt:"\u{1F455}",jeans:"\u{1F456}",necktie:"\u{1F454}",dress:"\u{1F457}",bikini:"\u{1F459}",kimono:"\u{1F458}",high_heel:"\u{1F460}",sandal:"\u{1F461}",boot:"\u{1F462}",mans_shoe:"\u{1F45E}",shoe:"\u{1F45E}",athletic_shoe:"\u{1F45F}",womans_hat:"\u{1F452}",tophat:"\u{1F3A9}",mortar_board:"\u{1F393}",crown:"\u{1F451}",rescue_worker_helmet:"\u26D1",school_satchel:"\u{1F392}",pouch:"\u{1F45D}",purse:"\u{1F45B}",handbag:"\u{1F45C}",briefcase:"\u{1F4BC}",eyeglasses:"\u{1F453}",dark_sunglasses:"\u{1F576}",closed_umbrella:"\u{1F302}",open_umbrella:"\u2602\uFE0F",dog:"\u{1F436}",cat:"\u{1F431}",mouse:"\u{1F42D}",hamster:"\u{1F439}",rabbit:"\u{1F430}",fox_face:"\u{1F98A}",bear:"\u{1F43B}",panda_face:"\u{1F43C}",koala:"\u{1F428}",tiger:"\u{1F42F}",lion:"\u{1F981}",cow:"\u{1F42E}",pig:"\u{1F437}",pig_nose:"\u{1F43D}",frog:"\u{1F438}",monkey_face:"\u{1F435}",see_no_evil:"\u{1F648}",hear_no_evil:"\u{1F649}",speak_no_evil:"\u{1F64A}",monkey:"\u{1F412}",chicken:"\u{1F414}",penguin:"\u{1F427}",bird:"\u{1F426}",baby_chick:"\u{1F424}",hatching_chick:"\u{1F423}",hatched_chick:"\u{1F425}",duck:"\u{1F986}",eagle:"\u{1F985}",owl:"\u{1F989}",bat:"\u{1F987}",wolf:"\u{1F43A}",boar:"\u{1F417}",horse:"\u{1F434}",unicorn:"\u{1F984}",bee:"\u{1F41D}",honeybee:"\u{1F41D}",bug:"\u{1F41B}",butterfly:"\u{1F98B}",snail:"\u{1F40C}",shell:"\u{1F41A}",beetle:"\u{1F41E}",ant:"\u{1F41C}",spider:"\u{1F577}",spider_web:"\u{1F578}",turtle:"\u{1F422}",snake:"\u{1F40D}",lizard:"\u{1F98E}",scorpion:"\u{1F982}",crab:"\u{1F980}",squid:"\u{1F991}",octopus:"\u{1F419}",shrimp:"\u{1F990}",tropical_fish:"\u{1F420}",fish:"\u{1F41F}",blowfish:"\u{1F421}",dolphin:"\u{1F42C}",flipper:"\u{1F42C}",shark:"\u{1F988}",whale:"\u{1F433}",whale2:"\u{1F40B}",crocodile:"\u{1F40A}",leopard:"\u{1F406}",tiger2:"\u{1F405}",water_buffalo:"\u{1F403}",ox:"\u{1F402}",cow2:"\u{1F404}",deer:"\u{1F98C}",dromedary_camel:"\u{1F42A}",camel:"\u{1F42B}",elephant:"\u{1F418}",rhinoceros:"\u{1F98F}",gorilla:"\u{1F98D}",racehorse:"\u{1F40E}",pig2:"\u{1F416}",goat:"\u{1F410}",ram:"\u{1F40F}",sheep:"\u{1F411}",dog2:"\u{1F415}",poodle:"\u{1F429}",cat2:"\u{1F408}",rooster:"\u{1F413}",turkey:"\u{1F983}",dove:"\u{1F54A}",rabbit2:"\u{1F407}",mouse2:"\u{1F401}",rat:"\u{1F400}",chipmunk:"\u{1F43F}",feet:"\u{1F43E}",paw_prints:"\u{1F43E}",dragon:"\u{1F409}",dragon_face:"\u{1F432}",cactus:"\u{1F335}",christmas_tree:"\u{1F384}",evergreen_tree:"\u{1F332}",deciduous_tree:"\u{1F333}",palm_tree:"\u{1F334}",seedling:"\u{1F331}",herb:"\u{1F33F}",shamrock:"\u2618\uFE0F",four_leaf_clover:"\u{1F340}",bamboo:"\u{1F38D}",tanabata_tree:"\u{1F38B}",leaves:"\u{1F343}",fallen_leaf:"\u{1F342}",maple_leaf:"\u{1F341}",mushroom:"\u{1F344}",ear_of_rice:"\u{1F33E}",bouquet:"\u{1F490}",tulip:"\u{1F337}",rose:"\u{1F339}",wilted_flower:"\u{1F940}",sunflower:"\u{1F33B}",blossom:"\u{1F33C}",cherry_blossom:"\u{1F338}",hibiscus:"\u{1F33A}",earth_americas:"\u{1F30E}",earth_africa:"\u{1F30D}",earth_asia:"\u{1F30F}",full_moon:"\u{1F315}",waning_gibbous_moon:"\u{1F316}",last_quarter_moon:"\u{1F317}",waning_crescent_moon:"\u{1F318}",new_moon:"\u{1F311}",waxing_crescent_moon:"\u{1F312}",first_quarter_moon:"\u{1F313}",moon:"\u{1F314}",waxing_gibbous_moon:"\u{1F314}",new_moon_with_face:"\u{1F31A}",full_moon_with_face:"\u{1F31D}",sun_with_face:"\u{1F31E}",first_quarter_moon_with_face:"\u{1F31B}",last_quarter_moon_with_face:"\u{1F31C}",crescent_moon:"\u{1F319}",dizzy:"\u{1F4AB}",star:"\u2B50\uFE0F",star2:"\u{1F31F}",sparkles:"\u2728",zap:"\u26A1\uFE0F",fire:"\u{1F525}",boom:"\u{1F4A5}",collision:"\u{1F4A5}",comet:"\u2604",sunny:"\u2600\uFE0F",sun_behind_small_cloud:"\u{1F324}",partly_sunny:"\u26C5\uFE0F",sun_behind_large_cloud:"\u{1F325}",sun_behind_rain_cloud:"\u{1F326}",rainbow:"\u{1F308}",cloud:"\u2601\uFE0F",cloud_with_rain:"\u{1F327}",cloud_with_lightning_and_rain:"\u26C8",cloud_with_lightning:"\u{1F329}",cloud_with_snow:"\u{1F328}",snowman_with_snow:"\u2603\uFE0F",snowman:"\u26C4\uFE0F",snowflake:"\u2744\uFE0F",wind_face:"\u{1F32C}",dash:"\u{1F4A8}",tornado:"\u{1F32A}",fog:"\u{1F32B}",ocean:"\u{1F30A}",droplet:"\u{1F4A7}",sweat_drops:"\u{1F4A6}",umbrella:"\u2614\uFE0F",green_apple:"\u{1F34F}",apple:"\u{1F34E}",pear:"\u{1F350}",tangerine:"\u{1F34A}",orange:"\u{1F34A}",mandarin:"\u{1F34A}",lemon:"\u{1F34B}",banana:"\u{1F34C}",watermelon:"\u{1F349}",grapes:"\u{1F347}",strawberry:"\u{1F353}",melon:"\u{1F348}",cherries:"\u{1F352}",peach:"\u{1F351}",pineapple:"\u{1F34D}",kiwi_fruit:"\u{1F95D}",avocado:"\u{1F951}",tomato:"\u{1F345}",eggplant:"\u{1F346}",cucumber:"\u{1F952}",carrot:"\u{1F955}",corn:"\u{1F33D}",hot_pepper:"\u{1F336}",potato:"\u{1F954}",sweet_potato:"\u{1F360}",chestnut:"\u{1F330}",peanuts:"\u{1F95C}",honey_pot:"\u{1F36F}",croissant:"\u{1F950}",bread:"\u{1F35E}",baguette_bread:"\u{1F956}",cheese:"\u{1F9C0}",egg:"\u{1F95A}",fried_egg:"\u{1F373}",bacon:"\u{1F953}",pancakes:"\u{1F95E}",fried_shrimp:"\u{1F364}",poultry_leg:"\u{1F357}",meat_on_bone:"\u{1F356}",pizza:"\u{1F355}",hotdog:"\u{1F32D}",hamburger:"\u{1F354}",fries:"\u{1F35F}",stuffed_flatbread:"\u{1F959}",taco:"\u{1F32E}",burrito:"\u{1F32F}",green_salad:"\u{1F957}",shallow_pan_of_food:"\u{1F958}",spaghetti:"\u{1F35D}",ramen:"\u{1F35C}",stew:"\u{1F372}",fish_cake:"\u{1F365}",sushi:"\u{1F363}",bento:"\u{1F371}",curry:"\u{1F35B}",rice:"\u{1F35A}",rice_ball:"\u{1F359}",rice_cracker:"\u{1F358}",oden:"\u{1F362}",dango:"\u{1F361}",shaved_ice:"\u{1F367}",ice_cream:"\u{1F368}",icecream:"\u{1F366}",cake:"\u{1F370}",birthday:"\u{1F382}",custard:"\u{1F36E}",lollipop:"\u{1F36D}",candy:"\u{1F36C}",chocolate_bar:"\u{1F36B}",popcorn:"\u{1F37F}",doughnut:"\u{1F369}",cookie:"\u{1F36A}",milk_glass:"\u{1F95B}",baby_bottle:"\u{1F37C}",coffee:"\u2615\uFE0F",tea:"\u{1F375}",sake:"\u{1F376}",beer:"\u{1F37A}",beers:"\u{1F37B}",clinking_glasses:"\u{1F942}",wine_glass:"\u{1F377}",tumbler_glass:"\u{1F943}",cocktail:"\u{1F378}",tropical_drink:"\u{1F379}",champagne:"\u{1F37E}",spoon:"\u{1F944}",fork_and_knife:"\u{1F374}",plate_with_cutlery:"\u{1F37D}",soccer:"\u26BD\uFE0F",basketball:"\u{1F3C0}",football:"\u{1F3C8}",baseball:"\u26BE\uFE0F",tennis:"\u{1F3BE}",volleyball:"\u{1F3D0}",rugby_football:"\u{1F3C9}","8ball":"\u{1F3B1}",ping_pong:"\u{1F3D3}",badminton:"\u{1F3F8}",goal_net:"\u{1F945}",ice_hockey:"\u{1F3D2}",field_hockey:"\u{1F3D1}",cricket:"\u{1F3CF}",golf:"\u26F3\uFE0F",bow_and_arrow:"\u{1F3F9}",fishing_pole_and_fish:"\u{1F3A3}",boxing_glove:"\u{1F94A}",martial_arts_uniform:"\u{1F94B}",ice_skate:"\u26F8",ski:"\u{1F3BF}",skier:"\u26F7",snowboarder:"\u{1F3C2}",weight_lifting_woman:"\u{1F3CB}\uFE0F\u200D\u2640\uFE0F",weight_lifting_man:"\u{1F3CB}",person_fencing:"\u{1F93A}",women_wrestling:"\u{1F93C}\u200D\u2640",men_wrestling:"\u{1F93C}\u200D\u2642",woman_cartwheeling:"\u{1F938}\u200D\u2640",man_cartwheeling:"\u{1F938}\u200D\u2642",basketball_woman:"\u26F9\uFE0F\u200D\u2640\uFE0F",basketball_man:"\u26F9",woman_playing_handball:"\u{1F93E}\u200D\u2640",man_playing_handball:"\u{1F93E}\u200D\u2642",golfing_woman:"\u{1F3CC}\uFE0F\u200D\u2640\uFE0F",golfing_man:"\u{1F3CC}",surfing_woman:"\u{1F3C4}\u200D\u2640",surfing_man:"\u{1F3C4}",surfer:"\u{1F3C4}",swimming_woman:"\u{1F3CA}\u200D\u2640",swimming_man:"\u{1F3CA}",swimmer:"\u{1F3CA}",woman_playing_water_polo:"\u{1F93D}\u200D\u2640",man_playing_water_polo:"\u{1F93D}\u200D\u2642",rowing_woman:"\u{1F6A3}\u200D\u2640",rowing_man:"\u{1F6A3}",rowboat:"\u{1F6A3}",horse_racing:"\u{1F3C7}",biking_woman:"\u{1F6B4}\u200D\u2640",biking_man:"\u{1F6B4}",bicyclist:"\u{1F6B4}",mountain_biking_woman:"\u{1F6B5}\u200D\u2640",mountain_biking_man:"\u{1F6B5}",mountain_bicyclist:"\u{1F6B5}",running_shirt_with_sash:"\u{1F3BD}",medal_sports:"\u{1F3C5}",medal_military:"\u{1F396}","1st_place_medal":"\u{1F947}","2nd_place_medal":"\u{1F948}","3rd_place_medal":"\u{1F949}",trophy:"\u{1F3C6}",rosette:"\u{1F3F5}",reminder_ribbon:"\u{1F397}",ticket:"\u{1F3AB}",tickets:"\u{1F39F}",circus_tent:"\u{1F3AA}",woman_juggling:"\u{1F939}\u200D\u2640",man_juggling:"\u{1F939}\u200D\u2642",performing_arts:"\u{1F3AD}",art:"\u{1F3A8}",clapper:"\u{1F3AC}",microphone:"\u{1F3A4}",headphones:"\u{1F3A7}",musical_score:"\u{1F3BC}",musical_keyboard:"\u{1F3B9}",drum:"\u{1F941}",saxophone:"\u{1F3B7}",trumpet:"\u{1F3BA}",guitar:"\u{1F3B8}",violin:"\u{1F3BB}",game_die:"\u{1F3B2}",dart:"\u{1F3AF}",bowling:"\u{1F3B3}",video_game:"\u{1F3AE}",slot_machine:"\u{1F3B0}",car:"\u{1F697}",red_car:"\u{1F697}",taxi:"\u{1F695}",blue_car:"\u{1F699}",bus:"\u{1F68C}",trolleybus:"\u{1F68E}",racing_car:"\u{1F3CE}",police_car:"\u{1F693}",ambulance:"\u{1F691}",fire_engine:"\u{1F692}",minibus:"\u{1F690}",truck:"\u{1F69A}",articulated_lorry:"\u{1F69B}",tractor:"\u{1F69C}",kick_scooter:"\u{1F6F4}",bike:"\u{1F6B2}",motor_scooter:"\u{1F6F5}",motorcycle:"\u{1F3CD}",rotating_light:"\u{1F6A8}",oncoming_police_car:"\u{1F694}",oncoming_bus:"\u{1F68D}",oncoming_automobile:"\u{1F698}",oncoming_taxi:"\u{1F696}",aerial_tramway:"\u{1F6A1}",mountain_cableway:"\u{1F6A0}",suspension_railway:"\u{1F69F}",railway_car:"\u{1F683}",train:"\u{1F68B}",mountain_railway:"\u{1F69E}",monorail:"\u{1F69D}",bullettrain_side:"\u{1F684}",bullettrain_front:"\u{1F685}",light_rail:"\u{1F688}",steam_locomotive:"\u{1F682}",train2:"\u{1F686}",metro:"\u{1F687}",tram:"\u{1F68A}",station:"\u{1F689}",helicopter:"\u{1F681}",small_airplane:"\u{1F6E9}",airplane:"\u2708\uFE0F",flight_departure:"\u{1F6EB}",flight_arrival:"\u{1F6EC}",rocket:"\u{1F680}",artificial_satellite:"\u{1F6F0}",seat:"\u{1F4BA}",canoe:"\u{1F6F6}",boat:"\u26F5\uFE0F",sailboat:"\u26F5\uFE0F",motor_boat:"\u{1F6E5}",speedboat:"\u{1F6A4}",passenger_ship:"\u{1F6F3}",ferry:"\u26F4",ship:"\u{1F6A2}",anchor:"\u2693\uFE0F",construction:"\u{1F6A7}",fuelpump:"\u26FD\uFE0F",busstop:"\u{1F68F}",vertical_traffic_light:"\u{1F6A6}",traffic_light:"\u{1F6A5}",world_map:"\u{1F5FA}",moyai:"\u{1F5FF}",statue_of_liberty:"\u{1F5FD}",fountain:"\u26F2\uFE0F",tokyo_tower:"\u{1F5FC}",european_castle:"\u{1F3F0}",japanese_castle:"\u{1F3EF}",stadium:"\u{1F3DF}",ferris_wheel:"\u{1F3A1}",roller_coaster:"\u{1F3A2}",carousel_horse:"\u{1F3A0}",parasol_on_ground:"\u26F1",beach_umbrella:"\u{1F3D6}",desert_island:"\u{1F3DD}",mountain:"\u26F0",mountain_snow:"\u{1F3D4}",mount_fuji:"\u{1F5FB}",volcano:"\u{1F30B}",desert:"\u{1F3DC}",camping:"\u{1F3D5}",tent:"\u26FA\uFE0F",railway_track:"\u{1F6E4}",motorway:"\u{1F6E3}",building_construction:"\u{1F3D7}",factory:"\u{1F3ED}",house:"\u{1F3E0}",house_with_garden:"\u{1F3E1}",houses:"\u{1F3D8}",derelict_house:"\u{1F3DA}",office:"\u{1F3E2}",department_store:"\u{1F3EC}",post_office:"\u{1F3E3}",european_post_office:"\u{1F3E4}",hospital:"\u{1F3E5}",bank:"\u{1F3E6}",hotel:"\u{1F3E8}",convenience_store:"\u{1F3EA}",school:"\u{1F3EB}",love_hotel:"\u{1F3E9}",wedding:"\u{1F492}",classical_building:"\u{1F3DB}",church:"\u26EA\uFE0F",mosque:"\u{1F54C}",synagogue:"\u{1F54D}",kaaba:"\u{1F54B}",shinto_shrine:"\u26E9",japan:"\u{1F5FE}",rice_scene:"\u{1F391}",national_park:"\u{1F3DE}",sunrise:"\u{1F305}",sunrise_over_mountains:"\u{1F304}",stars:"\u{1F320}",sparkler:"\u{1F387}",fireworks:"\u{1F386}",city_sunrise:"\u{1F307}",city_sunset:"\u{1F306}",cityscape:"\u{1F3D9}",night_with_stars:"\u{1F303}",milky_way:"\u{1F30C}",bridge_at_night:"\u{1F309}",foggy:"\u{1F301}",watch:"\u231A\uFE0F",iphone:"\u{1F4F1}",calling:"\u{1F4F2}",computer:"\u{1F4BB}",keyboard:"\u2328\uFE0F",desktop_computer:"\u{1F5A5}",printer:"\u{1F5A8}",computer_mouse:"\u{1F5B1}",trackball:"\u{1F5B2}",joystick:"\u{1F579}",clamp:"\u{1F5DC}",minidisc:"\u{1F4BD}",floppy_disk:"\u{1F4BE}",cd:"\u{1F4BF}",dvd:"\u{1F4C0}",vhs:"\u{1F4FC}",camera:"\u{1F4F7}",camera_flash:"\u{1F4F8}",video_camera:"\u{1F4F9}",movie_camera:"\u{1F3A5}",film_projector:"\u{1F4FD}",film_strip:"\u{1F39E}",telephone_receiver:"\u{1F4DE}",phone:"\u260E\uFE0F",telephone:"\u260E\uFE0F",pager:"\u{1F4DF}",fax:"\u{1F4E0}",tv:"\u{1F4FA}",radio:"\u{1F4FB}",studio_microphone:"\u{1F399}",level_slider:"\u{1F39A}",control_knobs:"\u{1F39B}",stopwatch:"\u23F1",timer_clock:"\u23F2",alarm_clock:"\u23F0",mantelpiece_clock:"\u{1F570}",hourglass:"\u231B\uFE0F",hourglass_flowing_sand:"\u23F3",satellite:"\u{1F4E1}",battery:"\u{1F50B}",electric_plug:"\u{1F50C}",bulb:"\u{1F4A1}",flashlight:"\u{1F526}",candle:"\u{1F56F}",wastebasket:"\u{1F5D1}",oil_drum:"\u{1F6E2}",money_with_wings:"\u{1F4B8}",dollar:"\u{1F4B5}",yen:"\u{1F4B4}",euro:"\u{1F4B6}",pound:"\u{1F4B7}",moneybag:"\u{1F4B0}",credit_card:"\u{1F4B3}",gem:"\u{1F48E}",balance_scale:"\u2696\uFE0F",wrench:"\u{1F527}",hammer:"\u{1F528}",hammer_and_pick:"\u2692",hammer_and_wrench:"\u{1F6E0}",pick:"\u26CF",nut_and_bolt:"\u{1F529}",gear:"\u2699\uFE0F",chains:"\u26D3",gun:"\u{1F52B}",bomb:"\u{1F4A3}",hocho:"\u{1F52A}",knife:"\u{1F52A}",dagger:"\u{1F5E1}",crossed_swords:"\u2694\uFE0F",shield:"\u{1F6E1}",smoking:"\u{1F6AC}",coffin:"\u26B0\uFE0F",funeral_urn:"\u26B1\uFE0F",amphora:"\u{1F3FA}",crystal_ball:"\u{1F52E}",prayer_beads:"\u{1F4FF}",barber:"\u{1F488}",alembic:"\u2697\uFE0F",telescope:"\u{1F52D}",microscope:"\u{1F52C}",hole:"\u{1F573}",pill:"\u{1F48A}",syringe:"\u{1F489}",thermometer:"\u{1F321}",toilet:"\u{1F6BD}",potable_water:"\u{1F6B0}",shower:"\u{1F6BF}",bathtub:"\u{1F6C1}",bath:"\u{1F6C0}",bellhop_bell:"\u{1F6CE}",key:"\u{1F511}",old_key:"\u{1F5DD}",door:"\u{1F6AA}",couch_and_lamp:"\u{1F6CB}",bed:"\u{1F6CF}",sleeping_bed:"\u{1F6CC}",framed_picture:"\u{1F5BC}",shopping:"\u{1F6CD}",shopping_cart:"\u{1F6D2}",gift:"\u{1F381}",balloon:"\u{1F388}",flags:"\u{1F38F}",ribbon:"\u{1F380}",confetti_ball:"\u{1F38A}",tada:"\u{1F389}",dolls:"\u{1F38E}",izakaya_lantern:"\u{1F3EE}",lantern:"\u{1F3EE}",wind_chime:"\u{1F390}",email:"\u2709\uFE0F",envelope:"\u2709\uFE0F",envelope_with_arrow:"\u{1F4E9}",incoming_envelope:"\u{1F4E8}","e-mail":"\u{1F4E7}",love_letter:"\u{1F48C}",inbox_tray:"\u{1F4E5}",outbox_tray:"\u{1F4E4}",package:"\u{1F4E6}",label:"\u{1F3F7}",mailbox_closed:"\u{1F4EA}",mailbox:"\u{1F4EB}",mailbox_with_mail:"\u{1F4EC}",mailbox_with_no_mail:"\u{1F4ED}",postbox:"\u{1F4EE}",postal_horn:"\u{1F4EF}",scroll:"\u{1F4DC}",page_with_curl:"\u{1F4C3}",page_facing_up:"\u{1F4C4}",bookmark_tabs:"\u{1F4D1}",bar_chart:"\u{1F4CA}",chart_with_upwards_trend:"\u{1F4C8}",chart_with_downwards_trend:"\u{1F4C9}",spiral_notepad:"\u{1F5D2}",spiral_calendar:"\u{1F5D3}",calendar:"\u{1F4C6}",date:"\u{1F4C5}",card_index:"\u{1F4C7}",card_file_box:"\u{1F5C3}",ballot_box:"\u{1F5F3}",file_cabinet:"\u{1F5C4}",clipboard:"\u{1F4CB}",file_folder:"\u{1F4C1}",open_file_folder:"\u{1F4C2}",card_index_dividers:"\u{1F5C2}",newspaper_roll:"\u{1F5DE}",newspaper:"\u{1F4F0}",notebook:"\u{1F4D3}",notebook_with_decorative_cover:"\u{1F4D4}",ledger:"\u{1F4D2}",closed_book:"\u{1F4D5}",green_book:"\u{1F4D7}",blue_book:"\u{1F4D8}",orange_book:"\u{1F4D9}",books:"\u{1F4DA}",book:"\u{1F4D6}",open_book:"\u{1F4D6}",bookmark:"\u{1F516}",link:"\u{1F517}",paperclip:"\u{1F4CE}",paperclips:"\u{1F587}",triangular_ruler:"\u{1F4D0}",straight_ruler:"\u{1F4CF}",pushpin:"\u{1F4CC}",round_pushpin:"\u{1F4CD}",scissors:"\u2702\uFE0F",pen:"\u{1F58A}",fountain_pen:"\u{1F58B}",black_nib:"\u2712\uFE0F",paintbrush:"\u{1F58C}",crayon:"\u{1F58D}",memo:"\u{1F4DD}",pencil:"\u{1F4DD}",pencil2:"\u270F\uFE0F",mag:"\u{1F50D}",mag_right:"\u{1F50E}",lock_with_ink_pen:"\u{1F50F}",closed_lock_with_key:"\u{1F510}",lock:"\u{1F512}",unlock:"\u{1F513}",heart:"\u2764\uFE0F",yellow_heart:"\u{1F49B}",green_heart:"\u{1F49A}",blue_heart:"\u{1F499}",purple_heart:"\u{1F49C}",black_heart:"\u{1F5A4}",broken_heart:"\u{1F494}",heavy_heart_exclamation:"\u2763\uFE0F",two_hearts:"\u{1F495}",revolving_hearts:"\u{1F49E}",heartbeat:"\u{1F493}",heartpulse:"\u{1F497}",sparkling_heart:"\u{1F496}",cupid:"\u{1F498}",gift_heart:"\u{1F49D}",heart_decoration:"\u{1F49F}",peace_symbol:"\u262E\uFE0F",latin_cross:"\u271D\uFE0F",star_and_crescent:"\u262A\uFE0F",om:"\u{1F549}",wheel_of_dharma:"\u2638\uFE0F",star_of_david:"\u2721\uFE0F",six_pointed_star:"\u{1F52F}",menorah:"\u{1F54E}",yin_yang:"\u262F\uFE0F",orthodox_cross:"\u2626\uFE0F",place_of_worship:"\u{1F6D0}",ophiuchus:"\u26CE",aries:"\u2648\uFE0F",taurus:"\u2649\uFE0F",gemini:"\u264A\uFE0F",cancer:"\u264B\uFE0F",leo:"\u264C\uFE0F",virgo:"\u264D\uFE0F",libra:"\u264E\uFE0F",scorpius:"\u264F\uFE0F",sagittarius:"\u2650\uFE0F",capricorn:"\u2651\uFE0F",aquarius:"\u2652\uFE0F",pisces:"\u2653\uFE0F",id:"\u{1F194}",atom_symbol:"\u269B\uFE0F",accept:"\u{1F251}",radioactive:"\u2622\uFE0F",biohazard:"\u2623\uFE0F",mobile_phone_off:"\u{1F4F4}",vibration_mode:"\u{1F4F3}",eight_pointed_black_star:"\u2734\uFE0F",vs:"\u{1F19A}",white_flower:"\u{1F4AE}",ideograph_advantage:"\u{1F250}",secret:"\u3299\uFE0F",congratulations:"\u3297\uFE0F",u6e80:"\u{1F235}",a:"\u{1F170}\uFE0F",b:"\u{1F171}\uFE0F",ab:"\u{1F18E}",cl:"\u{1F191}",o2:"\u{1F17E}\uFE0F",sos:"\u{1F198}",x:"\u274C",o:"\u2B55\uFE0F",stop_sign:"\u{1F6D1}",no_entry:"\u26D4\uFE0F",name_badge:"\u{1F4DB}",no_entry_sign:"\u{1F6AB}",anger:"\u{1F4A2}",hotsprings:"\u2668\uFE0F",no_pedestrians:"\u{1F6B7}",do_not_litter:"\u{1F6AF}",no_bicycles:"\u{1F6B3}","non-potable_water":"\u{1F6B1}",underage:"\u{1F51E}",no_mobile_phones:"\u{1F4F5}",no_smoking:"\u{1F6AD}",exclamation:"\u2757\uFE0F",heavy_exclamation_mark:"\u2757\uFE0F",grey_exclamation:"\u2755",question:"\u2753",grey_question:"\u2754",bangbang:"\u203C\uFE0F",interrobang:"\u2049\uFE0F",low_brightness:"\u{1F505}",high_brightness:"\u{1F506}",part_alternation_mark:"\u303D\uFE0F",warning:"\u26A0\uFE0F",children_crossing:"\u{1F6B8}",trident:"\u{1F531}",fleur_de_lis:"\u269C\uFE0F",beginner:"\u{1F530}",recycle:"\u267B\uFE0F",white_check_mark:"\u2705",chart:"\u{1F4B9}",sparkle:"\u2747\uFE0F",eight_spoked_asterisk:"\u2733\uFE0F",negative_squared_cross_mark:"\u274E",globe_with_meridians:"\u{1F310}",diamond_shape_with_a_dot_inside:"\u{1F4A0}",m:"\u24C2\uFE0F",cyclone:"\u{1F300}",zzz:"\u{1F4A4}",atm:"\u{1F3E7}",wc:"\u{1F6BE}",wheelchair:"\u267F\uFE0F",parking:"\u{1F17F}\uFE0F",sa:"\u{1F202}\uFE0F",passport_control:"\u{1F6C2}",customs:"\u{1F6C3}",baggage_claim:"\u{1F6C4}",left_luggage:"\u{1F6C5}",mens:"\u{1F6B9}",womens:"\u{1F6BA}",baby_symbol:"\u{1F6BC}",restroom:"\u{1F6BB}",put_litter_in_its_place:"\u{1F6AE}",cinema:"\u{1F3A6}",signal_strength:"\u{1F4F6}",koko:"\u{1F201}",symbols:"\u{1F523}",information_source:"\u2139\uFE0F",abc:"\u{1F524}",abcd:"\u{1F521}",capital_abcd:"\u{1F520}",ng:"\u{1F196}",ok:"\u{1F197}",up:"\u{1F199}",cool:"\u{1F192}",new:"\u{1F195}",free:"\u{1F193}",zero:"0\uFE0F\u20E3",one:"1\uFE0F\u20E3",two:"2\uFE0F\u20E3",three:"3\uFE0F\u20E3",four:"4\uFE0F\u20E3",five:"5\uFE0F\u20E3",six:"6\uFE0F\u20E3",seven:"7\uFE0F\u20E3",eight:"8\uFE0F\u20E3",nine:"9\uFE0F\u20E3",keycap_ten:"\u{1F51F}",hash:"#\uFE0F\u20E3",asterisk:"*\uFE0F\u20E3",arrow_forward:"\u25B6\uFE0F",pause_button:"\u23F8",play_or_pause_button:"\u23EF",stop_button:"\u23F9",record_button:"\u23FA",next_track_button:"\u23ED",previous_track_button:"\u23EE",fast_forward:"\u23E9",rewind:"\u23EA",arrow_double_up:"\u23EB",arrow_double_down:"\u23EC",arrow_backward:"\u25C0\uFE0F",arrow_up_small:"\u{1F53C}",arrow_down_small:"\u{1F53D}",arrow_right:"\u27A1\uFE0F",arrow_left:"\u2B05\uFE0F",arrow_up:"\u2B06\uFE0F",arrow_down:"\u2B07\uFE0F",arrow_upper_right:"\u2197\uFE0F",arrow_lower_right:"\u2198\uFE0F",arrow_lower_left:"\u2199\uFE0F",arrow_upper_left:"\u2196\uFE0F",arrow_up_down:"\u2195\uFE0F",left_right_arrow:"\u2194\uFE0F",arrow_right_hook:"\u21AA\uFE0F",leftwards_arrow_with_hook:"\u21A9\uFE0F",arrow_heading_up:"\u2934\uFE0F",arrow_heading_down:"\u2935\uFE0F",twisted_rightwards_arrows:"\u{1F500}",repeat:"\u{1F501}",repeat_one:"\u{1F502}",arrows_counterclockwise:"\u{1F504}",arrows_clockwise:"\u{1F503}",musical_note:"\u{1F3B5}",notes:"\u{1F3B6}",heavy_plus_sign:"\u2795",heavy_minus_sign:"\u2796",heavy_division_sign:"\u2797",heavy_multiplication_x:"\u2716\uFE0F",heavy_dollar_sign:"\u{1F4B2}",currency_exchange:"\u{1F4B1}",tm:"\u2122\uFE0F",copyright:"\xA9\uFE0F",registered:"\xAE\uFE0F",wavy_dash:"\u3030\uFE0F",curly_loop:"\u27B0",loop:"\u27BF",end:"\u{1F51A}",back:"\u{1F519}",on:"\u{1F51B}",top:"\u{1F51D}",soon:"\u{1F51C}",heavy_check_mark:"\u2714\uFE0F",ballot_box_with_check:"\u2611\uFE0F",radio_button:"\u{1F518}",white_circle:"\u26AA\uFE0F",black_circle:"\u26AB\uFE0F",red_circle:"\u{1F534}",large_blue_circle:"\u{1F535}",small_red_triangle:"\u{1F53A}",small_red_triangle_down:"\u{1F53B}",small_orange_diamond:"\u{1F538}",small_blue_diamond:"\u{1F539}",large_orange_diamond:"\u{1F536}",large_blue_diamond:"\u{1F537}",white_square_button:"\u{1F533}",black_square_button:"\u{1F532}",black_small_square:"\u25AA\uFE0F",white_small_square:"\u25AB\uFE0F",black_medium_small_square:"\u25FE\uFE0F",white_medium_small_square:"\u25FD\uFE0F",black_medium_square:"\u25FC\uFE0F",white_medium_square:"\u25FB\uFE0F",black_large_square:"\u2B1B\uFE0F",white_large_square:"\u2B1C\uFE0F",speaker:"\u{1F508}",mute:"\u{1F507}",sound:"\u{1F509}",loud_sound:"\u{1F50A}",bell:"\u{1F514}",no_bell:"\u{1F515}",mega:"\u{1F4E3}",loudspeaker:"\u{1F4E2}",eye_speech_bubble:"\u{1F441}\u200D\u{1F5E8}",speech_balloon:"\u{1F4AC}",thought_balloon:"\u{1F4AD}",right_anger_bubble:"\u{1F5EF}",spades:"\u2660\uFE0F",clubs:"\u2663\uFE0F",hearts:"\u2665\uFE0F",diamonds:"\u2666\uFE0F",black_joker:"\u{1F0CF}",flower_playing_cards:"\u{1F3B4}",mahjong:"\u{1F004}\uFE0F",clock1:"\u{1F550}",clock2:"\u{1F551}",clock3:"\u{1F552}",clock4:"\u{1F553}",clock5:"\u{1F554}",clock6:"\u{1F555}",clock7:"\u{1F556}",clock8:"\u{1F557}",clock9:"\u{1F558}",clock10:"\u{1F559}",clock11:"\u{1F55A}",clock12:"\u{1F55B}",clock130:"\u{1F55C}",clock230:"\u{1F55D}",clock330:"\u{1F55E}",clock430:"\u{1F55F}",clock530:"\u{1F560}",clock630:"\u{1F561}",clock730:"\u{1F562}",clock830:"\u{1F563}",clock930:"\u{1F564}",clock1030:"\u{1F565}",clock1130:"\u{1F566}",clock1230:"\u{1F567}",white_flag:"\u{1F3F3}\uFE0F",black_flag:"\u{1F3F4}",checkered_flag:"\u{1F3C1}",triangular_flag_on_post:"\u{1F6A9}",rainbow_flag:"\u{1F3F3}\uFE0F\u200D\u{1F308}",afghanistan:"\u{1F1E6}\u{1F1EB}",aland_islands:"\u{1F1E6}\u{1F1FD}",albania:"\u{1F1E6}\u{1F1F1}",algeria:"\u{1F1E9}\u{1F1FF}",american_samoa:"\u{1F1E6}\u{1F1F8}",andorra:"\u{1F1E6}\u{1F1E9}",angola:"\u{1F1E6}\u{1F1F4}",anguilla:"\u{1F1E6}\u{1F1EE}",antarctica:"\u{1F1E6}\u{1F1F6}",antigua_barbuda:"\u{1F1E6}\u{1F1EC}",argentina:"\u{1F1E6}\u{1F1F7}",armenia:"\u{1F1E6}\u{1F1F2}",aruba:"\u{1F1E6}\u{1F1FC}",australia:"\u{1F1E6}\u{1F1FA}",austria:"\u{1F1E6}\u{1F1F9}",azerbaijan:"\u{1F1E6}\u{1F1FF}",bahamas:"\u{1F1E7}\u{1F1F8}",bahrain:"\u{1F1E7}\u{1F1ED}",bangladesh:"\u{1F1E7}\u{1F1E9}",barbados:"\u{1F1E7}\u{1F1E7}",belarus:"\u{1F1E7}\u{1F1FE}",belgium:"\u{1F1E7}\u{1F1EA}",belize:"\u{1F1E7}\u{1F1FF}",benin:"\u{1F1E7}\u{1F1EF}",bermuda:"\u{1F1E7}\u{1F1F2}",bhutan:"\u{1F1E7}\u{1F1F9}",bolivia:"\u{1F1E7}\u{1F1F4}",caribbean_netherlands:"\u{1F1E7}\u{1F1F6}",bosnia_herzegovina:"\u{1F1E7}\u{1F1E6}",botswana:"\u{1F1E7}\u{1F1FC}",brazil:"\u{1F1E7}\u{1F1F7}",british_indian_ocean_territory:"\u{1F1EE}\u{1F1F4}",british_virgin_islands:"\u{1F1FB}\u{1F1EC}",brunei:"\u{1F1E7}\u{1F1F3}",bulgaria:"\u{1F1E7}\u{1F1EC}",burkina_faso:"\u{1F1E7}\u{1F1EB}",burundi:"\u{1F1E7}\u{1F1EE}",cape_verde:"\u{1F1E8}\u{1F1FB}",cambodia:"\u{1F1F0}\u{1F1ED}",cameroon:"\u{1F1E8}\u{1F1F2}",canada:"\u{1F1E8}\u{1F1E6}",canary_islands:"\u{1F1EE}\u{1F1E8}",cayman_islands:"\u{1F1F0}\u{1F1FE}",central_african_republic:"\u{1F1E8}\u{1F1EB}",chad:"\u{1F1F9}\u{1F1E9}",chile:"\u{1F1E8}\u{1F1F1}",cn:"\u{1F1E8}\u{1F1F3}",christmas_island:"\u{1F1E8}\u{1F1FD}",cocos_islands:"\u{1F1E8}\u{1F1E8}",colombia:"\u{1F1E8}\u{1F1F4}",comoros:"\u{1F1F0}\u{1F1F2}",congo_brazzaville:"\u{1F1E8}\u{1F1EC}",congo_kinshasa:"\u{1F1E8}\u{1F1E9}",cook_islands:"\u{1F1E8}\u{1F1F0}",costa_rica:"\u{1F1E8}\u{1F1F7}",cote_divoire:"\u{1F1E8}\u{1F1EE}",croatia:"\u{1F1ED}\u{1F1F7}",cuba:"\u{1F1E8}\u{1F1FA}",curacao:"\u{1F1E8}\u{1F1FC}",cyprus:"\u{1F1E8}\u{1F1FE}",czech_republic:"\u{1F1E8}\u{1F1FF}",denmark:"\u{1F1E9}\u{1F1F0}",djibouti:"\u{1F1E9}\u{1F1EF}",dominica:"\u{1F1E9}\u{1F1F2}",dominican_republic:"\u{1F1E9}\u{1F1F4}",ecuador:"\u{1F1EA}\u{1F1E8}",egypt:"\u{1F1EA}\u{1F1EC}",el_salvador:"\u{1F1F8}\u{1F1FB}",equatorial_guinea:"\u{1F1EC}\u{1F1F6}",eritrea:"\u{1F1EA}\u{1F1F7}",estonia:"\u{1F1EA}\u{1F1EA}",ethiopia:"\u{1F1EA}\u{1F1F9}",eu:"\u{1F1EA}\u{1F1FA}",european_union:"\u{1F1EA}\u{1F1FA}",falkland_islands:"\u{1F1EB}\u{1F1F0}",faroe_islands:"\u{1F1EB}\u{1F1F4}",fiji:"\u{1F1EB}\u{1F1EF}",finland:"\u{1F1EB}\u{1F1EE}",fr:"\u{1F1EB}\u{1F1F7}",french_guiana:"\u{1F1EC}\u{1F1EB}",french_polynesia:"\u{1F1F5}\u{1F1EB}",french_southern_territories:"\u{1F1F9}\u{1F1EB}",gabon:"\u{1F1EC}\u{1F1E6}",gambia:"\u{1F1EC}\u{1F1F2}",georgia:"\u{1F1EC}\u{1F1EA}",de:"\u{1F1E9}\u{1F1EA}",ghana:"\u{1F1EC}\u{1F1ED}",gibraltar:"\u{1F1EC}\u{1F1EE}",greece:"\u{1F1EC}\u{1F1F7}",greenland:"\u{1F1EC}\u{1F1F1}",grenada:"\u{1F1EC}\u{1F1E9}",guadeloupe:"\u{1F1EC}\u{1F1F5}",guam:"\u{1F1EC}\u{1F1FA}",guatemala:"\u{1F1EC}\u{1F1F9}",guernsey:"\u{1F1EC}\u{1F1EC}",guinea:"\u{1F1EC}\u{1F1F3}",guinea_bissau:"\u{1F1EC}\u{1F1FC}",guyana:"\u{1F1EC}\u{1F1FE}",haiti:"\u{1F1ED}\u{1F1F9}",honduras:"\u{1F1ED}\u{1F1F3}",hong_kong:"\u{1F1ED}\u{1F1F0}",hungary:"\u{1F1ED}\u{1F1FA}",iceland:"\u{1F1EE}\u{1F1F8}",india:"\u{1F1EE}\u{1F1F3}",indonesia:"\u{1F1EE}\u{1F1E9}",iran:"\u{1F1EE}\u{1F1F7}",iraq:"\u{1F1EE}\u{1F1F6}",ireland:"\u{1F1EE}\u{1F1EA}",isle_of_man:"\u{1F1EE}\u{1F1F2}",israel:"\u{1F1EE}\u{1F1F1}",it:"\u{1F1EE}\u{1F1F9}",jamaica:"\u{1F1EF}\u{1F1F2}",jp:"\u{1F1EF}\u{1F1F5}",crossed_flags:"\u{1F38C}",jersey:"\u{1F1EF}\u{1F1EA}",jordan:"\u{1F1EF}\u{1F1F4}",kazakhstan:"\u{1F1F0}\u{1F1FF}",kenya:"\u{1F1F0}\u{1F1EA}",kiribati:"\u{1F1F0}\u{1F1EE}",kosovo:"\u{1F1FD}\u{1F1F0}",kuwait:"\u{1F1F0}\u{1F1FC}",kyrgyzstan:"\u{1F1F0}\u{1F1EC}",laos:"\u{1F1F1}\u{1F1E6}",latvia:"\u{1F1F1}\u{1F1FB}",lebanon:"\u{1F1F1}\u{1F1E7}",lesotho:"\u{1F1F1}\u{1F1F8}",liberia:"\u{1F1F1}\u{1F1F7}",libya:"\u{1F1F1}\u{1F1FE}",liechtenstein:"\u{1F1F1}\u{1F1EE}",lithuania:"\u{1F1F1}\u{1F1F9}",luxembourg:"\u{1F1F1}\u{1F1FA}",macau:"\u{1F1F2}\u{1F1F4}",macedonia:"\u{1F1F2}\u{1F1F0}",madagascar:"\u{1F1F2}\u{1F1EC}",malawi:"\u{1F1F2}\u{1F1FC}",malaysia:"\u{1F1F2}\u{1F1FE}",maldives:"\u{1F1F2}\u{1F1FB}",mali:"\u{1F1F2}\u{1F1F1}",malta:"\u{1F1F2}\u{1F1F9}",marshall_islands:"\u{1F1F2}\u{1F1ED}",martinique:"\u{1F1F2}\u{1F1F6}",mauritania:"\u{1F1F2}\u{1F1F7}",mauritius:"\u{1F1F2}\u{1F1FA}",mayotte:"\u{1F1FE}\u{1F1F9}",mexico:"\u{1F1F2}\u{1F1FD}",micronesia:"\u{1F1EB}\u{1F1F2}",moldova:"\u{1F1F2}\u{1F1E9}",monaco:"\u{1F1F2}\u{1F1E8}",mongolia:"\u{1F1F2}\u{1F1F3}",montenegro:"\u{1F1F2}\u{1F1EA}",montserrat:"\u{1F1F2}\u{1F1F8}",morocco:"\u{1F1F2}\u{1F1E6}",mozambique:"\u{1F1F2}\u{1F1FF}",myanmar:"\u{1F1F2}\u{1F1F2}",namibia:"\u{1F1F3}\u{1F1E6}",nauru:"\u{1F1F3}\u{1F1F7}",nepal:"\u{1F1F3}\u{1F1F5}",netherlands:"\u{1F1F3}\u{1F1F1}",new_caledonia:"\u{1F1F3}\u{1F1E8}",new_zealand:"\u{1F1F3}\u{1F1FF}",nicaragua:"\u{1F1F3}\u{1F1EE}",niger:"\u{1F1F3}\u{1F1EA}",nigeria:"\u{1F1F3}\u{1F1EC}",niue:"\u{1F1F3}\u{1F1FA}",norfolk_island:"\u{1F1F3}\u{1F1EB}",northern_mariana_islands:"\u{1F1F2}\u{1F1F5}",north_korea:"\u{1F1F0}\u{1F1F5}",norway:"\u{1F1F3}\u{1F1F4}",oman:"\u{1F1F4}\u{1F1F2}",pakistan:"\u{1F1F5}\u{1F1F0}",palau:"\u{1F1F5}\u{1F1FC}",palestinian_territories:"\u{1F1F5}\u{1F1F8}",panama:"\u{1F1F5}\u{1F1E6}",papua_new_guinea:"\u{1F1F5}\u{1F1EC}",paraguay:"\u{1F1F5}\u{1F1FE}",peru:"\u{1F1F5}\u{1F1EA}",philippines:"\u{1F1F5}\u{1F1ED}",pitcairn_islands:"\u{1F1F5}\u{1F1F3}",poland:"\u{1F1F5}\u{1F1F1}",portugal:"\u{1F1F5}\u{1F1F9}",puerto_rico:"\u{1F1F5}\u{1F1F7}",qatar:"\u{1F1F6}\u{1F1E6}",reunion:"\u{1F1F7}\u{1F1EA}",romania:"\u{1F1F7}\u{1F1F4}",ru:"\u{1F1F7}\u{1F1FA}",rwanda:"\u{1F1F7}\u{1F1FC}",st_barthelemy:"\u{1F1E7}\u{1F1F1}",st_helena:"\u{1F1F8}\u{1F1ED}",st_kitts_nevis:"\u{1F1F0}\u{1F1F3}",st_lucia:"\u{1F1F1}\u{1F1E8}",st_pierre_miquelon:"\u{1F1F5}\u{1F1F2}",st_vincent_grenadines:"\u{1F1FB}\u{1F1E8}",samoa:"\u{1F1FC}\u{1F1F8}",san_marino:"\u{1F1F8}\u{1F1F2}",sao_tome_principe:"\u{1F1F8}\u{1F1F9}",saudi_arabia:"\u{1F1F8}\u{1F1E6}",senegal:"\u{1F1F8}\u{1F1F3}",serbia:"\u{1F1F7}\u{1F1F8}",seychelles:"\u{1F1F8}\u{1F1E8}",sierra_leone:"\u{1F1F8}\u{1F1F1}",singapore:"\u{1F1F8}\u{1F1EC}",sint_maarten:"\u{1F1F8}\u{1F1FD}",slovakia:"\u{1F1F8}\u{1F1F0}",slovenia:"\u{1F1F8}\u{1F1EE}",solomon_islands:"\u{1F1F8}\u{1F1E7}",somalia:"\u{1F1F8}\u{1F1F4}",south_africa:"\u{1F1FF}\u{1F1E6}",south_georgia_south_sandwich_islands:"\u{1F1EC}\u{1F1F8}",kr:"\u{1F1F0}\u{1F1F7}",south_sudan:"\u{1F1F8}\u{1F1F8}",es:"\u{1F1EA}\u{1F1F8}",sri_lanka:"\u{1F1F1}\u{1F1F0}",sudan:"\u{1F1F8}\u{1F1E9}",suriname:"\u{1F1F8}\u{1F1F7}",swaziland:"\u{1F1F8}\u{1F1FF}",sweden:"\u{1F1F8}\u{1F1EA}",switzerland:"\u{1F1E8}\u{1F1ED}",syria:"\u{1F1F8}\u{1F1FE}",taiwan:"\u{1F1F9}\u{1F1FC}",tajikistan:"\u{1F1F9}\u{1F1EF}",tanzania:"\u{1F1F9}\u{1F1FF}",thailand:"\u{1F1F9}\u{1F1ED}",timor_leste:"\u{1F1F9}\u{1F1F1}",togo:"\u{1F1F9}\u{1F1EC}",tokelau:"\u{1F1F9}\u{1F1F0}",tonga:"\u{1F1F9}\u{1F1F4}",trinidad_tobago:"\u{1F1F9}\u{1F1F9}",tunisia:"\u{1F1F9}\u{1F1F3}",tr:"\u{1F1F9}\u{1F1F7}",turkmenistan:"\u{1F1F9}\u{1F1F2}",turks_caicos_islands:"\u{1F1F9}\u{1F1E8}",tuvalu:"\u{1F1F9}\u{1F1FB}",uganda:"\u{1F1FA}\u{1F1EC}",ukraine:"\u{1F1FA}\u{1F1E6}",united_arab_emirates:"\u{1F1E6}\u{1F1EA}",gb:"\u{1F1EC}\u{1F1E7}",uk:"\u{1F1EC}\u{1F1E7}",us:"\u{1F1FA}\u{1F1F8}",us_virgin_islands:"\u{1F1FB}\u{1F1EE}",uruguay:"\u{1F1FA}\u{1F1FE}",uzbekistan:"\u{1F1FA}\u{1F1FF}",vanuatu:"\u{1F1FB}\u{1F1FA}",vatican_city:"\u{1F1FB}\u{1F1E6}",venezuela:"\u{1F1FB}\u{1F1EA}",vietnam:"\u{1F1FB}\u{1F1F3}",wallis_futuna:"\u{1F1FC}\u{1F1EB}",western_sahara:"\u{1F1EA}\u{1F1ED}",yemen:"\u{1F1FE}\u{1F1EA}",zambia:"\u{1F1FF}\u{1F1F2}",zimbabwe:"\u{1F1FF}\u{1F1FC}"}},function(c,_,i){c.exports={angry:[">:(",">:-("],blush:[':")',':-")'],broken_heart:["</3","<\\3"],confused:[":/",":-/"],cry:[":'(",":'-(",":,(",":,-("],frowning:[":(",":-("],heart:["<3"],imp:["]:(","]:-("],innocent:["o:)","O:)","o:-)","O:-)","0:)","0:-)"],joy:[":')",":'-)",":,)",":,-)",":'D",":'-D",":,D",":,-D"],kissing:[":*",":-*"],laughing:["x-)","X-)"],neutral_face:[":|",":-|"],open_mouth:[":o",":-o",":O",":-O"],rage:[":@",":-@"],smile:[":D",":-D"],smiley:[":)",":-)"],smiling_imp:["]:)","]:-)"],sob:[":,'(",":,'-(",";(",";-("],stuck_out_tongue:[":P",":-P"],sunglasses:["8-)","B-)"],sweat:[",:(",",:-("],sweat_smile:[",:)",",:-)"],unamused:[":s",":-S",":z",":-Z",":$",":-$"],wink:[";)",";-)"]}},function(c,_,i){function e(o){return o.replace(/[.?*+^$[\]\\(){}|-]/g,"\\$&")}c.exports=function(o){var n,r=o.defs;o.enabled.length&&(r=Object.keys(r).reduce(function(p,s){return o.enabled.indexOf(s)>=0&&(p[s]=r[s]),p},{})),n=Object.keys(o.shortcuts).reduce(function(p,s){return r[s]?Array.isArray(o.shortcuts[s])?(o.shortcuts[s].forEach(function(u){p[u]=s}),p):(p[o.shortcuts[s]]=s,p):p},{});var t=Object.keys(r).map(function(p){return":"+p+":"}).concat(Object.keys(n)).sort().reverse().map(function(p){return e(p)}).join("|"),a=RegExp(t),l=RegExp(t,"g");return{defs:r,shortcuts:n,scanRE:a,replaceRE:l}}},function(c,_,i){c.exports=function(e,o){return e[o].content}},function(c,_,i){c.exports=function(e,o,n,r,t){function a(u,d,v){var f,y=0,m=[];return u.replace(t,function(h,w,g){var k;if(n.hasOwnProperty(h)){if(k=n[h],w>0&&!s.test(g[w-1])||w+h.length<g.length&&!s.test(g[w+h.length]))return}else k=h.slice(1,-1);w>y&&(f=new v("text","",0),f.content=u.slice(y,w),m.push(f)),f=new v("emoji","",0),f.markup=k,f.content=o[k],m.push(f),y=w+h.length}),y<u.length&&(f=new v("text","",0),f.content=u.slice(y),m.push(f)),m}var l=e.utils.arrayReplaceAt,p=e.utils.lib.ucmicro,s=new RegExp([p.Z.source,p.P.source,p.Cc.source].join("|"));return function(u){var d,v,f,y,m,h=u.tokens,w=0;for(v=0,f=h.length;v<f;v++)if(h[v].type==="inline")for(y=h[v].children,d=y.length-1;d>=0;d--)m=y[d],m.type!=="link_open"&&m.type!=="link_close"||m.info==="auto"&&(w-=m.nesting),m.type==="text"&&w===0&&r.test(m.content)&&(h[v].children=y=l(y,d,a(m.content,m.level,u.Token)))}}},function(c,_,i){function e(s,u,d,v){var f=Number(s[u].meta.id+1).toString(),y="";return typeof v.docId=="string"&&(y="-"+v.docId+"-"),y+f}function o(s,u){var d=Number(s[u].meta.id+1).toString();return s[u].meta.subId>0&&(d+=":"+s[u].meta.subId),"["+d+"]"}function n(s,u,d,v,f){var y=f.rules.footnote_anchor_name(s,u,d,v,f),m=f.rules.footnote_caption(s,u,d,v,f),h=y;return s[u].meta.subId>0&&(h+=":"+s[u].meta.subId),'<sup class="footnote-ref"><a href="#fn'+y+'" id="fnref'+h+'">'+m+"</a></sup>"}function r(s,u,d){return(d.xhtmlOut?`<hr class="footnotes-sep" />
`:`<hr class="footnotes-sep">
`)+`<section class="footnotes">
<ol class="footnotes-list">
`}function t(){return`</ol>
</section>
`}function a(s,u,d,v,f){var y=f.rules.footnote_anchor_name(s,u,d,v,f);return s[u].meta.subId>0&&(y+=":"+s[u].meta.subId),'<li id="fn'+y+'" class="footnote-item">'}function l(){return`</li>
`}function p(s,u,d,v,f){var y=f.rules.footnote_anchor_name(s,u,d,v,f);return s[u].meta.subId>0&&(y+=":"+s[u].meta.subId),' <a href="#fnref'+y+'" class="footnote-backref">\u21A9\uFE0E</a>'}c.exports=function(s){function u(h,w,g,k){var b,C,x,E,D,A,S,T,I,$,q,M=h.bMarks[w]+h.tShift[w],z=h.eMarks[w];if(M+4>z||h.src.charCodeAt(M)!==91||h.src.charCodeAt(M+1)!==94)return!1;for(D=M+2;D<z;D++){if(h.src.charCodeAt(D)===32)return!1;if(h.src.charCodeAt(D)===93)break}if(D===M+2||D+1>=z||h.src.charCodeAt(++D)!==58)return!1;if(k)return!0;for(D++,h.env.footnotes||(h.env.footnotes={}),h.env.footnotes.refs||(h.env.footnotes.refs={}),A=h.src.slice(M+2,D-2),h.env.footnotes.refs[":"+A]=-1,S=new h.Token("footnote_reference_open","",1),S.meta={label:A},S.level=h.level++,h.tokens.push(S),b=h.bMarks[w],C=h.tShift[w],x=h.sCount[w],E=h.parentType,q=D,T=I=h.sCount[w]+D-(h.bMarks[w]+h.tShift[w]);D<z&&($=h.src.charCodeAt(D),m($));)$===9?I+=4-I%4:I++,D++;return h.tShift[w]=D-q,h.sCount[w]=I-T,h.bMarks[w]=q,h.blkIndent+=4,h.parentType="footnote",h.sCount[w]<h.blkIndent&&(h.sCount[w]+=h.blkIndent),h.md.block.tokenize(h,w,g,!0),h.parentType=E,h.blkIndent-=4,h.tShift[w]=C,h.sCount[w]=x,h.bMarks[w]=b,S=new h.Token("footnote_reference_close","",-1),S.level=--h.level,h.tokens.push(S),!0}function d(h,w){var g,k,b,C,x,E=h.posMax,D=h.pos;return!(D+2>=E)&&h.src.charCodeAt(D)===94&&h.src.charCodeAt(D+1)===91&&(g=D+2,!((k=y(h,D+1))<0)&&(w||(h.env.footnotes||(h.env.footnotes={}),h.env.footnotes.list||(h.env.footnotes.list=[]),b=h.env.footnotes.list.length,h.md.inline.parse(h.src.slice(g,k),h.md,h.env,x=[]),C=h.push("footnote_ref","",0),C.meta={id:b},h.env.footnotes.list[b]={content:h.src.slice(g,k),tokens:x}),h.pos=k+1,h.posMax=E,!0))}function v(h,w){var g,k,b,C,x,E=h.posMax,D=h.pos;if(D+3>E||!h.env.footnotes||!h.env.footnotes.refs||h.src.charCodeAt(D)!==91||h.src.charCodeAt(D+1)!==94)return!1;for(k=D+2;k<E;k++){if(h.src.charCodeAt(k)===32||h.src.charCodeAt(k)===10)return!1;if(h.src.charCodeAt(k)===93)break}return k!==D+2&&!(k>=E)&&(k++,g=h.src.slice(D+2,k-1),h.env.footnotes.refs[":"+g]!==void 0&&(w||(h.env.footnotes.list||(h.env.footnotes.list=[]),h.env.footnotes.refs[":"+g]<0?(b=h.env.footnotes.list.length,h.env.footnotes.list[b]={label:g,count:0},h.env.footnotes.refs[":"+g]=b):b=h.env.footnotes.refs[":"+g],C=h.env.footnotes.list[b].count,h.env.footnotes.list[b].count++,x=h.push("footnote_ref","",0),x.meta={id:b,subId:C,label:g}),h.pos=k,h.posMax=E,!0))}function f(h){var w,g,k,b,C,x,E,D,A,S,T=!1,I={};if(h.env.footnotes&&(h.tokens=h.tokens.filter(function($){return $.type==="footnote_reference_open"?(T=!0,A=[],S=$.meta.label,!1):$.type==="footnote_reference_close"?(T=!1,I[":"+S]=A,!1):(T&&A.push($),!T)}),h.env.footnotes.list)){for(x=h.env.footnotes.list,E=new h.Token("footnote_block_open","",1),h.tokens.push(E),w=0,g=x.length;w<g;w++){for(E=new h.Token("footnote_open","",1),E.meta={id:w,label:x[w].label},h.tokens.push(E),x[w].tokens?(D=[],E=new h.Token("paragraph_open","p",1),E.block=!0,D.push(E),E=new h.Token("inline","",0),E.children=x[w].tokens,E.content=x[w].content,D.push(E),E=new h.Token("paragraph_close","p",-1),E.block=!0,D.push(E)):x[w].label&&(D=I[":"+x[w].label]),h.tokens=h.tokens.concat(D),C=h.tokens[h.tokens.length-1].type==="paragraph_close"?h.tokens.pop():null,b=x[w].count>0?x[w].count:1,k=0;k<b;k++)E=new h.Token("footnote_anchor","",0),E.meta={id:w,subId:k,label:x[w].label},h.tokens.push(E);C&&h.tokens.push(C),E=new h.Token("footnote_close","",-1),h.tokens.push(E)}E=new h.Token("footnote_block_close","",-1),h.tokens.push(E)}}var y=s.helpers.parseLinkLabel,m=s.utils.isSpace;s.renderer.rules.footnote_ref=n,s.renderer.rules.footnote_block_open=r,s.renderer.rules.footnote_block_close=t,s.renderer.rules.footnote_open=a,s.renderer.rules.footnote_close=l,s.renderer.rules.footnote_anchor=p,s.renderer.rules.footnote_caption=o,s.renderer.rules.footnote_anchor_name=e,s.block.ruler.before("reference","footnote_def",u,{alt:["paragraph","reference"]}),s.inline.ruler.after("image","footnote_inline",d),s.inline.ruler.after("footnote_inline","footnote_ref",v),s.core.ruler.after("inline","footnote_tail",f)}},function(c,_){var i=function(e,o){o=o||{},o.highlighted===void 0&&(o.highlighted=!0),o.hljs===void 0&&(o.hljs="auto"),typeof o.langCheck!="function"&&(o.langCheck=function(){}),e.options.highlight=function(n,r){var t=o.hljs;if(o.hljs==="auto"&&(t=window.hljs),o.highlighted&&r&&t){if(t.getLanguage(r))return'<pre><div class="hljs"><code class="'+e.options.langPrefix+r+'">'+t.highlight(r,n,!0).value+"</code></div></pre>";typeof o.langCheck=="function"&&o.langCheck(r)}return'<pre><code class="'+e.options.langPrefix+r+'">'+e.utils.escapeHtml(n)+"</code></pre>"}};c.exports=i},function(c,_){c.exports=function(i,e){i.image_add=function(n,r){i.__image instanceof Object||(i.__image={}),i.__image[n]=r},i.image_del=function(n){i.__image instanceof Object||(i.__image={}),delete i.__image[n]};var o=i.renderer.rules.image;i.renderer.rules.image=function(n,r,t,a,l){var p=n[r].attrs;if(i.__image instanceof Object){for(var s=0;s<p.length;s++)if(p[s][0]=="src"&&i.__image.hasOwnProperty(n[r].attrs[s][1])){p.push(["rel",p[s][1]]),p[s][1]=i.__image[n[r].attrs[s][1]];break}}return o(n,r,t,a,l)}}},function(c,_,i){c.exports=function(e){function o(r,t){var a,l,p,s,u,d=r.pos,v=r.src.charCodeAt(d);if(t||v!==43||(l=r.scanDelims(r.pos,!0),s=l.length,u=String.fromCharCode(v),s<2))return!1;for(s%2&&(p=r.push("text","",0),p.content=u,s--),a=0;a<s;a+=2)p=r.push("text","",0),p.content=u+u,r.delimiters.push({marker:v,jump:a,token:r.tokens.length-1,level:r.level,end:-1,open:l.can_open,close:l.can_close});return r.pos+=l.length,!0}function n(r){var t,a,l,p,s,u=[],d=r.delimiters,v=r.delimiters.length;for(t=0;t<v;t++)l=d[t],l.marker===43&&l.end!==-1&&(p=d[l.end],s=r.tokens[l.token],s.type="ins_open",s.tag="ins",s.nesting=1,s.markup="++",s.content="",s=r.tokens[p.token],s.type="ins_close",s.tag="ins",s.nesting=-1,s.markup="++",s.content="",r.tokens[p.token-1].type==="text"&&r.tokens[p.token-1].content==="+"&&u.push(p.token-1));for(;u.length;){for(t=u.pop(),a=t+1;a<r.tokens.length&&r.tokens[a].type==="ins_close";)a++;a--,t!==a&&(s=r.tokens[a],r.tokens[a]=r.tokens[t],r.tokens[t]=s)}}e.inline.ruler.before("emphasis","ins",o),e.inline.ruler2.before("emphasis","ins",n)}},function(c,_,i){function e(t,a){var l,p,s=t.posMax,u=!0,d=!0;return l=a>0?t.src.charCodeAt(a-1):-1,p=a+1<=s?t.src.charCodeAt(a+1):-1,(l===32||l===9||p>=48&&p<=57)&&(d=!1),p!==32&&p!==9||(u=!1),{can_open:u,can_close:d}}function o(t,a){if(!r&&window.katex&&(r=window.katex),!r)return!1;var l,p,s,u,d;if(t.src[t.pos]!=="$")return!1;if(u=e(t,t.pos),!u.can_open)return a||(t.pending+="$"),t.pos+=1,!0;for(l=t.pos+1,p=l;(p=t.src.indexOf("$",p))!==-1;){for(d=p-1;t.src[d]==="\\";)d-=1;if((p-d)%2==1)break;p+=1}return p===-1?(a||(t.pending+="$"),t.pos=l,!0):p-l==0?(a||(t.pending+="$$"),t.pos=l+1,!0):(u=e(t,p),u.can_close?(a||(s=t.push("math_inline","math",0),s.markup="$",s.content=t.src.slice(l,p)),t.pos=p+1,!0):(a||(t.pending+="$"),t.pos=l,!0))}function n(t,a,l,p){if(!r&&window.katex&&(r=window.katex),!r)return!1;var s,u,d,v,f,y=!1,m=t.bMarks[a]+t.tShift[a],h=t.eMarks[a];if(m+2>h||t.src.slice(m,m+2)!=="$$")return!1;if(m+=2,s=t.src.slice(m,h),p)return!0;for(s.trim().slice(-2)==="$$"&&(s=s.trim().slice(0,-2),y=!0),d=a;!y&&!(++d>=l)&&(m=t.bMarks[d]+t.tShift[d],h=t.eMarks[d],!(m<h&&t.tShift[d]<t.blkIndent));)t.src.slice(m,h).trim().slice(-2)==="$$"&&(v=t.src.slice(0,h).lastIndexOf("$$"),u=t.src.slice(m,v),y=!0);return t.line=d+1,f=t.push("math_block","math",0),f.block=!0,f.content=(s&&s.trim()?s+`
`:"")+t.getLines(a+1,d,t.tShift[a],!0)+(u&&u.trim()?u:""),f.map=[a,t.line],f.markup="$$",!0}var r=null;c.exports=function(t,a){a=a||{};var l=function(d){!r&&window.katex&&(r=window.katex),a.displayMode=!1;try{return r.renderToString(d,a)}catch(v){return a.throwOnError&&console.log(v),d}},p=function(d,v){return l(d[v].content)},s=function(d){!r&&window.katex&&(r=window.katex),a.displayMode=!0;try{return"<p>"+r.renderToString(d,a)+"</p>"}catch(v){return a.throwOnError&&console.log(v),d}},u=function(d,v){return s(d[v].content)+`
`};t.inline.ruler.after("escape","math_inline",o),t.block.ruler.after("blockquote","math_block",n,{alt:["paragraph","reference","blockquote","list"]}),t.renderer.rules.math_inline=p,t.renderer.rules.math_block=u}},function(c,_,i){c.exports=function(e){function o(r,t){var a,l,p,s,u,d=r.pos,v=r.src.charCodeAt(d);if(t||v!==61||(l=r.scanDelims(r.pos,!0),s=l.length,u=String.fromCharCode(v),s<2))return!1;for(s%2&&(p=r.push("text","",0),p.content=u,s--),a=0;a<s;a+=2)p=r.push("text","",0),p.content=u+u,r.delimiters.push({marker:v,jump:a,token:r.tokens.length-1,level:r.level,end:-1,open:l.can_open,close:l.can_close});return r.pos+=l.length,!0}function n(r){var t,a,l,p,s,u=[],d=r.delimiters,v=r.delimiters.length;for(t=0;t<v;t++)l=d[t],l.marker===61&&l.end!==-1&&(p=d[l.end],s=r.tokens[l.token],s.type="mark_open",s.tag="mark",s.nesting=1,s.markup="==",s.content="",s=r.tokens[p.token],s.type="mark_close",s.tag="mark",s.nesting=-1,s.markup="==",s.content="",r.tokens[p.token-1].type==="text"&&r.tokens[p.token-1].content==="="&&u.push(p.token-1));for(;u.length;){for(t=u.pop(),a=t+1;a<r.tokens.length&&r.tokens[a].type==="mark_close";)a++;a--,t!==a&&(s=r.tokens[a],r.tokens[a]=r.tokens[t],r.tokens[t]=s)}}e.inline.ruler.before("emphasis","mark",o),e.inline.ruler2.before("emphasis","mark",n)}},function(c,_,i){function e(n,r){var t,a,l,p=n.posMax,s=n.pos;if(n.src.charCodeAt(s)!==126||r||s+2>=p)return!1;for(n.pos=s+1;n.pos<p;){if(n.src.charCodeAt(n.pos)===126){t=!0;break}n.md.inline.skipToken(n)}return t&&s+1!==n.pos?(a=n.src.slice(s+1,n.pos),a.match(/(^|[^\\])(\\\\)*\s/)?(n.pos=s,!1):(n.posMax=n.pos,n.pos=s+1,l=n.push("sub_open","sub",1),l.markup="~",l=n.push("text","",0),l.content=a.replace(o,"$1"),l=n.push("sub_close","sub",-1),l.markup="~",n.pos=n.posMax+1,n.posMax=p,!0)):(n.pos=s,!1)}var o=/\\([ \\!"#$%&'()*+,.\/:;<=>?@[\]^_`{|}~-])/g;c.exports=function(n){n.inline.ruler.after("emphasis","sub",e)}},function(c,_,i){function e(n,r){var t,a,l,p=n.posMax,s=n.pos;if(n.src.charCodeAt(s)!==94||r||s+2>=p)return!1;for(n.pos=s+1;n.pos<p;){if(n.src.charCodeAt(n.pos)===94){t=!0;break}n.md.inline.skipToken(n)}return t&&s+1!==n.pos?(a=n.src.slice(s+1,n.pos),a.match(/(^|[^\\])(\\\\)*\s/)?(n.pos=s,!1):(n.posMax=n.pos,n.pos=s+1,l=n.push("sup_open","sup",1),l.markup="^",l=n.push("text","",0),l.content=a.replace(o,"$1"),l=n.push("sup_close","sup",-1),l.markup="^",n.pos=n.posMax+1,n.posMax=p,!0)):(n.pos=s,!1)}var o=/\\([ \\!"#$%&'()*+,.\/:;<=>?@[\]^_`{|}~-])/g;c.exports=function(n){n.inline.ruler.after("emphasis","sup",e)}},function(c,_){function i(m,h,w){var g=m.attrIndex(h),k=[h,w];g<0?m.attrPush(k):m.attrs[g]=k}function e(m,h){for(var w=m[h].level-1,g=h-1;g>=0;g--)if(m[g].level===w)return g;return-1}function o(m,h){return p(m[h])&&s(m[h-1])&&u(m[h-2])&&d(m[h])}function n(m,h){if(m.children.unshift(r(m,h)),m.children[1].content=m.children[1].content.slice(3),m.content=m.content.slice(3),f)if(y){m.children.pop();var w="task-item-"+Math.ceil(1e7*Math.random()-1e3);m.children[0].content=m.children[0].content.slice(0,-1)+' id="'+w+'">',m.children.push(l(m.content,w,h))}else m.children.unshift(t(h)),m.children.push(a(h))}function r(m,h){var w=new h("html_inline","",0),g=v?' disabled="" ':"";return m.content.indexOf("[ ] ")===0?w.content='<input class="task-list-item-checkbox"'+g+'type="checkbox">':m.content.indexOf("[x] ")!==0&&m.content.indexOf("[X] ")!==0||(w.content='<input class="task-list-item-checkbox" checked=""'+g+'type="checkbox">'),w}function t(m){var h=new m("html_inline","",0);return h.content="<label>",h}function a(m){var h=new m("html_inline","",0);return h.content="</label>",h}function l(m,h,w){var g=new w("html_inline","",0);return g.content='<label class="task-list-item-label" for="'+h+'">'+m+"</label>",g.attrs=[{for:h}],g}function p(m){return m.type==="inline"}function s(m){return m.type==="paragraph_open"}function u(m){return m.type==="list_item_open"}function d(m){return m.content.indexOf("[ ] ")===0||m.content.indexOf("[x] ")===0||m.content.indexOf("[X] ")===0}var v=!0,f=!1,y=!1;c.exports=function(m,h){h&&(v=!h.enabled,f=!!h.label,y=!!h.labelAfter),m.core.ruler.after("inline","github-task-lists",function(w){for(var g=w.tokens,k=2;k<g.length;k++)o(g,k)&&(n(g[k],w.Token),i(g[k-2],"class","task-list-item"+(v?"":" enabled")),i(g[e(g,k-2)],"class","contains-task-list"))})}},function(c,_,i){c.exports=function(e){function o(l,p){for(;l.src.indexOf(`
`)>=0&&l.src.indexOf(`
`)<l.src.indexOf("@[toc]");)l.tokens.slice(-1)[0].type==="softbreak"&&(l.src=l.src.split(`
`).slice(1).join(`
`),l.pos=0);var s;if(l.src.charCodeAt(l.pos)!==64||l.src.charCodeAt(l.pos+1)!==91)return!1;var u=r.exec(l.src);if(!u||(u=u.filter(function(y){return y}),u.length<1)||p)return!1;s=l.push("toc_open","toc",1),s.markup="@[toc]",s=l.push("toc_body","",0);var d=t;u.length>1&&(d=u.pop()),s.content=d,s=l.push("toc_close","toc",-1);var v=0,f=l.src.indexOf(`
`);return v=f!==-1?l.pos+f:l.pos+l.posMax+1,l.pos=v,!0}var n,r=/^@\[toc\](?:\((?:\s+)?([^\)]+)(?:\s+)?\)?)?(?:\s+?)?$/im,t="Table of Contents",a=function(l){return l.replace(/[^\w\s]/gi,"").split(" ").join("_")};e.renderer.rules.heading_open=function(l,p){var s=l[p].tag,u=l[p+1];return u.type==="inline"?"<"+s+'><a id="'+(a(u.content)+"_"+u.map[0])+'"></a>':"</h1>"},e.renderer.rules.toc_open=function(l,p){return""},e.renderer.rules.toc_close=function(l,p){return""},e.renderer.rules.toc_body=function(l,p){for(var s=[],u=n.tokens,d=u.length,v=0;v<d;v++)if(u[v].type==="heading_close"){var f=u[v],y=u[v-1];y.type==="inline"&&s.push({level:+f.tag.substr(1,1),anchor:a(y.content)+"_"+y.map[0],content:y.content})}var m=0,h=s.map(function(w){var g=[];if(w.level>m)for(var k=w.level-m,b=0;b<k;b++)g.push("<ul>"),m++;else if(w.level<m)for(var k=m-w.level,b=0;b<k;b++)g.push("</ul>"),m--;return g=g.concat(['<li><a href="#',w.anchor,'">',w.content,"</a></li>"]),g.join("")});return"<h3>"+l[p].content+"</h3>"+h.join("")+new Array(m+1).join("</ul>")},e.core.ruler.push("grab_state",function(l){n=l}),e.inline.ruler.after("emphasis","toc",o)}},function(c,_,i){c.exports=i(142)},function(c,_,i){c.exports=["address","article","aside","base","basefont","blockquote","body","caption","center","col","colgroup","dd","details","dialog","dir","div","dl","dt","fieldset","figcaption","figure","footer","form","frame","frameset","h1","h2","h3","h4","h5","h6","head","header","hr","html","iframe","legend","li","link","main","menu","menuitem","meta","nav","noframes","ol","optgroup","option","p","param","section","source","summary","table","tbody","td","tfoot","th","thead","title","tr","track","ul"]},function(c,_,i){_.parseLinkLabel=i(140),_.parseLinkDestination=i(139),_.parseLinkTitle=i(141)},function(c,_,i){var e=i(0).unescapeAll;c.exports=function(o,n,r){var t,a,l=n,p={ok:!1,pos:0,lines:0,str:""};if(o.charCodeAt(n)===60){for(n++;n<r;){if((t=o.charCodeAt(n))===10)return p;if(t===62)return p.pos=n+1,p.str=e(o.slice(l+1,n)),p.ok=!0,p;t===92&&n+1<r?n+=2:n++}return p}for(a=0;n<r&&(t=o.charCodeAt(n))!==32&&!(t<32||t===127);)if(t===92&&n+1<r)n+=2;else{if(t===40&&a++,t===41){if(a===0)break;a--}n++}return l===n||a!==0||(p.str=e(o.slice(l,n)),p.lines=0,p.pos=n,p.ok=!0),p}},function(c,_,i){c.exports=function(e,o,n){var r,t,a,l,p=-1,s=e.posMax,u=e.pos;for(e.pos=o+1,r=1;e.pos<s;){if((a=e.src.charCodeAt(e.pos))===93&&--r==0){t=!0;break}if(l=e.pos,e.md.inline.skipToken(e),a===91){if(l===e.pos-1)r++;else if(n)return e.pos=u,-1}}return t&&(p=e.pos),e.pos=u,p}},function(c,_,i){var e=i(0).unescapeAll;c.exports=function(o,n,r){var t,a,l=0,p=n,s={ok:!1,pos:0,lines:0,str:""};if(n>=r||(a=o.charCodeAt(n))!==34&&a!==39&&a!==40)return s;for(n++,a===40&&(a=41);n<r;){if((t=o.charCodeAt(n))===a)return s.pos=n+1,s.lines=l,s.str=e(o.slice(p+1,n)),s.ok=!0,s;t===10?l++:t===92&&n+1<r&&(n++,o.charCodeAt(n)===10&&l++),n++}return s}},function(c,_,i){function e(g){var k=g.trim().toLowerCase();return!m.test(k)||!!h.test(k)}function o(g){var k=v.parse(g,!0);if(k.hostname&&(!k.protocol||w.indexOf(k.protocol)>=0))try{k.hostname=f.toASCII(k.hostname)}catch{}return v.encode(v.format(k))}function n(g){var k=v.parse(g,!0);if(k.hostname&&(!k.protocol||w.indexOf(k.protocol)>=0))try{k.hostname=f.toUnicode(k.hostname)}catch{}return v.decode(v.format(k))}function r(g,k){if(!(this instanceof r))return new r(g,k);k||t.isString(g)||(k=g||{},g="default"),this.inline=new u,this.block=new s,this.core=new p,this.renderer=new l,this.linkify=new d,this.validateLink=e,this.normalizeLink=o,this.normalizeLinkText=n,this.utils=t,this.helpers=t.assign({},a),this.options={},this.configure(g),k&&this.set(k)}var t=i(0),a=i(138),l=i(149),p=i(144),s=i(143),u=i(145),d=i(115),v=i(57),f=i(186),y={default:i(147),zero:i(148),commonmark:i(146)},m=/^(vbscript|javascript|file|data):/,h=/^data:image\/(gif|png|jpeg|webp);/,w=["http:","https:","mailto:"];r.prototype.set=function(g){return t.assign(this.options,g),this},r.prototype.configure=function(g){var k,b=this;if(t.isString(g)&&(k=g,!(g=y[k])))throw new Error('Wrong `markdown-it` preset "'+k+'", check name');if(!g)throw new Error("Wrong `markdown-it` preset, can't be empty");return g.options&&b.set(g.options),g.components&&Object.keys(g.components).forEach(function(C){g.components[C].rules&&b[C].ruler.enableOnly(g.components[C].rules),g.components[C].rules2&&b[C].ruler2.enableOnly(g.components[C].rules2)}),this},r.prototype.enable=function(g,k){var b=[];Array.isArray(g)||(g=[g]),["core","block","inline"].forEach(function(x){b=b.concat(this[x].ruler.enable(g,!0))},this),b=b.concat(this.inline.ruler2.enable(g,!0));var C=g.filter(function(x){return b.indexOf(x)<0});if(C.length&&!k)throw new Error("MarkdownIt. Failed to enable unknown rule(s): "+C);return this},r.prototype.disable=function(g,k){var b=[];Array.isArray(g)||(g=[g]),["core","block","inline"].forEach(function(x){b=b.concat(this[x].ruler.disable(g,!0))},this),b=b.concat(this.inline.ruler2.disable(g,!0));var C=g.filter(function(x){return b.indexOf(x)<0});if(C.length&&!k)throw new Error("MarkdownIt. Failed to disable unknown rule(s): "+C);return this},r.prototype.use=function(g){var k=[this].concat(Array.prototype.slice.call(arguments,1));return g.apply(g,k),this},r.prototype.parse=function(g,k){if(typeof g!="string")throw new Error("Input data should be a String");var b=new this.core.State(g,this,k);return this.core.process(b),b.tokens},r.prototype.render=function(g,k){return k=k||{},this.renderer.render(this.parse(g,k),this.options,k)},r.prototype.parseInline=function(g,k){var b=new this.core.State(g,this,k);return b.inlineMode=!0,this.core.process(b),b.tokens},r.prototype.renderInline=function(g,k){return k=k||{},this.renderer.render(this.parseInline(g,k),this.options,k)},c.exports=r},function(c,_,i){function e(){this.ruler=new o;for(var r=0;r<n.length;r++)this.ruler.push(n[r][0],n[r][1],{alt:(n[r][2]||[]).slice()})}var o=i(32),n=[["table",i(161),["paragraph","reference"]],["code",i(151)],["fence",i(152),["paragraph","reference","blockquote","list"]],["blockquote",i(150),["paragraph","reference","blockquote","list"]],["hr",i(154),["paragraph","reference","blockquote","list"]],["list",i(157),["paragraph","reference","blockquote"]],["reference",i(159)],["heading",i(153),["paragraph","reference","blockquote"]],["lheading",i(156)],["html_block",i(155),["paragraph","reference","blockquote"]],["paragraph",i(158)]];e.prototype.tokenize=function(r,t,a){for(var l,p=this.ruler.getRules(""),s=p.length,u=t,d=!1,v=r.md.options.maxNesting;u<a&&(r.line=u=r.skipEmptyLines(u),!(u>=a))&&!(r.sCount[u]<r.blkIndent);){if(r.level>=v){r.line=a;break}for(l=0;l<s&&!p[l](r,u,a,!1);l++);r.tight=!d,r.isEmpty(r.line-1)&&(d=!0),(u=r.line)<a&&r.isEmpty(u)&&(d=!0,u++,r.line=u)}},e.prototype.parse=function(r,t,a,l){var p;r&&(p=new this.State(r,t,a,l),this.tokenize(p,p.line,p.lineMax))},e.prototype.State=i(160),c.exports=e},function(c,_,i){function e(){this.ruler=new o;for(var r=0;r<n.length;r++)this.ruler.push(n[r][0],n[r][1])}var o=i(32),n=[["normalize",i(165)],["block",i(162)],["inline",i(163)],["linkify",i(164)],["replacements",i(166)],["smartquotes",i(167)]];e.prototype.process=function(r){var t,a,l;for(l=this.ruler.getRules(""),t=0,a=l.length;t<a;t++)l[t](r)},e.prototype.State=i(168),c.exports=e},function(c,_,i){function e(){var t;for(this.ruler=new o,t=0;t<n.length;t++)this.ruler.push(n[t][0],n[t][1]);for(this.ruler2=new o,t=0;t<r.length;t++)this.ruler2.push(r[t][0],r[t][1])}var o=i(32),n=[["text",i(179)],["newline",i(177)],["escape",i(173)],["backticks",i(170)],["strikethrough",i(56).tokenize],["emphasis",i(55).tokenize],["link",i(176)],["image",i(175)],["autolink",i(169)],["html_inline",i(174)],["entity",i(172)]],r=[["balance_pairs",i(171)],["strikethrough",i(56).postProcess],["emphasis",i(55).postProcess],["text_collapse",i(180)]];e.prototype.skipToken=function(t){var a,l,p=t.pos,s=this.ruler.getRules(""),u=s.length,d=t.md.options.maxNesting,v=t.cache;if(v[p]!==void 0)return void(t.pos=v[p]);if(t.level<d)for(l=0;l<u&&(t.level++,a=s[l](t,!0),t.level--,!a);l++);else t.pos=t.posMax;a||t.pos++,v[p]=t.pos},e.prototype.tokenize=function(t){for(var a,l,p=this.ruler.getRules(""),s=p.length,u=t.posMax,d=t.md.options.maxNesting;t.pos<u;){if(t.level<d)for(l=0;l<s&&!(a=p[l](t,!1));l++);if(a){if(t.pos>=u)break}else t.pending+=t.src[t.pos++]}t.pending&&t.pushPending()},e.prototype.parse=function(t,a,l,p){var s,u,d,v=new this.State(t,a,l,p);for(this.tokenize(v),u=this.ruler2.getRules(""),d=u.length,s=0;s<d;s++)u[s](v)},e.prototype.State=i(178),c.exports=e},function(c,_,i){c.exports={options:{html:!0,xhtmlOut:!0,breaks:!1,langPrefix:"language-",linkify:!1,typographer:!1,quotes:"\u201C\u201D\u2018\u2019",highlight:null,maxNesting:20},components:{core:{rules:["normalize","block","inline"]},block:{rules:["blockquote","code","fence","heading","hr","html_block","lheading","list","reference","paragraph"]},inline:{rules:["autolink","backticks","emphasis","entity","escape","html_inline","image","link","newline","text"],rules2:["balance_pairs","emphasis","text_collapse"]}}}},function(c,_,i){c.exports={options:{html:!1,xhtmlOut:!1,breaks:!1,langPrefix:"language-",linkify:!1,typographer:!1,quotes:"\u201C\u201D\u2018\u2019",highlight:null,maxNesting:100},components:{core:{},block:{},inline:{}}}},function(c,_,i){c.exports={options:{html:!1,xhtmlOut:!1,breaks:!1,langPrefix:"language-",linkify:!1,typographer:!1,quotes:"\u201C\u201D\u2018\u2019",highlight:null,maxNesting:20},components:{core:{rules:["normalize","block","inline"]},block:{rules:["paragraph"]},inline:{rules:["text"],rules2:["balance_pairs","text_collapse"]}}}},function(c,_,i){function e(){this.rules=o({},t)}var o=i(0).assign,n=i(0).unescapeAll,r=i(0).escapeHtml,t={};t.code_inline=function(a,l,p,s,u){var d=a[l];return"<code"+u.renderAttrs(d)+">"+r(a[l].content)+"</code>"},t.code_block=function(a,l,p,s,u){var d=a[l];return"<pre"+u.renderAttrs(d)+"><code>"+r(a[l].content)+`</code></pre>
`},t.fence=function(a,l,p,s,u){var d,v,f,y,m=a[l],h=m.info?n(m.info).trim():"",w="";return h&&(w=h.split(/\s+/g)[0]),d=p.highlight&&p.highlight(m.content,w)||r(m.content),d.indexOf("<pre")===0?d+`
`:h?(v=m.attrIndex("class"),f=m.attrs?m.attrs.slice():[],v<0?f.push(["class",p.langPrefix+w]):f[v][1]+=" "+p.langPrefix+w,y={attrs:f},"<pre><code"+u.renderAttrs(y)+">"+d+`</code></pre>
`):"<pre><code"+u.renderAttrs(m)+">"+d+`</code></pre>
`},t.image=function(a,l,p,s,u){var d=a[l];return d.attrs[d.attrIndex("alt")][1]=u.renderInlineAsText(d.children,p,s),u.renderToken(a,l,p)},t.hardbreak=function(a,l,p){return p.xhtmlOut?`<br />
`:`<br>
`},t.softbreak=function(a,l,p){return p.breaks?p.xhtmlOut?`<br />
`:`<br>
`:`
`},t.text=function(a,l){return r(a[l].content)},t.html_block=function(a,l){return a[l].content},t.html_inline=function(a,l){return a[l].content},e.prototype.renderAttrs=function(a){var l,p,s;if(!a.attrs)return"";for(s="",l=0,p=a.attrs.length;l<p;l++)s+=" "+r(a.attrs[l][0])+'="'+r(a.attrs[l][1])+'"';return s},e.prototype.renderToken=function(a,l,p){var s,u="",d=!1,v=a[l];return v.hidden?"":(v.block&&v.nesting!==-1&&l&&a[l-1].hidden&&(u+=`
`),u+=(v.nesting===-1?"</":"<")+v.tag,u+=this.renderAttrs(v),v.nesting===0&&p.xhtmlOut&&(u+=" /"),v.block&&(d=!0,v.nesting===1&&l+1<a.length&&(s=a[l+1],(s.type==="inline"||s.hidden||s.nesting===-1&&s.tag===v.tag)&&(d=!1))),u+=d?`>
`:">")},e.prototype.renderInline=function(a,l,p){for(var s,u="",d=this.rules,v=0,f=a.length;v<f;v++)s=a[v].type,d[s]!==void 0?u+=d[s](a,v,l,p,this):u+=this.renderToken(a,v,l);return u},e.prototype.renderInlineAsText=function(a,l,p){for(var s="",u=0,d=a.length;u<d;u++)a[u].type==="text"?s+=a[u].content:a[u].type==="image"&&(s+=this.renderInlineAsText(a[u].children,l,p));return s},e.prototype.render=function(a,l,p){var s,u,d,v="",f=this.rules;for(s=0,u=a.length;s<u;s++)d=a[s].type,d==="inline"?v+=this.renderInline(a[s].children,l,p):f[d]!==void 0?v+=f[a[s].type](a,s,l,p,this):v+=this.renderToken(a,s,l,p);return v},c.exports=e},function(c,_,i){var e=i(0).isSpace;c.exports=function(o,n,r,t){var a,l,p,s,u,d,v,f,y,m,h,w,g,k,b,C,x,E,D,A,S=o.lineMax,T=o.bMarks[n]+o.tShift[n],I=o.eMarks[n];if(o.sCount[n]-o.blkIndent>=4||o.src.charCodeAt(T++)!==62)return!1;if(t)return!0;for(s=y=o.sCount[n]+T-(o.bMarks[n]+o.tShift[n]),o.src.charCodeAt(T)===32?(T++,s++,y++,a=!1,C=!0):o.src.charCodeAt(T)===9?(C=!0,(o.bsCount[n]+y)%4==3?(T++,s++,y++,a=!1):a=!0):C=!1,m=[o.bMarks[n]],o.bMarks[n]=T;T<I&&(l=o.src.charCodeAt(T),e(l));)l===9?y+=4-(y+o.bsCount[n]+(a?1:0))%4:y++,T++;for(h=[o.bsCount[n]],o.bsCount[n]=o.sCount[n]+1+(C?1:0),d=T>=I,k=[o.sCount[n]],o.sCount[n]=y-s,b=[o.tShift[n]],o.tShift[n]=T-o.bMarks[n],E=o.md.block.ruler.getRules("blockquote"),g=o.parentType,o.parentType="blockquote",A=!1,f=n+1;f<r&&(o.sCount[f]<o.blkIndent&&(A=!0),T=o.bMarks[f]+o.tShift[f],I=o.eMarks[f],!(T>=I));f++)if(o.src.charCodeAt(T++)!==62||A){if(d)break;for(x=!1,p=0,u=E.length;p<u;p++)if(E[p](o,f,r,!0)){x=!0;break}if(x){o.lineMax=f,o.blkIndent!==0&&(m.push(o.bMarks[f]),h.push(o.bsCount[f]),b.push(o.tShift[f]),k.push(o.sCount[f]),o.sCount[f]-=o.blkIndent);break}m.push(o.bMarks[f]),h.push(o.bsCount[f]),b.push(o.tShift[f]),k.push(o.sCount[f]),o.sCount[f]=-1}else{for(s=y=o.sCount[f]+T-(o.bMarks[f]+o.tShift[f]),o.src.charCodeAt(T)===32?(T++,s++,y++,a=!1,C=!0):o.src.charCodeAt(T)===9?(C=!0,(o.bsCount[f]+y)%4==3?(T++,s++,y++,a=!1):a=!0):C=!1,m.push(o.bMarks[f]),o.bMarks[f]=T;T<I&&(l=o.src.charCodeAt(T),e(l));)l===9?y+=4-(y+o.bsCount[f]+(a?1:0))%4:y++,T++;d=T>=I,h.push(o.bsCount[f]),o.bsCount[f]=o.sCount[f]+1+(C?1:0),k.push(o.sCount[f]),o.sCount[f]=y-s,b.push(o.tShift[f]),o.tShift[f]=T-o.bMarks[f]}for(w=o.blkIndent,o.blkIndent=0,D=o.push("blockquote_open","blockquote",1),D.markup=">",D.map=v=[n,0],o.md.block.tokenize(o,n,f),D=o.push("blockquote_close","blockquote",-1),D.markup=">",o.lineMax=S,o.parentType=g,v[1]=o.line,p=0;p<b.length;p++)o.bMarks[p+n]=m[p],o.tShift[p+n]=b[p],o.sCount[p+n]=k[p],o.bsCount[p+n]=h[p];return o.blkIndent=w,!0}},function(c,_,i){c.exports=function(e,o,n){var r,t,a;if(e.sCount[o]-e.blkIndent<4)return!1;for(t=r=o+1;r<n;)if(e.isEmpty(r))r++;else{if(!(e.sCount[r]-e.blkIndent>=4))break;r++,t=r}return e.line=t,a=e.push("code_block","code",0),a.content=e.getLines(o,t,4+e.blkIndent,!0),a.map=[o,e.line],!0}},function(c,_,i){c.exports=function(e,o,n,r){var t,a,l,p,s,u,d,v=!1,f=e.bMarks[o]+e.tShift[o],y=e.eMarks[o];if(e.sCount[o]-e.blkIndent>=4||f+3>y||(t=e.src.charCodeAt(f))!==126&&t!==96||(s=f,f=e.skipChars(f,t),(a=f-s)<3)||(d=e.src.slice(s,f),l=e.src.slice(f,y),t===96&&l.indexOf(String.fromCharCode(t))>=0))return!1;if(r)return!0;for(p=o;!(++p>=n)&&(f=s=e.bMarks[p]+e.tShift[p],y=e.eMarks[p],!(f<y&&e.sCount[p]<e.blkIndent));)if(e.src.charCodeAt(f)===t&&!(e.sCount[p]-e.blkIndent>=4||(f=e.skipChars(f,t))-s<a||(f=e.skipSpaces(f))<y)){v=!0;break}return a=e.sCount[o],e.line=p+(v?1:0),u=e.push("fence","code",0),u.info=l,u.content=e.getLines(o+1,p,a,!0),u.markup=d,u.map=[o,e.line],!0}},function(c,_,i){var e=i(0).isSpace;c.exports=function(o,n,r,t){var a,l,p,s,u=o.bMarks[n]+o.tShift[n],d=o.eMarks[n];if(o.sCount[n]-o.blkIndent>=4||(a=o.src.charCodeAt(u))!==35||u>=d)return!1;for(l=1,a=o.src.charCodeAt(++u);a===35&&u<d&&l<=6;)l++,a=o.src.charCodeAt(++u);return!(l>6||u<d&&!e(a))&&(!!t||(d=o.skipSpacesBack(d,u),p=o.skipCharsBack(d,35,u),p>u&&e(o.src.charCodeAt(p-1))&&(d=p),o.line=n+1,s=o.push("heading_open","h"+String(l),1),s.markup="########".slice(0,l),s.map=[n,o.line],s=o.push("inline","",0),s.content=o.src.slice(u,d).trim(),s.map=[n,o.line],s.children=[],s=o.push("heading_close","h"+String(l),-1),s.markup="########".slice(0,l),!0))}},function(c,_,i){var e=i(0).isSpace;c.exports=function(o,n,r,t){var a,l,p,s,u=o.bMarks[n]+o.tShift[n],d=o.eMarks[n];if(o.sCount[n]-o.blkIndent>=4||(a=o.src.charCodeAt(u++))!==42&&a!==45&&a!==95)return!1;for(l=1;u<d;){if((p=o.src.charCodeAt(u++))!==a&&!e(p))return!1;p===a&&l++}return!(l<3)&&(!!t||(o.line=n+1,s=o.push("hr","hr",0),s.map=[n,o.line],s.markup=Array(l+1).join(String.fromCharCode(a)),!0))}},function(c,_,i){var e=i(137),o=i(54).HTML_OPEN_CLOSE_TAG_RE,n=[[/^<(script|pre|style)(?=(\s|>|$))/i,/<\/(script|pre|style)>/i,!0],[/^<!--/,/-->/,!0],[/^<\?/,/\?>/,!0],[/^<![A-Z]/,/>/,!0],[/^<!\[CDATA\[/,/\]\]>/,!0],[new RegExp("^</?("+e.join("|")+")(?=(\\s|/?>|$))","i"),/^$/,!0],[new RegExp(o.source+"\\s*$"),/^$/,!1]];c.exports=function(r,t,a,l){var p,s,u,d,v=r.bMarks[t]+r.tShift[t],f=r.eMarks[t];if(r.sCount[t]-r.blkIndent>=4||!r.md.options.html||r.src.charCodeAt(v)!==60)return!1;for(d=r.src.slice(v,f),p=0;p<n.length&&!n[p][0].test(d);p++);if(p===n.length)return!1;if(l)return n[p][2];if(s=t+1,!n[p][1].test(d)){for(;s<a&&!(r.sCount[s]<r.blkIndent);s++)if(v=r.bMarks[s]+r.tShift[s],f=r.eMarks[s],d=r.src.slice(v,f),n[p][1].test(d)){d.length!==0&&s++;break}}return r.line=s,u=r.push("html_block","",0),u.map=[t,s],u.content=r.getLines(t,s,r.blkIndent,!0),!0}},function(c,_,i){c.exports=function(e,o,n){var r,t,a,l,p,s,u,d,v,f,y=o+1,m=e.md.block.ruler.getRules("paragraph");if(e.sCount[o]-e.blkIndent>=4)return!1;for(f=e.parentType,e.parentType="paragraph";y<n&&!e.isEmpty(y);y++)if(!(e.sCount[y]-e.blkIndent>3)){if(e.sCount[y]>=e.blkIndent&&(s=e.bMarks[y]+e.tShift[y],u=e.eMarks[y],s<u&&((v=e.src.charCodeAt(s))===45||v===61)&&(s=e.skipChars(s,v),(s=e.skipSpaces(s))>=u))){d=v===61?1:2;break}if(!(e.sCount[y]<0)){for(t=!1,a=0,l=m.length;a<l;a++)if(m[a](e,y,n,!0)){t=!0;break}if(t)break}}return!!d&&(r=e.getLines(o,y,e.blkIndent,!1).trim(),e.line=y+1,p=e.push("heading_open","h"+String(d),1),p.markup=String.fromCharCode(v),p.map=[o,e.line],p=e.push("inline","",0),p.content=r,p.map=[o,e.line-1],p.children=[],p=e.push("heading_close","h"+String(d),-1),p.markup=String.fromCharCode(v),e.parentType=f,!0)}},function(c,_,i){function e(t,a){var l,p,s,u;return p=t.bMarks[a]+t.tShift[a],s=t.eMarks[a],l=t.src.charCodeAt(p++),l!==42&&l!==45&&l!==43||p<s&&(u=t.src.charCodeAt(p),!r(u))?-1:p}function o(t,a){var l,p=t.bMarks[a]+t.tShift[a],s=p,u=t.eMarks[a];if(s+1>=u||(l=t.src.charCodeAt(s++))<48||l>57)return-1;for(;;){if(s>=u)return-1;l=t.src.charCodeAt(s++);{if(!(l>=48&&l<=57)){if(l===41||l===46)break;return-1}if(s-p>=10)return-1}}return s<u&&(l=t.src.charCodeAt(s),!r(l))?-1:s}function n(t,a){var l,p,s=t.level+2;for(l=a+2,p=t.tokens.length-2;l<p;l++)t.tokens[l].level===s&&t.tokens[l].type==="paragraph_open"&&(t.tokens[l+2].hidden=!0,t.tokens[l].hidden=!0,l+=2)}var r=i(0).isSpace;c.exports=function(t,a,l,p){var s,u,d,v,f,y,m,h,w,g,k,b,C,x,E,D,A,S,T,I,$,q,M,z,U,L,P,j,O=!1,B=!0;if(t.sCount[a]-t.blkIndent>=4||t.listIndent>=0&&t.sCount[a]-t.listIndent>=4&&t.sCount[a]<t.blkIndent)return!1;if(p&&t.parentType==="paragraph"&&t.tShift[a]>=t.blkIndent&&(O=!0),(M=o(t,a))>=0){if(m=!0,U=t.bMarks[a]+t.tShift[a],C=Number(t.src.substr(U,M-U-1)),O&&C!==1)return!1}else{if(!((M=e(t,a))>=0))return!1;m=!1}if(O&&t.skipSpaces(M)>=t.eMarks[a])return!1;if(b=t.src.charCodeAt(M-1),p)return!0;for(k=t.tokens.length,m?(j=t.push("ordered_list_open","ol",1),C!==1&&(j.attrs=[["start",C]])):j=t.push("bullet_list_open","ul",1),j.map=g=[a,0],j.markup=String.fromCharCode(b),E=a,z=!1,P=t.md.block.ruler.getRules("list"),S=t.parentType,t.parentType="list";E<l;){for(q=M,x=t.eMarks[E],y=D=t.sCount[E]+M-(t.bMarks[a]+t.tShift[a]);q<x;){if((s=t.src.charCodeAt(q))===9)D+=4-(D+t.bsCount[E])%4;else{if(s!==32)break;D++}q++}if(u=q,f=u>=x?1:D-y,f>4&&(f=1),v=y+f,j=t.push("list_item_open","li",1),j.markup=String.fromCharCode(b),j.map=h=[a,0],$=t.tight,I=t.tShift[a],T=t.sCount[a],A=t.listIndent,t.listIndent=t.blkIndent,t.blkIndent=v,t.tight=!0,t.tShift[a]=u-t.bMarks[a],t.sCount[a]=D,u>=x&&t.isEmpty(a+1)?t.line=Math.min(t.line+2,l):t.md.block.tokenize(t,a,l,!0),t.tight&&!z||(B=!1),z=t.line-a>1&&t.isEmpty(t.line-1),t.blkIndent=t.listIndent,t.listIndent=A,t.tShift[a]=I,t.sCount[a]=T,t.tight=$,j=t.push("list_item_close","li",-1),j.markup=String.fromCharCode(b),E=a=t.line,h[1]=E,u=t.bMarks[a],E>=l||t.sCount[E]<t.blkIndent||t.sCount[a]-t.blkIndent>=4)break;for(L=!1,d=0,w=P.length;d<w;d++)if(P[d](t,E,l,!0)){L=!0;break}if(L)break;if(m){if((M=o(t,E))<0)break}else if((M=e(t,E))<0)break;if(b!==t.src.charCodeAt(M-1))break}return j=m?t.push("ordered_list_close","ol",-1):t.push("bullet_list_close","ul",-1),j.markup=String.fromCharCode(b),g[1]=E,t.line=E,t.parentType=S,B&&n(t,k),!0}},function(c,_,i){c.exports=function(e,o){var n,r,t,a,l,p,s=o+1,u=e.md.block.ruler.getRules("paragraph"),d=e.lineMax;for(p=e.parentType,e.parentType="paragraph";s<d&&!e.isEmpty(s);s++)if(!(e.sCount[s]-e.blkIndent>3||e.sCount[s]<0)){for(r=!1,t=0,a=u.length;t<a;t++)if(u[t](e,s,d,!0)){r=!0;break}if(r)break}return n=e.getLines(o,s,e.blkIndent,!1).trim(),e.line=s,l=e.push("paragraph_open","p",1),l.map=[o,e.line],l=e.push("inline","",0),l.content=n,l.map=[o,e.line],l.children=[],l=e.push("paragraph_close","p",-1),e.parentType=p,!0}},function(c,_,i){var e=i(0).normalizeReference,o=i(0).isSpace;c.exports=function(n,r,t,a){var l,p,s,u,d,v,f,y,m,h,w,g,k,b,C,x,E=0,D=n.bMarks[r]+n.tShift[r],A=n.eMarks[r],S=r+1;if(n.sCount[r]-n.blkIndent>=4||n.src.charCodeAt(D)!==91)return!1;for(;++D<A;)if(n.src.charCodeAt(D)===93&&n.src.charCodeAt(D-1)!==92){if(D+1===A||n.src.charCodeAt(D+1)!==58)return!1;break}for(u=n.lineMax,C=n.md.block.ruler.getRules("reference"),h=n.parentType,n.parentType="reference";S<u&&!n.isEmpty(S);S++)if(!(n.sCount[S]-n.blkIndent>3||n.sCount[S]<0)){for(b=!1,v=0,f=C.length;v<f;v++)if(C[v](n,S,u,!0)){b=!0;break}if(b)break}for(k=n.getLines(r,S,n.blkIndent,!1).trim(),A=k.length,D=1;D<A;D++){if((l=k.charCodeAt(D))===91)return!1;if(l===93){m=D;break}(l===10||l===92&&++D<A&&k.charCodeAt(D)===10)&&E++}if(m<0||k.charCodeAt(m+1)!==58)return!1;for(D=m+2;D<A;D++)if((l=k.charCodeAt(D))===10)E++;else if(!o(l))break;if(w=n.md.helpers.parseLinkDestination(k,D,A),!w.ok||(d=n.md.normalizeLink(w.str),!n.md.validateLink(d)))return!1;for(D=w.pos,E+=w.lines,p=D,s=E,g=D;D<A;D++)if((l=k.charCodeAt(D))===10)E++;else if(!o(l))break;for(w=n.md.helpers.parseLinkTitle(k,D,A),D<A&&g!==D&&w.ok?(x=w.str,D=w.pos,E+=w.lines):(x="",D=p,E=s);D<A&&(l=k.charCodeAt(D),o(l));)D++;if(D<A&&k.charCodeAt(D)!==10&&x)for(x="",D=p,E=s;D<A&&(l=k.charCodeAt(D),o(l));)D++;return!(D<A&&k.charCodeAt(D)!==10)&&!!(y=e(k.slice(1,m)))&&(!!a||(n.env.references===void 0&&(n.env.references={}),n.env.references[y]===void 0&&(n.env.references[y]={title:x,href:d}),n.parentType=h,n.line=r+E+1,!0))}},function(c,_,i){function e(r,t,a,l){var p,s,u,d,v,f,y,m;for(this.src=r,this.md=t,this.env=a,this.tokens=l,this.bMarks=[],this.eMarks=[],this.tShift=[],this.sCount=[],this.bsCount=[],this.blkIndent=0,this.line=0,this.lineMax=0,this.tight=!1,this.ddIndent=-1,this.listIndent=-1,this.parentType="root",this.level=0,this.result="",s=this.src,m=!1,u=d=f=y=0,v=s.length;d<v;d++){if(p=s.charCodeAt(d),!m){if(n(p)){f++,p===9?y+=4-y%4:y++;continue}m=!0}p!==10&&d!==v-1||(p!==10&&d++,this.bMarks.push(u),this.eMarks.push(d),this.tShift.push(f),this.sCount.push(y),this.bsCount.push(0),m=!1,f=0,y=0,u=d+1)}this.bMarks.push(s.length),this.eMarks.push(s.length),this.tShift.push(0),this.sCount.push(0),this.bsCount.push(0),this.lineMax=this.bMarks.length-1}var o=i(33),n=i(0).isSpace;e.prototype.push=function(r,t,a){var l=new o(r,t,a);return l.block=!0,a<0&&this.level--,l.level=this.level,a>0&&this.level++,this.tokens.push(l),l},e.prototype.isEmpty=function(r){return this.bMarks[r]+this.tShift[r]>=this.eMarks[r]},e.prototype.skipEmptyLines=function(r){for(var t=this.lineMax;r<t&&!(this.bMarks[r]+this.tShift[r]<this.eMarks[r]);r++);return r},e.prototype.skipSpaces=function(r){for(var t,a=this.src.length;r<a&&(t=this.src.charCodeAt(r),n(t));r++);return r},e.prototype.skipSpacesBack=function(r,t){if(r<=t)return r;for(;r>t;)if(!n(this.src.charCodeAt(--r)))return r+1;return r},e.prototype.skipChars=function(r,t){for(var a=this.src.length;r<a&&this.src.charCodeAt(r)===t;r++);return r},e.prototype.skipCharsBack=function(r,t,a){if(r<=a)return r;for(;r>a;)if(t!==this.src.charCodeAt(--r))return r+1;return r},e.prototype.getLines=function(r,t,a,l){var p,s,u,d,v,f,y,m=r;if(r>=t)return"";for(f=new Array(t-r),p=0;m<t;m++,p++){for(s=0,y=d=this.bMarks[m],v=m+1<t||l?this.eMarks[m]+1:this.eMarks[m];d<v&&s<a;){if(u=this.src.charCodeAt(d),n(u))u===9?s+=4-(s+this.bsCount[m])%4:s++;else{if(!(d-y<this.tShift[m]))break;s++}d++}f[p]=s>a?new Array(s-a+1).join(" ")+this.src.slice(d,v):this.src.slice(d,v)}return f.join("")},e.prototype.Token=o,c.exports=e},function(c,_,i){function e(r,t){var a=r.bMarks[t]+r.blkIndent,l=r.eMarks[t];return r.src.substr(a,l-a)}function o(r){var t,a=[],l=0,p=r.length,s=0,u=0,d=!1,v=0;for(t=r.charCodeAt(l);l<p;)t===96?d?(d=!1,v=l):s%2==0&&(d=!0,v=l):t!==124||s%2!=0||d||(a.push(r.substring(u,l)),u=l+1),t===92?s++:s=0,l++,l===p&&d&&(d=!1,l=v+1),t=r.charCodeAt(l);return a.push(r.substring(u)),a}var n=i(0).isSpace;c.exports=function(r,t,a,l){var p,s,u,d,v,f,y,m,h,w,g,k;if(t+2>a||(v=t+1,r.sCount[v]<r.blkIndent)||r.sCount[v]-r.blkIndent>=4||(u=r.bMarks[v]+r.tShift[v])>=r.eMarks[v]||(p=r.src.charCodeAt(u++))!==124&&p!==45&&p!==58)return!1;for(;u<r.eMarks[v];){if((p=r.src.charCodeAt(u))!==124&&p!==45&&p!==58&&!n(p))return!1;u++}for(s=e(r,t+1),f=s.split("|"),h=[],d=0;d<f.length;d++){if(!(w=f[d].trim())){if(d===0||d===f.length-1)continue;return!1}if(!/^:?-+:?$/.test(w))return!1;w.charCodeAt(w.length-1)===58?h.push(w.charCodeAt(0)===58?"center":"right"):w.charCodeAt(0)===58?h.push("left"):h.push("")}if(s=e(r,t).trim(),s.indexOf("|")===-1||r.sCount[t]-r.blkIndent>=4||(f=o(s.replace(/^\||\|$/g,"")),(y=f.length)>h.length))return!1;if(l)return!0;for(m=r.push("table_open","table",1),m.map=g=[t,0],m=r.push("thead_open","thead",1),m.map=[t,t+1],m=r.push("tr_open","tr",1),m.map=[t,t+1],d=0;d<f.length;d++)m=r.push("th_open","th",1),m.map=[t,t+1],h[d]&&(m.attrs=[["style","text-align:"+h[d]]]),m=r.push("inline","",0),m.content=f[d].trim(),m.map=[t,t+1],m.children=[],m=r.push("th_close","th",-1);for(m=r.push("tr_close","tr",-1),m=r.push("thead_close","thead",-1),m=r.push("tbody_open","tbody",1),m.map=k=[t+2,0],v=t+2;v<a&&!(r.sCount[v]<r.blkIndent)&&(s=e(r,v).trim(),s.indexOf("|")!==-1)&&!(r.sCount[v]-r.blkIndent>=4);v++){for(f=o(s.replace(/^\||\|$/g,"")),m=r.push("tr_open","tr",1),d=0;d<y;d++)m=r.push("td_open","td",1),h[d]&&(m.attrs=[["style","text-align:"+h[d]]]),m=r.push("inline","",0),m.content=f[d]?f[d].trim():"",m.children=[],m=r.push("td_close","td",-1);m=r.push("tr_close","tr",-1)}return m=r.push("tbody_close","tbody",-1),m=r.push("table_close","table",-1),g[1]=k[1]=v,r.line=v,!0}},function(c,_,i){c.exports=function(e){var o;e.inlineMode?(o=new e.Token("inline","",0),o.content=e.src,o.map=[0,1],o.children=[],e.tokens.push(o)):e.md.block.parse(e.src,e.md,e.env,e.tokens)}},function(c,_,i){c.exports=function(e){var o,n,r,t=e.tokens;for(n=0,r=t.length;n<r;n++)o=t[n],o.type==="inline"&&e.md.inline.parse(o.content,e.md,e.env,o.children)}},function(c,_,i){function e(r){return/^<a[>\s]/i.test(r)}function o(r){return/^<\/a\s*>/i.test(r)}var n=i(0).arrayReplaceAt;c.exports=function(r){var t,a,l,p,s,u,d,v,f,y,m,h,w,g,k,b,C,x=r.tokens;if(r.md.options.linkify){for(a=0,l=x.length;a<l;a++)if(x[a].type==="inline"&&r.md.linkify.pretest(x[a].content))for(p=x[a].children,w=0,t=p.length-1;t>=0;t--)if(u=p[t],u.type!=="link_close"){if(u.type==="html_inline"&&(e(u.content)&&w>0&&w--,o(u.content)&&w++),!(w>0)&&u.type==="text"&&r.md.linkify.test(u.content)){for(f=u.content,C=r.md.linkify.match(f),d=[],h=u.level,m=0,v=0;v<C.length;v++)g=C[v].url,k=r.md.normalizeLink(g),r.md.validateLink(k)&&(b=C[v].text,b=C[v].schema?C[v].schema!=="mailto:"||/^mailto:/i.test(b)?r.md.normalizeLinkText(b):r.md.normalizeLinkText("mailto:"+b).replace(/^mailto:/,""):r.md.normalizeLinkText("http://"+b).replace(/^http:\/\//,""),y=C[v].index,y>m&&(s=new r.Token("text","",0),s.content=f.slice(m,y),s.level=h,d.push(s)),s=new r.Token("link_open","a",1),s.attrs=[["href",k]],s.level=h++,s.markup="linkify",s.info="auto",d.push(s),s=new r.Token("text","",0),s.content=b,s.level=h,d.push(s),s=new r.Token("link_close","a",-1),s.level=--h,s.markup="linkify",s.info="auto",d.push(s),m=C[v].lastIndex);m<f.length&&(s=new r.Token("text","",0),s.content=f.slice(m),s.level=h,d.push(s)),x[a].children=p=n(p,t,d)}}else for(t--;p[t].level!==u.level&&p[t].type!=="link_open";)t--}}},function(c,_,i){var e=/\r\n?|\n/g,o=/\0/g;c.exports=function(n){var r;r=n.src.replace(e,`
`),r=r.replace(o,"\uFFFD"),n.src=r}},function(c,_,i){function e(p,s){return l[s.toLowerCase()]}function o(p){var s,u,d=0;for(s=p.length-1;s>=0;s--)u=p[s],u.type!=="text"||d||(u.content=u.content.replace(a,e)),u.type==="link_open"&&u.info==="auto"&&d--,u.type==="link_close"&&u.info==="auto"&&d++}function n(p){var s,u,d=0;for(s=p.length-1;s>=0;s--)u=p[s],u.type!=="text"||d||r.test(u.content)&&(u.content=u.content.replace(/\+-/g,"\xB1").replace(/\.{2,}/g,"\u2026").replace(/([?!])…/g,"$1..").replace(/([?!]){4,}/g,"$1$1$1").replace(/,{2,}/g,",").replace(/(^|[^-])---([^-]|$)/gm,"$1\u2014$2").replace(/(^|\s)--(\s|$)/gm,"$1\u2013$2").replace(/(^|[^-\s])--([^-\s]|$)/gm,"$1\u2013$2")),u.type==="link_open"&&u.info==="auto"&&d--,u.type==="link_close"&&u.info==="auto"&&d++}var r=/\+-|\.\.|\?\?\?\?|!!!!|,,|--/,t=/\((c|tm|r|p)\)/i,a=/\((c|tm|r|p)\)/gi,l={c:"\xA9",r:"\xAE",p:"\xA7",tm:"\u2122"};c.exports=function(p){var s;if(p.md.options.typographer)for(s=p.tokens.length-1;s>=0;s--)p.tokens[s].type==="inline"&&(t.test(p.tokens[s].content)&&o(p.tokens[s].children),r.test(p.tokens[s].content)&&n(p.tokens[s].children))}},function(c,_,i){function e(s,u,d){return s.substr(0,u)+d+s.substr(u+1)}function o(s,u){var d,v,f,y,m,h,w,g,k,b,C,x,E,D,A,S,T,I,$,q,M;for($=[],d=0;d<s.length;d++){for(v=s[d],w=s[d].level,T=$.length-1;T>=0&&!($[T].level<=w);T--);if($.length=T+1,v.type==="text"){f=v.content,m=0,h=f.length;e:for(;m<h&&(l.lastIndex=m,y=l.exec(f));){if(A=S=!0,m=y.index+1,I=y[0]==="'",k=32,y.index-1>=0)k=f.charCodeAt(y.index-1);else for(T=d-1;T>=0&&s[T].type!=="softbreak"&&s[T].type!=="hardbreak";T--)if(s[T].type==="text"){k=s[T].content.charCodeAt(s[T].content.length-1);break}if(b=32,m<h)b=f.charCodeAt(m);else for(T=d+1;T<s.length&&s[T].type!=="softbreak"&&s[T].type!=="hardbreak";T++)if(s[T].type==="text"){b=s[T].content.charCodeAt(0);break}if(C=t(k)||r(String.fromCharCode(k)),x=t(b)||r(String.fromCharCode(b)),E=n(k),D=n(b),D?A=!1:x&&(E||C||(A=!1)),E?S=!1:C&&(D||x||(S=!1)),b===34&&y[0]==='"'&&k>=48&&k<=57&&(S=A=!1),A&&S&&(A=!1,S=x),A||S){if(S){for(T=$.length-1;T>=0&&(g=$[T],!($[T].level<w));T--)if(g.single===I&&$[T].level===w){g=$[T],I?(q=u.md.options.quotes[2],M=u.md.options.quotes[3]):(q=u.md.options.quotes[0],M=u.md.options.quotes[1]),v.content=e(v.content,y.index,M),s[g.token].content=e(s[g.token].content,g.pos,q),m+=M.length-1,g.token===d&&(m+=q.length-1),f=v.content,h=f.length,$.length=T;continue e}}A?$.push({token:d,pos:y.index,single:I,level:w}):S&&I&&(v.content=e(v.content,y.index,p))}else I&&(v.content=e(v.content,y.index,p))}}}}var n=i(0).isWhiteSpace,r=i(0).isPunctChar,t=i(0).isMdAsciiPunct,a=/['"]/,l=/['"]/g,p="\u2019";c.exports=function(s){var u;if(s.md.options.typographer)for(u=s.tokens.length-1;u>=0;u--)s.tokens[u].type==="inline"&&a.test(s.tokens[u].content)&&o(s.tokens[u].children,s)}},function(c,_,i){function e(n,r,t){this.src=n,this.env=t,this.tokens=[],this.inlineMode=!1,this.md=r}var o=i(33);e.prototype.Token=o,c.exports=e},function(c,_,i){var e=/^<([a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*)>/,o=/^<([a-zA-Z][a-zA-Z0-9+.\-]{1,31}):([^<>\x00-\x20]*)>/;c.exports=function(n,r){var t,a,l,p,s,u,d=n.pos;return n.src.charCodeAt(d)===60&&(t=n.src.slice(d),!(t.indexOf(">")<0)&&(o.test(t)?(a=t.match(o),p=a[0].slice(1,-1),s=n.md.normalizeLink(p),!!n.md.validateLink(s)&&(r||(u=n.push("link_open","a",1),u.attrs=[["href",s]],u.markup="autolink",u.info="auto",u=n.push("text","",0),u.content=n.md.normalizeLinkText(p),u=n.push("link_close","a",-1),u.markup="autolink",u.info="auto"),n.pos+=a[0].length,!0)):!!e.test(t)&&(l=t.match(e),p=l[0].slice(1,-1),s=n.md.normalizeLink("mailto:"+p),!!n.md.validateLink(s)&&(r||(u=n.push("link_open","a",1),u.attrs=[["href",s]],u.markup="autolink",u.info="auto",u=n.push("text","",0),u.content=n.md.normalizeLinkText(p),u=n.push("link_close","a",-1),u.markup="autolink",u.info="auto"),n.pos+=l[0].length,!0))))}},function(c,_,i){c.exports=function(e,o){var n,r,t,a,l,p,s=e.pos;if(e.src.charCodeAt(s)!==96)return!1;for(n=s,s++,r=e.posMax;s<r&&e.src.charCodeAt(s)===96;)s++;for(t=e.src.slice(n,s),a=l=s;(a=e.src.indexOf("`",l))!==-1;){for(l=a+1;l<r&&e.src.charCodeAt(l)===96;)l++;if(l-a===t.length)return o||(p=e.push("code_inline","code",0),p.markup=t,p.content=e.src.slice(s,a).replace(/\n/g," ").replace(/^ (.+) $/,"$1")),e.pos=l,!0}return o||(e.pending+=t),e.pos+=t.length,!0}},function(c,_,i){function e(o,n){var r,t,a,l,p,s,u,d,v={},f=n.length;for(r=0;r<f;r++)if(a=n[r],a.length=a.length||0,a.close){for(v.hasOwnProperty(a.marker)||(v[a.marker]=[-1,-1,-1]),p=v[a.marker][a.length%3],s=-1,t=r-a.jump-1;t>p;t-=l.jump+1)if(l=n[t],l.marker===a.marker&&(s===-1&&(s=t),l.open&&l.end<0&&l.level===a.level&&(u=!1,(l.close||a.open)&&(l.length+a.length)%3==0&&(l.length%3==0&&a.length%3==0||(u=!0)),!u))){d=t>0&&!n[t-1].open?n[t-1].jump+1:0,a.jump=r-t+d,a.open=!1,l.end=r,l.jump=d,l.close=!1,s=-1;break}s!==-1&&(v[a.marker][(a.length||0)%3]=s)}}c.exports=function(o){var n,r=o.tokens_meta,t=o.tokens_meta.length;for(e(o,o.delimiters),n=0;n<t;n++)r[n]&&r[n].delimiters&&e(o,r[n].delimiters)}},function(c,_,i){var e=i(53),o=i(0).has,n=i(0).isValidEntityCode,r=i(0).fromCodePoint,t=/^&#((?:x[a-f0-9]{1,6}|[0-9]{1,7}));/i,a=/^&([a-z][a-z0-9]{1,31});/i;c.exports=function(l,p){var s,u,d=l.pos,v=l.posMax;if(l.src.charCodeAt(d)!==38)return!1;if(d+1<v){if(l.src.charCodeAt(d+1)===35){if(u=l.src.slice(d).match(t))return p||(s=u[1][0].toLowerCase()==="x"?parseInt(u[1].slice(1),16):parseInt(u[1],10),l.pending+=r(n(s)?s:65533)),l.pos+=u[0].length,!0}else if((u=l.src.slice(d).match(a))&&o(e,u[1]))return p||(l.pending+=e[u[1]]),l.pos+=u[0].length,!0}return p||(l.pending+="&"),l.pos++,!0}},function(c,_,i){for(var e=i(0).isSpace,o=[],n=0;n<256;n++)o.push(0);"\\!\"#$%&'()*+,./:;<=>?@[]^_`{|}~-".split("").forEach(function(r){o[r.charCodeAt(0)]=1}),c.exports=function(r,t){var a,l=r.pos,p=r.posMax;if(r.src.charCodeAt(l)!==92)return!1;if(++l<p){if((a=r.src.charCodeAt(l))<256&&o[a]!==0)return t||(r.pending+=r.src[l]),r.pos+=2,!0;if(a===10){for(t||r.push("hardbreak","br",0),l++;l<p&&(a=r.src.charCodeAt(l),e(a));)l++;return r.pos=l,!0}}return t||(r.pending+="\\"),r.pos++,!0}},function(c,_,i){function e(n){var r=32|n;return r>=97&&r<=122}var o=i(54).HTML_TAG_RE;c.exports=function(n,r){var t,a,l,p,s=n.pos;return!!n.md.options.html&&(l=n.posMax,!(n.src.charCodeAt(s)!==60||s+2>=l)&&!((t=n.src.charCodeAt(s+1))!==33&&t!==63&&t!==47&&!e(t))&&!!(a=n.src.slice(s).match(o))&&(r||(p=n.push("html_inline","",0),p.content=n.src.slice(s,s+a[0].length)),n.pos+=a[0].length,!0))}},function(c,_,i){var e=i(0).normalizeReference,o=i(0).isSpace;c.exports=function(n,r){var t,a,l,p,s,u,d,v,f,y,m,h,w,g="",k=n.pos,b=n.posMax;if(n.src.charCodeAt(n.pos)!==33||n.src.charCodeAt(n.pos+1)!==91||(u=n.pos+2,(s=n.md.helpers.parseLinkLabel(n,n.pos+1,!1))<0))return!1;if((d=s+1)<b&&n.src.charCodeAt(d)===40){for(d++;d<b&&(a=n.src.charCodeAt(d),o(a)||a===10);d++);if(d>=b)return!1;for(w=d,f=n.md.helpers.parseLinkDestination(n.src,d,n.posMax),f.ok&&(g=n.md.normalizeLink(f.str),n.md.validateLink(g)?d=f.pos:g=""),w=d;d<b&&(a=n.src.charCodeAt(d),o(a)||a===10);d++);if(f=n.md.helpers.parseLinkTitle(n.src,d,n.posMax),d<b&&w!==d&&f.ok)for(y=f.str,d=f.pos;d<b&&(a=n.src.charCodeAt(d),o(a)||a===10);d++);else y="";if(d>=b||n.src.charCodeAt(d)!==41)return n.pos=k,!1;d++}else{if(n.env.references===void 0)return!1;if(d<b&&n.src.charCodeAt(d)===91?(w=d+1,d=n.md.helpers.parseLinkLabel(n,d),d>=0?p=n.src.slice(w,d++):d=s+1):d=s+1,p||(p=n.src.slice(u,s)),!(v=n.env.references[e(p)]))return n.pos=k,!1;g=v.href,y=v.title}return r||(l=n.src.slice(u,s),n.md.inline.parse(l,n.md,n.env,h=[]),m=n.push("image","img",0),m.attrs=t=[["src",g],["alt",""]],m.children=h,m.content=l,y&&t.push(["title",y])),n.pos=d,n.posMax=b,!0}},function(c,_,i){var e=i(0).normalizeReference,o=i(0).isSpace;c.exports=function(n,r){var t,a,l,p,s,u,d,v,f,y,m="",h=n.pos,w=n.posMax,g=n.pos,k=!0;if(n.src.charCodeAt(n.pos)!==91||(s=n.pos+1,(p=n.md.helpers.parseLinkLabel(n,n.pos,!0))<0))return!1;if((u=p+1)<w&&n.src.charCodeAt(u)===40){for(k=!1,u++;u<w&&(a=n.src.charCodeAt(u),o(a)||a===10);u++);if(u>=w)return!1;for(g=u,d=n.md.helpers.parseLinkDestination(n.src,u,n.posMax),d.ok&&(m=n.md.normalizeLink(d.str),n.md.validateLink(m)?u=d.pos:m=""),g=u;u<w&&(a=n.src.charCodeAt(u),o(a)||a===10);u++);if(d=n.md.helpers.parseLinkTitle(n.src,u,n.posMax),u<w&&g!==u&&d.ok)for(f=d.str,u=d.pos;u<w&&(a=n.src.charCodeAt(u),o(a)||a===10);u++);else f="";(u>=w||n.src.charCodeAt(u)!==41)&&(k=!0),u++}if(k){if(n.env.references===void 0)return!1;if(u<w&&n.src.charCodeAt(u)===91?(g=u+1,u=n.md.helpers.parseLinkLabel(n,u),u>=0?l=n.src.slice(g,u++):u=p+1):u=p+1,l||(l=n.src.slice(s,p)),!(v=n.env.references[e(l)]))return n.pos=h,!1;m=v.href,f=v.title}return r||(n.pos=s,n.posMax=p,y=n.push("link_open","a",1),y.attrs=t=[["href",m]],f&&t.push(["title",f]),n.md.inline.tokenize(n),y=n.push("link_close","a",-1)),n.pos=u,n.posMax=w,!0}},function(c,_,i){var e=i(0).isSpace;c.exports=function(o,n){var r,t,a=o.pos;if(o.src.charCodeAt(a)!==10)return!1;for(r=o.pending.length-1,t=o.posMax,n||(r>=0&&o.pending.charCodeAt(r)===32?r>=1&&o.pending.charCodeAt(r-1)===32?(o.pending=o.pending.replace(/ +$/,""),o.push("hardbreak","br",0)):(o.pending=o.pending.slice(0,-1),o.push("softbreak","br",0)):o.push("softbreak","br",0)),a++;a<t&&e(o.src.charCodeAt(a));)a++;return o.pos=a,!0}},function(c,_,i){function e(a,l,p,s){this.src=a,this.env=p,this.md=l,this.tokens=s,this.tokens_meta=Array(s.length),this.pos=0,this.posMax=this.src.length,this.level=0,this.pending="",this.pendingLevel=0,this.cache={},this.delimiters=[],this._prev_delimiters=[]}var o=i(33),n=i(0).isWhiteSpace,r=i(0).isPunctChar,t=i(0).isMdAsciiPunct;e.prototype.pushPending=function(){var a=new o("text","",0);return a.content=this.pending,a.level=this.pendingLevel,this.tokens.push(a),this.pending="",a},e.prototype.push=function(a,l,p){this.pending&&this.pushPending();var s=new o(a,l,p),u=null;return p<0&&(this.level--,this.delimiters=this._prev_delimiters.pop()),s.level=this.level,p>0&&(this.level++,this._prev_delimiters.push(this.delimiters),this.delimiters=[],u={delimiters:this.delimiters}),this.pendingLevel=this.level,this.tokens.push(s),this.tokens_meta.push(u),s},e.prototype.scanDelims=function(a,l){var p,s,u,d,v,f,y,m,h,w=a,g=!0,k=!0,b=this.posMax,C=this.src.charCodeAt(a);for(p=a>0?this.src.charCodeAt(a-1):32;w<b&&this.src.charCodeAt(w)===C;)w++;return u=w-a,s=w<b?this.src.charCodeAt(w):32,y=t(p)||r(String.fromCharCode(p)),h=t(s)||r(String.fromCharCode(s)),f=n(p),m=n(s),m?g=!1:h&&(f||y||(g=!1)),f?k=!1:y&&(m||h||(k=!1)),l?(d=g,v=k):(d=g&&(!k||y),v=k&&(!g||h)),{can_open:d,can_close:v,length:u}},e.prototype.Token=o,c.exports=e},function(c,_,i){function e(o){switch(o){case 10:case 33:case 35:case 36:case 37:case 38:case 42:case 43:case 45:case 58:case 60:case 61:case 62:case 64:case 91:case 92:case 93:case 94:case 95:case 96:case 123:case 125:case 126:return!0;default:return!1}}c.exports=function(o,n){for(var r=o.pos;r<o.posMax&&!e(o.src.charCodeAt(r));)r++;return r!==o.pos&&(n||(o.pending+=o.src.slice(o.pos,r)),o.pos=r,!0)}},function(c,_,i){c.exports=function(e){var o,n,r=0,t=e.tokens,a=e.tokens.length;for(o=n=0;o<a;o++)t[o].nesting<0&&r--,t[o].level=r,t[o].nesting>0&&r++,t[o].type==="text"&&o+1<a&&t[o+1].type==="text"?t[o+1].content=t[o].content+t[o+1].content:(o!==n&&(t[n]=t[o]),n++);o!==n&&(t.length=n)}},function(c,_){c.exports={Aacute:"\xC1",aacute:"\xE1",Abreve:"\u0102",abreve:"\u0103",ac:"\u223E",acd:"\u223F",acE:"\u223E\u0333",Acirc:"\xC2",acirc:"\xE2",acute:"\xB4",Acy:"\u0410",acy:"\u0430",AElig:"\xC6",aelig:"\xE6",af:"\u2061",Afr:"\u{1D504}",afr:"\u{1D51E}",Agrave:"\xC0",agrave:"\xE0",alefsym:"\u2135",aleph:"\u2135",Alpha:"\u0391",alpha:"\u03B1",Amacr:"\u0100",amacr:"\u0101",amalg:"\u2A3F",amp:"&",AMP:"&",andand:"\u2A55",And:"\u2A53",and:"\u2227",andd:"\u2A5C",andslope:"\u2A58",andv:"\u2A5A",ang:"\u2220",ange:"\u29A4",angle:"\u2220",angmsdaa:"\u29A8",angmsdab:"\u29A9",angmsdac:"\u29AA",angmsdad:"\u29AB",angmsdae:"\u29AC",angmsdaf:"\u29AD",angmsdag:"\u29AE",angmsdah:"\u29AF",angmsd:"\u2221",angrt:"\u221F",angrtvb:"\u22BE",angrtvbd:"\u299D",angsph:"\u2222",angst:"\xC5",angzarr:"\u237C",Aogon:"\u0104",aogon:"\u0105",Aopf:"\u{1D538}",aopf:"\u{1D552}",apacir:"\u2A6F",ap:"\u2248",apE:"\u2A70",ape:"\u224A",apid:"\u224B",apos:"'",ApplyFunction:"\u2061",approx:"\u2248",approxeq:"\u224A",Aring:"\xC5",aring:"\xE5",Ascr:"\u{1D49C}",ascr:"\u{1D4B6}",Assign:"\u2254",ast:"*",asymp:"\u2248",asympeq:"\u224D",Atilde:"\xC3",atilde:"\xE3",Auml:"\xC4",auml:"\xE4",awconint:"\u2233",awint:"\u2A11",backcong:"\u224C",backepsilon:"\u03F6",backprime:"\u2035",backsim:"\u223D",backsimeq:"\u22CD",Backslash:"\u2216",Barv:"\u2AE7",barvee:"\u22BD",barwed:"\u2305",Barwed:"\u2306",barwedge:"\u2305",bbrk:"\u23B5",bbrktbrk:"\u23B6",bcong:"\u224C",Bcy:"\u0411",bcy:"\u0431",bdquo:"\u201E",becaus:"\u2235",because:"\u2235",Because:"\u2235",bemptyv:"\u29B0",bepsi:"\u03F6",bernou:"\u212C",Bernoullis:"\u212C",Beta:"\u0392",beta:"\u03B2",beth:"\u2136",between:"\u226C",Bfr:"\u{1D505}",bfr:"\u{1D51F}",bigcap:"\u22C2",bigcirc:"\u25EF",bigcup:"\u22C3",bigodot:"\u2A00",bigoplus:"\u2A01",bigotimes:"\u2A02",bigsqcup:"\u2A06",bigstar:"\u2605",bigtriangledown:"\u25BD",bigtriangleup:"\u25B3",biguplus:"\u2A04",bigvee:"\u22C1",bigwedge:"\u22C0",bkarow:"\u290D",blacklozenge:"\u29EB",blacksquare:"\u25AA",blacktriangle:"\u25B4",blacktriangledown:"\u25BE",blacktriangleleft:"\u25C2",blacktriangleright:"\u25B8",blank:"\u2423",blk12:"\u2592",blk14:"\u2591",blk34:"\u2593",block:"\u2588",bne:"=\u20E5",bnequiv:"\u2261\u20E5",bNot:"\u2AED",bnot:"\u2310",Bopf:"\u{1D539}",bopf:"\u{1D553}",bot:"\u22A5",bottom:"\u22A5",bowtie:"\u22C8",boxbox:"\u29C9",boxdl:"\u2510",boxdL:"\u2555",boxDl:"\u2556",boxDL:"\u2557",boxdr:"\u250C",boxdR:"\u2552",boxDr:"\u2553",boxDR:"\u2554",boxh:"\u2500",boxH:"\u2550",boxhd:"\u252C",boxHd:"\u2564",boxhD:"\u2565",boxHD:"\u2566",boxhu:"\u2534",boxHu:"\u2567",boxhU:"\u2568",boxHU:"\u2569",boxminus:"\u229F",boxplus:"\u229E",boxtimes:"\u22A0",boxul:"\u2518",boxuL:"\u255B",boxUl:"\u255C",boxUL:"\u255D",boxur:"\u2514",boxuR:"\u2558",boxUr:"\u2559",boxUR:"\u255A",boxv:"\u2502",boxV:"\u2551",boxvh:"\u253C",boxvH:"\u256A",boxVh:"\u256B",boxVH:"\u256C",boxvl:"\u2524",boxvL:"\u2561",boxVl:"\u2562",boxVL:"\u2563",boxvr:"\u251C",boxvR:"\u255E",boxVr:"\u255F",boxVR:"\u2560",bprime:"\u2035",breve:"\u02D8",Breve:"\u02D8",brvbar:"\xA6",bscr:"\u{1D4B7}",Bscr:"\u212C",bsemi:"\u204F",bsim:"\u223D",bsime:"\u22CD",bsolb:"\u29C5",bsol:"\\",bsolhsub:"\u27C8",bull:"\u2022",bullet:"\u2022",bump:"\u224E",bumpE:"\u2AAE",bumpe:"\u224F",Bumpeq:"\u224E",bumpeq:"\u224F",Cacute:"\u0106",cacute:"\u0107",capand:"\u2A44",capbrcup:"\u2A49",capcap:"\u2A4B",cap:"\u2229",Cap:"\u22D2",capcup:"\u2A47",capdot:"\u2A40",CapitalDifferentialD:"\u2145",caps:"\u2229\uFE00",caret:"\u2041",caron:"\u02C7",Cayleys:"\u212D",ccaps:"\u2A4D",Ccaron:"\u010C",ccaron:"\u010D",Ccedil:"\xC7",ccedil:"\xE7",Ccirc:"\u0108",ccirc:"\u0109",Cconint:"\u2230",ccups:"\u2A4C",ccupssm:"\u2A50",Cdot:"\u010A",cdot:"\u010B",cedil:"\xB8",Cedilla:"\xB8",cemptyv:"\u29B2",cent:"\xA2",centerdot:"\xB7",CenterDot:"\xB7",cfr:"\u{1D520}",Cfr:"\u212D",CHcy:"\u0427",chcy:"\u0447",check:"\u2713",checkmark:"\u2713",Chi:"\u03A7",chi:"\u03C7",circ:"\u02C6",circeq:"\u2257",circlearrowleft:"\u21BA",circlearrowright:"\u21BB",circledast:"\u229B",circledcirc:"\u229A",circleddash:"\u229D",CircleDot:"\u2299",circledR:"\xAE",circledS:"\u24C8",CircleMinus:"\u2296",CirclePlus:"\u2295",CircleTimes:"\u2297",cir:"\u25CB",cirE:"\u29C3",cire:"\u2257",cirfnint:"\u2A10",cirmid:"\u2AEF",cirscir:"\u29C2",ClockwiseContourIntegral:"\u2232",CloseCurlyDoubleQuote:"\u201D",CloseCurlyQuote:"\u2019",clubs:"\u2663",clubsuit:"\u2663",colon:":",Colon:"\u2237",Colone:"\u2A74",colone:"\u2254",coloneq:"\u2254",comma:",",commat:"@",comp:"\u2201",compfn:"\u2218",complement:"\u2201",complexes:"\u2102",cong:"\u2245",congdot:"\u2A6D",Congruent:"\u2261",conint:"\u222E",Conint:"\u222F",ContourIntegral:"\u222E",copf:"\u{1D554}",Copf:"\u2102",coprod:"\u2210",Coproduct:"\u2210",copy:"\xA9",COPY:"\xA9",copysr:"\u2117",CounterClockwiseContourIntegral:"\u2233",crarr:"\u21B5",cross:"\u2717",Cross:"\u2A2F",Cscr:"\u{1D49E}",cscr:"\u{1D4B8}",csub:"\u2ACF",csube:"\u2AD1",csup:"\u2AD0",csupe:"\u2AD2",ctdot:"\u22EF",cudarrl:"\u2938",cudarrr:"\u2935",cuepr:"\u22DE",cuesc:"\u22DF",cularr:"\u21B6",cularrp:"\u293D",cupbrcap:"\u2A48",cupcap:"\u2A46",CupCap:"\u224D",cup:"\u222A",Cup:"\u22D3",cupcup:"\u2A4A",cupdot:"\u228D",cupor:"\u2A45",cups:"\u222A\uFE00",curarr:"\u21B7",curarrm:"\u293C",curlyeqprec:"\u22DE",curlyeqsucc:"\u22DF",curlyvee:"\u22CE",curlywedge:"\u22CF",curren:"\xA4",curvearrowleft:"\u21B6",curvearrowright:"\u21B7",cuvee:"\u22CE",cuwed:"\u22CF",cwconint:"\u2232",cwint:"\u2231",cylcty:"\u232D",dagger:"\u2020",Dagger:"\u2021",daleth:"\u2138",darr:"\u2193",Darr:"\u21A1",dArr:"\u21D3",dash:"\u2010",Dashv:"\u2AE4",dashv:"\u22A3",dbkarow:"\u290F",dblac:"\u02DD",Dcaron:"\u010E",dcaron:"\u010F",Dcy:"\u0414",dcy:"\u0434",ddagger:"\u2021",ddarr:"\u21CA",DD:"\u2145",dd:"\u2146",DDotrahd:"\u2911",ddotseq:"\u2A77",deg:"\xB0",Del:"\u2207",Delta:"\u0394",delta:"\u03B4",demptyv:"\u29B1",dfisht:"\u297F",Dfr:"\u{1D507}",dfr:"\u{1D521}",dHar:"\u2965",dharl:"\u21C3",dharr:"\u21C2",DiacriticalAcute:"\xB4",DiacriticalDot:"\u02D9",DiacriticalDoubleAcute:"\u02DD",DiacriticalGrave:"`",DiacriticalTilde:"\u02DC",diam:"\u22C4",diamond:"\u22C4",Diamond:"\u22C4",diamondsuit:"\u2666",diams:"\u2666",die:"\xA8",DifferentialD:"\u2146",digamma:"\u03DD",disin:"\u22F2",div:"\xF7",divide:"\xF7",divideontimes:"\u22C7",divonx:"\u22C7",DJcy:"\u0402",djcy:"\u0452",dlcorn:"\u231E",dlcrop:"\u230D",dollar:"$",Dopf:"\u{1D53B}",dopf:"\u{1D555}",Dot:"\xA8",dot:"\u02D9",DotDot:"\u20DC",doteq:"\u2250",doteqdot:"\u2251",DotEqual:"\u2250",dotminus:"\u2238",dotplus:"\u2214",dotsquare:"\u22A1",doublebarwedge:"\u2306",DoubleContourIntegral:"\u222F",DoubleDot:"\xA8",DoubleDownArrow:"\u21D3",DoubleLeftArrow:"\u21D0",DoubleLeftRightArrow:"\u21D4",DoubleLeftTee:"\u2AE4",DoubleLongLeftArrow:"\u27F8",DoubleLongLeftRightArrow:"\u27FA",DoubleLongRightArrow:"\u27F9",DoubleRightArrow:"\u21D2",DoubleRightTee:"\u22A8",DoubleUpArrow:"\u21D1",DoubleUpDownArrow:"\u21D5",DoubleVerticalBar:"\u2225",DownArrowBar:"\u2913",downarrow:"\u2193",DownArrow:"\u2193",Downarrow:"\u21D3",DownArrowUpArrow:"\u21F5",DownBreve:"\u0311",downdownarrows:"\u21CA",downharpoonleft:"\u21C3",downharpoonright:"\u21C2",DownLeftRightVector:"\u2950",DownLeftTeeVector:"\u295E",DownLeftVectorBar:"\u2956",DownLeftVector:"\u21BD",DownRightTeeVector:"\u295F",DownRightVectorBar:"\u2957",DownRightVector:"\u21C1",DownTeeArrow:"\u21A7",DownTee:"\u22A4",drbkarow:"\u2910",drcorn:"\u231F",drcrop:"\u230C",Dscr:"\u{1D49F}",dscr:"\u{1D4B9}",DScy:"\u0405",dscy:"\u0455",dsol:"\u29F6",Dstrok:"\u0110",dstrok:"\u0111",dtdot:"\u22F1",dtri:"\u25BF",dtrif:"\u25BE",duarr:"\u21F5",duhar:"\u296F",dwangle:"\u29A6",DZcy:"\u040F",dzcy:"\u045F",dzigrarr:"\u27FF",Eacute:"\xC9",eacute:"\xE9",easter:"\u2A6E",Ecaron:"\u011A",ecaron:"\u011B",Ecirc:"\xCA",ecirc:"\xEA",ecir:"\u2256",ecolon:"\u2255",Ecy:"\u042D",ecy:"\u044D",eDDot:"\u2A77",Edot:"\u0116",edot:"\u0117",eDot:"\u2251",ee:"\u2147",efDot:"\u2252",Efr:"\u{1D508}",efr:"\u{1D522}",eg:"\u2A9A",Egrave:"\xC8",egrave:"\xE8",egs:"\u2A96",egsdot:"\u2A98",el:"\u2A99",Element:"\u2208",elinters:"\u23E7",ell:"\u2113",els:"\u2A95",elsdot:"\u2A97",Emacr:"\u0112",emacr:"\u0113",empty:"\u2205",emptyset:"\u2205",EmptySmallSquare:"\u25FB",emptyv:"\u2205",EmptyVerySmallSquare:"\u25AB",emsp13:"\u2004",emsp14:"\u2005",emsp:"\u2003",ENG:"\u014A",eng:"\u014B",ensp:"\u2002",Eogon:"\u0118",eogon:"\u0119",Eopf:"\u{1D53C}",eopf:"\u{1D556}",epar:"\u22D5",eparsl:"\u29E3",eplus:"\u2A71",epsi:"\u03B5",Epsilon:"\u0395",epsilon:"\u03B5",epsiv:"\u03F5",eqcirc:"\u2256",eqcolon:"\u2255",eqsim:"\u2242",eqslantgtr:"\u2A96",eqslantless:"\u2A95",Equal:"\u2A75",equals:"=",EqualTilde:"\u2242",equest:"\u225F",Equilibrium:"\u21CC",equiv:"\u2261",equivDD:"\u2A78",eqvparsl:"\u29E5",erarr:"\u2971",erDot:"\u2253",escr:"\u212F",Escr:"\u2130",esdot:"\u2250",Esim:"\u2A73",esim:"\u2242",Eta:"\u0397",eta:"\u03B7",ETH:"\xD0",eth:"\xF0",Euml:"\xCB",euml:"\xEB",euro:"\u20AC",excl:"!",exist:"\u2203",Exists:"\u2203",expectation:"\u2130",exponentiale:"\u2147",ExponentialE:"\u2147",fallingdotseq:"\u2252",Fcy:"\u0424",fcy:"\u0444",female:"\u2640",ffilig:"\uFB03",fflig:"\uFB00",ffllig:"\uFB04",Ffr:"\u{1D509}",ffr:"\u{1D523}",filig:"\uFB01",FilledSmallSquare:"\u25FC",FilledVerySmallSquare:"\u25AA",fjlig:"fj",flat:"\u266D",fllig:"\uFB02",fltns:"\u25B1",fnof:"\u0192",Fopf:"\u{1D53D}",fopf:"\u{1D557}",forall:"\u2200",ForAll:"\u2200",fork:"\u22D4",forkv:"\u2AD9",Fouriertrf:"\u2131",fpartint:"\u2A0D",frac12:"\xBD",frac13:"\u2153",frac14:"\xBC",frac15:"\u2155",frac16:"\u2159",frac18:"\u215B",frac23:"\u2154",frac25:"\u2156",frac34:"\xBE",frac35:"\u2157",frac38:"\u215C",frac45:"\u2158",frac56:"\u215A",frac58:"\u215D",frac78:"\u215E",frasl:"\u2044",frown:"\u2322",fscr:"\u{1D4BB}",Fscr:"\u2131",gacute:"\u01F5",Gamma:"\u0393",gamma:"\u03B3",Gammad:"\u03DC",gammad:"\u03DD",gap:"\u2A86",Gbreve:"\u011E",gbreve:"\u011F",Gcedil:"\u0122",Gcirc:"\u011C",gcirc:"\u011D",Gcy:"\u0413",gcy:"\u0433",Gdot:"\u0120",gdot:"\u0121",ge:"\u2265",gE:"\u2267",gEl:"\u2A8C",gel:"\u22DB",geq:"\u2265",geqq:"\u2267",geqslant:"\u2A7E",gescc:"\u2AA9",ges:"\u2A7E",gesdot:"\u2A80",gesdoto:"\u2A82",gesdotol:"\u2A84",gesl:"\u22DB\uFE00",gesles:"\u2A94",Gfr:"\u{1D50A}",gfr:"\u{1D524}",gg:"\u226B",Gg:"\u22D9",ggg:"\u22D9",gimel:"\u2137",GJcy:"\u0403",gjcy:"\u0453",gla:"\u2AA5",gl:"\u2277",glE:"\u2A92",glj:"\u2AA4",gnap:"\u2A8A",gnapprox:"\u2A8A",gne:"\u2A88",gnE:"\u2269",gneq:"\u2A88",gneqq:"\u2269",gnsim:"\u22E7",Gopf:"\u{1D53E}",gopf:"\u{1D558}",grave:"`",GreaterEqual:"\u2265",GreaterEqualLess:"\u22DB",GreaterFullEqual:"\u2267",GreaterGreater:"\u2AA2",GreaterLess:"\u2277",GreaterSlantEqual:"\u2A7E",GreaterTilde:"\u2273",Gscr:"\u{1D4A2}",gscr:"\u210A",gsim:"\u2273",gsime:"\u2A8E",gsiml:"\u2A90",gtcc:"\u2AA7",gtcir:"\u2A7A",gt:">",GT:">",Gt:"\u226B",gtdot:"\u22D7",gtlPar:"\u2995",gtquest:"\u2A7C",gtrapprox:"\u2A86",gtrarr:"\u2978",gtrdot:"\u22D7",gtreqless:"\u22DB",gtreqqless:"\u2A8C",gtrless:"\u2277",gtrsim:"\u2273",gvertneqq:"\u2269\uFE00",gvnE:"\u2269\uFE00",Hacek:"\u02C7",hairsp:"\u200A",half:"\xBD",hamilt:"\u210B",HARDcy:"\u042A",hardcy:"\u044A",harrcir:"\u2948",harr:"\u2194",hArr:"\u21D4",harrw:"\u21AD",Hat:"^",hbar:"\u210F",Hcirc:"\u0124",hcirc:"\u0125",hearts:"\u2665",heartsuit:"\u2665",hellip:"\u2026",hercon:"\u22B9",hfr:"\u{1D525}",Hfr:"\u210C",HilbertSpace:"\u210B",hksearow:"\u2925",hkswarow:"\u2926",hoarr:"\u21FF",homtht:"\u223B",hookleftarrow:"\u21A9",hookrightarrow:"\u21AA",hopf:"\u{1D559}",Hopf:"\u210D",horbar:"\u2015",HorizontalLine:"\u2500",hscr:"\u{1D4BD}",Hscr:"\u210B",hslash:"\u210F",Hstrok:"\u0126",hstrok:"\u0127",HumpDownHump:"\u224E",HumpEqual:"\u224F",hybull:"\u2043",hyphen:"\u2010",Iacute:"\xCD",iacute:"\xED",ic:"\u2063",Icirc:"\xCE",icirc:"\xEE",Icy:"\u0418",icy:"\u0438",Idot:"\u0130",IEcy:"\u0415",iecy:"\u0435",iexcl:"\xA1",iff:"\u21D4",ifr:"\u{1D526}",Ifr:"\u2111",Igrave:"\xCC",igrave:"\xEC",ii:"\u2148",iiiint:"\u2A0C",iiint:"\u222D",iinfin:"\u29DC",iiota:"\u2129",IJlig:"\u0132",ijlig:"\u0133",Imacr:"\u012A",imacr:"\u012B",image:"\u2111",ImaginaryI:"\u2148",imagline:"\u2110",imagpart:"\u2111",imath:"\u0131",Im:"\u2111",imof:"\u22B7",imped:"\u01B5",Implies:"\u21D2",incare:"\u2105",in:"\u2208",infin:"\u221E",infintie:"\u29DD",inodot:"\u0131",intcal:"\u22BA",int:"\u222B",Int:"\u222C",integers:"\u2124",Integral:"\u222B",intercal:"\u22BA",Intersection:"\u22C2",intlarhk:"\u2A17",intprod:"\u2A3C",InvisibleComma:"\u2063",InvisibleTimes:"\u2062",IOcy:"\u0401",iocy:"\u0451",Iogon:"\u012E",iogon:"\u012F",Iopf:"\u{1D540}",iopf:"\u{1D55A}",Iota:"\u0399",iota:"\u03B9",iprod:"\u2A3C",iquest:"\xBF",iscr:"\u{1D4BE}",Iscr:"\u2110",isin:"\u2208",isindot:"\u22F5",isinE:"\u22F9",isins:"\u22F4",isinsv:"\u22F3",isinv:"\u2208",it:"\u2062",Itilde:"\u0128",itilde:"\u0129",Iukcy:"\u0406",iukcy:"\u0456",Iuml:"\xCF",iuml:"\xEF",Jcirc:"\u0134",jcirc:"\u0135",Jcy:"\u0419",jcy:"\u0439",Jfr:"\u{1D50D}",jfr:"\u{1D527}",jmath:"\u0237",Jopf:"\u{1D541}",jopf:"\u{1D55B}",Jscr:"\u{1D4A5}",jscr:"\u{1D4BF}",Jsercy:"\u0408",jsercy:"\u0458",Jukcy:"\u0404",jukcy:"\u0454",Kappa:"\u039A",kappa:"\u03BA",kappav:"\u03F0",Kcedil:"\u0136",kcedil:"\u0137",Kcy:"\u041A",kcy:"\u043A",Kfr:"\u{1D50E}",kfr:"\u{1D528}",kgreen:"\u0138",KHcy:"\u0425",khcy:"\u0445",KJcy:"\u040C",kjcy:"\u045C",Kopf:"\u{1D542}",kopf:"\u{1D55C}",Kscr:"\u{1D4A6}",kscr:"\u{1D4C0}",lAarr:"\u21DA",Lacute:"\u0139",lacute:"\u013A",laemptyv:"\u29B4",lagran:"\u2112",Lambda:"\u039B",lambda:"\u03BB",lang:"\u27E8",Lang:"\u27EA",langd:"\u2991",langle:"\u27E8",lap:"\u2A85",Laplacetrf:"\u2112",laquo:"\xAB",larrb:"\u21E4",larrbfs:"\u291F",larr:"\u2190",Larr:"\u219E",lArr:"\u21D0",larrfs:"\u291D",larrhk:"\u21A9",larrlp:"\u21AB",larrpl:"\u2939",larrsim:"\u2973",larrtl:"\u21A2",latail:"\u2919",lAtail:"\u291B",lat:"\u2AAB",late:"\u2AAD",lates:"\u2AAD\uFE00",lbarr:"\u290C",lBarr:"\u290E",lbbrk:"\u2772",lbrace:"{",lbrack:"[",lbrke:"\u298B",lbrksld:"\u298F",lbrkslu:"\u298D",Lcaron:"\u013D",lcaron:"\u013E",Lcedil:"\u013B",lcedil:"\u013C",lceil:"\u2308",lcub:"{",Lcy:"\u041B",lcy:"\u043B",ldca:"\u2936",ldquo:"\u201C",ldquor:"\u201E",ldrdhar:"\u2967",ldrushar:"\u294B",ldsh:"\u21B2",le:"\u2264",lE:"\u2266",LeftAngleBracket:"\u27E8",LeftArrowBar:"\u21E4",leftarrow:"\u2190",LeftArrow:"\u2190",Leftarrow:"\u21D0",LeftArrowRightArrow:"\u21C6",leftarrowtail:"\u21A2",LeftCeiling:"\u2308",LeftDoubleBracket:"\u27E6",LeftDownTeeVector:"\u2961",LeftDownVectorBar:"\u2959",LeftDownVector:"\u21C3",LeftFloor:"\u230A",leftharpoondown:"\u21BD",leftharpoonup:"\u21BC",leftleftarrows:"\u21C7",leftrightarrow:"\u2194",LeftRightArrow:"\u2194",Leftrightarrow:"\u21D4",leftrightarrows:"\u21C6",leftrightharpoons:"\u21CB",leftrightsquigarrow:"\u21AD",LeftRightVector:"\u294E",LeftTeeArrow:"\u21A4",LeftTee:"\u22A3",LeftTeeVector:"\u295A",leftthreetimes:"\u22CB",LeftTriangleBar:"\u29CF",LeftTriangle:"\u22B2",LeftTriangleEqual:"\u22B4",LeftUpDownVector:"\u2951",LeftUpTeeVector:"\u2960",LeftUpVectorBar:"\u2958",LeftUpVector:"\u21BF",LeftVectorBar:"\u2952",LeftVector:"\u21BC",lEg:"\u2A8B",leg:"\u22DA",leq:"\u2264",leqq:"\u2266",leqslant:"\u2A7D",lescc:"\u2AA8",les:"\u2A7D",lesdot:"\u2A7F",lesdoto:"\u2A81",lesdotor:"\u2A83",lesg:"\u22DA\uFE00",lesges:"\u2A93",lessapprox:"\u2A85",lessdot:"\u22D6",lesseqgtr:"\u22DA",lesseqqgtr:"\u2A8B",LessEqualGreater:"\u22DA",LessFullEqual:"\u2266",LessGreater:"\u2276",lessgtr:"\u2276",LessLess:"\u2AA1",lesssim:"\u2272",LessSlantEqual:"\u2A7D",LessTilde:"\u2272",lfisht:"\u297C",lfloor:"\u230A",Lfr:"\u{1D50F}",lfr:"\u{1D529}",lg:"\u2276",lgE:"\u2A91",lHar:"\u2962",lhard:"\u21BD",lharu:"\u21BC",lharul:"\u296A",lhblk:"\u2584",LJcy:"\u0409",ljcy:"\u0459",llarr:"\u21C7",ll:"\u226A",Ll:"\u22D8",llcorner:"\u231E",Lleftarrow:"\u21DA",llhard:"\u296B",lltri:"\u25FA",Lmidot:"\u013F",lmidot:"\u0140",lmoustache:"\u23B0",lmoust:"\u23B0",lnap:"\u2A89",lnapprox:"\u2A89",lne:"\u2A87",lnE:"\u2268",lneq:"\u2A87",lneqq:"\u2268",lnsim:"\u22E6",loang:"\u27EC",loarr:"\u21FD",lobrk:"\u27E6",longleftarrow:"\u27F5",LongLeftArrow:"\u27F5",Longleftarrow:"\u27F8",longleftrightarrow:"\u27F7",LongLeftRightArrow:"\u27F7",Longleftrightarrow:"\u27FA",longmapsto:"\u27FC",longrightarrow:"\u27F6",LongRightArrow:"\u27F6",Longrightarrow:"\u27F9",looparrowleft:"\u21AB",looparrowright:"\u21AC",lopar:"\u2985",Lopf:"\u{1D543}",lopf:"\u{1D55D}",loplus:"\u2A2D",lotimes:"\u2A34",lowast:"\u2217",lowbar:"_",LowerLeftArrow:"\u2199",LowerRightArrow:"\u2198",loz:"\u25CA",lozenge:"\u25CA",lozf:"\u29EB",lpar:"(",lparlt:"\u2993",lrarr:"\u21C6",lrcorner:"\u231F",lrhar:"\u21CB",lrhard:"\u296D",lrm:"\u200E",lrtri:"\u22BF",lsaquo:"\u2039",lscr:"\u{1D4C1}",Lscr:"\u2112",lsh:"\u21B0",Lsh:"\u21B0",lsim:"\u2272",lsime:"\u2A8D",lsimg:"\u2A8F",lsqb:"[",lsquo:"\u2018",lsquor:"\u201A",Lstrok:"\u0141",lstrok:"\u0142",ltcc:"\u2AA6",ltcir:"\u2A79",lt:"<",LT:"<",Lt:"\u226A",ltdot:"\u22D6",lthree:"\u22CB",ltimes:"\u22C9",ltlarr:"\u2976",ltquest:"\u2A7B",ltri:"\u25C3",ltrie:"\u22B4",ltrif:"\u25C2",ltrPar:"\u2996",lurdshar:"\u294A",luruhar:"\u2966",lvertneqq:"\u2268\uFE00",lvnE:"\u2268\uFE00",macr:"\xAF",male:"\u2642",malt:"\u2720",maltese:"\u2720",Map:"\u2905",map:"\u21A6",mapsto:"\u21A6",mapstodown:"\u21A7",mapstoleft:"\u21A4",mapstoup:"\u21A5",marker:"\u25AE",mcomma:"\u2A29",Mcy:"\u041C",mcy:"\u043C",mdash:"\u2014",mDDot:"\u223A",measuredangle:"\u2221",MediumSpace:"\u205F",Mellintrf:"\u2133",Mfr:"\u{1D510}",mfr:"\u{1D52A}",mho:"\u2127",micro:"\xB5",midast:"*",midcir:"\u2AF0",mid:"\u2223",middot:"\xB7",minusb:"\u229F",minus:"\u2212",minusd:"\u2238",minusdu:"\u2A2A",MinusPlus:"\u2213",mlcp:"\u2ADB",mldr:"\u2026",mnplus:"\u2213",models:"\u22A7",Mopf:"\u{1D544}",mopf:"\u{1D55E}",mp:"\u2213",mscr:"\u{1D4C2}",Mscr:"\u2133",mstpos:"\u223E",Mu:"\u039C",mu:"\u03BC",multimap:"\u22B8",mumap:"\u22B8",nabla:"\u2207",Nacute:"\u0143",nacute:"\u0144",nang:"\u2220\u20D2",nap:"\u2249",napE:"\u2A70\u0338",napid:"\u224B\u0338",napos:"\u0149",napprox:"\u2249",natural:"\u266E",naturals:"\u2115",natur:"\u266E",nbsp:"\xA0",nbump:"\u224E\u0338",nbumpe:"\u224F\u0338",ncap:"\u2A43",Ncaron:"\u0147",ncaron:"\u0148",Ncedil:"\u0145",ncedil:"\u0146",ncong:"\u2247",ncongdot:"\u2A6D\u0338",ncup:"\u2A42",Ncy:"\u041D",ncy:"\u043D",ndash:"\u2013",nearhk:"\u2924",nearr:"\u2197",neArr:"\u21D7",nearrow:"\u2197",ne:"\u2260",nedot:"\u2250\u0338",NegativeMediumSpace:"\u200B",NegativeThickSpace:"\u200B",NegativeThinSpace:"\u200B",NegativeVeryThinSpace:"\u200B",nequiv:"\u2262",nesear:"\u2928",nesim:"\u2242\u0338",NestedGreaterGreater:"\u226B",NestedLessLess:"\u226A",NewLine:`
`,nexist:"\u2204",nexists:"\u2204",Nfr:"\u{1D511}",nfr:"\u{1D52B}",ngE:"\u2267\u0338",nge:"\u2271",ngeq:"\u2271",ngeqq:"\u2267\u0338",ngeqslant:"\u2A7E\u0338",nges:"\u2A7E\u0338",nGg:"\u22D9\u0338",ngsim:"\u2275",nGt:"\u226B\u20D2",ngt:"\u226F",ngtr:"\u226F",nGtv:"\u226B\u0338",nharr:"\u21AE",nhArr:"\u21CE",nhpar:"\u2AF2",ni:"\u220B",nis:"\u22FC",nisd:"\u22FA",niv:"\u220B",NJcy:"\u040A",njcy:"\u045A",nlarr:"\u219A",nlArr:"\u21CD",nldr:"\u2025",nlE:"\u2266\u0338",nle:"\u2270",nleftarrow:"\u219A",nLeftarrow:"\u21CD",nleftrightarrow:"\u21AE",nLeftrightarrow:"\u21CE",nleq:"\u2270",nleqq:"\u2266\u0338",nleqslant:"\u2A7D\u0338",nles:"\u2A7D\u0338",nless:"\u226E",nLl:"\u22D8\u0338",nlsim:"\u2274",nLt:"\u226A\u20D2",nlt:"\u226E",nltri:"\u22EA",nltrie:"\u22EC",nLtv:"\u226A\u0338",nmid:"\u2224",NoBreak:"\u2060",NonBreakingSpace:"\xA0",nopf:"\u{1D55F}",Nopf:"\u2115",Not:"\u2AEC",not:"\xAC",NotCongruent:"\u2262",NotCupCap:"\u226D",NotDoubleVerticalBar:"\u2226",NotElement:"\u2209",NotEqual:"\u2260",NotEqualTilde:"\u2242\u0338",NotExists:"\u2204",NotGreater:"\u226F",NotGreaterEqual:"\u2271",NotGreaterFullEqual:"\u2267\u0338",NotGreaterGreater:"\u226B\u0338",NotGreaterLess:"\u2279",NotGreaterSlantEqual:"\u2A7E\u0338",NotGreaterTilde:"\u2275",NotHumpDownHump:"\u224E\u0338",NotHumpEqual:"\u224F\u0338",notin:"\u2209",notindot:"\u22F5\u0338",notinE:"\u22F9\u0338",notinva:"\u2209",notinvb:"\u22F7",notinvc:"\u22F6",NotLeftTriangleBar:"\u29CF\u0338",NotLeftTriangle:"\u22EA",NotLeftTriangleEqual:"\u22EC",NotLess:"\u226E",NotLessEqual:"\u2270",NotLessGreater:"\u2278",NotLessLess:"\u226A\u0338",NotLessSlantEqual:"\u2A7D\u0338",NotLessTilde:"\u2274",NotNestedGreaterGreater:"\u2AA2\u0338",NotNestedLessLess:"\u2AA1\u0338",notni:"\u220C",notniva:"\u220C",notnivb:"\u22FE",notnivc:"\u22FD",NotPrecedes:"\u2280",NotPrecedesEqual:"\u2AAF\u0338",NotPrecedesSlantEqual:"\u22E0",NotReverseElement:"\u220C",NotRightTriangleBar:"\u29D0\u0338",NotRightTriangle:"\u22EB",NotRightTriangleEqual:"\u22ED",NotSquareSubset:"\u228F\u0338",NotSquareSubsetEqual:"\u22E2",NotSquareSuperset:"\u2290\u0338",NotSquareSupersetEqual:"\u22E3",NotSubset:"\u2282\u20D2",NotSubsetEqual:"\u2288",NotSucceeds:"\u2281",NotSucceedsEqual:"\u2AB0\u0338",NotSucceedsSlantEqual:"\u22E1",NotSucceedsTilde:"\u227F\u0338",NotSuperset:"\u2283\u20D2",NotSupersetEqual:"\u2289",NotTilde:"\u2241",NotTildeEqual:"\u2244",NotTildeFullEqual:"\u2247",NotTildeTilde:"\u2249",NotVerticalBar:"\u2224",nparallel:"\u2226",npar:"\u2226",nparsl:"\u2AFD\u20E5",npart:"\u2202\u0338",npolint:"\u2A14",npr:"\u2280",nprcue:"\u22E0",nprec:"\u2280",npreceq:"\u2AAF\u0338",npre:"\u2AAF\u0338",nrarrc:"\u2933\u0338",nrarr:"\u219B",nrArr:"\u21CF",nrarrw:"\u219D\u0338",nrightarrow:"\u219B",nRightarrow:"\u21CF",nrtri:"\u22EB",nrtrie:"\u22ED",nsc:"\u2281",nsccue:"\u22E1",nsce:"\u2AB0\u0338",Nscr:"\u{1D4A9}",nscr:"\u{1D4C3}",nshortmid:"\u2224",nshortparallel:"\u2226",nsim:"\u2241",nsime:"\u2244",nsimeq:"\u2244",nsmid:"\u2224",nspar:"\u2226",nsqsube:"\u22E2",nsqsupe:"\u22E3",nsub:"\u2284",nsubE:"\u2AC5\u0338",nsube:"\u2288",nsubset:"\u2282\u20D2",nsubseteq:"\u2288",nsubseteqq:"\u2AC5\u0338",nsucc:"\u2281",nsucceq:"\u2AB0\u0338",nsup:"\u2285",nsupE:"\u2AC6\u0338",nsupe:"\u2289",nsupset:"\u2283\u20D2",nsupseteq:"\u2289",nsupseteqq:"\u2AC6\u0338",ntgl:"\u2279",Ntilde:"\xD1",ntilde:"\xF1",ntlg:"\u2278",ntriangleleft:"\u22EA",ntrianglelefteq:"\u22EC",ntriangleright:"\u22EB",ntrianglerighteq:"\u22ED",Nu:"\u039D",nu:"\u03BD",num:"#",numero:"\u2116",numsp:"\u2007",nvap:"\u224D\u20D2",nvdash:"\u22AC",nvDash:"\u22AD",nVdash:"\u22AE",nVDash:"\u22AF",nvge:"\u2265\u20D2",nvgt:">\u20D2",nvHarr:"\u2904",nvinfin:"\u29DE",nvlArr:"\u2902",nvle:"\u2264\u20D2",nvlt:"<\u20D2",nvltrie:"\u22B4\u20D2",nvrArr:"\u2903",nvrtrie:"\u22B5\u20D2",nvsim:"\u223C\u20D2",nwarhk:"\u2923",nwarr:"\u2196",nwArr:"\u21D6",nwarrow:"\u2196",nwnear:"\u2927",Oacute:"\xD3",oacute:"\xF3",oast:"\u229B",Ocirc:"\xD4",ocirc:"\xF4",ocir:"\u229A",Ocy:"\u041E",ocy:"\u043E",odash:"\u229D",Odblac:"\u0150",odblac:"\u0151",odiv:"\u2A38",odot:"\u2299",odsold:"\u29BC",OElig:"\u0152",oelig:"\u0153",ofcir:"\u29BF",Ofr:"\u{1D512}",ofr:"\u{1D52C}",ogon:"\u02DB",Ograve:"\xD2",ograve:"\xF2",ogt:"\u29C1",ohbar:"\u29B5",ohm:"\u03A9",oint:"\u222E",olarr:"\u21BA",olcir:"\u29BE",olcross:"\u29BB",oline:"\u203E",olt:"\u29C0",Omacr:"\u014C",omacr:"\u014D",Omega:"\u03A9",omega:"\u03C9",Omicron:"\u039F",omicron:"\u03BF",omid:"\u29B6",ominus:"\u2296",Oopf:"\u{1D546}",oopf:"\u{1D560}",opar:"\u29B7",OpenCurlyDoubleQuote:"\u201C",OpenCurlyQuote:"\u2018",operp:"\u29B9",oplus:"\u2295",orarr:"\u21BB",Or:"\u2A54",or:"\u2228",ord:"\u2A5D",order:"\u2134",orderof:"\u2134",ordf:"\xAA",ordm:"\xBA",origof:"\u22B6",oror:"\u2A56",orslope:"\u2A57",orv:"\u2A5B",oS:"\u24C8",Oscr:"\u{1D4AA}",oscr:"\u2134",Oslash:"\xD8",oslash:"\xF8",osol:"\u2298",Otilde:"\xD5",otilde:"\xF5",otimesas:"\u2A36",Otimes:"\u2A37",otimes:"\u2297",Ouml:"\xD6",ouml:"\xF6",ovbar:"\u233D",OverBar:"\u203E",OverBrace:"\u23DE",OverBracket:"\u23B4",OverParenthesis:"\u23DC",para:"\xB6",parallel:"\u2225",par:"\u2225",parsim:"\u2AF3",parsl:"\u2AFD",part:"\u2202",PartialD:"\u2202",Pcy:"\u041F",pcy:"\u043F",percnt:"%",period:".",permil:"\u2030",perp:"\u22A5",pertenk:"\u2031",Pfr:"\u{1D513}",pfr:"\u{1D52D}",Phi:"\u03A6",phi:"\u03C6",phiv:"\u03D5",phmmat:"\u2133",phone:"\u260E",Pi:"\u03A0",pi:"\u03C0",pitchfork:"\u22D4",piv:"\u03D6",planck:"\u210F",planckh:"\u210E",plankv:"\u210F",plusacir:"\u2A23",plusb:"\u229E",pluscir:"\u2A22",plus:"+",plusdo:"\u2214",plusdu:"\u2A25",pluse:"\u2A72",PlusMinus:"\xB1",plusmn:"\xB1",plussim:"\u2A26",plustwo:"\u2A27",pm:"\xB1",Poincareplane:"\u210C",pointint:"\u2A15",popf:"\u{1D561}",Popf:"\u2119",pound:"\xA3",prap:"\u2AB7",Pr:"\u2ABB",pr:"\u227A",prcue:"\u227C",precapprox:"\u2AB7",prec:"\u227A",preccurlyeq:"\u227C",Precedes:"\u227A",PrecedesEqual:"\u2AAF",PrecedesSlantEqual:"\u227C",PrecedesTilde:"\u227E",preceq:"\u2AAF",precnapprox:"\u2AB9",precneqq:"\u2AB5",precnsim:"\u22E8",pre:"\u2AAF",prE:"\u2AB3",precsim:"\u227E",prime:"\u2032",Prime:"\u2033",primes:"\u2119",prnap:"\u2AB9",prnE:"\u2AB5",prnsim:"\u22E8",prod:"\u220F",Product:"\u220F",profalar:"\u232E",profline:"\u2312",profsurf:"\u2313",prop:"\u221D",Proportional:"\u221D",Proportion:"\u2237",propto:"\u221D",prsim:"\u227E",prurel:"\u22B0",Pscr:"\u{1D4AB}",pscr:"\u{1D4C5}",Psi:"\u03A8",psi:"\u03C8",puncsp:"\u2008",Qfr:"\u{1D514}",qfr:"\u{1D52E}",qint:"\u2A0C",qopf:"\u{1D562}",Qopf:"\u211A",qprime:"\u2057",Qscr:"\u{1D4AC}",qscr:"\u{1D4C6}",quaternions:"\u210D",quatint:"\u2A16",quest:"?",questeq:"\u225F",quot:'"',QUOT:'"',rAarr:"\u21DB",race:"\u223D\u0331",Racute:"\u0154",racute:"\u0155",radic:"\u221A",raemptyv:"\u29B3",rang:"\u27E9",Rang:"\u27EB",rangd:"\u2992",range:"\u29A5",rangle:"\u27E9",raquo:"\xBB",rarrap:"\u2975",rarrb:"\u21E5",rarrbfs:"\u2920",rarrc:"\u2933",rarr:"\u2192",Rarr:"\u21A0",rArr:"\u21D2",rarrfs:"\u291E",rarrhk:"\u21AA",rarrlp:"\u21AC",rarrpl:"\u2945",rarrsim:"\u2974",Rarrtl:"\u2916",rarrtl:"\u21A3",rarrw:"\u219D",ratail:"\u291A",rAtail:"\u291C",ratio:"\u2236",rationals:"\u211A",rbarr:"\u290D",rBarr:"\u290F",RBarr:"\u2910",rbbrk:"\u2773",rbrace:"}",rbrack:"]",rbrke:"\u298C",rbrksld:"\u298E",rbrkslu:"\u2990",Rcaron:"\u0158",rcaron:"\u0159",Rcedil:"\u0156",rcedil:"\u0157",rceil:"\u2309",rcub:"}",Rcy:"\u0420",rcy:"\u0440",rdca:"\u2937",rdldhar:"\u2969",rdquo:"\u201D",rdquor:"\u201D",rdsh:"\u21B3",real:"\u211C",realine:"\u211B",realpart:"\u211C",reals:"\u211D",Re:"\u211C",rect:"\u25AD",reg:"\xAE",REG:"\xAE",ReverseElement:"\u220B",ReverseEquilibrium:"\u21CB",ReverseUpEquilibrium:"\u296F",rfisht:"\u297D",rfloor:"\u230B",rfr:"\u{1D52F}",Rfr:"\u211C",rHar:"\u2964",rhard:"\u21C1",rharu:"\u21C0",rharul:"\u296C",Rho:"\u03A1",rho:"\u03C1",rhov:"\u03F1",RightAngleBracket:"\u27E9",RightArrowBar:"\u21E5",rightarrow:"\u2192",RightArrow:"\u2192",Rightarrow:"\u21D2",RightArrowLeftArrow:"\u21C4",rightarrowtail:"\u21A3",RightCeiling:"\u2309",RightDoubleBracket:"\u27E7",RightDownTeeVector:"\u295D",RightDownVectorBar:"\u2955",RightDownVector:"\u21C2",RightFloor:"\u230B",rightharpoondown:"\u21C1",rightharpoonup:"\u21C0",rightleftarrows:"\u21C4",rightleftharpoons:"\u21CC",rightrightarrows:"\u21C9",rightsquigarrow:"\u219D",RightTeeArrow:"\u21A6",RightTee:"\u22A2",RightTeeVector:"\u295B",rightthreetimes:"\u22CC",RightTriangleBar:"\u29D0",RightTriangle:"\u22B3",RightTriangleEqual:"\u22B5",RightUpDownVector:"\u294F",RightUpTeeVector:"\u295C",RightUpVectorBar:"\u2954",RightUpVector:"\u21BE",RightVectorBar:"\u2953",RightVector:"\u21C0",ring:"\u02DA",risingdotseq:"\u2253",rlarr:"\u21C4",rlhar:"\u21CC",rlm:"\u200F",rmoustache:"\u23B1",rmoust:"\u23B1",rnmid:"\u2AEE",roang:"\u27ED",roarr:"\u21FE",robrk:"\u27E7",ropar:"\u2986",ropf:"\u{1D563}",Ropf:"\u211D",roplus:"\u2A2E",rotimes:"\u2A35",RoundImplies:"\u2970",rpar:")",rpargt:"\u2994",rppolint:"\u2A12",rrarr:"\u21C9",Rrightarrow:"\u21DB",rsaquo:"\u203A",rscr:"\u{1D4C7}",Rscr:"\u211B",rsh:"\u21B1",Rsh:"\u21B1",rsqb:"]",rsquo:"\u2019",rsquor:"\u2019",rthree:"\u22CC",rtimes:"\u22CA",rtri:"\u25B9",rtrie:"\u22B5",rtrif:"\u25B8",rtriltri:"\u29CE",RuleDelayed:"\u29F4",ruluhar:"\u2968",rx:"\u211E",Sacute:"\u015A",sacute:"\u015B",sbquo:"\u201A",scap:"\u2AB8",Scaron:"\u0160",scaron:"\u0161",Sc:"\u2ABC",sc:"\u227B",sccue:"\u227D",sce:"\u2AB0",scE:"\u2AB4",Scedil:"\u015E",scedil:"\u015F",Scirc:"\u015C",scirc:"\u015D",scnap:"\u2ABA",scnE:"\u2AB6",scnsim:"\u22E9",scpolint:"\u2A13",scsim:"\u227F",Scy:"\u0421",scy:"\u0441",sdotb:"\u22A1",sdot:"\u22C5",sdote:"\u2A66",searhk:"\u2925",searr:"\u2198",seArr:"\u21D8",searrow:"\u2198",sect:"\xA7",semi:";",seswar:"\u2929",setminus:"\u2216",setmn:"\u2216",sext:"\u2736",Sfr:"\u{1D516}",sfr:"\u{1D530}",sfrown:"\u2322",sharp:"\u266F",SHCHcy:"\u0429",shchcy:"\u0449",SHcy:"\u0428",shcy:"\u0448",ShortDownArrow:"\u2193",ShortLeftArrow:"\u2190",shortmid:"\u2223",shortparallel:"\u2225",ShortRightArrow:"\u2192",ShortUpArrow:"\u2191",shy:"\xAD",Sigma:"\u03A3",sigma:"\u03C3",sigmaf:"\u03C2",sigmav:"\u03C2",sim:"\u223C",simdot:"\u2A6A",sime:"\u2243",simeq:"\u2243",simg:"\u2A9E",simgE:"\u2AA0",siml:"\u2A9D",simlE:"\u2A9F",simne:"\u2246",simplus:"\u2A24",simrarr:"\u2972",slarr:"\u2190",SmallCircle:"\u2218",smallsetminus:"\u2216",smashp:"\u2A33",smeparsl:"\u29E4",smid:"\u2223",smile:"\u2323",smt:"\u2AAA",smte:"\u2AAC",smtes:"\u2AAC\uFE00",SOFTcy:"\u042C",softcy:"\u044C",solbar:"\u233F",solb:"\u29C4",sol:"/",Sopf:"\u{1D54A}",sopf:"\u{1D564}",spades:"\u2660",spadesuit:"\u2660",spar:"\u2225",sqcap:"\u2293",sqcaps:"\u2293\uFE00",sqcup:"\u2294",sqcups:"\u2294\uFE00",Sqrt:"\u221A",sqsub:"\u228F",sqsube:"\u2291",sqsubset:"\u228F",sqsubseteq:"\u2291",sqsup:"\u2290",sqsupe:"\u2292",sqsupset:"\u2290",sqsupseteq:"\u2292",square:"\u25A1",Square:"\u25A1",SquareIntersection:"\u2293",SquareSubset:"\u228F",SquareSubsetEqual:"\u2291",SquareSuperset:"\u2290",SquareSupersetEqual:"\u2292",SquareUnion:"\u2294",squarf:"\u25AA",squ:"\u25A1",squf:"\u25AA",srarr:"\u2192",Sscr:"\u{1D4AE}",sscr:"\u{1D4C8}",ssetmn:"\u2216",ssmile:"\u2323",sstarf:"\u22C6",Star:"\u22C6",star:"\u2606",starf:"\u2605",straightepsilon:"\u03F5",straightphi:"\u03D5",strns:"\xAF",sub:"\u2282",Sub:"\u22D0",subdot:"\u2ABD",subE:"\u2AC5",sube:"\u2286",subedot:"\u2AC3",submult:"\u2AC1",subnE:"\u2ACB",subne:"\u228A",subplus:"\u2ABF",subrarr:"\u2979",subset:"\u2282",Subset:"\u22D0",subseteq:"\u2286",subseteqq:"\u2AC5",SubsetEqual:"\u2286",subsetneq:"\u228A",subsetneqq:"\u2ACB",subsim:"\u2AC7",subsub:"\u2AD5",subsup:"\u2AD3",succapprox:"\u2AB8",succ:"\u227B",succcurlyeq:"\u227D",Succeeds:"\u227B",SucceedsEqual:"\u2AB0",SucceedsSlantEqual:"\u227D",SucceedsTilde:"\u227F",succeq:"\u2AB0",succnapprox:"\u2ABA",succneqq:"\u2AB6",succnsim:"\u22E9",succsim:"\u227F",SuchThat:"\u220B",sum:"\u2211",Sum:"\u2211",sung:"\u266A",sup1:"\xB9",sup2:"\xB2",sup3:"\xB3",sup:"\u2283",Sup:"\u22D1",supdot:"\u2ABE",supdsub:"\u2AD8",supE:"\u2AC6",supe:"\u2287",supedot:"\u2AC4",Superset:"\u2283",SupersetEqual:"\u2287",suphsol:"\u27C9",suphsub:"\u2AD7",suplarr:"\u297B",supmult:"\u2AC2",supnE:"\u2ACC",supne:"\u228B",supplus:"\u2AC0",supset:"\u2283",Supset:"\u22D1",supseteq:"\u2287",supseteqq:"\u2AC6",supsetneq:"\u228B",supsetneqq:"\u2ACC",supsim:"\u2AC8",supsub:"\u2AD4",supsup:"\u2AD6",swarhk:"\u2926",swarr:"\u2199",swArr:"\u21D9",swarrow:"\u2199",swnwar:"\u292A",szlig:"\xDF",Tab:"	",target:"\u2316",Tau:"\u03A4",tau:"\u03C4",tbrk:"\u23B4",Tcaron:"\u0164",tcaron:"\u0165",Tcedil:"\u0162",tcedil:"\u0163",Tcy:"\u0422",tcy:"\u0442",tdot:"\u20DB",telrec:"\u2315",Tfr:"\u{1D517}",tfr:"\u{1D531}",there4:"\u2234",therefore:"\u2234",Therefore:"\u2234",Theta:"\u0398",theta:"\u03B8",thetasym:"\u03D1",thetav:"\u03D1",thickapprox:"\u2248",thicksim:"\u223C",ThickSpace:"\u205F\u200A",ThinSpace:"\u2009",thinsp:"\u2009",thkap:"\u2248",thksim:"\u223C",THORN:"\xDE",thorn:"\xFE",tilde:"\u02DC",Tilde:"\u223C",TildeEqual:"\u2243",TildeFullEqual:"\u2245",TildeTilde:"\u2248",timesbar:"\u2A31",timesb:"\u22A0",times:"\xD7",timesd:"\u2A30",tint:"\u222D",toea:"\u2928",topbot:"\u2336",topcir:"\u2AF1",top:"\u22A4",Topf:"\u{1D54B}",topf:"\u{1D565}",topfork:"\u2ADA",tosa:"\u2929",tprime:"\u2034",trade:"\u2122",TRADE:"\u2122",triangle:"\u25B5",triangledown:"\u25BF",triangleleft:"\u25C3",trianglelefteq:"\u22B4",triangleq:"\u225C",triangleright:"\u25B9",trianglerighteq:"\u22B5",tridot:"\u25EC",trie:"\u225C",triminus:"\u2A3A",TripleDot:"\u20DB",triplus:"\u2A39",trisb:"\u29CD",tritime:"\u2A3B",trpezium:"\u23E2",Tscr:"\u{1D4AF}",tscr:"\u{1D4C9}",TScy:"\u0426",tscy:"\u0446",TSHcy:"\u040B",tshcy:"\u045B",Tstrok:"\u0166",tstrok:"\u0167",twixt:"\u226C",twoheadleftarrow:"\u219E",twoheadrightarrow:"\u21A0",Uacute:"\xDA",uacute:"\xFA",uarr:"\u2191",Uarr:"\u219F",uArr:"\u21D1",Uarrocir:"\u2949",Ubrcy:"\u040E",ubrcy:"\u045E",Ubreve:"\u016C",ubreve:"\u016D",Ucirc:"\xDB",ucirc:"\xFB",Ucy:"\u0423",ucy:"\u0443",udarr:"\u21C5",Udblac:"\u0170",udblac:"\u0171",udhar:"\u296E",ufisht:"\u297E",Ufr:"\u{1D518}",ufr:"\u{1D532}",Ugrave:"\xD9",ugrave:"\xF9",uHar:"\u2963",uharl:"\u21BF",uharr:"\u21BE",uhblk:"\u2580",ulcorn:"\u231C",ulcorner:"\u231C",ulcrop:"\u230F",ultri:"\u25F8",Umacr:"\u016A",umacr:"\u016B",uml:"\xA8",UnderBar:"_",UnderBrace:"\u23DF",UnderBracket:"\u23B5",UnderParenthesis:"\u23DD",Union:"\u22C3",UnionPlus:"\u228E",Uogon:"\u0172",uogon:"\u0173",Uopf:"\u{1D54C}",uopf:"\u{1D566}",UpArrowBar:"\u2912",uparrow:"\u2191",UpArrow:"\u2191",Uparrow:"\u21D1",UpArrowDownArrow:"\u21C5",updownarrow:"\u2195",UpDownArrow:"\u2195",Updownarrow:"\u21D5",UpEquilibrium:"\u296E",upharpoonleft:"\u21BF",upharpoonright:"\u21BE",uplus:"\u228E",UpperLeftArrow:"\u2196",UpperRightArrow:"\u2197",upsi:"\u03C5",Upsi:"\u03D2",upsih:"\u03D2",Upsilon:"\u03A5",upsilon:"\u03C5",UpTeeArrow:"\u21A5",UpTee:"\u22A5",upuparrows:"\u21C8",urcorn:"\u231D",urcorner:"\u231D",urcrop:"\u230E",Uring:"\u016E",uring:"\u016F",urtri:"\u25F9",Uscr:"\u{1D4B0}",uscr:"\u{1D4CA}",utdot:"\u22F0",Utilde:"\u0168",utilde:"\u0169",utri:"\u25B5",utrif:"\u25B4",uuarr:"\u21C8",Uuml:"\xDC",uuml:"\xFC",uwangle:"\u29A7",vangrt:"\u299C",varepsilon:"\u03F5",varkappa:"\u03F0",varnothing:"\u2205",varphi:"\u03D5",varpi:"\u03D6",varpropto:"\u221D",varr:"\u2195",vArr:"\u21D5",varrho:"\u03F1",varsigma:"\u03C2",varsubsetneq:"\u228A\uFE00",varsubsetneqq:"\u2ACB\uFE00",varsupsetneq:"\u228B\uFE00",varsupsetneqq:"\u2ACC\uFE00",vartheta:"\u03D1",vartriangleleft:"\u22B2",vartriangleright:"\u22B3",vBar:"\u2AE8",Vbar:"\u2AEB",vBarv:"\u2AE9",Vcy:"\u0412",vcy:"\u0432",vdash:"\u22A2",vDash:"\u22A8",Vdash:"\u22A9",VDash:"\u22AB",Vdashl:"\u2AE6",veebar:"\u22BB",vee:"\u2228",Vee:"\u22C1",veeeq:"\u225A",vellip:"\u22EE",verbar:"|",Verbar:"\u2016",vert:"|",Vert:"\u2016",VerticalBar:"\u2223",VerticalLine:"|",VerticalSeparator:"\u2758",VerticalTilde:"\u2240",VeryThinSpace:"\u200A",Vfr:"\u{1D519}",vfr:"\u{1D533}",vltri:"\u22B2",vnsub:"\u2282\u20D2",vnsup:"\u2283\u20D2",Vopf:"\u{1D54D}",vopf:"\u{1D567}",vprop:"\u221D",vrtri:"\u22B3",Vscr:"\u{1D4B1}",vscr:"\u{1D4CB}",vsubnE:"\u2ACB\uFE00",vsubne:"\u228A\uFE00",vsupnE:"\u2ACC\uFE00",vsupne:"\u228B\uFE00",Vvdash:"\u22AA",vzigzag:"\u299A",Wcirc:"\u0174",wcirc:"\u0175",wedbar:"\u2A5F",wedge:"\u2227",Wedge:"\u22C0",wedgeq:"\u2259",weierp:"\u2118",Wfr:"\u{1D51A}",wfr:"\u{1D534}",Wopf:"\u{1D54E}",wopf:"\u{1D568}",wp:"\u2118",wr:"\u2240",wreath:"\u2240",Wscr:"\u{1D4B2}",wscr:"\u{1D4CC}",xcap:"\u22C2",xcirc:"\u25EF",xcup:"\u22C3",xdtri:"\u25BD",Xfr:"\u{1D51B}",xfr:"\u{1D535}",xharr:"\u27F7",xhArr:"\u27FA",Xi:"\u039E",xi:"\u03BE",xlarr:"\u27F5",xlArr:"\u27F8",xmap:"\u27FC",xnis:"\u22FB",xodot:"\u2A00",Xopf:"\u{1D54F}",xopf:"\u{1D569}",xoplus:"\u2A01",xotime:"\u2A02",xrarr:"\u27F6",xrArr:"\u27F9",Xscr:"\u{1D4B3}",xscr:"\u{1D4CD}",xsqcup:"\u2A06",xuplus:"\u2A04",xutri:"\u25B3",xvee:"\u22C1",xwedge:"\u22C0",Yacute:"\xDD",yacute:"\xFD",YAcy:"\u042F",yacy:"\u044F",Ycirc:"\u0176",ycirc:"\u0177",Ycy:"\u042B",ycy:"\u044B",yen:"\xA5",Yfr:"\u{1D51C}",yfr:"\u{1D536}",YIcy:"\u0407",yicy:"\u0457",Yopf:"\u{1D550}",yopf:"\u{1D56A}",Yscr:"\u{1D4B4}",yscr:"\u{1D4CE}",YUcy:"\u042E",yucy:"\u044E",yuml:"\xFF",Yuml:"\u0178",Zacute:"\u0179",zacute:"\u017A",Zcaron:"\u017D",zcaron:"\u017E",Zcy:"\u0417",zcy:"\u0437",Zdot:"\u017B",zdot:"\u017C",zeetrf:"\u2128",ZeroWidthSpace:"\u200B",Zeta:"\u0396",zeta:"\u03B6",zfr:"\u{1D537}",Zfr:"\u2128",ZHcy:"\u0416",zhcy:"\u0436",zigrarr:"\u21DD",zopf:"\u{1D56B}",Zopf:"\u2124",Zscr:"\u{1D4B5}",zscr:"\u{1D4CF}",zwj:"\u200D",zwnj:"\u200C"}},function(c,_,i){function e(r){var t,a,l=n[r];if(l)return l;for(l=n[r]=[],t=0;t<128;t++)a=String.fromCharCode(t),l.push(a);for(t=0;t<r.length;t++)a=r.charCodeAt(t),l[a]="%"+("0"+a.toString(16).toUpperCase()).slice(-2);return l}function o(r,t){var a;return typeof t!="string"&&(t=o.defaultChars),a=e(t),r.replace(/(%[a-f0-9]{2})+/gi,function(l){var p,s,u,d,v,f,y,m="";for(p=0,s=l.length;p<s;p+=3)u=parseInt(l.slice(p+1,p+3),16),u<128?m+=a[u]:(224&u)==192&&p+3<s&&(192&(d=parseInt(l.slice(p+4,p+6),16)))==128?(y=u<<6&1984|63&d,m+=y<128?"\uFFFD\uFFFD":String.fromCharCode(y),p+=3):(240&u)==224&&p+6<s&&(d=parseInt(l.slice(p+4,p+6),16),v=parseInt(l.slice(p+7,p+9),16),(192&d)==128&&(192&v)==128)?(y=u<<12&61440|d<<6&4032|63&v,m+=y<2048||y>=55296&&y<=57343?"\uFFFD\uFFFD\uFFFD":String.fromCharCode(y),p+=6):(248&u)==240&&p+9<s&&(d=parseInt(l.slice(p+4,p+6),16),v=parseInt(l.slice(p+7,p+9),16),f=parseInt(l.slice(p+10,p+12),16),(192&d)==128&&(192&v)==128&&(192&f)==128)?(y=u<<18&1835008|d<<12&258048|v<<6&4032|63&f,y<65536||y>1114111?m+="\uFFFD\uFFFD\uFFFD\uFFFD":(y-=65536,m+=String.fromCharCode(55296+(y>>10),56320+(1023&y))),p+=9):m+="\uFFFD";return m})}var n={};o.defaultChars=";/?:@&=+$,#",o.componentChars="",c.exports=o},function(c,_,i){function e(r){var t,a,l=n[r];if(l)return l;for(l=n[r]=[],t=0;t<128;t++)a=String.fromCharCode(t),/^[0-9a-z]$/i.test(a)?l.push(a):l.push("%"+("0"+t.toString(16).toUpperCase()).slice(-2));for(t=0;t<r.length;t++)l[r.charCodeAt(t)]=r[t];return l}function o(r,t,a){var l,p,s,u,d,v="";for(typeof t!="string"&&(a=t,t=o.defaultChars),a===void 0&&(a=!0),d=e(t),l=0,p=r.length;l<p;l++)if(s=r.charCodeAt(l),a&&s===37&&l+2<p&&/^[0-9a-f]{2}$/i.test(r.slice(l+1,l+3)))v+=r.slice(l,l+3),l+=2;else if(s<128)v+=d[s];else if(s>=55296&&s<=57343){if(s>=55296&&s<=56319&&l+1<p&&(u=r.charCodeAt(l+1))>=56320&&u<=57343){v+=encodeURIComponent(r[l]+r[l+1]),l++;continue}v+="%EF%BF%BD"}else v+=encodeURIComponent(r[l]);return v}var n={};o.defaultChars=";/?:@&=+$,-_.!~*'()#",o.componentChars="-_.!~*'()",c.exports=o},function(c,_,i){c.exports=function(e){var o="";return o+=e.protocol||"",o+=e.slashes?"//":"",o+=e.auth?e.auth+"@":"",e.hostname&&e.hostname.indexOf(":")!==-1?o+="["+e.hostname+"]":o+=e.hostname||"",o+=e.port?":"+e.port:"",o+=e.pathname||"",o+=e.search||"",o+=e.hash||""}},function(c,_,i){function e(){this.protocol=null,this.slashes=null,this.auth=null,this.port=null,this.hostname=null,this.hash=null,this.search=null,this.pathname=null}function o(m,h){if(m&&m instanceof e)return m;var w=new e;return w.parse(m,h),w}var n=/^([a-z0-9.+-]+:)/i,r=/:[0-9]*$/,t=/^(\/\/?(?!\/)[^\?\s]*)(\?[^\s]*)?$/,a=["<",">",'"',"`"," ","\r",`
`,"	"],l=["{","}","|","\\","^","`"].concat(a),p=["'"].concat(l),s=["%","/","?",";","#"].concat(p),u=["/","?","#"],d=/^[+a-z0-9A-Z_-]{0,63}$/,v=/^([+a-z0-9A-Z_-]{0,63})(.*)$/,f={javascript:!0,"javascript:":!0},y={http:!0,https:!0,ftp:!0,gopher:!0,file:!0,"http:":!0,"https:":!0,"ftp:":!0,"gopher:":!0,"file:":!0};e.prototype.parse=function(m,h){var w,g,k,b,C,x=m;if(x=x.trim(),!h&&m.split("#").length===1){var E=t.exec(x);if(E)return this.pathname=E[1],E[2]&&(this.search=E[2]),this}var D=n.exec(x);if(D&&(D=D[0],k=D.toLowerCase(),this.protocol=D,x=x.substr(D.length)),(h||D||x.match(/^\/\/[^@\/]+@[^@\/]+/))&&(!(C=x.substr(0,2)==="//")||D&&f[D]||(x=x.substr(2),this.slashes=!0)),!f[D]&&(C||D&&!y[D])){var A=-1;for(w=0;w<u.length;w++)(b=x.indexOf(u[w]))!==-1&&(A===-1||b<A)&&(A=b);var S,T;for(T=A===-1?x.lastIndexOf("@"):x.lastIndexOf("@",A),T!==-1&&(S=x.slice(0,T),x=x.slice(T+1),this.auth=S),A=-1,w=0;w<s.length;w++)(b=x.indexOf(s[w]))!==-1&&(A===-1||b<A)&&(A=b);A===-1&&(A=x.length),x[A-1]===":"&&A--;var I=x.slice(0,A);x=x.slice(A),this.parseHost(I),this.hostname=this.hostname||"";var $=this.hostname[0]==="["&&this.hostname[this.hostname.length-1]==="]";if(!$){var q=this.hostname.split(/\./);for(w=0,g=q.length;w<g;w++){var M=q[w];if(M&&!M.match(d)){for(var z="",U=0,L=M.length;U<L;U++)M.charCodeAt(U)>127?z+="x":z+=M[U];if(!z.match(d)){var P=q.slice(0,w),j=q.slice(w+1),O=M.match(v);O&&(P.push(O[1]),j.unshift(O[2])),j.length&&(x=j.join(".")+x),this.hostname=P.join(".");break}}}}this.hostname.length>255&&(this.hostname=""),$&&(this.hostname=this.hostname.substr(1,this.hostname.length-2))}var B=x.indexOf("#");B!==-1&&(this.hash=x.substr(B),x=x.slice(0,B));var H=x.indexOf("?");return H!==-1&&(this.search=x.substr(H),x=x.slice(0,H)),x&&(this.pathname=x),y[k]&&this.hostname&&!this.pathname&&(this.pathname=""),this},e.prototype.parseHost=function(m){var h=r.exec(m);h&&(h=h[0],h!==":"&&(this.port=h.substr(1)),m=m.substr(0,m.length-h.length)),m&&(this.hostname=m)},c.exports=o},function(c,_,i){(function(e,o){var n;(function(r){function t(L){throw new RangeError(q[L])}function a(L,P){for(var j=L.length,O=[];j--;)O[j]=P(L[j]);return O}function l(L,P){var j=L.split("@"),O="";return j.length>1&&(O=j[0]+"@",L=j[1]),L=L.replace($,"."),O+a(L.split("."),P).join(".")}function p(L){for(var P,j,O=[],B=0,H=L.length;B<H;)P=L.charCodeAt(B++),P>=55296&&P<=56319&&B<H?(j=L.charCodeAt(B++),(64512&j)==56320?O.push(((1023&P)<<10)+(1023&j)+65536):(O.push(P),B--)):O.push(P);return O}function s(L){return a(L,function(P){var j="";return P>65535&&(P-=65536,j+=U(P>>>10&1023|55296),P=56320|1023&P),j+=U(P)}).join("")}function u(L){return L-48<10?L-22:L-65<26?L-65:L-97<26?L-97:k}function d(L,P){return L+22+75*(L<26)-((P!=0)<<5)}function v(L,P,j){var O=0;for(L=j?z(L/E):L>>1,L+=z(L/P);L>M*C>>1;O+=k)L=z(L/M);return z(O+(M+1)*L/(L+x))}function f(L){var P,j,O,B,H,N,K,W,G,Y,J=[],Q=L.length,Z=0,te=A,ne=D;for(j=L.lastIndexOf(S),j<0&&(j=0),O=0;O<j;++O)L.charCodeAt(O)>=128&&t("not-basic"),J.push(L.charCodeAt(O));for(B=j>0?j+1:0;B<Q;){for(H=Z,N=1,K=k;B>=Q&&t("invalid-input"),W=u(L.charCodeAt(B++)),(W>=k||W>z((g-Z)/N))&&t("overflow"),Z+=W*N,G=K<=ne?b:K>=ne+C?C:K-ne,!(W<G);K+=k)Y=k-G,N>z(g/Y)&&t("overflow"),N*=Y;P=J.length+1,ne=v(Z-H,P,H==0),z(Z/P)>g-te&&t("overflow"),te+=z(Z/P),Z%=P,J.splice(Z++,0,te)}return s(J)}function y(L){var P,j,O,B,H,N,K,W,G,Y,J,Q,Z,te,ne,re=[];for(L=p(L),Q=L.length,P=A,j=0,H=D,N=0;N<Q;++N)(J=L[N])<128&&re.push(U(J));for(O=B=re.length,B&&re.push(S);O<Q;){for(K=g,N=0;N<Q;++N)(J=L[N])>=P&&J<K&&(K=J);for(Z=O+1,K-P>z((g-j)/Z)&&t("overflow"),j+=(K-P)*Z,P=K,N=0;N<Q;++N)if(J=L[N],J<P&&++j>g&&t("overflow"),J==P){for(W=j,G=k;Y=G<=H?b:G>=H+C?C:G-H,!(W<Y);G+=k)ne=W-Y,te=k-Y,re.push(U(d(Y+ne%te,0))),W=z(ne/te);re.push(U(d(W,0))),H=v(j,Z,O==B),j=0,++O}++j,++P}return re.join("")}function m(L){return l(L,function(P){return T.test(P)?f(P.slice(4).toLowerCase()):P})}function h(L){return l(L,function(P){return I.test(P)?"xn--"+y(P):P})}typeof _=="object"&&_&&_.nodeType,typeof e=="object"&&e&&e.nodeType;var w,g=2147483647,k=36,b=1,C=26,x=38,E=700,D=72,A=128,S="-",T=/^xn--/,I=/[^\x20-\x7E]/,$=/[\x2E\u3002\uFF0E\uFF61]/g,q={overflow:"Overflow: input needs wider integers to process","not-basic":"Illegal input >= 0x80 (not a basic code point)","invalid-input":"Invalid input"},M=k-b,z=Math.floor,U=String.fromCharCode;w={version:"1.4.1",ucs2:{decode:p,encode:s},decode:f,encode:y,toASCII:h,toUnicode:m},(n=function(){return w}.call(_,i,_,e))!==void 0&&(e.exports=n)})()}).call(_,i(208)(c),i(207))},function(c,_){c.exports=`@[toc](Catalog)

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
`},function(c,_){c.exports=`@[toc](Catalog)

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
`},function(c,_){c.exports=`@[toc](Catalogue)

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
`},function(c,_){c.exports=`@[toc](\u76EE\u6B21)

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
`},function(c,_){c.exports=`@[toc](Directory)

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
`},function(c,_){c.exports=`@[toc](Catalog)  
  
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
`},function(c,_){c.exports=`@[toc](\u76EE\u5F55)

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

`},function(c,_){c.exports=`@[toc](\u76EE\u9304)

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

`},function(c,_){c.exports=/[\xAD\u0600-\u0605\u061C\u06DD\u070F\u08E2\u180E\u200B-\u200F\u202A-\u202E\u2060-\u2064\u2066-\u206F\uFEFF\uFFF9-\uFFFB]|\uD804[\uDCBD\uDCCD]|\uD82F[\uDCA0-\uDCA3]|\uD834[\uDD73-\uDD7A]|\uDB40[\uDC01\uDC20-\uDC7F]/},function(c,_,i){_.Any=i(60),_.Cc=i(58),_.Cf=i(195),_.P=i(34),_.Z=i(59)},function(c,_,i){function e(n){i(202)}var o=i(16)(i(65),i(199),e,null,null);o.options.__file="D:\\work\\songwang\\yuangongji\\mavonEditor\\node_modules\\auto-textarea\\auto-textarea.vue",o.esModule&&Object.keys(o.esModule).some(function(n){return n!=="default"&&n.substr(0,2)!=="__"})&&console.error("named exports are not supported in *.vue files."),o.options.functional&&console.error("[vue-loader] auto-textarea.vue: functional components are not supported with templates, they should use render functions."),c.exports=o.exports},function(c,_,i){c.exports={render:function(){var e=this,o=e.$createElement,n=e._self._c||o;return n("div",{staticClass:"v-right-item"},[e._t("right-toolbar-before"),e._v(" "),e.toolbars.navigation?n("button",{directives:[{name:"show",rawName:"v-show",value:!e.s_navigation,expression:"!s_navigation"}],staticClass:"op-icon fa fa-mavon-bars",attrs:{type:"button","aria-hidden":"true",title:e.d_words.tl_navigation_on+" (F8)"},on:{click:function(r){return e.$clicks("navigation")}}}):e._e(),e._v(" "),e.toolbars.navigation?n("button",{directives:[{name:"show",rawName:"v-show",value:e.s_navigation,expression:"s_navigation"}],staticClass:"op-icon fa fa-mavon-bars selected",attrs:{type:"button","aria-hidden":"true",title:e.d_words.tl_navigation_off+" (F8)"},on:{click:function(r){return e.$clicks("navigation")}}}):e._e(),e._v(" "),e.toolbars.preview?n("button",{directives:[{name:"show",rawName:"v-show",value:e.s_preview_switch,expression:"s_preview_switch"}],staticClass:"op-icon fa fa-mavon-eye-slash selected",attrs:{type:"button","aria-hidden":"true",title:e.d_words.tl_edit+" (F9)"},on:{click:function(r){return e.$clicks("preview")}}}):e._e(),e._v(" "),e.toolbars.preview?n("button",{directives:[{name:"show",rawName:"v-show",value:!e.s_preview_switch,expression:"!s_preview_switch"}],staticClass:"op-icon fa fa-mavon-eye",attrs:{type:"button","aria-hidden":"true",title:e.d_words.tl_preview+" (F9)"},on:{click:function(r){return e.$clicks("preview")}}}):e._e(),e._v(" "),e.toolbars.fullscreen?n("button",{directives:[{name:"show",rawName:"v-show",value:!e.s_fullScreen,expression:"!s_fullScreen"}],staticClass:"op-icon fa fa-mavon-arrows-alt",attrs:{type:"button",title:e.d_words.tl_fullscreen_on+" (F10)","aria-hidden":"true"},on:{click:function(r){return e.$clicks("fullscreen")}}}):e._e(),e._v(" "),e.toolbars.fullscreen?n("button",{directives:[{name:"show",rawName:"v-show",value:e.s_fullScreen,expression:"s_fullScreen"}],staticClass:"op-icon fa fa-mavon-compress selected",attrs:{type:"button",title:e.d_words.tl_fullscreen_off+" (F10)","aria-hidden":"true"},on:{click:function(r){return e.$clicks("fullscreen")}}}):e._e(),e._v(" "),e.toolbars.readmodel?n("button",{staticClass:"op-icon fa fa-mavon-window-maximize",attrs:{type:"button","aria-hidden":"true",title:e.d_words.tl_read+" (F11)"},on:{click:function(r){return e.$clicks("read")}}}):e._e(),e._v(" "),e.toolbars.subfield?n("button",{staticClass:"op-icon fa fa-mavon-columns",class:{selected:e.s_subfield},attrs:{type:"button","aria-hidden":"true",title:(e.s_subfield?e.d_words.tl_single_column:e.d_words.tl_double_column)+" (F12)"},on:{click:function(r){return e.$clicks("subfield")}}}):e._e(),e._v(" "),e.toolbars.help&&e.toolbars.htmlcode&&e.toolbars.readmodel&&e.toolbars.fullscreen&&e.toolbars.subfield&&e.toolbars.navigation?n("span",{staticClass:"op-icon-divider"}):e._e(),e._v(" "),e.toolbars.htmlcode?n("button",{directives:[{name:"show",rawName:"v-show",value:!e.s_html_code,expression:"!s_html_code"}],staticClass:"op-icon fa fa-mavon-code",attrs:{type:"button",title:e.d_words.tl_html_on,"aria-hidden":"true"},on:{click:function(r){return e.$clicks("html")}}}):e._e(),e._v(" "),e.toolbars.htmlcode?n("button",{directives:[{name:"show",rawName:"v-show",value:e.s_html_code,expression:"s_html_code"}],staticClass:"op-icon fa fa-mavon-code selected",attrs:{type:"button",title:e.d_words.tl_html_off,"aria-hidden":"true"},on:{click:function(r){return e.$clicks("html")}}}):e._e(),e._v(" "),e.toolbars.help?n("button",{staticClass:"op-icon fa fa-mavon-question-circle",staticStyle:{"font-size":"17px",padding:"5px 6px 5px 3px"},attrs:{type:"button",title:e.d_words.tl_help,"aria-hidden":"true"},on:{click:function(r){return e.$clicks("help")}}}):e._e(),e._v(" "),e._t("right-toolbar-after")],2)},staticRenderFns:[]},c.exports.render._withStripped=!0},function(c,_,i){c.exports={render:function(){var e=this,o=e.$createElement,n=e._self._c||o;return n("div",{staticClass:"auto-textarea-wrapper",style:{fontSize:e.fontSize,lineHeight:e.lineHeight,height:e.fullHeight?"100%":"auto"}},[n("pre",{staticClass:"auto-textarea-block",style:{fontSize:e.fontSize,lineHeight:e.lineHeight,minHeight:e.fullHeight?"100%":"auto"}},[n("br"),e._v(e._s(e.temp_value)+" ")]),e._v(" "),n("textarea",{directives:[{name:"model",rawName:"v-model",value:e.temp_value,expression:"temp_value"}],ref:"vTextarea",staticClass:"auto-textarea-input",class:{"no-border":!e.border,"no-resize":!e.resize},style:{fontSize:e.fontSize,lineHeight:e.lineHeight},attrs:{autofocus:e.s_autofocus,spellcheck:"false",placeholder:e.placeholder},domProps:{value:e.temp_value},on:{keyup:e.change,input:function(r){r.target.composing||(e.temp_value=r.target.value)}}})])},staticRenderFns:[]},c.exports.render._withStripped=!0},function(c,_,i){c.exports={render:function(){var e=this,o=e.$createElement,n=e._self._c||o;return n("div",{staticClass:"v-left-item"},[e._t("left-toolbar-before"),e._v(" "),e.toolbars.bold?n("button",{staticClass:"op-icon fa fa-mavon-bold",attrs:{disabled:!e.editable,type:"button","aria-hidden":"true",title:e.d_words.tl_bold+" (ctrl+b)"},on:{click:function(r){return e.$clicks("bold")}}}):e._e(),e._v(" "),e.toolbars.italic?n("button",{staticClass:"op-icon fa fa-mavon-italic",attrs:{disabled:!e.editable,type:"button","aria-hidden":"true",title:e.d_words.tl_italic+" (ctrl+i)"},on:{click:function(r){return e.$clicks("italic")}}}):e._e(),e._v(" "),e.toolbars.header?n("div",{staticClass:"op-icon fa fa-mavon-header dropdown dropdown-wrapper",class:{selected:e.s_header_dropdown_open},attrs:{disabled:!e.editable,type:"button","aria-hidden":"true",title:e.d_words.tl_header+" (ctrl+h)"},on:{mouseleave:e.$mouseleave_header_dropdown,mouseenter:e.$mouseenter_header_dropdown}},[n("transition",{attrs:{name:"fade"}},[n("div",{directives:[{name:"show",rawName:"v-show",value:e.s_header_dropdown_open,expression:"s_header_dropdown_open"}],staticClass:"op-header popup-dropdown",class:{transition:e.transition},on:{mouseenter:e.$mouseenter_header_dropdown,mouseleave:e.$mouseleave_header_dropdown}},[n("div",{staticClass:"dropdown-item",attrs:{title:"#"},on:{click:function(r){return r.stopPropagation(),e.$click_header("header1")}}},[n("span",[e._v(e._s(e.d_words.tl_header_one))])]),e._v(" "),n("div",{staticClass:"dropdown-item",attrs:{title:"## "},on:{click:function(r){return r.stopPropagation(),e.$click_header("header2")}}},[n("span",[e._v(e._s(e.d_words.tl_header_two))])]),e._v(" "),n("div",{staticClass:"dropdown-item",attrs:{title:"### "},on:{click:function(r){return r.stopPropagation(),e.$click_header("header3")}}},[n("span",[e._v(e._s(e.d_words.tl_header_three))])]),e._v(" "),n("div",{staticClass:"dropdown-item",attrs:{title:"#### "},on:{click:function(r){return r.stopPropagation(),e.$click_header("header4")}}},[n("span",[e._v(e._s(e.d_words.tl_header_four))])]),e._v(" "),n("div",{staticClass:"dropdown-item",attrs:{title:"##### "},on:{click:function(r){return r.stopPropagation(),e.$click_header("header5")}}},[n("span",[e._v(e._s(e.d_words.tl_header_five))])]),e._v(" "),n("div",{staticClass:"dropdown-item",attrs:{title:"###### "},on:{click:function(r){return r.stopPropagation(),e.$click_header("header6")}}},[n("span",[e._v(e._s(e.d_words.tl_header_six))])])])])],1):e._e(),e._v(" "),e.toolbars.header||e.toolbars.italic||e.toolbars.bold?n("span",{staticClass:"op-icon-divider"}):e._e(),e._v(" "),e.toolbars.underline?n("button",{staticClass:"op-icon fa fa-mavon-underline",attrs:{disabled:!e.editable,type:"button",title:e.d_words.tl_underline+" (ctrl+u)","aria-hidden":"true"},on:{click:function(r){return e.$clicks("underline")}}}):e._e(),e._v(" "),e.toolbars.strikethrough?n("button",{staticClass:"op-icon fa fa-mavon-strikethrough",attrs:{disabled:!e.editable,type:"button",title:e.d_words.tl_strikethrough+" (ctrl+shift+d)","aria-hidden":"true"},on:{click:function(r){return e.$clicks("strikethrough")}}}):e._e(),e._v(" "),e.toolbars.mark?n("button",{staticClass:"op-icon fa fa-mavon-thumb-tack",attrs:{disabled:!e.editable,type:"button",title:e.d_words.tl_mark+" (ctrl+m)","aria-hidden":"true"},on:{click:function(r){return e.$clicks("mark")}}}):e._e(),e._v(" "),e.toolbars.superscript?n("button",{staticClass:"op-icon fa fa-mavon-superscript",attrs:{disabled:!e.editable,type:"button","aria-hidden":"true",title:e.d_words.tl_superscript+" (ctrl+alt+s)"},on:{click:function(r){return e.$clicks("superscript")}}}):e._e(),e._v(" "),e.toolbars.subscript?n("button",{staticClass:"op-icon fa fa-mavon-subscript",attrs:{disabled:!e.editable,type:"button","aria-hidden":"true",title:e.d_words.tl_subscript+" (ctrl+shift+s)"},on:{click:function(r){return e.$clicks("subscript")}}}):e._e(),e._v(" "),e.toolbars.alignleft?n("button",{staticClass:"op-icon fa fa-mavon-align-left",attrs:{disabled:!e.editable,type:"button","aria-hidden":"true",title:e.d_words.tl_alignleft+" (ctrl+l)"},on:{click:function(r){return e.$clicks("alignleft")}}}):e._e(),e._v(" "),e.toolbars.aligncenter?n("button",{staticClass:"op-icon fa fa-mavon-align-center",attrs:{disabled:!e.editable,type:"button","aria-hidden":"true",title:e.d_words.tl_aligncenter+" (ctrl+e)"},on:{click:function(r){return e.$clicks("aligncenter")}}}):e._e(),e._v(" "),e.toolbars.alignright?n("button",{staticClass:"op-icon fa fa-mavon-align-right",attrs:{disabled:!e.editable,type:"button","aria-hidden":"true",title:e.d_words.tl_alignright+" (ctrl+r)"},on:{click:function(r){return e.$clicks("alignright")}}}):e._e(),e._v(" "),e.toolbars.superscript||e.toolbars.subscript||e.toolbars.underline||e.toolbars.strikethrough||e.toolbars.mark?n("span",{staticClass:"op-icon-divider"}):e._e(),e._v(" "),e.toolbars.quote?n("button",{staticClass:"op-icon fa fa-mavon-quote-left",attrs:{disabled:!e.editable,type:"button","aria-hidden":"true",title:e.d_words.tl_quote+" (ctrl+q)"},on:{click:function(r){return e.$clicks("quote")}}}):e._e(),e._v(" "),e.toolbars.ol?n("button",{staticClass:"op-icon fa fa-mavon-list-ol",attrs:{disabled:!e.editable,type:"button","aria-hidden":"true",title:e.d_words.tl_ol+" (ctrl+o)"},on:{click:function(r){return e.$clicks("ol")}}}):e._e(),e._v(" "),e.toolbars.ul?n("button",{staticClass:"op-icon fa fa-mavon-list-ul",attrs:{disabled:!e.editable,type:"button","aria-hidden":"true",title:e.d_words.tl_ul+" (ctrl+alt+u)"},on:{click:function(r){return e.$clicks("ul")}}}):e._e(),e._v(" "),e.toolbars.ul||e.toolbars.ol||e.toolbars.quote?n("span",{staticClass:"op-icon-divider"}):e._e(),e._v(" "),e.toolbars.link?n("button",{staticClass:"op-icon fa fa-mavon-link",attrs:{disabled:!e.editable,type:"button","aria-hidden":"true",title:e.d_words.tl_link+" (ctrl+l)"},on:{click:function(r){return r.stopPropagation(),e.$toggle_imgLinkAdd("link")}}}):e._e(),e._v(" "),e.toolbars.imagelink?n("div",{staticClass:"op-icon fa fa-mavon-picture-o dropdown dropdown-wrapper",class:{selected:e.s_img_dropdown_open},attrs:{disabled:!e.editable,type:"button","aria-hidden":"true"},on:{mouseleave:e.$mouseleave_img_dropdown,mouseenter:e.$mouseenter_img_dropdown}},[n("transition",{attrs:{name:"fade"}},[n("div",{directives:[{name:"show",rawName:"v-show",value:e.s_img_dropdown_open,expression:"s_img_dropdown_open"}],staticClass:"op-image popup-dropdown",class:{transition:e.transition},on:{mouseleave:e.$mouseleave_img_dropdown,mouseenter:e.$mouseenter_img_dropdown}},[n("div",{staticClass:"dropdown-item",on:{click:function(r){return r.stopPropagation(),e.$toggle_imgLinkAdd("imagelink")}}},[n("span",[e._v(e._s(e.d_words.tl_image))])]),e._v(" "),n("div",{staticClass:"dropdown-item",staticStyle:{overflow:"hidden"}},[n("input",{attrs:{type:"file",accept:"image/gif,image/jpeg,image/jpg,image/png,image/svg",multiple:"multiple"},on:{change:function(r){return e.$imgAdd(r)}}}),e._v(e._s(e.d_words.tl_upload)+`
                `)]),e._v(" "),e._l(e.img_file,function(r,t){return r&&r[1]?n("div",{key:t,staticClass:"dropdown-item dropdown-images",attrs:{title:r[1].name},on:{click:function(a){return a.stopPropagation(),e.$imgFileListClick(t)}}},[n("span",[e._v(e._s(r[1].name))]),e._v(" "),n("button",{staticClass:"op-icon fa fa-mavon-times",attrs:{slot:"right",type:"button","aria-hidden":"true",title:e.d_words.tl_upload_remove},on:{click:function(a){return a.stopPropagation(),e.$imgDel(t)}},slot:"right"}),e._v(" "),n("img",{staticClass:"image-show",class:{transition:e.transition},attrs:{src:r[1].miniurl,alt:"none"}})]):e._e()})],2)])],1):e._e(),e._v(" "),e.toolbars.code?n("button",{staticClass:"op-icon fa fa-mavon-code",attrs:{disabled:!e.editable,type:"button","aria-hidden":"true",title:e.d_words.tl_code+" (ctrl+alt+c)"},on:{click:function(r){return e.$clicks("code")}}}):e._e(),e._v(" "),e.toolbars.table?n("button",{staticClass:"op-icon fa fa-mavon-table",attrs:{disabled:!e.editable,type:"button","aria-hidden":"true",title:e.d_words.tl_table+" (ctrl+alt+t)"},on:{click:function(r){return e.$clicks("table")}}}):e._e(),e._v(" "),e.toolbars.link||e.toolbars.imagelink||e.toolbars.code||e.toolbars.table?n("span",{staticClass:"op-icon-divider"}):e._e(),e._v(" "),e.toolbars.undo?n("button",{staticClass:"op-icon fa fa-mavon-undo",attrs:{type:"button","aria-hidden":"true",title:e.d_words.tl_undo+" (ctrl+z)"},on:{click:function(r){return e.$clicks("undo")}}}):e._e(),e._v(" "),e.toolbars.redo?n("button",{staticClass:"op-icon fa fa-mavon-repeat",attrs:{type:"button","aria-hidden":"true",title:e.d_words.tl_redo+" (ctrl+y)"},on:{click:function(r){return e.$clicks("redo")}}}):e._e(),e._v(" "),e.toolbars.trash?n("button",{staticClass:"op-icon fa fa-mavon-trash-o",attrs:{type:"button","aria-hidden":"true",title:e.d_words.tl_trash+" (ctrl+breakspace)"},on:{click:function(r){return e.$clicks("trash")}}}):e._e(),e._v(" "),e.toolbars.save?n("button",{staticClass:"op-icon fa fa-mavon-floppy-o",attrs:{type:"button","aria-hidden":"true",title:e.d_words.tl_save+" (ctrl+s)"},on:{click:function(r){return e.$clicks("save")}}}):e._e(),e._v(" "),e._t("left-toolbar-after"),e._v(" "),n("transition",{attrs:{name:"fade"}},[e.s_img_link_open?n("div",{staticClass:"add-image-link-wrapper"},[n("div",{staticClass:"add-image-link"},[n("i",{staticClass:"fa fa-mavon-times",attrs:{"aria-hidden":"true"},on:{click:function(r){r.stopPropagation(),r.preventDefault(),e.s_img_link_open=!1}}}),e._v(" "),n("h3",{staticClass:"title"},[e._v(e._s(e.link_type=="link"?e.d_words.tl_popup_link_title:e.d_words.tl_popup_img_link_title))]),e._v(" "),n("div",{staticClass:"link-text input-wrapper"},[n("input",{directives:[{name:"model",rawName:"v-model",value:e.link_text,expression:"link_text"}],ref:"linkTextInput",attrs:{type:"text",placeholder:e.link_type=="link"?e.d_words.tl_popup_link_text:e.d_words.tl_popup_img_link_text},domProps:{value:e.link_text},on:{input:function(r){r.target.composing||(e.link_text=r.target.value)}}})]),e._v(" "),n("div",{staticClass:"link-addr input-wrapper"},[n("input",{directives:[{name:"model",rawName:"v-model",value:e.link_addr,expression:"link_addr"}],attrs:{type:"text",placeholder:e.link_type=="link"?e.d_words.tl_popup_link_addr:e.d_words.tl_popup_img_link_addr},domProps:{value:e.link_addr},on:{input:function(r){r.target.composing||(e.link_addr=r.target.value)}}})]),e._v(" "),n("div",{staticClass:"op-btn cancel",on:{click:function(r){r.stopPropagation(),e.s_img_link_open=!1}}},[e._v(e._s(e.d_words.tl_popup_link_cancel))]),e._v(" "),n("div",{staticClass:"op-btn sure",on:{click:function(r){return r.stopPropagation(),e.$imgLinkAdd()}}},[e._v(e._s(e.d_words.tl_popup_link_sure))])])]):e._e()])],2)},staticRenderFns:[]},c.exports.render._withStripped=!0},function(c,_,i){c.exports={render:function(){var e=this,o=e.$createElement,n=e._self._c||o;return n("div",{staticClass:"v-note-wrapper markdown-body",class:[{fullscreen:e.s_fullScreen,shadow:e.boxShadow}],style:{"box-shadow":e.boxShadow?e.boxShadowStyle:""}},[n("div",{directives:[{name:"show",rawName:"v-show",value:e.toolbarsFlag,expression:"toolbarsFlag"}],staticClass:"v-note-op",style:{background:e.toolbarsBackground}},[n("v-md-toolbar-left",{ref:"toolbar_left",class:{transition:e.transition},attrs:{editable:e.editable,transition:e.transition,d_words:e.d_words,toolbars:e.toolbars,image_filter:e.imageFilter},on:{toolbar_left_click:e.toolbar_left_click,toolbar_left_addlink:e.toolbar_left_addlink,imgAdd:e.$imgAdd,imgDel:e.$imgDel,imgTouch:e.$imgTouch}},[e._t("left-toolbar-before",null,{slot:"left-toolbar-before"}),e._v(" "),e._t("left-toolbar-after",null,{slot:"left-toolbar-after"})],2),e._v(" "),n("v-md-toolbar-right",{ref:"toolbar_right",class:{transition:e.transition},attrs:{d_words:e.d_words,toolbars:e.toolbars,s_subfield:e.s_subfield,s_preview_switch:e.s_preview_switch,s_fullScreen:e.s_fullScreen,s_html_code:e.s_html_code,s_navigation:e.s_navigation},on:{toolbar_right_click:e.toolbar_right_click}},[e._t("right-toolbar-before",null,{slot:"right-toolbar-before"}),e._v(" "),e._t("right-toolbar-after",null,{slot:"right-toolbar-after"})],2)],1),e._v(" "),n("div",{staticClass:"v-note-panel"},[n("div",{ref:"vNoteEdit",staticClass:"v-note-edit divarea-wrapper",class:{"scroll-style":e.s_scrollStyle,"scroll-style-border-radius":e.s_scrollStyle&&!e.s_preview_switch&&!e.s_html_code,"single-edit":!e.s_preview_switch&&!e.s_html_code,"single-show":!e.s_subfield&&e.s_preview_switch||!e.s_subfield&&e.s_html_code,transition:e.transition},on:{scroll:e.$v_edit_scroll,click:e.textAreaFocus}},[n("div",{staticClass:"content-input-wrapper",style:{"background-color":e.editorBackground}},[n("v-autoTextarea",{ref:"vNoteTextarea",staticClass:"content-input",style:{"background-color":e.editorBackground},attrs:{placeholder:e.placeholder?e.placeholder:e.d_words.start_editor,fontSize:e.fontSize,lineHeight:"1.5",fullHeight:""},model:{value:e.d_value,callback:function(r){e.d_value=r},expression:"d_value"}})],1)]),e._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:e.s_preview_switch||e.s_html_code,expression:"s_preview_switch || s_html_code"}],staticClass:"v-note-show",class:{"single-show":!e.s_subfield&&e.s_preview_switch||!e.s_subfield&&e.s_html_code}},[n("div",{directives:[{name:"show",rawName:"v-show",value:!e.s_html_code,expression:"!s_html_code"}],ref:"vShowContent",staticClass:"v-show-content",class:{"scroll-style":e.s_scrollStyle,"scroll-style-border-radius":e.s_scrollStyle},style:{"background-color":e.previewBackground},domProps:{innerHTML:e._s(e.d_render)}}),e._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:e.s_html_code,expression:"s_html_code"}],staticClass:"v-show-content-html",class:{"scroll-style":e.s_scrollStyle,"scroll-style-border-radius":e.s_scrollStyle},style:{"background-color":e.previewBackground}},[e._v(`
                `+e._s(e.d_render)+`
            `)])]),e._v(" "),n("transition",{attrs:{name:"slideTop"}},[n("div",{directives:[{name:"show",rawName:"v-show",value:e.s_navigation,expression:"s_navigation"}],staticClass:"v-note-navigation-wrapper",class:{transition:e.transition}},[n("div",{staticClass:"v-note-navigation-title"},[e._v(`
                    `+e._s(e.d_words.navigation_title)),n("i",{staticClass:"fa fa-mavon-times v-note-navigation-close",attrs:{"aria-hidden":"true"},on:{click:function(r){return e.toolbar_right_click("navigation")}}})]),e._v(" "),n("div",{ref:"navigationContent",staticClass:"v-note-navigation-content",class:{"scroll-style":e.s_scrollStyle}})])])],1),e._v(" "),n("transition",{attrs:{name:"fade"}},[n("div",{ref:"help"},[e.s_help?n("div",{staticClass:"v-note-help-wrapper",on:{click:function(r){return r.target!==r.currentTarget?null:e.toolbar_right_click("help")}}},[n("div",{staticClass:"v-note-help-content markdown-body",class:{shadow:e.boxShadow}},[n("i",{staticClass:"fa fa-mavon-times",attrs:{"aria-hidden":"true"},on:{click:function(r){return r.stopPropagation(),r.preventDefault(),e.toolbar_right_click("help")}}}),e._v(" "),n("div",{staticClass:"scroll-style v-note-help-show",domProps:{innerHTML:e._s(e.d_help)}})])]):e._e()])]),e._v(" "),n("transition",{attrs:{name:"fade"}},[e.d_preview_imgsrc?n("div",{staticClass:"v-note-img-wrapper",on:{click:function(r){e.d_preview_imgsrc=null}}},[n("img",{attrs:{src:e.d_preview_imgsrc,alt:"none"}})]):e._e()]),e._v(" "),n("div",{ref:"vReadModel",staticClass:"v-note-read-model scroll-style",class:{show:e.s_readmodel}},[n("div",{ref:"vNoteReadContent",staticClass:"v-note-read-content",domProps:{innerHTML:e._s(e.d_render)}})])],1)},staticRenderFns:[]},c.exports.render._withStripped=!0},function(c,_,i){var e=i(107);typeof e=="string"&&(e=[[c.i,e,""]]),e.locals&&(c.exports=e.locals),i(17)("c0faed68",e,!1,{})},function(c,_,i){var e=i(108);typeof e=="string"&&(e=[[c.i,e,""]]),e.locals&&(c.exports=e.locals),i(17)("118de024",e,!1,{})},function(c,_,i){var e=i(109);typeof e=="string"&&(e=[[c.i,e,""]]),e.locals&&(c.exports=e.locals),i(17)("2f84471f",e,!1,{})},function(c,_,i){var e=i(110);typeof e=="string"&&(e=[[c.i,e,""]]),e.locals&&(c.exports=e.locals),i(17)("6daa4aa0",e,!1,{})},function(c,_){c.exports=function(i,e){for(var o=[],n={},r=0;r<e.length;r++){var t=e[r],a=t[0],l=t[1],p=t[2],s=t[3],u={id:i+":"+r,css:l,media:p,sourceMap:s};n[a]?n[a].parts.push(u):o.push(n[a]={id:a,parts:[u]})}return o}},function(c,_){var i;i=function(){return this}();try{i=i||Function("return this")()||(0,eval)("this")}catch{typeof window=="object"&&(i=window)}c.exports=i},function(c,_){c.exports=function(i){return i.webpackPolyfill||(i.deprecate=function(){},i.paths=[],i.children||(i.children=[]),Object.defineProperty(i,"loaded",{enumerable:!0,get:function(){return i.l}}),Object.defineProperty(i,"id",{enumerable:!0,get:function(){return i.i}}),i.webpackPolyfill=1),i}},function(c,_,i){function e(a,l){return new r(l).process(a)}var o=i(61),n=i(62),r=i(210);_=c.exports=e,_.filterXSS=e,_.FilterXSS=r;for(var t in o)_[t]=o[t];for(var t in n)_[t]=n[t];typeof window!="undefined"&&(window.filterXSS=c.exports),function(){return typeof self!="undefined"&&typeof DedicatedWorkerGlobalScope!="undefined"&&self instanceof DedicatedWorkerGlobalScope}()&&(self.filterXSS=c.exports)},function(c,_,i){function e(d){return d==null}function o(d){var v=u.spaceIndex(d);if(v===-1)return{html:"",closing:d[d.length-2]==="/"};d=u.trim(d.slice(v+1,-1));var f=d[d.length-1]==="/";return f&&(d=u.trim(d.slice(0,-1))),{html:d,closing:f}}function n(d){var v={};for(var f in d)v[f]=d[f];return v}function r(d){d=n(d||{}),d.stripIgnoreTag&&(d.onIgnoreTag&&console.error('Notes: cannot use these two options "stripIgnoreTag" and "onIgnoreTag" at the same time'),d.onIgnoreTag=a.onIgnoreTagStripAll),d.whiteList=d.whiteList||a.whiteList,d.onTag=d.onTag||a.onTag,d.onTagAttr=d.onTagAttr||a.onTagAttr,d.onIgnoreTag=d.onIgnoreTag||a.onIgnoreTag,d.onIgnoreTagAttr=d.onIgnoreTagAttr||a.onIgnoreTagAttr,d.safeAttrValue=d.safeAttrValue||a.safeAttrValue,d.escapeHtml=d.escapeHtml||a.escapeHtml,this.options=d,d.css===!1?this.cssFilter=!1:(d.css=d.css||{},this.cssFilter=new t(d.css))}var t=i(31).FilterCSS,a=i(61),l=i(62),p=l.parseTag,s=l.parseAttr,u=i(35);r.prototype.process=function(d){if(d=d||"",!(d=d.toString()))return"";var v=this,f=v.options,y=f.whiteList,m=f.onTag,h=f.onIgnoreTag,w=f.onTagAttr,g=f.onIgnoreTagAttr,k=f.safeAttrValue,b=f.escapeHtml,C=v.cssFilter;f.stripBlankChar&&(d=a.stripBlankChar(d)),f.allowCommentTag||(d=a.stripCommentTag(d));var x=!1;if(f.stripIgnoreTagBody){var x=a.StripTagBody(f.stripIgnoreTagBody,h);h=x.onIgnoreTag}var E=p(d,function(D,A,S,T,I){var $={sourcePosition:D,position:A,isClosing:I,isWhite:y.hasOwnProperty(S)},q=m(S,T,$);if(!e(q))return q;if($.isWhite){if($.isClosing)return"</"+S+">";var M=o(T),z=y[S],U=s(M.html,function(P,j){var O=u.indexOf(z,P)!==-1,B=w(S,P,j,O);if(!e(B))return B;if(O)return j=k(S,P,j,C),j?P+'="'+j+'"':P;var B=g(S,P,j,O);return e(B)?void 0:B}),T="<"+S;return U&&(T+=" "+U),M.closing&&(T+=" /"),T+=">"}var q=h(S,T,$);return e(q)?b(T):q},b);return x&&(E=x.remove(E)),E},c.exports=r},function(c,_){c.exports={start_editor:"Bearbeitung beginnen...",navigation_title:"Navigation",tl_bold:"Fett",tl_italic:"Kursiv",tl_header:"\xDCberschrift",tl_header_one:"\xDCberschrift 1",tl_header_two:"\xDCberschrift 2",tl_header_three:"\xDCberschrift 3",tl_header_four:"\xDCberschrift 4",tl_header_five:"\xDCberschrift 5",tl_header_six:"\xDCberschrift 6",tl_underline:"Unterstrichen",tl_strikethrough:"Durchgestrichen",tl_mark:"Markiert",tl_superscript:"Hochgestellt",tl_subscript:"Tiefgestellt",tl_quote:"Zitat",tl_ol:"Ol",tl_ul:"Ul",tl_link:"Link",tl_image:"Link mit Bild",tl_code:"Code",tl_table:"Tabelle",tl_undo:"R\xFCckg\xE4ngig",tl_redo:"Wiederherstellen",tl_trash:"M\xFClleimer",tl_save:"Speichern",tl_navigation_on:"Navigation AN",tl_navigation_off:"Navigation AUS",tl_preview:"Vorschau",tl_aligncenter:"Text zentrieren",tl_alignleft:"Nach links ausrichten",tl_alignright:"Nach rechts ausrichten",tl_edit:"Bearbeiten",tl_single_column:"Einspaltig",tl_double_column:"Zweispaltig",tl_fullscreen_on:"Vollbild AN",tl_fullscreen_off:"Vollbild AUS",tl_read:"Lesemodus",tl_html_on:"HTML AN",tl_html_off:"HTML AUS",tl_help:"Markdown Handbuch",tl_upload:"Bilder-Upload",tl_upload_remove:"Entfernen",tl_popup_link_title:"Link hinzuf\xFCgen",tl_popup_link_text:"Text des Links",tl_popup_link_addr:"Linkziel",tl_popup_img_link_title:"Bild hinzuf\xFCgen",tl_popup_img_link_text:"Text des Bildes",tl_popup_img_link_addr:"Link auf Bild",tl_popup_link_sure:"Ja",tl_popup_link_cancel:"Abbruch"}},function(c,_){c.exports={start_editor:"Begin editing...",navigation_title:"Navigation",tl_bold:"Bold",tl_italic:"Italic",tl_header:"Header",tl_header_one:"Header 1",tl_header_two:"Header 2",tl_header_three:"Header 3",tl_header_four:"Header 4",tl_header_five:"Header 5",tl_header_six:"Header 6",tl_underline:"Underline",tl_strikethrough:"Strikethrough",tl_mark:"Mark",tl_superscript:"Superscript",tl_subscript:"Subscript",tl_quote:"Quote",tl_ol:"Ol",tl_ul:"Ul",tl_link:"Link",tl_image:"Image Link",tl_code:"Code",tl_table:"Table",tl_undo:"Undo",tl_redo:"Redo",tl_trash:"Trash",tl_save:"Save",tl_navigation_on:"Navigation ON",tl_navigation_off:"Navigation OFF",tl_preview:"Preview",tl_aligncenter:"Center text",tl_alignleft:"Clamp text to the left",tl_alignright:"Clamp text to the right",tl_edit:"Edit",tl_single_column:"Single Column",tl_double_column:"Double Columns",tl_fullscreen_on:"FullScreen ON",tl_fullscreen_off:"FullScreen OFF",tl_read:"Read Model",tl_html_on:"HTML ON",tl_html_off:"HTML OFF",tl_help:"Markdown Guide",tl_upload:"Upload Images",tl_upload_remove:"Remove",tl_popup_link_title:"Add Link",tl_popup_link_text:"Link text",tl_popup_link_addr:"Link address",tl_popup_img_link_title:"Add Image",tl_popup_img_link_text:"Image Text",tl_popup_img_link_addr:"Image Link",tl_popup_link_sure:"Sure",tl_popup_link_cancel:"Cancel"}},function(c,_){c.exports={start_editor:"D\xE9but d'\xE9dition...",navigation_title:"Navigation",tl_bold:"Gras",tl_italic:"Italique",tl_header:"Ent\xEAte",tl_header_one:"Ent\xEAte 1",tl_header_two:"Ent\xEAte 2",tl_header_three:"Ent\xEAte 3",tl_header_four:"Ent\xEAte 4",tl_header_five:"Ent\xEAte 5",tl_header_six:"Ent\xEAte 6",tl_underline:"Soulign\xE9",tl_strikethrough:"Barr\xE9",tl_mark:"Mark",tl_superscript:"Exposant",tl_subscript:"Sous-exposant",tl_quote:"Quote",tl_ol:"Liste ",tl_ul:"Puce",tl_link:"Lien",tl_image:"Image Lien",tl_code:"Code",tl_table:"Table",tl_undo:"Annuler",tl_redo:"Refaire",tl_trash:"Supprimer",tl_save:"Sauver",tl_navigation_on:"Activer la navigation",tl_navigation_off:"D\xE9sactiver le navigation",tl_preview:"Previsualis\xE9",tl_aligncenter:"Center le texte",tl_alignleft:"F\xE9rer le texte \xE0 gauche",tl_alignright:"F\xE9rer le texte \xE0 droite",tl_edit:"Editer",tl_single_column:"Seule Colonne",tl_double_column:"Colonnes Doubles",tl_fullscreen_on:"Activer le mode plein \xE9cran",tl_fullscreen_off:"D\xE9sactiver le mode plein \xE9cran",tl_read:"Lire le mod\xE8le",tl_html_on:"Activer le mode HTML",tl_html_off:"D\xE9sactiver le mode HTML",tl_help:"Guide Markdown",tl_upload:"T\xE9l\xE9charger les images",tl_upload_remove:"Supprimer",tl_popup_link_title:"Ajouter un lien",tl_popup_link_text:"Description",tl_popup_link_addr:"Link",tl_popup_img_link_title:"Ajouter une image",tl_popup_img_link_text:"Description",tl_popup_img_link_addr:"Link",tl_popup_link_sure:"s\xFBr",tl_popup_link_cancel:"Annuler"}},function(c,_){c.exports={start_editor:"\u7DE8\u96C6\u3092\u59CB\u3081\u3066\u306D\uFF01",navigation_title:"\u30CA\u30D3\u30B2\u30FC\u30B7\u30E7\u30F3",tl_bold:"\u592A\u5B57",tl_italic:"\u659C\u4F53",tl_header:"\u898B\u51FA\u3057",tl_header_one:"\u898B\u51FA\u30571",tl_header_two:"\u898B\u51FA\u30572",tl_header_three:"\u898B\u51FA\u30573",tl_header_four:"\u898B\u51FA\u30574",tl_header_five:"\u898B\u51FA\u30575",tl_header_six:"\u898B\u51FA\u30576",tl_underline:"\u4E0B\u7DDA",tl_strikethrough:"\u53D6\u308A\u6D88\u3057\u7DDA",tl_mark:"\u86CD\u5149\u30DA\u30F3",tl_superscript:"\u4E0A\u4ED8\u304D\u6587\u5B57",tl_subscript:"\u4E0B\u4ED8\u304D\u6587\u5B57",tl_quote:"\u5F15\u7528",tl_ol:"\u756A\u53F7\u4ED8\u304D\u30EA\u30B9\u30C8",tl_ul:"\u7B87\u6761\u66F8\u304D\u30EA\u30B9\u30C8",tl_link:"\u30CF\u30A4\u30D1\u30FC\u30EA\u30F3\u30AF",tl_image:"\u753B\u50CF\u306E\u30EA\u30F3\u30AF",tl_code:"\u30B3\u30FC\u30C9\u306E\u633F\u5165",tl_table:"\u8868\u306E\u633F\u5165",tl_undo:"\u623B\u308B",tl_redo:"\u9032\u3080",tl_trash:"\u524A\u9664",tl_save:"\u4FDD\u5B58",tl_navigation_on:"\u30CA\u30D3\u30B2\u30FC\u30B7\u30E7\u30F3\u3092\u8868\u793A",tl_navigation_off:"\u30CA\u30D3\u30B2\u30FC\u30B7\u30E7\u30F3\u3092\u975E\u8868\u793A",tl_preview:"\u30D7\u30EC\u30D3\u30E5\u30FC",tl_aligncenter:"\u4E2D\u592E\u63C3\u3048",tl_alignleft:"\u5DE6\u63C3\u3048",tl_alignright:"\u53F3\u63C3\u3048",tl_edit:"\u7DE8\u96C6",tl_single_column:"\u4E00\u5217",tl_double_column:"\u4E8C\u5217",tl_fullscreen_on:"\u5168\u753B\u9762\u8868\u793A",tl_fullscreen_off:"\u5168\u753B\u9762\u8868\u793A\u306E\u7D42\u4E86",tl_read:"\u30E2\u30C7\u30EB\u306E\u8AAD\u307F\u8FBC\u307F",tl_html_on:"HTML\u3067\u8868\u793A",tl_html_off:"HTML\u8868\u793A\u306E\u7D42\u4E86",tl_help:"\u30D8\u30EB\u30D7",tl_upload:"\u753B\u50CF\u3092\u30A2\u30C3\u30D7\u30ED\u30FC\u30C9",tl_upload_remove:"\u753B\u50CF\u306E\u524A\u9664",tl_popup_link_title:"\u30EA\u30F3\u30AF\u306E\u8FFD\u52A0",tl_popup_link_text:"\u30EA\u30F3\u30AF\u30C6\u30AD\u30B9\u30C8",tl_popup_link_addr:"\u30EA\u30F3\u30AF\u5148\u306EURL",tl_popup_img_link_title:"\u753B\u50CF\u306E\u8FFD\u52A0",tl_popup_img_link_text:"\u753B\u50CF\u30BF\u30A4\u30C8\u30EB",tl_popup_img_link_addr:"\u753B\u50CFURL",tl_popup_link_sure:"OK",tl_popup_link_cancel:"\u623B\u308B"}},function(c,_){c.exports={start_editor:"Come\xE7ar edi\xE7\xE3o...",navigation_title:"Navega\xE7\xE3o",tl_bold:"Negrito",tl_italic:"It\xE1lico",tl_header:"Cabe\xE7alho",tl_header_one:"Cabe\xE7alho 1",tl_header_two:"Cabe\xE7alho 2",tl_header_three:"Cabe\xE7alho 3",tl_header_four:"Cabe\xE7alho 4",tl_header_five:"Cabe\xE7alho 5",tl_header_six:"Cabe\xE7alho 6",tl_underline:"Sublinhar",tl_strikethrough:"Tachar",tl_mark:"Marca\xE7\xE3o",tl_superscript:"Sobrescrito",tl_subscript:"Subscrito",tl_quote:"Cita\xE7\xE3o",tl_ol:"Lista Numerada",tl_ul:"Lista com marcadores",tl_link:"Link",tl_image:"Link de imagem",tl_code:"C\xF3digo",tl_table:"Tabela",tl_undo:"Desfazer",tl_redo:"Refazer",tl_trash:"Lixo",tl_save:"Salvar",tl_navigation_on:"Mostrar Navega\xE7\xE3o",tl_navigation_off:"Esconder Navega\xE7\xE3o",tl_preview:"Preview",tl_aligncenter:"Alinhar no centro",tl_alignleft:"Alinhar \xE0 esquerda",tl_alignright:"Alinhar \xE0 direita",tl_edit:"Editar",tl_single_column:"Coluna \xDAnica",tl_double_column:"Duas Colunas",tl_fullscreen_on:"Ligar Tela Cheia",tl_fullscreen_off:"Desligar Tela Cheia",tl_read:"Modo de Leitura",tl_html_on:"Ligar HTML",tl_html_off:"Desligar HTML",tl_help:"Guia Markdown",tl_upload:"Upload de Imagens",tl_upload_remove:"Remover",tl_popup_link_title:"Adicionar Link",tl_popup_link_text:"Descri\xE7\xE3o",tl_popup_link_addr:"Link",tl_popup_img_link_title:"Adicionar fotos",tl_popup_img_link_text:"Descri\xE7\xE3o",tl_popup_img_link_addr:"Link",tl_popup_link_sure:"Confirmar",tl_popup_link_cancel:"Cancelar"}},function(c,_){c.exports={start_editor:"\u041D\u0430\u0447\u043D\u0438\u0442\u0435 \u0440\u0435\u0434\u0430\u043A\u0442\u0438\u0440\u043E\u0432\u0430\u043D\u0438\u0435...",navigation_title:"\u041D\u0430\u0432\u0438\u0433\u0430\u0446\u0438\u044F",tl_bold:"\u041F\u043E\u043B\u0443\u0436\u0438\u0440\u043D\u044B\u0439",tl_italic:"\u041A\u0443\u0440\u0441\u0438\u0432",tl_header:"\u0417\u0430\u0433\u043E\u043B\u043E\u0432\u043A\u0438",tl_header_one:"\u0417\u0430\u0433\u043E\u043B\u043E\u0432\u043E\u043A 1",tl_header_two:"\u0417\u0430\u0433\u043E\u043B\u043E\u0432\u043E\u043A 2",tl_header_three:"\u0417\u0430\u0433\u043E\u043B\u043E\u0432\u043E\u043A 3",tl_header_four:"\u0417\u0430\u0433\u043E\u043B\u043E\u0432\u043E\u043A 4",tl_header_five:"\u0417\u0430\u0433\u043E\u043B\u043E\u0432\u043E\u043A 5",tl_header_six:"\u0417\u0430\u0433\u043E\u043B\u043E\u0432\u043E\u043A 6",tl_underline:"\u041F\u043E\u0434\u0447\u0435\u0440\u043A\u043D\u0443\u0442\u044B\u0439",tl_strikethrough:"\u0417\u0430\u0447\u0435\u0440\u043A\u043D\u0443\u0442\u044B\u0439",tl_mark:"\u041E\u0442\u043C\u0435\u0442\u043A\u0430",tl_superscript:"\u0412\u0435\u0440\u0445\u043D\u0438\u0439 \u0438\u043D\u0434\u0435\u043A\u0441",tl_subscript:"\u041D\u0438\u0436\u043D\u0438\u0439 \u0438\u043D\u0434\u0435\u043A\u0441",tl_quote:"\u0426\u0438\u0442\u0430\u0442\u0430",tl_ol:"\u041D\u0443\u043C\u0435\u0440\u043E\u0432\u0430\u043D\u043D\u044B\u0439 \u0441\u043F\u0438\u0441\u043E\u043A",tl_ul:"\u0421\u043F\u0438\u0441\u043E\u043A",tl_link:"\u0421\u0441\u044B\u043B\u043A\u0430",tl_image:"\u0421\u0441\u044B\u043B\u043A\u0430 \u0438\u0437\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u044F",tl_code:"\u041A\u043E\u0434",tl_table:"\u0422\u0430\u0431\u043B\u0438\u0446\u0430",tl_undo:"\u041E\u0442\u043C\u0435\u043D\u0438\u0442\u044C",tl_redo:"\u0412\u0435\u0440\u043D\u0443\u0442\u044C",tl_trash:"\u0423\u0434\u0430\u043B\u0438\u0442\u044C",tl_save:"\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C",tl_navigation_on:"\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C \u043D\u0430\u0432\u0438\u0433\u0430\u0446\u0438\u044E",tl_navigation_off:"\u0421\u043A\u0440\u044B\u0442\u044C \u043D\u0430\u0432\u0438\u0433\u0430\u0446\u0438\u044E",tl_preview:"\u041F\u0440\u0435\u0434\u043F\u0440\u043E\u0441\u043C\u043E\u0442\u0440",tl_aligncenter:"\u0412\u044B\u0440\u043E\u0432\u043D\u044F\u0442\u044C \u043F\u043E \u0446\u0435\u043D\u0442\u0440\u0443",tl_alignleft:"\u0412\u044B\u0440\u043E\u0432\u043D\u044F\u0442\u044C \u043F\u043E \u043B\u0435\u0432\u043E\u043C\u0443 \u043A\u0440\u0430\u044E",tl_alignright:"\u0412\u044B\u0440\u043E\u0432\u043D\u044F\u0442\u044C \u043F\u043E \u043F\u0440\u0430\u0432\u043E\u043C\u0443 \u043A\u0440\u0430\u044E",tl_edit:"\u0420\u0435\u0434\u0430\u043A\u0442\u043E\u0440",tl_single_column:"\u041E\u0434\u043D\u043E \u043F\u043E\u043B\u0435",tl_double_column:"\u0414\u0432\u0430 \u043F\u043E\u043B\u044F",tl_fullscreen_on:"\u041F\u043E\u043B\u043D\u043E\u044D\u043A\u0440\u0430\u043D\u043D\u044B\u0439 \u0440\u0435\u0436\u0438\u043C",tl_fullscreen_off:"\u0412\u044B\u043A\u043B\u044E\u0447\u0438\u0442\u044C \u043F\u043E\u043B\u043D\u043E\u044D\u043A\u0440\u0430\u043D\u043D\u044B\u0439 \u0440\u0435\u0436\u0438\u043C",tl_read:"\u0420\u0435\u0436\u0438\u043C \u0447\u0442\u0435\u043D\u0438\u044F",tl_html_on:"\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C HTML",tl_html_off:"\u0423\u0431\u0440\u0430\u0442\u044C HTML",tl_help:"Markdown \u043F\u043E\u043C\u043E\u0449\u044C",tl_upload:"\u0417\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044C \u0438\u0437\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u0435",tl_upload_remove:"\u0423\u0434\u0430\u043B\u0438\u0442\u044C",tl_popup_link_title:"\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0441\u0441\u044B\u043B\u043A\u0443",tl_popup_link_text:"\u0422\u0435\u043A\u0441\u0442 \u0441\u0441\u044B\u043B\u043A\u0438",tl_popup_link_addr:"\u0410\u0434\u0440\u0435\u0441 \u0441\u0441\u044B\u043B\u043A\u0438",tl_popup_img_link_title:"\u041B\u043E\u043A\u0430\u043B\u044C\u043D\u043E\u0435 \u0438\u0437\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u0435",tl_popup_img_link_text:"\u0422\u0435\u043A\u0441\u0442 \u0438\u0437\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u044F",tl_popup_img_link_addr:"\u0421\u0441\u044B\u043B\u043A\u0430 \u0438\u0437\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u044F",tl_popup_link_sure:"\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C",tl_popup_link_cancel:"\u041E\u0442\u043C\u0435\u043D\u0438\u0442\u044C"}},function(c,_){c.exports={start_editor:"\u5F00\u59CB\u7F16\u8F91...",navigation_title:"\u5BFC\u822A\u76EE\u5F55",tl_bold:"\u7C97\u4F53",tl_italic:"\u659C\u4F53",tl_header:"\u6807\u9898",tl_header_one:"\u4E00\u7EA7\u6807\u9898",tl_header_two:"\u4E8C\u7EA7\u6807\u9898",tl_header_three:"\u4E09\u7EA7\u6807\u9898",tl_header_four:"\u56DB\u7EA7\u6807\u9898",tl_header_five:"\u4E94\u7EA7\u6807\u9898",tl_header_six:"\u516D\u7EA7\u6807\u9898",tl_underline:"\u4E0B\u5212\u7EBF",tl_strikethrough:"\u4E2D\u5212\u7EBF",tl_mark:"\u6807\u8BB0",tl_superscript:"\u4E0A\u89D2\u6807",tl_subscript:"\u4E0B\u89D2\u6807",tl_quote:"\u6BB5\u843D\u5F15\u7528",tl_ol:"\u6709\u5E8F\u5217\u8868",tl_ul:"\u65E0\u5E8F\u5217\u8868",tl_link:"\u94FE\u63A5",tl_image:"\u6DFB\u52A0\u56FE\u7247\u94FE\u63A5",tl_code:"\u4EE3\u7801\u5757",tl_table:"\u8868\u683C",tl_undo:"\u4E0A\u4E00\u6B65",tl_redo:"\u4E0B\u4E00\u6B65",tl_trash:"\u6E05\u7A7A",tl_save:"\u4FDD\u5B58",tl_navigation_on:"\u5F00\u542F\u6807\u9898\u5BFC\u822A",tl_navigation_off:"\u5173\u95ED\u6807\u9898\u5BFC\u822A",tl_preview:"\u9884\u89C8",tl_aligncenter:"\u5C45\u4E2D",tl_alignleft:"\u5C45\u5DE6",tl_alignright:"\u5C45\u53F3",tl_edit:"\u7F16\u8F91",tl_single_column:"\u5355\u680F",tl_double_column:"\u53CC\u680F",tl_fullscreen_on:"\u5168\u5C4F\u7F16\u8F91",tl_fullscreen_off:"\u9000\u51FA\u5168\u5C4F",tl_read:"\u6C89\u6D78\u5F0F\u9605\u8BFB",tl_html_on:"\u67E5\u770Bhtml\u6587\u672C",tl_html_off:"\u8FD4\u56DEmarkdown\u6587\u672C",tl_help:"markdown\u8BED\u6CD5\u5E2E\u52A9",tl_upload:"\u4E0A\u4F20\u56FE\u7247",tl_upload_remove:"\u5220\u9664",tl_popup_link_title:"\u6DFB\u52A0\u94FE\u63A5",tl_popup_link_text:"\u94FE\u63A5\u63CF\u8FF0",tl_popup_link_addr:"\u94FE\u63A5\u5730\u5740",tl_popup_img_link_title:"\u6DFB\u52A0\u56FE\u7247",tl_popup_img_link_text:"\u56FE\u7247\u63CF\u8FF0",tl_popup_img_link_addr:"\u56FE\u7247\u94FE\u63A5",tl_popup_link_sure:"\u786E\u5B9A",tl_popup_link_cancel:"\u53D6\u6D88"}},function(c,_){c.exports={start_editor:"\u958B\u59CB\u7DE8\u8F2F...",navigation_title:"\u5C0E\u822A\u76EE\u9304",tl_bold:"\u7C97\u9AD4",tl_italic:"\u659C\u9AD4",tl_header:"\u6A19\u984C",tl_header_one:"\u4E00\u7D1A\u6A19\u984C",tl_header_two:"\u4E8C\u7D1A\u6A19\u984C",tl_header_three:"\u4E09\u7D1A\u6A19\u984C",tl_header_four:"\u56DB\u7D1A\u6A19\u984C",tl_header_five:"\u4E94\u7D1A\u6A19\u984C",tl_header_six:"\u516D\u7D1A\u6A19\u984C",tl_underline:"\u4E0B\u5283\u7DDA",tl_strikethrough:"\u4E2D\u5283\u7DDA",tl_mark:"\u6A19\u8A18",tl_superscript:"\u4E0A\u89D2\u6A19",tl_subscript:"\u4E0B\u89D2\u6A19",tl_quote:"\u6BB5\u843D\u5F15\u7528",tl_ol:"\u6709\u5E8F\u5217\u8868",tl_ul:"\u7121\u5E8F\u5217\u8868",tl_link:"\u93C8\u63A5",tl_image:"\u6DFB\u52A0\u5716\u7247\u93C8\u63A5",tl_code:"\u4EE3\u78BC\u584A",tl_table:"\u8868\u683C",tl_undo:"\u4E0A\u4E00\u6B65",tl_redo:"\u4E0B\u4E00\u6B65",tl_trash:"\u6E05\u7A7A",tl_save:"\u4FDD\u5B58",tl_navigation_on:"\u958B\u555F\u6A19\u984C\u5C0E\u822A",tl_navigation_off:"\u95DC\u9589\u6A19\u984C\u5C0E\u822A",tl_preview:"\u9810\u89BD",tl_aligncenter:"\u5C45\u4E2D",tl_alignleft:"\u5C45\u5DE6",tl_alignright:"\u5C45\u53F3",tl_edit:"\u7DE8\u8F2F",tl_single_column:"\u55AE\u6B04",tl_double_column:"\u96D9\u6B04",tl_fullscreen_on:"\u5168\u5C4F\u7DE8\u8F2F",tl_fullscreen_off:"\u9000\u51FA\u5168\u5C4F",tl_read:"\u6C88\u6D78\u5F0F\u95B1\u8B80",tl_html_on:"\u67E5\u770Bhtml\u6587\u672C",tl_html_off:"\u8FD4\u56DEmarkdown\u6587\u672C",tl_help:"markdown\u8A9E\u6CD5\u5E6B\u52A9",tl_upload:"\u4E0A\u50B3\u5716\u7247",tl_upload_remove:"\u522A\u9664",tl_popup_link_title:"\u6DFB\u52A0\u93C8\u63A5",tl_popup_link_text:"\u93C8\u63A5\u63CF\u8FF0",tl_popup_link_addr:"\u93C8\u63A5\u5730\u5740",tl_popup_img_link_title:"\u6DFB\u52A0\u5716\u7247",tl_popup_img_link_text:"\u5716\u7247\u63CF\u8FF0",tl_popup_img_link_addr:"\u5716\u7247\u93C8\u63A5",tl_popup_link_sure:"\u78BA\u5B9A",tl_popup_link_cancel:"\u53D6\u6D88"}}])})})(mavonEditor);var githubMarkdown_min="",index="";const _sfc_main$5={props:{docInfoShow:{type:Object,required:!0},requestParamList:{type:Array,required:!0},responseParamList:{type:Array,required:!0}},setup(){return{requestParamListColumns:[{title:"\u53C2\u6570\u540D",dataIndex:"name",width:200},{title:"\u7C7B\u578B",dataIndex:"type",width:150},{title:"\u53C2\u6570\u4F4D\u7F6E",dataIndex:"in",width:100},{title:"\u5FC5\u586B",dataIndex:"required",width:60},{title:"\u8BF4\u660E",dataIndex:"description"}],responseCodeListColumns:[{title:"\u72B6\u6001\u7801",dataIndex:"code",width:100},{title:"\u7C7B\u578B",dataIndex:"type",width:250},{title:"\u8BF4\u660E",dataIndex:"desc"}],responseParamListColumns:[{title:"\u53C2\u6570\u540D",dataIndex:"name",width:250},{title:"\u7C7B\u578B",dataIndex:"type",width:250},{title:"\u8BF4\u660E",dataIndex:"description"}]}}},_hoisted_1$3=["innerHTML"],_hoisted_2$2=createTextVNode("header"),_hoisted_3$2=createTextVNode("body"),_hoisted_4$2=createTextVNode("query"),_hoisted_5$2=createTextVNode("formData"),_hoisted_6$2=createTextVNode("-"),_hoisted_7={key:0,style:{color:"#f00"}},_hoisted_8=createTextVNode("\u5426"),_hoisted_9=createTextVNode("-"),_hoisted_10=["innerHTML"],_hoisted_11={key:1,style:{"text-align":"center",padding:"10px 0"}};function _sfc_render$5(c,_,i,e,o,n){const r=resolveComponent("a-form-item"),t=resolveComponent("a-card"),a=resolveComponent("a-tag"),l=resolveComponent("a-table"),p=resolveComponent("a-form"),s=resolveDirective("highlight");return openBlock(),createBlock(p,{"label-col":{span:4},"wrapper-col":{span:20}},{default:withCtx(()=>[createVNode(r,{label:"\u63A5\u53E3\u5730\u5740"},{default:withCtx(()=>[createTextVNode(toDisplayString(i.docInfoShow.url),1)]),_:1}),createVNode(r,{label:"\u8BF4\u660E"},{default:withCtx(()=>[createVNode(t,{size:"small"},{default:withCtx(()=>[withDirectives(createBaseVNode("div",{class:"markdown-body",innerHTML:i.docInfoShow.description},null,8,_hoisted_1$3),[[s]])]),_:1})]),_:1}),createVNode(r,{label:"\u8BF7\u6C42\u65B9\u5F0F"},{default:withCtx(()=>[createTextVNode(toDisplayString(i.docInfoShow.method),1)]),_:1}),createVNode(r,{label:"\u8BF7\u6C42\u6570\u636E\u7C7B\u578B"},{default:withCtx(()=>[createTextVNode(toDisplayString(i.docInfoShow.consumes),1)]),_:1}),createVNode(r,{label:"\u54CD\u5E94\u6570\u636E\u7C7B\u578B"},{default:withCtx(()=>[createTextVNode(toDisplayString(i.docInfoShow.produces),1)]),_:1}),createVNode(r,{label:"\u8BF7\u6C42\u53C2\u6570"},{default:withCtx(()=>[createVNode(l,{dataSource:i.requestParamList,columns:e.requestParamListColumns,size:"small",pagination:!1,defaultExpandAllRows:""},{bodyCell:withCtx(({column:u,text:d,record:v})=>[u.dataIndex==="type"?(openBlock(),createElementBlock(Fragment,{key:0},[createTextVNode(toDisplayString(d)+" ",1),v.subType?(openBlock(),createElementBlock(Fragment,{key:0},[createTextVNode("["+toDisplayString(v.subType)+"]",1)],64)):createCommentVNode("",!0),v.format?(openBlock(),createElementBlock(Fragment,{key:1},[createTextVNode("("+toDisplayString(v.format)+")",1)],64)):createCommentVNode("",!0)],64)):createCommentVNode("",!0),u.dataIndex==="in"?(openBlock(),createElementBlock(Fragment,{key:1},[d==="header"?(openBlock(),createBlock(a,{key:0,color:"pink"},{default:withCtx(()=>[_hoisted_2$2]),_:1})):d==="body"?(openBlock(),createBlock(a,{key:1,color:"red"},{default:withCtx(()=>[_hoisted_3$2]),_:1})):d==="query"?(openBlock(),createBlock(a,{key:2,color:"orange"},{default:withCtx(()=>[_hoisted_4$2]),_:1})):d==="formData"?(openBlock(),createBlock(a,{key:3,color:"green"},{default:withCtx(()=>[_hoisted_5$2]),_:1})):d?(openBlock(),createBlock(a,{key:5,color:"purple"},{default:withCtx(()=>[createTextVNode(toDisplayString(d),1)]),_:2},1024)):(openBlock(),createElementBlock(Fragment,{key:4},[_hoisted_6$2],64))],64)):createCommentVNode("",!0),u.dataIndex==="required"?(openBlock(),createElementBlock(Fragment,{key:2},[d==="\u662F"?(openBlock(),createElementBlock("span",_hoisted_7,"\u662F")):d==="\u5426"?(openBlock(),createElementBlock(Fragment,{key:1},[_hoisted_8],64)):(openBlock(),createElementBlock(Fragment,{key:2},[_hoisted_9],64))],64)):createCommentVNode("",!0),u.dataIndex==="description"?(openBlock(),createElementBlock(Fragment,{key:3},[createTextVNode(toDisplayString(d),1)],64)):createCommentVNode("",!0)]),_:1},8,["dataSource","columns"])]),_:1}),createVNode(r,{label:"\u8FD4\u56DE\u7ED3\u679C"},{default:withCtx(()=>[createVNode(l,{dataSource:i.responseParamList,columns:e.responseCodeListColumns,size:"small",pagination:!1},{bodyCell:withCtx(({column:u,text:d,record:v})=>[u.dataIndex==="desc"?(openBlock(),createElementBlock("div",{key:0,innerHTML:d},null,8,_hoisted_10)):createCommentVNode("",!0)]),expandedRowRender:withCtx(({record:u})=>[u.schemas?(openBlock(),createBlock(l,{key:0,dataSource:u.schemas,columns:e.responseParamListColumns,size:"small",pagination:!1},{bodyCell:withCtx(({column:d,text:v,record:f})=>[d.dataIndex==="type"?(openBlock(),createElementBlock(Fragment,{key:0},[createTextVNode(toDisplayString(v)+" ",1),f.subType?(openBlock(),createElementBlock(Fragment,{key:0},[createTextVNode("["+toDisplayString(f.subType)+"]",1)],64)):createCommentVNode("",!0),f.format?(openBlock(),createElementBlock(Fragment,{key:1},[createTextVNode("("+toDisplayString(f.format)+")",1)],64)):createCommentVNode("",!0)],64)):createCommentVNode("",!0)]),_:2},1032,["dataSource","columns"])):(openBlock(),createElementBlock("div",_hoisted_11,"\u65E0\u53C2\u6570\u8BF4\u660E"))]),_:1},8,["dataSource","columns"])]),_:1})]),_:1})}var DocContent=_export_sfc(_sfc_main$5,[["render",_sfc_render$5]]);const _sfc_main$4={props:{paramList:{type:Array,required:!0},showType:{type:Boolean}},components:{CloseOutlined,UploadOutlined},emits:["update:selected"],setup(c,{attrs:_,slots:i,emit:e,expose:o}){let n=ref(c.paramList),r=1e4;(n.value.length<=0||!n.value[n.value.length-1].isLastRow)&&n.value.push({name:"",value:void 0,type:"integer",key:++r,isLastRow:!0});let t=ref([]);n.value.forEach(f=>{f.value=f.example||void 0,(f.enum&&f.type==="array"||f.type==="file"||f.subType==="MultipartFile")&&(f.value=[]),t.value.push(f.key)});const a=(f,y)=>{t.value=f},l=f=>{f.isLastRow&&(f.isLastRow=!1,n.value.push({name:"",value:void 0,type:"integer",key:++r,isLastRow:!0}),t.value.push(r))},p=f=>{f.isLastRow||(n.value=n.value.filter(y=>y!==f))};let s=ref([]);return s.value.push({title:"\u53C2\u6570\u540D",dataIndex:"name",width:250}),c.showType&&s.value.push({title:"\u7C7B\u578B",dataIndex:"type",width:100}),s.value.push({title:"\u53C2\u6570\u503C",dataIndex:"value"}),s.value.push({title:"",dataIndex:"action",width:40}),{queryParamList:n,queryParamSelectedRowKeys:t,queryParamRowSelectionChange:a,queryParamChange:l,queryParamRemove:p,beforeUpload:(f,y)=>(y.type!=="array"?y.value=[f]:y.value=[...y.value,f],!1),handleRemove:(f,y)=>{y.value=y.value.filter(m=>m!==f)},queryParamListColumns:s,getSelectedRowKeys:()=>t.value}}},_hoisted_1$2=createTextVNode("Integer"),_hoisted_2$1=createTextVNode("String"),_hoisted_3$1=createTextVNode("File"),_hoisted_4$1=createTextVNode("Integer"),_hoisted_5$1=createTextVNode("String"),_hoisted_6$1=createTextVNode("\u9009\u62E9\u6587\u4EF6");function _sfc_render$4(c,_,i,e,o,n){const r=resolveComponent("a-input"),t=resolveComponent("a-select-option"),a=resolveComponent("a-select"),l=resolveComponent("a-tag"),p=resolveComponent("upload-outlined"),s=resolveComponent("a-button"),u=resolveComponent("a-upload"),d=resolveComponent("CloseOutlined"),v=resolveComponent("a-table");return openBlock(),createBlock(v,{"row-selection":{selectedRowKeys:e.queryParamSelectedRowKeys,onChange:e.queryParamRowSelectionChange},dataSource:e.queryParamList,columns:e.queryParamListColumns,size:"small",pagination:!1,scroll:{y:"300px"}},{bodyCell:withCtx(({column:f,text:y,record:m})=>[f.dataIndex==="name"?(openBlock(),createBlock(r,{key:0,placeholder:"\u8BF7\u8F93\u5165\u53C2\u6570\u540D",value:m.name,"onUpdate:value":h=>m.name=h,onChange:h=>e.queryParamChange(m)},null,8,["value","onUpdate:value","onChange"])):createCommentVNode("",!0),f.dataIndex==="type"?(openBlock(),createElementBlock(Fragment,{key:1},[m.key>=1e4?(openBlock(),createBlock(a,{key:0,value:m.type,"onUpdate:value":h=>m.type=h},{default:withCtx(()=>[createVNode(t,{value:"integer"},{default:withCtx(()=>[_hoisted_1$2]),_:1}),createVNode(t,{value:"string"},{default:withCtx(()=>[_hoisted_2$1]),_:1}),createVNode(t,{value:"file"},{default:withCtx(()=>[_hoisted_3$1]),_:1})]),_:2},1032,["value","onUpdate:value"])):y==="integer"?(openBlock(),createBlock(l,{key:1,color:"pink"},{default:withCtx(()=>[_hoisted_4$1]),_:1})):y==="string"?(openBlock(),createBlock(l,{key:2,color:"red"},{default:withCtx(()=>[_hoisted_5$1]),_:1})):(openBlock(),createBlock(l,{key:3,color:"green"},{default:withCtx(()=>[createTextVNode(toDisplayString(y||"-"),1)]),_:2},1024))],64)):createCommentVNode("",!0),f.dataIndex==="value"?(openBlock(),createElementBlock(Fragment,{key:2},[m.enum&&m.type==="array"?(openBlock(),createBlock(a,{key:0,value:m.value,"onUpdate:value":h=>m.value=h,mode:"multiple",placeholder:m.description||"\u8BF7\u9009\u62E9",style:{width:"100%"}},{default:withCtx(()=>[(openBlock(!0),createElementBlock(Fragment,null,renderList(m.enum,h=>(openBlock(),createBlock(t,{value:h},{default:withCtx(()=>[createTextVNode(toDisplayString(h),1)]),_:2},1032,["value"]))),256))]),_:2},1032,["value","onUpdate:value","placeholder"])):m.enum?(openBlock(),createBlock(a,{key:1,value:m.value,"onUpdate:value":h=>m.value=h,placeholder:m.description||"\u8BF7\u9009\u62E9",style:{width:"100%"}},{default:withCtx(()=>[(openBlock(!0),createElementBlock(Fragment,null,renderList(m.enum,h=>(openBlock(),createBlock(t,{value:h},{default:withCtx(()=>[createTextVNode(toDisplayString(h),1)]),_:2},1032,["value"]))),256))]),_:2},1032,["value","onUpdate:value","placeholder"])):m.type==="file"||m.subType==="file"||m.subType==="MultipartFile"?(openBlock(),createBlock(u,{key:2,"file-list":m.value,name:"file",multiple:m.type==="array","before-upload":h=>e.beforeUpload(h,m),remove:h=>e.handleRemove(h,m)},{default:withCtx(()=>[createVNode(s,null,{default:withCtx(()=>[createVNode(p),_hoisted_6$1]),_:1})]),_:2},1032,["file-list","multiple","before-upload","remove"])):(openBlock(),createBlock(r,{key:3,placeholder:m.description||"\u8BF7\u8F93\u5165\u53C2\u6570\u503C",value:m.value,"onUpdate:value":h=>m.value=h,onChange:h=>e.queryParamChange(m)},null,8,["placeholder","value","onUpdate:value","onChange"]))],64)):createCommentVNode("",!0),f.dataIndex==="action"?(openBlock(),createElementBlock(Fragment,{key:3},[m.isLastRow?createCommentVNode("",!0):(openBlock(),createBlock(d,{key:0,onClick:h=>e.queryParamRemove(m),style:{cursor:"pointer"}},null,8,["onClick"]))],64)):createCommentVNode("",!0)]),_:1},8,["row-selection","dataSource","columns"])}var ParamTable=_export_sfc(_sfc_main$4,[["render",_sfc_render$4]]);const _sfc_main$3={props:{paramList:{type:Array,required:!0}},components:{CloseOutlined,UploadOutlined},emits:[],setup(c,{attrs:_,slots:i,emit:e,expose:o}){let n=c.paramList,r="",t=p=>{if(p.children){let s={};return p.children.forEach(u=>{s[u.name]=t(u)}),s}return""};n.length===1?r=t(n[0]):n.length>1&&n.forEach(p=>{r[p.name]=t(p)});let a=ref("");return r&&(a.value=JSON.stringify(r,null,4)),{getParam:()=>a.value,bodyRowParam:a}}};function _sfc_render$3(c,_,i,e,o,n){const r=resolveComponent("a-textarea");return openBlock(),createBlock(r,{placeholder:"",value:e.bodyRowParam,"onUpdate:value":_[0]||(_[0]=t=>e.bodyRowParam=t),"auto-size":{minRows:15,maxRows:15}},null,8,["value"])}var ParamBody=_export_sfc(_sfc_main$3,[["render",_sfc_render$3]]);const _sfc_main$2={props:{result:{type:Object,required:!0}},components:{CloseOutlined,ParamTable,ParamBody},setup(c){const{result:_}=toRefs(c);let i=ref("body"),e=ref([]),o=ref([]);const n=()=>{c.result.data&&(c.result.data.headers&&(e.value=c.result.data.headers),c.result.data.cookies&&(o.value=c.result.data.cookies))};return n(),watch(_,()=>n()),{activePage:i,resultHeaders:e,resultCookies:o,resultHeadersColumns:[{title:"KEY",dataIndex:"name"},{title:"VALUE",dataIndex:"value"}],resultCookiesColumns:[{title:"KEY",dataIndex:"name"},{title:"VALUE",dataIndex:"value"}]}}};function _sfc_render$2(c,_,i,e,o,n){const r=resolveComponent("a-tab-pane"),t=resolveComponent("a-table"),a=resolveComponent("a-tabs");return openBlock(),createElementBlock("div",null,[createVNode(a,{activeKey:e.activePage,"onUpdate:activeKey":_[0]||(_[0]=l=>e.activePage=l),closable:"",onTabClick:_[1]||(_[1]=()=>{}),style:{padding:"5px 10px 0"}},{default:withCtx(()=>[createVNode(r,{tab:"Body",key:"body",forceRender:""},{default:withCtx(()=>[i.result.data&&i.result.data.data?(openBlock(),createElementBlock(Fragment,{key:0},[createTextVNode(toDisplayString(i.result.data.data),1)],64)):i.result.data?(openBlock(),createElementBlock(Fragment,{key:1},[createTextVNode(toDisplayString(i.result.data),1)],64)):(openBlock(),createElementBlock(Fragment,{key:2},[createTextVNode(toDisplayString(i.result),1)],64))]),_:1}),createVNode(r,{tab:"Headers",key:"headers",forceRender:""},{default:withCtx(()=>[createVNode(t,{dataSource:e.resultHeaders,columns:e.resultHeadersColumns,size:"small",pagination:!1,scroll:{y:"300px"}},null,8,["dataSource","columns"])]),_:1}),createVNode(r,{tab:"Cookies",key:"cookies",forceRender:""},{default:withCtx(()=>[createVNode(t,{dataSource:e.resultCookies,columns:e.resultCookiesColumns,size:"small",pagination:!1,scroll:{y:"300px"}},null,8,["dataSource","columns"])]),_:1})]),_:1},8,["activeKey"])])}var DocDebuggerResult=_export_sfc(_sfc_main$2,[["render",_sfc_render$2]]);const _sfc_main$1={props:{docInfoShow:{type:Object,required:!0},requestParamList:{type:Array,required:!0},responseParamList:{type:Array,required:!0}},components:{CloseOutlined,ParamTable,ParamBody,DocDebuggerResult},setup(c){const _=useStore();let i=_.state.swaggerResource||{},e=_.state.swaggerDoc||{},o=i.rewriteDomain||e.host,n=ref(o+c.docInfoShow.url),r=ref("urlParam");const t=ref();let a=c.requestParamList.filter(S=>S.in==="query"),l=ref([]);const p=ref();let s=c.requestParamList.filter(S=>S.in==="header"),u=ref(JSON.parse(JSON.stringify(s)));const d=ref();let v=c.requestParamList.filter(S=>S.in==="cookie"),f=ref(JSON.parse(JSON.stringify(v)));const y=ref();let m=c.requestParamList.filter(S=>S.in==="formData"),h=ref([]);c.docInfoShow.method==="post"?m=m.concat(a):l=ref(JSON.parse(JSON.stringify(a)));const w=ref();let g=ref([]),k=ref(),b=ref("form"),C=c.requestParamList.filter(S=>S.in==="body"),x=ref(JSON.parse(JSON.stringify(C)));c.docInfoShow.consumes.indexOf("application/x-www-form-urlencoded")>=0?(b.value="formUrlEncode",g=ref(JSON.parse(JSON.stringify(m)))):c.docInfoShow.consumes.indexOf("multipart/form-data")>=0?(b.value="form",h=ref(JSON.parse(JSON.stringify(m)))):c.docInfoShow.consumes.indexOf("application/json")>=0?(b.value="row",g=ref(JSON.parse(JSON.stringify(m))),m.length>0&&(b.value="formUrlEncode")):h=ref(JSON.parse(JSON.stringify(m))),h.value.length>0?r.value="urlParam":m.length>0||C.length>0?r.value="bodyParam":s.length>0&&(r.value="headerParam");let E=ref({}),D=ref(!1);return{docUrl:n,activePage:r,requestLoading:D,sendRequest:()=>{const S=new FormData;let T=t.value.getSelectedRowKeys(),I=l.value.filter(O=>T.indexOf(O.key)>=0&&O.name&&O.value).map(O=>O.name+"="+encodeURIComponent(O.value)).join("&"),$=p.value.getSelectedRowKeys(),q=u.value.filter(O=>$.indexOf(O.key)>=0&&O.name&&O.value).map(O=>({code:O.name,value:O.value})),M=d.value.getSelectedRowKeys(),z=f.value.filter(O=>M.indexOf(O.key)>=0&&O.name&&O.value).map(O=>({code:O.name,value:O.value})),U=[];if(y.value){let O=y.value.getSelectedRowKeys();U=h.value.filter(B=>O.indexOf(B.key)>=0&&B.name&&B.value).map(B=>({code:B.name,value:B.value}))}let L=[];if(w.value){let O=w.value.getSelectedRowKeys();L=g.value.filter(B=>O.indexOf(B.key)>=0&&B.name&&B.value).map(B=>({code:B.name,value:B.value}))}let P="";k.value&&(P=k.value.getParam());let j=I?n.value+"?"+I:n.value;S.append("url",j),S.append("method",c.docInfoShow.method),S.append("contentType",c.docInfoShow.consumes),S.append("headerParam",JSON.stringify(q)),S.append("cookieParam",JSON.stringify(z)),S.append("formParam",JSON.stringify(U)),S.append("formEncodeParam",JSON.stringify(L)),S.append("bodyParam",P),D.value=!0,zyplayerApi.requestUrl(S).then(O=>{E.value=O,D.value=!1}).catch(O=>{D.value=!1})},requestResult:E,urlParamRef:t,urlParamList:l,headerParamRef:p,headerParamList:u,cookieParamRef:d,cookieParamList:f,formParamRef:y,formParamList:h,formEncodeParamRef:w,formEncodeParamList:g,bodyParamRef:k,bodyParamType:b,bodyRowParamList:x,responseCodeListColumns:[{title:"\u72B6\u6001\u7801",dataIndex:"code",width:100},{title:"\u7C7B\u578B",dataIndex:"type",width:250},{title:"\u8BF4\u660E",dataIndex:"desc"}],responseParamListColumns:[{title:"\u53C2\u6570\u540D",dataIndex:"name",width:250},{title:"\u7C7B\u578B",dataIndex:"type",width:250},{title:"\u8BF4\u660E",dataIndex:"description"}]}}},_hoisted_1$1=createTextVNode("\u53D1\u9001\u8BF7\u6C42"),_hoisted_2=createTextVNode("none"),_hoisted_3=createTextVNode("form-data"),_hoisted_4=createTextVNode("x-www-form-urlencoded"),_hoisted_5=createTextVNode("row"),_hoisted_6=createTextVNode("binary");function _sfc_render$1(c,_,i,e,o,n){const r=resolveComponent("a-button"),t=resolveComponent("a-input-search"),a=resolveComponent("ParamTable"),l=resolveComponent("a-tab-pane"),p=resolveComponent("a-radio"),s=resolveComponent("a-radio-group"),u=resolveComponent("ParamBody"),d=resolveComponent("a-tabs"),v=resolveComponent("DocDebuggerResult");return openBlock(),createElementBlock("div",null,[createVNode(t,{"addon-before":i.docInfoShow.method.toUpperCase(),value:e.docUrl,"onUpdate:value":_[0]||(_[0]=f=>e.docUrl=f),onSearch:e.sendRequest},{enterButton:withCtx(()=>[createVNode(r,{type:"primary",loading:e.requestLoading},{default:withCtx(()=>[_hoisted_1$1]),_:1},8,["loading"])]),_:1},8,["addon-before","value","onSearch"]),createVNode(d,{activeKey:e.activePage,"onUpdate:activeKey":_[2]||(_[2]=f=>e.activePage=f),closable:"",onTabClick:_[3]||(_[3]=()=>{}),style:{padding:"5px 10px 0"}},{default:withCtx(()=>[createVNode(l,{tab:"URL\u53C2\u6570",key:"urlParam",forceRender:""},{default:withCtx(()=>[createVNode(a,{ref:"urlParamRef",paramList:e.urlParamList},null,8,["paramList"])]),_:1}),i.docInfoShow.method!=="get"?(openBlock(),createBlock(l,{tab:"\u8BF7\u6C42\u53C2\u6570",key:"bodyParam",forceRender:""},{default:withCtx(()=>[createVNode(s,{value:e.bodyParamType,"onUpdate:value":_[1]||(_[1]=f=>e.bodyParamType=f),style:{"margin-bottom":"5px"}},{default:withCtx(()=>[createVNode(p,{value:"none"},{default:withCtx(()=>[_hoisted_2]),_:1}),createVNode(p,{value:"form"},{default:withCtx(()=>[_hoisted_3]),_:1}),createVNode(p,{value:"formUrlEncode"},{default:withCtx(()=>[_hoisted_4]),_:1}),createVNode(p,{value:"row"},{default:withCtx(()=>[_hoisted_5]),_:1}),createVNode(p,{value:"binary"},{default:withCtx(()=>[_hoisted_6]),_:1})]),_:1},8,["value"]),withDirectives(createBaseVNode("div",null,[createVNode(a,{ref:"formParamRef",paramList:e.formParamList,showType:""},null,8,["paramList"])],512),[[vShow,e.bodyParamType==="form"]]),withDirectives(createBaseVNode("div",null,[createVNode(a,{ref:"formEncodeParamRef",paramList:e.formEncodeParamList},null,8,["paramList"])],512),[[vShow,e.bodyParamType==="formUrlEncode"]]),withDirectives(createBaseVNode("div",null,[createVNode(u,{ref:"bodyParamRef",paramList:e.bodyRowParamList},null,8,["paramList"])],512),[[vShow,e.bodyParamType==="row"]])]),_:1})):createCommentVNode("",!0),createVNode(l,{tab:"Header\u53C2\u6570",key:"headerParam",forceRender:""},{default:withCtx(()=>[createVNode(a,{ref:"headerParamRef",paramList:e.headerParamList},null,8,["paramList"])]),_:1}),createVNode(l,{tab:"Cookie\u53C2\u6570",key:"cookieParam",forceRender:""},{default:withCtx(()=>[createVNode(a,{ref:"cookieParamRef",paramList:e.cookieParamList},null,8,["paramList"])]),_:1})]),_:1},8,["activeKey"]),createVNode(v,{result:e.requestResult},null,8,["result"])])}var DocDebugger=_export_sfc(_sfc_main$1,[["render",_sfc_render$1]]);const _sfc_main={components:{DocContent,DocDebugger},setup(){const c=useRoute(),_=useStore();let i=ref("doc"),e=ref([]),o=ref([]),n=ref({url:"",description:"",method:"",consumes:"",produces:""}),r=ref(!1),t=0,a;const l=()=>{let s=c.query.path+"."+c.query.method;if(Object.keys(_.state.swaggerTreePathMap).length<=0){console.log("\u6587\u6863\u5C1A\u672A\u52A0\u8F7D\uFF0C\u7B49\u5F85\u52A0\u8F7D\u5B8C\u6210"),a||(a=setInterval(()=>{if(r.value||t++>50){clearInterval(a);return}Object.keys(_.state.swaggerTreePathMap).length>0&&(console.log("\u6587\u6863\u5185\u5BB9\u6539\u53D8\uFF0C\u91CD\u65B0\u52A0\u8F7D\u6587\u6863"),l())},1e3));return}let u=_.state.swaggerTreePathMap[s];if(!u){message.error("\u6CA1\u6709\u627E\u5230\u5BF9\u5E94\u7684\u6587\u6863");return}r.value=!0,_.commit("addTableName",{key:c.fullPath,val:u.summary});let d="",v="";u.consumes&&u.consumes.length>0&&(d=u.consumes.join(" ")),u.produces&&u.produces.length>0&&(v=u.produces.join(" "));let f=mavonEditor.exports.markdownIt.render(u.description||u.summary||"");n.value={url:u.url,description:f,method:u.method||"",consumes:d,produces:v};let y=_.state.swaggerDefinitions;e.value=swaggerAnalysis.getRequestParamList(u.parameters,y),o.value=swaggerAnalysis.getResponseParamList(u.responses,y)};return onMounted(()=>{l()}),{docInfoShow:n,activePage:i,changePage:()=>{},isLoadSuccess:r,requestParamList:e,responseParamList:o}}},_hoisted_1=createBaseVNode("div",{style:{padding:"20px 0",height:"100px"}},null,-1);function _sfc_render(c,_,i,e,o,n){const r=resolveComponent("DocContent"),t=resolveComponent("a-tab-pane"),a=resolveComponent("DocDebugger"),l=resolveComponent("a-tabs"),p=resolveComponent("a-spin");return e.isLoadSuccess?(openBlock(),createBlock(l,{key:0,activeKey:e.activePage,"onUpdate:activeKey":_[0]||(_[0]=s=>e.activePage=s),closable:"",onTabClick:e.changePage,style:{padding:"5px 10px 0"}},{default:withCtx(()=>[createVNode(t,{tab:"\u63A5\u53E3\u8BF4\u660E",key:"doc"},{default:withCtx(()=>[createVNode(r,{docInfoShow:e.docInfoShow,requestParamList:e.requestParamList,responseParamList:e.responseParamList},null,8,["docInfoShow","requestParamList","responseParamList"])]),_:1}),createVNode(t,{tab:"\u5728\u7EBF\u8C03\u8BD5",key:"debug"},{default:withCtx(()=>[createVNode(a,{docInfoShow:e.docInfoShow,requestParamList:e.requestParamList,responseParamList:e.responseParamList},null,8,["docInfoShow","requestParamList","responseParamList"])]),_:1})]),_:1},8,["activeKey","onTabClick"])):(openBlock(),createBlock(p,{key:1,tip:"\u6587\u6863\u6570\u636E\u52A0\u8F7D\u4E2D..."},{default:withCtx(()=>[_hoisted_1]),_:1}))}var DocView=_export_sfc(_sfc_main,[["render",_sfc_render]]);export{DocView as default};
