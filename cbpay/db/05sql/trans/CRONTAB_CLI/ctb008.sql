delete from trans_info where TRANCODE='ctb008' and TRANFROM='CRONTAB_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE, ROUTE_TYPE) values ('ctb008', '交易流水对账', 'CRONTAB_CLI_ctb008', null, '60000', 'CRONTAB_CLI', '1', null);

delete from conf_opration where OPR_CODE='CRONTAB_CLI_ctb008' and TRANCODE='ctb008';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('CRONTAB_CLI_ctb008', 'UMB_SVR', 'cp0023', null, '60000', 'ctb008');

delete from conf_xml_formate where TRANCODE='ctb008' and SYSTEMID='CRONTAB_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('ctb008', 'CRONTAB_CLI', 'crontab_cli/ctb008_1.xml', '1', 'IN');

delete from conf_xml_formate where TRANCODE='ctb008' and SYSTEMID='CRONTAB_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('ctb008', 'CRONTAB_CLI', 'crontab_cli/ctb008_0.xml', '0', 'IN');

delete from services_adapter where SYSTEMID='CRONTAB_CLI' and TRANCODE='ctb008';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('CRONTAB_CLI', 'ctb008', 'registermsg|baseservice|true,unpackmsg|baseservice|false,in.jms|protocolservice|false,rescodeconvert|baseservice|true,packmsg|baseservice|true,registermsg|baseservice|true', 'IN');

commit;