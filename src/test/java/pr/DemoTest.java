package pr;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pr.demo.entity.User;

import java.io.Reader;

/**
 * 1、配置文件初始化流程
 *
 * 2、数据源连接初始化的时机
 *
 * 3、缓存流程
 *
 * 4、插件的动态代理
 *
 * 源码借鉴的东西：1、设计模式 2、方法封装 3、面向接口编程 4、基础应用
 */
public class DemoTest {
    public static void main(String[] args) throws Exception {
        String resource = "configuration.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
                .build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        User user = sqlSession.selectOne("userTest.selectUser", 1);       //normal
        User user = sqlSession.selectOne("userTest.selectUserInclude", 1);  //include
        System.out.println(user.getUsername());
        User user1 = new User();
        user1.setId(3);user1.setAge(21);user1.setUsername("hi");user1.setPassword("hi");
        sqlSession.update("userTest.insertUser",user1);
        sqlSession.commit();
//        throw new RuntimeSqlException();
        sqlSession.close();
    }
}