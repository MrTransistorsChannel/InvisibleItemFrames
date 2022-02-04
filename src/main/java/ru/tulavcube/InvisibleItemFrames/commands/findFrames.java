package ru.tulavcube.InvisibleItemFrames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.GlowItemFrame;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class findFrames implements TabExecutor {

    private final Plugin _plg;

    public findFrames(Plugin plg) {
        _plg = plg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            List<Entity> entities = ((Player) sender).getNearbyEntities(10, 10, 10);
            entities.removeIf(entity -> {
                if(entity instanceof GlowItemFrame){
                    return ((ItemFrame) entity).isVisible();
                }
                else if(entity instanceof ItemFrame){
                    return ((ItemFrame) entity).isVisible();
                }
                return true;
            });
            for (Entity entity : entities) {
                if (!entity.isDead()) {
                    entity.setGlowing(true);
                    ((ItemFrame) entity).setVisible(true);
                }
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Entity entity : entities) {
                        if (!entity.isDead()) {
                            entity.setGlowing(false);
                            ((ItemFrame) entity).setVisible(false);
                        }
                    }
                }
            }.runTaskLater(_plg, 200);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return new ArrayList<>();
    }
}
