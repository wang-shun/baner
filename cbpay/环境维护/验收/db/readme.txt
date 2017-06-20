--------------------------dayend---------------------------------------
一、自动日切脚本配置
1.首先保证用户是否带有sqlplus的命令。
2.将dayend目录全部拖入用户目录下cbpay目录中。
3.在文件dayend/tns/tnsnames.ora查看是否已经配置好要连数据库的tns
4.在文件dayend/setEnv.sh中，配置连接数据库的tns、用户名、密码。
5.在文件dayend/cfg/baseparam.properties中，根据命令队列配置mqurl和mqqueue。
6.文件dayend/cfg/containid.properties中，根据命令队列中，各个需要日切的容器的containid来配置。
7.赋值dayend目录及其子文件755权限
8.配置系统的crontab
例如：0 0 * * * . $HOME/.bash_profile;$HOME/cbpay/dayend/start.sh $HOME/cbpay/dayend
------------------------------------------------------------------------
二、手动日切脚本
1.完成自动日切步骤1到步骤7
2.直接执行dayend/shell/handdayend.sh
-------------------------------------------------------------------------