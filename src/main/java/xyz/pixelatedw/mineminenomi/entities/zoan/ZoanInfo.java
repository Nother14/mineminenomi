package xyz.pixelatedw.mineminenomi.entities.zoan;

import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttribute;
import xyz.pixelatedw.mineminenomi.renderers.entities.ZoanMorphRenderer;

public abstract class ZoanInfo
{	
	public abstract String getDevilFruit();
	public abstract String getForm();
	
	public abstract ZoanMorphRenderer.Factory getFactory();
	
	public abstract AbilityAttribute getAttribute();
	
	public abstract double getWidth();
	public abstract double getHeight();
	
	public abstract double[] getHeldItemOffset();
	public abstract double getHeldItemRotation();
}
