package org.example;

import java.util.HashMap;
import java.util.Map;

public class DFA3 {
    public enum state {

        initial, middle, finalState

    }
    private final Map<state, Map<Character, state>> transition;
    private final state startState;
    private final state endState;
    private state currentState;

    public DFA3(){
        this.startState = state.initial;
        this.endState = state.finalState;
        this.currentState = startState;

        transition = new HashMap<>();


        //Initial State
        Map<Character, state> initialState = new HashMap<>();



    }







}
