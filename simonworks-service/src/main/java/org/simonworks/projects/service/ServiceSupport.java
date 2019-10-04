/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.service;

import org.simonworks.projects.dao.DaoSupport;
import org.simonworks.projects.dao.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface ServiceSupport {

    <T>  Stream<T> findPage(Pageable pageable);

}
