package ru.coolnotes.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.coolnotes.di.appModule
import ru.coolnotes.di.viewModelModule

class CoolNotesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CoolNotesApp)
            modules(listOf(viewModelModule, appModule))
        }
    }
}
