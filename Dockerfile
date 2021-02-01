#1
FROM registry.redhat.io/rhel7/rhel


RUN yum install -y java-11-openjdk &&  yum install -y java-11-openjdk-devel && yum clean all

# Set environment variables.
ENV HOME /root

# Get the JAR file 
RUN mkdir /var/clamav-rest
COPY --from=0 /target/clamav-rest-1.0.2.jar /var/clamav-rest/clamav-rest-1.0.2.jar
#COPY target/clamav-1.0.2.jar /var/clamav-rest/

# Define working directory.
WORKDIR /var/clamav-rest/

# Open up the server 
EXPOSE 8080

ADD bootstrap.sh /
ENTRYPOINT ["/bootstrap.sh"]

