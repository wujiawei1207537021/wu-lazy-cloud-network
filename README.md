
<p align="center">
    <a target="_blank" href="https://search.maven.org/search?q=wu-lazy-cloud-network%20wu-lazy-cloud-network">
        <img src="https://img.shields.io/nexus/s/top.wu2020/wu-lazy-cloud-network?server=https%3A%2F%2Foss.sonatype.org&style=flat&logo=log" alt="Maven" />
    </a>
    <a target="_blank" href="https://search.maven.org/search?q=wu-lazy-cloud-network%20wu-lazy-cloud-network">
        <img src="https://img.shields.io/maven-central/v/top.wu2020/wu-lazy-cloud-network" alt="Maven" />
    </a>
    <a target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0.txt">
		<img src="https://img.shields.io/:license-Apache2-blue.svg" alt="Apache 2" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-11-green.svg" alt="jdk-11" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-17-green.svg" alt="jdk-17" />
	</a>
    <br />
        <img src="https://img.shields.io/badge/SpringBoot-v3.x-blue">
    <br />
    <a target="_blank" href='https://gitee.com/wujiawei1207537021/wu-lazy-cloud-network'>
		<img src='https://gitee.com/wujiawei1207537021/wu-lazy-cloud-network/badge/star.svg' alt='Gitee star'/>
	</a>

</p>

#### 项目介绍
[wu-lazy-cloud-network](https://gitee.com/wujiawei1207537021/wu-lazy-cloud-network) 是一款基于（[wu-framework-parent](https://gitee.com/wujiawei1207537021/wu-framework-parent)）孵化出的项目，内部使用Lazy ORM操作数据库，主要功能是网络穿透，对于没有公网IP的服务进行公网IP映射
使用环境JDK17 Spring Boot 3.0.2

### 功能
    1.内网穿透
    2.服务端自主下发数据到客户端
    3.流量监控


### 项目结构

| 模块                                                                                                            | 版本                   | 描述                           |
|---------------------------------------------------------------------------------------------------------------|----------------------|------------------------------| 
| [wu-lazy-cloud-heartbeat-common](wu-lazy-cloud-heartbeat-common)                                              | 1.2.2-JDK17-SNAPSHOT | 内网穿透公共模块（声明接口、枚举、常量、适配器、解析器） |
| [wu-lazy-cloud-heartbeat-client](wu-lazy-cloud-heartbeat-client)                                              | 1.2.2-JDK17-SNAPSHOT | 客户端（支持二次开发）                  |
| [wu-lazy-cloud-heartbeat-server](wu-lazy-cloud-heartbeat-server)                                              | 1.2.2-JDK17-SNAPSHOT | 服务端（支持二次开发）                  |
| [wu-lazy-cloud-network-ui](wu-lazy-cloud-network-ui)                                                          | 1.2.2-JDK17-SNAPSHOT | 服务端页面                        |
| [wu-lazy-cloud-heartbeat-client-sample](wu-lazy-cloud-heartbeat-sample/wu-lazy-cloud-heartbeat-client-sample) | 1.2.2-JDK17-SNAPSHOT | 客户端样例                        |
| [wu-lazy-cloud-heartbeat-server-sample](wu-lazy-cloud-heartbeat-sample/wu-lazy-cloud-heartbeat-server-sample) | 1.2.2-JDK17-SNAPSHOT | 服务端样例                        |


### 使用技术

| 框架                                                                                                                                                                          | 版本                   | 描述           |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------|--------------| 
| spring-boot                                                                                                                                                                 | 3.0.7                | springboot框架 |
| [wu-framework-web](https://gitee.com/wujiawei1207537021/wu-framework-parent/tree/master/wu-framework-web)                                                                   | 1.2.2-JDK17-SNAPSHOT | web容器        |
| [Lazy -ORM](https://gitee.com/wujiawei1207537021/wu-framework-parent/tree/master/wu-inner-intergration/wu-database-parent)                                                  | 1.2.2-JDK17-SNAPSHOT | ORM          |
| mysql-connector-j                                                                                                                                                           | 8.0.33               | mysql驱动      |
| [wu-authorization-server-platform-starter](https://gitee.com/wujiawei1207537021/wu-framework-parent/tree/master/wu-smart-platform/wu-authorization-server-platform-starter) | 1.2.2-JDK17-SNAPSHOT | 用户授权体系       |

### 使用环境
    IDEA
    Mac、Windows
    JAVA >=13
    MAVEN

### 启动
    docker启动
        
        docker run  -d -it -p 18080:18080 --name wu-lazy-cloud-heartbeat-server registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-lazy-cloud-heartbeat-server:1.2.2-JDK17-SNAPSHOT
        
        http://127.0.0.1:18080/swagger-ui/index.html

    源码启动
#### 页面操作

启动项目后打开服务端界面
![img.png](url_info.png)


默认账号密码：admin/admin
![img.png](login.png)

初始化项目
![img.png](init_menu.png)
添加角色
![img.png](init_role.png)
为用户授权
![img.png](authRoe2User.png)

刷新页面
![img.png](cloud_client.png)

客户端管理（客户端会自动注册）
![img.png](cloud_client.png)

网络映射管理（修改后者新增需要映射的客户端）
![img.png](mapping.png)

访客端口池管理（服务器端需要开放的端口）
![img.png](visitor_port.png)

流量管理（每个客户端使用的流量）
![img.png](flow.png)