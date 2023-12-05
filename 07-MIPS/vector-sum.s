# Suma elementelor unui vector.

# Zona de date
.data

# Vectorul pentru care calculam suma elementelor.
# .word depune un cuvant de 32 de biti la adresa curenta.
# Cuvintele din enumerarea de mai jos sunt depuse la adrese multiplu de 4
# consecutive, de forma vector, vector + 4, vector + 8 etc.
vector:
    .word 1, 2, 3, 4, 5

# Numarul de elemente din vector.
N:
    .word 5

# Zona de cod
.text

# Eticheta main este obligatorie.
# Utilizam urmatorii registri:
# a0 - suma finala
# t0 - nr de elemente ramase
# t1 - adresa elementului curent
# t2 - elementul curent
main:
    lw $t0 N            # Numarul de elemente
    li $a0 0            # Initializarea sumei la 0
    la $t1 vector       # Adresa primului element, utilizand la (load address)
loop:
    lw $t2 0($t1)       # Elementul curent
    add $a0 $a0 $t2     # Adaugarea lui la suma
    addiu $t0 $t0 -1    # Decrementarea numarului de elemente ramase
    addiu $t1 $t1 4     # Avans la adresa urmatorului element
    bgtz $t0 loop       # Reluare bucla daca a ramas cel putin un element
                        # bgtz = branch if greater than zero
    li $v0 1            # Apel de sistem, cu codul 1, pentru afisarea
    syscall             # unui numar intreg din registrul $a0 (suma)
    li $v0 10           # Apel de sistem, cu codul 10, pentru incheierea
    syscall             # executiei programului