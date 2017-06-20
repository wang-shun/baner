一、日志定时备份安装文档
1.将整个tobaklog目录拖入cbpay用户下任意文件夹。
2.查看在文件tobaklog/cfg/logbak.cfg中，是否全部配置需要备份的目录及文件格式。
3.给目录tobaklog及其子文件赋755权限。
4.在环境定时器crontab里将tobaklog/baklog.sh配置好，参数为tobaklog所在的绝对目录。
crontab例如：
0 0 * * * /ztkx/cbpay/logs/tobaklog/baklog.sh /ztkx/cbpay/logs/tobaklog