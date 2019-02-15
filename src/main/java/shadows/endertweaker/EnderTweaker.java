package shadows.endertweaker;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = EnderTweaker.MODID, name = EnderTweaker.MODNAME, version = EnderTweaker.VERSION, dependencies = "required:enderio;required-after:crafttweaker;before:jei")
public class EnderTweaker {

	public static final String MODID = "endertweaker";
	public static final String MODNAME = "EnderTweaker";
	public static final String VERSION = "1.1.4";
	public static final List<Runnable> ADDITIONS = new ArrayList<>();
	public static final List<Runnable> REMOVALS = new ArrayList<>();

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		for (Runnable r : REMOVALS)
			r.run();
		REMOVALS.clear();
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent e) {
		for (Runnable r : ADDITIONS)
			r.run();
		ADDITIONS.clear();
	}
}
