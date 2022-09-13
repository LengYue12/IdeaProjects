# 前端开发 任务一	Vue基础入门学习笔记

# 1.Vue.js

## 1.1 Vue.js 介绍

### 1.1.1 Vue.js是什么？

​	Vue是一套用于构建用户界面的渐进式框架。

​	自底向上逐层应用：作为渐进式框架要实现的目标就是方便项目增量开发（即插即用）

### 1.1.2 为什么使用Vue？

1. 声明式渲染： 前后端分离架构情况下，vue可以更好地将数据渲染到HTML
2. 渐进式框架：适用于各种业务需求
3. 简单易学

## 1.2 Vue.js基础

## 1.2.1 Vue.js的使用

1. 在HTML页面中使用script引入vue.js的库
2. Vue-CLI脚手架：使用vue.js官方提供的CLI脚本架很方便去创建vue.js工程雏形

## 1.2.2 入门程序

代码编写步骤：

1. 定义html，导入vue.js库
2. **定义id为app  div，将此区域作为vue的接管区域**
3. **定义Vue实例，接管app区域**
4. **定义model(数据对象)**
5. 在app中展示数据

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <!-- 1.创建HTML文件， 引入vue.js 有两种方式 -->
    <!-- 第一种 引入 vue.js的CDN地址 -->
    <!-- <script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script> -->

    <!-- 第二种 本地导入 -->
    <script src="./js/vue.min.js"></script>

</head>
<body>
    <!-- 2.创建 id为 app 的div，此区域作为Vue实例的接管区域 -->
    
    <div id="app">
        <!-- {{}} 双括号是VUE中的差值表达式，将表达式的值输出到HTML页面 -->
        {{name}}
    </div>
</body>


<script>
    // 3.创建vue实例
    var VM = new Vue({
        // 定义 Vue实例挂载的元素节点，表示vue接管该div
        el:"#app",  // 挂载点
        // 4.定义model模型数据对象
        data:{
            name:"Hello Vue!"
        }
    });

</script>
</html>
```

## Vue分析

首先创建有id的div，然后创建vue实例，指定**el表示挂载点，指定vue实例接管的区域**，使用id选择器指定区域，表示当前div及里面的所有内容都归vue实例管理。定义 data，**vue中用到的数据定义在data**。定义好后可以用插值表达式取出的data中的数据，在页面中展示

### 1.{{}}:插值表达式

1. 插值表达式的作用？
   1. 通常用来获取Vue实例中定义的数据（data）
   2. 属性节点中 不能够使用插值表达式

### 2.el：挂载点

1. el的作用？
   1. 定义Vue实例挂载的元素节点，表示vue接管该区域，指定vue实例管理的区域
2. Vue的作用范围是什么？
   1. Vue会管理el选项命中的元素，及其内部元素
3. el选择挂载点时，是否可以使用其他选择器？
   1. 可以，但是建议使用id选择器
4. 是否可以设置其他的DOM元素进行关联？
   1. 可以但是建议选择div，不能使用HTML和Body标签

### 3.data：数据对象

1. vue中用到的数据，定义在data中
2. data中可以定义复杂数据
3. 渲染复杂类型数据时，遵守js语法

```html
<body>
    <!-- 2.创建 id为 app 的div -->
    <!-- 此区域作为vue的接管区域 -->
    <div id="app">
        {{name}} <br>
        {{person.name}} {{person.mobile}}<br>
        <ul>
            <li>{{names[0]}}</li>
            <li>{{names[1]}}</li>
            <li>{{names[2]}}</li>
        </ul>

    </div>
</body>


<script>
    // 3.创建vue实例
    var VM = new Vue({
        el:"#app",  // 挂载点
        data:{
            name:"你好世界",

            // 定义对象类型的数据
            person:{
                name:"张飞",
                mobile:"100111",
            },
            // 定义数组类型
            names:["曹操","李白","关羽"]
        }
    });

</script>
```

## 1.2.3 声明渲染的好处

渲染指的就是将数据显示到HTML页面中，Vue中的声明式渲染语法简洁，可以将数据渲染DOM

**数据和DOM已经建立了关联，所有东西都是响应式的。当model中的数据改变时，View视图里面的内容也会随之改变。不再和HTML交互了。**

JQuery渲染数据时，对DOM操作添加元素标签会使页面性能降低

**使用Vue方式渲染数据时，会将数据和视图之间进行解耦**，不用像JQuery一样拼接字符串了，显示方式简洁

```html
<body>
    <div id="app">
        <!-- 使用插值表达式取出name的值 -->
        <h2>{{name}}</h2>
    </div>
</body>
<!-- <script src="./js/jquery-1.8.3.min.js"></script>
JQuery方式渲染数据，如果DOM发生变化，js代码也需要做相应的改变，高耦合
<script>
    $(document).ready(function(){

        $("#app").append("<h2>Hello World！</h2>");

    }); -->
<script src="./js/vue.min.js"></script>
<!-- Vue方式渲染数据，只需要定义好展示的数据，并把它放在DOM合适的位置就可以 -->
<script>
    var VM = new Vue({
        el:"#app",  // 挂载点，表示vue实例所控制的区域
        data:{
            name:"Hello World!"
        }
    })
</script>
</script>
```

## 1.2.4 Vue常用指令

指令 是指带有v-前缀的特殊属性。通过指令来操作DOM元素

### 1. v-text 指令

作用：获取data数据，**设置标签的内容**

但在元素标签内部写，作为属性出现

注意：**默认写法会替换全部内容，使用插值表达式{{}}可以替换指定内容**

代码

```html
<body>
    <div id="app">
        <!-- 插值表达式 不会覆盖原来的内容体，直接拼接 -->
        <h2>{{message}}炎龙铠甲</h2>

        <!-- v-text 获取data数据，设置标签的内容，会覆盖之前的内容体 -->
        <h2 v-text="message">炎龙铠甲</h2>

        <!-- 拼接字符串 时，要加单引号 拼接数字不用 -->
        <h2 v-text="message+2"></h2>
        <h2 v-text="message+'aaa'"></h2>
    </div>
</body>
<script>
    // 创建vue实例，接管div区域
    var VM = new Vue({
        el:"#app",
        data:{
            message:"铠甲合体！"
        }
    })
