package org.Cry_Legende.sHRawPlaceholders;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SHRawPlaceholders extends JavaPlugin {

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().severe("PlaceholderAPI non trouvé ! Ce plugin ne peut pas fonctionner sans.");
            getLogger().severe("Veuillez installer PlaceholderAPI pour utiliser SHRawPlaceholders.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Enregistrement de notre expansion PlaceholderAPI
        new SHRawExpansion(this).register();
        getLogger().info("SHRawPlaceholders a été activé !");
        getLogger().info("Utilisation: %shraw_{placeholder_original}%");
    }

    @Override
    public void onDisable() {
        getLogger().info("SHRawPlaceholders a été désactivé.");
    }
}