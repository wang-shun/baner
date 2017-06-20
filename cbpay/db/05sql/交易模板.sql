
delete from trans_info where TRANCODE='chs007' and TRANFROM='CASHIER_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM) values ('chs007', 'µ•± ÷ß∏∂', 'CASHIER_CLI_chs007', '', '60000', 'CASHIER_CLI');

delete from conf_opration where OPR_CODE='PLATFORM_CLI_ctb003' and TRANCODE='ctb003';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('PLATFORM_CLI_ctb003', 'UMB_SVR', 'cp0034', null, '60000', 'ctb003');

delete from conf_xml_formate where TRANCODE='chs006' and SYSTEMID='CASHIER_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('chs006', 'CASHIER_CLI', 'cashier_cli/chs006_1.xml', '1', 'IN');

delete from services_adapter where SYSTEMID='MERCHANT_CLI' and TRANCODE='cbp001';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('MERCHANT_CLI', 'cbp001', 'registermsg|baseservice|true,unpackmsg|baseservice|false,router|baseservice|false,in.jms|protocolservice|false,packmsg|baseservice|true,registermsg|baseservice|true', 'IN');