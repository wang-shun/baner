
#!bin/sh
#   function: 启动脚本，调用此脚本可以启动in，out，process三个模块
#   author: tianguangzhao
#   version:1.0 2015/12/24

#设置编码格式，保证中文正确显示
export LANG=en_US.UTF-8

#脚本目录
SHELL_PATH=.

  #启动in模块
  sh ${SHELL_PATH}/startIn.sh
  if [ $? -ne 0 ]
  then
      echo "in模块启动失败 !"
  fi

   #启动out模块
   sh ${SHELL_PATH}/startOut.sh
   if [ $? -ne 0 ]
   then
       echo "out模块启动失败 !" 
   fi
 
  #启动console服务
  # sh ${SHELL_PATH}/startCon.sh
  # if [ $? -ne 0 ]
  # then
  #     echo "console服务启动失败 !"
  # fi 

  exit 0 
