#!/bin/sh
#   function: 此脚本功能是关闭in模块
#   author: tianguangzhao
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

#日志目录和打印方式
NOHUP_PATH=" /ztkx/cbpay/logs/in_nohup.out "

#监测进程是否已经启动
function check {

   flag=`ps -ef|grep ${FILE_PATH}|grep ${MODULAR_NAME}|grep -v grep|grep ${LOGNAME}|wc -l`
   if [ $? -ne 0 ]
   then
       echo -e "模块检查失败，模块名为 ${MODULAR_NAME}  ! \n"
       exit 1

   fi  


   #根据查询进程判断，如果查询到相应进程，则模块已经启动 
   if [ $flag -eq 0 ]
   then
       echo -e "模块${MODULAR_NAME}并未运行，无需停止 ! \n"
       exit 1   
   fi


}
#程序入口
function start {

  check
 
#参数说明：$1 是jar包路径,$2 模块名称,$3是相应操作,startup或者stop,$4是关闭端口,$5是报文位置
  sh ./controlService.sh ${FILE_PATH} ${MODULAR_NAME} stop ${CLOSE_PORT}  ${NOHUP_PATH}
    if [ $? -ne 0 ]
    then
        echo -e "模块停止失败，模块名为 $2，请检查  ! \n"
        exit 1

    fi

}



start 
exit 0

