delete from trans_info t where TRANCODE='chs006' and TRANFROM='CASHIER_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE, ROUTE_TYPE) values ('chs006', ' ’µ•÷ß∏∂', 'CASHIER_CLI_chs006', null, '60000', 'CASHIER_CLI', '1', null);

delete from conf_opration where OPR_CODE='CASHIER_CLI_chs006' and TRANCODE='chs006';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('CASHIER_CLI_chs006', 'UMB_SVR', 'cp0001', null, '60000', 'chs006');

delete from conf_xml_formate where TRANCODE='chs006' and SYSTEMID='CASHIER_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('chs006', 'CASHIER_CLI', 'cashier_cli/chs006_0.xml', '0', 'IN');

delete from conf_xml_formate where TRANCODE='chs006' and SYSTEMID='CASHIER_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('chs006', 'CASHIER_CLI', 'cashier_cli/chs006_1.xml', '1', 'IN');

delete  from services_adapter t where SYSTEMID='CASHIER_CLI' and TRANCODE='chs006';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('CASHIER_CLI', 'chs006', 'registermsg|baseservice|true,unpackmsg|baseservice|false,in.jms|protocolservice|false,rescodeconvert|baseservice|true,packmsg|baseservice|true,registermsg|baseservice|true', 'IN');

commit;