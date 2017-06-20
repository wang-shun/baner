delete from trans_info where TRANCODE='chs003' and TRANFROM='CASHIER_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE,ROUTE_TYPE) values ('chs003', '收银台需要订单的信息', 'CASHIER_CLI_chs003', '', '60000', 'CASHIER_CLI','1','MER');

delete from conf_opration where OPR_CODE='CASHIER_CLI_chs003' and TRANCODE='chs003';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('CASHIER_CLI_chs003', 'PLATFORM_SVR', 'act002', null, '60000', 'chs003');

delete from conf_xml_formate where TRANCODE='chs003' and SYSTEMID='CASHIER_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('chs003', 'CASHIER_CLI', 'cashier_cli/chs003_1.xml', '1', 'IN');

delete from conf_xml_formate where TRANCODE='chs003' and SYSTEMID='CASHIER_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('chs003', 'CASHIER_CLI', 'cashier_cli/chs003_0.xml', '0', 'IN');

delete from services_adapter where SYSTEMID='CASHIER_CLI' and TRANCODE='chs003';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('CASHIER_CLI', 'chs003', 'registermsg|baseservice|true,unpackmsg|baseservice|false,in.jms|protocolservice|false,rescodeconvert|baseservice|true,registermsg|baseservice|true,packmsg|baseservice|true', 'IN');

commit;