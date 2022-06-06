package com.juarez.androiddemo3.di

import com.juarez.androiddemo3.api.MacroPayApi
import com.juarez.androiddemo3.common.Constants.BASE_URL
import com.juarez.androiddemo3.login.data.LoginRepository
import com.juarez.androiddemo3.login.data.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMacroPayApi(retrofit: Retrofit): MacroPayApi {
        return retrofit.create(MacroPayApi::class.java)
    }

    @Provides
    fun providesRepository(api: MacroPayApi, dispatcher: CoroutineDispatcher): LoginRepository {
        return LoginRepositoryImpl(dispatcher, api)
    }

    @Singleton
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}