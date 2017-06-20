#****************************************************
#*时间:2015-10-21 10:25:07
#*功能:为日切初始化环境变量
#*作者:zhangxiaoyun
#*版本:0.0.1
#****************************************************

#执行系统环境变量
#. $HOME/.bash_profile
export TNS_ADMIN=/home/oracle/cbpay/dayend/tns
export ORACLE_SID=msds33

USERNAME=cbpay
export USERNAME

PASSWD=cbpay
export PASSWD

#日切系统安装home目录
HOME_DIR=$1
export HOME_DIR

#备份目录
BACKUP_DIR=$HOME_DIR/backup
export BACKUP_DIR

#日志目录
LOG_DIR=$HOME_DIR/logs
export LOG_DIR

#shell程序目录
SHELL_DIR=$HOME_DIR/shell
export SHELL_DIR

#执行sql目录
SQL_DIR=$HOME_DIR/sql
export SQL_DIR


#配置文件目录
CFG_DIR=$HOME_DIR/cfg
export CFG_DIR

#临时目录
TMP_DIR=$HOME_DIR/tmp
export TMP_DIR

#数据保留区间(单位:天)
DATE_INTERVAL=90
export DATE_INTERVAL

#备份数据保留日期
BACKUP_DATA_INTERVAL=90
export BACKUP_DATA_INTERVAL



