package com.chrisbaileydeveloper.bookshelf.config;


import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Protocol;

@Configuration
@Profile(Profiles.PROD)
public class ProdDatabaseConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ProdDatabaseConfiguration.class);

    @Bean
    public RedisConnectionFactory redisConnFactory() {

        try {
            URI redistogoUri = new URI(System.getenv("REDISTOGO_URL"));

            JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
            jedisConnFactory.setUsePool(true);
            jedisConnFactory.setHostName(redistogoUri.getHost());
            jedisConnFactory.setPort(redistogoUri.getPort());
            jedisConnFactory.setTimeout(Protocol.DEFAULT_TIMEOUT);
            jedisConnFactory.setPassword(redistogoUri.getUserInfo().split(":", 2)[1]);

            return jedisConnFactory;

        } catch (final URISyntaxException e) {
            logger.error("Redis URI Syntax Exception", e);
            return null;
        }
    }
}
