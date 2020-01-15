FROM openjdk:8
FROM gradle:jdk8 AS build-env

#making work directory
RUN mkdir -p /usr/src/app/services/authentication

#defining work directory
WORKDIR /usr/src/app/services/authentication


#Go to the Project Directory
COPY  ./services/authentication /usr/src/app/services/authentication
COPY  ./PersistantAccess /usr/src/app/PersistantAccess


#deleting build directory (if exists)
RUN rm -rf /usr/src/app/services/authentication/web/build

#building app
RUN gradle build


#exposing port
EXPOSE 8080:8080

#assigning entry point
ENTRYPOINT ["java", "-jar", "/usr/src/app/services/authentication/web/build/libs/web-all.jar"]