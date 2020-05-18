/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.fsm;

/**
 * Combines {@link State} and {@link FiniteStateMachine} behaviors. In particular, entering a {@link FsmState} cause
 * the FSM to start executing.
 */
public interface FsmState extends State {

    FiniteStateMachine getFsm();

    State getStartSubState();

    default void entryAction() throws StateTransitionException {
        FiniteStateMachine fsm = getFsm();
        StatesContext ctx = fsm.getStatesContext();
        fsm.transition( getStartSubState(), ctx );
    }

    @Override
    default void entryAction(StatesContext context) throws StateTransitionException {
        getFsm().transition( getStartSubState(), context);
    }
}
