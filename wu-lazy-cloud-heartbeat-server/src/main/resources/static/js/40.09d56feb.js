"use strict";(self.webpackChunkwu_lazy_cloud_network=self.webpackChunkwu_lazy_cloud_network||[]).push([[40],{47040:(e,n,t)=>{t.r(n),t.d(n,{default:()=>Z});var r=t(83986),a=t(93540),u=(t(96869),t(79288),t(34284),t(66252)),c=t(3577),l=t(2262),o=function(e){return(0,u.dD)("data-v-296174ba"),e=e(),(0,u.Cn)(),e},s={class:"ve-card ve_card2"},i=o((function(){return(0,u._)("p",null,"用户数量",-1)})),p={class:"ve-card ve_card1"},f=o((function(){return(0,u._)("p",null,"数据库实例数量",-1)})),m={class:"percentage-value"},v=o((function(){return(0,u._)("span",{class:"percentage-label"},"JVM内存使用",-1)})),d={class:"percentage-label"},_={class:"percentage-value"},w=o((function(){return(0,u._)("span",{class:"percentage-label"},"当前线程CPU使用",-1)})),g={class:"percentage-label"},y={class:"percentage-value"},h=o((function(){return(0,u._)("span",{class:"percentage-label"},"当前系统CPU使用",-1)})),b={class:"percentage-label"},k={class:"percentage-value"},M={class:"percentage-value"},x={class:"percentage-value"},C={class:"percentage-value"};const W={__name:"Shortcuts",setup:function(e){var n=(0,l.iH)({menuNum:0,roleNum:0,userNum:0,instanceNum:0,schemaNum:0,tableNum:0,tableColumnNum:0,maxMemory:0,totalMemory:1,freeMemory:0,useMemory:0}),t=(0,l.iH)({cpuNum:"0",total:"0",sys:"0.00",used:"0.00",wait:"0.00",free:"0.00",name:"",arch:"",version:""}),o=[{color:"#f56c6c",percentage:100},{color:"#e6a23c",percentage:80},{color:"#5cb87a",percentage:60},{color:"#1989fa",percentage:40},{color:"#6f7ad3",percentage:20}],W=(0,l.iH)(),Z=function(){var e=(0,a.Z)((0,r.Z)().mark((function e(){var t,a,u;return(0,r.Z)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,VE_API.system.jvmRunTimeMemory();case 2:t=e.sent,a=t.code,u=t.data,0===a&&(console.log(u),n.value.maxMemory=u.maxMemory,n.value.totalMemory=u.totalMemory,n.value.freeMemory=u.freeMemory,n.value.useMemory=u.useMemory);case 6:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}(),N=function(){var e=(0,a.Z)((0,r.Z)().mark((function e(){var n,t,a;return(0,r.Z)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,VE_API.system.jvmGC();case 2:if(n=e.sent,t=n.code,a=n.data,0!==t){e.next=9;break}return console.log(a),e.next=9,Z();case 9:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}(),z=function(){var e=(0,a.Z)((0,r.Z)().mark((function e(){var n,a,u;return(0,r.Z)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,VE_API.system.jvmCPU();case 2:n=e.sent,a=n.code,u=n.data,0===a&&(t.value=u,console.log(u));case 6:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}();return(0,u.bv)((0,a.Z)((0,r.Z)().mark((function e(){return(0,r.Z)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,Z();case 2:return e.next=4,z();case 4:W.value=setTimeout((function(){Z(),z()}),3e3);case 5:case"end":return e.stop()}}),e)})))),(0,u.Ah)((function(){clearTimeout(W.value),W.value=""})),function(e,r){var a=(0,u.up)("user"),l=(0,u.up)("el-icon"),W=(0,u.up)("el-col"),Z=(0,u.up)("grape"),P=(0,u.up)("el-button"),U=(0,u.up)("el-progress"),I=(0,u.up)("el-card"),T=(0,u.up)("el-row");return(0,u.wg)(),(0,u.j4)(T,{gutter:20,style:{"padding-bottom":"10px"}},{default:(0,u.w5)((function(){return[(0,u.Wm)(W,{span:12},{default:(0,u.w5)((function(){return[(0,u._)("div",s,[(0,u.Wm)(l,null,{default:(0,u.w5)((function(){return[(0,u.Wm)(a)]})),_:1}),(0,u._)("div",null,[i,(0,u._)("span",null,(0,c.zw)(n.value.userNum),1)])])]})),_:1}),(0,u.Wm)(W,{span:12},{default:(0,u.w5)((function(){return[(0,u._)("div",p,[(0,u.Wm)(l,null,{default:(0,u.w5)((function(){return[(0,u.Wm)(Z)]})),_:1}),(0,u._)("div",null,[f,(0,u._)("span",null,(0,c.zw)(n.value.instanceNum),1)])])]})),_:1}),(0,u.Wm)(W,{style:{padding:"20px"}},{default:(0,u.w5)((function(){return[(0,u.Wm)(I,{shadow:"always",class:"box-card",style:{width:"100%"}},{default:(0,u.w5)((function(){return[(0,u.Wm)(U,{type:"circle",width:140,"stroke-width":15,color:o,percentage:100*Number(n.value.useMemory/n.value.totalMemory)},{default:(0,u.w5)((function(){return[(0,u._)("span",m,(0,c.zw)(n.value.useMemory+"/"+n.value.totalMemory)+"MB",1),v,(0,u._)("span",d,[(0,u.Wm)(P,{onClick:r[0]||(r[0]=function(e){return N()})},{default:(0,u.w5)((function(){return[(0,u.Uk)("GC")]})),_:1})])]})),_:1},8,["percentage"]),(0,u.Wm)(U,{type:"circle",width:140,"stroke-width":15,color:o,percentage:t.value.used},{default:(0,u.w5)((function(){return[(0,u._)("span",_,(0,c.zw)(t.value.used)+"%",1),w,(0,u._)("span",g,[(0,u.Wm)(P,{onClick:r[1]||(r[1]=function(e){return z()})},{default:(0,u.w5)((function(){return[(0,u.Uk)("cpu")]})),_:1})])]})),_:1},8,["percentage"]),(0,u.Wm)(U,{type:"circle",width:140,"stroke-width":15,color:o,percentage:t.value.sys},{default:(0,u.w5)((function(){return[(0,u._)("span",y,(0,c.zw)(t.value.sys)+"%",1),h,(0,u._)("span",b,[(0,u.Wm)(P,{onClick:r[2]||(r[2]=function(e){return z()})},{default:(0,u.w5)((function(){return[(0,u.Uk)("cpu")]})),_:1})])]})),_:1},8,["percentage"]),(0,u.Wm)(U,null,{default:(0,u.w5)((function(){return[(0,u._)("span",k,"CUP名称: "+(0,c.zw)(t.value.name),1),(0,u._)("span",M,"CUP架构: "+(0,c.zw)(t.value.arch),1),(0,u._)("span",x,"CUP版本: "+(0,c.zw)(t.value.version),1),(0,u._)("span",C,"CUP核心数: "+(0,c.zw)(t.value.cpuNum)+"核",1)]})),_:1})]})),_:1})]})),_:1})]})),_:1})}}};const Z=(0,t(48118).Z)(W,[["__scopeId","data-v-296174ba"]])},83127:e=>{e.exports="function"==typeof Bun&&Bun&&"string"==typeof Bun.version},8552:(e,n,t)=>{var r,a=t(19037),u=t(61735),c=t(69985),l=t(83127),o=t(30071),s=t(96004),i=t(21500),p=a.Function,f=/MSIE .\./.test(o)||l&&((r=a.Bun.version.split(".")).length<3||"0"===r[0]&&(r[1]<3||"3"===r[1]&&"0"===r[2]));e.exports=function(e,n){var t=n?2:1;return f?function(r,a){var l=i(arguments.length,1)>t,o=c(r)?r:p(r),f=l?s(arguments,t):[],m=l?function(){u(o,this,f)}:o;return n?e(m,a):e(m)}:e}},10209:(e,n,t)=>{var r=t(79989),a=t(19037),u=t(8552)(a.setInterval,!0);r({global:!0,bind:!0,forced:a.setInterval!==u},{setInterval:u})},83509:(e,n,t)=>{var r=t(79989),a=t(19037),u=t(8552)(a.setTimeout,!0);r({global:!0,bind:!0,forced:a.setTimeout!==u},{setTimeout:u})},96869:(e,n,t)=>{t(10209),t(83509)}}]);