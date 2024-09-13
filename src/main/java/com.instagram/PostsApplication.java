package com.instagram;

import com.instagram.models.Comment;
import com.instagram.models.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableCaching
//@EnableHystrixDashboard
//@EnableCircuitBreaker
//@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
public class PostsApplication {
	@Value("${redis.hostname}")
	private String redisHostName;

	@Value("${redis.port}")
	private int redisPort;


	@Bean
	public JedisConnectionFactory getJedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(redisHostName);
		jedisConnectionFactory.setPort(redisPort);
		return jedisConnectionFactory;
	}

	@Bean
	public RedisTemplate<String, Post> postRedisTemplate() { //hset
		RedisTemplate<String, Post> template = new RedisTemplate<>();
		template.setConnectionFactory(getJedisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Post.class));
		return template;
	}

	@Bean
	public RedisTemplate<String, Comment> commentRedisTemplate() { //hset
		RedisTemplate<String, Comment> template = new RedisTemplate<>();
		template.setConnectionFactory(getJedisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Comment.class));
		return template;
	}

	@Bean
	public RedisTemplate<String, Object> sortedSetRedisTemplate() { //sorted set
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(getJedisConnectionFactory());
//		template.setKeySerializer(new StringRedisSerializer());
		return template;
	}

	public static void main(String[] args) {
		SpringApplication.run(PostsApplication.class, args);
	}
}

