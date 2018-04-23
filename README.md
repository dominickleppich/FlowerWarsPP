# DiavoloPP
DiavoloPP ist eine Variation des Spiels [Ponte del Diavolo](https://www.brettspielnetz.de/spielregeln/ponte+del+diavolo.php).

# Das Spielbrett

Gespielt wird auf einem dreieckigen Spielbrett mit einer Seitenlänge `3 <= n <= 30`.
Die Gitterpunkte, im Folgenden **Punkte** genannt, werden mit Spalten- und Zeilenkoordinaten versehen, wobei unten links die 
Koordinate `1,1` liegt. 

Hier ist ein Spielbrett der Größe `n = 3` dargestellt:

```
                   1,4
                   /\
                  /  \
                 /    \
                /      \
               /        \
             1,3_________2,3
             /\          /\
            /  \        /  \
           /    \      /    \
          /      \    /      \
         /        \  /        \
       1,2_________2,2_________3,2
       /\          /\          /\
      /  \        /  \        /  \
     /    \      /    \      /    \
    /      \    /      \    /      \
   /        \  /        \  /        \
 1,1_________2,1_________3,1_________4,1
```

Die Hauptbestandteile des Spieles sind eingeschlossene Flächen und Kanten dieses Spielbretts,
welche im Folgenden Land und Brücken genannt werden. 

Ein Land ist hierbei stets durch ein Tripel an umliegenden Punkten gegeben, eine Brücke
durch Paare von Start- und Endpunkten.

Hier ist wieder ein Spielbrett der Größe `n = 3` gegeben, `+` stellt den weißen Spieler und 
`*` den roten Spieler dar:

- Der rote Spieler hat ein Land auf (`1,1`, `2,1`, `1,2`)
- Der weiße Spieler hat Ländereien auf (`1,3`, `2,3`, `2,2`) und (`3,1`, `4,1`, `3,2`)
- Der weiße Spieler hat eine Brücke von `2,2` nach `3,1`, mit der seine beiden Länder verbunden werden

```
                   /\
                  /  \
                 /    \
                /      \
               /        \
              /__________\
             /\          /\
            /  \  ++++  /  \
           /    \  ++  /    \
          /      \    /      \
         /        \  /        \
        /__________\/__________\
       /\          /+          /\
      /  \        /  +        /  \
     /    \      /    +      /    \
    /  **  \    /      +    /  ++  \
   /  ****  \  /        +  /  ++++  \
  /__________\/__________+/__________\
```

# Zugregeln
- Der weiße Spieler hat den ersten Zug, danach wird abwechselnd gezogen.
- Nach dem ersten Zug ist der rote Spieler an der Reihe und hat einmalig die Möglichkeit 
sich zu entscheiden, mit dem weißen Spieler die Farben zu tauschen. Entscheidet er sich dafür, übernimmt
er die soeben gesetzten weißen Steine und spielt fortan als weißer Spieler, sein Zug ist beendet. Danach
ist der ehemals weiße Spieler als roter Spieler an der Reihe und spielt fortan als roter Spieler.
- In einem Zug muss der Spieler sich entscheiden ob er **zwei** Länder *oder* **eine** Brücke 
setzen möchte.
    - Die beiden Ländereien können beliebig auf dem Spielbrett positioniert werden, müssen jedoch
    auf leeren Feldern unter Einhaltung der nachfolgenden Regeln für Territorien, Inseln und Brücken
    gebaut werden.
    - Brücken müssen immer zwei eigene Ländereien verbinden, genauer zwei Eckpunkte eigener Ländereien.
    
## Inseln
- Eine Ansammlung von `4` gleichfarbigen Ländereien, die sich an den Seiten berühren, nennt man
eine **Insel**. Eine Insel besteht immer aus exakt `4` Ländereien.
- Eine Ansammlung von `1`, `2` oder `3` Ländereien nennt man ein **Territorium**. Ein Territorium
kann nie `5` Ländereien oder mehr umfassen.
- Eine Insel darf weder mit einer anderen eigenen Insel noch mit einem anderen eigenen Territorium
gemeinsame Eckpunkte haben. Das heißt in anderen Worten, dass Inseln eigene Inseln oder Territorien nicht 
über eine gleiche Seite berühren dürfen (weil dadurch automatisch die Inselgröße überschritten würde) und
auch nicht über einen gemeinsamen Eckpunkt.
- Territorien dürfen sich über Eckpunkte berühren.
- Territorien dürfen nur dann zu einer Insel ausgebaut werden, wenn ...
    - ... sie dann nicht mehr als `4` Ländereien enthalten.
    - ... sie dann keine weiteren eigenen Inseln oder Territorien berühren.
- Diese Abstandsregeln gelten nicht für Territorien und Inseln unterschiedlicher Farbe. Territorien und Inseln
unterschiedlicher Farbe dürfen beliebig nebeneinander liegen oder sich über Eckpunkte berühren.

## Brücken
- Eine Brücke verbindet zwei Ländereien über dessen Eckpunkte. Diese Verbindung darf maximal einen Schritt
entlang der Gitternetzlinien des Spielfeldes lang sein.
- Ein *Punkt* kann nur ein Brückenende tragen und ist somit für zukünftige Brücken blockiert.
- Die beiden Felder, die sich mit der Brücke eine gemeinsame Seite teilen, müssen beim Bau
der Brücke leer sein. Dieselben Felder gelten nach Bau der Brücke als blockiert und dürfen
von keinen Ländereien, unabhängig von der Farbe, bebaut werden. Auf den Bau von Brücken haben
blockierte *Felder* keinen Einfluss.

# Spielende
- Kann der weiße Spieler keine 2 Ländereien mehr bauen und verzichtet auf das Bauen einer
weiteren Brücke, kann er passen und der rote spieler darf einen letzten Zug machen.
- Kann der rote Spieler keine 2 Ländereien mehr bauen und verzichtet auf das Bauen einer
  weiteren Brücke, kann er passen das Spiel endet sofort.
- Nach Zugende werden für beide Spieler die Punkte nach diesen Regeln ermittelt:
    - Eine einzelne Insel, die nicht über eine Brücke mit anderen Inseln verbunden ist,
    zählt `p(1) = 1` Punkt.
    - Miteinander verbundene Inseln (auch über Territorien hinweg) geben `p(n)` Punkte, wobei `n`
    die Anzahl der verbundenen Inseln beschreibt: `p(n) = p(n-1) + n`
    
- Bei Punktegleichstand gewinnt der Spieler mit den meisten Inseln. Falls wiederum gleichstand herrscht,
gewinnt der Spieler mit den meisten Brücken. Sollte wieder Gleichstand herrschen, endet das
Spiel unentschieden.

# ASCII board design
```
                   /\
                  /  \
                 /    \
                /  ++  \
               /  ++++  \
              /__________\
             /\          /\
            /  \  ++++  /  \
           /    \  ++  /    \
          /  **  \    /      \
         /  ****  \  /        \
        /__________\/__________\
       /\          /+          /\
      /  \  ****  /  +        /  \
     /    \  **  /    +      /    \
    /  **  \    /      +    /  ++  \
   /  ****  \  /        +  /  ++++  \
  /__________\/__________+/__________\
```
