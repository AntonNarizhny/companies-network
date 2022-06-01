package ru.wanderer.companies.domain.mappers;

public interface Mapper <F, T> {

    default T map (F object) {
        return (T) object;
    }

    default T map(F fromObject, T toObject) {
        return toObject;
    }
}
