package xyz.pixelatedw.mineminenomi.models.entities.projectiles;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

/**
 * Negative Hollow Attack.tcn - TechneToTabulaImporter
 * Created using Tabula 7.0.0
 */
public class NegativeHollowModel extends EntityModel {
    public RendererModel head;
    public RendererModel body;
    public RendererModel rightarm1;
    public RendererModel rightarm2;
    public RendererModel leftarm1;
    public RendererModel leftarm2;
    public RendererModel rightleg;
    public RendererModel leftleg;

    public NegativeHollowModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.body = new RendererModel(this, 35, 0);
        this.body.setRotationPoint(0.0F, 5.0F, -6.0F);
        this.body.addBox(0.0F, 0.0F, 0.0F, 3, 8, 2, 0.0F);
        this.setRotateAngle(body, 1.5707963705062866F, -0.0F, 0.0F);
        this.leftleg = new RendererModel(this, 46, 0);
        this.leftleg.setRotationPoint(2.0F, 4.5F, 2.0F);
        this.leftleg.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(leftleg, 1.5707963705062866F, -0.0F, 0.0F);
        this.rightarm1 = new RendererModel(this, 28, 0);
        this.rightarm1.setRotationPoint(-3.0F, 5.0F, -9.0F);
        this.rightarm1.addBox(0.0F, 0.0F, 0.0F, 1, 6, 2, 0.0F);
        this.setRotateAngle(rightarm1, 1.5707963705062866F, 0.5585053563117981F, 0.0F);
        this.leftarm1 = new RendererModel(this, 28, 0);
        this.leftarm1.setRotationPoint(5.0F, 5.0F, -9.5F);
        this.leftarm1.addBox(0.0F, 0.0F, 0.0F, 1, 6, 2, 0.0F);
        this.setRotateAngle(leftarm1, 1.5707963705062866F, -0.5585053563117981F, 0.0F);
        this.leftarm2 = new RendererModel(this, 17, 0);
        this.leftarm2.setRotationPoint(4.0F, 5.5F, -14.5F);
        this.leftarm2.addBox(0.0F, 0.0F, 0.0F, 2, 6, 3, 0.0F);
        this.setRotateAngle(leftarm2, 1.5707963705062866F, -0.0F, 0.0F);
        this.rightarm2 = new RendererModel(this, 17, 0);
        this.rightarm2.setRotationPoint(-3.0F, 5.5F, -14.5F);
        this.rightarm2.addBox(0.0F, 0.0F, 0.0F, 2, 6, 3, 0.0F);
        this.setRotateAngle(rightarm2, 1.5707963705062866F, -0.0F, 0.0F);
        this.rightleg = new RendererModel(this, 46, 0);
        this.rightleg.setRotationPoint(0.0F, 4.5F, 2.0F);
        this.rightleg.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(rightleg, 1.5707963705062866F, -0.0F, 0.0F);
        this.head = new RendererModel(this, 0, 0);
        this.head.mirror = true;
        this.head.setRotationPoint(-0.5F, 2.0F, -10.0F);
        this.head.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
        this.leftleg.render(f5);
        this.rightarm1.render(f5);
        this.leftarm1.render(f5);
        this.leftarm2.render(f5);
        this.rightarm2.render(f5);
        this.rightleg.render(f5);
        this.head.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(RendererModel RendererModel, float x, float y, float z) {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }
}
