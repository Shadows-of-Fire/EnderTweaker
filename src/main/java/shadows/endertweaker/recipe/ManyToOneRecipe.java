package shadows.endertweaker.recipe;

import com.enderio.core.common.util.NNList;

import crazypants.enderio.base.recipe.*;

public class ManyToOneRecipe extends Recipe {

	public ManyToOneRecipe(RecipeOutput output, int energyRequired, RecipeBonusType bonusType, RecipeLevel level, IRecipeInput... input) {
		super(output, energyRequired, bonusType, level, input);
	}

	@Override
	public boolean isInputForRecipe(NNList<MachineRecipeInput> machineInputs) {
		IRecipeInput[] inputs = getInputs();
		if (inputs.length != machineInputs.size()) return false;
		boolean[] matched = new boolean[inputs.length];

		for (MachineRecipeInput input : machineInputs) {
			for (int i = 0; i < inputs.length; i++) {
				if (!matched[i] && inputs[i].isInput(input.item)) {
					matched[i] = true;
					break;
				}
			}
		}
		for (boolean b : matched)
			if (!b) return false;
		return true;
	}

}
