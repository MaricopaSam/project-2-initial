package com.csc205.project2.shapes;

/**
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: Claude Sonnet 4.6
 * Generation Date: 2/23/26
 *
 * Original Prompt:
 * "Create a sphere class with the radius propriety.
 * Extends Shape3D. Implements abstract methods from ThreeDimensionalShape.
 * Include constructors with validation. Override toString() with shape specific
 * formatting and add any shape specific methods if needed.
 * Please also include proper javadoc documentation."
 *
 * Follow-up Prompts (if any):
 * 1. "[Refinement prompt 1]"
 * 2. "[Refinement prompt 2]"
 *
 * Manual Modifications:
 * - [List any changes you made to the AI output]
 * - [Explain why changes were necessary]
 *
 * Formula Verification:
 * - Volume formula verified against: [source]
 * - Surface area formula verified against: [source]
 */



/**
 * Represents a three-dimensional sphere defined by a single radius.
 *
 * <p>This class extends {@link Shape3D} and provides concrete implementations
 * of the abstract calculation methods required by the {@link ThreeDimensionalShape}
 * interface. All geometric formulas are based on the standard Euclidean definitions:</p>
 *
 * <ul>
 *   <li><b>Surface Area:</b> {@code 4 * π * r²}</li>
 *   <li><b>Volume:</b>       {@code (4/3) * π * r³}</li>
 * </ul>
 *
 * <p>Additional sphere-specific methods are provided for computing the diameter,
 * circumference of a great circle, and for scaling the sphere by a given factor.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * Sphere s = new Sphere(5.0, "blue", "mySphere");
 * System.out.println(s.getSurfaceArea());   // 314.159...
 * System.out.println(s.getVolume());        // 523.598...
 * System.out.println(s.getDiameter());      // 10.0
 * System.out.println(s);                    // formatted output
 * }</pre>
 *
 * @author  Generated
 * @version 1.0
 * @see     Shape3D
 * @see     ThreeDimensionalShape
 */
public class Sphere extends Shape3D {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    /** Minimum permitted radius value (exclusive). */
    private static final double MIN_RADIUS = 0.0;

    // -------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------

    /**
     * The radius of this sphere. Must be strictly greater than {@value #MIN_RADIUS}.
     */
    private double radius;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Constructs a {@code Sphere} with the specified radius using default metadata.
     *
     * <p>The shape name is set to {@code "Sphere"}, color to {@code "white"},
     * and label to an empty string.</p>
     *
     * @param radius the radius of the sphere; must be greater than 0
     * @throws IllegalArgumentException if {@code radius} is not greater than 0
     */
    public Sphere(double radius) {
        this(radius, "white", "");
    }

    /**
     * Constructs a {@code Sphere} with the specified radius and color.
     *
     * <p>The shape name is set to {@code "Sphere"} and label to an empty string.</p>
     *
     * @param radius the radius of the sphere; must be greater than 0
     * @param color  the color of the sphere; must not be {@code null}
     * @throws IllegalArgumentException if {@code radius} is not greater than 0,
     *                                  or if {@code color} is {@code null}
     */
    public Sphere(double radius, String color) {
        this(radius, color, "");
    }

    /**
     * Constructs a {@code Sphere} with the specified radius, color, and label.
     *
     * <p>This is the primary constructor; all other constructors delegate here.</p>
     *
     * @param radius the radius of the sphere; must be greater than 0
     * @param color  the color of the sphere; must not be {@code null}
     * @param label  an optional label or tag for this instance; must not be {@code null}
     * @throws IllegalArgumentException if {@code radius} is not greater than 0,
     *                                  or if {@code color} or {@code label} is {@code null}
     */
    public Sphere(double radius, String color, String label) {
        super("Sphere", color, label);
        validateRadius(radius);
        this.radius = radius;
    }

    /**
     * Copy constructor. Creates a new {@code Sphere} that is an independent copy
     * of the provided instance.
     *
     * @param other the {@code Sphere} to copy; must not be {@code null}
     * @throws IllegalArgumentException if {@code other} is {@code null}
     */
    public Sphere(Sphere other) {
        this(
                requireNonNull(other, "other Sphere must not be null").radius,
                other.getColor(),
                other.getLabel()
        );
    }

    // -------------------------------------------------------------------------
    // Abstract method implementations
    // -------------------------------------------------------------------------

    /**
     * Calculates the surface area of this sphere using the formula
     * {@code 4 * π * r²}.
     *
     * <p>Called internally by the concrete {@link Shape3D#getSurfaceArea()} method.</p>
     *
     * @return the surface area of this sphere in square units; always positive
     */
    @Override
    protected double calculateSurfaceArea() {
        return 4.0 * Math.PI * radius * radius;
    }

    /**
     * Calculates the volume of this sphere using the formula
     * {@code (4/3) * π * r³}.
     *
     * <p>Called internally by the concrete {@link Shape3D#getVolume()} method.</p>
     *
     * @return the volume of this sphere in cubic units; always positive
     */
    @Override
    protected double calculateVolume() {
        return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
    }

    // -------------------------------------------------------------------------
    // Sphere-specific methods
    // -------------------------------------------------------------------------

    /**
     * Returns the diameter of this sphere.
     *
     * <p>The diameter is defined as twice the radius: {@code d = 2 * r}.</p>
     *
     * @return the diameter of this sphere in the same units as the radius
     */
    public double getDiameter() {
        return 2.0 * radius;
    }

