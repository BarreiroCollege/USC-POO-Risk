#!/bin/bash

rm -rf build/
mkdir build/
find . -name "*.java" > build/sources.txt
javac -d ./build @build/sources.txt
cp -r res/* build/
jar cvfm build/risk.jar build/META-INF/MANIFEST.MF -C build/ .
# java -jar build/risk.jar
# proguard/bin/proguard.sh @config.pro
