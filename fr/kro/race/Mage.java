package fr.kro.race;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
	private static WolvMCAPI wmc = new WolvMCAPI();
	private static boolean baguette = true;
	private static String bID = "Mage Baguette";
	private static int strenght = 3;
	private static String notMage = ChatColor.DARK_RED + "Tu n'es pas Mage !";
	private static HashMap<UUID, Long> stormc = new HashMap<>();
	private static HashMap<UUID, Long> stormcbug = new HashMap<>();
	private static int stormtps = 60;

	@EventHandler
	public void onEffects(WolvMCInitEffectsEvent e) {
		Player p = e.getPlayer();
		if (e.getRace().equals("mage")) {
			if (WolvMC.hasFinishMission("mage.1", p.getName())) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2147483647, strenght - 1));
			}
		}
	}

	@EventHandler
	public void onCraftBaguette(CraftItemEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack i = e.getRecipe().getResult();
		if (isMageBaguette(i) && WolvMC.getRace(p.getName()).equals("mage")) {
			WolvMCAPI.addNumberToPlayerMission(p.getName(), "mage.1", (double) 1);
			return;
		} else {
			p.sendMessage(notMage);
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onPluie(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.isSneaking() && wmc.getRace(p).equals("mage") && e.hasItem() && isMageBaguette(e.getItem())) {
			if(e.getAction().toString().contains("LEFT_CLICK") || e.getAction().toString().contains("RIGHT_CLICK")){
				int Cooldowntime = stormtps;
				System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
				if(stormc.containsKey(p.getUniqueId())){
					Long Bug = stormcbug.get(p.getUniqueId());
					if(System.currentTimeMillis()-Bug<20){
						System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
						return;
					}
					long secondesleft = ((stormc.get(p.getUniqueId())/1000)+Cooldowntime)-(System.currentTimeMillis()/1000);
					if(secondesleft< 0){
						stormcbug.put(p.getUniqueId(), System.currentTimeMillis());
						p.sendMessage(WolvMCAPI.getCooldownMessage((int) secondesleft));
						System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
						return;
					}
				}
				stormc.put(p.getUniqueId(), System.currentTimeMillis());
				stormcbug.put(p.getUniqueId(), System.currentTimeMillis());
				System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
				e.setCancelled(true);
			}
			if(e.getAction().toString().contains("LEFT_CLICK")){
				p.getWorld().setStorm(true);
				p.sendMessage(ChatColor.GREEN + "La pluie a été mise.");
				WolvMCAPI.addNumberToPlayerMission(p, "mage.2", (double) 1 );
				return;
			}
			else if(e.getAction().toString().contains("RIGHT_CLICK")){
				if(!WolvMCAPI.hasFinishMission(p, "mage.2")){
					p.sendMessage(ChatColor.DARK_RED+"Tu n'as pas fini la mission 2.");
					return;
				}
				p.getWorld().setStorm(false);
				p.sendMessage(ChatColor.GREEN + "Le beau temps a été mis.");
				return;
			}
		}		
	}

	public static void initMage() {
		WolvMC.addMission("mage.1", (double) 10, "mage", "Crafter %goal% fois la baguette du mage.", "Tu a force 1.");
		WolvMC.addMission("mage.2", (double) 50, "mage", "Invoquer la pluie %goal% fois.",
				"Tu peut faire des vague de feu.");
		WolvMCAPI.addRace("mage", ChatColor.DARK_BLUE + "Mage", new ItemStack(Material.ENCHANTMENT_TABLE));
		WolvMC.getPlugin(WolvMC.class).getLogger().fine("Mage Class loaded!");

		if (baguette) {
			ItemStack b = new ItemStack(Material.STICK);
			ItemMeta bM = b.getItemMeta();

			bM.setDisplayName(ChatColor.BLUE + "BAGUETTE DU MAGE");
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

	private static boolean isMageBaguette(ItemStack i) {
		return (i.hasItemMeta() && i.getItemMeta() != null && i.getItemMeta().hasLore()
				&& i.getItemMeta().getLore() != null && i.getItemMeta().getLore().size() == 1
				&& i.getItemMeta().getLore().get(0).equals(bID));
	}
}
