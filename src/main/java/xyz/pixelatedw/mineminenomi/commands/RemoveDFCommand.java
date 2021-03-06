package xyz.pixelatedw.mineminenomi.commands;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import xyz.pixelatedw.mineminenomi.api.data.abilitydata.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.api.data.abilitydata.IAbilityData;
import xyz.pixelatedw.mineminenomi.api.network.packets.server.SAbilityDataSyncPacket;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SDevilFruitSyncPacket;

public class RemoveDFCommand
{

	public static void register(CommandDispatcher<CommandSource> dispatcher)
	{
		LiteralArgumentBuilder<CommandSource> builder = Commands.literal("removedf").requires(source -> source.hasPermissionLevel(2));
		
		builder
			.then(Commands.argument("targets", EntityArgument.players())
					.executes(context -> { return removesDF(context, EntityArgument.getPlayers(context, "targets")); }));
		
		dispatcher.register(builder);
	}
	
	private static int removesDF(CommandContext<CommandSource> context, Collection<ServerPlayerEntity> targets) throws CommandSyntaxException 
	{
		for (ServerPlayerEntity player : targets)
		{
			IDevilFruit devilFruitProps = DevilFruitCapability.get(player);
			IAbilityData abilityDataProps = AbilityDataCapability.get(player);
			
			devilFruitProps.setDevilFruit("");
			devilFruitProps.setLogia(false);
			devilFruitProps.setZoanPoint("");
			devilFruitProps.setYamiPower(false);
			
			abilityDataProps.clearDevilFruitAbilities();
			abilityDataProps.clearHotbar(player);

			player.clearActivePotions();
			
			ModNetwork.sendTo(new SDevilFruitSyncPacket(player.getEntityId(), devilFruitProps), player);
			ModNetwork.sendTo(new SAbilityDataSyncPacket(player.getEntityId(), abilityDataProps), player);
		}

		return 1;		
	}
}
