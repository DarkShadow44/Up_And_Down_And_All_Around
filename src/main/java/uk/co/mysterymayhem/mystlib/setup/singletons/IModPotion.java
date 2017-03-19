package uk.co.mysterymayhem.mystlib.setup.singletons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.co.mysterymayhem.gravitymod.common.util.ReflectionLambdas;

/**
 * Created by Mysteryem on 15/03/2017.
 */
public interface IModPotion<T extends Potion & IModPotion<T>> extends IModObject {
    @SuppressWarnings("unchecked")
    default T getPotion() {
        return (T)this;
    }

    default ResourceLocation getTextureResource() {
        return null;
    }

    @Override
    default void preInit() {
        T potion = this.getPotion();
        String modID = this.getModID();
        String name = this.getModObjectName();
        potion.setPotionName("potion." + modID + "." + name);
        GameRegistry.register(potion, new ResourceLocation(modID, name));
    }

    default int getTextureWidth() {
        return 256;
    }

    default int getIconWidth() {
        return 18;
    }

    default int getIconHeight() {
        return 18;
    }

    default int getNumIconsPerRow() {
        return this.getTextureWidth() / this.getIconWidth();
    }

    default T setIconIndex(int index) {
        T potion = this.getPotion();
        int numIconsPerRow = this.getNumIconsPerRow();
        ReflectionLambdas.Potion$setIconIndex.apply(potion, index % numIconsPerRow, index / numIconsPerRow);
        return potion;
    }

    @FunctionalInterface
    interface PotionIntIntToPotion {
        Potion apply(Potion potion, int xIndex, int yIndex);
    }

    @SideOnly(Side.CLIENT)
    default void render(int x, int y, float alpha) {
        ResourceLocation textureResource = this.getTextureResource();
        if (textureResource == null) {
            return;
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(textureResource);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer buf = tessellator.getBuffer();
        buf.begin(7, DefaultVertexFormats.POSITION_TEX);
        GlStateManager.color(1, 1, 1, alpha);

        T potion = this.getPotion();
        int numIconsPerRow = this.getNumIconsPerRow();
        int iconWidth = this.getIconWidth();
        int iconHeight = this.getIconHeight();

        int textureX = potion.getStatusIconIndex() % numIconsPerRow * iconWidth;
        int textureY = potion.getStatusIconIndex() / numIconsPerRow * iconHeight;

        buf.pos(x, y + iconHeight, 0).tex(textureX * 0.00390625, (textureY + iconHeight) * 0.00390625).endVertex();
        buf.pos(x + iconWidth, y + iconHeight, 0).tex((textureX + iconWidth) * 0.00390625, (textureY + iconHeight) * 0.00390625).endVertex();
        buf.pos(x + iconWidth, y, 0).tex((textureX + iconWidth) * 0.00390625, textureY * 0.00390625).endVertex();
        buf.pos(x, y, 0).tex(textureX * 0.00390625, textureY * 0.00390625).endVertex();

        tessellator.draw();
    }
}
