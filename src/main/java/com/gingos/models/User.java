package com.gingos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class User {
    public int id;
    public String firstname;
    public String lastname;
    public String email;
    public String birthDate;
}