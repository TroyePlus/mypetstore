package org.csu.mypetstore;

import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import java.awt.*;
import java.util.Calendar;
import java.util.List;

@SpringBootTest
@MapperScan("org.csu.mypetstore.persistence")
class MypetstoreApplicationTests {

    @Autowired
    CartService cartService;

    @Autowired
    CatalogService catalogService;

    @Autowired
    AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testCart(){
//        cartService.insertItem("NROwind","EST-1",5,true);

//        List<CartItem> list = cartService.getCartItems("j2ee");
//        for (CartItem c:list
//             ) {
//            System.out.println(c.toString());
//
//        }

//        cartService.updataQuantity("NROwind","EST-5",2);

//        cartService.removeItem("NROwind","EST-5");

        cartService.updataInStock("NROwind","EST-1",false);

    }

    @Test
    public void testAuto(){

        List<String> list = catalogService.getProductName("a");

        for (String l :list
             ) {
            System.out.println(l);
        }

    }

    @Test
    public void testSecurity(){
        System.out.println(passwordEncoder.encode("j2ee"));
    }

    @Test
    public void testItem(){
//        System.out.println(catalogService.getStockQuantity("EST-1"));

        catalogService.updateStockQuantity("EST-1",2);
    }

    @Test
    public void testPhone(){
        String phone = "18374999116";
        System.out.println(accountService.getUsername("18374999116"));
    }
}
