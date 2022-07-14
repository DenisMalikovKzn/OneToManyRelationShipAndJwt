FROM maven:3.8.6-jdk-8 as builder
WORKDIR /opt/app

COPY . /opt/app
ENV MAVEN_OPTS="-Xmx1024m"
RUN mvn clean package -DskipTests

FROM openjdk:8-jdk

WORKDIR /opt/app

COPY --from=builder /opt/app/target/cms-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch cms-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","cms-0.0.1-SNAPSHOT.jar"]
