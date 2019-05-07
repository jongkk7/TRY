package mars.nomad.com.m0_http;

/**
 * Created by SJH on 2017-06-26.
 */

public class BaseResponseModel {

    protected boolean result;

    protected String message;

    protected String msg;

    protected String code;

    protected  String opt;


    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    @Override
    public String toString() {
        return "BaseResponseModel{" +
                "result=" + result +
                ", message='" + message + '\'' +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", opt='" + opt + '\'' +
                '}';
    }
}
