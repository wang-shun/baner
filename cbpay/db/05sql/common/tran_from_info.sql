--20160607 修改 收银台渠道
delete from tran_from_info where FROM_TYPE='4' and FROM_ID='CASHIER_CLI';
insert into tran_from_info (FROM_TYPE, FROM_ID, FROM_DESC, ISENCRYPT, ENCRYPT_ALGORITHM, ISSIGNATURE, SIGNATURE_ALGORITHM, PUBLIC_KEY_FILE, KEY_FILE_TYPE, ENCRYPT_TYPE, SIGNATURE_TYPE, CHANEL_CODE, CHANEL_DES, KEY_STORE_FILE, KEY_STORE_ALIAS, KEY_STORE_PASSWORD)values ('4', 'CASHIER_CLI', '收银台', '0', null, '0', null, '/ztkx/cbpay/CrossBorderPay/cers/cashier_cli/zlex.cer', null, null, null, 'respCode', 'respMsg', '/ztkx/cbpay/CrossBorderPay/cers/cashier_cli/zlex.keystore', 'www.zlex.org', '123456');


commit;

--20160321 新增 定时任务渠道
delete from tran_from_info where FROM_TYPE='4' and FROM_ID='CRONTAB_CLI';
insert into tran_from_info (FROM_TYPE, FROM_ID, FROM_DESC, ISENCRYPT, ENCRYPT_ALGORITHM, ISSIGNATURE, SIGNATURE_ALGORITHM, PUBLIC_KEY_FILE, KEY_FILE_TYPE, ENCRYPT_TYPE, SIGNATURE_TYPE, CHANEL_CODE, CHANEL_DES, KEY_STORE_FILE, KEY_STORE_ALIAS, KEY_STORE_PASSWORD) values ('4', 'CRONTAB_CLI', '定时任务', '0', '', '0', '', '', '', '', '', 'respcode', 'respmsg', '', '', '');

commit;

--20160324 新增 控制台渠道
delete from tran_from_info where FROM_TYPE='4' and FROM_ID='CONSOLE_CLI';
insert into tran_from_info (FROM_TYPE, FROM_ID, FROM_DESC, ISENCRYPT, ENCRYPT_ALGORITHM, ISSIGNATURE, SIGNATURE_ALGORITHM, PUBLIC_KEY_FILE, KEY_FILE_TYPE, ENCRYPT_TYPE, SIGNATURE_TYPE, CHANEL_CODE, CHANEL_DES, KEY_STORE_FILE, KEY_STORE_ALIAS, KEY_STORE_PASSWORD) values ('4', 'CONSOLE_CLI', '控制台', '0', '', '0', '', '', '', '', '', 'respcode', 'respmsg', '', '', '');
commit;

--20160504 新增 平安银行渠道
delete from tran_from_info where FROM_TYPE='2' and FROM_ID='PAB_CLI';
insert into tran_from_info (FROM_TYPE, FROM_ID, FROM_DESC, ISENCRYPT, ENCRYPT_ALGORITHM, ISSIGNATURE, SIGNATURE_ALGORITHM, PUBLIC_KEY_FILE, KEY_FILE_TYPE, ENCRYPT_TYPE, SIGNATURE_TYPE, CHANEL_CODE, CHANEL_DES, KEY_STORE_FILE, KEY_STORE_ALIAS, KEY_STORE_PASSWORD) values ('2', 'PAB_CLI', '平安银行', '0', '', '0', '', '', '', '', '', '', '', '', '', '');
commit;

--20160606 新增 商户客户渠道
delete from tran_from_info where FROM_TYPE='3' and FROM_ID='MERCHANT_CLI';
insert into tran_from_info (FROM_TYPE, FROM_ID, FROM_DESC, ISENCRYPT, ENCRYPT_ALGORITHM, ISSIGNATURE, SIGNATURE_ALGORITHM, PUBLIC_KEY_FILE, KEY_FILE_TYPE, ENCRYPT_TYPE, SIGNATURE_TYPE, CHANEL_CODE, CHANEL_DES, KEY_STORE_FILE, KEY_STORE_ALIAS, KEY_STORE_PASSWORD) values ('3', 'MERCHANT_CLI', '商户客户端', '1', null, '1', null, '/ztkx/cbpay/CrossBorderPay/cers/merchant/zlex.cer', null, null, null, 'respCode', 'respMsg', '/ztkx/cbpay/CrossBorderPay/cers/merchant/zlex.keystore', 'www.zlex.org', '123456');
commit;