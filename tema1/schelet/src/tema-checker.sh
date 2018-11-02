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
        `diff -Bw -u --ignore-all-space $RESOURCES_DIRECTORY/out/$1.out $RESOURCES_DIRECTORY/ref/$1.in.ref &> /dev/null`
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

function checkBonus
{
    echo -ne "Test\t$1\n"
    CRT_BONUS_FILE="$RESOURCES_DIRECTORY/bonus/input/$1.in"
    PLAYER=$(sed -n 2p $CRT_BONUS_FILE)
    WIZARD="wizard"
    ORDER="['$WIZARD', '$PLAYER']"

    BONUS_TEST="$RESOURCES_DIRECTORY/bonus/input/bonusTest.in"
    touch $BONUS_TEST
    sed -n 1p $CRT_BONUS_FILE > $BONUS_TEST
    echo $ORDER >> $BONUS_TEST

    OUT_TEST="$RESOURCES_DIRECTORY/bonus/out/$1.out"

    TEST_RESULT="FAIL"

    echo -ne "\t\t$WIZARD vs $PLAYER:\n"
    java main.Main $BONUS_TEST > $OUT_TEST

	if [ $? -eq 0 ]; then

        WIZARD_OUT=$(echo $WIZARD | tr a-z A-Z)
        PLAYER_OUT=$(echo $PLAYER | tr a-z A-Z)

	    echo -en "\t\t\t"
	    sed -n 1p $OUT_TEST

	    echo -en "\t\t\t"
	    sed -n 2p $OUT_TEST

        WIZARD_SCORE=$(cat $OUT_TEST | grep $WIZARD_OUT | grep -o '[0-9]*')
#        WIZARD_SCORE="137"
        PLAYER_SCORE=$(cat $OUT_TEST | grep $PLAYER_OUT | grep -o '[0-9]*')
#        PLAYER_SCORE="109"

        ROUND1_SCORE=$(($WIZARD_SCORE-PLAYER_SCORE))
        echo -en "\t\t\t\tRound score: $ROUND1_SCORE\n"

#        ROUND 2
        rm -rf $BONUS_TEST

        ORDER="['$PLAYER', '$WIZARD']"
        touch $BONUS_TEST
        sed -n 1p $CRT_BONUS_FILE > $BONUS_TEST
        echo $ORDER >> $BONUS_TEST

        echo -ne "\t\t$PLAYER vs $WIZARD:\n"
        java main.Main $BONUS_TEST > $OUT_TEST

        if [ $? -eq 0 ]; then

            echo -en "\t\t\t"
            sed -n 1p $OUT_TEST

            echo -en "\t\t\t"
            sed -n 2p $OUT_TEST

            WIZARD_SCORE=$(cat $OUT_TEST | grep $WIZARD_OUT | grep -o '[0-9]*')
#            WIZARD_SCORE="128"
            PLAYER_SCORE=$(cat $OUT_TEST | grep $PLAYER_OUT | grep -o '[0-9]*')
#            PLAYER_SCORE="109"

            ROUND2_SCORE=$(($WIZARD_SCORE-PLAYER_SCORE))
            echo -en "\t\t\t\tRound score: $ROUND2_SCORE\n"

            TOTAL_SCORE=$(($ROUND1_SCORE+$ROUND2_SCORE))
            if [ $TOTAL_SCORE -gt 0 ]; then
                BONUS_TESTS_SCORE=$((BONUS_TESTS_SCORE+1))

                TEST_RESULT="OK (total score: $TOTAL_SCORE)"
            else
                TEST_RESULT="FAIL (total score: $TOTAL_SCORE)"
            fi

            echo -ne "\n\t$1 $TEST_RESULT\n"
        else
            echo -ne '\t\tFAIL (program error)\n'
        fi
    else
        echo -ne '\t\tFAIL (program error)\n'
    fi

    rm -rf $BONUS_TEST

    echo -ne "................................................\n"
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

checkTest "test0"
checkTest "test1"
checkTest "test2"
checkTest "test3"
checkTest "test4"
checkTest "test5"
checkTest "test6"
checkTest "test7"
checkTest "test8"
checkTest "test9"

checkBonus "bonus1"
checkBonus "bonus2"
checkBonus "bonus3"
checkBonus "bonus4"
checkBonus "bonus5"
checkBonus "bonus6"
checkBonus "bonus7"
checkBonus "bonus8"
checkBonus "bonus9"
checkBonus "bonus10"
checkBonus "bonus11"
checkBonus "bonus12"
checkBonus "bonus13"
checkBonus "bonus14"
checkBonus "bonus15"
checkBonus "bonus16"
checkBonus "bonus17"
checkBonus "bonus18"
checkBonus "bonus19"
checkBonus "bonus20"

checkCheckstyle

calculateScore

cleanHomework
