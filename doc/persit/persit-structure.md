## 订单域开发资料
** **
#### 几个订单的特别情况

业务名称 | 场景描述|
-------| --------|
换货|用户买苹果,实际交易的是梨子,但是用户订单列表需要显示苹果|
果切|果切编码是0001, 但是这个果品编码是对应的一个叫0002的, 用0001的重量系数 * 0002的重量|
拼盘| 拼盘编码是0001, 下面有2个果品编码以及对应的系数,
团购| 发起团购的订单超过1天没有拼团OK的，都删除，生产都废弃,如果有加入订单，达到上限以后，所有订单开始投递,有团长记录，应对可能的针对团长有特别折扣|
周期购 | 一次性生产一个购买周期内的所有订单，但是只激活一个订单，其余订单要么放在数据库，定时加载到redis进行处理或者都放在redis处理，如果用户要取消就全部删除|
心享| 发货机构可能是门店，可能是配送中心，可能都有

** **
#### MySql表结构

* orders_N
* tab_od_raw_orders_N


> 原始订单表

字段 | 数据类型 | 是否是主键| 描述 |备注|  默认值 |
----|------|----|-----|------|----|
id | bigint unsigned  | Y | 主键 | 自动增长列|
orderNo | bigint unsigned  | N | 订单编号|唯一索引列|
enable|tinyint unsigned | 是否可以开始使用|默认都是可以使用的，除了周期购|


> 订单子表
* tab_od_shard_order_detail_N

字段 | 数据类型 | 是否是主键| 描述 |备注| 默认值 |
----|------|----|-----|------|----|
id | bigint unsigned | Y | 主键 | 自动增长列||
orderNo | bigint unsigned  | N | 订单编号|唯一索引列||
orgCode | varchar | N | 机构编号||
parentOrderNo | bigint unsigned  | N | 父订单编号|btree索引,订单拆分 orderNo != parentOrderNo ||
itemCode| varchar(10) | N  |果品编码 |这个需要去公共库匹配对应的名称|
realItemCode |varchar(10) | N  |果品编码 |实际交易的果品编码,处理换购| - |
translateType | tinyint unsigned | N | 交易类型| 1: 正 2:换货 3：果切 4：拼盘 |
status | tinyint unsigned | N | 订单状态|1.待支付 2.支付 3.发货 4.配送中 5.收货|



> 订单优惠表
* tab_od_shard_order_discount

字段 | 数据类型 | 是否是主键| 描述 |备注| 默认值 |
----|------|----|-----|------|----|
id | bigint unsigned | Y | 主键 | 自动增长列||
orderNo | bigint unsigned  | N | 订单编号|唯一索引列||
parentOrderNo | bigint unsigned  | N | 父订单编号|原始订单||
activityCode | varchar(10) | N | 活动编号|活动详情根据营销平台获取|
discountType| tinyint unsigned |N | 优惠类型|活动还是优惠券|


> 订单活动明细表
* tab_od_order_activities_detail_N

字段 | 数据类型 | 是否是主键| 描述 |备注| 默认值 |
----|------|----|-----|------|----|
id | bigint unsigned | Y | 主键 | 自动增长列||
orderNo | bigint unsigned  | N | 订单编号|唯一索引列||
targetCode | varchar(50) | N | 编号 |根据活动目标类型，可以是orderNo,也可以是itemCode,还可以是渠道|当是优惠券的时候，-|
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
  "orgCodes":[
    {
        "orgCode":"xxx",
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
        ]
    }
  ],
  "suborders":"c:00001:o,c:00002:o",
  "payment": "2000",
  "paymentNo":"s0001",
  "payway":"we",
  "status": "0",
  "createDate":"2019-11-12 13:15:13",
  "orderedType": "0",
  "channel":"美团",
  "receiverAddr":"收货人地址",
  "dispatchCompany":"",
  "rider":"",
  "phone":""
}
```

* redis数据结构描述
** **

> 主订单redis结构描述

key|描述|数据类型|英文含义|
:---|---|-------|:---:|
P:O:00001|key键|hash|primary:order:主订单编号|

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
receiverAddr| 字符串描述| 收货人地址|
dispatchCompany|字符串描述|配送公司地址|
rider | 字符串描述|骑手|
phone|字符串描述|电话号码|

key|描述|数据类型|英文含义|
---|---|-------|:---:|
P:O:00001:D|key键,活动|set, R:O:00001:A:0003|set|primary:order:订单编号:discount|

key|描述|数据类型|
---|---|-------|
P:O:00001:D:0003 | 原始订单00001下折扣编号0003的值|hash|

属性 | 值 | 业务含义|
----|----|--------|
disCountCode | 字符串编码| 优惠编码,`如果是订单价格计算不在订单域,那么其他计算域发给我的时候需要给出，如果是订单中台域计算的话，需要调用营销中心获取`|
desc | 字符串编码 | 活动描述|

key|描述|数据类型|英文含义|
---|---|-------|:----:|
P:O:00001:I|key键,活动|set, R:O:00001:I|set|primary:order:00001:items|

key|描述|数据类型|
---|---|-------|
P:O:00001:I:0006 | 原始订单00001下果品编号0006的值|hash|

属性 | 值 | 业务含义|
----|----|--------|
itemCode | 字符串编码 | 果品编码,可能是/裸果/果切/拼盘|
desc | 字符串编码 | 果品描述,冗余了一下，避免对商品域反复请求|
imageUrl | 字符串编码 |果品图片地址,冗余了一下，避免对商品域反复请求|
unitPrice |数字编码| 果品单价|
weight | 数字字符串编码 |果品重量|
disCount|数字字符串编码|单品折扣|
status|数字字符串编码|单品状态:退货/取消/正常/换货|

key|描述|数据类型|英文含义|
---|---|-------|:----:|
c:xxx | 用户key,c:00001 | hash|customer:用户编号

属性 | 值 | 业务含义|
----|----|--------|
subOrders|O:0001|子单单号,如果有多个逗号分离|
groupOrders|O:0002|这个字段不是组团订单的情况下不会有|
isTeamer|默认0|只要不是团购单都是0,团购单是1|
limit|数字类型字符串|购买限制|
riderPhone|字符串|骑手电话|
receiver|字符串|收货人地址|
disPatchCompany|字符串|配送公司地址|

> 子订单结构描述
```json
{
  "orderNo":"",
  "parentNo":"",
  "orgCode":"",
  "items":[
    {
      "itemCode":"",
      "disCount":"19.0",
    }
  ],
  "status": 1
}

```

属性|值|业务含义
---|:----:|----|
orderNo|字符串|子订单号码|
parentNo|字符串|父订单号|
orgCode|发货机构|`这个地方我有点疑惑`，如果订单拆单了，会不会不是和拆单之前不是同一个发货机构|
items.itemCode| 字符串|果品code|
items.disCount| 数字字符串|果品实际成交价|
status | 字符串|单据状态 |

> 注意: 子订单的相关果品维度数据都可以从父订单中获取,orgCode会不会不一致，如果一致这个key是没有的

** **

> mysql表结构

属性|值|业务含义|
----|:-----:|-----:|
orderNo|数字字符串|子订单单号|
parentNo|数字字符串|父订单的订单号|
orgCode|发货机构|这个字段不一定有|
itemCode|字符串|果品code|
disCount|字符串|折扣|

> 注意这个地方没有按照范式，如果出现分表确保出现在同一个库中，避免出现夸库关联的情

> redis结构

key|描述|数据类型|英文含义|
---|---|-------|:----:|
c:10001:o | 子订单key,c:10001:o | hash|child:10001:order|

















