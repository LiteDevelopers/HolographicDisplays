/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.plugin.internal.hologram;

import me.filoghost.holographicdisplays.plugin.HolographicDisplays;
import me.filoghost.holographicdisplays.plugin.api.current.DefaultVisibilitySettings;
import me.filoghost.holographicdisplays.plugin.hologram.base.BaseHologram;
import me.filoghost.holographicdisplays.plugin.hologram.base.BaseHologramLines;
import me.filoghost.holographicdisplays.plugin.hologram.base.ImmutablePosition;
import me.filoghost.holographicdisplays.plugin.hologram.tracking.LineTrackerManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class InternalHologram extends BaseHologram {

    private final BaseHologramLines<InternalHologramLine> lines;
    private final String name;
    private final DefaultVisibilitySettings visibilitySettings;

    protected InternalHologram(ImmutablePosition position, String name, LineTrackerManager lineTrackerManager) {
        super(position, lineTrackerManager);
        this.lines = new BaseHologramLines<>(this);
        this.name = name;
        this.visibilitySettings = new DefaultVisibilitySettings();
    }

    @Override
    public BaseHologramLines<InternalHologramLine> getLines() {
        return lines;
    }

    public InternalTextHologramLine createTextLine(String text, String serializedConfigValue) {
        return new InternalTextHologramLine(this, text, serializedConfigValue);
    }

    public InternalJsonComponentHologramLine createJsonComponentLine(String text, String serializedConfigValue) {
        return new InternalJsonComponentHologramLine(this, text, serializedConfigValue);
    }

    public InternalItemHologramLine createItemLine(ItemStack icon, String serializedConfigValue) {
        return new InternalItemHologramLine(this, icon, serializedConfigValue);
    }

    public String getName() {
        return name;
    }

    public DefaultVisibilitySettings getVisibilitySettings() {
        return visibilitySettings;
    }

    @Override
    public Plugin getCreatorPlugin() {
        return HolographicDisplays.getInstance();
    }

    @Override
    public boolean isVisibleTo(Player player) {
        return visibilitySettings.isVisibleTo(player);
    }

    @Override
    public String toString() {
        return "InternalHologram{"
                + "name=" + name
                + ", super=" + super.toString()
                + "}";
    }

}
