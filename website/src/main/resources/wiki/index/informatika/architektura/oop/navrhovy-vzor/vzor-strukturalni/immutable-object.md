## Immutable Object (neměnný objekt)

Přijde-li zákazník do obchodu se zvířaty a koupí si morče v kleci, naprosto oprávněně očekává, že i po příchodu domů ho v kleci najde. Kdyby mu cestou nepozorovaně uteklo, moc nadšen by asi nebyl. Při výběru zvířete totiž něco očekával a jeho očekávání nebylo naplněno. Podobné je to i s objekty. 

Neměnnost třídy úzce souvisí se seznamem atributů a hodnot v nich uložených. Neměnné třídy se vyznačují tím, že žádné jejich metody nemění hodnoty atributů. Jakmile se do atributu neměnného objektu v konstruktoru přiřadí nějaká výchozí hodnota, zůstává tato hodnota stejná během celé doby existence objektu, tedy až do jeho odstranění z paměti. Pokud se má některá hodnota neměnného objektu změnit, je nutné vytvořit novou instanci objektu.

Neměnnost je takřka nutností pro kvantity v matematických výrazech a objekty, které mají být uloženy v kolekci využívající metody *hashCode()* a *equals()*. Neočekávané komplikace totiž mohou přijít, je-li rovnost dvou objektů závislá na hodnotě měnitelného atributu a tento atribut se změní.

Výhodou neměnnosti je spolehlivost, stabilita a implicitní vláknová bezpečnost. Vlákna se nemusí v přístupu k atributům a metodám objektu nijak synchronizovat, protože se žádné atributy nemění. Další výhodou je možnost bezpečného sdílení všech instancí neměnných tříd v rámci celé aplikace a to bez obav z toho, že by někdo tyto instance modifikoval.

Nevýhodou je naopak nutnost vytvářet mnoho instancí velmi malých objektů. S tím ale moderní platformy nemají žádný větší problém.

Neměnné třídy často obsahují různé [tovární metody](factory-method), které vytváří odvozené instance, ve kterých jsou provedeny požadované změny nebo představují výsledek provedené operace. Atributy původní instance neměnné třídy se přitom nikdy nemění.

### Postup implementace neměnné třídy

1. nadeklarovat třídu a její atributy
1. všechny atributy označit modifikátorem *final*
1. vygenerovat konstruktor, který všechny atributy inicializuje
1. podle potřeby doplnit metodu *hashCode()* a *equals()*

### Příklad

#### Třída String

Třída *String* ve standardní knihovně jazyka Java je neměnná. V řetězci není možné změnit ani jeden znak. Všechny operace které v řetězci provádí změny ve skutečnosti vrací nové instance třídy *String* a původní řetězec nechávají nedotčený.

```java
String hello = "hello";
String helloUC = hello.toUpperCase();
// hello stále obsahuje text 'hello'
// helloUC obsahuje text 'HELLO'
// hello != helloUC
```

#### Komplexní číslo

Libovolné komplexní číslo budeme reprezentovat třídou *ComplexNumber*, která obsahuje dva konstantní atributy typu *double*. Ty uchovávají reálnou (*a*) a imaginární část (*b*) čísla. Třída *ComplexNumber* nenabízí žádnou možnost, jak by šlo reálnou či komplexní část čísla změnit - pracovat s jinými komplexními čísly znamená vytvářet jiné instance třídy *ComplexNumber*. Proto také mohou být oba atributy označeny modifikátorem *final* a kompilátor nenahlásí žádnou chybu.

```include:java
gof/immutableobject/ComplexNumber.java
```

### Reference

- Rudolf Pecinovský: Návrhové vzory
- předmět X36OBP na FEL ČVUT
