-- Create table
create table B_VERIFICATION_CODE
(
  verjnlno  VARCHAR2(32) not null,
  mblno     VARCHAR2(11) not null,
  verchnl   VARCHAR2(5),
  verbiztyp VARCHAR2(2) not null,
  vercode   VARCHAR2(8),
  prdtime   VARCHAR2(6),
  status    VARCHAR2(1) not null,
  tmsmp     VARCHAR2(14),
  prddate   VARCHAR2(8)
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
comment on table B_VERIFICATION_CODE
  is '验证码信息表';
-- Add comments to the columns 
comment on column B_VERIFICATION_CODE.verjnlno
  is '验证业务流水号';
comment on column B_VERIFICATION_CODE.mblno
  is '手机号';
comment on column B_VERIFICATION_CODE.verchnl
  is '短信验证渠道';
comment on column B_VERIFICATION_CODE.verbiztyp
  is '验证业务类型 ，1短信 ，2语音 ，缺失状态下表示短信';
comment on column B_VERIFICATION_CODE.vercode
  is '验证码';
comment on column B_VERIFICATION_CODE.prdtime
  is '验证码生成时间';
comment on column B_VERIFICATION_CODE.status
  is '生效标志 0 有效 ， 1 失效 ；';
comment on column B_VERIFICATION_CODE.tmsmp
  is '时间戳';
comment on column B_VERIFICATION_CODE.prddate
  is '验证码生成日期';
-- Create/Recreate primary, unique and foreign key constraints 
alter table B_VERIFICATION_CODE
  add primary key (VERJNLNO)
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
