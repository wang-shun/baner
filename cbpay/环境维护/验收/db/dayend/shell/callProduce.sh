#****************************************************
#*时间:2015-10-21 10:56:13
#*功能:调用存储过程程序
#*作者:sunyoushan
#*版本:0.0.1
#****************************************************
function prepare {
while read line
do
        producename=`echo $line|awk '{print $1}'`
        logNoEcho "读到需要调用的存储过程$producename"
        call $producename
done <$CFG_DIR/produce.cfg
}
function call {
	logNoEcho "调用存储过程$1开始"

	if [ "" = "$ORACLE_SID" ]
	then
	 	TNS_NAME="$USERNAME/${PASSWD}"
	else
		TNS_NAME="$USERNAME/${PASSWD}@$ORACLE_SID"
	fi
	echo $TNS_NAME
	logNoEcho `sqlplus -S ${TNS_NAME}<<EOF
		exec $1();
		exit
EOF`
	logNoEcho "调用存储过程$1结束"
}
prepare
