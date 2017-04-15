package fr.kro.race;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import fr.nashoba24.wolvmc.WolvMC;
import fr.nashoba24.wolvmc.WolvMCAPI;

public class Mage implements Listener {

	public static boolean enable = true;

	public static void initMage() {
		WolvMC.addMission("mage.1", (double) 10, "mage", "Crafter %goal% fois la baguette du mage", "Peut utiliser la baguette");
		WolvMCAPI.addRace("mage", ChatColor.DARK_BLUE+ "Mage", new ItemStack(Material.ENCHANTMENT_TABLE));
	}

}
