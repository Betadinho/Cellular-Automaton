package com.Life;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class CA {
    private static int generation = 0;
    private static int[] cells;

    CA() {
        //running loop with counter
        cells = new int[] { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                            0, 0, 0, 1, 0, 0, 0,
                            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        };
        TimerTask updateBoard = new TimerTask() {
            //Create int array containing the states of a cell, as 0 or 1
            
            @Override
            public void run() {
                cells = generate(cells);
            }
        };
    
        //Run scheduled task to update board every n seconds
        Timer updateTimer = new Timer();
        updateTimer.schedule(updateBoard, new Date(), 100);
    }
    
    //CA with randomly generated initial states
    CA(boolean random, int length) {
        //running loop with counter
        cells = new int[length];
        initRandom(cells);
        TimerTask updateBoard = new TimerTask() {
            //Create int array containing the states of a cell, as 0 or 1
            
            @Override
            public void run() {
                cells = generate(cells);
            }
        };
    
        //Run scheduled task to update board every n seconds
        Timer updateTimer = new Timer();
        updateTimer.schedule(updateBoard, new Date(), 100);
    }

    private static void initRandom(int[] cells) {
        //Init cells array with random states for each cell
        for( int i = 0; i <= cells.length - 1; i++) {
            
            cells[i] = (int)Math.round(Math.random());
        }
    }
    
    private static int[] generate(int[] cells) {
        //Print Board in its beginning state before evaluating new generations
        if(generation == 0) {
            printBoard(cells);
            generation++;
            return cells;
        } else {
            // Array representing states of next generation (has to have the same length as original cells array)
            int[] cellsNew = new int[cells.length];
            int neighLeft, neighRight;
                
            /*  Loop through cells array, get each cells neighbors values
                and fill new Array with cell states conform with a specified set of rules */
            for(int i = 1; i < cells.length - 1; i++) {
                //Assign left and right left to be used in evaluation
                neighLeft = cells[i - 1];
                neighRight = cells[i + 1];
                cellsNew[i] = evaluateByRuleSet(neighLeft, cells[i], neighRight);
            }
            //Assign new state to cells
            cells = cellsNew;
            generation++;
            
            printBoard(cells);
            return cells;
        }
    }
    
    private static int evaluateByRuleSet(int neighLeft, int cell, int neighRight) {
        // 000, 001, 010, 011, 100, 101, 110, 111 - possible combinations of a cell and its left and right neighbour
        // Rules: 000=0, 001=1 010=0 011=1, 100=1, 101=0, 110=1, 111=0
        int[] ruleSet = {0,1,0,1,1,0,1,0};
        String cellSet = "" + neighLeft + cell + neighRight;
        
        //convert cell config to regular binary int to use as index of the rule to apply
        int index = Integer.parseInt(cellSet, 2);
        return ruleSet[index];
        
    }
    
    private static void printBoard(int[] c) {
        //Display single cells as # or a whitespace depending on its state
        for(int i : c) {
            if(i == 1) {
                System.out.print("#");
            } else {
                System.out.print("-");
            }
        }
        System.out.print("\n");
    }
}
