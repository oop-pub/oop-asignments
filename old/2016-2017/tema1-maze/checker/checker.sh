#!/bin/bash

#
# Tema 1 Maze
#
# 2016, POO
#

# ----------------- General declarations and util functions ------------------ #

OUTPUT_DIR="outputs"
INPUT_DIR="inputs"
REF_DIR="refs"
CLASS_DIR="bin"
MAIN_CLASS="Game"
max_points=100

# common test
test_common()
{
	input_file=${INPUT_DIR}/test$test_index.in
	output_file=maze.out
	ref_file=${REF_DIR}/ref$test_index.out

	cp $input_file maze.in
	java -cp $CLASS_DIR $MAIN_CLASS

	# test output
	basic_test diff -Bwui $output_file $ref_file
	rm maze.in maze.out
}

basic_test()
{
	printf "Test %s" "$test_index"

	for ((i = 0; i < 30; i++)); do
		printf "."
	done

	$@ > /dev/null 2>&1
	if test $? -eq 0; then
		printf "passed  [%02d/%02d]\n" "$points" "$max_points"
	else
		printf "failed  [ 0/%02d]\n" "$max_points"
	fi
}

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

for i in $(seq 0 9); do
   test_index=$i;
   points=10
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
    printf "\n%66s  [%02d/100]\n", "Total:", sum;
}'

# Final cleanup
rm -f results.txt
rm -rf $CLASS_DIR
