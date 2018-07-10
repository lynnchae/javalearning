# Java Learning

---

### 1. Design Pattern 


#### 1.1 factory desgin


#### 1.2 singleton desgin


#### 1.3 decorator


#### 1.4 adapter
 >  装饰器与适配器模式都有一个别名叫包装模式(Wrapper)，它们的作用看似都是起到包装一个类或对象的作用，
    但是使用它们的目的不一样。
    适配器模式的意义是将一个接口转变成另一个接口，通过改变接口达到重复使用的目的；
    而装饰器模式不是要改变被装饰对象的接口，而恰恰要保持原有的接口，但增强原有对象的功能，
    或者改变原有对象的处理方法而提高性能。
 


#### 1.5 proxy
        
```java
public static Object newProxyInstance(ClassLoader loader,
                  Class<?>[] interfaces,
                      InvocationHandler h)
```
 > JDK动态代理，创建一个com.sun.proxy.$Proxy0类，继承Proxy，动态实现interfaces接口，通过父类Proxy构造器
   Constructor(InvocationHandler h)，实例化一个interfaces类型的对象，最后通过h.invoke反射调用对应的方法。

---

|代理方式|实现|优点|缺点|特点
|:-----|:-----|:-----|:-----|:-----|
|JDK动态代理|代理类与委托类实现同一接口，主要是通过代理类实现InvocationHandler，并重写invoke方法来进行动态代理的，在invoke方法中将对方法进行增强处理|不需要硬编码接口，代码复用率高|只能够代理实现了接口的委托类|底层使用反射机制进行方法的调用|
|CGLIB动态代理|代理类将委托类作为自己的父类并为其中的非final委托方法创建两个方法， 一个是与委托方法签名相同的方法，它在方法中会通过super调用委托方法；另一个是代理类独有的方法。在代理方法中，它会判断是否存在实现了MethodInterceptor接口的对象，若存在则将调用intercept方法对委托方法进行代理|可以在运行时对类或者是接口进行增强操作，且委托类无需实现接口|不能对final类以及final方法进行代理|底层将方法全部存入一个数组中，通过数组索引直接进行方法调用|

---

#### 1.6 strategy

---

### 2. Framework

#### 2.1 plugin chain simple implement

>  根据mybatis简单实现的一个pluginChain

>  简陋版，会针对所有的方法进行拦截



#### 2.2 spring mvc 1.0

>  简单实现springMVC定位、加载、注册过程



#### 2.3 filters

>  通过匿名内部类创建FilterChain，并对匿名内部类有个深入的理解
    
>  外部类方法中传入匿名内部类的变量，匿名内部类实际上持有了该变量的一个拷贝，如果对此拷贝进行改变，
   不会反应到方法中，而对于开发者而言，看到的是同一个对象，所以不能保持同步修改，故方法中的变量需要定义为final.
      

      
#### 2.4 spring mvc 2.0

>  实现springMVC定位、加载、注册过程
    
>  实现handlerAdapter,HandlerMapping,Aop,DispatchServlet...
    
>  可以通过url进行访问
  

       
#### 2.5 aop拦截
      
      
```Java
  protected Object invokeJoinpoint() throws Throwable {
      return AopUtils.invokeJoinpointUsingReflection(this.target, this.method, this.arguments);
  }
```
  > aop对Service类中的a() b()方法均配置的拦截，当a()内部调用b()，无法做到对b的拦截
    invokeJoinpoint()中传递的对象为目标对象，而不是被aop代理过的对象，即this.target
    调用b()方法时，并没有过代理对象来执行，所以无法拦截。如果需要在a()方法中调用b()方法，并且对b()进行拦截，
    则需要获取到Service类的代理对象来进行调用，((Service)AopContext.currentProxy()) -> b()
        
  > <aop:config expose-proxy="true" proxy-target-class="false"></aop:config>
    需要配置此项，暴露代理对象，实现线程内共享，使用ThreadLocal模式
        
  > AbstractAutoProxyCreator实现了BeanPostProcessor接口，spring容器初始化bean后，调用postProcessAfterInitialization
    对bean进行wrapIfNecessary，创建一个aop的proxy对象。
    
       ProxyFactory -> aopProxy -> getProxy
                                    <- JDKDynamicAopProxy
                                    <- CglibAopProxy
       ProxyFactory保存了aop拦截的配置信息                    
     

   
#### 2.6 init-method，afterPropertiesSet和BeanPostProcessor

   > 1.  先执行类的构造器，进行实例化
   > 2.  接着执行 BeanPostProcessor -> postProcessBeforeInitialization
   > 3.  然后到InitializingBean -> afterPropertiesSet
   > 4.  再到配置的init-method 方法
   > 5.  最后 BeanPostProcessor -> postProcessAfterInitialization

