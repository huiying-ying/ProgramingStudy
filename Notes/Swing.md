# Swing

Swing ，Java图形界面开发(Java GUl )

- AWT—— Java最早的界面库

- Swing——对AWT的扩展，轻量级设计的界面库

- JavaFX——自JDK1.8引入的新的界面库

- SWT—— Eclipse所使用的界面库，归Eclipse管理

- Android ——适用于手机、平板的界面GUI

- HarmonyOs / DevEco ——华为鸿蒙OS应用GUI

##### 空白窗口

```java
public static void main(String[] args) {
    // title 是窗口标题
    JFrame frame = new JFrame("Swing Example");
    // 当关闭窗口时，退出整个程序
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // 设置窗口其他参数，如窗口大小
    frame.setSize(400, 300);
    // 显示窗口
    frame.setVisible(true);
}
```

## 1 基本元素

### 1.1 JPanel & JButton（容器与控件）

**JPanel** --容器、面板

**JButton** --按钮控件

```java
// 创建容器对象并将其设置为窗口的内容
JPanel pannel = new JPanel();
frame.setContentPane(pannel);
// 创建按钮对象并将其设置为面板的内容
JButton button = new JButton("测试");
pannel.add(button);
```

### 1.2 JLabel（标签控件）

用于显示文本

```java
// 创建标签控件并添加到窗口
JLabel label = new JLabel("HELLO");
pannel.add(label);
// 或者可简写为 pannel.add(new JLabel("HELLO"));
```

### 1.3 自定义窗口

```java
public MyFrame(String title){
    // 调用父类方法设置窗口标题
    super(title);
    ...
}
```

### 1.4 事件处理

- 监听器机制

```java
// 内部类（在Frame类内部）
private class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("按钮被点击了");
        }
    }
```

```java
// 创建监听器并添加到button
MyActionListener listener = new MyActionListener();
button.addActionListener(listener);
// 
```

则每次点击时命令行都输出”按钮被点击了“

例：显示当前时间

```java
// 取得时间的方法
SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
String timeStr = sdf.format(new Date());
System.out.println(timeStr);
// 传入时间标签中
timeLabel.setText(timeStr);
```

注意 timeLabel 要在Frame 中作为属性（field）被声明，这样才可以在内部类中直接访问，整体代码如下

```java
public class MyFrame extends JFrame {

    // 创建标签控件作为成员变量，这样内部类也可以直接访问
    JLabel timeLabel = new JLabel("00:00:00");

    public MyFrame(String title){
        // 调用父类方法设置窗口标题
        super(title);
        JPanel pannel = new JPanel();
        this.setContentPane(pannel);
        // 创建按钮对象并将其设置为面板的内容
        JButton button = new JButton("测试");
        pannel.add(button);
        // 将成员标签控件添加到窗口
        pannel.add(timeLabel);

        // 创建监听器并添加到button
        MyActionListener listener = new MyActionListener();
        button.addActionListener(listener);
        // 可简写为 ActionListener listener = new MyActionListener();
        // 或者 button.addActionListener(new MyActionListener());
    }

    // 定义监听器
    private class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 取得时间的方法
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String timeStr = sdf.format(new Date());
            System.out.println(timeStr);
            // 传入时间标签中
            timeLabel.setText(timeStr);
            // 完整的写法是MyFrame.this.timeLabel.setText(timeStr);前面MyFrame.this.可以省略

        }
    }
}
```

#### 1.4.1 内部类

```java
private class MyActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        // 取得时间的方法
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timeStr = sdf.format(new Date());
        System.out.println(timeStr);
        // 传入时间标签中
        timeLabel.setText(timeStr);
        // 完整的写法是MyFrame.this.timeLabel.setText(timeStr);前面MyFrame.this.可以省略

    }
}
```

#### 1.4.2 匿名内部类

```java
// （匿名内部类写法）
button.addActionListener(new ActionListener(){

    @Override
    public void actionPerformed(ActionEvent e) {
        // 取得时间的方法
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timeStr = sdf.format(new Date());
        System.out.println(timeStr);
        // 传入时间标签中
        timeLabel.setText(timeStr);
        // 完整的写法是MyFrame.this.timeLabel.setText(timeStr);前面MyFrame.this.可以省略
    }
});
```

#### 1.4.3 Lambda表达式

**语法**：(参数) -> {方法体}

```java
// lambda写法，其中小括号里的e是参数，大括号里是方法体
button.addActionListener((e) -> {
    // 取得时间的方法
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    String timeStr = sdf.format(new Date());
    System.out.println(timeStr);
    // 传入时间标签中
    timeLabel.setText(timeStr);
    // 完整的写法是MyFrame.this.timeLabel.setText(timeStr);前面MyFrame.this.可以省略
});
```

### 1.5 文本框

```JAVA
public class MyFrameTextField extends JFrame {
    public MyFrameTextField (String title){
        super(title);

        JPanel root = new JPanel();
        this.setContentPane(root);

        // 参数20表示文本框长度
        JTextField textField = new JTextField(20);
        root.add(textField);
    }
}
```

###### JTextField 的API

设置文本   textField.setText  (str)

获取文本   textField.getText  ()

### 1.6 复选框

```java
JCheckBox checkBox = new JCheckBox("复选框");
root.add(checkBox);
// 界面初始化：设置其初始状态为未被选中
checkBox.setSelected(false);
button.setEnabled(false);
// 给checkBox添加事件处理
checkBox .addActionListener((e) -> {
    if(checkBox.isSelected())
        button.setEnabled(true);
    else
        button.setEnabled(false);
});
```

### 1.7 下拉框

```java
JComboBox<String> comboBox = new JComboBox<>();
root.add(comboBox);
comboBox.addItem("red");
comboBox.addItem("green");
comboBox.addItem("blue");
```

##### 常用函数

comboBox.getItemCount()  -- 得到下拉列表中的项数

comboBox.getSelectedIndex()  -- 得到所选项目的序号（从0开始）

comboBox.getItemAt(index)  -- 得到index序号的项目对象

### 1.8 标签控件

```java
JLabel label = new JLabel();
root.add(label);

label.setText("测试标签控件文字");
// 设置标签字体、风格、字号
label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
// 设置字体颜色
label.setForeground(new Color(0, 0, 0));
// 设置背景颜色
label.setOpaque(true);
label.setBackground(new Color(0, 255, 255));
// 设置控件大小
label.setPreferredSize(new Dimension(800, 200));
// 设置文本对齐
label.setHorizontalAlignment(SwingConstants.CENTER);
```

### 1.9 布局器

#### 1.9.1 Border布局器

```java
root.setLayout(new BorderLayout());
root.add(a1, BorderLayout.NORTH);
root.add(a2, BorderLayout.SOUTH);
root.add(a3, BorderLayout.EAST);
root.add(a4, BorderLayout.WEST);
root.add(a5, BorderLayout.CENTER);
```

![image-20220627203259768](D:\Typora\Notes\imgs\Swing\image-20220627203259768.png)

#### 1.9.2 绝对定位

```java
// 不使用布局器
root.setLayout(null);
root.add(a1);
a1.setBounds(0,0,100,50);
```

#### 1.9.3 自定义布局器

##### 布局适配器的作用

将addLayoutComponent  removeLayoutComponent  layoutContainer  这三个方法注释掉，也就是待实现，再让创建的实现类继承本抽象类，就只需要重写这3个方法就好了