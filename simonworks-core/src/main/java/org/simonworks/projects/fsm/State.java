/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.fsm;

/**
 * Object representing am atomic State for a Finite State Machine.
 */
@FunctionalInterface
public interface State {

    /**
     * Executes the action associate to the entry event raised when a transition enters this state.
     *
     * @param context
     *  Context that shares information among states.
     * @throws StateTransitionException
     *  If any error occurs
     */
    void entryAction(StatesContext context) throws StateTransitionException;

    /**
     * Executes the action associate to the exit event raised when a transition exits this state.
     *
     * @param context
     *  Context that shares information among states.
     * @throws StateTransitionException
     *  If any error occurs
     */
    default void exitAction(StatesContext context) throws StateTransitionException {
    }
}
