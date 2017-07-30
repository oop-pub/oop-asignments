#!/bin/bash

## CONSTANTS ##
CURRENT_DIRECTORY=`pwd`
RESOURCES_DIRECTORY="$CURRENT_DIRECTORY/checker/resources"
GOOD_TESTS=0
BAD_TESTS=0
GOOD_BONUS=`echo -ne "Starting audit...\nAudit done.\n"`
BAD_BONUS=0

## FUNCTIONS ##
function cleanHomework
{
	find . -name "*.class" -type f -delete
}

function compileHomework
{
	javac -g Main.java
}

function checkTest
{
    echo -ne "Test\t$1\t.....................................\t"
    java Main "$RESOURCES_DIRECTORY/$1/sub$1" "$RESOURCES_DIRECTORY/$1/in$1" "$RESOURCES_DIRECTORY/$1/out$1" "$RESOURCES_DIRECTORY/$1/ref$1"
    
	if [ $? -eq 0 ]; then
        `diff -Bw -u --ignore-all-space $RESOURCES_DIRECTORY/$1/out$1 $RESOURCES_DIRECTORY/$1/ref$1 &> /dev/null`
        DIFF_RESULT=$?

        if [ $DIFF_RESULT -eq 0 ]; then
           echo -ne "OK\n"
           GOOD_TESTS=$((GOOD_TESTS+1))
        else
           echo -ne "FAIL (files differ)\n"
           BAD_TESTS=$((BAD_TESTS+45))
        fi
    else
        echo -ne 'FAIL (program error)\n'
        BAD_TESTS=$((BAD_TESTS+45))
    fi
}

function checkBonus
{
	echo -ne "Bonus\t\t.....................................\t"
	java -jar checker/checkstyle/checkstyle-7.3-all.jar -c checker/checkstyle/poo_checks.xml * > checkstyle.txt
	
	YOUR_BONUS=`cat checkstyle.txt`
	
	if [[ "$GOOD_BONUS" != "$YOUR_BONUS" ]]; then
		echo -ne "FAIL\n"
		BAD_BONUS=`cat checkstyle.txt | grep -o 'Checkstyle ends with [0-9]* errors.' | grep -o '[0-9]*'`
		
		if [ $BAD_BONUS -lt 30 ]; then
			BAD_BONUS=0
		fi
	else
		echo -ne "OK\n"
	fi
}

function calculateScore
{
	BAD_TESTS=`echo "scale=2; -$BAD_TESTS/100" | bc -l`
	BAD_BONUS=`echo "scale=2; -$BAD_BONUS/10" | bc -l`

	echo -ne "\n\nPassed $GOOD_TESTS/20\n"
	echo -ne "\n$BAD_TESTS failed tests"
	echo -ne "\n$BAD_BONUS checkstyle errors\n\n"
}

## MAIN EXECUTION ##
cleanHomework
compileHomework

checkTest "01"
checkTest "02"
checkTest "03"
checkTest "04"
checkTest "05"
checkTest "06"
checkTest "07"
checkTest "08"
checkTest "09"
checkTest "10"
checkTest "11"
checkTest "12"
checkTest "13"
checkTest "14"
checkTest "15"
checkTest "16"
checkTest "17"
checkTest "18"
checkTest "19"
checkTest "20"
checkBonus

calculateScore

cleanHomework