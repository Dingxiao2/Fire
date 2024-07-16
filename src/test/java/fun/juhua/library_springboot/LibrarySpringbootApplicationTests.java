package fun.juhua.library_springboot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.juhua.library_springboot.entity.Monitor;
import fun.juhua.library_springboot.mapper.MonitorMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class LibrarySpringbootApplicationTests {
    @Resource
    MonitorMapper monitorMapper;

    @Test
    public void login() {
        String id = "r001";
        String password = "r0012";
        QueryWrapper<Monitor> wrapper = new QueryWrapper<>();
        Monitor monitor = monitorMapper.selectOne(wrapper.eq("id", id));
        Map<String, String> map = new HashMap<>();
        if (monitor == null || !monitor.getPassword().equals(password)) {
            System.out.println("false");
        } else {
            System.out.println("true");
        }
    }

}
