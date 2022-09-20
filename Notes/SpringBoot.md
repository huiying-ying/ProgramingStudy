# SpringBoot



## 1 Xml和JavaConfig

相较于Spring、SpringMVC，无需大量的配置文件（XML），不必了解其他框架配置规则去配置各种对象，把使用的对象放入spring容器中才能使用对象。

常用的框架和第三方库都已经配置好了，开发效率高。

### 1.1 JavaConfig配置容器

JavaConfig 是 Spring提供的使用 java 类配置容器，配置 Spring 10C 容器的纯 Java 方法。在这个java类可以创建java对象，把对象放入spring容器中（注入到容器）。

优点：可以使用面向对象的方式，一个配置类可以继承配置类，可以重写方法；避免繁琐的xml配置。

使用两个注解：

1）@Configuation：放在一个类的上面，表示这个类时作为配置文件使用的。

2）@Bean：声明对象，把对象注入到容器中

用一个 java 类代替配置文件 beans.xml

###### Ctrl+H ：查看接口的实现类

###### Ctrl+P ：查看方法参数的几种形式

对比两种方式创建容器的不同之处：

```java
// 使用xml作为容器配置文件
String config = "beans.xml";
ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
```

```java
// 使用javaConfig
ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
```

例子：

```java
/**
 * Configuation:表示当前类时作为配置文件使用的，就是用来配置容器的
 * 位置：在类的上面
 * SpringConfig这个类就相当于beans.xml（声明对象）
 */
@Configuration
public class SpringConfig {
    /**
     * 创建方法，方法的返回值是对象，在方法的上面加入@Bean
     * 方法的返回值对象就注入到容器中
     *
     * Bean：把对象注入到spring容器中，作用相当于<bean>
     * 位置：在类的上面
     * 说明：@Bean,不指定对象的名称，默认是方法名是id（这里就是createStudent）
     */
    @Bean
    public Student createStudent(){
        Student s1 = new Student();
        s1.setName("李威");
        s1.setAge(20);
        s1.setSex("男");
        return s1;
    }

    /**
     * 指定对象在容器中的名称（指定<bean>的id属性）
     * @Bean的name属性，指定对象的名称（id）
     */
    @Bean(name = "liyueStudent")
    public Student makeStudent(){
        Student s2 = new Student();
        s2.setName("李约");
        s2.setAge(21);
        s2.setSex("男");
        return s2;
    }
}
```

### 1.2 ImportResource

@ImportResouce 的作用：导入其他的xml配置文件，等于在xml文件中

```xml
<import resource="其他配置文件">
```

使用方式：

```java
@Configuration
@ImportResource(value = "classpath:applicationContext.xml")
/**
 * 类路径下的文件，即target下的classes
 * 多个value可以写作大括号下的多个文件，如
 * @ImportResource(value = {"classpath:applicationContext.xml","beans.xml"})
 */
public class SpringConfig{
}
```

### 1.3 PropertyResource

@PropertyResource 是读取properties属性配置文件的，可以实现外部化配置，在程序代码之外提供数据

步骤：

①在resources目录下，创建properties文件，使用k=v的格式提供数据

②在PropertyResource指定properties文件的位置

③使用@Value(value="${key}")

```java
@Configuration
@ImportResource(value = "classpath:applicationContext.xml")
@PropertySources(value = "classpath:config.properties")
@ComponentScan(basePackages = "com.uestc.vo")
public class SpringConfig {}
```

相较于原来在配置文件中

```java
<context:property-placeholder location="classpath:config.properties"/>
<context:component-scan base-package="com.uestc.vo"/>
<import resource="classpath:beans.xml"/>
```

变得更加简洁了

## 2 SpringBoot入门

核心还是IOC容器

特点：

1. 创建spring应用
2. 内嵌tomcat，jetty，Undertow服务器（不用安装就能执行web应用）
3. 提供了starter起步依赖，简化应用的配置。譬如在SpringBoot项目中，在pom.xml加入一个mybatis-spring-boot-starter依赖，则有关MyBatis的SqlSessionFactory，Dao的代理对象就可以直接生成了。
4. 尽可能去配置spring和第三方库，叫作自动配置
5. 提供了健康检查，统计，外部化配置
6. 自动生成代码，不同xml文件配置

