package com.csc205.project2.shapes;

/**
 * Abstract class representing a three-dimensional geometric shape.
 *
 * <p>This class implements the {@link ThreeDimensionalShape} interface and provides
 * concrete implementations of {@code getSurfaceArea()} and {@code getVolume()} that
 * delegate to protected abstract calculation methods. Subclasses are responsible for
 * supplying those calculation methods based on their specific geometry.</p>
 *
 * <p>Common properties such as {@code name} and {@code color} are maintained here,
 * along with constructors, getters, setters, and a formatted {@code toString()} method.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * public class Sphere extends Shape3D {
 *     private double radius;
 *
 *     public Sphere(double radius) {
 *         super("Sphere", "white");
 *         this.radius = radius;
 *     }
 *
 *     {@literal @}Override
 *     protected double calculateSurfaceArea() {
 *         return 4 * Math.PI * radius * radius;
 *     }
 *
 *     {@literal @}Override
 *     protected double calculateVolume() {
 *         return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
 *     }
 * }
 * }</pre>
 *
 * @author  Generated
 * @version 1.0
 * @see     ThreeDimensionalShape
 */
public abstract class Shape3D implements ThreeDimensionalShape {

    // -------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------

    /** The human-readable name of this shape (e.g., "Sphere", "Cube"). */
    private String name;

    /** The color of this shape (e.g., "red", "blue"). */
    private String color;

    /**
     * A label used to identify or tag this shape instance. Defaults to an
     * empty string when not explicitly provided.
     */
    private String label;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Default no-argument constructor.
     *
     * <p>Initialises {@code name} to {@code "Unknown"}, {@code color} to
     * {@code "Unspecified"}, and {@code label} to an empty string.</p>
     */
    public Shape3D() {
        this("Unknown", "Unspecified", "");
    }

    /**
     * Constructs a {@code Shape3D} with a given name and color.
     *
     * <p>{@code label} is initialised to an empty string.</p>
     *
     * @param name  the name of the shape; must not be {@code null}
     * @param color the color of the shape; must not be {@code null}
     * @throws IllegalArgumentException if {@code name} or {@code color} is {@code null}
     */
    public Shape3D(String name, String color) {
        this(name, color, "");
    }

    /**
     * Constructs a {@code Shape3D} with a given name, color, and label.
     *
     * @param name  the name of the shape; must not be {@code null}
     * @param color the color of the shape; must not be {@code null}
     * @param label an optional label or tag for the shape instance; may be empty
     * @throws IllegalArgumentException if {@code name}, {@code color}, or {@code label}
     *                                  is {@code null}
     */
    public Shape3D(String name, String color, String label) {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (color == null) {
            throw new IllegalArgumentException("color must not be null");
        }
        if (label == null) {
            throw new IllegalArgumentException("label must not be null");
        }
        this.name  = name;
        this.color = color;
        this.label = label;
    }

    // -------------------------------------------------------------------------
    // Abstract calculation methods (to be implemented by subclasses)
    // -------------------------------------------------------------------------

    /**
     * Calculates the surface area of this shape.
     *
     * <p>Subclasses must supply the shape-specific formula. The result is used
     * directly by {@link #getSurfaceArea()}.</p>
     *
     * @return the surface area of this shape, expressed in square units; always
     *         non-negative
     */
    protected abstract double calculateSurfaceArea();

    /**
     * Calculates the volume of this shape.
     *
     * <p>Subclasses must supply the shape-specific formula. The result is used
     * directly by {@link #getVolume()}.</p>
     *
     * @return the volume of this shape, expressed in cubic units; always
     *         non-negative
     */
    protected abstract double calculateVolume();

    // -------------------------------------------------------------------------
    // ThreeDimensionalShape interface — concrete implementations
    // -------------------------------------------------------------------------

    /**
     * Returns the surface area of this three-dimensional shape.
     *
     * <p>This concrete implementation delegates to {@link #calculateSurfaceArea()},
     * which must be provided by each concrete subclass.</p>
     *
     * @return the surface area of the shape in square units; always non-negative
     */
    @Override
    public final double getSurfaceArea() {
        return calculateSurfaceArea();
    }

    /**
     * Returns the volume of this three-dimensional shape.
     *
     * <p>This concrete implementation delegates to {@link #calculateVolume()},
     * which must be provided by each concrete subclass.</p>
     *
     * @return the volume of the shape in cubic units; always non-negative
     */
    @Override
    public final double getVolume() {
        return calculateVolume();
    }

    // -------------------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------------------

    /**
     * Returns the name of this shape.
     *
     * @return the shape name; never {@code null}
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this shape.
     *
     * @param name the new name; must not be {@code null}
     * @throws IllegalArgumentException if {@code name} is {@code null}
     */
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        this.name = name;
    }

    /**
     * Returns the color of this shape.
     *
     * @return the shape color; never {@code null}
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color of this shape.
     *
     * @param color the new color; must not be {@code null}
     * @throws IllegalArgumentException if {@code color} is {@code null}
     */
    public void setColor(String color) {
        if (color == null) {
            throw new IllegalArgumentException("color must not be null");
        }
        this.color = color;
    }

    /**
     * Returns the label associated with this shape instance.
     *
     * @return the label; never {@code null}, may be empty
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label associated with this shape instance.
     *
     * @param label the new label; must not be {@code null}
     * @throws IllegalArgumentException if {@code label} is {@code null}
     */
    public void setLabel(String label) {
        if (label == null) {
            throw new IllegalArgumentException("label must not be null");
        }
        this.label = label;
    }

    // -------------------------------------------------------------------------
    // Object overrides
    // -------------------------------------------------------------------------

    /**
     * Returns a consistently formatted string representation of this shape.
     *
     * <p>The output follows the pattern:</p>
     * <pre>
     * Shape3D {
     *   name         = Sphere
     *   color        = blue
     *   label        = myShape
     *   surfaceArea  = 314.16 square units
     *   volume       = 523.60 cubic units
     * }
     * </pre>
     *
     * @return a formatted, multi-line string summarising this shape's properties
     *         and computed measurements
     */
    @Override
    public String toString() {
        return String.format(
                "Shape3D {%n" +
                        "  name         = %s%n" +
                        "  color        = %s%n" +
                        "  label        = %s%n" +
                        "  surfaceArea  = %.2f square units%n" +
                        "  volume       = %.2f cubic units%n" +
                        "}",
                name,
                color,
                label.isEmpty() ? "(none)" : label,
                getSurfaceArea(),
                getVolume()
        );
    }
}
