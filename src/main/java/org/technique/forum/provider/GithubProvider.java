package org.technique.forum.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.technique.forum.dto.AccessTokenDTO;
import org.technique.forum.dto.GithubUser;
import sun.awt.geom.AreaOp;

import java.io.IOException;

@Component//把当前类初始化到spring容器上下文
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/authorize")
                .post(body)
                .build();//用post请求发送到accessToken接口
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            return string;
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
        try (Response response = client.newCall(request).execute();) {
            String string = response.body().string();
            GithubUser githubUser=JSON.parseObject(string,GithubUser.class);
            } catch (IOException e) {
        }
        return null;
    }
}
