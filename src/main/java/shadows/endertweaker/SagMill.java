package shadows.endertweaker;

import java.util.Arrays;

import com.google.common.base.Strings;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crazypants.enderio.base.recipe.IRecipe;
import crazypants.enderio.base.recipe.RecipeBonusType;
import crazypants.enderio.base.recipe.RecipeLevel;
import crazypants.enderio.base.recipe.sagmill.SagMillRecipeManager;
import net.minecraft.item.ItemStack;
import shadows.endertweaker.recipe.RecipeInput;
import shadows.endertweaker.recipe.SagRecipe;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.enderio.SagMill")
@ZenRegister
public class SagMill {

	@ZenMethod
	public static void addRecipe(IItemStack[] output, float[] chances, IIngredient input, @Optional String bonusType, @Optional int energyCost, @Optional float[] xp) {
		if (xp == null) {
			xp = new float[output.length];
			Arrays.fill(xp, 0);
		}
		final float[] xpa = xp;
		if (hasErrors(output, chances, input, xpa, bonusType)) return;
		EnderTweaker.ADDITIONS.add(() -> {
			SagRecipe recipe = new SagRecipe(new RecipeInput(CraftTweakerMC.getIngredient(input)), energyCost <= 0 ? 5000 : energyCost, RecipeBonusType.valueOf(Strings.isNullOrEmpty(bonusType) ? "NONE" : bonusType), EnderTweaker.toEIOOutputs(output, chances, xpa));
			SagMillRecipeManager.getInstance().addRecipe(recipe);
		});
	}

	@ZenMethod
	public static void removeRecipe(IItemStack input) {
		EnderTweaker.REMOVALS.add(() -> {
			ItemStack stack = CraftTweakerMC.getItemStack(input);
			IRecipe rec = SagMillRecipeManager.getInstance().getRecipeForInput(RecipeLevel.IGNORE, stack);
			if (rec != null) {
				SagMillRecipeManager.getInstance().getRecipes().remove(rec);
			} else CraftTweakerAPI.logError("No Sag Mill recipe found for " + stack.getDisplayName());
		});
	}

	public static boolean hasErrors(IItemStack[] output, float[] chances, IIngredient input, float[] xp, String type) {
		if (output == null || output.length == 0) {
			CraftTweakerAPI.logError("Invalid output (empty or null) in Sag Mill recipe: " + output);
			return true;
		}
		if (output.length > 4) {
			CraftTweakerAPI.logError("Invalid output (more than four entries) in Sag Mill recipe: " + EnderTweaker.getDisplayString(output));
			return true;
		}
		if (output.length != chances.length) {
			CraftTweakerAPI.logError("Invalid output chances (chances do not match outputs) in Sag Mill recipe: " + EnderTweaker.getDisplayString(output) + " | " + chances);
			return true;
		}
		if (output.length != xp.length) {
			CraftTweakerAPI.logError("Invalid output xp (xp does not match outputs) in Sag Mill recipe: " + EnderTweaker.getDisplayString(output) + " | " + xp);
			return true;
		}
		if (input == null) {
			CraftTweakerAPI.logError("Invalid null Sag Mill input.");
			return true;
		}
		if (RecipeBonusType.valueOf(Strings.isNullOrEmpty(type) ? "NONE" : type) == null) {
			CraftTweakerAPI.logError("Invalid bonus type in Sag Mill recipe: " + type + ". Valid values are NONE, MULTIPLY_OUTPUT, and CHANCE_ONLY.");
			return true;
		}
		return false;
	}

}
