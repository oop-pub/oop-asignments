#!/bin/bash

COMP="diff"

# compiling sources
find -name "*.java" > sources
rm -rf bin
mkdir bin
javac @sources -d bin

# cleanup
rm sources
rm -rf output_files
mkdir output_files

# testing
sum=0
noTests=`ls input_files | wc -l`

for (( test=0; test<$noTests; test++ ))
do
	stdinFile="input_files/input_"$test
	outputFile="output_"$test
	referenceFile="ref_"$test
	java -cp bin Main $stdinFile > output_files/$outputFile
	`$COMP -Bw output_files/$outputFile reference_files/$referenceFile &> /dev/null`
	result=$?
	if [ $result -eq 0 ]
	then
		sum=$(($sum+1))
		echo $test .................................... Passed
	else
		echo $test .................................... Failed
	fi
done

echo
echo
echo "Grade .................................... $sum/$noTests"

# clean up
rm -rf bin

