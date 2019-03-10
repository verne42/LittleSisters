FROM openjdk:8-jre
MAINTAINER Verne42 <support@verne42.com>

COPY data/ /opt/Verne42/oley/data/
COPY target/Verne42LittleSisterOley.jar /opt/Verne42/oley/Verne42LittleSisterOley.jar

WORKDIR /opt/Verne42/oley
VOLUME /tmp
ENTRYPOINT ["java","-Xms512m","-Xmx8192m","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","-Dspring.data.neo4j.password=${Neo4j_Password}","-Dspring.data.neo4j.uri=${Neo4j_url}","-Doley.csv.root.dir=${OLEY_CSV_ROOT}","-jar","Verne42LittleSisterOley.jar"]

