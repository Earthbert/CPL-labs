# Analiză semantică 1 - Rezolvarea simbolurilor

## Tabela de simboluri

În acest laborator ne vom ocupa de **rezolvarea simbolurilor** pentru limbajul CPLang, alături de depistarea unor **erori specifice**. Vom porni de la AST-ul implementat ı̂n laboratorul trecut, vom adăuga noi clase pentru **simboluri** și ***scope*-uri**, și vom implementa noi visitori care vor adnota AST-ul cu acestea.

Pentru a realiza aceste lucruri, ı̂n funcție de natura limbajului, sunt necesare una sau mai multe parcurgeri ale AST-ului. Motivul pentru care, în CPLang, o singură parcurgere nu este suficientă, este existența ***forward references*** la funcții, adică posibilitatea utilizării lor înaintea definirii. Astfel, ı̂n prima parcurgere, când găsim un apel de funcție, nu putem ști pe loc dacă funcția chiar va fi definită mai târziu.

Structurile de bază definite ı̂n schelet sunt `Symbol` și `Scope`.

### `Symbol`

Clasa `Symbol` și subclasele ei conțin detalii despre un **simbol**. În laboratorul curent, un simbol poate desemna o **variabilă** (globală sau parametru formal) sau o **funcție**. Rolul unui simbol este de a **unifica** definiția și toate utilizările unei anumite entități din program. Acest lucru se realizează **adnotând** nodurile **diferite** de AST corespunzătoare acestor definiții și utilizări cu **același** simbol. Simbolurile sunt adăugate ı̂n ***scope***-uri, pe baza cărora se realizează rezolvarea lor.

### `Scope`

Interfața `Scope` descrie operațiile permise pe un *scope*. Clasa `DefaultScope` conține o **mulțime de simboluri** indexată după numele acestora, și un *scope* **părinte**. La vizitarea nodului `Program` se introduce noțiunea de *scope* **curent**, inițializat cu un `DefaultScope`, care denotă *scope*-ul global. În acesta vor fi introduse simbolurile aferente variabilelor globale și funcțiilor. În plus, funcțiile însele reprezintă *scope*-uri pentru parametrii lor formali. Prin urmare, la vizitarea unei definiții de funcție, *scope*-ul curent **se schimbă** în cel aferent funcției, urmând să **se revină** la cel global la încheierea definiției.

La întâlnirea unei **definiții** de variabilă sau funcție, se creează un simbol nou și se **adaugă** în *scope*-ul curent, după ce ne-am asigurat că nu există deja acolo un simbol cu același nume. La întâlnirea unei **utilizări**, se **rezolvă** simbolul respectiv pe baza numelui său în raport cu *scope*-ul curent. Căutarea se poate realiza **ierarhic**: dacă nu există un simbol cu acel nume în *scope*-ul curent, se încearcă în părinte ș.a.m.d.

### Legătura cu AST-ul

Scheletul conține clasele AST-ului din laboratorul trecut, cu o mică modificare: clasa `Id` posedă acum două **atribute noi**, un `Symbol` și un `Scope`. Astfel, **adnotând** nodurile de AST cu simboluri și *scope*-uri aferente, ı̂n trecerile ulterioare le putem obține cu ușurință.

## Cerințe

### 1. Simboluri și *scope*-uri pentru funcții

Definiți reprezentarea simbolurilor de **funcție** (similară cu `IdSymbol`, deja implementată), ținând cont de faptul că funcțiile introduc ***scope***-uri pentru parametrii formali. Porniți de la clasa `FunctionSymbol`, care extinde clasa `IdSymbol` și implementează interfața `Scope`, și de la clasa `DefaultScope`. Urmăriți marcajele `TODO1` din fișierul `FunctionSymbol.java`.

### 2. Definirea și rezolvarea simbolurilor pentru variabile și funcții

Implementați, ı̂n **două treceri**, definirea și rezolvarea de simboluri pentru **variabile globale**, **parametri formali** și **funcții**:

- În **prima trecere** (*definition pass*), veți **defini** toate simbolurile pentru **variabile locale**, **variabile globale**, **parametri formali** și **funcții**, și veți **rezolva** referirile la **primele trei tipuri de simboluri**. Rezolvarea referirilor la **variabile** încă din prima trecere este necesară întrucât **nu** sunt permise *forward references* la acestea. Pentru funcții, rezolvarea se va face **doar** în a doua trecere.
- În **a doua trecere** (*resolution pass*), se vor **rezolva** referirile la funcții în raport cu *scope*-urile adnotate în AST în prima trecere (vedeți `visit(Call)` în `DefinitionPassVisitor`).

Urmăriți comentariile și marcajele `TODO2` din `DefinitionPassVisitor` și `ResolutionPassVisitor`.

Verificarea acestui exercițiu presupune absența `NullPointerException` la rularea pe fișierul de intrare `manual.txt`. Verificarea erorilor o vom face la exercițiul următor.

**Notă**: Ținând cont de faptul că, în CPLang, funcțiile pot fi definite doar în *scope*-ul global, rezolvarea referirilor la acestea în a doua trecere se poate face direct în raport cu *scope*-ul global (presupunând că avem permanent o referință la acesta), **fără a mai adnota** în prima trecere nodurile de AST cu *scope*-ul în raport cu care se va face rezolvarea. Totuși, deși pare pedantă în aceste condiții abordarea bazată pe adnotare, este important să o înțelegeți, fiind necesară la tema 2 (metodele din Cool pot fi definite în diverse clase).

### 3. Verificarea erorilor

Ne propunem să generăm să detectăm câteva erori uzuale de analiză semantică, și să generăm mesaje corespunzătoare. Acestea verifică de asemenea că AST-ul nostru a fost adnotat corect.

Astfel de erori includ:

- variabilă sau funcție **nedefinită**
- variabilă sau funcție **redefinită**
- ı̂ncercarea de **apelare a unei variabile** (e.g. `Int x; x(1, 2);`), sau invers, **atribuirea către o funcție** (e.g. `Int x() { 0 }; x = 5;`)
- existența mai multor parametri formali cu același nume.

**Notă**: În CPLang, blocurile se pot imbrica pe oricâte niveluri.

Spațiul de nume global este același pentru variabile și funcții, deci **nu** pot exista variabile globale și funcții cu același nume.

Urmăriți comentariile și marcajele `TODO3` din clasele `Test`, `DefinitionPassVisitor` și `ResolutionPassVisitor`.

Pentru testare, aveți la dispoziție fișierele `manual.txt` și `bad_input.txt`, împreună cu referințele lor, `manual_reference.txt` (aici ar trebui să apară că `print_float`, `print_bool` și `print_int` nu sunt definite, le puteți introduce manual ı̂n *scope*-ul global, acestea fiind predefinite de limbaj) și `bad_input_reference.txt`.

