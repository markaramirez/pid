package com.leerez.pid;

public class Leveler {

	public static final int[] caps = {-1, 0, 100, 250, 350, 450, 550, 650, 850, 1050, 1250, 1550, 1850, 2150, 2450, 2750, 3055, 3365, 3680, 4000, 4325, 4675, 5025, 5375, 5725, 6075, 6455, 6855, 7255, 7655, 8155};
	//................................0....1...2...3.....4....5.....6...7...8....9......10...11.....12...13.....14....15....16....17....18....19....20...21.....22...23.....24...25...26.....27.....28....29...30
	
	public static void updateRank() {
		for(int i = 0;i <= 30; i++) {
			if(pidPrefs.getXP() >= 8155) {
				pidPrefs.setRank(30);
			}
			else if(pidPrefs.getXP() >= caps[i] && pidPrefs.getXP() < caps[i + 1]) {
				pidPrefs.setRank(i);
			}
		}
		
	}
	
	public static int getNeeded() {
		return caps[pidPrefs.getRank() + 1] - pidPrefs.getXP();
	}

}
