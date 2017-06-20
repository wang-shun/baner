-- Create table
create table T_SYS_USER
(
  user_id          VARCHAR2(38) not null,
  branch_id        VARCHAR2(38),
  login_name       VARCHAR2(32) not null,
  user_name        VARCHAR2(50) not null,
  mobile           VARCHAR2(20),
  user_workaddress VARCHAR2(100),
  status           VARCHAR2(2),
  password         VARCHAR2(32),
  update_time      DATE,
  create_time      DATE,
  created_by       VARCHAR2(32),
  modified_by      VARCHAR2(32),
  email            VARCHAR2(32),
  data_environ     NUMBER,
  icon             VARCHAR2(100)
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
alter table T_SYS_USER
  add primary key (USER_ID)
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
