GOOD_CODE_STYLE=`echo -ne "Starting audit...\nAudit done.\n"`
BAD_CODE_STYLE=0

function checkCheckstyle
{
	echo -ne "\nCheckstyle.....................................\t"
	java -jar checkstyle-7.3-all.jar -c poo_checks.xml *  > checkstyle.txt

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