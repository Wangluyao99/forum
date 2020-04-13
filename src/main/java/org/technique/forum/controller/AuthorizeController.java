package org.technique.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.technique.forum.dto.AccessTokenDTO;
import org.technique.forum.dto.GithubUser;
import org.technique.forum.provider.GithubProvider;

@Controller
public class AuthorizeController {

    @Autowired//自动把spring容器中写好的实例化的实例，加载到当前使用的上下文
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public  String callback(@RequestParam(name="code") String code,
                            @RequestParam(name="state") String state){
        AccessTokenDTO accessTokenDTO= new AccessTokenDTO();
        accessTokenDTO.setClient_id("baed17714a7ce460cdb0");
        accessTokenDTO.setClient_secret("2bc792be55c8d56693b9c30d9a49ce07efe9b102");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setState(state);
        githubProvider.getAccessToken(accessTokenDTO);
        return "Index";
    }
}
