package cn.smbms.controller;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.service.provider.ProviderServiceImpl;
import cn.smbms.tools.Constants;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @RequestMapping("/providerlist.html")
    public String providerList(@RequestParam(value = "queryProName",defaultValue = "") String queryProName,
                             @RequestParam(value = "queryProCode",defaultValue = "") String queryProCode,
                             Model model){

        List<Provider> providerList = new ArrayList<Provider>();
        providerList = providerService.getProviderList(queryProName,queryProCode);

        model.addAttribute("providerList", providerList);
        model.addAttribute("queryProName", queryProName);
        model.addAttribute("queryProCode", queryProCode);
        return "providerlist";
    }

    @RequestMapping("/provideradd.html")
    public String addProvider(@ModelAttribute("provider") Provider provider){
        return "provideradd";
    }

    @RequestMapping(value = "/provideraddsave.html",method = {RequestMethod.GET,RequestMethod.POST})
    public String addProviderSave(@ModelAttribute("provider") @Valid Provider provider,
                                  BindingResult result,
                                  HttpSession session
                                  ){
        if(result.hasErrors()){
            return "provideradd";
        }
        provider.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        provider.setCreationDate(new Date());
        boolean flag = false;
        flag = providerService.add(provider);
        if(flag){
            return "redirect:/provider/providerlist.html";
        }else{
            return "provideradd";
        }
    }


    @RequestMapping("/view.html/{id}")
    public String view(@PathVariable String id,
                           Model model){
        if(!StringUtils.isNullOrEmpty(id)){
            Provider provider = null;
            provider = providerService.getProviderById(id);
            model.addAttribute("provider", provider);
            return "providerview";
        }else{
            throw new RuntimeException("数据不可用");
        }
    }


    @RequestMapping("/tomodify.html")
    public String toModify(@RequestParam("proid") String id,
                           Model model){
        if(!StringUtils.isNullOrEmpty(id)){
            Provider provider = null;
            provider = providerService.getProviderById(id);
            model.addAttribute("provider", provider);
            return "providermodify";
        }else{
            throw new RuntimeException("数据不可用");
        }

    }
    @RequestMapping("/modifysave.html")
    public String modify(@ModelAttribute("provider") Provider provider,
                         HttpSession session){

        provider.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        provider.setModifyDate(new Date());
        boolean flag = false;
        flag = providerService.modify(provider);
        if(flag){
            return "redirect:/provider/providerlist.html";
        }else{
            return "providermodify";
        }
    }

    @RequestMapping("/delprovider.html")
    public void deleteProvider(@RequestParam(value = "proid") String id,
                               HttpServletResponse response){
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(!StringUtils.isNullOrEmpty(id)){
            int flag = providerService.deleteProviderById(id);
            if(flag == 0){//删除成功
                resultMap.put("delResult", "true");
            }else if(flag == -1){//删除失败
                resultMap.put("delResult", "false");
            }else if(flag > 0){//该供应商下有订单，不能删除，返回订单数
                resultMap.put("delResult", String.valueOf(flag));
            }
        }else{
            resultMap.put("delResult", "notexit");
        }
        //把resultMap转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = null;
        try {
            outPrintWriter = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

}
