package com.example.routee_commerce.ui.userAuthentication.fragments.register

import android.content.Context
import android.content.SharedPreferences
import com.example.routee_commerce.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object RegisterModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(context.getString(R.string.user), Context.MODE_PRIVATE)
    }
}