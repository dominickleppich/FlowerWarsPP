# Computerspieler Strategie
Die folgende Strategie realisiert einen sehr einfachen, regelbasierten Computerspieler.

Alle möglichen Züge werden gemäß der nachfolgenden Bewertung evaluiert. Der Computerspieler wählt immer den Zug mit der
höchsten Bewertung. Gibt es mehrere, wird von diesen zufällig einer ausgewählt.

## Zugbewertung
Züge, die Gräben bauen werden in dieser Strategie ignoriert und erhalten eine Wertung von `0`.

Für Züge, die Blumen pflanzen, gelten folgende Überlegungen:
- Es ist sinnvoll an bereits existierende Blumenbeete anzuknüpfen
- Hängen beide gepflanzten Blumen zusammen ist das auch gut

Seien `f1` und `f2` die beiden Blumen, die in einem Zug gepflanzt werden sollen. Seien weiter `n1` und `n2` die Anzahl 
der direkt benachbarten Pflanzen von `f1` bzw. `f2` (wenn die Blumen auf das Spielfeld gesetzt werden würden).

Daraus wird folgende Bewertung hergeleitet: `score = (n1 + 1) * (n2 + 1)`.

Hängen die beiden zu pflanzenden Blumen `f1` und `f2` zusammen, wird dieser Score verdoppelt. 

## Sonderregeln
Es gelten ferner die folgenden Zusatzregeln:
- Der Computerspieler gibt niemals auf
- Der Computerspieler beendet niemals das Spiel
