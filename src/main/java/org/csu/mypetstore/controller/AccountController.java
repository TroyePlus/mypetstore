package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/account/")
@SessionAttributes({"account","myList"})
public class AccountController {
    @Autowired
    private AccountService accountService;

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
            List<Product> myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
            model.addAttribute("account",account);
            model.addAttribute("myList",myList);
            view = "catalog/main";
        }
        return view;
    }

    @GetMapping("signOut")
    public String signOff(Model model){
        model.addAttribute("account",new Account());
        model.addAttribute("myList", null);
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
    public String register(Account account, Model model){
        String view;
        if(account.getUsername()==null||account.getUsername().trim().length()==0||
        account.getPassword()==null||account.getPassword().trim().length()==0){
            String msg = "用户名或密码不能为空";
            model.addAttribute("msg",msg);
            view = "account/NewAccountForm";
        }
        else {
            accountService.insertAccount(account);
            view = "account/RegisterSuccessForm";
        }
        return view;
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
