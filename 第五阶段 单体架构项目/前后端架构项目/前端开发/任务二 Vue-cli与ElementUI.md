# 任务二 Vue-cli与ElementUI

# 1.Vue-cli

## 1.1 什么是vue-cli

**可以快速的搭建前台项目环境**

## 1.2 安装Vue-cli

要先安装node.js和npm

### 1.2.1 安装Node.js

安装了node.js才有使用npm,才能安装vue-cli

**在前后端分离项目中，前端项目是可以独立运行的，node.js就是前端JavaScript运行环境**

node -v           显示当前node的版本

### 1.2.2 安装npm	

​	npm是**对node包进行管理，管理js库，打包js文件的**

**node.js已经集成了npm**

* npm -v     查看当前npm版本
* npm config ls       查看包管理路径，**就是npm从远程下载的JS包存放的路径**
* 修改包管理路径
* npm config set prefix "F:\software\nodejs_package\npm_modules"
  npm config set cache "F:\software\nodejs_package\npm_cache"
* 配置NPM环境变量
  * npm  config  get  prefix       查看npm的全局路径
* 配置PATH环境变量
  * **key=NODE_HOME,value=F:\software\nodejs_package**
  * path中添加%NODE_HOME%\npm_modules
* 安装cnpm 淘宝镜像来下载js包，提高下载速度
* 安装        npm install -g cnpm --registry=https://registry.npm.taobao.org
* 查看cnpm版本  cnpm -v

### 1.2.3 安装3.x版本以上的vue-cli

* 安装
  * npm install -g @vue/cli     安装最新版本的vue-cli
* 输入vue -V  查看vue-cli版本
  * vue -V



## 1.3 快速构建Vue 项目

### 1.3.1 使用vue-cli快速构建项目

1. 创建一个文件夹用来放项目
2. 命令行进入文件夹
3. 执行命令

```
1.基于交互式命令方式,创建项目
//文件名 不支持驼峰（含大写字母）使用短横线方式
vue create my-project
```

4. 选择自定义安装
5. 选择要安装的组件
6. 输入n，选择hash模式
7. 选择项目配置文件单独存放
8. 保存模版
9. 安装完成，执行命令
10. 进入项目目录           cd  my-project
11. 启动项目                  npm  run serve
12. 访问项目
13. 停止项目 关闭命令行就可以

### 1.3.2  导入Vue项目到vscode

### 1.3.3 项目结构介绍

```
|--- my-project 项目名称
|--- node_modules 存放依赖包的目录
|--- public 静态资源管理目录
|--- src 组件源码目录(我们写的代码)
|--- assets 存放静态图片资源(CSS也可以放在这里)
|--- components 存放各种组件(一个页面可以看做一个组件)，各个组件联系在一起组成一个
完整的项目
|--- router 存放了项目路由文件
|--- views 放置的为公共组件(主要还是各个主要页面)
|--- App.vue app.vue可以当做是网站首页，是一个vue项目的主组件，页面入口文件
|--- main.js 打包运行的入口文件，引入了vue模块和app.vue组件以及路由route
  |--- babel.config.js babel配置文件, 对源代码进行转码(把es6=>es5)
  |--- package.json 项目及工具的依赖配置文件
  |--- paxkage-lock.json 依赖配置文件
  |--- README.md 项目说明
```

### 1.3.4 Vue脚手架自定义配置

#### 1.3.4.1 package.js介绍

​	每个项目的根目录下面有的 package.js文件，定义了这个项目所需要的各种模块，以及项目的配置信息。npm install 命令根据这个配置文件，自动下载所需的模块，也就是项目所需的运行和开发环境

```json
{
 //1.项目基本信息
 "name": "project3",
 "version": "0.1.0",
 "private": true,
  
 //2.指定运行脚本命令
 "scripts": {
  "serve": "vue-cli-service serve",
  "build": "vue-cli-service build"
},
  
 //4.生产环境所依赖模块的版本
 "dependencies": {
  "core-js": "^3.6.5",
  "vue": "^2.6.11",
  "vue-router": "^3.2.0"
},
 //5.本地环境开发所依赖的版本
 "devDependencies": {
  "@vue/cli-plugin-babel": "~4.4.0",
  "@vue/cli-plugin-router": "~4.4.0",
  "@vue/cli-service": "~4.4.0",
  "vue-template-compiler": "^2.6.11"
}
}
```

