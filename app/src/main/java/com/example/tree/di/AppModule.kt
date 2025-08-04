package com.example.tree.di

import android.content.Context
import androidx.room.Room
import com.example.tree.data.dao.NodeDao
import com.example.tree.data.db.TreeDatabase
import com.example.tree.data.repository.TreeRepositoryImpl
import com.example.tree.domain.repository.TreeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    abstract fun bindTreeRepository(impl: TreeRepositoryImpl): TreeRepository

    companion object {
        @Provides
        @Singleton
        fun provideTreeDatabase(context: Context): TreeDatabase {
            return Room.databaseBuilder(
                context,
                TreeDatabase::class.java,
                "tree_database.db"
            ).build()
        }

        @Provides
        fun provideNodeDao(db: TreeDatabase): NodeDao = db.nodeDao()
    }
}
