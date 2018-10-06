#!/bin/bash


GOOD_BONUS=`echo -ne "Starting audit...\nAudit done.\n"`
BAD_BONUS=0
totalTests=37
goodTests=0
badTests=0
inputName="test"
existingReadme=0

function checkForReadme
{
	echo -ne "Checking for README: "
	find README &>/dev/null
	if [ $? -eq  0 ]; then
		echo -ne "- OK! \n"
		existingReadme=10
	else
		echo -ne "- NO README! \n"
		existingReadme=0
	fi
}

function checkCodeStyle
{
	java -jar checkstyle/checkstyle-7.3-all.jar -c checkstyle/poo_checks.xml * > checkstyle.txt
	
	YOUR_BONUS=`cat checkstyle.txt`
	
	if [[ "$GOOD_BONUS" != "$YOUR_BONUS" ]]; then
		BAD_BONUS=`cat checkstyle.txt | grep -o 'Checkstyle ends with [0-9]* errors.' | grep -o '[0-9]*'`
		
		if [ $BAD_BONUS -le 30 ]; then
			echo -ne "Checkstyle erros: $BAD_BONUS ............. OK\n"
			BAD_BONUS=0
		else
			echo -ne "Checkstyle erros: $BAD_BONUS ............. FAILED\n"
		fi
	else
		echo -ne "Checkstyle erros: 0 ............. OK\n"
	fi
}

function compileHomework
{
	make -f MiniCADMakefile build &>/dev/null
}

function checkCat
{
	readFrom="./input/cat.in"
	imageName="cat.png"
	java Main $readFrom &>/dev/null

	if [ $? -eq 0 ]; then
		convert drawing.png drawing.rgba
		convert "./ref/$imageName" image.rgba
		cmp {drawing,image}.rgba &>/dev/null		

		##diff "drawing.png" "./ref/$imageName" &>/dev/null
		if [ $? -eq 0 ]; then
			echo -ne "TEST 38: You succeeded in drawing a cat!\n"
			goodTests=$((goodTests+1))
		else
			echo -ne "TEST 38: ............. FAILED!\n"
			badTests=$((badTests+1))
		fi
	else
		echo -ne "TEST 38: ............. FAILED! Program error!\n"
		badTests=$((badTests+1))
	fi
}

function checkOwl
{
	readFrom="./input/owl.in"
	imageName="owl.png"
	java Main $readFrom &>/dev/null

	if [ $? -eq 0 ]; then
		convert drawing.png drawing.rgba
		convert "./ref/$imageName" image.rgba
		cmp {drawing,image}.rgba &>/dev/null

		##diff "drawing.png" "./ref/$imageName" &>/dev/null
		if [ $? -eq 0 ]; then
			echo -ne "TEST 39: You succeeded in drawing an owl!\n"
			goodTests=$((goodTests+1))
		else
			echo -ne "TEST 39: ............. FAILED!\n"
			badTests=$((badTests+1))
		fi
	else
		echo -ne "TEST 39: ............. FAILED! Program error!\n"
		badTests=$((badTests+1))
	fi
}


## MAIN EXECUTION ##

compileHomework
if [ $? -eq 0 ]; then
	checkForReadme

	for ((nrTest=0; nrTest<=$totalTests; nrTest++))
	do
		readFrom="./input/$inputName$nrTest.in"
		imageName="image$nrTest.png"
		java Main $readFrom &>/dev/null

		if [ $? -eq 0 ]; then

			convert drawing.png drawing.rgba
			convert "./ref/$imageName" image.rgba
			cmp {drawing,image}.rgba &>/dev/null

			##diff "drawing.png" "./ref/$imageName" &>/dev/null

			if [ $? -eq 0 ]; then
				echo -ne "TEST $nrTest: ............. OK!\n"
				goodTests=$((goodTests+1))
			else
				echo -ne "TEST $nrTest: ............. FAILED!\n"
				badTests=$((badTests+1))
			fi
		else
			echo -ne "TEST $nrTest: ............. FAILED! Program error!\n"
			badTests=$((badTests+1))
		fi


	done

	checkCat
	checkOwl

	checkCodeStyle

	finalPoints=$((goodTests*90/40))
	finalPoints=$((finalPoints - BAD_BONUS + existingReadme))
	echo -ne "BAD TESTS: $badTests\n"
	echo -ne "TOTAL POINTS: $finalPoints\n"

	make clean &>/dev/null
else
	echo -ne "FAIL: Compilation error!\n"
fi
