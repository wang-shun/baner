
delete from services_adapter where SYSTEMID='PLATFORM_SVR' and TRANCODE='act001';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('PLATFORM_SVR', 'act001', 'checktradecode|businessservice|false,checkorder|businessservice|false,checkmerchantinfo|businessservice|false,checkmerchantpermission|businessservice|false,registertranslog|baseservice|true', 'OUT');

commit;