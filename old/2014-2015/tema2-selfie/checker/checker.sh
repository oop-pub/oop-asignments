#!/bin/bash

IMG_COMP="java -jar comparator.jar"

# compiling sources
find -name "*.java" > sources
rm -rf bin
mkdir bin
javac @sources -d bin

# cleanup
rm sources
rm -rf output_files
rm *.bmp &> /dev/null
mkdir output_files

# testing
sum=0
noTests=10

for (( test=0; test<=$noTests; test++ ))
do
	configFile="config_files/config"$test
	stdinFile="stdin_files/input"$test
	outputFile="output"$test
	cat $stdinFile | java -cp bin SimulationManager $configFile > output_files/$outputFile
	`diff -Bw output_files/$outputFile reference_files/$outputFile &> /dev/null`
	if [ $? -ne 0 ]
	then
		echo Test $test .................................... Failed
		rm *.bmp &> /dev/null
		continue
	fi
	images=`ls -d *.bmp`

	err=true
	for img in $images
	do
		mv $img  "output_files/"$img"_test"$test
		err=false
		out_img=$img"_test"$test
		java -jar comparator.jar output_files/$out_img reference_files/$out_img > /dev/null

		if [ $? -ne 0 ]
		then
			err=true
			break
		fi
	done

	if [ $err == true ]
	then
		echo Test $test .................................... Failed
		rm *.bmp &> /dev/null
	else
		sum=$(($sum+1))
		echo Test $test .................................... Passed
	fi
done

echo
echo
echo "Grade .................................... $sum/$noTests"

# clean up
rm -rf bin
rm -rf "output_files"

