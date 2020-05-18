package org.simonworks.projects.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssertionsTest {

    @Test
    void assertNotNull() {
        Assertions.assertNotNull(new Object());
    }

}