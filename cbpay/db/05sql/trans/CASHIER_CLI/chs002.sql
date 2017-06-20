delete from trans_info where TRANCODE='chs002' and TRANFROM='CASHIER_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE) values ('chs002', '收银台对订单的验证', 'CASHIER_CLI_chs002', '', '60000', 'CASHIER_CLI','1');

delete from conf_opration where OPR_CODE='CASHIER_CLI_chs002' and TRANCODE='chs002';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('CASHIER_CLI_chs002', 'PLATFORM_SVR', 'act001', null, '60000', 'chs002');

delete from conf_xml_formate where TRANCODE='chs002' and SYSTEMID='CASHIER_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('chs002', 'CASHIER_CLI', 'cashier_cli/chs002_1.xml', '1', 'IN');

delete from conf_xml_formate where TRANCODE='chs002' and SYSTEMID='CASHIER_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('chs002', 'CASHIER_CLI', 'cashier_cli/chs002_0.xml', '0', 'IN');

delete from services_adapter where SYSTEMID='CASHIER_CLI' and TRANCODE='chs002';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('CASHIER_CLI', 'chs002', 'registermsg|baseservice|true,unpackmsg|baseservice|false,in.jms|protocolservice|false,rescodeconvert|baseservice|true,packmsg|baseservice|true,registermsg|baseservice|true', 'IN');

commit;