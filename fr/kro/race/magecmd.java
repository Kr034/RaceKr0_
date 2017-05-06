package fr.kro.race;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nashoba24.wolvmc.WolvMC;

public class magecmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof org.bukkit.entity.Player) {

			Player p = ((org.bukkit.entity.Player) sender).getPlayer();
			Integer time = WolvMC.getTime(p.getName());

			if (cmd.getName().equalsIgnoreCase("mage")) {
				if (time >= 1500) {
					WolvMC.setRace(p, "mage");
				}
			}
		}
		return false;
	}

}
