/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.plugin.hologram.tracking;

import me.filoghost.holographicdisplays.common.nms.EntityID;
import me.filoghost.holographicdisplays.common.nms.NMSManager;
import me.filoghost.holographicdisplays.common.nms.NMSPacketList;
import me.filoghost.holographicdisplays.plugin.hologram.base.BaseClickableLine;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

public abstract class ClickableLineTracker<T extends BaseClickableLine> extends LocationBasedLineTracker<T> {

    private static final double SLIME_HEIGHT = 0.5;

    private final LineClickListener lineClickListener;
    private final EntityID vehicleEntityID;
    private final EntityID slimeEntityID;

    private boolean spawnSlimeEntities;
    private boolean spawnSlimeEntitiesChanged;

    public ClickableLineTracker(T line, NMSManager nmsManager, LineClickListener lineClickListener) {
        super(line, nmsManager);
        this.vehicleEntityID = nmsManager.newEntityID();
        this.slimeEntityID = nmsManager.newEntityID();
        this.lineClickListener = lineClickListener;
    }

    @MustBeInvokedByOverriders
    @Override
    public void onRemoval() {
        super.onRemoval();
        lineClickListener.unregisterLine(slimeEntityID);
    }

    @MustBeInvokedByOverriders
    @Override
    protected void detectChanges() {
        super.detectChanges();

        boolean spawnSlimeEntities = line.getClickListener() != null;
        if (this.spawnSlimeEntities != spawnSlimeEntities) {
            this.spawnSlimeEntities = spawnSlimeEntities;
            this.spawnSlimeEntitiesChanged = true;
            if (spawnSlimeEntities) {
                lineClickListener.registerLine(slimeEntityID, line);
            } else {
                lineClickListener.unregisterLine(slimeEntityID);
            }
        }
    }

    @MustBeInvokedByOverriders
    @Override
    protected void clearDetectedChanges() {
        super.clearDetectedChanges();
        this.spawnSlimeEntitiesChanged = false;
    }

    @MustBeInvokedByOverriders
    @Override
    protected void addSpawnPackets(NMSPacketList packetList) {
        if (spawnSlimeEntities) {
            addSlimeSpawnPackets(packetList);
        }
    }

    @MustBeInvokedByOverriders
    @Override
    protected void addDestroyPackets(NMSPacketList packetList) {
        if (spawnSlimeEntities) {
            addSlimeDestroyPackets(packetList);
        }
    }

    @MustBeInvokedByOverriders
    @Override
    protected void addChangesPackets(NMSPacketList packetList) {
        super.addChangesPackets(packetList);

        if (spawnSlimeEntitiesChanged) {
            if (spawnSlimeEntities) {
                addSlimeSpawnPackets(packetList);
            } else {
                addSlimeDestroyPackets(packetList);
            }
        }
    }

    @MustBeInvokedByOverriders
    @Override
    protected void addLocationChangePackets(NMSPacketList packetList) {
        if (spawnSlimeEntities) {
            packetList.addTeleportPackets(vehicleEntityID, locationX, getSlimeLocationY(), locationZ);
        }
    }

    private void addSlimeSpawnPackets(NMSPacketList packetList) {
        packetList.addArmorStandSpawnPackets(vehicleEntityID, locationX, getSlimeLocationY(), locationZ);
        packetList.addSlimeSpawnPackets(slimeEntityID, locationX, getSlimeLocationY(), locationZ);
        packetList.addMountPackets(vehicleEntityID, slimeEntityID);
    }

    private void addSlimeDestroyPackets(NMSPacketList packetList) {
        packetList.addEntityDestroyPackets(slimeEntityID, vehicleEntityID);
    }

    private double getSlimeLocationY() {
        return locationY + ((line.getHeight() - SLIME_HEIGHT) / 2);
    }

}