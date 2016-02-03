package com.oracle.tools.runtime.remote.java.options;

import com.oracle.tools.Options;
import com.oracle.tools.runtime.remote.options.Deployment;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author jk 2016.02.03
 */
public class JavaDeploymentTest
{
    @Test
    public void shouldWorkAsAnOption() throws Exception
    {
        Deployment deployment = JavaDeployment.automatic();
        Options    options    = new Options(deployment);

        assertThat(options.get(Deployment.class), is(deployment));
    }

}
