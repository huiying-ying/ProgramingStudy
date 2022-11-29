# Spring

## 0 简介

- Spring是轻量级的开源的JavaEE框架
- Spring可以解决企业应用开发的复杂性
- Spring有两个核心部分：IOC和Aop
  - IOC：控制反转，把创建对象过程交给Spring进行管理
  - Aop：面向切面，不修改源代码进行功能增强

- Spring特点
  - 方便解耦，简化开发
  - Aop编程支持
  - 方便程序测试
  - 方便和其他框架进行整合
  - 方便进行事务操作
  - 降低API开发难度

- jar包

  ![image-20221116111539793](imgs/Spring/image-20221116111539793.png)

### 例子

1. 创建一个普通项目，在项目之下（src并列）创建文件夹 lib，放入spring中基础的几个jar包

2. 在Project Structure中的依赖部分添加这几个jar包

3. 以User类的创建和实例化为例展示spring框架中如何创建对象

   ①首先在src目录下创建bean配置文件

   ![](imgs/Spring/image-20221116133433355.png)

   

   ②然后在包之下创建一个测试类

   ```java
   public class Test01 {
       @Test
       public void testAdd(){
           //1 加载spring配置文件
           ApplicationContext context = new ClassPathXmlApplicationContext("bean01.xml");
   
           //2 获取配置创建的对象
           User user = context.getBean("User", User.class);
   
           System.out.println(user);
           user.add();
       }
   }
   ```



## 1 IOC 容器

控制反转，把对象创建和对象之间的调用过程，交给Spring进行管理
使用IOC目的：为了耦合度降低
做入门案例就是IOC实现

### 1.1 IOC底层原理

- xml 解析
- 工厂原理
- 反射

![image-20221116140840228](imgs/Spring/image-20221116140840228.png)

![image-20221116141003155](imgs/Spring/image-20221116141003155.png)

#### IOC过程

![](imgs/Spring/image-20221116171820562.png)

### 1.2 IOC接口(BeanFactory)

#### 1.2.1 接口

Spring 提供IOC容器实现的两种方式（两个接口）：

1. BeanFactory：IOC容器基本实现，是Spring内部的使用接口，不提供开发人员进行使用
   - 加载配置文件时，不会创建对象，只有在获取、使用的时候才会创建对象。

2. ApplicationContext：BeanFactory接口的子接口，提供更多更强大的功能，一般提供开发人员进行使用
   - 加载配置文件时就会创建对象（在服务器启动时完成，而不是使用的时候再创建，因此更适合实用场景）

```java
// 加载配置文件的两个接口
ApplicationContext context = new ClassPathXmlApplicationContext("bean01.xml");
BeanFactory context = new ClassPathXmlApplicationContext("bean01.xml");

// 获取对象
```

#### 1.2.2 实现类

##### ApplicationContext

![image-20221121145115632](imgs/Spring/image-20221121145115632.png)

其中 FileSystemXmlApplicationContext 参数是带盘符的路径，如C盘；

ClassPathXmlApplicationContext 参数则是src下的路径

##### BeanFactory

![image-20221121151342870](imgs/Spring/image-20221121151342870.png)



### 1.3 IOC操作Bean管理——基于xml配置文件方式实现

#### 1.3.1 创建对象

![image-20221121151929820](imgs/Spring/image-20221121151929820.png)

在 spring 配置文件中使用 bean 标签，标签里面添加对应**属性**，实现对象创建：

- id属性：唯一标识
- class属性：类的全路径（包含类名）
- name属性：（不常用了）相比于id可以有斜杠/的出现

创建对象的时候，默认执行**无参构造方法**完成对象创建

#### 1.3.2 注入属性

##### 基本类型属性注入方式（3种）

DI：依赖注入，就是注入属性（给属性赋值，这里使用set方法进行）

<img src="imgs/Spring/image-20221121153633939.png" alt="image-20221121153633939" style="zoom: 50%;" />

###### 使用 set 函数进行注入

![image-20221121154619124](imgs/Spring/image-20221121154619124.png)

最后测试

![image-20221121160046546](imgs/Spring/image-20221121160046546.png)

###### 使用有参数构造进行注入

创建类，定义属性，创建属性对应有参数构造

在spring文件中进行配置

