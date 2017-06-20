delete from trans_info where TRANCODE='F002' and TRANFROM='PAB_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE, ROUTE_TYPE) values ('F002', '平安银行下行文件通知', 'PAB_CLI_F002', null, '60000', 'PAB_CLI', '1', null);

delete from conf_opration where OPR_CODE='PAB_CLI_F002' and TRANCODE='F002';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('PAB_CLI_F002', 'PAB_SVR', 'FILE03', null, '60000', 'F002');

delete from conf_xml_formate where TRANCODE='F002' and SYSTEMID='PAB_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('F002', 'PAB_CLI', 'pab_cli/F002_1.xml', '1', 'IN');

delete from conf_xml_formate where TRANCODE='F002' and SYSTEMID='PAB_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('F002', 'PAB_CLI', 'pab_cli/F002_0.xml', '0', 'IN');

delete from services_adapter where SYSTEMID='PAB_CLI' and TRANCODE='F002';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('PAB_CLI', 'F002', 'registermsg|baseservice|true,sysspecailhander|baseservice|false,unpackmsg|baseservice|false,in.jms|protocolservice|false,packmsg|baseservice|true,sysspecailhander|baseservice|false,registermsg|baseservice|true', 'IN');

commit;