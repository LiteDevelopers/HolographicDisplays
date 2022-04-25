/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.plugin.hologram.base;

import me.filoghost.holographicdisplays.nms.common.entity.TextNMSPacketEntity;
import me.filoghost.holographicdisplays.plugin.hologram.tracking.LineTrackerManager;
import me.filoghost.holographicdisplays.plugin.hologram.tracking.TextLineTracker;
import org.jetbrains.annotations.Nullable;

public abstract class BaseTextHologramLine extends BaseClickableHologramLine {

    private String text;

    public BaseTextHologramLine(BaseHologram hologram, String text) {
        super(hologram);
        setText(text);
    }

    public abstract boolean isAllowPlaceholders();

    public abstract boolean isJsonComponent();

    @Override
    protected TextLineTracker createTracker(LineTrackerManager trackerManager) {
        return trackerManager.startTracking(this);
    }

    public @Nullable String getText() {
        return text;
    }

    public void setText(@Nullable String text) {
        checkNotDeleted();

        this.text = text;
        setChanged();
    }

    @Override
    public double getHeight() {
        return TextNMSPacketEntity.ARMOR_STAND_TEXT_HEIGHT;
    }

    @Override
    public String toString() {
        return "TextLine{"
                + "text=" + text
                + "}";
    }

}
