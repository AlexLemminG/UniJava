package events.eventListeners;

import basicobjects.Cont;
import events.Event;

/**
 * Created by Lemming on 28.07.2014.
 */
public abstract class EventListener {
    Cont owner;

    public void setOwner(Cont owner){
        this.owner = owner;
    }

    public Cont getOwner(){
        return owner;
    }

    public final void handleEvent(Event e){
        System.out.println("Error ДНИЩЕ ЕВЕНТЛИСТЕНЕРА");
    }


}
