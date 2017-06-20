
delete from services_adapter where SYSTEMID='PLATFORM_SVR' and TRANCODE='act006';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('PLATFORM_SVR', 'act006', 'checkmerchantinfo|businessservice|false,checkmerchantpermission|businessservice|false,findfrgrate|businessservice|false,queryorderpack|businessservice|false,registertranslog|baseservice|true', 'OUT');

commit;