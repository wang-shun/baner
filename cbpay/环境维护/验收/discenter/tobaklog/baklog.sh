if [ "" = "$1" ]
then 
	echo "请将家目录作为参数输入"
fi
if [ ! -e $1 ]
then 
	echo "输入参数的文件家目录不存在"
fi
cd $1
if [ ! -e ./logs ]
then
        mkdir -p ./logs
fi
function getDate(){
        echo `date +%Y%m%d`
}


function getTime(){
        echo `date +%H:%M:%S`
}
function log(){
        echo -e "=======================`getDate`  `getTime` : $* \n\t">>./logs/tobaklog.log
}
log "备份日志开始"
if [ ! -e ./cfg/logbak.cfg ]
then 
	log "没有配置文件，请首先做好./cfg/logbak.cfg的配置"
	exit
fi
if [ ! -e ./baklogs ]
then
        mkdir -p ./baklogs
fi
datevalue=`date +%Y%m%d`
function baklog {
	if [ $# -ne 2 ]
	then
		log "配置文件格式不对"
		continue
	fi
	reg=`echo ${2//./\\\.}`
	count=`ls -l $1|grep "$reg"|grep -v grep|wc|awk '{print $1}'`
	log "$1需要备份文件数量$count"
	if [ $count -eq 0 ]
        then
                continue
        fi
	num=`ls -l ./baklogs|grep ${datevalue}|wc|awk '{print $1}'`
	mv $1/*$reg* ./baklogs
	cd ./baklogs
	tar -cvf - *$reg*|gzip>./${datevalue}_${num}.tar.gzip
	rm *$reg*
	cd ..
	log "备份$1下日志到${datevalue}_${num}.tar.gzip中"
}
while read line
do 
	logdir=`echo $line|awk '{print $1}'`
	logcfg=`echo $line|awk '{print $2}'`
	log "备份的文件夹为：$logdir,备份的日志规则为：$logcfg"
	baklog $logdir $logcfg
done<./cfg/logbak.cfg
