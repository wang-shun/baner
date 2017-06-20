delete from trans_info where TRANCODE='chs007' and TRANFROM='CASHIER_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE) values ('chs007', 'µ¥±Ê¹º»ãµÇ¼Ç', 'CASHIER_CLI_chs007', '', '60000', 'CASHIER_CLI','2');

delete from conf_opration where OPR_CODE='CASHIER_CLI_chs007' and TRANCODE='chs007';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('CASHIER_CLI_chs007', 'PAB_SVR', 'FBS003', null, '60000', 'chs007');

delete from conf_xml_formate where TRANCODE='chs007' and SYSTEMID='CASHIER_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('chs007', 'CASHIER_CLI', 'cashier_cli/chs007_1.xml', '1', 'IN');

delete from conf_xml_formate where TRANCODE='chs007' and SYSTEMID='CASHIER_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('chs007', 'CASHIER_CLI', 'cashier_cli/chs007_0.xml', '0', 'IN');

delete from services_adapter where SYSTEMID='CASHIER_CLI' and TRANCODE='chs007';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('CASHIER_CLI', 'chs007', 'registermsg|baseservice|true,unpackmsg|baseservice|false,in.jms|protocolservice|false,rescodeconvert|baseservice|true,packmsg|baseservice|true,registermsg|baseservice|true', 'IN');

commit;