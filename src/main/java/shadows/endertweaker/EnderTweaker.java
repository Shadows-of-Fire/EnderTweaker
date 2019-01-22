package shadows.endertweaker;

import java.util.ArrayList;
import java.util.List;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crazypants.enderio.base.recipe.IRecipeInput;
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

	public static RecipeInput toInput(IIngredient ing) {
		return new RecipeInput(CraftTweakerMC.getIngredient(ing));
	}

}
