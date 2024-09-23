# IMAGE openscinda/foodex-validator:1.0.2 #
FROM openjdk:11-jdk-slim

# fancy ls -al
RUN printf '#!/bin/sh\n/bin/ls -alh --color $*' > /bin/l && \
	chmod +x /bin/l

# -- ADDITIONAL DEPENDENCIES
RUN apt-get update && apt-get upgrade -y
RUN apt-get install unzip -y

# -- MAVEN

ENV MAVEN_VERSION 3.6.1
ENV MAVEN_HOME /usr/lib/mvn
ENV PATH $MAVEN_HOME/bin:$PATH

ADD https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz apache-maven-$MAVEN_VERSION-bin.tar.gz 

RUN tar -zxvf apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  rm apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  mv apache-maven-$MAVEN_VERSION /usr/lib/mvn

# -- BUILD SCINDA 
  
ENV revision 1.0.2  
ENV SCINDA_HOME /opt/nutridb
ENV SCINDA_REVISION 1.0.0
ENV SCINDA_SOURCE https://github.com/OpenNutritionScience/openscinda/archive/master.zip 

RUN mkdir -p $SCINDA_HOME

ADD $SCINDA_SOURCE $SCINDA_HOME
RUN cd $SCINDA_HOME && unzip master.zip
RUN cd $SCINDA_HOME/openscinda-master && mvn clean install

EXPOSE 8080 

ENV workingDir /opt/openefsa/app 
VOLUME /opt/openefsa
RUN mkdir -p $workingDir


VOLUME $SCINDA_HOME/openscinda-master/foodex-validator
WORKDIR $SCINDA_HOME/openscinda-master/foodex-validator

ENTRYPOINT ["mvn", "spring-boot:run", "-Dfile.separator=/"]
