#!/bin/bash

## CONSTANTS ##
CURRENT_DIRECTORY=`pwd`
RESOURCES_DIRECTORY="checker/tests"
GOOD_TESTS=0
GOOD_CODE_STYLE=`echo -ne "Starting audit...\nAudit done.\n"`
BAD_CODE_STYLE=0

## FUNCTIONS ##
function cleanHomework
{
	rm dummy 2> /dev/null
	find . -name "*.class" -type f -delete
	rm -rf "$RESOURCES_DIRECTORY/out"
}

function compileHomework
{
	javac -cp ".:FileIO.jar" -g com/tema1/main/Main.java 

	mkdir "$RESOURCES_DIRECTORY/out"
}

function checkTest
{
    echo -ne "Test\t$1\t.....................................\t"
    java -cp ".:FileIO.jar" com/tema1/main/Main "$RESOURCES_DIRECTORY/in/$1.in" dummy > "$RESOURCES_DIRECTORY/out/$1.out"

	if [ $? -eq 0 ]; then
        `diff -Bw -u --ignore-all-space $RESOURCES_DIRECTORY/out/$1.out $RESOURCES_DIRECTORY/ref/$1.ref &> /dev/null`
        DIFF_RESULT=$?

        if [ $DIFF_RESULT -eq 0 ]; then
        	echo -ne "OK\n"

            GOOD_TESTS=$((GOOD_TESTS))
        else
           echo -ne "FAIL (files differ)\n"
        fi
    else
        echo -ne 'FAIL (program error)\n'
    fi
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
	GOOD_TESTS=$((GOOD_TESTS))

	GOOD_TESTS=`echo "scale=2; $GOOD_TESTS" | bc -l`
	BAD_CODE_STYLE=`echo "scale=2; $BAD_CODE_STYLE" | bc -l`

	echo -ne "\n-$GOOD_TESTS failed tests"
	echo -ne "\n-$BAD_CODE_STYLE checkstyle errors"
}

## MAIN EXECUTION ##
cleanHomework
compileHomework

checkTest "test1"
checkTest "test2"
checkTest "test3"
checkTest "test4"
checkTest "test5"
checkTest "test6"
checkTest "test7"
checkTest "test8"
checkTest "test9"

checkCheckstyle

calculateScore

cleanHomework
