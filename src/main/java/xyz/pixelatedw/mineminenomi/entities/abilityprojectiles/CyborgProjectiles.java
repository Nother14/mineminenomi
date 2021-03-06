package xyz.pixelatedw.mineminenomi.entities.abilityprojectiles;

import java.util.HashMap;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper.Direction;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttribute;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityProjectile.Data;
import xyz.pixelatedw.mineminenomi.api.math.WyMathHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.particles.CustomParticleData;
import xyz.pixelatedw.mineminenomi.values.ModValuesParticles;

public class CyborgProjectiles 
{
	
	public static HashMap<AbilityAttribute, AbilityProjectile.Data> projectiles = new HashMap<AbilityAttribute, AbilityProjectile.Data>();
	
	public static final EntityType FRESH_FIRE = WyRegistry.registerEntityType("fresh_fire", FreshFire::new);
	public static final EntityType RADICAL_BEAM = WyRegistry.registerEntityType("radical_beam", RadicalBeam::new);
	public static final EntityType STRONG_RIGHT = WyRegistry.registerEntityType("strong_right", StrongRight::new);
	public static final EntityType COUP_DE_VENT = WyRegistry.registerEntityType("coup_de_vent", CoupDeVent::new);
	
	static
	{
		projectiles.put(ModAttributes.FRESH_FIRE, new Data(FRESH_FIRE, FreshFire.class));
		projectiles.put(ModAttributes.RADICAL_BEAM, new Data(RADICAL_BEAM, RadicalBeam.class));
		projectiles.put(ModAttributes.STRONG_RIGHT, new Data(STRONG_RIGHT, StrongRight.class));
		projectiles.put(ModAttributes.COUP_DE_VENT, new Data(COUP_DE_VENT, CoupDeVent.class));
	}
	
	public static class CoupDeVent extends AbilityProjectile
	{
		public CoupDeVent(World world)
		{super(COUP_DE_VENT, world);}
		
		public CoupDeVent(EntityType type, World world)
		{super(type, world);}
		
		public CoupDeVent(World world, double x, double y, double z)
		{super(COUP_DE_VENT, world, x, y, z);}
		
		public CoupDeVent(World world, LivingEntity player, AbilityAttribute attr) 
		{		
			super(COUP_DE_VENT, world, player, attr);		
		}
		
		@Override
		public void tasksImapct(RayTraceResult hit)
		{
			if(hit.getType() == Type.ENTITY)
			{
				EntityRayTraceResult entityHit = (EntityRayTraceResult) hit;
				LivingEntity entity = (LivingEntity) entityHit.getEntity();
				
				double newPosX = 0, newPosY = 0, newPosZ = 0;
				
				entity.setMotion(entity.getMotion().x, entity.getMotion().y + 2, entity.getMotion().z);
				Direction dir = WyHelper.get4Directions(entity);
				if(dir == WyHelper.Direction.SOUTH)
					newPosX += WyMathHelper.randomWithRange(-5, 5);
				else if(dir == WyHelper.Direction.EAST)
					newPosX -= WyMathHelper.randomWithRange(-5, 5);
				else if(dir == WyHelper.Direction.NORTH)
					newPosZ += WyMathHelper.randomWithRange(-5, 5);
				else if(dir == WyHelper.Direction.WEST)  
					newPosZ -= WyMathHelper.randomWithRange(-5, 5);

				entity.setPositionAndUpdate(entity.posX + newPosX, entity.posY + newPosY, entity.posZ + newPosZ);
			}
		}
		
		@Override
		public void tick()
		{				
			this.world.addParticle(ParticleTypes.CRIT, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);			
			super.tick();
		}
	}
	
	public static class StrongRight extends AbilityProjectile
	{
		public StrongRight(World world)
		{super(STRONG_RIGHT, world);}
		
		public StrongRight(EntityType type, World world)
		{super(type, world);}
		
		public StrongRight(World world, double x, double y, double z)
		{super(STRONG_RIGHT, world, x, y, z);}
		
		public StrongRight(World world, LivingEntity player, AbilityAttribute attr) 
		{		
			super(STRONG_RIGHT, world, player, attr);		
		}

	}
	
	public static class FreshFire extends AbilityProjectile
	{
		public FreshFire(World world)
		{super(FRESH_FIRE, world);}
		
		public FreshFire(EntityType type, World world)
		{super(type, world);}
		
		public FreshFire(World world, double x, double y, double z)
		{super(FRESH_FIRE, world, x, y, z);}
		
		public FreshFire(World world, LivingEntity player, AbilityAttribute attr) 
		{		
			super(FRESH_FIRE, world, player, attr);		
		}
		
		@Override
		public void tasksImapct(RayTraceResult hit)
		{
			if(hit.hitInfo != null && hit.getType() == Type.ENTITY && hit.hitInfo instanceof LivingEntity)
				((LivingEntity) hit.hitInfo).setFire(this.ticks);
		}
		
		@Override
		public void tick()
		{
			if(this.world.isRemote)
			{
				CustomParticleData data = new CustomParticleData();
				data.setTexture(ModValuesParticles.PARTICLE_ICON_MERA);
				data.setPosX(posX);
				data.setPosY(posY + 0.5);
				data.setPosZ(posZ);
				
				data.setMaxAge(10);
				data.setScale(1.3F);
				
				ModMain.proxy.spawnParticles(world, data);
			}
			super.tick();
		}
	}
	
	public static class RadicalBeam extends AbilityProjectile
	{
		public RadicalBeam(World world)
		{super(RADICAL_BEAM, world);}
		
		public RadicalBeam(EntityType type, World world)
		{super(type, world);}
		
		public RadicalBeam(World world, double x, double y, double z)
		{super(RADICAL_BEAM, world, x, y, z);}
		
		public RadicalBeam(World world, LivingEntity player, AbilityAttribute attr) 
		{		
			super(RADICAL_BEAM, world, player, attr);		
		}

	}
}
