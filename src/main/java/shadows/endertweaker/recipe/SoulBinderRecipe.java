package shadows.endertweaker.recipe;

import crazypants.enderio.base.recipe.RecipeLevel;
import crazypants.enderio.base.recipe.soul.AbstractSoulBinderRecipe;
import crazypants.enderio.util.CapturedMob;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class SoulBinderRecipe extends AbstractSoulBinderRecipe {

	private static int k = 0;

	protected final Ingredient input;
	protected final ItemStack output;

	public SoulBinderRecipe(Ingredient input, ItemStack output, int energy, int xp, RecipeLevel level, ResourceLocation... entities) {
		super(energy, xp, String.format("et_%s_%s", output.getDisplayName(), k++), level, entities);
		this.input = input;
		this.output = output;
	}

	@Override
	public ItemStack getInputStack() {
		return input.getMatchingStacks()[0].copy();
	}

	@Override
	public ItemStack getOutputStack() {
		return output.copy();
	}

	@Override
	public ItemStack getOutputStack(ItemStack input, CapturedMob mobType) {
		return getOutputStack();
	}

	@Override
	protected boolean isValidInputItem(ItemStack item) {
		return input.apply(item);
	}

}
