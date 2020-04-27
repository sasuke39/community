package cn.zxh.community.provider;

import cn.zxh.community.dto.AccesstokenDTO;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class GitHubProvider {
        public String getAccessToken(AccesstokenDTO accesstokenDTO){
            public static final MediaType JSON
                    = MediaType.get("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            String post(String url, String json) throws IOException {
                RequestBody body = RequestBody.create(json, JSON);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    return response.body().string();
                }
            }

        }
}
