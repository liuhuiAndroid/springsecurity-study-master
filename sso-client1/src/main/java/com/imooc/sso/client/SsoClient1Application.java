/**
 *
 */
package com.imooc.sso.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhailiang
 */
@SpringBootApplication
@RestController
// @EnableOAuth2Sso 会帮我们完成跳转到授权服务器，需要配置 application.properties
@EnableOAuth2Sso
public class SsoClient1Application {

    /**
     * @param user
     * @return Authentication 对象来描述当前用户的相关信息
     */
    @GetMapping("/user")
    public Authentication user(Authentication user) {
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(SsoClient1Application.class, args);
    }

}