</script>
```

### 2. v-html指令

作用：设置元素的 innerHTML(可以向元素中写入新的标签)

代码

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        /*
            v-html指令：设置元素的innerHTML，向元素中写入标签
        */
    </style>
    <script src="./js/vue.min.js"></script>
</head>
<body>
    <div id="app">
        <!-- 获取普通文本的三种方式 -->
        {{message}}
        <h2 v-text="message"></h2>
        <h2 v-html="message"></h2>

        <!-- 设置元素的innerHTML -->
        <h2 v-html="url"></h2>
        <!-- v-text直接取值 -->
        <h2 v-text="url"></h2>
    </div>
</body>
<script>
    var VM = new Vue({
        el:"#app",
        data:{
            message:"炎龙铠甲",
            url:"<a href='https://www.baidu.com'>百度一下</a>"
        }
    })
</script>
</html>
```

### 3.v-on 指令

作用：**为元素绑定事件，如：v-on：click，可以简写为@click="方法名"**

事件所触发的方法都可以在Vue实例的methods中编写。

绑定的方法定义在VUE实例的，method属性中

语法格式

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        /*
            v-on指令：作用是为元素绑定事件
            如给按钮绑定事件
        */
    </style>
</head>
<body>
    <div id="app">
        <!-- v-on绑定click点击事件 -->
        <input type="button" value="点击按钮" v-on:click="show"/>
        
        <!-- 简写方式 @符号也可以绑定 -->
        <input type="button" value="点击按钮" @click="show"/>

        <!-- 绑定双击事件 -->
        <input type="button" value="双击按钮" @dblclick="show"/>
        
        <!-- 绑定点击事件，修改内容 -->
        <h2 @click="changeFood">{{food}}</h2>
    </div>
</body>
<script src="./js/vue.min.js"></script>
<script>
    var VM = new Vue({
        el:"#app",
        data:{
            food:"麻辣香锅"
        },
        // 通过methods 专门存放Vue的方法
        methods: {
            // 键值对形式
            // 方法名：对应的方法
            show:function(){
                alert("引线在燃烧！");
            },
            changeFood:function(){

                console.log(this.food)
                // 使用this获取data中的数据      this  vue实例
                // 在vue中 不需要考虑如何更改DOM，重点放在修改数据上，数据更新后，使用数据的那个元素也会同步更新
                this.food += "真好次";
            }
        },
    })
</script>
</html>
```

### 4. 计数器案列

1）编码步骤

1. data中定义数据：比如num值为1
2. methods中添加两个方法：比如add（递增）、sub（递减）
3. 使用{{}}将num设置给span标签
4. 使用v-on指令将add、sub分别绑定给 +，-按钮
5. 累加到10停止
6. 递减到0停止

2）页面准备

```html
<body>
    <div id="app">
        <!-- 计算区域 -->
        <div>
            <!-- 加号 -->
            <input type="button" class="btn btn_plus"/>
            <!-- 数字 -->
            <span>{{num}}</span>
            <!-- 减号 -->
            <input type="button" class="btn btn_minus"/>
        </div>
    </div>
</body>
<script>
    var VM = new Vue({
        el:"#app",
        data:{
            num:1
        }
    })
</script>
```

3）案列演示

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="./css/inputNum.css"/>
    <script src="js/vue.min.js"></script>
</head>
<body>
    <div id="app">
        <!-- 计算区域 -->
        <div>
            <!-- 加号 -->
            <input type="button" class="btn btn_plus" v-on:click="add"/>
            <!-- 数字 -->
            <span>{{num}}</span>
            <!-- 减号 -->
            <input type="button" class="btn btn_minus" @click="sub"/>
        </div>
    </div>
</body>
<script>
    var VM = new Vue({
        el:"#app",
        data:{
            num:1
        },
        methods: {
            add:function(){
                if(this.num < 10){
                    this.num++;
                } else{
                    alert("别点了，最大了!");
                }
            },
            sub:function(){
                if(this.num > 0){
                    this.num--;
                } else{
                    alert("别点了，最小了！");
                }
            }
        },
    })
</script>
<script>
    /*
        案列总结：
            1.创建vue实例时：el(挂载点),data(数据)，methods(方法)
            2.v-on 指令 作用是绑定事件，简写为 @事件名
            3.方法中，使用this关键字 获取data中的数据
            4.v-text和插值表达式{{}} 都可以获取data中的数据，设置到元素中
    */
</script>
</html>
```

4）案列总结

* 创建Vue实例时：el(挂载点)，data(数据)，methods(方法)
* **v-on** 指令的作用 是绑定事件，简写为 @事件名
* 方法中使用**this**关键字，获取data中的数据
* **v-text**和插值表达式**{{}}** 用来获取data中的数据，都可以设置元素的文本值

### 5.v-show 指令

作用：v-show指令，**根据真假值，切换元素的显示状态**

页面准备

```html
<body>
  <div id="app">
    <img src="./img/ytmactblrxj.jpg" alt="">
  </div>
</body>
<script src="vue.min.js"></script>
<script>
  var VM = new Vue({
    el:"#app"
 })
</script>
```

代码

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<style>
    /*
        v-show：根据真假值，切换元素的显示状态
            原理就是修改display 实现显示和隐藏
            值为 true 显示，false 隐藏
            数据改变后，显示的状态会同步更新
    */
</style>
<script src="./js/vue.min.js"></script>
<body>
    <div id="app">
        <input type="button" value="切换状态" @click="changeShow"/>
        <img src="img/ytmactblrxj.jpg" v-show="isShow">
        <img src="img/ytmactblrxj.jpg" v-show="age>18">
    </div>
</body>
<script>
    var VM = new Vue({
        el:"#app",
        data:{
            isShow:true,
            age:19
        },
        methods: {
            changeShow:function(){
                // 触发方法，对isShow进行取反
                this.isShow=!this.isShow;
            }
        },
    })
</script>
</html>
```

v-show指令总结

* 原理就是修改元素的display，实现元素的显示和隐藏
* v-show 指令后面的内容，最终会解析为 布尔值
* 值为 true 显示，值为 false 隐藏
* 数据改变后，显示的状态会同步更新

### 6.v-if 指令

作用：根据表达式的真假，切换元素的显示和隐藏（操作的是DOM元素）

代码

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        /*
            v-if 指令：根据表达式的真假，切换元素的显示和隐藏(操作的是DOM)
            是将DOM中的元素去掉实现元素的隐藏
                频繁切换就是使用：v-show 因为操作样式比操作DOM效率高，反之使用 v-if
                本质上就是通过操作DOM元素来切换状态的，如果为true，就在DOM中把元素加入
                若为false就把元素从DOM中去掉
        */
    </style>
    <script src="./js/vue.min.js"></script>
</head>
<body>
    <div id="app">
        <input type="button" value="切换状态" @click="changeShow"/>
        <img src="img/ytmactblrxj.jpg" v-if="isShow">
    </div>
</body>
<script>
    var VM = new Vue({
        el:"#app",
        data:{
            isShow:false
        },
        methods: {
            changeShow:function(){
                this.isShow=!this.isShow;
            }
        },
    })
</script>
</html>
```

