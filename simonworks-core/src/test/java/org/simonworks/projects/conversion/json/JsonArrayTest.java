/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.conversion.json;

import org.junit.jupiter.api.Test;
import org.simonworks.projects.conversion.SimpleBean;

import java.util.Arrays;

class JsonArrayTest {

    SimpleBean[] simones = Arrays.asList(
            new SimpleBean("Simone", "45"),
            new SimpleBean(null, null, 67d)
    ).toArray(new SimpleBean[]{});

    @Test
    void testToString() {
        JsonElement[] jvs = new JsonElement[simones.length];
        for(int i = 0; i<simones.length; i++) {
            jvs[i] = new JsonObject(simones[i]);
        }
    }
}