# Generarea de cod

## Introducere

În laboratoarele precedente s-au tratat etapele de analiză lexicală, sintactică și semantică. În acest mod, am obținut o reprezentare abstractă a codului de CPLang sub formă de AST, care a fost validată în ultima etapă.

În acest laborator vom trata **generarea directă a codului de asamblare**, fără a ne preocupa expres considerentele de optimizare/performanță, cum ar fi utilizarea registrelor în loc de adrese pe stivă pentru a reduce operațiile cu memoria, evaluarea la compilare a unor expresii aritmetice cu valori cunoscute ale operanzilor etc. De asemenea, nu vom trata în acest laborator aspecte legate de generarea de cod orientat obiect. Obiectivul este generarea unui cod corect, ce respectă convențiile, și care poate fi rulat în QTSpim.

## Abordarea generală

La bază vor exista trei categorii de *template*-uri principale(`StringTemplate`), fiecare reprezentând una dintre următoarele secțiuni:

* **`.data`** — aici se vor depune toate **definițiile de variabile globale**. Inițializarea lor (dacă este cazul) se va face în rutina `main`.
* **`.text` (funcții)** — aici se vor depune toate **definițiile de funcții**. Acestea vor fi constituite dintr-o etichetă, urmată de prologul, corpul și epilogul funcției.
* **`.text` (`main`)** — reprezintă **corpul principal al programului**. Va fi plasată la final pentru a nu se intercala cu declarațiile de funcții.

Convențiile folosite sunt cele detaliate la curs între **slide-urile 281–296** (conform numărului de slide din dreapta jos).

**Observație**. Fiecărui nod din AST i-a fost adăugat un câmp **`debugStr`**. Dacă nu este `null`, acesta conține linia de cod originală. Este menită a fi introdusă în codul generat, pentru a fi mai ușor de citit. Vedeți exemplul pentru adunare.

## Task-uri

### Task 1

Definiți *template*-urile în fișierul `cgen.stg`, și instanțiați-le în visitor-ul `CodeGenVisitor` pentru tipurile de valori **imediate** și operatorul **minus unar**. Urmăriți comentariile `TODO 1`.

### Task 2

Completați *template*-urile și metodele `visit` pentru **operațiile binare** (aritmetice și relaționale). Tratați cazurile atât pentru numere întregi, cât și floating point ([detalii](https://www.doc.ic.ac.uk/lab/secondyear/spim/node20.html)). De reținut că, pentru operațiile floating point, acumulatorul ar trebui implementat prin registrul `$f0`, în loc de `$a0`, iar operațiile de tip `Int <op> Float` ar trebui să conducă la generarea unor instrucțiuni explicite de conversie (vedeți `cvt.s.w`) ale operandului `Int` la `Float`. Urmăriți comentariile `TODO 2`.

Definițiile de *template*-uri pot deveni rapid lungi și repetitive. Căutați o soluție în [*cheatsheet*-ul `StringTemplate`](https://github.com/antlr/stringtemplate4/blob/master/doc/cheatsheet.md) pentru a **reduce numărul de linii de cod** din `cgen.stg`.

### Task 3

Completați *template*-urile și metodele `visit` pentru construcțiile **`If`** și **`Call`**. Urmăriți comentariile `TODO 3` și **respectați convențiile prezentate la curs**.

Adăugați un mecanism prin care mai multe construcții `If` să genereze **etichete diferite**. Pentru construcția `Call`, definiți *template*-ul astfel încât să se poată genera apeluri cu **oricâți parametri**.

### Task 4

Completați construcțiile aferente pentru **`Assign`** și **`VarDef`**. Urmăriți comentariile cu `TODO 4`.

În cazul **definițiilor de variabile globale** va trebui înregistrat numele variabilei în secțiunea `.data`; pentru aceasta, utilizați `ST`-ul `dataSection`. Dacă declarația conține și **expresia de inițializare**, se va adăuga codul aferent în `main`. Pentru definirea valorilor cu tipul `Bool` sau `Int` folosiți `.word 0`, iar pentru cele cu tipul `Float`, `.float 0.0`.

### Task 5

Completați construcțiile aferente pentru **definițiile de funcții** și **referirile la variabile** (globale sau parametri formali). Urmăriți convenția prezentată la curs, și comentariile `TODO 5` din `CodeGenVisitor` și `IdSymbol`.

Variabilele globale vor fi accesate pe baza **etichetei** omonime din zona de date, iar parametrii formali, relativ la ***frame pointer*** (`$fp`). Pentru a distinge între cele două situații, îmbogățiți definiția simbolurilor cu informația necesară (de exemplu, numele etichetei sau *offset*-ul față de *frame pointer*).

### Bonus

Încercați să **îmbunătățiți** codul generat în următoarele moduri:

1. Pentru **apelurile de funcție**, calculați anticipat **deplasamentul *stack pointer*-ului** în funcție de numărul de parametri actuali, astfel încât să nu îl deplasați după fiecare depunere pe stivă a unui parametru, ca în convențiile de la curs. Astfel, va trebui să determinați *offset*-ul față de *stack pointer* unde veți depune direct valoarea unui parametru actual.
1. Pentru **definițiile de funcție**, determinați anticipat **necesarul de locații temporare pe stivă**, pentru a evita deplasarea *stack pointer*-ului la fiecare depunere a unui rezultat temporar (slide-urile 298–301). Astfel, locațiile temporare vor fi rezervate în înregistrarea de activare și accesate la anumite *offset*-uri față de ***frame pointer*** (nu *stack pointer*). Acest lucru va duce și la **recalcularea *offset*-urilor parametrilor formali** față de *frame pointer*.
1. Pentru punctul anterior, **reduceți necesarul de locații temporare**, în cazul operatorilor aritmetici și relaționali. Datorită asimetriei ecuațiilor de forma $\mathrm{NT}(e_1 + e_2) = \max(\mathrm{NT}(e_1), 1 + \mathrm{NT}(e_2))$, putem reduce necesarul de locații temporare dacă generăm mai întâi cod pentru subexpresia cu $\mathrm{NT}$ mai mare. Astfel, la fiecare nod de AST corespunzător unui operator binar, putem alege dacă prelucrăm mai întâi subexpresia din stânga sau pe cea din dreapta. În final, vor fi necesare **două treceri**: una pentru determinarea necesarului minim de locații temporare, cu reținerea alegerilor (stânga/dreapta) care au fost făcute pentru obținerea acestui minim, și apoi generea propriu-zisă de cod pentru expresie, implementând deciziile determinate în prima trecere.
