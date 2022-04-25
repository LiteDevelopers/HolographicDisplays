/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.api.beta.hologram.line;

import org.jetbrains.annotations.Nullable;

/**
 * @since 1
 */
public interface JsonComponentHologramLine extends ClickableHologramLine {

    /**
     * Returns the current JSON component.
     *
     * @return the current JSON component
     * @since 1
     */
    @Nullable String getJson();

    /**
     * Sets the text in the vanilla json format.
     *
     * @param json the text in the vanilla json format.
     * @since 1
     */
    void setJson(@Nullable String json);

}
