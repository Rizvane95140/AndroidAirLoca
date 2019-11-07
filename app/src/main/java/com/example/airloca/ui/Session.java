package com.example.airloca.ui;

import com.example.airloca.Entities.Personne;

public class Session {
    private static Personne personneConnected;

    public static Personne getPersonneConnected() {
        if(personneConnected == null)
        {
            personneConnected = new Personne();
        }
        return personneConnected;
    }

    public static void setPersonneConnected(Personne personneConnected) {

        Session.personneConnected = personneConnected;
    }

}
