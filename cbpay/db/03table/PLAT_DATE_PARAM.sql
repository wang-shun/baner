-- Create table
create table PLAT_DATE_PARAM
(
  sysname   VARCHAR2(20) not null,
  acdt      VARCHAR2(8) not null,
  lastacdt  VARCHAR2(8) not null,
  nextacdt  VARCHAR2(8) not null,
  sysstatus VARCHAR2(2) not null
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
comment on column PLAT_DATE_PARAM.sysname
  is '系统名称';
comment on column PLAT_DATE_PARAM.acdt
  is '会计日期';
comment on column PLAT_DATE_PARAM.lastacdt
  is '上一会计日期';
comment on column PLAT_DATE_PARAM.nextacdt
  is '下一会计日期';
comment on column PLAT_DATE_PARAM.sysstatus
  is '00：启运 
10：停运
';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PLAT_DATE_PARAM
  add constraint PLAT_DATE_PARAM_PK primary key (SYSNAME)
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
