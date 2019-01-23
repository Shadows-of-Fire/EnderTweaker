package shadows.endertweaker.recipe;

import com.enderio.core.common.util.NNList;

import crazypants.enderio.base.recipe.IRecipeInput;
import crazypants.enderio.base.recipe.MachineRecipeInput;
import crazypants.enderio.base.recipe.Recipe;
import crazypants.enderio.base.recipe.RecipeBonusType;
import crazypants.enderio.base.recipe.RecipeOutput;

public class SagRecipe extends Recipe {

	public SagRecipe(IRecipeInput input, int energyRequired, RecipeBonusType bonusType, RecipeOutput... output) {
		super(input, energyRequired, bonusType, output);
	}

	@Override
	public boolean isInputForRecipe(NNList<MachineRecipeInput> machineInputs) {
		return machineInputs.size() == 1 && getInputs()[0].isInput(machineInputs.get(0).item);
	}
}
