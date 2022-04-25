/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.nms.v1_14_R1;

import me.filoghost.holographicdisplays.common.PositionCoordinates;
import me.filoghost.holographicdisplays.nms.common.EntityID;
import me.filoghost.holographicdisplays.nms.common.IndividualTextPacketGroup;
import me.filoghost.holographicdisplays.nms.common.PacketGroup;
import me.filoghost.holographicdisplays.nms.common.entity.TextNMSPacketEntity;

class VersionTextNMSPacketEntity implements TextNMSPacketEntity {

    private final EntityID armorStandID;

    VersionTextNMSPacketEntity(EntityID armorStandID) {
        this.armorStandID = armorStandID;
    }

    @Override
    public PacketGroup newSpawnPackets(PositionCoordinates position, String text, boolean json) {
        return EntityLivingSpawnNMSPacket.builder(armorStandID, EntityTypeID.ARMOR_STAND, position, ARMOR_STAND_Y_OFFSET)
                .setArmorStandMarker()
                .setCustomName(text, json)
                .build();
    }

    @Override
    public IndividualTextPacketGroup newSpawnPackets(PositionCoordinates position) {
        return IndividualTextPacketGroup.of(
                (String text, boolean json) -> EntityLivingSpawnNMSPacket.builder(armorStandID, EntityTypeID.ARMOR_STAND, position, ARMOR_STAND_Y_OFFSET)
                        .setArmorStandMarker()
                        .setCustomName(text, json)
                        .build()
        );
    }

    @Override
    public PacketGroup newChangePackets(String text, boolean json) {
        return EntityMetadataNMSPacket.builder(armorStandID)
                .setCustomName(text, json)
                .build();
    }

    @Override
    public IndividualTextPacketGroup newChangePackets() {
        return IndividualTextPacketGroup.of(
                (String text, boolean json) -> EntityMetadataNMSPacket.builder(armorStandID)
                        .setCustomName(text, json)
                        .build()
        );
    }

    @Override
     public PacketGroup newTeleportPackets(PositionCoordinates position) {
        return new EntityTeleportNMSPacket(armorStandID, position, ARMOR_STAND_Y_OFFSET);
    }

    @Override
    public PacketGroup newDestroyPackets() {
        return new EntityDestroyNMSPacket(armorStandID);
    }

}
