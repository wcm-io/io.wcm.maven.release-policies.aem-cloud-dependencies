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

import org.apache.maven.shared.release.policy.PolicyException;
import org.apache.maven.shared.release.policy.version.VersionPolicy;
import org.apache.maven.shared.release.policy.version.VersionPolicyRequest;
import org.apache.maven.shared.release.policy.version.VersionPolicyResult;
import org.apache.maven.shared.release.versions.VersionParseException;
import org.codehaus.plexus.component.annotations.Component;

/**
 * AEM cloud dependencies version policy.
 * <p>
 * Example for version: <code>2022.2.6276.20220205T222203Z-220100.0001-SNAPSHOT</code>
 * </p>
 * <p>
 * This policy increments only the last part e.g. <code>.0000</code> =&gt; <code>.0001-SNAPSHOT</code>.
 * </p>
 */
@Component(
    role = VersionPolicy.class,
    hint = "AemCloudDependenciesVersionPolicy",
    description = "Manages versions for wcm.io AEM Cloud Dependencies")
public class AemCloudDependenciesVersionPolicy implements VersionPolicy {

  @Override
  public VersionPolicyResult getReleaseVersion(VersionPolicyRequest request) throws PolicyException, VersionParseException {
    AemCloudDepsVersion version = AemCloudDepsVersion.parse(request.getVersion());

    if (!version.isSnapshot()) {
      throw new PolicyException("Version is not a snapshot version: " + request.getVersion());
    }

    // return same version without snapshot
    return new VersionPolicyResult().setVersion(
        new AemCloudDepsVersion(version.getAemVersion(), version.getVersionSuffix(), false).toString());
  }

  @Override
  public VersionPolicyResult getDevelopmentVersion(VersionPolicyRequest request) throws PolicyException, VersionParseException {
    AemCloudDepsVersion version = AemCloudDepsVersion.parse(request.getVersion());

    if (version.isSnapshot()) {
      throw new PolicyException("Version is not a release version: " + request.getVersion());
    }

    // increment version suffix and add snapshot
    return new VersionPolicyResult().setVersion(
        new AemCloudDepsVersion(version.getAemVersion(), version.getVersionSuffix() + 1, true).toString());
  }

}
