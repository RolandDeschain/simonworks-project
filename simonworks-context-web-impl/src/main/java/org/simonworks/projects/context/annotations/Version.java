/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The version to which a {@link WebResource} belongs.
 * {@link Version} takes the format major.minor.patch and must come with a description about the changes introduced by this version.
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Version {

    /**
     * The major version. This makes incompatible changes to the API.
     * @return
     *  The major version
     */
    short major();

    /**
     * The minor version. This version introduces new functionality in a backward compatibility manner.
     * @return
     *  The minor version
     */
    short minor();

    /**
     * The patch version. This version introduces bug fixes in a backward compatibility manner.
     * @return
     */
    short patch();

    /**
     * Description about the changes introduced by this new version.
     * @return
     *  Description about the changes introduced by this new version.
     */
    String description();
}
