package com.lacklab.app.codetest.event

import com.lacklab.app.codetest.data.MigoPass

interface PassItemEvent {
    fun onButtonBuyClick(pass:MigoPass, position: Int)
}