/*
 * Copyright 2021 Xinto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xinto.apkhelper

import android.content.Context
import com.xinto.apkhelper.callback.StatusCallback

private var statusCallbackClass: StatusCallback? = null
private var logTagString = "APKHelper"

const val ID = "id"

const val ACTION = "action"
const val ACTION_INSTALL = "action_install"
const val ACTION_UNINSTALL = "action_uninstall"

var statusCallback
    get() = statusCallbackClass
    set(value) {
        statusCallbackClass = value
    }

var logTag
    get() = logTagString
    set(value) {
        logTagString = value
    }

/**
 * Builder for [statusCallback]
 * @param onInstall [StatusCallback.onApkInstall]
 * @param onInstallFailed [StatusCallback.onApkInstallFailed]
 * @param onUninstall [StatusCallback.onAppUninstall]
 * @param onUninstallFailed [StatusCallback.onAppUninstallFailed]
 * @return [StatusCallback]
 */
inline fun statusCallbackBuilder(
    crossinline onInstall: (id: Int, context: Context) -> Unit = { _, _ ->},
    crossinline onInstallFailed: (error: String, id: Int, context: Context) -> Unit = { _, _, _ ->},
    crossinline onUninstall: (id: Int, context: Context) -> Unit = { _, _ ->},
    crossinline onUninstallFailed: (error: String, id: Int, context: Context) -> Unit = { _, _, _ ->}
): StatusCallback = object : StatusCallback {
    override fun onApkInstall(id: Int, context: Context) = onInstall(id, context)
    override fun onApkInstallFailed(error: String, id: Int, context: Context) = onInstallFailed(error, id, context)
    override fun onAppUninstall(id: Int, context: Context) = onUninstall(id, context)
    override fun onAppUninstallFailed(error: String, id: Int, context: Context) = onUninstallFailed(error, id, context)
}

/**
 * Builder for [statusCallback]
 * @param onAction action to execute on all callbacks
 * @return [StatusCallback]
 */
inline fun statusCallbackBuilder(
    crossinline onAction: () -> Unit = {}
): StatusCallback = object : StatusCallback {
    override fun onApkInstall(id: Int, context: Context) = onAction()
    override fun onApkInstallFailed(error: String, id: Int, context: Context) = onAction()
    override fun onAppUninstall(id: Int, context: Context) = onAction()
    override fun onAppUninstallFailed(error: String, id: Int, context: Context) = onAction()
}