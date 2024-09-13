package com.instagram.service;

import com.instagram.models.Comment;
import com.instagram.models.Post;
import com.instagram.repository.CommentRepository;
import com.instagram.repository.PostRepository;
import com.instagram.repository.PostRepositoryImpl;
import com.instagram.response.PostRequest;
//import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
//import org.springframework.beans.factory.annotation.Autowired;
import com.instagram.response.PostResp;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostsService {

    @Autowired
    PostRepository postRepository;
    private static final Logger logger = LoggerFactory.getLogger(PostsService.class);


    public void createPosts() throws Exception {
        postRepository.generateAndSavePosts();
    }

    public String createPost(PostRequest post) throws Exception {
        logger.info("Creating a new post");
        Post post1 =new Post();
        String postId = UUID.randomUUID().toString();
        post1.setId(postId);
        post1.setTimestamp(System.currentTimeMillis());
        post1.setDescription(post.getDescription());
        post1.setLocation(post.getLocation());
        postRepository.savePost(post1);
        return postId;
    }

    public PostResp fetchPost(String postId){
        logger.info("Fetching post details");
        Post post = postRepository.getPostById(postId);
        PostResp postResp = new PostResp();
        postResp.setPostId(post.getId());
        postResp.setDescription(post.getDescription());
        postResp.setLocation(post.getLocation());
        postResp.setTimestamp(post.getTimestamp());
        return postResp;
    }

    public void updatePost(String postId,PostRequest post) throws Exception {
        logger.info("Updating post details");
        Post post1 = new Post();
        post1.setLocation(post.getLocation());
        post1.setTimestamp(System.currentTimeMillis());
        post1.setDescription(post.getDescription());
        post1.setId(postId);
        postRepository.updatePost(post1);
    }

    public void deletePost(String postId) throws Exception {
        postRepository.deletePost(postId);
    }

    public List<PostResp> fetchRecentPosts(int page, int pageSize){
        logger.info("fetching recent post details");
        List<Post> posts = postRepository.fetchRecentPosts(page,pageSize);
        List<PostResp> postRequests = new ArrayList<>();
        for(Post post: posts){
            PostResp postresp = new PostResp();
            postresp.setPostId(post.getId());
            postresp.setDescription(post.getDescription());
            postresp.setLocation(post.getLocation());
            postRequests.add(postresp);
        }
        return postRequests;
    }


}
