package com.example.dynamicskills;

import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvancementListener implements Listener {
   private final SkillManager skillManager;

   public AdvancementListener(SkillManager skillManager) {
      this.skillManager = skillManager;
   }

   @EventHandler
   public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
      Player player = event.getPlayer();
      PlayerData data = this.skillManager.getPlayerData(player.getUniqueId());
      if (data.getCategory() != null && this.isRelevantAdvancement(data.getCategory(), event.getAdvancement())) {
         this.skillManager.addProgress(player.getUniqueId(), this.skillManager.getAdvancementPoints());
      }

   }

   private boolean isRelevantAdvancement(Category category, Advancement advancement) {
      String key = advancement.getKey().getKey();
      switch(category) {
      case COMBAT:
         return key.contains("kill") || key.contains("combat") || key.contains("monster") || key.contains("hero");
      case MINING:
         return key.contains("mine") || key.contains("ore") || key.contains("stone") || key.contains("iron") || key.contains("diamond");
      case BUILDING:
         return key.contains("craft") || key.contains("build") || key.contains("farm") || key.contains("breed");
      case EXPLORATION:
         return key.contains("explore") || key.contains("adventure") || key.contains("nether") || key.contains("end") || key.contains("biome");
      default:
         return false;
      }
   }
}
