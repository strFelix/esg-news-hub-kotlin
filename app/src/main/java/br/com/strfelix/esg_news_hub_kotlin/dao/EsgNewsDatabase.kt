package br.com.strfelix.esg_news_hub_kotlin.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.strfelix.esg_news_hub_kotlin.model.User


@Database(entities = [User::class], version = 1)
abstract class EsgNewsDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{
        private lateinit var instance: EsgNewsDatabase

        fun getDatabase(context: Context): EsgNewsDatabase{
            if (!::instance.isInitialized){
                instance = Room
                    .databaseBuilder(
                        context,
                        EsgNewsDatabase::class.java,
                        "news_db"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration(true)
                    .build()
            }
            return instance
        }
    }
}