+            
    init-method和afterPropertiesSet可以针对某个单独的bean进行处理，
    BeanPostProcessor可以针对容器中所有的bean进行处理

+   
    如果一个Bbean是lazy-init，而另一个none lazy-init的singleton Abean依赖于它，
    那么当ApplicationContext实例化singleton Abean时，
    必须确保上述singleton Abean所依赖所有bean也被预先初始化，包括设置为lazy-init的Bbean,
    这种情况也符合延时加载的bean在第一次调用时才被实例化的规则。
      
---
  
### 3. Concurrent

#### 3.1 先++  后++
    
   +  先++：先运算，后使用
   
   +  后++：先使用，后运算
         
```java
    String[] names = {"jack","tom","lily"};
    int index = 0;
    System.out.println(names[index ++]);//输出jack
    System.out.println(names[index]);//输出tom
    System.out.println(names[++index]);//输出lily
```

  
#### 3.2 Lock

+ 自旋锁: 
   > 一种用于保护多线程共享资源的锁，与一般互斥锁（mutex）不同之处在于当自旋锁尝试获取锁时以忙等待（busy waiting）的形式不断地循环检查锁是否可用。
     自旋锁适用于锁使用者保持锁时间比较短的情况

+ 互斥锁：
    
   > 目的和自旋锁一样，但机制不一样，当线程占用资源后，加上锁，后者线程访问时，由于资源被占有，转入休眠(sleep)状态，等资源被释放后，通过信号量通知排队等候的线程。

      自旋锁是指锁的实现方式
      互斥锁是指锁的类型，互斥锁可以用自旋来实现

+ 可重入锁

>   
    同一个线程，外层方法获取锁后，内层方法仍有获得该锁的代码，不受影响。
    
    如果某个线程试图获取一个已经由它自己持有的锁时，那么这个请求会立刻成功，并且会将这个锁的计数值加1，
    而当线程退出同步代码块时，计数器将会递减，当计数值等于0时，锁释放。
    
