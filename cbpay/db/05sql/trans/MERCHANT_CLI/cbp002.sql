delete from trans_info where TRANCODE='cbp002' and TRANFROM='MERCHANT_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE) values ('cbp002', '¶©µ¥×´Ì¬²éÑ¯', 'MERCHANT_CLI_cbp002', '', '60000', 'MERCHANT_CLI','1');

delete from conf_opration where OPR_CODE='MERCHANT_CLI_cbp002' and TRANCODE='cbp002';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('MERCHANT_CLI_cbp002', 'PLATFORM_SVR', 'act003', null, '60000', 'cbp002');

delete from conf_xml_formate where TRANCODE='cbp002' and SYSTEMID='MERCHANT_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('cbp002', 'MERCHANT_CLI', 'merchant_cli/cbp002_1.xml', '1', 'IN');

delete from conf_xml_formate where TRANCODE='cbp002' and SYSTEMID='MERCHANT_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('cbp002', 'MERCHANT_CLI', 'merchant_cli/cbp002_0.xml', '0', 'IN');

delete from services_adapter where SYSTEMID='MERCHANT_CLI' and TRANCODE='cbp002';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('MERCHANT_CLI', 'cbp002', 'registermsg|baseservice|true,unpackmsg|baseservice|false,in.jms|protocolservice|false,rescodeconvert|baseservice|true,packmsg|baseservice|true,decipher|baseservice|true,registermsg|baseservice|true', 'IN');

commit;