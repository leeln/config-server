FROM docker.repo.leeln.com/leeln/java:jre-8

ENV PROFILES default

COPY build/libs/*.jar /opt/app/app.jar

HEALTHCHECK --interval=5m --timeout=10s \
  CMD curl -f http://localhost:8080/admin/health || exit 1

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILES}", "/opt/app/app.jar"]