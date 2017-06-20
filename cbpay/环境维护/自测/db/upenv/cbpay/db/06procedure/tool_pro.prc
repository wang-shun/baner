create or replace procedure tool_pro(PLATDATE in varchar2,TABLENAME in varchar2,DATAPARTNEW in varchar2,DATAPARTOLD in varchar2,STATUS in varchar2,opr_type in varchar2)
  is
--------------------------------
--日期：2016年6月4日
--作者：zhangxiaoyun
--功能：登记表分区日志数据库
--------------------------------
  var_inser_sql varchar2(2000);
  var_update_sql varchar2(2000);
begin
  if opr_type='insert' then
    --开始插入日志
    var_inser_sql:='insert into SWITCHDATAPARTLOG (platdate, tablename, datapartnew, datapartold, status) values ('''||PLATDATE||''', '''||TABLENAME||''', '''||DATAPARTNEW||''', '''||DATAPARTOLD||''', '''||STATUS||''')';
    execute immediate var_inser_sql;
  elsif opr_type='update' then
    var_update_sql:='update SWITCHDATAPARTLOG set status = '''||STATUS||'''';
    if DATAPARTNEW is not null then
      var_update_sql:=var_update_sql||',datapartnew = '''||DATAPARTNEW||'''';
    end if;
    if DATAPARTOLD is not null then
      var_update_sql:=var_update_sql||',datapartold = '''||DATAPARTOLD||'''';
    end if;

    var_update_sql:=var_update_sql||' where platdate='''||PLATDATE||''' and tablename='''||TABLENAME||'''';
    execute immediate var_update_sql;
  end if;


  commit;--提交数据
end tool_pro;
/
