delete from conf_xml_formate where TRANCODE='cp0023' and SYSTEMID='UMB_SVR' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('cp0023', 'UMB_SVR', 'umb_svr/cp0023_0.xml', '0', 'OUT');

delete from conf_xml_formate where TRANCODE='cp0023' and SYSTEMID='UMB_SVR' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('cp0023', 'UMB_SVR', 'umb_svr/cp0023_1.xml', '1', 'OUT');

delete from services_adapter t where SYSTEMID='UMB_SVR' and TRANCODE='cp0023';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('UMB_SVR', 'cp0023', 'sysspecailhander|baseservice|true,payflowcheckbeforhand|businessservice|false,packmsg|baseservice|false,registermsg|baseservice|true,umb_svr|protocolservice|false,registermsg|baseservice|true,unpackmsg|baseservice|false,rescodeconvert|baseservice|false,payflowcheck|businessservice|false,registertranslog|baseservice|true', 'OUT');

commit;