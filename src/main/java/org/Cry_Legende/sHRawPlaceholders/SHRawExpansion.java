package org.Cry_Legende.sHRawPlaceholders;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SHRawExpansion extends PlaceholderExpansion {

    private final SHRawPlaceholders plugin;

    public SHRawExpansion(SHRawPlaceholders plugin) {
        this.plugin = plugin;
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "shraw"; // Le préfixe de nos placeholders
    }

    @Override
    @NotNull
    public String getAuthor() {
        return "Cry_Legende";
    }

    @Override
    @NotNull
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true; // Les placeholders peuvent être utilisés même si le joueur est hors ligne
    }

    @Override
    public boolean canRegister() {
        return true; // Indique à PAPI que cette expansion peut être enregistrée
    }

    /**
     * C'est ici que la magie opère.
     * Le placeholder sera sous la forme %shraw_IDENTIFIANT_DU_PLACEHOLDER_ORIGINAL%
     * Par exemple : %shraw_{cookieclicker_cookies}%
     * `params` contiendra alors "{cookieclicker_cookies}"
     */
    @Override
    @Nullable
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        // 1. Vérifier si les paramètres sont bien entourés d'accolades
        if (!params.startsWith("{") || !params.endsWith("}")) {
            plugin.getLogger().warning("Format de placeholder incorrect pour shraw : " + params + ". Attendu : {placeholder_original}");
            return null; // ou retourner une erreur, par ex. "FORMAT_INCORRECT"
        }

        // 2. Extraire le placeholder original (sans les accolades)
        String originalPlaceholder = params.substring(1, params.length() - 1);

        // 3. Ajouter les % autour pour que PAPI le reconnaisse
        String placeholderToParse = "%" + originalPlaceholder + "%";

        // 4. Utiliser PAPI pour obtenir la valeur du placeholder original
        String formattedValue = PlaceholderAPI.setPlaceholders(player, placeholderToParse);

        // 5. Si le placeholder original n'existe pas ou retourne lui-même, on ne fait rien
        if (formattedValue == null || formattedValue.equals(placeholderToParse)) {
            return formattedValue; // Retourne la valeur telle quelle (ou null si PAPI n'a rien trouvé)
        }

        // 6. Retirer le formatage (tout ce qui n'est pas un chiffre)
        // Ceci enlèvera les virgules, points (sauf si on veut garder les décimaux), espaces, etc.
        // Si tu veux garder les points pour les nombres décimaux, utilise : .replaceAll("[^0-9.]", "")
        String rawValue = formattedValue.replaceAll("[^0-9]", "");

        return rawValue;
    }
}