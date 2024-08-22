package springboot06.work.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import springboot06.work.annotation.UserLoginToken;
import springboot06.work.pojo.User;
import springboot06.work.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor{
    @Autowired
    UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception{
        String token = httpServletRequest.getHeader("token");
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method=handlerMethod.getMethod();
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    httpServletResponse.setContentType("application/json; charset=utf-8");
                    httpServletResponse.getWriter().print("{\"success\":false,\"msg\":\"请先登录哈哈\",\"code\":500}");
                    return false;
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                User user = userService.findUserById(Integer.parseInt(userId));
                if (user == null) {
                    httpServletResponse.setContentType("application/json; charset=utf-8");
                    httpServletResponse.getWriter().print("{\"success\":false,\"msg\":\"!!!\",\"NoUser\"}");
                    return false;
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                     jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    e.printStackTrace();
                    System.out.println(1);
                    httpServletResponse.setContentType("application/json; charset=utf-8");
                    httpServletResponse.getWriter().print("{\"success\":false,\"msg\":\"请先登录!!!\",\"code\":500}");
                    return false;
                }
                return true;
            }
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
