package com.mument_android.core.util

import android.os.Bundle

interface SideEffect {
    data class PopBackStack(val bundle: Bundle?): SideEffect
}