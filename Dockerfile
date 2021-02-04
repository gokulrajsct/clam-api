#1
FROM registry.redhat.io/jboss-webserver-5/webserver54-openjdk11-tomcat9-openshift-rhel7


ENV HOME /root

# Get the JAR file 
#RUN mkdir /var/clamav-rest
COPY ./clamav-rest-1.0.2.jar /var/clamav-rest/clamav-rest-1.0.2.jar
#COPY target/clamav-1.0.2.jar /var/clamav-rest/

# Define working directory.
WORKDIR /var/clamav-rest/

# Open up the server 
EXPOSE 8080

ADD bootstrap.sh /
RUN chmod +x /bootstrap.sh
ENTRYPOINT ["/bootstrap.sh"]
