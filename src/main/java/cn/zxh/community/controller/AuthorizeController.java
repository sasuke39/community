package cn.zxh.community.controller;

import cn.zxh.community.dto.AccesstokenDTO;
import cn.zxh.community.dto.GithubUser;
import cn.zxh.community.mapper.UserMapper;
import cn.zxh.community.model.User;
import cn.zxh.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

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

    @Autowired
    private UserMapper userMapper;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(github_uri);
        accesstokenDTO.setState(state);
        accesstokenDTO.setClient_id(github_clientid);
        accesstokenDTO.setClient_secret(github_client_secret);
        String accessToken = gitHubProvider.getAccessToken(accesstokenDTO);
        GithubUser githubUser = gitHubProvider.getUser(accessToken);
        System.out.println(githubUser.getName());
        if (githubUser != null) {
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountID(String.valueOf(githubUser.getId()));
            user.setGmtCreat(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreat());
            userMapper.insert(user);
            request.getSession().setAttribute("githubUser", user);
            return "redirect:/";
        } else {

        }
        return "redirect:/";
    }

}
