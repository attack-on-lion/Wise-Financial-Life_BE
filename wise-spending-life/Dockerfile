# =======================
# 1) Build stage
# =======================
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Gradle 캐시를 최대한 활용하기 위해, 의존성 정의 파일들 먼저 복사
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle
# (있으면) gradle.properties도 복사
# COPY gradle.properties ./

RUN chmod +x gradlew

# 의존성만 먼저 받기 (소스 없이도 가능)
# --no-daemon: CI/컨테이너 환경 권장, 캐시 정확도↑
RUN ./gradlew dependencies --no-daemon || true

# 실제 소스 복사 후 빌드
COPY src ./src
# 테스트는 해커톤 속도 우선이면 제외
RUN ./gradlew clean bootJar --no-daemon -x test

# =======================
# 2) Runtime stage
# =======================
FROM eclipse-temurin:17-jre
WORKDIR /app

# 타임존(옵션)
ENV TZ=Asia/Seoul

# 실행 JAR만 런타임 이미지로 복사
COPY --from=build /app/build/libs/*-SNAPSHOT.jar /app/app.jar

# 컨테이너 메모리 기반 튜닝 (필요시 조정)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"

# Spring Boot 기본 포트
EXPOSE 8080

# (옵션) 헬스체크: Actuator 사용 시 활성화
# HEALTHCHECK --interval=30s --timeout=3s --retries=3 CMD wget -qO- http://localhost:8080/actuator/health || exit 1

# 프로필 등 환경변수로 주입 가능
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
