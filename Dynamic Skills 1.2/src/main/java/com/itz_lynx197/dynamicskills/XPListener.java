package com.example.dynamicskills;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;

public class XPListener implements Listener {
   private final SkillManager skillManager;

   public XPListener(SkillManager skillManager) {
      this.skillManager = skillManager;
   }

   @EventHandler
   public void onEntityDeath(EntityDeathEvent event) {
      if (event.getEntity().getKiller() instanceof Player) {
         Player player = event.getEntity().getKiller();
         PlayerData data = this.skillManager.getPlayerData(player.getUniqueId());
         if (data.getCategory() == Category.COMBAT) {
            this.skillManager.addProgress(player.getUniqueId(), this.skillManager.getXpPoints());
         }
      }

   }

   @EventHandler
   public void onBlockBreak(BlockBreakEvent event) {
      Player player = event.getPlayer();
      PlayerData data = this.skillManager.getPlayerData(player.getUniqueId());
      if (data.getCategory() == Category.MINING && this.isValuableBlock(event.getBlock().getType())) {
         this.skillManager.addProgress(player.getUniqueId(), this.skillManager.getXpPoints());
      }

   }

   @EventHandler
   public void onBlockPlace(BlockPlaceEvent event) {
      Player player = event.getPlayer();
      PlayerData data = this.skillManager.getPlayerData(player.getUniqueId());
      if (data.getCategory() == Category.BUILDING) {
         this.skillManager.addProgress(player.getUniqueId(), this.skillManager.getXpPoints());
      }

   }

   @EventHandler
   public void onCraftItem(CraftItemEvent event) {
      if (event.getWhoClicked() instanceof Player) {
         Player player = (Player)event.getWhoClicked();
         PlayerData data = this.skillManager.getPlayerData(player.getUniqueId());
         if (data.getCategory() == Category.BUILDING) {
            this.skillManager.addProgress(player.getUniqueId(), this.skillManager.getXpPoints());
         }
      }

   }

   private boolean isValuableBlock(Material material) {
      return material.name().contains("ORE") || material == Material.STONE || material == Material.COBBLESTONE;
   }
}
