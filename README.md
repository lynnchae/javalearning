# Java Learning

### Design Pattern 

---

#### factory desgin

---

#### singleton desgin

---

#### decorator

---

#### adapter
 >  装饰器与适配器模式都有一个别名叫包装模式(Wrapper)，它们的作用看似都是起到包装一个类或对象的作用，
    但是使用它们的目的不一样。
    适配器模式的意义是将一个接口转变成另一个接口，通过改变接口达到重复使用的目的；
    而装饰器模式不是要改变被装饰对象的接口，而恰恰要保持原有的接口，但增强原有对象的功能，
    或者改变原有对象的处理方法而提高性能。
 
---

#### proxy
        
```java
public static Object newProxyInstance(ClassLoader loader,
                  Class<?>[] interfaces,
                      InvocationHandler h)
```
 > JDK动态代理，创建一个com.sun.proxy.$Proxy0类，继承Proxy，动态实现interfaces接口，通过父类Proxy构造器
   Constructor(InvocationHandler h)，实例化一个interfaces类型的对象，最后通过h.invoke反射调用对应的方法。


|代理方式|实现|优点|缺点|特点
|:-----|:-----|:-----|:-----|:-----|
|JDK动态代理|代理类与委托类实现同一接口，主要是通过代理类实现InvocationHandler，并重写invoke方法来进行动态代理的，在invoke方法中将对方法进行增强处理|不需要硬编码接口，代码复用率高|只能够代理实现了接口的委托类|底层使用反射机制进行方法的调用|
|CGLIB动态代理|代理类将委托类作为自己的父类并为其中的非final委托方法创建两个方法， 一个是与委托方法签名相同的方法，它在方法中会通过super调用委托方法；另一个是代理类独有的方法。在代理方法中，它会判断是否存在实现了MethodInterceptor接口的对象，若存在则将调用intercept方法对委托方法进行代理|可以在运行时对类或者是接口进行增强操作，且委托类无需实现接口|不能对final类以及final方法进行代理|底层将方法全部存入一个数组中，通过数组索引直接进行方法调用|

---

#### strategy

---

### Framework

#### plugin chain simple implement

>  根据mybatis简单实现的一个pluginChain

>  简陋版，会针对所有的方法进行拦截

---

#### spring mvc 1.0

>  简单实现springMVC定位、加载、注册过程

---

#### filters

>  通过匿名内部类创建FilterChain，并对匿名内部类有个深入的理解
    
>  外部类方法中传入匿名内部类的变量，匿名内部类实际上持有了该变量的一个拷贝，如果对此拷贝进行改变，
   不会反应到方法中，而对于开发者而言，看到的是同一个对象，所以不能保持同步修改，故方法中的变量需要定义为final.
      
---
      
#### spring mvc 2.0

>  实现springMVC定位、加载、注册过程
    
>  实现handlerAdapter,HandlerMapping,Aop,DispatchServlet...
    
>  可以通过url进行访问
  
---
       
#### aop拦截
      
      
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
     
---
   
#### init-method，afterPropertiesSet和BeanPostProcessor

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
  
#### 先++  后++
    
   +  先++：先运算，后使用
   
   +  后++：先使用，后运算
         
```java
    String[] names = {"jack","tom","lily"};
    int index = 0;
    System.out.println(names[index ++]);//输出jack
    System.out.println(names[index]);//输出tom
    System.out.println(names[++index]);//输出lily
```

        
