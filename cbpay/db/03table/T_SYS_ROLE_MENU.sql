-- Create table
create table T_SYS_ROLE_MENU
(
  id      VARCHAR2(38) not null,
  menu_id NUMBER,
  role_id VARCHAR2(38),
  status  NUMBER
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
alter table T_SYS_ROLE_MENU
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
