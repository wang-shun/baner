create or replace procedure manualdayend
is
-----------------------------------------------------
--功能：平台手动日切，在自动日切的基础上去掉了日切是否完成的检查
--时间：2016年6月7日
--作者：zhangxiaoyun
--版本：v1.0
--
--
------------------------------------------------------
       var_currentDate varchar2(8);
       var_currenttime varchar2(6):=to_char(sysdate,'hh24miss');
       var_nextacdt varchar2(8);

begin
	------------------------------获取当天业务日期-----------------------------
	select acdt,nextacdt into var_currentDate,var_nextacdt from plat_date_param t ;

    insert into dayendlog (platdate, plattime,acdt, status) values (var_currentDate, var_currenttime,var_nextacdt, '0');
    commit;
	--开始切日
	switchplatdate();
	--开始切表分区
	switchdatapart();

  update dayendlog set status='1' where platdate=var_currentDate;
  commit;
  
  exception
    when OTHERS then
      update dayendlog set status='0' where platdate=var_currentDate;
      commit;
      errorlog_pro(var_currentDate,'manualdayend',SQLCODE,SQLERRM,dbms_utility.format_error_backtrace);
      RAISE;
end manualdayend;
/
