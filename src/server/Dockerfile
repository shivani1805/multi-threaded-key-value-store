# Use the official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /src/server

# Copy server source files to /src/server in the container
COPY . /src/server

# Compile the Java code for both server and client if necessary
RUN javac -d . /src/server/ServerInterface.java /src/server/ServerImpl.java

# Expose the port that the server will run on
EXPOSE 1024

# Command to run the server, allowing arguments to be passed in for the port and service name
ENTRYPOINT ["java", "server.ServerImpl"]

