delete from trans_info t where TRANCODE='chs008' and TRANFROM='CASHIER_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE, ROUTE_TYPE) values ('chs008', '下单支付实名认证', 'CASHIER_CLI_chs008', null, '60000', 'CASHIER_CLI', '1', null);

delete from conf_opration where OPR_CODE='CASHIER_CLI_chs008' and TRANCODE='chs008';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('CASHIER_CLI_chs008', 'UMB_SVR', 'cp0030', null, '60000', 'chs008');

delete from conf_xml_formate where TRANCODE='chs008' and SYSTEMID='CASHIER_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('chs008', 'CASHIER_CLI', 'cashier_cli/chs008_1.xml', '1', 'IN');

delete from conf_xml_formate where TRANCODE='chs008' and SYSTEMID='CASHIER_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('chs008', 'CASHIER_CLI', 'cashier_cli/chs008_0.xml', '0', 'IN');

delete  from services_adapter t where SYSTEMID='CASHIER_CLI' and TRANCODE='chs008';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('CASHIER_CLI', 'chs008', 'registermsg|baseservice|true,unpackmsg|baseservice|false,in.jms|protocolservice|false,rescodeconvert|baseservice|true,packmsg|baseservice|true,registermsg|baseservice|true', 'IN');

commit;