package com.corruptprotoss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author RoyDowney
 * @date 2024/8/29
 */

@SpringBootApplication
@MapperScan("${mybatis-plus.mapperPackage}") // 配置要扫描的包
//@ComponentScan(basePackages = {"com.corruptprotoss.core", "com.corruptprotoss.payment"}) //配置要扫描的组件和配置
//@ComponentScan("com.corruptprotoss")
public class PaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class,args);
        System.out.println("支付服务启动成功 (＾－＾)V。。。。");
    }
}