注意：没有无参构造时会报错

<img src="imgs/Spring/image-20221121161033705.png" alt="image-20221121161033705" style="zoom:33%;" /><img src="imgs/Spring/image-20221121161110922.png" alt="image-20221121161110922" style="zoom:50%;" />

针对有参构造函数进行改进，得到

![image-20221121161331577](imgs/Spring/image-20221121161331577.png)

除了用 name 针对属性进行操作，也可以用 index（从0开始）表征属性的序号对齐进行操作。

###### p名称空间注入（对set方式进行简化）

添加 p 名称空间

![image-20221121163529393](imgs/Spring/image-20221121163529393.png)

​       注入属性

![image-20221121163704256](imgs/Spring/image-20221121163704256.png)

##### 注入其他类型属性

1. null 值

   ![image-20221121164131936](imgs/Spring/image-20221121164131936.png)

2. 属性值包含特殊符号

   ① 把<>进行转义

   &lt  是 < ，&gt  是  >

   ②把带特殊符号的内容写到CDATA

   ![image-20221121165502639](imgs/Spring/image-20221121165502639.png)

   ```xml
   <property name="name"> <value><![CDATA[<<南京·南京>>]]></value> </property>
   <property name="year" value="1999"></property>
   ```

##### 注入属性-外部bean

1. 创建两个类 service 类和 dao 类

   ![image-20221122145615495](imgs/Spring/image-20221122145615495.png)

2. 在 service 调用dao里面的方法

   ![image-20221122151521057](imgs/Spring/image-20221122151521057.png)

3. 在 spring 配置文件中进行配置

   ![image-20221122150427179](imgs/Spring/image-20221122150427179.png)

##### 注入属性-内联bean

1. 一对多关系（在实体类中表示）

![image-20221122152944252](imgs/Spring/image-20221122152944252.png)

相应对象的创建配置

![image-20221122153937623](imgs/Spring/image-20221122153937623.png)

##### 注入属性-级联bean

第一种：

![image-20221122155242302](imgs/Spring/image-20221122155242302.png)

第二种

![image-20221122162300477](imgs/Spring/image-20221122162300477.png)

-----------但这种方式的前提是dept相应属性有get函数-----------------

##### 注入集合属性

1. 注入数组类型

2. 注入List

3. 注入Map

4. 注入Set

   ```xml
   <!--集合类型属性注入-->
       <bean id="stu" class="com.spring.collection.Stu">
           <!--数组类型属性注入-->
           <property name="course">
               <array>
                   <value>java课程</value>
                   <value>python课程</value>
               </array>
           </property>
           <!--list类型属性注入-->
           <property name="list">
               <list>
                   <value>zhangsan</value>
                   <value>lisi</value>
               </list>
           </property>
           <!--map类型属性注入-->
           <property name="map">
               <map>
                   <entry key="JAVA" value="java"></entry>
                   <entry key="PY" value="python"></entry>
               </map>
           </property>
           <!--set类型属性注入-->
           <property name="set">
               <set>
                   <value>MySQL</value>
                   <value>Redis</value>
               </set>
           </property>
       </bean>
   ```

5. 注入对象的集合

   ```xml
   <bean>
       ...
   <!--注入list集合类型，值是对象-->
       <property name="courseList">
           <list>
               <ref bean="course1"></ref>
               <ref bean="course2"></ref>
           </list>
       </property>
   </bean>
   
   <!--创建多个course对象-->
   <bean id="course1" class="com.spring.collection.Course">
       <property name="cname" value="Java"></property>
   </bean>
   <bean id="course2" class="com.spring.collection.Course">
       <property name="cname" value="Spring"></property>
   </bean>
   ```

6. 提取集合作为公共部分

   1. 在 spring 配置文件中引入名称空间 util

      ```xml
      <?xml version="1.0" encoding="UTF-8"?>
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:util="http://www.springframework.org/schema/util"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                 http://www.springframework.org/schema/util http://www.springframework.org/schema/beans/spring-util.xsd">
      
      </beans>
      ```

   2. 使用 util 标签完成注入

      ```xml
      <!--1 提取list集合类型属性注入-->
      <util:list id="booklist">
          <value>小王子</value>
          <value>汤姆索亚历险记</value>
      </util:list>
      
      <!--2 提取list集合类型属性注入使用-->
      <bean id="book" class="com.spring.collection.Book">
          <property name="list" ref="booklist"></property>
      </bean>
      ```

