#!/bin/bash

## CONSTANTS ##
CURRENT_DIRECTORY=`pwd`
RESOURCES_DIRECTORY="checker/tests"
GOOD_TESTS=0
GOOD_CODE_STYLE=`echo -ne "Starting audit...\nAudit done.\n"`
BAD_CODE_STYLE=0
no_tests=240
total_score=0
test_points=1

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
			total_score=$((total_score + test_points))

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
	#show_points = `echo "scale=2; $((total_score / 4))" | bc -l`
	BAD_CODE_STYLE=`echo "scale=2; $BAD_CODE_STYLE" | bc -l`

	echo -ne "\n$total_score / 240 tests passed\n"
	
	echo -ne "\n$BAD_CODE_STYLE checkstyle errors"
}

## MAIN EXECUTION ##
cleanHomework
compileHomework

checkTest "1round2players-illegal-only-test1"
checkTest "1round2players-illegal-only-test2"
checkTest "1round2players-illegal-only-test3"
checkTest "1round2players-illegal-only-test4"
checkTest "1round2players-illegal-only-test5"
checkTest "1round2players-illegal-only-test6"
checkTest "1round2players-illegal-only-test7"
checkTest "1round2players-illegal-only-test8"
checkTest "1round2players-illegal-only-test9"

checkTest "1round2players-legal-only-test1"
checkTest "1round2players-legal-only-test2"
checkTest "1round2players-legal-only-test3"
checkTest "1round2players-legal-only-test4"
checkTest "1round2players-legal-only-test5"
checkTest "1round2players-legal-only-test6"
checkTest "1round2players-legal-only-test7"
checkTest "1round2players-legal-only-test8"
checkTest "1round2players-legal-only-test9"

checkTest "1round2players-mixed-test1"
checkTest "1round2players-mixed-test2"
checkTest "1round2players-mixed-test3"
checkTest "1round2players-mixed-test4"
checkTest "1round2players-mixed-test5"
checkTest "1round2players-mixed-test6"
checkTest "1round2players-mixed-test7"
checkTest "1round2players-mixed-test8"
checkTest "1round2players-mixed-test9"

checkTest "2round2players-illegal-only-test1"
checkTest "2round2players-illegal-only-test2"
checkTest "2round2players-illegal-only-test3"
checkTest "2round2players-illegal-only-test4"
checkTest "2round2players-illegal-only-test5"
checkTest "2round2players-illegal-only-test6"
checkTest "2round2players-illegal-only-test7"
checkTest "2round2players-illegal-only-test8"
checkTest "2round2players-illegal-only-test9"

checkTest "2round2players-legal-only-test1"
checkTest "2round2players-legal-only-test2"
checkTest "2round2players-legal-only-test3"
checkTest "2round2players-legal-only-test4"
checkTest "2round2players-legal-only-test5"
checkTest "2round2players-legal-only-test6"
checkTest "2round2players-legal-only-test7"
checkTest "2round2players-legal-only-test8"
checkTest "2round2players-legal-only-test9"

checkTest "2round2players-mixed-test1"
checkTest "2round2players-mixed-test2"
checkTest "2round2players-mixed-test3"
checkTest "2round2players-mixed-test4"
checkTest "2round2players-mixed-test5"
checkTest "2round2players-mixed-test6"
checkTest "2round2players-mixed-test7"
checkTest "2round2players-mixed-test8"
checkTest "2round2players-mixed-test9"

checkTest "1round3players-illegal-only-test1"
checkTest "1round3players-illegal-only-test2"
checkTest "1round3players-illegal-only-test3"
checkTest "1round3players-illegal-only-test4"
checkTest "1round3players-illegal-only-test5"
checkTest "1round3players-illegal-only-test6"
checkTest "1round3players-illegal-only-test7"
checkTest "1round3players-illegal-only-test8"
checkTest "1round3players-illegal-only-test9"
checkTest "1round3players-illegal-only-test10"
checkTest "1round3players-illegal-only-test11"
checkTest "1round3players-illegal-only-test12"
checkTest "1round3players-illegal-only-test13"
checkTest "1round3players-illegal-only-test14"
checkTest "1round3players-illegal-only-test15"
checkTest "1round3players-illegal-only-test16"
checkTest "1round3players-illegal-only-test17"
checkTest "1round3players-illegal-only-test18"
checkTest "1round3players-illegal-only-test19"
checkTest "1round3players-illegal-only-test20"
checkTest "1round3players-illegal-only-test21"
checkTest "1round3players-illegal-only-test22"
checkTest "1round3players-illegal-only-test23"
checkTest "1round3players-illegal-only-test24"
checkTest "1round3players-illegal-only-test25"
checkTest "1round3players-illegal-only-test26"
checkTest "1round3players-illegal-only-test27"

