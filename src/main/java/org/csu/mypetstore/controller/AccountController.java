package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/account/")
@SessionAttributes({"account","myList","text"})
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CatalogService catalogService;

    private static final List<String> LANGUAGE_LIST;
    private static final List<String> CATEGORY_LIST;

    static {
        List<String> langList = new ArrayList<String>();
        langList.add("ENGLISH");
        langList.add("CHINESE");
        LANGUAGE_LIST = Collections.unmodifiableList(langList);

        List<String> catList = new ArrayList<String>();
        catList.add("FISH");
        catList.add("DOGS");
        catList.add("REPTILES");
        catList.add("CATS");
        catList.add("BIRDS");

        CATEGORY_LIST = Collections.unmodifiableList(catList);
    }

    @GetMapping("loginForm")
    public String loginForm(){
        return "account/SignonForm";
    }

    @PostMapping("login")
    public String login(String username, String password, String image,Model model,HttpSession session){
        //判断验证码
        String text = (String) model.getAttribute("text");
        System.out.println(text+"    "+image);

        if (text==null || !text.equalsIgnoreCase(image)) {//equalsIgnoreCase意思是不考虑大小写

            model.addAttribute("imageMess", "验证码输入错误!");

            return "account/SignonForm";
        }

        Account account = accountService.getAccount(username);
        if(account==null){
            String signMessage = "Invalid username or password. Signon failed.";
            model.addAttribute("signMessage",signMessage);
           return  "account/SignonForm";
        }
        else if(passwordEncoder.matches(password,account.getPassword())) {
            account.setPassword(null);
            List<Product> myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
            model.addAttribute("account",account);
            session.setAttribute("account",account);
            model.addAttribute("myList",myList);
            return  "catalog/main";
        }
        else{
            String signMessage = "Invalid username or password. Signon failed.";
            model.addAttribute("signMessage",signMessage);
            return  "account/SignonForm";
        }
    }

    @GetMapping("signOut")
    public String signOut(HttpSession session, SessionStatus status){
//        session.removeAttribute("user");//取出http session中的user属性
        session.invalidate();  //然后让http session失效
        status.setComplete(); //最后是调用sessionStatus方法

        return "catalog/main";
    }

    @GetMapping("newAccountForm")
    public String newAccountForm(Model model){
        model.addAttribute("newAccount",new Account());
        model.addAttribute("LANGUAGE_LIST",LANGUAGE_LIST);
        model.addAttribute("CATEGORY_LIST",CATEGORY_LIST);
        return "account/NewAccountForm";
    }

    @PostMapping("newAccount")
    public String register(Account account, String image, Model model){
        String text = (String) model.getAttribute("text");
        System.out.println(text+"    "+image);

        if (text==null || !text.equalsIgnoreCase(image)) {//equalsIgnoreCase意思是不考虑大小写
            model.addAttribute("imageMess", "验证码输入错误!");
            return "account/NewAccountForm";
        }

        if(account.getUsername()==null||account.getUsername().trim().length()==0||
        account.getPassword()==null||account.getPassword().trim().length()==0){
            String msg = "用户名或密码不能为空";
            model.addAttribute("msg",msg);
            return "account/NewAccountForm";
        }
        else {
            accountService.insertAccount(account);
            return "account/RegisterSuccessForm";
        }
    }

    @GetMapping("editAccountForm")
    public String editAccountForm(@SessionAttribute("account")Account account, Model model){
        model.addAttribute("account",account);
        model.addAttribute("LANGUAGE_LIST",LANGUAGE_LIST);
        model.addAttribute("CATEGORY_LIST",CATEGORY_LIST);
        return "account/EditAccountForm";
    }

    @PostMapping("editAccount")
    public String editAccount(Account account, String repeatedPassword, Model model){
        String view;
        String password = account.getPassword();
        if(password==null||password.length()==0||repeatedPassword==null||repeatedPassword.length()==0){
            String msg = "密码不能为空";
            model.addAttribute("msg",msg);
            view = "account/EditAccountForm";
        }
        else if(!password.equals(repeatedPassword)){
            String msg = "两次输入的密码不一致";
            model.addAttribute("msg",msg);
            view = "account/EditAccountForm";
        }
        else {
            accountService.updateAccount(account);
            account = accountService.getAccount(account.getUsername());
            List<Product> myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
            model.addAttribute("account",account);
            model.addAttribute("myList",myList);
            return "redirect:/catalog/index";
        }
        return view;
    }

    @GetMapping("checkUsername")
    public void checkUsername(String username, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String str = "{\"isExit\":";

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
