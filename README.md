# Assignment2
Distributed System Assignment2. For convenient, the weather information contain 8 elements.
Use Gson library for json parsing and Junit for testing.


#Compile
Use ***make build*** to compile all java files
Also can use ***make clean*** to clear all exist java files before we build.

#Run
Use ***make launch-aggregationserver*** to start the aggregation server
The port number in code is 6971, and the server url is "http://localhost:6971/".

Use ***make launch-contentserver server_name = "xxx" file_path = "yyy"*** to start a content server.
server_name is aggregation server url, and file_path is weather information file's path.
Like: make launch-contentserver server_name = "http://localhost:6971/" file_path = "data/weather1.txt"

Use ***make launch-getclient server_name = "xxx" port_number = "yyy"*** to start a client,the client will get the latest weather information.
port_number ia optional, because server_name can include port number.
Like: make launch-getclient server_name = "http://localhost:6971/"

#Test
Use ***make build-test*** to compile the test part.
Use ***make auto_test*** to run auto test.
