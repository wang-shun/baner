--服务响应码配置表

--宝易互通响应码
delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EAC1002007';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000541', 'UMB_SVR', 'EAC1002007', '付款方账户余额不足小于手续费金额，无法进行扣帐处理 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EAC1002006';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000541', 'UMB_SVR', 'EAC1002006', '付款方账户余额不足小于交易金额，无法进行扣帐处理 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EAC0000004';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000541', 'UMB_SVR', 'EAC0000004', '交易失败，账户余额不足小于交易总金额，无法进行扣帐处理 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EAC0000003';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000541', 'UMB_SVR', 'EAC0000003', '交易失败，账户可用余额小于0 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000049';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000542', 'UMB_SVR', 'ECP0000049', '证件号、姓名或手机号有误 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000019';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000542', 'UMB_SVR', 'ECP0000019', '不正确的密码 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000018';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000542', 'UMB_SVR', 'ECP0000018', '卡号与证件信息不符 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000013';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000542', 'UMB_SVR', 'ECP0000013', '银行卡号和姓名不符 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100006';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000542', 'UMB_SVR', 'E000100006', '银行卡和上送银行不符 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000024';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000543', 'UMB_SVR', 'ECP0000024', '卡冻结 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000016';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000543', 'UMB_SVR', 'ECP0000016', '挂失卡 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000008';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000543', 'UMB_SVR', 'ECP0000008', '发卡行不允许交易 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000007';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000543', 'UMB_SVR', 'ECP0000007', '银行卡被没收 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000006';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000543', 'UMB_SVR', 'ECP0000006', '卡受限制 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000005';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000543', 'UMB_SVR', 'ECP0000005', '无效卡号 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000004';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000543', 'UMB_SVR', 'ECP0000004', '卡过期 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECF1000016';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000543', 'UMB_SVR', 'ECF1000016', '该卡已被平台全局停用，请联系管理员！ ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECF1000005';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000543', 'UMB_SVR', 'ECF1000005', '银行卡认证失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000029';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EMG0000029', '渠道号格式不正确 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000028';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EMG0000028', '日期时间格式不正确 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000022';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EMG0000022', '交易流水号不合法 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000021';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EMG0000021', '交易流水号为空或者长度超长 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000020';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EMG0000020', '渠道号为空 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000018';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EMG0000018', '交易码不支持 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000010';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EMG0000010', '交易码不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000007';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EMG0000007', '摘要为空 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000006';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EMG0000006', '报文格式错误 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000005';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EMG0000005', '商户号为空 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000004';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EMG0000004', '交易码为空 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000003';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EMG0000003', '报文类型不正确 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EMG0000002', '报文版本不正确 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EMG0000001', '报文体为空 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0010004';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'ECP0010004', '清算日期不正确 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000043';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'ECP0000043', '批次明细笔数超过最大允许数量 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000042';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'ECP0000042', '批次明细为空 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000041';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'ECP0000041', '批次号为空或超过允许的最大长度 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000036';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'ECP0000036', '非存折户 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000035';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'ECP0000035', '非活期账号，或为旧账号 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'ECP0000002', '后端渠道通请求数据格式不正确 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001221';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBGP001221', '交易失败,用途不能为空 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001220';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBGP001220', '交易失败,格式不正确 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001219';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBGP001219', '交易失败,凭证号超过8位 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001218';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBGP001218', '交易失败,凭证号为空 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001215';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBGP001215', '交易失败,用途太长 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001214';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBGP001214', '交易失败,收款账户类型不能为空 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001213';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBGP001213', '交易失败,收款人姓名长度太长 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001212';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBGP001212', '交易失败,收款人账号长度太长 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001210';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBGP001210', '交易失败,收款人开户行信息未知 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001209';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBGP001209', '交易失败,收款人姓名不能为空 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001102';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBGP001102', '交易失败,收款人账号不能为空 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001005';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBGP001005', '字段长度超过最大值 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBGP001002', '不支持外币业务 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000024';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBG1000024', '通道不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000018';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBG1000018', '货币类型不支持 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000017';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBG1000017', '证件类型不支持 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000007';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBG1000007', '交易金额为空 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000006';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBG1000006', '银行卡类型不支持 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000005';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBG1000005', '银行卡客户类型不支持 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000004';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBG1000004', '交易订单号为空 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000003';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBG1000003', '交易类型不支持 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBG1000002', '请求的业务渠道编码不支持 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'EBG1000001', '请求参数为空 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100023';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E000100023', '开始时间不能超过结束时间 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100022';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E000100022', '查询时间区间超过最大天数 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100021';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E000100021', '查询交易状态不合法 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100020';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E000100020', '查询交易代码不合法 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100019';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E000100019', '查询时间必须为当天的时间 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100018';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E000100018', '查询时间不能为当天的时间 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100017';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E000100017', '交易时间格式不正确 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100012';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E000100012', '金额有误，必须大于0的数字，必须是两位小数，长度不能大于16');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100008';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E000100008', '业务类型不支持 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100007';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E000100007', '银行卡必须为借记卡 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100005';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E000100005', '找不到卡bin信息 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100004';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E000100004', '证件类型不合法 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100003';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E000100003', '币种类型不合法 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E000100002', '账户类型不合法 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E000100001', '身份证号不合法 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E999999998';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E999999998', '报文格式错误 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E999999997';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000544', 'UMB_SVR', 'E999999997', '报文字段非法 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='W000000000';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000545', 'UMB_SVR', 'W000000000', '处理中 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001106';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000545', 'UMB_SVR', 'EBGP001106', '批次处理中 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0010003';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'ECP0010003', '没有开通代付业务 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0010002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'ECP0010002', '没有开通代收业务 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000050';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'ECP0000050', '该银行卡不支持代收 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000040';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'ECP0000040', '超过银行账户月累计笔数 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000037';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'ECP0000037', '交易金额小于该储种的最低支取金额 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000030';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'ECP0000030', '当日存入的金额当日不能支取 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000028';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'ECP0000028', '未登折行数超限 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000021';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'ECP0000021', '交易受法律限制 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000020';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'ECP0000020', '超出取款/消费次数限制 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000012';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'ECP0000012', '超过商户日累计限额 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000011';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'ECP0000011', '超过银行账户日累计限额 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000010';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'ECP0000010', '超过单笔限额 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000009';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'ECP0000009', '不支持银行卡交易 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001217';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'EBGP001217', '汇路不支持 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001207';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'EBGP001207', '交易失败,转账金额超出限额 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001206';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'EBGP001206', '转账不支持 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000033';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'EBG1000033', '对公交易，不可为非营业日 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000031';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'EBG1000031', '未开通银行支付服务 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000025';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'EBG1000025', '超过允许的最大交易金额 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100015';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'UMB_SVR', 'E000100015', '查询请求间隔时间未超过{} ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='W000000004';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000547', 'UMB_SVR', 'W000000004', '子批次号重复 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='W000000003';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000547', 'UMB_SVR', 'W000000003', '批次号重复 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='W000000002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000547', 'UMB_SVR', 'W000000002', '记录重复操作 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EPM0000002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000547', 'UMB_SVR', 'EPM0000002', '重复发送流水 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000023';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000547', 'UMB_SVR', 'EMG0000023', '商户流水号重复 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E999999995';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000547', 'UMB_SVR', 'E999999995', '重复流水号码 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EPY1000002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'EPY1000002', '提现银行机构不存在，请配置数据 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EPY1000001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'EPY1000001', '提现银行卡信息不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EPY0008004';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'EPY0008004', '原订单用户状态异常，请联系管理员 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EPY0008003';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'EPY0008003', '原订单客户状态异常，请联系管理员 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EPY0008002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'EPY0008002', '原订单用户不存在，请联系管理员 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EPY0008001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'EPY0008001', '原订单客户不存在，请联系管理员 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EPY0004003';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'EPY0004003', '数据异常记录重复 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EPY0004002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'EPY0004002', '订单已被其他线程处理，请确认账户，如有疑问请联系客服 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EPY0004001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'EPY0004001', '记录重复，请返回商城端重新发起 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0010005';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'ECP0010005', '没有设置商户虚拟帐号 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0010001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'ECP0010001', '暂不支持商户指定账户 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000051';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'ECP0000051', '后端渠道当前暂不支持交易 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000048';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'ECP0000048', '落地代付申请审核拒绝 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000046';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'ECP0000046', '流水状态不正确 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000031';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'ECP0000031', '交易已被冲正 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000026';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'ECP0000026', '原交易已被撤销或冲正 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000014';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'ECP0000014', '超过允许的PIN试输入 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECF1000013';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'ECF1000013', '该卡已经绑定，请不要重复绑定 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001105';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'EBGP001105', '转账状态未知，且不支持查询 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001104';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'EBGP001104', '转账状态未知 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000029';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'EBG1000029', '通道被禁用 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG0000005';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'EBG0000005', '请先在页面请求验证码 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG0000002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'EBG0000002', '短信验证码已超时失效，请重新发送 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E999999996';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'E999999996', '未定义的错误码 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E999999994';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000548', 'UMB_SVR', 'E999999994', '系统忙，请稍后再提交 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='W000000001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'W000000001', '记录不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EPY0006001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EPY0006001', '用户内部账户不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EPY0000001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EPY0000001', '提现订单不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EPM0000001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EPM0000001', '银行信息未找到 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000027';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EMG0000027', '交易不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000009';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EMG0000009', '商户不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000008';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EMG0000008', '摘要验证失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000047';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'ECP0000047', '非法的代付退款处理请求状态值 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000045';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'ECP0000045', '批次总笔数与明细笔数不一致 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000044';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'ECP0000044', '批次总金额与明细金额之和不一致 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000034';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'ECP0000034', '账号货币不符 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000033';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'ECP0000033', '账号户名不符 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000032';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'ECP0000032', '账号错误 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000029';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'ECP0000029', '存折号码有误 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000022';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'ECP0000022', '转出卡委托不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000017';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'ECP0000017', '账户不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000015';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'ECP0000015', '无贷记账户 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECF1000014';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'ECF1000014', '提现银行不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECF1000004';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'ECF1000004', '用户支付密码错误 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001205';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EBGP001205', '转出帐号不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001204';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EBGP001204', '账号有误，请您重新核对 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001201';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EBGP001201', '转出帐号不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001101';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EBGP001101', '查询记录不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001100';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EBGP001100', '查询条件错误 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EBGP001001', '银企客户号不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000030';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EBG1000030', '银行信息未找到 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000027';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EBG1000027', '交易流水号不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000016';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EBG1000016', '交易日期错误 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000015';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EBG1000015', '联行号不正确 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000013';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EBG1000013', '渠道未找到或未实现 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000012';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EBG1000012', '渠道账户配置信息未找到 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG0000003';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EBG0000003', '短信验证码输入错误，请重新输入 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EAC0000001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'EAC0000001', '账户信息不存在 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100016';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'E000100016', '无效的平台虚拟账号 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100014';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'E000100014', '交易金额不能低于最小金额 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100013';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'E000100013', '交易总笔数与明细不符 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100011';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'E000100011', '交易总笔数不能大于最大笔数！ ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E000100010';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000549', 'UMB_SVR', 'E000100010', '总金额与实际金额不符 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000026';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EMG0000026', '保存请求报文至数据库失败，请检查！ ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000025';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EMG0000025', '拷贝数据失败，请检查 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000024';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EMG0000024', '报文体格式化失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000017';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EMG0000017', '错误码加载时转码错误 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000023';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'ECP0000023', '批量文件格式错误 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000003';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'ECP0000003', '渠道交易异常 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'ECP0000001', '后端渠道通讯异常 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECF1000006';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'ECF1000006', '数据校验失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001216';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBGP001216', '批量转账失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001103';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBGP001103', '转帐交易失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001004';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBGP001004', '银行数据库异常，请稍后再试 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001003';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBGP001003', '银行发生系统错误 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG2000004';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG2000004', '对账文件下载失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG2000003';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG2000003', '代扣对账文件未生成 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG2000002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG2000002', '下载代扣对账文件失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG2000001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG2000001', '单笔代扣请求失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000103';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000103', '对账失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000102';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000102', '单笔转账结果失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000101';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000101', '单笔转账查询请求失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000100';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000100', '单笔转账请求失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000034';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000034', '银企设置有错误 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000032';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000032', '银企操作员错误 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000028';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000028', '对账文件数据解析异常 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000026';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000026', '数据库查询异常 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000023';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000023', '路由表中找不到通道 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000022';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000022', '数据插入异常 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000021';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000021', '数据插入影响行数异常 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000020';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000020', '数据更新异常 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000019';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000019', '数据更新影响行数异常 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000011';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000011', '请求响应解析异常 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000010';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000010', '请求和响应的交易流水号不一致 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000009';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000009', '请求的响应数据为空 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000008';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG1000008', 'HTTP请求发送失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG0004001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG0004001', '充值返回数据异常 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG0000004';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG0000004', '发送短信验证码失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG0000001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'EBG0000001', '格式化短信息失败 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='E999999999';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'UMB_SVR', 'E999999999', '系统错误 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EPY0006002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000541', 'UMB_SVR', 'EPY0006002', '余额不足，无法完成提现 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000039';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000541', 'UMB_SVR', 'ECP0000039', '后端通道头寸不足 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000038';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000541', 'UMB_SVR', 'ECP0000038', '后端通道总额度不足 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000025';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000541', 'UMB_SVR', 'ECP0000025', '代扣余额不足 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000012';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000552', 'UMB_SVR', 'EMG0000012', '商户状态不正常 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EMG0000011';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000552', 'UMB_SVR', 'EMG0000011', '商户业务状态已关闭，请联系管理员 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECP0000027';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000552', 'UMB_SVR', 'ECP0000027', '账户被临时锁定 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECF1000017';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000552', 'UMB_SVR', 'ECF1000017', '该用户状态异常，请联系管理员 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='ECF1000015';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000552', 'UMB_SVR', 'ECF1000015', '客户状态异常，请联系管理员 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001203';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000552', 'UMB_SVR', 'EBGP001203', '转出帐号已经冻结 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBGP001202';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000552', 'UMB_SVR', 'EBGP001202', '转出帐号帐户已经注销 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EAC1002008';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000552', 'UMB_SVR', 'EAC1002008', '付款方账户状态异常 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EAC0000006';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000552', 'UMB_SVR', 'EAC0000006', '账户已销户，不能交易 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EAC0000002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000552', 'UMB_SVR', 'EAC0000002', '账户状态异常 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='C000000000';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000000', 'UMB_SVR', 'C000000000', '交易成功 ');
commit;

 delete from serverrescode where SERVERID='UMB_SVR' and SERVERRESCODE='EBG1000050';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000551', 'UMB_SVR', 'EBG1000050', '验证码请求频繁');
