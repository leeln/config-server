FROM leeln/java:jre-8

ENV PROFILES dev

COPY ssh root/.ssh

COPY build/libs/*.jar /opt/app/app.jar

RUN apk upgrade --update  && \
    apk add --no-cache openssh-client && \
    chmod 700 -R root/.ssh && \
    rm -rf /tmp/* /var/tmp/* /var/cache/*

HEALTHCHECK --interval=5m --timeout=10s \
  CMD curl -f http://localhost:8080/admin/health || exit 1

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILES}", "/opt/app/app.jar"]