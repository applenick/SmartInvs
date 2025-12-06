package fr.minuskube.inv.content;

import java.util.Optional;
import java.util.Set;

public interface InventoryLayout {

  boolean isValidSlot(int row, int column);

  Set<SlotPos> getValidSlots();

  int toLinearIndex(int row, int column);

  Optional<SlotPos> fromLinearIndex(int index);

  int getRows();

  int getColumns();
}
