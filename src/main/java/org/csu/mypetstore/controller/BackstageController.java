package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Administrator;
import org.csu.mypetstore.domain.LineItem;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.AdministratorService;
import org.csu.mypetstore.service.AdministratorService;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
    @RequestMapping("/backstage/")
public class BackstageController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private OrderService orderService;

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
    public String view_order(@RequestParam String cid,Model model){
        if(true){
            List<Order> allOrders = orderService.getAllOrders();
            List<Integer> orderIdList = new ArrayList<>();

            for(Order o: allOrders) {
                orderIdList.add(o.getOrderId());
            }

            model.addAttribute("allOrders",allOrders);
            model.addAttribute("orderIdList",orderIdList);
        }

        return "backstage/"+cid;
    }

    //统计订单类别数量
    @GetMapping("categoryCount")
    @ResponseBody //返回前端JSON数据
    public Map<String, Integer> category(Model model,HttpSession session){
//        List<Label> categoryLabel = new ArrayList<>();
//        //初始化五个标签
//        categoryLabel.add(new Label("BIRDS"));
//        categoryLabel.add(new Label("FISH"));
//        categoryLabel.add(new Label("CATS"));
//        categoryLabel.add(new Label("DOGS"));
//        categoryLabel.add(new Label("REPTILES"));

        //初始化五个map
        Map<String,Integer> categoryMap = new HashMap<>();
        categoryMap.put("BIRDS",0);
        categoryMap.put("FISH",0);
        categoryMap.put("CATS",0);
        categoryMap.put("DOGS",0);
        categoryMap.put("REPTILES",0);


        //分别获取五个种类的orderlist
        List<Order> birdsList = orderService.getOrdersByCategory("BIRDS");
        List<Order> fishList = orderService.getOrdersByCategory("FISH");
        List<Order> catsList = orderService.getOrdersByCategory("CATS");
        List<Order> dogsList = orderService.getOrdersByCategory("DOGS");
        List<Order> reptilesList = orderService.getOrdersByCategory("REPTILES");

        List<Order> allOrders = orderService.getAllOrders();
        List<Integer> orderIdList = new ArrayList<>();

        if(allOrders != null){

            for(Order o: allOrders){
                orderIdList.add(o.getOrderId());
                List<LineItem> lineItemList = o.getLineItems();
                for(LineItem l:lineItemList){
                    if(l != null){
                        //System.out.println(l.getItemId()+"---"+l.getQuantity());
                        String key = catalogService.getCategoryByItemId(l.getItemId());
                        System.out.println(key+"==="+categoryMap.get(key)+"________"+l.getQuantity());
                        categoryMap.put(key,l.getQuantity()+categoryMap.get(key));
                    }

                }
            }

        }

        model.addAttribute("categoryMap",categoryMap);

        model.addAttribute("allOrders",allOrders);
        model.addAttribute("fishList",fishList);
        model.addAttribute("catsList",catsList);
        model.addAttribute("dogsList",dogsList);
        model.addAttribute("birdsList",birdsList);
        model.addAttribute("reptilesList",reptilesList);

        //session.setAttribute("allOrders",allOrders);
        model.addAttribute("orderIdList",orderIdList);




        return categoryMap;
    }





}
