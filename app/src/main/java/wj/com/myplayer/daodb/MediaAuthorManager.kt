package wj.com.myplayer.daodb

import wj.com.myplayer.config.MainApplication

class MediaAuthorManager {

    var dao  = MainApplication.get().daoSession.mediaAuthorEntityDao

    private constructor()


    fun insert(entity: MediaAuthorEntity){
        dao.insert(entity)
    }

    fun insert(name : String){
        dao.insert(MediaAuthorEntity(null,name,""))
    }

    fun update(entity: MediaAuthorEntity){
        dao.update(entity)
    }

    fun getAll():List<MediaAuthorEntity>{
        return dao.loadAll()
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