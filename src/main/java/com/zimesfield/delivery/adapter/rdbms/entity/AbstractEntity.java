package com.zimesfield.delivery.adapter.rdbms.entity;

/**
 * Base abstract class for entities which will hold definitions for created, last modified, created by,
 * last modified by attributes.
 */
public interface AbstractEntity<T> {
    public abstract T getId();
}
