package jyl.robotloader.controller

import jyl.robotloader.service.UrdfLoader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.io.Serializable

@RestController
class UrdfController(
    @Autowired
    private val urdfLoader: UrdfLoader,
) {
    /*
     * Get the URDF file for the robot
     */
    @GetMapping("/urdfs/{filename}")
    fun getURDF(
        @PathVariable filename: String
    ): String {
        return urdfLoader.loadUrdf(filename)
    }

    @GetMapping("/meshes/{filename}")
    fun getMesh(
        @PathVariable filename: String
    ): Serializable? {
        return urdfLoader.loadMesh(filename)
    }

    data class Urdf (
        val data: String
    )
}