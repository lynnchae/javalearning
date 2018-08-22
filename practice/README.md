## 1、常用设计模式



###  单例模式
+ 懒汉式、饿汉式、双重校验锁、静态加载，内部类加载、枚举类加载。保证一个类仅有一个实例，并提供一个访问它的全局访问点。

###  代理模式
+ 动态代理和静态代理，什么时候使用动态代理。

###  适配器模式
+ 将一个类的接口转换成客户希望的另外一个接口。适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。

###  装饰者模式
+ 动态给类加功能。

###  观察者模式
+ 有时被称作发布/订阅模式，观察者模式定义了一种一对多的依赖关系，让多个观察者对象同时监听某一个主题对象。这个主题对象在状态发生变化时，会通知所有观察者对象，使它们能够自动更新自己。

###  策略模式
+ 定义一系列的算法,把它们一个个封装起来, 并且使它们可相互替换。

###  外观模式
+ 为子系统中的一组接口提供一个一致的界面，外观模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。

###  命令模式
+ 将一个请求封装成一个对象，从而使您可以用不同的请求对客户进行参数化。

###  创建者模式
+ 将一个复杂的构建与其表示相分离，使得同样的构建过程可以创建不同的表示。

###  抽象工厂模式
+ 提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们具体的类。





## 2、基础

###  2.1 Java基本类型哪些，所占字节和范围

###  2.2 Set、List、Map的区别和联系


  + List 有序可重复
  + Set 无序不可重复
  + Map 存储key-value映射关系的数据

###  2.3 什么时候使用Hashmap


  + 需要使用键值存储的场景

###  2.4 什么时候使用Linkedhashmap、Concurrenthashmap、Weakhashmap


  + Linkedhashmap 添加或者删除元素频繁

  + Concurrenthashmap 高并发、需要考虑安全性的场景

  + WeekHashMap 

    java弱引用相关，可能会被GC自动删除，适用于需要缓存的场景 

    ```java
    public final class org.apache.tomcat.util.collections.ConcurrentCache<K,V> {
    
        private final int size;
    
        //理解为活跃区域
        private final Map<K,V> eden;
    
        //理解为长时间不活跃的区域
        private final Map<K,V> longterm;
    
        public ConcurrentCache(int size) {
            this.size = size;
            this.eden = new ConcurrentHashMap<>(size);
            this.longterm = new WeakHashMap<>(size);
        }
        
        
        public void put(K k, V v) {
            //如果eden超过size容量
            if (this.eden.size() >= size) {
                synchronized (longterm) {
                    //将eden全部移动到longterm
                    this.longterm.putAll(this.eden);
                }
                //清除eden
                this.eden.clear();
            }
            //优先放入eden
            this.eden.put(k, v);
        }
    
        public V get(K k) {
            //优先从eden区域获取
            V v = this.eden.get(k);
            if (v == null) {
                //如果eden区没有，从longterm获取
                synchronized (longterm) {
                    v = this.longterm.get(k);
                }
                //如果longterm获取到此对象，此对象进入eden
                //即保持活跃状态，不被回收
                if (v != null) {
                    this.eden.put(k, v);
                }
            }
            return v;
        }
    }
    ```

    

###  2.5 哪些集合类是线程安全的


  + HashTable
  + ConcurrentHashMap
  + CopyOnWriteArrayList
  + CopyOnWriteArraySet 
  + ConcurrentLinkedDequeue

###  2.6 为什么Set、List、map不实现Cloneable和Serializable接口


  + 克隆（cloning）或者序列化（serialization）的语义和含义是跟具体的实现相关的。因此应该由集合类的具体实现类来决定如何被克隆或者序列化 
  + Collection表示一个集合，包含了一组对象。如何存储和维护这些对象是由具体实现来决定的。因为集合的具体形式多种多样，例如list允许重复，set则不允许。而克隆（clone）和序列化（serializable）只对于具体的实体，对象有意义，你不能说去把一个接口，抽象类克隆，序列化甚至反序列化。所以具体的Collection实现类是否可以克隆，是否可以序列化应该由其自身决定，而不能由其超类强行赋予。  如果Collection继承了clone和serializable，那么所有的集合实现都会实现这两个接口，而如果某个实现它不需要被克隆，甚至不允许它序列化（序列化有风险），那么就与Collection矛盾了。 