#### 1.3.4.2 通过package.json 配置项目

**配置内容采用JSON格式，所有的内容都用双引号包裹**

可以在package.json末端添加：

```json
"vue":{
 "devServer":{
   "port":"8888",
   "open":true
 }
}
```

* 配置说明：该配置设置打包时服务器相关的信息
  * port：项目运行的访问端口
  * open true：项目运行起来自动打开浏览器
* 启动项目
  * 在终端打开，输入命令  npm  run  serve，运行后发现端口号改为8888，并且在打包完成后自动打开浏览器

---

注意：不推荐在package.json里自定义配置，因为package.json是项目的主配置文件

可以将Vue脚手架相关的配置单独定义到**vue.config.js**配置文件中

#### 1.3.4.3 自定义的单独配置文件配置项目

1. 在项目根目录下创建vue.config.js
2. 删除package.json中新添加的配置
3. 在vue.config.js文件中进行相关的配置

```json
module.exports = {
 devServer:{
 open:true
   port:8889
 }
}
```



### 1.3.5 Vue组件化开发

#### 1.3.5.1 组件化开发

组件化是Vue的精髓，**Vue项目就是由一个个的组件构成的**。主要工作就是开发组件

#### 1.3.5.2 组件介绍

1）用vue-cli 脚手架搭建的项目。每一个***.vue 文件都是一个组件，是一个自定义的文件类型**，如App.vue 就是整个项目的根组件

2）常见的组件：

* **页面级别的组件**
  * 页面级别的组件，是views目录下的.vue组件，是组成整个项目的各个主要页面
* **业务上可复用的基础组件**
  * 是在业务中可以被各个页面复用的组件，写到**components**目录下，然后在各个页面中import导入

3）组件的组成部分

* **template**：组件的HTML部分。就是视图
* **script**：组件的js脚本
* **style**：组件的css样式

```vue
<!-- 1.template 代表html结构, template中的内容必须有且只有一个根元素
编写页面静态部分 就是 view部分 -->
<template>
  <div>
   测试页面...
  </div>
</template>
<!-- 2.编写vue.js代码 -->
<script>
  //可以导入其组件
  // import Header from '../components/header.vue'
 
  //默认写法, 输出该组件
  export default {
  name:"Home", // 组件名称，用于以后路由跳转
    data() {// 当前组件中需要使用的数据
      return {}
   },
   methods: {}
 }
</script>
<!-- 编写当前组件的样式代码 -->
<style scoped>
/* 页面样式 加上scoped 表示样式就只在当前组件有效*/
</style>
```



## 1.4 使用vue-cli脚手架搭建项目的运行流程

项目启动的时候会首先加载package.json的依赖，然后进入项目入口文件main.js

### 1.4.1 main.js

1. 项目运行 时加载的入口文件   main.js

```js
// 1.项目运行会加载入口文件 main.js

// 在vue中使用 import 变量名 from 文件路径
import Vue from 'vue'
import App from './App.vue'	// 主组件
import router from './router' // 路由

// 关闭启动提示
Vue.config.productionTip = false

// 创建Vue实例
new Vue({
  router,	// 为整个项目 添加路由
  render: h => h(App)  // 生成模版	App=App.vue
}).$mount('#app')	// 挂载了App.vue 中 id为app的区域
```

### 1.4.2 App.vue 

2. App.vue 是vue项目的主组件，是页面入口文件，所有页面都是在App.vue下进行切换的

```vue
// 2.App.vue 是项目的主组件(整个项目的其他页面都是在主组件中切换)
// App.vue 中 HTML代码部分
<template>
  <div id="app">	// 挂载的区域
    <div id="nav">
		// 路由的导航链接
		// 1.to="/" 项目的根路径，跳转的是首页
      <router-link to="/">Home</router-link> |
		
		// 2.to="/about" 点击 跳转到 about组件
      <router-link to="/about">About</router-link>
    </div>
    <router-view/>
  </div>
</template>

<style></style>
```

