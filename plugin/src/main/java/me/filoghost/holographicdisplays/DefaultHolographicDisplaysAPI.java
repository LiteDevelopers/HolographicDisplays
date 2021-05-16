/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays;

import me.filoghost.fcommons.Preconditions;
import me.filoghost.holographicdisplays.api.Hologram;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.placeholder.PlaceholderReplacer;
import me.filoghost.holographicdisplays.object.api.APIHologramManager;
import me.filoghost.holographicdisplays.placeholder.registry.PlaceholderRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class DefaultHolographicDisplaysAPI implements HolographicDisplaysAPI {

    private final Plugin plugin;
    private final APIHologramManager apiHologramManager;
    private final PlaceholderRegistry placeholderRegistry;

    public DefaultHolographicDisplaysAPI(Plugin plugin, APIHologramManager apiHologramManager, PlaceholderRegistry placeholderRegistry) {
        this.plugin = plugin;
        this.apiHologramManager = apiHologramManager;
        this.placeholderRegistry = placeholderRegistry;
    }

    @Override
    public @NotNull Hologram createHologram(@NotNull Location source) {
        Preconditions.notNull(source, "source");
        Preconditions.notNull(source.getWorld(), "source's world");
        Preconditions.checkState(Bukkit.isPrimaryThread(), "Async hologram creation");
        
        return apiHologramManager.createHologram(source, plugin);
    }
    
    @Override
    public void registerPlaceholder(@NotNull String identifier, int refreshIntervalTicks, @NotNull PlaceholderReplacer replacer) {
        Preconditions.notNull(identifier, "identifier");
        Preconditions.checkArgument(refreshIntervalTicks >= 0, "refreshIntervalTicks should be positive");
        Preconditions.notNull(replacer, "replacer");
        
        placeholderRegistry.registerReplacer(plugin, identifier, refreshIntervalTicks, replacer);
    }

    @Override
    public boolean isRegisteredPlaceholder(@NotNull String identifier) {
        Preconditions.notNull(identifier, "identifier");
        
        return placeholderRegistry.isRegisteredIdentifier(plugin, identifier);
    }

    @Override
    public @NotNull Collection<Hologram> getHolograms() {
        return apiHologramManager.getHologramsByPlugin(plugin);
    }

    @Override
    public @NotNull Collection<String> getRegisteredPlaceholders() {
        return placeholderRegistry.getRegisteredIdentifiers(plugin);
    }

    @Override
    public void unregisterPlaceholder(@NotNull String identifier) {
        Preconditions.notNull(identifier, "identifier");
        
        placeholderRegistry.unregister(plugin, identifier);
    }

    @Override
    public void unregisterPlaceholders() {
        placeholderRegistry.unregisterAll(plugin);
    }

}