###  2.7 Concurrenthashmap的实现，1.7和1.8的实现

  > 1.7 采用(Segment extends ReentrantLock)[] + HashEntry[] 的分段锁技术来实现同步

  > 1.8 采用Synchronized & CAS 来实现并发同步，并且使用与HashMap相同的数据结构 **Node数组 + 链表 + 红黑树**

###  2.8 Arrays.sort的实现

###  2.9 什么时候使用CopyOnWriteArrayList


  + 读写分离时使用
  + CopyOnWrite容器即写时复制的容器。通俗的理解是当我们往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。这样做的好处是我们可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。 

###  2.10 volatile的使用

当一个共享变量被volatile修饰时，它会保证修改的值会立即被更新到主存，当有其他线程需要读取时，它会去内存中读取新值。 

**语义**：

+ 保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。

+ 禁止进行指令重排序（内存屏障）。

  >  注：可见性只能保证每次读取的是最新的值，以及“有序性”，但是volatile没办法保证对变量的操作的原子性。 

由于JVM运行程序的实体是线程，而每个线程创建时JVM都会为其创建一个**工作内存**(有些地方称为线程栈)，用于存储线程私有的数据，而Java内存模型中规定所有变量都存储在**主内存（堆内存）**，主内存是共享内存区域，所有线程都可以访问，但线程对变量的操作(读取赋值等)必须在工作内存中进行，首先要将变量从主内存拷贝的自己的工作内存空间，然后对变量进行操作，操作完成后再将变量写回主内存，不能直接操作主内存中的变量，工作内存中存储着主内存中的变量副本拷贝，前面说过，工作内存是每个线程的私有数据区域，因此不同的线程间无法访问对方的工作内存，线程间的通信(传值)必须通过主内存来完成

###  2.11 synchronized的使用

+ 参考锁膨胀

### 2.12 Reentrantlock的实现和Synchronied的区别

+ ReentrantLock依赖于jdk的实现，而Synchronized依赖于jvm实现
+ ReenTrantLock可以指定是公平锁还是非公平锁。而synchronized只能是非公平锁。 
+ ReenTrantLock提供了一个Condition（条件）类，用来实现分组唤醒需要唤醒的线程们，而不是像synchronized要么随机唤醒一个线程要么唤醒全部线程。
+ ReenTrantLock提供了一种能够中断等待锁的线程的机制，通过lock.lockInterruptibly()来实现这个机制。

### 2.13 CAS的实现原理以及问题

CAS 操作包含三个操作数 —— 内存位置（V）、预期原值（A）和新值(B)。 如果内存位置的值与预期原值相匹配，那么处理器会自动将该位置值更新为新值 。否则，处理器不做任何操作。 

**问题**

+ ABA问题。因为CAS需要在操作值的时候检查下值有没有发生变化，如果没有发生变化则更新，但是如果一个值原来是A，变成了B，又变成了A，那么使用CAS进行检查时会发现它的值没有发生变化，但是实际上却变化了。ABA问题的解决思路就是使用版本号。在变量前面追加上版本号，每次变量更新的时候把版本号加一，那么A－B－A 就会变成1A-2B－3A。 
+ 循环时间长开销大。自旋CAS如果长时间不成功，会给CPU带来非常大的执行开销。 
+ 只能保证一个共享变量的原子操作。当对一个共享变量执行操作时，我们可以使用循环CAS的方式来保证原子操作，但是对多个共享变量操作时，循环CAS就无法保证操作的原子性，这个时候就可以用锁，或者有一个取巧的办法，就是把多个共享变量合并成一个共享变量来操作。 

### 2.14 AQS的实现原理

