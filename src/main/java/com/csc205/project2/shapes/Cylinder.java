package com.csc205.project2.shapes;


/**
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: Claude Sonnet 4.6
 * Generation Date: 2/23/26
 *
 * "Create a cylinder class with Properties: radius, height. Extends Shape 3D.
 * Implements abstract methods from ThreeDimensionalShape.
 * Include constructors with validation.
 * Override toString() with shape specific formatting and add any shape specific methods
 * if needed. Please also include proper javadoc documentation.
 *
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
 * Represents a three-dimensional right circular cylinder defined by a radius and height.
 *
 * <p>A right circular cylinder has two parallel, congruent circular bases connected
 * by a curved lateral surface. The axis is perpendicular to both bases.
 * This class extends {@link Shape3D} and provides concrete implementations of the
 * abstract calculation methods required by the {@link ThreeDimensionalShape} interface.
 * All geometric formulas are based on standard Euclidean definitions:</p>
 *
 * <ul>
 *   <li><b>Lateral Surface Area:</b> {@code 2 * π * r * h}</li>
 *   <li><b>Base Area:</b>            {@code π * r²}  (one circular base)</li>
 *   <li><b>Total Surface Area:</b>   {@code 2 * π * r * (r + h)}</li>
 *   <li><b>Volume:</b>               {@code π * r² * h}</li>
 *   <li><b>Slant Height:</b>         {@code √(r² + h²)}  (hypotenuse of axial cross-section)</li>
 *   <li><b>Axial Diagonal:</b>       {@code √((2r)² + h²)}  (longest internal line)</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * Cylinder c = new Cylinder(3.0, 7.0, "green", "myCylinder");
 * System.out.println(c.getSurfaceArea());        // 188.495...
 * System.out.println(c.getVolume());             // 197.920...
 * System.out.println(c.getLateralSurfaceArea()); // 131.946...
 * System.out.println(c.getAspectRatio());        // 2.333...
 * System.out.println(c.scale(2.0));              // Cylinder with r=6.0, h=14.0
 * System.out.println(c);                         // formatted output
 * }</pre>
 *
 * @author  Generated
 * @version 1.0
 * @see     Shape3D
 * @see     ThreeDimensionalShape
 */
public class Cylinder extends Shape3D {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    /** Minimum permitted value for radius and height (exclusive). */
    private static final double MIN_VALUE = 0.0;

    // -------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------

    /**
     * The radius of the circular base of this cylinder.
     * Must be strictly greater than {@value #MIN_VALUE}.
     */
    private double radius;

    /**
     * The height of this cylinder (perpendicular distance between the two bases).
     * Must be strictly greater than {@value #MIN_VALUE}.
     */
    private double height;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Constructs a {@code Cylinder} with the specified radius and height
     * using default metadata.
     *
     * <p>The shape name is set to {@code "Cylinder"}, color to {@code "white"},
     * and label to an empty string.</p>
     *
     * @param radius the radius of the cylinder's base; must be greater than 0
     * @param height the height of the cylinder; must be greater than 0
     * @throws IllegalArgumentException if {@code radius} or {@code height} is
     *                                  not greater than 0
     */
    public Cylinder(double radius, double height) {
        this(radius, height, "white", "");
    }

    /**
     * Constructs a {@code Cylinder} with the specified radius, height, and color.
     *
     * <p>The shape name is set to {@code "Cylinder"} and label to an empty string.</p>
     *
     * @param radius the radius of the cylinder's base; must be greater than 0
     * @param height the height of the cylinder; must be greater than 0
     * @param color  the color of the cylinder; must not be {@code null}
     * @throws IllegalArgumentException if {@code radius} or {@code height} is not
     *                                  greater than 0, or if {@code color} is {@code null}
     */
    public Cylinder(double radius, double height, String color) {
        this(radius, height, color, "");
    }

    /**
     * Constructs a {@code Cylinder} with the specified radius, height, color, and label.
     *
     * <p>This is the primary constructor; all other constructors delegate here.</p>
     *
     * @param radius the radius of the cylinder's base; must be greater than 0
     * @param height the height of the cylinder; must be greater than 0
     * @param color  the color of the cylinder; must not be {@code null}
     * @param label  an optional label or tag for this instance; must not be {@code null}
     * @throws IllegalArgumentException if {@code radius} or {@code height} is not
     *                                  greater than 0, or if {@code color} or
     *                                  {@code label} is {@code null}
     */
    public Cylinder(double radius, double height, String color, String label) {
        super("Cylinder", color, label);
        validateRadius(radius);
        validateHeight(height);
        this.radius = radius;
        this.height = height;
    }

