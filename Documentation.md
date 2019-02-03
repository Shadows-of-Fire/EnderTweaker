# Documentation

Individual documentation for machines is as follows:

---
### Alloy Smelter (mods.enderio.AlloySmelter)

##### Recipe Addition:
`addRecipe(IItemStack output, IIngredient[] input, @Optional int energyCost, @Optional float xp)`
Parameters:
 - IItemStack output - The result of the recipe.
 - IIngredient[] input - The ingredients.  Must be between 1 and 3 ingredients.
 - int energyCost - How much FE the recipe uses.  Defaults to 5000.
 - float xp - The xp is granted from this recipe.  May not be negative.
 
##### Recipe Removal:
`removeRecipe(IItemStack output)`
Parameters:
 - IItemStack output - The output of the recipe to remove.
---
### Combustion Generator (mods.enderio.CombustionGen)

The combustion generator doesn't support a concept of recipes.  Instead, it uses Fuels and Coolants to determine how much FE will be generated.

##### Addition: 
`addFuel(ILiquidStack fuel, int powerPerCycleRF, int totalBurnTime)`
Parameters:
 - ILiquidStack fuel - The fluid fuel to add.
 - int powerPerCycleRF - Amount of energy created per tick in a base-line machine.
 - int totalBurnTime - Total burn time of one bucket of fuel.

 `addCoolant(ILiquidStack coolant, float degreesCoolingPerMB)`
 Parameters:
 - ILiquidStack coolant - The fluid coolant to add.
 - float degreesCoolingPerMB - How much heat can one mB of the coolant absorb until it heats up by 1 K?

##### Removal: 
`removeFuel(ILiquidStack fuel)`
Parameters:
 - ILiquidStack fuel - The fluid fuel to remove.

`removeCoolant(ILiquidStack coolant)`
Parameters:
 - ILiquidStack coolant - The fluid coolant to remove.
---
### Enchanter (mods.enderio.Enchanter)

##### Recipe Addition:
`addRecipe(IEnchantmentDefinition output, IIngredient input, int amountPerLevel, double costMultiplier)`
Parameters:
 - IEnchantmentDefinition output - The output enchantment.
 - IIngredient input - The item that it used to make this enchantment.
 - int amountPerLevel - The number of input items per level of enchantment.
 - double costMultiplier - Used to modify how expensive the recipe is.

##### Recipe Removal:
`removeRecipe(IEnchantmentDefinition output)`
Parameters:
 - IEnchantmentDefinition output - The enchantment output of the recipe to remove.
---
### SAG Mill (mods.enderio.SagMill)

##### Recipe Addition:
`addRecipe(IItemStack[] output, float[] chances, IIngredient input, @Optional String bonusType, @Optional int energyCost, @Optional float[] xp)`
Parameters:
 - IItemStack[] output - The results of the recipe.
 - float[] chances - The chance that this item appears.  Must have the same length as output.
 - IIngredient input - The input.
 - String bonusType - This impacts how Grinding Balls work.  May be `NONE` (no bonus), `MULTIPLY_OUTPUT` (can increase chance > 1.0), and `CHANCE_ONLY` (caps chance at 1.0).
 - int energyCost - How much FE the recipe uses.  Defaults to 5000.
 - float xp - The xp is granted from this recipe.  May not be negative.

`addRecipe(WeightedItemStack[] output, IIngredient input, @Optional String bonusType, @Optional int energyCost, @Optional float[] xp)`
Parameters:
 - WeightedItemStack[] output - The results of the recipe.
 - IIngredient input - The input.
 - String bonusType - This impacts how Grinding Balls work.  May be `NONE` (no bonus), `MULTIPLY_OUTPUT` (can increase chance > 1.0), and `CHANCE_ONLY` (caps chance at 1.0).
 - int energyCost - How much FE the recipe uses.  Defaults to 5000.
 - float xp - The xp is granted from this recipe.  May not be negative.
 