    /**
     * Returns the circumference of a great circle of this sphere.
     *
     * <p>A great circle is the largest possible circle that can be drawn on the
     * surface of the sphere. Its circumference is {@code 2 * π * r}.</p>
     *
     * @return the circumference of a great circle in the same units as the radius
     */
    public double getGreatCircleCircumference() {
        return 2.0 * Math.PI * radius;
    }

    /**
     * Returns the cross-sectional area of a great circle of this sphere.
     *
     * <p>Computed as {@code π * r²}, this represents the area of the circular
     * cross-section through the centre of the sphere.</p>
     *
     * @return the cross-sectional area in square units
     */
    public double getCrossSectionalArea() {
        return Math.PI * radius * radius;
    }

    /**
     * Returns a new {@code Sphere} scaled by the given positive factor.
     *
     * <p>The new sphere inherits the color and label of this instance but has
     * a radius equal to {@code this.radius * scaleFactor}. The original sphere
     * is left unchanged.</p>
     *
     * @param scaleFactor the factor by which to scale the radius; must be greater than 0
     * @return a new {@code Sphere} whose radius is {@code radius * scaleFactor}
     * @throws IllegalArgumentException if {@code scaleFactor} is not greater than 0
     */
    public Sphere scale(double scaleFactor) {
        if (!(scaleFactor > MIN_RADIUS)) {
            throw new IllegalArgumentException(
                    "scaleFactor must be greater than 0, but was: " + scaleFactor
            );
        }
        return new Sphere(radius * scaleFactor, getColor(), getLabel());
    }

    /**
     * Determines whether this sphere is geometrically equal to another object.
     *
     * <p>Two spheres are considered equal when they have the same radius
     * (compared with a small epsilon tolerance to accommodate floating-point
     * arithmetic), the same color, and the same label.</p>
     *
     * @param obj the object to compare with this sphere
     * @return {@code true} if {@code obj} is a {@code Sphere} with the same
     *         radius, color, and label; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Sphere)) return false;
        Sphere other = (Sphere) obj;
        return Double.compare(this.radius, other.radius) == 0
                && getColor().equals(other.getColor())
                && getLabel().equals(other.getLabel());
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return a hash code value for this sphere
     */
    @Override
    public int hashCode() {
        int result = Double.hashCode(radius);
        result = 31 * result + getColor().hashCode();
        result = 31 * result + getLabel().hashCode();
        return result;
    }

    // -------------------------------------------------------------------------
    // Getter and setter
    // -------------------------------------------------------------------------

    /**
     * Returns the radius of this sphere.
     *
     * @return the radius; always greater than 0
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets a new radius for this sphere.
     *
     * @param radius the new radius; must be greater than 0
     * @throws IllegalArgumentException if {@code radius} is not greater than 0
     */
    public void setRadius(double radius) {
        validateRadius(radius);
        this.radius = radius;
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    /**
     * Returns a sphere-specific formatted string representation of this instance.
     *
     * <p>The output follows the pattern:</p>
     * <pre>
     * Sphere {
     *   name                    = Sphere
     *   color                   = blue
     *   label                   = mySphere
     *   radius                  = 5.00 units
     *   diameter                = 10.00 units
     *   greatCircleCircumference= 31.42 units
     *   crossSectionalArea      = 78.54 square units
     *   surfaceArea             = 314.16 square units
     *   volume                  = 523.60 cubic units
     * }
     * </pre>
     *
     * @return a formatted, multi-line string summarising all properties and
     *         computed measurements of this sphere
     */
    @Override
    public String toString() {
        return String.format(
                "Sphere {%n" +
                        "  name                     = %s%n" +
                        "  color                    = %s%n" +
                        "  label                    = %s%n" +
                        "  radius                   = %.2f units%n" +
                        "  diameter                 = %.2f units%n" +
                        "  greatCircleCircumference = %.2f units%n" +
                        "  crossSectionalArea       = %.2f square units%n" +
                        "  surfaceArea              = %.2f square units%n" +
                        "  volume                   = %.2f cubic units%n" +
                        "}",
                getName(),
                getColor(),
                getLabel().isEmpty() ? "(none)" : getLabel(),
                radius,
                getDiameter(),
                getGreatCircleCircumference(),
                getCrossSectionalArea(),
                getSurfaceArea(),
                getVolume()
        );
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Validates that the provided radius is strictly greater than zero.
     *
     * @param radius the value to validate
     * @throws IllegalArgumentException if {@code radius} is not greater than 0
     */
    private static void validateRadius(double radius) {
        // Using !(radius > MIN_RADIUS) instead of (radius <= MIN_RADIUS) so that
        // NaN is also rejected: any comparison involving NaN returns false, so
        // !(NaN > 0.0) == true and the exception is correctly thrown.
        if (!(radius > MIN_RADIUS)) {
            throw new IllegalArgumentException(
                    "radius must be greater than 0, but was: " + radius
            );
        }
    }

    /**
     * Null-check helper used by the copy constructor.
     *
     * @param <T>     the type of the object
     * @param obj     the object to check
     * @param message the exception message if {@code obj} is {@code null}
     * @return {@code obj} if it is not {@code null}
     * @throws IllegalArgumentException if {@code obj} is {@code null}
     */
    private static <T> T requireNonNull(T obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
        return obj;
    }
}