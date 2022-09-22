# pyQT

## 0 学习路线

- Qt Designer
- PyQt5基本窗口控件（QMainwindow、Qwidget、Qlabel、QLineEdit、菜单、工具栏等）
- PyQt5高级组件（QTableView、QListView、容器、多线程等）
- PyQt5布局管理（QBoxLayout、QGridlayout、QFormLayout、嵌套布局等）
- PyQt5信号与槽（事件处理、数据传递等）
- PyQt5图形与特效（定制窗口风格、绘图、QSS与UI美化、不规则窗口、设置样式等）
- PyQt5扩展应用（制作PyQt5安装程序、数据处理、第三方绘图库在PyQt5中的应用、UI自
  动化测试等）

搭建PyQt5开发环境

必须使用两个类：QApplication和QMidget。都在pyQts.Qtwidgets。

## 1 前期配置

### 1.1 PyCharm配置PyQt5

打开PyCharm，在setting里打开external tools，点击左上角的加号，作如下配置：

#### （1）配置QtDesigner

Name: QtDesigner
Group: Qt
Program: xxxxx\xxxxxx\designer.exe(参考路径：D:\Anaconda3\Library\bin\designer.exe)
Working Directory: $ProjectFileDir$
![在这里插入图片描述](imgs/Qt/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ2hhb0ZlaUxp,size_19,color_FFFFFF,t_70,g_se,x_16)

这里重点说一下这一步：
Program填写designer.exe的绝对路径，这里的路径因人而异，如果跟我一样是使用Anaconda创建的虚拟环境那么路径应该跟下面这条路径类似。

```
D:\Anaconda3\Library\bin\designer.exe
```

#### （2）配置PyUIC

Name中输入：PyUIC
Program选择PyQt 虚拟环境pygui安装目录中的路径（参考路径：D:\Anaconda3\envs\pygui\Scripts\pyuic5.exe）
Arguments为$FileName$ -o $FileNameWithoutExtension$.py
Working directory为：$FileDir$
如下图所示：

![在这里插入图片描述](imgs/Qt/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ2hhb0ZlaUxp,size_19,color_FFFFFF,t_70,g_se,x_14)

### 1.2 Pycharm使用PyQt

使用QtDesigner进行界面设计：在工具栏找到“Tools”–>Qt–>QtDesigner即可进行界面设计，设计完成会保存为一个.ui文件，右键点击选择external tools里的PyUIC即可将其转为一个.py文件。

## 2 Qt Designer

将图形（.ui）文件自动生成代码（.py）

方法一：python -m PyQt5.uic.pyuic demo.ui -o demo·py

方法二：调用扩展工具

### 2.1 布局与分割线

包括水平布局、垂直布局、栅格布局、表单布局，可以轻易地实现等距规则的排列各个控件

此外，水平、垂直分割线可以在上述布局的基础上，将不同类别的控件间隔开

### 2.2 尺寸

- 设置 minimumSize 和 maximumSize 设定最小、最大的边界值

- 尺寸策略（sizePolicy）

读取期望尺寸  sizeHint  （和控件的当前尺寸没有关系）

```
self.pushButton.sizeHint（）.width（）
self.pushButton.sizeHint（）.height（）
```

其中会常用到的有expanding，比如水平延展部分设置了2，就是在水平方向上永远占其他控件宽度的2倍

### 2.3 伙伴关系

Edit --> 编辑伙伴 --> 将相关联的两个空间连接在一起

![image-20220921204244614](imgs/QT/image-20220921204244614.png)

结合 **热键** 进行使用

![image-20220921204608939](imgs/QT/image-20220921204608939.png)

可以实现 **alt + 相应字母** 直接光标移在相应的输入区

### 2.4 修改控件的Tab顺序

默认Tab（光标移动）的顺序是防置控件的顺序

方法一：Edit --> 编辑Tab顺序

方法二：右键 --> 制表符顺序

### 2.5 信号(signal)与槽(slot)

信号：是由对象或控件发射出去的消息，可以理解为事件

按钮的单击事件——当单击按钮时，按钮就会向外部发送单击的消息，这些发送出去的信号需要一些代码来拦截，这些代码就是**槽**
槽：本质上是一个函数或方法，可以理解为事件函数

