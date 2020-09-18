package cn.smbms.controller;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.bill.BillService;
import cn.smbms.service.bill.BillServiceImpl;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.service.provider.ProviderServiceImpl;
import cn.smbms.tools.Constants;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/bill")
public class BillController {

    @Autowired
    ProviderService providerService;

    @Autowired
    BillService billService;

    @RequestMapping(value = "/billlist.html")
    public String query(@RequestParam(value = "queryProductName",defaultValue = "") String queryProductName,
                        @RequestParam(value = "queryProviderId",defaultValue = "0") Integer queryProviderId,
                        @RequestParam(value = "queryIsPayment",defaultValue = "0") Integer queryIsPayment,
                        Model model){

        List<Provider> providerList = new ArrayList<Provider>();
        providerList = providerService.getProviderList("","");

        List<Bill> billList = new ArrayList<Bill>();
        Bill bill = new Bill();

        bill.setProductName(queryProductName);
        bill.setProviderId(queryProviderId);
        bill.setIsPayment(queryIsPayment);

        billList = billService.getBillList(bill);

        model.addAttribute("providerList", providerList);
        model.addAttribute("billList", billList);
        model.addAttribute("queryProductName", queryProductName);
        model.addAttribute("queryProviderId", queryProviderId);
        model.addAttribute("queryIsPayment", queryIsPayment);
        return "billlist";
    }

    @RequestMapping("/billadd.html")
    public String addBill(@ModelAttribute("bill") Bill bill){
        return "billadd";
    }
    @RequestMapping("/billaddsave.html")
    public String addBillSave(@ModelAttribute("bill") @Valid Bill bill,
                              BindingResult result,
                              HttpSession session){
        if(result.hasErrors()){
            return "billadd";
        }
        bill.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        bill.setCreationDate(new Date());
        boolean flag = false;
        flag = billService.add(bill);
        System.out.println("add flag -- > " + flag);
        if(flag){
            return "redirect:/bill/billlist.html";
        }else{
            return "billadd";
        }
    }

    //获取供应商列表
    @RequestMapping("/providerlist.html")
    public void getProviderList(HttpServletResponse response){
        List<Provider> providerList = new ArrayList<Provider>();
        providerList = providerService.getProviderList("","");
        //把providerList转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = null;
        try {
            outPrintWriter = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outPrintWriter.write(JSONArray.toJSONString(providerList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    @RequestMapping("/view.html/{id}")
    public String view(@PathVariable String id,
                           Model model){
        if(!StringUtils.isNullOrEmpty(id)){
            Bill bill = null;
            bill = billService.getBillById(id);
            model.addAttribute("bill", bill);
            return "billview";
        }else {
            throw new RuntimeException("数据不可查");
        }
    }

    //修改订单信息
    @RequestMapping("/tomodify.html/{id}")
    public String toModify(@PathVariable String id,
                           Model model){
        if(!StringUtils.isNullOrEmpty(id)){
            Bill bill = null;
            bill = billService.getBillById(id);
            model.addAttribute("bill", bill);
            return "billadd";
        }else {
            throw new RuntimeException("数据不可查");
        }
    }
    @RequestMapping("/modifysave.html")
    public String modifySava(@ModelAttribute("bill") Bill bill,
                             HttpSession sessiom
                             ){

        bill.setModifyBy(((User)sessiom.getAttribute(Constants.USER_SESSION)).getId());
        bill.setModifyDate(new Date());
        boolean flag = false;
        flag = billService.modify(bill);
        if(flag){
            return "redirect:/bill/billlist.html";
        }else{
            return "billmodify";
        }
    }

    //删除订单
    @RequestMapping("/delbill.html")
    public void deleteBill(@RequestParam("billid") String id,
                             HttpServletResponse response){
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(!StringUtils.isNullOrEmpty(id)){
            boolean flag = billService.deleteBillById(id);
            if(flag){//删除成功
                resultMap.put("delResult", "true");
            }else{//删除失败
                resultMap.put("delResult", "false");
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
