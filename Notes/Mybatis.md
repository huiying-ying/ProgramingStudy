# 0 简介

![image-20230112011950733](imgs/Mybatis/image-20230112011950733.png)

![image-20230112012039574](imgs/Mybatis/image-20230112012039574.png)

## 历史

MyBatis:最初是Apachel的个开源项目iBatis,2010年6月这个项目由Apache Software Foundation迁移到了
G0 ogle Code。随若开发团队转投Google Code旗下，iBatis.3.X正式更名为MyBatis。代码于2013年11月迁移到
Github.
I
iBatis一词来源于"internet'和abatis"的组合，是一个基于]ava的特久层框架。Batis提供的持久层框架包括SQL
MapsData Access Objects (DAO)

## 特性

1) MyBatis是支持定制化SQL、存储过程以及高级映射的优苏的持久层框架
2) MyBatis避免了几乎所有的DBC代码和手动设置参数以及获取结果集
3) MyBatisi可以使用简单的XML或注解用于配置和原始映射，将接口和ava的POJO(Plain Old Java Objects,普
   通的ava对象）映射成数据库中的记录
4) MyBatis是-个半自动的 **ORM(Object Relation Mapping)** 框架（半自动指的是自己需要写sql语句，全自动的话只需要配置文件就可以运行了）

## 比较

- JRBC
  - SQL夹杂在ava代码中相合度高，导致硬编码内伤
  - 维护不易且实际开发需求中SQL有变化，领繁修改的情况多见
  - 代码冗长，开发效率低

- Hibernate和JPA
  - 操作简便，开发效率高
  - 程序中的长难复杂SQL需要绕过框架
  - 内部自动生产的SQL，不容易做特殊优化
  - 甚于全映射的全自动框架，大量字段的POO进行部分映射时比较困难
  - 反射操作太多，导致数据库性能下降

- MyBatis
  - 轻量级，性能出色
  - SQL和ava编码分开，功能边界清浙。Java代码专注业务、SQL语句专注数据
  - 开发效率稍逊于Hibernate,但是完全能够接受

# 1 搭建 MyBatis

##### 1 开发环境

IDE:idea 2019.2
构建工具：maven3.5.4
MySQL版本：MySQL5.7
MyBatis版本：MyBatis3.5.7

##### 2 依赖

```xml
<!--设置打包方式为jar包-->
<packaging>jar</packaging>

<dependencies>
    <!--Mybatis核心-->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.7</version>
    </dependency>
    <!--junit测试-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>
    <!--MySQL驱动-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.3</version>
    </dependency>
</dependencies>
```

##### 3 创建MyBatis的核心配置文件

习惯上命名为mybatis-config.xml,这个文件名仅仅只是建议，并非强制要求。将来整合Spring之后，这个配
置文件可以省略，所以大家操作时可以直接复制、粘贴。
核心配置文件主要用于配置连接数据库的环境以及MyBatis的全局配置信息
核心配置文件存放的位置是src/main/resources目录下

![image-20230112183423960](imgs/Mybatis/image-20230112183423960.png)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
         PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--配置连接数据库的环境-->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="suxujia520"/>
            </dataSource>
        </environment>
    </environments>
    <!--引入映射文件（这里还没设置）-->
    <mappers>
        <mapper resource="org/mybatis/example/BlogMapper.xm1"/>
    </mappers>
</configuration>
```

##### 4 创建mapper接口

MyBatis中 的 mapper 接口相当于以前的 dao。但是区别在于，mapper仅仅是接口，我们不需要提供实现类。

##### 5 创建MyBatis的映射文件

相关概念：ORM(Object Relationship Mapping)对象关系映射。

- 对象：Java的实体类对象
- 关系：关系型数据库
- 映射：二者之间的对应关系

| java概念 | 数据库概念 |
| -------- | ---------- |
| 类       | 表         |
| 属性     | 字段（列） |
| 对象     | 记录（行） |

1、映射文件的命名规则：

表所对应的实体类的类将+Mapper.xml

例如：表user ，映射的实体类为User，所对应的映射文件为 UserMapper.xml  

因此一个映射文件对应一个实体类，对应一张表的操作  **（表  对应  Mapper接口  对应  配置文件）**

MyBatis映射文件用于编写SQL，访问以及操作表中的数据

MyBatis映射文件存放的位置是src/maln/resources/mappers目录下

注：xml 文件中的 .dtd 文件不同

![image-20230112160006618](imgs/Mybatis/image-20230112160006618.png)

之后再 mybatis.xml 文件中添加相应的映射文件

```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
         PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--配置连接数据库的环境-->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf8"/>  // 这里和教程有差别，？之后的部分不加的话会报错，这里说的是编码的问题要明确一下，猜测可能是mysql版本造成的
                <property name="username" value="root"/>
                <property name="password" value="suxujia520"/>
            </dataSource>
        </environment>
    </environments>
    <!--引入映射文件-->
    <mappers>
        <mapper resource="mappers/UserMapper.xm1"/>
    </mappers>
