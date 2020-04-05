package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@RequestMapping("/order/")
@Controller
@SessionAttributes({"cart","order"})
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("viewOrder")
    public String viewOrder(Model model){
        Order order = (Order) model.getAttribute("order");
        String view, msg;

        if (order != null) {
            orderService.insertOrder(order);
            model.addAttribute("cart",null);

            msg = "Thank you, your order has been submitted.";
           view = "order/ViewOrder";
        } else {
            msg = "An error occurred processing your order (order was null).";
            view = "common/Error";
        }
        model.addAttribute("message",msg);
        return view;
    }

    @GetMapping("viewListOrder")
    public String viewListOrder(Model model){
        Account account = (Account) model.getAttribute("loginAccount");
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
            //order.initOrder(account, cart);
            model.addAttribute("order", order);
            view = "order/NewOrderForm";
        }else{
            model.addAttribute("message", "An order could not be created because a cart could not be found.");
            view = "common/Error";
        }
        return view;
    }

    @PostMapping("confirmOrder")
    public String confirmOrder(HttpServletRequest request, Model model){
        String shippingAddressRequired = request.getParameter("shippingAddressRequired");
        String view;

        if (shippingAddressRequired == null){
            view = "order/ConfirmOrder";
        }
        else{
            view = "order/ShippingForm";
        }
        return view;
    }

    @PostMapping("shippingAddress")
    public String updateAddress(Map<String,Object> params, Model model){
        Order order = (Order) model.getAttribute("order");
        if(order!=null) {
            order.setShipToFirstName((String) params.get("shipToFirstName"));
            order.setShipToLastName((String) params.get("shipToLastName"));
            order.setShipAddress1((String) params.get("shipAddress1"));
            order.setShipAddress2((String) params.get("shipAddress2"));
            order.setShipCity((String) params.get("shipCity"));
            order.setShipState((String) params.get("shipState"));
            order.setShipZip((String) params.get("shipZip"));
            order.setShipCountry((String) params.get("shipCountry"));
        }
        return "order/ConfirmOrder";
    }
}