package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/catalog/")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping("index")
    public String view(){
        return "catalog/main";
    }

//    viewCatalog
//    viewCategory

    @GetMapping("viewCategory")
    public String viewCategory(String categoryId, Model model){
        if (categoryId!=null){
            Category category = catalogService.getCategory(categoryId);
            List<Product> productList = catalogService.getProductListByCategory(categoryId);
            model.addAttribute("category",category);
            model.addAttribute("productList",productList);
        }
        return "catalog/Category";
    }

    @GetMapping("viewProduct")
    public String viewProduct(String productId, Model model){
        if(productId!=null){
            List<Item> itemList= catalogService.getItemListByProduct(productId);
            Product product = catalogService.getProduct(productId);
            model.addAttribute("itemList",itemList);
            model.addAttribute("product",product);
        }
        return "catalog/Product";
    }

    @GetMapping("viewItem")
    public String viewItem(String itemId, Model model){
        if(itemId!=null){
            Item item = catalogService.getItem(itemId);
            Product product = item.getProduct();
            processProductDescription(product);
            model.addAttribute("item",item);
            model.addAttribute("product",product);
        }
        return "catalog/Item";
    }

    @PostMapping("search")
    public String searchProducts(String keyword, Model model){
        if(keyword==null || keyword.length()<1){
            String msg = "Please enter a keyword!";
            model.addAttribute("msg",msg);
            return "common/Error";
        }
        else {
            List<Product> productList = catalogService.searchProductList(keyword);
            processProductDescription(productList);
            model.addAttribute("productList", productList);
            return "catalog/SearchProducts";
        }
    }

    public static void processProductDescription(Product product){
        String [] temp = product.getDescription().split("\"");
        product.setDescriptionImage(temp[1]);
        product.setDescriptionText(temp[2].substring(1));
    }
    public static void processProductDescription(List<Product> productList){
        for(Product product : productList) {
            processProductDescription(product);
        }
    }
}
