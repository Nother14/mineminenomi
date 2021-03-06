package xyz.pixelatedw.mineminenomi.particles.effects.yomi;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.api.math.WyMathHelper;
import xyz.pixelatedw.mineminenomi.particles.CustomParticle;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.values.ModValuesParticles;

public class ParticleEffectSoulParade extends ParticleEffect
{

	@Override
	public void spawn(PlayerEntity player, double posX, double posY, double posZ)
	{	
		for (int i = 0; i < 64; i++)
		{
	        double motionX = WyMathHelper.randomWithRange(-3, 3) + player.world.rand.nextDouble();
	        double motionY = WyMathHelper.randomWithRange(-3, 3) + player.world.rand.nextDouble();
	        double motionZ = WyMathHelper.randomWithRange(-3, 3) + player.world.rand.nextDouble();
	        
            double middlePoint = 0.1;
            middlePoint *= player.world.rand.nextFloat() * player.world.rand.nextFloat() + 0.3F;
	        
	        motionX *= middlePoint / 2;
	        motionY *= middlePoint / 2;
	        motionZ *= middlePoint / 2;
			
	        CustomParticle cp = new CustomParticle(player.world, ModValuesParticles.PARTICLE_ICON_HIE,
					posX,
					posY + 1,
					posZ,
					motionX,
					motionY,
					motionZ)
			.setParticleScale(1).setParticleAge(10);	
			Minecraft.getInstance().particles.addEffect(cp);
			
		}
	}
}