-- Create table
create table B_CHECK_STATUS
(
  trandate    VARCHAR2(8) not null,
  accountdate VARCHAR2(8) not null,
  checktype   CHAR(1) not null,
  status      CHAR(1) not null,
  trantime    VARCHAR2(6) not null,
  filename    VARCHAR2(30),
  tmsmp       TIMESTAMP(6) not null,
  jnlno       VARCHAR2(40),
  checknl     CHAR(1),
  ishandled   CHAR(1)
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
-- Add comments to the columns 
comment on column B_CHECK_STATUS.trandate
  is '交易日期';
comment on column B_CHECK_STATUS.accountdate
  is '账务日期';
comment on column B_CHECK_STATUS.checktype
  is '对账类型 0：支付流水对账   1：资金划转对账 2：购汇流水对账 3：结汇流水对账';
comment on column B_CHECK_STATUS.status
  is '对账结果 0：对账开始 1：对账成功 2：对账失败';
comment on column B_CHECK_STATUS.trantime
  is '交易时间';
comment on column B_CHECK_STATUS.filename
  is '对账文件名';
comment on column B_CHECK_STATUS.tmsmp
  is '时间戳';
comment on column B_CHECK_STATUS.jnlno
  is '平台流水号';
comment on column B_CHECK_STATUS.checknl
  is '对账渠道 0 ：宝易互通 1:平安银行';
comment on column B_CHECK_STATUS.ishandled
  is '是否处理，0，未处理；1 已处理。';
-- Create/Recreate primary, unique and foreign key constraints 
alter table B_CHECK_STATUS
  add constraint B_CHECK_STATUS_PK primary key (TRANDATE, CHECKTYPE,checknl)
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
