#!/bin/sh
#   function: 此脚本功能是启动in模块
#a   author: tianguangzhao
#   version:1.0 2015/12/24

#设置编码格式，保证中文正确显示
export LANG=en_US.UTF-8

#所需参数
#jar包路径
FILE_PATH=run.jar
 
#模块名称
MODULAR_NAME=IN

#关闭端口
CLOSE_PORT=7687

#debug端口
DEBUG_PORT=9999

#日志目录
NOHUP_PATH="/ztkx/cbpay/logs/in_nohup.out "

#监测进程是否已经启动
function check {

   flag=`ps -ef|grep ${FILE_PATH}|grep ${MODULAR_NAME}|grep -v grep|grep ${LOGNAME}|wc -l`
   if [ $? -ne 0 ]
   then
       echo "模块检查失败，模块名为 ${MODULAR_NAME}  !"
       exit 1

   fi  


   #根据查询进程判断，如果查询到相应进程，则模块已经启动 
   if [ $flag -ne 0 ]
   then
       echo "模块${MODULAR_NAME}正在运行，无需重新启动 ! "
       exit 1   
   fi


}
#程序入口
function start {

  check 

  sh ./controlService.sh ${FILE_PATH} ${MODULAR_NAME} startup ${CLOSE_PORT}  ${NOHUP_PATH} ${DEBUG_PORT}
    if [ $? -ne 0 ]
    then
        echo "模块启动失败，模块名为 $2，请检查  !"
        exit 1

    fi

}



start 
exit 0

