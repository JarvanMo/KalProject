/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jarvanmo.kal.recyclerview.paged

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class PagingNetworkState private constructor(
        val status: Status,
        val msg: String? = null) {
    companion object {
        val LOADED = PagingNetworkState(Status.SUCCESS)
        val LOADING = PagingNetworkState(Status.RUNNING)
        fun error(msg: String?) = PagingNetworkState(Status.FAILED, msg)
    }
}