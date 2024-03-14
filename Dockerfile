FROM azul/zulu-openjdk:21
WORKDIR /app

COPY ./target/*.jar /app/*.jar
COPY ./.env /app/.env

EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/app/*.jar"]