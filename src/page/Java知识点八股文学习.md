---
lang: zh-CN
title: 页面的标题
description: 页面的描述
---



# Java知识点八股文学习

制作人：**杨路恒**

## 一：Java基础

**HashMap、HashTable**：https://blog.csdn.net/yang13563758128/article/details/86655574?spm=1001.2014.3001.5502

### 异常

#### Java异常类层次结构?

- Throwable是 Java 语言中所有错误与异常的超类。 
  - **Error** 类及其子类：程序中无法处理的错误，表示运行应用程序中出现了严重的错误。
  - **Exception** 程序本身可以捕获并且可以处理的异常。Exception 这种异常又分为两类：运行时异常和编译时异常。
- **运行时异常**

都是RuntimeException类及其子类异常，如NullPointerException(空指针异常)、IndexOutOfBoundsException(下标越界异常)等，这些异常是不检查异常，程序中可以选择捕获处理，也可以不处理。这些异常一般是由程序逻辑错误引起的，程序应该从逻辑角度尽可能避免这类异常的发生。

运行时异常的特点是Java编译器不会检查它，也就是说，当程序中可能出现这类异常，即使没有用try-catch语句捕获它，也没有用throws子句声明抛出它，也会编译通过。

- **非运行时异常** （编译异常）

是RuntimeException以外的异常，类型上都属于Exception类及其子类。从程序语法角度讲是必须进行处理的异常，如果不处理，程序就不能编译通过。如IOException、SQLException等以及用户自定义的Exception异常，一般情况下不自定义检查异常。 

