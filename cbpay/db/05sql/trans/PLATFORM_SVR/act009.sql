
delete from services_adapter where SYSTEMID='PLATFORM_SVR' and TRANCODE='act009';
insert into services_adapter (SYSTEMID, TRANCODE, PROCESSLIST, OWNER) values ('PLATFORM_SVR', 'act009', 'registercheckstatusbuss|businessservice|false,pabbuyexgcheckbuss|businessservice|false,updatecheckstatusbuss|businessservice|false,registertranslog|baseservice|true', 'OUT');

commit;