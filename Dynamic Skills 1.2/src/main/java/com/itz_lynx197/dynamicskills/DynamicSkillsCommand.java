package com.example.dynamicskills;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DynamicSkillsCommand implements CommandExecutor {
   private final SkillManager skillManager;

   public DynamicSkillsCommand(SkillManager skillManager) {
      this.skillManager = skillManager;
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
         if (!sender.hasPermission("dynamicskills.reload")) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return true;
         } else {
            this.skillManager.reload();
            sender.sendMessage("§aDynamic Skills configuration reloaded!");
            return true;
         }
      } else {
         return false;
      }
   }
}