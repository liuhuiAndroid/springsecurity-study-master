/**
 *
 */
package com.imooc.sso.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author zhailiang
 */
@Configuration
// @EnableAuthorizationServer使一个授权服务器在当前的应用程序上下文。
@EnableAuthorizationServer
// AuthorizationServerConfigurerAdapter 类实现 AuthorizationServerConfigurer 它提供了所有必要的方法来配置一个授权服务器。
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 配置两个应用
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory() // 使用in-memory存储
                .withClient("imooc1") // client_id
                .secret("imoocsecrect1") // client_secret
                .authorizedGrantTypes("authorization_code", "refresh_token") // 该client允许的授权类型
                .scopes("all") // 允许的授权范围
                .and()
                .withClient("imooc2") // client_id
                .secret("imoocsecrect2") // client_secret
                .authorizedGrantTypes("authorization_code", "refresh_token") // 该client允许的授权类型
                .scopes("all"); // 允许的授权范围
    }

    /**
     * 配置使用Jwt令牌
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(jwtTokenStore()) // 通过TokenStore接口完成对生成数据的持久化，默认实现为InMemoryTokenStore，即内存存储
                .accessTokenConverter(jwtAccessTokenConverter()); // 解码Token令牌的转换类
    }

    /**
     * 认证服务器安全配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 授权表达式
        security.tokenKeyAccess("isAuthenticated()");
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * @return 一个解码的Token令牌的类,JwtTokenStore 依赖这个类来进行编码以及解码
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("imooc"); // 用来参与签名计算
        return converter;
    }

}
