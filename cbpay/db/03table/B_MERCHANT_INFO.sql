-- Create table
create table B_MERCHANT_INFO
(
  merchantid                  VARCHAR2(16) not null,
  merchantname                VARCHAR2(50),
  merchantdesc                VARCHAR2(200),
  isencrypt                   VARCHAR2(2),
  encrypt_algorithm           VARCHAR2(20),
  issignature                 VARCHAR2(2),
  signature_algorithm         VARCHAR2(20),
  public_key_file             VARCHAR2(100),
  key_file_type               VARCHAR2(2),
  encrypt_type                VARCHAR2(2),
  signature_type              VARCHAR2(2),
  key_store_file              VARCHAR2(100),
  key_store_alias             VARCHAR2(20),
  key_store_password          VARCHAR2(20),
  country_code                CHAR(3),
  currency_type               CHAR(3),
  merplatacctalias            VARCHAR2(30),
  protocolno                  VARCHAR2(30),
  valid                       CHAR(1),
  poundageflag                CHAR(1),
  poundagerate                NUMBER(12,6),
  contract_no                 VARCHAR2(20),
  merchant_principal          VARCHAR2(20),
  principal_tel               VARCHAR2(20),
  payee_acct_no               VARCHAR2(34),
  address                     VARCHAR2(120),
  open_branch_id              VARCHAR2(14),
  bank_card_csttype           CHAR(2),
  bank_card_type              CHAR(2),
  local_currency_account_no   VARCHAR2(34),
  foreign_currency_account_no VARCHAR2(34),
  supplier_list               VARCHAR2(200)
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
comment on column B_MERCHANT_INFO.merchantid
  is '商户id';
comment on column B_MERCHANT_INFO.merchantname
  is '商户名称';
comment on column B_MERCHANT_INFO.merchantdesc
  is '商户描述';
comment on column B_MERCHANT_INFO.country_code
  is '商户所在国家';
comment on column B_MERCHANT_INFO.currency_type
  is '商户开通的币种';
comment on column B_MERCHANT_INFO.merplatacctalias
  is '平台开立的账户账号别名，商户可开立多个账户';
comment on column B_MERCHANT_INFO.protocolno
  is '商户开通协议验证后必输';
comment on column B_MERCHANT_INFO.valid
  is '是否可用，0 ，可用；1 失效';
comment on column B_MERCHANT_INFO.poundageflag
  is '手续费缴纳方， 0商户，1用户  2 商户后付';
comment on column B_MERCHANT_INFO.poundagerate
  is '手续费率';
comment on column B_MERCHANT_INFO.contract_no
  is '合同号';
comment on column B_MERCHANT_INFO.merchant_principal
  is '商户负责人';
comment on column B_MERCHANT_INFO.principal_tel
  is '商户负责人联系电话';
comment on column B_MERCHANT_INFO.payee_acct_no
  is '收款方账户';
comment on column B_MERCHANT_INFO.address
  is '商户所在地址';
comment on column B_MERCHANT_INFO.open_branch_id
  is '收款人开户行BIC CODE';
comment on column B_MERCHANT_INFO.bank_card_csttype
  is '银行客户类型，00：对私；
01：对公；
';
comment on column B_MERCHANT_INFO.bank_card_type
  is '银行账户类型，01  纯借记卡 02 贷记卡 03 存折 99 企业账户';
comment on column B_MERCHANT_INFO.local_currency_account_no
  is '商户在宝易互通开具的本币虚拟账户';
comment on column B_MERCHANT_INFO.foreign_currency_account_no
  is '商户在宝易互通开具的外币虚拟账户';
comment on column B_MERCHANT_INFO.supplier_list
  is '供货商列表';
-- Create/Recreate primary, unique and foreign key constraints 
alter table B_MERCHANT_INFO
  add constraint B_MERCHANT_INFO_PK primary key (MERCHANTID)
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
