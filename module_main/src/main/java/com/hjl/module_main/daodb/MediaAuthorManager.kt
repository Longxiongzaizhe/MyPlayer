package com.hjl.module_main.daodb

import com.hjl.commonlib.base.BaseApplication
import com.hjl.module_main.module.ILocalModuleAppImpl


class MediaAuthorManager {

    private var dao:MediaAuthorEntityDao?  = null

    private constructor()

    init {
        val daoSession = ILocalModuleAppImpl().initDaoSession(BaseApplication.getApplication())
        dao = daoSession.mediaAuthorEntityDao
    }


    fun insert(entity: MediaAuthorEntity){
        dao!!.insert(entity)
    }

    fun insert(name : String){
        dao!!.insert(MediaAuthorEntity(null,name,""))
    }

    fun update(entity: MediaAuthorEntity){
        dao!!.update(entity)
    }

    fun getAll():List<MediaAuthorEntity>{
        return dao!!.loadAll()
    }

    companion object{
        private var instance : MediaAuthorManager? = null
        get() {
            if (field == null){
                instance = MediaAuthorManager()
            }
            return field
        }

        fun get() : MediaAuthorManager{
            return instance!!
        }
    }

}