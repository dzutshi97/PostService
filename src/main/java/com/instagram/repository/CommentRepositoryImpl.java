package com.instagram.repository;

import com.instagram.common.exception.custom.CommentNotFoundException;
import com.instagram.common.exception.custom.PostNotFoundException;
import com.instagram.models.Comment;
import com.instagram.models.Post;
import com.instagram.util.RedisUtil;
import com.instagram.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.instagram.common.lang.Consts.COMMENTS_LIST_PREFIX;
import static com.instagram.common.lang.Consts.POST_KEY_PREFIX;

@Component
public class CommentRepositoryImpl implements CommentRepository{

    private static final Logger logger = LoggerFactory.getLogger(CommentRepositoryImpl.class);
    @Autowired
    RedisUtil redisUtil;


    // Save a comment on a post
    @Override
    public String saveComment(String postId, Comment comment) throws Exception {
//        redisUtil.addToEndOfList(COMMENTS_LIST_PREFIX + postId + ":" + comment.getCommentId(), comment); //right push
        //retrive original post from HSET, update & save it
        Post post = (Post) redisUtil.hget(POST_KEY_PREFIX, postId);
        if(post == null){
            throw new PostNotFoundException("Invalid post ID");
        }
        if(post.getComments() == null){
            post.setComments(new ArrayList<>());
        }
        post.getComments().add(comment);
        redisUtil.hset(POST_KEY_PREFIX ,postId, post);

        //save comment in range LIST
        redisUtil.addToEndOfList(COMMENTS_LIST_PREFIX + postId, comment); //right push
        return comment.getCommentId();
    }

    @Override
    public void deleteComment(String postId, String commentId) throws Exception {
        //remove comment from post object in HSET
        Post post = (Post) redisUtil.hget(POST_KEY_PREFIX, postId);
        Comment comment = null;
        for(Comment cmnt: post.getComments()){
            assert comment != null;
            if(cmnt.getCommentId().equals(commentId)){
                comment = cmnt;
            }
        }
        if(comment == null){
            throw new CommentNotFoundException("Invalid comment ID");
        }
        post.getComments().remove(comment);
        redisUtil.hset(POST_KEY_PREFIX ,postId, post);

        //remove comment from range LIST
        redisUtil.removeFromList(COMMENTS_LIST_PREFIX + postId, comment); //remove
    }

    // Fetch comments for a post in a paginated way
    @Override
    public List<Comment> fetchCommentsByPostId(String postId, int page, int pageSize) {
        long start = (page - 1) * pageSize;
        long end = start + pageSize - 1;

        // Fetch comments from the Redis list
//        List<Object> commentsData = redisUtil.rangeList(COMMENTS_LIST_PREFIX + postId, start, end); //RANGE
        List<Object> commentsData = redisUtil.rangeList(COMMENTS_LIST_PREFIX + postId, start, end); //RANGE
        List<Comment> comments = new ArrayList<>();
        for (Object commentObj : commentsData) {
            if (commentObj instanceof List) {
                List<Comment> nestedComments = (List<Comment>) commentObj;
                comments.addAll(nestedComments);
            } else {
                // Handle unexpected data types if necessary
                logger.error("Unexpected data type: " + commentObj.getClass());
            }
//            comments.add((Comment) commentObj); // Assuming comment is serializable
        }
        return comments;
    }
}
