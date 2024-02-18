package com.momo.redisdemo.config;

import com.fasterxml.jackson.databind.deser.impl.JDKValueInstantiators;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${redis.host}")
    private String redisHostName;

    @Value("${redis.port}")
    private Integer redisPort;

    // connect to redis
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){

        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHostName);
        redisConfig.setPort(redisPort);
        return new JedisConnectionFactory(redisConfig);
    }

    // interact with redis with jedis connection
    @Bean
    public RedisTemplate<String,Object> redisTemplate(){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());

        // set the serializer for Redis keys
        // in this case
        // StringRedisSerializer, indicating that keys will be treated as strings.

        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // set the serializer for hash keys within Redis hashes.

        redisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());

        // set the serializer for hash values within Redis hashes.
        // uses JdkSerializationRedisSerializer, which serializes objects
        // using Java's built-in serialization.

        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

        // since we are using redis as a noSQL database
        // we enable transaction support, if we plan to use Redis transactions

        redisTemplate.setEnableTransactionSupport(true);

        // triggers the initialization of the RedisTemplate
        // after all properties have been set.
        // It's a necessary step to ensure that
        // the RedisTemplate is properly configured and ready to use.

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
