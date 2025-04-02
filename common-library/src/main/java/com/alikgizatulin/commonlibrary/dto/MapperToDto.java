package com.alikgizatulin.commonlibrary.dto;

public interface MapperToDto<E,D>{
    D toDto(E entity);
}
