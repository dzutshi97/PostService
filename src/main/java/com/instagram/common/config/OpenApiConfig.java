package com.instagram.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

import static org.springframework.http.RequestEntity.put;

/**
 * Swagger URL - http://localhost:8080/swagger-ui/index.html#/
 */
@OpenAPIDefinition
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI baseOpenAPI(){
        ApiResponse badRequest = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\" : 404, \"status\" : \"Bad Request\", \"Message\" : \"Bad Request\"}"))));
        ApiResponse internalServerError = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\" : 500, \"status\" : \"internalServerError\", \"Message\" : \"Internal Server Error\"}"))));
        ApiResponse successfulResponseReset = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\":200,\"msg\":\"Successful\",\"data\":\"reset success!\"}"))));

        ApiResponse successfulGetPost = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\":200,\"msg\":\"Successful\",\"data\":{\"id\":\"2032deb5-09fd-4088-ab4b-c4ec2607a761\",\"description\":\"This is a new post about a recent event -1.\",\"timestamp\":1687894567890,\"location\":\"MUMBAI\"}}"))
                )
        );

        ApiResponse successfulCreatePost = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\":200,\"msg\":\"Successful\",\"data\":\"post published successfully! Post ID:9edcfc99-7c1f-43f0-9869-73cb66a60bf5\"}"))
                )
        );

//        ApiResponse successfulFetchAllRecentPosts = new ApiResponse().content(
//                new Content().addMediaType("application/json",
//                        new io.swagger.v3.oas.models.media.MediaType()
//                                .addExamples("default", new Example().value(
//                                            "{" +
//                                                    "\"code\": 200," +
//                                                    "\"msg\": \"Successful\"," +
//                                                    "\"data\": [" +
//                                                    "{" +
//                                                    "\"id\": \"1000000\"," +
//                                                    "\"description\": null," +
//                                                    "\"timestamp\": 1725898384463," +
//                                                    "\"location\": null," +
//                                                    "}" +
//                                                    "]" +
//                                                    "}"
//                                    ))
//                )
//        );


        ApiResponse successfulFetchAllRecentPosts = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType()
                                .addExamples("default", new Example().value(
                                        "{" +
                                                "\"code\": 200," +
                                                "\"msg\": \"Successful\"," +
                                                "\"data\": [" +
                                                "{" +
                                                "\"postId\": \"ac376f55-8abd-4e37-9144-200474d7db5e\"," +
                                                "\"description\": \"This is a new post about a recent event -3.\"," +
                                                "\"location\": \"MUMBAI\"" +
                                                "}," +
                                                "{" +
                                                "\"postId\": \"9edcfc99-7c1f-43f0-9869-73cb66a60bf5\"," +
                                                "\"description\": \"This is a new post about a recent event -1.\"," +
                                                "\"location\": \"MUMBAI\"" +
                                                "}" +
                                                "]" +
                                                "}"
                                ))
                )
        );



        ApiResponse successfulUpdatePostId = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType()
                                .addExamples("default", new Example().value(
                                            "{" +
                                                    "\"code\": 200," +
                                                    "\"msg\": \"Successful\"," +
                                                    "\"data\": \"Post updated successfully!\"" +
                                                    "}"
                                    ))
                )
        );


        ApiResponse successfulDeletePostId = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\":200,\"msg\":\"Successful\",\"data\":\"Post deleted successfully!\"}"))
                )
        );


        ApiResponse successfulPOSTComment = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType()
                                .addExamples("default", new Example().value(
                                        "{" +
                                                "\"code\": 200," +
                                                "\"msg\": \"Successful\"," +
                                                "\"data\": \"Comment added successfully! ID = f90ef82a-d100-430a-aff5-0a8708900dfc\"" +
                                                "}"
                                ))
                )
        );


        ApiResponse successfulGETComment = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType()
                                .addExamples("default", new Example().value(
                                        "{" +
                                                "\"code\": 200," +
                                                "\"msg\": \"Successful\"," +
                                                "\"data\": [" +
                                                "{" +
                                                "\"commentId\": \"sa342155-8abd-4e37-9144-200474d7db5e\"," +
                                                "\"postId\": \"ac376f55-8abd-4e37-9144-200474d7db5e\"," +
                                                "\"userId\": null," +
                                                "\"comment\": \"\\\"hey,what's up\\\"\"," +
                                                "\"timestamp\": 1726164337460" +
                                                "}," +
                                                "{" +
                                                "\"commentId\": \"xyz5678-8abd-4e37-9144-200474d7db5e\"," +
                                                "\"postId\": \"ac376f55-8abd-4e37-9144-200474d7db5e\"," +
                                                "\"userId\": null," +
                                                "\"comment\": \"new comment\"," +
                                                "\"timestamp\": 1726164337460" +
                                                "}" +
                                                "]" +
                                                "}"
                                ))
                )
        );


        ApiResponse successfulDELComment = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\":200,\"msg\":\"Successful\",\"data\":\"Comment deleted successfully!!\"}"))
                )
        );

        ApiResponse successfulPrepopulate = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType()
                                .addExamples("default", new Example().value(
                                        "{" +
                                                "\"code\": 200," +
                                                "\"msg\": \"Successful\"," +
                                                "\"data\": \"1M sample posts generated and saved successfully!\"" +
                                                "}"
                                ))
                )
        );


        Components components = new Components();
        components.addResponses("badRequest",badRequest);
        components.addResponses("internalServerError",internalServerError);

        //post
        components.addResponses("successfulCreatePost",successfulCreatePost);
        components.addResponses("successfulGetPost",successfulGetPost);
        components.addResponses("successfulFetchAllRecentPosts",successfulFetchAllRecentPosts);
        components.addResponses("successfulUpdatePostId",successfulUpdatePostId);
        components.addResponses("successfulDeletePostId",successfulDeletePostId);
        //comment
        components.addResponses("successfulPOSTComment",successfulPOSTComment);
        components.addResponses("successfulGETComment",successfulGETComment);
        components.addResponses("successfulDELComment",successfulDELComment);
        return new OpenAPI().components(components).info(new Info().title("Post Service").version("1.0.0").description("Social Media (Instagram) post service - MVP version "));
    }
}
