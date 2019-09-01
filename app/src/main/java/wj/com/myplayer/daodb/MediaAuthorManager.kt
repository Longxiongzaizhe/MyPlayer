package wj.com.myplayer.daodb

import wj.com.myplayer.config.MainApplication

class MediaAuthorManager {

    var manager : MediaAuthorManager? = null
    var dao : MediaAuthorEntityDao? = null

    init {
        dao = MainApplication.get().daoSession.mediaAuthorEntityDao
    }

    private constructor()





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