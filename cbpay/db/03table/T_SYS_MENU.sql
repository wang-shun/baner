-- Create table
create table T_SYS_MENU
(
  menu_id   NUMBER not null,
  menu_name VARCHAR2(100),
  menu_desc VARCHAR2(200),
  menu_seq  NUMBER,
  menu_url  VARCHAR2(200),
  parent_id NUMBER,
  leaf_flag NUMBER,
  level1    NUMBER,
  icon      VARCHAR2(50)
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
alter table T_SYS_MENU
  add primary key (MENU_ID)
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
