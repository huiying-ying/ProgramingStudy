# Git

## 1 基础知识

### 1.1 常用命令

![img](imgs\Git\OPG@$MKX[]H]L}{R$OUP[}3.png)

#### 附：Vim操作

|      |     指令      |   操作   |
| :--: | :-----------: | :------: |
|  1   | vim hello.txt | 新建文档 |
|  2   |      yy       |   复制   |
|  3   |       p       |   粘贴   |
|  4   |      wq       |   保存   |

另，windows里换行符是LF，Linux里换行符是CRLF

### 1.2 版本

![image-20220920210456190](imgs\Git\image-20220920210456190.png)

------------------------------------------------------------------------------------------------------------------------------------------------------

![image-20220920210612455](imgs\Git\image-20220920210612455.png)

---------------------

### 1.3 分支

![image-20220920213450306](imgs\Git\image-20220920213450306.png)
那么遇到冲突怎么办呢

无冲突时正常合并，若有冲突时，有如下场景

![image-20220922012716292](imgs\Git\image-20220922012716292.png)

查看状态得知

![image-20220922013916033](imgs\Git\image-20220922013916033.png)

对第一项进行修改并提交，再次查看状态，可以发现该项变绿

![image-20220922014052302](imgs\Git\image-20220922014052302.png)

全部修改后，merge状态下的最后一次提交**不需要加最后的文件名**

![image-20220922014422387](imgs\Git\image-20220922014422387.png)

此时有冲突的merge彻底结束了

## 2 Github

#### 附--Github访问方法

[Github访问方法 Github被墙最新hosts 每日更新 2022年 - LabVIEWs](![img](file:///C:\Users\Sherry\AppData\Roaming\Tencent\QQ\Temp\%W@GJ$ACOF(TYDYECOKVDYB.png)https://www.labviews.cn/d/16-github-githubhosts-2022)

1. 打开“C:\Windows\System32\drivers\etc”，使用记事本修改“hosts”文件

2. 新增一行，从http://git.yoqi.me/lyq/github-host/raw/master/hosts中找github的IP地址

3. 最后打开cmd，执行 ipconfig/flushdns

![](imgs\Git\image-20220920210806126.png)

-----

### 2.1 git remote -v  &  git remote add

![image-20220920210752189](imgs\Git\image-20220920210752189.png)

-----------

### 2.2 git push

![image-20220920210838149](imgs\Git\image-20220920210838149.png)

报错解决：

如果远端分支有了变化，本地分支是基于当前远端分支的历史版本进行修改的，那么在提交并push后会报如下错误

![image-20220922020900091](imgs/Git/image-20220922020900091.png)

这种情况需要用一下两行代码解决

```
# 其中origin是远端的别名，main是分支

git pull origin main

git push -u origin main
```

![image-20220922020541125](imgs/Git/image-20220922020541125.png)

### 2.3 git pull

![image-20220920211124102](imgs\Git\image-20220920211124102.png)

查看拉取后的本地状态

![image-20220920211403398](imgs\Git\image-20220920211403398.png)

### 2.4 git clone

-----------

![image-20220920211507970](imgs\Git\image-20220920211507970.png)

----------------

![image-20220920211520140](imgs\Git\image-20220920211520140.png)

--------



![image-20220920211823029](imgs\Git\image-20220920211823029.png)

-----

#### 2.4.1 http协议

如果使用http协议访问需要有一个登录的操作

![image-20220920212108098](imgs\Git\image-20220920212108098.png)

--------------

![image-20220920212303097](imgs\Git\image-20220920212303097.png)

#### 2.4.2 SSH协议

通过SSH协议实现免密登录

![image-20220920212745550](imgs\Git\image-20220920212745550.png)

----------

相关设置步骤为

![image-20220920212843797](imgs\Git\image-20220920212843797.png)

----

打开用户的目录，直到看到.ssh文件夹

删除.ssh目录

```
rm - rvf .ssh
```

运行命令生成.ssh密钥目录

```
ssh-keygen -t rsa -C 邮箱@.com
```

之后操作3次enter即可生成

![image-20220920212857810](imgs\Git\image-20220920212857810.png)

---------

 查看公钥文件

![image-20220920213107698](imgs\Git\image-20220920213107698.png)

----

在Gitlab中添加ssh设置

![image-20220920213120975](imgs\Git\image-20220920213120975.png)

--------

![image-20220920213136988](imgs\Git\image-20220920213136988.png)

-----

![image-20220920213148994](imgs\Git\image-20220920213148994.png)

### 2.5 团队内协作

![image-20220921012154193](imgs\Git\image-20220921012154193.png)

虽然任意人有读的权限，即可以clone下来

![image-20220921164806539](imgs/Git/image-20220921164806539.png)

但是如果想要push，就会报如下错误

![image-20220921164911634](imgs/Git/image-20220921164911634.png)

因此需要给团队内其他成员设置权限，步骤如下

![image-20220921165013184](imgs/Git/image-20220921165013184.png)

------

![image-20220921165103616](imgs/Git/image-20220921165103616.png)

然后点击Add people，输入用户名进行查找

![image-20220921165129406](imgs/Git/image-20220921165129406.png)

----

![image-20220921165209885](imgs/Git/image-20220921165209885.png)

----

![image-20220921165241245](imgs/Git/image-20220921165241245.png)

将邀请链接Pending Invite发给团队内成员，成员复制后在gitlab中搜索，在如下页面中同意邀请。

![image-20220921165324362](imgs/Git/image-20220921165324362.png)

此后再push，相比上次就会push成功

![image-20220921165403980](imgs/Git/image-20220921165403980.png)

### 2.6 团队间协作

![image-20220920214518401](imgs\Git\image-20220920214518401.png)

外部人员修改时通过链接或者查找锁定原代码，点右上角fock叉一份到自己的仓库

![image-20220920212511387](imgs\Git\image-20220920212511387.png)

修改-->add-->commit-->push  到自己仓库后，点上方的pull request

![image-20220920212524282](imgs\Git\image-20220920212524282.png)

之后create request后编辑好注释和留言后完成

![image-20220920212630530](imgs\Git\image-20220920212630530.png)

-----

![image-20220920212642425](imgs\Git\image-20220920212642425.png)

内部管理者可以看到request，经过审查后合并到本仓库

![image-20220920212650324](imgs\Git\image-20220920212650324.png)

最后提交合并后的版本

![image-20220920212703205](imgs\Git\image-20220920212703205.png)

## 3 IDEA集成Git

![image-20220920213241626](imgs\Git\image-20220920213241626.png)
