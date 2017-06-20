
delete from services_adapter where SYSTEMID='PLATFORM_SVR' and TRANCODE='act003';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('PLATFORM_SVR', 'act003', 'checkmerchantpermission|businessservice|false,queryorderstatus|businessservice|false,queryorderpack|businessservice|false,registertranslog|baseservice|true', 'OUT');

commit;