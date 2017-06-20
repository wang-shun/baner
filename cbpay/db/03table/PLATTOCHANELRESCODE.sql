-- Create table
create table PLATTOCHANELRESCODE
(
  platcode   VARCHAR2(20) not null,
  tran_from  VARCHAR2(20) not null,
  chanelcode VARCHAR2(10),
  chaneldes  VARCHAR2(200)
)
tablespace CBPAY_CONF
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
alter table PLATTOCHANELRESCODE
  add constraint PRI_CHANEL primary key (PLATCODE, TRAN_FROM)
  using index 
  tablespace CBPAY_INDEX
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
