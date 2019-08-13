### 微服务后端项目结构

```
├─bing-auth						-- 授权中心(后台 后台登录现在认证走授权中心,微信业务授权暂未走授权中心rysuan-biz单独实现的登录)
├─bing-common					-- 公共工具类方法
├─bing-gateway					-- 网关
├─bing-mybatis					-- mybatis-plus 代码生成
├─bing-resources				-- Demo resources 与业务无关
├─bing-test						-- Test 测试项目模块 与业务无关
├─bing-upms						-- 后台权限管理模块
├─bing-view						-- 监控模块
│  └─bing-sba					-- spring-boot-admin 监控服务状态
├─doc							-- 文档资源存放地址
│  └─nacos-server-1.0.0.zip			-- nacos 注册中心配置中心
│  └─sentinel-dashboard-1.6.1.jar	-- sentinel (可以不使用)
```
### 界面效果

![输入图片说明](https://images.gitee.com/uploads/images/2019/0813/220722_6a3127ff_734677.png "")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0813/220744_b3334663_734677.png "")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0813/220805_a88902eb_734677.png "")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0813/220821_bd64ade0_734677.png "")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0813/220830_e5b22492_734677.png "")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0813/221258_138a0b17_734677.png "")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0813/220840_3a32191b_734677.png "")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0813/220847_069217d6_734677.png "")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0813/221059_06f04066_734677.png "")