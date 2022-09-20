# Git

## 1 基础知识

### 1.1 常用命令

![img](D:\Typora\Notes\imgs\Git\OPG@$MKX[]H]L}{R$OUP[}3.png)

#### 附：Vim操作

|      |     指令      |   操作   |
| :--: | :-----------: | :------: |
|  1   | vim hello.txt | 新建文档 |
|  2   |      yy       |   复制   |
|  3   |       p       |   粘贴   |
|  4   |      wq       |   保存   |

另，windows里换行符是LF，Linux里换行符是CRLF

### 1.2 版本

![image-20220920210456190](D:\Typora\Notes\imgs\Git\image-20220920210456190.png)

------------------------------------------------------------------------------------------------------------------------------------------------------

![image-20220920210612455](D:\Typora\Notes\imgs\Git\image-20220920210612455.png)

---------------------

### 1.3 分支

![image-20220920213450306](D:\Typora\Notes\imgs\Git\image-20220920213450306.png)

## 2 Github

#### 附--Github访问方法

[Github访问方法 Github被墙最新hosts 每日更新 2022年 - LabVIEWs](![img](file:///C:\Users\Sherry\AppData\Roaming\Tencent\QQ\Temp\%W@GJ$ACOF(TYDYECOKVDYB.png)https://www.labviews.cn/d/16-github-githubhosts-2022)

1. 打开“C:\Windows\System32\drivers\etc”，使用记事本修改“hosts”文件

2. 新增一行，从http://git.yoqi.me/lyq/github-host/raw/master/hosts中找github的IP地址

3. 最后打开cmd，执行 ipconfig/flushdns

![](D:\Typora\Notes\imgs\Git\image-20220920210806126.png)

-----

### 2.1 git remote -v  &  git remote add

![image-20220920210752189](D:\Typora\Notes\imgs\Git\image-20220920210752189.png)

-----------

### 2.2 git push

![image-20220920210838149](D:\Typora\Notes\imgs\Git\image-20220920210838149.png)

### 2.3 git pull

![image-20220920211124102](D:\Typora\Notes\imgs\Git\image-20220920211124102.png)

查看拉取后的本地状态

![image-20220920211403398](D:\Typora\Notes\imgs\Git\image-20220920211403398.png)

### 2.4 git clone

-----------

![image-20220920211507970](D:\Typora\Notes\imgs\Git\image-20220920211507970.png)

----------------

![image-20220920211520140](D:\Typora\Notes\imgs\Git\image-20220920211520140.png)

--------



![image-20220920211823029](D:\Typora\Notes\imgs\Git\image-20220920211823029.png)

-----

#### 2.4.1 http协议

如果使用http协议访问需要有一个登录的操作

![image-20220920212108098](D:\Typora\Notes\imgs\Git\image-20220920212108098.png)

--------------

![image-20220920212303097](D:\Typora\Notes\imgs\Git\image-20220920212303097.png)

#### 2.4.2 SSH协议

通过SSH协议实现免密登录

![image-20220920212745550](D:\Typora\Notes\imgs\Git\image-20220920212745550.png)

----------

相关设置步骤为

![image-20220920212843797](D:\Typora\Notes\imgs\Git\image-20220920212843797.png)

----

![image-20220920212857810](D:\Typora\Notes\imgs\Git\image-20220920212857810.png)

---------

查看公钥

![image-20220920213107698](D:\Typora\Notes\imgs\Git\image-20220920213107698.png)

----

![image-20220920213120975](D:\Typora\Notes\imgs\Git\image-20220920213120975.png)

--------

![image-20220920213136988](D:\Typora\Notes\imgs\Git\image-20220920213136988.png)

-----

![image-20220920213148994](D:\Typora\Notes\imgs\Git\image-20220920213148994.png)

-------

![image-20220920213205660](D:\Typora\Notes\imgs\Git\image-20220920213205660.png)

-------



### 2.5 团队内协作

逻辑关系

![image-20220920214518401](D:\Typora\Notes\imgs\Git\image-20220920214518401.png)

----

![image-20220920212630530](D:\Typora\Notes\imgs\Git\image-20220920212630530.png)

------

![image-20220920212642425](D:\Typora\Notes\imgs\Git\image-20220920212642425.png)

--------

![image-20220920212650324](D:\Typora\Notes\imgs\Git\image-20220920212650324.png)

-------

![image-20220920212703205](D:\Typora\Notes\imgs\Git\image-20220920212703205.png)

### 2.6 团队间协作

![image-20220920212501194](D:\Typora\Notes\imgs\Git\image-20220920212501194.png)

-----

![image-20220920212511387](D:\Typora\Notes\imgs\Git\image-20220920212511387.png)

--------

![image-20220920212524282](D:\Typora\Notes\imgs\Git\image-20220920212524282.png)

------

### 

## 3 IDEA集成Git

![image-20220920213241626](D:\Typora\Notes\imgs\Git\image-20220920213241626.png)