    /**
     * Copy constructor. Creates a new {@code Cylinder} that is an independent copy
     * of the provided instance.
     *
     * @param other the {@code Cylinder} to copy; must not be {@code null}
     * @throws IllegalArgumentException if {@code other} is {@code null}
     */
    public Cylinder(Cylinder other) {
        this(
                requireNonNull(other, "other Cylinder must not be null").radius,
                other.height,
                other.getColor(),
                other.getLabel()
        );
    }

    // -------------------------------------------------------------------------
    // Abstract method implementations
    // -------------------------------------------------------------------------

    /**
     * Calculates the total surface area of this cylinder using the formula
     * {@code 2 * π * r * (r + h)}, where {@code r} is the radius and
     * {@code h} is the height.
     *
     * <p>This combines two circular base areas ({@code 2 * π * r²}) and the
     * lateral surface area ({@code 2 * π * r * h}) into a single expression.</p>
     *
     * <p>Called internally by the concrete {@link Shape3D#getSurfaceArea()} method.</p>
     *
     * @return the total surface area of this cylinder in square units; always positive
     */
    @Override
    protected double calculateSurfaceArea() {
        return 2.0 * Math.PI * radius * (radius + height);
    }

    /**
     * Calculates the volume of this cylinder using the formula
     * {@code π * r² * h}, where {@code r} is the radius and {@code h} is the height.
     *
     * <p>Called internally by the concrete {@link Shape3D#getVolume()} method.</p>
     *
     * @return the volume of this cylinder in cubic units; always positive
     */
    @Override
    protected double calculateVolume() {
        return Math.PI * radius * radius * height;
    }

    // -------------------------------------------------------------------------
    // Cylinder-specific methods
    // -------------------------------------------------------------------------

    /**
     * Returns the area of one circular base of this cylinder.
     *
     * <p>Computed as {@code π * r²}, where {@code r} is the radius.</p>
     *
     * @return the base area in square units; always positive
     */
    public double getBaseArea() {
        return Math.PI * radius * radius;
    }

    /**
     * Returns the lateral (curved) surface area of this cylinder.
     *
     * <p>The lateral surface is the curved rectangle that wraps around the
     * cylinder, computed as {@code 2 * π * r * h}, where {@code r} is the
     * radius and {@code h} is the height.</p>
     *
     * @return the lateral surface area in square units; always positive
     */
    public double getLateralSurfaceArea() {
        return 2.0 * Math.PI * radius * height;
    }

    /**
     * Returns the diameter of the circular base of this cylinder.
     *
     * <p>Computed as {@code 2 * r}, where {@code r} is the radius.</p>
     *
     * @return the diameter in the same units as the radius; always positive
     */
    public double getDiameter() {
        return 2.0 * radius;
    }

    /**
     * Returns the circumference of one circular base of this cylinder.
     *
     * <p>Computed as {@code 2 * π * r}, where {@code r} is the radius.</p>
     *
     * @return the base circumference in the same units as the radius; always positive
     */
    public double getBaseCircumference() {
        return 2.0 * Math.PI * radius;
    }

    /**
     * Returns the slant height of this cylinder.
     *
     * <p>The slant height is the length of the hypotenuse formed by the radius
     * and height in an axial cross-section of the cylinder:
     * {@code √(r² + h²)}.</p>
     *
     * <p>This is equivalent to the shortest path along the curved surface from
     * a point on one base edge directly to the opposite point on the other
     * base edge.</p>
     *
     * @return the slant height in the same units as the radius and height;
     *         always positive
     */
    public double getSlantHeight() {
        return Math.sqrt(radius * radius + height * height);
    }

    /**
     * Returns the length of the axial diagonal of this cylinder.
     *
     * <p>The axial diagonal is the longest straight line that can be drawn
     * inside the cylinder, connecting two points on opposite base edges and
     * passing through the interior. It is computed as
     * {@code √((2r)² + h²)} = {@code √(4r² + h²)}.</p>
     *
     * @return the axial diagonal length in the same units as the radius and
     *         height; always positive
     */
    public double getAxialDiagonal() {
        double diameter = getDiameter();
        return Math.sqrt(diameter * diameter + height * height);
    }

