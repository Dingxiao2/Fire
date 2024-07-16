package fun.juhua.library_springboot.controller;

import fun.juhua.library_springboot.entity.Monitor;
import fun.juhua.library_springboot.service.AdminService;
import fun.juhua.library_springboot.service.ClearService;
import fun.juhua.library_springboot.service.MonitorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @Resource
    AdminService adminServiceImpl;
    @Resource
    MonitorService monitorServiceImpl;
    @Resource
    ClearService clearServiceImpl;

    @RequestMapping("/welcome")
    public String toWelcome() {
        return "welcome";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/Login")
    public String login(String id, String password, HttpSession session, Model model) {
        String checkAdmin = id.substring(0, 1);
        boolean state = false;
        if (checkAdmin.equals("m")) {
            state = adminServiceImpl.login(id, password);
            if (state) {
                session.setAttribute("role", "admin");
                session.setAttribute("user", adminServiceImpl.findAdmin(id));
                model.addAttribute("user", adminServiceImpl.findAdmin(id));
                return "redirect:/admin/";
            }
        } else if (checkAdmin.equals("r")) {
            state = monitorServiceImpl.login(id, password);
            if (state) {
                session.setAttribute("role", "monitor");
                boolean allow = clearServiceImpl.check(id) == 1 ? true : false;
                session.setAttribute("allow", allow);
                Monitor monitor = monitorServiceImpl.findMonitor(id);
                System.out.println("IndexController -> login(49)monitor: " + monitor);
                session.setAttribute("user", monitor);
                model.addAttribute("user", monitorServiceImpl.findMonitor(id));
                return "redirect:/monitor/";
            }
        }
        model.addAttribute("errorMsg", "用户名或密码错误");
        return "redirect:/toLogin";
    }

    @RequestMapping("/Logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/toIndex";
    }

    @RequestMapping("/toIndex")
    public String toIndex(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        Object user = session.getAttribute("user");
        if (role != null && user != null && role.equals("admin")) {
            model.addAttribute("user", session.getAttribute("user"));
            return "redirect:/admin/";
        } else if (role != null && user != null && role.equals("monitor")) {
            model.addAttribute("user", session.getAttribute("user"));
            return "redirect:/monitor/";
        }
        return "redirect:/toLogin";
    }
}
