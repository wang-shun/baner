delete from trans_info where TRANCODE='cbp004' and TRANFROM='MERCHANT_CLI';
insert into trans_info (TRANCODE, TRANDESC, TRAN_OPR, CHANNEL_DIY, OVERTIME, TRANFROM, TRAN_TYPE) values ('cbp004', 'ª„¬ ≤È—Ø', 'MERCHANT_CLI_cbp004', '', '60000', 'MERCHANT_CLI','1');

delete from conf_opration where OPR_CODE='MERCHANT_CLI_cbp004' and TRANCODE='cbp004';
insert into conf_opration (OPR_CODE, SERVERID, SERVERCODE, SERVER_DIY, SERVER_OVERTIME, TRANCODE) values ('MERCHANT_CLI_cbp004', 'PLATFORM_SVR', 'act006', null, '60000', 'cbp004');

delete from conf_xml_formate where TRANCODE='cbp004' and SYSTEMID='MERCHANT_CLI' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('cbp004', 'MERCHANT_CLI', 'merchant_cli/cbp004_1.xml', '1', 'IN');

delete from conf_xml_formate where TRANCODE='cbp004' and SYSTEMID='MERCHANT_CLI' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('cbp004', 'MERCHANT_CLI', 'merchant_cli/cbp004_0.xml', '0', 'IN');

delete from services_adapter where SYSTEMID='MERCHANT_CLI' and TRANCODE='cbp004';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('MERCHANT_CLI', 'cbp004', 'registermsg|baseservice|true,unpackmsg|baseservice|false,in.jms|protocolservice|false,rescodeconvert|baseservice|true,packmsg|baseservice|true,decipher|baseservice|true,registermsg|baseservice|true', 'IN');

commit;