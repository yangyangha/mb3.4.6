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
package org.apache.ibatis.scripting;

import java.util.HashMap;
import java.util.Map;

/**
 * 从3.2版本开始，mybatis提供了LanguageDriver接口，我们可以使用该接口自定义SQL的解析方式。
 *
 * LanguageDriver将实际的实现根据采用的底层不同，委托给了具体的Builder，对于XML配置，委托给XMLScriptBuilder。
 * 对于使用Velocity模板的解析器，委托给SQLScriptSource解析具体的SQL。
 *
 * @author Frank D. Martinez [mnesarco]
 */
public class LanguageDriverRegistry {

  private final Map<Class<?>, LanguageDriver> LANGUAGE_DRIVER_MAP = new HashMap<Class<?>, LanguageDriver>();

  private Class<?> defaultDriverClass;

  public void register(Class<?> cls) {
    if (cls == null) {
      throw new IllegalArgumentException("null is not a valid Language Driver");
    }
    if (!LANGUAGE_DRIVER_MAP.containsKey(cls)) {
      try {
        LANGUAGE_DRIVER_MAP.put(cls, (LanguageDriver) cls.newInstance());
      } catch (Exception ex) {
        throw new ScriptingException("Failed to load language driver for " + cls.getName(), ex);
      }
    }
  }

  public void register(LanguageDriver instance) {
    if (instance == null) {
      throw new IllegalArgumentException("null is not a valid Language Driver");
    }
    Class<?> cls = instance.getClass();
    if (!LANGUAGE_DRIVER_MAP.containsKey(cls)) {
      LANGUAGE_DRIVER_MAP.put(cls, instance);
    }
  }
  
  public LanguageDriver getDriver(Class<?> cls) {
    return LANGUAGE_DRIVER_MAP.get(cls);
  }

  public LanguageDriver getDefaultDriver() {
    return getDriver(getDefaultDriverClass());
  }

  public Class<?> getDefaultDriverClass() {
    return defaultDriverClass;
  }

  public void setDefaultDriverClass(Class<?> defaultDriverClass) {
    register(defaultDriverClass);
    this.defaultDriverClass = defaultDriverClass;
  }

}
