## special introduction


#### 创建普通订单
```shell
curl -XPOST "http://localhost:20000/order_domain/v1/order/created" -H "Content-Type:application/json" -d '{
	"customerId": "18623377391"
}'
```