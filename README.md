# Java Learning

---

## 1. Design Pattern 


### 1.1 Factory desgin


### 1.2 Singleton desgin


### 1.3 Decorator


### 1.4 Adapter
 >  装饰器与适配器模式都有一个别名叫包装模式(Wrapper)，它们的作用看似都是起到包装一个类或对象的作用，
    但是使用它们的目的不一样。
    适配器模式的意义是将一个接口转变成另一个接口，通过改变接口达到重复使用的目的；
    而装饰器模式不是要改变被装饰对象的接口，而恰恰要保持原有的接口，但增强原有对象的功能，
    或者改变原有对象的处理方法而提高性能。



### 1.5 Proxy

```java
public static Object newProxyInstance(ClassLoader loader,
                  Class<?>[] interfaces,
                      InvocationHandler h)
```
 > JDK动态代理，创建一个com.sun.proxy.$Proxy0类，继承Proxy，动态实现interfaces接口，通过父类Proxy构造器
   Constructor(InvocationHandler h)，实例化一个interfaces类型的对象，最后通过h.invoke反射调用对应的方法。

---

|代理方式|实现|优点|缺点|特点|
|:-----|:-----|:-----|:-----|:-----|
|JDK动态代理|代理类与委托类实现同一接口，主要是通过代理类实现InvocationHandler，并重写invoke方法来进行动态代理的，在invoke方法中将对方法进行增强处理|不需要硬编码接口，代码复用率高|只能够代理实现了接口的委托类|底层使用反射机制进行方法的调用|
|CGLIB动态代理|代理类将委托类作为自己的父类并为其中的非final委托方法创建两个方法， 一个是与委托方法签名相同的方法，它在方法中会通过super调用委托方法；另一个是代理类独有的方法。在代理方法中，它会判断是否存在实现了MethodInterceptor接口的对象，若存在则将调用intercept方法对委托方法进行代理|可以在运行时对类或者是接口进行增强操作，且委托类无需实现接口|不能对final类以及final方法进行代理|底层将方法全部存入一个数组中，通过数组索引直接进行方法调用|

***Cglib***

```java
    //final修饰的类和方法不能被继承和修改
    public class UserLog$$EnhancerByCGLIB$$9a9593ca extends UserLog implements Factory{
        //...
        final void CGLIB$doLog$0() {
            super.doLog();
        }
    
        public final void doLog() {
            MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
            if (this.CGLIB$CALLBACK_0 == null) {
                CGLIB$BIND_CALLBACKS(this);
                var10000 = this.CGLIB$CALLBACK_0;
            }
    
            if (var10000 != null) {
                var10000.intercept(this, CGLIB$doLog$0$Method, CGLIB$emptyArgs, CGLIB$doLog$0$Proxy);
            } else {
                super.doLog();
            }
        }
        //...
    }
    
     private static class FastClassInfo{
            FastClass f1; // org.lynn.designPattern.proxy.cglib.UserLog的fastclass
            FastClass f2; // UserLog$$EnhancerByCGLIB$$9a9593ca 的fastclass
            int i1; //方法doLog在f1中的索引
            int i2; //方法CGLIB$doLog$0在f2中的索引
     }
     
     public class MethodProxy {
         //...
         public Object invokeSuper(Object obj, Object[] args) throws Throwable {
             try {
                 this.init();
                 MethodProxy.FastClassInfo fci = this.fastClassInfo;
                 return fci.f2.invoke(fci.i2, obj, args);
             } catch (InvocationTargetException var4) {
                 throw var4.getTargetException();
             }
         }
         //...
     }
     //使用proxy.invokeSuper(obj,args)方法，就是执行原始类的方法。
     // 还有一个方法proxy.invoke(obj,args)，这是执行生成子类的方法。
     // 如果传入的obj就是子类的话，会发生内存溢出，因为子类的方法不挺地进入intercept方法，
     // 而这个方法又去调用子类的方法，两个方法直接循环调用了。
```

    cglib采用了FastClass的机制来实现对被拦截方法的调用。FastClass机制就是对一个类的方法建立索引，通过索引来直接调用相应的方法。

---

### 1.6 Strategy

### 1.7 TemplateMethod

模板方法设计模式

