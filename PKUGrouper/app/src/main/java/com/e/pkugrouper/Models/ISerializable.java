package com.e.pkugrouper.Models;

public interface ISerializable {
    String toJSON();
    void loadFromJSON(String JSONString);
}
