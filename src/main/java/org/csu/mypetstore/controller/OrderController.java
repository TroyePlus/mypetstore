package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;


@RequestMapping("/order/")
@Controller
@SessionAttributes({"cart","order"})
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CatalogService catalogService;

    @GetMapping("viewOrder")
    public String viewOrder(Model model,HttpSession session){
        Order order = (Order) model.getAttribute("order");
        String view, msg;

        if (order != null) {
            orderService.insertOrder(order);
            model.addAttribute("cart",null);

            msg = "Thank you, your order has been submitted.";
            //更改库存
            Account account = (Account) session.getAttribute("account");
            List<LineItem> lineItems = order.getLineItems();
            for (LineItem l:lineItems) {
                int stockQuantity = catalogService.getStockQuantity(l.getItemId());
                if(stockQuantity >= l.getQuantity()){
                    catalogService.updateStockQuantity(l.getItemId(),stockQuantity-l.getQuantity());
                }
                else{

                }
            }

           view = "order/ViewOrder";
        } else {
            msg = "An error occurred processing your order (order was null).";
            view = "common/Error";
        }
        model.addAttribute("message",msg);
        return view;
    }

    @GetMapping("viewListOrder")
    public String viewListOrder(Model model,HttpSession session){
        Account account = (Account) session.getAttribute("account");
        if(account!=null) {
            List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());
            model.addAttribute("orderList",orderList);
        }
        return "order/ListOrders";

    }

    @GetMapping("newOrder")
    public String newOrderForm(Model model, HttpSession session){
        Account account = (Account)session.getAttribute("account");
        Cart cart = (Cart)model.getAttribute("cart");
        String view;

        if (account == null){
           model.addAttribute("message", "You must sign on before attempting to check out.  Please sign on and try checking out again.");
           view = "account/SignonForm";
        } else if(cart != null){
            Order order = new Order();
            order.initOrder(account, cart);
            model.addAttribute("order", order);
            //库存判断
            List<LineItem> lineItems = order.getLineItems();
            for (LineItem l:lineItems
                 ) {
                int stockQuantity = catalogService.getStockQuantity(l.getItemId());
                if(stockQuantity < l.getQuantity()){

                    model.addAttribute("stockMessage","库存不够！");
                    view = "cart/Cart";
                    return view;
                }
            }
            model.addAttribute("stockMessage",null);

            view = "order/NewOrderForm";
        }else{
            model.addAttribute("message", "An order could not be created because a cart could not be found.");
            view = "common/Error";
        }
        return view;
    }

    @PostMapping("confirmOrder")
    public String confirmOrder(@RequestParam Map<String,Object> params, HttpServletRequest request, Model model){
        String shippingAddressRequired = request.getParameter("shippingAddressRequired");
        String view;
        Order order = (Order)model.getAttribute("order");
        order.setCardType((String)params.get("cardType"));
        order.setCreditCard((String)params.get("creditCard"));
        order.setExpiryDate((String)params.get("expiryDate"));

        if (shippingAddressRequired == null){
            view = "order/ConfirmOrder";
        }
        else{
            view = "order/ShippingForm";
        }
        return view;
    }

    @PostMapping("shippingAddress")
    public String updateAddress(@RequestParam Map<String,Object> params, Model model ,HttpSession session){
        Order order = (Order) model.getAttribute("order");
        Account account = (Account) session.getAttribute("account");
        Cart cart = (Cart)model.getAttribute("cart");

        order.initOrder(account,cart);

        if(true) {
            order.setShipToFirstName((String) params.get("shipToFirstName"));
            order.setShipToLastName((String) params.get("shipToLastName"));
            order.setShipAddress1((String) params.get("shipAddress1"));
            order.setShipAddress2((String) params.get("shipAddress2"));
            order.setShipCity((String) params.get("shipCity"));
            order.setShipState((String) params.get("shipState"));
            order.setShipZip((String) params.get("shipZip"));
            order.setShipCountry((String) params.get("shipCountry"));
        }

        model.addAttribute("order",order);
        return "order/ConfirmOrder";
    }

    @DeleteMapping("deleteOrder")
    public void deleteOrder(@RequestParam int orderId){
        orderService.deleteOrder(orderId);
    }

    @PostMapping("delivery")
    public void updateOrderStatus(@RequestParam int orderId,String status,Date deliveryTime ,String courierName){
        orderService.updateOrderStatus(orderId,status);

        Delivery delivery = new Delivery(orderId,deliveryTime,courierName);
        orderService.insertDelivery(delivery);



    }

    @PostMapping("updateLineItemCount")
    @ResponseBody
    public Map<String,Integer> updateLineItemCount(@RequestParam Integer lineNumber,Integer count,int orderId,Model model){
        Order order = orderService.getOrder(orderId);
        List<LineItem> lineItemList = order.getLineItems();
        Map<String,Integer> map = new HashMap<>();
        int stockQuantity = 0;

        int totalPrice = 0;
        int totalCount = 0;

        for (LineItem l:lineItemList
        ) {
            BigDecimal unitPrice = l.getUnitPrice();
            int quantity = (l.getLineNumber()==lineNumber)?count:l.getQuantity();
            totalPrice = totalPrice + unitPrice.intValue() * quantity;
            totalCount += quantity;

            if(l.getLineNumber()==lineNumber){
                stockQuantity = l.getItem().getStockQuantity()+l.getQuantity()-count;
                System.out.println("stock来咯" + l.getItemId() + "继续 " + stockQuantity);
                catalogService.updateStockQuantity(l.getItemId(),stockQuantity);
            }

        }

        String stock = String.valueOf(stockQuantity);
        orderService.updateLineItemCount(lineNumber,count);
        map.put("totalPrice",totalPrice);
        map.put("totalCount",totalCount);
        map.put("stockQuantity",stockQuantity);
        orderService.updateTotalPrice(orderId,totalPrice);

        return map;


    }


}