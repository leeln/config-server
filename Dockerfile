FROM java:8-jre-alpine

ENV PROFILES dev
ENV VERSION 0.0.1-SNAPSHOT

ENV HOSTNAME localhost
ENV SERVER_NAME config-server
ENV PORT 8080

ENV CONFIG_SERVER_ENABLED false
ENV CONFIG_SERVER_URL http://config.leeln.com

ENV EUREKA_SERVER_ENABLED true
ENV EUREKA_SERVER_URL http://172.18.139.140:8761/eureka/

ADD build/libs/${SERVER_NAME}-${VERSION}.jar /opt/app/app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILES}", "/opt/app/app.jar"]