    /**
     * Returns the aspect ratio of this cylinder (height-to-diameter ratio).
     *
     * <p>Computed as {@code h / (2 * r)}. Values greater than 1 indicate a
     * tall, narrow cylinder; values less than 1 indicate a short, wide one;
     * a value of exactly 1 indicates that the height equals the diameter.</p>
     *
     * @return the aspect ratio; always positive
     */
    public double getAspectRatio() {
        return height / getDiameter();
    }

    /**
     * Returns a new {@code Cylinder} scaled uniformly by the given positive factor.
     *
     * <p>Both radius and height are multiplied by {@code scaleFactor}. The new
     * cylinder inherits the color and label of this instance. The original
     * cylinder is left unchanged.</p>
     *
     * @param scaleFactor the factor by which to scale both dimensions;
     *                    must be greater than 0
     * @return a new {@code Cylinder} whose radius is {@code radius * scaleFactor}
     *         and height is {@code height * scaleFactor}
     * @throws IllegalArgumentException if {@code scaleFactor} is not greater than 0
     */
    public Cylinder scale(double scaleFactor) {
        if (!(scaleFactor > MIN_VALUE)) {
            throw new IllegalArgumentException(
                    "scaleFactor must be greater than 0, but was: " + scaleFactor
            );
        }
        return new Cylinder(radius * scaleFactor, height * scaleFactor,
                getColor(), getLabel());
    }

    /**
     * Returns a new {@code Cylinder} with independent scaling applied to
     * the radius and height separately.
     *
     * <p>This is useful when you want to stretch or compress the cylinder
     * in only one dimension (e.g., doubling the radius while keeping the height).
     * The new cylinder inherits the color and label of this instance.
     * The original cylinder is left unchanged.</p>
     *
     * @param radiusFactor the factor by which to scale the radius; must be greater than 0
     * @param heightFactor the factor by which to scale the height; must be greater than 0
     * @return a new {@code Cylinder} with the scaled dimensions
     * @throws IllegalArgumentException if either factor is not greater than 0
     */
    public Cylinder scale(double radiusFactor, double heightFactor) {
        if (!(radiusFactor > MIN_VALUE)) {
            throw new IllegalArgumentException(
                    "radiusFactor must be greater than 0, but was: " + radiusFactor
            );
        }
        if (!(heightFactor > MIN_VALUE)) {
            throw new IllegalArgumentException(
                    "heightFactor must be greater than 0, but was: " + heightFactor
            );
        }
        return new Cylinder(radius * radiusFactor, height * heightFactor,
                getColor(), getLabel());
    }

    /**
     * Determines whether a sphere with the given radius can fit entirely inside
     * this cylinder without touching the curved surface or the bases.
     *
     * <p>A sphere of radius {@code r_s} fits inside a cylinder of radius {@code r}
     * and height {@code h} when {@code r_s < r} and {@code 2 * r_s < h}
     * (i.e., the sphere's diameter is strictly less than the cylinder's height,
     * and the sphere's radius is strictly less than the cylinder's radius).</p>
     *
     * @param sphereRadius the radius of the sphere to test; must be greater than 0
     * @return {@code true} if the sphere fits strictly inside the cylinder;
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code sphereRadius} is not greater than 0
     */
    public boolean canFitSphere(double sphereRadius) {
        if (!(sphereRadius > MIN_VALUE)) {
            throw new IllegalArgumentException(
                    "sphereRadius must be greater than 0, but was: " + sphereRadius
            );
        }
        return sphereRadius < radius && (2.0 * sphereRadius) < height;
    }

    /**
     * Returns the radius of the largest sphere that fits exactly inside this cylinder.
     *
     * <p>The inscribed sphere is limited by whichever dimension is smaller:
     * the base radius or half the height. Its radius is
     * {@code min(r, h / 2)}.</p>
     *
     * @return the inscribed sphere radius in the same units as the cylinder's
     *         dimensions; always positive
     */
    public double getInscribedSphereRadius() {
        return Math.min(radius, height / 2.0);
    }

    // -------------------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------------------

    /**
     * Returns the radius of this cylinder's circular base.
     *
     * @return the radius; always greater than 0
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets a new radius for this cylinder's circular base.
     *
     * @param radius the new radius; must be greater than 0
     * @throws IllegalArgumentException if {@code radius} is not greater than 0
     */
    public void setRadius(double radius) {
        validateRadius(radius);
        this.radius = radius;
    }

