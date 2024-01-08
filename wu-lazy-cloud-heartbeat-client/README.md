

#### 构建native 镜像
```shell
mvn clean compile
mvn spring-boot:process-aot -Pnative

mvn native:build -Pnative
```
### 构建docker镜像
```shell
docker build -t docker-registry.laihui.com/middleground/middleground-under-cloud-heartbeat-client:middleground-2.4.2-native-SNAPSHOT_latest -f Native-Dockerfile .
docker push docker-registry.laihui.com/middleground/middleground-under-cloud-heartbeat-client:middleground-2.4.2-native-SNAPSHOT_latest
```


## BUILD IMAGE

```shell

#docker login --username=1207537021@qq.com registry.cn-hangzhou.aliyuncs.com

docker build -t registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-lazy-cloud-heartbeat-client:server-jdk17-master .
docker push registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-lazy-cloud-heartbeat-client:server-jdk17-master

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
docker run  -d -it -p 18080:18080 --name wu-lazy-cloud-heartbeat-client registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-lazy-cloud-heartbeat-client:server-jdk17-master

http://127.0.0.1:18080/swagger-ui/index.html


```