/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.fsm;

public interface IFiniteStateMachine {
    FSM withTransition(State fromState, State toState, Transition transition);

    StatesContext getStatesContext();

    void run();

    /**
     * Make a transition from current state to new one and invokes its {@link State#entryAction(StatesContext)}.
     * {@link StatesContext} passed to the method is the current one in this {@link FiniteStateMachine}
     *
     * @param newState
     *  The new state to enter
     * @throws StateTransitionException
     *  If any error occurs during states transition.
     */
    void transition(State newState) throws StateTransitionException;

    /**
     * Make a transition from current state to new one and invokes its {@link State#entryAction(StatesContext)}.
     *
     * @param newState
     *  The new state to enter.
     * @param context
     *  The context to pass.
     * @throws StateTransitionException
     *  If any error occurs during states transition.
     */
    void transition(State newState, StatesContext context) throws StateTransitionException;

    boolean isComplete();

    default boolean isNotComplete() {
        return !isComplete();
    }

    boolean isStarted();

    default void complete() {}
}
