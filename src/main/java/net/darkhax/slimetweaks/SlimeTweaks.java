package net.darkhax.slimetweaks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.bookshelf.util.MathsUtils;
import net.darkhax.bookshelf.util.WorldUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("slimetweaks")
public class SlimeTweaks {
    
    private final Logger log = LogManager.getLogger("Slime Tweaks");
    private final RegistryHelper registry = new RegistryHelper("slimetweaks", log, null);
    
    public SlimeTweaks() {
        
    	registry.injectTable(LootTables.GAMEPLAY_FISHING_FISH);
    	registry.initialize(FMLJavaModLoadingContext.get().getModEventBus());
    	
    	MinecraftForge.EVENT_BUS.addListener(this::onLivingDrops);
    }
    
    // Using the event because Global Loot Modifiers cause dupe bugs on mobs.
    private void onLivingDrops(LivingDropsEvent event) {
    	
    	final LivingEntity entity = event.getEntityLiving();
    	
    	if (entity.world instanceof ServerWorld && MathsUtils.tryPercentage(0.1) && entity instanceof IMob && WorldUtils.isSlimeChunk((ServerWorld) entity.world, entity.getPosition())) {
    		
    		event.getDrops().add(new ItemEntity(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Items.SLIME_BALL)));
    	}
    }
}