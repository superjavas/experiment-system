package springboot06.work.service;

import springboot06.work.pojo.User;

public interface TokenService {
    public String getToken(User user);
}
