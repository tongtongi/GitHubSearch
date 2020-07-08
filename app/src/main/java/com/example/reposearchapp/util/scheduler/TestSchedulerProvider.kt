package com.example.reposearchapp.util.scheduler

import io.reactivex.schedulers.Schedulers

class TestSchedulerProvider : SchedulerProvider {

    override fun io() = Schedulers.trampoline()

    override fun computation() = Schedulers.trampoline()

    override fun main() = Schedulers.trampoline()
}