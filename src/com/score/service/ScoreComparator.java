package com.score.service;

import java.util.Comparator;

import com.score.bean.ScoreInterface;

public class ScoreComparator<T extends ScoreInterface> implements Comparator<T>
{
	@Override
	public int compare(T o1, T o2) {
		return -o1.getValue().compareTo(o2.getValue());
	}
}
