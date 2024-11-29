.data
endl: .asciiz "\n"

.text
main:
	li $v0 5
	syscall

	move $a2 $v0
	move $a3 $a2

.while_put:
	ble $a2 $zero .end_while_put

	sw $a2 0($sp)
	sub $sp $sp 4

	sub $a2 $a2 1

	j .while_put
.end_while_put:

	move $a2 $a3

.while_print:
	ble $a2 $zero .end_while_print

	lw $a0 0($sp)
	add $sp $sp 4

	li $v0 1
	syscall

	li $v0 4
	la $a0 endl
	syscall

	sub $a2 $a2 1
	j .while_print
.end_while_print:

	li $v0 10
	syscall