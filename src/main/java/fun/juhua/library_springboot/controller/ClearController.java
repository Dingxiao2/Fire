package fun.juhua.library_springboot.controller;

import fun.juhua.library_springboot.entity.Fire;
import fun.juhua.library_springboot.entity.Clear;
import fun.juhua.library_springboot.entity.Monitor;
import fun.juhua.library_springboot.service.FireService;
import fun.juhua.library_springboot.service.ClearService;
import fun.juhua.library_springboot.service.MonitorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class ClearController {
    @Resource
    ClearService clearServiceImpl;
    @Resource
    FireService fireService;
    @Resource
    MonitorService monitorServiceImpl;

    @RequestMapping("/admin/toClearList")
    public String toClearList(String fireID, String monitorID, String isNull, Model model) {
        List<Clear> clearList = clearServiceImpl.getClearList(fireID, monitorID, isNull);
        model.addAttribute("clearList", clearList);
        return "admin/clearList";
    }

    @RequestMapping("/monitor/toMonitorClear")
    public String toMonitorClear(HttpSession httpSession, String isNull, Model model) {
        Monitor monitor = (Monitor) httpSession.getAttribute("user");
        List<Clear> clearList = clearServiceImpl.getClearList(null, monitor.getId(), isNull);
        model.addAttribute("clearList", clearList);
        model.addAttribute("user", monitor);
        return "monitor/clearList";
    }

    @RequestMapping("/admin/toReturnFire")
    @ResponseBody
    public String toReturnFire(String monitorID, String fireID, String clearTime) {
        clearTime = clearTime.toString().replace("T", " ").replace(".000+08:00", "");
        Clear clear = new Clear(monitorID, fireID, clearTime, new Date());
        int state = fireService.returnFire(fireID);
        String msg = "消除失败，请重试！";
        if (state == 1) {
            state = clearServiceImpl.updateClear(clear);
            msg = "消除成功！";
        }
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }

    @RequestMapping("/admin/toAddClear")
    public String toAddClear(Model model) {
        List<Monitor> monitorList = monitorServiceImpl.getMonitorList(null, null);
        List<Fire> fireList = fireService.getFireList(null, null);
        model.addAttribute("monitorList", monitorList);
        model.addAttribute("fireList", fireList);
        return "admin/addClear";
    }

    @RequestMapping("/admin/AddClear")
    @ResponseBody
    public String addClear(String monitorID, String fireID, String clearTime) {
        int state = 0;
        String msg = "暂无火险。";
        //判断消除数是否小于火险总数
        if (fireService.check(fireID) == 1) {
            state = clearServiceImpl.check(monitorID);
            Monitor monitor = monitorServiceImpl.findMonitor(monitorID);
            if (monitor != null) {
                if (state == 1) {
                    state = clearServiceImpl.addClear(monitorID, fireID, clearTime);
                    msg = "预警成功！";
                } else {
                    msg = "达到最迟火险时间！请尽快预警！";
                }
            } else {
                state = 0;
                msg = "查无此用户！";
            }
        }
        //System.out.println("MonitorController -> registerMonitor(49): " + msg);
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }
}
