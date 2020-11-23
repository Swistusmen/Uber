FROM ubuntu

WORKDIR /root/server_application
COPY CommonDataTypes/CommonDataTypes/DriverData.java /root/server_application/CommonDataTypes
COPY CommonDataTypes/CommonDataTypes/PersonalData.java /root/server_application/CommonDataTypes
COPY CommonDataTypes/CommonDataTypes/RideState.java /root/server_application/CommonDataTypes
COPY src/CommonDataTypes/CommonDataTypes/Ride.java /root/server_application/CommonDataTypes

COPY src/WebServerApplication/WebServerApplication/Server.java /root/server_application/WebServerApplication
COPY src/WebServerApplication/WebServerApplication/ServerCommunicator.java /root/server_application/WebServerApplication

RUN apt-get update
RUN apt-get install -y openjdk-8-jdk openjdk-8-jre