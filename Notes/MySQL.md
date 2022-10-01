## 1. 基础知识

![image-20220920220144212](imgs\MySql\image-20220920220144212.png)

![image-20220920220156414](imgs\MySql\image-20220920220156414.png)

### 1.1 sql、DB、DBMS关系

DB --DataBase 数据库

DBMS --DataBase Management System 数据库管理系统

SQL --结构化查询语言，标准版适合于所有的数据库

### 1.2 数据库管理系统（DBMS）

Oracle  MySQL   商用最多

DB2  Sybase  

MS SqlServer   支持标准sql的数据库管理系统

### 1.3 语句类型

DQL（数据查询语言） --查询语句，select语句都是

DML（数据操作语言）--insert delete update 对表中数据进行增删改

DDL（数据定义语言） --create drop alter 对表结构增删改

TCL（事务控制语言） --commit提交事务，rollback回放事务

DCL（数据控制语言） --grant授权、remove撤销权限等

#### 注：

行：数据/记录  --字段名、数据类型、相关约束

列：字段

### 1.4 安装与卸载

![image-20220920130944278](imgs\MySql\image-20220920130944278.png)



![image-20220920131047458](imgs\MySql\image-20220920131047458.png)



![image-20220920131130315](imgs\MySql\image-20220920131130315.png)

## 2. 操作指令

### 2.1 导入数据

#### 1）登录MySQL

```命令行
mysql -uroot -p密码
```

#### 2）查看数据库

```
show databases;  // 是MySQL命令而不是SQL语句
```

#### 3）创建数据库

```
create database bjpowernode（文件名）;  // 是MySQL命令而不是SQL语句
```

#### 4）使用数据库

```
use bjpowernode（文件名）;  // 是MySQL命令而不是SQL语句
```

#### 5）查看当前数据库中有哪些表格

```
show tables;  // 是MySQL命令而不是SQL语句
```

#### 6）初始化数据

```
mysql> source 绝对位置\文件名.sql
```

### 2.2 常用命令 

#### 1）删除数据库

```
drop database bjpowernode（文件名）;
```

#### 2）查看表结构

```
desc emp（表名）;
```

#### 3）查看表中的数据

```
select * from emp（表名）;
```

#### 4）查看当前使用的数据库

```
select database();
```

#### 5）查看mysql版本号

```
select version();
```

#### 6）结束一条命令

```
\c
```

#### 7）退出mysql

```
\q

QUIT 

EXIT 
```

#### 8）查看创建表的语句

```
show create table emp（表名）;
```

## 3 数据查询语句（DQL）

### 3.1 基本语句

注：任何sql语句以 ; 结尾，且语句不区分大小写

```
select 字段名1, 字段名2, ... from 表名;

select * from emp;   // 如果是查看所有，用*表示，但实际编程中不建议，因为效率低
```

给查询结构的列重命名

```
select ename,sal * 12 as yearsal from emp;
```

如果是中文名字

```
select ename,sal * 12 as '年薪' from emp;
```

### 3.2 条件查询

##### 语段格式

```
select
	字段,字段
from
	表名
where
	条件;
	
select sal from emp where ename = 'SMITS';
```

##### 判断条件

=  >  <  >=  <=  <>  !=  between...and...  null  and  or  in  like  %  _

###### =     --表示等于

###### <>  --表示不等于，与 != 相同

###### between...and...  --用于数字时左闭右闭，用于字符串时左闭右开，但都要求左小右大

```
sal between 100 and 300   // 表示处于100~300之间，相当于 sal >= 100 and sal <= 300 

between ‘A' and 'D'   // 也可以用在字符串，只不过不常用   （左闭右开）
```

###### null  --不可=null，因为null不是一个值，而是空，一种状态，只能用  is null  ,   is not null 判断

```
select ename,sal,comm from emp where comm is null;

select ename,sal,comm from emp where comm is not null;
```

###### and or  --and 比 or 的优先级高

想要实现sal > 1000的条件下deptno = 20 或者 deptno = 30，直接写的第一种写法其实并不对，考虑到优先级应当在后面加上()

```
select ename,sal,deptno from emp where sal > 1000 and deptno = 20 or deptno = 30;

										(						)

select ename,sal,deptno from emp where sal > 1000 and (deptno = 20 or deptno = 30);
```

###### in  --不是表示范围，而是()所列的选项的并集

```
select ename,job from emp where job = 'SALEMAN' or job ='MANAGER';

select ename,job from emp where job in ( 'SALEMAN' , 'MANAGER');  // 这两种写法是相同的意思

select ename,job from emp where job not in ( 'SALEMAN' , 'MANAGER');
```

###### like  --% 表示多个字符，_表示一个字符

```
select ename from emp where ename like '_A';  // 找第二个字母是A的

select ename from emp where ename like '%A%';  // 找含字母是A的

select ename from emp where ename like '%\_%';  // 找含字母是_的
```

### 3.3 排序语句

##### 语段格式

```
select 
	字段,字段
from
	表名
order by
	字段;
```

默认是升序，**asc** 表示升序，**desc **表示降序

```
select ename,sal from emp order by sal asc;   // 指定升序排列

select ename,sal from emp order by sal desc;  // 指定降序排列
```

越靠前的要求越占到主导地位

```
// 条件1(sal)降序，同条件1的情况下按照条件2(ename)的升序排列

select ename,sal from emp order by sal desc, ename asc;
```

数字表示第几列（不建议用，列的顺序改变后乱了）

```
select ename,sal from emp order by 2;   // 2表示第2列
```

条件与排序能够联合使用，按照顺序执行

```
select 
	字段,字段       3
from
	表名           1
where 
	条件           2
order by
	字段;          4
```

### 3.4 分组函数

--是对一组数据进行处理，**不可直接在where子句中使用**

###### count  计数 	sum  求和	avg  平均值	max  最大值	min  最小值

```
select sum(sal) from emp;

select max(sal) from emp;

select min(sal) from emp;

select avg(sal) from emp;

select count(*) from emp;  // 其中，count(*) 统计的是记录条数，count(字段)统计的是不为null的数据总数量
```

###### ifnull  --数学表达式中有null则计算结果总为null，因此有单行处理函数，其格式为     ifnull(可能为null的字段，被当做什么处理)

```
select ename, ifnull(come,0) as comm from emp;   // 将字段come中的null当做0来处理

select ename, (sal + ifnull(comm,0))*12 as years  from emp;
```

