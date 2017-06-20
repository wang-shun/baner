-- Create table
create table T_SYS_OP_LOGS
(
  id       VARCHAR2(64) not null,
  skeys    VARCHAR2(128) not null,
  contents VARCHAR2(1024) not null,
  level1   VARCHAR2(4) default 'INFO',
  crt_time DATE
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
alter table T_SYS_OP_LOGS
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
