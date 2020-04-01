package org.csu.mypetstore.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.csu.mypetstore.domain.VerifyCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@SessionAttributes("text")
public class UtilController {
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletResponse response, Model model) throws IOException {
        VerifyCode code = new VerifyCode();
        BufferedImage image = code.getImage();
        String text = code.getText();

        model.addAttribute("text",text);
        VerifyCode.output(image,response.getOutputStream());
    }
}
