-- Create table
create table PLAT_PARAMS
(
  paramname  VARCHAR2(50) not null,
  paramvalue VARCHAR2(200) not null,
  useflag    VARCHAR2(2) not null,
  paramdesc  VARCHAR2(200)
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
-- Add comments to the columns 
comment on column PLAT_PARAMS.paramname
  is '参数名';
comment on column PLAT_PARAMS.paramvalue
  is '参数值';
comment on column PLAT_PARAMS.useflag
  is '启用状态  1 启用   0 停用';
comment on column PLAT_PARAMS.paramdesc
  is '参数描述';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PLAT_PARAMS
  add constraint PLAT_PARAMS_PK primary key (PARAMNAME, USEFLAG)
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
