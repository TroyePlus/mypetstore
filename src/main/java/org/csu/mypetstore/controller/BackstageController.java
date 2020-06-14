package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Administrator;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.AdministratorService;
import org.csu.mypetstore.service.AdministratorService;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/backstage/")
public class BackstageController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private CatalogService catalogService;

    @GetMapping("loginFrom")
    public String loginFrom(){
        return "backstage/login";
    }

    @PostMapping("login")
    public String login(String username, String password, String veryficode, Model model, HttpSession session){
        //判断验证码
        String text = (String) model.getAttribute("text");
        System.out.println(text+"    "+veryficode);

//        if (text==null || !text.equalsIgnoreCase(veryficode)) {//equalsIgnoreCase意思是不考虑大小写
//
//            model.addAttribute("imageMess", "验证码输入错误!");
//
//            return "administrator/SignonForm";
//        }

        Administrator administrator = administratorService.getAdministrator(username);
        if(administrator==null){
            String signMessage = "Invalid username or password. Signon failed.";
            model.addAttribute("signMessage",signMessage);
            return  "backstage/login";
        }
        else if(passwordEncoder.matches(password,administrator.getPassword())) {
            administrator.setPassword(null);
            model.addAttribute("administrator",administrator);
            session.setAttribute("administrator",administrator);
            return  "backstage/index";
        }
        else{
            String signMessage = "Invalid username or password. Signon failed.";
            model.addAttribute("signMessage",signMessage);
            return  "backstage/login";
        }
    }


    //查看视图，也即iframe
    @GetMapping("view")
    public String view_order(@RequestParam String cid){
        return "backstage/"+cid;
    }



}
