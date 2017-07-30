#!/bin/bash

div ()  # Arguments: dividend and divisor
{
        if [ $2 -eq 0 ]; then echo division by 0; exit; fi
        local p=12                            # precision
        local c=${c:-0}                       # precision counter
        local d=.                             # decimal separator
        local r=$(($1/$2)); echo -n $r        # result of division
        local m=$(($r*$2))
        [ $c -eq 0 ] && [ $m -ne $1 ] && echo -n $d
        [ $1 -eq $m ] || [ $c -eq $p ] && return
        local e=$(($1-$m))
        let c=c+1
        div $(($e*10)) $2
}  

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

for (( test=1; test<=$noTests; test++ ))
do
	stdinFile="input_files/in"$test".txt"
	outputFile="out"$test.txt
	referenceFile="ref"$test.txt
	java -cp bin Test $stdinFile > output_files/$outputFile
	`$COMP -Bw output_files/$outputFile reference_files/$referenceFile &> /dev/null`
	result=$?
	if [ $result -eq 0 ]
	then
		if [ $test -eq 1 ] || [ $test -eq 2 ] || [ $test -eq 3 ] || [ $test -eq 4 ]
		then
			sum=$(($sum+1))
		elif [ $test -eq 9 ] || [ $test -eq 10 ]
		then
			sum=$(($sum+4))		
		else 
			sum=$(($sum+2))
		fi
		echo $test .................................... Passed
	else
		echo $test .................................... Failed
	fi
done

echo
echo
echo "Grade .................................... $(div $sum 2)/$(($noTests))"

# clean up
rm -rf bin

