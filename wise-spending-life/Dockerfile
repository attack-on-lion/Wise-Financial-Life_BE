# ---------- 1) Build stage ----------
FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace

# 캐시 최적화: 의존성 먼저
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle
RUN chmod +x gradlew && ./gradlew --version

# 소스 복사 후 빌드
COPY src ./src
# 테스트는 해커톤 속도 위해 제외. 필요시 -x test 제거
RUN ./gradlew clean bootJar --no-daemon -x test

# 산출물 하나로 고정
RUN ls -l build/libs && \
    JAR=$(ls build/libs | grep -E '\.jar$' | head -n1) && \
    cp build/libs/$JAR /workspace/app.jar

# ---------- 2) Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /workspace/app.jar /app/app.jar

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"

EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
