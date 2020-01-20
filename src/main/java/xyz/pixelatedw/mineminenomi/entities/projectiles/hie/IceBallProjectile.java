package xyz.pixelatedw.mineminenomi.entities.projectiles.hie;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.math.WyMathHelper;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.particles.data.GenericParticleData;

public class IceBallProjectile extends AbilityProjectileEntity
{
	public IceBallProjectile(World world)
	{
		super(HieProjectiles.ICE_BALL, world);
	}

	public IceBallProjectile(EntityType type, World world)
	{
		super(type, world);
	}

	public IceBallProjectile(World world, double x, double y, double z)
	{
		super(HieProjectiles.ICE_BALL, world, x, y, z);
	}

	public IceBallProjectile(World world, LivingEntity player)
	{
		super(HieProjectiles.ICE_BALL, world, player);

		this.setDamage(5);
		
		this.onBlockImpactEvent = this::onBlockImpactEvent;
		this.onTickEvent = this::onTickEvent;
	}

	private void onBlockImpactEvent(BlockRayTraceResult hit)
	{		
		WyHelper.createEmptySphere(this.world, (int)this.posX, (int)this.posY, (int)this.posZ, 6, Blocks.PACKED_ICE, "air", "liquid", "foliage");
	}

	private void onTickEvent()
	{
		if (!this.world.isRemote)
		{
			for (int i = 0; i < 10; i++)
			{
				double offsetX = WyMathHelper.randomDouble() / 1.5;
				double offsetY = WyMathHelper.randomDouble() / 1.5;
				double offsetZ = WyMathHelper.randomDouble() / 1.5;

				GenericParticleData data = new GenericParticleData();
				data.setTexture(ModResources.HIE);
				data.setLife(3);
				data.setSize(1.5F);
				((ServerWorld) this.world).spawnParticle(data, this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ, 1, 0, 0, 0, 0.0D);
			}
		}
	}
}