#!/bin/bash

mkdir -p build/classes
mkdir -p docs

javac -d build/classes src/main/java/HeapSort.java \
                       src/main/java/Main.java
javadoc -d docs src/main/java/HeapSort.java \
                src/main/java/Main.java

java -cp build/classes Main