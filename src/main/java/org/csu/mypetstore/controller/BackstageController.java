package org.csu.mypetstore.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.InsertProvider;
import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.service.AdministratorService;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
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

    @GetMapping("viewOrder_detailed")
    public String viewOrder_detailed(@RequestParam int orderId,Model model){

        Order order = orderService.getOrder(orderId);

        List<LineItem> lineItemList = order.getLineItems();

        for (LineItem l :lineItemList
             ) {
            Item item = l.getItem();
            Product product = item.getProduct();
            processProductDescription(product);

        }
        model.addAttribute("order",order);
        model.addAttribute("lineItemList",lineItemList);

        return "backstage/order_detailed.html";
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

    private static void processProductDescription(Product product){
        String [] temp = product.getDescription().split("\"");
        product.setDescriptionImage(temp[1]);
        product.setDescriptionText(temp[2].substring(1));
    }


    @GetMapping("viewProductChart")
    @ResponseBody
    public List<Label> viewProductChart(HttpSession session){
        Object object = session.getAttribute("productLabels");
        if(object instanceof List){
            return (List<Label>) object;
        }

        List<Label> labels = catalogService.getItemCountGroupByProductId();
        session.setAttribute("productLabels", labels);
        return labels;
    }

    @GetMapping("newProductForm")
    public String newProductForm(){
        return "backstage/Add_Brand";
    }

    @GetMapping("editProductForm")
    public String editProductForm(String productId, Model model){
        if(productId!=null) {
            Product product = catalogService.getProduct(productId);
            CatalogController.processProductDescription(product);
            List<String> catIdList = catalogService.getCategoryId();
            model.addAttribute("product",product);
            model.addAttribute("catIdList",catIdList);
        }
        return "backstage/product-edit";
    }

    /*
        pn-pageNumber
        ps-pageSize
     */
    @GetMapping("products")
    @ResponseBody
    public PageInfo<Product> getProductWithName(@RequestParam(required = false, defaultValue ="1",value = "pn")Integer pn,
                                                @RequestParam(required = false, defaultValue ="10",value = "ps")Integer ps,
                                                @RequestParam("name")String name){
        PageHelper.startPage(pn, ps);
        //startPage后紧跟的这个查询就是分页查询
        List<Product> products = catalogService.getProductWithName(name);

        //处理Description
        CatalogController.processProductDescription(products);

        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        return new PageInfo<>(products, 5);
    }

    @PostMapping("editProduct")
    public Map<String,Object> editProduct(Product product){
        int status = catalogService.updateProduct(product);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status",status);
        return resultMap;
    }

    @PostMapping("products")
    public Map<String,Object> addProduct(Product product){
        int status = catalogService.insertProduct(product);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status",status);
        return resultMap;
    }

    @DeleteMapping("products")
    @ResponseBody
    public Map<String,Object> deleteProduct(String productId){
        int status = catalogService.deleteProduct(productId);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status",status);
        return resultMap;
    }

    @GetMapping("items")
    @ResponseBody
    public Map<String,Object> getItems(@RequestParam(required = false, defaultValue ="1",value = "pn")int pn,
                                       @RequestParam(required = false, defaultValue ="10",value = "ps")int ps,
                                       @RequestParam(required = false, value="itemId")String itemId){
        PageHelper.startPage(pn, ps);
        List<Item> items = catalogService.getItemListWithId(itemId);

        Map<String,Object> map = new HashMap<>();

        int status;
        PageInfo<Item> pageInfo;
        if(items == null || items.isEmpty()){
            status = 0;
            pageInfo = null;
        }
        else{
            status =1;
            pageInfo = new PageInfo<>(items, 5);
        }
        map.put("status",status);
        map.put("pageInfo",pageInfo);
        return map;
    }

    @DeleteMapping("items")
    @ResponseBody
    public Map<String, Object> deleteItem(String itemId){
        Map<String,Object> map = new HashMap<>();
        int status = catalogService.deleteItem(itemId);
        map.put("status",status);
        return map;
    }

    @GetMapping("testForm")
    public String testForm(){
        return "backstage/test";
    }
}
