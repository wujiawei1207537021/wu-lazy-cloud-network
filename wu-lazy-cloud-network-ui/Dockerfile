FROM nginx:1.13.0-alpine
COPY  dist /usr/share/nginx/html/

##将nginx配置模板复制过去到指定目录
COPY default.template /etc/nginx/conf.d/

#将docker环境下的命令行路径切换到/etc/nginx/conf.d下执行
WORKDIR /etc/nginx/conf.d
#添加环境变量的写入

ENTRYPOINT envsubst  < default.template > default.conf && cat default.conf && nginx -g 'daemon off;'
