#!/bin/bash

find -name "*.java" > sources.txt
javac -d ./build @sources.txt
cp -r res/* build/
jar cvfm build/risk.jar build/META-INF/MANIFEST.MF -C build/ .
java -jar build/risk.jar