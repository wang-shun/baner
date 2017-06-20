delete from trans_info where TRANCODE='col005' and TRANFROM='CONSOLE_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE, ROUTE_TYPE) values ('col005', '账户划转报文（console发起）', 'CONSOLE_CLI_col005', null, '60000', 'CONSOLE_CLI', '1', null);

delete from conf_opration where OPR_CODE='CONSOLE_CLI_col005' and TRANCODE='col005';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('CONSOLE_CLI_col005', 'UMB_SVR', 'cp0039', null, '60000', 'col005');

delete from conf_xml_formate where TRANCODE='col005' and SYSTEMID='CONSOLE_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('col005', 'CONSOLE_CLI', 'console_cli/col005_1.xml', '1', 'IN');

delete from conf_xml_formate where TRANCODE='col005' and SYSTEMID='CONSOLE_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('col005', 'CONSOLE_CLI', 'console_cli/col005_0.xml', '0', 'IN');

delete from services_adapter where SYSTEMID='CONSOLE_CLI' and TRANCODE='col005';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('CONSOLE_CLI', 'col005', 'registermsg|baseservice|true,unpackmsg|baseservice|false,in.jms|protocolservice|fasle,rescodeconvert|baseservice|true,packmsg|baseservice|true,registermsg|baseservice|true', 'IN');


commit;