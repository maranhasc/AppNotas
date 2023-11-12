package net.azarquiel.note.model

import java.io.Serializable

data class Note(var id:Int=0, var titulo:String="", var descripcion:String="", var fecha:String=""):Serializable