v-if指令总结

* v-if 指令的作用：根据表达式的真假切换元素的显示状态
* 本质是通过操作DOM元素的增删，来切换显示状态
* 表达式为true，元素存在与dom树，为false从dom树中移除
* 频繁切换使用 v-show，反之使用 v-if

### 7.v-bind指令

作用：设置元素的属性（如：src，title，class）

```html
语法格式： v-bind：属性名=表达式
<img v-bind:src="imgSrc"/>

var VM = new Vue({
	el:"#app",
	data:{
		imgSrc:"图片地址"
	}
})

v-bind 可以省略，简写为冒号	:
<img :src="imgSrc"/>
```

代码

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        /*
            v-bind:设置元素的属性   src  class
            语法：v-bind:属性名=表达式

            作用：为元素绑定属性
            完整写法：v-bind:属性名=表达式  简写 :属性名=表达式
        */
    </style>
</head>
<body>
    <div id="app">
        <img src="img/ytmactblrxj.jpg" alt="">

        <!-- 使用v-bind 设置了src属性 -->
        <img v-bind:src="imgSrc">

        <!-- v-bind 可以简写为 : -->
        <img v-bind:src="imgSrc" :title="imgTitle">

        <!-- 设置class样式 -->
        <div :style="{fontSize:size+'px'}">v-bind指令</div>
    </div>
</body>
<script src="js/vue.min.js"></script>
<script>
    var VM = new Vue({
        el:"#app",
        data:{
            imgSrc:"img/ytmactblrxj.jpg",
            imgTitle:"头像",
            size:50
        }
    })
</script>
</html>
```

v-bind指令总结

* v-bind 指令的作用就是：为元素绑定属性
* 完整写法 **v-bind:属性名**，可以简写为 **:属性名**

### 8. v-for指令

作用：根据数据生成列表结构

语法结构

```html
<body>
    <div id="app">
        <ul>
            <li v-for="item in arr"></li>
        </ul>
    </div>
</body>
<script src="js/vue.min.js"></script>
<script>
    var VM = new Vue({
        el:"#app",
        data:{
            arr:[1,2,3,4,5],
            objArr:[
                {name:"tom"},
                {name:"jack"}
            ]
        }
    })
</script>
```

代码

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        /*
            v-for指令：根据数据生成列表结构
             1.数组经常和v-for结合使用，遍历数组
             2.语法格式:(item,index) in 数据
             3.数组的长度变化，会同步更新到页面上，响应式的。数据发生变化，页面也发生变化
        */
    </style>
</head>
<body>
    <div id="app">
        <input type="button" value="添加数据" @click="add">  
        <input type="button" value="移除数据" @click="remove">  

        <ul>
            <!-- 在li标签中  获取数组的元素并展示  遍历数组arr -->
            <!-- 遍历数组arr，item为从数组中取出来的数据 -->
            <li v-for="item,index in arr">
               {{index+1}}铠甲{{item}}
            </li>
        </ul>


        <!-- 使用he标签显示
            
        -->
        <h2 v-for="p in persons">{{p.name}}</h2>
    </div>
</body>
<script src="js/vue.min.js"></script>
<script>
    var VM = new Vue({
        el:"#app",
        data:{  
            // 数组
            arr:["炎龙侠","风鹰侠","雪獒侠","地虎侠"],

            // 对象数组
            persons:[
                {name:"迪迦"},
                {name:"泰罗"},
                {name:"艾斯"}
            ]
        },
        methods: {
            add:function(){
                // 向数组添加元素 push
                this.persons.push({name:"盖亚"});
            },
            remove:function(){
                // 移除数据
                this.persons.shift();
            }
        },
    })
</script>
</html>
```

v-for指令总结

* v-for指令的作用：根据数据生成列表结构
* 数组经常和v-for结合使用，数组有两个常用方法
  * push() 向数组末尾添加一个或多个元素
  * shift() 把数组中的第一个元素删除
* 语法是：(item,index) in  数据
* item和index 可以结合其他指令一起使用
* 数组的长度变化，会同步更新到页面上，是响应式的

### 9.v-on指令补充

1. 传递自定义参数：函数调用传参
2. 事件修饰符：对事件触发的方式进行限制

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        /*
            1.函数调用传参
                事件绑定方法后，可以传递参数
                定义方法时，需要定义形参，来接收参数
                
            2.事件修饰符
                可以对事件进行限制，.修饰符
                .enter 可以限制触发的方式为 回车
        */
    </style>
</head>
<body>
    <div id="app">
        <!-- 函数传参 -->
        <input type="button" value="刷礼物" @click="showTime(66,'爱你哥哥！')"/>

        <!-- 事件修饰符 可以指定哪些方式可以触发事件 -->
        <input type="text" @keyUp.enter="hi"/>
    </div>
</body>
<script src="js/vue.min.js"></script>
<script>
    var VM = new Vue({
        el:"#app",
        data:{

        },
        methods: {
            // 若方法被调用，则接收参数
            showTime:function(p1,p2){
                console.log(p1)
                console.log(p2)
            },
            hi:function(){
                alert("乌鸡哥~~~！");
            }
        },
    })
</script>
</html>
```

总结：

* 事件绑定方法，可以传入自定义参数
* 定义方法时，需要定义形参，来接收实际的参数
* 事件的后面跟上 .修饰符 可以对事件进行限制
* .enter 可以限制触发的按键为回车
* 事件修饰符有 许多 使用时可以查询文档

### 10.MVVM模式

* MVVM 是一种基于**前端开发的架构模式**
* MVVM模式将页面，分层了 M、V、和VM
  * Model：**负责数据存储**
  * View：**负责页面展示**
  * View Model：**负责业务逻辑处理，如将数据渲染到视图**
* MVVM模式可以实现双向数据绑定
  * 当View视图里面的值发生变化，Model的数据也会变化
  * 当Model数据发生变化，View视图里面的数据也会变化
* MVVM的思想，主要是为了让我们的开发更加方便，因为MVVM模式提供了**数据的双向绑定**

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        /*
            MVVM:前端的架构模式
            M:Model 负责存储数据
            V:View 负责页面展示
            VM:ViewModel 负责业务处理（MVVM模式的核心）
        */
    </style>
</head>
<body>
    <!-- 定义id为app的div -->
    <div id="app">
        <!-- View 视图部分 -->
        <h2>{{name}}</h2>
    </div>
</body>
<script src="./js/vue.min.js"></script>
<script>
    // 创建vue实例   就是 VM  ViewModel
    var VM = new Vue({
        // 绑定指定区域
        el:"#app",
        // 定义数据
        // data就是MVVM模式中的 model
        data:{
            name:"喜羊羊",
        }
    })

</script>
</html>
```

