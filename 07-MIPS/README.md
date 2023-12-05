# Arhitectura MIPS

## Introducere

MIPS este o arhitectură **RISC**, pe **32 de biți**. Alegerea unei arhitecturi RISC este motivată de **simplificarea** generării de cod în limbaj de asamblare, datorită instrucțiunilor nedistructive, cu efecte limitate. Deși optimizările vor fi discutate ulterior în cadrul cursului, oferim câteva detalii, pentru o înțelegere mai bună.

În cadrul unui compilator, optimizările nu sunt realizate pe codul țintă (de exemplu, MIPS sau x86), ci pe un așa-zis **cod intermediar** (de exemplu, *three-address code*), care mimează un limbaj de asamblare, dar care nu corespunde unei arhitecturi concrete. În final, codul intermediar optimizat este tradus în limbajul de asamblare țintă. Formele standard ale codului intermediar introduc **nume explicite** pentru rezultatele operațiilor (de exemplu, `a := b + c`), fapt ce permite **reutilizarea** unor valori deja calculate în vederea optimizărilor. Arhitectura **MIPS**, ce oferă instrucțiuni **nedistructive** în raport cu intrările (de forma `add $a0 $a1 $a2`, cu semnificația `$a0 := $a1 + $a2`, unde `$ai` sunt registre fizice), permite o corespondență firească cu instrucțiunile de cod intermediar. Prin contrast, o instrucțiune **x86** de forma `add eax, ebx` este **distructivă** în raport cu primul termen al sumei, suprascriindu-l cu rezultatul. Acest lucru, împreună cu efectele posibil complexe ale acestor instrucțiuni, complică generarea de cod de asamblare din codul intermediar, printre altele ridicând probleme în privința reutilizării valorilor deja calculate.