### 2.1 创建方式一：初始化器

使用 springboot 提供的初始化器，通过向导的方式完成springboot项目的创建，使用更加方便。

国外访问地址为http://start.spring.io，国内访问地址为https://start.springboot.io。国外地址通常不稳定，国内地址速度相对快一点。

- 创建项目

![image-20220904224851610](D:\Typora\Notes\imgs\SpringBoot\image-20220904224851610.png)

常用依赖也可以在列表中找到，自动添加到pom.xml文件中。

### 2.2 创建方式二：maven向导

两者相比较，就是initializer可以在列表中选择添加的依赖，而maven向导则要手动添加；但initializer必须在联网的状态下运行。

initializer创建方法中会在pom.xml文件中自动生成，但通过maven工具生成则需要手动添加**父依赖**和**起步依赖**。

```xml
<!--父依赖-->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.3</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

<groupId>com.uestc</groupId>
<artifactId>springboot_third</artifactId>
<version>1.0.0</version>

<dependencies>
    <!--起步依赖-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

### 2.3 重要注解@SpringBootApplication

```java
@SpringBootApplication
```

复合注解

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
```

1. @SpringBootConfiguration

```java
@Configuration
public @interface SpringBootConfiguration {
    @AliasFor(
        annotation = Configuration.class
    )
    boolean proxyBeanMethods() default true;
}
```

声明对象，对象注入到容器中（使用了@SpringBootConfiguration注解标注的类可以作为配置文件使用，可以使用Bean声明对象，注入到容器）

2. @EnableAutoConfiguration

启用自动配置，把java对象配置好，注入到spring容器中，例如可以把mybatis的对象创建好，放到容器中

3. @ComponentScan

扫描器，用于找到注解，根据注解功能创建对象，给属性赋值等。

默认扫描的是，@ComponentScan注解所在的包和子包中包含的所有类（本目录中是SpringbootFirstApplication 被注解，即 uestc 包及其子包是扫描的范围）

![image-20220905204252301](D:\Typora\Notes\imgs\SpringBoot\image-20220905204252301.png)

因此常规操作是，**主类放在主包下面，其他类放在主包中子包的下面**。

### 2.4 配置文件

文件名称：application（固定）

#### 2.4.1 .properties

格式：key=value

例：

```properties
# 设置端口号
server.port=8082
# 设置访问应用的上下文路径 .contextpath
server.servlet.context-path=/myboot
```

#### 2.4.2 .yml

格式：(k：v)

例：

```yaml
server:
  port: 8083
  servlet:
    context-path: /myboot2
```

务必注意**层级之间和冒号之后的空格**

#### 2.4.3 共存

如果两个配置文件都存在，则会默认以 .properties 文件为主运行

![image-20220905213450041](D:\Typora\Notes\imgs\SpringBoot\image-20220905213450041.png)

### 2.5 多环境配置

在**开发**、**测试**、**上线**的不同情景下，接口、服务器等环境都是不一样的，为了方便在不同环境之间切换，提供了多环境配置。

每个环境有不同的配置信息，例如端口、上下文件、数据库url，用户名、密码等。

每个环境创建一个配置文件，命名必须以 **application-环境标识-properties|yml** 的格式

![image-20220906120849803](D:\Typora\Notes\imgs\SpringBoot\image-20220906120849803.png)

### 2.6 自定义配置

#### 2.6.1 @value

```java
@Value("${server.port}")
private Integer port;

@Value("${server.servlet.context-path}")
private String contextPath;
```

#### 2.6.2 @ConfigurationProperties 

相较于@Value，能够实现批量注入（配置）

以schoolInfo类为例

```java
@Component   // 用于创建此类的对象
@ConfigurationProperties(prefix = "school")  // 设置前缀
public class SchoolInfo {

    private String name;

    private String website;

    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SchoolInfo{" +
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
```

在 controller 中进行配置

