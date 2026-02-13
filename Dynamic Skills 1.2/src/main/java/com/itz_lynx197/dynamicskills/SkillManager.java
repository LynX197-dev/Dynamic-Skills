package com.example.dynamicskills;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class SkillManager {
   private final DynamicSkills plugin;
   private final Map<UUID, PlayerData> playerData;
   private final Map<Category, Map<Integer, Reward[]>> rewards;
   private double pointsPerLevel;
   private double xpPoints;
   private double advancementPoints;
   private final Map<String, String> messages;

   public SkillManager(DynamicSkills plugin) {
      this.plugin = plugin;
      this.pointsPerLevel = plugin.getConfig().getDouble("points_per_level", 1000.0D);
      this.xpPoints = plugin.getConfig().getDouble("xp_points", 10.0D);
      this.advancementPoints = plugin.getConfig().getDouble("advancement_points", 100.0D);
      this.messages = new HashMap();
      this.loadMessages();
      this.playerData = new HashMap();
      this.rewards = this.initializeRewards();
   }

   private void loadMessages() {
      this.messages.put("category_selected",
            ChatColor.translateAlternateColorCodes('&',
                  this.plugin.getConfig().getString("messages.category_selected", "&aCategory selected: %category%")));
      this.messages.put("already_selected",
            ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("messages.already_selected",
                  "&cYou have already selected a category!")));
      this.messages.put("reward_applied",
            ChatColor.translateAlternateColorCodes('&',
                  this.plugin.getConfig().getString("messages.reward_applied", "&aReward applied!")));
      this.messages.put("level_up",
            ChatColor.translateAlternateColorCodes('&',
                  this.plugin.getConfig().getString("messages.level_up", "&eYou leveled up to level %level%!")));
   }

   public void reload() {
      this.plugin.reloadConfig();
      this.pointsPerLevel = this.plugin.getConfig().getDouble("points_per_level", 1000.0D);
      this.xpPoints = this.plugin.getConfig().getDouble("xp_points", 10.0D);
      this.advancementPoints = this.plugin.getConfig().getDouble("advancement_points", 100.0D);
      this.loadMessages();
   }

   private Map<Category, Map<Integer, Reward[]>> initializeRewards() {
      Map<Category, Map<Integer, Reward[]>> map = new HashMap();
      Map<Integer, Reward[]> combat = new HashMap();
      combat.put(1, new Reward[] { new EffectReward(PotionEffectType.STRENGTH, 1),
            new EffectReward(PotionEffectType.REGENERATION, 1) });
      combat.put(2, new Reward[] { new AttributeReward(Attribute.ATTACK_DAMAGE, 0.5D),
            new AttributeReward(Attribute.ARMOR, 1.0D) });
      combat.put(3, new Reward[] { new EffectReward(PotionEffectType.SPEED, 1),
            new AttributeReward(Attribute.ARMOR_TOUGHNESS, 0.5D) });
      combat.put(4, new Reward[] { new AttributeReward(Attribute.ATTACK_DAMAGE, 1.5D),
            new AttributeReward(Attribute.ARMOR, 2.0D) });
      map.put(Category.COMBAT, combat);
      return map;
   }

   public PlayerData getPlayerData(UUID uuid) {
      return (PlayerData) this.playerData.computeIfAbsent(uuid, (id) -> {
         return PlayerData.load(id, this.plugin.getDataFolder());
      });
   }

   public void saveAllData() {
      Iterator var1 = this.playerData.values().iterator();

      while (var1.hasNext()) {
         PlayerData data = (PlayerData) var1.next();
         data.save(this.plugin.getDataFolder());
      }

   }

   public void selectCategory(Player player, Category category) {
      PlayerData data = this.getPlayerData(player.getUniqueId());
      if (data.getCategory() != null) {
         player.sendMessage((String) this.messages.get("already_selected"));
      } else {
         data.setCategory(category);
         player.sendMessage(((String) this.messages.get("category_selected")).replace("%category%", category.name()));
      }
   }

   public void applyReward(Player player, int level, int choice) {
      PlayerData data = this.getPlayerData(player.getUniqueId());
      if (!data.getChosenRewards().containsKey(level)) {
         data.addChosenReward(level, choice);
         Reward reward = ((Reward[]) ((Map) this.rewards.get(data.getCategory())).get(level))[choice];
         reward.apply(player);
         player.sendMessage((String) this.messages.get("reward_applied"));
      }
   }

   public void addProgress(UUID uuid, double amount) {
      PlayerData data = this.getPlayerData(uuid);
      int oldLevel = data.getLevel(this.pointsPerLevel);
      data.addProgress(amount);
      int newLevel = data.getLevel(this.pointsPerLevel);
      if (newLevel > oldLevel) {
         Player player = this.plugin.getServer().getPlayer(uuid);
         if (player != null) {
            player.sendMessage(((String) this.messages.get("level_up")).replace("%level%", String.valueOf(newLevel)));
         }
      }

   }

   public double getXpPoints() {
      return this.xpPoints;
   }

   public double getAdvancementPoints() {
      return this.advancementPoints;
   }

   public double getPointsPerLevel() {
      return this.pointsPerLevel;
   }
}
