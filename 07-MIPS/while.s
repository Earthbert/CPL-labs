.data
endl: .asciiz "\n"

.text
main:
	li $v0 5
	syscall

	move $a2 $v0

.while:
	ble $a2 $zero .end_while

	move $a0 $a2

	li $v0 1
	syscall

	li $v0 4
	la $a0 endl
	syscall

	sub $a2 $a2 1
	j .while
.end_while:

	li $v0 10
	syscall