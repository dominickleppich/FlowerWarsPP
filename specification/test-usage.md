# Verwendung der automatisierten Tests
Die Implementation des Spielbretts kann recht einfach auf logische Fehler überprüft werden. Hierzu steht das Programm
`BoardTester` zur Verfügung, welches im Archiv [`FlowerWarsPP-BoardTester.jar`](../FlowerWarsPP-BoardTester.jar) zur 
Verfügung steht.

Eine kompilierte Spielbrettklasse kann wie folgt getestet werden:
```
java -cp PROJECT_CLASSPATH:FlowerWarsPP-BoardTester.jar BoardTester FULL_PATH_TO_BOARD_CLASS [PARAMETER]
```

## Parameter
Der Parameter `[PARAMETER]` ist optional. Wird er nicht gesetzt, werden final nur die bestandenen Tests gezählt.
Mögliche Optionen für diesen Parameter sind `mini` und `full`.

Bei `mini` werden alle fehlgeschlagenen Testfälle mit ihrem Namen aufgelistet.

Der Parameter `full` gibt vollständige Informationen über den fehlgeschlagenen Testfall inklusive vollem Stacktrace.

## Shell-Skript
Zur einfachen Verwendung steht ein kleines Shell-Skript namens [`run-tests.sh`](../example-implementation/run-tests.sh) zur Verfügung, welches in der [Beispielimplementation](../example-implementation)
zu finden ist. 

Das Skript kompiliert die Board Klasse automatisch und führt danach die Tests aus. Ein Aufruf dieses Skripts funktioniert wie folgt:
```
sh run-tests.sh FULL_PATH_TO_BOARD_CLASS [PARAMETER]
``` 

### Beispiel
Anhand einer einfachen [Beispielimplementation](../example-implementation) soll die Ausführung der Tests kurz gezeigt werden.

1. Kompiliere die Spielbrettklasse [`flowerwarspp.MyBoard`](../example-implementation/flowerwarspp/MyBoard.java)
```
javac flowerwarspp/MyBoard.java
```
2. Starte die Tests
```
java -cp .:FlowerWarsPP-BoardTester.jar BoardTester flowerwarspp.MyBoard
```
3. Das Ergebnis sieht wie folgt aus:
```
Starting tests...

..................................................
..................................................
..................................................
..................................................
..................................................
..................................................
..................................................
..................................................
..................................................
..................................................
.....

505 tests executed in 0.047 s
Success:    8 (  1.6%)
Failed:   497 ( 98.4%)
```
