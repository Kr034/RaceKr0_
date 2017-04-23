package fr.kro.race;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import fr.nashoba24.wolvmc.WolvMC;
import fr.nashoba24.wolvmc.WolvMCAPI;
import fr.nashoba24.wolvmc.events.WolvMCInitEffectsEvent;
import fr.nashoba24.wolvmc.utils.TitleAPI;

public class Mage implements Listener {

	public static boolean enable = true;
	private static boolean baguette = true;
	private static String bID = "Mage Baguette";
	private static String biID = "Mage Baguette Glace";
	private static String bfID = "Mage Baguette Feu";
	private static String baID = "Mage Baguette Air";
	private static String raID = "Radio Mage";
	private static String ArmorFeuID = "Armor Mage Feu";
	private static String ArmorGlaceID = "Armor Mage Glace";
	private static int strenght = 3;
	private static int jump = 4;
	private static int speed = 4;
	private static int firer = 2;
	private static int lev = 4;
	private static String notMage = ChatColor.DARK_RED + "Tu n'es pas Mage !";
	private static HashMap<UUID, Long> stormc = new HashMap<>();
	private static HashMap<UUID, Long> stormcbug = new HashMap<>();
	private static int stormtps = 60;
	private static HashMap<UUID, Long> speedc = new HashMap<>();
	private static HashMap<UUID, Long> speedbug = new HashMap<>();
	private static int speedtps = 120;
	private static HashMap<UUID, Long> feuc = new HashMap<>();
	private static HashMap<UUID, Long> feubug = new HashMap<>();
	private static int feutps = 60;
	private static HashMap<UUID, Long> musicc = new HashMap<>();
	private static HashMap<UUID, Long> musicbug = new HashMap<>();
	private static int musictps = 120;
	private static HashMap<UUID, Long> adieuc = new HashMap<>();
	private static HashMap<UUID, Long> adieubug = new HashMap<>();
	private static int adieutps = 180;
	private static HashMap<UUID, Long> levc = new HashMap<>();
	private static HashMap<UUID, Long> levbug = new HashMap<>();
	private static int levtps = 60;
	static Integer ejection = 10;

