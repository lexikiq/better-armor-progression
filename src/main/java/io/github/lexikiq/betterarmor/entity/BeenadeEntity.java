package io.github.lexikiq.betterarmor.entity;

import io.github.lexikiq.betterarmor.utils.Time;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BeenadeEntity extends BeeEntity {
    // this entity is the dumbest and most annoying thing i have ever written

    private UUID spawner = null;
    private int beenadeTicks = 0;
    private static final float BEENADE_SPEED = 1f;
    private static final int BEENADE_LIFE = Time.SECOND.of(0.5);
    private static final float BEENADE_POWER = .69f;

    public BeenadeEntity(EntityType<? extends BeeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        setBaby(true);
        setAiDisabled(true);
        setSilent(true);
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    protected void explode() {
        dead = true;
        world.createExplosion(this, getX(), getY(), getZ(), BEENADE_POWER, Explosion.DestructionType.NONE);
        remove();
    }

    @Override
    public @Nullable BeeEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        if(!this.world.isClient) {
            ++beenadeTicks;
            if (beenadeTicks <= BEENADE_LIFE) {
                if (getBlockState().shouldSuffocate(world, getBlockPos())) {
                    explode();
                } else {
                    for (Entity entity : world.getOtherEntities(this, getBoundingBox(getPose()).offset(getPos()))) {
                        if (!(entity instanceof BeenadeEntity) && entity.getUuid() != spawner) {
                            explode();
                        }
                    }
                }
            } else {
                explode();
            }
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        Vec3d addPos = getRotationVector().multiply(BEENADE_SPEED);
        setVelocityClient(addPos.x, addPos.y, addPos.z);
        setPos(getPos().add(addPos));
    }

    public void initialize(PlayerEntity spawner) {
        setRotation(spawner.getHeadYaw(), spawner.pitch);
        Vec3d addVec = spawner.getRotationVector();
        Vec3d pos = new Vec3d(spawner.getX(), spawner.getEyeY(), spawner.getZ()).add(addVec);
        setPos(pos);
        this.spawner = spawner.getUuid();
    }

    public void setPos(Vec3d pos) {
        setPos(pos.x, pos.y, pos.z);
    }
}
