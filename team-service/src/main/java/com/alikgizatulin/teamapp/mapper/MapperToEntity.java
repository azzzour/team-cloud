package com.alikgizatulin.teamapp.mapper;


public interface MapperToEntity<D,E> {

    E toEntity(D dto);
}
