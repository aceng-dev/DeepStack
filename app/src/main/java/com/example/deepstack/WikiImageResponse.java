package com.example.deepstack;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class WikiImageResponse {

    @SerializedName("query")
    private Query query;

    public Query getQuery() { return query; }

    public static class Query {
        @SerializedName("pages")
        private Map<String, Page> pages;
        public Map<String, Page> getPages() { return pages; }
    }

    public static class Page {
        @SerializedName("thumbnail")
        private Thumbnail thumbnail;
        public Thumbnail getThumbnail() { return thumbnail; }
    }

    public static class Thumbnail {
        @SerializedName("source")
        private String source;
        public String getSource() { return source; }
    }
}