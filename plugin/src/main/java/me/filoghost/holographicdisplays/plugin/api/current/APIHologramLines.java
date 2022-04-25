/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.plugin.api.current;

import me.filoghost.fcommons.Preconditions;
import me.filoghost.holographicdisplays.api.beta.hologram.HologramLines;
import me.filoghost.holographicdisplays.api.beta.hologram.line.HologramLine;
import me.filoghost.holographicdisplays.api.beta.hologram.line.ItemHologramLine;
import me.filoghost.holographicdisplays.api.beta.hologram.line.JsonComponentHologramLine;
import me.filoghost.holographicdisplays.api.beta.hologram.line.TextHologramLine;
import me.filoghost.holographicdisplays.plugin.hologram.base.BaseHologramLines;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class APIHologramLines extends BaseHologramLines<APIHologramLine> implements HologramLines {

    private final APIHologram hologram;

    APIHologramLines(APIHologram hologram) {
        super(hologram);
        this.hologram = hologram;
    }

    @Override
    public @NotNull TextHologramLine appendText(@Nullable String text) {
        checkNotDeleted();

        APITextHologramLine line = new APITextHologramLine(hologram, text);
        super.add(line);
        return line;
    }

    @Override
    public @NotNull JsonComponentHologramLine appendComponent(@Nullable String json) {
        checkNotDeleted();

        APIJsonComponentHologramLine line = new APIJsonComponentHologramLine(hologram, json);
        super.add(line);
        return line;
    }

    @Override
    public @NotNull ItemHologramLine appendItem(@Nullable ItemStack itemStack) {
        checkNotDeleted();

        APIItemHologramLine line = new APIItemHologramLine(hologram, itemStack);
        super.add(line);
        return line;
    }

    @Override
    public @NotNull TextHologramLine insertText(int beforeIndex, @Nullable String text) {
        checkNotDeleted();

        APITextHologramLine line = new APITextHologramLine(hologram, text);
        super.insert(beforeIndex, line);
        return line;
    }

    @Override
    public @NotNull JsonComponentHologramLine insertComponent(int beforeIndex, @Nullable String json) {
        checkNotDeleted();

        APIJsonComponentHologramLine line = new APIJsonComponentHologramLine(hologram, json);
        super.insert(beforeIndex, line);
        return line;
    }

    @Override
    public @NotNull ItemHologramLine insertItem(int beforeIndex, @NotNull ItemStack itemStack) {
        Preconditions.notNull(itemStack, "itemStack");
        checkNotDeleted();

        APIItemHologramLine line = new APIItemHologramLine(hologram, itemStack);
        super.insert(beforeIndex, line);
        return line;
    }

    @Override
    public boolean remove(@NotNull HologramLine line) {
        if (line instanceof APIHologramLine) {
            return super.remove((APIHologramLine) line);
        } else {
            return false;
        }
    }

}
