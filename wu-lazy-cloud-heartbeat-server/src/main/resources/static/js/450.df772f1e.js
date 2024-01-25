"use strict";(self.webpackChunkwu_lazy_cloud_network=self.webpackChunkwu_lazy_cloud_network||[]).push([[450],{41450:(e,l,u)=>{u.r(l),u.d(l,{default:()=>s});var r=u(35145),a=u(83986),t=u(93540),n=(u(34284),u(66252)),o=u(2262);const s={__name:"UsersEditRoute",props:{showDialog:{type:Boolean,default:!0},title:{type:String,default:"添加"},rowData:{type:Object,default:null}},emits:["closeDialog"],setup:function(e,l){var u=l.emit,s={name:[{required:!0,message:"请输入用户名",trigger:"blur"}],userName:[{required:!0,message:"请输入账户",trigger:"blur"}],password:[{required:!0,message:"请输入密码",trigger:"blur"}],role:[{required:!0,message:"请选择角色",trigger:"change"}]},d=e,i=u,c=(0,o.BK)(d),f=c.title,m=c.rowData,p=function(){i("closeDialog",!1)},w=(0,o.iH)(null),v=(0,o.qj)({name:"",userName:"",password:"",role:"",status:1}),b=(0,o.BK)(v),_=b.userName,g=b.name,k=b.password,V=b.role,h=b.status,y=(0,o.iH)([]);m.value&&(g.value=m.value.name,_.value=m.value.userName,k.value=m.value.password,V.value=m.value.role,h.value=m.value.status);var U=function(){var e=(0,t.Z)((0,a.Z)().mark((function e(){var l,u,r,t;return(0,a.Z)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,VE_API.system.roleList({page:1,size:10},{Global:!1});case 2:l=e.sent,u=l.code,r=l.data,"0"===u&&(t=r.list,y.value=t);case 6:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}();U();return function(e,l){var u=(0,n.up)("el-input"),d=(0,n.up)("el-form-item"),i=(0,n.up)("el-option"),c=(0,n.up)("el-select"),b=(0,n.up)("el-radio-button"),U=(0,n.up)("el-radio-group"),W=(0,n.up)("el-button"),q=(0,n.up)("el-form");return(0,n.wg)(),(0,n.j4)(q,{model:v,ref_key:"formRef",ref:w,rules:s,"label-width":"80px",inline:!1},{default:(0,n.w5)((function(){return[(0,n.Wm)(d,{label:"账号",prop:"name"},{default:(0,n.w5)((function(){return[(0,n.Wm)(u,{modelValue:(0,o.SU)(g),"onUpdate:modelValue":l[0]||(l[0]=function(e){return(0,o.dq)(g)?g.value=e:null}),placeholder:"",clearable:""},null,8,["modelValue"])]})),_:1}),(0,n.Wm)(d,{label:"用户名",prop:"userName"},{default:(0,n.w5)((function(){return[(0,n.Wm)(u,{modelValue:(0,o.SU)(_),"onUpdate:modelValue":l[1]||(l[1]=function(e){return(0,o.dq)(_)?_.value=e:null}),placeholder:"",clearable:""},null,8,["modelValue"])]})),_:1}),(0,n.Wm)(d,{label:"密码",prop:"password"},{default:(0,n.w5)((function(){return[(0,n.Wm)(u,{modelValue:(0,o.SU)(k),"onUpdate:modelValue":l[2]||(l[2]=function(e){return(0,o.dq)(k)?k.value=e:null}),"show-password":"",placeholder:"",clearable:""},null,8,["modelValue"])]})),_:1}),(0,n.Wm)(d,{label:"角色",prop:"role"},{default:(0,n.w5)((function(){return[(0,n.Wm)(c,{style:{width:"100%"},modelValue:(0,o.SU)(V),"onUpdate:modelValue":l[3]||(l[3]=function(e){return(0,o.dq)(V)?V.value=e:null}),placeholder:"",clearable:""},{default:(0,n.w5)((function(){return[((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(y.value,(function(e){return(0,n.wg)(),(0,n.j4)(i,{key:e.id,label:e.name,value:e.id,disabled:0==e.status},null,8,["label","value","disabled"])})),128))]})),_:1},8,["modelValue"])]})),_:1}),(0,n.Wm)(d,{label:"状态"},{default:(0,n.w5)((function(){return[(0,n.Wm)(U,{modelValue:(0,o.SU)(h),"onUpdate:modelValue":l[4]||(l[4]=function(e){return(0,o.dq)(h)?h.value=e:null})},{default:(0,n.w5)((function(){return[(0,n.Wm)(b,{label:1},{default:(0,n.w5)((function(){return[(0,n.Uk)("启用")]})),_:1}),(0,n.Wm)(b,{label:0},{default:(0,n.w5)((function(){return[(0,n.Uk)("停用")]})),_:1})]})),_:1},8,["modelValue"])]})),_:1}),(0,n.Wm)(d,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(W,{onClick:l[5]||(l[5]=function(e){return p()})},{default:(0,n.w5)((function(){return[(0,n.Uk)("取消")]})),_:1}),(0,n.Wm)(W,{type:"primary",onClick:l[6]||(l[6]=function(e){w.value.validate(function(){var e=(0,t.Z)((0,a.Z)().mark((function e(l){var u;return(0,a.Z)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(!l){e.next=14;break}if("添加"!==f.value){e.next=7;break}return e.next=4,VE_API.system.userAdd(v);case 4:u=e.sent,e.next=10;break;case 7:return e.next=9,VE_API.system.userEdit((0,r.Z)({id:m.value.id},v));case 9:u=e.sent;case 10:"0"===u.code&&p(),e.next=16;break;case 14:return console.log("error submit!!"),e.abrupt("return",!1);case 16:case"end":return e.stop()}}),e)})));return function(l){return e.apply(this,arguments)}}())})},{default:(0,n.w5)((function(){return[(0,n.Uk)("确定")]})),_:1})]})),_:1})]})),_:1},8,["model"])}}}}}]);