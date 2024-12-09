.data
	myvar: .word 0

	myvar2: .word 0

	i: .word 0

	true: .asciiz "true"

	false: .asciiz "false"

	newline: .asciiz "\n"

.text
myfunc:
    addiu $sp $sp -8
    sw $fp 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    addiu $sp $sp -4       # locals + temps
    la $t1 8($fp)
    lw $a0 0($t1)
    sw $a0 -4($fp)
    la $t1 12($fp)
    lw $a0 0($t1)
    lw $t1 -4($fp)
    add $a0 $t1 $a0    
    sw $a0 -4($fp)
    la $t1 16($fp)
    lw $a0 0($t1)
    lw $t1 -4($fp)
    add $a0 $t1 $a0    
    sw $a0 -4($fp)
    la $t1 20($fp)
    lw $a0 0($t1)
    lw $t1 -4($fp)
    add $a0 $t1 $a0    
    sw $a0 -4($fp)
    la $t1 24($fp)
    lw $a0 0($t1)
    lw $t1 -4($fp)
    add $a0 $t1 $a0    # a+b+c+d+e
    addiu $sp $fp -4
    lw $fp 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 20
    jr $ra

avg:
    addiu $sp $sp -8
    sw $fp 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    addiu $sp $sp -4       # locals + temps
    la $t1 8($fp)
    lw $a0 0($t1)
    sw $a0 -4($fp)
    la $t1 12($fp)
    lw $a0 0($t1)
    lw $t1 -4($fp)
    add $a0 $t1 $a0    
    sw $a0 -4($fp)
    li $a0 2
    lw $t1 -4($fp)
    div $a0 $t1 $a0    # (a+b)/2
    addiu $sp $fp -4
    lw $fp 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 8
    jr $ra

main:
    addiu $fp $sp 4
    addiu $sp $sp -12       # locals + temps
    li.s $f0 1.1
    mfc1 $a0 $f0
    li $a0 4
    not $a0 $a0
    li $a0 1
    sw $a0 -4($fp)
    li $a0 2
    lw $t1 -4($fp)
    add $a0 $t1 $a0    
    sw $a0 -4($fp)
    li $a0 3
    lw $t1 -4($fp)
    add $a0 $t1 $a0    # 1+2+3
    li.s $f0 5.0
    mfc1 $a0 $f0
    sw $a0 ($fp)
    li.s $f0 6.0
    mfc1 $a0 $f0
    lw $t1 ($fp)
    mtc1 $t1 $f0
    mtc1 $a0 $f1
    add.s $f0 $f0 $f1
    mfc1 $a0 $f0
    li $a0 2
    sw $a0 -4($fp)
    li $a0 2
    lw $t1 -4($fp)
    mul $a0 $t1 $a0    # 2*2
    li $a0 84
    sw $a0 -4($fp)
    li $a0 2
    lw $t1 -4($fp)
    div $a0 $t1 $a0    # 84/2
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
    addiu $sp $sp -20
    li $a0 1
    sw $a0 4($sp)
    li $a0 2
    sw $a0 8($sp)
    li $a0 3
    sw $a0 12($sp)
    li $a0 4
    sw $a0 16($sp)
    li $a0 5
    sw $a0 20($sp)
    jal myfunc
    li $v0 1
    syscall
    la $a0 newline
    li $v0 4
    syscall
    li $a0 0
    li $a0 5
    la $t1 myvar
    sw $a0 0($t1)
    li $a0 0
    li $a0 10
    la $t1 -8($fp)
    sw $a0 0($t1)
    li $a0 0
    li.s $f0 5.0
    mfc1 $a0 $f0
    la $t1 -12($fp)
    sw $a0 0($t1)
    li $a0 0
    la $t1 -8($fp)
    lw $a0 0($t1)
    sw $a0 ($fp)
    la $t1 -12($fp)
    lw $a0 0($t1)
    lw $t1 ($fp)
    mtc1 $t1 $f0
cvt.s.w $f0 $f0
    mtc1 $a0 $f1
    add.s $f0 $f0 $f1
    mfc1 $a0 $f0
    mtc1 $a0 $f12
    li $v0 2
    syscall
    la $a0 newline
    li $v0 4
    syscall
    li $a0 0
    la $t1 myvar
    lw $a0 0($t1)
    la $t1 myvar2
    sw $a0 0($t1)
    li $a0 0
    addiu $sp $sp -8
    la $t1 myvar
    lw $a0 0($t1)
    sw $a0 4($sp)
    la $t1 myvar2
    lw $a0 0($t1)
    sw $a0 8($sp)
    jal avg
    li $a0 1
    la $t1 i
    sw $a0 0($t1)
    li $a0 0
    la $t1 i
    lw $a0 0($t1)
    sw $a0 -4($fp)
    li $a0 30
    lw $t1 -4($fp)
    sle $a0 $t1 $a0    
for_0:
    beq $a0 $zero end_for_0
    la $t1 i
    lw $a0 0($t1)
    li $v0 1
    syscall
    la $a0 newline
    li $v0 4
    syscall
    li $a0 0
    la $t1 i
    lw $a0 0($t1)
    sw $a0 -4($fp)
    li $a0 1
    lw $t1 -4($fp)
    add $a0 $t1 $a0    
    la $t1 i
    sw $a0 0($t1)
    li $a0 0
    la $t1 i
    lw $a0 0($t1)
    sw $a0 -4($fp)
    li $a0 30
    lw $t1 -4($fp)
    sle $a0 $t1 $a0    
    beq $a0 $zero end_for_0
    j for_0
end_for_0:
    li $v0 10
    syscall        # exit