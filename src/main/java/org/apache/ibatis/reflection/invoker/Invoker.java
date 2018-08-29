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
package org.apache.ibatis.reflection.invoker;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * https://blog.csdn.net/likewindy/article/details/51433304
 * GetFieldInvoker，SetFieldInvoker和MethodInvoker。
 * GetFieldInvoker和SetFieldInvoker分别调用Field的get和set用来获取和设置对象的属性值。MethodInvoker用来调用Object的的某个方法（通过方法设置对象的属性）。
 *
 * @author Clinton Begin
 */
public interface Invoker {
  Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException;

  Class<?> getType();
}
