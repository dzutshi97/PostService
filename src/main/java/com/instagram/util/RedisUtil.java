package com.instagram.util;

import com.instagram.models.Comment;
import com.instagram.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate sortedSetRedisTemplate; //sortedSetRedisTemplate
    @Autowired
    private RedisTemplate<String, Post> postRedisTemplate; //playerRedisTemplate //
    @Autowired
    private RedisTemplate<String, Comment> commentRedisTemplate;

    /**
     * sorted set
     */
    public boolean zSet(String key, Object value, double score) {
        return sortedSetRedisTemplate.opsForZSet().add(key, value, score);
    }

    public void zIncrementScore(String key, Object value, long delta) {
        sortedSetRedisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    //ZREVRANGE
    public Set<ZSetOperations.TypedTuple> getZSetRankDesc(String key, long start, long end) {
        return sortedSetRedisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    public Set<ZSetOperations.TypedTuple> getZSetRankAsc(String key, long start, long end) {
        return sortedSetRedisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    public Boolean zDelete(String key){
       return sortedSetRedisTemplate.delete(key);
    }
    public void zDeleteAll() {
        sortedSetRedisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    public Boolean zHasKey(String key) {
        return sortedSetRedisTemplate.opsForZSet().getOperations().hasKey(key);
    }

    //ZRANGEBYSCORE
    public long getZsetScore(String key, Object value) {
        Double score = sortedSetRedisTemplate.opsForZSet().score(key, value);
        if(score==null){
            return 0;
        }else{
            return score.longValue();
        }
    }
    //ZREVRANK
    public long getZrank(String key, Object value){
        Long rank = sortedSetRedisTemplate.opsForZSet().reverseRank(key, value);
        if(rank==null){
            return 0;
        }else{
            return rank.longValue();
        }
    }
    public void zSetRemove(String key, Object... values){
        sortedSetRedisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * hset
     *
     */
    public boolean hasKey(String key, String id){
        return postRedisTemplate.opsForHash().hasKey(key,id);
    }
    public boolean hset(String key, String item, Object value) throws Exception{
            postRedisTemplate.opsForHash().put(key, item, value);
        return true;
    }

    public Object hget(String key, String item) {
        return postRedisTemplate.opsForHash().get(key, item);
    }
    public void hdel(String key, Object... item) {
        postRedisTemplate.opsForHash().delete(key, item);
    }
    public Boolean hdelKey(String hkey, String key) throws Exception{
            postRedisTemplate.opsForHash().delete(hkey,key);
            return true;
    }

    /**
     * list
     *
     */
    public void addToEndOfList(String key, Comment item){
        commentRedisTemplate.opsForList().rightPush(key,item);
    }
    public void removeFromList(String key, Comment item){
        commentRedisTemplate.opsForList().remove(key,1,item);
    }

    public List<Object> rangeList(String key, long start, long end){
        return Collections.singletonList(commentRedisTemplate.opsForList().range(key, start, end));
    }

}
