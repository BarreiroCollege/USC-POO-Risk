#!/bin/bash

rm -rf build/
mkdir build/
mkdir build/lib/
find . -type f -name '*.java' ! -name 'RiskGUI.java' ! -path "*gui*" > build/sources.txt
javac -d ./build -cp "*.jar" -encoding UTF-8 @build/sources.txt
cp -r res/* build/
cp -r lib/* build/lib/
jar cvfm build/risk.jar build/META-INF/MANIFEST.MF -C build/ .
# java -jar build/risk.jar
# proguard/bin/proguard.sh @config.pro
