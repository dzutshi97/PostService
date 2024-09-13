package com.instagram.controller;

import com.instagram.common.lang.Result;
import com.instagram.models.*;
import com.instagram.response.PostRequest;
import com.instagram.response.PostResp;
import com.instagram.service.CommentService;
import com.instagram.service.PostsService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("posts")
public class PostsApi {

    @Autowired
    PostsService postsService;

    @Autowired
    CommentService commentService;


    @PostMapping("/prepopulate")
    @Operation(
            description = "publish multiple posts - 1 Million",
            responses = {
                    @ApiResponse(responseCode = "404",ref = "badRequest"),
                    @ApiResponse(responseCode = "500",ref = "internalServerError"),
                    @ApiResponse(responseCode = "200",ref = "successfulPrepopulate")
            }
    )
    public Result createPosts() throws Exception {
        postsService.createPosts();
        return Result.succ("1M sample posts generated and saved successfully!");
    }

    @PostMapping
    @Operation(
            description = "publish a new post",
            responses = {
                    @ApiResponse(responseCode = "404",ref = "badRequest"),
                    @ApiResponse(responseCode = "500",ref = "internalServerError"),
                    @ApiResponse(responseCode = "200",ref = "successfulCreatePost")
            }
    )
    public Result createPost(//@PathVariable String userId,
                             @RequestBody PostRequest post) throws Exception {
        String postId = postsService.createPost(post);
        return Result.succ("post published successfully! Post ID:"+postId);
    }

    @GetMapping("/{postId}")
    @RateLimiter(name = "backendA", fallbackMethod = "fallback")
    @Operation(
            description = "Fetch a post",
            responses = {
                    @ApiResponse(responseCode = "404",ref = "badRequest"),
                    @ApiResponse(responseCode = "500",ref = "internalServerError"),
                    @ApiResponse(responseCode = "200",ref = "successfulGetPost")
            }
    )
    public Result fetchPost(@PathVariable String postId)
    { //throws CustomException
        PostResp resp =  postsService.fetchPost(postId);
        return Result.succ(resp);
    }

    @GetMapping("/recent")
    @Operation(
            description = "Fetch recent n posts (paginated)",
            responses = {
                    @ApiResponse(responseCode = "404",ref = "badRequest"),
                    @ApiResponse(responseCode = "500",ref = "internalServerError"),
                    @ApiResponse(responseCode = "200",ref = "successfulFetchAllRecentPosts")
            }
    )
    public Result fetchPosts(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(name = "location", required = false) Optional<Location> location
                             )
             { //throws CustomException
        List<PostResp> resp =  postsService.fetchRecentPosts(page,size);
        return Result.succ(resp);
    }
    // Fallback method when rate limit is exceeded
    public String fallback(int page, int size,Exception e) {
        System.out.println("FALLBACK!!!");
        return "Rate limit exceeded, please try again later!";
    }
//    @GetMapping
//    @Operation(
//            description = "fetch top N posts",
//            responses = {
//                    @ApiResponse(responseCode = "404",ref = "badRequest"),
//                    @ApiResponse(responseCode = "500",ref = "internalServerError"),
//                    @ApiResponse(responseCode = "200",ref = "successfulGetPostTop")
//            }
//    )
//    public Result getLeaderBoardTop(
//            @RequestParam(name = "limit", defaultValue = "5")  @Max(value = 100, message = "max 100") int limit,
//            @RequestParam(name = "country", required = false) Optional<String> country,
//            @RequestParam(name = "gender", required = false) Optional<Gender> gender,
//            @RequestParam(name = "tier", required = false) Optional<Tier> tier
//    ) throws NotFoundRankCalculationSourceType, PlayerNotFoundException {
//        // posts by - likes, limit, - top recent posts / top liked posts (limit = 100 or 50)
//        Map<String, Object> params = new LeaderBoardParamsBuilder()
//                .limit(limit)
//                .country(country)
//                .gender(gender)
//                .tier(tier)
//                .sortBy(SortBy.TOP)
//                .build();
//        List<LeaderBoardItem> items = leaderBoardService.getRanks(params);
//        return Result.succ(items);
//    }

    @PutMapping("/{postId}")
    @Operation(
            description = "update a post",
            responses = {
                    @ApiResponse(responseCode = "404",ref = "badRequest"),
                    @ApiResponse(responseCode = "500",ref = "internalServerError"),
                    @ApiResponse(responseCode = "200",ref = "successfulUpdatePostId")
            }
    )
    public Result updatePost(@PathVariable String postId, @RequestBody PostRequest post) throws Exception {
        postsService.updatePost(postId, post);
        return Result.succ("Post updated successfully!");
    }

    @DeleteMapping("/{postId}")
    @Operation(
            description = "delete a post",
            responses = {
                    @ApiResponse(responseCode = "404",ref = "badRequest"),
                    @ApiResponse(responseCode = "500",ref = "internalServerError"),
                    @ApiResponse(responseCode = "200",ref = "successfulDeletePostId")
            }
    )
    public Result deletePost(@PathVariable String postId) throws Exception {
        postsService.deletePost(postId);
        return Result.succ("Post deleted successfully!");
    }

    @PostMapping("/{postId}/comments")
    @Operation(
            description = "Add a comment to a post",
            responses = {
                    @ApiResponse(responseCode = "404",ref = "badRequest"),
                    @ApiResponse(responseCode = "500",ref = "internalServerError"),
                    @ApiResponse(responseCode = "200",ref = "successfulPOSTComment")
            }
    )
    public Result addCommentToPost(@PathVariable String postId
                                   ,@RequestBody String comment
    ) throws Exception { //throws CustomException
        String id = commentService.addComment(postId,comment);
        return Result.succ("Comment added successfully! ID = "+id);
    }


    @GetMapping("/{postId}/comments")
    @Operation(
            description = "List comments for a post (paginated)",
            responses = {
                    @ApiResponse(responseCode = "404",ref = "badRequest"),
                    @ApiResponse(responseCode = "500",ref = "internalServerError"),
                    @ApiResponse(responseCode = "200",ref = "successfulGETComment")
            }
    )
    public Result listCommentsForPost(@PathVariable String postId,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size)
             { //throws CustomException
        List<Comment> resp =  commentService.fetchComments(postId,page,size);
        return Result.succ(resp);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @Operation(
            description = "Delete comment of a post",
            responses = {
                    @ApiResponse(responseCode = "404",ref = "badRequest"),
                    @ApiResponse(responseCode = "500",ref = "internalServerError"),
                    @ApiResponse(responseCode = "200",ref = "successfulDELComment")
            }
    )
    public Result deleteComment(@PathVariable String postId
            ,@PathVariable String commentId
    ) throws Exception { //throws CustomException
        commentService.deleteComment(postId,commentId);
        return Result.succ("Comment deleted successfully!");
    }
}
