package com.chrisbaileydeveloper.bookshelf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
@Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
public class DevDatabaseConfiguration {

    @Bean
    public RedisConnectionFactory jedisConnFactory() {
        return new JedisConnectionFactory();
    }
    
}
