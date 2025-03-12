package com.alikgizatulin.teamapp.mapper;

public interface MapperToDto<D,E>{
    D toDto(E entity);
}