###### group by  --按照某字段进行分组

###### having  --对分组之后的数据再次过滤

```
select max(sal) from emp group by job;
```

先将津贴comn的平均值算出来，再将其作为条件，筛选津贴高于平均值的

```
select ename,sal from emp where sal > (select avg(comm) from emp);

***注意where是先执行的，group没有的话是默认全部，因此直接写where sal > avg(comn) 会报错，因为avg还没算
```

将各个工作岗位的薪资最大值算出

```
select max(sal),job from emp group by job;

// 这里select 后面只允许出现被分组的字段（job）和分组函数（max(sal)）
```

**联合分组**

每个部门不同岗位的最高薪资

```
select deptno,job,max(sal) from emp group by deptno,job;  // 谁在前就先分谁，比如先分各个部门，每个部门再分不同岗位
```

各部门薪资最大值，仅显示大于2900的数据

```
select max(sal),deptno from emp group by deptno having max(sal) > 2900;   // 使用having语句实现

select max(sal),deptno from emp where sal > 2900 group by deptno;  // 使用where语句实现

// 这里where更高效，但是偶尔其不能达到having能实现的功能，譬如要是要求是平均薪资大于2900的话，where后面不能跟分组函数avg
```

##### 执行顺序（当前书写顺序不能变）

```
select 5
	...
from 1
	...
where 2
	...
group 3
	...
having 4
	...
order by 6
```

##### 去重

###### distinct  --只能出现在所有字段的最前面，指的是所有字段联合去重（它不能够高效，因为只是在显示时去重而已）

```
select distinct job, deptno from emp;
```

统计岗位的数量

```
select count(distinct job) from emp;
```

### 3.5 连接查询

内连接：等值连接、非等值连接、自连接  两张表平等

外连接：左外连接（左为主表）、右外连接（右为主表）  分主副表，查主表时捎带查询副表，副表中没有相应数据时自动补充为null，即主表中的数据无条件地全部查询出来

全连接

##### 别名

```
select e.ename, d.deptno from emp e, dept d;  // 给表起别名，起到方便标记的作用
```

###### join...on...  --连接两个表格，join后加另一个表格，on后加连接的条件

加条件仅限制了显示，底层仍旧进行了两个数据个数的乘积次的比较。

##### 内连接--等值连续  条件是等量关系

两个表中部门编号相同的

```
select e.ename, d.deptno from emp e, dept d where e.deptno = d.deptno;  // 92.老语法，不再用了

select e.ename, d.deptno from emp e join dept d on e.deptno = d.deptno;  // 99, 常用
```

##### 内连接--非等值连续  条件是非等量关系

找出员工的工资等级

```
select 
	e.name, e.sal, s.grade 
from 
	emp e 
inner join                         // inner 可以省略，表示内连接
	salgrade s
on
	e.sal between s.losal and s.hisal;
```

##### 内连接--自连接  自己和自己连接，看做是两张表

找出每个员工的上级

```
select
	a.ename as '员工名', b.ename as '领导名'
from
	emp a
inner join
	emp b
on
	a.mgr = b.empno;
```

##### 外连接--左连接

```
select
	a.ename as '员工名', b.ename as '领导名'
from
	emp a
left join    // left 左边是主表，此时king的领导为null，而不是缺失这条记录
	emp b
on
	a.mgr = b.empno;
```

##### 外连接--右连接

```
select
	a.ename as '员工名', b.ename as '领导名'
from
	emp b
right outer join    // right右边是主表，ab两表调换位置   outer可以省略
	emp a
on
	a.mgr = b.empno;
```

找出哪个部门没有员工

```
select
	d.*
from 
	emp e
right join
	dept d
on
	e.deptno = d.deptno
where
	e.empno is null;
```

left和right所比较的是from 后的表和join后的表，因此left和right一定是放在b表的join之前

##### 综合内连与外连

找出每个员工的部门名称，工资等级、上级领导

```
select
	e.ename '员工', d.dname, s..grade, e1.ename '领导'
from 
	emp e
join   // 内连
	dept d
on
	e.deptno = d.deptno
join   // 内连
	salgrade s
on
	e.sal betweeen s.losal and s.hisal
left join  // 左外连
	emp e1
on
	e.mgr = e1.ename;
```

主机名或IP地址：localhost 或者是127.0.0.1，表示本机地址

##### 子查询语句

```
select * from emp where sal > (select avg(sal) from emp);  // where 嵌套子查询语句
```

```
select deptno,avg(sal) as avgsal from emp group by deptno;  // 作为临时表（即下示from嵌套的子句）

select
	t.*, s.grade
from
	(select deptno,avg(sal) as avgsal from emp group by deptno) t     // from 嵌套子查询语句
join
	salgrade s
on
	t.avgsal between s.losal and s.hisal;
```

```
select
	e.ename,
	(select d.dname from dept d where e.deptno = d.deptno) as dname   // select嵌套子查询
from
	emp e;
```

###### union  --将查询结果集相加

```
select ename, job from emp where job = 'MANAGER' or job = 'SALEMAN';  // 第1种写法

select ename, job from emp where job in ('MANAGER', 'SALEMAN');  // 第2种写法

select ename, job from emp where job = 'MANAGER'

union

select ename, job from emp where job = 'SALEMAN'; // 第3种写法
```

###### limit  --分页，仅MySQL所有，Oracle有类似的rawnum，语法为 limit 开始序号, 长度，开始序号省略时默认为0

```
select ename, sal from emp order by sal dect limit 3, 6;   // 表示工资由低到高第4~9名，序号是从0开始的
```

## 4 数据操作语言（DML）

### 4.1 建表语句

##### 语段格式

```
create table 表名(

	字段名1  数据类型,

	字段名2  数据类型,

	字段名3  数据类型

);
```

##### 数据类型

int  bigint float  date  

char--定长字符串  

varchar--变长字符串 

ELOB--二进制大对象，存储图片、视频等流媒体  

CLOB--字符大对象，存储较大文本

default  --设置默认值/缺省值

```
create table t_student(
	no bigint,
	name varchar(256),  // 虽然设置了长度，但系统会动态分配空间
	sex char(1),  // 其后添加default '1'  表示设置默认值为1，即未设置时会自动为该值而不是null
	classno char(10),
	birth char(10)
);
```

##### 删除表

###### drop  --删表

```
drop table if exists t_student;  // 表存在的话删除它
```

