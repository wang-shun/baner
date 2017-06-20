delete from trans_info where TRANCODE='col001' and TRANFROM='CONSOLE_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE, ROUTE_TYPE) values ('col001', 'ÅúÁ¿¸¶»ã', 'CONSOLE_CLI_col001', null, '60000', 'CONSOLE_CLI', '1', null);

delete from conf_opration where OPR_CODE='CONSOLE_CLI_col001' and TRANCODE='col001';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('CONSOLE_CLI_col001', 'PAB_SVR', 'FBS005', null, '60000', 'col001');

delete from conf_xml_formate where TRANCODE='col001' and SYSTEMID='CONSOLE_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('col001', 'CONSOLE_CLI', 'console_cli/col001_1.xml', '1', 'IN');

delete from conf_xml_formate where TRANCODE='col001' and SYSTEMID='CONSOLE_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('col001', 'CONSOLE_CLI', 'console_cli/col001_0.xml', '0', 'IN');

delete from services_adapter where SYSTEMID='CONSOLE_CLI' and TRANCODE='col001';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('CONSOLE_CLI', 'col001', 'registermsg|baseservice|true,unpackmsg|baseservice|false,in.jms|protocolservice|false,rescodeconvert|baseservice|true,packmsg|baseservice|true,registermsg|baseservice|true', 'IN');

commit;