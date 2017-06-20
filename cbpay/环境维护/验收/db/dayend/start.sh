#日切程序初始化环境变量
. $1/setEnv.sh $1

if [ ! -e $SHELL_DIR/functionlib.sh ] 
then
  echo "[ERROR]函数库 functionlib.sh 不存在  请检查配置!"
  exit
else
  . $SHELL_DIR/functionlib.sh
fi

log "开始执行cbpay日切"


#开始调用存储过程
. $SHELL_DIR/callProduce.sh

logNoEcho "刷新内存"
. $SHELL_DIR/loaddate.sh
log "cbpay 日切结束"
