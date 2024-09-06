import java.util.*;

public class Jornadal {

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        while (true) {
            System.out.print("Enter 'DFA' to use DFA, 'NFA' to use NFA, or 'EXIT' to exit: ");
            String choice = userInput.nextLine().trim().toUpperCase();

            if (choice.equals("EXIT")) {
                System.out.println("Exiting the program.");
                break;
            }

            if (choice.equals("DFA")) {
                DFA dfa = new DFA();
                System.out.println("This DFA accepts strings ending with '01' ");
                System.out.print("Enter the strings that you want to test (SSV): ");
                String userString = userInput.nextLine();
                String[] stringList = userString.split(" ");

                for(String s : stringList) {
                    System.out.println("String input: " + s + " Accepted: " + dfa.isAccepted(s));
                }

            } else if (choice.equals("NFA")) {
                NFA nfa = new NFA();
                System.out.println("This NFA accepts strings ending with '01' ");
                System.out.print("Enter the strings that you want to test (SSV): ");
                String userString = userInput.nextLine();
                String[] stringList = userString.split(" ");

                for(String s : stringList) {
                    System.out.println("String input: " + s + " Accepted: " + nfa.isAccepted(s));
                }

            } else {
                System.out.println("Invalid choice. Please enter 'DFA', 'NFA', or 'EXIT'.");
            }
        }

        userInput.close();
    }




}

// Deterministic Finite Automaton
class DFA {
    public final Map<State, Map<Character, State>> transition;

    public enum State {
        initial, middle, finalState;
    }


    public State currentState;
    public final State startState;
    public final State endState;

    // Constructor for Deterministic Automaton
    public DFA() {


        this.startState = State.initial;
        this.endState = State.finalState;
        this.currentState = startState;
        transition = new HashMap<>();

        // One State
        Map<Character, State> firstTransition = new HashMap<>();
        firstTransition.put('0',State.middle);
        firstTransition.put('1', State.initial);
        transition.put(State.initial, firstTransition);

        // Middle State
        Map<Character, State> middleTransition = new HashMap<>();
        middleTransition.put('0', State.middle);
        middleTransition.put('1', State.finalState);
        transition.put(State.middle, middleTransition);

        // End State
        Map<Character, State> finalTransition = new HashMap<>();
        finalTransition.put('0', State.middle);
        finalTransition.put('1', State.initial);
        transition.put(State.finalState, finalTransition);

    }

    public boolean isAccepted(String input) {
        currentState = startState;
        char[] string = input.toCharArray();

        for(char i : string) {
            if(!transition.get(currentState).containsKey(i)) {
                return false;
            }
            currentState = transition.get(currentState).get(i);
        }
        return currentState == endState;

    }


}

// Non-deterministic Finite Automaton
class NFA {

    public final Map<State, Map<Character, Set<State>>> transition;

    public enum State {
        initial, middle, finalState;
    }

    public Set<State> currentStates;
    public final State startState;
    public final State endState;

    public NFA() {
        this.startState = State.initial;
        this.endState = State.finalState;
        this.currentStates = new HashSet<>();
        this.currentStates.add(startState);
        transition = new HashMap<>();

        // Define the transitions allowing multiple transitions for some inputs
        // Initial State (q0): On input '0' it can transition to both 'middle' or 'initial', simulating non-determinism.
        Map<Character, Set<State>> initialTransition = new HashMap<>();
        initialTransition.put('0', new HashSet<>(Arrays.asList(State.middle, State.initial)));
        initialTransition.put('1', new HashSet<>(Collections.singletonList(State.initial))); // Go back to itself on '1'
        transition.put(State.initial, initialTransition);

        // Middle State (q1): On input '1', it transitions to the final state (q2).
        Map<Character, Set<State>> middleTransition = new HashMap<>();
        middleTransition.put('0', new HashSet<>(Collections.singletonList(State.middle))); // Stay in middle state on '0'
        middleTransition.put('1', new HashSet<>(Collections.singletonList(State.finalState))); // Go to final state on '1'
        transition.put(State.middle, middleTransition);

        // Final State (q2): Cycle transitions to make it behave like a looping automaton.
        Map<Character, Set<State>> finalTransition = new HashMap<>();
        finalTransition.put('0', new HashSet<>(Collections.singletonList(State.middle)));
        finalTransition.put('1', new HashSet<>(Collections.singletonList(State.initial)));
        transition.put(State.finalState, finalTransition);


        // How I implemented the non-determinism part:

        //The non-determinism in this NFA is primarily implemented in the transition
        // structure and the way multiple states are handled simultaneously.
        // Specifically, the transition function allows a single input to transition to multiple states.
        // This is evident in the transition map for the initialState, where on input '0', the NFA can
        // transition to both the initialState and the middleState.
        // This behavior is facilitated by the use of a Set<State> to represent the current states,
        // allowing the NFA to track multiple active states at once. As the input string is processed,
        // the automaton explores all possible next states for each current state, gathering potential next states into a new set
        // (nextStates.addAll()), effectively allowing the NFA to explore multiple paths in parallel.
        // This is a key feature of non-determinism, where the automaton doesn't follow a single path but
        // rather explores multiple possible paths simultaneously, accepting the input if any path leads to the final state.
    }

    public boolean isAccepted(String input) {
        currentStates = new HashSet<>();
        currentStates.add(startState);

        // Process the input string character by character
        for (char c : input.toCharArray()) {
            Set<State> nextStates = new HashSet<>();
            for (State state : currentStates) {
                if (transition.get(state).containsKey(c)) {
                    // Add all possible next states
                    nextStates.addAll(transition.get(state).get(c));
//                    System.out.println("Transition on input '" + c + "': " + state + " --> " + transition.get(state).get(c));
                }
            }
            currentStates = nextStates;
        }

        // Check if we end in the final state
        return currentStates.contains(endState);
    }

}