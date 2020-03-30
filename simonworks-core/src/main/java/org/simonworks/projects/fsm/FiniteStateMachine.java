/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.fsm;

import org.simonworks.projects.utils.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Finite state machine is the object responsible to orchestrate transitions among {@link State}'s.
 */
public class FiniteStateMachine implements IFiniteStateMachine {

    private static final Logger LOGGER = LoggerFactory.getLogger(FiniteStateMachine.class);

    private State startState;
    private State currentState;
    private State endState;
    private StatesContext statesContext;

    public FiniteStateMachine() {
        this(new MapStatesContext());
    }

    public FiniteStateMachine(StatesContext context) {
        Assertions.assertNotNull(context, "States Context cannot be null!");
        this.statesContext = context;
        startState = (ctx) -> LOGGER.trace("Start states fires entryAction()");
        endState = (ctx) -> {
            LOGGER.trace("Final state entered");
            statesContext.clear();
        };
        currentState = startState;
    }

    @Override
    public FSM withTransition(State fromState, State toState, Transition transition) {
        return null;
    }

    @Override
    public StatesContext getStatesContext() {
        return statesContext;
    }

    @Override
    public void run() {

    }

    @Override
    public void transition(State newState) throws StateTransitionException {
        transition(newState, statesContext);
    }

    @Override
    public void transition(State newState, StatesContext context) throws StateTransitionException {
        Assertions.assertNotNull(newState, "New State to enter cannot be null");
        if(isComplete()) {
            throw new UnsupportedOperationException("Can't exit from final State");
        }
        /* TODO test that changing currentState with newState should not change this value in context */
        statesContext.put(StatesContext.PREVIOUS_STATE_KEY, currentState);
        currentState = newState;
        currentState.entryAction(context);
    }

    @Override
    public boolean isComplete() {
        return isState(endState);
    }

    @Override
    public boolean isStarted() {
        return !isState(startState);
    }

    protected boolean isState(State state) {
        return currentState == state;
    }

    @Override
    public void complete() {
        try {
            transition(endState);
        } catch (StateTransitionException e) {
            /**
             * Should never happen!
             */
            LOGGER.error("Unexpected exception occurs ", e);
        }
    }
}
