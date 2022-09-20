# JDBC

## 1 基础概念

是java中提供给各个数据库操作系统的一组接口，各个数据库公司各自重写接口下的类即可，程序员只需要调用相应接口即可，能够实现同样的代码调用不同的数据库对象，不需要更改代码。

## 2 基本操作步骤

#### 1 注册驱动

##### 第一种方式

```java
// 1 注册驱动
Driver driver = new com.mysql.jdbc.Driver();  // 前一个Driver是接口名，后一个是Driver是类名
DriverManager.registerDriver(driver);
```

或者可以简写为**无名类**

```java
DriverManager.registerDriver(new com.mysql.jdbc.Driver());
```

##### 第二种方式

```java
Class.forName("com.mysql.jdbc.Driver");
// 类加载时带动静态代码块执行（甚至不必接收返回值Class c = ...）
// 更为常用，因为字符串参数可以直接写到文件中
```

#### 2 获取连接

```java
// 2 获取连接
String url = "jdbc:mysql://127.0.0.1:3306/bjpowernode";
String user = "root";
String password = "suxujia520";
conn = DriverManager.getConnection(url, user, password);   
// getConnection 是静态方法，因此使用类名Connection直接调用即可
System.out.println("数据库连接对象 = " + conn);
```

##### URL的组成

###### 协议 + IP + PORT + 资源名

**例1**：http://182.61.200.7:80/index.html 

http://   通信协议  --指的是通信之前就定好的数据传送格式

182.61.200.7   服务器IP地址

80  服务器上软件的端口

index.html    是服务器上的某个资源名

**例2**：jdbc:oracle:thin:@localhost:1521:orcl

是oracle的URL

#### 3 获取数据库操作对象

```java
// 3 获取数据库操作对象（Statement专门执行sql语句）
stmt = conn.createStatement();
```

#### 4 执行sql

一种面向DML语句

```java
// 用于执行DML（数据操作语句）
// int executeUpdate(insert/delete/update)  Update面向增删改，返回的是“影响数据库中的记录条数”
String sql = "insert into dept (deptno,dname,loc) values(50,'人事部','北京')";
int count = stmt.executeUpdate(sql);
```

一种面向DQL语句

```java
// 用于执行DQL（查询语句）
// ResultSet executeQuery(select)    Query面向查，返回的是查询集
String sql = "select empno,ename,sal from emp";
rs = stmt.executeQuery(sql);
```

#### 5 处理查询结果集

#### 6 释放资源