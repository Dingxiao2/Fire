package fun.juhua.library_springboot.service;

import fun.juhua.library_springboot.entity.Clear;

import java.util.List;

public interface ClearService {

    List<Clear> getClearList(String fireID, String monitorID, String isNull);

    int updateClear(Clear clear);

    int check(String monitorID);

    int addClear(String monitorID, String fireID, String clearTime);
}