### 11.v-model 指令

作用：获取和设置表单元素的值（实现双向数据绑定）

* **双向数据绑定**
  * 单向绑定：就是把Model绑定到View,当Model更新时，View就会自动更新        **Model数据变化会影响到View变化**
  * 双向绑定：用户更新了View，Model的数据也自动被更新，这种情况就是双向绑定     **视图数据变化会影响到Model**
  * 什么情况下用户可以更新View呢？
    * 填写表单就是。当用户填写表单时，View的状态就被更新了，如果此时MVVM框架可以自动更新Model的状态，那就相当于我们把Model和View做了双向绑定，就可以获取用户填写的最新值：

代码

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        /*
            v-model：可以实现双向数据绑定
            单向数据绑定：将model绑定到view上，当model发生变化时，view会随之变化
            双向数据绑定：View也会绑定到Model上，View视图发生变化时，model也会随之改变
        */
    </style>
</head>
<body>
    <div id="app">
        <input type="button" value="修改message" @click="update"/>

        <!-- View 视图 -->
        <!-- <input type="text" v-bind:value="message"/> -->

        <!-- v-model 实现双向数据绑定 -->
        <input type="text" v-model="message">
        <input type="password" v-model="password">
        <h2>{{message}}</h2>
    </div>
</body>
<script src="js/vue.min.js"></script>
<script>
    // VM 业务逻辑控制  Vue的实例
    var VM = new Vue({
        el:"#app",
        // Model 数据存储
        data:{
            message:"引线在燃烧",
            password:12345,
        },
        methods: {
            update:function(){
                this.message="引线"
            }
        },
    })

</script>
</html>
```

v-model指令总结

* v-model 指令的作用是便捷的设置和获取表单元素的值
* 绑定的数据会和表单元素值关联
* 双向数据绑定

## 1.2.5 实现简单记事本

* 新增内容
  * 生成列表结构（v-for数组）
  * 获取用户输入（v-model 双向绑定）
  * 回车，新增数据（v-on .enter事件修饰符）
* 删除内容
  * 点击删除指定的内容（根据索引删除元素）
  * 在methods中添加一个删除的方法，使用splice函数进行删除
* 统计
  * 统计页面信息的个数，就是列表中的元素的个数
  * 获取 list数组的长度，就是信息的个数
* 清空数据
  * 点击清除所有信息
  * 本质就是清空数组

```html
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>小黑记事本</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta name="robots" content="noindex, nofollow" />
    <meta name="googlebot" content="noindex, nofollow" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" type="text/css" href="../css/index.css" />
  </head>

  <body>
    <!-- VUE示例接管区域 -->
    <section id="app">

      <!-- 输入框 -->
      <header class="header">
        <h1>VUE记事本</h1>
        <input
          autofocus="autofocus"
          autocomplete="off"
          placeholder="输入日程"
          class="new-todo"
        
          v-model="inputValue"
          @keyUp.enter="add"  
        />
      </header>

      <!-- 列表区域 -->
      <section class="main">
        <ul class="listview">
            <!-- 使用v-for指令 生成列表结构 -->
          <li class="todo" v-for="item,index in list">
            <div class="view">
              <span class="index">{{index+1}}</span> <label>{{item}}</label>
              <!-- 2.删除操作 传递index -->
              <button class="destroy" @click="remove(index)"></button>
            </div>
          </li>
        </ul>
      </section>
       <!-- 统计和清空 -->
       <footer class="footer">
        <span class="todo-count"> <strong>{{list.length}}</strong> items left </span>
        <button class="clear-completed" @click="clear">
          Clear
        </button>
      </footer>
    </section>
    
    <!-- 开发环境版本，包含了有帮助的命令行警告 -->
    <!-- <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script> -->
    <script src="../js/vue.min.js"></script>
    <script>
        var VM = new Vue({
            el:"#app",
            data:{
                list:["看电影","吃饭","睡觉","玩游戏"],
                inputValue:"看小说"
            },
            methods: {
                // 新增日程方法
                add:function(){
                    // 将用户输入的内容添加到list
                    this.list.push(this.inputValue);
                },
                remove:function(index){
                    console.log(index)
                    // 使用splice（元素的索引，删除几个）
                    this.list.splice(index,1);
                },
                // 清空操作
                clear:function(){
                    this.list=[];
                }
            },
        })
    </script>
  </body>
</html>

```



## 1.3 axios

### 1.3.1 Ajax回顾

* ajax是一种交互式的网页开发技术
* 可以实现网页异步的刷新。可以在不重新加载网页的情况下，对网页的某一部分进行更新
* Ajax 是一种在无需加载整个网页的情况下，能够更新部分网页的技术。进行网页的局部刷新

异步与同步

* 浏览器访问服务器的方式
  * 同步访问：**客户端必须等待服务器端的响应，在等待过程中啥也不能干**
  * 异步访问：**客户端不需要等待服务器的响应，在等待期间，浏览器可以进行其他操作**

### 案列演示

ajax.jsp

```jsp
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>new jsp</title>
</head>
<body>
    <input type="text" />
    <input type="button" value="JQuery方式发送异步请求" onclick="run()"/>
</body>
<script src="jquery-1.8.3.min.js"></script>
<script>
    function run() {
        // JQuery方式 Ajax方式 发送异步请求
        $.ajax({
            url:"/ajax", // 请求路径
            async:true, // true 表示异步 false 表示同步
            data:{ name: "张飞" },   // 携带的数据
            type:"post", // 请求方式
            dataType:"text", // 返回数据的格式 text 普通文本
            // 回调函数
            success:function (param) {
                alert("响应成功：" + param);
            },
            error:function () {
                alert("响应失败了！");
            }
        });
    }

</script>
</html>

```

servlet

```java
package com.lagou.demo01;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zs
 * @date 2022/5/18 18:32
 * @description
 */
@WebServlet("/ajax")
public class AjaxServlet extends HttpServlet {

    private static final long serialVersionUID = 2891039710470075587L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 设置编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        // 1.获取请求数据
        String name = req.getParameter("name");

