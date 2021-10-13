package com.example.loginclean.di

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.room.Room
import com.example.loginclean.application.MainActivity
import com.example.loginclean.data.source.AppDatabase
import com.example.loginclean.ui.LoginActivity
import com.example.loginclean.ui.RegisterActivity
import com.example.loginclean.utilis.Constants.AUTH_INTENT
import com.example.loginclean.utilis.Constants.MAIN_INTENT
import com.example.loginclean.utilis.Constants.REGISTER_INTENT
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Named(MAIN_INTENT)
    fun provideMainIntent(context: Context): Intent {
        return Intent(context, MainActivity::class.java)
    }


    @Provides
    @Named(REGISTER_INTENT)
    fun provideRegisterIntent(context: Context): Intent {
        return Intent(context, RegisterActivity::class.java)
    }

    @Provides
    @Named(AUTH_INTENT)
    fun provideAuthIntent(context: Context): Intent {
        return Intent(context, LoginActivity::class.java)
    }

    @Provides
    fun provideGoogleSignInClient(
        application: Application,
        options: GoogleSignInOptions
    ): GoogleSignInClient {
        return GoogleSignIn.getClient(application, options)
    }

    @Provides
    fun provideSignInIntent(googleSignInClient: GoogleSignInClient): Intent {
        return googleSignInClient.signInIntent
    }

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "alumnosTable"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideAlumnosDao(db:AppDatabase) = db.alumnosDao()

}