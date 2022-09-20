# Django

## 1 基本介绍

### 1.1 组件

- 基本配置文件、路由系统

- 模型层（M）--连接数据库、模板层（T）--显示、视图层（V）

- Cookies和Session

- 分页及发邮件

- Admin管理后台

### 1.2 用途

- 用途一：网站、微信公众号、小程序后端开发，即与http请求相关的

- 用途二：人工智能平台融合

### 1.3 安装

python关于虚拟环境的操作

```
conda activate 环境名

conda info --env  可以获得所有虚拟环境

conda create -n py38 python=3.8  创建虚拟环境（以python3.8示例）
```

在需要的虚拟环境下 conda install django

## 2 项目结构

### 2.1 创建项目

```
django-admin startproject 项目名
```

### 2.2 启动服务

#### 第一种：测试开发阶段

1. cd 项目文件夹

2. 进入项目文件夹后，执行  

   ```
   python3 manage.py runserver 
   ```

   启动 django 服务【该启动方式下，Django在前台启动服务，默认监听8000端口】

3. 浏览器访问http://127.0.0.1:8000可看到django的启动页面

注：更换端口可以用  python3 manage.py runserver 端口号 

### 2.3 关闭服务

#### 方式1：在runserver启动终端下

执行Ctrl + c 可关闭Django服务

#### 方式2：在其他终端下

###### sudo lsof -i:8000  

查询出8000端口对应的Django的进程id，如下举例中带 (**LISTEN) **字样的 **6151** 即为所寻找的 Id

```
ubutnu0@ubuntu:~/NetFingerTest/NetFingerTest$ sudo lsof -i:8000
COMMAND  PID    USER   FD   TYPE DEVICE SIZE/OFF NODE NAME
python3 6151 ubutnu0    4u  IPv4 120028      0t0  TCP localhost:8000 (LISTEN)
```

###### kill -9 对应Django进程id

```
ubutnu0@ubuntu:~/NetFingerTest/NetFingerTest$ kill -9 6151
```

#### 报错

Error:That port is already in use.

表名端口已被使用，当前Django启动时，默认监听的8000端口已被其他进程占用

### 2.4 结构解析

tree NetFingerTest

输出当前项目结构

#### 项目同名文件夹

**_init_**                     --Python包的初始化文件
**wsgi.py**             --WEB服务网关的配置文件– Django正式启动时，需要用到
**urls.py**              --项目的主路由配置–HTTP请求进入Django时，优先调用该文件
**settings.py**      --项目的配置文件-包含项目启动时需要的配置

#### manage.py

包含项目管理的子命令，如

```
python3 manage.py runserver 启动服务

python3 manage.py startapp 创建应用

python3 manage.py migrate 数据库迁移
```

另外直接执行python3 manage.py 可列出所有的 Django 子命令

#### settings.py

包含了Django项目启动的所有配置项：公有配置、自定义配置

配置的格式例：BASE_DIR = 'XXXX'

##### 公有配置

###### BASE_DIR

用于绑定当前项目的绝对路径(动态计算出来的)，所有文件夹都可以依赖此路径

###### DEBUG

用于配置Django项目的启动模式取值——True表示开发环境中使用开发调试模式(用于开发中)，False表示当前项目运行在生产环境中

###### ALLOWED_HOSTS

默认是空的，只有请求头为127.0.0.1，localhost能访问本项目 

['*']表示任何请求头的host都能访问到当前项目

如果要在局域网其他主机也能访问此主机的Django服务，启动方式为

```
python3 manage.py runserver 0.0.0.0:5000
```

此时需要在ALLOWED_HOSTS中配置，将启动服务的主机的局域网IP添加到其中，如下所示

```
ALLOWED_HOSTS = ['192.168.192.150']
```

如此在其他主机上对页面进行访问才能不报错

###### INSTALLED_APPS 

指定当前项目中安装的应用列表

###### MIDDLEWARE 

用于注册中间件

###### TEMPLATES 

