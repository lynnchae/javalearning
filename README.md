# Java Learning

## JAVA学习过程中的一些简单代码

#### Design Pattern 

* `factory desgin`

* `singleton desgin`

* `decorator`

* `adapter`

* `proxy`

* `strategy`

##### Framework

+ `plugin chain simple implement`

    >  根据mybatis简单实现的一个pluginChain
 
    >  简陋版，会针对所有的方法进行拦截

+ `spring mvc 1.0`

    >  简单实现springMVC定位、加载、注册过程

+ `filters`

    >  通过匿名内部类创建FilterChain，并对匿名内部类有个深入的理解
    
        外部类方法中传入匿名内部类的变量，匿名内部类实际上持有了该变量的一个拷贝，如果对此拷贝进行改变，
        不会反应到方法中，而对于开发者而言，看到的是同一个对象，所以不能保持同步修改，故方法中的变量需要定义为final.
      
+ `spring mvc 2.0`

    >  实现springMVC定位、加载、注册过程
    
    >  实现handlerAdapter,HandlerMapping,Aop,DispatchServlet...
    
    >  可以通过url进行访问
    
+  `aop拦截`
      
       protected Object invokeJoinpoint() throws Throwable {
            return AopUtils.invokeJoinpointUsingReflection(this.target, this.method, this.arguments);
       }
      > 
        aop对Service类中的a() b()方法均配置的拦截，当a()内部调用b()，无法做到对b的拦截
        invokeJoinpoint()中传递的对象为目标对象，而不是被aop代理过的对象，即this.target
        调用b()方法时，并没有过代理对象来执行，所以无法拦截。如果需要在a()方法中调用b()方法，并且对b()进行拦截，
        则需要获取到Service类的代理对象来进行调用，((Service)AopContext.currentProxy()) -> b()
        
        