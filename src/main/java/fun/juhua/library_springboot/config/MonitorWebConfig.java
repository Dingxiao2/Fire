package fun.juhua.library_springboot.config;

import fun.juhua.library_springboot.interceptor.MonitorInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MonitorWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MonitorInterceptor())
                .addPathPatterns("/admin/**");
    }
}
