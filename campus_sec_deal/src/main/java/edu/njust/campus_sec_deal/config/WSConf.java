/**
 * @Class: WSConf
 * @Date: 2022/9/20
 * @Project: campus_sec_deal
 * @Author: Colyn
 * @Builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WSConf implements WebSocketMessageBrokerConfigurer {

    @Bean
    public ServerEndpointExporter exporter() {
        return new ServerEndpointExporter();
    }

}

