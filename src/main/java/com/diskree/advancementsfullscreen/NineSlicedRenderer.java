package com.diskree.advancementsfullscreen;

import it.unimi.dsi.fastutil.ints.IntIterator;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Unique;

import java.util.NoSuchElementException;

/**
 * Backport from Minecraft 1.19.4
 */
public class NineSlicedRenderer {

    @SuppressWarnings("SameParameterValue")
    @Unique
    public static void drawNineSlicedTexture(
        DrawableHelper drawableHelper,
        MatrixStack matrices,
        int x,
        int y,
        int width,
        int height,
        int leftSliceWidth,
        int topSliceHeight,
        int rightSliceWidth,
        int bottomSliceHeight,
        int centerSliceWidth,
        int centerSliceHeight,
        int u,
        int v
    ) {
        leftSliceWidth = Math.min(leftSliceWidth, width / 2);
        rightSliceWidth = Math.min(rightSliceWidth, width / 2);
        topSliceHeight = Math.min(topSliceHeight, height / 2);
        bottomSliceHeight = Math.min(bottomSliceHeight, height / 2);
        if (width == centerSliceWidth && height == centerSliceHeight) {
            drawableHelper.drawTexture(matrices, x, y, u, v, width, height);
            return;
        }
        if (height == centerSliceHeight) {
            drawableHelper.drawTexture(matrices, x, y, u, v, leftSliceWidth, height);
            drawRepeatingTexture(
                drawableHelper,
                matrices,
                x + leftSliceWidth,
                y,
                width - rightSliceWidth - leftSliceWidth,
                height,
                u + leftSliceWidth,
                v,
                centerSliceWidth - rightSliceWidth - leftSliceWidth,
                centerSliceHeight
            );
            drawableHelper.drawTexture(
                matrices,
                x + width - rightSliceWidth,
                y,
                u + centerSliceWidth - rightSliceWidth,
                v,
                rightSliceWidth,
                height
            );
            return;
        }
        if (width == centerSliceWidth) {
            drawableHelper.drawTexture(matrices, x, y, u, v, width, topSliceHeight);
            drawRepeatingTexture(
                drawableHelper,
                matrices,
                x,
                y + topSliceHeight,
                width,
                height - bottomSliceHeight - topSliceHeight,
                u,
                v + topSliceHeight,
                centerSliceWidth,
                centerSliceHeight - bottomSliceHeight - topSliceHeight
            );
            drawableHelper.drawTexture(
                matrices,
                x,
                y + height - bottomSliceHeight,
                u,
                v + centerSliceHeight - bottomSliceHeight,
                width,
                bottomSliceHeight
            );
            return;
        }
        drawableHelper.drawTexture(matrices, x, y, u, v, leftSliceWidth, topSliceHeight);
        drawRepeatingTexture(
            drawableHelper,
            matrices,
            x + leftSliceWidth,
            y,
            width - rightSliceWidth - leftSliceWidth,
            topSliceHeight,
            u + leftSliceWidth,
            v,
            centerSliceWidth - rightSliceWidth - leftSliceWidth,
            topSliceHeight
        );
        drawableHelper.drawTexture(
            matrices,
            x + width - rightSliceWidth,
            y,
            u + centerSliceWidth - rightSliceWidth,
            v,
            rightSliceWidth,
            topSliceHeight
        );
        drawableHelper.drawTexture(
            matrices,
            x,
            y + height - bottomSliceHeight,
            u,
            v + centerSliceHeight - bottomSliceHeight,
            leftSliceWidth,
            bottomSliceHeight
        );
        drawRepeatingTexture(
            drawableHelper,
            matrices,
            x + leftSliceWidth,
            y + height - bottomSliceHeight,
            width - rightSliceWidth - leftSliceWidth,
            bottomSliceHeight,
            u + leftSliceWidth,
            v + centerSliceHeight - bottomSliceHeight,
            centerSliceWidth - rightSliceWidth - leftSliceWidth,
            bottomSliceHeight
        );
        drawableHelper.drawTexture(
            matrices,
            x + width - rightSliceWidth,
            y + height - bottomSliceHeight,
            u + centerSliceWidth - rightSliceWidth,
            v + centerSliceHeight - bottomSliceHeight,
            rightSliceWidth,
            bottomSliceHeight
        );
        drawRepeatingTexture(
            drawableHelper,
            matrices,
            x,
            y + topSliceHeight,
            leftSliceWidth,
            height - bottomSliceHeight - topSliceHeight,
            u,
            v + topSliceHeight,
            leftSliceWidth,
            centerSliceHeight - bottomSliceHeight - topSliceHeight
        );
        drawRepeatingTexture(
            drawableHelper,
            matrices,
            x + leftSliceWidth,
            y + topSliceHeight,
            width - rightSliceWidth - leftSliceWidth,
            height - bottomSliceHeight - topSliceHeight,
            u + leftSliceWidth,
            v + topSliceHeight,
            centerSliceWidth - rightSliceWidth - leftSliceWidth,
            centerSliceHeight - bottomSliceHeight - topSliceHeight
        );
        drawRepeatingTexture(
            drawableHelper,
            matrices,
            x + width - rightSliceWidth,
            y + topSliceHeight,
            leftSliceWidth,
            height - bottomSliceHeight - topSliceHeight,
            u + centerSliceWidth - rightSliceWidth,
            v + topSliceHeight,
            rightSliceWidth,
            centerSliceHeight - bottomSliceHeight - topSliceHeight
        );
    }

    private static void drawRepeatingTexture(
        DrawableHelper drawableHelper,
        MatrixStack matrices,
        int x,
        int y,
        int width,
        int height,
        int u,
        int v,
        int textureWidth,
        int textureHeight
    ) {
        int i = x;
        IntIterator intIterator = createDivider(width, textureWidth);
        while (intIterator.hasNext()) {
            int j = intIterator.nextInt();
            int k = (textureWidth - j) / 2;
            int l = y;
            IntIterator intIterator2 = createDivider(height, textureHeight);
            while (intIterator2.hasNext()) {
                int m = intIterator2.nextInt();
                int n = (textureHeight - m) / 2;
                drawableHelper.drawTexture(matrices, i, l, u + k, v + n, j, m);
                l += m;
            }
            i += j;
        }
    }

    private static @NotNull IntIterator createDivider(int sideLength, int textureSideLength) {
        int i = ceilDiv(sideLength, textureSideLength);
        return new Divider(sideLength, i);
    }

    public static int ceilDiv(int a, int b) {
        return -Math.floorDiv(-a, b);
    }

    public static class Divider implements IntIterator {

        private final int divisor;
        private final int quotient;
        private final int mod;
        private int returnedCount;
        private int remainder;

        public Divider(int dividend, int divisor) {
            this.divisor = divisor;
            if (divisor > 0) {
                this.quotient = dividend / divisor;
                this.mod = dividend % divisor;
            } else {
                this.quotient = 0;
                this.mod = 0;
            }
        }

        @Override
        public boolean hasNext() {
            return this.returnedCount < this.divisor;
        }

        @Override
        public int nextInt() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            int i = this.quotient;
            this.remainder += this.mod;
            if (this.remainder >= this.divisor) {
                this.remainder -= this.divisor;
                ++i;
            }
            ++this.returnedCount;
            return i;
        }
    }
}
