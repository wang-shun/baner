delete from trans_info where TRANCODE='col008' and TRANFROM='CONSOLE_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE, ROUTE_TYPE) values ('col008', '平安银行付汇状态查询', 'CONSOLE_CLI_col008', null, '60000', 'CONSOLE_CLI', '1', null);

delete from conf_opration where OPR_CODE='CONSOLE_CLI_col008' and TRANCODE='col008';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('CONSOLE_CLI_col008', 'PAB_SVR', 'FBS006', null, '60000', 'col008');

delete from conf_xml_formate where TRANCODE='col008' and SYSTEMID='CONSOLE_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('col008', 'CONSOLE_CLI', 'console_cli/col008_1.xml', '1', 'IN');

delete from conf_xml_formate where TRANCODE='col008' and SYSTEMID='CONSOLE_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('col008', 'CONSOLE_CLI', 'console_cli/col008_0.xml', '0', 'IN');

delete from services_adapter where SYSTEMID='CONSOLE_CLI' and TRANCODE='col008';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('CONSOLE_CLI', 'col008', 'registermsg|baseservice|true,unpackmsg|baseservice|false,in.jms|protocolservice|false,rescodeconvert|baseservice|true,packmsg|baseservice|true,registermsg|baseservice|true', 'IN');

commit;