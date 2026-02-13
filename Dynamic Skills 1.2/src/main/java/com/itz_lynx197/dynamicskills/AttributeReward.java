package com.example.dynamicskills;

import java.util.UUID;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;

class AttributeReward implements Reward {
   private final Attribute attribute;
   private final double amount;

   public AttributeReward(Attribute attribute, double amount) {
      this.attribute = attribute;
      this.amount = amount;
   }

   public void apply(Player player) {
      player.getAttribute(this.attribute).addModifier(new AttributeModifier(UUID.randomUUID(), "dynamic_skill", this.amount, Operation.ADD_NUMBER));
   }
}
