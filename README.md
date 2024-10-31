# Multi-threaded Key-Value Store

## Steps to run Server through Terminal -
 1. Open terminal in the src folder.
 2. Use javac command to compile the file - `javac server/*.java`
 3. Use the command to specify the port number and a server name and run the TCPServer.- `java server/ServerImpl <port-number> <server-name>`
 4. The Server starts and. The key store stores a key of type string and value also of type string.
 5. The Server then awaits the client connection.

## Steps to close Server through Terminal -
1. Press `Ctrl + C`

## Steps to run Client through Terminal -
1. Open terminal in the src folder.
2. Use javac command to compile the file - `javac client/*.java`
3. Use the command to specify the port number, hostname of the server, a client name and the server name to which you are connecting (the server name should be the same that you specified while starting the server) and run the Client - `java client/Client <Hostname> <port-number> <server-name> <client-name>`
4. The TCPClient starts and pre-populates the key value store using the data-population-script.txt. 
5. The user needs to input `run` to run the 15 GET, PUT and DELETE Operations through a script, or input `console` to run the commands manually. The operations are run using the `operations-script.txt` in the res folder. The user can change the commands in this script file if they want to run any other operations.


## Steps to close Client through Terminal -
1. The user can input `close` to exit.

## Steps to run Server & Client on Docker -
1. Create a docker network using the command - docker network create my-network
2. Create docker image for the server - open the terminal in the server folder and run the command in the terminal - docker build -t <server-image-name> .
3. Run the docker image of the server using the command - docker run -d --network my-network --name <specify-a-name-for-the-network> -p <localport>:<docker-port> <server-image-name> <server-port-number> <server-name> 
4. Create docker image for the client - open the terminal in src folder and run the following commands in the terminal -
5. SERVER_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <name-specified-for-the-network)
6. docker build -t <client-image-name> .
7. Run the client docker image - docker run -it --network my-network <client-image-name> $SERVER_IP <port-number> <server-name> <client-name>
8. For the client - The user needs to input `run` to run the 15 GET, PUT and DELETE Operations through a script, or input `console` to run the commands manually. The operations are run using the `operations-script.txt` in the res folder. The user can change the commands in this script file if they want to run any other operations.
9. To exit the client type `close`. 


## NOTE - The dockerfile in the src folder is the client's dockerfile. The server's dockerfile is located in the server folder.