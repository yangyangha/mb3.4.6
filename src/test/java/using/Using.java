package using;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @version Created on 2017/11/23
 *
 * from http://www.mybatis.org/mybatis-3/zh/getting-started.html
 */
public class Using {
    public static void main(String... arg) throws IOException{
        String resource = "org/mybatis/example/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 101);
        session.close();
    }
}
