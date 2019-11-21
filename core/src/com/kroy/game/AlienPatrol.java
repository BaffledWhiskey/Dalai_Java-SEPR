package com.kroy.game;

public class AlienPatrol extends Unit{

    boolean hunting;
    Point[] patrolRoute;

    public AlienPatrol(boolean hunting, Point[] patrolRoute){
        this.hunting = hunting;
        this.patrolRoute = patrolRoute;
    }

    /**
     *
     * @param fireStation the fire station that the patrol is to attack
     */
    public void attackFireStation(FireStation fireStation){

    }

    /**
     *
     * @param fireStation the fire station that the patrol is to hunt
     */
    public void huntFireStation(FireStation fireStation){

    }

}
