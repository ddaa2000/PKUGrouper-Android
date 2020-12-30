package com.e.pkugrouper.Models;

import java.util.List;

public interface IMission extends ISerializable {
    int getID();

    void setID(int _ID);

    String getContent();

    void setContent(String _content);

    String getTitle();

    void setTitle(String _title);

    String getState();

    void setState(String _state);

    int getPublisher();

    void setPublisher(int _publisherID);

    int getSize();

    void setSize(int _size);

    String getPublishTime();

    String getApplicationEndTime();

    String getExecutionStartTime();

    String getExecutionEndTime();

    void setPublishTime(String _publishTime);

    void setApplicationEndTime(String _applicantionEndTime);

    void setExecutionStartTime(String _executionStartTime);

    void setExecutionEndTime(String _executionEndTime);


    List<Integer> getApplicantIDs();

    List<Integer> getMemberIDs();

    List<String> getChannels();

    void setApplicantIDs(List<Integer> _ApplicantIDs);

    void setMemberIDs(List<Integer> _MemberIDs);

    void setChannels(List<String> _Channels);

    boolean isInApplication();
    boolean isFinished();
    boolean isInExecution();


    boolean hasMember(IUser user);
    boolean hasPublisher(IUser user);
    boolean hasApplicant(IUser user);
}
