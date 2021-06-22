package cn.xjtu.iotlab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.xjtu.iotlab.dao")
public class IotlabApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotlabApplication.class, args);
    }
//    @s
}