用于指定模板的配置信息

###### DATABASES 

用于指定数据库的配置信息

###### LANGUAGE_CODE

表明语言是英文，将其切换为 zh-Hans 就可改为中文

```
LANGUAGE_CODE = 'en-us'
```

TIME_ZONE

表明时区，默认为格林威治 **‘UTC’**，改为东八区为**Asia/Shanghai**

###### ROOT_URLCONF 

用于配置主url配置''

##### 自定义配置

要求必须是大写，不与公有配置重名，引入方式为

```
from django.conf import settings
```

## 3 URL

#### **定义**

——即统一资源定位符Uniform Resource Locator

#### **作用**

——用来表示互联网上某个资源的地址

#### **结构**

URL的一般语法格式为（*注:[代表其中的内容可省略*)︰

协议  ://  域名/IP  +  [端口]  /  路由  [?查询字符串]  [#锚点]SSSS

```
protocol :// hostname[:port] / path [?query][#fragment].

eg：http://tts.tmooc.cn/video/showVideo?menuld=657421&version=AID999#subject
```

- protocol --协议

  **http** 通过HTTP访问该资源。格式http://

  **https**通过安全的HTTPS访问该资源。格式https://

  **file**资源是本地计算机上的文件。格式: file:///

- hostname --主机名

  是指存放资源的服务器的域名系统(DNS)主机名、域名或IP地址

- port --端口

  整数，可选，省略时使用方案的默认端口
  各种传输协议都有默认的端口号，如http的默认端口为80

- path --路由地址

  由零或多个“/”符号隔开的字符串，一般用来表示主机上的一个目录或文件地址。路由地址决定了服务器端如何处理这个请求

- query --查询

  可选，用于给动态网页传递参数，可有多个参数，用“&”符号隔开，每个参数的名和值用“=”符号隔开。

- fragment --信息片段

  字符串，用于指定网络资源中的片断。例如一个网页中有多个名词解释，可使用fragment直接定位到某一名词解释。

#### 处理URL请求

1. Django 从配置文件中根据 **ROOT URLCOlNF** 找到主路由文件，默认情况下，该文件在项目同名目录下的urls（例如mysite1/mysite1/urls.py）
2. Django加载主路由文件中的 urlpatterns变量[包含很多路由的数组]
3. 依次匹配 urlpatterns 中的 path，匹配到第一个合适的中断后续匹配
4. 匹配成功–调用对应的视图函数处理请求，返回响应
5. 匹配失败–返回404响应

## 4 视图函数

--视图函数是用于接收一个浏览器请求(HttpRequest对象)并通过HttpResponse对象返回响应的函数。此函数可以接收浏览器请求并根据业务逻辑返回相应的响应内容给浏览器。

#### 语法

```
def xxx_view(request[, 其他参数...]):

  return HttpResponse对象
```

示例

```
# file:<项目同名文件夹下>/views.py
from django.http import HttpResponse

def page1_view(request):

  html = "<h1>这是第1个页面</h1>"

  return HttpResponse(html)
```

在urls,py文件中引用views中的视图函数时要将其包引入，即from . import views  (从当前目录下引入)

## 5 路由配置

### 5.1 path() 函数

#### 导入

```
from django.urls import path
```

#### 语法

```
path(route, views, name=None)
```

#### 参数

**route** --字符串类型，匹配的请求路径

**views** --指定路径所对应的视图处理函数的名称（*不能加括号，否则返回的是值，而不是函数名*）

**name** --为地址起别名，在模板中地址反向解析时使用

### 5.2 path转换器

#### 语法

<<转换器类型:自定义名>>

#### 作用

若转换器类型匹配到对应类型的数据，则将数据按照关键字传参的方式传递给视图函数

#### 示例

```
path('page/\<int:page>', views.xxx)
```

#### 分类

