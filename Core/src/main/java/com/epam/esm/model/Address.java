package com.epam.esm.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Collection;

public class Address
{
    private String city;
    private String street;
    private String building;

    private Collection<Person> tenants;
}