package com.example.dynamicskills;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DynamicSkills extends JavaPlugin {
   private static DynamicSkills instance;
   private SkillManager skillManager;

   public void onEnable() {
      this.saveDefaultConfig();
      instance = this;
      this.skillManager = new SkillManager(this);
      this.getCommand("selectcategory").setExecutor(new SelectCategoryCommand(this.skillManager));
      this.getCommand("roadmap").setExecutor(new ViewRoadmapCommand(this.skillManager));
      this.getCommand("ds").setExecutor(new DynamicSkillsCommand(this.skillManager));
      this.getServer().getPluginManager().registerEvents(new XPListener(this.skillManager), this);
      this.getServer().getPluginManager().registerEvents(new AdvancementListener(this.skillManager), this);
      this.getServer().getPluginManager().registerEvents(new RoadmapGUI(this.skillManager), this);
      this.getLogger().info("Dynamic Skills enabled!");
      Bukkit.getConsoleSender().sendMessage(
            "§8╔══════════════════════════════════════════════════════════════════════╗\n     §8| §fDᴇsɪɢɴᴇᴅ Bʏ §bLʏиX §8| §3Sɪʟᴇɴᴛ §8| §aFᴀsᴛ §8| §cDᴇᴀᴅʟʏ §8|\n§8╚══════════════════════════════════════════════════════════════════════╝\n");
   }

   public void onDisable() {
      this.skillManager.saveAllData();
      this.getLogger().info("Dynamic Skills disabled!");
   }

   public static DynamicSkills getInstance() {
      return instance;
   }

   public SkillManager getSkillManager() {
      return this.skillManager;
   }
}
