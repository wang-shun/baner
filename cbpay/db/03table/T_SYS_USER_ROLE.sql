-- Create table
create table T_SYS_USER_ROLE
(
  user_role_id VARCHAR2(38) not null,
  role_id      VARCHAR2(64),
  user_id      VARCHAR2(38),
  status       NUMBER
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
alter table T_SYS_USER_ROLE
  add primary key (USER_ROLE_ID)
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
