# Use the official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /src

# Copy required client files to /src/client in the container
COPY client /src/client

# COPY server interface files to /src/server in the container
COPY server /src/server

# COPY scripts to /src folder in the container
COPY data-population-script.txt /src/data-population-script.txt
COPY operations-script.txt /src/operations-script.txt
# Compile the Java code for both server and client if necessary
RUN javac -d . /src/client/Client.java

# Specify the command to run the client application
# The client expects four arguments, so these will be passed as runtime arguments
ENTRYPOINT ["java", "client.Client"]
