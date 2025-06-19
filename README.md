# inSoft基线付费软件管理系统

官网：https://insoft.jodoo.cc/ 加群及相关疑难问题看这里。

运行环境：java 11 、maven 3.6.1 、mysql 8.0.21

## 特性

1.支付宝、微信、腾讯对象存储、阿里对象存储、短信、邮件等常用三方能力，可以快速搭建付费软件的管理系统。

2.能够助您快速搭建PC客户端软件、APP、源码、游戏、独立部署软件等付费产品的官网及用户授权验证能力。

3.具备完善的用户管理、用户授权、数据分析、数据收集、版本管理、客户端推送、财务分析等后台系统能力。

## 介绍
基于logYin通用管理系统，开发的付费软件管理系统，包含了付费软件的宣传官网、订单购买、授权等用户能力。

# 项目启动

1.使用maven安装依赖

`mvn install`

2.使用idea运行application文件即可启动web管理系统，查看web端的实际效果。

3.部署使用jar包进行部署，服务器为docker容器。
打包命令
```
mvn clean package
```

运行命令
```
java -jar xxx.jar
```
# controller 控制器目录说明

## 0.api
app端及其他客户端通用api接口存放处
### 结构信息
```
访问路径：/api/*
```

## 1.admin
后台管理系统控制器所在地，主要作用为后台的单体项目的请求处理和视图加载
### 结构信息
```
访问路径：/admin/*
```

## 2.common
说明：公共控制器，包含所有可通用的控制器。

## 3.home
C端用户页面控制器，主要为C端相关的交互页面，默认无任何过滤。

## 4.user
用户中心页面，包含了相对应的用户操作能力及登录状态验证。

### 结构信息
```
访问路径：/*
```

# 操作命令

## 1.maven的命令

1.mvn clean
: 清理项目，删除 target 目录中的编译结果和之前的包文件。

2.mvn compile
: 编译项目源代码。

3.mvn test
: 运行项目中的单元测试。

4.mvn package
: 编译代码并打包成 JAR 或 WAR 文件。

5.mvn install
: 编译、测试、打包，并将包安装到本地仓库。

6.mvn deploy
: 将构建好的包部署到远程仓库。

7.mvn site
: 生成项目文档和报告。

8.mvn clean install
: 常用的连续命令，先清理再编译、测试、打包并安装。

9.mvn versions:set -DnewVersion=
: 更新项目的 maven 版本号。

10.mvn dependency:list
: 列出项目的依赖项。

11.mvn dependency:tree
: 显示项目的依赖树。

12.mvn archetype:generate
: 根据一个 Archetype 模板创建新项目。

13.mvn help:effective-pom
: 显示实际使用的 POM 配置（考虑了父 POM 和配置文件）。

14.mvn eclipse:eclipse
: 为 Eclipse IDE 生成项目配置文件。

15.mvn idea:idea
: 为 IntelliJ IDEA 生成项目配置文件。

16.mvn jetty:run
: 使用 Jetty 插件来启动一个 Web 应用。

17.mvn tomcat:run
: 使用 Tomcat 插件来启动一个 Web 应用。

18.mvn cargo:run
: 使用 Cargo 插件来启动一个 Web 应用，可以集成各种应用服务器。

19.mvn release:prepare
: 准备发布新版本的项目。

20.mvn release:perform
: 完成发布流程，将项目发布到远程仓库。

21.mvn versions:use -DtargetVersion=
: 修改子模块的 maven 版本号。

22.mvn validate
: 验证项目的 POM 文件是否正确。

23.mvn appassembler:generate-daemons
: 为项目生成守护进程脚本。

24.mvn spring-boot:run
: 运行 Spring Boot 应用。

# 常见问题汇总

## 1.api或页面为空的情况
请检查过滤器是否默认为放行，如果没有则修改为放行即可恢复。

## 2.支付sdk配置、email配置、sms短信配置及系统中其他不存在的配置说明
固定配置信息通过手动修改配置文件进行配置，不通过管理系统进行。

## 3.文件上传相关配置说明
配置文件：src\main\java\com\inSoft\config\UploadConfig.java

操作说明：

1.如果我们是win系统则需要再配置文件中配置win，如果是linux则需要配置linux，进行文件存储路径的配置。

2.如果我们使用阿里云oss，那么就配置alioss，如果是腾讯cos即配置tencos，本地则配置local。

3.建议使用云服务，本地存储文件需要配置一个服务关联到文件目录，才可使用相对路径进行访问。

4.腾讯云cos 默认访问图片直接变为下载的主要原因和说明。

```
1.加速域名使用自定义域名
2.开启静态网站业务
3.使用自定义加速域名访问资源，才能达到资源预览效果
```

## 4.日志记录说明
我们已经封装好了日志采集的工具类，您只需要使用我们封装好的采集方法即可实时采集。

### 控制器中使用
```
    saveSysLog("访问了首页",1);
```

## 5.数据统计

### 控制器中使用
```
analysis(1,1);//前者为类型，后者为数字，类型信息在StaticTypeEnum枚举文件中注释，可增加n种
```

### 调用工具类时
```
    @Autowired
    private SysStaticUtil sysStaticUtil;

    //记录统计数据 type对应参数请在枚举中查看
    public void analysis(Integer type,double value)
    {
        String name = StaticTypeEnum.getByCode(type).getValue();
        sysStaticUtil.analysis(type,name,value);
    }
```

### 6.支付宝商户配置说明

首先应用的私钥和支付宝公钥是必须的，同时必须要在线上进行调试，本地报错openid错误时说明已经联通。

需要设置回调地址

### 7.微信支付商户配置说明

需要一个认证的公众号或者小程序，而后进行关联，配置appid和商户信息及apiv2的密钥即可正常联通。

需要支付后台配置ip白名单及回调地址

### 9.客户端权限验证说明

使用api目录下的token验证接口，本地自签token即可，放入header中。

详细在api相关配置文件及工具类中。

token可自动生成。

### 10.跳过测试打包
```
mvn package -DskipTests=true
```