[AQS介绍](https://segmentfault.com/a/1190000008471362)

​	`AbstractQueuedSynchronizer`

+ 同步器AQS内部的实现是依赖同步队列（一个FIFO的双向队列，其实就是数据结构双向链表）来完成同步状态的管理。

  + 当前线程获取同步状态失败时，同步器AQS会将**当前线程和等待状态**等信息构造成为一个节点（node）加入到同步队列，同时会阻塞当前线程；

  ```java
  	//竞争线程尝试获得锁，入队
  	private Node enq(final Node node) {
          //自旋 CAS入队尾
          for (;;) {
              Node t = tail;
              if (t == null) { // Must initialize
                  //初始化头节点，头节点为空Node
                  if (compareAndSetHead(new Node()))
                      tail = head;
              } else {
                  //加入队列尾部，构建双端队列
                  node.prev = t;
                  if (compareAndSetTail(t, node)) {
                      t.next = node;
                      return t;
                  }
              }
          }
      }
  ```

  #### 获取同步状态

  假设线程A要获取同步状态（这里想象成锁，方便理解），初始状态下state=0,所以线程A可以顺利获取锁，A获取锁后将state置为1。在A没有释放锁期间，线程B也来获取锁，此时因为state=1，表示锁被占用，所以将B的线程信息和等待状态等信息构成出一个Node节点对象，放入同步队列，head和tail分别指向队列的头部和尾部（此时队列中有一个空的Node节点作为头点，head指向这个空节点，空Node的后继节点是B对应的Node节点，tail指向它），同时阻塞线程B(这里的阻塞使用的是LockSupport.park()方法)。后续如果再有线程要获取锁，都会加入队列尾部并阻塞。

  #### 释放同步状态

  当线程A释放锁时，即将state置为0，此时A会唤醒头节点的后继节点（所谓唤醒，其实是调用LockSupport.unpark(B)方法），即B线程从LockSupport.park()方法返回，此时B发现state已经为0，所以B线程可以顺利获取锁，B获取锁后B的Node节点随之出队。

### 2.15接口和抽象类的区别，什么时候使用

### 2.16 类加载机制的步骤，每一步做了什么，static和final修改的成员变量的加载时机



+ **加载->链接（验证+准备+解析）->初始化（使用前的准备）->使用->卸载**  

  >  **加载**：首先通过一个类的全限定名来获取此类的二进制字节流；其次将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构；最后在java堆中生成一个代表这个类的Class对象，作为方法区这些数据的访问入口。 

  > **验证**：确保Class文件的字节流中包含的信息符合当前虚拟机的要求，并且不会危害虚拟机自身的安全

  > **准备**：为类变量分配内存并设置初始值

  >**解析**：将符号引用转为直接引用

  >**初始化**：执行类中定义的java程序代码，初始化类变量和其他资源，执行类构造器

  >（静态变量、静态初始化块）–>（成员变量、初始化块）–> 构造器；
  >
  >如果有父类，则顺序是：父类static方法 –> 子类static方法 –> 父类构造方法- -> 子类构造方法  

+ 加载顺序

  + 静态属性：static 开头定义的属性
  + 静态方法块： static {} 圈起来的方法块
  + 普通属性： 未带static定义的属性
  + 普通方法块： {} 圈起来的方法块
  + 构造函数： 类名相同的方法
  + 方法： 普通方法

### 2.17 双亲委派模型

+ `Bootstrap ClassLoader`  java_home\lib 核心类库
  + `Extension ClassLoader` java_home\lib\ext 扩展类库
    + `Application ClassLoader` 用户路径classpath下的类
      + `User ClassLoader `
+ TCCL (ThreadContextClassLoader)：线程上下文加载器打破双亲委派模型
  + 例如：SPI机制的核心代码由bootstrap classLoader加载，根据双亲委派模型，无法委派Application ClassLoader来加载第三方实现类，所以通过获取线程上下文类加载器来加载spi的实现类

+ 双亲委派模型工作过程是：如果一个类加载器收到类加载的请求，它首先不会自己去尝试加载这个类，而是把这个请求委派给父类加载器完成。每个类加载器都是如此，只有当父加载器在自己的搜索范围内找不到指定的类时（即`ClassNotFoundException`），子加载器才会尝试自己去加载。

+ 保证加载环境中类的唯一性 

### 2.18 反射机制：反射动态擦除泛型、反射动态调用方法等

### 2.19 动态绑定：父类引用指向子类对象

​	动态绑定是指“在执行期间（而非编译期间）判断所引用对象的实际类型，根据其实际类型调用其相应的方法。” 

​	动态绑定是多态性得以实现的重要因素，它通过方法表来实现：每个类被加载到虚拟机时，在方法区保存元数据，其中，包括一个叫做 方法表（method table）的东西，表中记录了这个类定义的方法的指针，每个表项指向一个具体的方法代码。如果这个类重写了父类中的某个方法，则对应表项指向新的代码实现处。从父类继承来的方法位于子类定义的方法的前面。

​        **动态绑定语句的编译、运行原理**：我们假设 Father ft=new Son();  ft.say();  Son继承自Father，重写了say()。

​        1：编译：我们知道，向上转型时，用父类引用执行子类对象，并可以用父类引用调用子类中重写了的同名方法。但是不能调用子类中新增的方法，为什么呢？

​	因为**在代码的编译阶段**，编译器通过 **声明对象的类型（即引用本身的类型）** 在方法区中该类型的方法表中查找匹配的方法（最佳匹配法：参数类型最接近的被调用），如果有则编译通过。（这里是根据声明的对象类型来查找的，所以此处是查找 Father类的方法表，而Father类方法表中是没有子类新增的方法的，所以不能调用。）编译阶段是确保方法的存在性，保证程序能顺利、安全运行。

​        2：运行：我们又知道，ft.say()调用的是Son中的say()，这不就与上面说的，查找Father类的方法表的匹配方法矛盾了吗？不，这里就是动态绑定机制的真正体现。

​	上面编译阶段在 声明对象类型 的方法表中查找方法，**只是为了安全地通过编译（也为了检验方法是否是存在的）**。而在实际**运行这条语句**时，在执行 Father ft=new Son(); 这一句时创建了一个Son实例对象，然后在 ft.say() 调用方法时，JVM会把刚才的son对象压入操作数栈，用它来进行调用。而用实例对象进行方法调用的过程就是动态绑定：**根据实例对象所属的类型去查找它的方法表，找到匹配的方法进行调用。**我们知道，子类中如果重写了父类的方法，则方法表中同名表项会指向子类的方法代码；若无重写，则按照父类中的方法表顺序保存在子类方法表中。故此：动态绑定根据对象的类型的方法表查找方法是一定会匹配（因为编译时在父类方法表中以及查找并匹配成功了，说明方法是存在的。这也解释了为何向上转型时父类引用不能调用子类新增的方法：**在父类方法表中必须先对这个方法的存在性进行检验，如果在运行时才检验就容易出危险——可能子类中也没有这个方法**）。

### 2.20 JVM内存管理机制：有哪些区域，每个区域做了什么

+ [JDK1.8内存模型](https://blog.csdn.net/bruce128/article/details/79357870)

+ GC Roots对象

  + （1）虚拟机栈中引用的对象  

  + （2）方法区中类静态属性引用的对象  

  + （3）方法区中常量引用的对象  

  + （4）本地方法栈中JNI引用的对象 

    

+ 程序计数器：指向当前线程正在执行的字节码代码的行号 
+ java虚拟机栈：生命周期与线程同进同退 
+ 本地方法栈： 为native方法服务
+ 堆(heap space)
  + YoungGen
    + Eden(8/10)
    + Survivor01(1/10) & Survivor02(1/10)
    + 年轻代对象默认年龄15，即经历的minorGC次数
  + OldGen
+ 元数据区（存放虚拟机加载的类信息，静态变量，常量等数据 ）

+ GC过程
  + 大部分对象刚创建的时候，JVM会将其分布到Eden区域。
  + 当Eden区域中的对象达到一定的数目的时候，就会进行Minor GC，经历这次垃圾回收后所有存活的对象都会进入两个Suvivor Place中的一个。
    + 当触发minor GC时，会先把Eden中存活的对象复制到to Survivor中；
    + 然后再看from survivor，如果次数达到年老代的标准，就复制到年老代中；如果没有达到则复制到to survivor中，如果to survivor满了，则复制到年老代中。
    + 然后调换from survivor 和 to survivor的名字，保证每次to survivor都是空的等待对象复制到那里的。
  + 同一时刻两个Suvivor Place，即s0和s1中总有一个总是空的。
  + 年轻代中的对象经历过了多次的垃圾回收就会转移到年老代中。

+ 大对象连续内存空间直接分配在老年代

### 2.21 JVM垃圾回收机制：垃圾回收算法 垃圾回收器 垃圾回收策略

[垃圾收集器](https://blog.csdn.net/tjiyu/article/details/53983650)

[垃圾回收算法](https://blog.csdn.net/hp910315/article/details/50937045)

+ 对象标记算法
  + 引用计数法。无法解决循环引用的问题
  + 可达性分析算法。GC Roots

+ 垃圾回收算法
  + 标记-清除算法
  + 复制算法
  + 标记-整理算法
  + 分代回收算法

+ 垃圾回收器
  + **新生代收集器**：

    + Serial：单线程复制算法

    + ParNew：Serial的多线程版本

    + Paralle Scavenge：以吞吐量为优先，多个CPU上，对暂停时间没有特别高的要求时，即程序主要在后台进行计算，而不需要与用户进行太多交互； 

      > **吞吐量=运行用户代码时间/（运行用户代码时间+垃圾收集时间）** 

  + **老年代收集器**：

    + Serial Old：单线程，标记-整理算法

    + Paralle Old：Paralle Scavenge的老年代版本，多线程标记-整理算法

    + CMS（ConcurrentMarkSweep）：只有ParNew可以搭配使用，标记-清除算法，并发低停顿，注重用户响应

      > 初始标记 -> 并发标记 -> 重新标记 -> 并发清除

  + **整堆收集器**：G1

  > 并行（parallel）：指多条垃圾收集线程并行工作，但此时用户线程仍然处于等待状态。
  >
  > 并发（Concurrent）：指用户线程与垃圾收集线程同时执行（但不一定是并行的，可能会交替进行），用户程序在继续镜像，而垃圾收集程序运行于另一个CPU上。

### 2.22 jvm参数的设置和jvm调优

### ~~2.23 什么情况产生年轻代内存溢出、什么情况产生年老代内存溢出~~

### 2.24 内部类：静态内部类和匿名内部类的使用和区别

[内部类](https://www.jianshu.com/p/f0fdea957792)

### 2.25 Redis和memcached：什么时候选择redis，什么时候选择memcached，内存模型和存储策略是什么样的

### 2.26 MySQL的基本操作 主从数据库一致性维护

### 2.27 mysql的优化策略有哪些

***什么情况下不推荐使用索引？***

1) 数据唯一性差（一个字段的取值只有几种时）的字段不要使用索引

> 比如性别，只有两种可能数据。意味着索引的二叉树级别少，多是平级。这样的二叉树查找无异于全表扫描。

2) 频繁更新的字段不要使用索引

> 比如logincount登录次数，频繁变化导致索引也频繁变化，增大数据库工作量，降低效率。

3) 字段不在where语句出现时不要添加索引,如果where后含IS NULL /IS NOT NULL/ like ‘%输入符%’等条件，不建议使用索引

> 只有在where语句出现，mysql才会去使用索引

4） where 子句里对索引列使用不等于（<>），使用索引效果一般

[优化参考](https://blog.csdn.net/kaka1121/article/details/53395587)

+ **为查询缓存优化你的查询** 
+ **EXPLAIN SELECT查询** 
+ **当只要一行数据时使用LIMIT1** 
+ **为搜索字段建索引** 
+ **在Join表的时候使用相当类型的例，并将其索引** 
+ **避免 SELECT \*** 
+ **永远为每张表设置一个ID** 
  + 使用VARCHAR类型来当主键会使用得性能下降。 
+ **应该总是让你的字段保持NOT NULL **
+ **固定长度的表会更快**  
+ **垂直分割** 
  + 把数据库中的表按列变成几张表的方法，这样可以降低表的复杂度和字段的数目，从而达到优化的目的。 
+ **越小的列会越快**  
  + 如果一个表只会有几列罢了（比如说字典表，配置表），那么，我们就没有理由使用INT来做主键，使用MEDIUMINT,SMALLINT或是更小的TINYINT会更经济一些。如果你不需要记录时间，使用DATE要比DATETIME好得多。 

### 2.28 mysql索引的实现 B+树的实现原理

### 2.29 什么情况索引不会命中，会造成全表扫描

+ mysql运算符 <>,!= 不会命中索引
+ or 条件中的列如果没索引，则不会命中索引
+ like 以 %开头的不会走索引，%结尾命中索引
+ 存在索引列的数据类型隐形转换，则用不上索引，比如列类型是字符串，那一定要在条件中将数据使用引号引用起来，否则不使用索引 
+ where 子句里对索引列上有***数学运算或者使用函数***，用不上索引 
+ 如果mysql估计使用全表扫描要比使用索引快，则不使用索引 ，例如表的数据量很小时

***多列索引***

如果使用多列索引，where条件中字段的顺序非常重要，需要满足最左前缀列。

**最左前缀**：查询条件中的所有字段需要从左边起按顺序出现在多列索引中，查询条件的字段数要小于等于多列索引的字段数，中间字段不能存在范围查询的字段(<,like等)，这样的sql可以使用该多列索引。 

### 2.30 java中bio nio aio的区别和联系

[bio nio aio](https://www.sohu.com/a/119086870_505779)

### 2.31 为什么bio是阻塞的 nio是非阻塞的 nio是模型是什么样的

### 2.32 Java io的整体架构和使用的设计模式

### 2.33 Reactor模型和Proactor模型

### 2.34 http请求报文结构和内容

### 2.35 http三次握手和四次挥手


  + **三次握手过程：**

    + 第一次握手：client发送一个TCP标志位SYN=1、ACK=0的数据包给server，并随机会产生一个Sequence number=3233.当server接收到这个数据后，server由SYN=1可知客户端是想要建立连接；

    + 第二次握手：server要对客户端的联机请求进行确认，向client发送应答号ACK=1、SYN=1、确认号Acknowledge number=3234，此值是server的序列号加1，还会产生一个随机的序列号Sequence number=36457，这样就告诉client可以进行连接；

    + 第三次握手：client收到数据后检查Acknowledge number是否是3233+1的值，以及ACK的值是否为1，若为1，client会发送ACK=1、确认号码Acknowledge number=36457，告诉server，你的请求连接被确认，连接可以建立。


​        

  + **四次挥手过程：**

    + 第一次挥手：当传输的数据到达尾部时，client向server发送FIN=1标志位；可理解成，client向server说，我这边的数据传送完成了，我准备断开了连接；
    + 第二次挥手：因TCP的连接是全双工的双向连接，关闭也是要从两边关闭；当server收到client发来的FIN=1的标志位后，server不会立刻向client发送FIND=1的请求关闭信息，而是先向client发送一个ACK=1的应答信息，表示：你请求关闭的请求我已经收到，但我可能还有数据没有完成传送，你再等下，等我数据传输完成了我就告诉你；
    + 第三次挥手：server数据传输完成，向client发送FIN=1，client收到请求关闭连接的请求后，client就明白server的数据已传输完成，现在可以断开连接了，
    + 第四次挥手：client收到FIND=1后，client还是怕由于网络不稳定的原因，怕server不知道他要断开连接，于是向server发送ACK=1确认信息进行确认，把自己设置成TIME_WAIT状态并启动定时器，如果server没有收到ACK，server端TCP的定时器到达后，会要求client重新发送ACK，当server收到ACK后，server就断开连接；当client等待2MLS（2倍报文最大生存时间）后，没有收到server的重传请求后，他就知道server已收到了ACK，所以client此时才关闭自己的连接。

### 2.36 rpc相关：如何设计一个rpc框架，从io模型 传输协议 序列化方式综合考虑

### 2.37 Linux命令 统计，排序，前几问题等

### 2.38 StringBuff 和StringBuilder的实现

+ 底层实现是通过数组的拷贝来实现的
+ 性能：StringBuilder > StringBuffer > String

### 2.39 cas操作的使用

### 2.40 内存缓存和数据库的一致性同步实现



+ **更新缓存VS淘汰缓存**

> 更新缓存很直接，但是涉及到本次更新的数据结果需要一堆数据运算（例如更新用户余额，可能需要先看看有没有优惠券等），复杂度就增加了。
>
> 淘汰缓存仅仅会增加一次cache miss，代价可以忽略，所以建议**淘汰缓存**

+ **先淘汰后写数据库vs先写数据库后淘汰**

> 先写后淘汰，如果淘汰失败，cache里一直是脏数据
>
> 先淘汰后写，下次请求的时候缓存就会miss hit一次，这个代价是可以忽略的，（如果淘汰失败return false）
>
> 综合比较，推荐**先淘汰缓存再写数据库**

### 2.41微服务的优缺点

### 2.42线程池：参数，每个参数的作用，几种不同线程池的比较，阻塞队列的使用，拒绝策略

### 2.43ip问题 如何判断ip是否在多个ip段中

### 2.44 判断数组两个中任意两个数之和是否为给定的值

### 2.45 乐观锁和悲观锁的实现

+ 乐观锁：CAS
+ 悲观锁：Synchronized| Reentrantlock

### 2.46 synchronized实现原理

> monitorenter & monitorexit
>
> > **同步语句块**的实现使用的是monitorenter 和 monitorexit 指令，其中monitorenter指令指向同步代码块的开始位置，monitorexit指令则指明同步代码块的结束位置 
>
> > **成员方法、类方法级同步**JVM可以从方法常量池中的方法表结构(method_info Structure) 中的 ACC_SYNCHRONIZED 访问标志区分一个方法是否同步方法。当方法调用时，调用指令将会 检查方法的 ACC_SYNCHRONIZED 访问标志是否被设置，如果设置了，执行线程将先持有monitor（虚拟机规范中用的是管程一词）， 然后再执行方法，最后再方法完成(无论是正常完成还是非正常完成)时释放monitor。 

> 成员方法锁类的实例对象，类方法锁类对象，即class对象

+ 当退出同步方法，或者程序异常时，会释放对象的锁

### 2.47 你在项目中遇到的困难和怎么解决的

### 2.48 你在项目中完成的比较出色的亮点

### 2.49 消息队列广播模式和发布/订阅模式的区别

### 2.50 生产者消费者代码实现

### 2.51 死锁代码实现

```java
public static void main(String[] args) {
        final Object o1 = new Object();
        final Object o2 = new Object();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o1) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "--> o1 get lock");
                        Thread.sleep(Long.parseLong("2000"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o2) {
                        System.out.println(Thread.currentThread().getName() + "-->>> o2 get lock");
                    }
                }
            }
        },"deadlock-t1-thread");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o2) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "--> o2 get lock");
                        Thread.sleep(Long.parseLong("2000"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o1) {
                        System.out.println(Thread.currentThread().getName() + "-->>> o1 get lock");
                    }
                }
            }
        },"deadlock-t2-thread");
        t1.start();
        t2.start();
    }
```



### 2.52 Future和ListenableFuture 异步回调相关

### 2.53 算法相关：判断能否从数组中找出两个数字和为给定值，随机生成1~10000不重复并放入数组，求数组的子数组的最大和，二分查找算法的实现及其时间复杂计算





## 3、其他



### 3.1 算法：常用排序算法，二分查找，链表相关，数组相关，字符串相关，树相关等

### 3.2 常见序列化协议及其优缺点

### 3.3 memcached内存原理，为什么是基于块的存储

### 3.4 搭建一个rpc需要准备什么

### 3.5 如果线上服务器频繁地出现full gc ，如何去排查

### 3.6 如果某一时刻线上机器突然量变得很大，服务扛不住了，怎么解决

### 3.7 LRU算法的实现

Least Recently Used

### 3.8 LinkedHashMap实现LRU

+ `LinkedHashMap#accessOrder=true`，即按访问顺序进行排序，最近访问的在队尾。

### 3.9 定义栈的数据结构，请在该类型中实现一个能够找到栈最小元素的min函数

### 3.10 海量数据处理的解决思路

### 3.11 reactor模型的演变

### 3.12 阻塞、非阻塞、同步、异步区别

### 3.13 Collection的子接口

### 3.14 zookeeper相关，节点类型，如何实现服务发现和服务注册

+ **节点类型**
  + PERSISTENT：持久化节点 
  + PERSISTENT_SEQUENTIAL： 顺序自动编号持久化节点，这种节点会根据当前已存在的节点数自动加 1 
  + EPHEMERAL：临时节点， 客户端session超时这类节点就会被自动删除 
  + EPHEMERAL_SEQUENTIAL：临时自动编号节点 
+ **客户端角色和状态**
  + Leader Follower Observer
  + Looking Following Leading Observing
+ **选举**
  + 投票内容（myid，zxid），先比较zxid，再比较myid
    + zxid是一个64位的数字，低32代表一个单调递增的计数器，高32位代表Leader周期。 
      + 当有新的Leader产生时，Leader周期epoch加1，计数器从0开始；
      + 每处理一个事务请求，计数器加1
    + myid相当于服务器id
  + 1.服务器初始化时Leader选举 
    + zookeeper由于其自身的性质，一般建议选取奇数个节点进行搭建分布式服务器集群。以3个节点组成的服务器集群为例，说明服务器初始化时的选举过程。启动第一台安装zookeeper的节点时，无法单独进行选举，启动第二台时，两节点之间进行通信，开始选举Leader。 
      + 1）每个Server投出一票。他们两都选自己为Leader，投票的内容为（myid，ZXID）。myid即Server的id，安装zookeeper时配置文件中所配置的myid；ZXID，事务id，为节点的更新程度，ZXID越大，代表Server对Znode的操作越新。由于服务器初始化，每个Sever上的Znode为0，所以Server1投的票为（1,0），Server2为（2,0）。两Server将各自投票发给集群中其他机器。 
      + 2）每个Server接收来自其他Server的投票。集群中的每个Server先判断投票有效性，如检查是不是本轮的投票（逻辑时钟或者投票次数），是不是来Looking状态的服务器投的票。 
      + 3）对投票结果进行处理。先了解下处理规则。首先对比ZXID。ZXID大的服务器优先作为Leader - 若ZXID相同，比如初始化的时候，每个Server的ZXID都为0，就会比较myid，myid大的选出来做Leader。 对于Server而言，他接受到的投票为（2,0），因为自身的票为（1,0），所以此时它会选举Server2为Leader，将自己的更新为（2,0）。而Server2收到的投票为Server1的（1,0）由于比他自己小，Server2的投票不变。Server1和Server2再次将票投出，投出的票都为（2,0）。 
      + 4） 统计投票。每次投票之后，服务器都会统计投票信息，如果判定某个Server有过半的票数投它，那么该Server将会作为Leader。对于Server1和Server2而言,统计出已经有两台机器接收了（2,0）的投票信息，此时认为选出了Leader。 
      + 5）改变服务器状态。当确定了Leader之后，每个Server更新自己的状态，Leader将状态更新为Leading，Follower将状态更新为Following。 

  + 2.服务器运行期间的Leader选举 
    + zookeeper运行期间，如果有新的Server加入，或者非Leader的Server宕机，那么Leader将会同步数据到新Server或者寻找其他备用Server替代宕机的Server。若Leader宕机，此时集群暂停对外服务，开始在内部选举新的Leader。假设当前集群中有Server1、Server2、Server3三台服务器，Server2为当前集群的Leader，由于意外情况，Server2宕机了，便开始进入选举状态。过程如下 
      + 1） 变更状态。其他的非Observer服务器将自己的状态改变为Looking，开始进入Leader选举。 
      + 2） 每个Server发出一个投票（myid，ZXID），由于此集群已经运行过，所以每个Server上的ZXID可能不同。假设Server1的ZXID为145，Server3的为122，第一轮投票中，Server1和Server3都投自己，票分别为（1，145）、（3，122），将自己的票发送给集群中所有机器。 
      + 3） 每个Server接收接收来自其他Server的投票，降下来的步骤与启动时步骤相同。
+ ZAB协议
+ FastLeaderElection
+ zookeeper数据的读写
  + **写**
    + Client向zk的server1上写数据，发送一个写的请求。
    + 如果server1不是leader,那么server1会把接收的请求转发给leader。这个leader会将写请求广播给各个server，比如server1和server2,各个server写成功之后就会通知leader。
    + 当leader收到大多数server写成功的消息，那么就说明数据写成功了。之后leader会告诉server1数据写成功了。
    + server1会通知Client数据写成功了。这时就认为整个写操作成功。
  + **读**
    + Client向zk集群中的节点发送一个读请求，该节点读取数据后直接返回，因为集群中每个节点看到的数据视图是一样的


### 3.15 nginx负载均衡相关，让你去实现负载均衡，该怎么实现

### 3.16 linux命令，awk、cat、sort、cut、grep、uniq、wc、top等

### 3.17 压力测试相关，怎么分析，单接口压测和多情况下的压测

### 3.18 spring mvc的实现原理

### 3.19 netty底层实现，IO模型，ChannelPipeline的实现和原理

### 3.20 缓存的设计和优化

### 3.21 缓存和数据库一致性同步解决方案

### 3.22 你所在项目的系统架构，谈谈整体实现

### 3.23 消息队列的使用场景

### 3.33 ActiveMQ、RabbitMQ、Kafka的区别