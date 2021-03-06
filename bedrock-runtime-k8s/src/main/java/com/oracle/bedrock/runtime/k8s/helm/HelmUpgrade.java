/*
 * File: HelmUpgrade.java
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

package com.oracle.bedrock.runtime.k8s.helm;

import com.oracle.bedrock.runtime.options.Argument;
import com.oracle.bedrock.runtime.options.Arguments;
import com.oracle.bedrock.runtime.options.EnvironmentVariables;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * A representation of the Helm upgrade command.
 * <p>
 * Copyright (c) 2018. All Rights Reserved. Oracle Corporation.<br>
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * @author Jonathan Knight
 */
public class HelmUpgrade
        extends HelmCommand<HelmUpgrade>
        implements CLI.WithCerts<HelmUpgrade>,
                   CLI.WithTLS<HelmUpgrade>,
                   CLI.WithRepo<HelmUpgrade>
{
    /**
     * Create a {@link HelmUpgrade} command.
     *
     * @param release  the name of the release to be upgraded
     * @param chart    the name of the chart to use to upgrade the release
     */
    HelmUpgrade(String release, File chart)
    {
    this(DEFAULT_HELM, Arguments.of(release, chart), Arguments.empty(), EnvironmentVariables.custom());
    }

    /**
     * Create a {@link HelmUpgrade} command.
     *
     * @param release  the name of the release to be upgraded
     * @param chart    the name of the chart to use to upgrade the release
     */
    HelmUpgrade(String release, String chart)
    {
    this(DEFAULT_HELM, Arguments.of(release, chart), Arguments.empty(), EnvironmentVariables.custom());
    }

    /**
     * Create a {@link HelmUpgrade} command.
     *
     * @param arguments  the command arguments
     * @param flags      the command flags
     * @param env        the environment variables
     */
    HelmUpgrade(String helm, Arguments arguments, Arguments flags, EnvironmentVariables env)
    {
        super(helm, arguments, flags, env, false, "upgrade");
    }

    /**
     * Use development versions too. Equivalent to version '>0.0.0-a'.
     * If {@code --version} is set, this is ignored.
     *
     * @return  a copy of this command with the {@code --devel} flag appended
     */
    public HelmUpgrade development()
    {
        return withFlags(Argument.of("--devel"));
    }

    /**
     * Perform a dry run install.
     *
     * @return  a copy of this command with the {@code --dry-run} flag appended
     */
    public HelmUpgrade dryRun()
    {
        return withFlags(Argument.of("--dry-run"));
    }

    /**
     * Force resource update through delete/recreate if needed.
     *
     * @return  a copy of this command with the {@code --force} flag appended
     */
    public HelmUpgrade force()
    {
        return withFlags(Argument.of("--force"));
    }

    /**
     * If a release by this name doesn't already exist, run an install.
     *
     * @return  a copy of this command with the {@code --install} flag appended
     */
    public HelmUpgrade install()
    {
        return withFlags(Argument.of("--install"));
    }

    /**
     * Set the release name. If unspecified, it will auto-generate one for you
     *
     * @param name  the release name
     *
     * @return  a copy of this command with the {@code --name} flag appended
     */
    public HelmUpgrade name(String name)
    {
        return withFlags(Argument.of("--name", name));
    }

    /**
     * Specify the template used to name the release.
     *
     * @param template  the template used to name the release
     *
     * @return  a copy of this command with the {@code --name-template} flag appended
     */
    public HelmUpgrade nameTemplate(String template)
    {
        return withFlags(Argument.of("--name-template", template));
    }

    /**
     * Specify the namespace to install the release into. Defaults to the current kube config namespace.
     *
     * @param namespace  the namespace to install the release into.
     *
     * @return  a copy of this command with the {@code --namespace} flag appended
     */
    public HelmUpgrade namespace(String namespace)
    {
        return withFlags(Argument.of("--namespace", namespace));
    }

    /**
     * Prevent hooks from running during install.
     *
     * @return  a copy of this command with the {@code --no-hooks} flag appended
     */
    public HelmUpgrade noHooks()
    {
        return withFlags(Argument.of("--no-hooks"));
    }

    /**
     * Re-use the given name, even if that name is already used, his is unsafe in production.
     *
     * @return  a copy of this command with the {@code --replace} flag appended
     */
    public HelmUpgrade replace()
    {
        return withFlags(Argument.of("--replace"));
    }

    /**
     * Set the chart repository url where to locate the requested chart
     *
     * @param repo  the chart repository url where to locate the requested chart
     *
     * @return  a copy of this command with the {@code --repo} flag appended
     */
    public HelmUpgrade repo(String repo)
    {
        return withFlags(Argument.of("--repo", repo));
    }

    /**
     * Set the values to use to override those in the values file.
     * Value strings are in the form "x=y".
     *
     * @param values  the values to use.
     *
     * @return  a copy of this command with the {@code --set} flag appended
     */
    public HelmUpgrade set(String... values)
    {
        if (values == null || values.length == 0)
        {
            return this;
        }

        String valueString = Arrays.stream(values)
                .collect(Collectors.joining(","));

        return withFlags(Argument.of("--set", valueString));
    }

    /**
     * Set the time in seconds to wait for any individual Kubernetes
     * operation (like Jobs for hooks) (default 300)
     *
     * @param seconds  the timeout in seconds
     *
     * @return  a copy of this command with the {@code --values} flag appended
     */
    public HelmUpgrade timeout(int seconds)
    {
        return withFlags(Argument.of("--timeout", seconds));
    }

    /**
     * Specify values in a YAML file (can specify multiple) (default []).
     *
     * @param valuesFile  the values YAML file
     *
     * @return  a copy of this command with the {@code --values} flag appended
     */
    public HelmUpgrade values(String valuesFile)
    {
        return withFlags(Argument.of("--values", valuesFile));
    }

    /**
     * Specify values in a YAML file (can specify multiple) (default []).
     *
     * @param valuesFile  the values YAML file
     *
     * @return  a copy of this command with the {@code --values} flag appended
     */
    public HelmUpgrade values(File valuesFile)
    {
        return withFlags(Argument.of("--values", valuesFile));
    }

    /**
     * Specify the exact chart version to install. If this is not specified,
     * the latest version is installed.
     *
     * @param  version  the chart version
     *
     * @return  a copy of this command with the {@code --version} flag appended
     */
    public HelmUpgrade version(String version)
    {
        return withFlags(Argument.of("--version", version));
    }

    /**
     * Wait until all Pods, PVCs, Services, and minimum number of Pods of a
     * Deployment are in a ready state before marking the release as successful.
     * Will wait for the number of seconds specified with the {@code --timeout}
     * option {@link #timeout(int)} or the default of 300 seconds.
     *
     * @return  a copy of this command with the {@code --wait} flag appended
     */
    public HelmUpgrade waitForK8s()
    {
        return withFlags(Argument.of("--wait"));
    }

    /**
     * Wait until all Pods, PVCs, Services, and minimum number of Pods of a
     * Deployment are in a ready state before marking the release as successful.
     *
     * @param  seconds  timeout in seconds
     *
     * @return  a copy of this command with the {@code --wait} and {@code --timeout} flags appended
     */
    public HelmUpgrade waitForK8s(int seconds)
    {
        return withFlags(Argument.of("--wait"), Argument.of("--timeout", seconds));
    }

    @Override
    public HelmUpgrade newInstance(String helm, Arguments arguments, Arguments flags, EnvironmentVariables env)
    {
        return new HelmUpgrade(helm, arguments, flags, env);
    }
}
