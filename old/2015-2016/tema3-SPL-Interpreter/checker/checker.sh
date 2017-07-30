#!/bin/bash

COMP="diff"
TEST_POINTS=( 5 5 10 10 10 10 10 10 15 15 );

# compiling sources
find -name "*.java" > sources
rm -rf bin
mkdir bin
javac @sources -d bin

# cleanup
rm sources
rm -rf output
mkdir output

# testing
sum=0
noTests=`ls tests | wc -l`

java -cp bin Interpreter

for (( test=1; test<=$noTests; test++ ))
do
	stdinFile="tests/test"$test".spl"
	outputFile="test"$test
	referenceFile="test"$test

	`$COMP -Bw output/$outputFile.ast tests-ref/$referenceFile.ast &> /dev/null`
	result_ast=$?
	if [ $result_ast -ne 0 ]; then
		echo "test$test.ast differs from expected output"
	fi
	`$COMP -Bw output/$outputFile.out tests-ref/$referenceFile.out &> /dev/null`
	result_out=$?
	if [ $result_out -ne 0 ]; then
		echo "test$test.out differs from expected output"
	fi
	if [ $result_ast -eq 0 ] && [ $result_out -eq 0 ]; then
		sum=$(($sum+TEST_POINTS[$test - 1]))
		echo $test .................................... Passed [$((TEST_POINTS[$test - 1])) p]
	else
		echo $test .................................... Failed [0 p]
	fi
done

echo
echo
echo "Grade .................................... $sum"

# clean up
rm -rf bin

