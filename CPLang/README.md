# Manualul CPLang

## Introducere

CPLang este un limbaj restrâns, inspirat din C, pe care îl vom folosi în cadrul cursului și al laboratorului, pentru a exemplifica implementarea etapelor specifice ale unui compilator. Acestea vor fi dezvoltate ulterior în cadrul temelor, însă pentru limbajul Cool.

## Structura unui program

Iată un prim exemplu de program scris în CPLang:

```c++
/* Exemplu
    de program /* CPLang */
*/
Int x;
Int y = x;

Int inc(Int x) { x + 1 };
x = inc(y);

print_float(mult(x, y));  // utilizare mult inainte de definire
Float mult(Float x, Float y) { x + y * 2.5 };

Bool b = x == x;
print_bool(b);

x = if b then 5 else 7 fi;
print_int(x);

Int factorial(Int n) {
    Int p = 1;
    Int i;

    for i = 1, i <= n, i = i + 1 do {
        p = p * i;
        print_int(p);
    };

    p;
};
print_int(factorial(5));
```

Spre deosebire de Cool, CPLang **nu** posedă clase și instanțe aferente. Tipurile sunt **primitive** (precum `int` din Java), iar valorile corespunzătoare sunt simple, neexistând obiecte sau referințe.

Un program este alcătuit dintr-o secvență de **definiții** și **expresii**, separate prin caracterul `;`. Cele două limbaje, Cool și CPLang, se aseamănă în privința faptului că sunt **bazate pe expresii** (*expression languages*), în sensul că toate instrucțiunile, cu excepția definițiilor, au valori asociate.

## Tipuri

Tipurile de bază sunt `Int`, `Float` și `Bool`.

### Tipul `Int`

Tipul `Int` are la dispoziție operatorii **aritmetici** consacrați, `+`, `-`, `*`, `/`. În plus, există operatorii **relaționali** `==`, `<` și `<=`. De asemenea, funcția predefinită `print_int(Int)` **afișează** o valoare întreagă la consolă. Se consideră că atât funcția `print_int(Int)`, cât și celelalte funcții de afișare de mai jos, întorc un `Int` cu valoarea `0`.

### Tipul `Float`

Pentru tipul `Float`, sunt definiți aceiași operatori **aritmetici** și **relaționali**, împreună cu funcția predefinită de **afișare** `print_float(Float)`. Dacă un operator este aplicat asupra unui operand de tipul `Float` și a unuia de tipul `Int`, ultimul va fi **promovat** automat la tipul `Float`; în cazul operatorilor aritmetici, rezultatul va fi tot de tipul `Float`.

### Tipul `Bool`

În cazul tipului `Bool`, dispunem de **literalii** predefiniți `true` și `false`, și de funcția predefinită `print_bool(Bool)`, care **afișează** `true` sau `false`, în funcție de valoarea de adevăr. Nu există alți operatori booleeni.

## Definiții

Într-un program CPLang, există două categorii de definiții:

* de funcții
* de variabile.

### Funcții

Funcțiile pot fi definite doar la *top-level*. **Antetele** lor conțin:

* tipul întors
* numele funcției, care începe cu **literă mică**
* lista de parametri formali, separați prin virgulă:

```c++
Int inc(Int x, Int y) {
    Int z = x + y;
    z + 2;
};
```

Spre deosebire de Cool, **corpul** unei funcții este un **bloc**, nu o expresie oarecare între acolade.

Funcțiile pot fi folosite **independent** de ordinea definirii (vezi exemplul de program, funcția `mult`)! **Nu** pot exista definiții multiple ale aceleiași funcții.

### Variabile

Există trei categorii de variabile în CPLang, numele acestora începând cu **literă mică**.

* variabile globale
* variabile locale (definite în blocuri)
* parametri formali ai funcțiilor.

#### Variabile globale și variabile locale

Variabilele **globale** sunt definite la *top-level* și sunt vizibile în întregul program. Variabilele **locale** sunt definite în cadrul unui bloc, și sunt vizibile doar în cadrul acestuia.

Definițiile de variabile globale și de variabile locale conțin:

* tipul
* numele variabilei
* opțional, expresia de inițializare:

```c++
Int x;
Int y = f(5);
```

În cazul în care inițializarea lipsește, variabila primește valoarea **implicită** aferentă fiecărui tip de bază: pentru `Int`, `0`, pentru `Float`, `0.0`, și pentru `Bool`, `false`.

Variabilele globale și variabilele locale pot fi folosite doar în **ordinea** în care au fost definite, similar cu variabilele de `let` din Cool! **Nu** pot exista definiții multiple ale aceleiași variabile globale sau definiții multiple ale aceleiași variabile locale în același bloc. În schimb o definiție dintr-un bloc imbricat poate ascunde o definiție cu același nume dintr-un bloc superior sau de la *top-level*.

#### Parametri formali

Definițiile de parametri formali conțin:

* tipul
* numele parametrului.

**Nu** pot exista mai mulți parametri formali cu același nume în cadrul aceluiași antet de funcție.

## Expresii

Expresiile din CPLang sunt următoarele:

* Literali întregi. Exemplu: `5`.
* Literali reali. Exemplu: `5.2`.
* Literali booleeni `true` și `false`.
* Referiri la variabile. Exemplu: `x`.
* Apeluri de funcție. Exemplu: `inc(mult2(x))`.
* Decizii. Exemplu: `if b then 5 else 7 fi`.
* Bucle: Exemplu: `for i = 0, i < 10, i = i + 1 do s = s + i`. Valoarea unui bucle este `0`. Atât cele trei expresii din antetul buclei (între `for` și `do`), cât și corpul buclei, sunt expresii arbitrare, singura constrângere fiind tipul `Bool` al condiției.
* Expresii aritmetice. Exemplu: `x + 1`.
* Expresii relaționale. Exemplu: `x <= 3`.
* Atribuiri. Exemplu: `x = x + 1`. Valoarea și tipul unei atribuiri sunt date de **partea dreaptă**. Atribuirile asociază la dreapta. Exemplu: `x = y = z + 1`.
* Expresii parantezate. Exemplu: `(x + 1)`.
* Minus unar. Exemplu: `-(x + 1)`.
* Blocuri. Exemplu: `{ print_int(5); print_float(5.2); }`. La fel ca în Cool, valoarea unui bloc este valoarea **ultimei expresii** din acesta. Prin urmare, ultima intrare dintr-un bloc trebuie să fie o **expresie**, și nu o definiție de variabilă locală. Din moment ce orice bloc este o expresie, blocurile se pot **imbrica** pe oricâte niveluri.

## Cuvinte cheie

Cuvintele cheie din limbaj sunt `if`, `then`, `else`, `fi`, `for`, `do`, `true` și `false`.

## Comentarii

Comentariile pe o **singură linie** au forma:

```c++
// Comentariu pe o singura linie.
```

Comentariile pe **mai multe linii** iau forma de mai jos și pot fi
**imbricate**!

```c++
/* Comentarii imbricate
    pe /* mai multe linii */
*/
```

**Dezechilibrul** dintre marcajele de început și de sfârșit de
comentariu constituie eroare lexicală!