##### 复制表

###### create table 表名 as select语句;    --将查询结果当做表创建表

```
create table emp2 as select empno, ename from emp;
```

将查询结果批量插入到一张表中

```
insert into dept1 select * from dept;  
```

### 4.2 插入数据

###### insert into 表名 (字段名1, 字段名2, ...) values (值1, 值2, ...)

```
insert into t_student(no, name, sex, classno, birth) values (1, 'zhangsan', '1', 'gaosan1ban', '1999-04-05');
```

字段部分省略的话，后面values必须严格按照顺序和数量一致；

values后的()可以写多个以一次性插入多组数据，()之间用, 隔开。

### 4.3 修改数据

###### update 表名 set 字段名1=值1，字段名2=值2，...  where 条件;

```
update dept1 sal loc = 'SHANGHAI', dame='REC' where deptno = 10;
```

### 4.4 删除数据

###### delete from 表名 where 条件;

```
delete from dept1 where deptno = 10;

delete from dept1;   // 删除所有数据
```

###### truncate table 表名  --删除大表（数据量很大的表）

```
truncate table emp1;  // 表被截断，不可回滚，永久丢失
```

CRUD  --增删改查，出现在java语句中 （Create, Retrieve, Update, Delete）

### 4.5 约束（Constraint）

##### not null（非空约束）  --约束的字段不能为NULL

```
drop table if exists t_user;

create table t_user(
	id int,
	username varchar(255) not null,  // 非空约束
	password varchar(255)
);

insert into t_user(id,password) values(1,'123');
// 此时会报1364号错误：Field 'username' doesn't have a default value


```

##### unique（唯一约束） --约束的字段不能重复

可以为null，而且可以多个为null

```
create table t_user(
	id int,
	username varchar(255) unique,  // 唯一约束
	password varchar(255)
);

create table t_user(
	id int,
	username varchar(255),  // 批量添加唯一约束，表示两个字段联合起来不可重复，但单个字段可以重复
	password varchar(255),
	unique(id,username)   // 非空约束没这个语法
);
```

##### primary key（主键约束）--约束的字段既不能为NULL，也不能重复

一张表的主键只能有一个，但是可以有单一主键和复合主键（多个字段联合起来），主键是一行数据的标识

```
drop table if exists t_user;

create table t_user(
	id int primary key,  // 单一主键
	username varchar(255),  
	password varchar(255)
	primary key（username, password）  // 复合主键
);                                    // 当然这两种不能同时出现，因为主键只能有一个
```

###### auto_increment  --自然主键自增

```
create table t_user(
	id int primary key auto_increment,  // 自动维护，从1开始，以1递增  Oracle中有sequence
	...
)
```

##### foreign key（外键约束）  --子表中某列只能引用父表某列的值，所引用的数据必须唯一，引用后可以为null

###### foreign key(子表中的字段名) reference 父表(父表中的字段名)

```
drop table if exuts t_student;
drop table if exits t_class;

create table t_class(              // 父表
	cno int,
	cname varchar(255),
	primary key(cno)               // 设置cno为主键
);

create table t_student(            // 子表
	sno int,
	sname varchar(255),
	classno int,
	foreign key(classno) reference t_class(cno)  // 引用父表中的cno作为外键
)                                                // cno不一定非要是主键，但一定是唯一的，
                                                 // 不设置的话可以为null
```

##### check（检查约束）  --Oracle数据库的约束，目前MySQL不支持该约束

### 4.6 函数

#### 日期和时间

##### 4.6.1 获取日期、时间

|                             函数                             |              用法              |
| :----------------------------------------------------------: | :----------------------------: |
|                   CURDATE(),CURRENT_DATE()                   | 返回当前日期，只包含年、月、日 |
|                   CURTIME(),CURRENT_TIME()                   | 返回当前时间，只包含时、分、秒 |
| NOW()/SYSDATE()/CURRENT_TIMESTAMP()/LOCALTIME()/LOCALTIMESTAMP() |     返回当前系统日期和时间     |
|                          UTC_DATE()                          |  返回UTC（世界标准时间）日期   |
|                          UTC_TIME()                          |  返回UTC（世界标准时间）时间   |

举例

```
SELECT CURDATE(),CURTIME(),NOW(),SYSDATE()+0,UTC_DATE(),UTC_DATE()+0,UTC_TIME(),UTC_TIME()+0
FROM DUAL;
```

##### 4.6.2 日期和时间戳的转换

| 函数                     | 用法                                                         |
| ------------------------ | ------------------------------------------------------------ |
| UNIX_TIMESTAMP()         | 以UNIX时间戳的形式返回当前时间。SELECT UNIX_TIMESTAMP()->1634348884 |
| UNIX_TIMESTAMP(date)     | 将时间date以UNIX时间戳的形式返回。                           |
| FROM_UNIXTIME(timestamp) | 将UNIX时间戳的时间转换为普通格式的时间                       |

举例

```
SELECT UNIX TIMESTAMP (
FROM UNIXTIME (1635173853),UNIX TIMESTAMP('2021-10-0112:12:32')
FROM DUAL;
```



##### 4.6.3 获取月份、星期、星期数、天数等

|                 函数                 |                      用法                       |
| :----------------------------------: | :---------------------------------------------: |
|   YEAR(date)/MONTH(date)/DAY(date)   |                返回具体的日期值                 |
| HOUR(time)/MINUTE(time)/SECOND(time) |                返回具体的时间值                 |
|           MONTHNAME(date)            |               返回月份：January,…               |
|            DAYNAME(date)             |       返回星期几：MONDAY,TUESDAY...SUNDAY       |
|            WEEKDAY(date)             |  返回周几，注意，周1是0，周2是1，。。。周日是6  |
|            QUARTER(date)             |          返回日期对应的季度，范围为1~4          |
|     WEEK(date),WEEKOFYEAR(date)      |               返回一年中的第几周                |
|           DAYOFYEAR(date)            |            返回日期是一年中的第几天             |
|           DAYOFMONTH(date)           |          返回日期位于所在月份的第几天           |
|           DAYOFWEEK(date)            | 返回周几，注意：周日是1，周一是2，。。。周六是7 |

##### 4.6.4 日期的操作函数

|          函数           |                   用法                   |
| :---------------------: | :--------------------------------------: |
| EXTRACT(type FROM date) | 返回指定期中特定的部分，type指定返回的值 |

##### 4.6.5 时间和秒钟的转换函数

