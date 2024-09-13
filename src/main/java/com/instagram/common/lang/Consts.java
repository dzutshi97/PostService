package com.instagram.common.lang;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Consts {

    public final static String GLOBAL = "GLOBAL";
    public final static Integer PLAYER_FILTER_LIMIT = 5;
    public final static String DATA_FILE_PATH = "src/main/resources/data/data.json";
    public static final String COMMENTS_LIST_PREFIX = "comments:";
    public static final int TOTAL_POSTS = 1_000_000; // 1 Million
    public static final int DESCRIPTION_LENGTH = 1000;
    public static final String POST_KEY_PREFIX = "post:";
    public static final String POSTS_SORTED_SET = "posts:sorted";
    public static final int DEFAULT_COMMENTS_PER_POST = 1000;

}