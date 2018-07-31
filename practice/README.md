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


###  Java基本类型哪些，所占字节和范围

###  Set、List、Map的区别和联系


  + List 有序可重复
  + Set 无序不可重复
  + Map 存储key-value映射关系的数据

###  什么时候使用Hashmap


  + 需要使用键值存储的场景

###  什么时候使用Linkedhashmap、Concurrenthashmap、Weakhashmap


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

    

###  哪些集合类是线程安全的


  + HashTable
  + ConcurrentHashMap
  + CopyOnWriteArrayList
  + CopyOnWriteArraySet 
  + ConcurrentLinkedDequeue


###  为什么Set、List、map不实现Cloneable和Serializable接口


  + 克隆（cloning）或者序列化（serialization）的语义和含义是跟具体的实现相关的。因此应该由集合类的具体实现类来决定如何被克隆或者序列化 
  + Collection表示一个集合，包含了一组对象。如何存储和维护这些对象是由具体实现来决定的。因为集合的具体形式多种多样，例如list允许重复，set则不允许。而克隆（clone）和序列化（serializable）只对于具体的实体，对象有意义，你不能说去把一个接口，抽象类克隆，序列化甚至反序列化。所以具体的Collection实现类是否可以克隆，是否可以序列化应该由其自身决定，而不能由其超类强行赋予。  如果Collection继承了clone和serializable，那么所有的集合实现都会实现这两个接口，而如果某个实现它不需要被克隆，甚至不允许它序列化（序列化有风险），那么就与Collection矛盾了。 

###  Concurrenthashmap的实现，1.7和1.8的实现

  > 1.7 采用(Segment extends ReentrantLock)[] + HashEntry[] 的分段锁技术来实现同步

  > 1.8 采用Synchronized & CAS 来实现并发同步，并且使用与HashMap相同的数据结构 **Node数组 + 链表 + 红黑树**

###  Arrays.sort的实现

###  什么时候使用CopyOnWriteArrayList


  + 读写分离时使用
  + CopyOnWrite容器即写时复制的容器。通俗的理解是当我们往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。这样做的好处是我们可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。 

###  volatile的使用

###  synchronied的使用

### Reentrantlock的实现和Synchronied的区别

+ ReentrantLock依赖于jdk的实现，而Synchronized依赖于jvm实现
+ ReenTrantLock可以指定是公平锁还是非公平锁。而synchronized只能是非公平锁。 
+ ReenTrantLock提供了一个Condition（条件）类，用来实现分组唤醒需要唤醒的线程们，而不是像synchronized要么随机唤醒一个线程要么唤醒全部线程。
+ ReenTrantLock提供了一种能够中断等待锁的线程的机制，通过lock.lockInterruptibly()来实现这个机制。

### CAS的实现原理以及问题

+ compareAndSwap，但是会出现ABA的问题，及

### AQS的实现原理

### 接口和抽象类的区别，什么时候使用

### 类加载机制的步骤，每一步做了什么，static和final修改的成员变量的加载时机

### 双亲委派模型

### 反射机制：反射动态擦除泛型、反射动态调用方法等

### 动态绑定：父类引用指向子类对象

### JVM内存管理机制：有哪些区域，每个区域做了什么

### JVM垃圾回收机制：垃圾回收算法 垃圾回收器 垃圾回收策略

### jvm参数的设置和jvm调优

### 什么情况产生年轻代内存溢出、什么情况产生年老代内存溢出

### 内部类：静态内部类和匿名内部类的使用和区别

### Redis和memcached：什么时候选择redis，什么时候选择memcached，内存模型和存储策略是什么样的

### MySQL的基本操作 主从数据库一致性维护

### mysql的优化策略有哪些

### mysql索引的实现 B+树的实现原理

### 什么情况索引不会命中，会造成全表扫描

### java中bio nio aio的区别和联系

### 为什么bio是阻塞的 nio是非阻塞的 nio是模型是什么样的

### Java io的整体架构和使用的设计模式

### Reactor模型和Proactor模型

### http请求报文结构和内容

### http三次握手和四次挥手


  + 握手

    + Client 发起建立连接的请求，数据包Syn.J，状态为SYN_SEND
    + Server 返回 Syn.K & ACK.J+1，状态SYN_RECV
    + Client 返回 ACK.K+1，双方状态为ESTABLISHED
  + 

### rpc相关：如何设计一个rpc框架，从io模型 传输协议 序列化方式综合考虑

### Linux命令 统计，排序，前几问题等

### StringBuff 和StringBuilder的实现，底层实现是通过byte数据，外加数组的拷贝来实现的

### cas操作的使用

### 内存缓存和数据库的一致性同步实现

### 微服务的优缺点

### 线程池的参数问题

### ip问题 如何判断ip是否在多个ip段中

### 判断数组两个中任意两个数之和是否为给定的值

### 乐观锁和悲观锁的实现

### synchronized实现原理

### 你在项目中遇到的困难和怎么解决的

### 你在项目中完成的比较出色的亮点

### 消息队列广播模式和发布/订阅模式的区别

### 生产者消费者代码实现

### 死锁代码实现

### 线程池：参数，每个参数的作用，几种不同线程池的比较，阻塞队列的使用，拒绝策略

### Future和ListenableFuture 异步回调相关

### 算法相关：判断能否从数组中找出两个数字和为给定值，随机生成1~10000不重复并放入数组，求数组的子数组的最大和，二分查找算法的实现及其时间复杂计算





## 3、其他



### 算法：常用排序算法，二分查找，链表相关，数组相关，字符串相关，树相关等

### 常见序列化协议及其优缺点

### memcached内存原理，为什么是基于块的存储

### 搭建一个rpc需要准备什么

### 如果线上服务器频繁地出现full gc ，如何去排查

### 如果某一时刻线上机器突然量变得很大，服务扛不住了，怎么解决

### LUR算法的实现

### LinkedHashMap实现LRU

### 定义栈的数据结构，请在该类型中实现一个能够找到栈最小元素的min函数

### 海量数据处理的解决思路

### reactor模型的演变

### 阻塞、非阻塞、同步、异步区别

### Collection的子接口

### jvm调优相关

### zookeeper相关，节点类型，如何实现服务发现和服务注册

### nginx负载均衡相关，让你去实现负载均衡，该怎么实现

### linux命令，awk、cat、sort、cut、grep、uniq、wc、top等

### 压力测试相关，怎么分析，单接口压测和多情况下的压测

### 你觉得你的有点是什么，你的缺点是什么

### spring mvc的实现原理

### netty底层实现，IO模型，ChannelPipeline的实现和原理

### 缓存的设计和优化

### 缓存和数据库一致性同步解决方案

### 你所在项目的系统架构，谈谈整体实现

### 消息队列的使用场景

### ActiveMQ、RabbitMQ、Kafka的区别 