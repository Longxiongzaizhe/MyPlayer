package com.hjl.module_main.daodb;

public class DaoManagerFactory {

    private static DaoManagerFactory factory;

    private DaoManagerFactory() {

    }

    public synchronized static DaoManagerFactory getInstance(){
        if (factory == null) factory = new DaoManagerFactory();
        return factory;
    }

    public MediaDaoManager getManager(){
        return MediaDaoManager.getInstance();
    }

    public DaoManager getManager(String type){
        switch (type){
            case DaoManager.MEDIA_ALBUMS :return MediaAlbumsManager.getInstance();
            case DaoManager.MEDIA_AUTHOR :return MediaAuthorManager.get();
            case DaoManager.MEDIA_DAO:return MediaDaoManager.getInstance();
            case DaoManager.MEDIA_LIST:return MediaListManager.getInstance();
            case DaoManager.MEDIA_REL:return MediaRelManager.getInstance();
        }
        return null;
    }




}
