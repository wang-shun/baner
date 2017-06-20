delete from conf_xml_formate where TRANCODE='cp0039' and SYSTEMID='UMB_SVR' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('cp0039', 'UMB_SVR', 'umb_svr/cp0039_0.xml', '0', 'OUT');

delete from conf_xml_formate where TRANCODE='cp0039' and SYSTEMID='UMB_SVR' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('cp0039', 'UMB_SVR', 'umb_svr/cp0039_1.xml', '1', 'OUT');

delete from services_adapter t where SYSTEMID='UMB_SVR' and TRANCODE='cp0039';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('UMB_SVR', 'cp0039', 'sysspecailhander|baseservice|false,accounttransferbeforehand|businessservice|false,packmsg|baseservice|false,registermsg|baseservice|true,registertransferaccountprogress|businessservice|false,umb_svr|protocolservice|false,registermsg|baseservice|true,unpackmsg|baseservice|false,rescodeconvert|baseservice|false,updatetransferaccountprogress|businessservice|false,registertranslog|baseservice|true', 'OUT');

commit;