--20160310  新增平安银服务端协议
delete from protocol where PROTOCOLID='pab_svr';
insert into protocol (PROTOCOLID, SERVERID, XMLCONF, OWNER, PROTOCOLTYPE) values ('pab_svr', 'PAB_SVR', '<protocol><common id="pab_svr" type="tcp" flag="client"  inOut="DataIn/DataOut" mode="syn"  encoding="GBK" sessionCount="5" connecMode="short"/><request host="127.0.0.1" port="7072" policy="stream_over_flag"    dataParams=""  file="" encryption="" connectTimeout="60000" method=""/><response policy="dynamic_len:start:30,end:39" headLen="222"  readTimeout="60000" /></protocol>', 'OUT', 'tcpclient');

commit;

--20160322  新增定时任务协议
delete from protocol where PROTOCOLID='crontab_cli';
insert into protocol (PROTOCOLID, SERVERID, XMLCONF, OWNER, PROTOCOLTYPE) values ('crontab_cli', 'CRONTAB_CLI', '<protocol><common id="crontab_cli" type="tcp" flag="server"  inOut="DataIn/DataOut" mode="syn"  encoding="GBK" sessionCount="5" connecMode="short"/><request host="127.0.0.1" port="8811"  policy="dynamic_len:start:0,end:5"  dataParams=""  file="" encryption="" connectTimeout="60000" method=""/><response policy="dynamic_len:start:0,end:5" dataParams="" readTimeout="60000" /></protocol>', 'DISCENTER', 'tcpserver');

commit;

--20160324  新增控制台协议
delete from protocol where PROTOCOLID='console_cli';
insert into protocol (PROTOCOLID, SERVERID, XMLCONF, OWNER, PROTOCOLTYPE) values ('console_cli', 'CONSOLE_CLI', '<protocol><common id="console_cli" type="tcp" flag="server"  inOut="DataIn/DataOut" mode="syn"  encoding="GBK" sessionCount="5" connecMode="short"/><request host="127.0.0.1" port="8812"  policy="dynamic_len:start:0,end:5"  dataParams=""  file="" encryption="" connectTimeout="60000" method=""/><response policy="dynamic_len:start:0,end:5" dataParams="" readTimeout="60000" /></protocol>', 'DISCENTER', 'tcpserver');

commit;

--20160324  新增平安银行客户端协议
delete from protocol where PROTOCOLID='pab_cli';
insert into protocol (PROTOCOLID, SERVERID, XMLCONF, OWNER, PROTOCOLTYPE) values ('pab_cli', 'PAB_CLI', '<protocol><common id="pab_cli" type="tcp" flag="server"  inOut="DataIn/DataOut" mode="syn"  encoding="GBK" sessionCount="5" connecMode="short"/><request host="127.0.0.1" port="8813" policy="dynamic_len:start:30,end:39" headLen="222"  dataParams=""  file="" encryption="" connectTimeout="60000" method=""/><response policy="stream_over_flag"   dataParams="" readTimeout="60000" /></protocol>', 'DISCENTER', 'tcpserver');

commit;

--20160606 新增收银台客户端接入协议
delete from protocol where PROTOCOLID='cashier_cli';
insert into protocol (PROTOCOLID, SERVERID, XMLCONF, OWNER, PROTOCOLTYPE) values ('cashier_cli', 'CASHIER_CLI', '<protocol><common id="cashier_cli" type="tcp" flag="client"  inOut="DataIn/DataOut" mode="syn"  encoding="UTF-8" sessionCount="5" connecMode="short"/><request host="127.0.0.1" port="60001"  policy="dynamic_len:start:0,end:5"  dataParams=""  file="" encryption="" connectTimeout="60000" method="POST"/><response policy="dynamic_len:start:0,end:5" readTimeout="60000" /></protocol>', 'DISCENTER', 'tcpserver');

--20160606 新增收银台购汇交易客户端接入协议
delete from protocol where PROTOCOLID='cashier_cli2';
insert into protocol (PROTOCOLID, SERVERID, XMLCONF, OWNER, PROTOCOLTYPE) values ('cashier_cli2', 'CASHIER_CLI', '<protocol><common id="cashier_cli2" type="jms" flag="server"  inOut="DataIn" mode="asyn"  encoding="UTF-8" sessionCount="5" connecMode="long" factory="org.apache.activemq.jndi.ActiveMQInitialContextFactory" url="tcp://127.0.0.1:61616" queueName="dynamicQueues/cashier_discenter.queue" overTime="0" isTransaction="false" autoAcknowledge="true" messageListener=""/><request  /><response /></protocol>', 'DISCENTER', 'jmsserver');

commit;

--20160606 新增out-in 的协议
delete from protocol where PROTOCOLID='out.jms';
insert into protocol (PROTOCOLID, SERVERID, XMLCONF, OWNER, PROTOCOLTYPE) values ('out.jms', 'PLATFORM', '<protocol><common id="out_in.jms" type="jms" inOut="DataOut" mode="asyn"  encoding="" sessionCount="" connecMode="long"/></protocol>', 'OUT', 'jmsclient');

--20160606 新增商户接入协议
delete from protocol where PROTOCOLID='merchant_cli';
insert into protocol (PROTOCOLID, SERVERID, XMLCONF, OWNER, PROTOCOLTYPE) values ('merchant_cli', 'MERCHANT_CLI', '<protocol><common id="merchant_cli" type="http" flag="server"  inOut="DataIn/DataOut" mode="syn"  encoding="UTF-8" sessionCount="5" connecMode="short"/><request host="127.0.0.1" port="8099"  policy="dataparam"  dataParams="MERCHANTDATA"  file="*.action" encryption="" connectTimeout="60000" method="POST"/><response policy="dynamic_len:str:0,end:5" dataParams="" readTimeout="60000" /></protocol>', 'DISCENTER', 'httpserver');

--20160606 新增in-out 的协议
delete from protocol where PROTOCOLID='in.jms';
insert into protocol (PROTOCOLID, SERVERID, XMLCONF, OWNER, PROTOCOLTYPE) values ('in.jms', 'PLATFORM', '<protocol><common id="in_out.jms" type="jms" inOut="DataOut" mode="asyn"  encoding="" sessionCount="" connecMode="long"/></protocol>', 'IN', 'jmsclient');


--20160606 新增宝易互通接出协议
delete from protocol where PROTOCOLID='umb_svr';
insert into protocol (PROTOCOLID, SERVERID, XMLCONF, OWNER, PROTOCOLTYPE) values ('umb_svr', 'UMB_SVR', '<protocol><common id="umb_svr" type="http" flag="client" inOut="DataIn/DataOut" mode="syn"  encoding="UTF-8" sessionCount="5" connecMode="short"/><request host="172.30.12.25" port="8080"  policy="dataparam" dataParams="xml"  file="/cbpayAgent/msgProcess/acceptXmlReq.do" encryption="" connectTimeout="60000" method="POST"/><response policy="dataparam"  dataParams="xml" readTimeout="60000" /></protocol>', 'OUT', 'httpclient');
commit;