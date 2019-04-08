package me.bristermitten.spigotmenus.menu.button;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.bristermitten.spigotmenus.menu.MenuClickEvent;
import me.bristermitten.spigotmenus.menu.button.builder.MenuButtonBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class MenuButton {
    private final ItemStack item;
    private final Consumer<MenuClickEvent> onClick;

    /**
     * Inject data into this button, essentially replacing any lore or title placeholders
     *
     * @param inject
     */
    public void injectData(Map<String, Object> inject) {
        MenuButtonBuilder buttonBuilder = new MenuButtonBuilder(item);
        buttonBuilder.onAnyClick().thenAction(onClick);
        /* copy over onClick. Technically it might not be onAnyClick but this ensures the Consumer will always
        be called, letting that handle the click*/

        if (item.hasItemMeta()) {
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta.hasDisplayName()) {
                buttonBuilder.setName(replaceString(itemMeta.getDisplayName(), inject));
            }
            if (itemMeta.hasLore()) {
                List<String> injected =
                        itemMeta.getLore().stream().map(s -> replaceString(s, inject))
                                .collect(Collectors.toList());
                itemMeta.setLore(injected);
            }
        }
    }

    private String replaceString(String string, Map<String, Object> data) {
        AtomicReference<String> sa = new AtomicReference<>(string);
        data.forEach((s, o) -> {
            if (o != null)
                sa.set(sa.get().replace(s, o.toString()));
        });
        return string;
    }
}
