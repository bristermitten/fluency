package me.bristermitten.spigotmenus.menu.button;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.bristermitten.spigotmenus.menu.MenuClickEvent;
import me.bristermitten.spigotmenus.menu.button.builder.MenuButtonBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class MenuButton {
    private final ItemStack item;
    private final Consumer<MenuClickEvent> onClick;
    private final Map<String, Object> injectedData = new HashMap<>();

    /**
     * Inject data into this button, essentially replacing any lore or title placeholders
     *
     * @param inject data to inject
     */
    public void injectData(Map<String, Object> inject) {
        injectedData.putAll(inject);
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

    public Object getInjectedData(String key) {
        return getInjectedData(key, null);
    }

    public Object getInjectedData(String key, Object defaultValue) {
        return injectedData.getOrDefault(key, defaultValue);
    }

    private String replaceString(String string, Map<String, Object> data) {
        String[] sa = {string};
        data.forEach((s, o) -> {
            if (o != null)
                sa[0] = (sa[0].replace(s, o.toString()));
        });
        return sa[0];
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MenuButton.class.getSimpleName() + "[", "]")
                .add("item=" + item)
                .toString();
    }
}
