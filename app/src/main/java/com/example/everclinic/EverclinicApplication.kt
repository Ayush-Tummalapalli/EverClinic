package com.example.everclinic

import android.app.Application
import com.example.everclinic.data.local.EverclinicDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class EverclinicApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { EverclinicDatabase.getDatabase(this, applicationScope) }
}