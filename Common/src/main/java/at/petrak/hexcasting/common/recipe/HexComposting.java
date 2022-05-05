package at.petrak.hexcasting.common.recipe;

import at.petrak.hexcasting.common.lib.HexBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

public final class HexComposting {

    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent evt) {
        compostBlock(HexBlocks.AKASHIC_LEAVES1, 0.3F);
        compostBlock(HexBlocks.AKASHIC_LEAVES2, 0.3F);
        compostBlock(HexBlocks.AKASHIC_LEAVES3, 0.3F);
    }

    private static <T extends Item> void compostItem(RegistryObject<T> itemHolder, float chance) {
        T item = itemHolder.get();

		if (item != Items.AIR) {
			ComposterBlock.COMPOSTABLES.put(item, chance);
		}
    }

    private static <T extends Block> void compostBlock(RegistryObject<T> blockHolder, float chance) {
        T block = blockHolder.get();
        Item item = block.asItem();

		if (item != Items.AIR) {
			ComposterBlock.COMPOSTABLES.put(item, chance);
		}
    }
}