### 1.4.3 router 路由

3. 看一下具体的路由配置

```js
// 3.查看路由文件的具体配置

// 引入所需文件
import Vue from 'vue'
import VueRouter from 'vue-router'

// 引入了 Home.vue组件
import Home from '../views/Home.vue'

// 使用了路由功能
Vue.use(VueRouter)

// 创建路由集合，元素就是一个一个的路由规则
  const routes = [
  {
    path: '/',	// 路径
    name: 'Home',	// 路由名
    component: Home	// Home = Home.vue ，表示 路由导航到的组件
  },
  
  {
    path: '/about',	// 路径
    name: 'About',	// 名称
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
	// 表示当前路由路径指向了 About.vue组件
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

// 创建路由管理器对象
const router = new VueRouter({
  routes
})

// export 用来表示导出模块
export default router
```

### 1.4.4 Home.vue 组件

1. 默认访问Home.vue 首页

```vue
// 4.查看默认访问的Home.vue

// 视图部分
<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
	
    <HelloWorld msg="Welcome to Your Vue.js App"/>
  </div>
</template>

<script>
// @ is an alias to /src
// @符号表示 src 目录
import HelloWorld from '@/components/HelloWorld.vue'

// 导出
export default {
  name: 'Home',
  components: {
    HelloWorld
  }
}
</script>
```

HelloWorld.vue 组件页面

```vue
<template>
 <div class="hello">
  <h1>{{ msg }}</h1>
  <p>
  For a guide and recipes on how to configure / customize this project,<br>
  check out the
   <a href="https://cli.vuejs.org" target="_blank" rel="noopener">vue-cli
documentation</a>.
  </p>
  <h3>Installed CLI Plugins</h3>
 </div>
</template>
<script>
export default {
 name: 'HelloWorld',
 props: {
  msg: String
}
}
</script
```



## 1.5 组件的使用案列，自定义组件使用

### 1.5.1 创建Header.vue组件

1. 在components 目录下创建Header.vue
2. 编写Header.vue

```vue
<template>
    <div class="header">{{msg}}</div>
</template>

<script>
// JS 部分
export default {
    name:"Header",   // 组件的名称
    data() {    // data函数
        return {
            msg:"这是一个Header组件"
        }
    },
}
</script>

// scoped 表示当前的样式只作用于当前组件中的 template 视图
<style scoped>
.header {
    height: 100px;
    line-height: 100px;
    background-color: #eee;
    text-align: center;
    color: blue
}
</style>
```

### 1.5.2 引入Header组件

修改Home.vue

```vue
<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <!-- <HelloWorld msg="Welcome to Your Vue.js App"/> -->

    <Header/>
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'
import Header from '@/components/Header.vue'

export default {
  name: 'Home',
  components: {
    // HelloWorld
    Header
  }
}
</script>
```

### 1.5.3 组件的传参

* props:组件的属性，表示组件可以接受参数
* 可以通过定义props属性来给组件传参

```vue
<template>
    <div class="header">{{msg}}</div>
</template>

<script>
// JS 部分
export default {
    name:"Header",   // 组件的名称
    // data() {    // data函数
    //     return {
    //         msg:"这是一个Header组件"
    //     }
    // },
    // 参数传递
    // 当前组件可以通过msg属性接收值
    props:['msg']
}
</script>

// scoped 表示当前的样式只作用于当前组件中的 template 视图
<style scoped>
.header {
    height: 100px;
    line-height: 100px;
    background-color: #eee;
    text-align: center;
    color: blue
}
</style>
```



# 2. Element-UI

## 2.1 Element-UI介绍

element-ui  饿了么前端出品的Vue.js后台组件库，方便页面布局的

## 2.2 Element-UI使用

### 2.2.1 命令行安装

1.  创建一个项目
2. 当前项目打开终端，安装依赖包，执行命令           npm i element-ui -S
3. 打开**mian.js**,导入Element-UI