#### 1.3.3 FactoryBean（工厂Bean）

- 普通 bean

  配置文件中定义的是什么类，返回的时候就是什么类

<img src="imgs/Spring/image-20221122201710021.png" alt="image-20221122201710021" style="zoom: 67%;" />

<img src="imgs/Spring/image-20221122202603695.png" alt="image-20221122202603695" style="zoom: 50%;" />

- FactoryBean（工厂Bean）

  配置文件中定义的类可以和返回的类不一样

  第一步：创建类，让这个类作为工厂bean，实现接口FactoryBean

  第二步：实现接口里面的方法，在实现的方法中定义返回的bean类型

  ![image-20221122205105249](imgs/Spring/image-20221122205105249.png)

#### 1.3.4 Bean作用域——单实例&多实例

getBean 得到的对象为同一地址则为单实例，不同则为多实例。

设置单、多实例

- 在spring配置文件bean标签里面有属性(scope)用于设置单实例还是多实例

- scope属性值

  - 第一个值默认值，singleton，表示单实例对象

    ```xml
    <bean id="MyBean" class="com.spring.factoryBean.MyBean" scope="singleton"></bean>
    ```

    加载 spring 配置文件时就会创建对象

  - 第二个值prototype，表示多实例对象

    ```xml
    <bean id="MyBean" class="com.spring.factoryBean.MyBean" scope="prototype"></bean>
    ```

    获取对象时创建对象

  - request

  - session

#### 1.3.5 Bean生命周期

- (1) 通过构造器创建bean实例（无参数构造）

- (2) 为bean的属性设置值和对其他bean引用(调用set方法)

  - (2.5) 把 bean 实例传递给 bean 后置处理器的方法

    从BeanPostProcessor接口中复制过来并做改动，得到如下类

    ```java
    public class MyBeanPost implements BeanPostProcessor {
        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("在初始化之前执行的方法");
            return bean;
        }
    
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("在初始化之后执行的方法");
            return bean;
        }
    }
    ```

    之后再在bean中进行配置

    ```xml
    <bean id="orders" class="com.spring.bean.Orders" init-method="initMethod" destroy-method="destoryMethod">
        <property name="oname" value="flower"></property>
    </bean>
    <bean id="myBeanPost" class="com.spring.bean.MyBeanPost"></bean>
    ```

    此时所有bean都会被添加后置处理器

- (3) 调用bean的初始化的方法（需要进行配置初始化的方法）

  ```java
  public void initMethod(){
      System.out.println("第三步：执行初始化方法");
  }
  ```

  ```xml
  <bean id="orders" class="com.spring.bean.Orders" init-method="initMethod"></bean>
  ```

  - (3.5) 把 bean 实例传递给 bean 后置处理器的方法

    执行结果为

    ![image-20221123130352492](imgs/Spring/image-20221123130352492.png)

- (4) bean可以使用了（对象获取到了）

- (5) 当容器关闭时候，调用bean的销毁的方法（需要进行配置销毁的方法）

  ```java
  public void destoryMethod(){
      System.out.println("第五步：执行销毁的方法");
  }
  ```

  ```xml
  <bean id="orders" class="com.spring.bean.Orders" init-method="initMethod" destroy-method="destoryMethod"></bean>
  ```



```java
@Test
public void testLifecycle(){
    ApplicationContext context = new ClassPathXmlApplicationContext("bean10.xml");
    Orders orders = context.getBean("orders", Orders.class);
    System.out.println("第四步：获取创建的对象实例");
    System.out.println(orders);

    // 手动让bean实例销毁
    ((ClassPathXmlApplicationContext) context).close();
    
    // ApplicationContext 接口中并没有close()方法，因此调用时需要强转，不然的话也可以采用以下方法
    // ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean10.xml");
    // context.close();
}
```

#### 1.3.6 xml自动注入

手动装配

```xml
<bean id="emp" class="com.spring.autowire.Emp">
    <!--手动装配-->
    <property name="dept" ref="dept"></property>
</bean>
<bean id="dept" class="com.spring.autowire.Dept"></bean>
```