需要将信号和槽绑定：一个信号可以和多个槽绑定，一个槽可以拦截多个信号

Edit --> 编辑信号和槽 --> 鼠标点击不放开，拖动到要控制的控件上

示例1：关闭窗口

![image-20220921220049724](imgs/QT/image-20220921220049724.png)

示例2：控制输入是否显示和是否可用

![image-20220921221332918](imgs/QT/image-20220921221332918.png)

### 2.6 菜单栏、工具栏

右键添加或删除

![image-20220921223627346](imgs/QT/image-20220921223627346.png)

对于编辑各个动作

![image-20220921223532444](imgs/QT/image-20220921223532444.png)

## 3 窗口

### 3.1 主窗口类型

- QMainWindow：可以包含菜单栏、工具栏、状态栏和标题栏，是最常见的窗口形式
- QD1alog：是对话窗口的基类。没有菜单栏、工具栏、状态栏。
- QWidget：不确定窗口的用途，就使用QWidget。

示例：继承QMainWindow的类的实现



### 3.2 窗口居中

需要手动计算，确定窗口左上角的坐标

![image-20220922111917440](imgs/QT/image-20220922111917440.png)

### 3.3 退出窗口

![image-20220922112259335](imgs/QT/image-20220922112259335.png)

![image-20220922112524208](imgs/QT/image-20220922112524208.png)

![image-20220922112621075](imgs/QT/image-20220922112621075.png)

### 3.4 屏幕坐标系

![image-20220922112920138](imgs/QT/image-20220922112920138.png)

![image-20220922114119210](imgs/QT/image-20220922114119210.png)

![image-20220922113227045](imgs/QT/image-20220922113227045.png)

两种方式相比，第一种坐标是整体窗口的数据；第二种则是工作区，不包括菜单栏。两者的宽和高都是工作区的数据。

![image-20220922113333777](imgs/QT/image-20220922113333777.png)

![image-20220922113602468](imgs/QT/image-20220922113602468.png)

第三种方法的坐标是工作区，宽和高则是整个窗口的数据。

![image-20220922113724442](imgs/QT/image-20220922113724442.png)

![image-20220922113950899](imgs/QT/image-20220922113950899.png)

总结

![image-20220922114341481](imgs/QT/image-20220922114341481.png)

![image-20220922114616413](imgs/QT/image-20220922114616413.png) 

在windows下的效果

![image-20220922115205963](imgs/QT/image-20220922115205963.png)

### 3.5 图标

![image-20220922115717476](imgs/QT/image-20220922115717476.png)

![image-20220922115732590](imgs/QT/image-20220922115732590.png)

![image-20220922115916243](imgs/QT/image-20220922115916243.png)

## 4 控件

### 4.1 显示提示信息

窗口/控件.setToolTip('Message')

![image-20220922121944323](imgs/QT/image-20220922121944323.png)

启动代码

![image-20220922122031025](imgs/QT/image-20220922122031025.png)

### 4.2 QLabel

![image-20220922122437140](imgs/QT/image-20220922122437140.png)

![image-20220922122648308](imgs/QT/image-20220922122648308.png)

![image-20220922123352656](imgs/QT/image-20220922123352656.png)

![image-20220922122855953](imgs/QT/image-20220922122855953.png)



![image-20220922123300002](imgs/QT/image-20220922123300002.png)

![image-20220922123449668](imgs/QT/image-20220922123449668.png)

![image-20220922123624089](imgs/QT/image-20220922123624089.png)

![image-20220922123925115](imgs/QT/image-20220922123925115.png)

## 5 报错解决

### 5.1 QtWebEngineWidgets

![image-20220921142704247](imgs\Qt\image-20220921142704247.png)

QtWebKitWidgets 是老版的代码，新版需要替换成 QtWebEngineWidgets 的包

![image-20220921142508991](C:\Users\Sherry\AppData\Roaming\Typora\typora-user-images\image-20220921142508991.png)

相应代码也许进行更换

![image-20220921142842228](imgs\Qt\image-20220921142842228.png)
