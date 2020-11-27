package com.e.pkugrouper.Models;

import java.util.List;

public class CommonUser  extends User implements ICommonUser{
    @Override
    public List<Integer> getEvaluationIDs() {
        return null;
    }

    @Override
    public List<Integer> getMissionIDs() {
        return null;
    }

    @Override
    public List<Integer> getViolationIDs() {
        return null;
    }

    @Override
    public double getAverageScore() {
        return 0;
    }

    @Override
    public void setAverageScore(double _averageScore) {

    }
}
