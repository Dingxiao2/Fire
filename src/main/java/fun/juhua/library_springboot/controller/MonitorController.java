package fun.juhua.library_springboot.controller;

import fun.juhua.library_springboot.entity.Fire;
import fun.juhua.library_springboot.entity.Monitor;
import fun.juhua.library_springboot.service.FireService;
import fun.juhua.library_springboot.service.MonitorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MonitorController {
    @Resource
    MonitorService monitorServiceImpl;
    @Resource
    FireService fireService;

    @RequestMapping("/monitor")
    public String toIndex(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "monitor/index";
    }

    @RequestMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @RequestMapping("/RegisterMonitor")
    @ResponseBody
    public String registerMonitor(Monitor monitor) {
        int state = 0;
        String msg = "用户名已存在！";
        if (!monitorServiceImpl.haveMonitor(monitor.getId())) {
            state = monitorServiceImpl.addMonitor(monitor);
            if (state == 1) {
                msg = "注册成功！";//注册成功！
            } else {
                msg = "请重试！";//请重试！
            }
        }
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }

    @RequestMapping("/admin/toMonitorList")
    public String toMonitorList(String name, String id, Model model) {
        List<Monitor> monitorList = monitorServiceImpl.getMonitorList(name, id);
        model.addAttribute("monitorList", monitorList);
        return "admin/monitorList";
    }

    @RequestMapping("/monitor/toFireshelf")
    public String toFireshelf(String fireName, String fireID, HttpSession session, Model model) {
        List<Fire> fireList = fireService.getFireList(fireName, fireID);
        model.addAttribute("fireList", fireList);
        Boolean allow = (Boolean) session.getAttribute("allow");
        model.addAttribute("allow", allow);
        return "monitor/fireList";
    }

    @RequestMapping("/UpdateMonitor")
    @ResponseBody
    public String updateMonitor(String id, String name, String password, String oldPassword, String gender, String telephone, String email) {
        Monitor monitor = new Monitor(id, name, password == "" ? oldPassword : password, gender, telephone, email);
        int state = monitorServiceImpl.updateMonitor(monitor);
        String msg = "请重试";
        if (state == 1) {
            msg = "更新成功!";
        }
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }

    @RequestMapping("/toEditMonitor")
    public String toEditMonitor(HttpSession session, Model model) {
        Monitor monitor = (Monitor) session.getAttribute("user");
        model.addAttribute("user", monitor);
        return "monitor/edit";
    }

    @RequestMapping("/admin/toEditMonitor")
    public String toEditMonitor(String id, Model model) {
        Monitor monitor = monitorServiceImpl.findMonitor(id);
        model.addAttribute("user", monitor);
        return "admin/editMonitor";
    }

    @RequestMapping("/admin/DeleteMonitor")
    public String deleteMonitor(String id) {
        int raw = monitorServiceImpl.deleteAdminById(id);
        return "redirect:/admin/";
    }

}
