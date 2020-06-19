package org.csu.mypetstore.controller;

import com.github.pagehelper.PageInfo;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Administrator;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/backstage/account/")
public class BackstageAccountController {

    @Autowired
    private AccountService accountService;
    private AdministratorService administratorService;

    @GetMapping("userList")
    public String userList() {
        return "backstage/account/user_list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public Map list(@RequestParam(value = "page") Integer page, @RequestParam(value = "limit") Integer limit,Account account) {
        HashMap<String, Object> map = new HashMap<>();
        PageInfo pageInfo = accountService.getList(page,limit,account);
        map.put("code", "0");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    @RequestMapping(value = "/toAdd")
    public String toAdd() {
        return "backstage/account/user_add";
    }

    @RequestMapping(value = "/toEdit")
    public String toEdit() {
        return "backstage/account/user_edit";
    }

    @RequestMapping(value = "addMember")
    @ResponseBody
    public Map addMember(Account account){
        HashMap<String, Object> map = new HashMap<>();
        try {
            account.setPassword("1111111");//默认密码 是 111111
            accountService.insertAccount(account);
            map.put("success",true);
            map.put("msg","新增成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","新增失败！");
        }
        return map;
    }


    @RequestMapping(value = "updateMember")
    @ResponseBody
    public Map updateMember(Account account){
        HashMap<String, Object> map = new HashMap<>();
        try {
            accountService.updateAccount(account);
            map.put("success",true);
            map.put("msg","更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","更新失败！");
        }
        return map;
    }

    @RequestMapping(value = "updateStatus")
    @ResponseBody
    public Map updateStatus(Account account){
        HashMap<String, Object> map = new HashMap<>();
        try {
            accountService.updateAccountStatusByUserName(account);
            map.put("success",true);
            map.put("msg","更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","更新失败！");
        }
        return map;
    }

    @RequestMapping(value = "deleteAccount")
    @ResponseBody
    public Map deleteAccount(Account account){
        HashMap<String, Object> map = new HashMap<>();
        try {
            accountService.deleteAccount(account);
            map.put("success",true);
            map.put("msg","删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","删除失败！");
        }
        return map;
    }


    //admin操作
    @RequestMapping(value = "listAdmin")
    @ResponseBody
    public Map list(@RequestParam(value = "page") Integer page, @RequestParam(value = "limit") Integer limit, Administrator administrator) {
        HashMap<String, Object> map = new HashMap<>();
        PageInfo pageInfo = administratorService.getList(page,limit,administrator);
        map.put("code", "0");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    @RequestMapping(value = "/toAddAdmin")
    public String toAddAdmin() {
        return "backstage/account/admin_add";
    }

    @RequestMapping(value = "/toEditAdmin")
    public String toEditAdmin() {
        return "backstage/account/admin_edit";
    }

    @RequestMapping(value = "addAdmin")
    @ResponseBody
    public Map addAdmin(Administrator administrator){
        HashMap<String, Object> map = new HashMap<>();
        try {
            administrator.setPassword("1111111");//默认密码 是 111111
            administratorService.insertAdministrator(administrator);
            map.put("success",true);
            map.put("msg","新增成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","新增失败！");
        }
        return map;
    }


    @RequestMapping(value = "updateAdmin")
    @ResponseBody
    public Map updateAdmin(Administrator administrator){
        HashMap<String, Object> map = new HashMap<>();
        try {
            administratorService.updateAdministrator(administrator);
            map.put("success",true);
            map.put("msg","更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","更新失败！");
        }
        return map;
    }


    @RequestMapping(value = "deleteAdmin")
    @ResponseBody
    public Map deleteAdmin(Administrator administrator){
        HashMap<String, Object> map = new HashMap<>();
        try {
            administratorService.deleteAdministrator(administrator);
            map.put("success",true);
            map.put("msg","删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","删除失败！");
        }
        return map;
    }

}
