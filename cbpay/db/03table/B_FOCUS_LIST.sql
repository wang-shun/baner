-- Create table
create table B_FOCUS_LIST
(
  chnname    VARCHAR2(60),
  engname    VARCHAR2(60),
  idtype     VARCHAR2(2) not null,
  idno       VARCHAR2(32) not null,
  createdate VARCHAR2(8)
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
comment on column B_FOCUS_LIST.chnname
  is '汉语名字';
comment on column B_FOCUS_LIST.engname
  is '英文名字';
comment on column B_FOCUS_LIST.idtype
  is '证件类型';
comment on column B_FOCUS_LIST.idno
  is '证件号';
comment on column B_FOCUS_LIST.createdate
  is '日期';
-- Create/Recreate primary, unique and foreign key constraints 
alter table B_FOCUS_LIST
  add constraint B_FOCUS_LIST_PK primary key (IDTYPE, IDNO)
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
