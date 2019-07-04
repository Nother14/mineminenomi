package xyz.pixelatedw.MineMineNoMi3.entities.mobs.animals;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.api.EnumParticleTypes;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.INBTEntity;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketEntityNBTSync;

public class EntityKungFuDugong extends EntityMob implements INBTEntity
{
	private Item[] food = new Item[]
		{
				Items.cooked_beef, Items.cooked_chicken, Items.cooked_fished, Items.cooked_porkchop
		};
	private boolean isHappy, isTamed, isWaiting, isEnraged;
	private EntityPlayer owner;
	private UUID ownerUUID;

	public EntityKungFuDugong(World world)
	{
		super(world);
		this.tasks.addTask(1, new EntityAILookIdle(this));
		this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
		if (!this.worldObj.isRemote)
		{
			boolean flagOwner = this.getOwner() != null && this.getDistanceToEntity(this.getOwner()) < 10;
			boolean flagTamed = this.isTamed();
			boolean flagWaiting = this.getAttackTarget() == null && this.isWaiting();
			boolean flagHealth = this.getHealth() > this.getMaxHealth() / 3;
			
			if ( flagOwner && flagTamed && flagWaiting && flagHealth )
				this.isHappy = true;
			else
				this.isHappy = false;

			if (this.ticksExisted % 100 == 0)
				this.updateNBT();
			
			if(this.isWaiting())
				this.getNavigator().clearPathEntity();
		}
	}

	@Override
	public boolean isAIEnabled()
	{
		return true;
	}

	public void updateNBT()
	{
		NBTTagCompound nbtClone = new NBTTagCompound();
		this.writeEntityToNBT(nbtClone);

		WyNetworkHelper.sendToAll(new PacketEntityNBTSync(this.getEntityId(), nbtClone));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);

		nbt.setBoolean("IsTamed", this.isTamed);
		nbt.setBoolean("IsWaiting", this.isWaiting);
		nbt.setBoolean("IsHappy", this.isHappy);
		nbt.setBoolean("IsEnraged", this.isEnraged);
		nbt.setString("OwnerUUID", this.ownerUUID != null ? this.ownerUUID.toString() : "");
	}

	@Override
	public void readEntityFromExtraNBT(NBTTagCompound nbt)
	{
		this.readEntityFromNBT(nbt);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);

		this.isTamed = nbt.getBoolean("IsTamed");
		this.isWaiting = nbt.getBoolean("IsWaiting");
		this.isHappy = nbt.getBoolean("IsHappy");
		this.isEnraged = nbt.getBoolean("IsEnraged");
		String uuid = nbt.getString("OwnerUUID");

		if (!WyHelper.isNullOrEmpty(uuid))
		{
			this.ownerUUID = UUID.fromString(uuid);
			this.owner = this.worldObj.func_152378_a(this.ownerUUID);
			this.isTamed = true;
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float amount)
	{
		super.attackEntityFrom(damageSource, amount);

		Entity entity = damageSource.getEntity();

		if(this.isTamed())
			return true;
		
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			IAttributeInstance attackDamage = this.getEntityAttribute(SharedMonsterAttributes.attackDamage);
			UUID rageModeUUID = UUID.fromString("d760dc58-8275-4ef6-ae34-d197a879c099");
			AttributeModifier attributeModifier = new AttributeModifier(rageModeUUID, "Rage Mode", 8, 0);

			if (player.getHeldItem() != null)
			{
				if (attackDamage.getModifier(rageModeUUID) == null)
				{
					attackDamage.applyModifier(attributeModifier);
					this.isEnraged = true;
					this.updateNBT();
				}
				for (int i = 0; i < 5; ++i)
				{
					double d0 = this.rand.nextGaussian() * 0.02D;
					double d1 = this.rand.nextGaussian() * 0.02D;
					double d2 = this.rand.nextGaussian() * 0.02D;
					this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY.getParticleName(), this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2);
				}
			}
			else
			{
				if (!this.isEnraged() && this.getHealth() < this.getMaxHealth() / 3)
				{
					this.setOwner(player);
					this.updateNBT();
					for (int i = 0; i < 5; ++i)
					{
						double d0 = this.rand.nextGaussian() * 0.02D;
						double d1 = this.rand.nextGaussian() * 0.02D;
						double d2 = this.rand.nextGaussian() * 0.02D;
						this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY.getParticleName(), this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 1.0D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2);
					}
				}
			}
		}

		return true;
	}

	@Override
	public boolean interact(EntityPlayer player)
	{
		ItemStack heldStack = player.getHeldItem();

		if (this.isTamed() && player == this.owner)
		{
			if (heldStack != null && this.getHealth() < this.getMaxHealth())
			{
				Optional<Item> foodItem = Arrays.stream(this.food).filter(x -> heldStack.getItem() == x).findFirst();

				if (foodItem != null)
				{
					--heldStack.stackSize;
					this.heal(4);
					return true;
				}
			}

			this.isWaiting = !this.isWaiting;
			this.updateNBT();
			return true;
		}

		return false;
	}

	public boolean isHappy()
	{
		return this.isHappy;
	}

	public boolean isTamed()
	{
		return this.isTamed;
	}

	public boolean isWaiting()
	{
		return this.isWaiting;
	}

	public boolean isEnraged()
	{
		return this.isEnraged;
	}

	private void setOwner(EntityPlayer player)
	{
		this.owner = player;
		this.ownerUUID = player.getPersistentID();
		this.isTamed = true;
	}

	public EntityPlayer getOwner()
	{
		return this.owner;
	}
	
	@Override
	protected boolean canDespawn()
	{
		if(this.isTamed())
			return false;
		else
			return true;
	}
}
