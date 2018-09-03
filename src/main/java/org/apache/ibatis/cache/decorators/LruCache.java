/**
 *    Copyright 2009-2015 the original author or authors.
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
package org.apache.ibatis.cache.decorators;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.ibatis.cache.Cache;

/**
 * done
 * Lru 算法使用了linkedhashmap的数据结构
 *
 * Lru (least recently used) cache decorator
 *
 * @author Clinton Begin
 *
 *
 * linkedhashmap 简单而言就是有序的hashmap
 * LinkedHashMap实现与HashMap的不同之处在于，后者维护着一个运行于所有条目的双重链接列表。此链接列表定义了迭代顺序，该迭代顺序可以是插入顺序或者是访问顺序。根据链表中元素的顺序可以分为：按插入顺序的链表，和按访问顺序(调用get方法)的链表。
 * 默认是按插入顺序排序，如果指定按访问顺序排序，那么调用get方法后，会将这次访问的元素移至链表尾部，不断访问可以形成按访问顺序排序的链表。
 *
 * linkedlist 按插入顺序，但是没有key-value结构！
 * https://www.cnblogs.com/whoislcj/p/5552421.html
 * http://www.cnblogs.com/children/archive/2012/10/02/2710624.html
 *
 * 装填因子/加载因子/负载因子 α= 填入表中的元素个数 / 哈希表的长度
 * 负载因子表示哈希表空间元素密度，越大表示哈希表越满，产生冲突的可能性越大
 * 一般常用0.75
 *
 * todo:interview linkedhashmap 实现结构
 */
public class LruCache implements Cache {

  private final Cache delegate;
  //维护了第二份缓存，只是使用其lru算法，获取最老、最少使用的元素来删除delegate的元素；有点儿浪费了空间
  private Map<Object, Object> keyMap;
  private Object eldestKey;

  public LruCache(Cache delegate) {
    this.delegate = delegate;
    setSize(1024);
  }

  @Override
  public String getId() {
    return delegate.getId();
  }

  @Override
  public int getSize() {
    return delegate.getSize();
  }

  public void setSize(final int size) {
    keyMap = new LinkedHashMap<Object, Object>(size, .75F, true) {
      private static final long serialVersionUID = 4267176411845948333L;

      //默认是返回false 即不会删除最老、使用最少的数据
      @Override
      protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
        boolean tooBig = size() > size;
        if (tooBig) {
          eldestKey = eldest.getKey();
        }
        return tooBig;
      }
    };
  }

  @Override
  public void putObject(Object key, Object value) {
    delegate.putObject(key, value);
    cycleKeyList(key);
  }

  @Override
  public Object getObject(Object key) {
    keyMap.get(key); //touch 增加一次访问，更新一次排序
    return delegate.getObject(key);
  }

  @Override
  public Object removeObject(Object key) {
    return delegate.removeObject(key);
  }

  @Override
  public void clear() {
    delegate.clear();
    keyMap.clear();
  }

  @Override
  public ReadWriteLock getReadWriteLock() {
    return null;
  }


  private void cycleKeyList(Object key) {
    keyMap.put(key, key);
    if (eldestKey != null) {
      delegate.removeObject(eldestKey);
      eldestKey = null;
    }
  }

}
