### 内网穿透使用

#### 模块说明

| 模块                                                                                     | 所属层级 | 描述                     | 端口                                                             |
|----------------------------------------------------------------------------------------|------|------------------------|----------------------------------------------------------------|
| [middleground-cloud-heartbeat-common](middleground-cloud-heartbeat-common)             | 基础模块 | 基于Netty数据解码、编码、通道处理器声明 | 无                                                              |
| [middleground-on-cloud-heartbeat-server](middleground-on-cloud-heartbeat-server)       | 启动模块 | 内网穿透服务端                | http端口：6001、tcp端口:7001  (默认tcp端口=http端口+1000 如：6001+1000=7001) |
| [middleground-under-cloud-heartbeat-client](middleground-under-cloud-heartbeat-client) | 启动模块 | 内网穿透客户端                | 6004                                                           |

#### 功能

    1.将局域网IP映射到公网IP
    2. 支持tcp、http映射

#### 使用

```text
客户端配置信息
```

```yaml
spring:
  middleground:
    netty:
      inet-host: 127.0.0.1 # 服务端地址
      inet-port: 7001 #服务端端口
      client-id: local # 客户端ID
```

```text
服务端配置客户端映射地址
数据库表【internal_network_penetration_mapping】 添加数据
```

| 客户端ID        | 客户端真实地址        | 客户端真实端口 | 创建时间 | id | 是否删除 | 更新时间 | 访客端口  | 描述                                                         |
|--------------|----------------|---------|------|----|------|------|-------|------------------------------------------------------------|
| local        | 127.0.0.1      | 18080   | null | 1  | 0    | null | 19080 | 访客通过 --> 19080 --> 访问 --> 客户端 local本地的 18080               |
| local        | 127.0.0.1      | 28080   | null | 2  | 0    | null | 29080 | 访客通过 -->  29080 --> 访问 --> 客户端 local本地的 28080              |
| local        | 127.0.0.1      | 3306    | null | 3  | 0    | null | 4306  | 访客通过 -->  4306 --> 访问 --> 客户端 local本地的 3306                |
| local        | 192.168.17.185 | 80      | null | 4  | 0    | null | 30080 | 访客通过 -->  30080 --> 访问 --> 客户端 local局域网内192.168.17.185的 80 |
| middleground | web-nginx      | 80      | null | 5  | 0    | null | 31570 | 访客通过 -->  31570 --> 访问 --> 客户端 local局域网内web-nginx的 80      |



#### 部署

##### 云端部署

```text
云端部署：内网穿透服务端
如果云端需要部署云上暂存+内网穿透功能：需要部署 内网穿透服务端、暂存服务、内网穿透客户端、云上离线网关
```

| 模块                                                                                     | 说明         | 部署内网穿透必须 | 部署内网穿透+云上暂存必须 |
|----------------------------------------------------------------------------------------|------------|----------|---------------|
| [middleground-on-cloud-heartbeat-server](middleground-on-cloud-heartbeat-server)       | 内网穿透+心跳服务端 | ☑️       | ☑️            |
| [middleground-under-cloud-heartbeat-client](middleground-under-cloud-heartbeat-client) | 内网穿透+心跳客户端 | ✖️       | ☑️            |
| [middleground-cloud-staging-provider](middleground-cloud-staging-provider)             | 暂存服务       | ✖️       | ☑️            |
| [middleground-on-cloud-central-gateway](middleground-on-cloud-central-gateway)         | 云上暂存网关     | ✖️       | ☑️            |
| [middleground-under-cloud-central-gateway](middleground-under-cloud-central-gateway)   | 云下暂存网关     | ✖️       | ✖️            |

##### 云网关部署

```text
云网关部署内网穿透客户端
```

| 模块                                                                                     | 说明         | 部署内网穿透必须 | 部署内网穿透+云上暂存必须 |
|----------------------------------------------------------------------------------------|------------|----------|---------------|
| [middleground-on-cloud-heartbeat-server](middleground-on-cloud-heartbeat-server)       | 内网穿透+心跳服务端 | ✖️       | ✖️            |
| [middleground-under-cloud-heartbeat-client](middleground-under-cloud-heartbeat-client) | 内网穿透+心跳客户端 | ✖️       | ☑️            |
| [middleground-cloud-staging-provider](middleground-cloud-staging-provider)             | 暂存服务       | ✖️       | ✖️            |
| [middleground-on-cloud-central-gateway](middleground-on-cloud-central-gateway)         | 云上暂存网关     | ✖️       | ☑️            |
| [middleground-under-cloud-central-gateway](middleground-under-cloud-central-gateway)   | 云下暂存网关     | ✖️       | ✖️            |

##### 独立租户部署

```text
拥有内网穿透能力：需要部署内网穿透客户端
内网穿透+离线暂存能力: 需要部署内网穿透客户端、离线网关、离线暂存服务
```

| 模块                                                                                     | 说明         | 部署内网穿透必须 | 部署内网穿透+云上暂存必须 |
|----------------------------------------------------------------------------------------|------------|----------|---------------|
| [middleground-on-cloud-heartbeat-server](middleground-on-cloud-heartbeat-server)       | 内网穿透+心跳服务端 | ✖️       | ✖️            |
| [middleground-under-cloud-heartbeat-client](middleground-under-cloud-heartbeat-client) | 内网穿透+心跳客户端 | ☑️       | ☑️            |
| [middleground-cloud-staging-provider](middleground-cloud-staging-provider)             | 暂存服务       | ✖️       | ☑️            |
| [middleground-on-cloud-central-gateway](middleground-on-cloud-central-gateway)         | 云上暂存网关     | ✖️       | ✖️            |
| [middleground-under-cloud-central-gateway](middleground-under-cloud-central-gateway)   | 云下暂存网关     | ✖️       | ☑️            |


