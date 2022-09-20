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
![在这里插入图片描述](https://img-blog.csdnimg.cn/2637d1aa56474d97b7c4495110c2c74b.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ2hhb0ZlaUxp,size_19,color_FFFFFF,t_70,g_se,x_16)

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

![在这里插入图片描述](https://img-blog.csdnimg.cn/5ecef9e47eb04e458d7be1be001244ad.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ2hhb0ZlaUxp,size_19,color_FFFFFF,t_70,g_se,x_16)

### 1.2 Pycharm使用PyQt

使用QtDesigner进行界面设计：在工具栏找到“Tools”–>Qt–>QtDesigner即可进行界面设计，设计完成会保存为一个.ui文件，右键点击选择external tools里的PyUIC即可将其转为一个.py文件。
