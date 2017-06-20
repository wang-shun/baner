-- Create table
create table T_SYS_ROLE_MUTEX
(
  role_mutex_id VARCHAR2(38) not null,
  role_id_a     VARCHAR2(38),
  role_id_b     VARCHAR2(38),
  status        NUMBER
)
tablespace CBPAY_CONSOLE
  pctfree 10
  initrans 1
  maxtrans 255;
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_SYS_ROLE_MUTEX
  add primary key (ROLE_MUTEX_ID)
  using index 
  tablespace CBPAY_CONSOLE
  pctfree 10
  initrans 2
  maxtrans 255;
