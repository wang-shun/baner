
delete from conf_xml_formate where TRANCODE='FILE03' and SYSTEMID='PAB_SVR' and TYPE='1';
insert into conf_xml_formate (TRANCODE, SYSTEMID, PATH, TYPE, OWNER) values ('FILE03', 'PAB_SVR', 'pab_svr/FILE03_1.xml', '1', 'OUT');


delete from services_adapter where SYSTEMID='PAB_SVR' and TRANCODE='FILE03';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('PAB_SVR', 'FILE03', 'registerfileinfo|businessservice|false,packmsg|baseservice|false,sysspecailhander|baseservice|false,registermsg|baseservice|true,pab_svr|protocolservice|false,registermsg|baseservice|true,sysspecailhander|baseservice|false,rescodeconvert|baseservice|true,registertranslog|baseservice|true', 'OUT');

commit;