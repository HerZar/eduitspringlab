FROM ubuntu 
RUN apt-get update  &&\
    apt-get -y install software-properties-common &&\
    apt-get -y update &&\
    apt-get -y install wget openjdk-8-jdk
VOLUME /tmp
ADD target/student.crud-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

