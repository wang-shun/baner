--创建历史流水表

drop table H_B_MERCHANT_ORDER purge;
create table H_B_MERCHANT_ORDER as select * from B_MERCHANT_ORDER;

drop table H_B_BUY_EXG_CTRL purge;
create table H_B_BUY_EXG_CTRL as select * from B_BUY_EXG_CTRL;

drop table H_B_BUY_EXG_DET purge;
create table H_B_BUY_EXG_DET as select * from b_buy_exg_det;

drop table H_B_FOREIGN_CURRENCY_RATE purge;
create table H_B_FOREIGN_CURRENCY_RATE as select * from B_FOREIGN_CURRENCY_RATE;

drop table H_B_PAYMENT_FLOW purge;
create table H_B_PAYMENT_FLOW as select * from B_PAYMENT_FLOW;

drop table H_B_ACCOUNT_TRANSFER_FLOW purge;
create table H_B_ACCOUNT_TRANSFER_FLOW as select * from B_ACCOUNT_TRANSFER_FLOW;

drop table H_B_SELL_EXG_CTRL purge;
create table H_B_SELL_EXG_CTRL as select * from B_SELL_EXG_CTRL;

drop table H_B_SELL_EXG_DET purge;
create table H_B_SELL_EXG_DET as select * from B_SELL_EXG_DET;

drop table H_B_TRANSFER_FILE_INFO purge;
create table H_B_TRANSFER_FILE_INFO as select * from B_TRANSFER_FILE_INFO;