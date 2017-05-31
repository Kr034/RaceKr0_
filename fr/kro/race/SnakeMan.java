package fr.kro.race;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import fr.nashoba24.wolvmc.WolvMC;
import fr.nashoba24.wolvmc.WolvMCAPI;
import fr.nashoba24.wolvmc.events.WolvMCInitEffectsEvent;

public class SnakeMan implements Listener {

	public static boolean enable = true;
	private static int water = 1;
	private static int speed = 2;
	private static int jump = 3;
	private static int nightvision = 1;
	private static int weakness = 2;
	static Double degamultiplier = 3.0;
	static Double chutedouble = 2.0;

	private static String tridentID = "Trident Du SnakeMan";

	@EventHandler
	public void onEffects(WolvMCInitEffectsEvent e) {
		Player p = e.getPlayer();
		if (e.getRace().equals("snakeman")) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 2147483647, water - 1));
		}
		if (WolvMC.hasFinishMission("snakeman.1", p.getName())) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2147483647, nightvision - 1));
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (WolvMCAPI.getRace(p).equals("snakeman")) {
				if (e.getCause() == DamageCause.FALL) {
					e.setDamage(e.getFinalDamage() * chutedouble);
				}
			}
		}
	}

	@EventHandler
	public void onEat(PlayerItemConsumeEvent e) {
		Player p = e.getPlayer();
		if ((e.getItem().getType() != Material.COOKED_FISH && WolvMC.getRace(p.getName()).equals("snakeman"))) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 15 * 20, 4, true), true);
		}
	}

	@EventHandler
	public void onCraftTrident(CraftItemEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack t = e.getRecipe().getResult();
		if (isTrident(t) && WolvMC.getRace(p.getName()).equals("snakeman")) {
			WolvMCAPI.addNumberToPlayerMission(p.getName(), "snakeman.1", (double) 1);
			return;
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPeche(PlayerInteractEvent e) {

		ItemStack fish = new ItemStack(Material.RAW_FISH);

		Player p = e.getPlayer();
		if (WolvMCAPI.getRace(p).equals("snakeman")) {
			if (p.isSneaking() && e.getAction().toString().contains("RIGHT_CLICK")) {
				if (e.getClickedBlock().getType() == Material.PRISMARINE && e.getClickedBlock().getData() == 1) {
					p.getInventory().addItem(fish);
				}
			}
		}
	}

	@EventHandler
	public void damage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getDamager() instanceof LivingEntity) {
				if (WolvMCAPI.getRace(p).equals("snakeman"))
					if (((LivingEntity) e.getDamager()).getEquipment().getItemInMainHand()
							.getType() == Material.FISHING_ROD) {
						e.setDamage(e.getFinalDamage() * degamultiplier);
					} else if (((LivingEntity) e.getDamager()).getEquipment().getItemInMainHand()
							.getType() == Material.WOOD_AXE) {
						e.setDamage(e.getFinalDamage() * degamultiplier);
					} else if (((LivingEntity) e.getDamager()).getEquipment().getItemInMainHand()
							.getType() == Material.WOOD_HOE) {
						e.setDamage(e.getFinalDamage() * degamultiplier);
					} else if (((LivingEntity) e.getDamager()).getEquipment().getItemInMainHand()
							.getType() == Material.WOOD_PICKAXE) {
						e.setDamage(e.getFinalDamage() * degamultiplier);
					} else if (((LivingEntity) e.getDamager()).getEquipment().getItemInMainHand()
							.getType() == Material.WOOD_SPADE) {
						e.setDamage(e.getFinalDamage() * degamultiplier);
					} else if (((LivingEntity) e.getDamager()).getEquipment().getItemInMainHand()
							.getType() == Material.WOOD_SWORD) {
						e.setDamage(e.getFinalDamage() * degamultiplier);
					}
			}
		}
	}

	public static void initSnakeMan() {
		WolvMC.addMission("snakeman.1", (double) 1, "snakeman", "Confectionné un Trident, il reste ",
				"Vision nocturne");
		WolvMC.addMission("snakeman.2", (double) 250, "snakeman", "Atteindre 250 de dégâts de chute, il reste ",
				"Une fois sur terre le malus deviens Lenteur I");
		WolvMC.addMission("snakeman.3", (double) 100, "snakeman", "Manger 100 Poissons, il reste ",
				"Acquérir Vitesse IV dans l'eau au lieux de Vitesse II, ainsi qu'un bonus de Force I");
		WolvMCAPI.addRace("snakeman", ChatColor.DARK_GREEN + "SnakeMan", new ItemStack(Material.WATER_LILY));
		WolvMC.getPlugin(WolvMC.class).getLogger().fine("SnakeMan Class loaded!");
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
			@Override
			public void run() {
				for (Player p : Main.getPlugin(Main.class).getServer().getOnlinePlayers()) {
					if (WolvMCAPI.getRace(p).equals("snakeman")) {
						if (p.getLocation().getBlock().getType() == Material.WATER
								|| p.getLocation().getBlock().getType() == Material.STATIONARY_WATER
								|| p.getWorld().hasStorm()) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, speed - 1));
							p.removePotionEffect(PotionEffectType.JUMP);
							p.removePotionEffect(PotionEffectType.WEAKNESS);
						} else {
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2147483647, jump - 1));
							p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 2147483647, weakness - 1));
						}
						if (p.getWorld().getEnvironment() == Environment.NETHER && !p.isDead()
								&& !p.hasPermission("snakeman.nether.nokill")) {
							p.setHealth(0);
						}
					}
				}
			}
		}, 0L, 20L);

		ItemStack trident = new ItemStack(Material.DIAMOND_SPADE);
		ItemMeta tridentM = trident.getItemMeta();

		tridentM.setDisplayName(ChatColor.GREEN + "Trident");
		tridentM.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
		tridentM.addEnchant(Enchantment.KNOCKBACK, 2, true);
		tridentM.setUnbreakable(true);
		tridentM.setLore(Arrays.asList(tridentID));
		trident.setItemMeta(tridentM);

		ShapedRecipe tridentr = new ShapedRecipe(trident);
		tridentr.shape("III", "SSS", " S ");
		tridentr.setIngredient('I', Material.IRON_INGOT);
		tridentr.setIngredient('S', Material.STICK);
		Bukkit.addRecipe(tridentr);

	}

	private static boolean isTrident(ItemStack t) {
		return (t.hasItemMeta() && t.getItemMeta() != null && t.getItemMeta().hasLore()
				&& t.getItemMeta().getLore() != null && t.getItemMeta().getLore().size() == 1
				&& t.getItemMeta().getLore().get(0).equals(tridentID));
	}
}