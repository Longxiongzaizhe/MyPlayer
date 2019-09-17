package com.hjl.module_main.daodb

import com.hjl.commonlib.base.BaseApplication
import com.hjl.module_main.module.ILocalModuleAppImpl
import org.greenrobot.greendao.query.WhereCondition


class MediaAuthorManager : DaoManager<MediaAuthorEntity>{
    override fun query(cond: WhereCondition?, vararg condMore: WhereCondition?): MutableList<MediaAuthorEntity> {
        return dao?.queryBuilder()?.where(cond, *condMore)?.list()!!
    }


    private var dao:MediaAuthorEntityDao?  = null


    init {
        val daoSession = ILocalModuleAppImpl().initDaoSession(BaseApplication.getApplication())
        dao = daoSession.mediaAuthorEntityDao
    }


    override fun insert(entity: MediaAuthorEntity){
        dao!!.insert(entity)
    }

    fun insert(name : String){
        dao!!.insert(MediaAuthorEntity(null,name,""))
    }

    override fun update(entity: MediaAuthorEntity){
        dao!!.update(entity)
    }

    override fun query(id: Long): MediaAuthorEntity {
        return dao!!.queryBuilder().where(MediaAlbumsEntityDao.Properties.Id.eq(id)).unique()
    }

    override fun loadAll(): MutableList<MediaAuthorEntity> {
        return dao!!.loadAll()
    }

    override fun delete(id: Long) {
        dao!!.deleteByKey(id)
    }

    override fun delete(entity: MediaAuthorEntity?) {
        dao!!.delete(entity)
    }

    override fun deleteAll() {
        dao!!.deleteAll()
    }

    companion object{
        private var instance : MediaAuthorManager? = null
        get() {
            if (field == null){
                instance = MediaAuthorManager()
            }
            return field
        }

        @JvmStatic
        fun get() : MediaAuthorManager{
            return instance!!
        }
    }

}