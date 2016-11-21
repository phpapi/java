# simba 
一个基于SpringMVC的web框架,初始化脚本在doc目录中，目前只提供了mysql版本，ppt有基本的使用介绍，由于时间关系并未包含所有的功能介绍。
配置环境变量JAVA_HOME到jdk的安装目录，必须使用jdk1.8
直接运行Jetty类的main方法，即可启动web服务
登陆系统的入口: 
http://localhost:8888/simba/login/toLogin.do  
或者
http://localhost:8888/simba/
账号密码是admin admin123 
所有的配置都在configs.properties里
系统默认把登陆拦截和权限拦截关闭了，需要打开的可以在SpringMVC.xml文件中修改
如果大家对框架改进有任何意见，麻烦可以在下载资源页进行留言，或者发送email到我的邮箱loceme.student@163.com
或者大家有什么功能已经开发好了，愿意加到我的框架中，共享给大家一起学习，也可以随时联系我，我会在收到信息之后，尽快回复，谢谢。
如果大家在使用中，发现bug，可以通过邮件截图+描述发送给我，我会在之后的版本修改并发布，如果您修改了bug，也可以把修改的源码发给我，谢谢。
以下是每个版本的日志
1.0.5
从web项目迁移成maven项目
1.0.6
增加菜单框架ext实现，类路径调整
1.0.7
增加http工具类，demo例子
1.0.8
socket工具类，权限组件,菜单组件，jdbc分页支持多种数据库,ant路径工具类,增加jquery easyUI
1.0.9
版本管理,服务根路径工具类,文件上传工具类
1.0.10
集成ueditor在线编辑器
1.0.11
地址联动
1.0.12
Excel工具类 Word工具类  Java NIO实现socket工具类 分布式session jdk升级到1.7 嵌入式redis服务（只支持linux）
1.0.13
修改默认的beanName生成策略,controller参数扩展
1.0.14
分布式session使用zookeeper
1.0.15
zookeeper工具类优化 增加工具类
1.0.16
页面html标志修改 httpclient中文支持 工具类增强(zip,reflect,thread)
1.0.17
ftp服务端和客户端工具类，配置文件maven和web项目路径统一
1.1.0
soapui工具类(web版本) properties等工具类
1.1.1
工具类数据校验 jsp自定义标签 Spring自定义注解 默认requestMapping
1.1.2
代码生成器
1.1.3
首页修改 dateformat.js 时间参数转换 SpringMVC配置文件集中 快递参数接口 
1.1.4
des加解密字符串和文件
1.1.5
redis 加锁，redis升级成2.8.2 freemarker工具类
1.1.6 
spring websocket 实现在线聊天 
maven升级jdk1.8 jetty9.2.4 
web升级jdk1.7 tomcat7
1.1.7(maven only)
包名修改 
从此不再支持web版本，只支持maven版本
1.1.8
jquery 
图片预览插件
图片滚动显示插件
1.1.9
jquery实现鼠标在按钮上显示窗口,离开窗口和按钮时消失
1.1.10
rabbitMQ集成
视频截图
图片缩略图旋转
集成Mybatis
使用数据库连接池druid
dubbo使用
1.1.11
集成Spring Cache,FastJson
Spring Cache增加redis缓存实现
Mybatis使用二级缓存,增加redis实现
增加reactJs
增加Mybatis插件pageHelper,Mapper
1.1.12
使用draft富文本编辑器
增加ant design
代码生成器功能增强
1.1.13
react集成ueditor在线编辑器
增加spring-integration
增加activiti
通过配置自动扩展用户属性
1.1.14
机构管理
通过配置自动扩展机构属性
用户机构关系管理
按照账号全局查询用户
权限改成树形结构
1.1.15
页面优化
代码生成器生成页面（树形+表格结构和表格结构）使用EasyUI实现
注册类型和注册表管理
更新说明ppt
1.2.0
Redis发布订阅功能
集群执行功能
注册表修改通知集群中所有服务器更新
流程管理，启动流程，待办任务，已办任务
流程监控 作业管理 已归档流程
1.2.1
mycat集成
界面配置定时器任务调度，可以监控操作定时器任务
异步框架
1.2.2
Spring Batch
Spring Batch Admin
Spring Mobile可以根据不同的设备跳转到不同的页面
登陆增加验证码
生成二维码(Js和java代码都可执行)
框架取名为simba，辛巴，快速成长成狮子王之意
增加数据库监控菜单，直接使用druid页面
线程变量工具
TODO
流程特送，退回，任务转办，设置流程属性，执行人，发送选择人
在线绘制流程
。。。工作流相关
1.3.0
集成统计图形
1.3.1
集成mongodb，solr
1.3.2
hadoop lucense