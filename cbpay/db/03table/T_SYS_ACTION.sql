-- Create table
create table T_SYS_ACTION
(
  action_id   NUMBER not null,
  action_flag VARCHAR2(30) not null,
  action_name VARCHAR2(100) not null,
  action_desc VARCHAR2(200),
  action_menu NUMBER not null,
  field1      VARCHAR2(100)
)
tablespace CBPAY_CONSOLE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_SYS_ACTION
  add primary key (ACTION_ID)
  using index 
  tablespace CBPAY_CONSOLE
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
