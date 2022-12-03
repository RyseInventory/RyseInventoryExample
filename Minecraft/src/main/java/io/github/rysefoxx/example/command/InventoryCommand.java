package io.github.rysefoxx.example.command;

import io.github.rysefoxx.example.ExamplePlugin;
import io.github.rysefoxx.example.util.ItemBuilder;
import io.github.rysefoxx.inventory.plugin.content.IntelligentItem;
import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.pagination.Pagination;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import io.github.rysefoxx.inventory.plugin.pagination.SlotIterator;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author Rysefoxx | Rysefoxx#7880
 * @since 12/3/2022
 */
@RequiredArgsConstructor
public class InventoryCommand implements CommandExecutor {

    private final ExamplePlugin plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player))
            return false;

        if (args.length == 1 && args[0].equalsIgnoreCase("open")) {
            RyseInventory.builder()
                    .title("Test Inventory - 1")
                    .rows(6)
                    .disableUpdateTask()
                    .provider(new InventoryProvider() {
                        @Override
                        public void init(Player player, InventoryContents contents) {
                            contents.fillBorders(new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

                            Pagination pagination = contents.pagination();
                            pagination.setItemsPerPage(20);
                            pagination.iterator(SlotIterator.builder()
                                    .startPosition(1, 1)
                                    .type(SlotIterator.SlotIteratorType.HORIZONTAL)
                                    .build());

                            contents.set(5, 3, IntelligentItem.of(new ItemBuilder(Material.ARROW)
                                    .amount((pagination.isFirst() ? 1 : pagination.page() - 1))
                                    .displayName(pagination.isFirst()
                                            ? "This is the first page"
                                            : "Page -> " + pagination.newInstance(pagination).previous().page())
                                    .build(), event -> {
                                if (pagination.isFirst()) {
                                    player.sendMessage(Component.text("You are already on the first page.",
                                            NamedTextColor.RED,
                                            TextDecoration.BOLD));
                                    return;
                                }

                                RyseInventory currentInventory = pagination.inventory();
                                currentInventory.open(player, pagination.previous().page());
                            }));

                            for (int i = 0; i < 30; i++)
                                pagination.addItem(new ItemStack(Material.STONE));

                            int page = pagination.newInstance(pagination).next().page();
                            contents.set(5, 5, IntelligentItem.of(new ItemBuilder(Material.ARROW)
                                    .amount((pagination.isLast() ? 1 : page))
                                    .displayName((!pagination.isLast()
                                            ? "Page -> " + page
                                            : "This is the last page"))
                                    .build(), event -> {
                                if (pagination.isLast()) {
                                    player.sendMessage(Component.text("You are already on the last page.",
                                            NamedTextColor.RED,
                                            TextDecoration.BOLD));
                                    return;
                                }

                                RyseInventory currentInventory = pagination.inventory();
                                currentInventory.open(player, pagination.next().page());
                            }));
                        }
                    })
                    .build(this.plugin)
                    .open(player);
            return true;
        }

        player.sendMessage(Component.text("Please use /inventory open",
                NamedTextColor.RED,
                TextDecoration.BOLD));
        return true;
    }
}
