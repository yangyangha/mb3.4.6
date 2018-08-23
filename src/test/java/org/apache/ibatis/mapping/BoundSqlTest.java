/**
 *    Copyright 2009-2016 the original author or authors.
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
package org.apache.ibatis.mapping;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.Configuration;
import org.junit.Test;

public class BoundSqlTest {

 @Test
  public void testHasAdditionalParameter() throws Exception {
    List<ParameterMapping> params = Collections.emptyList();
    BoundSql boundSql = new BoundSql(new Configuration(), "some sql", params, new Object());

    Map<String, String> map = new HashMap<String, String>();
    map.put("key1", "value1");
    boundSql.setAdditionalParameter("map", map);

    Person bean = new Person();
    bean.id = 1;
    boundSql.setAdditionalParameter("person", bean);

    String[] array = new String[] {"User1", "User2"};
    boundSql.setAdditionalParameter("array", array);

    assertFalse(boundSql.hasAdditionalParameter("pet"));
    assertFalse(boundSql.hasAdditionalParameter("pet.name"));

    assertTrue(boundSql.hasAdditionalParameter("map"));
    assertTrue(boundSql.hasAdditionalParameter("map.key1"));
    assertTrue("should return true even if the child property does not exists.", boundSql.hasAdditionalParameter("map.key2"));

    assertTrue(boundSql.hasAdditionalParameter("person"));
    assertTrue(boundSql.hasAdditionalParameter("person.id"));
    assertTrue("should return true even if the child property does not exists.", boundSql.hasAdditionalParameter("person.name"));

    assertTrue(boundSql.hasAdditionalParameter("array[0]"));
    assertTrue("should return true even if the element does not exists.", boundSql.hasAdditionalParameter("array[99]"));
  }

  public static class Person {
    public Integer id;
  }

}

//Collections.EMPTY_LIST 和 Collections.emptyList()的区别
//Collections.EMPTY_LIST返回的是一个空的List。为什么需要空的List呢？
// 有时候我们在函数中需要返回一个List，但是这个List是空的，如果我们直接返回null的话，调用者还需要进行null的判断，所以一般建议返回一个空的List。
// Collections.EMPTY_LIST返回的这个空的List是不能进行添加元素这类操作的。这时候你有可能会说，我直接返回一个new ArrayList()呗，
// 但是new ArrayList()在初始化时会占用一定的资源，所以在这种场景下，还是建议返回Collections.EMPTY_LIST。
//Collections. emptyList()返回的也是一个空的List，
// 它与Collections.EMPTY_LIST的唯一区别是，Collections. emptyList()支持泛型，所以在需要泛型的时候，可以使用Collections. emptyList()。