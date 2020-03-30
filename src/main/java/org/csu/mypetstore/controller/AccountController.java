package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/account/")
@SessionAttributes("loginAccount")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("loginForm")
    public String loginForm(){
        return "account/SignonForm";
    }

    @PostMapping("login")
    public String login(String username, String password, Model model){
        Account account = accountService.getAccount(username,password);
        String view;
        if(account==null){
            String signMessage = "Invalid username or password. Signon failed.";
            model.addAttribute("signMessage",signMessage);
            view = "account/SignonForm";
        }
        else {
            account.setPassword(null);
            model.addAttribute("loginAccount",account);
            view = "catalog/main";
        }
        return view;
    }

    @GetMapping("register")
    public String registerForm(){
        return "account/NewAccountForm";
    }

    @PostMapping("register")
    public String register(Account account){
        String view;
        try {
            accountService.insertAccount(account);
            view = "account/RegisterSuccessForm";
        }
        catch (Exception e){
            e.printStackTrace();
            view = "NewAccountForm";
        }
        return view;
    }

    @GetMapping("profile")
    public String profileForm(){
        return "account/EditAccountForm";
    }

    @PostMapping("profile")
    public String updateProfile(Account account){
        String view;
        try {
            accountService.updateAccount(account);
            view = "account/RegisterSuccessForm";
        }
        catch (Exception e){
            e.printStackTrace();
            view = "NewAccountForm";
        }
        return view;
    }

    @GetMapping("checkUsername")
    public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String str = "{\"isExit\":";

        String username = request.getParameter("username");
        int result = accountService.getAccount(username) == null? 0:1;
        if(result == 1){
            str += "\"yes\"}";
        }
        else{
            str += "\"no\"}";
        }
        out.println(str);
        out.flush();
        out.close();
    }
}
