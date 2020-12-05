package com.e.pkugrouper.Models;

import java.util.List;

public interface ICommonUser extends IUser{
    List<Integer> getEvaluationIDs();
    List<Integer> getMissionIDs();
    List<Integer> getViolationIDs();
    double getAverageScore();
    void setAverageScore(double _averageScore);


}
