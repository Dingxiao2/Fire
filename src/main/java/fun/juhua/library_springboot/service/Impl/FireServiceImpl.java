package fun.juhua.library_springboot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.juhua.library_springboot.entity.Fire;
import fun.juhua.library_springboot.mapper.FireMapper;
import fun.juhua.library_springboot.service.FireService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FireServiceImpl implements FireService {
    @Resource
    FireMapper fireMapper;

    @Override
    public List<Fire> getFireList(String fireName, String fireID) {
        List<Fire> fireList = null;
        QueryWrapper<Fire> wrapper = new QueryWrapper<>();
        if ((fireName == null || fireName == "") && (fireID == null || fireID == "")) {
            fireList = fireMapper.selectList(null);
        } else if ((fireID == null || fireID == "")) {
            fireList = fireMapper.selectList(wrapper.like("fireName", fireName));
        } else {
            fireList = fireMapper.selectList(wrapper.eq("fireID", fireID));
        }
        return fireList;
    }

    @Override
    public Fire findById(String fireID) {
        return fireMapper.selectById(fireID);
    }

    @Override
    public int updateFire(Fire fire) {
        if (fire.getFireSum() < fire.getFireLend()) {
            return 0;
        }
        return fireMapper.updateById(fire);
    }

    @Override
    public int deleteFire(String fireID) {
        return fireMapper.deleteById(fireID);
    }

    @Override
    public int addFire(Fire fire) {
        return fireMapper.insert(fire);
    }

    @Override
    public int returnFire(String fireID) {
        Fire fire = findById(fireID);
        fire.setFireLend(fire.getFireLend() - 1);
        return fireMapper.updateById(fire);
    }

    @Override
    public int check(String fireID) {
        Fire fire = findById(fireID);
        if (fire == null || fire.getFireSum() <= fire.getFireLend()) {
            return 0;
        }
        return 1;
    }
}
