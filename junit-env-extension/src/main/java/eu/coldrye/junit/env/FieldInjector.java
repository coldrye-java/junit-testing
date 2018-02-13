/*
 * Copyright 2018 coldrye.eu, Carsten Klein
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.coldrye.junit.env;

import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * The final class FieldInjector models an injector for fields for which it resolves
 * the instances for by querying the available {@link EnvProvider}S.
 *
 * @since 1.0.0
 */
class FieldInjector {

    /**
     * @param testInstance
     * @param context
     * @param providers
     * @throws Exception
     */
    public void inject(Object testInstance, ExtensionContext context, List<EnvProvider> providers)
        throws Exception {

        List<Field> fields = ReflectionHelper.getDeclaredFields(testInstance.getClass(), EnvProvided.class);
        for (Field field : fields) {
            for (EnvProvider provider : providers) {
                if (provider.canProvideInstance(field, field.getType())) {
                    Object instance = provider.getOrCreateInstance(field, field.getType());
                    try {
                        Method setter = testInstance.getClass().getMethod(
                            "set"
                                + field.getName().substring(0, 1).toUpperCase()
                                + field.getName().substring(1));
                        setter.invoke(testInstance, instance);
                    } catch (NoSuchMethodException ex) {
                        field.setAccessible(true);
                        field.set(testInstance, instance);
                    }
                }
            }
        }
    }
}
