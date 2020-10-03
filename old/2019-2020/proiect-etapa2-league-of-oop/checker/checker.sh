#!/bin/bash

## CONSTANTS ##
CURRENT_DIRECTORY=`pwd`
RESOURCES_DIRECTORY="$CURRENT_DIRECTORY/checker/resources"
GOOD_TESTS=0
GOOD_CHECKSTYLE=`echo -ne "Starting audit...\nAudit done.\n"`
BAD_CHECKSTYLE=0

## FUNCTIONS ##
function cleanHomework
{
	find . -name "*.class" -type f -delete
	rm -rf "$RESOURCES_DIRECTORY/out"
}

function compileHomework
{
	if [ -f "$CURRENT_DIRECTORY/FileIO.jar" ]
	then
		unzip FileIO.jar
	fi

	javac -g main/Main.java

	mkdir "$RESOURCES_DIRECTORY/out"
}

function checkTest
{
    echo -ne "Test\t$1\t.....................................\t"
    java main.Main "$RESOURCES_DIRECTORY/in/$1.in" "$RESOURCES_DIRECTORY/out/$1.out" > /dev/null

	if [ $? -eq 0 ]; then
        `diff -Bw -u --ignore-all-space "$RESOURCES_DIRECTORY/out/$1.out" "$RESOURCES_DIRECTORY/res/$1.in.res" &> /dev/null`
        DIFF_RESULT=$?

        if [ $DIFF_RESULT -eq 0 ]; then
        	echo -ne "OK\n"

			if [[ $1 == *x* ]]; then
            	GOOD_TESTS=$((GOOD_TESTS+5))
        	elif [[ $1 == *dense* ]]; then
            	GOOD_TESTS=$((GOOD_TESTS+21))
        	else
            	GOOD_TESTS=$((GOOD_TESTS+1))
        	fi
        else
           echo -ne "FAIL (files differ)\n"
        fi
    else
        echo -ne 'FAIL (program error)\n'
    fi
}

function checkStyle
{
	java -jar checker/checkstyle/checkstyle-7.3-all.jar -c checker/checkstyle/poo_checks.xml *  > checkstyle.txt

	YOUR_CHECKSTYLE=`cat checkstyle.txt`

	if [[ "$GOOD_CHECKSTYLE" != "$YOUR_CHECKSTYLE" ]]; then
		BAD_CHECKSTYLE=`cat checkstyle.txt | grep -o 'Checkstyle ends with [0-9]* errors.' | grep -o '[0-9]*'`

		if [[ $BAD_CHECKSTYLE -lt 30 ]]; then
			BAD_CHECKSTYLE=0
		else
			BAD_CHECKSTYLE=20
		fi
	fi
}

function calculateScore
{
	GOOD_TESTS=$((80-GOOD_TESTS*8/10))

	GOOD_TESTS=`echo "scale=2; $GOOD_TESTS" | bc -l`
	BAD_CHECKSTYLE=`echo "scale=2; $BAD_CHECKSTYLE" | bc -l`

	echo -ne "\n-$GOOD_TESTS failed tests"
	echo -ne "\n-$BAD_CHECKSTYLE checkstyle errors\n\n"
}

function checkBonus
{
	echo -ne "\nGit bonus will be checked manually\n"
}

## MAIN EXECUTION ##
cleanHomework
compileHomework

checkTest "3x3"
checkTest "4x4"
checkTest "5x5"
checkTest "dense"
checkTest "fightKKD"
checkTest "fightKKL"
checkTest "fightKKV"
checkTest "fightKKW"
checkTest "fightKPD"
checkTest "fightKPL"
checkTest "fightKPV"
checkTest "fightKPW"
checkTest "fightKRD"
checkTest "fightKRL"
checkTest "fightKRV"
checkTest "fightKRW"
checkTest "fightKWD"
checkTest "fightKWL"
checkTest "fightKWV"
checkTest "fightKWW"
checkTest "fightPKD"
checkTest "fightPKL"
checkTest "fightPKV"
checkTest "fightPKW"
checkTest "fightPPD"
checkTest "fightPPL"
checkTest "fightPPV"
checkTest "fightPPW"
checkTest "fightPRD"
checkTest "fightPRL"
checkTest "fightPRV"
checkTest "fightPRW"
checkTest "fightPWD"
checkTest "fightPWL"
checkTest "fightPWV"
checkTest "fightPWW"
checkTest "fightRKD"
checkTest "fightRKL"
checkTest "fightRKV"
checkTest "fightRKW"
checkTest "fightRPD"
checkTest "fightRPL"
checkTest "fightRPV"
checkTest "fightRPW"
checkTest "fightRRD"
checkTest "fightRRL"
checkTest "fightRRV"
checkTest "fightRRW"
checkTest "fightRWD"
checkTest "fightRWL"
checkTest "fightRWV"
checkTest "fightRWW"
checkTest "fightWKD"
checkTest "fightWKL"
checkTest "fightWKV"
checkTest "fightWKW"
checkTest "fightWPD"
checkTest "fightWPL"
checkTest "fightWPV"
checkTest "fightWPW"
checkTest "fightWRD"
checkTest "fightWRL"
checkTest "fightWRV"
checkTest "fightWRW"
checkTest "fightWWD"
checkTest "fightWWL"
checkTest "fightWWV"
checkTest "fightWWW"

checkStyle

checkBonus

calculateScore

cleanHomework
