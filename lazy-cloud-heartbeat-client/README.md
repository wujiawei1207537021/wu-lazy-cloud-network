

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