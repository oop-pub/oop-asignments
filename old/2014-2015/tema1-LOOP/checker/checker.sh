#!/bin/bash

javac $(find . -name "*.java")

TOTAL=0
idx=0
cd in
for i in *; do
    cd ..
    timeout 2s java main.Main in/$i out &>/dev/null
    diff -iwB out res/"$i".res &>/dev/null
    if [ $? -eq 0 ]; then
        echo "$i ................... passed"
        if [[ $i == *x* ]]; then
            ((TOTAL+=2))
        else
            ((TOTAL+=1))
        fi
    else
        echo "$i ................... failed"
    fi
    cd in
done

find . -name "*.class" -delete

rm -rf out
echo "TOTAL : $TOTAL"
