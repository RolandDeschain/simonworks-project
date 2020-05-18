/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.simonworks.projects.annotations.Singleton;
import org.simonworks.projects.context.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@WebResource(name = "/examples", version = @Version(major = 1, minor = 1, patch = 1, description = "first release"))
@Singleton(name = "examplesWebResource")
public class ExampleWebResource {

    private List<ExampleBean> repo = new ArrayList<>();

    class ExampleBean {
        String name;
        ExampleBean(String name) { this.name = name; }
    }

    @MethodMapping(
            path = "/names"
    )
    public List<ExampleBean> getAll() {
        return repo;
    }

    @MethodMapping(
            path = "/examples/{name}"
    )
    public @Serialize(serializerName = "jsonSerializer") List<ExampleBean> getByNameOrPrefix(
            @PathParam("name") String name,
            @QueryParam("prefix") String prefix) {
        return StreamSupport
                .stream(repo.spliterator(), false)
                .filter(exampleBean -> exampleBean.name.startsWith(prefix) || exampleBean.name.equals(name))
                .collect(Collectors.toList());
    }

    @MethodMapping(
        verb = HttpVerb.POST
    )
    public void insert(
            @Deserialize(deserializerName = "jsonDeserializer") ExampleBean example) {
        repo.add(example);
    }

    @MethodMapping(
            verb = HttpVerb.GET,
            path = "/names/{prefix}/{suffix}"
    )
    public List<ExampleBean> getByPrefixAndSuffix(
            @PathParam("prefix") String prefix,
            @PathParam("suffix") String suffix) {
        return StreamSupport
                .stream(repo.spliterator(), false)
                .filter(e -> e.name.startsWith(prefix) && e.name.endsWith("suffix"))
                .collect(Collectors.toList());
    }
}