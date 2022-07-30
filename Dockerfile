FROM openjdk:8
EXPOSE 8080
ADD target/demo-0.0.1-SNAPSHOT.jar /app.jar
CMD java -jar -DredisIp=10.101.242.68 /app.jar