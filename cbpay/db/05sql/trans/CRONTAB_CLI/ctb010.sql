delete from trans_info where TRANCODE='ctb010' and TRANFROM='CRONTAB_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE, ROUTE_TYPE) values ('ctb010', 'ÅúÁ¿¹º»ã', 'CRONTAB_CLI_ctb010', null, '60000', 'CRONTAB_CLI', '1', null);

delete from conf_opration where OPR_CODE='CRONTAB_CLI_ctb010' and TRANCODE='ctb010';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('CRONTAB_CLI_ctb010', 'PAB_SVR', 'FBS003', null, '60000', 'ctb010');

delete from conf_xml_formate where TRANCODE='ctb010' and SYSTEMID='CRONTAB_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('ctb010', 'CRONTAB_CLI', 'crontab_cli/ctb010_1.xml', '1', 'IN');

delete from conf_xml_formate where TRANCODE='ctb010' and SYSTEMID='CRONTAB_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('ctb010', 'CRONTAB_CLI', 'crontab_cli/ctb010_0.xml', '0', 'IN');

delete from services_adapter where SYSTEMID='CRONTAB_CLI' and TRANCODE='ctb010';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('CRONTAB_CLI', 'ctb010', 'registermsg|baseservice|true,unpackmsg|baseservice|false,querybuyexgserver|businessservice|false,in.jms|protocolservice|false,rescodeconvert|baseservice|true,packmsg|baseservice|true,registermsg|baseservice|true', 'IN');

commit;