package com.example.routee_commerce.userRepository

import com.example.routee_commerce.data.userDataSource.UserDataSource
import com.example.routee_commerce.data.userDataSourceImpl.UserDataSourceImpl
import com.example.routee_commerce.data.userRepositoryImpl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UserRepositoryModule {
    @Binds
    abstract fun provideUserRepository(repo: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun provideUserDataSource(dataSource: UserDataSourceImpl): UserDataSource

}