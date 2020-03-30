/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Samples {

    @Test public void testComputeIfAbsent() {
        Map<String, Map<String, Long>> m = new HashMap<>();

        Arrays.asList("a", "b").forEach(
                s -> {
                    Map<String, Long> stringLongMap = m.computeIfAbsent(s, s1 -> new HashMap<>());
                    Assertions.assertNotNull(stringLongMap);
                    Assertions.assertTrue(stringLongMap.isEmpty());
                }
        );

        Assertions.assertNotNull(m.get("a"));
        Assertions.assertNotNull(m.get("b"));
    }
}
