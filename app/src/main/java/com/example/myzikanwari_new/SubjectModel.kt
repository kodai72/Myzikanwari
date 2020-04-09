package com.example.myzikanwari_new

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SubjectModel :RealmObject(){
    private var id:Long = 0
    private var name:String?=""
    private var teacher_name:String?=""
    private var room_name:String?=""
    private var memo:String?=""

    public fun getName():String?{
        return this.name
    }

    public fun setName(name:String){
        this.name=name
    }
    public fun getId():Long?{
        return this.id
    }

    public fun setId(id:Long){
        this.id=id
    }

    public fun getTeacher_name():String?{
        return this.teacher_name
    }

    public fun setTeacher_name(teacher_name:String){
        this.teacher_name=teacher_name
    }

    public fun getRoom_name():String?{
        return this.room_name
    }

    public fun setRoom_name(room_name:String){
        this.room_name=room_name
    }

    public fun getMemo():String?{
        return this.memo
    }

    public fun setMemo(memo:String){
        this.memo=memo
    }
}