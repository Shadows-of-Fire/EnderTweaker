package shadows.endertweaker.recipe;

import crazypants.enderio.base.recipe.IRecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public class RecipeInput implements IRecipeInput {

	protected final Ingredient ing;

	public RecipeInput(Ingredient ing) {
		this.ing = ing;
	}

	@Override
	public IRecipeInput copy() {
		return new RecipeInput(ing);
	}

	@Override
	public boolean isFluid() {
		return false;
	}

	@Override
	public ItemStack getInput() {
		return ing.getMatchingStacks()[0];
	}

	@Override
	public FluidStack getFluidInput() {
		return null;
	}

	@Override
	public float getMulitplier() {
		return 0;
	}

	@Override
	public int getSlotNumber() {
		return -1;
	}

	@Override
	public boolean isInput(ItemStack test) {
		return ing.apply(test);
	}

	@Override
	public boolean isInput(FluidStack test) {
		return false;
	}

	@Override
	public ItemStack[] getEquivelentInputs() {
		return ing.getMatchingStacks();
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void shrinkStack(int count) {
	}

}
