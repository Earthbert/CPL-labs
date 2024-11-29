.data
endl: .asciiz "\n"
comma: .asciiz ", "

.text
main:

	li $a1 12
	move $a0 $a1
	li $v0 1
	syscall

.while:
	ble $a1 1 .end_while

	and $a2 $a1 1

	bne $a2 $zero .odd

	srl $a1 $a1 1
	b .end_if
.odd:
	mul $a1 $a1 3
	add $a1 $a1 1
.end_if:

	la $a0 comma
	li $v0 4
	syscall

	move $a0 $a1
	li $v0 1
	syscall

	b .while
.end_while:

	li $v0 10
	syscall