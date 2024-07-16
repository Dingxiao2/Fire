package fun.juhua.library_springboot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.juhua.library_springboot.entity.Monitor;
import fun.juhua.library_springboot.mapper.MonitorMapper;
import fun.juhua.library_springboot.service.MonitorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MonitorServiceImpl implements MonitorService {
    @Resource
    MonitorMapper monitorMapper;

    @Override
    public List<Monitor> getMonitorList(String name, String id) {
        List<Monitor> monitorList = null;
        QueryWrapper<Monitor> wrapper = new QueryWrapper<>();
        if ((name == null || name == "") && (id == null || id == "")) {
            monitorList = monitorMapper.selectList(null);
        } else if ((id == null || id == "")) {
            monitorList = monitorMapper.selectList(wrapper.like("name", name));
        } else {
            monitorList = monitorMapper.selectList(wrapper.eq("id", id));
        }
        return monitorList;
    }

    @Override
    public boolean login(String id, String password) {
        Monitor monitor = findMonitor(id);
        if (monitor == null || !monitor.getPassword().equals(password)) {
            return false;
        }
        return true;
    }

    @Override
    public Monitor findMonitor(String id) {
        QueryWrapper<Monitor> wrapper = new QueryWrapper<>();
        return monitorMapper.selectOne(wrapper.eq("id", id));
    }

    @Override
    public boolean haveMonitor(String id) {
        QueryWrapper<Monitor> wrapper = new QueryWrapper<>();
        return monitorMapper.selectOne(wrapper.eq("id", id)) != null;
    }

    @Override
    public int addMonitor(Monitor monitor) {
        return monitorMapper.insert(monitor);
    }

    @Override
    public int updateMonitor(Monitor monitor) {
        return monitorMapper.updateById(monitor);
    }

    @Override
    public int deleteAdminById(String id) {
        return monitorMapper.deleteById(id);
    }
}
