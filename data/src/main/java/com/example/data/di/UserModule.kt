package com.example.data.di

import com.example.data.dataSource.user.UserDataSourceImpl
import com.example.data.dataSourceContract.user.UserDataSource
import com.example.data.repository.user.UserRepositoryImpl
import com.example.domain.repositories.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UserModule {
    @Binds
    abstract fun provideUserRepository(repo: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun provideUserDataSource(dataSource: UserDataSourceImpl): UserDataSource

}