package com.example.deepstack;

import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WikiImageRepository {

    private final ApiService apiService;

    public WikiImageRepository() {
        apiService = WikiImageClient.getClient().create(ApiService.class);
    }

    public interface ImageCallback {
        void onImageLoaded(String imageUrl);
    }

    public void fetchImageForFish(String fishName, ImageCallback callback) {
        String url = "https://en.wikipedia.org/w/api.php?action=query"
                + "&prop=pageimages&format=json&piprop=thumbnail&pithumbsize=300"
                + "&titles=" + fishName.replace(" ", "%20");

        apiService.getWikiImage(url).enqueue(new Callback<WikiImageResponse>() {
            @Override
            public void onResponse(Call<WikiImageResponse> call,
                                   Response<WikiImageResponse> response) {
                if (response.isSuccessful() && response.body() != null
                        && response.body().getQuery() != null) {

                    Map<String, WikiImageResponse.Page> pages =
                            response.body().getQuery().getPages();

                    if (pages != null) {
                        for (WikiImageResponse.Page page : pages.values()) {
                            if (page.getThumbnail() != null) {
                                callback.onImageLoaded(page.getThumbnail().getSource());
                                return;
                            }
                        }
                    }
                }
                callback.onImageLoaded(null); // tidak ada gambar
            }

            @Override
            public void onFailure(Call<WikiImageResponse> call, Throwable t) {
                callback.onImageLoaded(null);
            }
        });
    }
}