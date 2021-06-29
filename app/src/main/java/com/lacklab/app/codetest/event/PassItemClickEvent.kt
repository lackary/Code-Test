package com.lacklab.app.codetest.event

import com.lacklab.app.codetest.data.MigoPass

interface PassItemClickEvent {
    fun onButtonBuyClick(pass:MigoPass)
}