# simple-brac
简单实现的BRAC用户权限校验框架，just a toy : )

## 功能
不校验用户名密码是否正确，只校验接口是否有权限

## 用法
1. 引入maven依赖（本地打包或deploy到私服，maven中央仓库没有）
    ```xml
     <dependency>
        <groupId>priv.thinkam</groupId>
        <artifactId>simple-brac</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    ```
1. 配置类加EnableSbrac注解
1. 实现SbracAuthContext接口，实现其中的方法，并注册到spring bean容器中
1. 实现SbracAuthFailHandler处理校验失败的情况，比如返回JSON或HTML，并注册到spring bean容器中。也可以不实现，那鉴权就会返回空白页
1. 配置文件配置"sbrac.non-validate-urls"不经过brac权限校验的url，用逗号隔开
1. 配置文件配置"sbrac.non-validate-urls"不经过brac权限校验的角色，用逗号隔开

## 开发
修改代码后运行单元测试"UnitTest.java"

## 许可证
[Apache-2.0](http://www.apache.org/licenses/LICENSE-2.0)