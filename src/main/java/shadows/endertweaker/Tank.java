package shadows.endertweaker;

import java.util.Map;

import com.enderio.core.common.util.NNList;
import com.enderio.core.common.util.stackable.Things;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crazypants.enderio.base.recipe.IMachineRecipe;
import crazypants.enderio.base.recipe.MachineRecipeRegistry;
import crazypants.enderio.base.recipe.RecipeLevel;
import crazypants.enderio.base.recipe.tank.TankMachineRecipe;
import crazypants.enderio.base.recipe.tank.TankMachineRecipe.Logic;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.enderio.Tank")
@ZenRegister
public class Tank {

	static int k = 0;

	@ZenMethod
	public static void addRecipe(boolean fill, IIngredient input, ILiquidStack fluid, IItemStack output) {
		EnderTweaker.ADDITIONS.add(() -> {
			Things in = new Things().add(new NNList<>(CraftTweakerMC.getIngredient(input).getMatchingStacks()));
			Things out = new Things().add(CraftTweakerMC.getItemStack(output));
			TankMachineRecipe rec = new TankMachineRecipe("tank_recipe_" + k++, fill, in, CraftTweakerMC.getLiquidStack(fluid), out, Logic.NONE, RecipeLevel.IGNORE);
			MachineRecipeRegistry.instance.registerRecipe(rec);
		});
	}

	@ZenMethod
	public static void removeRecipe(boolean fill, ILiquidStack fluid, IItemStack output) {
		if (output == null || fluid == null) {
			CraftTweakerAPI.logError("Cannot remove recipe for null from tank.");
			return;
		}
		EnderTweaker.REMOVALS.add(() -> {
			String rec = null;
			String id = fill ? MachineRecipeRegistry.TANK_FILLING : MachineRecipeRegistry.TANK_EMPTYING;
			for (Map.Entry<String, IMachineRecipe> ent : MachineRecipeRegistry.instance.getRecipesForMachine(id).entrySet()) {
				TankMachineRecipe r = (TankMachineRecipe) ent.getValue();
				if (r.getFluid().getFluid().getName().equals(fluid.getName()) && OreDictionary.itemMatches(CraftTweakerMC.getItemStack(output), r.getOutput().getMatchingStacks()[0], false)) {
					rec = ent.getKey();
					break;
				}
			}
			if (rec != null) {
				MachineRecipeRegistry.instance.getRecipesForMachine(id).remove(rec);
			} else CraftTweakerAPI.logError("No tank recipe found for " + output.getName() + " | " + fluid.getName());
		});
	}
}
