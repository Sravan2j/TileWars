package com.games.tilewars;

import android.app.Application;

public class GlobalClass extends Application{
     
    private boolean sound=true;
    
    public boolean soundIsOn() {         
        return sound;
    }
     
    public void turnInSound(boolean status) {        
       sound = status;         
    }
     
}