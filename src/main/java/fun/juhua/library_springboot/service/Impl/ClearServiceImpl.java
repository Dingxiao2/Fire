package fun.juhua.library_springboot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.juhua.library_springboot.entity.Clear;
import fun.juhua.library_springboot.mapper.ClearMapper;
import fun.juhua.library_springboot.service.ClearService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClearServiceImpl implements ClearService {
    @Resource
    ClearMapper clearMapper;

    @Override
    public List<Clear> getClearList(String fireID, String monitorID, String isNull) {
        List<Clear> clearList = null;
        QueryWrapper<Clear> wrapper = new QueryWrapper<>();
        //当fireID和monitorID都为空，说明显示全部
        if ((fireID == null || fireID == "") && (monitorID == null || monitorID == "")) {
            //如果isNull为T，查询未归还的书籍
            if ("T".equals(isNull)) {
                clearList = clearMapper.selectList(wrapper.isNull("returnTime"));
            } else {
                clearList = clearMapper.selectList(null);
            }
        } else if (fireID == null || fireID == "") {
            //monitorID不为空，查询某人所有借阅记录
            //如果isNull为T，查询此人未归还的书籍
            if ("T".equals(isNull)) {
                clearList = clearMapper.selectList(wrapper.eq("monitorID", monitorID).isNull("returnTime"));
            } else {
                clearList = clearMapper.selectList(wrapper.eq("monitorID", monitorID));
            }
        } else {
            //fireID不为空，查询某人所有借阅记录
            //如果isNull为T，查询此人未归还的书籍
            if ("T".equals(isNull)) {
                clearList = clearMapper.selectList(wrapper.eq("fireID", fireID).isNull("returnTime"));
            } else {
                clearList = clearMapper.selectList(wrapper.eq("fireID", fireID));
            }
        }
        return clearList;
    }

    @Override
    public int updateClear(Clear clear) {
        QueryWrapper<Clear> wrapper = new QueryWrapper();
        wrapper.eq("monitorID", clear.getMonitorID())
                .eq("fireID", clear.getFireID())
                .eq("clearTime", clear.getClearTime());
        return clearMapper.update(clear, wrapper);
    }

    /**
     * 判断用户是否超过8本未还，未超过返回1
     *
     * @param monitorID
     * @return
     */
    @Override
    public int check(String monitorID) {
        QueryWrapper<Clear> wrapper = new QueryWrapper();
        wrapper.eq("monitorID", monitorID).isNull("returnTime");
        Long raw = clearMapper.selectCount(wrapper);
        if (raw < 8) {
            return 1;
        }
        return 0;
    }

    @Override
    public int addClear(String monitorID, String fireID, String clearTime) {
        Clear clear = new Clear(monitorID, fireID, clearTime);
        return clearMapper.insert(clear);
    }
}
