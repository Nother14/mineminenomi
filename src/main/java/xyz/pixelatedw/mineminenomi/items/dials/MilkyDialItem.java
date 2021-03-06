package xyz.pixelatedw.mineminenomi.items.dials;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.api.telemetry.WyTelemetry;
import xyz.pixelatedw.mineminenomi.entities.abilityprojectiles.ExtraProjectiles.MilkyDialProjectile;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModCreativeTabs;
import xyz.pixelatedw.mineminenomi.init.ModExtraAttributes;

public class MilkyDialItem extends BlockItem
{

	public MilkyDialItem()
	{
		super(ModBlocks.milkyDialBlock, new Properties().group(ModCreativeTabs.MISC).maxStackSize(16));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	{
		if (!world.isRemote)
		{
    		MilkyDialProjectile proj = new MilkyDialProjectile(player.world, player, ModExtraAttributes.DIAL_MILKY);
			
    		world.addEntity(proj);
    		
			if (!player.isCreative())
			{
				WyTelemetry.addMiscStat("milkytDialsUsed", "Milky Dials Used", 1);
				player.getHeldItem(hand).shrink(1);
				player.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			}
		}
		
		return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}
	
	@Override
	protected boolean onBlockPlaced(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack itemStack, BlockState state)
	{
    	if(!player.isCreative())
    		WyTelemetry.addMiscStat("milkyDialsPlaced", "Milky Dials Placed", 1);
		return super.onBlockPlaced(pos, world, player, itemStack, state);
	}
}
