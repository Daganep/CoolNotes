package ru.coolnotes.app

import android.app.Application
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.coolnotes.di.appModule
import ru.coolnotes.di.viewModelModule
import ru.coolnotes.extensions.StateDataStore

class CoolNotesApp : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch { this@CoolNotesApp.StateDataStore.edit { it.clear() } }

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CoolNotesApp)
            modules(listOf(viewModelModule, appModule))
        }
    }
}
