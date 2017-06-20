delete from trans_info where TRANCODE='FILE04' and TRANFROM='PAB_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM,TRAN_TYPE) values ('FILE04', '平安银行文件状态通知', 'PAB_CLI_FILE04', '', '60000', 'PAB_CLI', '1');

delete from conf_xml_formate where TRANCODE='FILE04' and SYSTEMID='PAB_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('FILE04', 'PAB_CLI', 'pab_cli/FILE04_1.xml', '1', 'IN');

delete from conf_xml_formate where TRANCODE='FILE04' and SYSTEMID='PAB_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('FILE04', 'PAB_CLI', 'pab_cli/FILE04_0.xml', '0', 'IN');

delete from services_adapter where SYSTEMID='PAB_CLI' and TRANCODE='FILE04';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('PAB_CLI', 'FILE04', 'registermsg|baseservice|true,sysspecailhander|baseservice|false,unpackmsg|baseservice|false,updatefilestatus|businessservice|false,routebyfiletype|businessservice|false,in.jms|protocolservice|false,packmsg|baseservice|true,sysspecailhander|baseservice|true,registermsg|baseservice|true', 'IN');

commit;