"use strict";const Android={};Android[Symbol.for("internal")]={apiLevel:e=>{if(parseInt(Android_Other.getSdkNumber())<e)throw new Android.Errors.RequiresHigherAPILevelError(e)}},Android[Symbol.for("internal")].ClassExtender=class{_id;#api;#destroyed;constructor(e){this.#destroyed=!1,this.#api=window["Android_"+e],this._id=this.#api.instantiate()}_call(e,...r){if(this.#destroyed)throw new Android.Errors.InstanceAlreadyDestroyedError;Android[Symbol.for("internal")].ClassExtender.call({id:this._id,api:this.#api,method:e,args:r})}destroy(){this.#api.destroy(this._id),this.#destroyed=!0,this.onDestroy?.()}static call({id:e,api:r,method:t,args:o}){switch(r.call(e??null,t,o?JSON.stringify(o):null)){case 1:throw new Android.Errors.MethodNotFoundError(t);case 2:throw new Android.Errors.InstanceIdRequiredError;case 3:throw new Android.Errors.InstanceNotFoundError(this._id)}}},Android.Color=Object.freeze({TRANSPARENT:"#00000000",RED:"#ff0000",GREEN:"#00ff00",BLUE:"#0000ff"}),Android.Errors=Object.freeze({StringResourceNameNotFoundError:class extends Error{constructor(e){super(`String resource name ${e} not found`)}},RequiresHigherAPILevelError:class extends Error{constructor(e){super(`Requires API level ${e}. Current API level: `+Android.Other.sdk)}},InstanceAlreadyDestroyedError:class extends Error{constructor(){super("Instance has been destroyed")}},MethodNotFoundError:class extends Error{constructor(e){super(`Method ${e} not found`)}},InstanceIdRequiredError:class extends Error{constructor(){super("Instance id is required for instance methods")}},InstanceNotFoundError:class extends Error{constructor(e){super(`Instance id ${e} not found! Already destroyed?`)}},DynamicColorsUnavailableError:class extends Error{constructor(){super("Dynamic Color is unavailable")}}}),Android.Events={events:new Map,on:(e,r)=>{var t=Android.Events;t.events.has(e)||t.events.set(e,new Set),t.events.get(e).add(r)},off:(e,r)=>{var t=Android.Events;if(!t.events.has(e))throw new Error("No listener for event "+e);var o=t.events.get(e);if(!o.delete(r))throw new Error("Given listener is not registered");0===o.size&&t.events.delete(e)},once:(e,r)=>{const t=Android.Events;t.on(e,r)},emit(e,r){Android.Events.events.get(e)?.forEach(e=>e(...r))},removeListeners(e){Android.Events.events.delete(e)}},Android.Rect=class{constructor(e,r,t,o){this.left=e,this.top=r,this.right=t,this.bottom=o}get width(){return this.right-this.left}get height(){return this.bottom-this.top}get centerX(){return this.width/2+this.left}get centerY(){return this.height/2+this.top}},Android.RoundedCornerPosition=Object.freeze({TOP_LEFT:0,TOP_RIGHT:1,BOTTOM_RIGHT:2,BOTTOM_LEFT:3}),Android.VersionCode=Object.freeze({BASE:1,BASE_1_1:2,CUPCAKE:3,CUR_DEVELOPMENT:1e4,DONUT:4,ECLAIR:5,ECLAIR_0_1:6,ECLAIR_MR1:7,FROYO:8,GINGERBREAD:9,GINGERBREAD_MR1:10,HONEYCOMB:11,HONEYCOMB_MR1:12,HONEYCOMB_MR2:13,ICE_CREAM_SANDWICH:14,ICE_CREAM_SANDWICH_MR1:15,JELLY_BEAN:16,JELLY_BEAN_MR1:17,JELLY_BEAN_MR2:18,KITKAT:19,KITKAT_WATCH:20,LOLLIPOP:21,LOLLIPOP_MR1:22,M:23,N:24,N_MR1:25,O:26,O_MR1:27,P:28,Q:29,R:30,S:31,S_V2:32,TIRAMISU:33,UPSIDE_DOWN_CAKE:34}),Android.DynamicColors=Object.freeze({get available(){return Android.Other.sdk>=Android.VersionCode.S},get palette(){if(Android.DynamicColors.available)return JSON.parse(Android_DynamicColors.getPalette());throw new Android.Errors.DynamicColorsUnavailableError},applyCSS(e){var r,t=[];for(const n in e=e||Android.DynamicColors.palette)Object.hasOwnProperty.call(e,n)&&(r=e[n],t.push(`--color-${n.replace(/[A-Z]/g,e=>"-"+e.toLowerCase())}: ${r};`));var o=document.createElement("style");o.innerText=`:root { ${t.join(" ")} }`,document.head.appendChild(o)}}),Android.HTTP=Object.freeze({request(t,o={}){return new Promise(e=>{if(o.data&&"POST"!==o.method)throw new Error('data field can only be used with method set to "POST"');let r=new Uint32Array(5);window.crypto.getRandomValues(r),r=r.join(""),Android.Events.once("HTTP_Request_"+r,e),Android_HTTP.request(r,t,o.method??"GET",o.data??"",o.headers?JSON.stringify(o.headers):"{}")})}}),Android.Other=Object.freeze({get sdk(){return parseInt(Android_Other.getSdkNumber())},str(e){var r=Android_Other.getString("string"==typeof e?e:e[0]);if(r)return r;throw new Android.Errors.StringResourceNameNotFoundError(e)}}),Android.Share=Object.freeze({shareText(e,r){Android_Share.shareText(e,r??null)}}),Android.SystemUI=Object.freeze({setBehindSystemBars(e){Android_SystemUI.setBehindSystemBars(e)},setStatusBarColor(e,r){Android_SystemUI.setStatusBarColor(e,r)},setNavigationBarColor(e){Android_SystemUI.setNavigationBarColor(e)},get statusBarHeight(){return parseInt(Android_SystemUI.getStatusBarHeight())/window.devicePixelRatio},get displayCutout(){let e=Android_SystemUI.getDisplayCutout();return e?(e=JSON.parse(e)).map(e=>new Android.Rect(e.left/window.devicePixelRatio,e.top/window.devicePixelRatio,e.right/window.devicePixelRatio,e.bottom/window.devicePixelRatio)):null},get roundedCorners(){return Android[Symbol.for("internal")].apiLevel(Android.VersionCode.S),JSON.parse(Android_SystemUI.getRoundedCorners()).map(e=>({...e,centerX:e.centerX/window.devicePixelRatio,centerY:e.centerY/window.devicePixelRatio,radius:e.radius/window.devicePixelRatio}))}}),Android.Toast=class extends Android[Symbol.for("internal")].ClassExtender{#text;#duration;#callback;constructor(e,r=0){super("Toast"),this.text=e,this.duration=r,Android.Other.sdk>=Android.VersionCode.R&&(this.#callback=e=>{"hidden"===e&&this.destroy()},this.addCallback(this.#callback),this._call("enableCallback",this._id))}set text(e){this._call("setText",e),this.#text=e}get text(){return this.#text}set duration(e){this._call("setDuration",e),this.#duration=e}get duration(){return this.#duration}show(e){this._call("show"),(Android.Other.sdk<Android.VersionCode.R&&void 0===e||!0===e)&&this.destroy()}addCallback(e){Android.Events.on("Toast_Callback_"+this._id,e)}removeCallback(e){Android.Events.off("Toast_Callback_"+this._id,e)}cancel(){this._call("cancel")}onDestroy(){this.removeCallback(this.#callback)}static show(e){Android[Symbol.for("internal")].ClassExtender.call({api:Android_Toast,method:"showText",args:[e]})}static Duration=Object.freeze({SHORT:0,LONG:1})};