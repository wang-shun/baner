#!/bin/sh
#function 
#        此脚本用于新建用户并设置密码
#author tianguangzhao
#history  V1.0 2016/10/8

#为保证中文正常显示，首先导入编码格式
export LANG=en_US.UTF-8

function addUser {

#首先请用户输入用户所属组
read  -p  "请输入用户所属组："  group_name

#判断用户组是否存在
count=`cat /etc/group |grep $group_name |wc -l`
if [ $? != 0 ]
then 
   echo -e  "查询用户组失败，请检查 ...\n"
   exit 1 
fi

#如果用户组不存在，创建用户组
if [ $count = 0 ] 
then 
   groupadd $group_name
   if [ $? != 0 ] 
   then 
      echo -e "创建用户组[$group_name]失败，请检查 ...\n"
      exit 1
   fi
fi

#请用户输入用户主目录
read  -p  "请输入用户主目录(格式：/app/myhome)："  user_home

#判断父目录是否存在
parent_path=`echo ${user_home%/*}`

if [ ! -d $parent_path ]
then
   mkdir $parent_path
   if [ $? != 0 ]
   then
       echo -e "创建用户主目录的父目录[$parent_path]失败，请检查 ... \n"
       exit 0
   fi
fi

#请用户输入用户名
read  -p  "请输入用户名："  user_name

#创建用户
useradd -g $group_name -d $user_home $user_name
if [ $? != 0 ]
then 
    echo -e "创建用户[$user_name]失败，主目录[$user_home],所属组[$group_name],请检查...\n"
    exit 1
fi

#请用户输入用户密码
read  -p  "请输入用户密码："  user_passwd
#为用户设定密码
passwd $user_name << EOF
$user_passwd
$user_passwd
EOF

if [ $? != 0 ]
then
    echo -e "设置密码失败,密码为[$user_passwd],请检查...\n"
    exit 1
fi

}

#程序入口调用addUser方法，
addUser

exit 0 
