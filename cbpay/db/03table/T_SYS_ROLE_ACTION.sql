-- Create table
create table T_SYS_ROLE_ACTION
(
  id        CHAR(38) not null,
  action_id NUMBER,
  role_id   CHAR(38),
  status    NUMBER not null
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
alter table T_SYS_ROLE_ACTION
  add primary key (ID)
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