```js
//导入组件库
import ElementUI from 'element-ui'
//导入组件相关样式
import 'element-ui/lib/theme-chalk/index.css'
//配置Vue插件 将El安装到Vue上
Vue.use(ElementUI);
```

4. **复制Element 按钮样式，到app.vue文件的 template下**

```vue
<template>
 <div id="app">
  <!-- 测试elementUI -->
  <el-row>
   <el-button>默认按钮</el-button>
   <el-button type="primary">主要按钮</el-button>
   <el-button type="success">成功按钮</el-button>
   <el-button type="info">信息按钮</el-button>
   <el-button type="warning">警告按钮</el-button>
   <el-button type="danger">危险按钮</el-button>
  </el-row>
  <div id="nav">
   <router-link to="/">Home</router-link>|
   <router-link to="/about">About</router-link>
  </div>
  <router-view />
 </div>
</template>
```

5. 启动项目 npm run serve ，测试element-ui

### 2.2.2 Vue-cli 工程改造

1. 删除components目录下的HelloWorld.vue
2. 删除App.vue的部分内容
3. 删除router文件下的路由文件 index.js
4. 删除views目录下的About.vue与Home.vue

### 2.2.3 安装axios

1. npm安装：使用npm下载axios包           npm  i  axios
2. 在mian.js文件中导入axios

```js
//引入axios
import axios from 'axios'
//Vue对象使用axios
Vue.prototype.axios = axios;
```



## 2.3 用户登录界面制作

### 2.3.1 Dialog对话框组件

**用Dialog制作一个登陆弹窗，自定义内容**

```vue
<el-dialog title="收货地址" :visible.sync="dialogFormVisible">
 <el-form :model="form">
  <el-form-item label="活动名称" :label-width="formLabelWidth">
   <el-input v-model="form.name" autocomplete="off"></el-input>
  </el-form-item>
  <el-form-item label="活动区域" :label-width="formLabelWidth">
   <el-select v-model="form.region" placeholder="请选择活动区域">
    <el-option label="区域一" value="shanghai"></el-option>
    <el-option label="区域二" value="beijing"></el-option>
   </el-select>
  </el-form-item>
 </el-form>
 <div slot="footer" class="dialog-footer">
  <el-button @click="dialogFormVisible = false">取 消</el-button>
  <el-button type="primary" @click="dialogFormVisible = false">确 定</el-
button>
 </div>
</el-dialog>
```

### 2.3.2 创建login.vue 组件

1. 在components 下创建login.vue
2. 将Diglog组件的内容，拷贝到login.vue 进行修改

```vue
<template>
    <el-dialog title="用户登录" :visible.sync="dialogFormVisible">

  <el-form>
    <el-form-item label="用户名称" :label-width="formLabelWidth">
      <el-input autocomplete="off"></el-input>
    </el-form-item>

    <el-form-item label="用户密码" :label-width="formLabelWidth">
      <el-input autocomplete="off"></el-input>
    </el-form-item>
  </el-form>

  <div slot="footer" class="dialog-footer">
    <el-button type="primary" @click="dialogFormVisible = false">登录</el-button>
  </div>
</el-dialog>
</template>

<script>
  export default {
    data() {
      return {
        dialogFormVisible: true,
        formLabelWidth: '120px'
      };
    }
  };
</script>

<style scoped>

</style>
```

### 2.3.3 给组件配置路由

```js
import Vue from 'vue';
import VueRouter from 'vue-router';

// 导入Login.vue组件
import Login from '@components/Login'

Vue.use(VueRouter);

  const routes = [
	//访问 /,也跳转到login
{
  path:'/',
  redirect:'login' //重定向到login
},
      
    // 登录路由
    {
      path:"/login",
      name:"login",
      component:Login
    }
]

const router = new VueRouter({
  routes
})

export default router
```

### 2.3.4 修改App.vue

```vue
<template>
  <div id="app">
    
    <!-- 根据访问路径，渲染路径匹配到的组件 -->
    <router-view></router-view>
  </div>
</template>

<style></style>
```