##### Recipe Removal:
`removeRecipe(IItemStack input)`
Parameters:
 - IItemStack input - A valid input item of the recipe to remove.
---
### Slice'n'Splice (mods.enderio.SliceNSplice)

##### Recipe Addition:
`addRecipe(IItemStack output, IIngredient[] input, @Optional int energyCost, @Optional float xp)`
Parameters:
 - IItemStack output - The result of the recipe.
 - IIngredient[] input - The ingredients.  Must be between 1 and 6 ingredients.
 - int energyCost - How much FE the recipe uses.  Defaults to 5000.
 - float xp - The xp is granted from this recipe.  May not be negative.
 
##### Recipe Removal:
`removeRecipe(IItemStack output)`
Parameters:
 - IItemStack output - The output of the recipe to remove.
---
### Soul Binder (mods.enderio.SoulBinder)

##### Recipe Addition:
`addRecipe(IItemStack output, IIngredient input, String[] entities, int xp, @Optional int energyCost)`
Parameters:
 - IItemStack output - The result of the recipe.
 - IIngredient input - The main (non-soul vial) ingredient.
 - String[] entities - Allowed entities that must be present in a soul vial for this recipe to work.
 - int xp - The XP cost of this recipe, in levels.
 - int energyCost - How much FE the recipe uses.  Defaults to 5000.
 
##### Recipe Removal:
`removeRecipe(IItemStack output)`
Parameters:
 - IItemStack output - The output of the recipe to remove.
---
### The Vat (mods.enderio.Vat)

The Vat uses a system of multipliers to calculate output.  This means each pair of inputs will use a different amount of input.  The ratio of input -> output fluid is constant.
The multipliers on the inputs determine how much input fluid is used.  `slot1Mult * slot2Mult * 1000mB` of input is used per craft.
The final output amount is calculated from `inMult * slot1Mult * slot2Mult * 1000mB`.
The ratio of input to output fluid is equal to `inMult`.

##### Recipe Addition:
`addRecipe(ILiquidStack output, float inMult, ILiquidStack input, IIngredient[] slot1Solids, float[] slot1Mults, IIngredient[] slot2Solids, float[] slot2Mults, @Optional int energyCost)`
Parameters:
 - ILiquidStack output - The output fluid (right tank).  Amount is ignored.
 - float inMult - The multiplier of the input fluid.  Changes how much output is created.
 - ILiquidStack input - The input fluid (left tank).  Amount is ignored.
 - IIngredient[] slot1Solids - Items that can go in slot 1.
 - float[] slot1Mults - The multipliers for the items in slot 1.  Must be the same length as slot1Solids.  Changes how much output is created and how much input is used.
 - IIngredient[] slot2Solids - Items that can go in slot 2.
 - float[] slot2Mults - The multipliers for the items in slot 2.  Must be the same length as slot2Solids.  Changes how much output is created and how much input is used.
 - int energyCost - How much FE the recipe uses.  Defaults to 5000.

##### Recipe Removal:
`removeRecipe(ILiquidStack output)`
Parameters:
 - ILiquidStack output - The output of the recipe to remove.

### Tank (mods.enderio.Tank)

##### Recipe Addition:
`addRecipe(boolean fill, IIngredient input, ILiquidStack fluid, IItemStack output)`
Parameters:
 - boolean fill - If true, the fluid passed will be consumed when the recipe is processed.  If false, the fluid will be added to the tank.
 - IIngredient input - The input item.
 - ILiquidStack fluid - The relevant fluid.  Operation is determined by fill.
 - IItemStack output - The output of the recipe.

##### Recipe Removal:
`removeRecipe(boolean fill, ILiquidStack fluid, IItemStack output)`
Parameters:
 - boolean fill - If this recipe is a filling recipe.
 - ILiquidStack fluid - The fluid involved in this recipe.
 - IItemStack output - The output of the recipe to remove.