checkTest "1round3players-legal-only-test1"
checkTest "1round3players-legal-only-test2"
checkTest "1round3players-legal-only-test3"
checkTest "1round3players-legal-only-test4"
checkTest "1round3players-legal-only-test5"
checkTest "1round3players-legal-only-test6"
checkTest "1round3players-legal-only-test7"
checkTest "1round3players-legal-only-test8"
checkTest "1round3players-legal-only-test9"
checkTest "1round3players-legal-only-test10"
checkTest "1round3players-legal-only-test11"
checkTest "1round3players-legal-only-test12"
checkTest "1round3players-legal-only-test13"
checkTest "1round3players-legal-only-test14"
checkTest "1round3players-legal-only-test15"
checkTest "1round3players-legal-only-test16"
checkTest "1round3players-legal-only-test17"
checkTest "1round3players-legal-only-test18"
checkTest "1round3players-legal-only-test19"
checkTest "1round3players-legal-only-test20"
checkTest "1round3players-legal-only-test21"
checkTest "1round3players-legal-only-test22"
checkTest "1round3players-legal-only-test23"
checkTest "1round3players-legal-only-test24"
checkTest "1round3players-legal-only-test25"
checkTest "1round3players-legal-only-test26"
checkTest "1round3players-legal-only-test27"

checkTest "1round3players-mixed-test1"
checkTest "1round3players-mixed-test2"
checkTest "1round3players-mixed-test3"
checkTest "1round3players-mixed-test4"
checkTest "1round3players-mixed-test5"
checkTest "1round3players-mixed-test6"
checkTest "1round3players-mixed-test7"
checkTest "1round3players-mixed-test8"
checkTest "1round3players-mixed-test9"
checkTest "1round3players-mixed-test10"
checkTest "1round3players-mixed-test11"
checkTest "1round3players-mixed-test12"
checkTest "1round3players-mixed-test13"
checkTest "1round3players-mixed-test14"
checkTest "1round3players-mixed-test15"
checkTest "1round3players-mixed-test16"
checkTest "1round3players-mixed-test17"
checkTest "1round3players-mixed-test18"
checkTest "1round3players-mixed-test19"
checkTest "1round3players-mixed-test20"
checkTest "1round3players-mixed-test21"
checkTest "1round3players-mixed-test22"
checkTest "1round3players-mixed-test23"
checkTest "1round3players-mixed-test24"
checkTest "1round3players-mixed-test25"
checkTest "1round3players-mixed-test26"
checkTest "1round3players-mixed-test27"

checkTest "2round3players-illegal-only-test1"
checkTest "2round3players-illegal-only-test2"
checkTest "2round3players-illegal-only-test3"
checkTest "2round3players-illegal-only-test4"
checkTest "2round3players-illegal-only-test5"
checkTest "2round3players-illegal-only-test6"
checkTest "2round3players-illegal-only-test7"
checkTest "2round3players-illegal-only-test8"
checkTest "2round3players-illegal-only-test9"
checkTest "2round3players-illegal-only-test10"
checkTest "2round3players-illegal-only-test11"
checkTest "2round3players-illegal-only-test12"
checkTest "2round3players-illegal-only-test13"
checkTest "2round3players-illegal-only-test14"
checkTest "2round3players-illegal-only-test15"
checkTest "2round3players-illegal-only-test16"
checkTest "2round3players-illegal-only-test17"
checkTest "2round3players-illegal-only-test18"
checkTest "2round3players-illegal-only-test19"
checkTest "2round3players-illegal-only-test20"
checkTest "2round3players-illegal-only-test21"
checkTest "2round3players-illegal-only-test22"
checkTest "2round3players-illegal-only-test23"
checkTest "2round3players-illegal-only-test24"
checkTest "2round3players-illegal-only-test25"
checkTest "2round3players-illegal-only-test26"
checkTest "2round3players-illegal-only-test27"

