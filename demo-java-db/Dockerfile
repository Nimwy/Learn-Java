FROM ubuntu:24.04

RUN apt-get update \
    && apt-get install -y --no-install-recommends openjdk-25-jdk-headless curl ca-certificates \
    && rm -rf /var/lib/apt/lists/*

RUN curl -fsSL -o /opt/postgresql.jar \
    https://repo1.maven.org/maven2/org/postgresql/postgresql/42.7.4/postgresql-42.7.4.jar

ENV LANG=C.UTF-8
WORKDIR /app
CMD ["java", "-cp", "/opt/postgresql.jar", "src/Main.java"]