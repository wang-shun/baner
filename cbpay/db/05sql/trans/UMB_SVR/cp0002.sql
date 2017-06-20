
delete from conf_xml_formate where TRANCODE='cp0002' and SYSTEMID='UMB_SVR' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('cp0002', 'UMB_SVR', 'umb_svr/cp0002_0.xml', '0', 'OUT');

delete from conf_xml_formate where TRANCODE='cp0002' and SYSTEMID='UMB_SVR' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('cp0002', 'UMB_SVR', 'umb_svr/cp0002_1.xml', '1', 'OUT');

delete  from services_adapter t where SYSTEMID='UMB_SVR' and TRANCODE='cp0002';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('UMB_SVR', 'cp0002', 'sysspecailhander|baseservice|false,packmsg|baseservice|true,registermsg|baseservice|true,umb_svr|protocolservice|false,registermsg|baseservice|false,unpackmsg|baseservice|false,rescodeconvert|baseservice|false,querypayprogress|businessservice|true,registertranslog|baseservice|true', 'OUT');

commit;