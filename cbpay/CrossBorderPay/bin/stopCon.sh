#!/bin/sh
#   function: 此脚本功能是关闭console模块
#   author: tianguangzhao
#   version:1.0 2016/2/22

#设置编码格式，保证中文正确显示
export LANG=en_US.UTF-8

#设定java_home 由于本机器默认的java_home 为jdk1.6
# update by tiangunagzhao 2016/5/19 将导入环境变量放入启动脚本中
#export JAVA_HOME=/opt/jdk1.7.0_55
#所需参数

#日志目录,暂时未用到
NOHUP_PATH="/ztkx/cbpay/logs/console_nohup.out"

#tomcat目录
FILE_PATH="/ztkx/cbpay/console/apache-tomcat-7.0.64"

#设定tomcat内存信息
JAVA_OPTS=" -Xms512m -Xmx1024m -XX:PermSize=64m -XX:MaxPermSize=128m "

#监测进程是否已经启动
function check {

   flag=`ps -ef|grep ${FILE_PATH}|grep -v grep|grep ${LOGNAME}|wc -l`
   if [ $? -ne 0 ]
   then
       echo -e "console服务检查失败 ! \n"
       exit 1

   fi  


   #根据查询进程判断，如果查询到相应进程，则模块已经启动 
   if [ $flag -eq 0 ]
   then
       echo -e "console服务并未运行，无需停止 ! \n"
       exit 1   
   fi


}

#程序入口
function start {

  check

  echo "------------------------------------------console 服务正在停止 ！------------------------------------------------------"
  

  #导入参数
  export JAVA_OPTS

  export NOHUP_PATH 

  sh ${FILE_PATH}/bin/shutdown.sh  
    if [ $? -ne 0 ]
    then
        echo "console服务停止失败 ，请检查  !"
        exit 1
    else 
        echo "------------------------------------------console 服务停止成功 ！------------------------------------------------------"
    fi

}



start
exit 0
