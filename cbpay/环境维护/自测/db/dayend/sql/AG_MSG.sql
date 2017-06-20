spool /home/oracle/day_End/logs/AG_MSG.spool
delete from AG_MSG WHERE TRANDATE<='20151020';
commit;
spool off
