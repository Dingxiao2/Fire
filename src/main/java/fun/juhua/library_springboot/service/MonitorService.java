package fun.juhua.library_springboot.service;

import fun.juhua.library_springboot.entity.Monitor;

import java.util.List;

public interface MonitorService {
    List<Monitor> getMonitorList(String name, String id);

    boolean login(String id, String password);

    Monitor findMonitor(String id);

    boolean haveMonitor(String id);

    int addMonitor(Monitor monitor);

    int updateMonitor(Monitor monitor);

    int deleteAdminById(String id);
}
