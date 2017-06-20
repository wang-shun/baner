-- Create table
create table T_SYS_RSP_CODE
(
  id           VARCHAR2(32) not null,
  prod_id      VARCHAR2(32) not null,
  out_rsp_code VARCHAR2(20) not null,
  in_rsp_code2 VARCHAR2(20) not null,
  rsp_desc     VARCHAR2(100)
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
alter table T_SYS_RSP_CODE
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
