package com.example.dynamicskills;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RoadmapGUI implements Listener {
   private final SkillManager skillManager;

   public RoadmapGUI(SkillManager skillManager) {
      this.skillManager = skillManager;
   }

   public void openRoadmap(Player player) {
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, "§8Skill Roadmap");
      PlayerData data = this.skillManager.getPlayerData(player.getUniqueId());
      int currentLevel = data.getLevel(this.skillManager.getPointsPerLevel());
      double progress = data.getProgress();

      for(int level = 1; level <= 10; ++level) {
         int slot = level - 1;
         Material mat = level <= currentLevel ? Material.ENCHANTED_BOOK : Material.BOOK;
         ItemStack item = new ItemStack(mat);
         ItemMeta meta = item.getItemMeta();
         meta.setDisplayName("§6Level " + level + (level <= currentLevel ? " §a(Unlocked)" : " §c(Locked)"));
         List<String> lore = new ArrayList();
         if (level == currentLevel + 1) {
            double nextThreshold = (double)currentLevel * this.skillManager.getPointsPerLevel();
            double currentProgress = progress - (double)(currentLevel - 1) * this.skillManager.getPointsPerLevel();
            lore.add("§7Progress: " + (int)currentProgress + "/" + (int)this.skillManager.getPointsPerLevel());
         }

         if (level <= currentLevel) {
            lore.add("§eChoice 1: ...");
            lore.add("§eChoice 2: ...");
         }

         meta.setLore(lore);
         item.setItemMeta(meta);
         inv.setItem(slot, item);
      }

      player.openInventory(inv);
   }

   @EventHandler
   public void onInventoryClick(InventoryClickEvent event) {
      if (event.getView().getTitle().equals("§8Skill Roadmap")) {
         event.setCancelled(true);
         Player player = (Player)event.getWhoClicked();
         int level = event.getSlot() + 1;
         PlayerData data = this.skillManager.getPlayerData(player.getUniqueId());
         if (level > data.getLevel(this.skillManager.getPointsPerLevel())) {
            player.sendMessage("§cThis level is not unlocked yet!");
         } else {
            this.skillManager.applyReward(player, level, 0);
         }
      }
   }
}
