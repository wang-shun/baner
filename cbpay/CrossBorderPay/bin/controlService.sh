#!/bin/sh
#   function: 此脚本为启动服务的公共脚本，根据传入参数，启动相应的模块
#   author: tianguangzhao
#   version:1.0 2015/12/24

#设置编码格式，保证中文正确显示
export LANG=en_US.UTF-8

#所需参数

#jar包的公共路径
JAR_PATH=/ztkx/cbpay/CrossBorderPay

#java路径
# update by tianguangzhao 20160519更新测试环境变量是否正确
#JAVA_HOME=/opt/jdk1.7.0_55/bin/java
JAVA_HOME=java

#编码格式，可设为utf-8，gbk等，暂时未用到
CODED_FORMAT=

#lib文件夹位置，用于加载需要的jar包
LIB_HOME=/ztkx/cbpay/CrossBorderPay

#内存等参数
JVM_XMS=512m
JVM_XMX=1024m
JVM_PERM_SIZE=64m
JVM_MAX_PERM_SIZE=128m

#包含所有启动参数的字符串
START_PARAMTERS=

#日志追加方式
LOG_APPEND_WAY=" > "

#打印环境变量以及变量等信息
function printStartMessage {
 
  echo -e "----------------------------------------------模块 $2 $3 开始---------------------------------------------------------------------\n \n"
 
  echo -e "环境变量为: $PATH \n"

  echo -e "jar包路径为: $1 \n"

  echo -e "模块名称: $2 \n"

  echo -e "JAVA_HOME为: ${JAVA_HOME} \n"
  
}

#启动完成后打印结束信息
function printEndMessage {

 echo -e "----------------------------------------------模块 $1 $2完成---------------------------------------------------------------------\n \n"

}

#程序入口
#参数说明：$1 是jar包路径,$2 模块名称,$3是相应操作,startup或者stop,$4是关闭端口,$5是报文位置,$6是远程debug端口
function start { 
 
  printStartMessage $1 $2 $3
 
  prepareparameters $4 $6 $3

 #根据操作不同选择日志追加方式
  if [ $3 = "stop" ]
  then
     LOG_APPEND_WAY=" >> "
  fi

   #凭借最终启动的字符串
    START_PARAMTERS="nohup ${START_PARAMTERS} -jar $1 $2 $3 ${LOG_APPEND_WAY} $5 2>&1 &"
    
    echo -e "启动命令如下: ${START_PARAMTERS} \n"

    #启动
   eval ${START_PARAMTERS} 
   if [ $? -ne 0 ]
    then
        echo "$3模块$2失败，请检查 !"
        exit 1
    fi
  
  printEndMessage $2 $3

}

#将所有启动参数，组合成一条语句
function prepareparameters {
   
  #虚拟机所需参数
  JVM_PARAMETERS=" -Xms${JVM_XMS} -Xmx${JVM_XMX} -XX:PermSize=${JVM_PERM_SIZE} -XX:MaxPermSize=${JVM_MAX_PERM_SIZE} "
  echo -e "java虚拟机相关参数: ${JVM_PARAMETERS} \n"  

  #lib参数
  #LIB_PARAMETERS=" -Djava.ext.dirs=${LIB_HOME} "
  #echo -e "相关jar包参数: ${LIB_PARAMETERS} \n"

  #编码格式，暂时未用到
  #CODE_PARAMETERS=" -Dfile.encoding=${CODED_FORMAT} "
  CODE_PARAMETERS=
  #echo -e "编码格式参数: ${CODE_PARAMETERS} \n"

  #远程debug参数，启动时用到，停止服务时不需要
  REMOTE_DEBUG_PARAMETERS=""
  if [ $3 = "startup" ] 
  then
      REMOTE_DEBUG_PARAMETERS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,address=$2,suspend=n "
  fi

  echo -e "远程dung参数: ${REMOTE_DEBUG_PARAMETERS} \n"  

  #传入HOME_DIR
  HOME_DIR_PARAMETER=" -DHOME_DIR=${JAR_PATH} "
  echo -e "jar包目录参数：${HOME_DIR_PARAMETER} \n"

  #close_port
  CLOSE_PROT_PARAMETER=" -Dclose_port=$1 "
  echo -e "关闭端口参数：${CLOSE_PROT_PARAMETER} \n"   

  #将所有参数进行拼装，并复制给全局变量
  START_PARAMTERS="${JAVA_HOME} ${JVM_PARAMETERS} ${REMOTE_DEBUG_PARAMETERS} ${CODE_PARAMETERS} ${HOME_DIR_PARAMETER} ${CLOSE_PROT_PARAMETER} "

}


start $1 $2 $3 $4 $5 $6

exit 0