În [manualul SPIM](https://curs.upb.ro/2023/mod/resource/view.php?id=18816), la pagina A-23 găsiți descrierea **registrelor** arhitecturii MIPS, iar la paginile A-48–A-49, lista **apelurilor de sistem**. În continuare, prezentăm câteva elemente utile pentru rezolvarea laboratorului.

Vom executa programele MIPS în cadrul **[simulatorului SPIM](https://sourceforge.net/projects/spimsimulator/)** (cu varianta vizuală QtSpim).

* De fiecare dată când modificați programul, va trebui să îl **reîncărcați** prin comanda `Reinitialize and load file` (al doilea buton de pe toolbar).
* **Rularea** propriu-zisă se realizează prin comanda `Run/Continue`.
* Dacă doriți să rulați **din nou** programul deja încărcat, trebuie să selectați mai întâi `Clear registers`.
* Aveți și opțiunea să rulați programul **instrucțiune cu instrucțiune** (F10), pentru a observa modificările asupra registrelor.
* Asigurați-vă că fereastra `Console` este vizibilă. Dacă o închideți, ea nu se va mai redeschide automat, și va trebui să selectați `Window -> Console`.

## Structura unui program și câteva instrucțiuni utile

Un program MIPS conține o zonă de **date** și o zonă de **cod**. Zona de cod trebuie să conțină eticheta **`main:`**, de unde va începe execuția programului. La final, trebuie **încheiată explicit** execuția programului, prin apelul de sistem `exit`, pentru că altfel contorul de program va fi incrementat în continuu, până la întâlnirea unei erori sau a unei instrucțiuni invalide.

Programul de mai jos definește trei etichete în zona de date, unde depune un întreg (32 de biți), o secvență de 5 întregi, respectiv un șir de caractere terminat cu caracterul nul (de aici `z`(`ero`) din directiva `.asciiz`). În final, invocă apelul de sistem `exit`, încărcând codul acestuia (10) în registrul `$v0` și executând apoi instrucțiunea `syscall`. Instrucțiunea `li` (*load immediate*) se utilizează pentru a încărca literali întregi în registre.

```mips
.data
number:
    .word 5
vector:
    .word 1, 2, 3, 4, 5
str:
    .asciiz "abc"

.text
main:
    li $v0 10  # exit
    syscall
```

Dacă dorim să afișăm la consolă întregul de la eticheta `number` (5), trebuie să invocăm apelul de sistem `print_int`, având codul 1, care trebui încărcat tot în registrul `$v0`, ca mai sus. Acesta preia din registrul `$a0` valoarea întregului de afișat. Pentru a depune acolo întregul, o primă variantă o constituie instrucțiunea `lw` (*load word*), care încarcă într-un registru conținutul unei adrese de memorie:

```mips
lw $a0 number
li $v0 1  # print_int
syscall
```

Există și instrucțiunea `sw` (*store word*), de forma `sw $a0 number`, care depune valoarea din registrul `$a0` la adresa de memorie aferentă lui `number`.

O altă variantă de a încărca întregul este de a obține mai întâi adresa explicită într-un registru, folosind instrucțiunea `la` (*load address*), și apoi de a încărca valoarea pe baza adresei din registru, plus un eventual *offset*:

```mips
la $t0 number
lw $a0 0($t0)  # offset 0 față de adresa din $t0

la $t0 vector
lw $a0 4($t0)  # offset 4, valoarea celui de-al doilea element din vector
```

În final, încărcarea se poate face și încărcând *offset*-ul într-un registru:

```mips
li $t0 4            # offset 4
lw $a0 vector($t0)  # valoarea celui de-al doilea element din vector
```

Afișarea unui șir de caractere se realizează invocând apelul de sistem `print_string`, având codul `4`, și care preia din registrul `$a0` adresa șirului:

```mips
la $a0 str
li $v0 4
syscall
```

Instrucțiunile de **salt necondiționat** sunt `b`(`ranch`) și `j`(`ump`), iar cele de **salt condiționat** preiau operanzii de comparat și o etichetă:

```mips
b label1            # salt necondiționat la label1
ble $a0 $a1 label2  # salt condiționat la label2 dacă $a0 <= $a1
```

Similar cu convenția de pe x86, **stiva crește către adrese mai mici**. Registrul *stack pointer* se numește `$sp`. În laboratorul de astăzi și în următoarele, vom presupune că acest registru indică prima locație liberă de pe stivă:

```mips
sw $a0 0($sp)     # stocăm pe stivă conținutul lui $a0
addiu $sp $sp -4  # actualizăm stack pointer către următoarea locație liberă
```

Vom discuta și alte registre, relevante în cazul apelurilor de procedură, în laboratorul viitor.

Consultați fișierul implementat la curs, `vector-sum.s`, pentru un exemplu mai complex, de calcul al sumei elementelor din vector.

## Cerințe

### Hello world

Pornind de la fișierul `vector-sum.s`, scrieți un program MIPS care afișează `Hello World`.

### If-Then

Traduceți din pseudocod în assembly următorul fragment:

```
print_string("1");
if N > 64 then
    print_string("Large value")
end-if
print_string("2");
```

### If-Then-Else

Traduceți din pseudocod în assembly următorul fragment:

```
print_string("1");
if N > 64 then
    print_string("Large value")
else
    print_string("Small value")
end-if
print_string("2");
```

### While loop

Afișați la consolă, descrescator, numerele de la 20 la 1. Acesta este echivalentul unui while:

```
unsigned int i = 20;
while(i > 0)
    print_int(i)
    i--
```

**Atenție!** Bucla din fișierul `vector-sum.s` corespunde unei construcții `do-while`, întrucât corpul este executat cel puțin o dată. Implementarea voastră va trebui să **testeze condiția și înainte** de prima executare a corpului.(

### Stack-pointer

Încărcați pe stivă numerele între 20 la 1. Apoi, afișați-le folosind o nouă buclă, ce scoate numerele de pe stivă în timp ce le afișează.

### Hailstone sequence

Rezolvați problema [Hailstone sequence](https://en.wikipedia.org/wiki/Collatz_conjecture) în assembly. Algoritmul de implementat este prezentat in pseudocod mai jos:

```
var = 12
print_int(var)
while(var != 1)
    if (var mod 2 == 0)
        var = var / 2
    else
        var = 3 * var + 1
    print_string(",")
    print_int(var)
```

Output așteptat:

```
12,6,3,10,5,16,8,4,2,1
```

**Notă:** Restul împărțirii este calculat de operația `divu` și stocat în registrul `hi`. Copiați restul folosind `mfhi`.

### Bonus: Floating point numbers

Definiți un array de valori floating-point. Folosind instrucțiunile de FPU (`lwc1`, `cvt.w.s`, `add.s`, `div.s`, `mfc1`), afișati media din acel vector și conversia acestuia la întreg.

### Bonus: Interpretarea output-ului de GCC

Folosind site-ul [Godbolt Compiler Explorer](https://godbolt.org/), urmăriți instrucțiunile de MIPS generate pentru un program simplu scris în C.

### Bonus: Asamblarea și rularea în QEMU (Linux-only)

Până acum ați folosit emulatorul QtSpim, care pentru operațiile de I/O folosește interfața proprie bazată pe `syscall` (fiind echivalentul unui firmware/BIOS propriu). Pentru exercițiul următor vom folosi emulatorul QEMU pentru a executa binare de MIPS pe calculatoare cu arhitecturi Intel, folosind interfața de `syscall` de Linux.

Convenția de apel pentru Linux este următoarea:

* `$v0` - Codul operației de `syscall`. Pentru MIPS este documentat
    [aici](http://git.linux-mips.org/cgit/ralf/linux.git/tree/arch/mips/include/uapi/asm/unistd.h). Documentația privind argumentele primite de fiecare `syscall` este [aici](https://blog.rchapman.org/posts/Linux_System_Call_Table_for_x86_64/).
* `$a0` - Primul parametru;
* `$a1` - Al doilea parametru;
* `$a2` - Al treilea parametru.

Transcrieți următorul program:

```mips
.globl __start
.data

# store the string
buffer:
.asciiz "hello world\n"

# store the string length
buffer_len:
.word 12

.text
__start:
# write(stdout, buffer, buffer_len)
li $v0, 4004                  # 4004 - syscall code for "write"
li $a0, 1                     # write to STDOUT (file descriptor 1)
# load $a1 register in two parts:
# first the high 16 bits, then the low ones
lui $a1, %hi(buffer)
addiu $a1, $a1, %lo(buffer)
lw $a2, buffer_len            # specify the length of the string
syscall                       # run the syscall

# exit(0)
li $v0, 4001                  # 4001 - syscall code for "exit"
li $a0, 0                     # return value (success = 0)
syscall                       # run the syscall
```

Instalați programele necesare:

    sudo apt-get install binutils-mips-linux-gnu qemu-user \
    qemu-system-mips qemu-user-binfmt

După care urmează obținerea executabilului de MIPS:

    $ mips-linux-gnu-as hello.as # generam fisierul de cod-obiect
    $ mips-linux-gnu-ld a.out -o bin # transformam codul-obiect in executabil
    $ file bin # verificare
    bin: ELF 32-bit MSB executable, MIPS, MIPS-I version 1 (SYSV), statically
    linked, not stripped

Putem încărca executabilul în QEMU, folosit ca "user-mode emulator". Aceasta înseamnă ca tot contextul aplicației va fi emulat, iar `syscall`-urile (tranzițiile în kernel-space) vor fi transformate on-the-fly în apeluri native de x86 (și vor fi tratate de kernel-ul gazdă).

    $ qemu-mips bin
    hello world

### Bonus: Binfmt_misc (Linux-only)

Încercați să rulați direct binarul generat pentru arhitectura MIPS.

    $ file bin
    bin: ELF 32-bit MSB executable, MIPS, MIPS-I version 1 (SYSV), statically
    linked, not stripped

    $ ./bin
    hello world

Dacă s-a executat transparent, se datorează extensiei de [binfmt_misc](https://en.wikipedia.org/wiki/Binfmt_misc). Sistemul Linux detectează că binarul este specific altei arhitecturi, și pornește automat o instanță de `qemu-mips` în care încarcă acel binar.
