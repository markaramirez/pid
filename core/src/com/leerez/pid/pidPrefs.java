package com.leerez.pid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class pidPrefs {

	private static final String PREFS_NAME = "PID";
	private static final String HIGH_SCORE = "high_score";
	private static final String RANK = "rank";
	private static final String XP = "xp";
	private static final String MUSIC = "music";
	private static final String SOUND = "sound";

	public pidPrefs() {

	}

	private static Preferences preferences;

	protected static Preferences getPrefs() {
		if (preferences == null) {
			preferences = Gdx.app.getPreferences(PREFS_NAME);
		}
		return preferences;
	}

	public static int getHighscore() {
		return getPrefs().getInteger(HIGH_SCORE, 0);
	}

	public static void setHighscore(int score) {
		getPrefs().putInteger(HIGH_SCORE, score);
		getPrefs().flush();
	}
	
	public static int getRank() {
		return getPrefs().getInteger(RANK, 1);
	}
	
	public static void setRank(int rank) {
		getPrefs().putInteger(RANK, rank);
		getPrefs().flush();
	}
	
	public static int getXP() {
		return getPrefs().getInteger(XP, 0);
	}
	
	public static void setXP(int xp) {
		getPrefs().putInteger(XP, xp);
		getPrefs().flush();
	}
	
	public static boolean getMusicPref() {
		return getPrefs().getBoolean(MUSIC, true);
	}
	
	public static void toggleMusic() {
		if(getMusicPref())
		{
			getPrefs().putBoolean(MUSIC, false);
			getPrefs().flush();
		}
		else
		{
			getPrefs().putBoolean(MUSIC, true);
			getPrefs().flush();
		}
	}
	
	public static boolean getSoundPref() {
		return getPrefs().getBoolean(SOUND, true);
	}
	
	public static void toggleSound() {
		if(getSoundPref())
		{
			getPrefs().putBoolean(SOUND, false);
			getPrefs().flush();
		}
		else
		{
			getPrefs().putBoolean(SOUND, true);
			getPrefs().flush();
		}
	}
}
