package fr.kro.race;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import fr.nashoba24.wolvmc.WolvMC;
import fr.nashoba24.wolvmc.WolvMCAPI;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		WolvMCAPI.addRace("mage", ChatColor.DARK_BLUE + "Mage", new ItemStack(Material.ENCHANTMENT_TABLE));
		WolvMC.getPlugin(WolvMC.class).getLogger().fine("Mage Class loaded!");
		String mission = "mage.1";
		Double goal = (double) 10;
		String race = "mage";
		String descr = "Crafter la baguette du mage";
		String msg = "Peut utiliser la baguette";
		WolvMC.addMission(mission, goal, race, descr, msg);
	}


	public void onDisable() {

	}

}
