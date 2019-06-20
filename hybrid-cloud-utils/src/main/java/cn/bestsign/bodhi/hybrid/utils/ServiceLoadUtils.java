/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.github.bodhi.hybrid.utils;

import org.github.bodhi.hybrid.utils.condition.LoadCondition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;


public class ServiceLoadUtils {


    public static <S> S loadFirst(final Class<S> clazz) {
        return loadFirst(clazz,Thread.currentThread().getContextClassLoader());
    }

    public static <S> S loadFirst(final Class<S> clazz,final ClassLoader classLoader) {
        final ServiceLoader<S> loader = loadAll(clazz,classLoader);
        final Iterator<S> iterator = loader.iterator();
        if (isNotNull(iterator)){
            return iterator.next();
        }
        return null;
    }

    public static <S> S loadCondition(final Class<S> clazz,final ClassLoader classLoader,final LoadCondition condition){
        final ServiceLoader<S> loader = loadAll(clazz,classLoader);
        final Iterator<S> iterator = loader.iterator();

        S clz;
        if (isNotNull(iterator)){
            while (iterator.hasNext()){
                clz = iterator.next();
                if (condition.judge(clz)){
                    return clz;
                }
            }
        }
        return null;
    }


    public static <S> ServiceLoader<S> loadAll(final Class<S> clazz) {
        return loadAll(clazz,Thread.currentThread().getContextClassLoader());
    }


    public static <S> ServiceLoader<S> loadAll(final Class<S> clazz,final ClassLoader classLoader) {
        return ServiceLoader.load(clazz,classLoader);
    }

    public static <S> List<S> loadAllInstance(final Class<S> clazz,final ClassLoader classLoader) {
        final ServiceLoader<S> loader =  ServiceLoader.load(clazz,classLoader);
        final Iterator<S> iterator = loader.iterator();
        boolean isNotNull = isNotNull(iterator);
        List<S> list = new ArrayList<>();
        if (isNotNull){
            while (iterator.hasNext()){
                list.add(iterator.next());
            }
        }
        return list;
    }

    private static boolean isNotNull(Iterator iterator){
        if (iterator!=null&&iterator.hasNext()) {
            return true;
        }
        return false;
    }

}
