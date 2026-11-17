package dev.prototype.blackjack;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class BlackjackClient implements ClientModInitializer {
    private static KeyBinding toggleHud;
    private static boolean hudVisible = true;
    private static final CardRegistry REGISTRY = new CardRegistry();
    private static final GameTracker TRACKER = new GameTracker();

    @Override
    public void onInitializeClient() {
        REGISTRY.load();
        toggleHud = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.blackjack_tracker.toggle_hud",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                KeyBinding.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleHud.wasPressed()) hudVisible = !hudVisible;
            if (client.player != null) {
                REGISTRY.scanHotbar(client.player);
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            if (!hudVisible) return;
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) return;

            int x = 8;
            int y = 8;
            int width = 190;
            int height = 66;
            drawContext.fill(x, y, x + width, y + height, 0xCC171020);
            drawContext.fill(x, y, x + width, y + 2, 0xFF9B5CFF);
            drawContext.drawText(client.textRenderer, Text.literal("BLACKJACK TRACKER"),
                    x + 8, y + 7, 0xFFE7D7FF, false);
            drawContext.drawText(client.textRenderer,
                    Text.literal("Player: " + TRACKER.playerScore() + "    Dealer: " + TRACKER.dealerScore()),
                    x + 8, y + 23, 0xFFFFFFFF, false);
            drawContext.drawText(client.textRenderer,
                    Text.literal("Cards observed: " + TRACKER.cardsSeen() + "   Saved IDs: " + REGISTRY.size()),
                    x + 8, y + 39, 0xFFCCBBDD, false);
        });
    }
}
