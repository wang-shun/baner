-- Create table
create table T_APP_CONTAIN
(
  appid       VARCHAR2(20),
  containtype VARCHAR2(10),
  ip          VARCHAR2(20),
  valid       CHAR(1)
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
-- Add comments to the columns 
comment on column T_APP_CONTAIN.appid
  is '容器id';
comment on column T_APP_CONTAIN.containtype
  is '容器类型';
comment on column T_APP_CONTAIN.ip
  is '容器所在IP';
comment on column T_APP_CONTAIN.valid
  is '有效表示（1:有效，0：无效）';
