#****************************************************
#*时间:2015-10-21 10:56:13
#*功能:为日切程序提供函数库
#*作者:zhangxiaoyun
#*版本:0.0.1
#****************************************************
function getDate(){
	echo `date +%Y%m%d`
}


function getTime(){
	echo `date +%H:%M:%S`
}

#获取n天前日期
function getBeforeDay(){
	echo `date -d "$1 days ago" +%Y%m%d`
}

#删除n天前的文件
function getBeforeDay(){
	echo `date -d "$1 days ago" +%Y%m%d`
}


function log(){
	echo -e "=======================`getDate`  `getTime` : $* \n\t"
	echo -e "=======================`getDate`  `getTime` : $* \n\t">>$LOG_DIR/`getDate`.log
}

function logNoEcho(){
	echo -e "=======================`getDate`  `getTime` : $* \n\t">>$LOG_DIR/`getDate`.log
}
