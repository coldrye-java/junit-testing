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

import eu.coldrye.junit.util.ReflectionUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.platform.commons.util.Preconditions;

import java.lang.reflect.Parameter;
import java.util.List;

/**
 * The final class ParameterResolverImpl models a resolver for parameters.
 * <p>
 * It does so by querying available {@link EnvProvider}S.
 *
 * @since 1.0.0
 */
class ParameterResolverImpl {

  /**
   * @param parameterContext
   * @param extensionContext
   * @param providers
   * @return
   * @throws ParameterResolutionException
   */
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext,
                                   List<EnvProvider> providers) {

    Preconditions.notNull(parameterContext, "parameterContext must not be null");
    Preconditions.notNull(providers, "providers must not be null");

    Parameter parameter = parameterContext.getParameter();
    if (ReflectionUtils.isAnnotatedBy(parameter, EnvProvided.class)) {
      for (EnvProvider provider : providers) {
        if (provider.canProvideInstance(parameter, parameter.getType())) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * @param parameterContext
   * @param extensionContext
   * @param providers
   * @return
   * @throws ParameterResolutionException
   */
  public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext,
                                 List<EnvProvider> providers) {

    Preconditions.notNull(parameterContext, "parameterContext must not be null");
    Preconditions.notNull(providers, "providers must not be null");

    Parameter parameter = parameterContext.getParameter();
    for (EnvProvider provider : providers) {
      if (provider.canProvideInstance(parameter, parameter.getType())) {
        return provider.getOrCreateInstance(parameter, parameter.getType());
      }
    }
    throw new ParameterResolutionException("unable to resolve parameter " + parameter.toString());
  }
}
