#!/bin/bash

rm -rf build/
mkdir build/
find . -type f -name '*.java' > build/sources.txt
javac -d ./build -encoding UTF-8 @build/sources.txt
cp -r res/META-INF/ build/
jar cvfm build/risk.jar build/META-INF/MANIFEST.MF -C build/ .
# java -jar build/risk.jar
# proguard/bin/proguard.sh @config.pro
