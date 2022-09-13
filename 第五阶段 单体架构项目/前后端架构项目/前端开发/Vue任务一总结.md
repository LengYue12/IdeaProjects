# Vue任务一总结

# Vue基础

1. Vue简介

* Vue.js  前端的JavaScript框架
* 渐进式框架，即插即用
* 适合前后端分离

2. Vue.js的使用

* 引入Vue
* 定义div  指定id        就是Vue实例控制的区域
* 创建Vue实例             new Vue对象
  * {{}}插值表达式       取出数据对象中的数据
  * el：挂载点             指定Vue实例接管的区域
  * data：数据            Vue以数据驱动的，改变数据就可以让视图内容发生改变
* 渲染数据                 
  * Vue中使用声明式数据渲染，准备好数据，Vue就可以帮我们将数据渲染到HTML

3. Vue常用的指令

* v-text指令           获取data中的数据，设置标签内容
* v-html指令         设置元素的innerHTML（向元素中写入新的标签）
* v-on指令            为元素绑定事件
* v-show指令        根据真假值，切换元素显示状态         频繁操作用v-show
* v-if指令               根据表达值的真假，切换元素的显示和隐藏（操作DOM元素）
* v-bind指令         设置元素属性的（src，title）
* v-for指令            遍历数组，根据数据生成列表结构
* v-on指令补充      函数调用传递参数。限制事件的触发方式（如键盘抬起触发事件）

4. MVVM模式

* model：负责数据存储              Vue实例中的data
* View：负责页面展示                 div部分HTML
* view model：负责业务逻辑处理，将数据渲染到页面                new的Vue实例

5. v-model指令实现双向数据绑定            
   1.  单向绑定：把数据显示到View上，model更新，视图更新
   2. 双向绑定：用户更新了View视图，model数据也会发生改变（form表单）



# Vue进阶

1. axios         对ajax的封装，用来发送异步请求，导入再使用
   1. Get：axios.get(url?key=value).then(function(resp){},function(err){})
   2. post：axios.post(url,{key:value}).then(function(resp){},function(err){})
2. computed 计算属性        减少运算次数，缓存运算结果。
   1. 就是被计算出来的属性，一旦属性被计算出来后，值不发生改变，就可以引用缓存好的结果
3. filter  过滤器            对数据进行过滤的，筛选后再显示
   1. {{ msg | filterA }} 
4. watch 侦听器          监听数据变化做响应操作
5. component 组件化开发     
   1. 组件就是自定义封装的功能
   2. 一个页面可能由很多组件组成，可以把重复的功能提取封装成组件，随处引入
6. Vue 生命周期
   1. vue在创建时经历的初始化过程
   2. 钩子函数，vue生命周期执行过程中会执行到某一个节点时执行对应的钩子函数
7. vue  router路由
   1. 在Web开发中，路由可以根据url分配到指定的处理程序
   2. 单页应用：就是只有一张web页面的应用，vue中使用路由完成单页面应用的开发
   3. 使用路由步骤
      1. 定义路由所需的组件
      2. 定义路由   每个路由有两部分      path路径和component组件
      3. 创建router路由管理器，对路由对象routes进行管理
      4. 创建Vue实例，把路由管理器放到Vue实例中，调用挂载mount函数，让整个应用都有路由功能