| 转换器类型 |                          作用                           |                           样例                            |
| :--------: | :-----------------------------------------------------: | :-------------------------------------------------------: |
|    str     |               匹配除了‘/'之外的非空字符串               | "y1/users/<<str:username>>"  匹配/v1/users/**guoxiaonao** |
|    int     |          匹配0或任何正整数，返回一个int型变量           |          "page/<<int:page>>"  匹配/page/**100**           |
|    slug    | 匹配任意由ASCII字母或数字以及连字符和下划线组成的短标签 |   "detail/<<slug:sl>>"  匹配/detail/**this-is-django**    |
|    path    |             匹配非空字段，包括路径分隔符'/'             |      "v1/users/<<path:ph>>"  匹配/v1/goods/**a/b/c**      |

###  5.3 re_path()函数

在url的匹配过程中可以使用正则表达式进行精确匹配

#### 语法

```
re_path(reg, view, name=xxx)
```

正则表达式为命名分组模式**(?P<name>pattern)**; 匹配提取参数后用**关键字传参方式**传递给视图函数 

#### 样例

#可匹配http://127.0.0.1:8000/20/mul/40

#不可匹配 http://127.0.0.1:8000/200/mul/400

```
urlpatterns = [
	path('admin/" , admin.site.urls),
	re_path(r'^(?P<x>ld{1,2})/(?P<op>\w+)/(?P<y>\d{1,2})$' ,views.cal_view),
]
```

再举例输出生日

```
#http://127.0.0.1:8000/birthday/年4/月2/日2

re_path(r'^birthday/(?P<year>\d{4})/(?P<month>\d{1,2})/(?P<day>\d{1,2})$', views.birthday_view1),
```

## 6 请求和响应

**请求**是指浏览器端通过HTTP协议发送给服务器端的数据
**响应**是指服务器端接收到请求后做相应的处理后再回复给浏览器端的数据

### 6.1 请求

#### **起始行**——请求的方法  路由  协议及版本号

1. 请求方法

   原本的 GET、POST、HEAD，后又新增了OPTIONS、PUT、DELETE、TRACE 和 CONNECT

   - GET

     请求指定的页面信息，并返回实体主体

   - POST

     向指定资源**提交数据**进行处理请求（例如提交表单或者上传文件)。数据被包含在请求体中。POST请求可能会导致新的资源的建立和/或已有资源的修改。

   - HEAD

     类似于get请求，只不过返回的响应中没有具体的内容，用于获取报头

   - OPTIONS

     允许客户端查看服务器的性能

   - PUT

     从客户端向服务器传送的数据取代指定的文档的内容

   - DELETE

     请求服务器删除指定的页面。

   - TRACE

     回显服务器收到的请求，主要用于测试或诊断

   - CONNECT

     HTTP/1.1协议中预留给能够将连接改为管道方式的代理服务器

   实际为视图函数中的第一个参数 request，即 HttpRequest 对象。Django 接收到 http 协议的请求后，会根据请求数据报创建 HttpRequest 对象，该对象通过**属性**描述了请求的所有相关信息。

   - path_info

     URL字符串

   - method

     字符串，表示HTTP请求方法，就是上面所列

   - GET

     QueryDict 查询字典的对象，包含get请求方式的所有数据

   - POST

     QueryDict查询字典的对象，包含post请求方式的所有数据

   - FILES

     类似于字典的对象，包含所有的上传文件信息

   - COOKIES

     Python字典，包含所有的cookie，键和值都为字符串session:似于字典的对象，表示当前的会话

   - session

     似于字典的对象，表示当前的会话

   - body

     字符串，请求体的内容(POST或PUT)

   - schenme

     请求协议('http'/'https')

   - request.get_full_path()

     请求的完整路径

   - request.META

     请求中的元数据(消息头)   - request,META['REMOTE_ADDR']:客户端IP地址

2. 路由

3. 协议及版本号

#### **headers（请求头）**——多组 Key-Value

#### **body（请求体）**——数据

```
cd ../      返回上一级目录

cp -fr 被复制的文件名/ 复制过去的文件名/.    复制文件夹到指定文件夹

ls  当前文件夹下的文件

rm -fr NetFingerTest/   删掉名为NetFingerTest的文件夹
```

### 6.2 响应

###### 起始行——请求协议  状态码

###### headers（响应头）

###### body（响应体）

#### 状态码（HTTP Status Code）

常见状态码——

200 --请求成功    301 --永久重定向-资源（网页等）被永久转移到其他URL    302 --临时重定向    

404 --请求的资源（网页等）不存在    500 --内部服务器错误

| 分类 |                    分类描述                    |
| :--: | :--------------------------------------------: |
| 1**  |  信息，服务器收到请求，需要请求者继续执行操作  |
| 2**  |           成功，操作被成功接收并处理           |
| 3**  |       重定向，需要进一步的操作以完成请求       |
| 4**  |   客户端错误，请求包含语法错误或无法完成请求   |
| 5**  | 服务器错误，服务器在处理请求的过程中发生了错误 |

#### 响应对象

##### **构造函数格式**

```
HttpResponse(content=响应体, content_type=响应体数据类型, status=状态码)
```

**作用**：向客户端浏览器返回响应，同时携带响应体内容 

常用Content-Type

- 'text/ html'                        (默认的，html文件)
- 'text/plain’                         (纯文本)
- 'text/css'                            (css文件)
- 'text/javascript’                (js文件)
- 'multipart/form-data’     (文件提交)
- 'application/json'            (json传输)
- 'application/xml’             (xml文件)

##### HttpResponse 子类

| 类型                    | 作用           | 状态码 |
| ----------------------- | -------------- | ------ |
| HttpResponseRedirect    | 重定向         | 302    |
| HttpResponseNotModified | 未修改         | 304    |
| HttpResponseBadRequest  | 错误请求       | 400    |
| HttpResponseNotFound    | 没有对应的资源 | 404    |
| HttpResponseForbidden   | 请求被禁止     | 403    |
| HttpResponseServerError | 服务器错误     | 500    |

eg.

```python
from django.http import HttpResponseRedirect
...
return HttpResponseRedirect('/page/1' )
```

### 6.3 GET请求和POST请求

无论是GET还是POST，统一都由视图函数接收请求，通过判断request.method区分具体的请求动作。

#### 6.3.1 GET

产生GET请求的场景

- 浏览器地址栏中输入URL,回车后

- <a href="地址?参数=值&参数=值">
- form表单中的method为get

有数据需要传递给服务器时，使用**查询字符串（Query String）**传递

```
request.GET['参数名'] #QueryDict

request.GET.get('参数名','默认值)

request.GET.getlist('参数名')

# mypage?a=100&b=200&c=300&b=400
# request.GET=QueryDict({'a':[ '100']，'b':[ '200' , '400'],'c' :['300']})
# a = request.GET['a']
# b = request.GET['b']# Error
```

- request.GET 和 request.GET.get

  二者的区别在于，当 url 中没有查询字符串时，直接GET会报错，因为没有写相应的查询字符，而 get 就不会报错，即 get 更普适。

- request.GET.getlist

  对于url = http://127.0.0.1:8000/test_get_post?a=400&a=200&a=100的情况，key对应不同的value，则调用request.GET 会得到 QueryDict({'a':[  '400','200','100']})  ，但调用request.GET['a'] 就只能得到最后一个值，这个时候就需要用到request.GET.getlist('a ')，得到的是['6', '4']

#### 6.3.2 POST

一般用于向服务器提交大量或者隐私数据

客户端通过表单等POST请求将数据传递给服务器端，如：

```
<form method= 'post' action=" /login">
    姓名:<input type="text" name="username">
    <input type= 'submit' value='登陆'>
</form>
```

将上述字符串定义为一个全局变量 POST_FORM，在GET对请求的处理中提交

```
return HttpResponse(POST_FORM)
```

使用post方式接收客户端数据

```
request.POST['参数名']# request.POST 绑定QueryDict

request.PoST.get('参数名',''）

request.PoST.getlist('参数名')
```

取消 csrf 验证,否则 Django 将会拒绝客户端发来的POST请求，报403（禁止访问）响应

- 取消 csrf 验证

  -禁止掉 settings.py 中 MIDDLEWARE 中的 CsrfViewsMiddleWare 的中间件，即注释掉下示语句

  ```
  MIDDLEWARE = [
  ...
  #'django.middleware.csrf.csrfviewMiddleware',
  ...
  ]
  ```

## 7 模板层

### 7.1 Django的设计模式

作用：降低模块间的耦合度(解耦)

MVC 代表 **Model-View-Controller (模型-视图-控制器)** 模式。

<img src="D:\Typora\Notes\imgs\Django\image-20220513144443696.png" alt="image-20220513144443696" style="zoom:50%;" />

- M 模型层(Model)，主要用于对数据库层的封装
- V 视图层(View)，用于向用户展示结果(WHAT + HQW )
- C 控制(Controller ，用于处理请求、获取数据、返回结果(重要)

MVC 的升级版——MTV代表 **Model-Template-View(模型-模板-视图) **模式。

<img src="D:\Typora\Notes\imgs\Django\image-20220513144839689.png" alt="image-20220513144839689" style="zoom:50%;" />

- M 模型层(Model)负责与数据库交互

- T 模板层(Template)负责呈现内容到浏览器(HOW)

- V 视图层(View)是核心，负责接收请求、获取数据、返回结果(WHAT)

### 7.2 模板层

创建模板文件夹<项目名>/templates

在 settings.py 中 TEMPLATES 配置项

1. BACKEND——指定模板的引擎
2. **DIRS**——模板的搜索目录(可以是一个或多个)
3. APP_DIRS——是否要在应用中的templates文件夹中搜索模板文件
4. OPTIONS——有关模板的选项

配置项中需要修改部分

```
设置DIRS - 'DIRS': [os.path.join(BASE_DIR, 'templates')],
```

操作：在项目文件夹下建立新的 Directory, 名为 templates（建立之后与 manage.py 平级），之后在 settings.py 中修改 **DIRS**

```
TEMPLATES = [
    {
        ...
        'DIRS': [path(BASE_DIR, 'templates')],
        ...
    },
]
```

#### 加载方式

- 方案1：通过loader 获取模板，通过 HttpResponse 进行响应

在视图函数中

```python
from django.template import loader
#1.通过1oader加载模板
t = loader. get_template("模板文件名")
#2.将t转换成HTML字符串
html = t.render(字典数据)
#3.用响应对象将转换的字符串内容返回给浏览器
return HttpResponse(htm1)
```

之后在templates中新建html文件，设计页面后，在 urls.py 文件中创建对应路由即可

- 方案2：使用 render() 直接加载并响应模板  --（更简便）

在视图函数中

```python
from django.shortcuts import render
return render(request, '模板文件名', 字典数据)
```

#### 视图与模板层之间的交互

——通过字典

1，视图函数中可以将Python变量封装到**字典**中传递到模板

样例

```python
def xxx_view(request)
	dic = {
		"变量1":"值1",
		"变量2":"值2",
	}
	return render(request, 'xxx.html ', dic)
```

譬如

```
 dic = {'username':'Sherry', 'age':18}
```

2，模板中，我们可以用 **{{变量名}}** 的语法调用视图传进来的变量

譬如

```
{{username}}是模板
```

### 7.3 模板层——变量和标签

#### 变量

##### 能传递到模板中的数据类型

- str        -字符串
- int        -整型
- list       - 数组
- tuple   -元组
- dict      -字典
- func    - 方法
- obj      -类实例化的对象

##### 在模板中使用变量语法

-{{变量名}}

-{{变量名.index}}

-{{变量名.key}}

-{{对象,方法}}  (没有括号)

-{{函数名}}

#### 标签

作用——将一些服务器端的功能嵌入到模板中，例如流程控制等

标签语法

```python
{% 标签 %}
...
{% 结束标签 %}
```

##### if 标签

```python
{% if 条件表达式1 %}
...
{% elif条件表达式2 %}
...
{% elif条件表达式3 %}
...
{% else %}
...
{% endif %}
```

###### if 标签注意:

1. if 条件表达式里可以用的运算符 ==、!=、<、 >、<=、>=、 in、 not in、is、is not、not、and、or
2. 在 if 标记中使用实际**括号是无效的语法**。如果需要它们指示优先级，则应使用嵌套的 if 标记。

**python有locals()，可以将当前局部变量封装成一个字典**

模板中变量不存在也不会报错

目前出现的编程错误：

'test_computor.html' 显示没加**''**，后又没加**.html**

```
render(request, 'test_computor.html', locals())
```

##### for 标签

语法

```
{% for 变量 in 可迭代对象 %}
	..、循环语句
{% empty %}
	...可迭代对象无数据时填充的语句
{% endfor %}
```

内置变量——forloop

|        变量         |                描述                |
| :-----------------: | :--------------------------------: |
|   forloop.counter   |   循环的当前迭代（从1开始索引）    |
|  forloop.counter0   |   循环的当前迭代（从0开始索引）    |
| forloop.revcounter  |          counter值的倒序           |
| forloop.revcounter0 |         revcounter值的倒序         |
|    forloop.first    |    如果这是最后一次循环，则为真    |
|    forloop.last     |    如果这是最后一次循环，则为真    |
| forloop.parentloop  | 当嵌套循环，parentloop表示外层循环 |

样例

```
{% for name in lst %}
	{{forloop.counter}} {{ name }}
{% empty %}
	当前没数据
{% endfor %}
```

### 7.4 模板层——过滤器和继承

#### 过滤器

定义：在变量输出时对变量的值进行处理

作用：可以通过使用过滤器来改变变量的输出显示

语法：

```
{{变量|过滤器1:'参数值1'|过滤器2:参数值2'...}}
```

 典型过滤器

|      过滤器       |                             说明                             |
| :---------------: | :----------------------------------------------------------: |
|       lower       |                    将字符串转换为全部小写                    |
|       upper       |                    将字符串转换为大写形式                    |
|       safe        |              默认不对变量内的字符串进行html转义              |
|     add: "n"      |                       将value的值增加n                       |
| truncatechars:'n' | 如果字符串字符多于指定的字符数量，那么会被截断。截断的字符串将以可翻译的省略号序列(“...")结尾。 |

safe：默认情况下传进来的变量即使是代码也会被转义为纯文本，在html页面上输出，用了safe就不会对其进行转义

譬如

<script>alert(1111)</script></h3>

没有加safe时就是一串文本，加上的话就是一个弹窗，上面显示1111

#### 继承

模板继承可以使父模板的内容重用，子模板直接继承父模板的全部内容并可以覆盖父模板中相应的块

语法-父模板中：

- 定义父模板中的块 block标签

- 标识出哪些在子模块中是允许被修改的

- block标签:在父模板中定义，可以在子模板中覆盖

  ```html
  {% block info %}
      it's base page
  {% endblock %}
  
  {% block mytitle %}
      <title>Main Page</title>
  {% endblock %}
  ```

  

语法-子模板中：

- 继承模板extends 标签(写在模板文件的第一行)，例如

  ```python
  {%  extends 'base.html' %}
  ```

- 子模板重写父模板中的内容块

  ```python
  {% block block_name %}
  子模板块用来覆盖父模板中 b1ock_name 块的内容
  { % endblock block_name %})
  ```

重写的覆盖规则

- 不重写,将按照父模板的效果显示
- 重写,则按照重写效果显示

注意：模板继承时，服务器端的动态内容无法继承

## 8 url反向解析

### 8.1 url 出现位置

##### 模板【html中】

1, <ahref='url'>超链接</a>

点击后页面跳转至url

2,<form action='url' method='post'>

form表单中的数据用post方法提交至url

##### 视图函数中- 302跳转HttpResponseRedirect('url')

将用户地址栏中的地址跳转到url

QPS：Queries Per Second意思是“每秒查询率”，是一台服务器每秒能够相应的查询次数，是对一个特定的查询服务器在规定时间内所处理流量多少的衡量标准。

TPS：是TransactionsPerSecond的缩写，也就是事务数/秒。它是软件测试结果的测量单位。一个事务是指一个客户机向服务器发送请求然后服务器做出反应的过程。客户机在发送请求时开始计时，收到服务器响应后结束计时，以此来计算使用的时间和完成的事务个数。

### 8.2 书写规范

#### 绝对地址

http://127.0.0.1:8000/page/12，

#### 相对地址

|       相对地址        |                   实际url                    |                             举例                             |
| :-------------------: | :------------------------------------------: | :----------------------------------------------------------: |
| /page/1（开头有 / ）  |         当前协议+ip+端口+该相对地址          | 例 当前为 http://127.0.0.1:8000/page/3，则相对地址对应的为 http://127.0.0.1:8000 + /page/1 |
| page/1（开头没有 / ） | 当前url的最后一个/之前的内容加上该相对地址， | 例 当前地址栏地址为http://127.0.0.1:8000/topic/detail，则相对地址对应为http://127.0.0.1:8000/topic/ + page/1 |

### 8.3 反向解析

--是指在视图或模板中，用path定义的名称来动态查找或计算出相应的路由

##### path函数的语法

```python
 path(route, views, name="别名")
 path( page', views.page_view, name="page_url")
```

根据path中的`name=`关键字传参给url确定了个唯一确定的名字，在模板或视图中，可以通过这个名字反向推断出此url信息

#### 模板中

##### url 标签

```html
{% ur1‘别名’ %}
{% ur1'别名’'参数值1''参数值2' %}
```

例如

```html
{% url 'pagen' '400' %}
{% ur1'person' age=' 18' name=' gxn' %}
```

即在原来 url.py 的文件中的路径基础上添加别名 

<img src="D:\Typora\Notes\imgs\Django\image-20220529151756897.png" alt="image-20220529151756897" style="zoom:67%;" />

响应模板为<img src="D:\Typora\Notes\imgs\Django\image-20220529153250364.png" alt="image-20220529153250364" style="zoom: 67%;" />

或者带参数的

<img src="D:\Typora\Notes\imgs\Django\image-20220529153206061.png" alt="image-20220529153206061" style="zoom:67%;" />

响应模板为<img src="D:\Typora\Notes\imgs\Django\image-20220529153334906.png" alt="image-20220529153334906" style="zoom:67%;" />

#### 视图函数中

调用django中的reverse方法进行反向解析

```python
from django.urls import reverse
reverse('别名'，args=[] ，kwargs={})
例如
print( reverse('pagen' ,args=[300] ) ) 
print( reverse('person'，kwargs={'name' :'xixi' ，age' :18}) )
```

## 9 静态文件

1、配置静态文件的访问路径 [该配置默认存在]

```
 STATIC URL = '/static/'
```

2、配置静态文件的存储路径 STATICEILES DIRS

STATICFIL ES_ DIRS = PATH(BASE_DIR, "static"),

3、访问静态文件

普通的方式在写路径 src 时可以采用绝对路径或者相对路径，django这里提供另外一种方案。

##### 通过{% static %}标签访问静态文件

1.加载static - {% load static %}
2.使用静态资源- {% static '静态资源路径' %}
3.样例 <img src="{% static images/lena.jpg' %}">

<img src="D:\Typora\Notes\imgs\Django\image-20220529160932756.png" alt="image-20220513144443696" />

### 应用

#### 步骤1

用manage.py中的子命令startapp创建应用文件夹

```
python3 manage.py startapp music
```

#### 步骤2

在settings.py的INSTALLED_ _APPS列表中配置安装此应用