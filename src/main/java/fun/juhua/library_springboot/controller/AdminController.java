package fun.juhua.library_springboot.controller;

import fun.juhua.library_springboot.entity.Admin;
import fun.juhua.library_springboot.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
// @RestController渲染成json格式返回给前台
public class AdminController {
    @Resource
    AdminService adminServiceImpl;

    @RequestMapping("/toAdminList")
    public String toAdminList(String name, Model model) {
        List<Admin> adminList = adminServiceImpl.getAdminList(name);
        model.addAttribute("adminList", adminList);
        return "admin/adminList";
    }

    @RequestMapping("/DeleteAdmin")
    public String deleteAdmin(String id) {
        int raw = adminServiceImpl.deleteAdminById(id);
        return "redirect:/admin/";
    }


    @RequestMapping("/")
    public String toIndex(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "admin/index";
    }

    @RequestMapping("/toAddMonitor")
    public String toAddMonitor() {
        return "redirect:/toRegister";
    }

    @RequestMapping("/toEditAdmin")
    public String toEditAdmin(String id, HttpSession session, Model model) {
        Admin admin = null;
        // 如果ID为空或null，则从session中获取当前用户信息
        if (id == "" || id == null || id.equals("")) {
            admin = (Admin) session.getAttribute("user");
        } else {
            // 否则，根据ID查询管理员信息
            admin = adminServiceImpl.findAdmin(id);
        }
        // 将查询到的管理员信息添加到模型中，以便在页面中使用
        model.addAttribute("editUser", admin);
        // 返回编辑管理员页面的视图名
        return "admin/editAdmin";
    }

    @RequestMapping("/UpdateAdmin")
    @ResponseBody
    public String updateAdmin(String id, String name, String password, String oldPassword, String gender, String telephone, String email) {
        // 根据传递的参数创建Admin对象，如果新密码为空则使用旧密码
        Admin admin = new Admin(id, name, password == "" ? oldPassword : password, gender, telephone, email);
        // 调用服务层方法更新管理员信息，并获取更新操作的状态
        int state = adminServiceImpl.updateAdmin(admin);
        // 默认更新失败的消息
        String msg = "更新失败，请重试";
        // 根据更新操作的状态修改消息内容
        if (state == 1) {
            msg = "更新成功!";
        }
        // 构造JSON格式的响应，包含更新操作的状态码和消息
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }

    @RequestMapping("/toAddAdmin")
    public String toAddAdmin() {
        return "admin/addAdmin";
    }

    @RequestMapping("/RegisterAdmin")
    @ResponseBody
    public String registerMonitor(Admin admin) {
        int state = 0;// 初始状态为0，表示注册失败
        String msg = "用户名已存在！";// 默认消息为用户名已存在
        // 检查是否存在相同用户名的管理员
        if (!adminServiceImpl.haveAdmin(admin.getId())) {
            // 如果不存在相同用户名的管理员，尝试添加管理员
            state = adminServiceImpl.addAdmin(admin);
            // 根据添加状态修改消息内容
            if (state == 1) {
                msg = "注册成功！";//注册成功！
            } else {
                msg = "请重试！";//请重试！
            }
        }
        // 构造JSON格式的响应，包含状态码和消息
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }

}