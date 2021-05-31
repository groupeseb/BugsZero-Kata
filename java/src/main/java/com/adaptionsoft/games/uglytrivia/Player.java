package com.adaptionsoft.games.uglytrivia;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//Il me semble bien mieux de faire un objet Player de la sorte contenant
//tous ses attributs, plutôt que de maintenir des arrays dans la classe Game...
//Nous sommes dans un langage orienté objet :)
@Getter
@Builder
public class Player {

    private String name;

    private int place;

    private int purse;

    @Setter
    private boolean inPenaltyBox;

    public void fillPurse(){
        this.purse ++;
    }

    public void move(int howFar){
        this.place += howFar;

        if(this.place > 11){
            this.place -= 12;
        }
    }

    public boolean doIWin(){
        return this.purse >= 6;
    }
}
