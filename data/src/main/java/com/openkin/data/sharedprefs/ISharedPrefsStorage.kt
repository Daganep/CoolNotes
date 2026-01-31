package com.openkin.data.sharedprefs

interface ISharedPrefsStorage {

    fun  saveViewType(viewType: Int)

    fun  getViewType() : Int
}
