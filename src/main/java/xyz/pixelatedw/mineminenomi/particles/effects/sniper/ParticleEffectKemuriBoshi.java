package xyz.pixelatedw.mineminenomi.particles.effects.sniper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import xyz.pixelatedw.mineminenomi.api.math.WyMathHelper;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ParticleEffectKemuriBoshi extends ParticleEffect
{

	@Override
	public void spawn(PlayerEntity player, double posX, double posY, double posZ)
	{
		for (int i = 0; i < 512; i++)
		{
			double offsetX = WyMathHelper.randomWithRange(-4, 4) + WyMathHelper.randomDouble();
			double offsetY = WyMathHelper.randomWithRange(-2, 3) + WyMathHelper.randomDouble();
			double offsetZ = WyMathHelper.randomWithRange(-4, 4) + WyMathHelper.randomDouble();
			
			player.world.addParticle(ParticleTypes.CLOUD, posX + offsetX + WyMathHelper.randomWithRange(-7, 7), (posY + 0.5) + offsetY + WyMathHelper.randomWithRange(-1, 3), posZ + offsetZ + WyMathHelper.randomWithRange(-7, 7), 0.0D, 0.1D, 0.0D);
		}	
	}

}
