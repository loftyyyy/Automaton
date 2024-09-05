package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DFA2 {
    public final Map<STATE, Map<Character, STATE>> transition;
    public final STATE startState;
    public final STATE finalState;
    public STATE currentState;

    enum STATE{

        START, ONE, FINAL

    }


    public DFA2(){
        this.startState = STATE.START;
        this.finalState = STATE.FINAL;
        this.currentState = startState;
        transition = new HashMap<>();
        //START
        Map<Character,STATE> startTransition = new HashMap<>();
        startTransition.put('0',STATE.ONE);
        startTransition.put('1', STATE.START);
        transition.put(STATE.START, startTransition);

        //ONE
        Map<Character, STATE> oneTransition = new HashMap<>();
        oneTransition.put('0', STATE.ONE);
        oneTransition.put('1',STATE.FINAL);
        transition.put(STATE.ONE, oneTransition);

        //FINAL
        Map<Character, STATE> finalTransition = new HashMap<>();
        finalTransition.put('0', STATE.ONE);
        finalTransition.put('1', STATE.START);
        transition.put(STATE.FINAL, finalTransition);





    }
    public boolean isAccepted(String input){
        currentState = startState;
        char[] inputs = input.toCharArray();

        for(char i : inputs){
            if(!transition.get(currentState).containsKey(i)){
                System.out.println("Doesn't exist");
                return false;
            }
            currentState = transition.get(currentState).get(i);
        }
        return currentState == finalState;

    }
    public static void main(String[] args){
        DFA2 dfa = new DFA2();
        System.out.println("DFA Code that accepts any string that ends with '01' ");
        Scanner userScanner = new Scanner(System.in);
        System.out.print("Give a SSV list of DFA input ");
        String givenStringInput = userScanner.nextLine();

        String[] inputList = givenStringInput.split(" ");

        for (int i = 0; i < inputList.length; i++){
            System.out.println("Input: " + inputList[i] + " -> Accepted: " + dfa.isAccepted(inputList[i]));
//            dfa.isAccepted(inputList[i]);
        }




    }
}
