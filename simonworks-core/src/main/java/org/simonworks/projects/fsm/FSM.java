/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.fsm;

import org.simonworks.projects.utils.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.BooleanSupplier;

public class FSM implements IFiniteStateMachine {

    private static final Logger LOGGER = LoggerFactory.getLogger(FSM.class);

    private Map<State, List<TransitionWrapper>> transitions;
    private State entryPoint = (ctx) -> {
        LOGGER.info("Entry node!");
    };
    private State currentState;
    private StatesContext statesContext;
    private BooleanSupplier endCondition;

    public FSM(Transition transition, State startState, BooleanSupplier endCondition) {
        this(transition, startState, endCondition, new MapStatesContext());
    }

    public FSM(Transition transition, State startState, BooleanSupplier endCondition, StatesContext statesContext) {
        Assertions.assertNotNull(statesContext, "States Context cannot be null!");
        this.statesContext = statesContext;
        currentState = entryPoint;
        transitions = new HashMap<>();
        registerTransition(currentState, startState, transition);
        this.endCondition = endCondition;
    }

    private class TransitionWrapper implements Transition {
        private Transition delegate;
        private State newState;
        public TransitionWrapper(Transition delegate, State newState) {
            this.delegate = delegate;
            this.newState = newState;
        }

        @Override
        public boolean canApply() {
            return delegate.canApply();
        }

        void tryExecute(StatesContext context) throws StateTransitionException {
            if(canApply()) {
                transition(newState);
            }
        }
    }

    @Override
    public FSM withTransition(State fromState, State toState, Transition transition) {
        registerTransition(fromState, toState, transition);
        return this;
    }

    private void registerTransition(State fromState, State toState, Transition transition) {
        transitions.computeIfAbsent(fromState, state -> new ArrayList<>());
        transitions.get(fromState).add(new TransitionWrapper(transition, toState));
    }

    @Override
    public StatesContext getStatesContext() {
        return statesContext;
    }

    @Override
    public void run() {
        while( isNotComplete() ) {
            transitions.get(currentState).forEach(
                    t -> {
                        try {
                            t.tryExecute(statesContext);
                        } catch (StateTransitionException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
    }

    /**
     * Make a transition from current state to new one and invokes its {@link State#entryAction(StatesContext)}.
     * {@link StatesContext} passed to the method is the current one in this {@link FiniteStateMachine}
     *
     * @param newState The new state to enter
     * @throws StateTransitionException If any error occurs during states transition.
     */
    @Override
    public void transition(State newState) throws StateTransitionException {
        transition(newState, statesContext);
    }

    /**
     * Make a transition from current state to new one and invokes its {@link State#entryAction(StatesContext)}.
     *
     * @param newState The new state to enter.
     * @param context  The context to pass.
     * @throws StateTransitionException If any error occurs during states transition.
     */
    @Override
    public void transition(State newState, StatesContext context) throws StateTransitionException {
        Assertions.assertNotNull(newState, "New State to enter cannot be null");
        if(isComplete()) {
            throw new UnsupportedOperationException("Can't exit from final State");
        }
        currentState.exitAction(context);
        currentState = newState;
        currentState.entryAction(context);
    }

    @Override
    public boolean isComplete() {
        return endCondition.getAsBoolean();
    }

    @Override
    public boolean isStarted() {
        return currentState != entryPoint;
    }

    public static void main(String[] args) {
        Logger l = LoggerFactory.getLogger(FSM.class);
        int[] i = new int[] {0};
        State s1 = (ctx) -> {
            l.debug("State 1, i = {}", i[0]);
            i[0]++;
        };
        State s2 = (ctx) -> {
            l.debug("State 2, i = {}", i[0]);
            i[0]++;
        };
        State s3 = (ctx) -> {
            l.debug("State 3, i = {}", i[0]);
            i[0]++;
        };
        State s4 = (ctx) -> {
            l.debug("State 4, i = {}", i[0]);
            i[0]++;
        };
        State s5 = (ctx) -> {
            l.debug("State 5, i = {}", i[0]);
            i[0]++;
        };
        State s6 = (ctx) -> {
            l.debug("State 6, i = {}", i[0]);
            i[0]++;
        };
        State s7 = (ctx) -> {
            l.debug("State 7, i = {}", i[0]);
            i[0] = 20;
        };
        FSM fsm = new FSM(
                () -> i[0]%2 == 0,
                s1,
                () -> i[0] == 20);
        fsm.withTransition(s1, s2, () -> i[0] == 1);
        fsm.withTransition(s2, s3, () -> i[0] == 2);
        fsm.withTransition(s3, s4, () -> i[0] == 3);
        fsm.withTransition(s4, s5, () -> i[0] == 4);
        fsm.withTransition(s5, s6, () -> i[0] == 5);
        fsm.withTransition(s6, s7, () -> i[0] == 6);
        fsm.withTransition(s7, s1, () -> i[0] == 7);
        fsm.run();
    }
}
