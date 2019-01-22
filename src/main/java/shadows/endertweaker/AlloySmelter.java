package shadows.endertweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crazypants.enderio.base.recipe.BasicManyToOneRecipe;
import crazypants.enderio.base.recipe.IManyToOneRecipe;
import crazypants.enderio.base.recipe.Recipe;
import crazypants.enderio.base.recipe.RecipeBonusType;
import crazypants.enderio.base.recipe.RecipeOutput;
import crazypants.enderio.base.recipe.alloysmelter.AlloyRecipeManager;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.enderio.AlloySmelter")
@ZenRegister
public class AlloySmelter {

	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient[] input, @Optional int energyCost, @Optional float xp) {
		if (hasErrors(output, input)) return;
		EnderTweaker.ADDITIONS.add(() -> {
			Recipe r = new Recipe(new RecipeOutput(CraftTweakerMC.getItemStack(output)), energyCost, RecipeBonusType.NONE, EnderTweaker.toEIOInputs(input));
			AlloyRecipeManager.getInstance().addRecipe(new BasicManyToOneRecipe(r));
		});
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output) {
		EnderTweaker.REMOVALS.add(() -> {
			ItemStack stack = CraftTweakerMC.getItemStack(output);
			IManyToOneRecipe rec = null;
			for (IManyToOneRecipe r : AlloyRecipeManager.getInstance().getRecipes()) {
				if (r.getOutput().isItemEqual(stack)) {
					rec = r;
					break;
				}
			}
			if (rec != null) {
				AlloyRecipeManager.getInstance().getRecipes().remove(rec);
			} else CraftTweakerAPI.logError("No Alloy Smelter recipe found for " + output.getDisplayName());
		});
	}

	public static boolean hasErrors(IItemStack output, IIngredient[] input) {
		if (output == null || output.isEmpty()) {
			CraftTweakerAPI.logError("Invalid output (empty or null) in Alloy Smelter recipe: " + output);
			return true;
		}
		if (input.length > 3 || input.length < 2) {
			CraftTweakerAPI.logError("Invalid Alloy Smelter input, must be between 2-3 inputs.  Provided: " + getDisplayString(input));
			return true;
		}
		return false;
	}

	public static String getDisplayString(IIngredient[] ings) {
		StringBuilder sb = new StringBuilder("[");
		for (IIngredient i : ings)
			sb.append(i.toCommandString() + ",");
		sb.replace(sb.length() - 1, sb.length(), "");
		return sb.append("]").toString();
	}

}
