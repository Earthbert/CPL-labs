.data
	true: .asciiz "true"

	false: .asciiz "false"

	newline: .asciiz "\n"

.text

main:
	li.s $f0 1.1
	mfc1 $a0 $f0
	sw $a0 0($sp)
	addiu $sp $sp -4
	li.s $f0 2.2
	mfc1 $a0 $f0
	lw $t1 4($sp)
	mtc1 $t1 $f0
	mtc1 $a0 $f1
	add.s $f0 $f0 $f1
	mfc1 $a0 $f0
	addiu $sp $sp 4		
	sw $a0 0($sp)
	addiu $sp $sp -4
	li $a0 2
	lw $t1 4($sp)
	mtc1 $t1 $f0
	mtc1 $a0 $f1
cvt.s.w $f1 $f1
	sub.s $f0 $f0 $f1
	mfc1 $a0 $f0
	addiu $sp $sp 4		
	mtc1 $a0 $f12
	li $v0 2
	syscall
	la $a0 newline
	li $v0 4
	syscall
	li.s $f0 1.1
	mfc1 $a0 $f0
	li $a0 4
	not $a0 $a0
	li $a0 1
	sw $a0 0($sp)
	addiu $sp $sp -4
	li $a0 2
	lw $t1 4($sp)
	add $a0 $t1 $a0
	addiu $sp $sp 4		
	sw $a0 0($sp)
	addiu $sp $sp -4
	li $a0 3
	lw $t1 4($sp)
	add $a0 $t1 $a0
	addiu $sp $sp 4		# 1+2+3
	li.s $f0 5.0
	mfc1 $a0 $f0
	sw $a0 0($sp)
	addiu $sp $sp -4
	li.s $f0 6.0
	mfc1 $a0 $f0
	lw $t1 4($sp)
	mtc1 $t1 $f0
	mtc1 $a0 $f1
	add.s $f0 $f0 $f1
	mfc1 $a0 $f0
	addiu $sp $sp 4		# 5.0+6.0
	li $a0 2
	sw $a0 0($sp)
	addiu $sp $sp -4
	li $a0 2
	lw $t1 4($sp)
	mul $a0 $t1 $a0
	addiu $sp $sp 4		# 2*2
	li $a0 84
	sw $a0 0($sp)
	addiu $sp $sp -4
	li $a0 2
	lw $t1 4($sp)
	div $a0 $t1 $a0
	addiu $sp $sp 4		# 84/2
	li $a0 1
    beq $a0 $zero else_0
then_0:
	li $a0 5
    b end_if_0
else_0:
	li $a0 7
end_if_0:
	li $a0 0
    beq $a0 $zero else_1
then_1:
	li $a0 7
    b end_if_1
else_1:
	li $a0 5
end_if_1:
	li $v0 10
	syscall		# exit