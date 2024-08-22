package springboot06.work.util;

public class ResultJson <E>{
    private Boolean success; //是否成功
    private Integer code;  //返回码
    private String message;  //返回消息
    private E data;  //返回数据
    private String token;

    public ResultJson() {
    }

    public ResultJson(Boolean success, Integer code, String message, E data,String token) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.token = token;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

}
