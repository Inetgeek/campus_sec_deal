package edu.njust.campus_sec_deal;

import edu.njust.campus_sec_deal.controller.WSChatController;
import edu.njust.campus_sec_deal.controller.WSNoticeController;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("edu.njust.campus_sec_deal.mapper")
public class CampusSecDealApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(CampusSecDealApplication.class, args);
        WSChatController.setApplicationContext(run);
        WSNoticeController.setApplicationContext(run);
    }

}
