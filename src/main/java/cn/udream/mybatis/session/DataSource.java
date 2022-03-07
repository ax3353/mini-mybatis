package cn.udream.mybatis.session;

/**
 * 描述: 数据源连接信息
 * @author kun.zhu
 * @date 2022/3/2 17:03
 */
public class DataSource {

    private String username;

    private String password;

    private String url;

    private String driver;

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String url() {
        return url;
    }

    public String driver() {
        return driver;
    }

    public DataSource username(String username) {
        this.username = username;
        return this;
    }

    public DataSource password(String password) {
        this.password = password;
        return this;
    }

    public DataSource url(String url) {
        this.url = url;
        return this;
    }

    public DataSource driver(String driver) {
        this.driver = driver;
        return this;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", driver='" + driver + '\'' +
                '}';
    }
}