</configuration>
```

2、MyBatis中可以面向接口操作数据，要保证两个一致：

- 映射文件的namespace要和mapper接口的全类名保持一致

![image-20230112160439324](imgs/Mybatis/image-20230112160439324.png)

- 映射文件中SQL语句的id要和mapper接口中的方法名一致（id）

![image-20230112160554629](imgs/Mybatis/image-20230112160554629.png)

##### 6 通过junit测试功能

```java
package com.mybatis.test;**
import com.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisTest001 {
    @Test
    public void testMybatis() throws IOException {
        // 加载核心配置文件
        InputStream is = Resources.getResourceAsStream("mybatis_config.xml");
        // 获取SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        // 获取SqlSession(默认不自动提交事务，若要自动提交事务，可以是)
        // sqlSessionFactory.openSession(true);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 获取Mapper接口对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 测试功能
        int result = mapper.insertUser();
        // 提交事务
        sqlSession.commit();
        System.out.println("result:" + result);
    }
}
```

##### 7 加入log4j日志功能

###### 加入依赖

```xml
<!--Log4j日志-->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

###### 加入 log4j 配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">
    <appender name="STDOUT" class="org.apache.log4j.ConsoleAppender">
        <param name="Encoding" value="UTF-8"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%-5p %d{MM-dd HH:mm:ss,SSs}%m (%F:%L) \n"/>
        </layout>
    </appender>
    <logger name="java.sql">
        <level value="debug"/>
    </logger>
    <logger name="org.apache.ibatis">
        <level value="info"/>
    </logger>
    <root>
        <level value="debug"/>
        <appender-ref ref="STDOUT"/>
    </root>
</log4j:configuration>
```

###### 日志的级别

FATAL(致命>ERROR（错误）>WARN（警告）>INFO（信息）>DEBUG（调试）

从左到右打印的内容越来越详细

###### 附：增删改查映射文件中的例子

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.mybatis.mapper.UserMapper">
    <!--int insertUser();-->
    <insert id="insertUser">
        insert into t_user values(2, "jack", "123456", 23, 'm', "1234@qq.com")
--         insert into t_user_another values(1, 'admin')
    </insert>
    <!--void updateUser();-->
    <update id="updateUser">
        update t_user set username = '张三' where id = 1
    </update>
    <!--void deleteUser();-->
    <delete id="deleteUser">
        delete from t_user where username = '张三'
    </delete>
    <!--User getUserById();
    查询功能的标签必须设置resultType或resultMap
    resultType:设置默以的映射关系（字段名和属性名一致）
    resultMap:设置自定义的映射关肃（字段名和属性名不一致或一对多）-->
    <select id="getUserById" resultType="com.mybatis.pojo.User">
        select * from t_user where id = 1
    </select>
    <!--List<User> getAllUser();-->
    <select id="getAllUser" resultType="com.mybatis.pojo.User">
        select * from t_user
    </select>
</mapper>
```

# 2 核心配置文件详解

简单做了解，之后由springboot管理

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
         PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--配置连接数据库的环境-->
    <!--environments：配置多个连接数据库的环境
            属性：
            default：设置默认使用的环境的d
        -->
    <environments default="development">
        <!-- environment:配置某个具体的环凭
            属性：
            id:表示连接数据库的环境的唯一标识，不能重复
        -->
        <environment id="development">
            <!--transactionManager:设置事务管理方式
                属性：
                type="JDBC MANAGED"
                JDBC：表示当前环境中，执行SQL时，使用的是JDBC中原生的事务管理方式,事务的提交或回漓需要手动处理
                MANAGED:被管理，例如spring
            -->
            <transactionManager type="JDBC"/>
            <!--datasource:配置数据源(之后spring会承包)
            属性：
            type：设置数据源的类型
            type="POOLED |UNPOOLED JNDI"
            POOLED:表示使用故据库连接池缓存数据库连接
            UNPOOLED:表示不使用数据库连接池
            JNDI:表示使用上下文中的数据源
            -->
            <dataSource type="POOLED">
                <!--驱动-->
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <!--连接地址-->
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf8"/>
                <!--用户名-->
                <property name="username" value="root"/>
                <!--密码-->
                <property name="password" value="suxujia520"/>
            </dataSource>
</environment>
</environments>
<!--引入映射文件-->
    <mappers>
        <mapper resource="mappers/UserMapper.xml"/>
    </mappers>
</configuration>
```

#### 数据库配置文件

##### mybatis_config.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
         PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <properties resource="jdbc.properties"/>
   。。。
   。。。
            <dataSource type="POOLED">
                <!--驱动-->
                <property name="driver" value="${jdbc.driver}"/>
                <!--连接地址-->
                <property name="url" value="${jdbc.url}"/>
                <!--用户名-->
                <property name="username" value="${jdbc.username}"/>
                <!--密码-->
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
</environment>
</environments>
<!--引入映射文件-->
    <mappers>
        <mapper resource="mappers/UserMapper.xml"/>
    </mappers>
</configuration>
```

##### jdbc.properties

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf8
jdbc.username=root
jdbc.password=suxujia520
```







# 3 MyBatis 的增删改查



# 4 MyBatis获取参数值的两种方式

