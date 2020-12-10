package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Player {
    private int places;
    private int purses ;
    private boolean inPenaltyBox  = false;
    
    
    public  Player(){
    }


	private boolean didPlayerWin() {
		return !(purses == 6);
	}
}
