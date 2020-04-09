package com.example.myzikanwari_new

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ManagementModel:RealmObject(){
    @PrimaryKey
    private var id:Long = 0
}