package com.instagram.repository;

import com.instagram.models.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository {

    void savePost(Post post) throws Exception;
    Post getPostById(String postId);
    List<Post> fetchRecentPosts(int page, int pageSize);
    void updatePost(Post post) throws Exception;
    void deletePost(String postId) throws Exception;
    void generateAndSavePosts() throws Exception;
}
