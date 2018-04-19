# DiavoloPP
The basic game idea has its source in the game [Ponte del Diavolo](https://www.brettspielnetz.de/spielregeln/ponte+del+diavolo.php)

# Grid coordinates
- Columns and rows
- Direct neighbors for (c,r) starting from right: (c+1, r), (c+1, r-1), (c, r-1), (c-1, r), (c-1, r+1), (c, r+1)

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
          /  --  \    /      \
         /  ----  \  /        \
        /__________\/__________\
       /\          /+          /\
      /  \  ----  /  +        /  \
     /    \  --  /    +      /    \
    /  --  \    /      +    /  ++  \
   /  ----  \  /        +  /  ++++  \
  /__________\/__________+/__________\
```
