#!/bin/bash
echo -e "Compile Board class"
javac flowerwarspp/MyBoard.java

echo -e "Run tests ..."
java -cp FlowerWarsPP-BoardTester.jar:. BoardTester flowerwarspp.MyBoard