package pr.demo.entity;

public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer age;

    //如果增加来如下参数构造方法，就不符合javabean规范，同样mybatis解析也会报错
//    public User(Integer id, String username, String password, Integer age) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.age = age;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}