### 2.3.5 编写登录功能

1. 去掉关闭按钮，添加属性：**show-close="false"**

```vue
<el-dialog title="登录" :show-close="false" :visible.sync="dialogFormVisible">
```

2. 修改登录触发事件

```vue
<el-button type="primary" @click="login">登录</el-button>
```

3. 进行双向数据绑定，当用户在页面中输入数据时data里数据也会变化

* data 中定义数据

```js
data() {
  return {
   formLabelWidth: "120px", //宽度
   dialogFormVisible: true, //是否关闭对话框
   user: { username: "", password: "" }, //登录数据
 };
},
```

* 使用 v-model，将视图与模型双向绑定

```vue
<el-form>
   <el-form-item label="用户名称" :label-width="formLabelWidth">
    <el-input v-model="user.username" autocomplete="off"></el-input>
   </el-form-item>
   <el-form-item label="用户密码" :label-width="formLabelWidth">
    <el-input v-model="user.password" autocomplete="off"></el-input>
   </el-form-item>
  </el-form>
```

4. 编写login方法

```js
methods: {
      // 登录的方法
      login(){
        
        // 定义常量保存url
        const url = "";

        // 发送请求
        this.axios(url,{
          // 携带的参数
          param:{
            username:this.user.username,
            password:this.user.password
          }
        }).then(function(resp){
          // 请求成功
          console.log(resp);
          alert("登录成功")
        }).catch((error) => {
        	
        });
      }
    }
```

### 2.3.6 Postman搭建伪服务器

* Mock server就是一个模拟的服务器，可以使用Mock server模拟后台接口，对请求进行响应
* 在前后端分离的开发中，前端利用mockserver模拟出对应接口，拿到返回数据调试，无需等后端开发人员工作
* postman模拟出一个server步骤：
  * 使用postman模拟出一个server伪服务
  * 填写访问路径url和响应数据
  * 修改前台的请求url

```js
<template>
    <el-dialog :show-close="false" title="用户登录" :visible.sync="dialogFormVisible">

  <el-form>
    <el-form-item label="用户名称" :label-width="formLabelWidth">
      <el-input v-model="user.username" autocomplete="off"></el-input>
    </el-form-item>

    <el-form-item label="用户密码" :label-width="formLabelWidth">
      <el-input v-model="user.password" autocomplete="off"></el-input>
    </el-form-item>
  </el-form>

  <div slot="footer" class="dialog-footer">
    <el-button type="primary" @click="login">登录</el-button>
  </div>
</el-dialog>
</template>

<script>
  export default {
    data() {
      return {
        dialogFormVisible: true,
        formLabelWidth: '120px',
        user:{username:"",password:""}
      };
    },
    methods: {
      // 登录的方法
      login(){
        
        // 定义常量保存url
        const url = "https://d1eca569-371d-46c7-badb-dc6db990d813.mock.pstmn.io/login";

        
        // 发送请求
        this.axios(url,{
          // 携带的参数
          param:{
            username:this.user.username,
            password:this.user.password
          }
        }).then((resp) => {
          // 请求成功
          console.log(resp);
          alert("登录成功");

          // 成功就关闭 对话框	 使用es6箭头函数语法可以使用this了
          this.dialogFormVisible=false;
        }).catch((error) => {
          // 登录失败 提供消息提示
          this.$message.error('对不起，登录失败');
        });
      },
    }
  }
</script>

<style scoped>

</style>
```

### 2.3.7 登录成功后页面跳转

* 通过$router访问路由实例，调用**this.$router.push**，可以导航到不同的url
* 在js中设置页面跳转，常用**this.$router.push**

```js
methods: {
      // 登录的方法
      login(){
        
        // 定义常量保存url
        const url = "https://d1eca569-371d-46c7-badb-dc6db990d813.mock.pstmn.io/login";

        
        // 发送请求
        this.axios(url,{
          // 携带的参数
          param:{
            username:this.user.username,
            password:this.user.password
          }
        }).then((resp) => {
          // 请求成功
          console.log(resp);
          alert("登录成功");

          // 成功就关闭 对话框
          this.dialogFormVisible=false;

          // 进行页面跳转，跳转到首页，在前端进行页面跳转 必须使用路由，使用$router对象中的push方法
          this.$router.push("index");
        }).catch((error) => {
          // 登录失败 提供消息提示
          this.$message.error('对不起，登录失败');
        });
      },
    }
```



