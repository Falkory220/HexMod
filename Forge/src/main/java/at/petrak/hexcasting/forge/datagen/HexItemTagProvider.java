package at.petrak.hexcasting.forge.datagen;

import at.petrak.hexcasting.HexMod;
import at.petrak.hexcasting.api.mod.HexItemTags;
import at.petrak.hexcasting.common.lib.HexBlockTags;
import at.petrak.hexcasting.common.lib.HexItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class HexItemTagProvider extends ItemTagsProvider {
    public HexItemTagProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider,
        @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, pBlockTagsProvider, HexMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(Tags.Items.GEMS).add(HexItems.CHARGED_AMETHYST.get());
        tag(HexItemTags.AMETHYST_DUST).add(HexItems.AMETHYST_DUST.get());
        tag(HexItemTags.WANDS).add(HexItems.WAND_OAK.get(), HexItems.WAND_SPRUCE.get(), HexItems.WAND_BIRCH.get(),
            HexItems.WAND_JUNGLE.get(), HexItems.WAND_ACACIA.get(), HexItems.WAND_DARK_OAK.get(),
            HexItems.WAND_CRIMSON.get(), HexItems.WAND_WARPED.get(), HexItems.WAND_AKASHIC.get());
        tag(HexItemTags.PHIAL_BASE).add(Items.GLASS_BOTTLE);

        this.copy(HexBlockTags.AKASHIC_LOGS, HexItemTags.AKASHIC_LOGS);
        this.copy(HexBlockTags.AKASHIC_PLANKS, HexItemTags.AKASHIC_PLANKS);
        this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        this.copy(BlockTags.LOGS, ItemTags.LOGS);
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.SLABS, ItemTags.SLABS);
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.DOORS, ItemTags.DOORS);
        this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        this.copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        // Apparently, there's no "Pressure Plates" item tag.
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
        this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
    }
}
