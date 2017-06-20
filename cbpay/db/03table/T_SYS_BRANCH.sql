-- Create table
create table T_SYS_BRANCH
(
  id             VARCHAR2(32) not null,
  branch_id      VARCHAR2(38) not null,
  branch_desc    VARCHAR2(200),
  branch_name    VARCHAR2(100),
  branch_manager VARCHAR2(50),
  mobile         VARCHAR2(20),
  begin_time     DATE,
  end_time       DATE,
  parent_id      VARCHAR2(38) not null,
  leaf_flag      NUMBER not null,
  branch_seq     NUMBER not null,
  level1         NUMBER not null
)
tablespace CBPAY_CONSOLE
  pctfree 10
  initrans 1
  maxtrans 255;
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_SYS_BRANCH
  add primary key (ID)
  using index 
  tablespace CBPAY_CONSOLE
  pctfree 10
  initrans 2
  maxtrans 255;