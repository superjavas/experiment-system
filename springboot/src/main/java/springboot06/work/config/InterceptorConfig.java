package springboot06.work.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**配置拦截器**/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置白名单；存放在一个List集合
        List<String> patterns=new ArrayList<>();
        patterns.add("/js/**");
        patterns.add("/login.html");



        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**")//表示要拦截的url
                .excludePathPatterns(patterns);

    }



    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
}

