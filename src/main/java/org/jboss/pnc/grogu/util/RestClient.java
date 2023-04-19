package org.jboss.pnc.grogu.util;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

public class RestClient {
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient.Builder().connectTimeout(Duration.ofSeconds(60))
            .readTimeout(Duration.ofMinutes(5))
            .writeTimeout(Duration.ofMinutes(5))
            .build();

    public static String post(String url, String jsonData, Map<String, String> headers) throws IOException {

        Request.Builder builderRequest = new Request.Builder().url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, jsonData));

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builderRequest = builderRequest.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = builderRequest.build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
    }
}
