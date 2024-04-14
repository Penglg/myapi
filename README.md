# MyAPI - API接口开放平台

## 项目介绍

### 项目描述
该项目是基于 Spring Boot + MyBatis-Plus + MySQL + Spring Cloud Gateway + Dubbo 开发的API开放接口平台，目的是方便开发者的开发工作，让开发者方便地使用由该平台提供的接口。

### 项目结构
| 项目             | 功能                            |
| ---------------- | ------------------------------- |
| backend          | 提供API开放接口平台后端服务     |
| myapi-interface  | 提供可调用接口                  |
| myapi-client-sdk | 客户端sdk，提供给用户以调用接口 |
| myapi-common     | 公共服务模块                    |
| myapi-gateway    | 网关模块                        |

### 功能描述
![图片](https://github.com/Penglg/myapi/assets/109449337/9184f39e-dd9f-4bf1-b48b-fb7025ad5f86)


## 快速开始

### 前端安装
1. 下载myapi-frontend，即该项目对应的前端项目：https://github.com/Penglg/myapi-frontend
   ```git clone https://github.com/Penglg/myapi-frontend.git
2. 安装npm
   本人npm版本是10.2.3
3. 启动项目
   ```npm run start:dev

### 后端安装
1. 下载项目
   ```git clone https://github.com/Penglg/myapi.git
2. 项目打包
   打开api-common项目，执行maven的install命令将其打包到本地
   打开api-client-sdk项目，执行maven的install命令将其打包到本地
3. 启动Redis
   启动Redis，并在backend项目的yml文件中配置为自己的Redis
4. 启动Nacos
   安装Nacos，本人是2.1.2版本
   单机启动岂可
   ```startup.cmd -m standalone
   并在backend项目的yml文件中配置为自己的Nacos
5. 启动backend项目、myapi-gateway项目及myapi-interface项目

### 使用
1. 查看backend项目文档
   访问http://localhost:7529/api/doc.html
2. 查看项目页面
   访问http://localhost:8000

## 展示
- 文档
  ![图片](https://github.com/Penglg/myapi/assets/109449337/0d401638-1861-4011-9d33-2a083acd67a9)

- 页面
  ![图片](https://github.com/Penglg/myapi/assets/109449337/6b13e862-72fc-4103-abcd-538a4d435df5)
  ![图片](https://github.com/Penglg/myapi/assets/109449337/0e6a153d-da16-4c71-9625-adbf44f9b131)
  ![图片](https://github.com/Penglg/myapi/assets/109449337/fc7c1e8e-b462-4991-8df6-3be5966452dd)
  ![图片](https://github.com/Penglg/myapi/assets/109449337/954a03bb-bee9-46ea-ab3c-dfe181b4134d)





