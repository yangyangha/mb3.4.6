/**
 *    Copyright 2009-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.submitted.blocking_cache;

import java.io.Reader;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

// issue #524
public class BlockingCacheTest {

  private static SqlSessionFactory sqlSessionFactory;

  @Before
  public void setUp() throws Exception {
    // create a SqlSessionFactory
    Reader reader = Resources.getResourceAsReader("org/apache/ibatis/submitted/blocking_cache/mybatis-config.xml");
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    reader.close();

    // populate in-memory database
    SqlSession session = sqlSessionFactory.openSession();
    Connection conn = session.getConnection();
    reader = Resources.getResourceAsReader("org/apache/ibatis/submitted/blocking_cache/CreateDB.sql");
    ScriptRunner runner = new ScriptRunner(conn);
    runner.setLogWriter(null);
    runner.runScript(reader);
    conn.close();
    reader.close();
    session.close();
  }

  @Test
  public void testBlockingCache() {
    ExecutorService defaultThreadPool = Executors.newFixedThreadPool(2);

    long init = System.currentTimeMillis();

    for (int i = 0; i < 2; i++) {
      defaultThreadPool.execute(new Runnable() {

        @Override
        public void run() {
          accessDB();
        }
      });
    }

    defaultThreadPool.shutdown();

    while (!defaultThreadPool.isTerminated()) {
    }

    long totalTime = System.currentTimeMillis() - init;
    Assert.assertTrue(totalTime > 1000);
  }

  private void accessDB() {
    SqlSession sqlSession1 = sqlSessionFactory.openSession();
    try {
      PersonMapper pm = sqlSession1.getMapper(PersonMapper.class);
      pm.findAll();
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        Assert.fail(e.getMessage());
      }
    } finally {
      sqlSession1.close();
    }
  }


    /**
     * basic
     */
    //
    @Test
    public void testMap(){
        //参考putIfAbsent 方法注释
        //put putIfAbsent 都会返回map中已存在的值，但是put会替换，putIfAbset 不会替换值
        Map<String,String> map = new HashMap<>();
        String val = map.putIfAbsent("one","onevalue");
        System.out.println("putIfAbsent first return "+val);
        val = map.putIfAbsent("one","onevalue");
        System.out.println("putIfAbsetn second return :"+val);
        val = map.putIfAbsent("one","anotherval");
        System.out.println("putIfAbsent third return :"+val);
        System.out.println("putIfAbsetn value is :"+map.get("one"));

        val = map.put("two","twoval");
        System.out.println("test put return :"+val);
    }

    @Test
    public void testReentrant(){
        ReentrantLock lock = new ReentrantLock();
        try {
            //tryLock lock 区别
            lock.tryLock();// 能不能获取都直接返回
            lock.lock();//会一直等待
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
