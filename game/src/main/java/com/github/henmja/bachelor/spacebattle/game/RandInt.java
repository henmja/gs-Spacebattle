package com.github.henmja.bachelor.spacebattle.game;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

class RandInt {
	private List<Integer> intervalList = new ArrayList<>();

	RandInt(int beg, int end) {
		addInterval(beg, end);
	}

	int getRandom() {
		return intervalList.get(new Random().nextInt(intervalList.size()));
	}

	public void addInterval(int beg, int end) {
		for (int i = beg; i <= end; i++) {
			intervalList.add(i);
		}
	}

}
