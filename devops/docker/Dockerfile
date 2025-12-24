FROM --platform=linux/amd64 eclipse-temurin:25-alpine

WORKDIR /opt/app

COPY maven/finance.jar /opt/app

CMD ["java", "-jar", "finance.jar"]

EXPOSE 8055

