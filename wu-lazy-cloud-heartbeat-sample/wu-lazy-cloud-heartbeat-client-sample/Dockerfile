FROM registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-framework-parent:jdk-17.0.7-x64
MAINTAINER wujiawei <1207537021@qq.com>

RUN echo "Asia/Shanghai" > /etc/timezone



ENV APP_JAR_NAME=$APP_NAME \
    JAVA_OPTS="-Xms512m -Xmx1024m -Djava.security.egd=file:/dev/./urandom" \
    PARAMS=""


COPY target/*.jar /app.jar



ENTRYPOINT exec java -server $JAVA_OPTS  -jar /app.jar $PARAMS