        // 模拟业务处理的时间，造成的延时效果
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 打印，和响应
        System.out.println(name);
        resp.getWriter().write("你好，你好");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

```

### 1.3.1 axios介绍

Vue中结合网络数据进行开发

* 网络请求库，专门用来发送请求的。封装了ajax
* axios作用：在浏览器中完成 ajax异步请求的发送 

### 1.3.2 axios入门

使用步骤：

1. 导包
2. 请求方式，以Get和Post举例

Get语法

```js
axios.get(url?key=value&key2=value2).then(function(response){},function(error){});
```

**axios.get(接口URL，携带的访问参数).then(成功的回调函数请求响应完成触发，失败的回调函数请求失败触发)**

**要访问的接口的URL、和key的名称都是接口文档提供的，value值 就是我们要传递的参数值**

Post语法

```js
axios.post(url,{key:value,key2:value2}).then(function(response){},function(error){});
```

3.根据接口文档，访问测试接口，进行测试



**接口1：随机笑话**

```
请求地址:https://autumnfish.cn/api/joke/list
请求方法:get
请求参数:num(笑话条数,数字)
响应内容:随机笑话
```

**接口2：用户注册**

```
请求地址:https://autumnfish.cn/api/user/reg
请求方法:post
请求参数:username:"用户名"
响应内容:注册成功或失败
```

代码：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width= , initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="./js/axios.min.js"></script>
    <style>
        /*
            axios总结
                1.axios必须要导包
                2.使用get或者post方式发送请求
                3.then方法 中的回调函数，会在请求成功或者失败的时候被触发
                4.通过回调函数的形参，可以获取响应的内容
        */
    </style>
</head>
<body>
    <input type="button" value="get请求" id="get">
    <input type="button" value="post请求" id="post">
    
</body>
<script>
    /*
        随机笑话接口测试
            请求地址:https://autumnfish.cn/api/joke/list
            请求方法:get
            请求参数:num(笑话条数,数字)
            响应内容:随机笑话

            使用原生JS
    */
    document.getElementById("get").onclick=function(){
        axios.get("https://autumnfish.cn/api/joke/list?num=2").then(function(resp){
            // 调用成功
            console.log(resp)
        },function(err){
            // 调用失败
            console.log(err)
        });

    };

    /*
        用户注册
            请求地址:https://autumnfish.cn/api/user/reg
            请求方法:post
            请求参数:username:"用户名"
            响应内容:注册成功或失败
    */
   document.getElementById("post").onclick=function(){
       axios.post("https://autumnfish.cn/api/user/reg",{username:"胡凯莉"}).then(function(resp){
           // 调用成功
           console.log(resp)
       },function(err){
           // 调用失败
           console.log(err)
       });
   }


</script>
</html>
```

### **1.3.3 axios总结**

1. **axios 必须导包才能使用**
2. **使用get或者post方法，就可以发送请求**
3. **then方法中的回调函数，会在请求成功或者请求失败的时候触发**
4. **通过回调函数的形参可以获取到响应的内容，或者错误信息**

### 1.3.4 获取笑话案列

通过vue+axios 完成一个获取笑话的案列

接口：随机获取一个笑话

```
请求地址:https://autumnfish.cn/api/joke
请求方法:get
请求参数:无
响应内容:随机笑话
```

代码：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="../js/vue.min.js"></script>
    <script src="../js/axios.min.js"></script>
    <style>
        /*
            1.axios 回调函数中，this的指向已经改变，无法访问data中的数据
            2.解决方案：将this异步访问之前进行保存
        */
    </style>
</head>
<body>
    <div id="app">
        <input type="button" value="点击获取一个笑话" @click="getJoke">
        <p>{{joke}}</p>
    </div>
</body>
<script>
    /*
        请求地址:https://autumnfish.cn/api/joke
        请求方法:get
        请求参数:无
        响应内容:随机笑话
    */
   var VM = new Vue({
       el:"#app",
       data:{
           joke:"笑口常开"
       },
       methods: {
           getJoke:function(){

            // 把this进行保存
            var that = this;

            // 异步访问
               // 向接口发送请求获取笑话
               axios.get("https://autumnfish.cn/api/joke").then(function(resp){
                   // 请求成功
                   console.log(resp.data);
                   
                   // 在回调函数内部，this无法正常使用，需要提前保存起来
                   console.log(that.joke); // undefined
                   // 将获取到的笑话放到joke里，并显示到view
                   that.joke=resp.data;
               },function(err){
                   // 请求失败
                   console.log(err);
               });
           }
       },
   })
</script>
</html>
```

案列总结

1. axios回调函数中的this指向已经改变，无法访问data中的数据
2. 解决方法：在axios异步访问前，将this进行保存，回调函数中直接使用保存的this即可

### 1.3.5 天气查询案列

#### 1.3.5.1 需求分析

* 功能分析：回车查询天气数据
  * 1.输入内容，点击回车（v-on .enter）
  * 2.访问接口，查询数据（axios v-model）
  * 3.返回数据，渲染数据到页面

#### 1.3.5.2 接口文档

```
请求地址:http://wthrcdn.etouch.cn/weather_mini
请求方法:get
请求参数:city (要查询的城市名称)
响应内容:天气信息
```

#### 1.3.5.3 案列演示

自定义js文件

​	**作为一个标准的应用程序，我们将创建的Vue实例的代码，抽取到main.js 文件中**

main.js	 编写的是创建Vue实例，发送请求，获取数据的代码

```js
/**
 *  请求地址:http://wthrcdn.etouch.cn/weather_mini
    请求方法:get
    请求参数:city (要查询的城市名称)
    响应内容:天气信息
 */

