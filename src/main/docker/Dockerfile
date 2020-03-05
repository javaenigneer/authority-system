FROM java:8
#VOLUME /tmp
ADD authority-system-0.0.1.jar /demo.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/demo.jar"]