|         函数         |                             用法                             |
| :------------------: | :----------------------------------------------------------: |
|  TIME_TO_SEC(time)   | 将time转化为秒并返回结果值。转化的公式为：小时*3680+分钟*60+秒 |
| SEC_TO_TIME(seconds) |         将seconds描述转化为包含小时、分钟和秒的时间          |

##### 4.6.6 计算时间和日期的函数

part 1

|                             函数                             |                      用法                      |
| :----------------------------------------------------------: | :--------------------------------------------: |
|            DATE_ADD(datetime,INTERVAL expr type),            | 返回与给定日期时间相差INTERVAL时间段的日期时间 |
| DATE_SUB(date,INTERVAL expr type),SUBDATE(date,INTERVAL expr type) |      返回与date相差INTERVAL时间间隔的日期      |

part 2

|             函数             |                             用法                             |
| :--------------------------: | :----------------------------------------------------------: |
|     ADDTIME(time1,time2)     | 返回time1加上time2的时间。当time2为一个数字时，代表的是秒，可以为负数 |
|     SUBTIME(time1,time2)     | 返回time1减去time2后的时间。当time2为一个数字时，代表的是秒，可以为负数 |
|    DATEDIFF(date1,date2)     |                返回date1~date2的日期间隔天数                 |
|    TIMEDIFF(time1,time2)     |                  返回time1-time2的时间间隔                   |
|         FROM_DAYS(N)         |             返回从0000年1月1日起，N天以后的日期              |
|        TO_DAYS(date)         |              返回日期date距离0000年1月1日的天数              |
|        LAST_DAY(date)        |               返回date所在月份的最后一天的日期               |
|       MAKEDATE(year,n)       |          针对给定年份与所在年份中的天数返回一个日期          |
| MAKETIME(hour,minute,second) |            将给定的小时、分钟和秒组合成时间并返回            |
|      PERIOD_ADD(time,n)      |                    返回time加上n后的时间                     |



##### 4.6.7 日期和时间的格式化

|               函数                |                    用法                    |
| :-------------------------------: | :----------------------------------------: |
|       DATE_FORMAT(date,fmt)       |       按照字符串fmt格式化日期date值        |
|       TIME_FORMAT(time,fmt)       |       按照字符串fmt格式化时间time值        |
| GET_FORMAT(date_type,format_type) |          返回日期字符串的显示格式          |
|       STR_TO_DATE(str,fmt)        | 按照字符串fmt对str进行解析，解析为一个日期 |





### 4.7 存储引擎

查看当前使用引擎

```
show engines \G
```

#### MyISAM

采用3个文件组织一张表

xxx.frm   存储格式的文件

xxx.MYD  存储表中数据的文件

xxx.MYI   存储表中索引的文件

优点：可被压缩，节省存储空间，可以转换为只读表，提高检索效率

缺点：不支持事务

#### InnoDB

xxx.frm   存储格式的文件

tablespace 表空间     存储数据的地方

优点：支持事务、行级锁、外键等，在MySQL数据库崩溃之后提供自动恢复机制，支持级联删除和级联更新（面向父表与子表之间的外键），其所存储的数据安全能够得到保障

缺点：无法被压缩，无法转换成只读

#### MEMORY

所有数据和索引都存储在内存当中

优点：查询速度快

缺点：不支持事务，数据容易丢失

## 5 事务控制语言（TCL） 

#### 特性--ACID

A--原子性：事务是最小的工作单元，不可再分

C--一致性：事务必须保证多条DML语句同时成功或者同时失败

I--隔离性：事务A与事务B之间具有隔离性

D--持久性：最终数据必须持久化到硬盘文件中，事务才算成功地结束

#### 语句

commit  提交事务，rollback  回放事务，savepoint  保存点

```
set global transaction isolation level read uncommitted;  // 设置全局隔离性为读未提交
```

```
select @@global.表名;   // 查看当前隔离性
```

```
start transaction;  // 开始事务
```

#### 隔离性4个级别

###### read uncommitted  --读未提交

事务A没有提交时，事务B就可以读取到对方未提交的数据

###### read committed  --读已提交       （Oracle默认级别）

事务A提交之后，事务B可以读取到对方提交的数据，但是不可重复读，即对同一数据不能在不同时间读到相同的

###### repeatable  --可重复读     （MySQL默认级别）

事务B仅读取到其开始时数据库中的数据，事务A在B开始之后无论对数据作出什么改动，B都不能读到，可重复读

###### serializable  --序列化

不同事务之间需要排队

## 6 索引

##### 建立索引

###### creat index 索引名称 on 表名（字段名）;

```
creat index emp_sal_index on emp(sal);
```

###### explain 语句  --查看执行计划

```
expliain select ...
```

##### 删除索引对象

###### drop index 索引名称 on 表名（字段名）;

主键和unique会自动添加索引

## 7 视图

#### 创建视图

###### create view 视图名 as select 字段, 字段,... from 表名;

```
create view myview as select empno,ename from emp;
```

#### 删除视图

###### drop view 视图名;

```
drop view myview;
```

#### 更新视图

```
update myview1 set ename = 'hehe', sal = 1 where empno = 7369;  // 通过视图修改原表数据
```

## 8 DBA命令

#### 导出数据

mysqldump 文件名 表名>路径 -uroot -p密码  （需要在dos窗口中执行）

```
mysqldump bjpowernode>D:\bjpowernode.sql -uroot -p333   // 导出整个库

mysqldump bjpowernode emp>D:\bjpowernode.sql -uroot -p333   // 导出数据库中的指定表
```

#### 导入数据

```
create database bjpowernode;

use bipowernode;

source D:\bjpowernode.sql   // 在dos窗口中执行
```

## 9 数据库设计三范式

## 附：练习题

##### 1.取出每个部门工资最高的人的名称

先取出各个部门最大工资（即 b 表）

select deptno,max(sal) maxsal from emp group by deptno;   

之后建立自连接

select b.deptno, a.ename, b.maxsal from emp a join (select deptno,max(sal) maxsal from emp group by deptno) b on a.deptno = b.deptno and a.sal = b.maxsal;

+--------+-------+---------+
| deptno | ename | maxsal  |
+--------+-------+---------+
|     30 | BLAKE | 2850.00 |
|     10 | CLARK | 2450.00 |
|     20 | FORD  | 3000.00 |
+--------+-------+---------+

###### 错误示范

