package shadows.endertweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crazypants.enderio.base.recipe.IRecipe;
import crazypants.enderio.base.recipe.IRecipeInput;
import crazypants.enderio.base.recipe.Recipe;
import crazypants.enderio.base.recipe.RecipeBonusType;
import crazypants.enderio.base.recipe.RecipeInput;
import crazypants.enderio.base.recipe.RecipeOutput;
import crazypants.enderio.base.recipe.vat.VatRecipeManager;
import shadows.endertweaker.recipe.VatRecipeInput;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.enderio.Vat")
@ZenRegister
public class Vat {

	@ZenMethod
	public static void addRecipe(ILiquidStack output, ILiquidStack input, IIngredient[] slot1Solids, float[] slot1Mults, IIngredient[] slot2Solids, float[] slot2Mults, @Optional int energyCost) {
		if (hasErrors(output, input, slot1Solids, slot1Mults, slot2Solids, slot2Mults)) return;
		EnderTweaker.ADDITIONS.add(() -> {
			RecipeOutput out = new RecipeOutput(CraftTweakerMC.getLiquidStack(output));
			Recipe rec = new Recipe(out, energyCost <= 0 ? 5000 : energyCost, RecipeBonusType.NONE, getVatInputs(input, slot1Solids, slot1Mults, slot2Solids, slot2Mults));
			VatRecipeManager.getInstance().addRecipe(rec);
		});
	}

	@ZenMethod
	public static void removeRecipe(ILiquidStack output) {
		if (output == null) {
			CraftTweakerAPI.logError("Cannot remove recipe for null from vat.");
			return;
		}
		EnderTweaker.REMOVALS.add(() -> {
			IRecipe rec = null;
			for (IRecipe r : VatRecipeManager.getInstance().getRecipes()) {
				if (r.getOutputs()[0].getFluidOutput().getFluid().getName().equals(output.getName())) {
					rec = r;
					break;
				}
			}
			if (rec != null) {
				VatRecipeManager.getInstance().getRecipes().remove(rec);
			} else CraftTweakerAPI.logError("No Vat recipe found for " + output.getName());
		});
	}

	public static boolean hasErrors(ILiquidStack output, ILiquidStack input, IIngredient[] slot1Solids, float[] slot1Mults, IIngredient[] slot2Solids, float[] slot2Mults) {
		if (output == null) {
			CraftTweakerAPI.logError("Invalid null output in Vat recipe!");
			return true;
		}
		if (input == null) {
			CraftTweakerAPI.logError("Invalid null fluid input in Vat recipe!");
			return true;
		}
		if (slot1Solids.length != slot1Mults.length) {
			CraftTweakerAPI.logError("Invalid slot 1 configuration in vat recipe; slot1Solids must have equal length of slot1Mults! Provided: " + EnderTweaker.getDisplayString(slot1Solids) + " | " + slot1Mults);
			return true;
		}
		if (slot2Solids.length != slot2Mults.length) {
			CraftTweakerAPI.logError("Invalid slot 2 configuration in vat recipe; slot1Solids must have equal length of slot2Mults! Provided: " + EnderTweaker.getDisplayString(slot2Solids) + " | " + slot2Mults);
			return true;
		}
		return false;
	}

	public static IRecipeInput[] getVatInputs(ILiquidStack input, IIngredient[] slot1, float[] slot1Mult, IIngredient[] slot2, float[] slot2Mult) {
		IRecipeInput[] ret = new IRecipeInput[1 + slot1.length + slot2.length];
		int x = 0;
		ret[x++] = new RecipeInput(CraftTweakerMC.getLiquidStack(input));
		for (int i = 0; i < slot1.length; i++) {
			ret[x++] = new VatRecipeInput(CraftTweakerMC.getIngredient(slot1[i]), 0, slot1Mult[i]);
		}
		for (int i = 0; i < slot2.length; i++) {
			ret[x++] = new VatRecipeInput(CraftTweakerMC.getIngredient(slot2[i]), 1, slot2Mult[i]);
		}
		return ret;
	}

}
