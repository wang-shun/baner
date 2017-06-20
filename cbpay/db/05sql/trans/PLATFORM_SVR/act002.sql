
delete from services_adapter where SYSTEMID='PLATFORM_SVR' and TRANCODE='act002';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('PLATFORM_SVR', 'act002', 'queryfrgcurrrate|businessservice|false,dealorder|businessservice|true,chechinfoexist|businessservice|false,queryvalidcard|businessservice|false,registertranslog|baseservice|true', 'OUT');

commit;