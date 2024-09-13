package com.instagram.repository;

import com.instagram.models.Comment;
import com.instagram.models.Location;
import com.instagram.models.Post;
import com.instagram.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import java.util.*;

import static com.instagram.common.lang.Consts.*;

@Component
public class PostRepositoryImpl implements PostRepository{

    private static final Logger logger = LoggerFactory.getLogger(PostRepositoryImpl.class);
    @Autowired
    RedisUtil redisUtil;


    @Autowired
    CommentRepository commentRepository;


     public void generateAndSavePosts() throws Exception {
        logger.info("Prepopulating Redis with " + TOTAL_POSTS + " posts...");

        for (int i = 1; i <= TOTAL_POSTS; i++) {
            // Store the post in Redis
            Post post = new Post();
            post.setId(Integer.toString(i)); // Unique post ID
            post.setTimestamp(System.currentTimeMillis());
            post.setDescription(generateDescription(DESCRIPTION_LENGTH));
//            Comment comment = new Comment();
//            comment.setComment(generateDescription(DESCRIPTION_LENGTH));
//            List<Comment> comments = new ArrayList<>();
//            comments.add(comment);
//            post.setComments(comments);
            if(i%3 == 0 && i%5==0){ //multiple of 15
                post.setLocation(Location.MUMBAI);
            } else if (i%11 ==0) { //multiple of 11
                post.setLocation(Location.KASHMIR);
            }
            savePost(post);

            if (i % 10000 == 0) {
                 logger.info("Inserted " + i + " posts into Redis.");
            }
        }
        logger.info("Successfully prepopulated 1 million posts into Redis.");
    }

    /**
     * Commands to delete all 1M entries:
     * redis-cli --scan --pattern "post:*" | xargs redis-cli del
     * redis-cli del posts:sorted
     */

    // Generate a random 1000-character string
    public String generateDescription(int length) {
        StringBuilder sb = new StringBuilder(length);
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return sb.toString();
    }

    // Save a post to Redis
    @Override
    public void savePost(Post post) throws Exception {

        // Store the post as a hash
        redisUtil.hset(POST_KEY_PREFIX ,post.getId(), post);

        // Add the post to the sorted set with the timestamp as the score
        redisUtil.zSet(POSTS_SORTED_SET, post.getId(),post.getTimestamp());
    }

    @Override
    public Post getPostById(String postId) {
        Post post = (Post) redisUtil.hget(POST_KEY_PREFIX, postId);
//        List<Comment> comments = commentRepository.fetchCommentsByPostId(postId,1,DEFAULT_COMMENTS_PER_POST);
//        post.setComments(comments);
        return post;
    }

    // Fetch recent posts in a paginated way
    @Override
    public List<Post> fetchRecentPosts(int page, int pageSize) {
        long start = (page - 1) * pageSize;//0 100 200
        long end = start + pageSize - 1; //100 199 399

        // Fetch post IDs from the sorted set in reverse order (most recent first)
//        Set<Object> postIds = redisTemplate.opsForZSet().reverseRange(POSTS_SORTED_SET, start, end);
        Set<ZSetOperations.TypedTuple> postIds = redisUtil.getZSetRankDesc(POSTS_SORTED_SET, start, end);

        // Fetch the post details from Redis
        List<Post> posts = new ArrayList<>();

        for (ZSetOperations.TypedTuple<String> tuple: postIds) {
            String postID = tuple.getValue();
            Post post = (Post) redisUtil.hget(POST_KEY_PREFIX,  postID);
            if(post!= null){
               posts.add(post);
            }
        }
        return posts;
    }

    // Update a post description
    @Override
    public void updatePost(Post post) throws Exception {
        redisUtil.hset(POST_KEY_PREFIX, post.getId(),post);
    }

    // Delete a post
    @Override
    public void deletePost(String postId) throws Exception {
        redisUtil.hdelKey(POST_KEY_PREFIX, postId);
//        redisTemplate.delete(POST_KEY_PREFIX + postId);
//        redisTemplate.opsForZSet().remove(POSTS_SORTED_SET, postId);
        redisUtil.zSetRemove(POSTS_SORTED_SET,postId);
    }
}
