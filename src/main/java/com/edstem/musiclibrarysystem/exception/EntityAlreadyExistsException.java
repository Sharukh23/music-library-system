package com.edstem.musiclibrarysystem.exception;

import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {
    private final String entity;
    private final int id;

    public EntityAlreadyExistsException(String entity) {
        super(entity + " already exists");
        this.entity = entity;
        this.id = 0;
    }
}
