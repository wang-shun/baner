-- Create table
create table B_USER_INFO
(
  merchantid  VARCHAR2(20) not null,
  purchaserid VARCHAR2(64) not null,
  nickname    VARCHAR2(32),
  usrsts      VARCHAR2(1),
  realnmflg   VARCHAR2(1),
  realname    VARCHAR2(60),
  idtyp       VARCHAR2(2),
  idno        VARCHAR2(32),
  regdate     VARCHAR2(8),
  regtime     VARCHAR2(6),
  regchnl     VARCHAR2(10),
  email       VARCHAR2(32),
  tmsmp       VARCHAR2(14),
  telnum      VARCHAR2(16)
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
comment on table B_USER_INFO
  is '用户信息表';
-- Add comments to the columns 
comment on column B_USER_INFO.merchantid
  is '商户号';
comment on column B_USER_INFO.purchaserid
  is '购买者标识';
comment on column B_USER_INFO.nickname
  is '用户昵称';
comment on column B_USER_INFO.usrsts
  is '用户状态 0，不可用，1表示可用。';
comment on column B_USER_INFO.realnmflg
  is '实名标志0，是初始状态，1表示验证通过。2表示验证失败';
comment on column B_USER_INFO.realname
  is '姓名';
comment on column B_USER_INFO.idtyp
  is '证件类型';
comment on column B_USER_INFO.idno
  is '证件号码';
comment on column B_USER_INFO.regdate
  is '注册日期';
comment on column B_USER_INFO.regtime
  is '注册时间';
comment on column B_USER_INFO.regchnl
  is '注册渠道';
comment on column B_USER_INFO.email
  is '注册邮箱';
comment on column B_USER_INFO.tmsmp
  is '时间戳';
comment on column B_USER_INFO.telnum
  is '手机号';
-- Create/Recreate primary, unique and foreign key constraints 
alter table B_USER_INFO
  add constraint B_USER_INFO_PK primary key (PURCHASERID, MERCHANTID)
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
