delete from trans_info t where TRANCODE='chs005' and TRANFROM='CASHIER_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE, ROUTE_TYPE) values ('chs005', '验证短信下发', 'CASHIER_CLI_chs005', null, '60000', 'CASHIER_CLI', '1', null);

delete from conf_opration where OPR_CODE='CASHIER_CLI_chs005' and TRANCODE='chs005';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('CASHIER_CLI_chs005', 'UMB_SVR', 'cp0032', null, '60000', 'chs005');

delete from conf_xml_formate where TRANCODE='chs005' and SYSTEMID='CASHIER_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('chs005', 'CASHIER_CLI', 'cashier_cli/chs005_0.xml', '0', 'IN');

delete from conf_xml_formate where TRANCODE='chs005' and SYSTEMID='CASHIER_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('chs005', 'CASHIER_CLI', 'cashier_cli/chs005_1.xml', '1', 'IN');

delete  from services_adapter t where SYSTEMID='CASHIER_CLI' and TRANCODE='chs005';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('CASHIER_CLI', 'chs005', 'registermsg|baseservice|true,unpackmsg|baseservice|false,in.jms|protocolservice|false,rescodeconvert|baseservice|true,packmsg|baseservice|true,registermsg|baseservice|true', 'IN');

commit;