	@EventHandler
	public void onEffects(WolvMCInitEffectsEvent e) {
		Player p = e.getPlayer();
		if (e.getRace().equals("mage")) {
			if (WolvMC.hasFinishMission("mage.1", p.getName())) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2147483647, strenght - 1));
			}
			if (WolvMC.hasFinishMission("mage.3", p.getName())) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2147483647, jump - 1));
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (WolvMCAPI.getRace(p).equals("mage")) {
				if (e.getCause() == DamageCause.FALL) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onCraftBaguette(CraftItemEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack i = e.getRecipe().getResult();
		ItemStack d = e.getRecipe().getResult();
		ItemStack f = e.getRecipe().getResult();
		ItemStack a = e.getRecipe().getResult();
		ItemStack r = e.getRecipe().getResult();
		ItemStack bg = e.getRecipe().getResult();
		ItemStack bf = e.getRecipe().getResult();
		if (isMageBaguette(i) || isMageBaguetteGlace(d) || isMageBaguetteFeu(f) || isMageBaguetteAir(a)
				|| isMageRadio(r) && WolvMC.getRace(p.getName()).equals("mage")) {
			WolvMCAPI.addNumberToPlayerMission(p.getName(), "mage.1", (double) 1);
			if (isMageArmorGlace(bg) && WolvMC.getRace(p.getName()).equals("mage")) {
				WolvMCAPI.addNumberToPlayerMission(p.getName(), "mage.5", (double) 1);
				if (isMageArmorFeu(bf) && WolvMC.getRace(p.getName()).equals("mage")) {
					WolvMCAPI.addNumberToPlayerMission(p.getName(), "mage.4", (double) 1);
					return;
				} else {
			p.sendMessage(notMage);
			e.setCancelled(true);
			return;
				}
			}
		}
	}

	@EventHandler
	public void onPluie(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.isSneaking() && WolvMCAPI.getRace(p).equals("mage") && e.hasItem() && isMageBaguette(e.getItem())) {
			if (e.getAction().toString().contains("LEFT_CLICK") || e.getAction().toString().contains("RIGHT_CLICK")) {
				int Cooldowntime = stormtps;
				if (stormc.containsKey(p.getUniqueId())) {
					Long Bug = stormcbug.get(p.getUniqueId());
					if (System.currentTimeMillis() - Bug < 20) {
						return;
					}
					long secondesleft = ((stormc.get(p.getUniqueId()) / 1000) + Cooldowntime)
							- (System.currentTimeMillis() / 1000);
					if (secondesleft > 0) {
						stormcbug.put(p.getUniqueId(), System.currentTimeMillis());
						p.sendMessage(WolvMCAPI.getCooldownMessage((int) secondesleft));
						return;
					}
				}
				stormc.put(p.getUniqueId(), System.currentTimeMillis());
				stormcbug.put(p.getUniqueId(), System.currentTimeMillis());
				e.setCancelled(true);
			}
			if (e.getAction().toString().contains("LEFT_CLICK")) {
				p.getWorld().setStorm(true);
				p.sendMessage(ChatColor.GREEN + "La pluie a été mise.");
				WolvMCAPI.addNumberToPlayerMission(p, "mage.2", (double) 1);
				return;
			} else if (e.getAction().toString().contains("RIGHT_CLICK")) {
				if (!WolvMCAPI.hasFinishMission(p, "mage.2")) {
					p.sendMessage(ChatColor.DARK_RED + "Tu n'as pas fini la mission 2.");
					return;
				}
				p.getWorld().setStorm(false);
				p.sendMessage(ChatColor.GREEN + "Le beau temps a été mis.");
				WolvMCAPI.addNumberToPlayerMission(p, "mage.3", (double) 1);
				return;
			}
		}
	}

	@EventHandler
	public void onGel(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.isSneaking() && WolvMCAPI.getRace(p).equals("mage") && e.hasItem() && isMageBaguetteGlace(e.getItem())) {
			if (e.getAction().toString().contains("LEFT_CLICK")) {
				int Cooldowntime = speedtps;
				if (speedc.containsKey(p.getUniqueId())) {
					Long Bug = speedbug.get(p.getUniqueId());
					if (System.currentTimeMillis() - Bug < 20) {
						return;
					}
					long secondesleft = ((speedc.get(p.getUniqueId()) / 1000) + Cooldowntime)
							- (System.currentTimeMillis() / 1000);
					if (secondesleft > 0) {
						speedbug.put(p.getUniqueId(), System.currentTimeMillis());
						p.sendMessage(WolvMCAPI.getCooldownMessage((int) secondesleft));
						return;
					}
				}
				speedc.put(p.getUniqueId(), System.currentTimeMillis());
				speedbug.put(p.getUniqueId(), System.currentTimeMillis());
				e.setCancelled(true);
			}
			if (e.getAction().toString().contains("LEFT_CLICK")) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, speed - 1));
				p.sendMessage(ChatColor.GREEN + "Speed IV pour 20 secondes!");
				return;
			}
		}

	}

	@EventHandler
	public void onFeu(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.isSneaking() && WolvMCAPI.getRace(p).equals("mage") && e.hasItem() && isMageBaguetteFeu(e.getItem())) {
			if (e.getAction().toString().contains("RIGHT_CLICK")) {
				int Cooldowntime = feutps;
				if (feuc.containsKey(p.getUniqueId())) {
					Long Bug = feubug.get(p.getUniqueId());
					if (System.currentTimeMillis() - Bug < 20) {
						return;
					}
					long secondesleft = ((feuc.get(p.getUniqueId()) / 1000) + Cooldowntime)
							- (System.currentTimeMillis() / 1000);
					if (secondesleft > 0) {
						feubug.put(p.getUniqueId(), System.currentTimeMillis());
						p.sendMessage(WolvMCAPI.getCooldownMessage((int) secondesleft));
						return;
					}
				}
				feuc.put(p.getUniqueId(), System.currentTimeMillis());
				feubug.put(p.getUniqueId(), System.currentTimeMillis());
				e.setCancelled(true);
			}
			if (e.getAction().toString().contains("LEFT_CLICK")) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 18000, firer - 1));
				p.sendMessage(ChatColor.GREEN + "Fire Resistance 2 pour 15 minutes!");
				return;
			}
			if (e.getAction().toString().contains("RIGHT_CLICK")) {
				BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
				scheduler.scheduleSyncDelayedTask(WolvMC.getPlugin(WolvMC.class), new Runnable() {
					@Override
					public void run() {
						p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREBALL);
					}
				}, 10L);
				scheduler.scheduleSyncDelayedTask(WolvMC.getPlugin(WolvMC.class), new Runnable() {
					@Override
					public void run() {
						p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREBALL);
					}
				}, 20L);
				scheduler.scheduleSyncDelayedTask(WolvMC.getPlugin(WolvMC.class), new Runnable() {
					@Override
					public void run() {
						p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREBALL);

					}
				}, 30L);
				p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREBALL);
				p.sendMessage(ChatColor.GREEN + "Tu a fait spawn une fireball!");
				return;
			}
		}
	}

	@EventHandler
	public void onAir(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.isSneaking() && WolvMCAPI.getRace(p).equals("mage") && e.hasItem() && isMageBaguetteAir(e.getItem())) {
			if (e.getAction().toString().contains("LEFT_CLICK")) {
				int Cooldowntime = levtps;
				if (levc.containsKey(p.getUniqueId())) {
					Long Bug = levbug.get(p.getUniqueId());
					if (System.currentTimeMillis() - Bug < 20) {
						return;
					}
					long secondesleft = ((levc.get(p.getUniqueId()) / 1000) + Cooldowntime)
							- (System.currentTimeMillis() / 1000);
					if (secondesleft > 0) {
						levbug.put(p.getUniqueId(), System.currentTimeMillis());
						p.sendMessage(WolvMCAPI.getCooldownMessage((int) secondesleft));
						return;
					}
				}
				levc.put(p.getUniqueId(), System.currentTimeMillis());
				levbug.put(p.getUniqueId(), System.currentTimeMillis());
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED + "Vole pour 30 secondes");
				p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 2147483647, lev - 1));
				BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
				scheduler.scheduleSyncDelayedTask(WolvMC.getPlugin(WolvMC.class), new Runnable() {
					@Override
					public void run() {
						p.sendMessage(ChatColor.RED + "Il reste 10 secondes");
					}
				}, 300);
				scheduler.scheduleSyncDelayedTask(WolvMC.getPlugin(WolvMC.class), new Runnable() {
					@Override
					public void run() {
						p.removePotionEffect(PotionEffectType.LEVITATION);
					}
				}, 600L);
			}
			if (e.getAction().toString().contains("RIGHT_CLICK")) {
				int Cooldowntime = adieutps;
				if (adieuc.containsKey(p.getUniqueId())) {
					Long Bug = adieubug.get(p.getUniqueId());
					if (System.currentTimeMillis() - Bug < 20) {
						return;
					}
					long secondesleft = ((adieuc.get(p.getUniqueId()) / 1000) + Cooldowntime)
							- (System.currentTimeMillis() / 1000);
					if (secondesleft > 0) {
						adieubug.put(p.getUniqueId(), System.currentTimeMillis());
						p.sendMessage(WolvMCAPI.getCooldownMessage((int) secondesleft));
						return;
					}
				}
				adieuc.put(p.getUniqueId(), System.currentTimeMillis());
				adieubug.put(p.getUniqueId(), System.currentTimeMillis());
				e.setCancelled(true);
				if (WolvMC.canUsePowerSafe(e.getPlayer().getLocation(), e.getPlayer())) {
					Vector dir = e.getPlayer().getEyeLocation().getDirection();
					dir.setY(0);
					dir = dir.multiply(ejection);
					final Vector push = dir;
					List<Entity> list = e.getPlayer().getNearbyEntities(5, 5, 5);
					BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
					scheduler.scheduleSyncDelayedTask(WolvMC.getPlugin(WolvMC.class), new Runnable() {

						@Override
						public void run() {
							TitleAPI.sendTitle(e.getPlayer(), 1, 9, 0, ChatColor.DARK_BLUE + "Mage", "");
							e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
									1.0F, 1.0F);
						}
					}, 10L);
					scheduler.scheduleSyncDelayedTask(WolvMC.getPlugin(WolvMC.class), new Runnable() {
						@Override
						public void run() {
							TitleAPI.sendTitle(e.getPlayer(), 0, 10, 0, ChatColor.WHITE + "DE L'AIR", "");
							e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
									1.0F, 1.0F);
						}
					}, 20L);
					scheduler.scheduleSyncDelayedTask(WolvMC.getPlugin(WolvMC.class), new Runnable() {
						@Override
						public void run() {
							TitleAPI.sendTitle(e.getPlayer(), 0, 20, 5, ChatColor.GOLD + "ADIEU!", "");
							e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0F,
									1.0F);
						}
					}, 30L);
					for (final Entity ent : list) {
						if (ent instanceof Player) {
							scheduler.scheduleSyncDelayedTask(WolvMC.getPlugin(WolvMC.class), new Runnable() {
								@Override
								public void run() {
									TitleAPI.sendTitle((Player) ent, 1, 9, 0, ChatColor.DARK_BLUE + "Mage", "");
									((Player) ent).playSound(ent.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
											1.0F, 1.0F);
								}
							}, 10L);
							scheduler.scheduleSyncDelayedTask(WolvMC.getPlugin(WolvMC.class), new Runnable() {
								@Override
								public void run() {
									TitleAPI.sendTitle((Player) ent, 0, 10, 0, ChatColor.DARK_BLUE + "DE L'AIR", "");
									((Player) ent).playSound(ent.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
											1.0F, 1.0F);
								}
							}, 20L);
							scheduler.scheduleSyncDelayedTask(WolvMC.getPlugin(WolvMC.class), new Runnable() {
								@Override
								public void run() {
									TitleAPI.sendTitle((Player) ent, 0, 20, 5, ChatColor.DARK_BLUE + "ADIEU!", "");
									ent.setVelocity(push);
									((Player) ent).playSound(ent.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0F,
											1.0F);
								}
							}, 30L);
						} else {
							scheduler.scheduleSyncDelayedTask(WolvMC.getPlugin(WolvMC.class), new Runnable() {
								@Override
								public void run() {
									ent.setVelocity(push);
								}
							}, 30L);
						}
					}
				}

			}
		}
	}

	@EventHandler
	public void onRadio(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.isSneaking() && WolvMCAPI.getRace(p).equals("mage") && e.hasItem() && isMageRadio(e.getItem())) {
			if (e.getAction().toString().contains("LEFT_CLICK") || e.getAction().toString().contains("RIGHT_CLICK")) {
				int Cooldowntime = musictps;
				if (musicc.containsKey(p.getUniqueId())) {
					Long Bug = musicbug.get(p.getUniqueId());
					if (System.currentTimeMillis() - Bug < 20) {
						return;
					}
					long secondesleft = ((musicc.get(p.getUniqueId()) / 1000) + Cooldowntime)
							- (System.currentTimeMillis() / 1000);
					if (secondesleft > 0) {
						musicbug.put(p.getUniqueId(), System.currentTimeMillis());
						p.sendMessage(WolvMCAPI.getCooldownMessage((int) secondesleft));
						return;
					}
				}
				musicc.put(p.getUniqueId(), System.currentTimeMillis());
				musicbug.put(p.getUniqueId(), System.currentTimeMillis());
				e.setCancelled(true);
				if (e.getAction().toString().contains("LEFT_CLICK")) {
					p.playEffect(p.getLocation(), Effect.RECORD_PLAY, Material.RECORD_7);
					e.setCancelled(true);
				}
				if (e.getAction().toString().contains("RIGHT_CLICK")) {
					p.playEffect(p.getLocation(), Effect.RECORD_PLAY, Material.RECORD_12);
					e.setCancelled(true);
				}
			}
		}
	}

	public static void initMage() {
		WolvMC.addMission("mage.3", (double) 50, "mage", "Mettre le beau temps %goal% fois", "Tu à jump 4.");
		WolvMC.addMission("mage.1", (double) 10, "mage", "Crafter %goal% fois la baguette du mage", "Tu à force 3.");
		WolvMC.addMission("mage.2", (double) 50, "mage", "Mettre la pluie %goal% fois",
				"Tu peut mettre le beau temps.");
		WolvMC.addMission("mage.4", (double) 4, "mage", "Crafter %goal% fois l'armure du mage de feu",
				"Tu a les effet de l'armure du mage de feu.");
		WolvMC.addMission("mage.5", (double) 4, "mage", "Crafter %goal% fois l'armure du mage de glace",
				"Tu a les effet de l'armure du mage de glace.");
		WolvMCAPI.addRace("mage", ChatColor.DARK_BLUE + "Mage", new ItemStack(Material.ENCHANTMENT_TABLE));
		WolvMC.getPlugin(WolvMC.class).getLogger().fine("Mage Class loaded!");

		if (baguette) {
			ItemStack b = new ItemStack(Material.STICK);
			ItemMeta bM = b.getItemMeta();

			bM.setDisplayName(ChatColor.BLUE + "BAGUETTE DU MAGE (BASE)");
			bM.addEnchant(Enchantment.LOOT_BONUS_MOBS, 4, true);
			bM.setLore(Arrays.asList(bID));
			b.setItemMeta(bM);

			ShapedRecipe rb = new ShapedRecipe(b);
			rb.shape(" D ", " S ", " D ");
			rb.setIngredient('D', Material.DIAMOND);
			rb.setIngredient('S', Material.STICK);
			Bukkit.addRecipe(rb);

			ItemStack bi = new ItemStack(Material.STICK);
			ItemMeta biM = bi.getItemMeta();

			biM.setDisplayName(ChatColor.AQUA + "BAGUETTE DU MAGE (GLACE)");
			biM.addEnchant(Enchantment.LOOT_BONUS_MOBS, 4, true);
			biM.addEnchant(Enchantment.FROST_WALKER, 2, true);
			biM.setLore(Arrays.asList(biID));
			bi.setItemMeta(biM);

			ShapedRecipe rbi = new ShapedRecipe(bi);
			rbi.shape("GGG", "GBG", "GGG");
			rbi.setIngredient('B', Material.STICK);
			rbi.setIngredient('G', Material.ICE);
			Bukkit.addRecipe(rbi);

			ItemStack bf = new ItemStack(Material.BLAZE_ROD);
			ItemMeta bfM = bf.getItemMeta();

			bfM.setDisplayName(ChatColor.RED + "BAGUETTE DU MAGE (FEU)");
			bfM.addEnchant(Enchantment.LOOT_BONUS_MOBS, 4, true);
			bfM.addEnchant(Enchantment.FIRE_ASPECT, 4, true);
			bfM.setLore(Arrays.asList(bfID));
			bf.setItemMeta(bfM);

			ShapedRecipe rbf = new ShapedRecipe(bf);
			rbf.shape("MMM", "MBM", "MMM");
			rbf.setIngredient('B', Material.STICK);
			rbf.setIngredient('M', Material.MAGMA);
			Bukkit.addRecipe(rbf);

			ItemStack ba = new ItemStack(Material.END_ROD);
			ItemMeta baM = ba.getItemMeta();

			baM.setDisplayName(ChatColor.WHITE + "BAGUETTE DU MAGE (AIR)");
			baM.addEnchant(Enchantment.LOOT_BONUS_MOBS, 4, true);
			baM.addEnchant(Enchantment.KNOCKBACK, 2, true);
			baM.setLore(Arrays.asList(baID));
			ba.setItemMeta(baM);

			ShapedRecipe rba = new ShapedRecipe(ba);
			rba.shape("VVV", "VBV", "VVV");
			rba.setIngredient('B', Material.STICK);
			rba.setIngredient('V', Material.GLASS);
			Bukkit.addRecipe(rba);

			ItemStack ra = new ItemStack(Material.JUKEBOX);
			ItemMeta raM = ra.getItemMeta();

			raM.setDisplayName(ChatColor.WHITE + "RADIO MAGE");
			raM.setLore(Arrays.asList(raID));
			ra.setItemMeta(raM);

			ShapedRecipe rac = new ShapedRecipe(ra);
			rac.shape(" A ", " D ", " B ");
			rac.setIngredient('A', Material.RECORD_7);
			rac.setIngredient('B', Material.RECORD_12);
			rac.setIngredient('D', Material.DIAMOND);
			Bukkit.addRecipe(rac);

			ItemStack boot = new ItemStack(Material.LEATHER_BOOTS);
			LeatherArmorMeta meta = (LeatherArmorMeta) boot.getItemMeta();
			meta.setColor(Color.RED);
			boot.setItemMeta(meta);
			ItemMeta bootM = boot.getItemMeta();

			bootM.setDisplayName(ChatColor.RED + "Boots Mage FEU");
			bootM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6, true);
			bootM.addEnchant(Enchantment.DEPTH_STRIDER, 3, true);
			bootM.setUnbreakable(true);
			bootM.setLore(Arrays.asList(ArmorFeuID));
			boot.setItemMeta(bootM);

			ShapedRecipe rboot = new ShapedRecipe(boot);
			rboot.shape("A A", "A A", "C C");
			rboot.setIngredient('A', Material.DIAMOND);
			rboot.setIngredient('C', Material.BLAZE_POWDER);
			Bukkit.addRecipe(rboot);

			ItemStack jamb = new ItemStack(Material.LEATHER_LEGGINGS);
			LeatherArmorMeta meta2 = (LeatherArmorMeta) jamb.getItemMeta();
			meta2.setColor(Color.RED);
			jamb.setItemMeta(meta2);
			ItemMeta jambM = jamb.getItemMeta();

			jambM.setDisplayName(ChatColor.RED + "Jambiere Mage FEU");
			jambM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6, true);
			jambM.addEnchant(Enchantment.THORNS, 3, true);
			jambM.setUnbreakable(true);
			jambM.setLore(Arrays.asList(ArmorFeuID));
			jamb.setItemMeta(jambM);

			ShapedRecipe rjamb = new ShapedRecipe(jamb);
			rjamb.shape("AAA", "A A", "C C");
			rjamb.setIngredient('A', Material.DIAMOND);
			rjamb.setIngredient('C', Material.BLAZE_POWDER);
			Bukkit.addRecipe(rjamb);

			ItemStack plast = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta meta3 = (LeatherArmorMeta) plast.getItemMeta();
			meta3.setColor(Color.RED);
			plast.setItemMeta(meta3);
			ItemMeta plastM = plast.getItemMeta();

			plastM.setDisplayName(ChatColor.RED + "Plastron Mage FEU");
			plastM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6, true);
			plastM.addEnchant(Enchantment.THORNS, 3, true);
			plastM.setUnbreakable(true);
			plastM.setLore(Arrays.asList(ArmorFeuID));
			plast.setItemMeta(plastM);

			ShapedRecipe rplast = new ShapedRecipe(plast);
			rplast.shape("ACA", "AAA", "AAA");
			rplast.setIngredient('A', Material.DIAMOND);
			rplast.setIngredient('C', Material.BLAZE_POWDER);
			Bukkit.addRecipe(rplast);

			ItemStack tete = new ItemStack(Material.LEATHER_HELMET);
			LeatherArmorMeta meta4 = (LeatherArmorMeta) tete.getItemMeta();
			meta4.setColor(Color.RED);
			tete.setItemMeta(meta4);
			ItemMeta teteM = tete.getItemMeta();

			teteM.setDisplayName(ChatColor.RED + "Casque Mage FEU");
			teteM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6, true);
			teteM.addEnchant(Enchantment.THORNS, 3, true);
			teteM.addEnchant(Enchantment.OXYGEN, 3, true);
			teteM.addEnchant(Enchantment.WATER_WORKER, 2, true);
			teteM.setUnbreakable(true);
			teteM.setLore(Arrays.asList(ArmorFeuID));
			tete.setItemMeta(teteM);

			ShapedRecipe rtete = new ShapedRecipe(tete);
			rtete.shape("AAA", "C C", "   ");
			rtete.setIngredient('A', Material.DIAMOND);
			rtete.setIngredient('C', Material.BLAZE_POWDER);
			Bukkit.addRecipe(rtete);

			ItemStack bootg = new ItemStack(Material.LEATHER_BOOTS);
			LeatherArmorMeta meta5 = (LeatherArmorMeta) bootg.getItemMeta();
			meta5.setColor(Color.AQUA);
			bootg.setItemMeta(meta5);
			ItemMeta bootgM = bootg.getItemMeta();

			bootgM.setDisplayName(ChatColor.AQUA + "Boots Mage Glace");
			bootgM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6, true);
			bootgM.addEnchant(Enchantment.FROST_WALKER, 2, true);
			bootgM.setUnbreakable(true);
			bootgM.setLore(Arrays.asList(ArmorGlaceID));
			bootg.setItemMeta(bootgM);

			ShapedRecipe rbootg = new ShapedRecipe(bootg);
			rbootg.shape("A A", "A A", "C C");
			rbootg.setIngredient('A', Material.DIAMOND);
			rbootg.setIngredient('C', Material.ICE);
			Bukkit.addRecipe(rbootg);

			ItemStack jambg = new ItemStack(Material.LEATHER_LEGGINGS);
			LeatherArmorMeta meta6 = (LeatherArmorMeta) jambg.getItemMeta();
			meta6.setColor(Color.AQUA);
			jambg.setItemMeta(meta6);
			ItemMeta jambgM = jambg.getItemMeta();

			jambgM.setDisplayName(ChatColor.AQUA + "Jambiere Mage Glace");
			jambgM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6, true);
			jambgM.addEnchant(Enchantment.THORNS, 3, true);
			jambgM.setUnbreakable(true);
			jambgM.setLore(Arrays.asList(ArmorGlaceID));
			jambg.setItemMeta(jambgM);

			ShapedRecipe rjambg = new ShapedRecipe(jambg);
			rjambg.shape("AAA", "A A", "C C");
			rjambg.setIngredient('A', Material.DIAMOND);
			rjambg.setIngredient('C', Material.ICE);
			Bukkit.addRecipe(rjambg);

			ItemStack plastg = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta meta7 = (LeatherArmorMeta) plastg.getItemMeta();
			meta7.setColor(Color.AQUA);
			plastg.setItemMeta(meta7);
			ItemMeta plastgM = plastg.getItemMeta();

			plastgM.setDisplayName(ChatColor.AQUA + "Plastgron Mage Glace");
			plastgM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6, true);
			plastgM.addEnchant(Enchantment.THORNS, 3, true);
			plastgM.setUnbreakable(true);
			plastgM.setLore(Arrays.asList(ArmorGlaceID));
			plastg.setItemMeta(plastgM);

			ShapedRecipe rplastg = new ShapedRecipe(plastg);
			rplastg.shape("ACA", "AAA", "AAA");
			rplastg.setIngredient('A', Material.DIAMOND);
			rplastg.setIngredient('C', Material.ICE);
			Bukkit.addRecipe(rplastg);

			ItemStack teteg = new ItemStack(Material.LEATHER_HELMET);
			LeatherArmorMeta meta8 = (LeatherArmorMeta) teteg.getItemMeta();
			meta8.setColor(Color.AQUA);
			teteg.setItemMeta(meta8);
			ItemMeta tetegM = teteg.getItemMeta();

			tetegM.setDisplayName(ChatColor.AQUA + "Casque Mage Glace");
			tetegM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6, true);
			tetegM.addEnchant(Enchantment.THORNS, 3, true);
			tetegM.addEnchant(Enchantment.OXYGEN, 3, true);
			tetegM.addEnchant(Enchantment.WATER_WORKER, 2, true);
			tetegM.setUnbreakable(true);
			tetegM.setLore(Arrays.asList(ArmorGlaceID));
			teteg.setItemMeta(tetegM);

			ShapedRecipe rteteg = new ShapedRecipe(teteg);
			rteteg.shape("AAA", "C C", "   ");
			rteteg.setIngredient('A', Material.DIAMOND);
			rteteg.setIngredient('C', Material.ICE);
			Bukkit.addRecipe(rteteg);
		}
	}

	private static boolean isMageBaguette(ItemStack i) {
		return (i.hasItemMeta() && i.getItemMeta() != null && i.getItemMeta().hasLore()
				&& i.getItemMeta().getLore() != null && i.getItemMeta().getLore().size() == 1
				&& i.getItemMeta().getLore().get(0).equals(bID));
	}

	private static boolean isMageBaguetteGlace(ItemStack d) {
		return (d.hasItemMeta() && d.getItemMeta() != null && d.getItemMeta().hasLore()
				&& d.getItemMeta().getLore() != null && d.getItemMeta().getLore().size() == 1
				&& d.getItemMeta().getLore().get(0).equals(biID));
	}

	private static boolean isMageBaguetteFeu(ItemStack f) {
		return (f.hasItemMeta() && f.getItemMeta() != null && f.getItemMeta().hasLore()
				&& f.getItemMeta().getLore() != null && f.getItemMeta().getLore().size() == 1
				&& f.getItemMeta().getLore().get(0).equals(bfID));
	}

	private static boolean isMageBaguetteAir(ItemStack a) {
		return (a.hasItemMeta() && a.getItemMeta() != null && a.getItemMeta().hasLore()
				&& a.getItemMeta().getLore() != null && a.getItemMeta().getLore().size() == 1
				&& a.getItemMeta().getLore().get(0).equals(baID));
	}

	private static boolean isMageRadio(ItemStack r) {
		return (r.hasItemMeta() && r.getItemMeta() != null && r.getItemMeta().hasLore()
				&& r.getItemMeta().getLore() != null && r.getItemMeta().getLore().size() == 1
				&& r.getItemMeta().getLore().get(0).equals(raID));
	}

	private static boolean isMageArmorFeu(ItemStack bf) {
		return (bf.hasItemMeta() && bf.getItemMeta() != null && bf.getItemMeta().hasLore()
				&& bf.getItemMeta().getLore() != null && bf.getItemMeta().getLore().size() == 1
				&& bf.getItemMeta().getLore().get(0).equals(ArmorFeuID));
	}

	private static boolean isMageArmorGlace(ItemStack bg) {
		return (bg.hasItemMeta() && bg.getItemMeta() != null && bg.getItemMeta().hasLore()
				&& bg.getItemMeta().getLore() != null && bg.getItemMeta().getLore().size() == 1
				&& bg.getItemMeta().getLore().get(0).equals(ArmorGlaceID));
	}
}
