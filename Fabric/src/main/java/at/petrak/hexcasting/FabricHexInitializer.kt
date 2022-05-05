import at.petrak.hexcasting.common.lib.HexBlockEntities
import at.petrak.hexcasting.common.lib.HexBlocks
import at.petrak.hexcasting.common.lib.HexItems
import at.petrak.hexcasting.common.lib.HexSounds
import at.petrak.hexcasting.common.misc.Brainsweeping
import at.petrak.hexcasting.fabric.FabricHexConfig
import at.petrak.hexcasting.fabric.event.VillagerConversionCallback
import at.petrak.hexcasting.fabric.network.FabricPacketHandler
import at.petrak.hexcasting.xplat.IXplatAbstractions
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.UseEntityCallback
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import java.util.function.BiConsumer

object FabricHexInitializer : ModInitializer {
    override fun onInitialize() {
        IXplatAbstractions.INSTANCE.init()
        FabricPacketHandler.init()
        FabricHexConfig.setup()

        initListeners()

        initRegistries()
    }

    fun initListeners() {
        UseEntityCallback.EVENT.register(Brainsweeping::tradeWithVillager)
        VillagerConversionCallback.EVENT.register(Brainsweeping::copyBrainsweepFromVillager)
    }

    fun initRegistries() {
        HexSounds.registerSounds(bind(Registry.SOUND_EVENT))
        HexBlocks.registerBlocks(bind(Registry.BLOCK))
        HexBlocks.registerBlockItems(bind(Registry.ITEM))
        HexItems.registerItems(bind(Registry.ITEM))
        HexBlockEntities.registerTiles(bind(Registry.BLOCK_ENTITY_TYPE))
    }

    private fun <T> bind(registry: Registry<in T>): BiConsumer<T, ResourceLocation> =
        BiConsumer<T, ResourceLocation> { t, id -> Registry.register(registry, id, t) }
}