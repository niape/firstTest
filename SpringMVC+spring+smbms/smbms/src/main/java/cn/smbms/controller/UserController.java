package cn.smbms.controller;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.role.RoleServiceImpl;
import cn.smbms.service.user.UserService;
import cn.smbms.service.user.UserServiceImpl;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = {"/login.html"})
    public String login(){
        return "login";
    }

    @RequestMapping(value = {"/doLogin.html"})
    public String doLogin(Model model,
                          @RequestParam("userCode") String userCode,
                          @RequestParam("userPassword") String userPassword,
                          HttpSession session){

        //调用service方法，进行用户匹配
        User user = userService.login(userCode);
        if(null != user){//有该用户
            if(!user.getUserPassword().equals(userPassword)){
                throw new RuntimeException("密码输入错误");
            }else{
            //放入session
            session.setAttribute(Constants.USER_SESSION, user);
            //页面跳转（frame.jsp）
            return "redirect:/user/main.html";}
        }else{
            //页面跳转（login.jsp）带出提示信息--转发
            /*model.addAttribute("error", "用户名或密码不正确");
            return "login";*/
            throw new RuntimeException("用户名不存在");
            //判断页面用户名是否正确

        }
    }

    @RequestMapping(value = "/main.html")
    public String main(HttpSession session){
        if (session.getAttribute(Constants.USER_SESSION)==null){
            return "redirect:/user/login.html";
        }
        return "frame";
    }

    @RequestMapping("/logout.html")
    public String logout(HttpSession session){
        session.removeAttribute(Constants.USER_SESSION);
        return "login";
    }

    //异常处理器
    /*@ExceptionHandler(value = {RuntimeException.class})
    public String handlerException(RuntimeException e,HttpServletRequest request){
        request.setAttribute("e",e);
        return "login";
    }*/

    //查询用户列表
    @RequestMapping(value = "/userlist.html",method = {RequestMethod.GET,RequestMethod.POST})
    public String query(@RequestParam(value = "queryname",defaultValue = "") String queryUserName,
                         @RequestParam(value = "queryUserRole",defaultValue = "0") Integer queryUserRole,
                         @RequestParam(value = "pageIndex",defaultValue = "1") Integer currentPageNo,
                         Model model) {

        List<User> userList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        /**
         * http://localhost:8090/SMBMS/userlist.do
         * ----queryUserName --NULL
         * http://localhost:8090/SMBMS/userlist.do?queryname=
         * --queryUserName ---""
         */
        System.out.println("queryUserName servlet--------"+queryUserName);
        System.out.println("queryUserRole servlet--------"+queryUserRole);
        System.out.println("query pageIndex--------- > " + currentPageNo);

        //总数量（表）
        int totalCount	= userService.getUserCount(queryUserName,queryUserRole);
        //总页数
        PageSupport pages=new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);

        int totalPageCount = pages.getTotalPageCount();

        //控制首页和尾页
        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }


        userList = userService.getUserList(queryUserName,queryUserRole,currentPageNo, pageSize);

        List<Role> roleList = null;
        roleList = roleService.getRoleList();

        model.addAttribute("userList", userList);
        model.addAttribute("roleList", roleList);
        model.addAttribute("queryUserName", queryUserName);
        model.addAttribute("queryUserRole", queryUserRole);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        return "userlist";
    }

    //添加用戶
    @RequestMapping("/useradd.html")
    public String add(@ModelAttribute("user") User user){
        return "useradd";
    }
    @RequestMapping("/useraddsave.html")
    public String addUserSave(@ModelAttribute("user") @Valid User user,
                              BindingResult result,
                              HttpSession session,
                              @RequestParam("a_idPicPath") MultipartFile[] multipartFiles,
                              Model model){

        //多文件上传的准备工作
        //创建错误集
        String[] errors = {"uploadFileError","uploadWorkFileError"};
        //创建保存至数据库的对象属性的匹配序列
        String[] attrs = {"idPicPath","workPicPath"};
        //创建文件的标志序列
        String[] symbols = {"_Personal.","_Work."};
        //多文件上传
        for (int i = 0; i < multipartFiles.length; i++) {
            MultipartFile multipartFile = multipartFiles[i];
            String error = errors[i];
            String symbol = symbols[i];
            String attr = attrs[i];
            //设置文件的保存路径
            String savePath = null;
            if (!multipartFile.isEmpty()) {
                //上传准备工作
                //获取文件的原名和大小
                String oldName = multipartFile.getOriginalFilename();
                //获取文件的后缀,对上传文件的类型进行限制
                String ext = FilenameUtils.getExtension(oldName);
                long size = multipartFile.getSize();
                //设置上传文件不能超过500K
                if (size > 500 * 1024) {
                    model.addAttribute(error, "上传图片不能超过500K");
                    return "useradd";
                } else {
                    String[] types = {"jpg", "jpeg", "png", "pneg", "gif"};
                    if (!Arrays.asList(types).contains(ext)) {
                        model.addAttribute(error, "上传的文件类型不满足要求,只能是jpg,jpeg,png,pneg,gif格式的图片");
                        return "useradd";
                    } else {
                        //开始正式上传文件
                        //获取要存储在服务器中的真实路径（使用File.separator代替 /， 实现低入侵）
                        String targetPath = session.getServletContext().getRealPath("static" + File.separator + "upload");
                        //修改上传文件名，避免同文件名的文件无法上传
                        String fileName = System.currentTimeMillis() + RandomUtils.nextInt(100000) + symbol + ext;
                        File saveFile = new File(targetPath, fileName);
                        if (!saveFile.exists()) {
                            saveFile.mkdirs();
                        }
                        try {
                            //真正的文件上传操作
                            multipartFile.transferTo(saveFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                            model.addAttribute(error, "上传失败，联系管理员！");
                            return "useradd";
                        }
                        savePath = targetPath + File.pathSeparator + fileName;
                        if("idPicPath".equals(attr))user.setIdPicPath(savePath);
                        if("workPicPath".equals(attr))user.setWorkPicPath(savePath);
                    }
                }
            }
        }


        /*//设置文件的保存路径
        String workSavePath = null;
        if(!multipartFile_work.isEmpty()){
            //上传准备工作
            //获取文件的原名和大小
            String oldName = multipartFile_work.getOriginalFilename();
            //获取文件的后缀,对上传文件的类型进行限制
            String ext = FilenameUtils.getExtension(oldName);
            long size = multipartFile_work.getSize();
            //设置上传文件不能超过500K
            if(size > 500*1024){
                model.addAttribute("uploadFileError","上传图片不能超过500K");
                return "useradd";
            }else{
                String[] types = {"jpg","jpeg","png","pneg","gif"};
                if(!Arrays.asList(types).contains(ext)){
                    model.addAttribute("uploadWorkFileError","上传的文件类型不满足要求,只能是jpg,jpeg,png,pneg,gif格式的图片");
                    return "useradd";
                }else{
                    //开始正式上传文件
                    //获取要存储在服务器中的真实路径（使用File.separator代替 /， 实现低入侵）
                    String targetPath = session.getServletContext().getRealPath("static"+ File.separator+"upload");
                    //修改上传文件名，避免同文件名的文件无法上传
                    String fileName = System.currentTimeMillis()+ RandomUtils.nextInt(100000)+"_Work."+ext;
                    File saveFile = new File(targetPath,fileName);
                    if(!saveFile.exists()){
                        saveFile.mkdirs();
                    }
                    try {
                        //真正的文件上传操作
                        multipartFile_work.transferTo(saveFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        model.addAttribute("uploadWorkFileError","上传失败，联系管理员！");
                        return "useradd";
                    }
                    workSavePath = targetPath +File.pathSeparator+ fileName;
                }
            }
        }*/

        if(result.hasErrors()){
            return "useradd";
        }
        user.setCreationDate(new Date());
        user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        /*user.setIdPicPath(savePath);
        user.setWorkPicPath(workSavePath);*/

        if(userService.add(user)){
            return "redirect:/user/userlist.html";
        }else{
            return "useradd";
        }
    }

    //查找所有的用户角色
    @RequestMapping("/rolelist.html")
    public void getRoleList(HttpServletResponse response){
        List<Role> roleList = null;
        roleList = roleService.getRoleList();
        //把roleList转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = null;
        try {
            outPrintWriter = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outPrintWriter.write(JSONArray.toJSONString(roleList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    //验证当前账号是否可用
    @RequestMapping("/ucexist.html")
    @ResponseBody
    public String userCodeExist(@RequestParam("userCode") String userCode){
        //判断用户账号是否可用

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(userCode)){
            //userCode == null || userCode.equals("")
            resultMap.put("userCode", "exist");
        }else{
            User user = userService.selectUserCodeExist(userCode);
            if(null != user){
                resultMap.put("userCode","exist");
            }else{
                resultMap.put("userCode", "notexist");
            }
        }

        return JSONArray.toJSONString(resultMap);
    }

    //查看用户信息
    @RequestMapping("/view/{uid}")
    public String view(@PathVariable("uid") String id,
                       Model model){

        if(!StringUtils.isNullOrEmpty(id)){
            //调用后台方法得到user对象
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            return "userview";
        }
        return "userlist";
    }
    //查看用户信息  使用ajax方式实现
    /**
     *  为避免响应的数据乱码，可使用produces 属性设置编码,（方法一）
     *      但是要注意value属性中要请求的地址的后缀不能是.html结尾，否则会报406问题
     *         (请求头为自己设置的值，而响应的值默认为text/html）
     *  避免出现数据乱码的第二种方式是： 在spring-servlet.xml中配置消息转换器中的字符编码格式为utf-8
     */
//    @RequestMapping(value = "/view",produces = {"application/json;charset=utf-8"})
    @RequestMapping(value = "/view")
    @ResponseBody
    public String view(@RequestParam(value = "uid",defaultValue = "") String id){

        if(!StringUtils.isNullOrEmpty(id)){
            //调用后台方法得到user对象
            User user = userService.getUserById(id);
            return JSON.toJSONString(user);
        }else{
            return "null";
        }
    }

    //修改用户
    @RequestMapping("/tomodify.html")
    public String toModify(@RequestParam("uid") String id,
                           Model model){
        if(!StringUtils.isNullOrEmpty(id)){
            //调用后台方法得到user对象
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            return "usermodify";
        }else{
            throw new RuntimeException("数据不存在");
        }
    }
    @RequestMapping("/modifysave.html")
    public String modify(@ModelAttribute("user") User user,
                         HttpSession session){

        user.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        user.setModifyDate(new Date());

        if(userService.modify(user)){
            return "redirect:/user/userlist.html";
        }else{
            return "usermodify";
        }
    }

    //删除用户
    @RequestMapping("/deluser.html")
    public void deleteUser(@RequestParam(value = "uid",defaultValue = "0") Integer id,
                           HttpServletResponse response){

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(id <= 0){
            resultMap.put("delResult", "notexist");
        }else{
            if(userService.deleteUserById(id)){
                resultMap.put("delResult", "true");
            }else{
                resultMap.put("delResult", "false");
            }
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