#### 3.3 JVM内置锁的膨胀
    
   >[简书：浅谈偏向锁、轻量级锁、重量级锁](https://www.jianshu.com/p/36eedeb3f912)
   
   >[CSDN：java 中的锁 -- 偏向锁、轻量级锁、自旋锁、重量级锁](https://blog.csdn.net/zqz_zqz/article/details/70233767)

    偏向锁 -> 轻量级锁 -> 重量级锁  
+ JDK1.5开始，引入了轻量锁与偏向锁，默认启用了自旋锁，这些都属于乐观锁
+ JDK1.6引入自适应自旋锁， -XX:+UseSpinning开启； -XX:PreBlockSpin=10 为自旋次数； 
+ JDK1.7后，去掉此参数，由jvm控制；
    
    
    
> mark word
    
|状态|标志位|存储内容|
|:-----|:-----|:-----|
|未锁定|01|	对象哈希码、对象分代年龄，是否可偏向锁0|
|偏向锁|01|	偏向线程ID、偏向时间戳、对象分代年龄，是否可偏向锁1|
|轻量级锁定|00|	指向锁记录的指针|
|膨胀(重量级锁定)|	10|	执行重量级锁定的指针|
|GC标记|	11|	空(不需要记录信息)|

**偏向锁**： 
顾名思义，它会偏向于第一个访问锁的线程，如果在运行过程中，同步锁只有一个线程访问，不存在多线程争用的情况，
则线程是不需要触发同步的，这种情况下，就会给线程加一个偏向锁。 
如果在运行过程中，遇到了其他线程抢占锁，则持有偏向锁的线程会被挂起，JVM会撤销它身上的偏向锁，此时会发生stop the world,
锁升级为**轻量级锁**。

**轻量级锁**:
轻量级锁是由偏向锁升级来的，偏向锁运行在一个线程进入同步块的情况下，当第二个线程加入锁争用的时候，偏向锁就会升级为轻量级锁。

**重量级锁**：
重量级锁在轻量级锁自旋超过一定的次数，或者一个线程在持有锁，一个在自旋，又有第三个来访时，
轻量级锁膨胀为重量级锁，重量级锁使除了拥有锁的线程以外的线程都阻塞，防止CPU空转。



#### 3.4 锁优化

* **减少锁的时间**

不需要同步执行的代码，能不放在同步快里面执行就不要放在同步快内，可以让锁尽快释放；

*  **减少锁的粒度**

它的思想是将物理上的一个锁，拆成逻辑上的多个锁，增加并行度，从而降低锁竞争。它的思想也是用空间来换时间；

> 

`ConcurrentHashMap`

> java中的ConcurrentHashMap在jdk1.8之前的版本，使用一个Segment 数组Segment< K,V >[] segments;
Segment继承自ReenTrantLock，所以每个Segment就是个可重入锁，每个Segment 有一个HashEntry< K,V >数组用来存放数据，put操作时，先确定往哪个Segment放数据，只需要锁定这个Segment，执行put，其它的Segment不会被锁定；所以数组中有多少个Segment就允许同一时刻多少个线程存放数据，这样增加了并发能力。

`LongAdder`     
> LongAdder 实现思路也类似ConcurrentHashMap，LongAdder有一个根据当前并发状况动态改变的Cell数组，Cell对象里面有一个long类型的value用来存储值; 
  开始没有并发争用的时候或者是cells数组正在初始化的时候，会使用cas来将值累加到成员变量的base上，在并发争用的情况下，LongAdder会初始化cells数组，在Cell数组中选定一个Cell加锁，数组有多少个cell，就允许同时有多少线程进行修改，最后将数组中每个Cell中的value相加，在加上base的值，就是最终的值；cell数组还能根据当前线程争用情况进行扩容，初始长度为2，每次扩容会增长一倍，直到扩容到大于等于cpu数量就不再扩容，这也就是为什么LongAdder比cas和AtomicInteger效率要高的原因，后面两者都是volatile+cas实现的，他们的竞争维度是1，LongAdder的竞争维度为“Cell个数+1”为什么要+1？因为它还有一个base，如果竞争不到锁还会尝试将数值加到base上；

`LinkedBlockingQueue`
>  LinkedBlockingQueue也体现了这样的思想，在队列头入队，在队列尾出队，入队和出队使用不同的锁，相对于LinkedBlockingArray只有一个锁效率要高；

拆锁的粒度不能无限拆，最多可以将一个锁拆为当前cup数量个锁即可；

* **锁粗化**

> 大部分情况下我们是要让锁的粒度最小化，锁的粗化则是要增大锁的粒度; 
  在以下场景下需要粗化锁的粒度：  
  假如有一个循环，循环内的操作需要加锁，我们应该把锁放到循环外面，否则每次进出循环，都进出一次临界区，效率是非常差的；

* **使用读写锁**

> ReentrantReadWriteLock 是一个读写锁，读操作加读锁，可以并发读，写操作使用写锁，只能单线程写；

* **读写分离**

> CopyOnWriteArrayList 、CopyOnWriteArraySet 
  CopyOnWrite容器即写时复制的容器。通俗的理解是当我们往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，
  复制出一个新的容器，然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。
  这样做的好处是我们可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，
  读和写不同的容器。CopyOnWrite并发容器用于读多写少的并发场景，因为，读的时候没有锁，
  但是对其进行更改的时候是会加锁的，否则会导致多个线程同时复制出多个副本，各自修改各自的；

* **使用cas**

> 如果需要同步的操作执行速度非常快，并且线程竞争并不激烈，这时候使用cas效率会更高，
  因为加锁会导致线程的上下文切换，如果上下文切换的耗时比同步操作本身更耗时，且线程对资源的竞争不激烈，
  使用volatiled+cas操作会是非常高效的选择；

* **消除缓存行的伪共享**

> 除了我们在代码中使用的同步锁和jvm自己内置的同步锁外，还有一种隐藏的锁就是缓存行，它也被称为性能杀手。 
  在多核cup的处理器中，每个cup都有自己独占的一级缓存、二级缓存，甚至还有一个共享的三级缓存，为了提高性能，
  cpu读写数据是以缓存行为最小单元读写的；32位的cpu缓存行为32字节，64位cup的缓存行为64字节，这就导致了一些问题。 
  例如，多个不需要同步的变量因为存储在连续的32字节或64字节里面，当需要其中的一个变量时，
  就将它们作为一个缓存行一起加载到某个cup-1私有的缓存中（虽然只需要一个变量，但是cpu读取会以缓存行为最小单位，
  将其相邻的变量一起读入），被读入cpu缓存的变量相当于是对主内存变量的一个拷贝，也相当于变相的将在同一个缓存行中的几个变量加了一把锁，
  这个缓存行中任何一个变量发生了变化，当cup-2需要读取这个缓存行时，
  就需要先将cup-1中被改变了的整个缓存行更新回主存（即使其它变量没有更改），
  然后cup-2才能够读取，而cup-2可能需要更改这个缓存行的变量与cpu-1已经更改的缓存行中的变量是不一样的，
  所以这相当于给几个毫不相关的变量加了一把同步锁； 
  为了防止伪共享，不同jdk版本实现方式是不一样的： 
1. 在jdk1.7之前会 将需要独占缓存行的变量前后添加一组long类型的变量，依靠这些无意义的数组的填充做到一个变量自己独占一个缓存行； 
2. 在jdk1.7因为jvm会将这些没有用到的变量优化掉，所以采用继承一个声明了好多long变量的类的方式来实现； 
3. 在jdk1.8中通过添加sun.misc.Contended注解来解决这个问题，若要使该注解有效必须在jvm中添加以下参数： 
-XX:-RestrictContended