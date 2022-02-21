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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.shared.release.versions.VersionParseException;

/**
 * Parses AEM cloud dependencies version.
 * <p>
 * Example version: <code>2022.2.6276.20220205T222203Z-220100.0001-SNAPSHOT</code>
 * </p>
 */
class AemCloudDepsVersion {

  private static final Pattern VERSION_PATTERN = Pattern.compile("^(\\d{4}\\.\\d{1,2}\\.\\d+\\.\\d{8}T\\d{6}Z-\\d{6})\\.(\\d{4})(-SNAPSHOT)?$");

  private final String aemVersion;
  private final int versionSuffix;
  private final boolean snapshot;
  private final NumberFormat versionSuffixFormat = new DecimalFormat("0000");

  /**
   * @param aemVersion AEM version like 2022.2.6276.20220205T222203Z-220100
   * @param versionSuffix Version suffix like 0000
   * @param snapshot Snapshot version
   */
  AemCloudDepsVersion(String aemVersion, int versionSuffix, boolean snapshot) {
    this.aemVersion = aemVersion;
    this.versionSuffix = versionSuffix;
    this.snapshot = snapshot;
  }

  /**
   * @return AEM version like 2022.2.6276.20220205T222203Z-220100
   */
  public String getAemVersion() {
    return this.aemVersion;
  }

  /**
   * @return Version suffix like 0000
   */
  public int getVersionSuffix() {
    return this.versionSuffix;
  }

  /**
   * @return Snapshot version
   */
  public boolean isSnapshot() {
    return this.snapshot;
  }

  @Override
  public String toString() {
    return aemVersion + "." + versionSuffixFormat.format(versionSuffix) + (snapshot ? "-SNAPSHOT" : "");
  }

  static AemCloudDepsVersion parse(String version) throws VersionParseException {
    Matcher matcher = VERSION_PATTERN.matcher(version);
    if (!matcher.matches()) {
      throw new VersionParseException("Version is not a valid AEM Cloud dependencies version: " + version);
    }
    return new AemCloudDepsVersion(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3) != null);
  }

}
