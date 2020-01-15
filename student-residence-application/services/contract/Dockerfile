FROM openjdk:8
FROM gradle:jdk8 AS build-env

#making work directory
RUN mkdir -p /usr/src/app/services/contract

#defining work directory
WORKDIR /usr/src/app/services/contract


#Go to the Project Directory
COPY  ./services/contract /usr/src/app/services/contract
COPY  ./PersistantAccess /usr/src/app/PersistantAccess


#deleting build directory (if exists)
RUN rm -rf /usr/src/app/services/contract/web/build

#building app
RUN gradle build


#exposing port
EXPOSE 8081:8081

#assigning entry point
ENTRYPOINT ["java", "-jar", "/usr/src/app/services/contract/web/build/libs/web-all.jar"]