# JavaSE

### this

this. 可出现在实例方法和构造方法中，不能在静态方法中出现。

this() 只能出现在构造方法第一行，通过当前构造方法去调用本类中其他构造方法，能够实现代码复用。

绝大多数情况this.可以省略，但在区分局部变量和实例变量时不能省略。

```java
public void setName(String name){
	this.name = name;
}
```

### super

super. 可出现在实例方法和构造方法中，不能在静态方法中出现。

super() 只能出现在构造方法第一行，通过当前构造方法调用父类中的构造方法，能够在创建子类对象的时候先初始化父类型特征。即使不写super也会默认调用父类的无参构造方法，但是super()和this()不共存。

主要用于子类在调用父类中的private变量或者方法。注意，子类和父类中允许有同名属性，this.调用时先仅子类，子类中没有就调用父类中的，此时super不能省略。

super不能访问父类私有。

## 集合Collection

### List

#### ArrayList

#### LinkedList

##### Queue

队列是一种特殊的线性表，它只允许在表的前端进行删除操作，而在表的后端进行插入操作。

**offer与add 作用一致：**不同的是在一个满的队列中加入一个新项，add()会抛出异常，而offer()则返回fasle。

**poll与remove作用一致：**不同的是对空集合取出时，romove()会抛出异常，而poll()则返回null。

**peek与element作用一致：**不同的是在队列为空时， element() 抛出一个异常，而 peek() 返回 null。

#### Vector



## IO流

#### 1 重点需要掌握的流

Input/OutputStream 均指的是字节流，用byte[]读和写

Writer/Reader 均指的是字符流，用char[]读和写

##### 文件专属

java.io.FileInputStream

java.io.FileOutputStream

java.io.FileReader

java.io.FileWriter

##### 转换流

java.io.InputStreamReader

java.io.OutputStreamWriter

##### 缓冲流专属

java.io.BufferedReader

java.io.BufferedWriter

java.io.BufferedInputStream

java.io.BufferedOutputStream

##### 数据流专属

java.io.DataInputStream

java.io.DataOutputStream

##### 对象专属流

java.io.ObjectInputStream

java.io.ObjectOutputStream

##### 标准输出流

java.io.PrintWriter

java.io.PrintStream 

#### 2 文件专属流

##### FileInputStream和jFileOutputStream

###### 搭建框架

###### 创建对象并排除异常

###### 利用byte数组高效读取

###### 基于读到的字节数目将所读内容转换为String

###### file.available()   --返回剩余未读字节数

###### file.skip(2)  --略过部分字节读取

###### 在流对象不空的情形下关闭流

```java
public static void main(String[] args) {
    // 创建文件字节流输入文件
    FileInputStream file = null;
    try {
        file = new FileInputStream("test");
        // 每次读取一个字节，读到末尾时返回-1
        System.out.println(file.read());

        // 返回读到的字节数目
        byte[] arry = new byte[4];
        int readnum = file.read(arry);
        System.out.println(readnum);

        // 将读到的字节转换为String，读多少转多少（由readNumber控制）
        int readNumber = 0;
        while ((readNumber = file.read(arry)) != -1){
            System.out.println(new String(arry, 0, readNumber));
        }
        // 返回剩余未读字节数
            System.out.println(file.available());

            // 跳过一部分
            file.skip(2);
            System.out.println(file.read());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {   // 避免空指针异常，同时确保流一定关闭
            if(file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
```

##### FileReader & FileWriter

两个字符流和字节流用法一致，只不过读写过程中使用char[]，并且只能面向普通文本文件。

并且Stream 用 int readNumber循环读取，Reader和Writer用Strring。

##### 复制文件

使用FileInputStream和jFileOutputStream可以实现任何类型的文件拷贝（Reader和Writer仅能用于文本文件）

```java
public class CopyTest {
    public static void main(String[] args) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream("test");
            fos = new FileOutputStream("test_copy");

            byte[] bytes = new byte[1024 * 1024];
            int readnum = 0;
            // 边读边写，任何文件类型都可以
            while ((readnum = fis.read(bytes)) != -1){
                fos.write(bytes, 0, readnum);
            }
            // 写操作之后都有一个刷新函数，保证流都传输完了
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 两个流分开关闭
            try {
                if (fos != null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(fis != null){
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
```

#### 3 缓冲流专属&转换流

