BUILD_DIR = ./src/build/

all: build

build:
	mkdir -p $(BUILD_DIR);
	javac -d $(BUILD_DIR) -cp lib/gson-2.10.1.jar src/WeatherInfo.java src/LamportClock.java src/AggregationServer.java src/ContentServer.java src/GETClient.java;


launch-aggregationserver:
	java -cp "src/build:lib/gson-2.10.1.jar" AggregationServer;

launch-contentserver:
	java -cp "src/build:lib/gson-2.10.1.jar" ContentServer $(server_name) $(file_path);

launch-getclient:
	java -cp "src/build:lib/gson-2.10.1.jar" GETClient $(server_name) $(port_number);

build-test:
	javac -d $(BUILD_DIR) -cp "src/build:lib/gson-2.10.1.jar:lib/junit-4.12.jar" test/Auto_Test.java

auto-test:
	java -cp "src/build:lib/gson-2.10.1.jar:lib/junit-4.12.jar" Auto_Test