## Null Object (prázdný objekt)

> I call it my billion-dollar mistake. It was the invention of the null reference in 1965. *sir C. A. R. Hoare*
 
V řadě objektově orientovaných jazyků se vyskytuje speciální výraz, jehož sémantika je "nic" nebo "nulová reference". 

Například v jazyce Java je to výraz *null*, který lze přetypovat na jakýkoliv jiný referenční typ (*Object* a jeho potomci). Proto se teoreticky může objevit jako hodnota téměř libovolného výrazu. Problém nastává tehdy, když je na výsledku tohoto výrazu volána nějaká metoda. V případě, že je výsledek roven *null*, je toto volání neúspěšné a skončí s výjimkou *NullPointerException*. Jedná se snad o nejčastější chybu programů v jazyce Java vůbec.

Z tohoto důvodů bývá ve zdrojových kódech programů v jazyce Java mnoho kontrol, které testují, zda je nějaký výraz skutečně roven hodnotě *null* a pokud ano, na tuto skutečnost nějak rozumně zareagují. Někdy vývojáři tyto kontroly dokonce preventivně používají "pro jistotu" téměř všude, kde si nejsou jisti. Tyto mnohdy nepotřebné kontroly by bylo možné odebrat, zkrátit tak kód a předem zabránit vzniku potenciální chyby tam, kde kontrola chybí.

Záměrem návrhového vzoru Null Object (nulový objekt) je poskytnout rozumnou náhradu za chybějící objekt, která definuje výchozí chování v situacích, kdy není žádný plnohodnotný objekt dostupný. Místo univerzální a skrytě nebezpečné hodnoty *null* se pak používá speciální předpřipravená instance nulového objektu s rozhraním alespoň částečně shodným s třídou zastupovanou. Tento zástupce může být přímo potomkem zastupované třídy s přetíženými metodami, nebo implementací nějakého společného rozhraní. Možností implementace je celá řada, důležitá je však základní myšlenka - **poskytnout něco místo ničeho** a tak se vyvarovat potenciálních chyb.

### Postup implementace - rozhraní

1. navrhnout společné rozhraní nulového a nenulového objektu
1. používat toto rozhraní namísto skutečného objektu
1. místo hodnot *null* se na místě nenulového objektu objeví nulový objekt
1. implementovat chování nulového objektu

```uml:class
class Parent <<interface>> {
}

Client -right.-> Parent
Something -up.-|> Parent
Nothing -up.-|> Parent
```

### Postup implementace - podtřída

1. vytvořit potomka zastupovaného objektu, který se stane nulovým objektem
1. místo hodnot *null* se na místě nenulového objektu objeví nulový objekt
1. nulový objekt překryje metody nenulového objektu
1. implementovat chování nulového objektu

```uml:class
Client -right-> Something
Nothing -up-|> Something
note bottom of Nothing
  1) getters return constant values
  2) setters are disabled
  3) usually there is one instance only
end note
```

### Příklad - loggery (pomocí rozhraní)

Vytvoříme rozhraní loggeru a dvě jeho implementace: standardní logger, který bude vypisovat zprávy na standardní výstup, a neaktivní logger, který nebude dělat nic (to bude příklad prázdného objektu).

#### Rozhraní loggeru

```include:java
gof/nullobject/Logger.java
```

#### Standardní logger

```include:java
gof/nullobject/PrintLogger.java
```

#### Neaktivní logger (prázdný objekt)

```include:java
gof/nullobject/NullLogger.java
```

#### Logování PŘED použitím vzoru

```include:java
gof/nullobject/ExampleBefore.java
```

#### Logování PO použití vzoru

```include:java
gof/nullobject/ExampleAfter.java
```

### Příklad - nikdo (pomocí podtřídy)

#### Standardní třída

```include:java
gof/nullobject/Person.java
```

#### Nikdo (prázdný objekt)

Prázdný objekt v tomto případě dědí ze standardního objektu. Je několik možností, jak rodičovskou třídu převést na prázdný objekt. Jako velmi vhodné se v každém případě jeví mít nanejvýš jednu instanci nulového objektu, tedy použít zjednodušený návrhový vzor [Singleton](wiki/singleton). V tomto příkladu je zvolen následující postup:

1. vytvořit potomka standardní třídy
1. přetížit všechny modifikační metody (např. settery) tak, aby nic nevykonávaly
1. nadeklarovat privátní konstruktor
1. v konstruktoru pomocí setterů rodiče inicializovat atributy na výchozí hodnoty
1. použít jednoduchou variantu návrhového vzoru [Singleton](wiki/singleton) a k dispozici dát pouze jednu instanci této třídy

```include:java
gof/nullobject/NullPerson.java
```

### Reference

- Rudolf Pecinovský: Návrhové vzory
- Kevlin Henney: Null Object - Something for Nothing (2003)
