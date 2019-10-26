## 订单域开发资料
** **
#### 几个订单的特别情况

业务名称 | 场景描述|
-------| --------|
换货|用户买苹果,实际交易的是梨子,但是用户订单列表需要显示苹果|
果切|果切编码是0001, 但是这个果品编码是对应的一个叫0002的, 用0001的重量系数 * 0002的重量|
拼盘| 拼盘编码是0001, 下面有2个果品编码以及对应的系数,
团购| `拼团购买数据结构没有想清楚`
周期购 | `还没有想清楚`

** **
#### MySql表结构

* orders_N
* tab_od_raw_orders_N


> 原始订单表

字段 | 数据类型 | 是否是主键| 描述 |备注|  默认值 |
----|------|----|-----|------|----|
id | bigint unsigned  | Y | 主键 | 自动增长列|
orderNo | bigint unsigned  | N | 订单编号|唯一索引列|



> 订单子表
* tab_od_shard_order_detail_N

字段 | 数据类型 | 是否是主键| 描述 |备注| 默认值 |
----|------|----|-----|------|----|
id | bigint unsigned | Y | 主键 | 自动增长列||
orderNo | bigint unsigned  | N | 订单编号|唯一索引列||
parentOrderNo | bigint unsigned  | N | 父订单编号|btree索引,订单拆分 orderNo != parentOrderNo ||
orgCode | varchar(10) |N| 发货机构编码 |||
itemCode| varchar(10) | N  |果品编码 |这个需要去公共库匹配对应的名称|
realItemCode |varchar(10) | N  |果品编码 |实际交易的果品编码,处理换购| - |
translateType | tinyint unsigned | N | 交易类型| 1: 正 2:换货 3：果切 4：拼盘 |



> 订单活动表
* tab_od_shard_order_activities

字段 | 数据类型 | 是否是主键| 描述 |备注| 默认值 |
----|------|----|-----|------|----|
id | bigint unsigned | Y | 主键 | 自动增长列||
orderNo | bigint unsigned  | N | 订单编号|唯一索引列||
parentOrderNo | bigint unsigned  | N | 父订单编号|原始订单||
activityCode | varchar(10) | N | 活动编号|活动详情根据营销平台获取|


> 订单活动明细表
* tab_od_order_activities_detail_N

字段 | 数据类型 | 是否是主键| 描述 |备注| 默认值 |
----|------|----|-----|------|----|
id | bigint unsigned | Y | 主键 | 自动增长列||
orderNo | bigint unsigned  | N | 订单编号|唯一索引列||
targetCode | varchar(50) | N | 编号 |根据活动类型，可以是orderNo,也可以是itemCode||
parentOrderNo | bigint unsigned  | N | 父订单编号|原始订单||
activityCode | varchar(10) | N | 活动编号|活动详情根据营销平台获取|
activityType| tinyint unsigned |N | 活动目标对象,1:订单 2:果品| | 1 |
storeBurden | int unsigned| N | 门店负担 | | 0 |
groupBurden | int unsigned| N | 集团负担 | | 0 |
thirdBurden | int unsigned | N |第三方负担| | 0 |

#### redis存储结构
** **
* 主订单数据
```json
{
  "orderNo":"0001",
  "fullPrice": "50",
  "customerId":"c01",
  "orgCode":"",
  "isTeamer": "1",
  "activities":[
    {
      "activityCode":"",
      "desc":""
    }
  ],
  "items":[
    {
      "itemCode":"",
      "desc": "",
      "imageUrl":"",
      "unitPrice":"",
      "weight":"",
      "disCount":"19.0",
      "status":"1"
    }
  ],
  "payment": "2000",
  "paymentNo":"s0001",
  "payway":"we",
  "status": "0",
  "createDate":"2019-11-12 13:15:13",
  "orderedType": "0",
  "channel":"美团"
}
```

* redis数据结构描述
** **

> 主订单redis结构描述

key|描述|数据类型|
---|---|-------|
R:O:00001|key键|hash|

属性 | 值 | 业务含义|
----|----|--------|
orderNo| 字符串| 订单编号|
fullPrice| 数字字符串|原单价 |
teamer|数字字符串|1:团长 0: 普通人,如果是1表示这个单据是团购单|
customerId|字符串| 客户id, `如果是第三方平台下单，用什么数据可以作为customerId`|
orgCode | 字符串编码|发货机构可以是门店，可以是配送中心,这个调用`其他什么域`可以获取具体的详情 |
payment|数字字符串编码|订单实际支付价格|
paymentNo|字符串编码|支付编码|
payway|字符串编码|支付方式|
status|字符串编码|订单状态 未支付/支付/发货/配送/结束配送|
createDate|时间格式字符串  yyyy-MM-dd hh:mi:ss| 订单创建时间|
orderedType | 数字类型  |下单类型: 普通购买/团购/周期购 |
channel | 数字类型 | 购买渠道: 心享/美团/app |

key|描述|数据类型|
---|---|-------|
R:O:00001:A|key键,活动|set, R:O:00001:A:0003|set|

key|描述|数据类型|
---|---|-------|
R:O:00001:A:0003 | 原始订单00001下活动编号0003的值|hash|

属性 | 值 | 业务含义|
----|----|--------|
activityCode | 字符串编码| 活动编码,`如果是订单价格计算不在订单域,那么其他计算域发给我的时候需要给出，如果是订单中台域计算的话，需要调用营销中心获取`|
desc | 字符串编码 | 活动描述|

key|描述|数据类型|
---|---|-------|
R:O:00001:I|key键,活动|set, R:O:00001:I|set|

key|描述|数据类型|
---|---|-------|
R:O:00001:A:0006 | 原始订单00001下果品编号0006的值|hash|

属性 | 值 | 业务含义|
----|----|--------|
itemCode | 字符串编码 | 果品编码,可能是/裸果/果切/拼盘|
desc | 字符串编码 | 果品描述,冗余了一下，避免对商品域反复请求|
imageUrl | 字符串编码 |果品图片地址,冗余了一下，避免对商品域反复请求|
unitPrice |数字编码| 果品单价|
weight | 数字字符串编码 |果品重量|
disCount|数字字符串编码|单品折扣|
status|数字字符串编码|单品状态:退货/取消/正常/换货|

key|描述|数据类型|
---|---|-------|
c:xxx | 用户key | hash

属性 | 值 | 业务含义|
----|----|--------|
participation| 数字字符串| 不是拼团，参与人数，这个字段没有的|
subOrders|O:0001|子单单号,如果有多个逗号分离|
limit|购买限制||

> 子订单结构描述
```json

```














