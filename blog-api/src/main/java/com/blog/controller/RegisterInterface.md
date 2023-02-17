## 1.注册
#### 接口url：/register
#### 请求方式：POST
#### 请求参数：
| 参数名称 | 参数类型 | 说明 |
| -------- | -------- | ---- |
| account  | string   | 账号 |
| password | string   | 密码 |
| nickname | string   | 昵称 |
#### 响应数据：token(由jwt根据账号生成)