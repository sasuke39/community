package cn.zxh.community.controller;

import cn.zxh.community.dto.AccesstokenDTO;
import cn.zxh.community.dto.GithubUser;
import cn.zxh.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String github_clientid;

    @Value("${github.Redirect_uri}")
    private String github_uri;

    @Value("${github.client.secret}")
    private String github_client_secret;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(github_uri);
        accesstokenDTO.setState(state);
        accesstokenDTO.setClient_id(github_clientid);
        accesstokenDTO.setClient_secret(github_client_secret);
        String accessToken = gitHubProvider.getAccessToken(accesstokenDTO);
        GithubUser user = gitHubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }




















































































































































































}
