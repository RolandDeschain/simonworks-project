/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.fsm;

import java.util.function.Predicate;

public class ExitAction<V> {

    private State nextState;
    private Predicate<V> condition;

    public ExitAction(State nextState, Predicate<V> condition) {
        this.nextState = nextState;
        this.condition = condition;
    }

    public void execute(StatesContext ctx, V target) throws StateTransitionException {
        if(condition.test(target)) {
            nextState.entryAction(ctx);
        }
    }
}
