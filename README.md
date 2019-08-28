# Hybrid Cloud Framework 使用说明

1. 结构介绍
2. 功能&原理介绍
3. 操作手册

---

### 结构介绍
![hybrid-cloud-framework（bodhi） (4).jpg](http://note.youdao.com/yws/res/5009/WEBRESOURCE189a1c946ff4196a5c8c86455d518e0d)


##### hybrid-cloud-norms:「标准规范」
```hybrid-cloud-norms 约定了框架的使用规范，根据约定上层框架实现具体功能```  

**主要规范**：
- BodhiApplication ：应用标识接口
- BodhiApi ：开放Api标识接口
- ApplicationContext ：应用容器标准接口
- BodhiBean ：容器存放的对象类型
- LifeCycle ：生命周期约定
- @BodhiProvider ：应用层注入标识
- Serializer ：序列化约定
- BodhiEvent ：事件约定
- BodhiListener ：监听约定
- BodhiEventPublisher ：事件发布
- BodhiValidator ：校验器约定
- ValidatorActuator ：校验链执行器
- BodhiException ：标准错误

---

##### hybrid-cloud-utils ：「框架工具类」
- ASMUtils 字节码增强工具类
- StringUtils 字符串处理工具类
- ServiceLoadUtils SPI工具类
- LoadCondition 加载筛选接口

---

##### hybrid-cloud-context ：「核心容器」
```hybrid-cloud-cotext实现了hybrid-cloud-norms 中约定的接口```
- AbstractApplicationContext ：实现了==ApplicationContext==以及==LifeCycle==接口，提供基础的容器功能
- ApplicationContextFactory ：容器工厂类
- ApplicationInterceptor : 提供容器初始化拦截器功能，子类可以通过实现ApplicationInterceptor接口对容器Bean进行再加工，DefaultInterceptor是默认实现
- @Order ：排序注解，用于拦截器排序

---

##### hybrid-cloud-platform ：「平台管理」
```hybrid-cloud-platform 用于管理ApplicationContext的声明周期：启动、加载、停机、刷新```
- PlatformBootstrap : 平台启动引导
- PlatformManager ： 平台管理类，初始化、启动、停机、刷新在此管理
- PathMenu ： 文件目录枚举，定义平台监控路径
- ClassLoaderFactory ： 类加载器工厂类
- FilePathBuilder ： 文件路径构建器

---

##### hybrid-cloud-internet ：「网络交互」
```hybrid-cloud-internet 用户网络交互功能，提供HttpClient Api支持，注解转发支持 ```

- PlatformStartedListener ：平台启动监听器，用于初始化ClientHolder，请求/响应拦截器初始化
- HttpProxyInterceptor ：ApplicationInterceptor拦截器的实现类，用于生成代理对象
- HttpMethodInterceptor ：CGLIB代理切面
- HttpProxyFactory ： 代理工厂
- StrategySelector ： 代理策略选择器
- OKHttpConfigure ：OkHttp初始化配置类
- Client ：Client接口规范
- AbstractHttpClient ：抽象Client类，定义了Client执行模版
- DefaultHttpClient ：继承AbstractHttpClient，默认Relay注解的请求会使用此HttpClient，会执行过滤器
- SimpleHttpClient ：无过滤器、无序列化的Client
- @Relay ：转发标记注解，支持前置转发，后置转发，跳跃转发，前置转发返回值会存放到ThreadLoad中。
- @Header、@Param 用于Get请求对象中字段的标记，根据注解写入请求头、请求体，Post请求无用

---

##### hybrid-cloud-adapter : 「核心适配」
``` hybrid-cloud-adapter用于处理底层各模块之间的依赖关系，简化上层应用的使用 ```

- AbstractBodhiApplication ： 向上提供简单的Application抽象类，基础该类的子类可以简单使用client api
- ApplicationHelper ：提供便捷的Applicaiton获取支持
- ConfigHelper ： 提供全局配置的便捷获取支持
- BodhiBootstrap ：提供便捷的平台启动支持

---

##### hybrid-cloud-support-spring ：「Spring支持」 
```hybrid-cloud-support-spring 提供自动配置、路由自动化注册功能，以及标准输出定义 ```
- ApiResult ： 标准返回格式定义
- BodhiApplicationContextInitializer ：spring-boot启动容器初始化事件
- BootstrapAutoConfiguration ：自动配置实现
- MappingHelper ：RequestMappingInfo生成工具类
- WebApplicationContext ：继承RelayApplicationContext 用于路由注册，Spring容器BeanDefine注册
- DefaultController ：提供低级默认路由，版本更新接口
- Dispatcher ：公有云转发支持

---

##### hybrid-cloud-application-starter ：「Jar应用依赖」
``` pom文件依赖hybrid-cloud-adapter，为jar包依赖的应用提供支持 ```



##### hybrid-cloud-client-stater ：「Web应用依赖」
``` pom文件依赖hybrid-cloud-support-spring，为web应用提供支持```


---

### 功能&原理介绍
```Hybrid Cloud Framework 核心功能为服务不停机的热更新功能，这个功能的初衷是为了可以为混合用提供持续集成/持续交付的支持```  


#### 原理：   
##### Part 1：加载  
基于ClassLoader的双亲委派模型的加载机制，不同的ClassLoader加载的类是不同的，根据这个特性服务启动后==PlatformManager==会创建一个指定目录==的BodhiClassLoader==，通过这个==BodhiClassloader==使用Java原生的SPI机制将指定接口的实现类加载到==ApplicaitonContext==容器中，然后经过初始化拦截器对这些==BodhiApplication==进行再加工，子类继承重写init（）实现各自容器的功能增强。

##### Part 2: 刷新
根据指定Version与baesPath去PathMenu枚举定义的路径下查找可使用的jar包，默认约定路径为 ==./bestsign/version/ver_number/xxxxxx.jar== 例如： ==./bestsign/version/1.0.0/demo-application.jar== 如果版本号为空则默认加载最新版本；创建一个新的Classloader，这个Classloader指向了新的文件路径，使用新的Classloader创建一个新的ApplicationContext容器，然后使用新的容器替换掉旧的容器，由于在替换中会有一部分正在允许的线程，所以旧的容器延迟回收关闭；


##### Part 3 : 路由
由于提供Web支持的Controller不在默认启动程序的ClassPath下，所以需要启动时自行手动加载，SpringMVC在启动时会初始化一个RequestMappingHandlerMapping的Bean，这个Bean提供了两个重要的方法==registerMapping==与==unregisterMapping==，通过这两个方法实现路由的自动注入

---

### 操作手册
#### Application部分 ：   
> 记得先依赖hybrid-cloud-application

1. 先创建一个业务接口
```
/**
 * @program: hybrid-cloud-framework
 * @description: 有 @BodhiProvider 注解的接口实现类才会被容器管理并刷新，没有就是一个普通接口无法热加载
 * @author: Maxxx.Yg
 * @create: 2019-03-18 16:38
 **/
@BodhiProvider
public interface DemoApplication {

    void voidAction();

    void relayAction();

    String stringAction(String param);

    DemoEntity entityAction(DemoEntity entity);

    String patchAction();
}
```

2. 实现这个接口

```
/**
 * @program: hybrid-cloud-framework
 * @description: 需要继承 AbstractBodhiApplication 类 ，或者自己实现 BodhiApplication 接口
 * @author: Maxxx.Yg
 * @create: 2019-03-18 16:38
 **/
public class DemoApplicationImpl extends AbstractBodhiApplication implements DemoApplication,OtherApplication {

    @Override
    public void voidAction() {
        System.out.println("void action");
    }

    @Override
    @Relay(path = "/test/relay",relayTiming = RelayTiming.AFTER,responseType = ResponseType.VOID)
    public void relayAction() {
        System.out.println("relay action");
    }

    @Override
    public String stringAction(String param) {
        return "welcome " + param;
    }

    @Override
    public DemoEntity entityAction(DemoEntity entity) {
        return entity;
    }

    @Override
    public String patchAction() {
        return "i'm patch";
    }

    @Override
    public void nothing() {
        System.out.println("nothing ..");
    }

}
```
3. 为使用者提供一个便捷的工具类

```
public final class ApplicationMenu {

    private ApplicationMenu(){

    }

    public static DemoApplication demoApplication(){
        return ApplicationHelper.getApp(DemoApplication.class);
    }

    public static OtherApplication otherApplication(){
        return ApplicationHelper.getApp(OtherApplication.class);
    }

    public static ProxyApplication proxyApplication(){
        return ApplicationHelper.getApp(ProxyApplication.class);
    }
}
```

4. 在META-INF/services/目录下创建一个文件，文件名：==cn.bestsign.bodhi.hybrid.norms.BodhiApplication==这个就是BodhiApplication类的全名称，文件里面的内容是这个接口的实现类全名称

```
cn.bestsign.bodhi.hybrid.samples.application.business.impl.DemoApplicationImpl
```

---

#### Web Client部分：
> 需要依赖hybrid-cloud-client-starter

1. 写一个Controller，这个Controller要实现BodhiApi接口

```
/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 16:56
 **/
@RestController
@RequestMapping("/demo")
public class DemoController implements BodhiApi{

    @GetMapping("/void")
    public void voidAction() {
        ApplicationMenu.demoApplication().voidAction();
    }

    @GetMapping("/relay")
    public String relayAction() {
        ApplicationMenu.demoApplication().relayAction();
        return "relay";
    }

    @GetMapping("/string/{param}")
    public String stringAction(@PathVariable("param") String param) {
        return ApplicationMenu.demoApplication().stringAction(param);
    }

    @PostMapping("/entity")
    public DemoEntity entityAction(@RequestBody DemoEntity entity) {
        return ApplicationMenu.demoApplication().entityAction(entity);
    }

    @GetMapping("/nothing")
    public String nothing() {
        ApplicationMenu.otherApplication().nothing();
        return "nothing";
    }

    @GetMapping("/patch")
    public String patch(){
        return ApplicationMenu.demoApplication().patchAction();
    }

    @GetMapping("/proxy")
    public String proxy(){
        return ApplicationMenu.proxyApplication().proxy();
    }
}

```

2. 在META-INF/services/目录下创建一个文件文件名：==cn.bestsign.bodhi.hybrid.norms.BodhiApi==这个就是BodhiApi接口的全名称，在文件里面写入上面的Controller的全名称

```
cn.bestsign.bodhi.hybrid.samples.client.DemoController
```

---

#### boot 引导部分
> 只需依赖hybrid-cloud-client-starter，简单配置下application.yml 配置文件

1. 启动类
```
/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 17:06
 **/
@SpringBootApplication
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }

}

```

2. 配置文件：

```
bestsign:
  # 用于指定加载版本
  version: 1.0.0 
  client:
    # 配置请求远程公有云地址
    host: http://localhost:9009
```


#### PS：
> 基于Hybrid Cloud Framework 还可以做的事情：提供即插即用的功能，客户可以在上上签平台在线购买服务，在线签约，签约完毕后服务立即可用，无需重新部署。

##### 说明
这个项目设计的初衷是为了做到PAAS服务的效果。
即：在远程的客户端运行一个空壳项目，然后项目启动后从远程服务器获取可执行代码文件，然后在本地服务进行加载。

- 项目设想：https://www.processon.com/view/link/5c7bdfc2e4b043f594cfb4e9
- 项目结构：https://www.processon.com/view/link/5c8a277ee4b0d1a5b0fe577d

不想将这个耗费了大量心血的项目埋没到硬盘中丢掉，分享给大家。

当然这个项目还没有最终完全开发完毕，有需求的朋友可以联系我。提供文档共同改造。
