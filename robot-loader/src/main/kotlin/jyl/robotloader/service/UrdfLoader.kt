package jyl.robotloader.service

import java.io.Serializable


interface UrdfLoader {
    fun loadUrdf(filename: String): String
    fun loadMesh(filename: String): Serializable?
}