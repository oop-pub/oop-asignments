#!/bin/bash


GOOD_CODE_STYLE=`echo -ne "Starting audit...\nAudit done.\n"`
BAD_CODE_STYLE=0
totalTests=21
startTest=1
goodTests=0
badTests=0
badHomework=0
inputName="test"
existingReadme=0
badCompile=0

function checkForReadme
{
	echo -ne "Checking for README: "
	readme=$( find -iname "README" -size +0c | wc -l )
	if [[ $readme -eq  1 ]]; then
		echo -ne "- OK! \n"
		existingReadme=10
	else
		readme=$( find -iname "README.md" -size +0c | wc -l )
		if [[ $readme -eq  1 ]]; then
			echo -ne "- OK! \n"
			existingReadme=10
		else
			readme=$( find -iname "README.txt" -size +0c | wc -l)
			if [[ $readme -eq  1 ]]; then
				echo -ne "- OK! \n"
				existingReadme=10
			else
				echo -ne "- NO README! \n"
				existingReadme=0
			fi
		fi
	fi
}

function checkCodeStyle
{
	java -jar checker/checkstyle/checkstyle-7.3-all.jar -c checker/checkstyle/poo_checks.xml * > checkstyle.txt

	YOUR_CODE_STYLE=`cat checkstyle.txt`

	if [[ "$GOOD_CODE_STYLE" != "$YOUR_CODE_STYLE" ]]; then
		BAD_CODE_STYLE=`cat checkstyle.txt | grep -o 'Checkstyle ends with [0-9]* errors.' | grep -o '[0-9]*'`

		if [[ $BAD_CODE_STYLE -le 30 ]]; then
			echo -ne "Checkstyle erros: $BAD_CODE_STYLE .. OK!\n"
			BAD_CODE_STYLE=0
		else
			echo -ne "Checkstyle erros: $BAD_CODE_STYLE .. FAILED!\n"
		fi
                if [[ $BAD_CODE_STYLE -ge 30 ]]; then
                    BAD_CODE_STYLE=`expr $BAD_CODE_STYLE - 30`
                fi
	else
		echo -ne "Checkstyle erros: 0 .. OK!\n"
	fi
}

function compileHomework
{
	make -f VCSMakefile build &>/dev/null
}
compileHomework
if [ $? -eq 0 ]; then
	checkForReadme
	badCompile=0
	if [ "$1" = "fs" ]; then
		startTest=16
	fi

	if [ "$1" = "vcs" ]; then
		totalTests=15
	fi
	rm -rf ./checker/out >/dev/null 2>&1
	mkdir ./checker/out >/dev/null 2>&1

	for ((nrTest=$startTest; nrTest<=$totalTests; nrTest++))
	do
		if [ $nrTest -eq 1 ]; then
			echo -ne "\nVCS Test .............\n"
		fi
		if [ $nrTest -eq 16 ]; then
			echo -ne "\nFS Test  .............\n"
		fi
		readFrom="./checker/in/test$nrTest.in"
		outName="./checker/out/test$nrTest.out"
		refName="./checker/ref/test$nrTest.ref"
		
		make -f VCSMakefile arg0=$readFrom arg1=$outName run
		if [ $? -ne 0 ]; then
                        if [ $nrTest -le 9 ]; then
                            echo -ne "TEST $nrTest: .............. FAILED! Program error!\n"
                        else
                            echo -ne "TEST $nrTest: ............. FAILED! Program error!\n"
                        fi
			badTests=$((badTests+1))
			badCompile=1
		fi

		if [[ $badCompile -eq 0 ]]; then
			diff $outName $refName &> /dev/null
			if [ $? -eq 0 ]; then
                        if [ $nrTest -le 9 ]; then
				echo -ne "TEST $nrTest: .............. OK!\n"
                                else

				echo -ne "TEST $nrTest: ............. OK!\n"
                                fi
				if [ $nrTest -le 15 ]; then
					goodTests=$((goodTests+1))
				fi
			else
				if [ $nrTest -ge 16 ]; then
					badHomework=1
				fi
                        if [ $nrTest -le 9 ]; then
                            echo -ne "TEST $nrTest:  ............. FAILED!\n"
                        else 
                            echo -ne "TEST $nrTest: ............. FAILED!\n"
                        fi
				if [ $nrTest -le 15 ]; then
					badTests=$((badTests+1))
				fi
			fi
		fi
	done

	checkCodeStyle

	finalPoints=$((goodTests*90/15))
	finalPoints=$((finalPoints - BAD_CODE_STYLE + existingReadme))

	if [[ $badHomework -eq 1 ]];then
		finalPoints=0
		badTests="WRONG FileSystem"
	fi
	echo -ne "BAD TESTS: $badTests\n"
	echo -ne "TOTAL POINTS: $finalPoints\n"

	make -f VCSMakefile clean &>/dev/null
else
	echo -ne "FAIL: Compilation error!\n"
fi