select deptno,ename, max(sal) from emp group by deptno;

如此所显示的ename只是表中按照部门找到的第一个人

##### 2.哪些人的薪水在部门平均薪水之上

各部门的平均薪水（b表）

select deptno, avg(sal) from emp group by deptno;

建立自连接

select a.ename, a.deptno from emp a join (select deptno, avg(sal) avgsal from emp group by deptno) b on a.deptno = b.deptno and a.sal > b.avgsal;

+-------+--------+ 
| ename | deptno |
+-------+--------+
| ALLEN |     30 |
| JONES |     20 |
| BLAKE |     30 |
| CLARK |     10 |
| FORD  |     20 |
+-------+--------+

##### 3.取得部门中所有人的平均的薪水等级

每个人的薪水等级（表g）

select deptno, ename, grade from emp e, salgrade s where e.sal between s.losal and s.hisal;

以此表为基础，分部门算平均等级

select deptno, avg(g.grade) from (select deptno, ename, grade from emp e, salgrade s where e.sal between s.losal and s.hisal) g group by deptno;

+--------+--------------+
| deptno | avg(g.grade) |
+--------+--------------+
|     20 |       2.5000 |
|     30 |       2.5000 |
|     10 |       3.0000 |
+--------+--------------+

其实并不需要临时表，因为薪水等级并不是新的数据，而是salgrade表中的原始数据，

avg() .......group by deptno可以直接对 s.grade 进行处理

**临时表仅在根据原始表计算得到新的数据，如avg，max，min等，使用，如果所处理的本来就是表中的原始数据，那么临时表是不必要的。**

select e.deptno,avg(s.grade) from emp e join salgrade s on e.sal between s.losal and s.hisal group by deptno;

##### 4.不用max，取得最高薪水，给出两种解决方案

###### 第1种

先对表进行排序，此为临时表

select ename,sal from emp order by sal desc ;

输出降序后的表的第一行

select * from (select ename,sal from emp order by sal desc) a limit 0,1;

输出第一行

select * from 表名 limit 开始行数（从0开始）,显示行数;

+-------+---------+
| ename | sal     |
+-------+---------+
| KING  | 5000.00 |
+-------+---------+

###### 第2种 表的自连接

临时表，找出b表中有比a大的sal的a表中的工资，再去重

select distinct a.sal from emp a join emp b on a.sal < b.sal; 

寻找表中不在工资

select sal from emp where sal not in (select distinct a.sal from emp a join emp b on a.sal < b.sal);

##### 5.取得平均薪水最高的部门的部门编号，给出两种解决方案

###### 第1种

先求各部门平均薪水，再降序排序，最后输出第一行

select deptno, avg(sal) as avgsal from emp group by deptno order by avgsal desc limit 0,1;

###### 第2种

临时表

select deptno, avg(sal) from emp group by deptno;

在此基础上取出最大值

select a.deptno, max(a.avgsal) from (select deptno, avg(sal) avgsal from emp group by deptno) a;

但是不用临时表的话

select deptno,max(avg(sal)) from emp group by deptno;   

报错 Invalid use of group function

+--------+-------------+
| deptno | avgsal      |
+--------+-------------+
|     10 | 2916.666667 |
+--------+-------------+

##### 6.取得平均薪水最高的部门的部门名称

###### 第1种 在临时表的基础上

select dname from dept d0 join (select deptno, avg(sal) as avgsal from emp group by deptno order by avgsal desc limit 0,1) d1 on d1.deptno = d0.deptno ;

###### 第2种 建立连接后执行分组函数、排序、分页

select d.dname, avg(e.sal) as avgsal from emp e join dept d on e.deptno = d.deptno group by d.dname order by avgsal desc limit 1;

+------------+-------------+
| dname      | avgsal      |
+------------+-------------+
| ACCOUNTING | 2916.666667 |
+------------+-------------+

##### 7.平均薪水的等级最低的部门名称

先将emp表和dept表连接，表示各个部门名称对应的平均薪水

select d.dname, avg(sal) avgsal from emp e join dept d on e.deptno = d.deptno group by d.dname;

将上表与salgrade表连接，获得各个部门平均薪水的等级，再取其中的最小值

注：下示语句实际为错误的，dname与min(grade)实际并无关联，应当用where语句进行筛选

select a.dname, min(grade) from (select d.dname, avg(sal) avgsal from emp e join dept d on e.deptno = d.deptno group by d.dname) a join salgrade b on a.avgsal between b.losal and hisal;

正确的表达应该是

select t.*, s.grade

from (select d.dname, avg(sal) avgsal from emp e join dept d on e.deptno = d.deptno group by d.dname) t

join salgrade s

on t.avgsal between s.losal and s.hisal

where s.grade = 最低平均工资的等级;

最低平均工资的等级是

select grade from salgrade where (select avg(sal) as avgsal from emp group by deptno order by avgsal asc limit 1) between losal and hisal;

+-------+
| grade |
+-------+
|     3 |
+-------+

##### 8.取得比普通员工的最高薪水还要高的领导人姓名

not in () 在使用时，()要排除null

普通员工当中的最高薪水

select max(sal) from emp where empno not in (select distinct mgr from emp where mgr is not null);

两个条件，一个是比普工最高薪水要高，一个是领导人

sal > (select max(sal) from emp where empno not in (select distinct mgr from emp where mgr is not null))

empno in (select distinct mgr from emp where mgr is not null)

最后结果是

select ename from emp where sal > (select max(sal) from emp where empno not in (select distinct mgr from emp where mgr is not null)) and empno in (select distinct mgr from emp where mgr is not null);

+-------+
| ename |
+-------+
| JONES |
| BLAKE |
| CLARK |
| SCOTT |
| KING  |
| FORD  |
+-------+

##### 9.取得薪水最高的前5名员工

select ename,sal from emp order by sal desc limit 5;

+-------+---------+
| ename | sal     |
+-------+---------+
| KING  | 5000.00 |
| SCOTT | 3000.00 |
| FORD  | 3000.00 |
| JONES | 2975.00 |
| BLAKE | 2850.00 |
+-------+---------+

##### 10.取得薪水最高的第6到第10名员工

select ename,sal from emp order by sal desc limit 5,5;

+--------+---------+
| ename  | sal     |
+--------+---------+
| CLARK  | 2450.00 |
| ALLEN  | 1600.00 |
| TURNER | 1500.00 |
| MILLER | 1300.00 |
| WARD   | 1250.00 |
+--------+---------+

