/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.nms.v1_12_R1;

import com.google.gson.JsonParseException;
import me.filoghost.fcommons.Strings;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftChatMessage;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

abstract class DataWatcherPacketBuilder<T> {

    private static final int MAX_CUSTOM_NAME_LENGTH = 5000;

    private final PacketByteBuffer packetByteBuffer;

    DataWatcherPacketBuilder(PacketByteBuffer packetByteBuffer) {
        this.packetByteBuffer = packetByteBuffer;
    }

    DataWatcherPacketBuilder<T> setInvisible() {
        packetByteBuffer.writeDataWatcherEntry(DataWatcherKey.ENTITY_STATUS, (byte) 0x20); // Invisible
        return this;
    }

    DataWatcherPacketBuilder<T> setArmorStandMarker() {
        setInvisible();
        packetByteBuffer.writeDataWatcherEntry(
                DataWatcherKey.ARMOR_STAND_STATUS, (byte) (0x01 | 0x02 | 0x08 | 0x10)); // Small, no gravity, no base plate, marker
        return this;
    }

    DataWatcherPacketBuilder<T> setCustomName(String customName, boolean json) {
        packetByteBuffer.writeDataWatcherEntry(DataWatcherKey.CUSTOM_NAME, Strings.truncate(customName, MAX_CUSTOM_NAME_LENGTH));
        packetByteBuffer.writeDataWatcherEntry(DataWatcherKey.CUSTOM_NAME_VISIBILITY, !Strings.isEmpty(customName));
        return this;
    }

    DataWatcherPacketBuilder<T> setItemStack(ItemStack itemStack) {
        packetByteBuffer.writeDataWatcherEntry(DataWatcherKey.ITEM_STACK, CraftItemStack.asNMSCopy(itemStack));
        return this;
    }

    DataWatcherPacketBuilder<T> setSlimeSmall() {
        packetByteBuffer.writeDataWatcherEntry(DataWatcherKey.SLIME_SIZE, 1);
        return this;
    }

    T build() {
        packetByteBuffer.writeDataWatcherEntriesEnd();
        return createPacket(packetByteBuffer);
    }

    abstract T createPacket(PacketByteBuffer packetByteBuffer);

}
