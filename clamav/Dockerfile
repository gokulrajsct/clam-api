#0
FROM maven:latest as builder
COPY . .
RUN mvn install -DskipTests
RUN find / | grep clamav-.*.jar

#1
FROM centos:centos7


RUN yum update -y && yum install -y java-11-openjdk &&  yum install -y java-11-openjdk-devel && yum clean all

# Set environment variables.
ENV HOME /root

# Get the JAR file 
RUN mkdir /var/clamav-rest
COPY --from=0 /target/clamav-1.0.2.jar /var/clamav-rest/clamav-1.0.2.jar
#COPY target/clamav-1.0.2.jar /var/clamav-rest/

# Define working directory.
WORKDIR /var/clamav-rest/

# Open up the server 
EXPOSE 8080

ADD bootstrap.sh /
ENTRYPOINT ["/bootstrap.sh"]

