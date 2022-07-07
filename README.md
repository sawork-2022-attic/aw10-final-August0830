# aw10-final

## micropos的基本组成

### 对外展示的功能有：

#### pos-product

顾客可以查看所有商品信息，可以选择特定的商品。

#### pos-carts

顾客可以添加购物车，根据购物车号查询当前购物车内的物品，向购物车内根据商品号添加一件或者多件商品，用户可以结算，结算时自动生成订单并运输。

#### pos-order

生成订单的服务，支持顾客根据订单号进行查询详情。

#### pos-delivery

负责运输的服务，支持顾客根据运输号查询物流状态。

## 对内支持其他服务的服务

#### pos-discovery

各种服务需要在此处注册进入系统。

#### pos-gateway

负责转发顾客访问请求，使得多个服务对外仅有一个接口，便于顾客访问

#### pos-api

规定了部分服务的对外接口，规定了各服务之间用于传输信息的数据结构，使得各服务之间可以在数据交换的方式上达成一致。

## 设计思路

采用微服务架构，各个部分独立开发，可以独立运行进行测试；对外以gateway作为统一接口，之间以网络请求来发送消息，耦合度低，容易扩展。如果需要添加新的服务，只需要独立开发后进行eureka配置即可。

采用相似架构进行开发，每个项目下分为controller/rest层 todo，不同层次都可以安排异常处理机制避免系统整体崩溃。

采用消息驱动机制，如pos-order和pos-delivery之间采用publish-subscribe架构，在负载平衡的同时保持了低耦合度。



### 小规模测试样例

```bash
curl -H "Content-Type:text/plain" -X POST -d 'p1|p2|addr:CityA' http://localhost:2222/order 
#for pos-order
curl -H "Content-type:application/json" -X POST -d '{"orderId":"001","deliveryId":"001","delivered":"false"}' http://localhost:2222/delivery/add
#for pos-delivery
```