checkTest "2round3players-legal-only-test1"
checkTest "2round3players-legal-only-test2"
checkTest "2round3players-legal-only-test3"
checkTest "2round3players-legal-only-test4"
checkTest "2round3players-legal-only-test5"
checkTest "2round3players-legal-only-test6"
checkTest "2round3players-legal-only-test7"
checkTest "2round3players-legal-only-test8"
checkTest "2round3players-legal-only-test9"
checkTest "2round3players-legal-only-test10"
checkTest "2round3players-legal-only-test11"
checkTest "2round3players-legal-only-test12"
checkTest "2round3players-legal-only-test13"
checkTest "2round3players-legal-only-test14"
checkTest "2round3players-legal-only-test15"
checkTest "2round3players-legal-only-test16"
checkTest "2round3players-legal-only-test17"
checkTest "2round3players-legal-only-test18"
checkTest "2round3players-legal-only-test19"
checkTest "2round3players-legal-only-test20"
checkTest "2round3players-legal-only-test21"
checkTest "2round3players-legal-only-test22"
checkTest "2round3players-legal-only-test23"
checkTest "2round3players-legal-only-test24"
checkTest "2round3players-legal-only-test25"
checkTest "2round3players-legal-only-test26"
checkTest "2round3players-legal-only-test27"

checkTest "2round3players-mixed-test1"
checkTest "2round3players-mixed-test2"
checkTest "2round3players-mixed-test3"
checkTest "2round3players-mixed-test4"
checkTest "2round3players-mixed-test5"
checkTest "2round3players-mixed-test6"
checkTest "2round3players-mixed-test7"
checkTest "2round3players-mixed-test8"
checkTest "2round3players-mixed-test9"
checkTest "2round3players-mixed-test10"
checkTest "2round3players-mixed-test11"
checkTest "2round3players-mixed-test12"
checkTest "2round3players-mixed-test13"
checkTest "2round3players-mixed-test14"
checkTest "2round3players-mixed-test15"
checkTest "2round3players-mixed-test16"
checkTest "2round3players-mixed-test17"
checkTest "2round3players-mixed-test18"
checkTest "2round3players-mixed-test19"
checkTest "2round3players-mixed-test20"
checkTest "2round3players-mixed-test21"
checkTest "2round3players-mixed-test22"
checkTest "2round3players-mixed-test23"
checkTest "2round3players-mixed-test24"
checkTest "2round3players-mixed-test25"
checkTest "2round3players-mixed-test26"
checkTest "2round3players-mixed-test27"

checkTest "3round4players-mixed-test1"
checkTest "3round4players-mixed-test2"
checkTest "3round4players-mixed-test3"
checkTest "3round4players-mixed-test4"

checkTest "3round2players-mixed-test1"
checkTest "3round2players-mixed-test2"
checkTest "3round2players-mixed-test3"
checkTest "3round2players-mixed-test4"

checkTest "4round3players-mixed-test1"
checkTest "4round3players-mixed-test2"
checkTest "4round3players-mixed-test3"
checkTest "4round3players-mixed-test4"

checkTest "4round5players-mixed-test1"
checkTest "4round5players-mixed-test2"
checkTest "4round5players-mixed-test3"
checkTest "4round5players-mixed-test4"

checkTest "2round4players-mixed-test1"
checkTest "2round4players-mixed-test2"
checkTest "2round4players-mixed-test3"
checkTest "2round4players-mixed-test4"

checkTest "5round7players-mixed-test1"
checkTest "5round7players-mixed-test2"
checkTest "5round7players-mixed-test3"
checkTest "5round7players-mixed-test4"

checkCheckstyle

calculateScore

cleanHomework
