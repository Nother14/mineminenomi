package xyz.pixelatedw.mineminenomi.particles.effects.yami;

import java.util.Timer;

import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.particles.CustomParticle;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.tasks.ParticleTaskTornado;
import xyz.pixelatedw.mineminenomi.values.ModValuesParticles;

public class ParticleEffectDarkMatter extends ParticleEffect
{

	@Override
	public void spawn(PlayerEntity player, double posX, double posY, double posZ)
	{
		Timer timer = new Timer(true); 
		
		CustomParticle particle = new CustomParticle(player.world, ModValuesParticles.PARTICLE_ICON_DARKNESS,
				posX, 
				posY + 1,
				posZ, 
				0, 0, 0)
				.setParticleGravity(-1.25f + player.world.rand.nextFloat()).setParticleScale(player.world.rand.nextInt(3) + 1);
		timer.schedule(ParticleTaskTornado.Create(player, posX, posY, posZ, particle, 8.0, 2, 0.15, 0.5), 0);
	}

}
