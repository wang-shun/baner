delete from trans_info where TRANCODE='col007' and TRANFROM='CONSOLE_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE, ROUTE_TYPE) values ('col007', '平安银行购汇状态查询', 'CONSOLE_CLI_col007', null, '60000', 'CONSOLE_CLI', '1', null);

delete from conf_opration where OPR_CODE='CONSOLE_CLI_col007' and TRANCODE='col007';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('CONSOLE_CLI_col007', 'PAB_SVR', 'FBS004', null, '60000', 'col007');

delete from conf_xml_formate where TRANCODE='col007' and SYSTEMID='CONSOLE_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('col007', 'CONSOLE_CLI', 'console_cli/col007_1.xml', '1', 'IN');

delete from conf_xml_formate where TRANCODE='col007' and SYSTEMID='CONSOLE_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('col007', 'CONSOLE_CLI', 'console_cli/col007_0.xml', '0', 'IN');

delete from services_adapter where SYSTEMID='CONSOLE_CLI' and TRANCODE='col007';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('CONSOLE_CLI', 'col007', 'registermsg|baseservice|true,unpackmsg|baseservice|false,in.jms|protocolservice|false,rescodeconvert|baseservice|true,packmsg|baseservice|true,registermsg|baseservice|true', 'IN');

commit;