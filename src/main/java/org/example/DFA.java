package org.example;

import java.util.HashMap;
import java.util.Map;

public class DFA {
    // Define the DFA states
    enum State {
        START, ONE, FINAL
    }

    // Define DFA class variables
    private final State startState;
    private State currentState;
    private final Map<State, Map<Character, State>> transitions;
    private final State finalState;

    // Constructor to initialize DFA
    public DFA() {
        this.startState = State.START;
        this.finalState = State.FINAL;
        this.currentState = startState;

        // Initialize transitions
        transitions = new HashMap<>();

        // Add transitions for START state
        Map<Character, State> startTransitions = new HashMap<>();
        startTransitions.put('0', State.ONE);
        startTransitions.put('1', State.START);
        transitions.put(State.START, startTransitions);

        // Add transitions for ONE state
        Map<Character, State> oneTransitions = new HashMap<>();
        oneTransitions.put('0', State.ONE);
        oneTransitions.put('1', State.FINAL);
        transitions.put(State.ONE, oneTransitions);

        // Add transitions for FINAL state (self-loop on both 0 and 1)
        Map<Character, State> finalTransitions = new HashMap<>();
        finalTransitions.put('0', State.ONE);
        finalTransitions.put('1', State.START);
        transitions.put(State.FINAL, finalTransitions);
    }

    // Method to process the input string
    public boolean isAccepted(String input) {
        currentState = startState;  // Reset to start state for new input
        for (char c : input.toCharArray()) {
            if (!transitions.get(currentState).containsKey(c)) {
                return false;  // Invalid character for the current state
            }
            currentState = transitions.get(currentState).get(c);  // Transition to next state
        }
        return currentState == finalState;  // Check if in accepting state
    }

    public static void main(String[] args) {
        DFA dfa = new DFA();

        // Test cases
        String[] testStrings = { "01", "001", "101", "1001", "010" };
        for (String test : testStrings) {
            System.out.println("Input: " + test + " -> Accepted: " + dfa.isAccepted(test));
        }
    }
}
