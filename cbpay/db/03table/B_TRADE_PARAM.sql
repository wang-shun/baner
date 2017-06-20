-- Create table
create table B_TRADE_PARAM
(
  type VARCHAR2(50),
  key  VARCHAR2(10),
  des  VARCHAR2(200)
)
tablespace CBPAY_JOURNAL
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
