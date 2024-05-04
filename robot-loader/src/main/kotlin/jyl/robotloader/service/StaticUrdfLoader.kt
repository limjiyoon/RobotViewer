package jyl.robotloader.service

import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.Serializable


@Service
class StaticUrdfLoader: UrdfLoader {
    override fun loadUrdf(filename: String): String {
        val path = ClassPathResource("static/T12/urdf/$filename")
        if (!path.exists()) {
            return "File not found"
        }
        return path.inputStream.bufferedReader().use { it.readText() }
    }

    override fun loadMesh(filename: String): Serializable? {
        val path = ClassPathResource("static/T12/meshes/$filename")
        if (!path.exists()) {
            return "File not found"
        }
        return path.inputStream.readAllBytes()
    }
}