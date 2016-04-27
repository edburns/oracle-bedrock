/*
 * File: MavenTest.java
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * The contents of this file are subject to the terms and conditions of
 * the Common Development and Distribution License 1.0 (the "License").
 *
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the License by consulting the LICENSE.txt file
 * distributed with this file, or by consulting https://oss.oracle.com/licenses/CDDL
 *
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file LICENSE.txt.
 *
 * MODIFICATIONS:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 */

package com.oracle.tools.maven;

import com.oracle.tools.Options;
import com.oracle.tools.runtime.LocalPlatform;
import com.oracle.tools.runtime.MetaClass;
import com.oracle.tools.runtime.java.ClassPath;
import com.oracle.tools.runtime.java.JavaApplication;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for the {@link Maven}.
 * <p>
 * Copyright (c) 2016. All Rights Reserved. Oracle Corporation.<br>
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * @author Brian Oliver
 */
public class MavenTest
{
    /**
     * Ensure that {@link Maven} can resolve a single artifact (without a transitive dependency).
     */
    @Test
    public void shouldResolveSingleArtifact()
    {
        LocalPlatform platform  = LocalPlatform.get();
        MetaClass     metaClass = new JavaApplication.MetaClass();
        Options       options   = new Options();

        options.add(Maven.artifact("org.hamcrest:hamcrest-library:jar:1.3"));

        Maven maven = options.get(Maven.class);

        maven.onLaunching(platform, metaClass, options);

        ClassPath classPath = options.getOrDefault(ClassPath.class, null);

        assertThat(classPath, is(not(nullValue())));
        assertThat(classPath.size(), is(2));

        assertThat(classPath.toString(), containsString("hamcrest-library-1.3.jar"));
    }


    /**
     * Ensure that {@link Maven} can resolve a single artifact (with a transitive dependency).
     */
    @Test
    public void shouldResolveSingleArtifactWithTransitiveDependency()
    {
        LocalPlatform platform  = LocalPlatform.get();
        MetaClass     metaClass = new JavaApplication.MetaClass();
        Options       options   = new Options();

        options.add(Maven.artifact("junit:junit:jar:4.12"));

        Maven maven = options.get(Maven.class);

        maven.onLaunching(platform, metaClass, options);

        ClassPath classPath = options.getOrDefault(ClassPath.class, null);

        assertThat(classPath, is(not(nullValue())));
        assertThat(classPath.size(), is(2));

        assertThat(classPath.toString(), containsString("junit-4.12.jar"));
        assertThat(classPath.toString(), containsString("hamcrest-core-1.3.jar"));
    }
}
