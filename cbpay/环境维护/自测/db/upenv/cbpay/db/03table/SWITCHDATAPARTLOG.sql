-- Create table
create table SWITCHDATAPARTLOG
(
  platdate    VARCHAR2(8) not null,
  tablename   VARCHAR2(50) not null,
  datapartnew VARCHAR2(50),
  datapartold VARCHAR2(50),
  status      VARCHAR2(200)
)
tablespace USERS
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
comment on column SWITCHDATAPARTLOG.platdate
  is '平台日期';
comment on column SWITCHDATAPARTLOG.tablename
  is '表名称';
comment on column SWITCHDATAPARTLOG.datapartnew
  is '新分区名称';
comment on column SWITCHDATAPARTLOG.datapartold
  is '老分区名称';
comment on column SWITCHDATAPARTLOG.status
  is '状态描述';
