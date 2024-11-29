.data
one: .asciiz "1\n"
two: .asciiz "2\n"
large: .asciiz "Large value\n"
small: .asciiz "Small value\n"

.text
main:
	# print_string("1");
	la $a0 one
	li $v0 4
	syscall

	li $v0 5
	syscall

	li $a1 64
	move $a2 $v0

	blt $a2 $a1 .small

	la $a0 large
	li $v0 4
	syscall
	j .end_if
.small:
	la $a0 small
	li $v0 4
	syscall
.end_if:

	la $a0 two
	li $v0 4
	syscall

	li $v0 10
	syscall