package com.tuto.leboncointest

import com.tuto.leboncointest.utils.AppExecutors
import java.util.concurrent.Executor



class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant: Executor = Executor { command -> command.run() }
    }


}