## 2.4 首页布局组件

### 2.4.1 创建index.vue

```vue
<template>
    <el-button type="danger">布局页面</el-button>    
</template>

<script>
export default {
    
}
</script>

<style scoped>

</style>
```

### 2.4.2 创建完组件后去配置路由

router 目录下的 index.js路由文件

```js
// 导入布局组件
import Index from '@/components/Index'

// 登录路由
    {
      path:"/login",
      name:"login",
      component:Login
    },
```

### 2.4.3 布局容器

Container 布局容器 ,是用于布局的容器组件，方便快速搭建页面的基本结构：

1. 在文档中找到布局容器代码，复制到index.vue

```vue
<el-container>
 <el-header>Header</el-header>
 <el-container>
  <el-aside width="200px">Aside</el-aside>
  <el-main>Main</el-main>
 </el-container>
</el-container>
<style scoped>
.el-container {
 height: 720px;
}
.el-header,
.el-footer {
 background-color: #b3c0d1;
 color: #333;
 text-align: center;
 line-height: 60px;
}
.el-aside {
 background-color: #d3dce6;
 color: #333;
 text-align: center;
 line-height: 200px;
}
.el-main {
 background-color: #e9eef3;
 color: #333;
 text-align: center;
 line-height: 30px;
}
</style>
```

2. 拷贝导航菜单代码，进行修改

```vue
<template>
    <div>
        <el-container>
  <el-header>后台管理</el-header>
  <el-container>
      <!-- 侧边栏 -->
    <el-aside width="200px">
        
        <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      background-color="#D3DCE6">
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>导航菜单</span>
        </template>
        <el-menu-item-group>
          
          <el-menu-item index="1-1"><i class="el-icon-menu"></i>课程管理</el-menu-item>
          
        </el-menu-item-group>
      </el-submenu>

    </el-menu>
    </el-aside>

    <!-- 主要区域 -->
    <el-main>Main</el-main>
  </el-container>
</el-container>
</div>  
</template>

<script>
export default {
    
}
</script>

<style scoped>
.el-container{
    height: 725px;
}
.el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }
  
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 160px;
  }
</style>
```

## 2.5 课程列表组件

**当我们点击导航菜单中的课程管理时**，要去切换**main区域中的内容，显示课程列表信息。切换课程信息的组件**

### 2.5.1 编写 Course.vue         课程管理组件

```vue
<template>
    <el-button type="danger">课程信息</el-button>
</template>

<script>
export default {

}
</script>

<style scoped>

</style>
```

### 2.5.2 配置路由

1. 在index.js路由文件中，为布局路由添加**children**属性表示子路由

```js
 //引入课程组件
 import Course from "@/components/Course.vue"
 //布局路由
{
  path: "/index",
  name: "index",
  component: Index,
  //添加子路由,使用 children属性 来表示子路由
  children:[
   //课程信息子路由
  {
    path:"/course",
    name:"course",
    component:Course
  }
 ]
},
```

2. 修改 Index.vue 组件中的 导航菜单属性

   **router**表示是否使用vue-router的模式，启用该模式会在激活导航时以index作为path进行路由跳转

```vue
el-menu中 添加一个 router属性
<el-menu default-active="2" class="el-menu-vertical-demo" background-
color="#d3dce6" router >
```

3. 为index属性指定路由

```vue
<el-menu-item-group>
  <!-- 修改 index的路由地址 -->
  <el-menu-item index="/course">
    <i class="el-icon-menu"></i>课程管理
  </el-menu-item>
</el-menu-item-group>
```

4. 设置路由的出口，导航到course.vue组件后将组件渲染到main 主要区域

```vue
<!-- 主要区域 -->
<el-main>
  <router-view></router-view>
</el-main>
```



