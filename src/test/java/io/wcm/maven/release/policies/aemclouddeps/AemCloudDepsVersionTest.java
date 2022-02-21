/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2022 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.wcm.maven.release.policies.aemclouddeps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.maven.shared.release.versions.VersionParseException;
import org.junit.jupiter.api.Test;

class AemCloudDepsVersionTest {

  @Test
  void testParseInvalid() {
    assertThrows(VersionParseException.class, () -> {
      AemCloudDepsVersion.parse("invalid");
    });
  }

  @Test
  void testValid() throws VersionParseException {
    AemCloudDepsVersion underTest = AemCloudDepsVersion.parse("2022.2.6276.20220205T222203Z-220100.0000");
    assertEquals("2022.2.6276.20220205T222203Z-220100", underTest.getAemVersion());
    assertEquals(0, underTest.getVersionSuffix());
    assertFalse(underTest.isSnapshot());
    assertEquals("2022.2.6276.20220205T222203Z-220100.0000", underTest.toString());
  }

  @Test
  void testValidMonth2Digits() throws VersionParseException {
    AemCloudDepsVersion underTest = AemCloudDepsVersion.parse("2021.12.6151.20211217T120950Z-211100.0002");
    assertEquals("2021.12.6151.20211217T120950Z-211100", underTest.getAemVersion());
    assertEquals(2, underTest.getVersionSuffix());
    assertFalse(underTest.isSnapshot());
    assertEquals("2021.12.6151.20211217T120950Z-211100.0002", underTest.toString());
  }

  @Test
  void testValidSnapshot() throws VersionParseException {
    AemCloudDepsVersion underTest = AemCloudDepsVersion.parse("2022.2.6276.20220205T222203Z-220100.0001-SNAPSHOT");
    assertEquals("2022.2.6276.20220205T222203Z-220100", underTest.getAemVersion());
    assertEquals(1, underTest.getVersionSuffix());
    assertTrue(underTest.isSnapshot());
    assertEquals("2022.2.6276.20220205T222203Z-220100.0001-SNAPSHOT", underTest.toString());
  }

}
