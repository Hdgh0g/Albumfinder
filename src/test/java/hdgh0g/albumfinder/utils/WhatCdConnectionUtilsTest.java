package hdgh0g.albumfinder.utils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class WhatCdConnectionUtilsTest {

    @Test
    public void testWrongUsernameAndLogin() {
        boolean connected = WhatCdConnectionUtils.connect("randomUser", "randomPassword");
        assertThat("not connected", connected, is(false));
        assertThat("not connected two", WhatCdConnectionUtils.isConnected(), is(false));
        assertThat("user is null", WhatCdConnectionUtils.getLoggedInUser(), nullValue());
    }
}