package com.epam.esm.audit;

/**
 * Interface for Save. It defines the operations to be audited.
 *
 * @author Alexander Pishchala
 */
public interface Save {

    /**
     *Method for saving the object.
     *
     * @param object the Object to be audited.
     */
    void save(Object object);
}
