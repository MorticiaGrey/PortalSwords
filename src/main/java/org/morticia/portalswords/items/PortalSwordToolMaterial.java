package org.morticia.portalswords.items;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class PortalSwordToolMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return 5000;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 10.0F;
    }

    @Override
    public float getAttackDamage() {
        return 9.0F;
    }

    @Override
    public int getMiningLevel() {
        return 5;
    }

    @Override
    public int getEnchantability() {
        return 30;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }
}
