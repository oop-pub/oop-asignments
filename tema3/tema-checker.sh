#!/bin/bash

## CONSTANTS ##
CURRENT_DIRECTORY=`pwd`
RESOURCES_DIRECTORY="$CURRENT_DIRECTORY/checker/tests"
GOOD_TESTS=0
GOOD_CODE_STYLE=`echo -ne "Starting audit...\nAudit done.\n"`
BAD_CODE_STYLE=0
BONUS_TESTS_SCORE=0

## FUNCTIONS ##
function cleanHomework
{
	find . -name "*.class" -type f -delete
	rm -rf "$RESOURCES_DIRECTORY/out"
	rm -rf "$RESOURCES_DIRECTORY/bonus/out"
}

function compileHomework
{
	javac -g main/Main.java

	mkdir "$RESOURCES_DIRECTORY/out"
	mkdir "$RESOURCES_DIRECTORY/bonus/out"
}

function checkTest
{
    echo -ne "Test\t$1\t.....................................\t"
    java main.Main "$RESOURCES_DIRECTORY/input/$1.in" > "$RESOURCES_DIRECTORY/out/$1.out"

	if [ $? -eq 0 ]; then
        `diff -Bw -u --ignore-all-space $RESOURCES_DIRECTORY/out/$1.out $RESOURCES_DIRECTORY/ref/$1.out.ref &> /dev/null`
        DIFF_RESULT=$?

        if [ $DIFF_RESULT -eq 0 ]; then
        	echo -ne "OK\n"

            GOOD_TESTS=$((GOOD_TESTS+6))
        else
           echo -ne "FAIL (files differ)\n"
        fi
    else
        echo -ne 'FAIL (program error)\n'
    fi
}

function cleanOutputFiles
{
	rm -r $RESOURCES_DIRECTORY/out/*.out
}

function checkCheckstyle
{
	echo -ne "\nCheckstyle.....................................\t"
	java -jar checker/checkstyle/checkstyle-7.3-all.jar -c checker/checkstyle/poo_checks.xml *  > checkstyle.txt

	YOUR_CODE_STYLE=`cat checkstyle.txt`

	if [[ "$GOOD_CODE_STYLE" != "$YOUR_CODE_STYLE" ]]; then
		echo -ne "FAIL\n"
		BAD_CODE_STYLE=`cat checkstyle.txt | grep -o 'Checkstyle ends with [0-9]* errors.' | grep -o '[0-9]*'`
		echo $BAD_CODE_STYLE

		if [[ $BAD_CODE_STYLE -lt 30 ]]; then
			BAD_CODE_STYLE=0
		else
			BAD_CODE_STYLE=20
		fi
	else
		echo -ne "OK\n"
	fi
}

function calculateScore
{
	GOOD_TESTS=$((60-GOOD_TESTS))

	GOOD_TESTS=`echo "scale=2; $GOOD_TESTS" | bc -l`
	BAD_CODE_STYLE=`echo "scale=2; $BAD_CODE_STYLE" | bc -l`

	echo -ne "\n-$GOOD_TESTS failed tests"
	echo -ne "\n-$BAD_CODE_STYLE checkstyle errors"
	echo -ne "\n+$BONUS_TESTS_SCORE bonus tests\n\n"
}

## MAIN EXECUTION ##
cleanHomework
compileHomework

checkTest "0"
checkTest "1"
checkTest "2"
checkTest "3"
checkTest "4"
checkTest "5"
checkTest "6"
checkTest "7"
checkTest "8"
checkTest "9"

cleanOuputFiles

checkCheckstyle

calculateScore

cleanHomework
