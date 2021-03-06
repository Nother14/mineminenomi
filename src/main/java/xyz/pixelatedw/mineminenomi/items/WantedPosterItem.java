package xyz.pixelatedw.mineminenomi.items;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.blocks.tileentities.WantedPosterTileEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class WantedPosterItem extends WallOrFloorItem
{

	public WantedPosterItem()
	{
		super(ModBlocks.wantedPoster, ModBlocks.wantedPoster, new Properties().maxStackSize(1));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	{
		if (player.getHeldItem(hand).hasTag())
		{
			if (world.isRemote)
				ModMain.proxy.openWantedPosterScreen(player, player.getHeldItem(hand).getTag());
		}
		return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	protected boolean onBlockPlaced(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack itemStack, BlockState state)
	{
		if (player != null)
		{
			WantedPosterTileEntity tileEntity = (WantedPosterTileEntity) world.getTileEntity(pos);
			
			if (tileEntity != null && itemStack.hasTag())
			{
				tileEntity.setEntityName(itemStack.getTag().getString("Name"));
				tileEntity.setUUID(itemStack.getTag().getString("UUID"));
				tileEntity.setPosterBounty(itemStack.getTag().getLong("Bounty") + "");
				tileEntity.setBackground(itemStack.getTag().getString("Background"));
				tileEntity.setIssuedDate(itemStack.getTag().getString("Date"));
				tileEntity.markDirty();
			}
		}
		boolean flag = super.onBlockPlaced(pos, world, player, itemStack, state);
		return flag;
	}
}
