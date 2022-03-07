package cn.udream.mybatis.exception;

public class MybatisException extends RuntimeException {

    public MybatisException(String message) {
        super(message);
    }

    public MybatisException(Throwable cause) {
        super(cause);
    }
}
