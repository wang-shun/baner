log "刷新内存开始"
currentDate=`getDate`
java -jar -DLOGNAME="$currentDate" $HOME_DIR/lib/run.jar $HOME_DIR >$HOME_DIR/logs/loaddate.log
log "刷新内存结束"