##### 11.取得最后入职的5名员工

select ename,hiredate from emp order by hiredate desc limit 5;

+--------+------------+
| ename  | hiredate   |
+--------+------------+
| ADAMS  | 1987-05-23 |
| SCOTT  | 1987-04-19 |
| MILLER | 1982-01-23 |
| JAMES  | 1981-12-03 |
| FORD   | 1981-12-03 |
+--------+------------+

##### 12.取得每个薪水等级有多少个员工

*是所有条目，grade是所有grade不是null的条目

select grade, count(*) from emp e join salgrade s on e.sal between s.losal and s.hisal group by grade;

+-------+--------------+
| grade | count(grade) |
+-------+--------------+
|     1 |            3 |
|     3 |            2 |
|     2 |            3 |
|     4 |            5 |
|     5 |            1 |
+-------+--------------+

13.

##### 14.列出所有员工及领导的名字

使用外连接，员工表为主表

select a.ename, b.ename as mgrname from emp a left join emp b on a.mgr = b.empno;

+--------+---------+
| ename  | mgrname |
+--------+---------+
| SMITH  | FORD    |
| ALLEN  | BLAKE   |
| WARD   | BLAKE   |
| JONES  | KING    |
| MARTIN | BLAKE   |
| BLAKE  | KING    |
| CLARK  | KING    |
| SCOTT  | JONES   |
| KING   | NULL    |
| TURNER | BLAKE   |
| ADAMS  | SCOTT   |
| JAMES  | BLAKE   |
| FORD   | JONES   |
| MILLER | CLARK   |
+--------+---------+

##### 15.列出受雇日期早于其直接上级的员工的编号、姓名、部门名称

先列出符合要求的员工

select a.ename, a.hiredate, b.ename as mgrname, b.hiredate from emp a left join emp b on a.mgr = b.empno **having** a.hiredate < b.hiredate;

+-------+------------+---------+------------+
| ename | hiredate   | mgrname | hiredate   |
+-------+------------+---------+------------+
| SMITH | 1980-12-17 | FORD    | 1981-12-03 |
| ALLEN | 1981-02-20 | BLAKE   | 1981-05-01 |
| WARD  | 1981-02-22 | BLAKE   | 1981-05-01 |
| JONES | 1981-04-02 | KING    | 1981-11-17 |
| BLAKE | 1981-05-01 | KING    | 1981-11-17 |
| CLARK | 1981-06-09 | KING    | 1981-11-17 |
+-------+------------+---------+------------+

把**having**换成**where**也可以

select a.ename, a.hiredate, b.ename as mgrname, b.hiredate from emp a left join emp b on a.mgr = b.empno **where** a.hiredate < b.hiredate;

如下为最终结果，但是用**having**的话不显示两个hiredate就报错

select a.empno, a.ename, a.hiredate, d.dname, b.hiredate from emp a  join dept d on a.deptno = d.deptno left join emp b on a.mgr = b.empno having a.hiredate < b.hiredate;

+-------+-------+------------+------------+------------+
| empno | ename | hiredate   | dname      | hiredate   |
+-------+-------+------------+------------+------------+
|  7369 | SMITH | 1980-12-17 | RESEARCE   | 1981-12-03 |
|  7499 | ALLEN | 1981-02-20 | SALES      | 1981-05-01 |
|  7521 | WARD  | 1981-02-22 | SALES      | 1981-05-01 |
|  7566 | JONES | 1981-04-02 | RESEARCE   | 1981-11-17 |
|  7698 | BLAKE | 1981-05-01 | SALES      | 1981-11-17 |
|  7782 | CLARK | 1981-06-09 | ACCOUNTING | 1981-11-17 |
+-------+-------+------------+------------+------------+

改用where即可正常输出

select a.empno, a.ename, d.dname from emp a  join dept d on a.deptno = d.deptno left join emp b on a.mgr = b.empno where a.hiredate < b.hiredate;

+-------+-------+------------+
| empno | ename | dname      |
+-------+-------+------------+
|  7369 | SMITH | RESEARCE   |
|  7499 | ALLEN | SALES      |
|  7521 | WARD  | SALES      |
|  7566 | JONES | RESEARCE   |
|  7698 | BLAKE | SALES      |
|  7782 | CLARK | ACCOUNTING |
+-------+-------+------------+

##### 16.列出部门名称和这些部门的员工信息，同时列出那些没有员工的部门

主要是部门表是主表

select d.dname, e.* from dept d left join emp e on d.deptno = e.deptno order by d.deptno;

+------------+-------+--------+-----------+------+------------+---------+---------+--------+
| dname      | empno | ename  | job       | mgr  | hiredate   | sal     | comm    | deptno |
+------------+-------+--------+-----------+------+------------+---------+---------+--------+
| ACCOUNTING |  7934 | MILLER | CLERK     | 7782 | 1982-01-23 | 1300.00 |    NULL |     10 |
| ACCOUNTING |  7839 | KING   | PRESIDENT | NULL | 1981-11-17 | 5000.00 |    NULL |     10 |
| ACCOUNTING |  7782 | CLARK  | MANAGER   | 7839 | 1981-06-09 | 2450.00 |    NULL |     10 |
| RESEARCE   |  7902 | FORD   | ANALYST   | 7566 | 1981-12-03 | 3000.00 |    NULL |     20 |
| RESEARCE   |  7876 | ADAMS  | CLERK     | 7788 | 1987-05-23 | 1100.00 |    NULL |     20 |
| RESEARCE   |  7788 | SCOTT  | ANALYST   | 7566 | 1987-04-19 | 3000.00 |    NULL |     20 |
| RESEARCE   |  7566 | JONES  | MANAGER   | 7839 | 1981-04-02 | 2975.00 |    NULL |     20 |
| RESEARCE   |  7369 | SMITH  | CLERK     | 7902 | 1980-12-17 |  800.00 |    NULL |     20 |
| SALES      |  7900 | JAMES  | CLERK     | 7698 | 1981-12-03 |  950.00 |    NULL |     30 |
| SALES      |  7844 | TURNER | SALESMAN  | 7698 | 1981-09-08 | 1500.00 |    0.00 |     30 |
| SALES      |  7698 | BLAKE  | MANAGER   | 7839 | 1981-05-01 | 2850.00 |    NULL |     30 |
| SALES      |  7654 | MARTIN | SALESMAN  | 7698 | 1981-05-01 | 1250.00 | 1400.00 |     30 |
| SALES      |  7521 | WARD   | SALESMAN  | 7698 | 1981-02-22 | 1250.00 |  500.00 |     30 |
| SALES      |  7499 | ALLEN  | SALESMAN  | 7698 | 1981-02-20 | 1600.00 |  300.00 |     30 |
| OPERATIONS |  NULL | NULL   | NULL      | NULL | NULL       |    NULL |    NULL |   NULL |
+------------+-------+--------+-----------+------+------------+---------+---------+--------+

