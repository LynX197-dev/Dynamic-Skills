package com.example.dynamicskills;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerData {
   private final UUID uuid;
   private Category category;
   private double progress;
   private Map<Integer, Integer> chosenRewards;

   public PlayerData(UUID uuid) {
      this.uuid = uuid;
      this.progress = 0.0D;
      this.chosenRewards = new HashMap();
   }

   public Category getCategory() {
      return this.category;
   }

   public void setCategory(Category category) {
      this.category = category;
   }

   public int getLevel(double pointsPerLevel) {
      return Math.min(10, (int)(this.progress / pointsPerLevel) + 1);
   }

   public double getProgress() {
      return this.progress;
   }

   public void addProgress(double amount) {
      this.progress += amount;
   }

   public Map<Integer, Integer> getChosenRewards() {
      return this.chosenRewards;
   }

   public void addChosenReward(int level, int choice) {
      this.chosenRewards.put(level, choice);
   }

   public void save(File dataFolder) {
      File file = new File(dataFolder, String.valueOf(this.uuid) + ".yml");
      YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
      config.set("category", this.category != null ? this.category.name() : null);
      config.set("progress", this.progress);
      config.set("chosenRewards", this.chosenRewards);

      try {
         config.save(file);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   public static PlayerData load(UUID uuid, File dataFolder) {
      File file = new File(dataFolder, String.valueOf(uuid) + ".yml");
      if (!file.exists()) {
         return new PlayerData(uuid);
      } else {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         PlayerData data = new PlayerData(uuid);
         String cat = config.getString("category");
         if (cat != null) {
            data.setCategory(Category.valueOf(cat));
         }

         data.progress = config.getDouble("progress");
         data.chosenRewards = (Map)config.get("chosenRewards", new HashMap());
         return data;
      }
   }
}
