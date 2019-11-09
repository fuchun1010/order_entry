## special introduction

* wiki
```text

http://wiki.pagoda.com.cn/pages/viewpage.action?pageId=7646294


http://wiki.pagoda.com.cn/pages/viewpage.action?title=EOMS-php&spaceKey=eshop

电商wiki
http://wiki.pagoda.com.cn/pages/viewpage.action?pageId=2590226
```


#### 创建普通pos订单
```shell
curl -XPOST "http://localhost:20000/order_domain/v1/order/created" -H "Content-Type:application/json" -d '{
  "storeCode": "0001",
  "customerId": "10001",
  "entry": "pos",
  "items":[
    {
      "itemCode":"107852",
      "itemName": "芒果",
      "quality": 15.2,
      "unitPrice": 55.2
    }
  ]
}'
```