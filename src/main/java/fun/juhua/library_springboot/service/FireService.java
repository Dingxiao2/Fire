package fun.juhua.library_springboot.service;

import fun.juhua.library_springboot.entity.Fire;

import java.util.List;

public interface FireService {
    List<Fire> getFireList(String fireName, String fireID);

    Fire findById(String fireID);

    int updateFire(Fire fire);

    int deleteFire(String fireID);

    int addFire(Fire fire);

    int returnFire(String fireID);

    int check(String fireID);


}