commit;


--平安银行响应码
delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='EA9983';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000015', 'PAB_SVR', 'EA9983', '签名验签失败,交易未签名');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='YQ9998';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000015', 'PAB_SVR', 'YQ9998', '签名验签失败');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='EA9985';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000015', 'PAB_SVR', 'EA9985', '企业签名的证书不对');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='YQ9986';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'PAB_SVR', 'YQ9986', '企业没有开通银企直联');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='YQ9990';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000546', 'PAB_SVR', 'YQ9990', '企业本账户没有开通此交易');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='YQ9977';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'PAB_SVR', 'YQ9977', '报文体解析编码或格式异常');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='E00006';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'PAB_SVR', 'E00006', '服务方响应错误');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='E00007';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000019', 'PAB_SVR', 'E00007', '创建连接异常');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='E00008';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000019', 'PAB_SVR', 'E00008', '通讯异常');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='AFE004';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000019', 'PAB_SVR', 'AFE004', '通讯异常-接后台服务响应');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='AFE001';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000019', 'PAB_SVR', 'AFE001', 'AFE处理异常');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='AFE002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000019', 'PAB_SVR', 'AFE002', '通讯异常');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='E00006';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000019', 'PAB_SVR', 'E00006', '服务方响应错误');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='E00007';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000019', 'PAB_SVR', 'E00007', '创建连接异常[连银行]');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='E00008';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000019', 'PAB_SVR', 'E00008', '通讯异常');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='YQ9999';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'PAB_SVR', 'YQ9999', '银企平台程序故障');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='EBLN00';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'PAB_SVR', 'EBLN00', '银企平台程序故障');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='GW3002';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000019', 'PAB_SVR', 'GW3002', '通讯异常');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='U1';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'PAB_SVR', 'U1', '接收文件上传请求');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='U2';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'PAB_SVR', 'U2', '从企业FTP服务器取文件');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='U3';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'PAB_SVR', 'U3', '文件签名、加密');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='U4';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'PAB_SVR', 'U4', '文件上传银行');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='U5';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000550', 'PAB_SVR', 'U5', '发送通知');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='F0';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000000', 'PAB_SVR', 'F0', '文件传输成功');

delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='F0';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000000', 'PAB_SVR', '000000', '交易成功');

commit;


--2016-5-31 16:51:42 新增平安银行错误码    FE0502    查询记录不存在
delete from serverrescode where SERVERID='PAB_SVR' and SERVERRESCODE='FE0502';
insert into serverrescode (PLATCODE, SERVERID, SERVERRESCODE, SERVERRESDES) values ('PLA000501', 'PAB_SVR', 'FE0502', '记录不存在');

commit;