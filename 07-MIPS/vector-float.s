.data
vector:
	.float 1.3, 2.4, 3.8, 4.4, 5.3
	endl: .asciiz "\n"

N:
	.word 5

.text
main:

	lw $t0 N
	li.s $f0 0.0

	la $t1 vector

.while:
	ble $t0 0 .end_while
	addi $t0 $t0 -1

	lwc1 $f2 0($t1)
	add.s $f0 $f0 $f2

	addi $t1 $t1 4

	b .while
.end_while:

	mov.s $f12 $f0
	li $v0 2
	syscall

	la $a0 endl
	li $v0 4
	syscall

	cvt.w.s $f0 $f0
	mfc1 $a0 $f0
	li $v0 1
	syscall

	li $v0 10
	syscall
