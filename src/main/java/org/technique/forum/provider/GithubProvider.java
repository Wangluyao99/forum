package org.technique.forum.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.technique.forum.dto.AccessTokenDTO;
import org.technique.forum.dto.GithubUser;

import java.io.IOException;

@Component//把当前类初始化到spring容器上下文
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token?client_id=baed17714a7ce460cdb0&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                .post(body)
                .build();//用post请求发送到accessToken接口
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
           String token = string.split("&")[0].split("=")[1];
            System.out.println(token);
            return token;//通过对其拆解，得到token，并返回
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_Token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser=JSON.parseObject(string,GithubUser.class);
            return githubUser;
            } catch (IOException e) {
        }
        return null;
    }
}