##### 17.列出至少有5个员工的所有部门

select deptno from (select deptno, count(*) as c from emp group by deptno) a where a.c >=5;

+--------+
| deptno |
+--------+
|     20 |
|     30 |
+--------+

更简洁地，使用having过滤

select deptno from emp group by deptno having count(*)>=5;

##### 18.列出薪金比SMITH多的所有员工信息

select * from emp where sal > (select sal from emp where ename='SMITH');

##### 19.列出所有CLERK的姓名及其部门的名称和人数

以下这么写的话，每个部门只显示一个人的名字

select ename, dname,count(*) from emp e join dept d on e.deptno = d.deptno where job = 'CLERK' group by d.deptno;

采用临时表先计算各个部门的人数

select deptno,count(*) as c from emp group by deptno ;

再进行连接

select ename, dname, t.c from emp e join dept d on e.deptno = d.deptno join (select deptno,count(*) as c from emp group by deptno) t on e.deptno = t.deptno where job = 'CLERK' ;

##### 20.列出最低薪金大于1500的各种工作及从事此工作的全部雇员人数

select job,count(*) from emp group by job having min(sal) > 1500;

+-----------+----------+
| job       | count(*) |
+-----------+----------+
| MANAGER   |        3 |
| ANALYST   |        2 |
| PRESIDENT |        1 |
+-----------+----------+

##### 21.列出部门SALES工作的员工的姓名，在不知道部门编号的前提下

select e.ename from emp e join dept d on e.deptno = d.deptno where d.dname='SALES';

+--------+
| ename  |
+--------+
| ALLEN  |
| WARD   |
| MARTIN |
| BLAKE  |
| TURNER |
| JAMES  |
+--------+

##### 22.列出薪金高于部门平均薪金的所有员工，所在部门，上级领导，雇员的工资等级

一个连接用于算等级，另一个连接用于匹配所属部门的平均薪资，用于筛选

select e.ename, e.deptno, e.mgr, s.grade from emp e join salgrade s on e.sal between s.losal and s.hisal join (select deptno, avg(sal) avgsal from emp group by deptno) t on e.deptno = t.deptno where e.sal > t.avgsal;

+-------+--------+------+-------+
| ename | deptno | mgr  | grade |
+-------+--------+------+-------+
| ALLEN |     30 | 7698 |     3 |
| JONES |     20 | 7839 |     4 |
| BLAKE |     30 | 7839 |     4 |
| SCOTT |     20 | 7566 |     4 |
| KING  |     10 | NULL |     5 |
| FORD  |     20 | 7566 |     4 |
+-------+--------+------+-------+

##### 23.列出与'SCOTT'从事相同工作的所有员工及部门名称

select e.ename, d.dname from emp e join dept d on e.deptno = d.deptno where job = (select job from emp where ename = 'SCOTT');

+-------+----------+
| ename | dname    |
+-------+----------+
| FORD  | RESEARCE |
| SCOTT | RESEARCE |
+-------+----------+

结果应当排除SCOTT本身，所以要

select e.ename, d.dname from emp e join dept d on e.deptno = d.deptno where job = (select job from emp where ename = 'SCOTT') and e.ename <> 'SCOTT';

##### 24.列出薪资等于部门30中的员工的薪资的其他员工的姓名和薪金

select ename,sal from emp where sal in (select sal from emp where deptno = 30) and deptno <> 30;

Empty set (0.22 sec)

##### 25.列出薪资高于部门30中的员工薪资的员工的姓名、薪金、部门名称

select e.ename, e.sal, d.dname from emp e join dept d on e.deptno = d.deptno where sal > (select max(sal) from emp where deptno = 30) ;

+-------+---------+------------+
| ename | sal     | dname      |
+-------+---------+------------+
| JONES | 2975.00 | RESEARCE   |
| SCOTT | 3000.00 | RESEARCE   |
| KING  | 5000.00 | ACCOUNTING |
| FORD  | 3000.00 | RESEARCE   |
+-------+---------+------------+

##### 26.列出在每个部门工作的员工数量，平均工资和平均服务期限

select deptno,count(*), avg(sal) from emp group by deptno;

上示没有考虑到空部门，因此要建立外连接，部门表为主表

select d.deptno,count(e.ename), avg(sal) from emp e right join dept d on e.deptno = d.deptno group by deptno;

+--------+----------------+-------------+
| deptno | count(e.ename) | avg(sal)    |
+--------+----------------+-------------+
|     10 |              3 | 2916.666667 |
|     20 |              5 | 2175.000000 |
|     30 |              6 | 1566.666667 |
|     40 |              0 |        NULL |
+--------+----------------+-------------+

考虑全面一点，null可改为0

select d.deptno,count(e.ename), ifnull(avg(sal),0) from emp e right join dept d on e.deptno = d.deptno group by deptno;

+--------+----------------+--------------------+
| deptno | count(e.ename) | ifnull(avg(sal),0) |
+--------+----------------+--------------------+
|     10 |              3 |        2916.666667 |
|     20 |              5 |        2175.000000 |
|     30 |              6 |        1566.666667 |
|     40 |              0 |           0.000000 |
+--------+----------------+--------------------+

这里涉及计算时间的差值，有一个MySQL的内置函数，TimeStampDiff(间隔类型，前一个日期，后一个日期)

select d.deptno,count(e.ename), ifnull(avg(sal),0), ifnull(avg(TimeStampDiff(YEAR,e.hiredate,now())),0) from emp e right join dept d on e.deptno = d.deptno group by deptno;

+--------+----------------+--------------------+-----------------------------------------------------+
| deptno | count(e.ename) | ifnull(avg(sal),0) | ifnull(avg(TimeStampDiff(YEAR,e.hiredate,now())),0) |
+--------+----------------+--------------------+-----------------------------------------------------+
|     10 |              3 |        2916.666667 |                                             40.0000 |
|     20 |              5 |        2175.000000 |                                             37.8000 |
|     30 |              6 |        1566.666667 |                                             40.3333 |
|     40 |              0 |           0.000000 |                                              0.0000 |
+--------+----------------+--------------------+-----------------------------------------------------+

