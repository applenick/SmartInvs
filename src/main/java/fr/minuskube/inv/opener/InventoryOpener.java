package fr.minuskube.inv.opener;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryLayout;
import fr.minuskube.inv.content.RectangularLayout;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public interface InventoryOpener {

    Inventory open(SmartInventory inv, Player player);
    boolean supports(InventoryType type);

    default void fill(Inventory handle, InventoryContents contents) {
        ClickableItem[][] items = contents.all();
        SmartInventory inv = contents.inventory();
        InventoryLayout layout = inv.getLayout().orElse(new RectangularLayout(inv.getRows(), inv.getColumns()));

        for(int row = 0; row < items.length; row++) {
            for(int column = 0; column < items[row].length; column++) {
                if(items[row][column] != null) {
                    int linearIndex = layout.toLinearIndex(row, column);
                    if (linearIndex >= 0) {
                        handle.setItem(linearIndex, items[row][column].getItem());
                    }
                }
            }
        }
    }

}