![ ](https://www.pdai.tech/images/java/java-basic-exception-1.png)

#### 可查的异常（checked exceptions）和不可查的异常（unchecked exceptions）区别？

- **可查异常**（编译器要求必须处置的异常）：

正确的程序在运行中，很容易出现的、情理可容的异常状况。可查异常虽然是异常状况，但在一定程度上它的发生是可以预计的，而且一旦发生这种异常状况，就必须采取某种方式进行处理。

除了RuntimeException及其子类以外，其他的Exception类及其子类都属于可查异常。这种异常的特点是Java编译器会检查它，也就是说，当程序中可能出现这类异常，要么用try-catch语句捕获它，要么用throws子句声明抛出它，否则编译不会通过。

- **不可查异常**(编译器不要求强制处置的异常)

包括运行时异常（RuntimeException与其子类）和错误（Error）。

#### throw和throws的区别？

- **异常的申明(throws)**

在Java中，当前执行的语句必属于某个方法，Java解释器调用main方法执行开始执行程序。若方法中存在检查异常，如果不对其捕获，那必须在方法头中显式声明该异常，以便于告知方法调用者此方法有异常，需要进行处理。 在方法中声明一个异常，方法头中使用关键字throws，后面接上要声明的异常。若声明多个异常，则使用逗号分割。如下所示：

```Java
public static void method() throws IOException, FileNotFoundException{
    //something statements
}

```

- **异常的抛出(throw)**

如果代码可能会引发某种错误，可以创建一个合适的异常类实例并抛出它，这就是抛出异常。如下所示：

```Java
public static double method(int value) {
    if(value == 0) {
        throw new ArithmeticException("参数不能为0"); //抛出一个运行时异常
    }
    return 5.0 / value;
}
```

#### Java 7 的 try-with-resource?

如果你的资源实现了 AutoCloseable 接口，你可以使用这个语法。大多数的 Java 标准资源都继承了这个接口。当你在 try 子句中打开资源，资源会在 try 代码块执行后或异常处理后自动关闭。

```Java
public void automaticallyCloseResource() {
    File file = new File("./tmp.txt");
    try (FileInputStream inputStream = new FileInputStream(file);) {
        // use the inputStream to read a file
    } catch (FileNotFoundException e) {
        log.error(e);
    } catch (IOException e) {
        log.error(e);
    }
}
```

#### 异常的底层？

提到JVM处理异常的机制，就需要提及Exception Table，以下称为异常表。我们暂且不急于介绍异常表，先看一个简单的 Java 处理异常的小例子。

```Java
public static void simpleTryCatch() {
   try {
       testNPE();
   } catch (Exception e) {
       e.printStackTrace();
   }
}
```

使用javap来分析这段代码（需要先使用javac编译）：

```Java
//javap -c Main
 public static void simpleTryCatch();
    Code:
       0: invokestatic  #3                  // Method testNPE:()V
       3: goto          11
       6: astore_0
       7: aload_0
       8: invokevirtual #5                  // Method java/lang/Exception.printStackTrace:()V
      11: return
    Exception table:
       from    to  target type
           0     3     6   Class java/lang/Exception
```

看到上面的代码，应该会有会心一笑，因为终于看到了Exception table，也就是我们要研究的异常表。

异常表中包含了一个或多个异常处理者(Exception Handler)的信息，这些信息包含如下

- **from** 可能发生异常的起始点
- **to** 可能发生异常的结束点
- **target** 上述from和to之前发生异常后的异常处理者的位置
- **type** 异常处理者处理的异常的类信息





### 反射

#### 什么是反射机制?

   JAVA反射机制是在运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法；对于任意一个对象，都能够调用它的任意一个方法和属性；这种动态获取的信息以及动态调用对象的法的功能称为java语言的反射机制。 

直接new对象就叫正射。

如下:

    Map<String, String> map = new HashMap<>();
    map.put("蔡徐鸡","唱跳rap篮球");

那反射是啥？我先不说反射是啥，概念啥的太虚幻我就不说了，把你绕蒙你这篇文章就白看了，直接举例吧

接着看上面的正射，如果哪天你发现用LinkedHashMap效果更好，然后你修改代码：

    Map<String, String> map = new LinkedHashMap<>();
    map.put("蔡徐鸡","唱跳rap篮球");

改完了编译运行没有bug然而过了两天你发现用LinkedHashMap会有隐患，还是得改回去用HashMap，成年人的崩溃如此简单，但是聪明的你想到可以加个判断，根据传入的条件来决定用HashMap还是LinkedHashMap，于是：

    public Map<String, String> getMap(String param) {
        Map<String, String> map = null;
        if (param.equals("HashMap")) {
            map = new HashMap<>();
        } else if (param.equals("LinkedHashMap")) {
            map = new LinkedHashMap<>();
         }
       return map;
     }

大功告成，这么难的逻辑都被你实现了，然后你得用TreeMap，你又要改代码。

有没有一种办法可以让你不修改代码呢，这时候反射就派上用场了。

**概念：反射是Java的一种机制，让我们可以在运行时获取类的信息**

**作用：通过反射，我们可以在程序运行时动态创建对象，还能获取到类的所有信息，比如它的属性、构造器、方法、注解等；**

直接举例吧：

```java
public Map<String, String> getMap(String className) {
    Class clazz = Class.forName(className);
    Constructor constructor = clazz.getConstructor();
    return (Map<String, String>) constructor.newInstance();
}
```

这时候不管你需要什么Map，只要实现了Map接口，你都能通过getMap获得，只需要传入对应Map的全限定名，例如java.util.HashMap / java.util.LinkedHashMap。

java中反射的用法非常非常多，常见的有以下这几个：

    一、在运行时获取一个类的 Class 对象
    二、在运行时构造一个类的实例化对象
    三、在运行时获取一个类的所有信息：变量、方法、构造器、注解

#### 一、获取class对象

三种方法
1、类名.class：这种获取方式只有在编译前已经声明了该类的类型才能获取到 Class 对象

    Class<HashMap> hashMap= HashMap.class;

2、实例.getClass()：通过实例化对象获取该实例的 Class 对象

    Map<String, String> hashMap = new HashMap<>();
    Class<? extends Map> hashMapClass = hashMap.getClass();

3、Class.forName(“类的全限定名”)：通过类的全限定名获取该类的 Class 对象

    Class<?> hashMap= Class.forName("java.util.HashMap");

拿到 Class对象就可以对它为所欲为了：调用它的方法、获取属性、获取类信息，总之它在你面前就没有隐私了，好羞羞，嘤~。

#### 二、构造类的实例化对象

通过反射构造一个类的实例方式有2种：
1、Class 对象调用newInstance()方法

    Class<?> hashMapClass = Class.forName("java.util.HashMap");
    HashMap hashMapInstance = (HashMap) hashMapClass.newInstance();

注意：即使 HashMap已经显式定义了构造方法，通过 newInstance() 创建的实例中，所有属性值都是对应类型的初始值，因为 newInstance() 构造实例会调用默认无参构造器。

2、Constructor 构造器调用newInstance()方法

    Class<?> hashMapClass = Class.forName("java.util.HashMap");
    Constructor<?> constructor = hashMapClass.getConstructor();
    constructor.setAccessible(true);
    HashMap newInstance = (HashMap) constructor.newInstance();

通过 getConstructor(Object… paramTypes) 方法指定获取指定参数类型的 Constructor， Constructor 调用 newInstance(Object… paramValues) 时传入构造方法参数的值，同样可以构造一个实例，且内部属性已经被赋值。

通过Class对象调用 newInstance() 会走默认无参构造方法，如果想通过显式构造方法构造实例，需要提前从Class中调用getConstructor()方法获取对应的构造器，通过构造器去实例化对象。

#### 三、获取类的所有信息

1、获取类中的变量（Field）

    Field[] getFields()：获取类中所有被public修饰的所有变量 Field getField(String
    name)：根据变量名获取类中的一个变量，该变量必须被public修饰 Field[]
    getDeclaredFields()：获取类中所有的变量，但无法获取继承下来的变量 Field
    getDeclaredField(String name)：根据姓名获取类中的某个变量，无法获取继承下来的变量

2、获取类中的方法（Method）

    Method[] getMethods()：获取类中被public修饰的所有方法
    Method getMethod(String name, Class…<?>
    paramTypes)：根据名字和参数类型获取对应方法，该方法必须被public修饰
    Method[] getDeclaredMethods()：获取所有方法，但无法获取继承下来的方法
    Method getDeclaredMethod(String name, Class…<?>
    paramTypes)：根据名字和参数类型获取对应方法，无法获取继承下来的方法

3、获取类的构造器（Constructor）

    Constuctor[] getConstructors()：获取类中所有被public修饰的构造器 Constructor
    getConstructor(Class…<?> paramTypes)：根据参数类型获取类中某个构造器，该构造器必须被public修饰
    Constructor[] getDeclaredConstructors()：获取类中所有构造器 Constructor
    getDeclaredConstructor(class…<?> paramTypes)：根据参数类型获取对应的构造器

反射的应用场景

    1、Spring 实例化对象：当程序启动时，Spring 会读取配置文件applicationContext.xml并解析出里面所有的标签实例化到IOC容器中。
    2、反射 + 工厂模式：通过反射消除工厂中的多个分支，如果需要生产新的类，无需关注工厂类，工厂类可以应对各种新增的类，反射可以使得程序更加健壮。
    3、JDBC连接数据库：使用JDBC连接数据库时，指定连接数据库的驱动类时用到反射加载驱动类

参考：https://blog.csdn.net/qq_33709582/article/details/113550163









### List







### Set









### Map





#### 为什么JDK1.8中HashMap从头插入改成尾插入

原文链接：https://blog.csdn.net/qq_35590459/article/details/108988011

JDK1.7中扩容时，每个元素的rehash之后，都会插入到新数组对应索引的链表头，所以这就导致原链表顺序为A->B->C，扩容之后，rehash之后的链表可能为C->B->A，元素的顺序发生了变化。在并发场景下，扩容时可能会出现循环链表的情况。而JDK1.8从头插入改成尾插入元素的顺序不变，避免出现循环链表的情况。

#### 为什么JDK1.8采用红黑树存储[Hash](https://so.csdn.net/so/search?q=Hash&spm=1001.2101.3001.7020)冲突的元素？

红黑树本质上是一棵二叉查找树，但它在二叉查找树的基础上增加了着色和相关的性质使得**红黑树相对平衡，从而保证了红黑树的查找、插入、删除的时间复杂度最坏为O(log n)。能够加快检索速率。**



#### 为什么在长度小于8时使用链表，不一直使用红黑树？

桶中元素的插入只会在hash冲突时发生，而hash冲突发生的概率较小，一直维护一个红黑树比链表耗费资源更多，在桶中元素量较小时没有这个必要。



#### 为什么要使用红黑树而不使用AVL树？

红黑树与AVLl树，在检索的时候效率差不多，都是通过平衡来二分查找。但红黑树不像avl树一样追求绝对的平衡，红黑树允许局部很少的不完全平衡，这样对于效率影响不大，但省去了很多没有必要的调平衡操作，avl树调平衡有时候代价较大，所以效率不如红黑树。



#### 为什么数组容量必须是2次幂？

索引计算公式为i = (n - 1) & hash，如果n为2次幂，那么n-1的低位就全是1，哈希值进行与操作时可以保证低位的值不变，从而保证分布均匀，效果等同于hash%n，但是位运算比取余运算要高效的多。



#### 为什么单链表转为红黑树要求桶内的元素个数大于8？

当hashCode离散性很好的时候，树型bin用到的概率非常小，因为数据均匀分布在每个bin中，几乎不会有bin中链表长度会达到阈值。但是在随机hashCode下，离散性可能会变差，然而JDK又不能阻止用户实现这种不好的hash算法，因此就可能导致不均匀的数据分布。不过理想情况下随机hashCode算法下所有bin中节点的分布频率会遵循泊松分布，而一个bin中链表长度达到8个元素的概率为0.00000006，几乎是不可能事件。

同理，少于8就从红黑树转回单链表是为了节省维护一个树的资源消耗，而选择8作为临界值，是因理想情况下一个bin中元素个数达到6的概率是0.00001316，达到7的概率为0.00000094，二者跨度较大，可以减小树和链表之间频繁转化的可能性。




### 数据结构

#### 数组

#### 链表

#### 栈

#### 队列

#### 二叉树

#### 红黑树

##### 1.简介

红黑树是一种自平衡的二叉查找树，是一种高效的查找树。红黑树具有良好的效率，它可在 O(logN) 时间内完成查找、增加、删除等操作。

##### 2.为什么需要红黑树？

对于二叉搜索树，如果插入的数据是随机的，那么它就是接近平衡的二叉树，平衡的二叉树，它的操作效率（查询，插入，删除）效率较高，时间复杂度是O（logN）。但是可能会出现一种极端的情况，那就是插入的数据是有序的（递增或者递减），那么所有的节点都会在根节点的右侧或左侧，此时，二叉搜索树就变为了一个链表，它的操作效率就降低了，时间复杂度为O(N)，所以可以认为二叉搜索树的时间复杂度介于O（logN）和O(N)之间，视情况而定。那么为了应对这种极端情况，红黑树就出现了，它是具备了某些特性的二叉搜索树，能解决非平衡树问题，红黑树是一种接近平衡的二叉树（说它是接近平衡因为它并没有像AVL树的平衡因子的概念，它只是靠着满足红黑节点的5条性质来维持一种接近平衡的结构，进而提升整体的性能，并没有严格的卡定某个平衡因子来维持绝对平衡）。

##### 3.红黑树的特性

首先，红黑树是一个二叉搜索树，它在每个节点增加了一个存储位记录节点的颜色，可以是RED,也可以是BLACK；通过任意一条从根到叶子简单路径上颜色的约束，**红黑树保证最长路径不超过最短路径的二倍，因而近似平衡（最短路径就是全黑节点，最长路径就是一个红节点一个黑节点，当从根节点到叶子节点的路径上黑色节点相同时，最长路径刚好是最短路径的两倍）**。它同时满足以下特性：

- 节点是**红色**或**黑色**

- 根是**黑色**

- 叶子节点（外部节点，空节点）都是**黑色，**这里的叶子节点指的是最底层的空节点（外部节点），下图中的那些null节点才是叶子节点，null节点的父节点在红黑树里不将其看作叶子节点

- 红色节点的子节点都是黑色；

  **红色**节点的父节点都是**黑色**；

  从根节点到叶子节点的所有路径上不能有 2 个连续的**红色**节点

- 从任一节点到叶子节点的所有路径都包含相同数目的**黑色**节点

![img](https://img-blog.csdnimg.cn/14c3c358dc3e4428b59add6dfe85b361.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bCP5LiDbW9k,size_20,color_FFFFFF,t_70,g_se,x_16)



##### 4.红黑树的效率

红黑树的查找，插入和删除操作，时间复杂度都是O(logN)。

**查找操作时**，它和普通的相对平衡的二叉搜索树的效率相同，都是通过相同的方式来查找的，没有用到红黑树特有的特性。

但如果**插入的时候**是有序数据，那么红黑树的查询效率就比二叉搜索树要高了，因为此时二叉搜索树不是平衡树，它的时间复杂度O(N)。

**插入和删除操作**时，由于红黑树的每次操作平均要旋转一次和变换颜色，所以它比普通的二叉搜索树效率要低一点，不过时间复杂度仍然是O(logN)。总之，红黑树的优点就是对有序数据的查询操作不会慢到O(N)的时间复杂度。

红黑树和AVL树的比较：

- AVL树的时间复杂度虽然优于红黑树，但是对于现在的计算机，cpu太快，可以忽略性能差异
- 红黑树的**插入删除**比AVL树更便于控制操作
- 红黑树整体性能略优于AVL树（红黑树旋转情况少于AVL树）



#### 哈希表结构



### 常见的设计模式

#### 软件设计原则有哪些？

![](http://www.img.youngxy.top/Java/fig/%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99.PNG)



#### 什么是设计模式？

设计模式（Design pattern）代表了最佳的实践，通常被有经验的⾯向对象的软件开发⼈员所采⽤。设计模式是软件开发⼈员在软件开发过程中⾯临的⼀般问题的解决⽅案。这些解决⽅案是众多软件开发⼈员经过相当⻓的⼀段时间的试验和错误总结出来的。

分为三大类：

**创建型**： 在创建对象的同时隐藏创建逻辑，不使⽤ new 直接实例化对象，程序在判断需要创建哪些对象时更灵活。包括⼯⼚/抽象⼯⼚/单例/建造者/原型模式。
**结构型**： 通过类和接⼝间的继承和引⽤实现创建复杂结构的对象。包括适配器/桥接模式/过滤器/组合/装饰器/外观/享元/代理模式。
**行为型**： 通过类之间不同通信⽅式实现不同⾏为。包括责任链/命名/解释器/迭代器/中介者/备忘录/观察者/状态/策略/模板/访问者模式。

#### 单例模式

单例模式属于创建型模式，⼀个单例类在任何情况下都只存在⼀个实例，构造⽅法必须是私有的、由自己创建⼀个静态变量存储实例，对外提供⼀个静态公有方法获取实例。

**双重检查锁（DCL， 即 double-checked locking）**
实现代码如下：

```Java
public class Singleton {
 
    // 1、私有化构造⽅法

    private Singleton() {

    }

    // 2、定义⼀个静态变量指向⾃⼰类型

    private volatile static Singleton instance;

    // 3、对外提供⼀个公共的⽅法获取实例

    public static Singleton getInstance() {

        // 第⼀重检查是否为 null

        if (instance == null) {

            // 使⽤ synchronized 加锁

            synchronized (Singleton.class) {

                // 第⼆重检查是否为 null
                if (instance == null) {

                    // new 关键字创建对象不是原⼦操作

                    instance = new Singleton();
                 }
             }
          }
            return instance;
        }
}
```

**优点：懒加载，线程安全，效率较⾼**
**缺点：实现较复杂**
这⾥的双重检查是指两次⾮空判断，锁指的是 synchronized 加锁，为什么要进⾏双重判断，其实很简单，第⼀重判断，如果实例已经存在，那么就不再需要进⾏同步操作，⽽是直接返回这个实例，如果没有创建，才会进⼊同步块，同步块的⽬的与之前相同，⽬的是为了防⽌有多个线程同时调⽤时，导致⽣成多个实例，有了同步块，每次只能有⼀个线程调⽤访问同步块内容，当第⼀个抢到锁的调⽤获取了实例之后，这个实例就会被创建，之后的所有调⽤都不会进⼊同步块，直接在第⼀重判断就返回了单例。关于内部的第⼆重空判断的作⽤，当多个线程⼀起到达锁位置时，进⾏锁竞争，其中⼀个线程获取锁，如果是第⼀次进⼊则为 null，会进⾏单例对象的创建，完成后释放锁，其他线程获取锁后就会被空判断拦截，直接返回已创建的单例对象。



#### 工厂模式

##### 说⼀说简单⼯⼚模式：

简单⼯⼚模式指由⼀个⼯⼚对象来创建实例，客户端不需要关注创建逻辑，只需提供传⼊⼯⼚的参数。

适⽤于⼯⼚类负责创建对象较少的情况，缺点是如果要增加新产品，就需要修改⼯⼚类的判断逻辑，违背开闭原则，且产品多的话会使⼯⼚类⽐较复杂。

Spring 中的 BeanFactory 使⽤简单⼯⼚模式，根据传⼊⼀个唯⼀的标识来获得 Bean 对象。



##### ⼯⼚⽅法模式了解吗：

和简单⼯⼚模式中⼯⼚负责⽣产所有产品相⽐，⼯⼚⽅法模式将⽣成具体产品的任务分发给具体的产品⼯⼚。

也就是定义⼀个抽象⼯⼚，其定义了产品的⽣产接⼝，但不负责具体的产品，将⽣产任务交给不同的派⽣类⼯⼚。这样不⽤通过指定类型来创建对象了。

##### 抽象⼯⼚模式了解吗：

简单⼯⼚模式和⼯⼚⽅法模式不管⼯⼚怎么拆分抽象，都只是针对⼀类产品，如果要⽣成另⼀种产品，就⽐较难办了！抽象⼯⼚模式通过在 AbstarctFactory 中增加创建产品的接⼝，并在具体⼦⼯⼚中实现新加产品的创建，当然前提是⼦⼯⼚⽀持⽣产该产品。否则继承的这个接⼝可以什么也不⼲。



#### 装饰器模式





#### 代理模式

##### 什么是代理模式？

代理模式的本质是⼀个中间件，主要⽬的是解耦合服务提供者和使⽤者。使⽤者通过代理间接的访问服务提供者，便于后者的封装和控制，是⼀种结构性模式。

##### 静态代理和动态代理的区别：

1. 灵活性 ：动态代理更加灵活，不需要必须实现接⼝，可以直接代理实现类，并且可以不需要针对每个⽬标类都创建⼀个代理类。另外，静态代理中，接⼝⼀旦新增加⽅法，⽬标对象和代理对象都要进⾏修改，这是⾮常麻烦的！

2. JVM 层⾯ ：静态代理在编译时就将接⼝、实现类、代理类这些都变成了⼀个个实际的 class ⽂件。⽽动态代理是在运⾏时动态⽣成类字节码，并加载到 JVM 中的。

##### 静态代理：

**静态代理中，我们对目标对象的每个方法的增强都是手动完成的（\*后面会具体演示代码\*），非常不灵活（\*比如接口一旦新增加方法，目标对象和代理对象都要进行修改\*）且麻烦(\*需要对每个目标类都单独写一个代理类\*）。** 实际应用场景非常非常少，日常开发几乎看不到使用静态代理的场景。

上面我们是从实现和应用角度来说的静态代理，从 JVM 层面来说， 静态代理在编译时就将接口、实现类、代理类这些都变成了一个个实际的 class 文件。

##### 动态代理：

相比于静态代理来说，动态代理更加灵活。我们不需要针对每个目标类都单独创建一个代理类，并且也不需要我们必须实现接口，我们可以直接代理实现类( *CGLIB 动态代理机制*)。

**从 JVM 角度来说，动态代理是在运行时动态生成类字节码，并加载到 JVM 中的。**

说到动态代理，Spring AOP、RPC 框架应该是两个不得不提的，它们的实现都依赖了动态代理。

**动态代理在我们日常开发中使用的相对较少，但是在框架中的几乎是必用的一门技术。学会了动态代理之后，对于我们理解和学习各种框架的原理也非常有帮助。**

就 Java 来说，动态代理的实现方式有很多种，比如 **JDK 动态代理**、**CGLIB 动态代理**等等。

###### JDK 动态代理机制

**在 Java 动态代理机制中 `InvocationHandler` 接口和 `Proxy` 类是核心。**

`Proxy` 类中使用频率最高的方法是：`newProxyInstance()` ，这个方法主要用来生成一个代理对象。

```
    public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h)
        throws IllegalArgumentException
    {
        ......
    }


这个方法一共有 3 个参数：

    loader :类加载器，用于加载代理对象。
    interfaces : 被代理类实现的一些接口；
    h : 实现了 InvocationHandler 接口的对象；
```

要实现动态代理的话，还必须需要实现`InvocationHandler` 来自定义处理逻辑。 当我们的动态代理对象调用一个方法时，这个方法的调用就会被转发到实现`InvocationHandler` 接口类的 `invoke` 方法来调用。

```
public interface InvocationHandler {

    /**
     * 当你使用代理对象调用方法的时候实际会调用到这个方法
     */
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable;
}

invoke() 方法有下面三个参数：

    proxy :动态生成的代理类
    method : 与代理类对象调用的方法相对应
    args : 当前 method 方法的参数
```

也就是说：**你通过`Proxy` 类的 `newProxyInstance()` 创建的代理对象在调用方法的时候，实际会调用到实现`InvocationHandler` 接口的类的 `invoke()`方法。** 你可以在 `invoke()` 方法中自定义处理逻辑，比如在方法执行前后做什么事情。



###### CGLIB 动态代理机制

**JDK 动态代理有一个最致命的问题是其只能代理实现了接口的类。**

**为了解决这个问题，我们可以用 CGLIB 动态代理机制来避免。**

CGLIB*Code Generation Library*)允许我们在运行时对字节码进行修改和动态生成。CGLIB 通过继承方式实现代理。例如 Spring 中的 AOP 模块中：如果目标对象实现了接口，则默认采用 JDK 动态代理，否则采用 CGLIB 动态代理。

**在 CGLIB 动态代理机制中 `MethodInterceptor` 接口和 `Enhancer` 类是核心。**

你需要自定义 `MethodInterceptor` 并重写 `intercept` 方法，`intercept` 用于拦截增强被代理类的方法。

```
public interface MethodInterceptor
extends Callback{
    // 拦截被代理类中的方法
    public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args,MethodProxy proxy) throws Throwable;
}

    obj : 被代理的对象（需要增强的对象）
    method : 被拦截的方法（需要增强的方法）
    args : 方法入参
    proxy : 用于调用原始方法

```

你可以通过 `Enhancer`类来动态获取被代理类，当代理类调用方法的时候，实际调用的是 `MethodInterceptor` 中的 `intercept` 方法。

###### JDK 动态代理和 CGLIB 动态代理对比：

- **JDK 动态代理只能代理实现了接口的类或者直接代理接口，而 CGLIB 可以代理未实现任何接口的类。** 另外， CGLIB 动态代理是通过生成一个被代理类的子类来拦截被代理类的方法调用，因此不能代理声明为 final 类型的类和方法。
- 就二者的效率来说，大部分情况都是 JDK 动态代理更优秀，随着 JDK 版本的升级，这个优势更加明显。



## 二：JVM

### 基本概念

##### 说一下 Jvm 的主要组成部分？及其作用？

1. 类加载器（ClassLoader）
2. 运行时数据区（Runtime Data Area）
3. 执行引擎（Execution Engine）
4. 本地库接口（Native Interface）

首先通过类加载器（ClassLoader）会把 Java 代码转换成字节码，运行时数据区（Runtime Data Area）再把字节码加载到内存中，而字节码文件只是 JVM 的一套指令集规范，并不能直接交给底层操作系统去执行，因此需要特定的命令解析器执行引擎（Execution Engine），将字节码翻译成底层系统指令，再交由 CPU 去执行，而这个过程中需要调用其他语言的本地库接口（Native Interface）来实现整个程序的功能。



### 内存结构（运行时数据区）

![java-runtime-data-areas-jdk1.7](C:\Users\Administrator\Desktop\简历\图片\java-runtime-data-areas-jdk1.7.png)

![java-runtime-data-areas-jdk1.8](C:\Users\Administrator\Desktop\简历\图片\java-runtime-data-areas-jdk1.8.png)



### 垃圾回收机制

#### 如何判断对象是否可回收？

#### 垃圾回收算法

#### 垃圾收集器



### 类加载机制

#### 类的生命周期

类从被加载到虚拟机内存中开始到卸载出内存为止，它的整个生命周期可以简单概括为 7 个阶段：：加载（Loading）、验证（Verification）、准备（Preparation）、解析（Resolution）、初始化（Initialization）、使用（Using）和卸载（Unloading）。其中，前三个阶段可以统称为连接（Linking）。

这 7 个阶段的顺序如下图所示：

![一个类的完整生命周期](https://oss.javaguide.cn/github/javaguide/java/jvm/lifecycle-of-a-class.png)



#### 类加载过程

**Class 文件需要加载到虚拟机中之后才能运行和使用，那么虚拟机是如何加载这些 Class 文件呢？**

系统加载 Class 类型的文件主要三步：**加载->连接->初始化**。连接过程又可分为三步：**验证->准备->解析**。

![类加载过程](https://oss.javaguide.cn/github/javaguide/java/jvm/class-loading-procedure.png)



#### 类加载器

##### 介绍：

- 类加载器是一个负责加载类的对象，用于实现类加载过程中的加载这一步。
- 每个 Java 类都有一个引用指向加载它的 `ClassLoader`。
- 数组类不是通过 `ClassLoader` 创建的（数组类没有对应的二进制字节流），是由 JVM 直接生成的。

简单来说，**类加载器的主要作用就是加载 Java 类的字节码（ `.class` 文件）到 JVM 中（在内存中生成一个代表该类的 `Class` 对象）。**

##### 类加载器加载规则

JVM 启动的时候，并不会一次性加载所有的类，而是根据需要去动态加载。也就是说，大部分类在具体用到的时候才会去加载，这样对内存更加友好。

对于已经加载的类会被放在 `ClassLoader` 中。在类加载的时候，系统会首先判断当前类是否被加载过。已经被加载的类会直接返回，否则才会尝试加载。也就是说，对于一个类加载器来说，相同二进制名称的类只会被加载一次。

##### 类加载器总结

JVM 中内置了三个重要的 `ClassLoader`：

1. **`BootstrapClassLoader`(启动类加载器)** ：最顶层的加载类，由 C++实现，通常表示为 null，并且没有父级，主要用来加载 JDK 内部的核心类库（ `%JAVA_HOME%/lib`目录下的 `rt.jar` 、`resources.jar` 、`charsets.jar`等 jar 包和类）以及被 `-Xbootclasspath`参数指定的路径下的所有类。

2. **`ExtensionClassLoader`(扩展类加载器)** ：主要负责加载 `%JRE_HOME%/lib/ext` 目录下的 jar 包和类以及被 `java.ext.dirs` 系统变量所指定的路径下的所有类。

3. **`AppClassLoader`(应用程序类加载器)** ：面向我们用户的加载器，负责加载当前应用 classpath 下的所有 jar 包和类。

   

   除了这三种类加载器之外，用户还可以加入自定义的类加载器来进行拓展，以满足自己的特殊需求。就比如说，我们可以对 Java 类的字节码（ `.class` 文件）进行加密，加载时再利用自定义的类加载器对其解密。

   如果我们要自定义自己的类加载器，很明显需要继承 `ClassLoader`抽象类。

   `ClassLoader` 类有两个关键的方法：

   - `protected Class loadClass(String name, boolean resolve)`：加载指定二进制名称的类，实现了双亲委派机制 。`name` 为类的二进制名称，`resove` 如果为 true，在加载时调用 `resolveClass(Class<?> c)` 方法解析该类。
   - `protected Class findClass(String name)`：根据类的二进制名称来查找类，默认实现是空方法。

   官方 API 文档中写到：

   > Subclasses of `ClassLoader` are encouraged to override `findClass(String name)`, rather than this method.
   >
   > 建议 `ClassLoader`的子类重写 `findClass(String name)`方法而不是`loadClass(String name, boolean resolve)` 方法。

   如果我们不想打破双亲委派模型，就重写 `ClassLoader` 类中的 `findClass()` 方法即可，无法被父类加载器加载的类最终会通过这个方法被加载。但是，如果想打破双亲委派模型则需要重写 `loadClass()` 方法。

   

![类加载器层次关系图](https://oss.javaguide.cn/github/javaguide/java/jvm/class-loader-parents-delegation-model.png)



##### 双亲委派模型

类加载器有很多种，当我们想要加载一个类的时候，具体是哪个类加载器加载呢？这就需要提到双亲委派模型了。

- `ClassLoader` 类使用委托模型来搜索类和资源。
- 双亲委派模型要求除了顶层的启动类加载器外，其余的类加载器都应有自己的父类加载器。
- `ClassLoader` 实例会在试图亲自查找类或资源之前，将搜索类或资源的任务委托给其父类加载器。

下图展示的各种类加载器之间的层次关系被称为类加载器的“**双亲委派模型(Parents Delegation Model)**”。

![类加载器层次关系图](https://oss.javaguide.cn/github/javaguide/java/jvm/class-loader-parents-delegation-model.png)

双亲委派模型的实现代码非常简单，逻辑非常清晰，都集中在 `java.lang.ClassLoader` 的 `loadClass()` 中，相关代码如下所示。

```Java
protected Class<?> loadClass(String name, boolean resolve)
    throws ClassNotFoundException
{
    synchronized (getClassLoadingLock(name)) {
        //首先，检查该类是否已经加载过
        Class c = findLoadedClass(name);
        if (c == null) {
            //如果 c 为 null，则说明该类没有被加载过
            long t0 = System.nanoTime();
            try {
                if (parent != null) {
                    //当父类的加载器不为空，则通过父类的loadClass来加载该类
                    c = parent.loadClass(name, false);
                } else {
                    //当父类的加载器为空，则调用启动类加载器来加载该类
                    c = findBootstrapClassOrNull(name);
                }
            } catch (ClassNotFoundException e) {
                //非空父类的类加载器无法找到相应的类，则抛出异常
            }

            if (c == null) {
                //当父类加载器无法加载时，则调用findClass方法来加载该类
                //用户可通过覆写该方法，来自定义类加载器
                long t1 = System.nanoTime();
                c = findClass(name);

                //用于统计类加载器相关的信息
                sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                sun.misc.PerfCounter.getFindClasses().increment();
            }
        }
        if (resolve) {
            //对类进行link操作
            resolveClass(c);
        }
        return c;
    }
}

```

每当一个类加载器接收到加载请求时，它会先将请求转发给父类加载器。在父类加载器没有找到所请求的类的情况下，该类加载器才会尝试去加载。

结合上面的源码，简单总结一下双亲委派模型的执行流程：

- 在类加载的时候，系统会首先判断当前类是否被加载过。已经被加载的类会直接返回，否则才会尝试加载（每个父类加载器都会走一遍这个流程）。
- 类加载器在进行类加载的时候，它首先不会自己去尝试加载这个类，而是把这个请求委派给父类加载器去完成（调用父加载器 `loadClass()`方法来加载类）。这样的话，所有的请求最终都会传送到顶层的启动类加载器 `BootstrapClassLoader` 中。
- 只有当父加载器反馈自己无法完成这个加载请求（它的搜索范围中没有找到所需的类）时，子加载器才会尝试自己去加载（调用自己的 `findClass()` 方法来加载类）。

**优点：**

双亲委派模型保证了 Java 程序的稳定运行，可以避免类的重复加载（JVM 区分不同类的方式不仅仅根据类名，相同的类文件被不同的类加载器加载产生的是两个不同的类），也保证了 Java 的核心 API 不被篡改。



## 三：多线程与高并发

### 线程的互斥同步方式有哪些? 如何比较和选择?

Java 提供了两种锁机制来控制多个线程对共享资源的互斥访问，第一个是 JVM 实现的 synchronized，而另一个是 JDK 实现的 ReentrantLock。

1. 锁的实现synchronized 是 JVM 实现的，而 ReentrantLock 是 JDK 实现的。

2. 性能新版本 Java 对 synchronized 进行了很多优化，例如自旋锁等，synchronized 与 ReentrantLock 大致相同。

3. 等待可中断当持有锁的线程长期不释放锁的时候，正在等待的线程可以选择放弃等待，改为处理其他事情。ReentrantLock 可中断，而 synchronized 不行。

4. 公平锁公平锁是指多个线程在等待同一个锁时，必须按照申请锁的时间顺序来依次获得锁。synchronized 中的锁是非公平的，ReentrantLock 默认情况下也是非公平的，但是也可以是公平的。

5. 锁绑定多个条件一个 ReentrantLock 可以同时绑定多个 Condition 对象。

   

   #### ReentrantLock：
   
   `ReentrantLock` 实现了 `Lock` 接口，是一个可重入且独占式的锁，和 `synchronized` 关键字类似。不过，`ReentrantLock` 更灵活、更强大，增加了轮询、超时、中断、公平锁和非公平锁等高级功能。
   
   ```
   public class ReentrantLock implements Lock, java.io.Serializable {}
   ```
   
   `ReentrantLock` 里面有一个内部类 `Sync`，`Sync` 继承 AQS（`AbstractQueuedSynchronizer`），添加锁和释放锁的大部分操作实际上都是在 `Sync` 中实现的。`Sync` 有公平锁 `FairSync` 和非公平锁 `NonfairSync` 两个子类。
   
   `ReentrantLock` 默认使用非公平锁，也可以通过构造器来显式的指定使用公平锁。
   
   ```
   // 传入一个 boolean 值，true 时为公平锁，false 时为非公平锁
   public ReentrantLock(boolean fair) {
       sync = fair ? new FairSync() : new NonfairSync();
   }
   
   ```
   
   https://blog.csdn.net/zhengzhaoyang122/article/details/110847701；https://javaguide.cn/java/concurrent/java-concurrent-questions-02.html

### 锁升级

 

![img](https://img-blog.csdnimg.cn/img_convert/cb70e785275ec6276cbc544b031e3ffd.png)



#### 无锁

无锁并不会对资源锁定，所有的线程都可以访问并修改同一个资源，但同时只有一个线程能修改成功。也就是我们常说的乐观锁。

#### 偏向锁

偏向于第一个访问锁的线程，初次执行synchronized代码块时，通过 CAS 修改对象头里的锁标志位，锁对象变成偏向锁。

当一个线程访问同步代码块并获取锁时，会在 Mark Word 里存储锁偏向的线程 ID。在线程进入和退出同步块时不再通过 CAS 操作来加锁和解锁，而是检测 Mark Word 里是否存储着指向当前线程的偏向锁。轻量级锁的获取及释放依赖多次 CAS 原子指令，而偏向锁只需要在置换 ThreadID 的时候依赖一次 CAS 原子指令即可。

执行完同步代码块后，线程并不会主动释放偏向锁。当线程第二次再执行同步代码块时，线程会判断此时持有锁的线程是否就是自己（持有锁的线程ID也在对象头里），如果是则正常往下执行。由于之前没有释放锁，这里不需要重新加锁，偏向锁几乎没有额外开销，性能极高。

偏向锁只有遇到其他线程尝试竞争偏向锁时，持有偏向锁的线程才会释放锁，线程是不会主动释放偏向锁的。关于偏向锁的撤销，需要等待全局安全点，即在某个时间点上没有字节码正在执行时，它会先暂停拥有偏向锁的线程，然后判断锁对象是否处于被锁定状态。如果线程不处于活动状态，则将对象头设置成无锁状态，并撤销偏向锁，恢复到无锁（标志位为01）或轻量级锁（标志位为00）的状态。

    偏向锁是指当一段同步代码一直被同一个线程所访问时，即不存在多个线程的竞争时，那么该线程在后续访问时便会自动获得锁，从而降低获取锁带来的消耗。

#### 轻量级锁

当前锁是偏向锁，此时有多个线程同时来竞争锁，偏向锁就会升级为轻量级锁。轻量级锁认为虽然竞争是存在的，但是理想情况下竞争的程度很低，通过自旋方式来获取锁。

轻量级锁的获取有两种情况：

    当关闭偏向锁功能时
    
    多个线程竞争偏向锁导致偏向锁升级为轻量级锁。一旦有第二个线程加入锁竞争，偏向锁就升级为轻量级锁（自旋锁）

在轻量级锁状态下继续锁竞争，没有抢到锁的线程将自旋，不停地循环判断锁是否能够被成功获取。获取锁的操作，其实就是通过CAS修改对象头里的锁标志位。先比较当前锁标志位是否为“释放”，如果是则将其设置为“锁定”，此过程是原子性。如果抢到锁，然后线程将当前锁的持有者信息修改为自己。

#### 重量级锁

如果线程的竞争很激励，线程的自旋超过了一定次数（默认循环10次，可以通过虚拟机参数更改），将轻量级锁升级为重量级锁（依然是 CAS  修改锁标志位，但不修改持有锁的线程ID），当后续线程尝试获取锁时，发现被占用的锁是重量级锁，则直接将自己挂起（而不是忙等），等待将来被唤醒。

重量级锁是指当有一个线程获取锁之后，其余所有等待获取该锁的线程都会处于阻塞状态。简言之，就是所有的控制权都交给了操作系统，由操作系统来负责线程间的调度和线程的状态变更。而这样会出现频繁地对线程运行状态的切换，线程的挂起和唤醒，从而消耗大量的系统资。



#### 锁优化技术（锁粗化、锁消除）：

**锁粗化**就是告诉我们任何事情都有个度，有些情况下我们反而希望把很多次锁的请求合并成一个请求，以降低短时间内大量锁请求、同步、释放带来的性能损耗。

**锁消除**指的是在某些情况下，JVM 虚拟机如果检测不到某段代码被共享和竞争的可能性，就会将这段代码所属的同步锁消除掉，从而到底提高程序性能的目的。





### CAS

https://mp.weixin.qq.com/s?__biz=MzU0OTE4MzYzMw==&mid=2247510278&idx=3&sn=a58a9ec9f805d0e109c4b09cd497c9ca&chksm=fbb120f8ccc6a9eecd9a0cc3aff335b12c88b3e9fa041008c1d970fe5f0bf564ca94e37946c1&scene=27



在并发编程中我们都知道`i++`操作是非线程安全的，这是因为 `i++`操作不是原子操作。

如何保证原子性呢？常用的方法就是`加锁`。在Java语言中可以使用 `Synchronized`和`CAS`实现加锁效果。

`Synchronized`是悲观锁，线程开始执行第一步就是获取锁，一旦获得锁，其他的线程进入后就会阻塞等待锁。如果不好理解，举个生活中的例子：一个人进入厕所后首先把门锁上（获取锁），然后开始上厕所，这个时候有其他人来了只能在外面等（阻塞），就算再急也没用。上完厕所完事后把门打开（解锁），其他人就可以进入了。

`CAS`是乐观锁，线程执行的时候不会加锁，假设没有冲突去完成某项操作，如果因为冲突失败了就重试，最后直到成功为止。

#### 什么是 CAS？

CAS（Compare-And-Swap）是`比较并交换`的意思，它是一条 CPU 并发原语，用于判断内存中某个值是否为预期值，如果是则更改为新的值，这个过程是`原子`的。下面用一个小示例解释一下。

CAS机制当中使用了3个基本操作数：内存地址V，旧的预期值A，计算后要修改后的新值B。

（1）初始状态：在内存地址V中存储着变量值为 1。

![图片](https://mmbiz.qpic.cn/mmbiz_png/RXvHpViaz3EppbYg2u6sqDRqZUGMvKjw1JK3vRD1y024uia8g2eLJib7ZMw8GiaLlKGcEFOgHtr4AGtXmVE9TlG8dQ/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

（2）线程1想要把内存地址为 V 的变量值增加1。这个时候对线程1来说，旧的预期值A=1，要修改的新值B=2。

![图片](https://mmbiz.qpic.cn/mmbiz_png/RXvHpViaz3EppbYg2u6sqDRqZUGMvKjw19pEib6mIDOibSzfVQRvqPxr0icOrBzsD4SnUJNRXEbUoicGERI9HKHCqkg/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

（3）在线程1要提交更新之前，线程2捷足先登了，已经把内存地址V中的变量值率先更新成了2。

![图片](https://mmbiz.qpic.cn/mmbiz_png/RXvHpViaz3EppbYg2u6sqDRqZUGMvKjw1qTQ3iau6tlmV7PZaghsuibpVQzDJ5NiaCKWw9Llo1BXHKumaJ3riaNiaaug/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

（4）线程1开始提交更新，首先将预期值A和内存地址V的实际值比较（Compare），发现A不等于V的实际值，提交失败。

![图片](https://mmbiz.qpic.cn/mmbiz_png/RXvHpViaz3EppbYg2u6sqDRqZUGMvKjw1fhpCqkjbNUwYe8Exkfxrt43ttk9icoQRMMYPrRmgGmzmjk1YC24Rm5A/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

（5）线程1重新获取内存地址 V 的当前值，并重新计算想要修改的新值。此时对线程1来说，A=2，B=3。这个重新尝试的过程被称为`自旋`。如果多次失败会有多次自旋。

![图片](https://mmbiz.qpic.cn/mmbiz_png/RXvHpViaz3EppbYg2u6sqDRqZUGMvKjw1O3j4gDwXephYUWMYFrEs2Niba3ITeib4icdL7fj46eSmIxprp5nB5uK6Q/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)



（6）线程 1 再次提交更新，这一次没有其他线程改变地址 V 的值。线程1进行Compare，发现预期值 A 和内存地址 V的实际值是相等的，进行 Swap 操作，将内存地址 V 的实际值修改为 B。

![图片](https://mmbiz.qpic.cn/mmbiz_png/RXvHpViaz3EppbYg2u6sqDRqZUGMvKjw1gA641Y4CXAHIlweg4ZUZVk0A4JiaMOlm7eB8vYv4qnruDYbxYehX3icA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

总结：更新一个变量的时候，只有当变量的预期值 A 和内存地址 V 中的实际值相同时，才会将内存地址 V 对应的值修改为 B，这整个操作就是`CAS`。



#### CAS 基本原理

CAS 主要包括两个操作：`Compare`和`Swap`，有人可能要问了：两个操作能保证是原子性吗？可以的。

CAS 是一种`系统原语`，原语属于操作系统用语，原语由若干指令组成，用于完成某个功能的一个过程，并且原语的执行必须是连续的，在执行过程中不允许被中断，也就是说 CAS 是一条 CPU 的原子指令，由操作系统硬件来保证。在 Intel 的 CPU 中，使用 cmpxchg 指令。

回到 Java 语言，JDK 是在 1.5 版本后才引入 CAS 操作，在`sun.misc.Unsafe`这个类中定义了 CAS 相关的方法。

在 Java 编程中我们通常不会直接使用到 CAS，都是通过 JDK 封装好的并发工具类来间接使用的，这些并发工具类都在`java.util.concurrent`包中。



#### CAS 的问题

CAS 不是万能的，也有很多问题。

`敲黑板：CAS有哪些问题，这是面试高频考点，需要重点掌握`。

##### 典型 ABA 问题

ABA 是 CAS 操作的一个经典问题，假设有一个变量初始值为 A，修改为 B，然后又修改为 A，这个变量实际被修改过了，但是 CAS 操作可能无法感知到。

如果是整型还好，不会影响最终结果，但如果是对象的引用类型包含了多个变量，引用没有变实际上包含的变量已经被修改，这就会造成大问题。

如何解决？思路其实很简单，在变量前加版本号，每次变量更新了就把版本号加一，结果如下：

![图片](https://mmbiz.qpic.cn/mmbiz_png/RXvHpViaz3EppbYg2u6sqDRqZUGMvKjw1g6kufWOzbKW7NmjfooQbm5Jwicl5ljECYaZUpFBIBJmgawXWybC0zxQ/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

最终结果都是 A 但是版本号改变了。

从 JDK 1.5 开始提供了`AtomicStampedReference`类，这个类的 `compareAndSe`方法首先检查`当前引用`是否等于`预期引用`，并且`当前标志`是否等于`预期标志`，如果全部相等，则以原子方式将该引用和该标志的值设置为给定的更新值。



##### 自旋开销问题

CAS 出现冲突后就会开始`自旋`操作，如果资源竞争非常激烈，自旋长时间不能成功就会给 CPU 带来非常大的开销。

解决方案：可以考虑限制自旋的次数，避免过度消耗 CPU；另外还可以考虑延迟执行。



##### 只能保证单个变量的原子性

当对一个共享变量执行操作时，可以使用 CAS 来保证原子性，但是如果要对多个共享变量进行操作时，CAS 是无法保证原子性的，比如需要将 i 和 j 同时加 1：

```
i++；j++；
```

这个时候可以使用 synchronized 进行加锁，有没有其他办法呢？有，将多个变量操作合成一个变量操作。从 JDK1.5 开始提供了`AtomicReference` 类来保证引用对象之间的原子性，你可以把多个变量放在一个对象里来进行CAS操作。

#### 总结

CAS 是 Compare And Swap，是一条 CPU 原语，由操作系统保证原子性。

Java语言从 JDK1.5 版本开始引入 CAS ， 并且是 Java 并发编程J.U.C 包的基石，应用非常广泛。

当然 CAS 也不是万能的，也有很多问题：典型 ABA 问题、自旋开销问题、只能保证单个变量的原子性。



### AQS

https://zhuanlan.zhihu.com/p/543902719

#### 什么是AQS

AQS（AbstractQueuedSynchronizer），即队列同步器，它是构建锁或者其他同步组件的基础框架，如ReentrantLock、ReentrantReadWriteLock、Semaphore，CountDownLatch等。
AQS是一个抽象类，主要是通过继承方式使用，本身没有实现任何接口，仅仅是定义了同步状态的获取和释放的方法。AQS解决了了之类实现同步器的大量细节问题，例如获取同步状态，FIFO队列，入队和出队。自定义同步器在实现时候只需要实现共享资源state的获取和释放即可，至于获取资源失败入队/唤醒出队等，AQS在顶层已经定义好了。

#### AQS的两种功能

从使用层面来说，AQS功能分为两种：独占和共享

- 独占锁，每次只能一个线程持有锁，比如ReentrantLock就是独占锁
- 共享锁，允许多个线程持有锁，并发访问共享资源，比如ReentrantReadWriteLock
- 共享锁和独占锁的释放有一定区别，前面部分是一致的，先判断头结点是不是signal状态，如果是则唤醒头节点的下一个节点，并将该节点设置为头结点。而共享锁不一样，某个节点被设置为head之后，如果它的后继节点是shared状态，那么会尝试使用doReleaseShared方法尝试唤醒节点，实现共享状态的传播。

#### AQS内部实现

AQS是依赖内部的同步队列实现，也就是FIFO双向队列，如果当前线程竞争锁失败，那么AQS会把当前线程以及等待状态封装成一个Node节点加入到同步队列中，同时阻塞该线程，当同步状态释放时，会把首节点唤醒，使其再次尝试获取同步状态。

AQS队列内部维护的是一个双向链表，这种结构每个数据都有两个指针，分别指向直接的的前驱节点和后继节点，当线程抢占锁失败时候，会封装成Node加入到AQS中去。

AQS 核心思想是，如果被请求的共享资源空闲，则将当前请求资源的线程设置为有效的工作线程，并且将共享资源设置为锁定状态。如果被请求的共享资源被占用，那么就需要一套线程阻塞等待以及被唤醒时锁分配的机制，这个机制 AQS 是用 **CLH 队列锁** 实现的，即将暂时获取不到锁的线程加入到队列中。

CLH(Craig,Landin,and Hagersten) 队列是一个虚拟的双向队列（虚拟的双向队列即不存在队列实例，仅存在结点之间的关联关系）。AQS 是将每条请求共享资源的线程封装成一个 CLH 锁队列的一个结点（Node）来实现锁的分配。在 CLH 同步队列中，一个节点表示一个线程，它保存着线程的引用（thread）、 当前节点在队列中的状态（waitStatus）、前驱节点（prev）、后继节点（next）。

CLH 队列结构如下图所示：

![img](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/40cb932a64694262993907ebda6a0bfe~tplv-k3u1fbpfcp-zoom-1.image)

在同步队列中，一个节点表示一个线程，他保存这线程的引用ThreadId，状态（watiStatus）,前驱结点（pre），后继节点（next），其数据结构如下：  

![img](https://pic2.zhimg.com/80/v2-6df6cc730852956e29dfafbbf0efa411_720w.webp)

#### acquire方法流程总结

- 首先通过子类判断是否获取了锁，如果获取了就什么也不干。tryAcquire
- 如果没有获取锁、通过线程创建节点加入同步队列的队尾。addWaiter
- 当线程在同步队列中不断的通过自旋去获取同步状态，如果获取了锁，就把其设为同步队列中的头节点，否则在同步队列中不停的自旋等待获取同步状态 acquireQueued,shouldParkAfterFailedAcquire(Node pre,Node  node),parkAndCheckInterrupt()
- 如果在获取同步状态的过程中被中断过最后自行调用interrupted方法进行中断操作



#### AQS 底层使用了模板方法模式，你能说出几个需要重写的方法吗？

使用者继承 AbstractQueuedSynchronizer 并重写指定的方法。将 AQS 组合在自定义同步组件的实现中，并调用其模板方法，而这些模板方法会调用使用者重写的方法。

1. isHeldExclusively() ：该线程是否正在独占资源。只有用到 condition 才需要去实现它。
2. tryAcquire(int) ：独占方式。尝试获取资源，成功则返回 true，失败则返回 false。
3. tryRelease(int) ：独占方式。尝试释放资源，成功则返回 true，失败则返回 false。
4. tryAcquireShared(int) ：共享方式。尝试获取资源。负数表示失败；0 表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。
5. tryReleaseShared(int) ：共享方式。尝试释放资源，成功则返回 true，失败则返回 false。



#### 总结

总结的来说：线程获取锁，如果获取了锁就  保存当前获得锁的线程，如果没获取就创造一个节点通过compareAndSetTail(CAS操作)操作的方式将创建的节点加入同步队列的尾部，在同步队列中的节点通过自旋的操作不断去获取同步状态【当然由于FIFO先进先出的特性】等待时间越长就越先被唤醒。当头节点释放同步状态的时候，首先查看是否存在后继节点，如果存在就唤醒自己的后继节点，如果不存在就获取等待时间最长的符合条件的线程。  





### volitile关键字原理

##### 简述Java内存模型：

Java内存模型分为**主内存和线程工作内存**两大类。

- **主内存：** **多个线程共享的内存。**如下图所示，方法区和堆属于主内存区域。
- **线程工作内存：** **每个线程独享的内存**。如下图所示，虚拟机栈、本地方法栈、程序计数器属于线程独享的工作内存。

![图片](https://mmbiz.qpic.cn/mmbiz_png/4mrYCvzMKmDXY4ebv4Kykbz9CBXt6QwIbBu2XrBxyPTmzxSvfTMic58ia4XstJFlFWX7QD34ZzcAKSU8iapShegeg/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

**Java内存模型规定：所有变量都需要存储在主内存中，线程工作内存保存了变量在主内存中的副本，线程对变量的所有操作都在工作内存中进行，执行结束后在同步到主内存中去**。这里必然会存在时间差，在这个时间差内，该线程对副本的操作，对于其他线程是不见的，从而造成了可见性问题。

##### volatile的作用：

- **保证共享变量的可见性：** 使用volatile修饰的变量，任何线程对其进行操作都是在主内存中进行的，不会产生副本，从而保证共享变量的可见性。
- **防止局部指令重排序：** happens-before规则中的**volatile变量规则**规定了一个线程先去写一个volatile变量，然后一个线程去读这个变量，那么这个写操作的结果一定对读的这个线程可见。

##### volatile如何防止指令重排序：

**volatile是通过内存屏障来防止指令重排序的**。

**硬件层面的内存屏障分为`Load Barrier` 和 `Store Barrier`即读屏障和写屏障**。

- 对于Load Barrier来说，在指令前插入Load Barrier，可以让高速缓存中的数据失效，强制从新从主内存加载数据。
- 对于Store Barrier来说，在指令后插入Store Barrier，能让写入缓存中的最新数据更新写入主内存，让其他线程可见。

**Java内存屏障类型把上述两种内存屏障两两组合**，如下图所示：

![图片](https://mmbiz.qpic.cn/mmbiz_png/4mrYCvzMKmDXY4ebv4Kykbz9CBXt6QwICGjJej1ZmPiaNAY0bW9wW5VfZJDMtmfgibZk2ZzGqyGdSiaeclWGHNcEg/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

**volatile防止指令重排序具体步骤：**

- 在每个volatile写操作的前面插入一个StoreStore屏障。
- 在每个volatile写操作的后面插入一个StoreLoad屏障。
- 在每个volatile读操作的后面插入一个LoadLoad屏障。
- 在每个volatile读操作的后面插入一个LoadStore屏障。

![图片](https://mmbiz.qpic.cn/mmbiz_png/4mrYCvzMKmDXY4ebv4Kykbz9CBXt6QwIXhWaarZicbsdFbG4CicPGecbCEaoN1SKJRksicp0C1EWxH0eibKGk7XqkA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

![图片](https://mmbiz.qpic.cn/mmbiz_png/4mrYCvzMKmDXY4ebv4Kykbz9CBXt6QwImpLIiaKXMictqbYGpBw4bH6swK9JEm8dkIRqY8OdBY9wrBfM6n5wxFIA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

##### volatile总结：

**`volatile`解决的是多线程共享变量可见性问题，但是被volatile修饰的变量操作并非具有原子性**。

参考：https://mp.weixin.qq.com/s?__biz=MzAxNTE2NjEyMw==&mid=2247483734&idx=1&sn=8509d4aa61d08550c2b7668314da3582&chksm=9b897e92acfef7846cd7dcac24d46c05ef0fabe5187befa0216b986f232d681c948b9deba26e&scene=27



### 锁的分类实现

#### 悲观锁：

正如其名，它是指对数据修改时持保守态度，认为其他人也会修改数据。因此在操作数据时，会把数据锁住，直到操作完成。悲观锁大多数情况下依靠数据库的锁机制实现，以保证操作最大程度的独占性。如果加锁的时间过长，其他用户长时间无法访问，影响程序的并发访问性，同时这样对数据库性能开销影响也很大，特别是长事务而言，这样的开销往往无法承受。


#### 乐观锁：

乐观锁，从字面意思也能猜到个大概，在操作数据时非常乐观，认为别人不会同时修改数据，因此乐观锁不会上锁 只是在 `提交更新` 时，才会正式对数据的冲突与否进行检测。如果发现冲突了，则返回错误信息，让用户决定如何去做，`fail-fast 机制` 。否则，执行本次操作。



#### 可重入锁：

可重入锁，也叫做递归锁，是指在同一个线程在调外层方法获取锁的时候，再进入内层方法会自动获取锁。

对象锁或类锁内部有计数器，一个线程每获得一次锁，计数器 +1；解锁时，计数器 -1。

JAVA 中的 `ReentrantLock` 和 `synchronized` 都是 可重入锁。可重入锁的一个好处是可一定程度避免死锁。



#### 自旋锁：

自旋锁是采用让当前线程不停地在循环体内执行，当循环的条件被其他线程改变时才能进入临界区。自旋锁只是将当前线程不停地执行循环体，不进行线程状态的改变，所以响应速度更快。但当线程数不断增加时，性能下降明显，因为每个线程都需要执行，会占用CPU时间片。如果线程竞争不激烈，并且保持锁的时间段。适合使用自旋锁。

自旋锁缺点：

    可能引发死锁
    
    可能占用 CPU 的时间过长

我们可以设置一个 循环时间 或 循环次数，超出阈值时，让线程进入阻塞状态，防止线程长时间占用 CPU 资源。JUC 并发包中的 CAS 就是采用自旋锁，compareAndSet 是CAS操作的核心，底层利用Unsafe对象实现的。


#### 独享锁：

独享锁，也有人叫它排他锁。无论读操作还是写操作，只能有一个线程获得锁，其他线程处于阻塞状态。

缺点：读操作并不会修改数据，而且大部分的系统都是 读多写少，如果读读之间互斥，大大降低系统的性能。下面的 共享锁 会解决这个问题。

像JAVA 中的 ReentrantLock 和 synchronized 都是独享锁。

#### 共享锁：

共享锁是指允许多个线程同时持有锁，一般用在读锁上。读锁的共享锁可保证并发读是非常高效的。读写，写读 ，写写的则是互斥的。独享锁与共享锁也是通过AQS来实现的，通过实现不同的方法，来实现独享或者共享

ReentrantReadWriteLock，其读锁是共享锁，其写锁是独享锁。

#### 读锁/写锁：

如果对某个资源是读操作，那多个线程之间并不会相互影响，可以通过添加读锁实现共享。如果有修改动作，为了保证数据的并发安全，此时只能有一个线程获得锁，我们称之为 写锁。读读是共享的；而 读写、写读 、写写 则是互斥的

    像 JAVA 中的 ReentrantReadWriteLock 就是一种 读写锁


#### 公平锁/非公平锁：

**公平锁**：多个线程按照申请锁的顺序去获得锁，所有线程都在队列里排队，先来先获取的公平性原则。

**优点**：所有的线程都能得到资源，不会饿死在队列中。

**缺点**：吞吐量会下降很多，队列里面除了第一个线程，其他的线程都会阻塞，CPU 唤醒下一个阻塞线程有系统开销

**非公平锁：**多个线程不按照申请锁的顺序去获得锁，而是同时以插队方式直接尝试获取锁，获取不到（插队失败），会进入队列等待（失败则乖乖排队），如果能获取到（插队成功），就直接获取到锁。

**优点：**可以减少 CPU 唤醒线程的开销，整体的吞吐效率会高点

**缺点：**可能导致队列中排队的线程一直获取不到锁或者长时间获取不到锁，活活饿死。

Java 多线程并发操作，我们操作锁大多时候都是基于 Sync 本身去实现的，而 Sync 本身却是 ReentrantLock 的一个内部类，Sync 继承 AbstractQueuedSynchronizer

像 ReentrantLock 默认是非公平锁，我们可以在构造函数中传入 true，来创建公平锁。


参考：https://blog.csdn.net/weixin_70730532/article/details/126875303



### synchronized原理

#### synchronized 同步语句块的情况:

```
public class SynchronizedDemo {
    public void method() {
        synchronized (this) {
            System.out.println("synchronized 代码块");
        }
    }
}

```

通过 JDK 自带的 `javap` 命令查看 `SynchronizedDemo` 类的相关字节码信息：首先切换到类的对应目录执行 `javac SynchronizedDemo.java` 命令生成编译后的 .class 文件，然后执行`javap -c -s -v -l SynchronizedDemo.class`。

![synchronized关键字原理](https://oss.javaguide.cn/github/javaguide/java/concurrent/synchronized-principle.png)



从上面我们可以看出：**`synchronized` 同步语句块的实现使用的是 `monitorenter` 和 `monitorexit` 指令，其中 `monitorenter` 指令指向同步代码块的开始位置，`monitorexit` 指令则指明同步代码块的结束位置。**

上面的字节码中包含一个 `monitorenter` 指令以及两个 `monitorexit` 指令，这是为了保证锁在同步代码块代码正常执行以及出现异常的这两种情况下都能被正确释放。

当执行 `monitorenter` 指令时，线程试图获取锁也就是获取 **对象监视器 `monitor`** 的持有权。

在 Java 虚拟机(HotSpot)中，Monitor 是基于 C++实现的，由ObjectMonitor实现的。每个对象中都内置了一个 `ObjectMonitor`对象。

> 另外，`wait/notify`等方法也依赖于`monitor`对象，这就是为什么只有在同步的块或者方法中才能调用`wait/notify`等方法，否则会抛出`java.lang.IllegalMonitorStateException`的异常的原因。

在执行`monitorenter`时，会尝试获取对象的锁，如果锁的计数器为 0 则表示锁可以被获取，获取后将锁计数器设为 1 也就是加 1。

对象锁的的拥有者线程才可以执行 `monitorexit` 指令来释放锁。在执行 `monitorexit` 指令后，将锁计数器设为 0，表明锁被释放，其他线程可以尝试获取锁。如果获取对象锁失败，那当前线程就要阻塞等待，直到锁被另外一个线程释放为止。



#### synchronized 修饰方法的的情况:

```
public class SynchronizedDemo2 {
    public synchronized void method() {
        System.out.println("synchronized 方法");
    }
}


```

![synchronized关键字原理](https://oss.javaguide.cn/github/javaguide/synchronized关键字原理2.png)

`synchronized` 修饰的方法并没有 `monitorenter` 指令和 `monitorexit` 指令，取得代之的确实是 `ACC_SYNCHRONIZED` 标识，该标识指明了该方法是一个同步方法。JVM 通过该 `ACC_SYNCHRONIZED` 访问标志来辨别一个方法是否声明为同步方法，从而执行相应的同步调用。

如果是实例方法，JVM 会尝试获取实例对象的锁。如果是静态方法，JVM 会尝试获取当前 class 的锁。

#### 总结:

`synchronized` 同步语句块的实现使用的是 `monitorenter` 和 `monitorexit` 指令，其中 `monitorenter` 指令指向同步代码块的开始位置，`monitorexit` 指令则指明同步代码块的结束位置。

`synchronized` 修饰的方法并没有 `monitorenter` 指令和 `monitorexit` 指令，取得代之的确实是 `ACC_SYNCHRONIZED` 标识，该标识指明了该方法是一个同步方法。

**不过两者的本质都是对对象监视器 monitor 的获取。**

参考：https://javaguide.cn/java/concurrent/java-concurrent-questions-02.html#synchronized-%E5%BA%95%E5%B1%82%E5%8E%9F%E7%90%86%E4%BA%86%E8%A7%A3%E5%90%97



### ThreadLocal原理

#### ThreadLocal 有什么用？

通常情况下，我们创建的变量是可以被任何一个线程访问并修改的。**如果想实现每一个线程都有自己的专属本地变量该如何解决呢？**

JDK 中自带的`ThreadLocal`类正是为了解决这样的问题。 **`ThreadLocal`类主要解决的就是让每个线程绑定自己的值，可以将`ThreadLocal`类形象的比喻成存放数据的盒子，盒子中可以存储每个线程的私有数据。**

如果你创建了一个`ThreadLocal`变量，那么访问这个变量的每个线程都会有这个变量的本地副本，这也是`ThreadLocal`变量名的由来。他们可以使用 `get()` 和 `set()` 方法来获取默认值或将其值更改为当前线程所存的副本的值，从而避免了线程安全问题。

#### 原理：

**源码：**

```Java
public class Thread implements Runnable {
    //......
    //与此线程有关的ThreadLocal值。由ThreadLocal类维护
    ThreadLocal.ThreadLocalMap threadLocals = null;

    //与此线程有关的InheritableThreadLocal值。由InheritableThreadLocal类维护
    ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;
    //......
}

```

从上面`Thread`类 源代码可以看出`Thread` 类中有一个 `threadLocals` 和 一个 `inheritableThreadLocals` 变量，它们都是 `ThreadLocalMap` 类型的变量,我们可以把 `ThreadLocalMap` 理解为`ThreadLocal` 类实现的定制化的 `HashMap`。默认情况下这两个变量都是 null，只有当前线程调用 `ThreadLocal` 类的 `set`或`get`方法时才创建它们，实际上调用这两个方法的时候，我们调用的是`ThreadLocalMap`类对应的 `get()`、`set()`方法。

`ThreadLocal`类的`set()`方法：

```Java
public void set(T value) {
    //获取当前请求的线程
    Thread t = Thread.currentThread();
    //取出 Thread 类内部的 threadLocals 变量(哈希表结构)
    ThreadLocalMap map = getMap(t);
    if (map != null)
        // 将需要存储的值放入到这个哈希表中
        map.set(this, value);
    else
        createMap(t, value);
}
ThreadLocalMap getMap(Thread t) {
    return t.threadLocals;
}

```

通过上面这些内容，我们足以通过猜测得出结论：**最终的变量是放在了当前线程的 `ThreadLocalMap` 中，并不是存在 `ThreadLocal` 上，`ThreadLocal` 可以理解为只是`ThreadLocalMap`的封装，传递了变量值。** `ThrealLocal` 类中可以通过`Thread.currentThread()`获取到当前线程对象后，直接通过`getMap(Thread t)`可以访问到该线程的`ThreadLocalMap`对象。

**每个`Thread`中都具备一个`ThreadLocalMap`，而`ThreadLocalMap`可以存储以`ThreadLocal`为 key ，Object 对象为 value 的键值对。**

```
ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {
    //......
}

```

比如我们在同一个线程中声明了两个 `ThreadLocal` 对象的话， `Thread`内部都是使用仅有的那个`ThreadLocalMap` 存放数据的，`ThreadLocalMap`的 key 就是 `ThreadLocal`对象，value 就是 `ThreadLocal` 对象调用`set`方法设置的值。



### 线程池

#### 为什么要用线程池？

这里借用《Java 并发编程的艺术》提到的来说一下**使用线程池的好处**：

- **降低资源消耗**。通过重复利用已创建的线程降低线程创建和销毁造成的消耗。
- **提高响应速度**。当任务到达时，任务可以不需要等到线程创建就能立即执行。
- **提高线程的可管理性**。线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。

#### 如何创建线程池？

**方式一：通过`ThreadPoolExecutor`构造函数来创建（推荐）。**

```
package com.young.多线程;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 杨路恒
 */
public class ThreadPool {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,2,
                2, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(2),
                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.AbortPolicy()
//                new ThreadPoolExecutor.CallerRunsPolicy()
//                new ThreadPoolExecutor.DiscardOldestPolicy()
                new ThreadPoolExecutor.DiscardPolicy()
                );
        threadPoolExecutor.submit(new Thread2());
        threadPoolExecutor.submit(new Thread2());
        threadPoolExecutor.submit(new Thread2());
        threadPoolExecutor.submit(new Thread2());
        threadPoolExecutor.submit(new Thread2());
        threadPoolExecutor.submit(new Thread2());
        threadPoolExecutor.submit(new Thread2());
        threadPoolExecutor.submit(new Thread2());
        threadPoolExecutor.shutdown();
    }
}

```

**参数：**

```
/**
 * Creates a new {@code ThreadPoolExecutor} with the given initial
 * parameters.
 *
 * @param corePoolSize the number of threads to keep in the pool, even
 *        if they are idle, unless {@code allowCoreThreadTimeOut} is set
 * @param maximumPoolSize the maximum number of threads to allow in the
 *        pool
 * @param keepAliveTime when the number of threads is greater than
 *        the core, this is the maximum time that excess idle threads
 *        will wait for new tasks before terminating.
 * @param unit the time unit for the {@code keepAliveTime} argument
 * @param workQueue the queue to use for holding tasks before they are
 *        executed.  This queue will hold only the {@code Runnable}
 *        tasks submitted by the {@code execute} method.
 * @param threadFactory the factory to use when the executor
 *        creates a new thread
 * @param handler the handler to use when execution is blocked
 *        because the thread bounds and queue capacities are reached
 * @throws IllegalArgumentException if one of the following holds:<br>
 *         {@code corePoolSize < 0}<br>
 *         {@code keepAliveTime < 0}<br>
 *         {@code maximumPoolSize <= 0}<br>
 *         {@code maximumPoolSize < corePoolSize}
 * @throws NullPointerException if {@code workQueue}
 *         or {@code threadFactory} or {@code handler} is null
 */
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler) {
    if (corePoolSize < 0 ||
        maximumPoolSize <= 0 ||
        maximumPoolSize < corePoolSize ||
        keepAliveTime < 0)
        throw new IllegalArgumentException();
    if (workQueue == null || threadFactory == null || handler == null)
        throw new NullPointerException();
    this.acc = System.getSecurityManager() == null ?
            null :
            AccessController.getContext();
    this.corePoolSize = corePoolSize;
    this.maximumPoolSize = maximumPoolSize;
    this.workQueue = workQueue;
    this.keepAliveTime = unit.toNanos(keepAliveTime);
    this.threadFactory = threadFactory;
    this.handler = handler;
}
```

**`ThreadPoolExecutor` 3 个最重要的参数：**

- **`corePoolSize` :** 任务队列未达到队列容量时，最大可以同时运行的线程数量。
- **`maximumPoolSize` :** 任务队列中存放的任务达到队列容量的时候，当前可以同时运行的线程数量变为最大线程数。
- **`workQueue`:** 新任务来的时候会先判断当前运行的线程数量是否达到核心线程数，如果达到的话，新任务就会被存放在队列中。

`ThreadPoolExecutor`其他常见参数 :

- **`keepAliveTime`**:线程池中的线程数量大于 `corePoolSize` 的时候，如果这时没有新的任务提交，核心线程外的线程不会立即销毁，而是会等待，直到等待的时间超过了 `keepAliveTime`才会被回收销毁；
- **`unit`** : `keepAliveTime` 参数的时间单位。
- **`threadFactory`** :executor 创建新线程的时候会用到。
- **`handler`** :饱和策略。关于饱和策略下面单独介绍一下。

下面这张图可以加深你对线程池中各个参数的相互关系的理解（图片来源：《Java 性能调优实战》）：

![线程池各个参数的关系](https://javaguide.cn/assets/线程池各个参数之间的关系.d65f3309.png)



**线程池的饱和策略有哪些？**

如果当前同时运行的线程数量达到最大线程数量并且队列也已经被放满了任务时，`ThreadPoolTaskExecutor` 定义一些策略:

- **`ThreadPoolExecutor.AbortPolicy`：** 抛出 `RejectedExecutionException`来拒绝新任务的处理。
- **`ThreadPoolExecutor.CallerRunsPolicy`：** 调用执行自己的线程运行任务，也就是直接在调用`execute`方法的线程中运行(`run`)被拒绝的任务，如果执行程序已关闭，则会丢弃该任务。因此这种策略会降低对于新任务提交速度，影响程序的整体性能。如果您的应用程序可以承受此延迟并且你要求任何一个任务请求都要被执行的话，你可以选择这个策略。
- **`ThreadPoolExecutor.DiscardPolicy`：** 不处理新任务，直接丢弃掉。
- **`ThreadPoolExecutor.DiscardOldestPolicy`：** 此策略将丢弃最早的未处理的任务请求。

举个例子：Spring 通过 `ThreadPoolTaskExecutor` 或者我们直接通过 `ThreadPoolExecutor` 的构造函数创建线程池的时候，当我们不指定 `RejectedExecutionHandler` 饱和策略来配置线程池的时候，默认使用的是 `AbortPolicy`。在这种饱和策略下，如果队列满了，`ThreadPoolExecutor` 将抛出 `RejectedExecutionException` 异常来拒绝新来的任务 ，这代表你将丢失对这个任务的处理。如果不想丢弃任务的话，可以使用`CallerRunsPolicy`。`CallerRunsPolicy` 和其他的几个策略不同，它既不会抛弃任务，也不会抛出异常，而是将任务回退给调用者，使用调用者的线程来执行任务

**线程池常用的阻塞队列有哪些？**

新任务来的时候会先判断当前运行的线程数量是否达到核心线程数，如果达到的话，新任务就会被存放在队列中。

不同的线程池会选用不同的阻塞队列，我们可以结合内置线程池来分析。

- 容量为 `Integer.MAX_VALUE` 的 `LinkedBlockingQueue`（无界队列）：`FixedThreadPool` 和 `SingleThreadExector` 。由于队列永远不会被放满，因此`FixedThreadPool`最多只能创建核心线程数的线程。
- `SynchronousQueue`（同步队列） ：`CachedThreadPool` 。`SynchronousQueue` 没有容量，不存储元素，目的是保证对于提交的任务，如果有空闲线程，则使用空闲线程来处理；否则新建一个线程来处理任务。也就是说，`CachedThreadPool` 的最大线程数是 `Integer.MAX_VALUE` ，可以理解为线程数是可以无限扩展的，可能会创建大量线程，从而导致 OOM。
- `DelayedWorkQueue`（延迟阻塞队列）：`ScheduledThreadPool` 和 `SingleThreadScheduledExecutor` 。`DelayedWorkQueue` 的内部元素并不是按照放入的时间排序，而是会按照延迟的时间长短对任务进行排序，内部采用的是“堆”的数据结构，可以保证每次出队的任务都是当前队列中执行时间最靠前的。`DelayedWorkQueue` 添加元素满了之后会自动扩容原来容量的 1/2，即永远不会阻塞，最大扩容可达 `Integer.MAX_VALUE`，所以最多只能创建核心线程数的线程。

**线程池处理任务的流程了解吗？**

- 如果当前运行的线程数小于核心线程数，那么就会新建一个线程来执行任务。
- 如果当前运行的线程数等于或大于核心线程数，但是小于最大线程数，那么就把该任务放入到任务队列里等待执行。
- 如果向任务队列投放任务失败（任务队列已经满了），但是当前运行的线程数是小于最大线程数的，就新建一个线程来执行任务。
- 如果当前运行的线程数已经等同于最大线程数了，新建线程将会使当前运行的线程超出最大线程数，那么当前任务会被拒绝，饱和策略会调用`RejectedExecutionHandler.rejectedExecution()`方法。

![图解线程池实现原理](https://oss.javaguide.cn/javaguide/图解线程池实现原理.png)

**如何设定线程池的大小？**

有一个简单并且适用面比较广的公式：

- **CPU 密集型任务(N+1)：** 这种任务消耗的主要是 CPU 资源，可以将线程数设置为 N（CPU 核心数）+1。比 CPU 核心数多出来的一个线程是为了防止线程偶发的缺页中断，或者其它原因导致的任务暂停而带来的影响。一旦任务暂停，CPU 就会处于空闲状态，而在这种情况下多出来的一个线程就可以充分利用 CPU 的空闲时间。
- **I/O 密集型任务(2N)：** 这种任务应用起来，系统会用大部分的时间来处理 I/O 交互，而线程在处理 I/O 的时间段内不会占用 CPU 来处理，这时就可以将 CPU 交出给其它线程使用。因此在 I/O 密集型任务的应用中，我们可以多配置一些线程，具体的计算方法是 2N。



**方式二：通过 `Executor` 框架的工具类 `Executors` 来创建。**

我们可以创建多种类型的 `ThreadPoolExecutor`：

- **`FixedThreadPool`** ： 该方法返回一个固定线程数量的线程池。该线程池中的线程数量始终不变。当有一个新的任务提交时，线程池中若有空闲线程，则立即执行。若没有，则新的任务会被暂存在一个任务队列中，待有线程空闲时，便处理在任务队列中的任务。
- **`SingleThreadExecutor`：** 该方法返回一个只有一个线程的线程池。若多余一个任务被提交到该线程池，任务会被保存在一个任务队列中，待线程空闲，按先入先出的顺序执行队列中的任务。
- **`CachedThreadPool`：** 该方法返回一个可根据实际情况调整线程数量的线程池。线程池的线程数量不确定，但若有空闲线程可以复用，则会优先使用可复用的线程。若所有线程均在工作，又有新的任务提交，则会创建新的线程处理任务。所有线程在当前任务执行完毕后，将返回线程池进行复用。
- **`ScheduledThreadPool`** ：该返回一个用来在给定的延迟后运行任务或者定期执行任务的线程池。

#### 为什么不推荐使用内置线程池？

在《阿里巴巴 Java 开发手册》“并发处理”这一章节，明确指出线程资源必须通过线程池提供，不允许在应用中自行显式创建线程。

**为什么呢？**

> 使用线程池的好处是减少在创建和销毁线程上所消耗的时间以及系统资源开销，解决资源不足的问题。如果不使用线程池，有可能会造成系统创建大量同类线程而导致消耗完内存或者“过度切换”的问题。

另外，《阿里巴巴 Java 开发手册》中强制线程池不允许使用 `Executors` 去创建，而是通过 `ThreadPoolExecutor` 构造函数的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险

`Executors` 返回线程池对象的弊端如下(后文会详细介绍到)：

- **`FixedThreadPool` 和 `SingleThreadExecutor`** ： 使用的是无界的 `LinkedBlockingQueue`，任务队列最大长度为 `Integer.MAX_VALUE`,可能堆积大量的请求，从而导致 OOM。
- **`CachedThreadPool`** ：使用的是同步队列 `SynchronousQueue`, 允许创建的线程数量为 `Integer.MAX_VALUE` ，可能会创建大量线程，从而导致 OOM。
- **`ScheduledThreadPool` 和 `SingleThreadScheduledExecutor`** : 使用的无界的延迟阻塞队列`DelayedWorkQueue`，任务队列最大长度为 `Integer.MAX_VALUE`,可能堆积大量的请求，从而导致 OOM。

#### 如何动态修改线程池的参数？

JDK原生线程池ThreadPoolExecutor提供了如下几个public的setter方法，如下图所示：

![图片](https://mmbiz.qpic.cn/mmbiz_png/hEx03cFgUsXAj6OrUTUDRoG5tCBgm4CJ9359Lqbqaqicvj0TXRpYT5TD26dSfbcxRibrYibvBSAVTvxThW5n4FiczQ/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

重点是基于这几个public方法，我们只需要维护ThreadPoolExecutor的实例，并且在需要修改的时候拿到实例修改其参数即可。

**问题一：线程池被创建后里面有线程吗？如果没有的话，你知道有什么方法对线程池进行预热吗？**

线程池被创建后如果没有任务过来，里面是不会有线程的。如果需要预热的话可以调用下面的两个方法：

全部启动：

![图片](https://mmbiz.qpic.cn/mmbiz_png/lnCqjsQ6QHfBqOxop3hDOhKCOm6v4MXYvdzS6VCjKicoib5eGJgeW9wPEnt9Zk1YEspKYaVPI5ZEqlibCWQ7Rofdw/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

仅启动一个：

![图片](https://mmbiz.qpic.cn/mmbiz_png/lnCqjsQ6QHfBqOxop3hDOhKCOm6v4MXYQUPFNiaw7bjF5RsHSkAib2HE9vlNq2R6dN4U5OicOcLJ29ZvotUm3jdGA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

**问题二：核心线程数会被回收吗？需要什么设置？**

核心线程数默认是不会被回收的，如果需要回收核心线程数，需要调用下面的方法：

![图片](https://mmbiz.qpic.cn/mmbiz_png/lnCqjsQ6QHfBqOxop3hDOhKCOm6v4MXYVTBfSHpeUKaRL64ZwrN6sAMMsPD3XvILspTG405TMIgPtTiaV84fexQ/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)



参考：https://javaguide.cn/java/concurrent/java-concurrent-questions-03.html；https://mp.weixin.qq.com/s?__biz=MjM5NjQ5MTI5OA==&mid=2651751537&idx=1&sn=c50a434302cc06797828782970da190e&chksm=bd125d3c8a65d42aaf58999c89b6a4749f092441335f3c96067d2d361b9af69ad4ff1b73504c&scene=21#wechat_redirect



## 四：Java高级：Spring、SpringMVC、Spring Boot、MyBatis

### AOP和IOC概念

AOP：AOP(Aspect-Oriented Programming:面向切面编程)能够将那些与业务无关，却为业务模块所共同调用的逻辑或责任（例如事务处理、日志管理、权限控制等）封装起来，便于减少系统的重复代码，降低模块间的耦合度，并有利于未来的可拓展性和可维护性。

Spring AOP 就是基于动态代理的，如果要代理的对象，实现了某个接口，那么 Spring AOP 会使用 **JDK Proxy**，去创建代理对象，而对于没有实现接口的对象，就无法使用 JDK Proxy 去进行代理了，这时候 Spring AOP 会使用 **Cglib** 生成一个被代理对象的子类来作为代理。

IOC： 是一种设计思想，而不是一个具体的技术实现。IoC 的思想就是将原本在程序中手动创建对象的控制权，交由 Spring 框架来管理。不过， IoC 并非 Spring 特有，在其他语言中也有应用。

**为什么叫控制反转？**

- **控制** ：指的是对象创建（实例化、管理）的权力
- **反转** ：控制权交给外部环境（Spring 框架、IoC 容器）



### Bean的生命周期





### MVC的基本流程

简单来说：客户端发送请求-> 前端控制器 DispatcherServlet 接受客户端请求 -> 找到处理器映射  HandlerMapping 解析请求对应的 Handler -> HandlerAdapter 会根据 Handler  来调用真正的处理器来处理请求，并处理相应的业务逻辑 -> 处理器返回一个模型视图 ModelAndView -> 视图解析器进行解析 -> 返回一个视图对象 -> 前端控制器 DispatcherServlet 渲染数据（Model）->  将得到视图对象返回给用户。

![image-20210608002334025](https://image.iamshuaidi.com/picture/image-20210608002334025.png)



上图用于辅助理解，面试时可用下列 8 步描述 SpringMVC 运行流程：

1. 用户向服务器发送请求，请求被 Spring 前端控制Servelt DispatcherServlet 捕获；
2. DispatcherServlet 对请求 URL 进行解析，得到请求资源标识符（URI）。然后根据该 URI，调用  HandlerMapping 获得该 Handler 配置的所有相关的对象（包括 Handler 对象以及 Handler  对象对应的拦截器），最后以 HandlerExecutionChain 对象的形式返回；
3. DispatcherServlet 根据获得的 Handler，选择一个合适的HandlerAdapter；（附注：如果成功获得 HandlerAdapter 后，此时将开始执行拦截器的 preHandler(…)方法）
4. 提取 Request 中的模型数据，填充 Handler 入参，开始执行Handler（Controller)。在填充 Handler 的入参过程中，根据你的配置，Spring 将帮你做一些额外的工作：

（1）HttpMessageConveter：将请求消息（如：Json、xml 等数据）转换成一个对象，将对象转换为指定的响应信息；

（2）数据转换：对请求消息进行数据转换。如：String 转换成 Integer、Double 等；

（3）数据格式化：对请求消息进行数据格式化。如：将字符串转换成格式化数字或格式化日期等；

（4）数据验证：验证数据的有效性（长度、格式等），验证结果存储到 BindingResult 或 Error 中;

5.Handler 执行完成后，向 DispatcherServlet 返回一个 ModelAndView 对象；

6.根据返回的 ModelAndView，选择一个适合的 ViewResolver（必须是已经注册到 Spring 容器中的 ViewResolver)返回给DispatcherServlet；

7.ViewResolver 结合 Model 和 View，来渲染视图；

8.将渲染结果返回给客户端。



### Spring的设计模式

1. 工厂设计模式 : Spring 使用工厂模式通过 BeanFactory、ApplicationContext 创建 bean 对象；
2. 代理设计模式 : Spring AOP 功能的实现；
3. 单例设计模式 : Spring 中的 Bean 默认都是单例的；
4. 模板方法模式 : Spring 中 jdbcTemplate、hibernateTemplate 等以 Template 结尾的对数据库操作的类，它们就使用到了模板模式；
5. 包装器设计模式 : 我们的项目需要连接多个数据库，而且不同的客户在每次访问中根据需要会去访问不同的数据库。这种模式让我们可以根据客户的需求能够动态切换不同的数据源；
6. 观察者模式：Spring 事件驱动模型就是观察者模式很经典的一个应用；
7. 适配器模式：Spring AOP 的增强或通知(Advice)使用到了适配器模式、SpringMVC 中也是用到了适配器模式适配 Controller。



### Spring事务机制

#### 谈谈你对 Spring 中的事务的理解？

事务是逻辑上的一组操作，要么都执行，要么都不执行。

**事务特性**

原子性：事务是最小的执行单位，不允许分割。事务的原子性确保动作要么全部完成，要么完全不起作用；

一致性：执行事务前后，数据保持一致；

隔离性：并发访问数据库时，一个用户的事物不被其他事物所干扰，各并发事务之间数据库是独立的；

持久性: 一个事务被提交之后。它对数据库中数据的改变是持久的，即使数据库发生故障也不应该对其有任何影响。

**Spring 事务管理接口**

1. PlatformTransactionManager：（平台）事务管理器；
2. TransactionDefinition：事务定义信息（事务隔离级别、传播行为、超时、只读、回滚规则）；
3. TransactionStatus：事务运行状态；

所谓事务管理，其实就是“按照给定的事务规则来执行提交或者回滚操作”。



#### Spring 中的事务隔离级别？

TransactionDefinition 接口中定义了五个表示隔离级别的常量：

TransactionDefinition.ISOLATION_DEFAULT：使用后端数据库默认的隔离级别，MySQL 默认采用的 REPEATABLE_READ 隔离级别 Oracle 默认采用的 READ_COMMITTED 隔离级别；

TransactionDefinition.ISOLATION_READ_UNCOMMITTED：最低的隔离级别，允许读取尚未提交的数据变更，可能会导致脏读、幻读或不可重复读；

TransactionDefinition.ISOLATION_READ_COMMITTED：允许读取并发事务已经提交的数据，可以阻止脏读，但是幻读或不可重复读仍有可能发生；

TransactionDefinition.ISOLATION_REPEATABLE_READ：对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，可以阻止脏读和不可重复读，但幻读仍有可能发生；

TransactionDefinition.ISOLATION_SERIALIZABLE：最高的隔离级别，完全服从 ACID  的隔离级别。所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别。



#### Spring 中的事物传播行为？

事务传播行为是为了解决业务层方法之间互相调用的事务问题。当事务方法被另一个事务方法调用时，必须指定事务应该如何传播。例如：方法可能继续在现有事务中运行，也可能开启一个新事务，并在自己的事务中运行。在 TransactionDefinition 定义中包括了如下几个表示传播行为的常量：

- **支持当前事务的情况：**

TransactionDefinition.PROPAGATION_REQUIRED：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务；

TransactionDefinition.PROPAGATION_SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行；

TransactionDefinition.PROPAGATION_MANDATORY：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。

- **不支持当前事务的情况：**

TransactionDefinition.PROPAGATION_REQUIRES_NEW：创建一个新的事务，如果当前存在事务，则把当前事务挂起；

TransactionDefinition.PROPAGATION_NOT_SUPPORTED：以非事务方式运行，如果当前存在事务，则把当前事务挂起。

TransactionDefinition.PROPAGATION_NEVER：以非事务方式运行，如果当前存在事务，则抛出异常。

- **其他情况：**

TransactionDefinition.PROPAGATION_NESTED：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于 TransactionDefinition.PROPAGATION_REQUIRED。



### Spring如何解决循环依赖问题？

了解问题的本质再分析问题，往往更利于对问题有更深入的了解和研究。**所以我们在分析 Spring 关于循环依赖的源码之前，先要了解下什么是循环依赖。**

**1. 循环依赖的概念**

![img](https://image.iamshuaidi.com/picture/v2-dcc3be57d2f88cc011165231c40122d8_720w.jpg)



- 循环依赖分为三种，自身依赖于自身、互相循环依赖、多组循环依赖。
- 但无论循环依赖的数量有多少，循环依赖的本质是一样的。就是你的完整创建依赖于我，而我的完整创建也依赖于你，但我们互相没法解耦，最终导致依赖创建失败。
- 所以 Spring 提供了除了构造函数注入和原型注入外的，setter循环依赖注入解决方案。那么我们也可以先来尝试下这样的依赖，如果是我们自己处理的话该怎么解决。

**2. 问题体现**

```
public class ABTest {
    public static void main(String[] args) {
        new ClazzA();
    }
}

class ClazzA {
    private ClazzB b = new ClazzB();
}

class ClazzB {
    private ClazzA a = new ClazzA();
}


```

**3.解决方案**

整个解决循环依赖的核心内容，A 创建后填充属性时依赖 B，那么就去创建 B，在创建 B 开始填充时发现依赖于 A，但此时 A 这个半成品对象已经存放在缓存到`singletonObjects` 中了，所以 B 可以正常创建，在通过递归把 A 也创建完整了。

**4.Spring解决**

（1）一级缓存能解决吗？

![img](https://image.iamshuaidi.com/picture/v2-ccb36cabfd45ce428bd64416d0b82752_720w.jpg)



- 其实只有一级缓存并不是不能解决循环依赖，就像我们自己做的例子一样。
- 但是在 Spring 中如果像我们例子里那么处理，就会变得非常麻烦，而且也可能会出现 NPE 问题。
- 所以如图按照 Spring 中代码处理的流程，我们去分析一级缓存这样存放成品 Bean 的流程中，是不能解决循环依赖的问题的。因为 A 的成品创建依赖于 B，B的成品创建又依赖于 A，当需要补全B的属性时 A 还是没有创建完，所以会出现死循环。



（2） 二级缓存能解决吗？

![img](https://image.iamshuaidi.com/picture/v2-a05bd6e83c73b2b082a5c8916064a00c_720w.jpg)



- 有了二级缓存其实这个事处理起来就容易了，一个缓存用于存放成品对象，另外一个缓存用于存放半成品对象。
- A 在创建半成品对象后存放到缓存中，接下来补充 A 对象中依赖 B 的属性。
- B 继续创建，创建的半成品同样放到缓存中，在补充对象的 A 属性时，可以从半成品缓存中获取，现在 B 就是一个完整对象了，而接下来像是递归操作一样 A 也是一个完整对象了。



（3） 三级缓存解决什么？

![img](https://image.iamshuaidi.com/picture/v2-f71dd02ea011bdf372636af76b7c5b8a_720w.jpg)



有了二级缓存都能解决 Spring 依赖了，怎么要有三级缓存呢。其实我们在前面分析源码时也提到过，三级缓存主要是解决 Spring AOP 的特性。AOP 本身就是对方法的增强，是 `ObjectFactory<?>` 类型的 lambda 表达式，而 Spring 的原则又不希望将此类类型的 Bean 前置创建，所以要存放到三级缓存中处理。

其实整体处理过程类似，唯独是 B 在填充属性 A 时，先查询成品缓存、再查半成品缓存，最后在看看有没有单例工程类在三级缓存中。最终获取到以后调用 getObject 方法返回代理引用或者原始引用。

至此也就解决了 Spring AOP 所带来的三级缓存问题。*本章节涉及到的 AOP 依赖有源码例子，可以进行调试*

（4）总结

- 回顾本文基本以实际操作的例子开始，引导大家对循环依赖有一个整体的认识，也对它的解决方案可以上手的例子，这样对后续的关于 Spring 对循环依赖的解决也就不会那么陌生了。
- 通篇全文下来大家也可以看到，三级缓存并不是非必须不可，只不过在满足 Spring 自身创建的原则下，是必须的。如果你可以下载 Spring 源码对这部分代码进行改动下，提前创建 AOP 对象保存到缓存中，那么二级缓存一样可以解决循环依赖问题。



### Spring Boot自动配置原理

在Spring程序main方法中，添加@SpringBootApplication或者@EnableAutoConfiguration会自动去maven中读取每个starter中的spring.factories文件，该文件里配置了所有需要被创建的Spring容器中的bean。Spring Boot 通过`@EnableAutoConfiguration`开启自动装配，通过 SpringFactoriesLoader 最终加载`META-INF/spring.factories`中的自动配置类实现自动装配，自动配置类其实就是通过`@Conditional`按需加载的配置类，想要其生效必须引入`spring-boot-starter-xxx`包实现起步依赖。

```
第 1 步:
判断自动装配开关是否打开。默认spring.boot.enableautoconfiguration=true，可在 application.properties 或 application.yml 中设置
```

![img](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/77aa6a3727ea4392870f5cccd09844ab~tplv-k3u1fbpfcp-watermark.image)



```
第 2 步 ：
用于获取EnableAutoConfiguration注解中的 exclude 和 excludeName。
```

![img](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3d6ec93bbda1453aa08c52b49516c05a~tplv-k3u1fbpfcp-zoom-1.image)



```
第 3 步：
获取需要自动装配的所有配置类，读取META-INF/spring.factories：

spring-boot/spring-boot-project/spring-boot-autoconfigure/src/main/resources/META-INF/spring.factories
```

![img](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/58c51920efea4757aa1ec29c6d5f9e36~tplv-k3u1fbpfcp-watermark.image)



```
从下图可以看到这个文件的配置内容都被我们读取到了。XXXAutoConfiguration的作用就是按需加载组件。
不光是这个依赖下的META-INF/spring.factories被读取到，所有 Spring Boot Starter 下的META-INF/spring.factories都会被读取到。
```

![img](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/94d6e1a060ac41db97043e1758789026~tplv-k3u1fbpfcp-watermark.image)

参考：https://javaguide.cn/system-design/framework/spring/spring-boot-auto-assembly-principles.html#autoconfigurationimportselector-%E5%8A%A0%E8%BD%BD%E8%87%AA%E5%8A%A8%E8%A3%85%E9%85%8D%E7%B1%BB





### Spring Boot 加载配置文件的优先级了解么？

项目启动后扫描顺序：

1）先去项目根目录找config文件夹下找配置文件件
 2）再去根目录下找配置文件
 3）去resources下找cofnig文件夹下找配置文件
 4）去resources下找配置文件



![在这里插入图片描述](https://img-blog.csdnimg.cn/bdd2d57bf7ca4c0f90d5fbb9b8add8f9.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5ZC56ICB5biI,size_20,color_FFFFFF,t_70,g_se,x_16)



### 如何使用 Spring Boot 实现全局异常处理？

https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485568&idx=2&sn=c5ba880fd0c5d82e39531fa42cb036ac&chksm=cea2474bf9d5ce5dcbc6a5f6580198fdce4bc92ef577579183a729cb5d1430e4994720d59b34&token=1729829670&lang=zh_CN#rd



- 使用 `@ControllerAdvice` 和 `@ExceptionHandler` 处理全局异常
- `@ExceptionHandler` 处理 Controller 级别的异常

例子：

```
CustomException：

public class CustomException extends RuntimeException {

    private AppHttpCodeEnum appHttpCodeEnum;

    public CustomException(AppHttpCodeEnum appHttpCodeEnum){
        this.appHttpCodeEnum = appHttpCodeEnum;
    }

    public AppHttpCodeEnum getAppHttpCodeEnum() {
        return appHttpCodeEnum;
    }
}

```

```
ExceptionCatch：



@ControllerAdvice  //控制器增强类
@Slf4j
public class ExceptionCatch {

    /**
     * 处理不可控异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e){
        e.printStackTrace();
        log.error("catch exception:{}",e.getMessage());

        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }

    /**
     * 处理可控异常  自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult exception(CustomException e){
        log.error("catch exception:{}",e);
        return ResponseResult.errorResult(e.getAppHttpCodeEnum());
    }
}

```



### 核心配置文件

SpringBoot的核心配置文件是application和bootstrap配置文件。

application配置文件这个容易理解，主要用于Spring Boot项目的自动化配置。

bootstrap配置文件有以下几个应用场景：

- 使用Spring Cloud Config配置中心时，这时需要在bootstrap配置文件中添加连接到配置中心的配置属性来加载外部配置中心的配置信息；
- 一些固定的不能被覆盖的属性；
- 一些加密/解密的场景；





### 实现热部署的方式

这可以使用 DEV 工具来实现。通过这种依赖关系，您可以节省任何更改，嵌入式tomcat 将重新启动。Spring Boot  有一个开发工具（DevTools）模块，它有助于提高开发人员的生产力。Java  开发人员面临的一个主要挑战是将文件更改自动部署到服务器并自动重启服务器。开发人员可以重新加载 Spring Boot  上的更改，而无需重新启动服务器。这将消除每次手动部署更改的需要。Spring Boot  在发布它的第一个版本时没有这个功能。这是开发人员最需要的功能。DevTools 模块完全满足开发人员的需求。该模块将在生产环境中被禁用。它还提供 H2 数据库控制台以更好地测试应用程序。

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```



### 监视器和监听器

#### 监听器

监听器也叫listener，是servlet的监听器，可以用于监听web应用程序中某些对象的创建、销毁、增加、修改、删除等动作的发生，然后做出相应的响应处理。当范围对象的状态发生变化时，服务器自动调用监听器对象中的方法，常用于系统加载时进行信息初始化，统计在线人数和在线用户，统计网站的访问量。 配置监听器的方法：

- 　　通过@Component把监听器加入Spring容器中管理;
- 　　在application.properties中添加context.listener.classes配置;
- 　　在方法上加@EventListener注解;

#### 监控器

**actuator**

```
 这是springboot程序的监控系统，可以实现健康检查，info信息等。在使用之前需要引入`spring-boot-starter-actuator`，并做简单的配置即可。
 引入依赖并编写好配置之后，启动项目，访问http://localhost:8080/actuator
```

Spring Boot自带监控组件—Actuator，它可以帮助实现对程序内部运行情况的监控。Actuator轻松实现应用程序的监控治理，比如健康状况、审计、统计和HTTP追踪、Bean加载情况、环境变量、日志信息、线程信息等。
Actuator的核心是端点（Endpoint），它用来监视、提供应用程序的信息，Spring Boot提供的spring-boot-actuator组件中已经内置了非常多的Endpoint（health、info、beans、metrics、httptrace、shutdown等），每个端点都可以启用和禁用。



### 什么是Spring Boot Starter？有哪些常用的？

和自动配置一样，Spring Boot Starter的目的也是简化配置，而Spring Boot Starter解决的是依赖管理配置复杂的问题，有了它，当我需要构建一个Web应用程序时，不必再遍历所有的依赖包，一个一个地添加到项目的依赖管理中，而是只需要一个配置spring-boot-starter-web。

常用的：

- spring-boot-starter-web - Web 和 RESTful 应用程序
- spring-boot-starter-test - 单元测试和集成测试

**spring-boot-starter-parent有什么作用？**

我们知道，新建一个SpringBoot项目，默认都是有parent的，这个parent就是spring-boot-starter-parent，spring-boot-starter-parent主要有如下作用：

- 定义了Java编译版本
- 使用UTF-8格式编码
- 继承自spring-boor-dependencies，这里面定义了依赖的版本，也正是因为继承了这个依赖，所以我们在写依赖时才不需要写版本号
- 执行打包操作的配置
- 自动化的资源过滤
- 自动化的插件配置



### Spring Boot四大核心组件

四大组件分别是：starter， autoconfigure, CLI 以及actuator。

autoconfigure在我们的开发中并不会被感知，因为它是存在与我们的starter中的。所以我们的每个starter都是依赖autoconfigure的。

Spring Boot CLI是一个命令行使用Spring Boot的客户端工具；主要功能如下：

- 运行groovy脚本 => `官网2.1`
- 打包groovy文件到jar => `官网2.3`
- 初始化Spring Boot项目 => `官网2.4`
- 其他

actuator是Spring Boot的监控插件，本身提供了很多接口可以获取当前项目的各项运行状态指标。



### MyBatis Dao接口原理



### MyBatis分页原理 

![img](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWM0LnpoaW1nLmNvbS84MC92Mi01ZWY4YzU3ZDFiYjI5YTk4Yzc3MTc1YmU2MzI3MDJjMl83MjB3LmpwZw?x-oss-process=image/format,png)

**总结一下：**

- 通过page对象作为分页依据
- 通过count来进行查询总条数的限制
- 对原sql通过limit来进行分页的效果

参考：https://blog.csdn.net/xiaolegeaizy/article/details/108461284

#### MyBatis 是如何进行分页的？分页插件的原理是什么？

答：

**(1)** MyBatis 使用 RowBounds 对象进行分页，它是针对 ResultSet 结果集执行的内存分页，而非物理分页；

**(2)** 可以在 sql 内直接书写带有物理分页的参数来完成物理分页功能；

**(3)** 也可以使用分页插件来完成物理分页。

分页插件的基本原理是使用 MyBatis 提供的插件接口，实现自定义插件，在插件的拦截方法内拦截待执行的 sql，然后重写 sql，根据 dialect 方言，添加对应的物理分页语句和物理分页参数。

```
举例： select _ from student ，拦截 sql 后重写为： select t._ from （select \* from student）t limit 0，10
```

MyBatis 使用 JDK 的动态代理，为需要拦截的接口生成代理对象以实现接口方法拦截功能，每当执行接口对象的方法时，就会进入拦截方法，具体就是 `InvocationHandler` 的 `invoke()` 方法，当然，只会拦截那些指定需要拦截的方法。

实现 MyBatis 的 `Interceptor` 接口并复写 `intercept()` 方法，然后在给插件编写注解，指定要拦截哪一个接口的哪些方法即可，在配置文件中配置编写的插件。





### MyBatis缓存机制(一级缓存、二级缓存、三级缓存)

##### 一级缓存：

作用域是同一个 SqlSession，在同一个 sqlSession 中两次执行相同的 sql 语句，
第一次执行完毕会将数据库中查询的数据写到缓存（内存），第二次会从缓存中获取,从而提高查询效率。当一个 sqlSession 结束后该 sqlSession 中的 一级缓存也就不存在了。**Mybatis 默认开启一级缓存。**
一级缓存内部存储使用一个 HashMap，key 为 hashCode+sqlId+Sql 语句。value 为 从查询出来映射生成的 java 对象 sqlSession 执行 insert、update、delete 等操作 commit 提交后会清空缓存区域。

##### 二级缓存：

是多个 SqlSession 共享的，其作用域是 mapper 的同一个 namespace，不同 的 sqlSession 两次执行相同 namespace 下的 sql 语句且向 sql 中传递参数也相同即最终执行 相同的 sql 语句，第一次执行完毕会将数据库中查询的数据写到缓存（内存），第二次会从 缓存中获取数据将不再从数据库查询，从而提高查询效率。Mybatis 默认没有开启二级缓存 需要在 setting 全局参数中配置开启二级缓存。

在yml中添加以下代码：

![在这里插入图片描述](https://img-blog.csdnimg.cn/5462527f7131484994305ba0c63c4d78.png)

然后在对应的mapper.xml里面加入配置：

![在这里插入图片描述](https://img-blog.csdnimg.cn/832a865ca52f4283a0a2c81919cf1665.png)

注意：开启二级缓存后，对应的pojo一定要实现Serializable，否则在序列化的时候会报错。
readOnly：是否只读 。 值为true时，mybatis认为所有从缓存中获取数据的操作都是只读操作，不会修改数据。mybatis为了加快获取速度，直接会将数据在缓存中的引用交给用户，不安全，但速度快。
值为false时，mybatis觉得获取的数据可能会被修改，mybatis会利用序列化&反序列化的技术克隆一份新的数据给你，安全，但速度慢。

##### 三级缓存：

Mybatis 的一级缓存与二级缓存 只适用于单体项目，在分布式服务或者微服务架构下 都会出现数据不一致问题。所以Mybatis 为我们提供了自定义缓存 我们可以集成很多三方中间件来做缓存 这里就那Redis来说一下。

![在这里插入图片描述](https://img-blog.csdnimg.cn/3556c96846f04ee1aede9f1b22644b70.png)

##### 总结：

- 一级缓存的作用域是一个sqlsession内；二级缓存作用域是针对mapper进行缓存；
- 一级缓存是默认开启的；二级缓存需要手动配置；
- 一级缓存sqlSession 执行 insert、update、delete 等操作 commit 提交后会清空缓存区域。sqlSession.close()后一级缓存也没有了。但是销毁sqlSession后会将里面的缓存存到二级缓存中；
- 二级缓存cache中readonly属性如果为false 那么相应的pojo类必须实现Serializable接口 并且其缓存查询到的对象都是通过序列化或者反序列化克隆的，所以对象之间两两不相等；
- 二级缓存的生命周期和应用同步，它是用来解决一级缓存不能跨会话共享数据的问题，范围是namespace级别的，可以被多个会话共享(只要是同一个接口的相同方法，都可以进行共享)。
- 只要没有显式地设置cacheEnabled为false，都会使用CachingExector装饰基本的执行器(SIMPLE、REUSE、BATCH)。 二级缓存总是默认开启的，但是每个Mapper的二级开关是默认关闭的。
- 二级缓存进行增删改操作也会刷新二级缓存，导致二级缓存失效；

二级缓存的执行流程：

![在这里插入图片描述](https://img-blog.csdnimg.cn/fc440551003f479f93f8c1d43f99470b.png)

参考：https://blog.csdn.net/weixin_45161172/article/details/129526131

## 五：数据库

### MySQL

#### InnoDB特性

##### MyISAM 和 InnoDB 有什么区别？

MySQL 5.5 之前，MyISAM 引擎是 MySQL 的默认存储引擎，可谓是风光一时。

虽然，MyISAM 的性能还行，各种特性也还不错（比如全文索引、压缩、空间函数等）。但是，MyISAM 不支持事务和行级锁，而且最大的缺陷就是崩溃后无法安全恢复。

MySQL 5.5 版本之后，InnoDB 是 MySQL 的默认存储引擎。

言归正传！咱们下面还是来简单对比一下两者：

**1.是否支持行级锁**

MyISAM 只有表级锁(table-level locking)，而 InnoDB 支持行级锁(row-level locking)和表级锁,默认为行级锁。

也就说，MyISAM 一锁就是锁住了整张表，这在并发写的情况下是多么滴憨憨啊！这也是为什么 InnoDB 在并发写的时候，性能更牛皮了！

**2.是否支持事务**

MyISAM 不提供事务支持。

InnoDB 提供事务支持，实现了 SQL 标准定义了四个隔离级别，具有提交(commit)和回滚(rollback)事务的能力。并且，InnoDB 默认使用的 REPEATABLE-READ（可重读）隔离级别是可以解决幻读问题发生的（基于 MVCC 和 Next-Key Lock）。

**3.是否支持外键**

MyISAM 不支持，而 InnoDB 支持。

外键对于维护数据一致性非常有帮助，但是对性能有一定的损耗。因此，通常情况下，我们是不建议在实际生产项目中使用外键的，在业务代码中进行约束即可！

阿里的《Java 开发手册》也是明确规定禁止使用外键的。

**4.是否支持数据库异常崩溃后的安全恢复**

MyISAM 不支持，而 InnoDB 支持。

使用 InnoDB 的数据库在异常崩溃后，数据库重新启动的时候会保证数据库恢复到崩溃前的状态。这个恢复的过程依赖于 `redo log` 。

**5.是否支持 MVCC**

MyISAM 不支持，而 InnoDB 支持。

讲真，这个对比有点废话，毕竟 MyISAM 连行级锁都不支持。MVCC 可以看作是行级锁的一个升级，可以有效减少加锁操作，提高性能。

**6.索引实现不一样。**

虽然 MyISAM 引擎和 InnoDB 引擎都是使用 B+Tree 作为索引结构，但是两者的实现方式不太一样。

InnoDB 引擎中，其数据文件本身就是索引文件。相比 MyISAM，索引文件和数据文件是分离的，其表数据文件本身就是按 B+Tree 组织的一个索引结构，树的叶节点 data 域保存了完整的数据记录。



#### 索引

**索引是一种用于快速查询和检索数据的数据结构，其本质可以看成是一种排序好的数据结构。**

##### 底层数据结构：

**Hash 表**

哈希表是键值对的集合，通过键(key)即可快速取出对应的值(value)，因此哈希表可以快速检索数据（接近 O（1））。

**为何能够通过 key 快速取出 value 呢？** 原因在于 **哈希算法**（也叫散列算法）。通过哈希算法，我们可以快速找到 key 对应的 index，找到了 index 也就找到了对应的 value。

既然哈希表这么快，**为什么 MySQL 没有使用其作为索引的数据结构呢？** 主要是因为 Hash 索引不支持顺序和范围查询。假如我们要对表中的数据进行排序或者进行范围查询，那 Hash 索引可就不行了。并且，每次 IO 只能取一个。

**B 树& B+树**

B 树也称 B-树,全称为 **多路平衡查找树** ，B+ 树是 B 树的一种变体。B 树和 B+树中的 B 是 `Balanced` （平衡）的意思。

目前大部分数据库系统及文件系统都采用 B-Tree 或其变种 B+Tree 作为索引结构。

**B 树& B+树两者有何异同呢？**

- B 树的所有节点既存放键(key) 也存放 数据(data)，而 B+树只有叶子节点存放 key 和 data，其他内节点只存放 key。
- B 树的叶子节点都是独立的;B+树的叶子节点有一条引用链指向与它相邻的叶子节点。
- B 树的检索的过程相当于对范围内的每个节点的关键字做二分查找，可能还没有到达叶子节点，检索就结束了。而 B+树的检索效率就很稳定了，任何查找都是从根节点到叶子节点的过程，叶子节点的顺序检索很明显。

在 MySQL 中，MyISAM 引擎和 InnoDB 引擎都是使用 B+Tree 作为索引结构，但是，两者的实现方式不太一样。（下面的内容整理自《Java 工程师修炼之道》）

> MyISAM 引擎中，B+Tree 叶节点的 data 域存放的是数据记录的地址。在索引检索的时候，首先按照 B+Tree 搜索算法搜索索引，如果指定的 Key 存在，则取出其 data 域的值，然后以 data 域的值为地址读取相应的数据记录。这被称为“**非聚簇索引（非聚集索引）**”。
>
> InnoDB 引擎中，其数据文件本身就是索引文件。相比 MyISAM，索引文件和数据文件是分离的，其表数据文件本身就是按 B+Tree 组织的一个索引结构，树的叶节点 data 域保存了完整的数据记录。这个索引的 key 是数据表的主键，因此 InnoDB 表数据文件本身就是主索引。这被称为“**聚簇索引（聚集索引）**”，而其余的索引都作为 **辅助索引** ，辅助索引的 data 域存储相应记录主键的值而不是地址，这也是和 MyISAM 不同的地方。在根据主索引搜索时，直接找到 key 所在的节点即可取出数据；在根据辅助索引查找时，则需要先取出主键的值，再走一遍主索引。 因此，在设计表的时候，不建议使用过长的字段作为主键，也不建议使用非单调的字段作为主键，这样会造成主索引频繁分裂。

##### 类型总结

按照数据结构维度划分：

- BTree 索引：MySQL 里默认和最常用的索引类型。只有叶子节点存储 value，非叶子节点只有指针和 key。存储引擎 MyISAM 和 InnoDB 实现 BTree 索引都是使用 B+Tree，但二者实现方式不一样（前面已经介绍了）。
- 哈希索引：类似键值对的形式，一次即可定位。
- RTree 索引：一般不会使用，仅支持 geometry 数据类型，优势在于范围查找，效率较低，通常使用搜索引擎如 ElasticSearch 代替。
- 全文索引：对文本的内容进行分词，进行搜索。目前只有 `CHAR`、`VARCHAR` ，`TEXT` 列上可以创建全文索引。一般不会使用，效率较低，通常使用搜索引擎如 ElasticSearch 代替。

按照底层存储方式角度划分：

- 聚簇索引（聚集索引）：索引结构和数据一起存放的索引，InnoDB 中的主键索引就属于聚簇索引。
- 非聚簇索引（非聚集索引）：索引结构和数据分开存放的索引，二级索引(辅助索引)就属于非聚簇索引。MySQL 的 MyISAM 引擎，不管主键还是非主键，使用的都是非聚簇索引。

按照应用维度划分：

- 主键索引：加速查询 + 列值唯一（不可以有 NULL）+ 表中只有一个。
- 普通索引：仅加速查询。
- 唯一索引：加速查询 + 列值唯一（可以有 NULL）。
- 覆盖索引：一个索引包含（或者说覆盖）所有需要查询的字段的值。
- 联合索引：多列值组成一个索引，专门用于组合搜索，其效率大于索引合并。
- 全文索引：对文本的内容进行分词，进行搜索。目前只有 `CHAR`、`VARCHAR` ，`TEXT` 列上可以创建全文索引。一般不会使用，效率较低，通常使用搜索引擎如 ElasticSearch 代替。

  

##### 主键索引： 

数据表的主键列使用的就是主键索引。

一张数据表有只能有一个主键，并且主键不能为 null，不能重复。

在 MySQL 的 InnoDB 的表中，当没有显示的指定表的主键时，InnoDB 会自动先检查表中是否有唯一索引且不允许存在 null 值的字段，如果有，则选择该字段为默认的主键，否则 InnoDB 将会自动创建一个 6Byte 的自增主键。

![img](https://oss.javaguide.cn/github/javaguide/open-source-project/cluster-index.png)



##### 二级索引：

**二级索引（Secondary Index）又称为辅助索引，是因为二级索引的叶子节点存储的数据是主键。也就是说，通过二级索引，可以定位主键的位置。**

唯一索引，普通索引，前缀索引等索引属于二级索引。

PS: 不懂的同学可以暂存疑，慢慢往下看，后面会有答案的，也可以自行搜索。

1. **唯一索引(Unique Key)** ：唯一索引也是一种约束。**唯一索引的属性列不能出现重复的数据，但是允许数据为 NULL，一张表允许创建多个唯一索引。** 建立唯一索引的目的大部分时候都是为了该属性列的数据的唯一性，而不是为了查询效率。
2. **普通索引(Index)** ：**普通索引的唯一作用就是为了快速查询数据，一张表允许创建多个普通索引，并允许数据重复和 NULL。**
3. **前缀索引(Prefix)** ：前缀索引只适用于字符串类型的数据。前缀索引是对文本的前几个字符创建索引，相比普通索引建立的数据更小， 因为只取前几个字符。
4. **全文索引(Full Text)** ：全文索引主要是为了检索大文本数据中的关键字的信息，是目前搜索引擎数据库使用的一种技术。Mysql5.6 之前只有 MYISAM 引擎支持全文索引，5.6 之后 InnoDB 也支持了全文索引。

![img](https://oss.javaguide.cn/github/javaguide/open-source-project/no-cluster-index.png)

##### 聚簇索引：

**聚簇索引（Clustered Index）即索引结构和数据一起存放的索引，并不是一种单独的索引类型。InnoDB 中的主键索引就属于聚簇索引。**

在 MySQL 中，InnoDB 引擎的表的 `.ibd`文件就包含了该表的索引和数据，对于 InnoDB 引擎表来说，该表的索引(B+树)的每个非叶子节点存储索引，叶子节点存储索引和索引对应的数据。

**优点** ：

- **查询速度非常快** ：聚簇索引的查询速度非常的快，因为整个 B+树本身就是一颗多叉平衡树，叶子节点也都是有序的，定位到索引的节点，就相当于定位到了数据。相比于非聚簇索引， 聚簇索引少了一次读取数据的 IO 操作。
- **对排序查找和范围查找优化** ：聚簇索引对于主键的排序查找和范围查找速度非常快。

**缺点** ：

- **依赖于有序的数据** ：因为 B+树是多路平衡树，如果索引的数据不是有序的，那么就需要在插入时排序，如果数据是整型还好，否则类似于字符串或 UUID 这种又长又难比较的数据，插入或查找的速度肯定比较慢。
- **更新代价大** ： 如果对索引列的数据被修改时，那么对应的索引也将会被修改，而且聚簇索引的叶子节点还存放着数据，修改代价肯定是较大的，所以对于主键索引来说，主键一般都是不可被修改的。

##### 非聚簇索引：

**非聚簇索引(Non-Clustered Index)即索引结构和数据分开存放的索引，并不是一种单独的索引类型。二级索引(辅助索引)就属于非聚簇索引。MySQL 的 MyISAM 引擎，不管主键还是非主键，使用的都是非聚簇索引。**

非聚簇索引的叶子节点并不一定存放数据的指针，因为二级索引的叶子节点就存放的是主键，根据主键再回表查数据。

**优点** ：

更新代价比聚簇索引要小 。非聚簇索引的更新代价就没有聚簇索引那么大了，非聚簇索引的叶子节点是不存放数据的

**缺点** ：

- **依赖于有序的数据** ：跟聚簇索引一样，非聚簇索引也依赖于有序的数据
- **可能会二次查询(回表)** ：这应该是非聚簇索引最大的缺点了。 当查到索引对应的指针或主键后，可能还需要根据指针或主键再到数据文件或表中查询。



##### 覆盖索引：

如果一个索引包含（或者说覆盖）所有需要查询的字段的值，我们就称之为 **覆盖索引（Covering Index）** 。我们知道在 InnoDB 存储引擎中，如果不是主键索引，叶子节点存储的是主键+列值。最终还是要“回表”，也就是要通过主键再查找一次，这样就会比较慢。而覆盖索引就是把要查询出的列和索引是对应的，不做回表操作！

**覆盖索引即需要查询的字段正好是索引的字段，那么直接根据该索引，就可以查到数据了，而无需回表查询。**

> 如主键索引，如果一条 SQL 需要查询主键，那么正好根据主键索引就可以查到主键。再如普通索引，如果一条 SQL 需要查询 name，name 字段正好有索引， 那么直接根据这个索引就可以查到数据，也无需回表。

##### 联合索引：

使用表中的多个字段创建索引，就是 **联合索引**，也叫 **组合索引** 或 **复合索引**。

**最左前缀匹配原则**

最左前缀匹配原则指的是，在使用联合索引时，**MySQL** 会根据联合索引中的字段顺序，从左到右依次到查询条件中去匹配，如果查询条件中存在与联合索引中最左侧字段相匹配的字段，则就会使用该字段过滤一批数据，直至联合索引中全部字段匹配完成，或者在执行过程中遇到范围查询（如 **`>`**、**`<`**）才会停止匹配。对于 **`>=`**、**`<=`**、**`BETWEEN`**、**`like`** 前缀匹配的范围查询，并不会停止匹配。所以，我们在使用联合索引时，可以将区分度高的字段放在最左边，这也可以过滤更多数据。

**索引下推**

**索引下推（Index Condition Pushdown）** 是 **MySQL 5.6** 版本中提供的一项索引优化功能，可以在非聚簇索引遍历过程中，对索引中包含的字段先做判断，过滤掉不符合条件的记录，减少回表次数。

##### 正确使用索引的一些建议：

**选择合适的字段创建索引**：

- **不为 NULL 的字段** ：索引字段的数据应该尽量不为 NULL，因为对于数据为 NULL 的字段，数据库较难优化。如果字段频繁被查询，但又避免不了为 NULL，建议使用 0,1,true,false 这样语义较为清晰的短值或短字符作为替代。
- **被频繁查询的字段** ：我们创建索引的字段应该是查询操作非常频繁的字段。
- **被作为条件查询的字段** ：被作为 WHERE 条件查询的字段，应该被考虑建立索引。
- **频繁需要排序的字段** ：索引已经排序，这样查询可以利用索引的排序，加快排序查询时间。
- **被经常频繁用于连接的字段** ：经常用于连接的字段可能是一些外键列，对于外键列并不一定要建立外键，只是说该列涉及到表与表的关系。对于频繁被连接查询的字段，可以考虑建立索引，提高多表连接查询的效率。

**被频繁更新的字段应该慎重建立索引**：

虽然索引能带来查询上的效率，但是维护索引的成本也是不小的。 如果一个字段不被经常查询，反而被经常修改，那么就更不应该在这种字段上建立索引了。

**限制每张表上的索引数量**：

索引并不是越多越好，建议单张表索引不超过 5 个！索引可以提高效率同样可以降低效率。

索引可以增加查询效率，但同样也会降低插入和更新的效率，甚至有些情况下会降低查询效率。

因为 MySQL 优化器在选择如何优化查询时，会根据统一信息，对每一个可以用到的索引来进行评估，以生成出一个最好的执行计划，如果同时有很多个索引都可以用于查询，就会增加 MySQL 优化器生成执行计划的时间，同样会降低查询性能。

**尽可能的考虑建立联合索引而不是单列索引**：

因为索引是需要占用磁盘空间的，可以简单理解为每个索引都对应着一颗 B+树。如果一个表的字段过多，索引过多，那么当这个表的数据达到一个体量后，索引占用的空间也是很多的，且修改索引时，耗费的时间也是较多的。如果是联合索引，多个字段在一个索引上，那么将会节约很大磁盘空间，且修改数据的操作效率也会提升。

**注意避免冗余索引：**

冗余索引指的是索引的功能相同，能够命中索引(a, b)就肯定能命中索引(a) ，那么索引(a)就是冗余索引。如（name,city ）和（name ）这两个索引就是冗余索引，能够命中前者的查询肯定是能够命中后者的 在大多数情况下，都应该尽量扩展已有的索引而不是创建新索引。

**字符串类型的字段使用前缀索引代替普通索引：**

前缀索引仅限于字符串类型，较普通索引会占用更小的空间，所以可以考虑使用前缀索引带替普通索引。

**避免索引失效：**

索引失效也是慢查询的主要原因之一，常见的导致索引失效的情况有下面这些：

- 使用 `SELECT *` 进行查询;
- 创建了组合索引，但查询条件未遵守最左匹配原则;
- 在索引列上进行计算、函数、类型转换等操作;
- 以 `%` 开头的 LIKE 查询比如 `like '%abc'`;
- 查询条件中使用 or，且 or 的前后条件中有一个列没有索引，涉及的索引都不会被使用到;
- 发生隐式转换



#### 锁机制

在 MySQL 里，根据加锁的范围，可以分为**全局锁、表级锁和行锁**三类。

![img](https://cdn.xiaolincoding.com//mysql/other/1e37f6994ef44714aba03b8046b1ace2.png)

##### 全局锁：

要使用全局锁，则要执行这条命令：

```
flush tables with read lock
```

执行后，**整个数据库就处于只读状态了**，这时其他线程执行以下操作，都会被阻塞：

- 对数据的增删改操作，比如 insert、delete、update等语句；
- 对表结构的更改操作，比如 alter table、drop table 等语句。

如果要释放全局锁，则要执行这条命令：

```
unlock tables

```

**全局锁应用场景是什么？**

全局锁主要应用于做**全库逻辑备份**，这样在备份数据库期间，不会因为数据或表结构的更新，而出现备份文件的数据与预期的不一样。

**加全局锁又会带来什么缺点呢？**

加上全局锁，意味着整个数据库都是只读状态。

那么如果数据库里有很多数据，备份就会花费很多的时间，关键是备份期间，业务只能读数据，而不能更新数据，这样会造成业务停滞。

**既然备份数据库数据的时候，使用全局锁会影响业务，那有什么其他方式可以避免？**

有的，如果数据库的引擎支持的事务支持**可重复读的隔离级别**，那么在备份数据库之前先开启事务，会先创建 Read View，然后整个事务执行期间都在用这个  Read View，而且由于 MVCC 的支持，备份期间业务依然可以对数据进行更新操作。

因为在可重复读的隔离级别下，即使其他事务更新了表的数据，也不会影响备份数据库时的 Read View，这就是事务四大特性中的隔离性，这样备份期间备份的数据一直是在开启事务时的数据。

备份数据库的工具是 mysqldump，在使用 mysqldump 时加上 `–single-transaction` 参数的时候，就会在备份数据库之前先开启事务。这种方法只适用于支持「可重复读隔离级别的事务」的存储引擎。

InnoDB 存储引擎默认的事务隔离级别正是可重复读，因此可以采用这种方式来备份数据库。

但是，对于 MyISAM 这种不支持事务的引擎，在备份数据库时就要使用全局锁的方法。

##### 表级锁：

MySQL 里面表级别的锁有这几种：

- 表锁；
- 元数据锁（MDL）;
- 意向锁；
- AUTO-INC 锁；

###### 表锁

先来说说**表锁**。

如果我们想对学生表（t_student）加表锁，可以使用下面的命令：

```sql
//表级别的共享锁，也就是读锁；
lock tables t_student read;

//表级别的独占锁，也就是写锁；
lock tables t_stuent write;
```

需要注意的是，表锁除了会限制别的线程的读写外，也会限制本线程接下来的读写操作。

也就是说如果本线程对学生表加了「共享表锁」，那么本线程接下来如果要对学生表执行写操作的语句，是会被阻塞的，当然其他线程对学生表进行写操作时也会被阻塞，直到锁被释放。

要释放表锁，可以使用下面这条命令，会释放当前会话的所有表锁：

```sql
unlock tables
```

另外，当会话退出后，也会释放所有表锁。

不过尽量避免在使用 InnoDB 引擎的表使用表锁，因为表锁的颗粒度太大，会影响并发性能，**InnoDB 牛逼的地方在于实现了颗粒度更细的行级锁**。

###### 元数据锁

再来说说**元数据锁**（MDL）。

我们不需要显示的使用 MDL，因为当我们对数据库表进行操作时，会自动给这个表加上 MDL：

- 对一张表进行 CRUD 操作时，加的是 **MDL 读锁**；
- 对一张表做结构变更操作的时候，加的是 **MDL 写锁**；

MDL 是为了保证当用户对表执行 CRUD 操作时，防止其他线程对这个表结构做了变更。

当有线程在执行 select 语句（ 加 MDL 读锁）的期间，如果有其他线程要更改该表的结构（ 申请 MDL 写锁），那么将会被阻塞，直到执行完 select 语句（ 释放 MDL 读锁）。

反之，当有线程对表结构进行变更（ 加 MDL 写锁）的期间，如果有其他线程执行了 CRUD 操作（ 申请 MDL 读锁），那么就会被阻塞，直到表结构变更完成（ 释放 MDL 写锁）。

> MDL 不需要显示调用，那它是在什么时候释放的?

MDL 是在事务提交后才会释放，这意味着**事务执行期间，MDL 是一直持有的**。

那如果数据库有一个长事务（所谓的长事务，就是开启了事务，但是一直还没提交），那在对表结构做变更操作的时候，可能会发生意想不到的事情，比如下面这个顺序的场景：

1. 首先，线程 A 先启用了事务（但是一直不提交），然后执行一条 select 语句，此时就先对该表加上 MDL 读锁；
2. 然后，线程 B 也执行了同样的 select  语句，此时并不会阻塞，因为「读读」并不冲突；
3. 接着，线程 C 修改了表字段，此时由于线程 A 的事务并没有提交，也就是 MDL 读锁还在占用着，这时线程 C 就无法申请到 MDL 写锁，就会被阻塞，

那么在线程 C 阻塞后，后续有对该表的 select 语句，就都会被阻塞，如果此时有大量该表的 select 语句的请求到来，就会有大量的线程被阻塞住，这时数据库的线程很快就会爆满了。

> 为什么线程 C  因为申请不到  MDL 写锁，而导致后续的申请读锁的查询操作也会被阻塞？

这是因为申请 MDL 锁的操作会形成一个队列，队列中**写锁获取优先级高于读锁**，一旦出现 MDL 写锁等待，会阻塞后续该表的所有 CRUD 操作。

所以为了能安全的对表结构进行变更，在对表结构变更前，先要看看数据库中的长事务，是否有事务已经对表加上了 MDL 读锁，如果可以考虑 kill 掉这个长事务，然后再做表结构的变更。

###### 意向锁

接着，说说**意向锁**。

- 在使用 InnoDB 引擎的表里对某些记录加上「共享锁」之前，需要先在表级别加上一个「意向共享锁」；
- 在使用 InnoDB 引擎的表里对某些纪录加上「独占锁」之前，需要先在表级别加上一个「意向独占锁」；

也就是，当执行插入、更新、删除操作，需要先对表加上「意向独占锁」，然后对该记录加独占锁。

而普通的 select 是不会加行级锁的，普通的 select 语句是利用 MVCC 实现一致性读，是无锁的。

不过，select 也是可以对记录加共享锁和独占锁的，具体方式如下：

```sql
//先在表上加上意向共享锁，然后对读取的记录加共享锁
select ... lock in share mode;

//先表上加上意向独占锁，然后对读取的记录加独占锁
select ... for update;
```

**意向共享锁和意向独占锁是表级锁，不会和行级的共享锁和独占锁发生冲突，而且意向锁之间也不会发生冲突，只会和共享表锁（\*lock tables ... read\*）和独占表锁（\*lock tables ... write\*）发生冲突。**

表锁和行锁是满足读读共享、读写互斥、写写互斥的。

如果没有「意向锁」，那么加「独占表锁」时，就需要遍历表里所有记录，查看是否有记录存在独占锁，这样效率会很慢。

那么有了「意向锁」，由于在对记录加独占锁前，先会加上表级别的意向独占锁，那么在加「独占表锁」时，直接查该表是否有意向独占锁，如果有就意味着表里已经有记录被加了独占锁，这样就不用去遍历表里的记录。

所以，**意向锁的目的是为了快速判断表里是否有记录被加锁**。

###### AUTO-INC 锁

表里的主键通常都会设置成自增的，这是通过对主键字段声明 `AUTO_INCREMENT` 属性实现的。

之后可以在插入数据时，可以不指定主键的值，数据库会自动给主键赋值递增的值，这主要是通过 **AUTO-INC 锁**实现的。

AUTO-INC 锁是特殊的表锁机制，锁**不是再一个事务提交后才释放，而是再执行完插入语句后就会立即释放**。

**在插入数据时，会加一个表级别的 AUTO-INC 锁**，然后为被 `AUTO_INCREMENT` 修饰的字段赋值递增的值，等插入语句执行完成后，才会把  AUTO-INC 锁释放掉。

那么，一个事务在持有 AUTO-INC 锁的过程中，其他事务的如果要向该表插入语句都会被阻塞，从而保证插入数据时，被 `AUTO_INCREMENT` 修饰的字段的值是连续递增的。

但是， AUTO-INC 锁再对大量数据进行插入的时候，会影响插入性能，因为另一个事务中的插入会被阻塞。

因此， 在 MySQL 5.1.22 版本开始，InnoDB 存储引擎提供了一种**轻量级的锁**来实现自增。

一样也是在插入数据的时候，会为被 `AUTO_INCREMENT` 修饰的字段加上轻量级锁，**然后给该字段赋值一个自增的值，就把这个轻量级锁释放了，而不需要等待整个插入语句执行完后才释放锁**。

InnoDB 存储引擎提供了个 innodb_autoinc_lock_mode 的系统变量，是用来控制选择用 AUTO-INC 锁，还是轻量级的锁。

- 当 innodb_autoinc_lock_mode = 0，就采用 AUTO-INC 锁，语句执行结束后才释放锁；
- 当 innodb_autoinc_lock_mode = 2，就采用轻量级锁，申请自增主键后就释放锁，并不需要等语句执行后才释放。
- 当 innodb_autoinc_lock_mode = 1：
  - 普通 insert 语句，自增锁在申请之后就马上释放；
  - 类似 insert … select 这样的批量插入数据的语句，自增锁还是要等语句结束后才被释放；

当 innodb_autoinc_lock_mode = 2 是性能最高的方式，但是当搭配  binlog 的日志格式是 statement 一起使用的时候，在「主从复制的场景」中会发生**数据不一致的问题**。

要解决这问题，binlog 日志格式要设置为 row，这样在 binlog 里面记录的是主库分配的自增值，到备库执行的时候，主库的自增值是什么，从库的自增值就是什么。

所以，**当 innodb_autoinc_lock_mode = 2 时，并且 binlog_format = row，既能提升并发性，又不会出现数据一致性问题**。



##### 行级锁：

InnoDB 引擎是支持行级锁的，而 MyISAM 引擎并不支持行级锁。

前面也提到，普通的 select 语句是不会对记录加锁的，因为它属于快照读。如果要在查询时对记录加行锁，可以使用下面这两个方式，这种查询会加锁的语句称为**锁定读**。

```sql
//对读取的记录加共享锁
select ... lock in share mode;

//对读取的记录加独占锁
select ... for update;
```

上面这两条语句必须在一个事务中，**因为当事务提交了，锁就会被释放**，所以在使用这两条语句的时候，要加上 begin、start transaction 或者 set autocommit = 0。

共享锁（S锁）满足读读共享，读写互斥。独占锁（X锁）满足写写互斥、读写互斥。

![img](https://cdn.xiaolincoding.com/gh/xiaolincoder/mysql/%E9%94%81/x%E9%94%81%E5%92%8Cs%E9%94%81.png)

行级锁的类型主要有三类：

- Record Lock，记录锁，也就是仅仅把一条记录锁上；
- Gap Lock，间隙锁，锁定一个范围，但是不包含记录本身；
- Next-Key Lock：Record Lock + Gap Lock 的组合，锁定一个范围，并且锁定记录本身。

###### Record Lock

Record Lock 称为记录锁，锁住的是一条记录。而且记录锁是有 S 锁和 X 锁之分的：

- 当一个事务对一条记录加了 S 型记录锁后，其他事务也可以继续对该记录加 S 型记录锁（S 型与 S 锁兼容），但是不可以对该记录加 X 型记录锁（S 型与 X 锁不兼容）;
- 当一个事务对一条记录加了 X 型记录锁后，其他事务既不可以对该记录加 S 型记录锁（S 型与 X 锁不兼容），也不可以对该记录加 X 型记录锁（X 型与 X 锁不兼容）。

###### Gap Lock

Gap Lock 称为间隙锁，只存在于可重复读隔离级别，目的是为了解决可重复读隔离级别下幻读的现象。

假设，表中有一个范围 id 为（3，5）间隙锁，那么其他事务就无法插入 id = 4 这条记录了，这样就有效的防止幻读现象的发生。

间隙锁虽然存在  X 型间隙锁和 S 型间隙锁，但是并没有什么区别，**间隙锁之间是兼容的，即两个事务可以同时持有包含共同间隙范围的间隙锁，并不存在互斥关系，因为间隙锁的目的是防止插入幻影记录而提出的**。

###### Next-Key Lock

Next-Key Lock 称为临键锁，是 Record Lock + Gap Lock 的组合，锁定一个范围，并且锁定记录本身。

所以，next-key lock 即能保护该记录，又能阻止其他事务将新纪录插入到被保护记录前面的间隙中。

**next-key lock 是包含间隙锁+记录锁的，如果一个事务获取了 X 型的 next-key lock，那么另外一个事务在获取相同范围的 X 型的 next-key lock 时，是会被阻塞的**。

比如，一个事务持有了范围为 (1, 10] 的 X 型的 next-key lock，那么另外一个事务在获取相同范围的 X 型的 next-key lock 时，就会被阻塞。

虽然相同范围的间隙锁是多个事务相互兼容的，但对于记录锁，我们是要考虑 X 型与 S 型关系，X 型的记录锁与 X 型的记录锁是冲突的。

###### 插入意向锁

一个事务在插入一条记录的时候，需要判断插入位置是否已被其他事务加了间隙锁（next-key lock 也包含间隙锁）。

如果有的话，插入操作就会发生**阻塞**，直到拥有间隙锁的那个事务提交为止（释放间隙锁的时刻），在此期间会生成一个**插入意向锁**，表明有事务想在某个区间插入新记录，但是现在处于等待状态。

插入意向锁名字虽然有意向锁，但是它并**不是意向锁，它是一种特殊的间隙锁，属于行级别锁**。

如果说间隙锁锁住的是一个区间，那么「插入意向锁」锁住的就是一个点。因而从这个角度来说，插入意向锁确实是一种特殊的间隙锁。

插入意向锁与间隙锁的另一个非常重要的差别是：尽管「插入意向锁」也属于间隙锁，但两个事务却不能在同一时间内，一个拥有间隙锁，另一个拥有该间隙区间内的插入意向锁（当然，插入意向锁如果不在间隙锁区间内则是可以的）。





#### ACID事务

事务是由 MySQL 的引擎来实现的，我们常见的 InnoDB 引擎它是支持事务的。

不过并不是所有的引擎都能支持事务，比如 MySQL 原生的 MyISAM 引擎就不支持事务，也正是这样，所以大多数 MySQL 的引擎都是用 InnoDB。

事务看起来感觉简单，但是要实现事务必须要遵守 4 个特性，分别如下：

- **原子性（Atomicity）**：一个事务中的所有操作，要么全部完成，要么全部不完成，不会结束在中间某个环节，而且事务在执行过程中发生错误，会被回滚到事务开始前的状态，就像这个事务从来没有执行过一样，就好比买一件商品，购买成功时，则给商家付了钱，商品到手；购买失败时，则商品在商家手中，消费者的钱也没花出去。
- **一致性（Consistency）**：是指事务操作前和操作后，数据满足完整性约束，数据库保持一致性状态。比如，用户 A 和用户 B 在银行分别有 800 元和 600 元，总共 1400 元，用户 A 给用户 B 转账 200 元，分为两个步骤，从 A  的账户扣除 200 元和对 B 的账户增加 200 元。一致性就是要求上述步骤操作后，最后的结果是用户 A 还有 600 元，用户 B 有  800 元，总共 1400 元，而不会出现用户 A 扣除了 200 元，但用户 B 未增加的情况（该情况，用户 A 和 B 均为 600  元，总共 1200 元）。
- **隔离性（Isolation）**：数据库允许多个并发事务同时对其数据进行读写和修改的能力，隔离性可以防止多个事务并发执行时由于交叉执行而导致数据的不一致，因为多个事务同时使用相同的数据时，不会相互干扰，每个事务都有一个完整的数据空间，对其他并发事务是隔离的。也就是说，消费者购买商品这个事务，是不影响其他消费者购买的。
- **持久性（Durability）**：事务处理结束后，对数据的修改就是永久的，即便系统故障也不会丢失。

InnoDB 引擎通过什么技术来保证事务的这四个特性的呢？

- 持久性是通过 redo log （重做日志）来保证的；
- 原子性是通过 undo log（回滚日志） 来保证的；
- 隔离性是通过 MVCC（多版本并发控制） 或锁机制来保证的；
- 一致性则是通过持久性+原子性+隔离性来保证；

##### 脏读

**如果一个事务「读到」了另一个「未提交事务修改过的数据」，就意味着发生了「脏读」现象。**

举个栗子。

假设有 A 和 B 这两个事务同时在处理，事务 A 先开始从数据库中读取小林的余额数据，然后再执行更新操作，如果此时事务 A  还没有提交事务，而此时正好事务 B 也从数据库中读取小林的余额数据，那么事务 B 读取到的余额数据是刚才事务 A  更新后的数据，即使没有提交事务。

因为事务 A 是还没提交事务的，也就是它随时可能发生回滚操作，**如果在上面这种情况事务 A 发生了回滚，那么事务 B 刚才得到的数据就是过期的数据，这种现象就被称为脏读。**

##### 不可重复读

**在一个事务内多次读取同一个数据，如果出现前后两次读到的数据不一样的情况，就意味着发生了「不可重复读」现象。**

举个栗子。

假设有 A 和 B 这两个事务同时在处理，事务 A 先开始从数据库中读取小林的余额数据，然后继续执行代码逻辑处理，**在这过程中如果事务 B 更新了这条数据，并提交了事务，那么当事务 A 再次读取该数据时，就会发现前后两次读到的数据是不一致的，这种现象就被称为不可重复读。**

##### 幻读

**在一个事务内多次查询某个符合查询条件的「记录数量」，如果出现前后两次查询到的记录数量不一样的情况，就意味着发生了「幻读」现象。**

举个栗子。

假设有 A 和 B 这两个事务同时在处理，事务 A 先开始从数据库查询账户余额大于 100 万的记录，发现共有 5 条，然后事务 B 也按相同的搜索条件也是查询出了 5 条记录。

接下来，事务 A 插入了一条余额超过 100 万的账号，并提交了事务，此时数据库超过 100 万余额的账号个数就变为 6。

然后事务 B 再次查询账户余额大于 100 万的记录，此时查询到的记录数量有 6 条，**发现和前一次读到的记录数量不一样了，就感觉发生了幻觉一样，这种现象就被称为幻读。**



#### 四大隔离级别

SQL 标准提出了四种隔离级别来规避这些现象，隔离级别越高，性能效率就越低，这四个隔离级别如下：

- **读未提交（\*read uncommitted\*）**，指一个事务还没提交时，它做的变更就能被其他事务看到；
- **读提交（\*read committed\*）**，指一个事务提交之后，它做的变更才能被其他事务看到；
- **可重复读（\*repeatable read\*）**，指一个事务执行过程中看到的数据，一直跟这个事务启动时看到的数据是一致的，**MySQL InnoDB 引擎的默认隔离级别**；
- **串行化（\*serializable\* ）**；会对记录加上读写锁，在多个事务对这条记录进行读写操作时，如果发生了读写冲突的时候，后访问的事务必须等前一个事务执行完成，才能继续执行；

按隔离水平高低排序如下：

![图片](https://cdn.xiaolincoding.com//mysql/other/cce766a69dea725cd8f19b90db2d0430.png)



针对不同的隔离级别，并发事务时可能发生的现象也会不同。也就是说：

- 在「读未提交」隔离级别下，可能发生脏读、不可重复读和幻读现象；
- 在「读提交」隔离级别下，可能发生不可重复读和幻读现象，但是不可能发生脏读现象；
- 在「可重复读」隔离级别下，可能发生幻读现象，但是不可能脏读和不可重复读现象；
- 在「串行化」隔离级别下，脏读、不可重复读和幻读现象都不可能会发生。

所以，要解决脏读现象，就要升级到「读提交」以上的隔离级别；要解决不可重复读现象，就要升级到「可重复读」的隔离级别，要解决幻读现象不建议将隔离级别升级到「串行化」。

不同的数据库厂商对 SQL 标准中规定的 4 种隔离级别的支持不一样，有的数据库只实现了其中几种隔离级别，**我们讨论的 MySQL 虽然支持 4 种隔离级别，但是与SQL 标准中规定的各级隔离级别允许发生的现象却有些出入**。

MySQL 在「可重复读」隔离级别下，可以很大程度上避免幻读现象的发生（注意是很大程度避免，并不是彻底避免），所以 MySQL 并不会使用「串行化」隔离级别来避免幻读现象的发生，因为使用「串行化」隔离级别会影响性能。

MySQL InnoDB 引擎的默认隔离级别虽然是「可重复读」，但是它很大程度上避免幻读现象，解决的方案有两种：

- 针对**快照读**（普通 select 语句），是**通过 MVCC 方式解决了幻读**，因为可重复读隔离级别下，事务执行过程中看到的数据，一直跟这个事务启动时看到的数据是一致的，即使中途有其他事务插入了一条数据，是查询不出来这条数据的，所以就很好了避免幻读问题。
- 针对**当前读**（select ... for update 等语句），是**通过 next-key lock（记录锁+间隙锁）方式解决了幻读**，因为当执行 select ... for update 语句的时候，会加上 next-key lock，如果有其他事务在 next-key lock 锁范围内插入了一条记录，那么这个插入语句就会被阻塞，无法成功插入，所以就很好了避免幻读问题。



这四种隔离级别具体是如何实现的呢？

- 对于「读未提交」隔离级别的事务来说，因为可以读到未提交事务修改的数据，所以直接读取最新的数据就好了；
- 对于「串行化」隔离级别的事务来说，通过加读写锁的方式来避免并行访问；
- 对于「读提交」和「可重复读」隔离级别的事务来说，它们是通过 Read View 来实现的，它们的区别在于创建 Read View 的时机不同，大家可以把 Read View 理解成一个数据快照，就像相机拍照那样，定格某一时刻的风景。「读提交」隔离级别是在「每个语句执行前」都会重新生成一个 Read View，而「可重复读」隔离级别是「启动事务时」生成一个 Read View，然后整个事务期间都在用这个 Read View。





#### MVCC

`MVCC` 的实现依赖于：**隐藏字段、Read View、undo log**。在内部实现中，`InnoDB` 通过数据行的 `DB_TRX_ID` 和 `Read View` 来判断数据的可见性，如不可见，则通过数据行的 `DB_ROLL_PTR` 找到 `undo log` 中的历史版本。每个事务读到的数据版本可能是不一样的，在同一个事务中，用户只能看到该事务创建 `Read View` 之前已经提交的修改和该事务本身做的修改

**隐藏字段**

在内部，`InnoDB` 存储引擎为每行数据添加了三个 隐藏字段：

`DB_TRX_ID（6字节）`：表示最后一次插入或更新该行的事务 id。此外，`delete` 操作在内部被视为更新，只不过会在记录头 `Record header` 中的 `deleted_flag` 字段将其标记为已删除

`DB_ROLL_PTR（7字节）` 回滚指针，指向该行的 `undo log` 。如果该行未被更新，则为空

`DB_ROW_ID（6字节）`：如果没有设置主键且该表没有唯一非空索引时，`InnoDB` 会使用该 id 来生成聚簇索引



**ReadView**主要是用来做可见性判断，里面保存了 “当前对本事务不可见的其他活跃事务”

```
  trx_id_t m_low_limit_id;      /* 大于等于这个 ID 的事务均不可见 */

  trx_id_t m_up_limit_id;       /* 小于这个 ID 的事务均可见 */

  trx_id_t m_creator_trx_id;    /* 创建该 Read View 的事务ID */

  trx_id_t m_low_limit_no;      /* 事务 Number, 小于该 Number 的 Undo Logs 均可以被 Purge */

  ids_t m_ids;                  /* 创建 Read View 时的活跃事务列表 */
```



主要有以下字段：

- `m_low_limit_id`：目前出现过的最大的事务 ID+1，即下一个将被分配的事务 ID。大于等于这个 ID 的数据版本均不可见
- `m_up_limit_id`：活跃事务列表 `m_ids` 中最小的事务 ID，如果 `m_ids` 为空，则 `m_up_limit_id` 为 `m_low_limit_id`。小于这个 ID 的数据版本均可见
- `m_ids`：`Read View` 创建时其他未提交的活跃事务 ID 列表。创建 `Read View`时，将当前未提交事务 ID 记录下来，后续即使它们修改了记录行的值，对于当前事务也是不可见的。`m_ids` 不包括当前事务自己和已提交的事务（正在内存中）
- `m_creator_trx_id`：创建该 `Read View` 的事务 ID

![trans_visible](https://javaguide.cn/assets/trans_visible.048192c5.png)

#### undo-log

`undo log` 主要有两个作用：

- 当事务回滚时用于将数据恢复到修改前的样子
- 另一个作用是 `MVCC` ，当读取记录时，若该记录被其他事务占用或当前版本对该事务不可见，则可以通过 `undo log` 读取之前的版本数据，以此实现非锁定读

**在 `InnoDB` 存储引擎中 `undo log` 分为两种： `insert undo log` 和 `update undo log`：**

1. **`insert undo log`** ：指在 `insert` 操作中产生的 `undo log`。因为 `insert` 操作的记录只对事务本身可见，对其他事务不可见，故该 `undo log` 可以在事务提交后直接删除。不需要进行 `purge` 操作



#### Jdbc防止sql注入

我们只需要使用PreparedStatement解决 **将敏感字符进行转义**。

我们可以看到输出的SQL文是把整个参数用引号包起来，并把参数中的引号作为转义字符，从而避免了参数也作为条件的一部分。

参考：https://blog.csdn.net/weixin_52258054/article/details/124891266



#### 常见优化手段

##### 经常一起使用的列放到一个表中

避免更多的关联操作。

##### 禁止在数据库中存储文件（比如图片）这类大的二进制数据

在数据库中存储文件会严重影响数据库性能，消耗过多存储空间。

文件（比如图片）这类大的二进制数据通常存储于文件服务器，数据库只存储文件地址信息。

##### 优先选择符合存储需要的最小的数据类型

存储字节越小，占用也就空间越小，性能也越好。

##### 同财务相关的金额类数据必须使用 decimal 类型

- **非精准浮点** ：float,double
- **精准浮点** ：decimal

decimal 类型为精准浮点数，在计算时不会丢失精度。占用空间由定义的宽度决定，每 4 个字节可以存储 9 位数字，并且小数点要占用一个字节。并且，decimal 可用于存储比 bigint 更大的整型数据

不过， 由于 decimal 需要额外的空间和计算开销，应该尽量只在需要对数据进行精确计算时才使用 decimal 。

##### 单表不要包含过多字段

如果一个表包含过多字段的话，可以考虑将其分解成多个表，必要时增加中间表进行关联。

##### 禁止使用全文索引

全文索引不适用于 OLTP 场景。

##### 禁止给表中的每一列都建立单独的索引

5.6 版本之前，一个 sql 只能使用到一个表中的一个索引，5.6 以后，虽然有了合并索引的优化方式，但是还是远远没有使用一个联合索引的查询方式好。

##### 每个 InnoDB 表必须有个主键



##### 禁止使用 SELECT * 必须使用 SELECT <字段列表> 查询

- `SELECT *` 消耗更多的 CPU 和 IO 以网络带宽资源
- `SELECT *` 无法使用覆盖索引
- `SELECT <字段列表>` 可减少表结构变更带来的影响



##### 避免使用子查询，可以把子查询优化为 join 操作

通常子查询在 in 子句中，且子查询中为简单 SQL(不包含 union、group by、order by、limit 从句) 时,才可以把子查询转化为关联查询进行优化。

**子查询性能差的原因：** 子查询的结果集无法使用索引，通常子查询的结果集会被存储到临时表中，不论是内存临时表还是磁盘临时表都不会存在索引，所以查询性能会受到一定的影响。特别是对于返回结果集比较大的子查询，其对查询性能的影响也就越大。由于子查询会产生大量的临时表也没有索引，所以会消耗过多的 CPU 和 IO 资源，产生大量的慢查询。



### Redis

#### Redis 为什么这么快？

Redis 内部做了非常多的性能优化，比较重要的主要有下面 3 点：

- Redis 基于内存，内存的访问速度是磁盘的上千倍；
- Redis 基于 Reactor 模式设计开发了一套高效的事件处理模型，主要是单线程事件循环和 IO 多路复用（Redis 线程模式后面会详细介绍到）；
- Redis 内置了多种优化过后的数据结构实现，性能非常高。



#### 基本数据类型



**5 种基础数据类型** ：String（字符串）、List（列表）、Set（集合）、Hash（散列）、Zset（有序集合）。

**3 种特殊数据类型** ：HyperLogLogs（基数统计）、Bitmap （位存储）、Geospatial (地理位置)。



#### 数据结构及适用场景

##### String（字符串）：

###### 数据结构：

String 类型的底层的数据结构实现主要是 int 和 SDS（简单动态字符串）。

SDS 和我们认识的 C 字符串不太一样，之所以没有使用 C 语言的字符串表示，因为 SDS 相比于 C 的原生字符串：

- **SDS  不仅可以保存文本数据，还可以保存二进制数据**。因为 `SDS` 使用 `len` 属性的值而不是空字符来判断字符串是否结束，并且 SDS 的所有 API 都会以处理二进制的方式来处理 SDS 存放在 `buf[]` 数组里的数据。所以 SDS 不光能存放文本数据，而且能保存图片、音频、视频、压缩文件这样的二进制数据。
- **SDS 获取字符串长度的时间复杂度是 O(1)**。因为 C 语言的字符串并不记录自身长度，所以获取长度的复杂度为 O(n)；而 SDS 结构里用 `len` 属性记录了字符串长度，所以复杂度为 `O(1)`。
- **Redis 的 SDS API 是安全的，拼接字符串不会造成缓冲区溢出**。因为 SDS 在拼接字符串之前会检查 SDS 空间是否满足要求，如果空间不够会自动扩容，所以不会导致缓冲区溢出的问题。

###### 应用场景：

- 常规数据（比如 session、token、序列化后的对象、图片的路径）的缓存；
- 计数比如用户单位时间的请求数（简单限流可以用到）、页面单位时间的访问数；
- 分布式锁(利用 `SETNX key value` 命令可以实现一个最简易的分布式锁)；



##### List（列表）：

###### 数据结构：

List 类型的底层数据结构是由**双向链表或压缩列表**实现的：

- 如果列表的元素个数小于 `512` 个（默认值，可由 `list-max-ziplist-entries` 配置），列表每个元素的值都小于 `64` 字节（默认值，可由 `list-max-ziplist-value` 配置），Redis 会使用**压缩列表**作为 List 类型的底层数据结构；
- 如果列表的元素不满足上面的条件，Redis 会使用**双向链表**作为 List 类型的底层数据结构；

但是**在 Redis 3.2 版本之后，List 数据类型底层数据结构就只由 quicklist 实现了，替代了双向链表和压缩列表**。



###### 应用场景：

消息队列，List 可以使用 LPUSH + RPOP （或者反过来，RPUSH+LPOP）命令实现消息队列；



##### Set（集合）：

###### 数据结构：

Set 类型的底层数据结构是由**哈希表或整数集合**实现的：

- 如果集合中的元素都是整数且元素个数小于 `512` （默认值，`set-maxintset-entries`配置）个，Redis 会使用**整数集合**作为 Set 类型的底层数据结构；
- 如果集合中的元素不满足上面条件，则 Redis 使用**哈希表**作为 Set 类型的底层数据结构。

###### 应用场景：

点赞、共同关注；



##### Hash（散列）：

###### 数据结构：

Hash 类型的底层数据结构是由**压缩列表或哈希表**实现的：

- 如果哈希类型元素个数小于 `512` 个（默认值，可由 `hash-max-ziplist-entries` 配置），所有值小于 `64` 字节（默认值，可由 `hash-max-ziplist-value` 配置）的话，Redis 会使用**压缩列表**作为 Hash 类型的底层数据结构；
- 如果哈希类型元素不满足上面条件，Redis 会使用**哈希表**作为 Hash 类型的 底层数据结构。

**在 Redis 7.0 中，压缩列表数据结构已经废弃了，交由 listpack 数据结构来实现了**。

###### 应用场景：

Hash 类型的 （key，field， value） 的结构与对象的（对象id， 属性， 值）的结构相似，也可以用来存储对象以及购物车；



##### Zset（有序集合）：

###### 数据结构：

Zset 类型的底层数据结构是由**压缩列表或跳表**实现的：

- 如果有序集合的元素个数小于 `128` 个，并且每个元素的值小于 `64` 字节时，Redis 会使用**压缩列表**作为 Zset 类型的底层数据结构；
- 如果有序集合的元素不满足上面的条件，Redis 会使用**跳表**作为 Zset 类型的底层数据结构；

**在 Redis 7.0 中，压缩列表数据结构已经废弃了，交由 listpack 数据结构来实现了。**

###### 应用场景：

有序集合比较典型的使用场景就是排行榜。例如学生成绩的排名榜、游戏积分排行榜、视频播放排名、电商系统中商品的销量排名等。





#### 持久化

Redis 不同于 Memcached 的很重要一点就是，Redis 支持持久化，而且支持 3 种持久化方式:

- 快照（snapshotting，RDB）
- 只追加文件（append-only file, AOF）
- RDB 和 AOF 的混合持久化(Redis 4.0 新增)

### 什么是 RDB 持久化？

Redis 可以通过创建快照来获得存储在内存里面的数据在 **某个时间点** 上的副本。Redis 创建快照之后，可以对快照进行备份，可以将快照复制到其他服务器从而创建具有相同数据的服务器副本（Redis 主从结构，主要用来提高 Redis 性能），还可以将快照留在原地以便重启服务器的时候使用。

快照持久化是 Redis 默认采用的持久化方式，在 `redis.conf` 配置文件中默认有此下配置：

```
save 900 1           #在900秒(15分钟)之后，如果至少有1个key发生变化，Redis就会自动触发bgsave命令创建快照。

save 300 10          #在300秒(5分钟)之后，如果至少有10个key发生变化，Redis就会自动触发bgsave命令创建快照。

save 60 10000        #在60秒(1分钟)之后，如果至少有10000个key发生变化，Redis就会自动触发bgsave命令创建快照。

```

### RDB 创建快照时会阻塞主线程吗？

Redis 提供了两个命令来生成 RDB 快照文件：

- `save` : 同步保存操作，会阻塞 Redis 主线程；
- `bgsave` : fork 出一个子进程，子进程执行，不会阻塞 Redis 主线程，默认选项。



### 什么是 AOF 持久化？

与快照持久化相比，AOF 持久化的实时性更好。默认情况下 Redis 没有开启 AOF（append only file）方式的持久化（Redis 6.0 之后已经默认是开启了），可以通过 `appendonly` 参数开启：

```
appendonly yes
```

开启 AOF 持久化后每执行一条会更改 Redis 中的数据的命令，Redis 就会将该命令写入到 AOF 缓冲区 `server.aof_buf` 中，然后再写入到 AOF 文件中（此时还在系统内核缓存区为同步到磁盘），最后再根据持久化方式（ `fsync`策略）的配置来决定何时将系统内核缓存区的数据同步到硬盘中的。

只有同步到磁盘中才算持久化保存了，否则依然存在数据丢失的风险，比如说：系统内核缓存区的数据还未同步，磁盘机器就宕机了，那这部分数据就算丢失了。

AOF 文件的保存位置和 RDB 文件的位置相同，都是通过 `dir` 参数设置的，默认的文件名是 `appendonly.aof`。

### AOF 工作基本流程是怎样的？

AOF 持久化功能的实现可以简单分为 5 步：

1. **命令追加（append）** ：所有的写命令会追加到 AOF 缓冲区中。
2. **文件写入（write）** ：将 AOF 缓冲区的数据写入到 AOF 文件中。这一步需要调用`write`函数（系统调用），`write`将数据写入到了系统内核缓冲区之后直接返回了（延迟写）。注意！！！此时并没有同步到磁盘。
3. **文件同步（fsync）** ：AOF 缓冲区根据对应的持久化方式（ `fsync` 策略）向硬盘做同步操作。这一步需要调用 `fsync` 函数（系统调用）， `fsync` 针对单个文件操作，对其进行强制硬盘同步，`fsync` 将阻塞直到写入磁盘完成后返回，保证了数据持久化。
4. **文件重写（rewrite）** ：随着 AOF 文件越来越大，需要定期对 AOF 文件进行重写，达到压缩的目的。
5. **重启加载（load）** ：当 Redis 重启时，可以加载 AOF 文件进行数据恢复。

> Linux 系统直接提供了一些函数用于对文件和设备进行访问和控制，这些函数被称为 **系统调用（syscall）**。

这里对上面提到的一些 Linux 系统调用再做一遍解释：

- `write` ：写入系统内核缓冲区之后直接返回（仅仅是写到缓冲区），不会立即同步到硬盘。虽然提高了效率，但也带来了数据丢失的风险。同步硬盘操作通常依赖于系统调度机制，Linux 内核通常为 30s 同步一次，具体值取决于写出的数据量和 I/O 缓冲区的状态。
- `fsync` ： `fsync`用于强制刷新系统内核缓冲区（同步到到磁盘），确保写磁盘操作结束才会返回。

AOF 工作流程图如下：

![AOF 工作基本流程](https://oss.javaguide.cn/github/javaguide/database/redis/aof-work-process.png)





#### 删除与淘汰策略

##### 过期的数据的删除策略了解么？

如果假设你设置了一批 key 只能存活 1 分钟，那么 1 分钟后，Redis 是怎么对这批 key 进行删除的呢？

常用的过期数据的删除策略就两个（重要！自己造缓存轮子的时候需要格外考虑的东西）：

1. **惰性删除** ：只会在取出 key 的时候才对数据进行过期检查。这样对 CPU 最友好，但是可能会造成太多过期 key 没有被删除。
2. **定期删除** ： 每隔一段时间抽取一批 key 执行删除过期 key 操作。并且，Redis 底层会通过限制删除操作执行的时长和频率来减少删除操作对 CPU 时间的影响。

定期删除对内存更加友好，惰性删除对 CPU 更加友好。两者各有千秋，所以 Redis 采用的是 **定期删除+惰性/懒汉式删除** 。

但是，仅仅通过给 key 设置过期时间还是有问题的。因为还是可能存在定期删除和惰性删除漏掉了很多过期 key 的情况。这样就导致大量过期 key 堆积在内存里，然后就 Out of memory 了。

怎么解决这个问题呢？答案就是：**Redis 内存淘汰机制。**

##### Redis 内存淘汰机制了解么？

> 相关问题：MySQL 里有 2000w 数据，Redis 中只存 20w 的数据，如何保证 Redis 中的数据都是热点数据?

Redis 提供 6 种数据淘汰策略：

1. **volatile-lru（least recently used）**：从已设置过期时间的数据集（`server.db[i].expires`）中挑选最近最少使用的数据淘汰。
2. **volatile-ttl**：从已设置过期时间的数据集（`server.db[i].expires`）中挑选将要过期的数据淘汰。
3. **volatile-random**：从已设置过期时间的数据集（`server.db[i].expires`）中任意选择数据淘汰。
4. **allkeys-lru（least recently used）**：当内存不足以容纳新写入数据时，在键空间中，移除最近最少使用的 key（这个是最常用的）。
5. **allkeys-random**：从数据集（`server.db[i].dict`）中任意选择数据淘汰。
6. **no-eviction**：禁止驱逐数据，也就是说当内存不足以容纳新写入数据时，新写入操作会报错。这个应该没人使用吧！

4.0 版本后增加以下两种：

1. **volatile-lfu（least frequently used）**：从已设置过期时间的数据集（`server.db[i].expires`）中挑选最不经常使用的数据淘汰。
2. **allkeys-lfu（least frequently used）**：当内存不足以容纳新写入数据时，在键空间中，移除最不经常使用的 key。



#### 主从复制





#### 哨兵





#### 缓存雪崩/击穿/穿透

##### 什么是缓存穿透？

缓存穿透说简单点就是大量请求的 key 是不合理的，**根本不存在于缓存中，也不存在于数据库中** 。这就导致这些请求直接到了数据库上，根本没有经过缓存这一层，对数据库造成了巨大的压力，可能直接就被这么多请求弄宕机了。

##### 有哪些解决办法？

最基本的就是首先做好参数校验，一些不合法的参数请求直接抛出异常信息返回给客户端。比如查询的数据库 id 不能小于 0、传入的邮箱格式不对的时候直接返回错误消息给客户端等等。

**1）缓存无效 key**

如果缓存和数据库都查不到某个 key 的数据就写一个到 Redis 中去并设置过期时间，具体命令如下： `SET key value EX 10086` 。这种方式可以解决请求的 key 变化不频繁的情况，如果黑客恶意攻击，每次构建不同的请求 key，会导致 Redis 中缓存大量无效的 key 。很明显，这种方案并不能从根本上解决此问题。如果非要用这种方式来解决穿透问题的话，尽量将无效的 key 的过期时间设置短一点比如 1 分钟。

**2）布隆过滤器**

布隆过滤器是一个非常神奇的数据结构，通过它我们可以非常方便地判断一个给定数据是否存在于海量数据中。我们需要的就是判断 key 是否合法，有没有感觉布隆过滤器就是我们想要找的那个“人”。

具体是这样做的：把所有可能存在的请求的值都存放在布隆过滤器中，当用户请求过来，先判断用户发来的请求的值是否存在于布隆过滤器中。不存在的话，直接返回请求参数错误信息给客户端，存在的话才会走下面的流程。

但是，需要注意的是布隆过滤器可能会存在误判的情况。总结来说就是： **布隆过滤器说某个元素存在，小概率会误判。布隆过滤器说某个元素不在，那么这个元素一定不在。**

*为什么会出现误判的情况呢? 我们还要从布隆过滤器的原理来说！*

我们先来看一下，**当一个元素加入布隆过滤器中的时候，会进行哪些操作：**

1. 使用布隆过滤器中的哈希函数对元素值进行计算，得到哈希值（有几个哈希函数得到几个哈希值）。
2. 根据得到的哈希值，在位数组中把对应下标的值置为 1。

我们再来看一下，**当我们需要判断一个元素是否存在于布隆过滤器的时候，会进行哪些操作：**

1. 对给定元素再次进行相同的哈希计算；
2. 得到值之后判断位数组中的每个元素是否都为 1，如果值都为 1，那么说明这个值在布隆过滤器中，如果存在一个值不为 1，说明该元素不在布隆过滤器中。

然后，一定会出现这样一种情况：**不同的字符串可能哈希出来的位置相同。** （可以适当增加位数组大小或者调整我们的哈希函数来降低概率）

##### 什么是缓存击穿？

缓存击穿中，请求的 key 对应的是 **热点数据** ，该数据 **存在于数据库中，但不存在于缓存中（通常是因为缓存中的那份数据已经过期）** 。这就可能会导致瞬时大量的请求直接打到了数据库上，对数据库造成了巨大的压力，可能直接就被这么多请求弄宕机了。

##### 有哪些解决办法？

- 设置热点数据永不过期或者过期时间比较长。
- 针对热点数据提前预热，将其存入缓存中并设置合理的过期时间比如秒杀场景下的数据在秒杀结束之前不过期。
- 请求数据库写数据到缓存之前，先获取互斥锁，保证只有一个请求会落到数据库上，减少数据库的压力。

##### 缓存穿透和缓存击穿有什么区别？

缓存穿透中，请求的 key 既不存在于缓存中，也不存在于数据库中。

缓存击穿中，请求的 key 对应的是 **热点数据** ，该数据 **存在于数据库中，但不存在于缓存中（通常是因为缓存中的那份数据已经过期）** 。

##### 什么是缓存雪崩？

我发现缓存雪崩这名字起的有点意思，哈哈。

实际上，缓存雪崩描述的就是这样一个简单的场景：**缓存在同一时间大面积的失效，导致大量的请求都直接落到了数据库上，对数据库造成了巨大的压力。** 这就好比雪崩一样，摧枯拉朽之势，数据库的压力可想而知，可能直接就被这么多请求弄宕机了。

另外，缓存服务宕机也会导致缓存雪崩现象，导致所有的请求都落到了数据库上。

##### 有哪些解决办法？

**针对 Redis 服务不可用的情况：**

1. 采用 Redis 集群，避免单机出现问题整个缓存服务都没办法使用。
2. 限流，避免同时处理大量的请求。

**针对热点缓存失效的情况：**

1. 设置不同的失效时间比如随机设置缓存的失效时间。
2. 缓存永不失效（不太推荐，实用性太差）。
3. 设置二级缓存。

##### 缓存雪崩和缓存击穿有什么区别？

缓存雪崩和缓存击穿比较像，但缓存雪崩导致的原因是缓存中的大量或者所有数据失效，缓存击穿导致的原因主要是某个热点数据不存在与缓存中（通常是因为缓存中的那份数据已经过期）。









#### redis 6.0 多线程的实现机制：

https://blog.csdn.net/zhizhengguan/article/details/120627481





### ElasticSearch



## 六：消息中间件Kafka





### 数据保存的策略

kafka 有两种数据保存策略:

1、按照过期时间保留

2、按照存储的消息大小保留

Kafka Broker默认的消息保留策略是：要么保留一定时间，要么保留到消息达到一定大小的字节数。

当消息达到设置的条件上限时，旧消息就会过期并被删除，所以，在任何时刻，可用消息的总量都不会超过配置参数所指定的大小。

topic可以配置自己的保留策略，可以将消息保留到不再使用他们为止。

因为在一个大文件里查找和删除消息是很费时的事，也容易出错，所以，分区被划分为若干个片段。默认情况下，每个片段包含1G或者一周的数据，以较小的那个为准。在broker往leader分区写入消息时，如果达到片段上限，就关闭当前文件，并打开一个新文件。当前正在写入数据的片段叫活跃片段。当所有片段都被写满时，会清除下一个分区片段的数据，如果配置的是7个片段，每天打开一个新片段，就会删除一个最老的片段，循环使用所有片段。

kafka 同时设置了 7 天和 10G 清除数据，到第五天的时候消息达到了 10G，这个时候 kafka 将如何处理？
这个时候 kafka 会执行数据清除工作，时间和大小不论那个满足条件，都会清空数据。



### 分区策略

##### 生产者：

**为什么要分区**

1. 多Partition分布式存储，利于集群数据的均衡。
2. 并发读写，加快读写速度。
3. 加快数据恢复的速率：当某台机器挂了，每个Topic仅需恢复一部分的数据，多机器并发。

**分区的原则**

1. 指明partition的情况下，使用指定的partition；
2. 没有指明partition，但是有key的情况下，将key的hash值与topic的partition数进行取余得到partition值；
3. 既没有指定partition，也没有key的情况下，第一次调用时随机生成一个整数（后面每次调用在这个整数上自增），将这个值与topic可用的partition数取余得到partition值，也就是常说的round-robin算法。

```
public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
    List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
    int numPartitions = partitions.size();
    if (keyBytes == null) {
        //key为空时，获取一个自增的计数，然后对分区做取模得到分区编号
        int nextValue = nextValue(topic);
        List<PartitionInfo> availablePartitions = cluster.availablePartitionsForTopic(topic);
        if (availablePartitions.size() > 0) {
            int part = Utils.toPositive(nextValue) % availablePartitions.size();
            return availablePartitions.get(part).partition();
        } else {
            // no partitions are available, give a non-available partition
            return Utils.toPositive(nextValue) % numPartitions;
        }
    } else {
        // hash the keyBytes to choose a partition
        // key不为空时，通过key的hash对分区取模（疑问：为什么这里不像上面那样，使用availablePartitions呢？）
        // 根据《Kafka权威指南》Page45理解：为了保证相同的键，总是能路由到固定的分区，如果使用可用分区，那么因为分区数变化，会导致相同的key，路由到不同分区
        // 所以如果要使用key来映射分区，最好在创建主题的时候就把分区规划好
        return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;
    }
}
 
private int nextValue(String topic) {
    //为每个topic维护了一个AtomicInteger对象，每次获取时+1
    AtomicInteger counter = topicCounterMap.get(topic);
    if (null == counter) {
        counter = new AtomicInteger(ThreadLocalRandom.current().nextInt());
        AtomicInteger currentCounter = topicCounterMap.putIfAbsent(topic, counter);
        if (currentCounter != null) {
            counter = currentCounter;
        }
    }
    return counter.getAndIncrement();
}
```



##### 消费者：

**分区分配策略**

一个consumer  group中有多个consumer，一个topic有多个partition，所以必然会涉及到partition的分配问题，即确定哪个partition由哪个consumer来消费。Kafka提供了3种消费者分区分配策略：RangeAssigor、RoundRobinAssignor、StickyAssignor。

 PartitionAssignor接口用于用户定义实现分区分配算法，以实现Consumer之间的分区分配。消费组的成员订阅它们感兴趣的Topic并将这种订阅关系传递给作为订阅组协调者的Broker。协调者选择其中的一个消费者来执行这个消费组的分区分配并将分配结果转发给消费组内所有的消费者。**Kafka默认采用RangeAssignor的分配算法。**

**RangeAssignor**

 RangeAssignor对每个Topic进行独立的分区分配。对于每一个Topic，首先对分区按照分区ID进行排序，然后订阅这个Topic的消费组的消费者再进行排序，之后尽量均衡的将分区分配给消费者。这里只能是尽量均衡，因为分区数可能无法被消费者数量整除，那么有一些消费者就会多分配到一些分区。分配示意图如下：

![f4108e1816b3087f38b546372e214958.png](https://img-blog.csdnimg.cn/img_convert/f4108e1816b3087f38b546372e214958.png)

分区分配的算法如下：

```
@Override
public Map<String, List<TopicPartition>> assign(Map<String, Integer> partitionsPerTopic,
                                                Map<String, Subscription> subscriptions) {
    Map<String, List<String>> consumersPerTopic = consumersPerTopic(subscriptions);
    Map<String, List<TopicPartition>> assignment = new HashMap<>();
    for (String memberId : subscriptions.keySet())
        assignment.put(memberId, new ArrayList<TopicPartition>());
    //for循环对订阅的多个topic分别进行处理
    for (Map.Entry<String, List<String>> topicEntry : consumersPerTopic.entrySet()) {
        String topic = topicEntry.getKey();
        List<String> consumersForTopic = topicEntry.getValue();
 
        Integer numPartitionsForTopic = partitionsPerTopic.get(topic);
        if (numPartitionsForTopic == null)
            continue;
        //对消费者进行排序
        Collections.sort(consumersForTopic);
        //计算平均每个消费者分配的分区数
        int numPartitionsPerConsumer = numPartitionsForTopic / consumersForTopic.size();
        //计算平均分配后多出的分区数
        int consumersWithExtraPartition = numPartitionsForTopic % consumersForTopic.size();
 
        List<TopicPartition> partitions = AbstractPartitionAssignor.partitions(topic, numPartitionsForTopic);
        for (int i = 0, n = consumersForTopic.size(); i < n; i++) {
            //计算第i个消费者，分配分区的起始位置
            int start = numPartitionsPerConsumer * i + Math.min(i, consumersWithExtraPartition);
            //计算第i个消费者，分配到的分区数量
            int length = numPartitionsPerConsumer + (i + 1 > consumersWithExtraPartition ? 0 : 1);
            assignment.get(consumersForTopic.get(i)).addAll(partitions.subList(start, start + length));
        }
    }
    return assignment;
}
```



这种分配方式明显的一个问题是随着消费者订阅的Topic的数量的增加，不均衡的问题会越来越严重，比如上图中4个分区3个消费者的场景，C0会多分配一个分区。如果此时再订阅一个分区数为4的Topic，那么C0又会比C1、C2多分配一个分区，这样C0总共就比C1、C2多分配两个分区了，而且随着Topic的增加，这个情况会越来越严重。分配结果：

![eff9adb5a086691e56b5d68bec68ffcf.png](https://img-blog.csdnimg.cn/img_convert/eff9adb5a086691e56b5d68bec68ffcf.png)



订阅2个Topic，每个Topic4个分区，共3个Consumer

- **C0：**[T0P0，T0P1，T1P0，T1P1]
- **C1：**[T0P2，T1P2]
- **C2：**[T0P3，T1P3]



**RoundRobinAssignor**

RoundRobinAssignor的分配策略是将消费组内订阅的所有Topic的分区及所有消费者进行排序后尽量均衡的分配（RangeAssignor是针对单个Topic的分区进行排序分配的）。如果消费组内，消费者订阅的Topic列表是相同的（每个消费者都订阅了相同的Topic），那么分配结果是尽量均衡的（消费者之间分配到的分区数的差值不会超过1）。如果订阅的Topic列表是不同的，那么分配结果是不保证“尽量均衡”的，因为某些消费者不参与一些Topic的分配。

![51b27d00cf50d9aca86e0934ab42a565.png](https://img-blog.csdnimg.cn/img_convert/51b27d00cf50d9aca86e0934ab42a565.png)



以上两个topic的情况，相比于之前RangeAssignor的分配策略，可以使分区分配的更均衡。不过考虑这种情况，假设有三个消费者分别为C0、C1、C2，有3个Topic  T0、T1、T2，分别拥有1、2、3个分区，并且C0订阅T0，C1订阅T0和T1，C2订阅T0、T1、T2，那么RoundRobinAssignor的分配结果如下：

![4e161a06a0afcae8d2c06603d676de4e.png](https://img-blog.csdnimg.cn/img_convert/4e161a06a0afcae8d2c06603d676de4e.png)



看上去分配已经尽量的保证均衡了，不过可以发现C2承担了4个分区的消费而C1订阅了T1，是不是把T1P1交给C1消费能更加的均衡呢？

 **StickyAssignor**

StickyAssignor分区分配算法，目的是在执行一次新的分配时，能在上一次分配的结果的基础上，尽量少的调整分区分配的变动，节省因分区分配变化带来的开销。Sticky是“粘性的”，可以理解为分配结果是带“粘性的”——每一次分配变更相对上一次分配做最少的变动。其目标有两点：

- 分区的分配尽量的均衡。
- 每一次重分配的结果尽量与上一次分配结果保持一致。

当这两个目标发生冲突时，优先保证第一个目标。第一个目标是每个分配算法都尽量尝试去完成的，而第二个目标才真正体现出StickyAssignor特性的。

StickyAssignor算法比较复杂，下面举例来说明分配的效果（对比RoundRobinAssignor），前提条件：

- 有4个Topic：T0、T1、T2、T3，每个Topic有2个分区。
- 有3个Consumer：C0、C1、C2，所有Consumer都订阅了这4个分区。



![eb5597ed0b81b03c762e54ff3f909492.png](https://img-blog.csdnimg.cn/img_convert/eb5597ed0b81b03c762e54ff3f909492.png)



上面红色的箭头代表的是有变动的分区分配，可以看出，StickyAssignor的分配策略，变动较小。



参考：https://blog.csdn.net/easylife206/article/details/124580641?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-4-124580641-blog-124349832.235^v28^pc_relevant_t0_download&spm=1001.2101.3001.4242.3&utm_relevant_index=7



### kafka如何保证消息不被重复消费

#### 原因

（1）kafka有个offset的概念，当每个消息被写进去后，都有一个offset，代表他的序号，然后consumer消费该数据之后，隔一段时间，会把自己消费过的消息的offset提交一下，代表我已经消费过了。下次我要是重启，就会继续从上次消费到的offset来继续消费。但是当我们直接kill进程了，再重启。这会导致consumer有些消息处理了，但是没来得及提交offset。等重启之后，少数消息就会再次消费一次
（2）在Kafka中有一个Partition Balance机制，就是把多个Partition均衡的分配给多个消费者。消费端会从分配到的Partition里面去消费消息，如果消费者在默认的5分钟内没有处理完这一批消息。就会触发Kafka的Rebalance机制，从而导致offset自动提交失败。而Rebalance之后，消费者还是会从之前没提交的offset位置开始消费，从而导致消息重复消费。

#### 解决方案

- **开启kafka本身存在的幂等性：**

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/afb50c35d4dd4b14bd8f30929a494228.png)

  注： 添加唯一ID，类似于数据库的主键，用于唯一标记一个消息。
   ProducerID：#在每个新的Producer初始化时，会被分配一个唯一的PID
   SequenceNumber：#对于每个PID发送数据的每个Topic都对应一个从0开始单调递增的SN值。

- **将获取的唯一id存表，（利用mySQl的唯一键约束，或者redis天然的set结构）**

参考：https://blog.csdn.net/m0_51167384/article/details/128106266?spm=1001.2101.3001.6650.7&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-7-128106266-blog-76435385.235%5Ev28%5Epc_relevant_t0_download&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-7-128106266-blog-76435385.235%5Ev28%5Epc_relevant_t0_download&utm_relevant_index=8&ydreferer=aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTA2Mjc4NDAvYXJ0aWNsZS9kZXRhaWxzLzc2NDM1Mzg1P3NwbT0xMDAxLjIxMDEuMzAwMS42NjUwLjEmdXRtX21lZGl1bT1kaXN0cmlidXRlLnBjX3JlbGV2YW50Lm5vbmUtdGFzay1ibG9nLTIlN0VkZWZhdWx0JTdFQ1RSTElTVCU3RVJhdGUtMS03NjQzNTM4NS1ibG9nLTExNzQxOTA1OC4yMzUlNUV2MjglNUVwY19yZWxldmFudF90MF9kb3dubG9hZCZkZXB0aF8xLXV0bV9zb3VyY2U9ZGlzdHJpYnV0ZS5wY19yZWxldmFudC5ub25lLXRhc2stYmxvZy0yJTdFZGVmYXVsdCU3RUNUUkxJU1QlN0VSYXRlLTEtNzY0MzUzODUtYmxvZy0xMTc0MTkwNTguMjM1JTVFdjI4JTVFcGNfcmVsZXZhbnRfdDBfZG93bmxvYWQmdXRtX3JlbGV2YW50X2luZGV4PTI%3D



### 如何保证消息的顺序性?

#### 为什么要保证顺序？

消息队列中的若干消息如果是对同一个数据进行操作, 这些操作具有前后关系, 必须要按前后的顺序执行, 否则就会造成数据异常.

#### 出现顺序错乱的场景：

    第一种情况:
    一个queue, 有多个consumer去消费, 这样就会造成顺序的错误, consumer从MQ里面读取数据是有序的, 但是每个consumer的执行时间是不固定的, 无法保证先读到消息的consumer一定先完成操作, 这样就会出现消息并没有按照顺序执行, 造成数据顺序错误。

![img](https://img-blog.csdnimg.cn/cfe17769c1c949a4b6eced9fe3d68491.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Y-v54ix6L-35Lq6,size_20,color_FFFFFF,t_70,g_se,x_16)


```
第二种情况:

一个queue对应一个consumer, 但是consumer里面进行了多线程消费, 这样也会造成消息消费顺序错误。
```

![img](https://img-blog.csdnimg.cn/3fed5f0626084f83ae5aea89963d5fc6.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Y-v54ix6L-35Lq6,size_20,color_FFFFFF,t_70,g_se,x_16)

#### 如何保证消息的消费顺序？

```
第一种方案:

拆分多个queue, 每一个queue一个consumer, 就是多一些queue而已, 确实是麻烦点; 这样也会造成吞吐量下降, 可以在消费者内部采用多线程的方式去消费。
```

![img](https://img-blog.csdnimg.cn/f1e9445d6c5d411b9b9b385c70797fcc.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Y-v54ix6L-35Lq6,size_20,color_FFFFFF,t_70,g_se,x_16)



```
第二种方案:

就是一个queue对应一个consumer, 然后这个consumer内部用内存队列做排队, 然后分发给底层不同的worker来处理。
```

![img](https://img-blog.csdnimg.cn/227a2d3b375e47e0aa171bd184ba7af1.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Y-v54ix6L-35Lq6,size_20,color_FFFFFF,t_70,g_se,x_16)



参考：https://blog.csdn.net/qq_44901983/article/details/123416498



## 七：操作系统

### 进程和线程管理

#### Java里的进程有哪些状态？

- 新建状态(New)：新创建了一个线程对象。

- 就绪状态(Runnable)：线程对象创建后，其他线程调用了该对象的start()方法。该状态的线程位于“可运行线程池”中，变得可运行，只等待获取CPU的使用权。即在就绪状态的进程除CPU之外，其它的运行所需资源都已全部获得。

- 运行状态(Running)：就绪状态的线程获取了CPU，执行程序代码。

- 阻塞状态(Blocked)：阻塞状态是线程因为某种原因放弃CPU使用权，暂时停止运行。直到线程进入就绪状态，才有机会转到运行状态。阻塞的情况分三种：

-     等待阻塞：运行的线程执行wait()方法，该线程会释放占用的所有资源，JVM会把该线程放入“等待池”中。进入这个状态后，是不能自动唤醒的，必须依靠其他线程调用notify()或notifyAll()方法才能被唤醒，
      同步阻塞：运行的线程在获取对象的同步锁时，若该同步锁被别的线程占用，则JVM会把该线程放入“锁池”中。
      其他阻塞：运行的线程执行sleep()或join()方法，或者发出了I/O请求时，JVM会把该线程置为阻塞状态。当sleep()状态超时、join()等待线程终止或者超时、或者I/O处理完毕时，线程重新转入就绪状态。

- 终止状态(Dead)：线程执行完了或者因异常退出了run()方法，该线程结束生命周期。
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/ff58fec643d74a3d974051b8538cd15b.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAUmVkaHVyLQ==,size_20,color_FFFFFF,t_70,g_se,x_16)









### 进程间通信



### 互斥同步

线程同步是两个或多个共享关键资源的线程的并发执行。应该同步线程以避免关键的资源使用冲突。

下面是几种常见的线程同步的方式：

1. **互斥锁(Mutex)** ：采用互斥对象机制，只有拥有互斥对象的线程才有访问公共资源的权限。因为互斥对象只有一个，所以可以保证公共资源不会被多个线程同时访问。比如 Java 中的 `synchronized` 关键词和各种 `Lock` 都是这种机制。
2. **读写锁（Read-Write Lock）**：允许多个线程同时读取共享资源，但只有一个线程可以对共享资源进行写操作。
3. **信号量(Semaphore)** ：它允许同一时刻多个线程访问同一资源，但是需要控制同一时刻访问此资源的最大线程数量。
4. **屏障（Barrier）** ：屏障是一种同步原语，用于等待多个线程到达某个点再一起继续执行。当一个线程到达屏障时，它会停止执行并等待其他线程到达屏障，直到所有线程都到达屏障后，它们才会一起继续执行。比如 Java 中的 `CyclicBarrier` 是这种机制。
5. **事件(Event)** :Wait/Notify：通过通知操作的方式来保持多线程同步，还可以方便的实现多线程优先级的比较操作。



在进程/线程并发执行的过程中，进程/线程之间存在协作的关系，例如有互斥、同步的关系。

为了实现进程/线程间正确的协作，操作系统必须提供实现进程协作的措施和方法，主要的方法有两种：

- *锁*：加锁、解锁操作；
- *信号量*：P、V 操作；

这两个都可以方便地实现进程/线程互斥，而信号量比锁的功能更强一些，它还可以方便地实现进程/线程同步。



### 进程间通信

- **管道/匿名管道(Pipes)** ：用于具有亲缘关系的父子进程间或者兄弟进程之间的通信。
- **有名管道(Named Pipes)** : 匿名管道由于没有名字，只能用于亲缘关系的进程间通信。为了克服这个缺点，提出了有名管道。有名管道严格遵循 **先进先出(First In First Out)** 。有名管道以磁盘文件的方式存在，可以实现本机任意两个进程通信。
- **信号(Signal)** ：信号是一种比较复杂的通信方式，用于通知接收进程某个事件已经发生；
- **消息队列(Message Queuing)** ：消息队列是消息的链表,具有特定的格式,存放在内存中并由消息队列标识符标识。管道和消息队列的通信数据都是先进先出的原则。与管道（无名管道：只存在于内存中的文件；命名管道：存在于实际的磁盘介质或者文件系统）不同的是消息队列存放在内核中，只有在内核重启(即，操作系统重启)或者显式地删除一个消息队列时，该消息队列才会被真正的删除。消息队列可以实现消息的随机查询,消息不一定要以先进先出的次序读取,也可以按消息的类型读取.比 FIFO 更有优势。**消息队列克服了信号承载信息量少，管道只能承载无格式字 节流以及缓冲区大小受限等缺点。**
- **信号量(Semaphores)** ：信号量是一个计数器，用于多进程对共享数据的访问，信号量的意图在于进程间同步。这种通信方式主要用于解决与同步相关的问题并避免竞争条件。
- **共享内存(Shared memory)** ：使得多个进程可以访问同一块内存空间，不同进程可以及时看到对方进程中对共享内存中数据的更新。这种方式需要依靠某种同步操作，如互斥锁和信号量等。可以说这是最有用的进程间通信方式。
- **套接字(Sockets)** : 此方法主要用于在客户端和服务器之间通过网络进行通信。套接字是支持 TCP/IP 的网络通信的基本操作单元，可以看做是不同主机之间的进程进行双向通信的端点，简单的说就是通信的两方的一种约定，用套接字中的相关函数来完成通信过程。



### 虚拟内存管理







###  I/O 多路复用

既然为每个请求分配一个进程/线程的方式不合适，那有没有可能只使用一个进程来维护多个 Socket 呢？答案是有的，那就是 **I/O 多路复用**技术。

一个进程虽然任一时刻只能处理一个请求，但是处理每个请求的事件时，耗时控制在 1 毫秒以内，这样 1 秒内就可以处理上千个请求，把时间拉长来看，多个请求复用了一个进程，这就是多路复用，这种思想很类似一个 CPU 并发多个进程，所以也叫做时分多路复用。

我们熟悉的 select/poll/epoll 内核提供给用户态的多路复用系统调用，**进程可以通过一个系统调用函数从内核中获取多个事件**。

select/poll/epoll 是如何获取网络事件的呢？在获取事件时，先把所有连接（文件描述符）传给内核，再由内核返回产生了事件的连接，然后在用户态中再处理这些连接对应的请求即可。

##### epoll

epoll 通过两个方面，很好解决了 select/poll 的问题。

*第一点*，epoll 在内核里使用**红黑树来跟踪进程所有待检测的文件描述字**，把需要监控的 socket 通过 `epoll_ctl()` 函数加入内核中的红黑树里，红黑树是个高效的数据结构，增删改一般时间复杂度是 `O(logn)`。而 select/poll 内核里没有类似 epoll 红黑树这种保存所有待检测的 socket 的数据结构，所以 select/poll  每次操作时都传入整个 socket 集合给内核，而 epoll 因为在内核维护了红黑树，可以保存所有待检测的 socket  ，所以只需要传入一个待检测的 socket，减少了内核和用户空间大量的数据拷贝和内存分配。

*第二点*， epoll 使用**事件驱动**的机制，内核里**维护了一个链表来记录就绪事件**，当某个 socket 有事件发生时，通过**回调函数**内核会将其加入到这个就绪事件列表中，当用户调用 `epoll_wait()` 函数时，只会返回有事件发生的文件描述符的个数，不需要像 select/poll 那样轮询扫描整个 socket 集合，大大提高了检测的效率。

从下图你可以看到 epoll 相关的接口作用：

![img](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost4@main/%E6%93%8D%E4%BD%9C%E7%B3%BB%E7%BB%9F/%E5%A4%9A%E8%B7%AF%E5%A4%8D%E7%94%A8/epoll.png)



epoll 的方式即使监听的 Socket 数量越多的时候，效率不会大幅度降低，能够同时监听的 Socket 的数目也非常的多了，上限就为系统定义的进程打开的最大文件描述符个数。因而，**epoll 被称为解决 C10K 问题的利器**。

插个题外话，网上文章不少说，`epoll_wait` 返回时，对于就绪的事件，epoll 使用的是共享内存的方式，即用户态和内核态都指向了就绪链表，所以就避免了内存拷贝消耗。

这是错的！看过 epoll 内核源码的都知道，**压根就没有使用共享内存这个玩意**。

epoll 支持两种事件触发模式，分别是**边缘触发（\*edge-triggered，ET\*）\**和\**水平触发（\*level-triggered，LT\*）**。

这两个术语还挺抽象的，其实它们的区别还是很好理解的。

- 使用边缘触发模式时，当被监控的 Socket 描述符上有可读事件发生时，**服务器端只会从 epoll_wait 中苏醒一次**，即使进程没有调用 read 函数从内核读取数据，也依然只苏醒一次，因此我们程序要保证一次性将内核缓冲区的数据读取完；
- 使用水平触发模式时，当被监控的 Socket 上有可读事件发生时，**服务器端不断地从 epoll_wait 中苏醒，直到内核缓冲区数据被 read 函数读完才结束**，目的是告诉我们有数据需要读取；

举个例子，你的快递被放到了一个快递箱里，如果快递箱只会通过短信通知你一次，即使你一直没有去取，它也不会再发送第二条短信提醒你，这个方式就是边缘触发；如果快递箱发现你的快递没有被取出，它就会不停地发短信通知你，直到你取出了快递，它才消停，这个就是水平触发的方式。

这就是两者的区别，水平触发的意思是只要满足事件的条件，比如内核中有数据需要读，就一直不断地把这个事件传递给用户；而边缘触发的意思是只有第一次满足条件的时候才触发，之后就不会再传递同样的事件了。

如果使用水平触发模式，当内核通知文件描述符可读写时，接下来还可以继续去检测它的状态，看它是否依然可读或可写。所以在收到通知后，没必要一次执行尽可能多的读写操作。

如果使用边缘触发模式，I/O 事件发生时只会通知一次，而且我们不知道到底能读写多少数据，所以在收到通知后应尽可能地读写数据，以免错失读写的机会。因此，我们会**循环**从文件描述符读写数据，那么如果文件描述符是阻塞的，没有数据可读写时，进程会阻塞在读写函数那里，程序就没办法继续往下执行。所以，**边缘触发模式一般和非阻塞 I/O 搭配使用**，程序会一直执行 I/O 操作，直到系统调用（如 `read` 和 `write`）返回错误，错误类型为 `EAGAIN` 或 `EWOULDBLOCK`。

一般来说，边缘触发的效率比水平触发的效率要高，因为边缘触发可以减少 epoll_wait 的系统调用次数，系统调用也是有一定的开销的的，毕竟也存在上下文的切换。

select/poll 只有水平触发模式，epoll 默认的触发模式是水平触发，但是可以根据应用场景设置为边缘触发模式。

C10K ：并发 1 万请求，也就是经典的 C10K 问题 ，C 是 Client 单词首字母缩写，C10K 就是单机同时处理 1 万个请求的问题。

参考：https://www.xiaolincoding.com/os/8_network_system/selete_poll_epoll.html#%E5%A6%82%E4%BD%95%E6%9C%8D%E5%8A%A1%E6%9B%B4%E5%A4%9A%E7%9A%84%E7%94%A8%E6%88%B7



### 零拷贝

零拷贝技术实现的方式通常有 2 种：

- mmap + write
- sendfile

下面就谈一谈，它们是如何减少「上下文切换」和「数据拷贝」的次数。

#### mmap + write

在前面我们知道，`read()` 系统调用的过程中会把内核缓冲区的数据拷贝到用户的缓冲区里，于是为了减少这一步开销，我们可以用 `mmap()` 替换 `read()` 系统调用函数。

```
buf = mmap(file, len);
write(sockfd, buf, len);
```

`mmap()` 系统调用函数会直接把内核缓冲区里的数据「**映射**」到用户空间，这样，操作系统内核与用户空间就不需要再进行任何的数据拷贝操作。

![img](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E6%93%8D%E4%BD%9C%E7%B3%BB%E7%BB%9F/%E9%9B%B6%E6%8B%B7%E8%B4%9D/mmap%20%2B%20write%20%E9%9B%B6%E6%8B%B7%E8%B4%9D.png)



具体过程如下：

- 应用进程调用了 `mmap()` 后，DMA 会把磁盘的数据拷贝到内核的缓冲区里。接着，应用进程跟操作系统内核「共享」这个缓冲区；
- 应用进程再调用 `write()`，操作系统直接将内核缓冲区的数据拷贝到 socket 缓冲区中，这一切都发生在内核态，由 CPU 来搬运数据；
- 最后，把内核的 socket 缓冲区里的数据，拷贝到网卡的缓冲区里，这个过程是由 DMA 搬运的。

我们可以得知，通过使用 `mmap()` 来代替 `read()`， 可以减少一次数据拷贝的过程。

但这还不是最理想的零拷贝，因为仍然需要通过 CPU 把内核缓冲区的数据拷贝到 socket 缓冲区里，而且仍然需要 4 次上下文切换，因为系统调用还是 2 次。



#### sendfile

在 Linux 内核版本 2.1 中，提供了一个专门发送文件的系统调用函数 `sendfile()`，函数形式如下：

```
#include <sys/socket.h>
ssize_t sendfile(int out_fd, int in_fd, off_t *offset, size_t count);

```

它的前两个参数分别是目的端和源端的文件描述符，后面两个参数是源端的偏移量和复制数据的长度，返回值是实际复制数据的长度。

首先，它可以替代前面的 `read()` 和 `write()` 这两个系统调用，这样就可以减少一次系统调用，也就减少了 2 次上下文切换的开销。

其次，该系统调用，可以直接把内核缓冲区里的数据拷贝到 socket 缓冲区里，不再拷贝到用户态，这样就只有 2 次上下文切换，和 3 次数据拷贝。如下图：

![img](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E6%93%8D%E4%BD%9C%E7%B3%BB%E7%BB%9F/%E9%9B%B6%E6%8B%B7%E8%B4%9D/senfile-3%E6%AC%A1%E6%8B%B7%E8%B4%9D.png)



但是这还不是真正的零拷贝技术，如果网卡支持 SG-DMA（*The Scatter-Gather Direct Memory Access*）技术（和普通的 DMA 有所不同），我们可以进一步减少通过 CPU 把内核缓冲区里的数据拷贝到 socket 缓冲区的过程。

于是，从 Linux 内核 `2.4` 版本开始起，对于支持网卡支持 SG-DMA 技术的情况下， `sendfile()` 系统调用的过程发生了点变化，具体过程如下：

- 第一步，通过 DMA 将磁盘上的数据拷贝到内核缓冲区里；
- 第二步，缓冲区描述符和数据长度传到 socket 缓冲区，这样网卡的 SG-DMA 控制器就可以直接将内核缓存中的数据拷贝到网卡的缓冲区里，此过程不需要将数据从操作系统内核缓冲区拷贝到 socket 缓冲区中，这样就减少了一次数据拷贝；

所以，这个过程之中，只进行了 2 次数据拷贝，如下图：

![img](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E6%93%8D%E4%BD%9C%E7%B3%BB%E7%BB%9F/%E9%9B%B6%E6%8B%B7%E8%B4%9D/senfile-%E9%9B%B6%E6%8B%B7%E8%B4%9D.png)



这就是所谓的**零拷贝（\*Zero-copy\*）技术，因为我们没有在内存层面去拷贝数据，也就是说全程没有通过 CPU 来搬运数据，所有的数据都是通过 DMA 来进行传输的。**。

零拷贝技术的文件传输方式相比传统文件传输的方式，减少了 2 次上下文切换和数据拷贝次数，**只需要 2 次上下文切换和数据拷贝次数，就可以完成文件的传输，而且 2 次的数据拷贝过程，都不需要通过 CPU，2 次都是由 DMA 来搬运。**

所以，总体来看，**零拷贝技术可以把文件传输的性能提高至少一倍以上**。

#### 使用零拷贝技术的项目

事实上，Kafka 这个开源项目，就利用了「零拷贝」技术，从而大幅提升了 I/O 的吞吐率，这也是 Kafka 在处理海量数据为什么这么快的原因之一。

如果你追溯 Kafka 文件传输的代码，你会发现，最终它调用了 Java NIO 库里的 `transferTo` 方法。

如果 Linux 系统支持 `sendfile()` 系统调用，那么 `transferTo()` 实际上最后就会使用到 `sendfile()` 系统调用函数。



### Reactor

Reactor 模式主要由 Reactor 和处理资源池这两个核心部分组成，它俩负责的事情如下：

- Reactor 负责监听和分发事件，事件类型包含连接事件、读写事件；
- 处理资源池负责处理事件，如 read -> 业务逻辑 -> send；

Reactor 模式是灵活多变的，可以应对不同的业务场景，灵活在于：

- Reactor 的数量可以只有一个，也可以有多个；
- 处理资源池可以是单个进程 / 线程，也可以是多个进程 /线程；

将上面的两个因素排列组设一下，理论上就可以有 4 种方案选择：

- 单 Reactor 单进程 / 线程；
- 单 Reactor 多进程 / 线程；
- 多 Reactor 单进程 / 线程；
- 多 Reactor 多进程 / 线程；

其中，「多 Reactor 单进程 / 线程」实现方案相比「单 Reactor 单进程 / 线程」方案，不仅复杂而且也没有性能优势，因此实际中并没有应用。

剩下的 3 个方案都是比较经典的，且都有应用在实际的项目中：

- 单 Reactor 单进程 / 线程；
- 单 Reactor 多线程 / 进程；
- 多 Reactor 多进程 / 线程；

方案具体使用进程还是线程，要看使用的编程语言以及平台有关：

- Java 语言一般使用线程，比如 Netty;
- C 语言使用进程和线程都可以，例如 Nginx 使用的是进程，Memcache 使用的是线程。

接下来，分别介绍这三个经典的 Reactor 方案。





#### 单 Reactor 单进程 / 线程

一般来说，C 语言实现的是「**单 Reactor 单进程**」的方案，因为 C 语编写完的程序，运行后就是一个独立的进程，不需要在进程中再创建线程。

而 Java 语言实现的是「**单 Reactor 单线程**」的方案，因为 Java 程序是跑在 Java 虚拟机这个进程上面的，虚拟机中有很多线程，我们写的 Java 程序只是其中的一个线程而已。

我们来看看「**单 Reactor 单进程**」的方案示意图：

![img](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost4@main/%E6%93%8D%E4%BD%9C%E7%B3%BB%E7%BB%9F/Reactor/%E5%8D%95Reactor%E5%8D%95%E8%BF%9B%E7%A8%8B.png)

可以看到进程里有 **Reactor、Acceptor、Handler** 这三个对象：

- Reactor 对象的作用是监听和分发事件；
- Acceptor 对象的作用是获取连接；
- Handler 对象的作用是处理业务；

对象里的 select、accept、read、send 是系统调用函数，dispatch 和 「业务处理」是需要完成的操作，其中 dispatch 是分发事件操作。

接下来，介绍下「单 Reactor 单进程」这个方案：

- Reactor 对象通过 select （IO 多路复用接口） 监听事件，收到事件后通过 dispatch 进行分发，具体分发给 Acceptor 对象还是 Handler 对象，还要看收到的事件类型；
- 如果是连接建立的事件，则交由 Acceptor 对象进行处理，Acceptor 对象会通过 accept 方法 获取连接，并创建一个 Handler 对象来处理后续的响应事件；
- 如果不是连接建立事件， 则交由当前连接对应的 Handler 对象来进行响应；
- Handler 对象通过 read -> 业务处理 -> send 的流程来完成完整的业务流程。

单 Reactor 单进程的方案因为全部工作都在同一个进程内完成，所以实现起来比较简单，不需要考虑进程间通信，也不用担心多进程竞争。

但是，这种方案存在 2 个缺点：

- 第一个缺点，因为只有一个进程，**无法充分利用 多核 CPU 的性能**；
- 第二个缺点，Handler 对象在业务处理时，整个进程是无法处理其他连接的事件的，**如果业务处理耗时比较长，那么就造成响应的延迟**；

所以，单 Reactor 单进程的方案**不适用计算机密集型的场景，只适用于业务处理非常快速的场景**。

Redis 是由 C 语言实现的，在 Redis 6.0 版本之前采用的正是「单 Reactor 单进程」的方案，因为 Redis 业务处理主要是在内存中完成，操作的速度是很快的，性能瓶颈不在 CPU 上，所以 Redis 对于命令的处理是单进程的方案。



#### 单 Reactor 多线程 / 多进程

如果要克服「单 Reactor 单线程 / 进程」方案的缺点，那么就需要引入多线程 / 多进程，这样就产生了**单 Reactor 多线程 / 多进程**的方案。

闻其名不如看其图，先来看看「单 Reactor 多线程」方案的示意图如下：

![img](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost4@main/%E6%93%8D%E4%BD%9C%E7%B3%BB%E7%BB%9F/Reactor/%E5%8D%95Reactor%E5%A4%9A%E7%BA%BF%E7%A8%8B.png)



详细说一下这个方案：

- Reactor 对象通过 select （IO 多路复用接口） 监听事件，收到事件后通过 dispatch 进行分发，具体分发给 Acceptor 对象还是 Handler 对象，还要看收到的事件类型；
- 如果是连接建立的事件，则交由 Acceptor 对象进行处理，Acceptor 对象会通过 accept 方法 获取连接，并创建一个 Handler 对象来处理后续的响应事件；
- 如果不是连接建立事件， 则交由当前连接对应的 Handler 对象来进行响应；

上面的三个步骤和单 Reactor 单线程方案是一样的，接下来的步骤就开始不一样了：

- Handler 对象不再负责业务处理，只负责数据的接收和发送，Handler 对象通过 read 读取到数据后，会将数据发给子线程里的 Processor 对象进行业务处理；
- 子线程里的 Processor 对象就进行业务处理，处理完后，将结果发给主线程中的 Handler 对象，接着由 Handler 通过 send 方法将响应结果发送给 client；

单 Reator 多线程的方案优势在于**能够充分利用多核 CPU 的能力**，那既然引入多线程，那么自然就带来了多线程竞争资源的问题。

例如，子线程完成业务处理后，要把结果传递给主线程的 Handler 进行发送，这里涉及共享数据的竞争。

要避免多线程由于竞争共享资源而导致数据错乱的问题，就需要在操作共享资源前加上互斥锁，以保证任意时间里只有一个线程在操作共享资源，待该线程操作完释放互斥锁后，其他线程才有机会操作共享数据。

聊完单 Reactor 多线程的方案，接着来看看单 Reactor 多进程的方案。

事实上，单 Reactor 多进程相比单 Reactor 多线程实现起来很麻烦，主要因为要考虑子进程 <-> 父进程的双向通信，并且父进程还得知道子进程要将数据发送给哪个客户端。

而多线程间可以共享数据，虽然要额外考虑并发问题，但是这远比进程间通信的复杂度低得多，因此实际应用中也看不到单 Reactor 多进程的模式。

另外，「单 Reactor」的模式还有个问题，**因为一个 Reactor 对象承担所有事件的监听和响应，而且只在主线程中运行，在面对瞬间高并发的场景时，容易成为性能的瓶颈的地方**。

#### 多 Reactor 多进程 / 线程

要解决「单 Reactor」的问题，就是将「单 Reactor」实现成「多 Reactor」，这样就产生了第 **多 Reactor 多进程 / 线程**的方案。

老规矩，闻其名不如看其图。多 Reactor 多进程 / 线程方案的示意图如下（以线程为例）：

![img](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost4@main/%E6%93%8D%E4%BD%9C%E7%B3%BB%E7%BB%9F/Reactor/%E4%B8%BB%E4%BB%8EReactor%E5%A4%9A%E7%BA%BF%E7%A8%8B.png)

方案详细说明如下：

- 主线程中的 MainReactor 对象通过 select 监控连接建立事件，收到事件后通过 Acceptor 对象中的 accept  获取连接，将新的连接分配给某个子线程；
- 子线程中的 SubReactor 对象将 MainReactor 对象分配的连接加入 select 继续进行监听，并创建一个 Handler 用于处理连接的响应事件。
- 如果有新的事件发生时，SubReactor 对象会调用当前连接对应的 Handler 对象来进行响应。
- Handler 对象通过 read -> 业务处理 -> send 的流程来完成完整的业务流程。

多 Reactor 多线程的方案虽然看起来复杂的，但是实际实现时比单 Reactor 多线程的方案要简单的多，原因如下：

- 主线程和子线程分工明确，主线程只负责接收新连接，子线程负责完成后续的业务处理。
- 主线程和子线程的交互很简单，主线程只需要把新连接传给子线程，子线程无须返回数据，直接就可以在子线程将处理结果发送给客户端。

参考：https://www.xiaolincoding.com/os/8_network_system/reactor.html#%E6%BC%94%E8%BF%9B





## 八：计算机网络



### 键入网址到网页显示，期间发生了什么？

![img](https://oss.javaguide.cn/github/javaguide/url输入到展示出来的过程.jpg)

> 上图有一个错误，请注意，是 OSPF 不是 OPSF。 OSPF（Open Shortest Path First，ospf）开放最短路径优先协议, 是由 Internet 工程任务组开发的路由选择协议

总体来说分为以下几个过程:

1. DNS 解析
2. TCP 连接
3. 发送 HTTP 请求
4. 服务器处理请求并返回 HTTP 报文
5. 浏览器解析渲染页面
6. 连接结束

### OSI 七层模型

![OSI 七层模型](https://oss.javaguide.cn/github/javaguide/cs-basics/network/osi-7-model.png)



### TCP/IP 四层模型

![img](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost4@main/%E7%BD%91%E7%BB%9C/tcpip%E5%8F%82%E8%80%83%E6%A8%A1%E5%9E%8B.drawio.png)



### TCP





### IP





### UDP





### ARP

ARP 协议，全称 **地址解析协议（Address Resolution Protocol）**，它解决的是网络层地址和链路层地址之间的转换问题。因为一个 IP 数据报在物理上传输的过程中，总是需要知道下一跳（物理上的下一个目的地）该去往何处，但 IP 地址属于逻辑地址，而 MAC 地址才是物理地址，ARP 协议解决了 IP 地址转 MAC 地址的一些问题。

ARP 协议工作时有一个大前提，那就是 **ARP 表**。

在一个局域网内，每个网络设备都自己维护了一个 ARP 表，ARP 表记录了某些其他网络设备的 IP 地址-MAC 地址映射关系，该映射关系以 `<IP, MAC, TTL>` 三元组的形式存储。其中，TTL 为该映射关系的生存周期，典型值为 20 分钟，超过该时间，该条目将被丢弃。

ARP 的工作原理将分两种场景讨论：

1. **同一局域网内的 MAC 寻址**；
2. **从一个局域网到另一个局域网中的网络设备的寻址**。

#### 同一局域网内的 MAC 寻址

假设当前有如下场景：IP 地址为`137.196.7.23`的主机 A，想要给同一局域网内的 IP 地址为`137.196.7.14`主机 B，发送 IP 数据报文。

> 再次强调，当主机发送 IP 数据报文时（网络层），仅知道目的地的 IP 地址，并不清楚目的地的 MAC 地址，而 ARP 协议就是解决这一问题的。

为了达成这一目标，主机 A 将不得不通过 ARP 协议来获取主机 B 的 MAC 地址，并将 IP 报文封装成链路层帧，发送到下一跳上。在该局域网内，关于此将按照时间顺序，依次发生如下事件：

1. 主机 A 检索自己的 ARP 表，发现 ARP 表中并无主机 B 的 IP 地址对应的映射条目，也就无从知道主机 B 的 MAC 地址。

2. 主机 A 将构造一个 ARP 查询分组，并将其广播到所在的局域网中。

   ARP 分组是一种特殊报文，ARP 分组有两类，一种是查询分组，另一种是响应分组，它们具有相同的格式，均包含了发送和接收的 IP 地址、发送和接收的 MAC 地址。当然了，查询分组中，发送的 IP 地址，即为主机 A 的 IP 地址，接收的 IP 地址即为主机 B 的 IP 地址，发送的 MAC 地址也是主机 A 的 MAC 地址，但接收的 MAC 地址绝不会是主机 B 的 MAC 地址（因为这正是我们要问询的！），而是一个特殊值——`FF-FF-FF-FF-FF-FF`，之前说过，该 MAC 地址是广播地址，也就是说，查询分组将广播给该局域网内的所有设备。

3. 主机 A 构造的查询分组将在该局域网内广播，理论上，每一个设备都会收到该分组，并检查查询分组的接收 IP 地址是否为自己的 IP 地址，如果是，说明查询分组已经到达了主机 B，否则，该查询分组对当前设备无效，丢弃之。

4. 主机 B 收到了查询分组之后，验证是对自己的问询，接着构造一个 ARP 响应分组，该分组的目的地只有一个——主机 A，发送给主机 A。同时，主机 B 提取查询分组中的 IP 地址和 MAC 地址信息，在自己的 ARP 表中构造一条主机 A 的 IP-MAC 映射记录。

   ARP 响应分组具有和 ARP 查询分组相同的构造，不同的是，发送和接受的 IP 地址恰恰相反，发送的 MAC 地址为发送者本身，目标 MAC 地址为查询分组的发送者，也就是说，ARP 响应分组只有一个目的地，而非广播。

5. 主机 A 终将收到主机 B 的响应分组，提取出该分组中的 IP 地址和 MAC 地址后，构造映射信息，加入到自己的 ARP 表中。

总结来说，ARP 协议是一个**广播问询，单播响应**协议。



#### 不同局域网内的 MAC 寻址

更复杂的情况是，发送主机 A 和接收主机 B 不在同一个子网中，假设一个一般场景，两台主机所在的子网由一台路由器联通。这里需要注意的是，一般情况下，我们说网络设备都有一个 IP 地址和一个 MAC 地址，这里说的网络设备，更严谨的说法应该是一个接口。路由器作为互联设备，具有多个接口，每个接口同样也应该具备不重复的 IP 地址和 MAC 地址。因此，在讨论 ARP 表时，路由器的多个接口都各自维护一个 ARP 表，而非一个路由器只维护一个 ARP 表。

接下来，回顾同一子网内的 MAC 寻址，如果主机 A 发送一个广播问询分组，那么 A 所在子网内的所有设备（接口）都将不会捕获该分组，因为该分组的目的 IP 地址在另一个子网中，本子网内不会有设备成功接收。那么，主机 A 应该发送怎样的查询分组呢？整个过程按照时间顺序发生的事件如下：

1. 主机 A 查询 ARP 表，期望寻找到目标路由器的本子网接口的 MAC 地址。

   目标路由器指的是，根据目的主机 B 的 IP 地址，分析出 B 所在的子网，能够把报文转发到 B 所在子网的那个路由器。

2. 主机 A 未能找到目标路由器的本子网接口的 MAC 地址，将采用 ARP 协议，问询到该 MAC 地址，由于目标接口与主机 A 在同一个子网内，该过程与同一局域网内的 MAC 寻址相同。

3. 主机 A 获取到目标接口的 MAC 地址，先构造 IP 数据报，其中源 IP 是 A 的 IP 地址，目的 IP 地址是 B 的 IP 地址，再构造链路层帧，其中源 MAC 地址是 A 的 MAC 地址，目的 MAC 地址是**本子网内与路由器连接的接口的 MAC 地址**。主机 A 将把这个链路层帧，以单播的方式，发送给目标接口。

4. 目标接口接收到了主机 A 发过来的链路层帧，解析，根据目的 IP 地址，查询转发表，将该 IP 数据报转发到与主机 B 所在子网相连的接口上。

   到此，该帧已经从主机 A 所在的子网，转移到了主机 B 所在的子网了。

5. 路由器接口查询 ARP 表，期望寻找到主机 B 的 MAC 地址。

6. 路由器接口如未能找到主机 B 的 MAC 地址，将采用 ARP 协议，广播问询，单播响应，获取到主机 B 的 MAC 地址。

7. 路由器接口将对 IP 数据报重新封装成链路层帧，目标 MAC 地址为主机 B 的 MAC 地址，单播发送，直到目的地。







### HTTP

#### HTTP 状态码有哪些？

HTTP 状态码用于描述 HTTP 请求的结果，比如 2xx 就代表请求被成功处理。

![常见 HTTP 状态码](https://oss.javaguide.cn/github/javaguide/cs-basics/network/http-status-code.png)

#### HTTP/1.0 和 HTTP/1.1 有什么区别？

- **连接方式** : HTTP/1.0 为短连接，HTTP/1.1 支持长连接。
- **状态响应码** : HTTP/1.1 中新加入了大量的状态码，光是错误响应状态码就新增了 24 种。比如说，`100 (Continue)`——在请求大资源前的预热请求，`206 (Partial Content)`——范围请求的标识码，`409 (Conflict)`——请求与当前资源的规定冲突，`410 (Gone)`——资源已被永久转移，而且没有任何已知的转发地址。
- **缓存机制** : 在 HTTP/1.0 中主要使用 Header 里的 If-Modified-Since,Expires 来做为缓存判断的标准，HTTP/1.1 则引入了更多的缓存控制策略例如 Entity tag，If-Unmodified-Since, If-Match, If-None-Match 等更多可供选择的缓存头来控制缓存策略。
- **Host 头（Host Header）处理** :HTTP/1.1 引入了 Host 头字段，允许在同一 IP 地址上托管多个域名，从而支持虚拟主机的功能。而 HTTP/1.0 没有 Host 头字段，无法实现虚拟主机。

#### HTTP/1.1 和 HTTP/2.0 有什么区别？

- **IO 多路复用（Multiplexing）** ：HTTP/2.0 在同一连接上可以同时传输多个请求和响应（可以看作是 HTTP/1.1 中长链接的升级版本）。HTTP/1.1 则使用串行方式，每个请求和响应都需要独立的连接。这使得 HTTP/2.0 在处理多个请求时更加高效，减少了网络延迟和提高了性能。
- **二进制帧（Binary Frames）** ：HTTP/2.0 使用二进制帧进行数据传输，而 HTTP/1.1 则使用文本格式的报文。二进制帧更加紧凑和高效，减少了传输的数据量和带宽消耗。
- **头部压缩（Header Compression）** ：HTTP/1.1 支持`Body`压缩，`Header`不支持压缩。HTTP/2.0 支持对`Header`压缩，减少了网络开销。
- **服务器推送（Server Push）**：HTTP/2.0 支持服务器推送，可以在客户端请求一个资源时，将其他相关资源一并推送给客户端，从而减少了客户端的请求次数和延迟。而 HTTP/1.1 需要客户端自己发送请求来获取相关资源。

#### HTTP/2.0 和 HTTP/3.0 有什么区别？

- **传输协议** ：HTTP/2.0 是基于 TCP 协议实现的，HTTP/3.0 新增了 QUIC（Quick UDP Internet Connections） 协议来实现可靠的传输，提供与 TLS/SSL 相当的安全性，具有较低的连接和传输延迟。你可以将 QUIC 看作是 UDP 的升级版本，在其基础上新增了很多功能比如加密、重传等等。HTTP/3.0 之前名为 HTTP-over-QUIC，从这个名字中我们也可以发现，HTTP/3 最大的改造就是使用了 QUIC。
- **连接建立** ：HTTP/2.0 需要经过经典的 TCP 三次握手过程（一般是 3 个 RTT）。由于 QUIC 协议的特性，HTTP/3.0 可以避免 TCP 三次握手的延迟，允许在第一次连接时发送数据（0 个 RTT ，零往返时间）。
- **队头阻塞** ：HTTP/2.0 多请求复用一个 TCP 连接，一旦发生丢包，就会阻塞住所有的 HTTP 请求。由于 QUIC 协议的特性，HTTP/3.0 在一定程度上解决了队头阻塞（Head-of-Line blocking, 简写：HOL blocking）问题，一个连接建立多个不同的数据流，这些数据流之间独立互不影响，某个数据流发生丢包了，其数据流不受影响（本质上是多路复用+轮询）。



### HTTPS

#### HTTP 和 HTTPS 有什么区别？

- **端口号** ：HTTP 默认是 80，HTTPS 默认是 443。
- **URL 前缀** ：HTTP 的 URL 前缀是 `http://`，HTTPS 的 URL 前缀是 `https://`。
- **安全性和资源消耗** ： HTTP 协议运行在 TCP 之上，所有传输的内容都是明文，客户端和服务器端都无法验证对方的身份。HTTPS 是运行在 SSL/TLS 之上的 HTTP 协议，SSL/TLS 运行在 TCP 之上。所有传输的内容都经过加密，加密采用对称加密，但对称加密的密钥用服务器方的证书进行了非对称加密。所以说，HTTP 安全性没有 HTTPS 高，但是 HTTPS 比 HTTP 耗费更多服务器资源。
- **SEO（搜索引擎优化）** ：搜索引擎通常会更青睐使用 HTTPS 协议的网站，因为 HTTPS 能够提供更高的安全性和用户隐私保护。使用 HTTPS 协议的网站在搜索结果中可能会被优先显示，从而对 SEO 产生影响。





### DNS

DNS 中的域名都是用**句点**来分隔的，比如 `www.server.com`，这里的句点代表了不同层次之间的**界限**。

在域名中，**越靠右**的位置表示其层级**越高**。

实际上域名最后还有一个点，比如 `www.server.com.`，这个最后的一个点代表根域名。

也就是，`.` 根域是在最顶层，它的下一层就是 `.com` 顶级域，再下面是 `server.com`。

所以域名的层级关系类似一个树状结构：

- 根 DNS 服务器（.）
- 顶级域 DNS 服务器（.com）
- 权威 DNS 服务器（server.com）

根域的 DNS 服务器信息保存在互联网中所有的 DNS 服务器中。

这样一来，任何 DNS 服务器就都可以找到并访问根域 DNS 服务器了。

因此，客户端只要能够找到任意一台 DNS 服务器，就可以通过它找到根域 DNS 服务器，然后再一路顺藤摸瓜找到位于下层的某台目标 DNS 服务器。

#### 域名解析的工作流程：

1. 客户端首先会发出一个 DNS 请求，问 www.server.com 的 IP 是啥，并发给本地 DNS 服务器（也就是客户端的 TCP/IP 设置中填写的 DNS 服务器地址）。
2. 本地域名服务器收到客户端的请求后，如果缓存里的表格能找到 www.server.com，则它直接返回 IP 地址。如果没有，本地 DNS 会去问它的根域名服务器：“老大， 能告诉我  www.server.com 的 IP 地址吗？” 根域名服务器是最高层次的，它不直接用于域名解析，但能指明一条道路。
3. 根 DNS 收到来自本地 DNS 的请求后，发现后置是 .com，说：“www.server.com 这个域名归 .com 区域管理”，我给你 .com 顶级域名服务器地址给你，你去问问它吧。”
4. 本地 DNS 收到顶级域名服务器的地址后，发起请求问“老二， 你能告诉我 www.server.com  的 IP 地址吗？”
5. 顶级域名服务器说：“我给你负责 www.server.com 区域的权威 DNS 服务器的地址，你去问它应该能问到”。
6. 本地 DNS 于是转向问权威 DNS 服务器：“老三，www.server.com对应的IP是啥呀？” server.com 的权威 DNS 服务器，它是域名解析结果的原出处。为啥叫权威呢？就是我的域名我做主。
7. 权威 DNS 服务器查询后将对应的 IP 地址 X.X.X.X 告诉本地 DNS。
8. 本地 DNS 再将 IP 地址返回客户端，客户端和目标建立连接。



## 九：Linux常用命令

### 常用的命令

### 内核

### 基本组件

### 进程间通信方式

### 目录结构

Linux 使用一种称为目录树的层次结构来组织文件和目录。目录树由根目录（/）作为起始点，向下延伸，形成一系列的目录和子目录。每个目录可以包含文件和其他子目录。结构层次鲜明，就像一棵倒立的树。

![Linux的目录结构](https://javaguide.cn/assets/Linux目录树.b82202fa.png)





## 十：场景问题 

### 缓存一致性解决



### 4G数据找系统记录(大数据题)





### 很多短任务线程，选择 synchronized 还是 lock（2022-04-11 携程）

锁竞争小时，synchronized和lock效率没差，偏向模式下（单线程读写）甚至高于lock，但是并发量上升时锁撤销会大幅影响性能，稳定自适应轻量级锁状态下，线程接近交替运行，或者说短任务线程多，基本一样，因为都是自旋，大量任务并发竞争时，随着任务量的增大，synchronized的效率会远小于lock，因为重量级锁会频繁切换内核态与用户态；大量长任务，只能重量级锁。



### 多个人给一个主播打赏怎么设计？（2022-6-3   58同城）

我说是一个高并发写的操作，对一个记录频繁写，分批操作，比如 10 个记录 操作一次。他说这个方案可以 但是有 100 个记录 怎么去做一个一个操作呢？我说如果在一个进程可以  分多个线程分批。他说还是不够快 我们是用的 MQ 多个消费者 一个打赏就发一个消息 



### 怎么实现一个点赞功能？

主要的流程解释下：先查询数据库改用户是否进行点赞，如果已经点赞则抛出异常，如果没有则new一个对象来一个一个Set，然后将已点赞的信息存入redis中，相反，取消点赞的操作就是删除redis中的数据即可，然后通过Dubbo调用API来完成保存操作，因为我这里是还要获取点赞数和评论数啥的，所以会对动态表进行更新操作。



### 比如下单清空购物车，你是如何设计的？

1. 生产者（订单系统）产生消息，发送一条半事务消息到MQ服务器
2. MQ收到消息后，将消息持久化到存储系统，这条消息的状态是待发送状态。
3. MQ服务器返回ACK确认到生产者，此时MQ不会触发消息推送事件
4. 生产者执行本地事务（订单创建成功，提交事务消息）
5. 如果本地事务执行成功，即commit执行结果到MQ服务器；如果执行失败，发送rollback。
6. 如果是commit正常提交，MQ服务器更新消息状态为**可发送**；如果是rollback，即**删除消息**。
7. 如果消息状态更新为可发送，则MQ服务器会push消息给消费者（购物车系统）。消费者消费完（即拿到订单消息，清空购物车成功）就应答ACK。
8. 如果MQ服务器长时间没有收到生产者的commit或者rollback，它会反查生产者，然后根据查询到的结果（回滚操作或者重新发送消息）执行最终状态。

有些伙伴可能有疑惑，如果消费者消费失败怎么办呢？那数据是不是不一致啦？所以就需要消费者消费成功，执行业务逻辑成功，再反馈ack嘛。如果消费者消费失败，那就自动重试嘛，接口支持幂等即可。



### 排行榜的实现，比如高考成绩排序（2022 虾皮）

排行版的实现，一般使用redis的**zset**数据类型。

```
zadd key score member [score member ...]，zrank key member
```





### 有一批帖子，会根据类别搜索，但是现在是单独一个表，现在查询非常慢，如何提高搜索性能？（2022 虾皮）

根据类别分库分表，库可以放到不同的实例上，经常查询的不变的数据]可以放到缓存里。 数据有更新时，需要刷新下缓存 因为分表后，只能是固定类别，所以需要根据类别去分开查找。 如果还有另一个重要的字段也需要查，可以再建一个分表，user-ses/ses-user就是这么做的，但是冗余就比较大。



### 如果有多个表，进行聚合查询，如何解决深分页的问题（2022 虾皮）

就是保存每个节点的表id给前端，前端查询时把id返回过来了，然后加到SQL里，但是不一定准。这里回答的是单个表吧。



### 分表的数据，动态增加一张表，不停服如何实现？（2022 虾皮）

分区策略使用一致性哈希 然后新表的数据，查询的时候，先查老的，再插入新的。如果老数据没有动，需要有对应的迁移服务进行定时迁移。插入的时候优先插入到新的表。



### 迁移线程和用户线程同时执行，会有数据库不一致的问题，怎么解决？（2022 虾皮）

加分布式锁。



### 两个机房，某个机房可能断电，如何做多机房容灾（2022 虾皮）

负载均衡层，支持切换机房写数据的时候，中间件（db/redis/es）都要进行双写。

 kafka容灾，mirror maker: https://cloud.tencent.com/developer/article/1358933



### 主从机房同步有什么问题呢？ （2022 虾皮）

> 会有比较大的延迟。 一些分布式的问题，例如分布式事务，可能就执行了几步，然后就挂了，需要有一定的策略，进行回滚或者提交。 切换机房的过程中，可能存在数据丢失，重复数据等

- 双向同步，两个机房都能写入，如果操作的是各自的数据的话，问题不大。如果操作的是相同数据，必然会有冲突，需要解决。所以上层保证相同数据到同一个机房即可，然后同步到另外一个机房，保证每个机房都有全量的数据。各种中间件都要做改造。
- 总之，分片的核心思路在于，让同一个用户的相关请求，只在一个机房内完成所有业务「闭环」，不再出现「跨机房」访问。
- 阿里在实施这种方案时，给它起了个名字，叫做「单元化」。
- 这里还有一种情况，是无法做数据分片的：全局数据。例如系统配置、商品库存这类需要强一致的数据，这类服务依旧只能采用写主机房，读从机房的方案，不做双活。
- 双活的重点，是要优先保证「核心」业务先实现双活，并不是「全部」业务实现双活。



### 冷机房新请求过来，发现缓存没有，会把数据库打挂，这个怎么解决？（2022 虾皮）

预热，提前加载到缓存。 或者平时保持一定的流量。 用了缓存的，一般需要预热下，防止雪崩。



### 定时任务这种，怎么改变执行的机房（2022 虾皮）

加开关，任何时候都有一个条件不满足，在空跑。











## 十一：微服务Spring Cloud、分布式

![1597213385700](https://zhiyu1998.github.io/Computer-Science-Learn-Notes/assets/1597213385700-d3b7c7b5.png)

### Spring Cloud

#### 什么是Spring Cloud？

Spring Cloud是一系列框架的有序集合。它利用Spring Boot的开发便利性巧妙地简化了分布式系统基础设施的开发，如服务发现注册、配置中心、智能路由、消息总线、负载均衡、断路器、数据监控等，都可以用Spring Boot的开发风格做到一键启动和部署。Spring Cloud并没有重复制造轮子，它只是将各家公司开发的比较成熟、经得起实际考验的服务框架组合起来，通过Spring Boot风格进行再封装屏蔽掉了复杂的配置和实现原理，最终给开发者留出了一套简单易懂、易部署和易维护的分布式系统开发工具包。

#### 微服务的概念

分布式，多个模块，每一个模块都是一个单独的系统。

以前所有的代码都放在同一个工程中、部署在同一个服务器、同一项目的不同模块不同功能互相抢占资源，微服务就是将工程根据不同的业务规则拆分成微服务，部署在不同的服务器上，服务之间相互调用，java中有的微服务有dubbo(只能用来做微服务)、springcloud( 提供了服务的发现、断路器等)。

- **微服务的特点**：

1. 按业务划分为一个独立运行的程序，即服务单元
2. 服务之间通过HTTP协议相互通信
3. 自动化部署
4. 可以用不同的编程语言
5. 可以用不同的存储技术
6. 服务集中化管理
7. 微服务是一个分布式系统

- **微服务的优势**

1. 将一个复杂的业务拆分为若干小的业务，将复杂的业务简单化，新人只需要了解他所接管的服务的代码，减少了新人的学习成本。
2. 由于微服务是分布式服务，服务与服务之间没有任何耦合。微服务系统的微服务单元具有很强的横向拓展能力。
3. 服务与服务之间采用HTTP网络通信协议来通信，单个服务内部高度耦合，服务与服务之间完全独立，无耦合。这使得微服务可以采用任何的开发语言和技术来实现，提高开发效率、降低开发成本。
4. 微服务是按照业务进行拆分的，并有坚实的服务边界，若要重写某一业务代码，不需了解所有业务，重写简单。
5. 微服务的每个服务单元是独立部署的，即独立运行在某个进程中，微服务的修改和部署对其他服务没有影响。
6. 微服务在CAP理论中采用的AP架构，具有高可用分区容错特点。高可用主要体现在系统7x24不间断服务，他要求系统有大量的服务器集群，从而提高系统的负载能力。分区容错也使得系统更加健壮。

- **微服务的不足**

1. 微服务的复杂度：构建一个微服务比较复杂，服务与服务之间通过HTTP协议或其他消息传递机制通信，开发者要选出最佳的通信机制，并解决网络服务差时带来的风险。
2.  分布式事物：将事物分成多阶段提交，如果一阶段某一节点失败仍会导致数据不正确。如果事物涉及的节点很多，某一节点的网络出现异常会导致整个事务处于阻塞状态，大大降低数据库的性能。
3. 服务划分：将一个完整的系统拆分成很多个服务，是一件非常困难的事，因为这涉及了具体的业务场景
4. 服务部署：最佳部署容器Docker





#### Spring Cloud的优缺点以及组件

**设计目标**

协调各个微服务，简化分布式系统开发。

**优缺点**

微服务的框架那么多比如：dubbo、Kubernetes，为什么就要使用Spring Cloud的呢？

优点：

    产出于Spring大家族，Spring在企业级开发框架中无人能敌，来头很大，可以保证后续的更新、完善
    组件丰富，功能齐全。Spring Cloud 为微服务架构提供了非常完整的支持。例如、配置管理、服务发现、断路器、微服务网关等；
    Spring Cloud 社区活跃度很高，教程很丰富，遇到问题很容易找到解决方案
    服务拆分粒度更细，耦合度比较低，有利于资源重复利用，有利于提高开发效率
    可以更精准的制定优化服务方案，提高系统的可维护性
    减轻团队的成本，可以并行开发，不用关注其他人怎么开发，先关注自己的开发
    微服务可以是跨平台的，可以用任何一种语言开发
    适于互联网时代，产品迭代周期更短

缺点：

    微服务过多，治理成本高，不利于维护系统
    分布式系统开发的成本高（容错，分布式事务等）对团队挑战大

**springcloud中的组件有那些？**

```
说出主要的组件：
Spring Cloud Eureka,服务注册中心,特性有失效剔除、服务保护
Spring Cloud Zuul,API服务网关,功能有路由分发和过滤
Spring Cloud Config,分布式配置中心，支持本地仓库、SVN、Git、Jar包内配置等模式
Spring Cloud Ribbon,客户端负载均衡,特性有区域亲和,重试机制
Spring Cloud Hystrix,客户端容错保护,特性有服务降级、服务熔断、请求缓存、请求合并、依赖隔离
Spring Cloud Feign,声明式服务调用本质上就是Ribbon+Hystrix
Spring Cloud Stream,消息驱动,有Sink、Source、Processor三种通道,特性有订阅发布、消费组、消息分区
Spring Cloud Bus,消息总线,配合Config仓库修改的一种Stream实现，
Spring Cloud Sleuth,分布式服务追踪,需要搞清楚TraceID和SpanID以及抽样,如何与ELK整合
```

**Spring Cloud项目部署架构？**

![img](https://www.pdai.tech/images/spring/spring-cloud-1.jpeg)



**网关与过滤器有什么区别？**

网关是对所有服务的请求进行分析过滤，过滤器是对单个服务而言。



**什么是断路器**

当一个服务调用另一个服务由于网络原因或自身原因出现问题，调用者就会等待被调用者的响应 当更多的服务请求到这些资源导致更多的请求等待，发生连锁效应（雪崩效应）

断路器有三种状态

- 打开状态：一段时间内 达到一定的次数无法调用 并且多次监测没有恢复的迹象 断路器完全打开 那么下次请求就不会请求到该服务
- 半开状态：短时间内 有恢复迹象 断路器会将部分请求发给该服务，正常调用时 断路器关闭
- 关闭状态：当服务一直处于正常状态 能正常调用



**什么是 Hystrix？**

在分布式系统，我们一定会依赖各种服务，那么这些个服务一定会出现失败的情况，就会导致雪崩，Hystrix就是这样的一个工具，防雪崩利器，它具有服务降级，服务熔断，服务隔离，监控等一些防止雪崩的技术。

Hystrix有四种防雪崩方式:

- 服务降级：接口调用失败就调用本地的方法返回一个空
- 服务熔断：接口调用失败就会进入调用接口提前定义好的一个熔断的方法，返回错误信息
- 服务隔离：隔离服务之间相互影响
- 服务监控：在服务发生调用时,会将每秒请求数、成功请求数等运行指标记录下来。



**什么是Feign？**

Feign 是一个声明web服务客户端，这使得编写web服务客户端更容易。它将我们需要调用的服务方法定义成抽象方法保存在本地就可以了，不需要自己构建Http请求了，直接调用接口就行了，不过要注意，调用方法要和本地抽象方法的签名完全一致。







#### Feign远程调用的基本流程

Feign远程调用，核心就是通过一系列的封装和处理，将以JAVA注解的方式定义的远程调用API接口，最终转换成HTTP的请求形式，然后将HTTP的请求的响应结果，解码成JAVA Bean，放回给调用者。Feign远程调用的基本流程，大致如下图所示。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20191201125843452.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NyYXp5bWFrZXJjaXJjbGU=,size_16,color_FFFFFF,t_70)



从上图可以看到，Feign通过处理注解，将请求模板化，当实际调用的时候，传入参数，根据参数再应用到请求上，进而转化成真正的 Request  请求。通过Feign以及JAVA的动态代理机制，使得Java  开发人员，可以不用通过HTTP框架去封装HTTP请求报文的方式，完成远程服务的HTTP调用。

**过程：**

在微服务启动时，Feign会进行包扫描，对加@FeignClient注解的接口，按照注解的规则，创建远程接口的本地JDK  Proxy代理实例。然后，将这些本地Proxy代理实例，注入到Spring  IOC容器中。当远程接口的方法被调用，由Proxy代理实例去完成真正的远程访问，并且返回结果。

### ZooKeeper

#### 基本功能

ZooKeeper 是一个开源的**分布式协调服务**，它的设计目标是将那些复杂且容易出错的分布式一致性服务封装起来，构成一个高效可靠的原语集，并以一系列简单易用的接口提供给用户使用。

ZooKeeper 为我们提供了高可用、高性能、稳定的分布式数据一致性解决方案，通常被用于实现诸如数据发布/订阅、负载均衡、命名服务、分布式协调/通知、集群管理、Master 选举、分布式锁和分布式队列等功能。这些功能的实现主要依赖于 ZooKeeper 提供的 **数据存储+事件监听** 功能。

ZooKeeper 将数据保存在内存中，性能是不错的。 在“读”多于“写”的应用程序中尤其地高性能，因为“写”会导致所有的服务器间同步状态。（“读”多于“写”是协调服务的典型场景）。

另外，很多顶级的开源项目都用到了 ZooKeeper，比如：

- **Kafka** : ZooKeeper 主要为 Kafka 提供 Broker 和 Topic 的注册以及多个 Partition 的负载均衡等功能。不过，在 Kafka 2.8 之后，引入了基于 Raft 协议的 KRaft 模式，不再依赖 Zookeeper，大大简化了 Kafka 的架构。



#### ZAB算法

##### ZAB 协议介绍

ZAB（ZooKeeper Atomic Broadcast 原子广播） 协议是为分布式协调服务 ZooKeeper 专门设计的一种支持崩溃恢复的原子广播协议。 在 ZooKeeper 中，主要依赖 ZAB 协议来实现分布式数据一致性，基于该协议，ZooKeeper 实现了一种主备模式的系统架构来保持集群中各个副本之间的数据一致性。

##### ZAB 协议两种基本的模式：崩溃恢复和消息广播

ZAB 协议包括两种基本的模式，分别是

- **崩溃恢复** ：当整个服务框架在启动过程中，或是当 Leader 服务器出现网络中断、崩溃退出与重启等异常情况时，ZAB 协议就会进入恢复模式并选举产生新的 Leader 服务器。当选举产生了新的 Leader 服务器，同时集群中已经有过半的机器与该 Leader 服务器完成了状态同步之后，ZAB 协议就会退出恢复模式。其中，**所谓的状态同步是指数据同步，用来保证集群中存在过半的机器能够和 Leader 服务器的数据状态保持一致**。
- **消息广播** ：**当集群中已经有过半的 Follower 服务器完成了和 Leader 服务器的状态同步，那么整个服务框架就可以进入消息广播模式了。** 当一台同样遵守 ZAB 协议的服务器启动后加入到集群中时，如果此时集群中已经存在一个 Leader 服务器在负责进行消息广播，那么新加入的服务器就会自觉地进入数据恢复模式：找到 Leader 所在的服务器，并与其进行数据同步，然后一起参与到消息广播流程中去。



##### Zookeeper 架构

作为一个优秀高效且可靠的分布式协调框架，`ZooKeeper` 在解决分布式数据一致性问题时并没有直接使用 `Paxos` ，而是专门定制了一致性协议叫做 `ZAB(ZooKeeper Atomic Broadcast)` 原子广播协议，该协议能够很好地支持 **崩溃恢复** 。



##### ZAB 中的三个角色

和介绍 `Paxos` 一样，在介绍 `ZAB` 协议之前，我们首先来了解一下在 `ZAB` 中三个主要的角色，`Leader 领导者`、`Follower跟随者`、`Observer观察者` 。

- `Leader` ：集群中 **唯一的写请求处理者** ，能够发起投票（投票也是为了进行写请求）。
- `Follower`：能够接收客户端的请求，如果是读请求则可以自己处理，**如果是写请求则要转发给 `Leader`** 。在选举过程中会参与投票，**有选举权和被选举权** 。
- `Observer` ：就是没有选举权和被选举权的 `Follower` 。

在 `ZAB` 协议中对 `zkServer`(即上面我们说的三个角色的总称) 还有两种模式的定义，分别是 **消息广播** 和 **崩溃恢复** 。

##### 消息广播模式

说白了就是 `ZAB` 协议是如何处理写请求的，上面我们不是说只有 `Leader` 能处理写请求嘛？那么我们的 `Follower` 和 `Observer` 是不是也需要 **同步更新数据** 呢？总不能数据只在 `Leader` 中更新了，其他角色都没有得到更新吧？

第一步肯定需要 `Leader` 将写请求 **广播** 出去呀，让 `Leader` 问问 `Followers` 是否同意更新，如果超过半数以上的同意那么就进行 `Follower` 和 `Observer` 的更新（和 `Paxos` 一样）。

在 `Leader` 这端，它为每个其他的 `zkServer` 准备了一个 **队列** ，采用先进先出的方式发送消息。由于协议是 **通过 `TCP`** 来进行网络通信的，保证了消息的发送顺序性，接受顺序性也得到了保证。

除此之外，在 `ZAB` 中还定义了一个 **全局单调递增的事务ID `ZXID`** ，它是一个64位long型，其中高32位表示 `epoch` 年代，低32位表示事务id。`epoch` 是会根据 `Leader` 的变化而变化的，当一个 `Leader` 挂了，新的 `Leader` 上位的时候，年代（`epoch`）就变了。而低32位可以简单理解为递增的事务id。

定义这个的原因也是为了顺序性，每个 `proposal` 在 `Leader` 中生成后需要 **通过其 `ZXID` 来进行排序** ，才能得到处理。



##### 崩溃恢复模式

说到崩溃恢复我们首先要提到 `ZAB` 中的 `Leader` 选举算法，当系统出现崩溃影响最大应该是 `Leader` 的崩溃，因为我们只有一个 `Leader` ，所以当 `Leader` 出现问题的时候我们势必需要重新选举 `Leader` 。

`Leader` 选举可以分为两个不同的阶段，第一个是我们提到的 `Leader` 宕机需要重新选举，第二则是当 `Zookeeper` 启动时需要进行系统的 `Leader` 初始化选举。下面我先来介绍一下 `ZAB` 是如何进行初始化选举的。

假设我们集群中有3台机器，那也就意味着我们需要两台以上同意（超过半数）。比如这个时候我们启动了 `server1` ，它会首先 **投票给自己** ，投票内容为服务器的 `myid` 和 `ZXID` ，因为初始化所以 `ZXID` 都为0，此时 `server1` 发出的投票为 (1,0)。但此时 `server1` 的投票仅为1，所以不能作为 `Leader` ，此时还在选举阶段所以整个集群处于 **`Looking` 状态**。

接着 `server2` 启动了，它首先也会将投票选给自己(2,0)，并将投票信息广播出去（`server1`也会，只是它那时没有其他的服务器了），`server1` 在收到 `server2` 的投票信息后会将投票信息与自己的作比较。**首先它会比较 `ZXID` ，`ZXID` 大的优先为 `Leader`，如果相同则比较 `myid`，`myid` 大的优先作为 `Leader`**。所以此时`server1` 发现 `server2` 更适合做 `Leader`，它就会将自己的投票信息更改为(2,0)然后再广播出去，之后`server2` 收到之后发现和自己的一样无需做更改，并且自己的 **投票已经超过半数** ，则 **确定 `server2` 为 `Leader`**，`server1` 也会将自己服务器设置为 `Following` 变为 `Follower`。整个服务器就从 `Looking` 变为了正常状态。

当 `server3` 启动发现集群没有处于 `Looking` 状态时，它会直接以 `Follower` 的身份加入集群。

还是前面三个 `server` 的例子，如果在整个集群运行的过程中 `server2` 挂了，那么整个集群会如何重新选举 `Leader` 呢？其实和初始化选举差不多。

首先毫无疑问的是剩下的两个 `Follower` 会将自己的状态 **从 `Following` 变为 `Looking` 状态** ，然后每个 `server` 会向初始化投票一样首先给自己投票（这不过这里的 `zxid` 可能不是0了，这里为了方便随便取个数字）。

假设 `server1` 给自己投票为(1,99)，然后广播给其他 `server`，`server3` 首先也会给自己投票(3,95)，然后也广播给其他 `server`。`server1` 和 `server3` 此时会收到彼此的投票信息，和一开始选举一样，他们也会比较自己的投票和收到的投票（`zxid` 大的优先，如果相同那么就 `myid` 大的优先）。这个时候 `server1` 收到了 `server3` 的投票发现没自己的合适故不变，`server3` 收到 `server1` 的投票结果后发现比自己的合适于是更改投票为(1,99)然后广播出去，最后 `server1` 收到了发现自己的投票已经超过半数就把自己设为 `Leader`，`server3` 也随之变为 `Follower`。

> 请注意 `ZooKeeper` 为什么要设置奇数个结点？比如这里我们是三个，挂了一个我们还能正常工作，挂了两个我们就不能正常工作了（已经没有超过半数的节点数了，所以无法进行投票等操作了）。而假设我们现在有四个，挂了一个也能工作，**但是挂了两个也不能正常工作了**，这是和三个一样的，而三个比四个还少一个，带来的效益是一样的，所以 `Zookeeper` 推荐奇数个 `server` 。

那么说完了 `ZAB` 中的 `Leader` 选举方式之后我们再来了解一下 **崩溃恢复** 是什么玩意？

其实主要就是 **当集群中有机器挂了，我们整个集群如何保证数据一致性？**

如果只是 `Follower` 挂了，而且挂的没超过半数的时候，因为我们一开始讲了在 `Leader` 中会维护队列，所以不用担心后面的数据没接收到导致数据不一致性。

如果 `Leader` 挂了那就麻烦了，我们肯定需要先暂停服务变为 `Looking` 状态然后进行 `Leader` 的重新选举（上面我讲过了），但这个就要分为两种情况了，分别是 **确保已经被Leader提交的提案最终能够被所有的Follower提交** 和 **跳过那些已经被丢弃的提案** 。

确保已经被Leader提交的提案最终能够被所有的Follower提交是什么意思呢？

假设 `Leader (server2)` 发送 `commit` 请求（忘了请看上面的消息广播模式），他发送给了 `server3`，然后要发给 `server1` 的时候突然挂了。这个时候重新选举的时候我们如果把 `server1` 作为 `Leader` 的话，那么肯定会产生数据不一致性，因为 `server3` 肯定会提交刚刚 `server2` 发送的 `commit` 请求的提案，而 `server1` 根本没收到所以会丢弃。

![崩溃恢复](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4b8365e80bdf441ea237847fb91236b7~tplv-k3u1fbpfcp-zoom-1.image)

那怎么解决呢？

聪明的同学肯定会质疑，**这个时候 `server1` 已经不可能成为 `Leader` 了，因为 `server1` 和 `server3` 进行投票选举的时候会比较 `ZXID` ，而此时 `server3` 的 `ZXID` 肯定比 `server1` 的大了**。(不理解可以看前面的选举算法)

那么跳过那些已经被丢弃的提案又是什么意思呢？

假设 `Leader (server2)` 此时同意了提案N1，自身提交了这个事务并且要发送给所有 `Follower` 要 `commit` 的请求，却在这个时候挂了，此时肯定要重新进行 `Leader` 的选举，比如说此时选 `server1` 为 `Leader` （这无所谓）。但是过了一会，这个 **挂掉的 `Leader` 又重新恢复了** ，此时它肯定会作为 `Follower` 的身份进入集群中，需要注意的是刚刚 `server2` 已经同意提交了提案N1，但其他 `server` 并没有收到它的 `commit` 信息，所以其他 `server` 不可能再提交这个提案N1了，这样就会出现数据不一致性问题了，所以 **该提案N1最终需要被抛弃掉** 。

![崩溃恢复](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/99cdca39ad6340ae8b77e8befe94e36e~tplv-k3u1fbpfcp-zoom-1.image)







#### CAP

##### 简介

**CAP** 也就是 **Consistency（一致性）**、**Availability（可用性）**、**Partition Tolerance（分区容错性）** 这三个单词首字母组合。

![img](https://oss.javaguide.cn/2020-11/cap.png)

在理论计算机科学中，CAP 定理（CAP theorem）指出对于一个分布式系统来说，当设计读写操作时，只能同时满足以下三点中的两个：

- **一致性（Consistency）** : 所有节点访问同一份最新的数据副本
- **可用性（Availability）**: 非故障的节点在合理的时间内返回合理的响应（不是错误或者超时的响应）。
- **分区容错性（Partition Tolerance）** : 分布式系统出现网络分区的时候，仍然能够对外提供服务。

**什么是网络分区？**

分布式系统中，多个节点之前的网络本来是连通的，但是因为某些故障（比如部分节点网络出了问题）某些节点之间不连通了，整个网络就分成了几块区域，这就叫 **网络分区**。

##### 不是所谓的“3 选 2”

大部分人解释这一定律时，常常简单的表述为：“一致性、可用性、分区容忍性三者你只能同时达到其中两个，不可能同时达到”。实际上这是一个非常具有误导性质的说法，而且在 CAP 理论诞生 12 年之后，CAP 之父也在 2012 年重写了之前的论文。

> **当发生网络分区的时候，如果我们要继续服务，那么强一致性和可用性只能 2 选 1。也就是说当网络分区之后 P 是前提，决定了 P 之后才有 C 和 A 的选择。也就是说分区容错性（Partition tolerance）我们是必须要实现的。**
>
> 简而言之就是：CAP 理论中分区容错性 P 是一定要满足的，在此基础上，只能满足可用性 A 或者一致性 C。

因此，**分布式系统理论上不可能选择 CA 架构，只能选择 CP 或者 AP 架构。** 比如 ZooKeeper 就是 CP 架构，Cassandra、Eureka 就是 AP 架构，Nacos 不仅支持 CP 架构也支持 AP 架构。

**为啥不可能选择 CA 架构呢？** 举个例子：若系统出现“分区”，系统中的某个节点在进行写操作。为了保证 C， 必须要禁止其他节点的读写操作，这就和 A 发生冲突了。如果为了保证 A，其他节点的读写操作正常的话，那就和 C 发生冲突了。

**选择 CP 还是 AP 的关键在于当前的业务场景，没有定论，比如对于需要确保强一致性的场景如银行一般会选择保证 CP 。**

另外，需要补充说明的一点是： **如果网络分区正常的话（系统在绝大部分时候所处的状态），也就说不需要保证 P 的时候，C 和 A 能够同时保证。**



### Docker

#### 什么是 Docker?

- **Docker 是世界领先的软件容器平台。**
- **Docker** 使用 Google 公司推出的 **Go 语言** 进行开发实现，基于 **Linux 内核** 提供的 CGroup 功能和 namespace 来实现的，以及 AUFS 类的 **UnionFS** 等技术，**对进程进行封装隔离，属于操作系统层面的虚拟化技术。** 由于隔离的进程独立于宿主和其它的隔离的进程，因此也称其为容器。
- **Docker 能够自动执行重复性任务，例如搭建和配置开发环境，从而解放了开发人员以便他们专注在真正重要的事情上：构建杰出的软件。**
- **用户可以方便地创建和使用容器，把自己的应用放入容器。容器还可以进行版本管理、复制、分享、修改，就像管理普通的代码一样。**



#### Docker的架构

Docker 是一个 C/S 模式的架构，后端是一个松耦合架构，模块各司其职。下图是它的总体架构图：

![img](https://pics4.baidu.com/feed/3b292df5e0fe9925352c266b0d1303d78db17128.jpeg@f_auto?token=c40cb7490618ce8a15a9032ad3bb0eea)



1. 用户是使用 Docker Client 与 Docker Daemon 建立通信，并发送请求给后者。
2. Docker Daemon 作为 Docker 架构中的主体部分，首先提供 Docker Server 的功能使其可以接受 Docker Client 的请求。
3. Docker Engine 执行 Docker 内部的一系列工作，每一项工作都是以一个 Job 的形式的存在。
4. Job 的运行过程中，当需要容器镜像时，则从 Docker Registry 中下载镜像，并通过镜像管理驱动 Graphdriver 将下载镜像以 Graph 的形式存储。
5. 当需要为 Docker 创建网络环境时，通过网络管理驱动 Networkdriver 创建并配置 Docker容器网络环境。
6. 当需要限制 Docker 容器运行资源或执行用户指令等操作时，则通过 Execdriver 来完成。
7. Libcontainer 是一项独立的容器管理包，Networkdriver 以及 Execdriver 都是通过 Libcontainer 来实现具体对容器进行的操作。



#### 镜像和容器的相关操作





## 十二：项目

**开发技术**：Spring Cloud + Spring Boot + MybatisPlus + Redis + mysql + Mongodb + Zookeeper + kafka + ElasticSearch + Docker + 第三方技术阿里云OSS;

**项目背景：**”来看头条“ 项目类似于今日头条，是一个新闻资讯类项目。该项目由用户端和自媒体端组成。在用户端，实现了用户通过app端登录功能、浏览文章功能、搜索文章功能、用户历史记录功能。在自媒体端，实现了自媒体管理员登录功能、发布文章功能、删除文章功能、上传素材功能、文章内容审核功能

**项目重难点：**网关搭建；文章详情静态化及存储；文章自动审核及延迟发布；分布式锁解决集群下的方法抢占执行；热点文章实时计算

**技术栈的具体应用**：

- Spring-Cloud-Gateway : 微服务之前架设的网关服务，实现服务注册中的API请求路由，以及控制流速控制和熔断处理都是常用的架构手段，而这些功能Gateway天然支持
- 运用Spring Boot快速开发框架，构建项目工程；并结合Spring Cloud全家桶技术，实现app后端、自媒体等微服务。
- 运用Spring Cloud Alibaba Nacos作为项目中的注册中心和配置中心
- 运用mybatis-plus作为持久层提升开发效率
- 采用kafka作为消息服务中间件，把自媒体文章上下架放进消息队列；通过用户的行为（点赞、评论、喜欢）实时记录用户数据，通过kafkaStream流式计算最新的数据；与客户端系统消息通知
- 运用Redis缓存技术，实现热数据的计算，提升系统性能指标，同时作为消息中间件异步消费任务。
- 使用Mysql存储用户数据，以保证上层数据查询的高性能
- 使用Mongo存储用户历史记录数据，以保证用户热数据高扩展和高性能指标 
- 运用AI技术，来完成系统自动化功能，以提升效率及节省成本。比如文章审核

 

#### **优化：**

##### （1）优化一

缺陷 ：写操作（定时刷新）比较频繁的话导致 cache 中的数据会被频繁被删除，这样会影响缓存命中率 。

解决办法：

- 数据库和缓存数据强一致场景 ：更新 db 的时候同样更新 cache，不过我们需要加一个分布式锁来保证更新 cache 的时候不存在线程安全问题。



##### （2）优化二

缺陷：消费者丢失消息的情况

我们知道消息在被追加到 Partition(分区)的时候都会分配一个特定的偏移量（offset）。偏移量（offset)表示 Consumer 当前消费到的 Partition(分区)的所在的位置。Kafka 通过偏移量（offset）可以保证消息在分区内的顺序性。

当消费者拉取到了分区的某个消息之后，消费者会自动提交了 offset。自动提交的话会有一个问题，试想一下，当消费者刚拿到这个消息准备进行真正消费的时候，突然挂掉了，消息实际上并没有被消费，但是 offset 却被自动提交了。

**解决办法**:

- 我们手动关闭自动提交 offset，每次在真正消费完消息之后再自己手动提交 offset 。 细心的朋友一定会发现，这样会带来消息被重新消费的问题。比如你刚刚消费完消息之后，还没提交 offset，结果自己挂掉了，那么这个消息理论上就会被消费两次。



##### （3）优化三

存储技术选型优化：

MinIO：

AliyunOSS：



##### （4）优化四

缺陷：对于变量存在多线程并发竞争

**解决办法：**

为变量设置ThreadLocal。



#### **身份验证怎么做的？**

```
AuthorizedFilter + AppJwtUtil
```

1. 用户向服务器发送用户名、密码以及验证码用于登陆系统。用户进入网关开始登陆，网关过滤器进行判断，如果是登录，则路由到后台管理微服务进行登录。
2. 如果用户用户名、密码以及验证码校验正确的话，服务端会返回已经签名的 Token，也就是 JWT。
3. 用户以后每次向后端发请求都在 Header 中带上这个 JWT ，再次进入网关开始访问，网关过滤器接收用户携带的TOKEN。
4. 服务端检查 JWT 并从中获取用户相关信息。网关过滤器解析TOKEN ，判断是否有权限，如果有，则放行，如果没有则返回未认证错误。

两点建议：

1. 建议将 JWT 存放在 localStorage 中，放在 Cookie 中会有 CSRF 风险。
2. 请求服务端并携带 JWT 的常见做法是将其放在 HTTP Header 的 `Authorization` 字段中（`Authorization: Bearer Token`）。



**乐观锁：**

使用版本号。



#### **网关搭建**：

思路分析：

1. 用户进入网关开始登陆，网关过滤器进行判断，如果是登录，则路由到后台管理微服务进行登录
2. 用户登录成功，后台管理微服务签发JWT TOKEN信息返回给用户
3. 用户再次进入网关开始访问，网关过滤器接收用户携带的TOKEN 
4. 网关过滤器解析TOKEN ，判断是否有权限，如果有，则放行，如果没有则返回未认证错误

具体实现：

第一：

​	在认证过滤器中需要用到jwt的解析，所以需要把工具类拷贝一份到网关微服务

第二：

​	在网关微服务中新建全局过滤器



#### 文章详情静态化及存储：

**文章详情静态化：**

​	FreeMarker 是一款模板引擎： 即一种基于模板和要改变的数据， 并用来生成输出文本(HTML网页，电子邮件，配置文件，源代码等)的通用工具。 它不是面向最终用户的，而是一个Java类库，是一款程序员可以嵌入他们所开发产品的组件。

​	模板编写为FreeMarker Template Language (FTL)。它是简单的，专用的语言， 不是像PHP那样成熟的编程语言。 那就意味着要准备数据在真实编程语言中来显示，比如数据库查询和业务运算， 之后模板显示已经准备好的数据。在模板中，你可以专注于如何展现数据， 而在模板之外可以专注于要展示什么数据。 

**存储：AliyunOSS**

对象存储可提供更好的数据保护，加密、保护敏感数据。

![](http://www.img.youngxy.top/Java/fig/image-20210602180856833.png)

#### 文章自动审核及延迟发布：

**文章自动审核：**

1 自媒体端发布文章后，开始审核文章（异步线程的方式审核文章，在自动审核的方法上加上@Async注解（标明要异步调用），在自媒体引导类中使用@EnableAsync注解开启异步调用）

2 审核的主要是审核文章的内容（文本内容和图片）

3 借助第三方提供的接口审核文本

4 借助第三方提供的接口审核图片，由于图片存储到OSS中，需要先下载才能审核

5 如果审核失败，则需要修改自媒体文章的状态，status:2  审核失败    status:3  转到人工审核

6 如果审核成功，则需要在文章微服务中创建app端需要的文章：

​		在文章审核成功以后需要在app的article库中新增文章数据：

- ​			保存文章信息 ap_article

- ​			保存文章配置信息 ap_article_config
- ​			保存文章内容 ap_article_content



![](http://www.img.youngxy.top/Java/fig/image-20210505010938575.png)



**延迟发布：**

redis实现：zset数据类型的去重有序（分数排序）特点进行延迟。例如：时间戳作为score进行排序

实现思路：

![](http://www.img.youngxy.top/Java/fig/image-20210513150440342.png)



问题思路：

1.为什么任务需要存储在数据库中？

延迟任务是一个通用的服务，任何需要延迟得任务都可以调用该服务，需要考虑数据持久化的问题，存储数据库中是一种数据安全的考虑。

2.为什么redis中使用两种数据类型，list和zset？

效率问题，算法的时间复杂度

3.在添加zset数据的时候，为什么不需要预加载？

任务模块是一个通用的模块，项目中任何需要延迟队列的地方，都可以调用这个接口，要考虑到数据量的问题，如果数据量特别大，为了防止阻塞，只需要把未来几分钟要执行的数据存入缓存即可。

实现：

- 延迟队列服务提供对外接口：提供远程的feign接口
- 发布文章集成添加延迟队列接口
- 修改发布文章代码：把之前的异步调用修改为调用延迟任务
- 消费任务进行审核文章



#### 分布式锁解决集群下的方法抢占执行：

问题描述：

启动两台heima-leadnews-schedule服务，每台服务都会去执行refresh定时任务方法

分布式锁：

控制分布式系统有序的去对共享资源进行操作，通过互斥来保证数据的一致性。

解决方案：

sexnx （SET if Not eXists） 命令在指定的 key 不存在时，为 key 设置指定的值。

这种加锁的思路是，如果 key 不存在则为 key 设置 value，如果 key 已存在则 SETNX 命令不做任何操作

- 客户端A请求服务器设置key的值，如果设置成功就表示加锁成功
- 客户端B也去请求服务器设置key的值，如果返回失败，那么就代表加锁失败
- 客户端A执行代码完成，删除锁
- 客户端B在等待一段时间后再去请求设置key的值，设置成功
- 客户端B执行代码完成，删除锁







#### 热点文章实时计算：

![](http://www.img.youngxy.top/Java/fig/image-20210730201509223.png)



思路说明：

![](http://www.img.youngxy.top/Java/fig/image-20210621235620854.png)





**待优化**：

使用FastDFS作为静态资源存储器，在其上实现热静态资源缓存、淘汰等功能（待优化）

运用Hbase技术，存储系统中的冷数据，保证系统数据的可靠性（待优化）

运用ES搜索技术，对冷数据、文章数据建立索引，以保证冷数据、文章查询性能（待优化）

当用户 Logout 的话，JWT 也还有效。除非，我们在后端增加额外的处理逻辑比如将失效的 JWT 存储起来，后端先验证 JWT 是否有效再进行处理。



## **自我介绍：**

面试官，您好，首先很感谢您给我的面试机会！我叫杨路恒，今年24岁，山东济宁人，就读于陕西师范大学，今年研二，软件工程专业，研究方向为知识图谱。大学时间我主要利用课外时间学习了 Java 以及 一些框架 。在校期间参与了全国大学生数学建模竞赛和全国大学生英语竞赛，并且在数学建模比赛中担任队长并获得了陕西省一等奖。说到业余爱好的话，一个是比较喜欢通过博客整理分享自己所学知识，现在在CSDN上的粉丝数达到了3k+，访问量达到了44W+。 另一个是喜欢旅游和骑行的方式来放松。这就是我的自我介绍，感谢。 

 























## 面试：

### 一、中科全安

#### 一面：HR面，态度算好的（5分钟）

1.介绍公司

2.问啥时候能来实习

3.问你能接受加班吗

4.问你的期望薪资多少

5.问我想听听你对加班什么看法

6.最后让明天面试，我说不行，能最晚啥时候，她说最晚周五下午，电话面试



#### 二面：面试官狗叫（15分钟 ）

面试官的态度极其恶心，什么垃圾玩意，老子是来面试的，不是听狗叫的，就算老子啥都不会也不去你这个垃圾公司。

1.自我介绍，直接打断说只介绍自己会的，项目中的技术栈

2.问我熟悉哪些框架，我说Spring、Mysql，其他都是了解，熟悉的是Java基础和Mysql，他就非得问SpringBoot，离谱

3.问Kafka的分区、主题了解吗，我就是了解，然后他也说了你只是了解

4.问ES了解吗（我只是了解他是干啥的，其它都是API）

5.问SpringBoot异常怎么处理（这个紧张忘了，只是说在一个包里要继承一个异常的接口）

6.问SpringBoot的核心配置文件以及它们的区别

7.问SpringBoot的起步依赖starter有哪些以及作用（他说你了解的很少，我都说了了解Mysql、Spring，你他妈的非得问）

8.问SpringBoot 中的监视器是什么（我说不了解）

9.问Swagger用过么、它用来做什么

10.问什么是 WebSockets（我说只了解它是用来作为即时通讯的协议）

11.问什么是 FreeMarker 模板（这个离谱，说完他是干啥的以及插值表达式，他问还有呢？他妈的）

12.问C/S 、B/S模式

13.问String、StringBuilder、StringBuffer 的区别

14.问了MySQL的存储引擎

15.问了 InnoDB 和 MyISAM 的比较

16.问了建立索引的原则

17.问了三大范式

18.问了Jdbc的防止sql注入（我说不了解）

19.反问（我说实习生的日常工作安排是什么，他妈的开始狗叫，想打他）

20.最后问我啥时能来实习（老子不去）



### 二、联想

#### 一面：（16分钟）

1.说一下红黑树（吧啦吧啦，讲了下红黑树的特性、又扯到HashMap底层用到了红黑树、突然说到MySQLB+树用到了红黑树）

2.讲一下线程池（这个熟，线程池的7大参数以及具体用法）

3.讲一下为什么用线程池（两点原因）

4.讲一下线程池参数MaxSize满了你会怎么做（我说实现自定义处理策略，可以选择移除等待队列中的最不常用的线程）

5.看我熟悉Python（这里没听清发音，重复确认了好几遍哈哈哈哈哈哈哈），问我生成器用过没（我说是生成随机数吗，面试官说不是，是，还耐心给我解答了是用来循环遍历的）

5.项目拷打环节 

1）讲一下你的项目经验（从项目的整体架构到具体功能实现细节都说了）

2）我说了做的一些优化（存储到阿里云OSS）

3）突然电话断了，在那我俩喂喂喂了好几声，面试官又给我打了过来，问我同步和异步怎么实现的（加@Async注解）

4）问异步调用后，做了些什么（第三方接口审核完后传来一个statusCode200，表示审核成功存储到数据库）

6.反问环节

1）实习生的日常工作安排（面试官说了早上9：30上班，下午6：30下班，八小时工作制，中午休息一小时）

2）技术栈是什么（面试官说这有Java和Python，你来就是Java） 





### 三、中国邮政储蓄银行

#### 一面：结构化面试+技术面（20分钟，五个面试官）

1.先做一个两分钟以内的自我介绍（对邮储的看法，对未来工作的看法）

2.对工作地点的看法

3.有没有参加过团体活动

4.数学建模比赛中遇到的最大困难

5.开始技术面试了，面试官说看你简历都是Java相关，讲一下Spring Cloud你用过的组件

6.ConcurrentHashMap为什么用红黑树

7.看你项目中用到了网关，是怎么实现的

8.都说CAS要比synchronized好，但是CAS也有自己的问题，你能说一下有哪些问题吗

9.另一个面试官问看你简历有高并发系统的东西，你有实际的项目经验吗（这个我说就是了解，实际项目是自学的，实际项目经验都是人工智能相关的）

10.最后女的HR问看你成绩这么好，没有保研吗

11.讲一下研究生的论文成果

12.研究生的专业成绩排名，有获得过奖学金吗

13.最后问有offer吗，方便说一下是哪个公司吗  

另：

1.对工作地点的看法

2.CPU达到100%的时候你如何排查问题

3.新生代和老年代数据是如何流动的

4.Linux如何给文件权限

5.想从实习中收获什么

6.看你是院研副部长，如果负责的活动和实验室科研冲突了怎么平衡

7.你平常也这样扎头发吗

 