##### 27.列出所有员工的姓名，部门名称和工资

select e.ename, d.dname, e.sal from emp e join dept d on e.deptno = d.deptno;

##### 28.列出所有部门的详细信息和人数

select d.*, count(e.ename) '人数' from dept d left join emp e on e.deptno = d.deptno group by d.dname, d.deptno, d.loc;

+--------+------------+----------+------+
| deptno | dname      | loc      | 人数 |
+--------+------------+----------+------+
|     10 | ACCOUNTING | NEW YORK |    3 |
|     20 | RESEARCE   | DALLAS   |    5 |
|     30 | SALES      | CHICAGO  |    6 |
|     40 | OPERATIONS | BOSTON   |    0 |
+--------+------------+----------+------+

##### 29.列出各种工作的最低工资及从事此工作的雇员姓名

临时表

select job, min(sal) from emp group by job;

将其与emp连接

select t.*, e.ename from emp e join (select job, min(sal) minsal from emp group by job) t on e.job = t.job and e.sal = t.minsal;

+-----------+---------+--------+
| job       | minsal  | ename  |
+-----------+---------+--------+
| CLERK     |  800.00 | SMITH  |
| SALESMAN  | 1250.00 | WARD   |
| SALESMAN  | 1250.00 | MARTIN |
| MANAGER   | 2450.00 | CLARK  |
| ANALYST   | 3000.00 | SCOTT  |
| PRESIDENT | 5000.00 | KING   |
| ANALYST   | 3000.00 | FORD   |
+-----------+---------+--------+

##### 30.列出各个部门领导的最低薪资

select deptno, min(sal) from emp where empno in (select distinct mgr from emp where mgr is not null) group by deptno;

+--------+----------+
| deptno | min(sal) |
+--------+----------+
|     20 |  2975.00 |
|     30 |  2850.00 |
|     10 |  2450.00 |
+--------+----------+

更简洁的

select deptno, min(sal) from emp where job = 'MANAGER' group by deptno;

##### 31.列出所有员工的年工资，并按年薪从低到高排序

select ename, (sal+ifnull(comm,0))*12 as yearsal from emp order by yearsal asc;

+--------+----------+
| ename  | yearsal  |
+--------+----------+
| SMITH  |  9600.00 |
| JAMES  | 11400.00 |
| ADAMS  | 13200.00 |
| MILLER | 15600.00 |
| TURNER | 18000.00 |
| WARD   | 21000.00 |
| ALLEN  | 22800.00 |
| CLARK  | 29400.00 |
| MARTIN | 31800.00 |
| BLAKE  | 34200.00 |
| JONES  | 35700.00 |
| SCOTT  | 36000.00 |
| FORD   | 36000.00 |
| KING   | 60000.00 |
+--------+----------+

##### 32.求出员工领导的薪水超过3000的员工名称与领导名称

select a.ename, b.ename from emp a join (select ename, empno, sal from emp where empno in (select distinct mgr from emp)) b on a.mgr = b.empno where b.sal > 3000;

更为简洁的是

select a.ename, b.ename from emp a join emp b on a.mgr = b.empno where b.sal > 3000;

+-------+-------+
| ename | ename |
+-------+-------+
| JONES | KING  |
| BLAKE | KING  |
| CLARK | KING  |
+-------+-------+

##### 33.求出部门名称中带'S'字符的部门员工的工资合计，部门人数

注意要外连接，涉及所有部门

select d.deptno, d.dname, sum(sal), count(ename) from emp e right join dept d on e.deptno = d.deptno where d.dname like '%S%' group by d.deptno;

+--------+------------+----------+--------------+
| deptno | dname      | sum(sal) | count(ename) |
+--------+------------+----------+--------------+
|     20 | RESEARCE   | 10875.00 |            5 |
|     30 | SALES      |  9400.00 |            6 |
|     40 | OPERATIONS |     NULL |            0 |
+--------+------------+----------+--------------+

##### 34.给任职日期超过30年的员工加薪10%

update emp set sal=sal*1.1 where ifnull(TimeStampDiff(YEAR,hiredate,now()),0) > 30;

+-------+--------+-----------+------+------------+---------+---------+--------+
| empno | ename  | job       | mgr  | hiredate   | sal     | comm    | deptno |
+-------+--------+-----------+------+------------+---------+---------+--------+
|  7369 | SMITH  | CLERK     | 7902 | 1980-12-17 |  880.00 |    NULL |     20 |
|  7499 | ALLEN  | SALESMAN  | 7698 | 1981-02-20 | 1760.00 |  300.00 |     30 |
|  7521 | WARD   | SALESMAN  | 7698 | 1981-02-22 | 1375.00 |  500.00 |     30 |
|  7566 | JONES  | MANAGER   | 7839 | 1981-04-02 | 3272.50 |    NULL |     20 |
|  7654 | MARTIN | SALESMAN  | 7698 | 1981-05-01 | 1375.00 | 1400.00 |     30 |
|  7698 | BLAKE  | MANAGER   | 7839 | 1981-05-01 | 3135.00 |    NULL |     30 |
|  7782 | CLARK  | MANAGER   | 7839 | 1981-06-09 | 2695.00 |    NULL |     10 |
|  7788 | SCOTT  | ANALYST   | 7566 | 1987-04-19 | 3300.00 |    NULL |     20 |
|  7839 | KING   | PRESIDENT | NULL | 1981-11-17 | 5500.00 |    NULL |     10 |
|  7844 | TURNER | SALESMAN  | 7698 | 1981-09-08 | 1650.00 |    0.00 |     30 |
|  7876 | ADAMS  | CLERK     | 7788 | 1987-05-23 | 1210.00 |    NULL |     20 |
|  7900 | JAMES  | CLERK     | 7698 | 1981-12-03 | 1045.00 |    NULL |     30 |
|  7902 | FORD   | ANALYST   | 7566 | 1981-12-03 | 3300.00 |    NULL |     20 |
|  7934 | MILLER | CLERK     | 7782 | 1982-01-23 | 1430.00 |    NULL |     10 |
+-------+--------+-----------+------+------------+---------+---------+--------+
