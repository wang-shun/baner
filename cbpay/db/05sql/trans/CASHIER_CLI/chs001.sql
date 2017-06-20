delete from trans_info where TRANCODE='chs001' and TRANFROM='CASHIER_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE) values ('chs001', '收银台方向的解密', 'CASHIER_CLI_chs001', '', '60000', 'CASHIER_CLI','1');

delete from conf_xml_formate where TRANCODE='chs001' and SYSTEMID='CASHIER_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('chs001', 'CASHIER_CLI', 'cashier_cli/chs001_0.xml', '0', 'IN');

delete from services_adapter where SYSTEMID='CASHIER_CLI' and TRANCODE='chs001';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('CASHIER_CLI', 'chs001', 'registermsg|baseservice|true,unpackmsg|baseservice|false,decript|businessservice|false,registermsg|baseservice|true', 'IN');

commit;