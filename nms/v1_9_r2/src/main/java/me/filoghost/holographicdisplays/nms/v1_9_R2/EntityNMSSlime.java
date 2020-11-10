/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package me.filoghost.holographicdisplays.nms.v1_9_R2;

import me.filoghost.holographicdisplays.api.line.HologramLine;
import me.filoghost.holographicdisplays.nms.interfaces.entity.NMSEntityBase;
import me.filoghost.holographicdisplays.nms.interfaces.entity.NMSSlime;
import me.filoghost.holographicdisplays.util.ConsoleLogger;
import me.filoghost.holographicdisplays.util.Utils;
import me.filoghost.holographicdisplays.util.reflection.ReflectField;
import net.minecraft.server.v1_9_R2.AxisAlignedBB;
import net.minecraft.server.v1_9_R2.DamageSource;
import net.minecraft.server.v1_9_R2.Entity;
import net.minecraft.server.v1_9_R2.EntityDamageSource;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.EntitySlime;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import net.minecraft.server.v1_9_R2.PacketPlayOutMount;
import net.minecraft.server.v1_9_R2.SoundEffect;
import net.minecraft.server.v1_9_R2.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftEntity;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.logging.Level;

public class EntityNMSSlime extends EntitySlime implements NMSSlime {
    
    private static final ReflectField<Entity> VEHICLE_FIELD = new ReflectField<>(Entity.class, "at");

    private HologramLine parentPiece;
    
    private int resendMountPacketTicks;
    
    public EntityNMSSlime(World world, HologramLine parentPiece) {
        super(world);
        super.persistent = true;
        a(0.0F, 0.0F);
        setSize(1);
        setInvisible(true);
        this.parentPiece = parentPiece;
        forceSetBoundingBox(new NullBoundingBox());
    }
    
    @Override
    public void m() {
        // Disable normal ticking for this entity.
        
        // So it won't get removed.
        ticksLived = 0;
        
        if (resendMountPacketTicks++ > 20) {
            resendMountPacketTicks = 0;

            if (bz() != null) {
                // Send a packet near to "remind" players that the slime is riding the armor stand (Spigot bug or client bug)
                PacketPlayOutMount mountPacket = new PacketPlayOutMount(bz());
    
                for (Object obj : super.world.players) {
                    if (obj instanceof EntityPlayer) {
                        EntityPlayer nmsPlayer = (EntityPlayer) obj;
    
                        double distanceSquared = Utils.square(nmsPlayer.locX - super.locX) + Utils.square(nmsPlayer.locZ - super.locZ);
                        if (distanceSquared < 1024 && nmsPlayer.playerConnection != null) {
                            nmsPlayer.playerConnection.sendPacket(mountPacket);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void inactiveTick() {
        // Disable normal ticking for this entity.
        
        // So it won't get removed.
        ticksLived = 0;
    }
    
    @Override
    public void a(AxisAlignedBB boundingBox) {
        // Do not change it!
    }
    
    public void forceSetBoundingBox(AxisAlignedBB boundingBox) {
        super.a(boundingBox);
    }
    
    @Override
    public void b(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
    }
    
    @Override
    public boolean c(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
        return false;
    }

    @Override
    public boolean d(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
        return false;
    }
    
    @Override
    public NBTTagCompound e(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
        return nbttagcompound;
    }
    
    @Override
    public void f(NBTTagCompound nbttagcompound) {
        // Do not load NBT.
    }
    
    @Override
    public void a(NBTTagCompound nbttagcompound) {
        // Do not load NBT.
    }
    
    @Override
    public boolean damageEntity(DamageSource damageSource, float amount) {
        if (damageSource instanceof EntityDamageSource) {
            EntityDamageSource entityDamageSource = (EntityDamageSource) damageSource;
            if (entityDamageSource.getEntity() instanceof EntityPlayer) {
                Bukkit.getPluginManager().callEvent(new PlayerInteractEntityEvent(((EntityPlayer) entityDamageSource.getEntity()).getBukkitEntity(), getBukkitEntity())); // Bukkit takes care of the exceptions
            }
        }
        return false;
    }
    
    @Override
    public boolean isInvulnerable(DamageSource source) {
        /*
         * The field Entity.invulnerable is private.
         * It's only used while saving NBTTags, but since the entity would be killed
         * on chunk unload, we prefer to override isInvulnerable().
         */
        return true;
    }
    
    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public void setCustomName(String customName) {
        // Locks the custom name.
    }
    
    @Override
    public void setCustomNameVisible(boolean visible) {
        // Locks the custom name.
    }
    
    @Override
    public void a(SoundEffect soundeffect, float f, float f1) {
        // Remove sounds.
    }
    
    @Override
    public void die() {
        // Prevent being killed.
    }
    
    @Override
    public CraftEntity getBukkitEntity() {
        if (super.bukkitEntity == null) {
            super.bukkitEntity = new CraftNMSSlime(super.world.getServer(), this);
        }
        return super.bukkitEntity;
    }

    @Override
    public boolean isDeadNMS() {
        return super.dead;
    }
    
    @Override
    public void killEntityNMS() {
        super.dead = true;
    }
    
    @Override
    public void setLocationNMS(double x, double y, double z) {
        super.setPosition(x, y, z);
    }
    
    @Override
    public int getIdNMS() {
        return super.getId();
    }
    
    @Override
    public HologramLine getHologramLine() {
        return parentPiece;
    }

    @Override
    public org.bukkit.entity.Entity getBukkitEntityNMS() {
        return getBukkitEntity();
    }
    
    @Override
    public void setPassengerOfNMS(NMSEntityBase vehicleBase) {
        if (vehicleBase == null || !(vehicleBase instanceof Entity)) {
            // It should never dismount
            return;
        }
        
        Entity entity = (Entity) vehicleBase;
        
        try {
            if (super.bz() != null) {
                Entity oldVehicle = super.bz();
                VEHICLE_FIELD.set(this, null);
                oldVehicle.passengers.remove(this);
            }

            VEHICLE_FIELD.set(this, entity);
            entity.passengers.clear();
            entity.passengers.add(this);

        } catch (Throwable t) {
            ConsoleLogger.logDebug(Level.SEVERE, "Couldn't set passenger", t);
        }
    }
}
