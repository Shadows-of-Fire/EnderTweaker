package shadows.endertweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crazypants.enderio.base.recipe.IManyToOneRecipe;
import crazypants.enderio.base.recipe.RecipeBonusType;
import crazypants.enderio.base.recipe.RecipeOutput;
import crazypants.enderio.base.recipe.slicensplice.SliceAndSpliceRecipeManager;
import net.minecraft.item.ItemStack;
import shadows.endertweaker.recipe.ManyToOneRecipe;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.enderio.SliceNSplice")
@ZenRegister
public class SliceNSplice {

	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient[] input, @Optional int energyCost, @Optional float xp) {
		if (hasErrors(output, input)) return;
		EnderTweaker.ADDITIONS.add(() -> {
			RecipeOutput out = new RecipeOutput(CraftTweakerMC.getItemStack(output), 1, xp);
			ManyToOneRecipe rec = new ManyToOneRecipe(out, energyCost <= 0 ? 5000 : energyCost, RecipeBonusType.NONE, EnderTweaker.toEIOInputs(input));
			SliceAndSpliceRecipeManager.getInstance().addRecipe(rec);
		});
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output) {
		if (output == null) {
			CraftTweakerAPI.logError("Cannot remove recipe for null from slice'n'splice.");
			return;
		}
		EnderTweaker.REMOVALS.add(() -> {
			ItemStack stack = CraftTweakerMC.getItemStack(output);
			IManyToOneRecipe rec = null;
			for (IManyToOneRecipe r : SliceAndSpliceRecipeManager.getInstance().getRecipes()) {
				if (r.getOutput().isItemEqual(stack)) {
					rec = r;
					break;
				}
			}
			if (rec != null) {
				SliceAndSpliceRecipeManager.getInstance().getRecipes().remove(rec);
			} else CraftTweakerAPI.logError("No Slice'n'Splice recipe found for " + output.getDisplayName());
		});
	}

	public static boolean hasErrors(IItemStack output, IIngredient[] input) {
		if (output == null || output.isEmpty()) {
			CraftTweakerAPI.logError("Invalid output (empty or null) in Slice'n'Splice recipe: " + output);
			return true;
		}
		if (input.length > 6) {
			CraftTweakerAPI.logError("Invalid Slice'n'Splice input, must be between 1 and 6 inputs.  Provided: " + EnderTweaker.getDisplayString(input));
			return true;
		}
		return false;
	}

}
