FROM openjdk:17

EXPOSE 8080

ADD target/kafka-spring-boot.jar kafka-spring-boot.jar

ENTRYPOINT ["java", "-jar", "kafka-spring-boot.jar"]