 var VM = new Vue({
     el:"#app",
     data:{
        city:'',
        // 定义数组保存 天气信息
        weatherList:[]
     },
     // 编写查询天气的方法
     methods: {
         searchWeather:function(){
             console.log("天气查询");
             console.log(this.city);

             // 调用接口
             // 保存this
             that = this;
             axios.get("http://wthrcdn.etouch.cn/weather_mini?city=" + this.city).then(function(resp){
                 // 访问成功   将获取到的天气信息展示，放到数组中
                 console.log(resp.data.data.forecast);
                 // 获取天气信息  保存到weatherList
                 that.weatherList=resp.data.data.forecast;
             },function(err){

             });
         }
     },
 })
```

查询天气案列.html

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>天气查询</title>
    <link rel="stylesheet" href="css/reset.css" />
    <link rel="stylesheet" href="css/index.css" />

    <style>
        /*
            1.应用的逻辑代码 js部分 建议与页面进行分离，使用单独的js编写
            2.axios中 回调函数中的this 需要先保存再使用
            3.服务器返回的数据比较复杂，获取数据时候 要注意层级结构
        */
        /*
            通过属性选择器，设置 添加了 v-cloak 可以解决页面闪烁问题
        */
        [v-cloak]{
            display: none;
        }
    </style>
  </head>

  <body>
    <div class="wrap" id="app" v-cloak>
      <div class="search_form">
        <div class="logo">天气查询</div>
        <div class="form_group">
            <!-- 输入城市的信息，v-model 进行双向数据绑定，和model模型里的city进行绑定 -->
            <!-- 绑定一个回车进行查询的事件，通过@keyUp.enter -->
          <input type="text" class="input_txt" placeholder="请输入要查询的城市" v-model="city" @keyUp.enter="searchWeather" />
          <button class="input_sub">回车查询</button>
        </div>
      
      </div>
      <ul class="weather_list">
          <!-- 展示数据 通过v-for指令 生成li列表结构 -->
        <li v-for="item in weatherList">
          <div class="info_type"><span class="iconfont">{{item.type}}</span></div>
          <div class="info_temp">
            <b>{{item.low}}</b>
            ~
            <b>{{item.high}}</b>
          </div>
          <div class="info_date"><span>{{item.date}}</span></div>
        </li>
      </ul>
    </div>
    <!-- 开发环境版本，包含了有帮助的命令行警告 -->
    <!-- <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script> -->
    <!-- 官网提供的 axios 在线地址 -->
    <!-- <script src="https://unpkg.com/axios/dist/axios.min.js"></script> -->
    <script src="js/vue.min.js"></script>
    <script src="js/axios.min.js"></script>
    <!-- 自己的js -->
    <script src="./js/main.js"></script>
  </body>
</html>
```

#### 1.3.5.4 案列总结

1. 应用的逻辑代码，建议与页面分离，使用单独的js编写
2. axios回调函数中的this指向改变，无法正常使用，需要保存另外一份
3. 服务器返回的数据比较复杂时，获取数据时要注意层级结构



### 1.3.6 解决页面闪烁问题

我们发现访问天气案列页面时，使用插值表达式的地方出现了闪烁问题

**v-cloak指令**

作用：解决插值表达式闪烁问题，**在页面完全完成响应后，再显示**

**当网络较慢时，网页还在加载Vue.js时，还没有加载完成，Vue源码就被显示了**

​	**当网络较慢时，网页还在加载Vue.js时，而导致 Vue 来不及渲染，这时页面就会显示出Vue源码，我们可以使用v-cloak指令来解决**

1）添加样式

```css
<style>
  /* 通过属性选择器,设置 添加了v-cloak */
 [v-cloak] {
    display: none;
 }
</style
```

2）在id为app的div中添加 v-cloak

```html
<div class="wrap" id="app" v-cloak>
```

## 1.4 computed  计算属性

### 1.4.1 什么是计算属性？

**计算出的属性，可以减少运算次数，用于重复相同的操作**

通过计算获取到的属性，可以把获取到属性值当做普通属性使用

computed作用：减少运算次数，缓存运算结果，用于重复相同的计算

示列：

```html
 <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="./js/vue.min.js"></script>
    <style>
        /*
            1.计算属性可以减少运算次数，用于重复相同的计算
            2.定义函数也可以实现与计算属性相同的效果，但是计算属性可以简化运算
            3.只要第一次调用后，值没有变化，第二次调用时还是获取之前计算好的值
        */
    </style>
</head>
<body>
    <div id="app">
        <!-- <h1>{{a*b}}</h1>
        <h1>{{a*b}}</h1> -->

        <!-- <h2>{{res()}}</h2>
        <h2>{{res()}}</h2> -->

        <h1>{{res2}}</h1>
        <h1>{{res2}}</h1>
    </div>
</body>
<script>
    var VM = new Vue({
        el:"#app",
        data:{
            a:10,
            b:20
        },
        methods: {
            res:function(){
                console.log("res方法执行了！")
                return this.a*this.b
            }
        },
        // 使用计算属性 进行优化，减少运算次数，用于重复相同的运算
        computed:{
            // 计算出的属性，返回的是计算的结果
            res2:function(){
                console.log("res方法执行了！")
                return this.a*this.b
            }
        }
    })

</script>
</html>
```

### 1.4.2 computed总结

1. 定义函数也可以实现与计算属性相同的效果，都可以简化运算
2. 不同的是**计算属性是基于它们的响应式依赖进行缓存的**。只在相关响应式依赖发生改变时它们才会重新求值

## 1.5 filter 过滤器

### 1.5.1 什么是过滤器？

​	Vue中的过滤器是对即将显示的数据做筛选处理，然后进行显示，过滤器只是在原数据基础上产生新的数据，并没有改变原来的数据。数据加工，对要显示的值进行筛选加工

### 1.5.2 过滤器使用

1. 在插值表达式{{msg | filterA}}

```
msg 表示需要处理的数据，filterA表示过滤器, | 这个竖线是一个管道，通过这个管道 将数据传给过滤器进行 过滤加工
```

2. v-bind 绑定值的位置

```html
<h1 v-bind:id="msg | filterA"></h1>
```

### 1.5.3 过滤器

**局部过滤器**

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="js/vue.min.js"></script>
</head>
<body>
    <div id="app">
        <!-- 使用插值表达式调用过滤器 -->
        <p>电脑价格:{{price | addIcon()}}</p>
    </div>
</body>
<script>
    // 局部过滤器   在Vue实例内部创建filter
    var VM = new Vue({
        el:"#app",  // 挂载点
        data:{  
            // model
            price:200
        },
        methods: {
            // 方法
        },
        computed:{ 
            // 计算属性
        },

        // 局部过滤器
        filters:{
            // 定义处理函数 value = price
            addIcon(value){
                return "$" + value
            }
        }
    })

</script>
</html>
```

**全局过滤器**

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        /*
            需求：将用户名开头字母大写
            总结： 
                1.过滤器经常被用来处理文本格式化操作
                2.过滤器使用的两个位置：{{}} 插值表达式中，v-bind表达式中
                3.过滤器是通过管道传输数据的   插值表达式里有 |  就是过滤器
        */
    
    </style>
</head>
<body>
    <div id="app">
        <p>{{user.name | changeName}}</p>
    </div>
</body>
<script src="js/vue.min.js"></script>
<script>
    // 在创建Vue实例之前，创建全局过滤器
    Vue.filter("changeName", function(value){
        // 将姓名的开头字母大写
        return value.charAt(0).toUpperCase() + value.slice(1)
    })

    var VM = new Vue({
        el:"#app",
        data:{
            user:{name:"jack"}
        },
    })
</script>
</html>
```

### 1.5.4 总结

1. 过滤器常用来处理文本格式化的操作，过滤器可以用在两个地方：**双花括号插值表达式和v-bind表达式**
2. 过滤器应该被添加在JavaScript表达式的尾部，由“管道”符号指示

## 1.6 watch 侦听器

### 1.6.1 什么是侦听器

用于观察Vue实例上的数据变化。根据数据变化进行响应的处理

作用：当有一些数据需要随着其它数据变动而变动时，可以使用侦听器

**监听属性值的变化**

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
    
