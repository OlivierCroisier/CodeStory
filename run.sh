#!/bin/bash

echo "Creating some required temp directories..."
mkdir bin
mkdir temp

echo "Compiling the source code..."
javac -sourcepath src -d bin -cp lib/javassist.jar src/main/java/net/thecodersbreakfast/codestory/foobarqix/*.java

echo "Waving hands to unleash some powerful magic and change the whole Java universe as we know it..."
echo "(well, at least the Integer class)"
java -cp lib/javassist.jar:bin net.thecodersbreakfast.codestory.foobarqix.Setup temp

echo "Launching the FooBarQix challenge..."
java -cp bin -Xbootclasspath/p:temp net.thecodersbreakfast.codestory.foobarqix.FooBarQix

echo "Done!"
