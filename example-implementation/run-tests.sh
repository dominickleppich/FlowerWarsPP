#!/bin/bash
if [ -n "$1" ];
then
    echo -e "Board class name: $1"


    JAVA_FILE=$(echo -e "$1" | tr . /)
    JAVA_FILE=$(echo -e "$JAVA_FILE.java")
    echo -e "Compile Board class: $JAVA_FILE"
    $(javac $JAVA_FILE)

    echo -e "Run tests ..."
    java -cp .:FlowerWarsPP-Tester.jar BoardTester $1 $2

else
    echo -e "Wrong usage! Run \"sh run-tests.sh <FULL-PATH-TO-BOARD-CLASS>\""
fi