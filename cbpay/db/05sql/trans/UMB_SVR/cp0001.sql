
delete from conf_xml_formate where TRANCODE='cp0001' and SYSTEMID='UMB_SVR' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('cp0001', 'UMB_SVR', 'umb_svr/cp0001_0.xml', '0', 'OUT');

delete from conf_xml_formate where TRANCODE='cp0001' and SYSTEMID='UMB_SVR' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('cp0001', 'UMB_SVR', 'umb_svr/cp0001_1.xml', '1', 'OUT');

delete  from services_adapter t where SYSTEMID='UMB_SVR' and TRANCODE='cp0001';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('UMB_SVR', 'cp0001', 'sysspecailhander|baseservice|true,paymentbeforehand|businessservice|false,packmsg|baseservice|false,registermsg|baseservice|true,registerpayprogress|businessservice|false,umb_svr|protocolservice|false,registermsg|baseservice|true,unpackmsg|baseservice|false,rescodeconvert|baseservice|false,updatepayprogress|businessservice|false,registertranslog|baseservice|true', 'OUT');

commit;