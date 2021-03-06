package xyz.pixelatedw.mineminenomi.entities.abilityprojectiles;

import java.util.HashMap;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.abilities.effects.DFEffectMeroPetrification;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttribute;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityProjectile.Data;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.particles.CustomParticleData;
import xyz.pixelatedw.mineminenomi.values.ModValuesParticles;

public class MeroProjectiles
{
	public static HashMap<AbilityAttribute, AbilityProjectile.Data> projectiles = new HashMap<AbilityAttribute, AbilityProjectile.Data>();
	
	public static final EntityType MERO_MERO_MELLOW = WyRegistry.registerEntityType("mero_mero_mellow", MeroMeroMellow::new);
	public static final EntityType PISTOL_KISS = WyRegistry.registerEntityType("pistol_kiss", PistolKiss::new);
	public static final EntityType SLAVE_ARROW = WyRegistry.registerEntityType("slave_arrow", SlaveArrow::new);

	static
	{
		projectiles.put(ModAttributes.MERO_MERO_MELLOW, new Data(MERO_MERO_MELLOW, MeroMeroMellow.class));
		projectiles.put(ModAttributes.PISTOL_KISS, new Data(PISTOL_KISS, PistolKiss.class));
		projectiles.put(ModAttributes.SLAVE_ARROW, new Data(SLAVE_ARROW, SlaveArrow.class));
	}
	
	public static class SlaveArrow extends AbilityProjectile
	{
		public SlaveArrow(World world)
		{super(SLAVE_ARROW, world);}
		
		public SlaveArrow(EntityType type, World world)
		{super(type, world);}
		
		public SlaveArrow(World world, double x, double y, double z)
		{super(SLAVE_ARROW, world, x, y, z);}
		
		public SlaveArrow(World world, LivingEntity player, AbilityAttribute attr) 
		{		
			super(SLAVE_ARROW, world, player, attr);		
		}
		
		@Override
		public void tasksImapct(RayTraceResult hit)
		{
			if(hit.getType() == Type.ENTITY)
			{
				EntityRayTraceResult entityHit = (EntityRayTraceResult) hit;
				LivingEntity entity = (LivingEntity) entityHit.getEntity();
				
				new DFEffectMeroPetrification(entity, 400);
			}
		}
		
		@Override
		public void tick()
		{		
			if(this.world.isRemote && this.ticksExisted > 5)
			{		
				for(int i = 0; i < 2; i++)
				{
					CustomParticleData data = new CustomParticleData();
					data.setTexture(ModValuesParticles.PARTICLE_ICON_MERO);
					data.setPosX(posX);
					data.setPosY(posY);
					data.setPosZ(posZ);
					
					data.setMaxAge(1);
					data.setScale(1.3F);
					
					ModMain.proxy.spawnParticles(world, data);
				}
			}
			super.tick();
		}
	}	
	
	public static class PistolKiss extends AbilityProjectile
	{
		public PistolKiss(World world)
		{super(PISTOL_KISS, world);}
		
		public PistolKiss(EntityType type, World world)
		{super(type, world);}
		
		public PistolKiss(World world, double x, double y, double z)
		{super(PISTOL_KISS, world, x, y, z);}
		
		public PistolKiss(World world, LivingEntity player, AbilityAttribute attr) 
		{		
			super(PISTOL_KISS, world, player, attr);		
		}
		
		@Override
		public void tasksImapct(RayTraceResult hit)
		{
			if(hit.getType() == Type.ENTITY)
			{
				EntityRayTraceResult entityHit = (EntityRayTraceResult) hit;
				LivingEntity entity = (LivingEntity) entityHit.getEntity();
				
				new DFEffectMeroPetrification(entity, 200);
			}
		}
	}	
	
	public static class MeroMeroMellow extends AbilityProjectile
	{
		public MeroMeroMellow(World world)
		{super(MERO_MERO_MELLOW, world);}
		
		public MeroMeroMellow(EntityType type, World world)
		{super(type, world);}
		
		public MeroMeroMellow(World world, double x, double y, double z)
		{super(MERO_MERO_MELLOW, world, x, y, z);}
		
		public MeroMeroMellow(World world, LivingEntity player, AbilityAttribute attr) 
		{		
			super(MERO_MERO_MELLOW, world, player, attr);		
		}
		
		@Override
		public void tasksImapct(RayTraceResult hit)
		{
			if(hit.getType() == Type.ENTITY)
			{
				EntityRayTraceResult entityHit = (EntityRayTraceResult) hit;
				LivingEntity entity = (LivingEntity) entityHit.getEntity();
				
				new DFEffectMeroPetrification(entity, 600);
			}
		}
	}	
}
