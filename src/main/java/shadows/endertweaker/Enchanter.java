package shadows.endertweaker;

import java.util.Map;

import com.enderio.core.common.util.NNList;
import com.enderio.core.common.util.stackable.Things;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crazypants.enderio.base.recipe.IMachineRecipe;
import crazypants.enderio.base.recipe.MachineRecipeRegistry;
import crazypants.enderio.base.recipe.enchanter.EnchanterRecipe;
import net.minecraft.enchantment.Enchantment;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.enderio.Enchanter")
@ZenRegister
public class Enchanter {

	@ZenMethod
	public static void addRecipe(IEnchantmentDefinition output, IIngredient input, int amountPerLevel, double costMultiplier) {
		if (output == null) {
			CraftTweakerAPI.logError("Cannot add recipe for null to enchanter.");
			return;
		}
		if (input == null) {
			CraftTweakerAPI.logError("Cannot add recipe for " + output.getTranslatedName(1) + " with null input to enchanter.");
			return;
		}
		EnderTweaker.ADDITIONS.add(() -> {
			Things thing = new Things();
			thing.add(new NNList<>(CraftTweakerMC.getIngredient(input).getMatchingStacks()));
			Enchantment enchantment = (Enchantment) output.getInternal();
			if (!thing.isEmpty() && enchantment != null) {
				EnchanterRecipe recipe = new EnchanterRecipe(thing, amountPerLevel, enchantment, costMultiplier);
				MachineRecipeRegistry.instance.registerRecipe(MachineRecipeRegistry.ENCHANTER, recipe);
			}
		});
	}

	@ZenMethod
	public static void removeRecipe(IEnchantmentDefinition output) {
		if (output == null) {
			CraftTweakerAPI.logError("Cannot remove recipe for null from enchanter.");
			return;
		}
		EnderTweaker.REMOVALS.add(() -> {
// May this be the Crash Causing Part ? enchantment instead of just ench ? Idk I'm noob :D -Speecker
			Enchantment ench = (Enchantment) output.getInternal();
			String id = null;
			for (Map.Entry<String, IMachineRecipe> ent : MachineRecipeRegistry.instance.getRecipesForMachine(MachineRecipeRegistry.ENCHANTER).entrySet()) {
				if (((EnchanterRecipe) ent.getValue()).getEnchantment() == ench) {
					id = ent.getKey();
					break;
				}
			}
			if (id != null) {
				MachineRecipeRegistry.instance.getRecipesForMachine(MachineRecipeRegistry.ENCHANTER).remove(id);
			} else CraftTweakerAPI.logError("No Enchanter recipe found for " + output.getName());
		});
	}

}
