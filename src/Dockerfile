FROM ubuntu AS base

RUN apt-get update
RUN apt-get install -y openjdk-8-jdk openjdk-8-jre
COPY CommonDataTypes /root/usr
EXPOSE 45676
EXPOSE 45677
EXPOSE 45678
EXPOSE 45679

#Commands below to create server
FROM base AS server
COPY CommonDataTypes /root/usr
COPY WebServerApplication /root/usr/WebServerApplication

RUN javac /root/usr/CommonDataTypes/*.java 
RUN mv /root/usr/CommonDataTypes/*.class /root/usr/WebServerApplication

RUN javac -cp /root/usr/ /root/usr/WebServerApplication/*.java

RUN mv /root/usr/CommonDataTypes/*.class /root/usr/WebServerApplication
CMD java -cp /root/usr WebServerApplication.Server "192.168.0.31"

#Commands below to create client
FROM base AS client
COPY CommonDataTypes /root/usr
COPY MobileApplication /root/usr/MobileApplication
COPY ClientMobileApplication /root/usr/ClientMobileApplication

RUN javac /root/usr/CommonDataTypes/*.java

RUN mv /root/usr/CommonDataTypes/*.class /root/usr/MobileApplication/

RUN javac -cp /root/usr/ /root/usr/MobileApplication/*.java
RUN mv /root/usr/MobileApplication/*.class /root/usr/ClientMobileApplication

RUN javac -cp /root/usr/ /root/usr/ClientMobileApplication/*.java
CMD java -cp /root/usr/ ClientMobileApplication.ClientMobileApplication "192.168.0.31"
 


