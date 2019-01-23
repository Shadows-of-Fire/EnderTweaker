package shadows.endertweaker.recipe;

import net.minecraft.item.crafting.Ingredient;

public class VatRecipeInput extends RecipeInput {

	protected final int slot;
	protected final float mult;

	public VatRecipeInput(Ingredient ing, int slot, float mult) {
		super(ing);
		this.slot = slot;
		this.mult = mult;
	}

	@Override
	public int getSlotNumber() {
		return slot;
	}

	@Override
	public float getMulitplier() {
		return mult;
	}

}
