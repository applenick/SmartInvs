package fr.minuskube.inv.content;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/** A rectangular layout where all slots in the grid are valid. */
public class RectangularLayout implements InventoryLayout {

  private final int rows;
  private final int columns;
  private final Set<SlotPos> validSlots;

  public RectangularLayout(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;

    // Pre-compute all valid slots
    this.validSlots = new HashSet<>();
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        validSlots.add(new SlotPos(row, col));
      }
    }
  }

  @Override
  public boolean isValidSlot(int row, int column) {
    return row >= 0 && row < rows && column >= 0 && column < columns;
  }

  @Override
  public Set<SlotPos> getValidSlots() {
    return new HashSet<>(validSlots);
  }

  @Override
  public int toLinearIndex(int row, int column) {
    if (!isValidSlot(row, column)) {
      return -1;
    }
    return row * columns + column;
  }

  @Override
  public Optional<SlotPos> fromLinearIndex(int index) {
    if (index < 0 || index >= rows * columns) {
      return Optional.empty();
    }
    int row = index / columns;
    int column = index % columns;
    return Optional.of(new SlotPos(row, column));
  }

  @Override
  public int getRows() {
    return rows;
  }

  @Override
  public int getColumns() {
    return columns;
  }
}
