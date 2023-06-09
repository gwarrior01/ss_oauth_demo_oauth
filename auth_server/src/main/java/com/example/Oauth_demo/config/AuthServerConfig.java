package com.example.Oauth_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(NoOpPasswordEncoder.getInstance())
                .checkTokenAccess("isAuthenticated()")
//                .tokenKeyAccess("isAuthenticated()")
        ;

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client1")
                .secret("secret1")
                .scopes("read")
                .accessTokenValiditySeconds(5000) // seconds
                .authorizedGrantTypes("password", "refresh_token")
                .and()
                .withClient("client2")
                .secret("secret2")
                .scopes("read")
                .accessTokenValiditySeconds(5000) // seconds
                .authorizedGrantTypes("client_credentials")
                .and()
                .withClient("resourceserver")
                .secret("12345");

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
//                .tokenStore(tokenStore())
//                .accessTokenConverter(converter())
        ;
    }

//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(converter());
//    }
//
//    @Bean
//    public JwtAccessTokenConverter converter() {
//        var conv = new JwtAccessTokenConverter();
//        conv.setSigningKey("secret");
//        return conv;
//    }
}
