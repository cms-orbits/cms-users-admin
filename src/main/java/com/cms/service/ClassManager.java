package com.cms.service;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ClassManager implements Serializable {
    private static final long serialVersionUID = 1L;

    public ClassManager() {
    }

    @PersistenceContext
    protected EntityManager manager;

}
