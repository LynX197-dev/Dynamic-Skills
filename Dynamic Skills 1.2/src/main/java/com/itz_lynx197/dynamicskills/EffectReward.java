package com.example.dynamicskills;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

class EffectReward implements Reward {
   private final PotionEffectType type;
   private final int level;

   public EffectReward(PotionEffectType type, int level) {
      this.type = type;
      this.level = level;
   }

   public void apply(Player player) {
      player.addPotionEffect(new PotionEffect(this.type, Integer.MAX_VALUE, this.level - 1, false, false));
   }
}
