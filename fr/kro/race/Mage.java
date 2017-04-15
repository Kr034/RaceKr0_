package fr.kro.race;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.nashoba24.wolvmc.WolvMC;
import fr.nashoba24.wolvmc.WolvMCAPI;
import fr.nashoba24.wolvmc.events.WolvMCInitEffectsEvent;

public class Mage implements Listener {

	public static boolean enable = true;
	private static boolean baguette = true;
	private static String bID = "Mage Baguette";
	private static int strenght = 1;
	private static String notMage = ChatColor.DARK_RED+"Tu n'es pas Mage !";
	
	
	@EventHandler
	public void onEffects(WolvMCInitEffectsEvent e){
		Player p = e.getPlayer();
		if(e.getRace().equals("mage")) {
			if(WolvMC.hasFinishMission("mage.1", p.getName())){
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2147483647, strenght -1));
			}
		}
	}
	
	@EventHandler
	public void onCraftBaguette(CraftItemEvent e){
		Player p = (Player) e.getWhoClicked();
		ItemStack i = e.getRecipe().getResult();
		if(i.hasItemMeta() && i.getItemMeta()!=null && i.getItemMeta().hasLore() && i.getItemMeta().getLore()!=null){
			List<String> lore = i.getItemMeta().getLore();
			if(lore.size()== 1 && lore.get(0).equals(bID)){
				if(WolvMC.getRace(p.getName()).equals("mage")){
					WolvMCAPI.addNumberToPlayerMission(p.getName(), "mage.1", (double) 1); 
					return;
				}else{
					p.sendMessage(notMage);
					e.setCancelled(true);
					return;
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	

	public static void initMage() {
		WolvMC.addMission("mage.1", (double) 10, "mage", "Crafter %goal% fois la baguette du mage.", "Tu a force 1.");
		WolvMCAPI.addRace("mage", ChatColor.DARK_BLUE+ "Mage", new ItemStack(Material.ENCHANTMENT_TABLE));
		WolvMC.getPlugin(WolvMC.class).getLogger().fine("Mage Class loaded!");
		
		if(baguette){
			ItemStack b = new ItemStack(Material.STICK);
			ItemMeta bM = b.getItemMeta();
			
			bM.setDisplayName(ChatColor.BLUE+"BAGUETTE DU MAGE");
			bM.addEnchant(Enchantment.LOOT_BONUS_MOBS, 3, true);
			bM.setLore(Arrays.asList(bID));
			b.setItemMeta(bM);
			
			ShapedRecipe r = new ShapedRecipe(b);
			r.shape(" D ", " S ", " D ");
			r.setIngredient('D', Material.DIAMOND);
			r.setIngredient('S', Material.STICK);
			Bukkit.addRecipe(r);
		}
	}

}
