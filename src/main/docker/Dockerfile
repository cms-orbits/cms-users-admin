FROM openjdk:11-jre

COPY cmsusers.jar /

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cmsusers.jar"]
