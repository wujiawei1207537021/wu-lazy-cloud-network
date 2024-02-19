# DOCKER JDK IMAGE

## BUILD IMAGE

```shell

#docker login --username=1207537021@qq.com registry.cn-hangzhou.aliyuncs.com

mvn clean install 

docker build -t registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-lazy-cloud-heartbeat-server-start:1.2.2-JDK17-SNAPSHOT .
docker push registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-lazy-cloud-heartbeat-server-start:1.2.2-JDK17-SNAPSHOT

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
docker run  -d -it -p 18080:18080 --name wu-lazy-cloud-heartbeat-server-start registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-lazy-cloud-heartbeat-server-start:1.2.2-JDK17-SNAPSHOT

http://127.0.0.1:18080/swagger-ui/index.html


```

