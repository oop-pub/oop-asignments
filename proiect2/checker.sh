#!/bin/bash
rm -rf result
rm -rf bin
rm -f sources.txt
find src/ -name "*.java" > sources.txt
javac -cp "lib/*:.jar" -p lib/lombok.jar -sourcepath src @sources.txt -d bin
java -cp ".:bin/:lib/jackson-annotations-2.13.3.jar:lib/jackson-core-2.13.3.jar:lib/jackson-databind-2.13.3.jar:lib/lombok.jar" Test
rm -rf result
rm -rf bin
rm -f sources.txt
