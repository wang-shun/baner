-- Create table
create table T_SYS_BUSINESS
(
  business_id              VARCHAR2(38) not null,
  business_name            VARCHAR2(100),
  business_desc            VARCHAR2(100),
  business_manager         VARCHAR2(100),
  business_mobile          VARCHAR2(100),
  business_seq             NUMBER,
  begin_time               DATE,
  end_time                 DATE,
  status                   NUMBER,
  business_data_permission NUMBER
)
tablespace CBPAY_CONSOLE
  pctfree 10
  initrans 1
  maxtrans 255;
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_SYS_BUSINESS
  add primary key (BUSINESS_ID)
  using index 
  tablespace CBPAY_CONSOLE
  pctfree 10
  initrans 2
  maxtrans 255;
