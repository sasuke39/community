package cn.zxh.community.provider;

import cn.zxh.community.dto.AccesstokenDTO;
import cn.zxh.community.dto.GithubUser;
import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import okhttp3.*;
import org.springframework.stereotype.Component;
import sun.security.provider.certpath.OCSPResponse;

import javax.swing.*;
import java.io.IOException;

@Component
public class GitHubProvider {
        public String getAccessToken(AccesstokenDTO accesstokenDTO){
            MediaType mediaType = MediaType.get("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON.toJSONString(accesstokenDTO),mediaType);
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String token = string.split("&")[0].split("=")[1];//以"="划分字符串并且获取第2个
                return token;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }
        public GithubUser getUser(String accessToken){
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String string = response.body().string();
                GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
                return githubUser;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
}
