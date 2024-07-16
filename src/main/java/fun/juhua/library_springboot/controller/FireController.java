package fun.juhua.library_springboot.controller;

import fun.juhua.library_springboot.entity.Fire;
import fun.juhua.library_springboot.service.FireService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class FireController {
    @Resource
    FireService fireServiceImpl;

    @RequestMapping("/admin/toFireList")
    public String toFireList(String fireName, String fireID, Model model) {
        List<Fire> fireList = fireServiceImpl.getFireList(fireName, fireID);
        model.addAttribute("fireList", fireList);
        return "admin/fireList";
    }

    @RequestMapping("/admin/toEditFire")
    public String toEditFire(String id, Model model) {
        //System.out.println(id);
        Fire fire = fireServiceImpl.findById(id);
        model.addAttribute("editFire", fire);
        return "admin/editFire";
    }

    @RequestMapping("/admin/UpdateFire")
    @ResponseBody
    public String updateFire(String fireID, String fireName, String fireAuthor, String firePublisher, String publishTime, String firePrice, String fireSum, String fireLend, String tag, String isbn) {
        Fire fire = new Fire(fireID, fireName, fireAuthor, firePublisher, publishTime, firePrice, fireSum, fireLend, tag, isbn);
        int state = fireServiceImpl.updateFire(fire);
        String msg = "更新失败，请重试";
        if (state == 1) {
            msg = "更新成功!";
        }
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }

    @RequestMapping("/admin/DeleteFire")
    public String deleteFire(String id) {
        int raw = fireServiceImpl.deleteFire(id);
        return "redirect:/admin/";
    }

    @RequestMapping("/admin/toAddFire")
    public String toAddFire() {
        return "admin/addFire";
    }

    @RequestMapping("/admin/AddFire")
    @ResponseBody
    public String addFire(String fireID, String fireName, String fireAuthor, String firePublisher, String publishTime, String firePrice, String fireSum, String tag, String isbn) {
        // 创建一个Fire对象
        Fire fire = new Fire(fireID, fireName, fireAuthor, firePublisher, publishTime, firePrice, fireSum, "0", tag, isbn);
        // 调用服务层方法，添加Fire对象到数据库
        int state = fireServiceImpl.addFire(fire);
        // 初始化消息为添加失败的提示
        String msg = "添加失败，请重试";
        // 根据添加状态修改消息内容
        if (state == 1) {
            msg = "添加成功!";
        }
        // 返回JSON格式的结果，包含状态码和消息
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }
}
