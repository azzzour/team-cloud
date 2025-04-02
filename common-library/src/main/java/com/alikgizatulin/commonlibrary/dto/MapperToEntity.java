package com.alikgizatulin.commonlibrary.dto;

public interface MapperToEntity<E,D>{
    E toEntity(D dto);
}