### 1.7 SnowFlake
 > Twitter分布式自增id算法(64位)

|正数|当前时间戳 - 开始时间戳（41位）|数据中心标识（5位）|机器标识（5位）|自增序列（12位）|
|:-----|:-----|:-----|:-----|:-----|
|0| 0000000000 0000000000 0000000000 0000000000 0 | 00000 | 00000 | 000000000000|

---

## 2. Framework

### 2.1 plugin chain simple implement

>  根据mybatis简单实现的一个pluginChain

>  简陋版，会针对所有的方法进行拦截



### 2.2 spring mvc 1.0

>  简单实现springMVC定位、加载、注册过程



### 2.3 filters

>  通过匿名内部类创建FilterChain，并对匿名内部类有个深入的理解

>  外部类方法中传入匿名内部类的变量，匿名内部类实际上持有了该变量的一个拷贝，如果对此拷贝进行改变，
   不会反应到方法中，而对于开发者而言，看到的是同一个对象，所以不能保持同步修改，故方法中的变量需要定义为final.


​      
### 2.4 spring mvc 2.0

>  实现springMVC定位、加载、注册过程

>  实现handlerAdapter,HandlerMapping,Aop,DispatchServlet...

>  可以通过url进行访问


​       
### 2.5 aop拦截


```Java
  protected Object invokeJoinpoint() throws Throwable {
      return AopUtils.invokeJoinpointUsingReflection(this.target, this.method, this.arguments);
  }
```
  > aop对Service类中的a() b()方法均配置的拦截，当a()内部调用b()，无法做到对b的拦截
    invokeJoinpoint()中传递的对象为目标对象，而不是被aop代理过的对象，即this.target
    调用b()方法时，并没有过代理对象来执行，所以无法拦截。如果需要在a()方法中调用b()方法，并且对b()进行拦截，
    则需要获取到Service类的代理对象来进行调用，((Service)AopContext.currentProxy()) -> b()

  > <aop:config expose-proxy="true" proxy-target-class="false"/>
    需要配置此项，暴露代理对象，实现线程内共享，使用ThreadLocal模式

  > AbstractAutoProxyCreator实现了BeanPostProcessor接口，spring容器初始化bean后，调用postProcessAfterInitialization
    对bean进行wrapIfNecessary，创建一个aop的proxy对象。

       ProxyFactory -> aopProxy -> getProxy
                                    <- JDKDynamicAopProxy
                                    <- CglibAopProxy
       ProxyFactory保存了aop拦截的配置信息                    

  > AbstractAutoProxyCreator实现了Ordered接口，并将期顺序设置为Ordered.LOWEST_PRECEDENCE，最低优先级
    保证aop后置处理器最后调用


### 2.6 Bean的初始化扩展方法

***init-method，afterPropertiesSet和BeanPostProcessor***

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

### 2.7 Spring Annotation 

#### @Component 注解的“派生”

  > `@Controller`  `@Service`  `@Repository`均为`@Component`注解的派生，类似注解的继承关系

#### @Resource & @Autowire

 + @Resource默认是按照名称来装配注入的，只有当找不到与名称匹配的bean才会按照类型来装配注入；

 + @Autowired默认是按照类型装配注入的，如果想按照名称来转配注入，则需要结合@Qualifier一起使用；

 + @Resource注解是又J2EE提供，而@Autowired是由Spring提供，故减少系统对spring的依赖建议使用@Resource的方式；

 + @Resource和@Autowired都可以书写标注在字段或者该字段的setter方法之上
   
### 2.8 Spring MVC

+ 通过 `org.springframework.web.servlet.FrameworkServlet.ContextRefreshListener` 监听`ContextRefreshedEvent` 调用 `org.springframework.web.servlet.DispatcherServlet#onRefresh` 初始化以下内容

```java
initMultipartResolver(context);
initLocaleResolver(context);
initThemeResolver(context);
initHandlerMappings(context);
initHandlerAdapters(context);
initHandlerExceptionResolvers(context);
initRequestToViewNameTranslator(context);
initViewResolvers(context);
initFlashMapManager(context);
```

 + HandlerMapping 定位
    + 遍历容器中所有的bean，(isHandler())找到@Controller 或者 @RequestMapping 注解的bean，处理成HandlerMethod，注册到mappingRegistry
    + HandlerAdapter，为了适配handler并统一返回ModelAndView对象，通过HandlerAdapter调用HandlerMethod
  ```java
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
  ```

