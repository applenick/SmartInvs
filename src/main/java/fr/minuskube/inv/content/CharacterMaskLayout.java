package fr.minuskube.inv.content;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * A layout defined by character masking patterns. Each character in the mask represents a specific
 * type of item. Space characters represent empty slots that cannot be interacted with.
 *
 * <p>Example:
 *
 * <pre>
 * new CharacterMaskLayout(
 *     "#########",
 *     "#   A   #",
 *     "#  BBB  #",
 *     "#   C   #",
 *     "### X ###"
 * )
 * </pre>
 */
public class CharacterMaskLayout implements InventoryLayout {

  private final int rows;
  private final int columns;
  private final char[][] mask;
  private final Map<Character, Set<SlotPos>> charPositions;
  private final Set<SlotPos> validSlots;

  public CharacterMaskLayout(String... maskRows) {
    if (maskRows == null || maskRows.length == 0) {
      throw new IllegalArgumentException("Mask must have at least one row");
    }

    this.rows = maskRows.length;
    this.columns = maskRows[0].length();

    // Validate all rows have same length
    for (int i = 0; i < maskRows.length; i++) {
      if (maskRows[i].length() != columns) {
        throw new IllegalArgumentException(
            "All mask rows must have the same length. Row 0 has "
                + columns
                + " characters, but row "
                + i
                + " has "
                + maskRows[i].length());
      }
    }

    // Parse the mask
    this.mask = new char[rows][columns];
    this.charPositions = new HashMap<>();
    this.validSlots = new HashSet<>();

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        char c = maskRows[row].charAt(col);
        mask[row][col] = c;

        // Space is reserved for empty slots
        if (c != ' ') {
          SlotPos pos = new SlotPos(row, col);
          validSlots.add(pos);

          // Track positions for each character
          charPositions.computeIfAbsent(c, k -> new HashSet<>()).add(pos);
        }
      }
    }
  }

  @Override
  public boolean isValidSlot(int row, int column) {
    if (row < 0 || row >= rows || column < 0 || column >= columns) {
      return false;
    }
    return mask[row][column] != ' ';
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
    if (!isValidSlot(row, column)) {
      return Optional.empty();
    }
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

  public char getCharAt(int row, int column) {
    if (row < 0 || row >= rows || column < 0 || column >= columns) {
      return ' ';
    }
    return mask[row][column];
  }

  public Set<SlotPos> getSlotsForChar(char c) {
    return new HashSet<>(charPositions.getOrDefault(c, Collections.emptySet()));
  }

  public Set<Character> getCharacters() {
    return new HashSet<>(charPositions.keySet());
  }

  public String getMaskPattern() {
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        sb.append(mask[row][col]);
      }
      if (row < rows - 1) {
        sb.append('\n');
      }
    }
    return sb.toString();
  }
}
