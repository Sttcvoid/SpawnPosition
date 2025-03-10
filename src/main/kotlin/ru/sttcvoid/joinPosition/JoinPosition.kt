package ru.sttcvoid.joinPosition

import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class JoinPosition : JavaPlugin(), Listener {

    override fun onEnable() {
        logger.info("Created by Sttcvoid")

        if (!dataFolder.exists()) dataFolder.mkdir()
        if (!File(dataFolder, "config.yml").exists()) saveDefaultConfig()

        server.pluginManager.registerEvents(this, this)

        logger.info("Loaded!")
    }

    override fun onDisable() {
        logger.info("Disabled!")
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        val world = server.getWorld(config.getString("world") ?: server.worlds[0].name) ?: server.worlds[0]
        val coords = (config.getString("coords") ?: "${world.spawnLocation.x} ${world.spawnLocation.y} ${world.spawnLocation.z}").split(" ").map { it.toDouble() }
        val angle = (config.getString("angle") ?: "0 0").split(" ").map { it.toFloat() }

        player.teleport(
            Location(
                world,
                coords[0], coords[1], coords[2],
                angle[0], angle[1]
            )
        )
    }

}
