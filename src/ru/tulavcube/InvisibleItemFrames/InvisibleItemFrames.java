package ru.tulavcube.InvisibleItemFrames;

import org.bukkit.craftbukkit.v1_17_R1.entity.CraftGlowItemFrame;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftItemFrame;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.tulavcube.InvisibleItemFrames.commands.findFrames;

import java.util.Collection;

public class InvisibleItemFrames extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getLogger().info("InvisibleItemFrames plugin started");
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("findframes").setExecutor(new findFrames(this));
        getCommand("findFrames").setTabCompleter(new findFrames(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("InvisibleItemFrames plugin stopped");
    }

    @EventHandler
    public void onPotionSplash(ProjectileHitEvent event) {
        if (event.getEntity() instanceof ThrownPotion potion) {
            Collection<PotionEffect> effects = potion.getEffects();
            if (effects.isEmpty()) {
                for (Entity entity : potion.getNearbyEntities(1, 1, 1)) {
                    if (entity.getType() == EntityType.ITEM_FRAME) {
                        ((CraftItemFrame) entity).setVisible(true);
                    }
                    else if(entity.getType() == EntityType.GLOW_ITEM_FRAME){
                        ((CraftGlowItemFrame) entity).setVisible(true);
                    }
                }
            } else {
                for (PotionEffect eff : effects) {
                    if (eff.getType().equals(PotionEffectType.INVISIBILITY)) {
                        for (Entity entity : potion.getNearbyEntities(1, 1, 1)) {
                            if (entity.getType() == EntityType.ITEM_FRAME) {
                                ((ItemFrame) entity).setVisible(false);
                            }
                            else if(entity.getType() == EntityType.GLOW_ITEM_FRAME){
                                ((GlowItemFrame) entity).setVisible(false);
                            }
                        }
                        return;
                    }
                }
            }
        }
    }
}
