-- Create table
create table SERVERRESCODE
(
  platcode      VARCHAR2(20),
  serverid      VARCHAR2(15),
  serverrescode VARCHAR2(20),
  serverresdes  VARCHAR2(200)
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