##### BufferReader & BufferWriter  InputStreamReader&OutputStreamWriter

与文件流的区别在于可以直接读写，无须数组

**注**：构造方法中的参数有流时，参数涉及的流是**节点流**，外部承接的流时**包装流**。（关闭时只需关闭外部的包装流，里面的节点流可以自动关闭）

```java
public class BufferedReaderTest {
    public static void main(String[] args) throws IOException {
     // 与之前4种相比无需数组进行读写
     /*
     流的构造方法中如果需要另一个流，那么作为参数被传进来的流就是“节点流”，外部负责包装的流叫作“包装流”或“处理流”
      */
        FileReader reader = null;
        try {
            reader = new FileReader("test");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(reader);

//        String firstline = null;
//        try {
//            firstline = br.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(firstline);

        String s = null;
        while ((s = br.readLine()) != null){
            System.out.println(s);
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 如果使用BufferedReader想传字节流，就需要进行流转换
        FileInputStream in = new FileInputStream("log");
        InputStreamReader readerin = new InputStreamReader(in);
        BufferedReader brin = new BufferedReader(readerin);
        String line = null;
        while ((line = brin.readLine()) != null){
            System.out.println(line);
        }
        try {
            if(brin != null){
                brin.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

#### 4 数据流

保存时会同时保存类型和数据。需要按照顺序读写，且仅能以读的方式访问，文本文件无法显示

```java
public class DataOutputStreamTest {
    public static void main(String[] args) throws Exception{
        DataOutputStream out = new DataOutputStream(new FileOutputStream("DataTset"));
        byte b = 1;
        short s = 5;
        int i = 2;
        long l = 4L;
        float f = 3;
        double d = 6;
        char c = 'a';
        boolean bo = true;

        out.writeByte(b);
        out.writeChar(c);
        out.writeInt(i);
        out.writeShort(s);
        out.writeLong(l);
        out.writeFloat(f);
        out.writeDouble(d);
        out.writeBoolean(bo);

        out.flush();
        out.close();

        // 该流只能对应读写，在已知写的顺序的情况下读取
        DataInputStream in = new DataInputStream(new FileInputStream("DataTset"));
        byte b0 = in.readByte();
        char c0 = in.readChar();
        int i0 = in.readInt();
        short s0 = in.readShort();
        long l0 = in.readLong();
        float f0 = in.readFloat();
        double d0 = in.readFloat();
        boolean bo0 = in.readBoolean();
    }
}
```

#### 5 标准输出流

PrintStream不需要关闭

###### System.setOut()  -- 用于改变输出方向，典型应用是进行日志记录

```java
public class PrintStreamTest {
    public static void main(String[] args) throws Exception{
        // 此时输出方向是控制台
        PrintStream ps = System.out;
        ps.println("123");
        // 将标准输出流的输出方向由控制台指向log文件
        PrintStream printStream = new PrintStream(new FileOutputStream("log", true));
        System.setOut(printStream);  // 就是这里改的方向
        System.out.println("hello world");
        System.out.println("111");
    }
}
```

#### 7 File

File是路径名的抽象表现形式，可能是目录或者文件名

```java
public class FileTest {
    /*
    File 是文件和目录路径名的抽象表示形式，它不能完成文件的读和写
     */
    public static void main(String[] args) {
        File f1 = new File("D:\\file");
        System.out.println(f1.exists());
        // 若不存在则创建新的文件
        if(f1.exists() == false){
            try {
                f1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 若不存在则创建新目录
        if (f1.exists() == false){
            f1.mkdir();
        }

        // 获取父路径
        File f2 = new File("C:\\Users\\Sherry\\Desktop\\备忘");
        String parentPath = f2.getParent();
        System.out.println(parentPath);
        // 绝对路径
        String absolutePath = f2.getAbsolutePath();
        System.out.println(absolutePath);

        // 获取当前目录下的所有子文件
        File f = new File("C:\\Users\\Sherry\\Desktop");
        File[] files = f.listFiles();
        for (File file: files) {
            System.out.println(file.getName());
        }
        
        // 判断是否是目录
        System.out.println(f1.isDirectory());
        // 判断是否是文件
        System.out.println(f2.isFile());
        
        // 获取文件的最后一次修改时间
        long haomiao = f1.lastModified();
        Date lastTime = new Date(haomiao);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String tine = sdf.format(lastTime);
        System.out.println(tine);
    }
}
```