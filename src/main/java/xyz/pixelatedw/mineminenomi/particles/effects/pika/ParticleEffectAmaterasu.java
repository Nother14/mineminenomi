package xyz.pixelatedw.mineminenomi.particles.effects.pika;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.particles.CustomParticle;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.values.ModValuesParticles;

public class ParticleEffectAmaterasu extends ParticleEffect
{

	@Override
	public void spawn(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ)
	{
		motionX = -MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * 1;
		motionZ = MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * 1;
		motionY = -MathHelper.sin((player.rotationPitch) / 180.0F * (float) Math.PI) * 1;

		CustomParticle cp = new CustomParticle(player.world, ModValuesParticles.PARTICLE_ICON_PIKA, posX, posY, posZ, 0, 0, 0).setParticleScale(50F).setParticleGravity(0).setParticleAge(10);
		Minecraft.getInstance().particles.addEffect(cp);
	}

}
