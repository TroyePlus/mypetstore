package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.domain.VerifyCode;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class UtilController {
    @Autowired
    private CatalogService catalogService;

    //验证码
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        VerifyCode code = new VerifyCode();
        BufferedImage image = code.getImage();
        String text = code.getText();

        request.getSession().setAttribute("text",text);
        VerifyCode.output(image,response.getOutputStream());
    }

    //自动补全
    @GetMapping("/findResult")
    public void findResult(@RequestParam("keyword")String keyword, HttpServletResponse response) throws IOException {
        //向server层调用相应的业务
        List<Product> productList = catalogService.searchProductList(keyword);

        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();

        //返回结果
        StringBuilder res = new StringBuilder();
        for(int i=0; i<productList.size(); i++){
            if(i>0){
                res.append(",").append(productList.get(i));
            }else{
                res.append(productList.get(i));
            }
        }
        out.write(res.toString());

        out.flush();
        out.close();
    }

    @GetMapping("/help")
    public String help(){
        return "common/help";
    }
}
