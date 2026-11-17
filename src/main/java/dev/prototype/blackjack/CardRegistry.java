package dev.prototype.blackjack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapIdComponent;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Stores map IDs observed in the player's hotbar.
 * This prototype records IDs only; image-to-card/value recognition is a later stage.
 */
public final class CardRegistry {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = Path.of("config", "blackjack-tracker", "map-ids.json");
    private final Set<Integer> mapIds = new LinkedHashSet<>();

    public void scanHotbar(PlayerEntity player) {
        boolean changed = false;
        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = player.getInventory().getStack(slot);
            if (!(stack.getItem() instanceof FilledMapItem)) continue;
            MapIdComponent id = stack.get(DataComponentTypes.MAP_ID);
            if (id != null && mapIds.add(id.id())) changed = true;
        }
        if (changed) save();
    }

    public int size() { return mapIds.size(); }

    public void load() {
        try {
            if (!Files.exists(FILE)) return;
            Type type = new TypeToken<Set<Integer>>() {}.getType();
            try (Reader reader = Files.newBufferedReader(FILE)) {
                Set<Integer> loaded = GSON.fromJson(reader, type);
                if (loaded != null) mapIds.addAll(loaded);
            }
        } catch (Exception ignored) {
            // A malformed config should not prevent the client from starting.
        }
    }

    private void save() {
        try {
            Files.createDirectories(FILE.getParent());
            try (Writer writer = Files.newBufferedWriter(FILE)) {
                GSON.toJson(mapIds, writer);
            }
        } catch (Exception ignored) {
            // Config persistence is best-effort in this prototype.
        }
    }
}
