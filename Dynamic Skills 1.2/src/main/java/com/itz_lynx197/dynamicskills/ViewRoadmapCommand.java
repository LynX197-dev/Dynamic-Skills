package com.example.dynamicskills;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ViewRoadmapCommand implements CommandExecutor {
   private final SkillManager skillManager;
   private final RoadmapGUI gui;

   public ViewRoadmapCommand(SkillManager skillManager) {
      this.skillManager = skillManager;
      this.gui = new RoadmapGUI(skillManager);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!(sender instanceof Player)) {
         return false;
      } else {
         Player player = (Player)sender;
         this.gui.openRoadmap(player);
         return true;
      }
   }
}
