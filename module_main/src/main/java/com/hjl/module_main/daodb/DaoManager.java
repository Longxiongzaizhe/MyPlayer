package com.hjl.module_main.daodb;

import java.util.List;

public interface DaoManager<T> {

    T query(long id);
    List<T> loadAll();
    void delete(long id);
    void delete(T entity);
    void insert(T entity);
    void deleteAll();
    void update(T entity);


    String MEDIA_DAO = "meidia_dao";
    String MEDIA_AUTHOR = "author";
    String MEDIA_ALBUMS = "albums";
    String MEDIA_LIST = "list";
    String MEDIA_REL = "rel";

}
