#!/bin/bash


GOOD_BONUS=`echo -ne "Starting audit...\nAudit done.\n"`
BAD_BONUS=0
totalTests=8
goodTests=0
badTests=0


function checkCodeStyle
{
    java -jar checkstyle/checkstyle-7.3-all.jar -c checkstyle/poo_checks.xml src > checkstyle.txt

    YOUR_BONUS=`cat checkstyle.txt`

    if [[ "$GOOD_BONUS" != "$YOUR_BONUS" ]]; then
        BAD_BONUS=`cat checkstyle.txt | grep -o 'Checkstyle ends with [0-9]* errors.' | grep -o '[0-9]*'`

        if [ $BAD_BONUS -lt 30 ]; then
            echo -ne "Checkstyle erros: $BAD_BONUS ............. OK\n"
            BAD_BONUS=1
        else
            echo -ne "Checkstyle errors: $BAD_BONUS ............. FAILED\n"
            BAD_BONUS=0
        fi
    else
        echo -ne "Checkstyle errors: 0 ............. OK\n"
        BAD_BONUS=1
    fi
}


function compileHomework
{
    make -f CachingMakefile build &>/dev/null
}


compileHomework
if [ $? -eq 0 ]; then

    mkdir -p outputs

    for ((nrTest=1; nrTest<=$totalTests; nrTest++)) do
        in_file="./tests/test_$nrTest"
        out_file="./outputs/out_$nrTest"
        ref_file="./ref/ref_$nrTest"

        timeout 20s java -cp src Main $in_file > $out_file

        if [ $? -eq 0 ]; then
            diff $out_file $ref_file &>/dev/null
            if [ $? -eq 0 ]; then
                echo -ne "TEST $nrTest: ............. OK!\n"
                goodTests=$((goodTests+1))
            else
                echo -ne "TEST $nrTest: ............. FAILED!\n"
                badTests=$((badTests+1))
            fi
        else
            echo -ne "TEST $nrTest: ............. FAILED! Program error!\n"
            badTests=$((badTests+1))
        fi


    done
    checkCodeStyle

    finalPoints=$((goodTests + BAD_BONUS))
    echo -ne "TOTAL POINTS: $finalPoints\n"
    echo -ne "FAILED TESTS (including checkstyle): $((9 - $finalPoints))\n"

    make -f CachingMakefile clean &>/dev/null

else
    echo -ne "FAIL: Compilation error!\n"
fi