    </style>
</head>
<body>
    <div id="app">
        <h2>计数器：{{count}}</h2>
        <input type="button" value="点我+1" @click="count++">
    </div>
    
</body>
<script src="js/vue.min.js"></script>
<script>
    var VM = new Vue({
        el:"#app",
        data:{
            count:1
        },
        watch:{
            // 监测属性值的变化
            count:function(nval,oval){  // 参数1：原来的值  参数2：变化后新的值
                alert("计数器发生变化:" + oval + "变化为：" + nval);
            }
        }
    })
</script>
</html>
```

### 1.6.2 案列

需求：监听姓名变化，实时显示

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <div id="app">
        <!-- 根据用户输入的值做双向数据绑定 -->
        <!-- 当视图中的数据发生变化，model模型中的数据也会变化 -->
        <label>姓：<input type="text" v-model="fristName"></label>
        <label>名：<input type="text" v-model="lastName"></label>
        {{fullName}}
    </div>
</body>
<script src="js/vue.min.js"></script>
<script>
    var VM = new Vue({
        el:"#app",
        data:{
            fristName:"",
            lastName:"",
            fullName:""
        },
        // 侦听器
        watch:{
            fristName:function(navl,oval){// 参数1 是新值  参数2 是旧值
                this.fullName = navl + " " + this.lastName;
            },
            lastName:function(navl,oval){
                this.fullName = this.fristName + " " + navl;
            }
        }
    })
</script>
</html>
```

## 1.7 Component 组件

### 1.7.1 组件介绍

组件是自定义封装的功能。在前端开发中，经常会出现很多页面功能重复，所以可以将功能进行抽取，抽取成一个组件封装起来。在开发时，引入就可以

组件化开发，可以把整个页面中的每块拿出来单独进行开发。封装成组件，最后再拼一起

****

**Vue中的组件有两种：全局组件 和 局部组件**

### 1.7.2 全局组件

语法：

```js
Vue.component("组件名称",{
// 组件内容
	template:"html代码",	// 组件的页面信息 HTML代码
	data(){
	// 组件数据
    	return{}
	},
	methods:{
	// 组件的相关方法
		方法名(){
			// 逻辑代码
		}
	}
})
```

注意：

1. **组件名以小写开头，采用短横线分割命名**：如：**hello-World**
2. **组件中的data 必须是一个函数，注意与Vue实例中的data区分**
3. **在template模版中，只能有一个根元素**

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <div id="app">
        <!-- 使用组件 -->
        <lagou-header></lagou-header>

    </div>
</body>
<script src="js/vue.min.js"></script>
<script>
    // 定义全局组件
    // 组件的命名规则：一般用短横线进行连接，左边是公司名，右边是组件作用名
    Vue.component(
        "lagou-header", 
        {
            template:"<div><h1 @click='hello'>{{msg}}</h1></div>", // template模版中 只能有一个根元素
            // 组件中的data是一个函数
            data() {
                return {
                    msg:"这是lagou-header组件中的数据部分"
                }
            },
            methods:{
                hello(){
                    alert("你好")
                }
            }
        }
    )


    var VM = new Vue({
        el:"#app",
        data:{

        },
        methods: {
            
        },
    })
</script>
</html>
```

### 1.7.3 局部组件

​		局部组件只能在同一个实例内才能被调用。	**局部组件写在Vue实例内部**

```js 
new Vue({
	el:"#app",
	components:{
		组件名:{
			// 组件结构
			template:"HTML代码",
			// data数据
			data(){ return {msg:"xxx"}}
		}
	}
})
```

注意：

​	创建局部组件，注意components。components里可以创建多个组件

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <div id="app">
        <web-msg></web-msg>
    </div>
</body>
<script src="js/vue.min.js"></script>
<script>
    // 创建局部组件
    var VM = new Vue({
        el:"#app",
        components:{
            // 组件名
            "web-msg":{
                template:"<div><h1>{{msg1}}</h1><h1>{{msg2}}</h1></div>",
                data() {
                    return {
                        msg1:"吃饭中...",
                        msg2:"吃完饭了"
                    }
                },
            }
        }
    })
</script>
</html>
```

### 1.7.4 组件与模版分离

由于把html写在组件里不方便，也不好看。所以分开写

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <div id="app">
        <!-- 使用组件 -->
        <web-msg></web-msg>
    </div>

