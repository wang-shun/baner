
delete from tran_from_info where FROM_TYPE='4' and FROM_ID='CASHIER_CLI';
insert into tran_from_info (FROM_TYPE, FROM_ID, FROM_DESC, ISENCRYPT, ENCRYPT_ALGORITHM, ISSIGNATURE, SIGNATURE_ALGORITHM, PUBLIC_KEY_FILE, KEY_FILE_TYPE, ENCRYPT_TYPE, SIGNATURE_TYPE, CHANEL_CODE, CHANEL_DES, KEY_STORE_FILE, KEY_STORE_ALIAS, KEY_STORE_PASSWORD) values ('4', 'CASHIER_CLI', '收银台', '0', '', '0', '', '', '', '', '', 'rescode', 'resdes', '', '', '');

delete from server_info where SERVERID='UMB_SVR';
insert into server_info (SERVERID, SERVER_KEY, SERVERDESC, IP, PROT, ISENCRYPT, ENCRYPT_ALGORITHM, PUBLIC_KEY_FILE, ISSIGNATURE, SIGNATURE_ALGORITHM, REMITTANCE_FEE, RES_CODE, KEY_STORE_FILE, KEY_STORE_ALIAS, KEY_STORE_PASSWORD) values ('UMB_SVR', 'SMS', '宝易互通服务平台', '', '', '0', '', '', '0', '', '', 'respcode', '', '', '');

delete from protocol where PROTOCOLID='platform_cli';
insert into protocol (PROTOCOLID, SERVERID, XMLCONF, OWNER, PROTOCOLTYPE) values ('platform_cli', 'PLATFORM_CLI', '<protocol><common id="platform_cli" type="http" flag="client"  inOut="DataIn/DataOut" mode="syn"  encoding="GBK" sessionCount="5" connecMode="short"/><request host="127.0.0.1" port="8809"  policy="dynamic_len:start:0,end:5"  dataParams="MERCHANTDATA"  file="*.do" encryption="" connectTimeout="60000" method="POST"/><response policy="stream_over_flag" readTimeout="60000" /></protocol>', 'DISCENTER', 'httpserver');

commit;