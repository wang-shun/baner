delete from conf_xml_formate where TRANCODE='cp0040' and SYSTEMID='UMB_SVR' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('cp0040', 'UMB_SVR', 'umb_svr/cp0040_0.xml', '0', 'OUT');

delete from conf_xml_formate where TRANCODE='cp0040' and SYSTEMID='UMB_SVR' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('cp0040', 'UMB_SVR', 'umb_svr/cp0040_1.xml', '1', 'OUT');

delete from services_adapter t where SYSTEMID='UMB_SVR' and TRANCODE='cp0040';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('UMB_SVR', 'cp0040', 'sysspecailhander|baseservice|true,packmsg|baseservice|true,registermsg|baseservice|true,umb_svr|protocolservice|false,registermsg|baseservice|true,unpackmsg|baseservice|false,rescodeconvert|baseservice|false,querytransferaccountprogress|businessservice|false,registertranslog|baseservice|true', 'OUT');

commit;