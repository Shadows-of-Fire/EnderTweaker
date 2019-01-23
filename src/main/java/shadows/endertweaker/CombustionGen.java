package shadows.endertweaker;

import java.util.Map;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crazypants.enderio.base.fluid.FluidFuelRegister;
import crazypants.enderio.base.fluid.IFluidCoolant;
import crazypants.enderio.base.fluid.IFluidFuel;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.enderio.CombustionGen")
@ZenRegister
public class CombustionGen {

	private static final Map<String, IFluidCoolant> coolants = ObfuscationReflectionHelper.getPrivateValue(FluidFuelRegister.class, FluidFuelRegister.instance, "coolants");
	private static final Map<String, IFluidFuel> fuels = ObfuscationReflectionHelper.getPrivateValue(FluidFuelRegister.class, FluidFuelRegister.instance, "fuels");

	@ZenMethod
	public static void addFuel(ILiquidStack fuel, int powerPerCycleRF, int totalBurnTime) {
		EnderTweaker.ADDITIONS.add(() -> {
			FluidFuelRegister.instance.addFuel(CraftTweakerMC.getLiquidStack(fuel).getFluid(), powerPerCycleRF, totalBurnTime);
		});
	}

	@ZenMethod
	public static void addCoolant(ILiquidStack coolant, float degreesCoolingPerMB) {
		EnderTweaker.ADDITIONS.add(() -> {
			FluidFuelRegister.instance.addCoolant(CraftTweakerMC.getLiquidStack(coolant).getFluid(), degreesCoolingPerMB);
		});
	}

	@ZenMethod
	public static void removeFuel(ILiquidStack fuel) {
		if (fuel == null) {
			CraftTweakerAPI.logError("Cannot remove null fuel.");
			return;
		}
		EnderTweaker.REMOVALS.add(() -> {
			fuels.remove(fuel.getName());
		});
	}

	@ZenMethod
	public static void removeCoolant(ILiquidStack coolant) {
		if (coolant == null) {
			CraftTweakerAPI.logError("Cannot remove null coolant.");
			return;
		}
		EnderTweaker.REMOVALS.add(() -> {
			coolants.remove(coolant.getName());
		});
	}
}
