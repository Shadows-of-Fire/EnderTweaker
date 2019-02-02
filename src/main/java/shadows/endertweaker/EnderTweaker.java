package shadows.endertweaker;

import java.util.ArrayList;
import java.util.List;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.WeightedItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crazypants.enderio.base.recipe.IRecipeInput;
import crazypants.enderio.base.recipe.RecipeOutput;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import shadows.endertweaker.recipe.RecipeInput;

@Mod(modid = EnderTweaker.MODID, name = EnderTweaker.MODNAME, version = EnderTweaker.VERSION, dependencies = "required-after:enderio;required-after:crafttweaker")
public class EnderTweaker {

	public static final String MODID = "endertweaker";
	public static final String MODNAME = "EnderTweaker";
	public static final String VERSION = "1.0.0";
	public static final List<Runnable> ADDITIONS = new ArrayList<>();
	public static final List<Runnable> REMOVALS = new ArrayList<>();

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		for (Runnable r : REMOVALS)
			r.run();
		for (Runnable r : ADDITIONS)
			r.run();
		ADDITIONS.clear();
		REMOVALS.clear();
	}

	public static IRecipeInput[] toEIOInputs(IIngredient[] inputs) {
		IRecipeInput[] ret = new IRecipeInput[inputs.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = toInput(inputs[i]);
		}
		return ret;
	}

	public static RecipeOutput[] toEIOOutputs(IItemStack[] inputs, float[] chances, float[] xp) {
		RecipeOutput[] ret = new RecipeOutput[inputs.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new RecipeOutput(CraftTweakerMC.getItemStack(inputs[i]), chances[i], xp[i]);
		}
		return ret;
	}
	
	public static RecipeOutput[] toEIOOutputs(WeightedItemStack[] inputs, float[] xp) {
		RecipeOutput[] ret = new RecipeOutput[inputs.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new RecipeOutput(CraftTweakerMC.getItemStack(inputs[i].getStack()), inputs[i].getChance(), xp[i]);
		}
		return ret;
	}

	public static RecipeInput toInput(IIngredient ing) {
		return new RecipeInput(CraftTweakerMC.getIngredient(ing));
	}

	public static String getDisplayString(IIngredient... ings) {
		StringBuilder sb = new StringBuilder("[");
		for (IIngredient i : ings)
			sb.append(i == null ? i : i.toCommandString() + ",");
		sb.replace(sb.length() - 1, sb.length(), "");
		return sb.append("]").toString();
	}
	
	public static String getDisplayString(WeightedItemStack... ings) {
		StringBuilder sb = new StringBuilder("[");
		for (WeightedItemStack i : ings)
			sb.append(i == null ? i : i.getStack().toCommandString() + " % " + i.getPercent() + ",");
		sb.replace(sb.length() - 1, sb.length(), "");
		return sb.append("]").toString();
	}

}
