package fr.kro.race;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		if (Mage.enable) {
			Bukkit.getServer().getPluginManager().registerEvents(new Mage(), this);
		}
	}

	public void onDisable() {

	}

}
