#!/bin/sh
#   function: 此脚本功能是启动console模块
#   author: tianguangzhao
#   version:1.0 2016/2/22

#设置编码格式，保证中文正确显示
export LANG=en_US.UTF-8

#设定java_home 由于本机器默认的java_home 为jdk1.6
#update  by tianguangzhao 2016/5/19 去掉导入命令，在该用户的启动脚本中实现
#export JAVA_HOME=/opt/jdk1.7.0_55

#所需参数

#日志目录,暂时未用到
NOHUP_PATH="/ztkx/cbpay/logs/console_nohup.out"
#tomcat目录
FILE_PATH="/ztkx/cbpay/console/apache-tomcat-7.0.64"
#设定tomcat内存信息 
JAVA_OPTS=" -Xms512m -Xmx1024m -XX:PermSize=64m -XX:MaxPermSize=128m -Dcontainer_path=/ztkx/cbpay/console/config/ "

#设定class目录
#CONTAINER_PATH=" -Dcontainer_path=/ztkx/cbpay/console/apache-tomcat-7.0.64/webapps/console/WEB-INF/classes/ "

#监测进程是否已经启动
function check {

   flag=`ps -ef|grep ${FILE_PATH}|grep -v grep|grep ${LOGNAME}|wc -l`
   if [ $? -ne 0 ]
   then
       echo -e "console服务检查失败 ! \n"
       exit 1

   fi  


   #根据查询进程判断，如果查询到相应进程，则模块已经启动 
   if [ $flag -ne 0 ]
   then
       echo -e "console服务正在运行，无需启动 ! \n"
       exit 1   
   fi


}


#程序入口
function start {

  check

  echo "----------------------------------------------console服务开始启动！-----------------------------------------------"

  #导入参数

  export JAVA_OPTS

  export NOHUP_PATH 

  export CONTAINER_PATH

  sh ${FILE_PATH}/bin/startup.sh 
    if [ $? -ne 0 ]
    then
        echo "console服务启动失败 ，请检查  !"
        exit 1
    else
        echo " " > ${NOHUP_PATH}
        echo "----------------------------------------------console服务启动成功！-----------------------------------------------"
    fi

}



start
exit 0
