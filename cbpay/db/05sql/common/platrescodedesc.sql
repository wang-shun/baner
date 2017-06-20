--平台 错误码表数据

--平台部分
delete from platrescodedesc where errorcode='PLA000000';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000000', '成功');

delete from platrescodedesc where errorcode='PLA000001';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000001', '表中配置数据错误');

delete from platrescodedesc where errorcode='PLA000002';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000002', 'context取出数据错误');

delete from platrescodedesc where errorcode='PLA000003';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000003', '必输字段校验失败');

delete from platrescodedesc where errorcode='PLA000004';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000004', '获取循环次数失败');

delete from platrescodedesc where errorcode='PLA000005';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000005', '协议未启动');

delete from platrescodedesc where errorcode='PLA000006';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000006', '数据发送失败');

delete from platrescodedesc where errorcode='PLA000007';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000007', '系统特殊处理异常');

delete from platrescodedesc where errorcode='PLA000008';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000008', '拆包异常');

delete from platrescodedesc where errorcode='PLA000009';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000009', '组包异常');

delete from platrescodedesc where errorcode='PLA000010';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000010', '前拦截器异常');

delete from platrescodedesc where errorcode='PLA000011';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000011', '响应码转换异常');

delete from platrescodedesc where errorcode='PLA000012';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000012', '登记报文失败');

delete from platrescodedesc where errorcode='PLA000013';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000013', '登记交易日志失败');

delete from platrescodedesc where errorcode='PLA000014';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000014', '解密验签失败');

delete from platrescodedesc where errorcode='PLA000015';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000015', '加密加签失败');

delete from platrescodedesc where errorcode='PLA000016';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000016', '协议基础服务执行异常');

delete from platrescodedesc where errorcode='PLA000017';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000017', '获取报文对应的msgdescriber失败');

delete from platrescodedesc where errorcode='PLA000018';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000018', '后拦截器异常');

delete from platrescodedesc where errorcode='PLA000019';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000019', '和服务方通讯失败');







--业务部分
delete from platrescodedesc where errorcode='PLA000500';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000500', '订单重复');

delete from platrescodedesc where errorcode='PLA000501';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000501', '记录不存在');

delete from platrescodedesc where errorcode='PLA000502';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000502', '订单已经开始购汇，不能重复购汇');

delete from platrescodedesc where errorcode='PLA000503';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000503', '订单购汇未完成，不能付汇');

delete from platrescodedesc where errorcode='PLA000504';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000504', '订单已开始付汇，不能重复付汇');

delete from platrescodedesc where errorcode='PLA000505';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000505', '购汇前账户划转不成功，不能购汇');

delete from platrescodedesc where errorcode='PLA000506';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000506', '付汇前账户划转不成功，不能付汇');

delete from platrescodedesc where errorcode='PLA000507';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000507', '订单未完成支付');

delete from platrescodedesc where errorcode='PLA000508';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000508', '商户不存在');

delete from platrescodedesc where errorcode='PLA000509';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000509', '商户不支持此币种');

delete from platrescodedesc where errorcode='PLA000510';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000510', '商户没有此权限');

delete from platrescodedesc where errorcode='PLA000511';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000511', '(某商户的)用户信息不存在');

delete from platrescodedesc where errorcode='PLA000512';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000512', '(某商户的)用户卡信息不存在');

delete from platrescodedesc where errorcode='PLA000513';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000513', '用户信息重复');

delete from platrescodedesc where errorcode='PLA000514';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000514', '商户信息重复');

delete from platrescodedesc where errorcode='PLA000515';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000515', '查询数据异常');

delete from platrescodedesc where errorcode='PLA000516';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000516', '业务服务执行异常');

delete from platrescodedesc where errorcode='PLA000517';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000517', '插入数据异常');

delete from platrescodedesc where errorcode='PLA000518';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000518', '更新数据异常');

delete from platrescodedesc where errorcode='PLA000519';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000519', '转码失败');

delete from platrescodedesc where errorcode='PLA000520';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000520', '系统特殊处理失败');

delete from platrescodedesc where errorcode='PLA000521';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000521', '对账失败');

delete from platrescodedesc where errorcode='PLA000522';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000522', '平台多交易');

delete from platrescodedesc where errorcode='PLA000523';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000523', '平台少交易');

delete from platrescodedesc where errorcode='PLA000524';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000524', '交易状态不一致');

delete from platrescodedesc where errorcode='PLA000525';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000525', '已经完成对账');

delete from platrescodedesc where errorcode='PLA000526';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000526', '汇率表里没有最近两天数据');

delete from platrescodedesc where errorcode='PLA000527';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000527', '文件状态错误');

delete from platrescodedesc where errorcode='PLA000528';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000528', '获取最小汇率服务方异常');

delete from platrescodedesc where errorcode='PLA000529';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000529', '账户划转报文准备异常');

delete from platrescodedesc where errorcode='PLA000530';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000530', '支付流水不存在');

delete from platrescodedesc where errorcode='PLA000531';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000531', '账户划转信息不存在');

delete from platrescodedesc where errorcode='PLA000532';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000532', '商户支付报文中没有币种');

delete from platrescodedesc where errorcode='PLA000533';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000533', '交易状态错误');

delete from platrescodedesc where errorcode='PLA000534';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000534', '账户划转类型错误');

delete from platrescodedesc where errorcode='PLA000535';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000535', '商户上送tradecode不合法');

delete from platrescodedesc where errorcode='PLA000536';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000536', '商户报文验签失败');

delete from platrescodedesc where errorcode='PLA000537';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000537', '对账摘要校验失败');

delete from platrescodedesc where errorcode='PLA000538';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000538', '文件类型错误不能完成路由');

delete from platrescodedesc where errorcode='PLA000539';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000539', '报文类型错误');

delete from platrescodedesc where errorcode='PLA000540';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000540', '文件不存在');

commit;


--20160530 新增服务方部分错误码
delete from platrescodedesc where errorcode='PLA000541';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000541', '付款账户余额不足');

delete from platrescodedesc where errorcode='PLA000542';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000542', '输入信息有误');

delete from platrescodedesc where errorcode='PLA000543';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000543', '无效卡号');

delete from platrescodedesc where errorcode='PLA000544';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000544', '参数不合法');

delete from platrescodedesc where errorcode='PLA000545';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000545', '交易处理中');

delete from platrescodedesc where errorcode='PLA000546';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000546', '交易受限');

delete from platrescodedesc where errorcode='PLA000547';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000547', '流水号重复');

delete from platrescodedesc where errorcode='PLA000548';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000548', '其它错误');

delete from platrescodedesc where errorcode='PLA000549';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000549', '数据错误');

delete from platrescodedesc where errorcode='PLA000550';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000550', '服务方错误');

delete from platrescodedesc where errorcode='PLA000551';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000551', '请求频繁');

delete from platrescodedesc where errorcode='PLA000552';
insert into platrescodedesc (ERRORCODE, ERRORDESC) values ('PLA000552', '账户状态异常');

commit;