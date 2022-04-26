/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.plugin.api.current;

import me.filoghost.holographicdisplays.api.beta.hologram.line.HologramLineClickListener;
import me.filoghost.holographicdisplays.api.beta.hologram.line.JsonComponentHologramLine;
import me.filoghost.holographicdisplays.nms.common.entity.TextNMSPacketEntity;
import me.filoghost.holographicdisplays.plugin.hologram.base.BaseTextHologramLine;
import org.jetbrains.annotations.Nullable;

class APIJsonComponentHologramLine extends BaseTextHologramLine implements JsonComponentHologramLine, APIClickableHologramLine {

    private HologramLineClickListener clickListener;

    APIJsonComponentHologramLine(APIHologram hologram, String json) {
        super(hologram, json);
    }


    @Override
    public void setClickListener(@Nullable HologramLineClickListener clickListener) {
        checkNotDeleted();

        this.clickListener = clickListener;
    }

    @Override
    public @Nullable HologramLineClickListener getClickListener() {
        return clickListener;
    }

    @Override
    public @Nullable String getJson() {
        return this.getText();
    }

    @Override
    public void setJson(@Nullable String json) {
        this.setText(json);
    }

    @Override
    public boolean isAllowPlaceholders() {
        return true;
    }

    @Override
    public boolean isJsonComponent() {
        return true;
    }

    @Override
    public double getHeight() {
        return TextNMSPacketEntity.ARMOR_STAND_TEXT_HEIGHT;
    }

}
