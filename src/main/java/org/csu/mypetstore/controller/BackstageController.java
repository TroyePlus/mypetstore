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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

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
        List<Order> allOrders = orderService.getAllOrders();
        List<Integer> orderIdList = new ArrayList<>();

        for(Order o: allOrders) {
            orderIdList.add(o.getOrderId());
        }

        model.addAttribute("allOrders",allOrders);
        model.addAttribute("orderIdList",orderIdList);


        switch (cid){
            case "transaction.html":
                //统计信息
                Map<Integer,Integer> map = new HashMap<>();
                Map<Integer,Integer> tobedeliveredMap = new HashMap<>();
                Map<Integer,Integer> deliveredMap = new HashMap<>();

                //初始化
                for(int i =1 ;i<=6;i++){
                    map.put(i,0);
                    tobedeliveredMap.put(i,0);
                    deliveredMap.put(i,0);
                }


                for(Order o :allOrders){
                    map.put(o.getOrderDate().getMonth()+1,map.get(o.getOrderDate().getMonth()+1) + 1);
                    switch (o.getStatus()){
                        //case "已完成":completedOrder.add(o.getOrderId());break;
                        case "待发货":tobedeliveredMap.put(o.getOrderDate().getMonth()+1,tobedeliveredMap.get(o.getOrderDate().getMonth()+1) + 1);break;
                        case "已发货":deliveredMap.put(o.getOrderDate().getMonth()+1,deliveredMap.get(o.getOrderDate().getMonth()+1) + 1);break;
                        //case "待付款":topayOrder.add(o.getOrderId());break;
                    }
                }

                model.addAttribute("allMap",map);
                model.addAttribute("deliveredMap",deliveredMap);
                model.addAttribute("tobedeliveredMap",tobedeliveredMap);


                break;
            case "Order_handling.html":
                List<Integer> completedOrder = new ArrayList<>();
                List<Integer> tobereceivedOrder = new ArrayList<>();
                List<Integer> tobedeliveredOrder = new ArrayList<>();
                List<Integer> topayOrder = new ArrayList<>();

                for(Order o: allOrders) {
                    switch (o.getStatus()){
                        case "已完成":completedOrder.add(o.getOrderId());break;
                        case "待发货":tobedeliveredOrder.add(o.getOrderId());break;
                        case "待收货":tobereceivedOrder.add(o.getOrderId());break;
                        case "待付款":topayOrder.add(o.getOrderId());break;
                    }
                }

                model.addAttribute("completedOrder",completedOrder);
                model.addAttribute("tobedeliveredOrder",tobedeliveredOrder);
                model.addAttribute("tobereceivedOrder",tobereceivedOrder);
                model.addAttribute("topayOrder",topayOrder);



                break;
            case "Orderform.html":
                //分别获取五个种类的orderlist
                List<Order> birdsList = orderService.getOrdersByCategory("BIRDS");
                List<Order> fishList = orderService.getOrdersByCategory("FISH");
                List<Order> catsList = orderService.getOrdersByCategory("CATS");
                List<Order> dogsList = orderService.getOrdersByCategory("DOGS");
                List<Order> reptilesList = orderService.getOrdersByCategory("REPTILES");

                List<Integer> birdsIdList = new ArrayList<>();
                List<Integer> catsIdList = new ArrayList<>();
                List<Integer> fishIdList = new ArrayList<>();
                List<Integer> dogsIdList = new ArrayList<>();
                List<Integer> reptilesIdList = new ArrayList<>();

                for(Order o: catsList) {
                    catsIdList.add(o.getOrderId());
                }
                for(Order o: fishList) {
                    fishIdList.add(o.getOrderId());
                }
                for(Order o: dogsList) {
                    dogsIdList.add(o.getOrderId());
                }
                for(Order o: reptilesList) {
                    reptilesIdList.add(o.getOrderId());
                }


                for(Order o: birdsList) {
                    birdsIdList.add(o.getOrderId());
                }

                model.addAttribute("birdsIdList",birdsIdList);
                model.addAttribute("catsIdList",catsIdList);
                model.addAttribute("dogsIdList",dogsIdList);
                model.addAttribute("fishIdList",fishIdList);
                model.addAttribute("reptilesIdList",reptilesIdList);

                break;
        }

        return "backstage/"+cid;
    }

    @GetMapping("viewOrder_detailed")
    public String viewOrder_detailed(@RequestParam int orderId,Model model){

        Order order = orderService.getOrder(orderId);

        List<LineItem> lineItemList = order.getLineItems();

//        for (LineItem l :lineItemList
//             ) {
//            Item item = l.getItem();
//            Product product = item.getProduct();
//            processProductDescription(product);
//
//        }

        //添加发货信息
        Delivery delivery = orderService.getDeliveryByOrderId(orderId);
        model.addAttribute("delivery",delivery);

        model.addAttribute("order",order);
        model.addAttribute("lineItemList",lineItemList);

        return "backstage/order_detailed.html";
    }


    //统计订单类别数量
    @GetMapping("categoryCount")
    @ResponseBody //返回前端JSON数据
    public Map<String, Integer> category(Model model,HttpSession session){
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

        List<Integer> birdsIdList = new ArrayList<>();

        for(Order o: birdsList) {
            birdsIdList.add(o.getOrderId());
        }

        model.addAttribute("birdsIdList",birdsIdList);

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
    public List<Label> viewProductChart(){
        //HttpSession session
//        Object object = session.getAttribute("productLabels");
//        if(object instanceof List){
//            return (List<Label>) object;
//        }

        List<Label> labels = catalogService.getItemCountGroupByProductId();
//        session.setAttribute("productLabels", labels);
        return labels;
    }

    @GetMapping("categories")
    @ResponseBody
    public Map<String,Object> getCategory(@RequestParam(required = false, value = "id")String catId){
        Category category = catalogService.getCategory(catId);
        int status = category==null ? 0 : 1;
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        map.put("data",category);

        return map;
    }

    @DeleteMapping("categories")
    @ResponseBody
    public Map<String,Object> delCategory(String catId){
        int status = catalogService.deleteCategory(catId);
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        return map;
    }

    @PutMapping("categories")
    @ResponseBody
    public Map<String,Object> updateCategory(Category category,String initId){
        int status = catalogService.updateCategory(category,initId);
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        return map;
    }

    @PostMapping("categories")
    @ResponseBody
    public Map<String,Object> addCategory(Category category){
        int status = catalogService.insertCategory(category);
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        return map;
    }

    @GetMapping("categories/idList")
    @ResponseBody
    public List<String> getAllCategoryId(){
        return catalogService.getAllCategoryId();
    }

    @GetMapping("categories/idTree")
    @ResponseBody
    public List<Map<String,Object>> getAllCatIdTree(){
        return catalogService.getAllCatIdTree();
    }

    /*
        pn-pageNumber
        ps-pageSize
     */
    @GetMapping("products")
    @ResponseBody
    public Map<String,Object> getProductWithName(@RequestParam(required = false, defaultValue ="1",value = "pn")int pn,
                                                @RequestParam(required = false, defaultValue ="10",value = "ps")int ps,
                                                @RequestParam(required = false, value="name")String name){
        PageHelper.startPage(pn, ps);
        //startPage后紧跟的这个查询就是分页查询
        List<Product> products = catalogService.getProductWithName(name);

        Map<String,Object> map = new HashMap<>();

        int status = products == null || products.isEmpty() ? 0 : 1;
        PageInfo<Product> pageInfo = new PageInfo<>(products, 5);
        map.put("status",status);
        map.put("pageInfo",pageInfo);
        return map;
    }

    @PostMapping("products")
    @ResponseBody
    public Map<String,Object> addProduct(Product product,
                                         @RequestParam(required = false)MultipartFile file){
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

    @PutMapping("products")
    @ResponseBody
    public Map<String,Object> editProduct(Product product, String initId){
        int status = catalogService.updateProduct(product, initId);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status",status);
        return resultMap;
    }

    @GetMapping("products/idList")
    @ResponseBody
    public List<String> getAllProductId(){
//        Map<String,Object> map = new HashMap<>();
//        String pId = catalogService.getProductIdByItemId(itemId);
        List<String> idList = catalogService.getAllProductId();
//        map.put("pId",pId);
//        map.put("idList",idList);
        return idList;
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
//            pageInfo = null;
        }
        else{
            status =1;
        }
        pageInfo = new PageInfo<>(items, 5);
        map.put("status",status);
        map.put("pageInfo",pageInfo);
        return map;
    }

    @PatchMapping("items")
    @ResponseBody
    public Map<String,Object> updateItemStatus(String itemId, String status){
        Map<String,Object> map = new HashMap<>();
        int code = catalogService.updateItemStatus(itemId,status);
        map.put("status",code);
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

    @PutMapping("items")
    @ResponseBody
    public Map<String,Object> updateItem(Item item,
                                         String initId){
        Map<String,Object> map = new HashMap<>();
        int status = catalogService.updateItem(item, initId);
        map.put("status",status);
        return map;
    }

    @PostMapping("items")
    @ResponseBody
    public Map<String,Object> addItem(Item item){
        Map<String,Object> map = new HashMap<>();
        int status = catalogService.insertItem(item);
        map.put("status", status);
        return map;
    }

    @GetMapping("/help")
    public String help() {
        return "common/help";
    }
}
