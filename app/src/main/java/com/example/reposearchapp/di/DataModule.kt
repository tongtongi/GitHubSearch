package com.example.reposearchapp.di

import com.example.reposearchapp.data.DataSource
import com.example.reposearchapp.data.DataSourceImpl
import com.example.reposearchapp.util.ApolloWrapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {

    @Singleton
    @Provides
    @JvmStatic
    fun provideDataSource(apolloWrapper: ApolloWrapper): DataSource = DataSourceImpl(apolloWrapper)
}