## 2.6 Table表格组件

我们可以通过table组件来实现课程页面展示的功能，在Element—UI官方，找到Table组件，复制到vue页面

### 2.6.1 添加表格组件

复制表格组件相关代码到 Course.vue 中

```vue
<template>
    <el-table
    :data="tableData"
    stripe
    style="width: 100%">
    <el-table-column
      prop="date"
      label="日期"
      width="180">
    </el-table-column>
    <el-table-column
      prop="name"
      label="姓名"
      width="180">
    </el-table-column>
    <el-table-column
      prop="address"
      label="地址">
    </el-table-column>
  </el-table>
</template>

<script>
export default {
    data() {
        return {
            tableData: [{
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }]
        }
    },
}
</script>
```

### 2.6.2 表格组件说明

分析一下表格组件

```vue
//视图部分 进行页面展示
<template>
 //el-table组件 绑定了tableData数据
 <el-table :data="tableData" style="width: 100%">
 //el-table-column 表示表格的每列,prop属性与模型数据中的key对应 ,label 列名
  <el-table-column prop="date" label="日期" width="180"></el-table-column>
  <el-table-column prop="name" label="姓名" width="180"></el-table-column>
  <el-table-column prop="address" label="地址"></el-table-column>
 </el-table>
</template>
<script>
//export default 相当于提供一个接口给外界，让其他文件通过 import 来引入使用。
export default {
 //data() 函数
 data() {
  return {
   //数据部分
   tableData: [
   {
     date: "2016-05-02",
     name: "王小虎",
     address: "上海市普陀区金沙江路 1518 弄"
   }
 };
}
};
</script>
```



## 2.7 课程内容信息展示

### 2.7.1 修改Course.vue

对course.vue组件进行修改

```vue
<template>
    <el-table
     v-loading="loading"
     element-loading-text="拼命加载中"
    :data="courseList"
    stripe
    style="width: 100%">
    <el-table-column
      prop="id"
      label="ID"
      >
    </el-table-column>
    <el-table-column
      prop="course_name"
      label="课程名称"
     >
    </el-table-column>
    <el-table-column
      prop="price"
      label="价格">
    </el-table-column>
    <el-table-column
      prop="sort_num"
      label="排序">
    </el-table-column>
    <el-table-column
      prop="status"
      label="状态">
    </el-table-column>
  </el-table>
</template>

<script>
export default {
    // 数据部分
    data() {
        return {
            // 定义数据
            loading: false, //是否弹出加载提示
        courseList: [{        
        }]
        };
    },
    // 定义钩子函数  created,在DOM页面生成之前执行
    created() {
        // 在页面生成之前，调用 loadCourse()
        this.loadCourse();
    },

    // 方法集合
    methods: {
        // 方法一：获取课程信息
        loadCourse(){
            //开启加载提示
   this.loading = true;
            alert("loadCourse方法执行了");
            // 发送请求获取课程数据
            const url = "http://localhost:8080/lagou_edu_home/course";
            //访问后台接口,获取数据并返回
            return this.axios.get(url,{
                params:{
                    methodName:"findCourseList"
                }
            }).then((resp) => {
                console.log(resp.data);
                // 将获取到的数据 赋值给courseList
                this.courseList = resp.data;
               
                // 取消加载动画
                this.loading=false
            }).catch((error) => {
                // 异常
                this.$message.error("获取数据失败！");
            })

            
        }    
    },
}
</script>

<style scoped>

</style>
```

### 2.7.2 跨域问题

### 2.7.2.1 出现跨域问题

当我们在前端项目中，向后端发送请求时，出现了跨域问题：

* 已被CORS策略阻止：请求的资源上没有' Access-Control-Allow-Origin'标头（跨域请求失败）

```
Access to XMLHttpRequest at 'http://localhost:8080/lagou_edu_home/course?
methodName=findCourseList' from origin 'http://localhost:8088' has been blocked
by CORS policy: No 'Access-Control-Allow-Origin' header is present on the
requested resource.
```

### 2.7.2.2 什么是跨域		跨域：协议，域名，端口 任何一个不同就是跨域

