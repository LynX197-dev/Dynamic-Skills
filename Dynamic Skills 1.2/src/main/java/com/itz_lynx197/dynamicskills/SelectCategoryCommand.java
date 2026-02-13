package com.example.dynamicskills;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SelectCategoryCommand implements CommandExecutor {
   private final SkillManager skillManager;

   public SelectCategoryCommand(SkillManager skillManager) {
      this.skillManager = skillManager;
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!(sender instanceof Player)) {
         return false;
      } else {
         Player player = (Player) sender;
         if (args.length != 1) {
            return false;
         } else {
            try {
               Category category = Category.valueOf(args[0].toUpperCase());
               this.skillManager.selectCategory(player, category);
            } catch (IllegalArgumentException var7) {
               player.sendMessage("§cInvalid category!");
            }

            return true;
         }
      }
   }
}
