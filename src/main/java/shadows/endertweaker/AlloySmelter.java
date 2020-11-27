package shadows.endertweaker;

import java.util.ArrayList;
import java.util.List;

import com.enderio.core.common.util.NNList;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crazypants.enderio.base.recipe.IManyToOneRecipe;
import crazypants.enderio.base.recipe.IRecipe;
import crazypants.enderio.base.recipe.MachineRecipeInput;
import crazypants.enderio.base.recipe.RecipeBonusType;
import crazypants.enderio.base.recipe.RecipeLevel;
import crazypants.enderio.base.recipe.RecipeOutput;
import crazypants.enderio.base.recipe.alloysmelter.AlloyRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import shadows.endertweaker.recipe.ManyToOneRecipe;
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
			RecipeOutput out = new RecipeOutput(CraftTweakerMC.getItemStack(output), 1, xp);
			ManyToOneRecipe rec = new ManyToOneRecipe(out, energyCost, RecipeBonusType.NONE, RecipeLevel.IGNORE, RecipeUtils.toEIOInputs(input));
			AlloyRecipeManager.getInstance().addRecipe(rec);
		});
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output) {
		if (output == null) {
			CraftTweakerAPI.logError("Cannot remove recipe for null from alloy smelter.");
			return;
		}
		EnderTweaker.REMOVALS.add(() -> {
			ItemStack stack = CraftTweakerMC.getItemStack(output);
			List<IManyToOneRecipe> removals = new ArrayList<>();
			for (IManyToOneRecipe r : AlloyRecipeManager.getInstance().getRecipes()) {
				if (OreDictionary.itemMatches(stack, r.getOutput(), false)) {
					removals.add(r);
				}
			}
			if (!removals.isEmpty()) {
				removals.forEach(AlloyRecipeManager.getInstance().getRecipes()::remove);
			} else CraftTweakerAPI.logError("No Alloy Smelter recipe found for " + output.getDisplayName());
		});
	}

	@ZenMethod
	public static void removeByInputs(IItemStack... input) {
		if (input == null || input.length > 3) {
			CraftTweakerAPI.logError("Cannot remove recipe for null from alloy smelter.");
			return;
		}
		EnderTweaker.REMOVALS.add(() -> {
			NNList<MachineRecipeInput> inputs = new NNList<>();
			for (int i = 0; i < input.length; i++) {
				inputs.add(new MachineRecipeInput(i, CraftTweakerMC.getItemStack(input[i])));
			}
			IRecipe r = AlloyRecipeManager.getInstance().getRecipeForInputs(RecipeLevel.IGNORE, inputs);

			if (r != null) {
				AlloyRecipeManager.getInstance().getRecipes().remove(r);
			} else CraftTweakerAPI.logError("No Alloy Smelter recipe found for " + RecipeUtils.getDisplayString(input));
		});
	}

	public static boolean hasErrors(IItemStack output, IIngredient[] input) {
		if (output == null || output.isEmpty()) {
			CraftTweakerAPI.logError("Invalid output (empty or null) in Alloy Smelter recipe: " + output);
			return true;
		}
		if (input.length > 3) {
			CraftTweakerAPI.logError("Invalid Alloy Smelter input, must be between 1 and 3 inputs.  Provided: " + RecipeUtils.getDisplayString(input));
			return true;
		}
		return false;
	}

}