+ mappingRegistry
  + registry(HashMap)
    + <RequestMappingInfo, MappingRegistration>
      + `MappingRegistration`
        + mapping
        + handlerMethod
  + mappingLookup
  + urlLookup
  + nameLookup


### 2.9 Spring Transaction
+ `@Configuration` ProxyTransactionManagementConfiguration
  + 自动装配配置类 ProxyTransactionManagementConfiguration，这个类首先注入了AnnotationTransactionAttributeSource，用来读取解析 @Transactional注解，获取需要进行事务管理的方法，并将相关的事务管理配置的参数暴露给Spring。
  + 注入TransactionInterceptor：基于AOP MethodInterceptor (Advice)实现的声明式事务管理，内部依赖于TransactionManager，TransactionManager是实际的事务管理对象。
  + 注入BeanFactoryTransactionAttributeSourceAdvisor：由AnnotationTransactionAttributeSource驱动的AOP Advisor，用于为@Transactional注解的方法添加一个事务advice通知
+ 解析标签 `<tx:annotation-driven/>`，org.springframework.transaction.config.AnnotationDrivenBeanDefinitionParser.AopAutoProxyConfigurer#**configureAutoProxyCreator** 将**AnnotationTransactionAttributeSource**、**BeanFactoryTransactionAttributeSourceAdvisor**解析为BeanDefinition注册到容器中，初始化**BeanFactoryTransactionAttributeSourceAdvisor**时，会将**AnnotationTransactionAttributeSource**初始化并进行注入
---

## 3. Concurrent

### 3.1 先++  后++

   +  先++：先运算，后使用
   
   +  后++：先使用，后运算
         
```java
    String[] names = {"jack","tom","lily"};
    int index = 0;
    System.out.println(names[index ++]);//输出jack
    System.out.println(names[index]);//输出tom
    System.out.println(names[++index]);//输出lily
```


### 3.2 Lock

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

### 3.3 JVM内置锁的膨胀

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

