cd ..
HOME_DIR_HAND=`pwd`
echo $HOME_DIR_HAND
. $HOME_DIR_HAND/setEnv.sh $HOME_DIR_HAND
. $HOME_DIR_HAND/shell/functionlib.sh
log "调用存储过程$1开始"

        TNS_NAME="$USERNAME/${PASSWD}@$ORACLE_SID"
        log `sqlplus -S ${TNS_NAME}<<EOF
                exec manualdayend();
                exit
EOF`
        log "调用存储过程$1结束"
currentDate=`getDate`
log "刷新内存开始"
java -jar -DLOGNAME="$currentDate" $HOME_DIR/lib/run.jar $HOME_DIR >$HOME_DIR/logs/loaddate.log
log "刷新内存结束"
