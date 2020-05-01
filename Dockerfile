FROM openjdk:11.0.2-jdk
ENV TZ=Asia/Bangkok
RUN mkdir fileData
RUN mkdir /opt/conf
COPY ./build/libs/Application-0.0.1-SNAPSHOT.jar thongkao-api.jar
ENTRYPOINT ["java","-Dspring.config.location=classpath:/,file:/opt/conf/application.properties","-jar","thongkao-api.jar"]