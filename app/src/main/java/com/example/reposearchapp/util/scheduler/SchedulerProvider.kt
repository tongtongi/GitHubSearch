package com.example.reposearchapp.util.scheduler

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun io(): Scheduler

    fun computation(): Scheduler

    fun main(): Scheduler
}