# Uber
Our own implementation of Uber


# How to run and compile:
This repository has inside 3 applications: server, client and driver.

Running application for any version of application demand on user to give IP as an argument

There is also possibility to run application from Dockerfile:
preparation:
  go to /src, from where run

a) for client:
 docker build -t ubuntu_client --target client .

b) for server
   docker build -t ubuntu_client --target server .

after that you can easilly run container with image f.e:
 docker run -ti --rm --name usun ubuntu_client