​	通过JS在不同的域之间进行数据传输或通信。不同的域就是，只要**协议、域名、端口**有任何一个不同，就被当做不同的域，浏览器不允许跨域请求

### 2.7.2.3 解决跨域

​	跨域允许主要由服务端控制。

* 设置响应头中的参数来允许跨域：
  * Access-Control-Allow-Credentials
  * Access-Control-Allow-Origin 标识允许跨域的请求有哪些

1. 在pom文件中引入依赖

```xml
<!-- 解决跨域问题所需依赖 -->
<dependency>
  <groupId>com.thetransactioncompany</groupId>
  <artifactId>cors-filter</artifactId>
  <version>2.5</version>
</dependency>
```

2. 在web.xml中 配置跨域 filter

```xml
<!--配置跨域过滤器-->
  <filter>
    <filter-name>corsFilter</filter-name>
    <filter-class>com.thetransactioncompany.cors.CORSFilter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>corsFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```

### 2.7.2.4 再次查询

解决跨域问题后，页面显示数据



## 2.8 条件查询

### 2.8.1 ElementUI输入框组件

* input输入框通过或键盘输入字符

```vue
<el-input
  placeholder="请输入内容"
  v-model="input4">
  <i slot="prefix" class="el-input__icon el-icon-search"></i>
</el-input>
```

Course.vue 中添加输入框

```vue
<template>
 <div>
  <!-- 条件查询 -->
  <el-input placeholder="请输入课程名称">
   <i slot="prefix" class="el-input__icon el-icon-search"></i>
  </el-input>
  
  <!-- 表单显示 ... -->
 </div>
</template>
```

### 2.8.2 Layout布局

* 通过基础的24 分栏，迅速简便地创建布局
* 通过row 和 col组件，并通过col组件的**span**属性我们就可以自由地组合布局

1）Row 组件提供**gutter**属性来指定每一栏之间的间隔，默认间隔为0

```vue
<el-row :gutter="20">
 <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
 <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
 <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
 <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
</el-row>
```

2）使用分隔栏，分隔查询条件

```vue
<!-- 条件查询 -->
  <el-row :gutter="10">
   <el-col :span="5">
    <el-input clearable placeholder="课程名称">
     <i slot="prefix" class="el-input__icon el-icon-search"></i>
    </el-input>
   </el-col>
  </el-row>
```

3）添加一个查询按钮

```vue
<el-row :gutter="10">
  <el-col :span="5">
    <el-input clearable placeholder="课程名称">
      <i slot="prefix" class="el-input__icon el-icon-search"></i>
    </el-input>
  </el-col>
  <el-col :span="1">
    <el-button type="primary">查询</el-button>
  </el-col>
</el-row>
```



### 2.8.3 完成根据课程名查询

1. 通过双向数据绑定

* Model模型

```js
//数据部分
 data() {
  //定义查询条件
  return {
   loading: false, //是否弹出加载提示
   courseList: [], //定义集合,保存从接口获取的参数
   filter: { course_name: "" } //根据双向绑定来获取视图输入的查询条件
 };
},
```

* View视图

```vue
<el-input v-model="filter.course_name" clearable placeholder="课程名称">
  <i slot="prefix" class="el-input__icon el-icon-search"></i>
</el-input>
```

2. 设置点击事件

```vue
<el-button type="primary" @click="search">查询</el-button>
```

3. methods中添加方法

```js
// 根据课程名查询方法
        search(){
            // 开启加载提示
            this.loading = true;

            // 发送请求
            const url = "http://localhost:8080/lagou_edu_home/course"
            return this.axios.get(url,{
                // 携带参数
                params:{
                    methodName:"findByCourseNameAndStatus",
                    // 获取双向绑定的课程名
                    course_name:this.filter.course_name
                }
            }).then((resp) => {
                console.log(resp.data);
                // 把查询出的数据赋值到courseList
                this.courseList = resp.data;
                // 关闭加载	
                this.loading = false;

            }).catch((error) => {
                // 异常
                this.$message.error("获取数据失败！");
            });
        }
```