```java
@Resource  // 用于自动注入
private SchoolInfo info;

@RequestMapping("/info")
@ResponseBody
public String queryInfo(){
    return "SchoolInfo对象：" + info.toString();
}
```

注意，没有前缀的话，配置时就是直接寻找=左侧的key

![image-20220906194000930](D:\Typora\Notes\imgs\SpringBoot\image-20220906194000930.png)

通过添加依赖，补充配置信息的解释

```xml
<!--处理ConfigurationProperties有关的元数据-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

### 2.7 JSP

SpringBoot不推荐使用JSP，而是使用模板技术代替JSP。

1. 首先加入一个处理JSP的依赖，负责编译jsp文件

```xml
<!--编译JSP文件-->
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-jasper</artifactId>
</dependency>
```

2. 另外如果需要使用servlet，jsp，jstl的功能，就再添加相应的依赖

```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
</dependency>

<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
</dependency>

<dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>javax.servlet.jsp-api</artifactId>
    <version>2.3.1</version>
</dependency>
```

###### Ctrl+Shift+O  -- pom.xml文件中加载更新后的依赖

3. 创建一个存放 jsp 的目录，一般叫做webapp（设置webapp的结构）

![image-20220906221020001](D:\Typora\Notes\imgs\SpringBoot\image-20220906221020001.png)

4. 需要在pom.xml指定jsp文件编译后的存放目录，即META-INF/resources

   ```xml
   <build>
      <!--指定jsp编译后的存放目录-->
      <resources>
         <resource>
            <!--jsp原来的目录-->
            <directory>src/main/webapp</directory>
            <!--指定编译后的存放目录-->
            <targetPath>META-INF/resources</targetPath>
            <!--指定处理的目录和文件-->
            <includes>
               <!--表示所有的目录下的所有文件类型-->
               <include>**/*.*</include>
            </includes>
         </resource>
      </resources>
       ...
   </build>
   ```

5. 创建Controller，访问jsp。有两种方式

```java
@Controller
public class JspController {
//    public String doJsp(HttpServletRequest request){
//        request.setAttribute("data", "StringBoot 使用 JSP");
//        // 视图的逻辑名称
//        return "Index";
//    }

    @RequestMapping("/myjsp")
    public String doJsp(Model model){

        // 把数据放入到request作用域
        model.addAttribute("data", "StringBoot 使用 JSP");
        // 视图的逻辑名称，之后需要配置视图解析器
        return "index";
    }
}
```

6. 在application.properties文件中配置视图解析器

```xml
#配置视图解析器
#/相当于src/main/webapp
spring.mvc.view.prefix=/
spring.mvc.view.suffix=.jsp
```

### 2.8 使用容器

通过SpringApplication.run(SpringbootJspApplication.class, args)的返回值获取容器

```java
@SpringBootApplication
public class SpringbootContainerApplication {

    public static void main(String[] args) {
        // 获取容器对象
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringbootContainerApplication.class, args);
        // 从容器中获取对象
        UserService userService = (UserService) ctx.getBean("userService");
        userService.sayHello("李月");
    }

}
```

### 2.9 CommandLineRunner 接口 & ApplicationRunner 接口

用于在**容器启动后**执行一些内容，譬如读取配置文件、数据库连接之类的。

CommandLineRunner 和 ApplicationRunner 接口都能实现，不同之处在于前者方法参数是String[]，后者方法参数是 ApplicationArguments。

```java
@SpringBootApplication
public class SpringbootCommandLineRunnerApplication implements CommandLineRunner {

    @Resource
    private HelloService helloService;

    public static void main(String[] args) {
        System.out.println("准备创建容器对象");
        // 创建容器对象
        SpringApplication.run(SpringbootCommandLineRunnerApplication.class, args);
        System.out.println("容器对象创建之后");
    }

    @Override
    public void run(String... args) throws Exception{
        String str = helloService.sayHello("liyue");
        System.out.println("调用容器中的对象" + str);
        // 可做自定义操作
        System.out.println("在容器对象创建好后执行的方法");
    }

}
```

## 3 SpringBoot 的Web组件
