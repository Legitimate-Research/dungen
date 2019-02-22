package com.cd;

/**
 * Created by PlanetCookieX on 5/21/2017.
 */
public class BossAttack {
    private Runnable func;
    private double runTime;
    private double currTime;
    private BossAttack next;

    public BossAttack(Runnable func, double runTime, double currTime, BossAttack next) {
        this.func = func;
        this.runTime = runTime;
        this.currTime = currTime;
        this.next = next;
    }

    public  BossAttack(){

    }

    public Runnable getFunc() {
        return func;
    }

    public void setFunc(Runnable func) {
        this.func = func;
    }

    public double getRunTime() {
        return runTime;
    }

    public void setRunTime(double runTime) {
        this.runTime = runTime;
    }

    public double getCurrTime() {
        return currTime;
    }

    public void setCurrTime(double currTime) {
        this.currTime = currTime;
    }

    public BossAttack getNext() {
        return next;
    }

    public void setNext(BossAttack next) {
        this.next = next;
    }
}
