package com.instagram.postservice;

import com.instagram.models.Post;
import com.instagram.repository.PostRepository;
import com.instagram.response.PostRequest;
import com.instagram.response.PostResp;
import com.instagram.service.PostsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostsService postsService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and inject them into the PostsService instance
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchRecentPosts() {
        // Arrange
        int page = 1;
        int pageSize = 3;

        // Mock posts from the repository
        Post post1 = new Post();
        post1.setId("1");
        post1.setDescription("Description 1");

        Post post2 = new Post();
        List<Post> mockPosts = Arrays.asList(post1, post2);

        // Define the behavior of the repository
        when(postRepository.fetchRecentPosts(page, pageSize)).thenReturn(mockPosts);

        // Call the service method
        List<PostResp> result = postsService.fetchRecentPosts(page, pageSize);

        // Validate the result
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getPostId());
        assertEquals("Description 1", result.get(0).getDescription());

        // Verify that the fetchRecentPosts method in the repository was called with the correct parameters
        verify(postRepository, times(1)).fetchRecentPosts(page, pageSize);
    }
}

//
//    @Test
//    public void testFetchAllTop_Global() throws NotFoundRankCalculationSourceType {
//        // Mocking
//        RankCalculatorStrategy rs = mock(RankCalculatorStrategy.class);
//        when(rankingContext.getInstance(RankCalculatorSource.REDIS)).thenReturn(rs);
//        List<LeaderBoardItem> expected = new ArrayList<>();
//        LeaderBoardItem item = new LeaderBoardItem();
//        expected.add(item);
//        when(rs.getTopNPlayers(anyInt(), eq("GLOBAL"))).thenReturn(expected);
//
//        // Test
//        List<LeaderBoardItem> result = leaderBoardService.fetchAllTop(5, Optional.empty());
//        // Verify
//        assertEquals(expected, result);
//        verify(rs).getTopNPlayers(5, "GLOBAL");
//    }
//
//    @Test
//    public void testFetchAllTop_Country() throws NotFoundRankCalculationSourceType {
//        // Mocking
//        RankCalculatorStrategy rs = mock(RankCalculatorStrategy.class);
//        when(rankingContext.getInstance(RankCalculatorSource.REDIS)).thenReturn(rs);
//        List<LeaderBoardItem> expected = new ArrayList<>();
//        when(rs.getTopNPlayers(anyInt(), eq("US"))).thenReturn(expected);
//
//        // Test
//        List<LeaderBoardItem> result = leaderBoardService.fetchAllTop(5, Optional.of("US"));
//
//        // Verify
//        assertEquals(expected, result);
//        verify(rs).getTopNPlayers(5, "US");
//    }
//
//    @Test
//    void testGetRanks() throws NotFoundRankCalculationSourceType, PlayerNotFoundException {
//        // Prepare test data
//        Map<String, Object> params = new HashMap<>();
//        params.put("country",  Optional.of("US"));
//        params.put("sortBy", SortBy.TOP);
//        params.put("gender", Optional.of(Gender.MALE));
//        params.put("tier", Optional.of(Tier.SILVER));
//        params.put("limit", 10);
//
//
//        // Configure mock behavior
//        List<String> country = new ArrayList<>();
//        country.add("US");
//        RankCalculatorStrategy rs = mock(RankCalculatorStrategy.class);
//        when(rankingContext.getInstance(RankCalculatorSource.REDIS)).thenReturn(rs);
//
//        List<LeaderBoardItem> mockPlayers = new ArrayList<>();
//        LeaderBoardItem item = new LeaderBoardItem();
//        item.setCountryRank(1);
//        item.setCountry("US");
//        item.setTier(Tier.SILVER);
//        item.setGender(Gender.MALE);
//        mockPlayers.add(item);
//
//        LeaderBoardItem item2 = new LeaderBoardItem();
//        item2.setCountryRank(1);
//        item2.setCountry("US");
//        item2.setTier(Tier.GOLD);
//        item2.setGender(Gender.FEMALE);
//        mockPlayers.add(item2);
//        when(rs.getTopNPlayers(anyInt(), eq("US"))).thenReturn(mockPlayers);
//
//        List<LeaderBoardItem> expectdFilteredPlayers = List.of(item);
//        doReturn(expectdFilteredPlayers).when(andCriteria).filter(any());
//
//        // Call the method under test
//        List<LeaderBoardItem> result = leaderBoardService.getRanks(params);
//
//        assertEquals(expectdFilteredPlayers, result);
//    }
//}
