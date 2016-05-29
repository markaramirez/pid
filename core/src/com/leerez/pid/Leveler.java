package com.leerez.pid;

public class Leveler {

    public static void updateRank() {
        if (pidPrefs.getXP() >= (.1 * pidPrefs.getRank() * pidPrefs.getRank() * pidPrefs.getRank() + 25)) {
            pidPrefs.setRank(pidPrefs.getRank() + 1);
            Leveler.updateRank();
        }
    }

    public static int getNeeded() {
        return (int) ((.1 * (pidPrefs.getRank() + 1) * (pidPrefs.getRank() + 1) * (pidPrefs.getRank() + 1) + 25) - pidPrefs.getXP());
    }

}
