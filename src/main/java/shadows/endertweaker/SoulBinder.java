package shadows.endertweaker;

import java.util.Map;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crazypants.enderio.base.recipe.IMachineRecipe;
import crazypants.enderio.base.recipe.MachineRecipeRegistry;
import crazypants.enderio.base.recipe.soul.ISoulBinderRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import shadows.endertweaker.recipe.SoulBinderRecipe;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.enderio.SoulBinder")
@ZenRegister
public class SoulBinder {

	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient input, String[] entities, int xp, @Optional int energyCost) {
		EnderTweaker.ADDITIONS.add(() -> {
			MachineRecipeRegistry.instance.registerRecipe(MachineRecipeRegistry.SOULBINDER, new SoulBinderRecipe(CraftTweakerMC.getIngredient(input), CraftTweakerMC.getItemStack(output), xp, energyCost <= 0 ? 5000 : energyCost, toRelocs(entities)));
		});
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output) {
		if (output == null) {
			CraftTweakerAPI.logError("Cannot remove recipe for null from soul binder.");
			return;
		}
		EnderTweaker.REMOVALS.add(() -> {
			ItemStack stack = CraftTweakerMC.getItemStack(output);
			String id = null;
			for (Map.Entry<String, IMachineRecipe> ent : MachineRecipeRegistry.instance.getRecipesForMachine(MachineRecipeRegistry.SOULBINDER).entrySet()) {
				if (((ISoulBinderRecipe) ent.getValue()).getOutputStack().isItemEqual(stack)) {
					id = ent.getKey();
					break;
				}
			}
			if (id != null) {
				MachineRecipeRegistry.instance.getRecipesForMachine(MachineRecipeRegistry.SOULBINDER).remove(id);
			} else CraftTweakerAPI.logError("No Soul Binder recipe found for " + output.getDisplayName());
		});
	}

	public static ResourceLocation[] toRelocs(String[] ar) {
		ResourceLocation[] ret = new ResourceLocation[ar.length];
		for (int i = 0; i < ar.length; i++)
			ret[i] = new ResourceLocation(ar[i]);
		return ret;
	}
}
