
delete from conf_xml_formate where TRANCODE='FBS006' and SYSTEMID='PAB_SVR' and TYPE='0';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('FBS006', 'PAB_SVR', 'pab_svr/FBS006_0.xml', '0', 'OUT');

delete from conf_xml_formate where TRANCODE='FBS006' and SYSTEMID='PAB_SVR' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('FBS006', 'PAB_SVR', 'pab_svr/FBS006_1.xml', '1', 'OUT');

delete from services_adapter where SYSTEMID='PAB_SVR' and TRANCODE='FBS006';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('PAB_SVR', 'FBS006', 'querycustno|businessservice|false,packmsg|baseservice|false,sysspecailhander|baseservice|false,registermsg|baseservice|true,pab_svr|protocolservice|false,registermsg|baseservice|true,sysspecailhander|baseservice|false,unpackmsg|baseservice|false,rescodeconvert|baseservice|true,registertranslog|baseservice|true,updatepayexgstatusbuss|businessservice|false', 'OUT');

commit;