自动装配

```xml
<bean id="emp" class="com.spring.autowire.Emp" autowire="byName"></bean>
<bean id="dept" class="com.spring.autowire.Dept"></bean>
```

其中根据名字注入和根据属性注入是最常用的（byName，byType）

byName（根据名字注入）——注入值bean的id值和类属性名称一样

byType（根据属性类型注入）——注入值bean类型和类属性一样

实际中很少用xml进行自动装配，一般是用注解方式进行自动装配

#### 1.3.7 外部属性文件

- 直接配置数据库信息

  ```xml
  <!--直接配置连接池-->
      <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
          <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
          <property name="url" value="jdbc:mysql://localhost:3306/userDb"></property>
          <property name="username" value="root"></property>
          <property name="password" value="root"></property>
      </bean>
  ```

- 使用外部属性文件配置德鲁伊连接池

1. 引入德鲁伊

   ![image-20221126162236687](imgs/Spring/image-20221126162236687.png)

2. 引入外部属性文件配置数据库连接池

   - 创建外部属性文件，properties格式文件，写数据库信息

     ```properties
     prop.driverClass=com.mysql.jdbc.Driver
     prop.url=jdbc:mysql://localhost:3306/userDb
     prop.userName=root
     prop.password=root
     ```

   - 把外部properties属性文件引入到spring

     - 引入context名称空间

       ```xml
       <beans xmlns="http://www.springframework.org/schema/beans"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:util="http://www.springframework.org/schema/util"
              xmlns:context="http://www.springframework.org/schema/context"
              xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                  http://www.springframework.org/schema/context http://www.springframework.org/schema/beans/spring-context.xsd">
       ```

     - 在spring配置文件使用标签引入外部属性文件

       ```xml
       <!--引入外部属性文件-->
       <context:property-placeholder location="classpath:jdbc.properties"/>
       ```

   - 配置数据库信息

     ```xml
     <!--引入外部属性文件-->
     <context:property-placeholder location="classpath:jdbc.properties"/>
     
     <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
         <property name="driverClassName" value="${prop.driverClass}"></property>
         <property name="url" value="${prop.url}"></property>
         <property name="username" value="${prop.userName}"></property>
         <property name="password" value="${prop.password}"></property>
     </bean>
     ```

### 1.4 IOC操作Bean管理——基于注解方式实现

#### 1.4.1 概念

注解是代码特殊标记，格式：@注解名称(属性名称=属性值，属性名称=属性值)

使用注解，注解作用在类上面，方法上面，属性上面

可以简化 xml 配置

#### 1.4.2 Spring 提供用于 Bean 管理的注解

都可以用来创建bean实例

@Component

@Service

@Controller

@Repository

#### 1.4.3 基于注解方式实现对象的创建

- 引入 spring-aop 依赖

![image-20221127164643988](imgs/Spring/image-20221127164643988.png)

- 开启组件扫描

```xml
<!--开启组件扫描-->
    <!--扫描多个包的写法 1、用逗号隔开-->
    <context:component-scan base-package="com.spring.service,com.spring.dao"></context:component-scan>
    <!--扫描多个包的写法 2、扫描上层目录-->
    <context:component-scan base-package="com.spring"></context:component-scan>

</beans>
```

**配置细节**

```xml
<!--开启组件扫描-->
    <!--示例1-->
    <context:component-scan base-package="com.spring.service" use-default-filters="false">
		<context:include-filter type="annotation" expression="org.springfranmework.stereotype.Controller"/>
	</context:component-scan>
</beans>
```

use-default-filters="false" 表示不使用默认的 filter（就是扫描包下的所有类），而是自己配置规则

include-filter 设置扫描哪些内容——

​						type="annotation" expression="org.springfranmework.stereotype.Controller"

​						设置扫描 <<注解为Controller>>

```xml
<!--开启组件扫描-->
    <!--示例1-->
    <context:component-scan base-package="com.spring.service">
		<context:exclude-filter type="annotation" expression="org.springfranmework.stereotype.Controller"/>
	</context:component-scan>
</beans>
```

exclude-filter 设置不扫描哪些内容——

​						type="annotation" expression="org.springfranmework.stereotype.Controller"

