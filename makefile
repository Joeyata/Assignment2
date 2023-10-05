BUILD_DIR = ./src/build/

all: clean build

build:
	mkdir -p $(BUILD_DIR);
	javac -d $(BUILD_DIR) -cp lib/gson-2.10.1.jar src/WeatherInfo.java src/LamportClock.java src/AggregationServer.java src/ContentServer.java src/GETClient.java;

clean:
	rm -rf $(BUILD_DIR);

launch-aggregationserver:
	java -cp "src/build;lib/gson-2.10.1.jar" AggregationServer;

launch-contentserver:
	java -cp "src/build;lib/gson-2.10.1.jar" ContentServer $(server_name) $(file_path);

launch-getclient:
	java -cp "src/build;lib/gson-2.10.1.jar" GETClient $(server_name) $(port_number);