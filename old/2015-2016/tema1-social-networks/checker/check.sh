#!/bin/bash

#
# Tema 1 Social Networks Test Suite
#
# 2015, POO
#

# ----------------- General declarations and util functions ------------------ #

OUTPUT_DIR="outputs"
INPUT_DIR="inputs"
REF_DIR="refs"
CLASS_DIR="bin"
MAIN_CLASS="Main"
max_points=11

# common test
test_common()
{
    input_file=${INPUT_DIR}/test$test_index.in
    output_file=${OUTPUT_DIR}/out$test_index.out
    ref_file=${REF_DIR}/ref$test_index.out
    
    java -cp $CLASS_DIR $MAIN_CLASS $input_file > $output_file
    
	# test output
	basic_test diff -Bwui $output_file $ref_file
}

basic_test()
{
	printf "%02d) %s" "$test_index" "$description"

	for ((i = 0; i < 56 - ${#description}; i++)); do
		printf "."
	done

	$@ > /dev/null 2>&1
	if test $? -eq 0; then
		printf "passed  [%02d/%02d]\n" "$points" "$max_points"
	else
		printf "failed  [ 0/%02d]\n" "$max_points"
	fi
}

test_array=(								        \
	"Testing invalid command, empty network"	1	\
	"Testing ADD command"				        1	\
	"Testing REMOVE command"			        1	\
	"Testing FRIEND command"			        1	\
	"Testing UNFRIEND command"			        1	\
	"Testing REMOVE command, inexistent"		1	\
	"Testing COMMUNITIES command, none"		    1	\
	"Testing COMMUNITIES command, simple"		1	\
	"Testing COMMUNITIES command, multiple"		1	\
	"Testing STRENGTH command, simple"		    1	\
	"Testing STRENGTH command, multiple"		1	\
)

check_dirs()
{
    if [ ! -d "$REF_DIR" ]; then
        echo "Couldn't find reference directory, aborting tests."
        exit 1
    fi
    if [ ! -d "$INPUT_DIR" ]; then
        echo "Couldn't find input directory, aborting tests."
        exit 1
    fi	
}

check_dirs

# Compile sources
find . -name "*.java" > sources
rm -rf $CLASS_DIR
mkdir $CLASS_DIR
javac @sources -d $CLASS_DIR

# Compile cleanup
rm sources
rm *.bmp &> /dev/null

rm -rf $OUTPUT_DIR
mkdir $OUTPUT_DIR

for i in $(seq 0 10); do
   test_index=$i;
   arr_index=$(($test_index * 2))
   description=${test_array[$(($arr_index))]}
   points=${test_array[$(($arr_index + 1))]}
   test_common
done | tee results.txt

cat results.txt | grep -a '\[.*\]$' | awk -F '[] /[]+' '
BEGIN {
    sum=0
}

{
	sum += $(NF-2);
}

END {
    printf "\n%66s  [%02d/11]\n", "Total:", sum;
}'

# Final cleanup
rm -f results.txt
rm -rf $CLASS_DIR
rm -rf $OUTPUT_DIR