    <!-- 将模版写在 HTML中，给模版一个id -->
    <template id="t1">
        <div>
            <button @click="show">{{msg}}</button>
        </div>
    </template>
</body>
<script src="js/vue.min.js"></script>
<script>
    var VM = new Vue({
        el:"#app",
        components:{
            "web-msg":{
                template:"#t1",
                data() {
                    return {
                       msg:"点击查询" 
                    }
                },
                methods: {
                    show(){
                        alert("对不起，不可以查询哦！");
                    }
                },
            }
        }
    })

</script>
</html>
```

总结：

1. 上面这种写法，**浏览器会把HTML里的template标签过滤，所以template标签的内容是不会在页面中显示**。直到被JS中的Vue调用
2. 在HTML中，**template标签一定要有一个id**，因为通过id是最直接被选中的。data和methods等参数，全部都要放到Vue实例里

## 1.8 Vue声明周期

### 每个Vue实例在**创建**之前都要经过的一系列初始化过程，这个过程就是Vue的生命周期

### 钩子函数

**在Vue生命周期中，每当Vue执行到一个阶段时对应的钩子函数，钩子函数被触发，执行操作**

| 函数             | 说明                                                      |
| ---------------- | --------------------------------------------------------- |
| beforeCreate()   | **在创建Vue实例之前,可以执行这个方法**. 例如 加载动画操作 |
| created()        | **实例创建完成,属性绑定好了,但是DOM页面还没有生成.**      |
| beforeMount()    | 模板已经在内存中编辑完成了，尚未被渲染到页面中.           |
| mounted()        | 内存中的模板已经渲染到页面，用户已经可以看见内容.         |
| beforeUpdate()   | 数据更新的前一刻 , 组件在发生更新之前,调用的函数          |
| updated()        | updated执行时，内存中的数据已更新，并且页面已经被渲染     |
| beforeDestroy () | 钩子函数在实例销毁之前调用                                |
| destroyed ()     | 钩子函数在Vue 实例销毁后调用                              |

### 案列

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <div id="app">
        <h2 id="msg">{{message}}</h2>
        <button @click="next">获取下一句</button>
    </div>
</body>
<script src="js/vue.min.js"></script>
<script>
    var VM = new Vue({
        el:"#app",
        data:{
            message:"抬头望明月"
        },
        methods: {
            show(){
                alert("show方法执行了！");
            },
            next(){
                this.message="低头思故乡"
            }
        },
        // beforeCreate() {
        //     // 在Vue实例创建之前执行
        //     alert("1.beforeCreate函数，在Vue对象实例化之前执行");
                // 获取data中的数据时是未定义，因为Vue实例还未创建
        //     console.log(this.message);  // undefined
                // 获取不到methods中的方法，因为Vue实例还未把属性初始化
        //     this.show();    // this.show is not a function
        // },
        // created() {
            // 在DOM页面加载之前之前执行
        //     alert("2.created函数执行时，Vue实例化已完成，但是DOM页面还未生成");
                // 在此方法中调用就可以获取到view中的数据了，因为实例化已完成，但是页面还未生成
        //     console.log(this.message);  
        //     this.show();    
        // },
        // beforeMount() {
            // 模型data中的数据已编辑完成，但是还未渲染到View视图
        //     alert("3.beforeMount函数执行时，模版已经在内存中编辑完成了，但是还没有被渲染到页面中");
                // View页面中的内容还未被渲染，获取不到
        //     console.log("页面显示的内容" + document.getElementById("msg").innerText)
            // 可以获取到data中的数据
        //     console.log("data中的数据" + this.message);
        // },
        // mounted() {
        //      执行mounted时，页面已经渲染完成了，就差显示了，所以可以获取到页面中的内容
        //     alert("4.mounted函数执行时，模版已经被渲染到页面，执行完就会显示页面")
                // 已经渲染完了，所以可以获取到View页面的内容
        //     console.log("页面显示的内容" + document.getElementById("msg").innerText)
        // },
        // beforeUpdate() {
            // 当修改msg值时，data数据已改变，但是View视图还未被渲染
        //     alert("5.beforeUpdate执行时，内存中的数据已经更新，但是还没有渲染到页面")
        //     // 所以当获取msg的时候，还是之前的内容。data数据已经改变，还没有渲染到页面
        //     console.log("页面显示的内容" + document.getElementById("msg").innerText)
            // 但是可以获取data中的已改变数据
        //     console.log("data中的数据" + this.message);

        // },
        updated() {
            alert("6.updated执行时，内存中的数据已经更新了，此方法执行完显示页面")
            // 此方法执行时，页面中的内容和data中的数据都已经修改了
            console.log("页面显示的内容" + document.getElementById("msg").innerText)
            console.log("data中的数据" + this.message);
        },
    })
</script>
</html>
```

## 1.9 Vue Router 路由

### 1.9.1 什么是路由？

在Web开发中，路由可以根据URL分配对应的处理程序。路由可以通过不同的URL访问不同的内容

**如发送请求到页面，是先到路由，路由再根据对应的请求找到页面**

### 1.9.2 什么是SPA？

单页面应用。只有一个Web页面。**通过路由，Vue.js可以实现多视图单页面web应用**。**根据不同的URL访问不同的组件通过路由实现**

**单页应用不存在页面跳转，它本身只有一个HTML页面。在页面跳转时单页应用时改变body里的组件，实现局部内容更新，但依然还在单页面中**

单页面应用的好处：

1. 用户操作体验好，用户不用刷新页面，整个交互过程都是通过ajax来操作
2. 适合前后端分离开发，服务端提供http接口，前端请求http接口获取数据，使用js进行客户端渲染

### 1.9.3 路由相关的概念

* router：Vue.js路由管理器，管理路由的
* route：相当于一条路由
* routes：是一组路由
* router-link组件：对<a>标签的封装，导航连接，点击跳转到不同的内容。**to**属性为目标地址
* router-view组件：路由导航到指定组件后，进行渲染的

### 1.9.4 使用路由

1）Vue.js 路由要导入 vue-router库

2）使用步骤

1. **定义路由所需的组件**			就是路由跳转的页面
2. **定义路由 每个路由都由两部分组成  path(路径URL)和component(组件页面)**
3. **创建router路由器实例，管理路由**
4. **创建Vue实例，注入路由对象，使用$mount() 指定挂载点**

```
Vue的$mount()为手动挂载
```

3）代码

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        /*
            1.router 是Vue中的路由管理器对象，用来管理路由的
            2.route  是路由对象，一个路由就对应了一条访问路径，一组路由用 routes
            3.每个路由对象，都有两部分：path(路径信息),component(组件)（小页面，JS，HTML，CSS）
            4.router-link 是对a标签的封装，通过to属性  指定链接
            5.router-view 路由访问到指定的组件，进行页面展示的。路由的出口，路由到这就结束并展示组件
        */
    </style>
</head>
<body>
    <div id="app">
        <h1>渣浪.com</h1>

        <p>
            <!-- 添加超链接，router-link 组件来进行导航，to属性指定连接URL路径 -->
            <!-- 当点击链接时，就会访问到当前path对应的component组件 -->
            <!-- 当点击时，跳转显示组件内容 -->
            <router-link to="/home">go to home</router-link>
            <router-link to="/news">go to news</router-link>
        </p>

        <!-- 路由的出口，路由匹配到组件之后，要渲染到这里 -->
        <router-view></router-view>
    </div>
</body>
<!-- 导入vue 与 router库 -->
<script src="js/vue.min.js"></script>
<script src="js/vue-router.min.js"></script>
<script>
    // 1.定义路由所需的组件     页面
    const home = {template:"<div>首页 花边新闻</div>"};
    const news = {template:"<div>新闻 新闻联播</div>"};

    // 2.定义路由 每个路由有两部分 path(访问路径URL),component(组件页面)
    const routes = [
        {path:"/home",component:home},
        {path:"/news",component:news}
    ]

    // 3.创建路由管理器实例     把定义好的路由放到路由管理器中管理
    const router = new VueRouter({
        routes:routes,
    })

    // 4.创建Vue实例 ,将router注入到 Vue实例中，让整个应用都拥有路由的功能
    var VM = new Vue({
        router
    }).$mount("#app");  // 代替el，手动挂载
</script>
</html>
```

### 1.9.5 路由总结

1. router是Vue中的路由管理器对象，用来管理路由的
2. route是路由对象，一个路由就对应了一条访问路径，一组路由用routes表示
3. 每个路由对象都有两部分 path(路径)和component(组件)
4. router-link 是对a标签的封装，通过to属性指定链接
5. router-view 路由访问到指定组件后，进行页面展示的部分