    /**
     * Returns the height of this cylinder.
     *
     * @return the height; always greater than 0
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets a new height for this cylinder.
     *
     * @param height the new height; must be greater than 0
     * @throws IllegalArgumentException if {@code height} is not greater than 0
     */
    public void setHeight(double height) {
        validateHeight(height);
        this.height = height;
    }

    // -------------------------------------------------------------------------
    // Object overrides
    // -------------------------------------------------------------------------

    /**
     * Determines whether this cylinder is geometrically equal to another object.
     *
     * <p>Two cylinders are considered equal when they have the same radius and
     * height (using {@link Double#compare} for exact bit-for-bit equality),
     * the same color, and the same label.</p>
     *
     * @param obj the object to compare with this cylinder
     * @return {@code true} if {@code obj} is a {@code Cylinder} with the same
     *         radius, height, color, and label; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cylinder)) return false;
        Cylinder other = (Cylinder) obj;
        return Double.compare(this.radius, other.radius) == 0
                && Double.compare(this.height, other.height) == 0
                && getColor().equals(other.getColor())
                && getLabel().equals(other.getLabel());
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return a hash code value for this cylinder
     */
    @Override
    public int hashCode() {
        int result = Double.hashCode(radius);
        result = 31 * result + Double.hashCode(height);
        result = 31 * result + getColor().hashCode();
        result = 31 * result + getLabel().hashCode();
        return result;
    }

    /**
     * Returns a cylinder-specific formatted string representation of this instance.
     *
     * <p>The output follows the pattern:</p>
     * <pre>
     * Cylinder {
     *   name                  = Cylinder
     *   color                 = green
     *   label                 = myCylinder
     *   radius                = 3.00 units
     *   height                = 7.00 units
     *   diameter              = 6.00 units
     *   aspectRatio           = 1.17
     *   baseCircumference     = 18.85 units
     *   baseArea              = 28.27 square units
     *   lateralSurfaceArea    = 131.95 square units
     *   surfaceArea           = 188.50 square units
     *   slantHeight           = 7.62 units
     *   axialDiagonal         = 9.22 units
     *   inscribedSphereRadius = 3.00 units
     *   volume                = 197.92 cubic units
     * }
     * </pre>
     *
     * @return a formatted, multi-line string summarising all properties and
     *         computed measurements of this cylinder
     */
    @Override
    public String toString() {
        return String.format(
                "Cylinder {%n" +
                        "  name                  = %s%n" +
                        "  color                 = %s%n" +
                        "  label                 = %s%n" +
                        "  radius                = %.2f units%n" +
                        "  height                = %.2f units%n" +
                        "  diameter              = %.2f units%n" +
                        "  aspectRatio           = %.2f%n" +
                        "  baseCircumference     = %.2f units%n" +
                        "  baseArea              = %.2f square units%n" +
                        "  lateralSurfaceArea    = %.2f square units%n" +
                        "  surfaceArea           = %.2f square units%n" +
                        "  slantHeight           = %.2f units%n" +
                        "  axialDiagonal         = %.2f units%n" +
                        "  inscribedSphereRadius = %.2f units%n" +
                        "  volume                = %.2f cubic units%n" +
                        "}",
                getName(),
                getColor(),
                getLabel().isEmpty() ? "(none)" : getLabel(),
                radius,
                height,
                getDiameter(),
                getAspectRatio(),
                getBaseCircumference(),
                getBaseArea(),
                getLateralSurfaceArea(),
                getSurfaceArea(),
                getSlantHeight(),
                getAxialDiagonal(),
                getInscribedSphereRadius(),
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
        // !(radius > MIN_VALUE) rejects NaN, negative values, and zero.
        if (!(radius > MIN_VALUE)) {
            throw new IllegalArgumentException(
                    "radius must be greater than 0, but was: " + radius
            );
        }
    }

    /**
     * Validates that the provided height is strictly greater than zero.
     *
     * @param height the value to validate
     * @throws IllegalArgumentException if {@code height} is not greater than 0
     */
    private static void validateHeight(double height) {
        // !(height > MIN_VALUE) rejects NaN, negative values, and zero.
        if (!(height > MIN_VALUE)) {
            throw new IllegalArgumentException(
                    "height must be greater than 0, but was: " + height
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

