export ORACLE_HOME=/ztkx/cbpay/upenv/oracle
export NLS_LANG="SIMPLIFIED CHINESE_CHINA.ZHS16GBK"
export LD_LIBRARY_PATH=${ORACLE_HOME}/lib
PATH=$ORACLE_HOME/bin:$LD_LIBRARY_PATH:$PATH:
export PATH
sqlplus -S cbpay/cbpay@msds33 @total.sql
exit
