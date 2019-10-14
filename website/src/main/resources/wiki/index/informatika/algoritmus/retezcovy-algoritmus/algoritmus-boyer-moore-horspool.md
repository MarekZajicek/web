## Algoritmus Boyer-Moore-Horspool

![Nigel Horspool](horspool.png){.right}

Tento algoritmus pro vyhledávání řetězců představil v roce 1980 **Nigel Horspool** (Kanada) a vznikl zjednodušením algoritmu Boyer-Moore (autor jej také původně pojmenoval SBM - Simplified Boyer-Moore). Za toto zjednodušení platí využitím většího množství paměti a v některých situacích i horším výkonem. V praktických aplikacích se vlastnosti obou algoritmů podobají.

Algoritmus je vhodné použít v situacích, kdy je abeceda v porovnání s typickou délkou vyhledávaného řetězce velká. To může být případ textových editorů, ve kterých se běžně pracuje s mezinárodními znakovými sadami a vyhledávají se jen krátká slova či jejich spojení.

### Kroky algoritmu

#### Posunovací tabulka

Prvním krokem je vytvoření pomocné "posunovací" tabulky. Tabulka obsahuje záznam pro každý znak abecedy (např. 256 záznamů pro [ASCII](wiki/ascii), 65536 záznamů pro šestnáctibitový Unicode) a její hodnota udává, o kolik znaků se může pozice v prohledávaném řetězci bezpečně posunout, nedojde-li ke shodě s vyhledávaným řetězcem. Indexem do tabulky je poslední znak vyhodnocované části prohledávaného řetězce.

Tabulka se vyplní podle následujících pravidel: 

- Pro znaky, které ve vyhledávaném řetězci nejsou, je možný posun o celou délku vyhledávaného řetězce (proto bude většina řádků obsahovat právě tuto hodnotu).
- Pro znaky, které ve vyhledávaném řetězci jsou, se zjistí vzdálenost jejich posledního výskytu ve vyhledávaném řetězci od konce.

Pokud například vyhledáváme v dekadickém čísle posloupnost *01214*, bude posunovací tabulka vypadat následovně:

| Index znaku | Posun | Vysvětlení
|---|---|---
| 0 | 4 | číslo "0" je čtvrtý znak po posledním znaku řetězce, čteme-li jej zprava (4-121*0*)
| 1 | 1 | číslo "1" je první znak po posledním znaku řetězce, čteme-li jej zprava (4-*1*)
| 2 | 2 | číslo "2" je druhý znak po posledním znaku řetězce, čteme-li jej zprava (4-1*2*)
| 3 | 5 | číslo "3" se ve vyhledávaném řetězci nevyskytuje
| 4 | 0 | číslo "4" je poslední znak řetězce
| 5 | 5 | číslo "5" se ve vyhledávaném řetězci nevyskytuje
| 6 | 5 | číslo "6" se ve vyhledávaném řetězci nevyskytuje
| 7 | 5 | číslo "7" se ve vyhledávaném řetězci nevyskytuje
| 8 | 5 | číslo "8" se ve vyhledávaném řetězci nevyskytuje
| 9 | 5 | číslo "9" se ve vyhledávaném řetězci nevyskytuje

#### Vyhledávání s tabulkou

Sestavená tabulka se potom využije pro posun pozice v hlavním cyklu algoritmu:

```include:java
search/BoyerMooreHorspool.java
```

### Složitost

Teoretická [asymptotická složitost](wiki/asymptoticka-slozitost) algoritmu je stejná jako u naivního algoritmu, a sice € O(m \cdot n) €, kde €m€ je délka vyhledávaného řetězce a €n€ délka řetězce, ve kterém vyhledávání probíhá. V běžných případech však složitost klesá až k lineární € O(n) €.

### Reference

- http://www-igm.univ-mlv.fr/~lecroq/string/node18.html#SECTION00180
- http://www-igm.univ-mlv.fr/~lecroq/string/examples/exp18.html
- http://webhome.cs.uvic.ca/~nigelh/
- http://www.cin.br/~paguso/courses/if767/bib/Horspool_1980.pdf
