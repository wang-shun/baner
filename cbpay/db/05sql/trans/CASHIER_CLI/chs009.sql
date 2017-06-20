delete from trans_info where TRANCODE='chs009' and TRANFROM='CASHIER_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE) values ('chs009', '收银台加密', 'CASHIER_CLI_chs009', '', '60000', 'CASHIER_CLI','1');

delete from conf_xml_formate where TRANCODE='chs009' and SYSTEMID='CASHIER_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('chs009', 'CASHIER_CLI', 'cashier_cli/chs009_0.xml', '0', 'IN');

delete from services_adapter where SYSTEMID='CASHIER_CLI' and TRANCODE='chs009';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('CASHIER_CLI', 'chs009', 'registermsg|baseservice|true,unpackmsg|baseservice|false,encript|businessservice|false,registermsg|baseservice|true', 'IN');

commit;