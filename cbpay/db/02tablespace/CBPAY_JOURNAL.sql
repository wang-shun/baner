
--CBPAY_JOURNAL
--CBPAY_CONF
--CBPAY_REPORT

create tablespace CBPAY_JOURNAL datafile '/oradata/msds/cbpay/cbpay_journal01.dbf' size 500m  AUTOEXTEND off; 

create tablespace CBPAY_CONF datafile '/oradata/msds/cbpay/cbpay_conf01.dbf' size 500m  AUTOEXTEND off;

create tablespace CBPAY_INDEX datafile '/oradata/msds/cbpay/cbpay_index01.dbf' size 100m  AUTOEXTEND off;

CREATE TABLESPACE CBPAY_CONSOLE datafile '/oradata/msds/cbpay/cbpay_console.dbf' size 500m  AUTOEXTEND off;