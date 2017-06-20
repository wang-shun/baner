
#!bin/sh
#   function: 启动脚本，调用此脚本可以关闭in，out模块
#   author: tianguangzhao
#   version:1.0 2015/12/24

#设置编码格式，保证中文正确显示
export LANG=en_US.UTF-8

#脚本目录
SHELL_PATH=.

  #关闭in模块
  sh ${SHELL_PATH}/stopIn.sh
  if [ $? -ne 0 ]
  then
      echo -e "in模块停止失败 ! \n"
  fi

   #关闭out模块
   sh ${SHELL_PATH}/stopOut.sh
   if [ $? -ne 0 ]
   then
       echo -e "out模块停止失败 ! \n" 
   fi
    #关闭console服务
  # sh ${SHELL_PATH}/stopCon.sh
  # if [ $? -ne 0 ]
  # then
  #     echo -e "console服务停止失败 ! \n"
  # fi
 
  exit 0 
