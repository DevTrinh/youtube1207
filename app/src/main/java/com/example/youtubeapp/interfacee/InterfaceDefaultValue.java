package com.example.youtubeapp.interfacee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface InterfaceDefaultValue {
    //    String API_YOUTUBE_MAIN_VIDEO_VN = "https://youtube.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&chart=mostPopular&maxResults=50&regionCode=VN&key=AIzaSyDA2oRYCsTyVd6u5y1ypfda19b-fQZ_7VQ";
    String API_YOUTUBE_MAIN_VIDEO = "https://youtube.googleapis.com/youtube/v3/videos?part=id&part=liveStreamingDetails&part=snippet&part=statistics&chart=mostPopular&maxResults=50&key=AIzaSyBqGtsd3uG32P2u2WPbfiS-9qF1uikVWJ8";
    String API_IMAGE_CHANNEL_YOUTUBE = "https://www.googleapis.com/youtube/v3/channels?part=snippet&id=UCwppdrjsBPAZg5_cUwQjfMQ&fields=items%2Fsnippet%2Fthumbnails&key=AIzaSyDA2oRYCsTyVd6u5y1ypfda19b-fQZ_7VQ";
    //    String infovideo  ... https://youtube.googleapis.com/youtube/v3/commentThreads?part=snippet%2Creplies&maxResults=100&videoId=_VB39Jo8mAQ&key=AIzaSyDA2oRYCsTyVd6u5y1ypfda19b-fQZ_7VQ
    String API_YOUTUBE_EXPLORE = "https://youtube.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&chart=mostPopular&maxResults=50&regionCode=VN&key=AIzaSyDA2oRYCsTyVd6u5y1ypfda19b-fQZ_7VQ";
    String API_KEY = "AIzaSyBqGtsd3uG32P2u2WPbfiS-9qF1uikVWJ8";
    String PREF_SEARCH ="PREF_SEARCH";
    String ITEMS = "items";
    String ID = "id";
    String VALUE_ID_SEARCH = "ID_VALUE_SEARCH";
    String SNIPPET = "snippet";
    String VALUE_SEARCH = "VALUE_SEARCH";
    String THUMBNAIL = "thumbnails";
    String STANDARD = "standard";
    String CHANNEL_TITLE ="channelTitle";
    String VIEW_COUNT = "viewCount";
    String LIKED_COUNT = "likeCount";
    String URL = "url";
    String ID_VIDEO = "videoId";
    String TITLE = "title";
    String VALUE_TITLE_SEARCH = "TITLE_VALUE_SEARCH";
    String VALUE_IMAGE_CHANNEL = "IMAGE_VALUE_CHANNEL";
    String VALUE_DESCRIPTION_CHANNEL = "DESCRIPTION_VALUE_CHANNEL";
    String VALUE_TIME_UP_SEARCH = "VALUE_TIME_UP_SEARCH";
    String VALUE_TITLE_CHANNEL = "TITLE_CHANNEL_VALUE";
    String VALUE_VIEW_SEARCH = "VIEW_COUNT_SEARCH";
    String VALUE_CHANNEL_ID = "VALUE_CHANNEL_ID";
    String PUBLISHED_AT = "publishedAt";
    String STATISTICS = "statistics";
    String VALUE_ITEM_VIDEO = "valueItemVideo";
    String COMMENT_COUNT = "commentCount";
    String CHANNEL_ID = "channelId";
    String HIGH = "high";
    String SUBSCRIBE_COUNT = "subscriberCount";
    String FRAGMENT_SEARCH = "FRAGMENT_SEARCH";
    String FRAGMENT_HOME = "FRAGMENT_HOME";
    String FRAGMENT_EXPLORE = "FRAGMENT_EXPLORE";
    String FRAGMENT_SUBSCRIPTION = "FRAGMENT_SUBSCRIPTION";
    String FRAGMENT_NOTIFICATION = "FRAGMENT_NOTIFICATION";
    String FRAGMENT_LIBRARY = "FRAGMENT_LIBRARY";
    String DATA_SEARCH = "DATA_SEARCH";
    String DESCRIPTION = "description";
    String IMAGE_CHANNEL = "IMAGE_CHANNEL";
    String ID_REFRESH  = "ID_REFRESH";

    String AUTHOR_DISPLAY_NAME = "authorDisplayName";
    String TEXT_DISPLAY = "textDisplay";
    String AUTHOR_PROFILE_IMAGE_URL = "authorProfileImageUrl";
    String TOTAL_REPLY_COUNT = "totalReplyCount";
    String TOP_LEVEL_COMMENT  = "topLevelComment";

    int RQS_RECORDING = 1;
}