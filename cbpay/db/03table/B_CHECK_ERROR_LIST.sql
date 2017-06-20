-- Create table
create table B_CHECK_ERROR_LIST
(
  accountdate   VARCHAR2(8) not null,
  chkchnl       VARCHAR2(10) not null,
  orderid       VARCHAR2(64) not null,
  chnljnlno     VARCHAR2(32) not null,
  chkerrtyp     CHAR(1) not null,
  chkerrdealtyp VARCHAR2(2),
  chkerrdealsts CHAR(1),
  errcancelmark VARCHAR2(60),
  dealdate      VARCHAR2(8),
  stamp         TIMESTAMP(6),
  trandate      VARCHAR2(8) not null
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
-- Add comments to the table 
comment on table B_CHECK_ERROR_LIST
  is '对账差错表';
-- Add comments to the columns 
comment on column B_CHECK_ERROR_LIST.accountdate
  is '业务日期';
comment on column B_CHECK_ERROR_LIST.chkchnl
  is '对账渠道';
comment on column B_CHECK_ERROR_LIST.orderid
  is '订单id';
comment on column B_CHECK_ERROR_LIST.chnljnlno
  is '合作渠道流水号';
comment on column B_CHECK_ERROR_LIST.chkerrtyp
  is '差错类型  0：状态不符
1：平台多账
2：合作渠道多账';
comment on column B_CHECK_ERROR_LIST.chkerrdealtyp
  is '处理方式 字段预留';
comment on column B_CHECK_ERROR_LIST.chkerrdealsts
  is '处理状态 0：未处理
1：已处理
2：差错取消';
comment on column B_CHECK_ERROR_LIST.errcancelmark
  is '取消差错原因';
comment on column B_CHECK_ERROR_LIST.dealdate
  is '处理日期';
comment on column B_CHECK_ERROR_LIST.trandate
  is '对账日期';
-- Create/Recreate primary, unique and foreign key constraints 
alter table B_CHECK_ERROR_LIST
  add constraint B_CHECK_ERROR_LIST_PK primary key (ACCOUNTDATE, CHKCHNL, ORDERID, CHNLJNLNO, CHKERRTYP)
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
