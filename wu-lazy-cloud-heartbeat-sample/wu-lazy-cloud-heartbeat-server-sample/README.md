# DOCKER JDK IMAGE

## BUILD IMAGE

```shell

#docker login --username=1207537021@qq.com registry.cn-hangzhou.aliyuncs.com

docker build -t registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-lazy-cloud-heartbeat-server:server-jdk17-master .
docker push registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-lazy-cloud-heartbeat-server:server-jdk17-master

```

```shell
gu install native-image

gu list

mvn native:build
```

```shell
 mvn clean native:compile -Pnative
```

```RUN
docker run  -d -it -p 18080:18080 --name wu-lazy-cloud-heartbeat-server registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-lazy-cloud-heartbeat-server:server-jdk17-master

http://127.0.0.1:18080/swagger-ui/index.html


```

