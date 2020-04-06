package org.csu.mypetstore.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.HtmlEscapingAwareTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart/")
@SessionAttributes({"cart","account"})
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CatalogService catalogService;

    @GetMapping("viewCart")
    public String viewCart(Model model){
        Cart cart = (Cart) model.getAttribute("cart");
        Account account = (Account) model.getAttribute("account");
        if(cart==null){
            if(account!=null){
                cart = new Cart();
                initCart(cart,account.getUsername());
                model.addAttribute("cart",cart);
                return "cart/Cart";
            }
            else
                return "account/SignonForm";
        }
        else
            return "cart/Cart";
    }

    @GetMapping("addItemToCart")
    public String addItemToCart(String workingItemId, Model model,HttpSession session){
        Cart cart = (Cart) model.getAttribute("cart");
        Account account = (Account) session.getAttribute("account");

        if(account==null){
            String msg ="You must sign on before attempting to check out. Please sign on and try checking out again.";
            model.addAttribute("signMessage",msg);
            return "account/SignonForm";
        }
        else {
            if (cart==null) {
                cart = new Cart();
                initCart(cart, account.getUsername());
            }


            if(cart.containsItemId(workingItemId))
            {
                cart.incrementQuantityByItemId(workingItemId);
                CartItem cartItem = cart.getCartItemById(workingItemId);
                cartService.updataQuantity(account.getUsername(),workingItemId,cartItem.getQuantity());
            }
            else{
                boolean isInStock = catalogService.isItemInStock(workingItemId);
                Item item = catalogService.getItem(workingItemId);
                cart.addItem(item,isInStock);
                cart.setQuantityByItemId(workingItemId,1);
                cartService.insertItem(account.getUsername(),workingItemId,1,isInStock);
            }
            model.addAttribute("cart",cart);
            return "cart/Cart";
        }
    }

    @GetMapping("removeItemFromCart")
    public String removeItemFromCart(String workingItemId, Model model,HttpSession session){
        Cart cart = (Cart) model.getAttribute("cart");

        Item item = cart.removeItemById(workingItemId);

        Account account = (Account)session.getAttribute("account");
        String userId = account.getUsername();
        String itemId = item.getItemId();
        cartService.removeItem(userId,itemId);

        model.addAttribute("cart",cart);
        if(item == null){
            model.addAttribute("msg", "Attempted to remove null CartItem from Cart.");
            return "common/Error";
        }else{
            return "cart/Cart";
        }
    }

    @GetMapping("updateCart")
    public void updateCart(String itemId, @RequestParam("number")int quantity, HttpServletRequest request, HttpServletResponse response){
        HttpSession session  = request.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        Account account = (Account)session.getAttribute("account");
        CartItem cartItem = cart.getCartItemById(itemId);

        cartService.updataQuantity(account.getUsername(),itemId,quantity);
        cartItem.setQuantity(quantity);

        request.getSession().setAttribute("cart",cart);

        String str="{\"total\":"+cartItem.getTotal()+",";
        str += "\"subTotal\":"+cart.getSubTotal()+"}";

        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        try{
            PrintWriter out = response.getWriter();

            out.write(str);
            out.flush();
            out.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("viewCartChart")
    public String viewCartChart(){
        return "cart/CartChart";
    }

    @PostMapping("viewChartInfo")
    public void viewChartInfo(HttpServletResponse response,Model model) throws IOException {
        Cart cart = (Cart)model.getAttribute("cart");
        List<CartItem> cartItemList = cart.getCartItemList();

        List<Label> list = new ArrayList<>();

        for (CartItem c:cartItemList
        ) {
            Item item = c.getItem();
            Product product = item.getProduct();
            String name = product.getCategoryId();

            int flag = 0;
            for (Label l:list
            ) {
                if(l.getLabel().equals(name)){
                    l.setCount(l.getCount()+c.getQuantity());
                    flag = 1;
                    break;
                }

            }

            if(flag == 0){
                Label label = new Label();
                label.setLabel(name);
                label.setCount(c.getQuantity());
                list.add(label);
            }

        }

        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.parseArray(JSON.toJSONString(list));

        PrintWriter out  =response.getWriter();

        System.out.println(jsonArray.toJSONString());
        out.write(jsonArray.toJSONString());
        out.flush();
        out.close();
    }

    //初始化购物车
    private void initCart(Cart cart, String username){
        List<CartItem> cartItemList  =cartService.getCartItems(username);

        for (CartItem c:cartItemList
        ) {
            Item item = catalogService.getItem(c.getItem().getItemId());
            cart.addItem(item,c.isInStock());
            cart.setQuantityByItemId(c.getItem().getItemId(),c.getQuantity());
        }
    }
}
