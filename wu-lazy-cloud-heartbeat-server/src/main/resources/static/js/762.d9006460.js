"use strict";(self.webpackChunkwu_lazy_cloud_network=self.webpackChunkwu_lazy_cloud_network||[]).push([[762,289],{29864:(e,n,t)=>{t.r(n),t.d(n,{default:()=>_});var r=t(83986),a=t(93540),u=(t(70560),t(66252)),o=t(2262),s=t(49963),l=t(32393),c=t(85729),i=t(33907),d=t(22201),p={class:"ve_container"},f=function(e){return(0,u.dD)("data-v-596ed213"),e=e(),(0,u.Cn)(),e}((function(){return(0,u._)("h1",null,"Lazy-UI",-1)}));const m={__name:"Login",setup:function(e){var n={username:[{required:!0,message:"请输入用户名",trigger:"blur"}],password:[{required:!0,message:"请输入密码",trigger:"blur"}]},t=(0,i.oR)(),m=(0,d.tv)(),_=(0,o.qj)({username:"",password:""}),w=(0,o.BK)(_),v=w.username,k=w.password,h=(0,o.iH)(null),y=(0,o.iH)(!1);sessionStorage.clear(),t.dispatch("app/".concat(l.L4),""),m.options.isAddDynamicMenuRoutes=!1;var g=function(){h.value.validate(function(){var e=(0,a.Z)((0,r.Z)().mark((function e(n){var a,u;return(0,r.Z)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(!n){e.next=24;break}return e.next=3,VE_API.system.login(_);case 3:if(0!==(a=e.sent).code){e.next=22;break}return e.next=7,t.dispatch("app/".concat(l.L4),a.data);case 7:return e.next=9,t.dispatch("app/".concat(l.Wy),a.data.accessToken);case 9:return e.next=11,t.dispatch("app/".concat(l.aR),a.data.refreshToken);case 11:return e.next=13,t.dispatch("app/".concat(l.TR),_.username);case 13:return e.next=15,VE_API.system.resolvingUser({accessToken:a.data.accessToken});case 15:if(u=e.sent,console.log(u),!u||!u.id){e.next=20;break}return e.next=20,t.dispatch("app/".concat(l.mg),u.id);case 20:y.value=!0,m.push({name:"AppMain"});case 22:e.next=25;break;case 24:return e.abrupt("return");case 25:case"end":return e.stop()}}),e)})));return function(n){return e.apply(this,arguments)}}())};return function(e,t){var r=(0,u.up)("router-link"),a=(0,u.up)("Avatar"),l=(0,u.up)("el-icon"),i=(0,u.up)("el-input"),d=(0,u.up)("el-form-item"),m=(0,u.up)("Key"),w=(0,u.up)("el-button"),b=(0,u.up)("el-form"),W=(0,u.up)("el-card");return(0,u.wg)(),(0,u.j4)((0,o.SU)(c.default),null,{default:(0,u.w5)((function(){return[(0,u._)("div",p,[(0,u.Wm)(W,{"body-style":{background:"rgba(0,0,0,0.15)"}},{default:(0,u.w5)((function(){return[(0,u.Wm)(r,{style:{float:"right"},to:{path:"register"}},{default:(0,u.w5)((function(){return[(0,u.Uk)("注册")]})),_:1}),f,(0,u.Wm)(s.uT,{name:"el-fade-in-linear"},{default:(0,u.w5)((function(){return[(0,u.wy)((0,u.Wm)(b,{model:_,rules:n,class:"ve_form",ref_key:"ref_form",ref:h,inline:!1,onKeyup:(0,s.D2)(g,["enter"])},{default:(0,u.w5)((function(){return[(0,u.Wm)(d,{prop:"username"},{default:(0,u.w5)((function(){return[(0,u.Wm)(i,{modelValue:(0,o.SU)(v),"onUpdate:modelValue":t[0]||(t[0]=function(e){return(0,o.dq)(v)?v.value=e:null}),modelModifiers:{trim:!0},placeholder:"用户名"},{prepend:(0,u.w5)((function(){return[(0,u.Wm)(l,{size:20},{default:(0,u.w5)((function(){return[(0,u.Wm)(a)]})),_:1})]})),_:1},8,["modelValue"])]})),_:1}),(0,u.Wm)(d,{prop:"password"},{default:(0,u.w5)((function(){return[(0,u.Wm)(i,{modelValue:(0,o.SU)(k),"onUpdate:modelValue":t[1]||(t[1]=function(e){return(0,o.dq)(k)?k.value=e:null}),modelModifiers:{trim:!0},"show-password":"",placeholder:"密码"},{prepend:(0,u.w5)((function(){return[(0,u.Wm)(l,{size:20},{default:(0,u.w5)((function(){return[(0,u.Wm)(m)]})),_:1})]})),_:1},8,["modelValue"])]})),_:1}),(0,u.Wm)(d,null,{default:(0,u.w5)((function(){return[(0,u.Wm)(w,{class:"ve_submit",type:"primary",onClick:g},{default:(0,u.w5)((function(){return[(0,u.Uk)(" 登录 ")]})),_:1})]})),_:1})]})),_:1},8,["model"]),[[s.F8,!y.value]])]})),_:1})]})),_:1},8,["body-style"])])]})),_:1})}}};const _=(0,t(48118).Z)(m,[["__scopeId","data-v-596ed213"]])}}]);