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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EnvProviderManagerTest {

    private EnvProviderManager sut;
    private EnvProviderCollector mockCollector;

    @BeforeEach
    public void setUp() {
        mockCollector = Mockito.mock(EnvProviderCollector.class);
        sut = new EnvProviderManager(mockCollector);
    }

    @AfterEach
    public void tearDown() {
        sut = null;
        mockCollector = null;
    }

    @Test
    public void toDO() {
        Assertions.fail("no tests have been implemented yet");
    }
}
