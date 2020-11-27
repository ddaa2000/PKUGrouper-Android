package com.e.pkugrouper.Models;

import java.util.List;

public interface IMission {
    int getID();
    void setID(int _missionID);
    String getContent();
    void setContent(String _content);
    String getTitle();
    void setTitle(String title);

    List<Integer> getApplicantIDs();
    List<Integer> getMemberIDs();

    void setState();
    int getState();
    List<String> getTags();

    int getPublisher();

    int getSize();
    void setSize(int size);
}
