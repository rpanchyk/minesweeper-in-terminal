build:
	mvn clean package

run:
	java -jar target/msit-1.0-SNAPSHOT.jar

all: build run
