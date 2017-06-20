echo "">a.txt
for dir in `ls ./cbpay/db|awk '{print $1}'`
do
	find ./cbpay/db/$dir -name "*sql">>./a.txt
	find ./cbpay/db/$dir -name "*prc">>./a.txt
done
echo "spool ./logs/exesql.spool ">total.sql
while read line
do
	if [ -f "$line" ]
	then
	echo "--${line} is begin-- ">>total.sql 
	echo "prompt ${line} is begin">>total.sql
	cat $line >> total.sql
	echo "">>total.sql
	echo "prompt ${line} is end">>total.sql
	echo "--${line} is end-- ">>total.sql
	fi
done<./a.txt
echo "" >> total.sql
echo "spool off;" >> total.sql
echo "/">>total.sql
echo "exit">>total.sql
chmod 755 total.sql
rm a.txt