[JAVA锁的膨胀过程和优化](https://www.cnblogs.com/dsj2016/p/5714921.html)
+ 加锁的过程：JVM在当前线程的栈帧中创建用于储存锁记录的空间（LockRecord），然后把MarkWord放进去，同时生成一个叫Owner的指针指向那个被加锁的对象，用CAS尝试把对象头的MarkWord替换成一个指向锁记录（LockRecord）的指针。
  成功了就拿到了锁。那么失败了呢？
  《深入理解JVM》的说法：失败了，去查看MarkWord的值。有2种可能：1，指向当前线程的指针，2，别的值。
  - 如果是1，那么说明发生了“重入”的情况，直接当做成功获得锁处理。其实这个有个疑问，为什么获得锁成功了而CAS失败了，这里其实要牵扯到CAS的具体过程：先比较某个值是不是预测的值，是的话就动用原子操作交换（或赋值），否则不操作直接返回失败。在用CAS的时候期待的值是其原本的MarkWord。
    发生“重入”的时候会发现其值不是期待的原本的MarkWord，而是一个指针，所以当然就返回失败，但是如果这个指针指向这个线程，那么说明其实已经获得了锁，不过是再进入一次。

   - 如果是2，那么发生了竞争，锁会膨胀为一个重量级锁（MutexLock）

  《并发编程的艺术》的说法：失败了直接自旋。期望在自旋的时间内获得锁，如果还是不能获得，那么开始膨胀，修改锁的MarkWord改为重量级锁的指针，并且阻塞自己。

+ 解锁过程：当前持有锁的线程，用CAS把MarkWord换回到原来的对象头，如果成功，那么没有竞争发生，解锁完成。
  如果失败，表示存在竞争（之前有线程试图通过CAS修改MarkWord，即MarkWord被修改为了重量级锁），这时要释放锁并且唤醒阻塞的线程。

**重量级锁**：
重量级锁在轻量级锁自旋超过一定的次数，或者一个线程在持有锁，一个在自旋，又有第三个来访时，
轻量级锁膨胀为重量级锁，重量级锁使除了拥有锁的线程以外的线程都阻塞，防止CPU空转。

### 3.4 锁优化

* **减少锁的时间**

不需要同步执行的代码，能不放在同步快里面执行就不要放在同步快内，可以让锁尽快释放；

*  **减少锁的粒度**

它的思想是将物理上的一个锁，拆成逻辑上的多个锁，增加并行度，从而降低锁竞争。它的思想也是用空间来换时间；

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
  使用volatile+cas操作会是非常高效的选择；

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

## 4.MySql

### 4.1 B-tree & B+tree

[漫画B+树](https://www.sohu.com/a/156886901_479559)

**B+树和B树相比，主要的不同点在以下3项：**

+ 内部节点中，关键字的个数与其子树的个数相同，不像B树，子树的个数总比关键字个数多1个。
+ 所有指向文件的关键字及其指针都在叶子节点中，不像B树，有的指向文件的关键字是在内部节点中。
  换句话说，B+树中，内部节点仅仅起到索引的作用。
+ 在搜索过程中，如果查询和内部节点的关键字一致，那么搜索过程不停止，而是继续向下搜索这个分支。

#### B+tree 优势

> B+树的磁盘读写代价更低

    B+树的内部结点并没有指向关键字具体信息的指针。
    因此其内部结点相对B树更小。如果把所有同一内部结点的关键字存放在同一盘块中，
    那么盘块所能容纳的关键字数量也越多。一次性读入内存中的需要查找的关键字也就越多。
    相对来说I/O读写次数也就降低了。

> B+树的查询效率更加稳定

    由于内部结点并不是最终指向文件内容的结点，而只是叶子结点中关键字的索引。
    所以任何关键字的查找必须走一条从根结点到叶子结点的路。所有关键字查询的路径长度相同，
    导致每一个数据的查询效率相当。

> B+树更有利于对数据库的扫描 

    B树在提高了磁盘IO性能的同时并没有解决元素遍历的效率低下的问题，
    而B+树只需要遍历叶子节点就可以解决对全部关键字信息的扫描，
    所以对于数据库中频繁使用的range query，B+树有着更高的性能。

### 4.2 MySql

#### 1. MyISAM （非聚集索引）

   + MyISAM引擎使用B+Tree作为索引结构，叶结点的data域存放的是数据记录的地址
    
   + MyISAM索引文件和数据文件是分离的，索引文件仅保存数据记录的地址
    
   + 主索引和辅助索引（Secondary key）在结构上没有任何区别，只是主索引要求key是唯一的，而辅助索引的key可以重复。

#### 2. InnoDB （聚集索引）

   + InnoDB的数据文件本身就是索引文件，InnoDB要求表必须有主键（MyISAM可以没有）
   
   + InnoDB的辅助索引data域存储相应记录主键的值而不是地址，
     辅助索引搜索需要检索两遍索引：首先检索辅助索引获得主键，然后用主键到主索引中检索获得记录。
     
#### 3. MySql **Lock** & **Transaction**

  > InnoDB行锁是通过给索引上的索引项加锁来实现的，这一点MySQL与Oracle不同，
  后者是通过在数据块中，对相应数据行加锁来实现的。InnoDB这种行锁实现特点意味着：
  只有通过索引条件检索数据，InnoDB才使用行级锁，否则InnoDB将使用表锁，在实际开发中应当注意。

共享锁又称为读锁，简称S锁，共享锁就是多个事务对于同一数据可以共享一把锁，都能访问到数据，但是只能读不能修改。

排他锁又称为写锁，简称X锁，排他锁就是不能与其他所并存，如一个事务获取了一个数据行的排他锁，
   其他事务就不能再获取该行的其他锁，包括共享锁和排他锁，但是获取排他锁的事务是可以对数据就行读取和修改。

#### 4.事务（原子性、一致性、隔离性、持久性）隔离级别
   1. 脏读：在一个事务处理过程里读取了另一个未提交的事务中的数据。
   
   2. 不可重复读：指在对于数据库中的某个数据，一个事务范围内多次查询却返回了不同的数据值，
      这是由于在查询间隔，被另一个事务修改并提交了。
      
   3. 幻读：事务非独立执行时发生的一种现象。
      例如事务T1对一个表中所有的行的某个数据项做了从“1”修改为“2”的操作，
      这时事务T2又对这个表中插入了一行数据项，而这个数据项的数值还是为“1”并且提交给数据库。
      而操作事务T1的用户如果再查看刚刚修改的数据，会发现还有一行没有修改，
      其实这行是从事务T2中添加的，就好像产生幻觉一样，这就是发生了幻读。

   ① Serializable (串行化)：可避免脏读、不可重复读、幻读的发生。
    
   ② Repeatable read (可重复读)：可避免脏读、不可重复读的发生。（Mysql默认隔离级别）
    
   ③ Read committed (读已提交)：可避免脏读的发生。
    
   ④ Read uncommitted (读未提交)：最低级别，任何情况都无法保证。

## 5. JDK

### 5.1 ConcurrentHashMap

   > 1.7 采用(Segment extends ReentrantLock)[] + HashEntry[] 的分段锁技术来实现同步

   > 1.8 采用Synchronized & CAS 来实现并发同步，并且使用与HashMap相同的数据结构 **Node数组 + 链表 + 红黑树**


### 5.2 Throwable

Error 和 Exception均继承自Throwable

+ Error : 程序无法处理的错误，表示运行应用程序中较严重问题。大多数错误与代码编写者执行的操作无关，而表示代码运行时 JVM（Java 虚拟机）出现的问题。

+ Exception : 程序本身可以处理的异常。
  + Unchecked Exception: 
    + 指的是程序的瑕疵或逻辑错误，并且在运行时无法恢复。 
    + 包括Error与RuntimeException及其子类，如：OutOfMemoryError, UndeclaredThrowableException, IllegalArgumentException, IllegalMonitorStateException, NullPointerException, IllegalStateException, IndexOutOfBoundsException等。 
    + 语法上不需要声明抛出异常。 

  + Checked Exception: 
    + 代表程序不能直接控制的无效外界情况（如用户输入，数据库问题，网络异常，文件丢失等） 
    + 除了Error和RuntimeException及其子类之外，如：ClassNotFoundException, NamingException, ServletException, SQLException, IOException等。 
    + 需要try catch处理或throws声明抛出异常。 

### 5.3 ***equals & ==***

+ 对于基本数据类型，“==”比较的是两者的值是否相等。

+ 对于引用数据类型

   + “==”比较的是引用的地址是否相同（即是否是堆内的同一个对象）；Object中的.equals()方法和"==’功能一样

   + String类中的.equals()方法重写了，比较的是两个引用对象的内容是否想同（即是否是完全相同的汽车（注意，有两辆汽车，且一模一样，完全相同））。

### 5.4 ***object.wait() && Thread.sleep()***

+ wait只能在同步（synchronize）环境中被调用，而sleep不需要。
+ 进入wait状态的线程能够被notify和notifyAll线程唤醒，但是进入sleeping状态的线程不能被notify方法唤醒。
+ wait通常有条件地执行，线程会一直处于wait状态，直到某个条件变为真。但是sleep仅仅让你的线程进入睡眠状态。
+ wait方法在进入wait状态的时候会释放对象的锁，但是sleep方法不会。
+ wait方法是针对一个被同步代码块加锁的对象，而sleep是针对一个线程。

### 5.5 Collection & Map

#### Collection：单列集合的根接口
+ List：元素有序  可重复 
  + ArrayList：类似一个长度可变的数组 。适合查询，不适合增删
    + ArrayList实现了RandomAccess接口，使用for循环遍历比iterator效率高
  + LinkedList：底层是双向循环链表。适合增删，不适合查询。
    + 遍历效率与linkedlist相反，使用iterator效率高
+ Set：元素无序，不可重复
  + HashSet：根据对象的哈希值确定元素在集合中的位置
  + TreeSet: 以二叉树的方式存储元素，实现了对集合中的元素排序
#### Map：双列集合的根接口，用于存储具有键（key）、值（value）映射关系的元素。
+ HashMap：用于存储键值映射关系，不能出现重复的键key，key和value允许null
+ TreeMap：用来存储键值映射关系，不能出现重复的键key，所有的键按照二叉树的方式排列

|Hashtable|HashMap|
|:----|:----|
|方法是同步的|方法是非同步的|
|基于Dictionary类|基于AbstractMap，而AbstractMap基于Map接口的实现|
|key和value都不允许为null，遇到null，直接返回 NullPointerException|key和value都允许为null，遇到key为null的时候，调用putForNullKey方法进行处理，而对value没有处理|
|hash数组默认大小是11，扩充方式是old*2+1|hash数组的默认大小是16，而且一定是2的指数|

### 5.6 浅拷贝(ShallowClone) & 深拷贝(DeepClone)

**浅拷贝**
> 浅拷贝是按位拷贝对象，它会创建一个新对象，这个对象有着原始对象属性值的一份精确拷贝。
如果属性是基本类型，拷贝的就是基本类型的值；
如果属性是内存地址（引用类型），拷贝的就是内存地址 ，因此如果其中一个对象改变了这个地址，就会影响到另一个对象。

 **深拷贝**
> 深拷贝会拷贝所有的属性，并拷贝属性指向的动态分配的内存。当对象和它所引用的对象一起拷贝时即发生深拷贝。

+ 通过序列化、反序列化方式实现对象的深拷贝
+ transient属性无法实现拷贝


### 5.7 CountDownLatch

+ 通过内部类Sync extends AbstractQueueSynchronizer共享锁实现
+ countDownLatch.await()，进入线程等待队列，假设t1，t2进入
  + head -> t1 -> t2(tail)
+ countDown() 将 state-1，直到state=0时，准备释放共享锁，唤醒head节点next节点中的线程，`AbstractQueuedSynchronizer#releaseShared()` -> `AbstractQueuedSynchronizer#doReleaseShared()`->`unparkSuccessor()`，将t1设置为队列head节点，并将头节点thread设置为null，唤醒t1，t2.await()自旋获取共享锁成功后，head.next.thread.unpark()唤醒线程t2

### 5.8 CyclicBarrier

+ 通过ReentrantLock排他锁 & Condition实现
+ CyclicBarrier.await()，进入Condition条件队列，线程阻塞
+ 等到所有线程都到达屏障，count为0，Condition.signalAll
```java
        //java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject
        public final void await() throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();
            //创建新节点，添加到条件队列
            Node node = addConditionWaiter();
            //释放锁
            int savedState = fullyRelease(node);
            int interruptMode = 0;
            //如果不是在获取锁的阻塞队列中，阻塞当前线程
            while (!isOnSyncQueue(node)) {
                LockSupport.park(this);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            //尝试获取锁
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null) // clean up if cancelled
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
        }
```

### 5.9位运算

+ 与（&）：全1出1
+ 或（|）：有1出1
+ 异或（^）：值不同为1

```java

    int i = 11;
    System.out.println(Integer.toBinaryString(11));
    System.out.println(Integer.toBinaryString(7));
    //11对8取余数，只需要跟（ 11 & 2^3-1） 与运算即可
    System.out.println(11 & 7);
    //获取2^3-1位运算
    System.out.println(~(-1 << 3));
    
```

## 6. Distribution System

### 6.1 CAP 
+ C（一致性）：所有的节点上的数据时刻保持同步
+ A（可用性）：每个请求都能接受到一个响应，无论响应成功或失败
+ P（分区容错）：系统应该能持续提供服务，即使系统内部有消息丢失（分区）

### 6.2 [DUBBO](http://dubbo.apache.org/#!/?lang=zh-cn)

#### FailCluster
|Feature|Strength|Problem|
|:-----:|:-----|:-----|
|FailOver|失败自动切换，当出现失败，重试其它服务器，通常用于读操作（推荐使用）|重试会带来更长延迟|
|FailFast|快速失败，只发起一次调用，失败立即报错,通常用于非幂等性的写操作|如果有机器正在重启，可能会出现调用失败|
|FailBack|失败自动恢复，后台记录失败请求，定时重发，通常用于消息通知操作|不可靠，重启丢失|
|FailSafe|失败安全，出现异常时，直接忽略，通常用于写入审计日志等操作|调用信息丢失|
|Forking|并行调用多个服务器，只要一个成功即返回，通常用于实时性要求较高的读操作|需要浪费更多服务资源|
|Broadcast|广播调用所有提供者，逐个调用，任意一台报错则报错，通常用于更新提供方本地状态|速度慢，任意一台报错则报错|
