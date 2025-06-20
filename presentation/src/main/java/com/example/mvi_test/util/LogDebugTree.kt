package com.example.mvi_test.util

import timber.log.Timber

class LogDebugTree(
    private val tag: String?
) : Timber.DebugTree() {

    private var element: StackTraceElement? = null

    override fun createStackElementTag(element: StackTraceElement): String? {
        this.element = element
        return "${tag}#(${element.fileName}:${element.lineNumber})"
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        element?.let {
            super.log(priority, tag, "(${it.fileName}:${it.lineNumber})" + message, t)
        }?: run {
            super.log(priority, tag, message, t)
        }
    }
}
