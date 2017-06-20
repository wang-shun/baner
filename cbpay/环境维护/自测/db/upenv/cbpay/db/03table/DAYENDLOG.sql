-- Create table
create table DAYENDLOG
(
  platdate CHAR(8) not null,
  plattime CHAR(6) not null,
  acdt     CHAR(8),
  status   CHAR(1) not null
  ) tablespace CBPAY_JOURNAL
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

comment on column DAYENDLOG.status is '0:初始状态 1:成功 2:失败';
comment on column DAYENDLOG.acdt is '业务日期';