​						设置不扫描 <<注解为Controller>>

- 添加注释

```java
import org.springframework.stereotype.Component;

// 在注解里value属性值可以不写，其默认值是类名称（首字母小写）
@Component(value = "userServiceForAop")  // 与<bean id="userServiceForAop" class=""></bean>写法类似
public class UserServiceForAop {

    public void add(){
        System.out.println("service add ...");
    }
}
```

#### 1.4.3 基于注解方式实现属性注入

##### @AutoWired

根据属性类型进行自动注入

1. 把 service 和 dao 对象创建，在 service 和 dao 类添加创建对象

2. 在 service 中注入 dao 对象，在 service 类添加 dao 类型属性，在属性上面使用注解（无需添加set方法）

   ```java
   @Service
   public class UserService {
       @Autowired
       private UserDao userDao;
   
       public void add(){
           System.out.println("service add ...");
           userDao.add();
       }
   }
   ```

3. 测试

##### @Qualifier

根据属性名称进行注入

![image-20221127224639311](imgs/Spring/image-20221127224639311.png)

##### @Resourse

两者皆可

```java
@Resource                          // 根据类型
@Resource(name = "userDaoImpl01")  // 根据名称
private UserDao userDao;
```

##### @Value

针对普通类型输入

```java
public class UserService {

    @Value(value = "abc")
    private String name;
```

#### 1.4.4 完全注解开发

创建配置类，替代 xml 配置文件

```java
@Configuration
@ComponentScan(basePackages = {"com.spring.aop"})
public class SpringConfig {
}
```

测试类

```java
@Test
public void test02(){
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    UserService userService = context.getBean("userService", UserService.class);
    System.out.println(userService);
    userService.add();
}
```

## 2 AOP

### 2.1 概念

(1) 面向切面编程（方面），利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

(2) 通俗描述：不通过修改源代码方式，在主干功能里面添加新功能

### 2.2 底层原理

#### 2.2.1 动态代理

- 有接口情况——使用 JDK 动态代理

  创建接口的实现类**代理对象**，通过代理对象增强原方法，嫁接新方法

  ![image-20221129202751423](imgs/Spring/image-20221129202751423.png)

- 没有接口情况——使用 CGLIB 动态代理

  创建当前类子类的**代理对象**

  ![image-20221129202808783](imgs/Spring/image-20221129202808783.png)

以 JDK 动态代理实现为例

1. 调用 **newProxyInstance** 方法

   第一参数，类加载器
   第二参数，增强方法所在的类，这个类实现的接口，支持多个接口
   第三参数，实现这个接口InvocationHandler,创建代理对象，写增强的方法

2. 编写 JDK 动态代理代码

   （1）创建接口，定义方法

   ```java
   public interface UserDao {
       public int add(int a, int b);
       public String update(String id);
   }
   ```

   （2）创建接口实现类，实现方法

   ```java
   public class UserDaoImpl implements UserDao{
       @Override
       public int add(int a, int b) {
           return a+b;
       }
   
       @Override
       public String update(String id) {
           return id;
       }
   
   }
   ```

   （3）使用 Proxy 类创建接口代理对象

   ```java
   public class JDKProxy {
       public static void main(String[] args) {
           // 创建接口实现类代理对象
           Class[] interfaces = {UserDao.class};
           UserDaoImpl userDao = new UserDaoImpl();
           UserDao dao = (UserDao)Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(userDao));
           int result = dao.add(1,2);
           System.out.println("result:" + result);
       }
   }
   
   // 创建代理对象代码
   class UserDaoProxy implements InvocationHandler{
       // 1 把创建的是谁的代理对象，把谁传递过来
       // 有参数构造传递
       private Object obj;
       public UserDaoProxy(Object obj){
           this.obj = obj;
       }
       // 增强的逻辑
       @Override
       public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
           // 方法之前
           System.out.println("方法之前执行。。。" + method.getName() + ":传递的参数" + Arrays.toString(args));
           // 被增强的方法执行
           Object res = method.invoke(obj, args);
           // 方法之后
           System.out.println("方法之后执行。。。" + obj);
   
           return res;
       }
   }
   ```

### 2.3 术语

1. 连接点
2. 切入点
3. 通知（逻辑）

4. 
