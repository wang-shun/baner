#1.在备份日志目录中建立相应日期目录
#2.读取配置文件中需要备份的主机，用户，密码，目录
#3.依次用ftp命令将文件拉到相应日期目录下并安主机和用户打成相应的tar
#4.完成3后将整个日期目录打成gzip

#1
function tomkdir {
datevalue=`getDate`
num=`ls -l $BACKUP_DIR|grep ${datevalue}|wc|awk '{print $1}'`
mkdir -p $BACKUP_DIR/${datevalue}_${num}
export tmpbasedir=$BACKUP_DIR/${datevalue}_${num}
}


function ftpgetlogs {
sftp $2@$1<<!
set rez [connect $3] 
binary
hash
cd $4
lcd $5
prompt
mget *.log.*
bye
!
}

function mktar {
while read line
do
	appip=`echo $line|awk '{print $1}'`
	appname=`echo $line|awk '{print $2}'`
	apppasd=`echo $line|awk '{print $3}'`
	applogfile=`echo $line|awk '{print $4}'`
	ftpgetlogs ${appip} ${appname} ${apppasd} ${applogfile} ${tmpbasedir}
	ls -l|grep .log.|grep -v grep|tar -cf ${appip}_${appname}.tar
done <$CFG_DIR/log_address.cfg
}

tomkdir
mktar
