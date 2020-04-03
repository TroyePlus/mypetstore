package org.csu.mypetstore;

import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.service.CartService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import java.util.Calendar;
import java.util.List;

@SpringBootTest
@MapperScan("org.csu.mypetstore.persistence")
class MypetstoreApplicationTests {

    @Autowired
    CartService cartService;

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

}
