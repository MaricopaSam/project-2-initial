package com.csc205.project2.shapes;

/**
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: Claude Sonnet 4.6
 * Generation Date: 2/23/26
 *
 * "Create a Cone class with properties radius, circumference,
 * height and other relevant properties. Extends Shape 3D.
 * Implements abstract methods from ThreeDimensionalShape.
 * Include constructors with validation.
 * Override toString() with shape specific formatting and add any shape specific methods
 * if needed. Please also include proper javadoc documentation"
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

 /**
 * Represents a three-dimensional right circular cone defined by a base radius
 * and a perpendicular height.
 *
 * <p>A right circular cone has a circular base whose centre lies directly below
 * the apex. All geometric measurements are derived from two primary fields —
 * {@code radius} and {@code height} — while {@code slantHeight} and
 * {@code circumference} are lazily derived properties kept in sync automatically
 * whenever either primary field is mutated through a setter.</p>
 *
 * <p>All geometric formulas are based on standard Euclidean definitions:</p>
 *
 * <ul>
 *   <li><b>Slant Height:</b>          {@code l = √(r² + h²)}</li>
 *   <li><b>Base Circumference:</b>    {@code C = 2 * π * r}</li>
 *   <li><b>Base Area:</b>             {@code A_base = π * r²}</li>
 *   <li><b>Lateral Surface Area:</b>  {@code A_lateral = π * r * l}</li>
 *   <li><b>Total Surface Area:</b>    {@code A_total = π * r * (r + l)}</li>
 *   <li><b>Volume:</b>                {@code V = (1/3) * π * r² * h}</li>
 *   <li><b>Apex Angle (half):</b>     {@code θ = arctan(r / h)}</li>
 *   <li><b>Inscribed Sphere Radius:</b>{@code r_i = (r * h) / (r + l)}</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * Cone c = new Cone(3.0, 4.0, "red", "myCone");
 * System.out.println(c.getSlantHeight());          // 5.0
 * System.out.println(c.getSurfaceArea());          // 75.398...
 * System.out.println(c.getVolume());               // 37.699...
 * System.out.println(c.getHalfApexAngleDegrees()); // 36.869...
 * System.out.println(c.scale(2.0));                // Cone r=6, h=8
 * System.out.println(c);                           // formatted output
 * }</pre>
 *
 * @author  Generated
 * @version 1.0
 * @see     Shape3D
 * @see     ThreeDimensionalShape
 */
public class Cone extends Shape3D {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    /** Minimum permitted value for radius and height (exclusive). */
    private static final double MIN_VALUE = 0.0;

    // -------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------

    /**
     * The radius of the circular base of this cone.
     * Must be strictly greater than {@value #MIN_VALUE}.
     */
    private double radius;

    /**
     * The perpendicular height from the base centre to the apex of this cone.
     * Must be strictly greater than {@value #MIN_VALUE}.
     */
    private double height;

    /**
     * The slant height of this cone — the distance from the apex to any point
     * on the base circumference, computed as {@code √(r² + h²)}.
     * Derived from {@code radius} and {@code height}; updated automatically
     * whenever either primary field is mutated.
     */
    private double slantHeight;

    /**
     * The circumference of the circular base of this cone, computed as
     * {@code 2 * π * r}.
     * Derived from {@code radius}; updated automatically whenever {@code radius}
     * is mutated.
     */
    private double circumference;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Constructs a {@code Cone} with the specified radius and height using default
     * metadata.
     *
     * <p>The shape name is set to {@code "Cone"}, color to {@code "white"},
     * and label to an empty string.</p>
     *
     * @param radius the radius of the cone's base; must be greater than 0
     * @param height the perpendicular height of the cone; must be greater than 0
     * @throws IllegalArgumentException if {@code radius} or {@code height} is not
     *                                  greater than 0
     */
    public Cone(double radius, double height) {
        this(radius, height, "white", "");
    }

    /**
     * Constructs a {@code Cone} with the specified radius, height, and color.
     *
     * <p>The shape name is set to {@code "Cone"} and label to an empty string.</p>
     *
     * @param radius the radius of the cone's base; must be greater than 0
     * @param height the perpendicular height of the cone; must be greater than 0
     * @param color  the color of the cone; must not be {@code null}
     * @throws IllegalArgumentException if {@code radius} or {@code height} is not
     *                                  greater than 0, or if {@code color} is
     *                                  {@code null}
     */
    public Cone(double radius, double height, String color) {
        this(radius, height, color, "");
    }

    /**
     * Constructs a {@code Cone} with the specified radius, height, color, and label.
     *
     * <p>This is the primary constructor; all other constructors delegate here.
     * {@code slantHeight} and {@code circumference} are derived automatically
     * from the validated {@code radius} and {@code height}.</p>
     *
     * @param radius the radius of the cone's base; must be greater than 0
     * @param height the perpendicular height of the cone; must be greater than 0
     * @param color  the color of the cone; must not be {@code null}
     * @param label  an optional label or tag for this instance; must not be {@code null}
     * @throws IllegalArgumentException if {@code radius} or {@code height} is not
     *                                  greater than 0, or if {@code color} or
     *                                  {@code label} is {@code null}
     */
    public Cone(double radius, double height, String color, String label) {
        super("Cone", color, label);
        validateRadius(radius);
        validateHeight(height);
        this.radius  = radius;
        this.height  = height;
        updateDerivedFields();
    }

    /**
     * Copy constructor. Creates a new {@code Cone} that is an independent copy
     * of the provided instance.
     *
     * @param other the {@code Cone} to copy; must not be {@code null}
     * @throws IllegalArgumentException if {@code other} is {@code null}
     */
    public Cone(Cone other) {
        this(
                requireNonNull(other, "other Cone must not be null").radius,
                other.height,
                other.getColor(),
                other.getLabel()
        );
    }

    // -------------------------------------------------------------------------
    // Abstract method implementations
    // -------------------------------------------------------------------------

    /**
     * Calculates the total surface area of this cone using the formula
     * {@code π * r * (r + l)}, where {@code r} is the radius and {@code l}
     * is the slant height.
     *
     * <p>This is the compact combined form of the base area ({@code π * r²})
     * and the lateral surface area ({@code π * r * l}).</p>
     *
     * <p>Called internally by the concrete {@link Shape3D#getSurfaceArea()} method.</p>
     *
     * @return the total surface area of this cone in square units; always positive
     */
    @Override
    protected double calculateSurfaceArea() {
        return Math.PI * radius * (radius + slantHeight);
    }

    /**
     * Calculates the volume of this cone using the formula
     * {@code (1/3) * π * r² * h}, where {@code r} is the radius and {@code h}
     * is the height.
     *
     * <p>Called internally by the concrete {@link Shape3D#getVolume()} method.</p>
     *
     * @return the volume of this cone in cubic units; always positive
     */
    @Override
    protected double calculateVolume() {
        return (1.0 / 3.0) * Math.PI * radius * radius * height;
    }

    // -------------------------------------------------------------------------
    // Cone-specific methods
    // -------------------------------------------------------------------------

    /**
     * Returns the area of the circular base of this cone.
     *
     * <p>Computed as {@code π * r²}, where {@code r} is the radius.</p>
     *
     * @return the base area in square units; always positive
     */
    public double getBaseArea() {
        return Math.PI * radius * radius;
    }

    /**
     * Returns the lateral (curved) surface area of this cone.
     *
     * <p>The lateral surface is the curved region between the base edge and
     * the apex, computed as {@code π * r * l}, where {@code r} is the radius
     * and {@code l} is the slant height.</p>
     *
     * @return the lateral surface area in square units; always positive
     */
    public double getLateralSurfaceArea() {
        return Math.PI * radius * slantHeight;
    }

    /**
     * Returns the diameter of the circular base of this cone.
     *
     * <p>Computed as {@code 2 * r}.</p>
     *
     * @return the diameter in the same units as the radius; always positive
     */
    public double getDiameter() {
        return 2.0 * radius;
    }

    /**
     * Returns the half-apex angle of this cone in radians.
     *
     * <p>The half-apex angle {@code θ} is the angle between the axis of the
     * cone and any slant edge (generator line), computed as
     * {@code arctan(r / h)}, where {@code r} is the radius and {@code h} is
     * the height.</p>
     *
     * @return the half-apex angle in radians; in the range {@code (0, π/2)}
     */
    public double getHalfApexAngleRadians() {
        return Math.atan(radius / height);
    }

    /**
     * Returns the half-apex angle of this cone in degrees.
     *
     * <p>Converts the result of {@link #getHalfApexAngleRadians()} to degrees
     * using {@link Math#toDegrees(double)}.</p>
     *
     * @return the half-apex angle in degrees; in the range {@code (0°, 90°)}
     */
    public double getHalfApexAngleDegrees() {
        return Math.toDegrees(getHalfApexAngleRadians());
    }

    /**
     * Returns the full apex angle of this cone in radians.
     *
     * <p>The full apex angle is twice the half-apex angle:
     * {@code 2 * arctan(r / h)}.</p>
     *
     * @return the full apex angle in radians; in the range {@code (0, π)}
     */
    public double getFullApexAngleRadians() {
        return 2.0 * getHalfApexAngleRadians();
    }

    /**
     * Returns the full apex angle of this cone in degrees.
     *
     * @return the full apex angle in degrees; in the range {@code (0°, 180°)}
     */
    public double getFullApexAngleDegrees() {
        return Math.toDegrees(getFullApexAngleRadians());
    }

    /**
     * Returns the radius of the largest sphere that fits exactly inside this cone
     * (the inscribed sphere).
     *
     * <p>For a right circular cone with base radius {@code r}, height {@code h},
     * and slant height {@code l}, the inscribed sphere radius is:
     * {@code r_i = (r * h) / (r + l)}.</p>
     *
     * @return the inscribed sphere radius in the same units as the cone's
     *         dimensions; always positive
     */
    public double getInscribedSphereRadius() {
        return (radius * height) / (radius + slantHeight);
    }

    /**
     * Returns the axis-to-centroid distance (height of the centroid above the base).
     *
     * <p>For a solid cone, the centroid lies on the axis at {@code h / 4}
     * above the base.</p>
     *
     * @return the centroid height in the same units as the height; always positive
     */
    public double getCentroidHeight() {
        return height / 4.0;
    }

    /**
     * Determines whether this cone is a right circular cone with an equilateral
     * axial cross-section (i.e., the axial cross-section — a triangle through the
     * apex and base diameter — is equilateral).
     *
     * <p>The axial cross-section is equilateral when the slant height equals the
     * base diameter: {@code l == 2 * r}.</p>
     *
     * @return {@code true} if the axial cross-section is equilateral;
     *         {@code false} otherwise
     */
    public boolean isEquilateral() {
        return Double.compare(slantHeight, getDiameter()) == 0;
    }

    /**
     * Returns a new {@code Cone} scaled uniformly by the given positive factor.
     *
     * <p>Both radius and height are multiplied by {@code scaleFactor}. The new
     * cone inherits the color and label of this instance. The original cone is
     * left unchanged.</p>
     *
     * @param scaleFactor the factor by which to scale both dimensions;
     *                    must be greater than 0
     * @return a new uniformly scaled {@code Cone}
     * @throws IllegalArgumentException if {@code scaleFactor} is not greater than 0
     */
    public Cone scale(double scaleFactor) {
        if (!(scaleFactor > MIN_VALUE)) {
            throw new IllegalArgumentException(
                    "scaleFactor must be greater than 0, but was: " + scaleFactor
            );
        }
        return new Cone(radius * scaleFactor, height * scaleFactor,
                getColor(), getLabel());
    }

    /**
     * Returns a new {@code Cone} with independent scaling applied to the radius
     * and height separately.
     *
     * <p>This is useful for creating frustum-like transformations or changing
     * only the aspect ratio of the cone. The new cone inherits the color and
     * label of this instance. The original cone is left unchanged.</p>
     *
     * @param radiusFactor the factor by which to scale the radius; must be greater than 0
     * @param heightFactor the factor by which to scale the height; must be greater than 0
     * @return a new independently scaled {@code Cone}
     * @throws IllegalArgumentException if either factor is not greater than 0
     */
    public Cone scale(double radiusFactor, double heightFactor) {
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
        return new Cone(radius * radiusFactor, height * heightFactor,
                getColor(), getLabel());
    }

    /**
     * Determines whether a sphere with the given radius fits entirely inside
     * this cone without touching the base or the lateral surface.
     *
     * <p>A sphere fits inside the cone when its radius is strictly less than
     * the inscribed sphere radius: {@code sphereRadius < getInscribedSphereRadius()}.</p>
     *
     * @param sphereRadius the radius of the sphere to test; must be greater than 0
     * @return {@code true} if the sphere fits strictly inside the cone;
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code sphereRadius} is not greater than 0
     */
    public boolean canFitSphere(double sphereRadius) {
        if (!(sphereRadius > MIN_VALUE)) {
            throw new IllegalArgumentException(
                    "sphereRadius must be greater than 0, but was: " + sphereRadius
            );
        }
        return sphereRadius < getInscribedSphereRadius();
    }

    // -------------------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------------------

    /**
     * Returns the radius of this cone's circular base.
     *
     * @return the radius; always greater than 0
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets a new radius for this cone's circular base.
     *
     * <p>Updates {@code slantHeight} and {@code circumference} automatically.</p>
     *
     * @param radius the new radius; must be greater than 0
     * @throws IllegalArgumentException if {@code radius} is not greater than 0
     */
    public void setRadius(double radius) {
        validateRadius(radius);
        this.radius = radius;
        updateDerivedFields();
    }

    /**
     * Returns the perpendicular height of this cone.
     *
     * @return the height; always greater than 0
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets a new perpendicular height for this cone.
     *
     * <p>Updates {@code slantHeight} automatically.</p>
     *
     * @param height the new height; must be greater than 0
     * @throws IllegalArgumentException if {@code height} is not greater than 0
     */
    public void setHeight(double height) {
        validateHeight(height);
        this.height = height;
        updateDerivedFields();
    }

    /**
     * Returns the slant height of this cone.
     *
     * <p>The slant height is the distance from the apex to any point on the base
     * circumference, computed as {@code √(r² + h²)}. This value is derived and
     * read-only; it is updated automatically whenever {@code radius} or
     * {@code height} changes.</p>
     *
     * @return the slant height; always greater than 0
     */
    public double getSlantHeight() {
        return slantHeight;
    }

    /**
     * Returns the circumference of this cone's circular base.
     *
     * <p>Computed as {@code 2 * π * r}. This value is derived and read-only;
     * it is updated automatically whenever {@code radius} changes.</p>
     *
     * @return the base circumference; always greater than 0
     */
    public double getCircumference() {
        return circumference;
    }

    // -------------------------------------------------------------------------
    // Object overrides
    // -------------------------------------------------------------------------

    /**
     * Determines whether this cone is geometrically equal to another object.
     *
     * <p>Two cones are considered equal when they have the same radius and
     * height (using {@link Double#compare} for exact bit-for-bit equality),
     * the same color, and the same label. Derived fields ({@code slantHeight},
     * {@code circumference}) are intentionally excluded since they are fully
     * determined by {@code radius} and {@code height}.</p>
     *
     * @param obj the object to compare with this cone
     * @return {@code true} if {@code obj} is a {@code Cone} with the same
     *         radius, height, color, and label; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cone)) return false;
        Cone other = (Cone) obj;
        return Double.compare(this.radius, other.radius) == 0
                && Double.compare(this.height, other.height) == 0
                && getColor().equals(other.getColor())
                && getLabel().equals(other.getLabel());
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return a hash code value for this cone
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
     * Returns a cone-specific formatted string representation of this instance.
     *
     * <p>The output follows the pattern:</p>
     * <pre>
     * Cone {
     *   name                  = Cone
     *   color                 = red
     *   label                 = myCone
     *   radius                = 3.00 units
     *   height                = 4.00 units
     *   slantHeight           = 5.00 units
     *   diameter              = 6.00 units
     *   circumference         = 18.85 units
     *   halfApexAngle         = 36.87 degrees
     *   fullApexAngle         = 73.74 degrees
     *   isEquilateral         = false
     *   centroidHeight        = 1.00 units
     *   baseArea              = 28.27 square units
     *   lateralSurfaceArea    = 47.12 square units
     *   surfaceArea           = 75.40 square units
     *   inscribedSphereRadius = 1.50 units
     *   volume                = 37.70 cubic units
     * }
     * </pre>
     *
     * @return a formatted, multi-line string summarising all properties and
     *         computed measurements of this cone
     */
    @Override
    public String toString() {
        return String.format(
                "Cone {%n" +
                        "  name                  = %s%n" +
                        "  color                 = %s%n" +
                        "  label                 = %s%n" +
                        "  radius                = %.2f units%n" +
                        "  height                = %.2f units%n" +
                        "  slantHeight           = %.2f units%n" +
                        "  diameter              = %.2f units%n" +
                        "  circumference         = %.2f units%n" +
                        "  halfApexAngle         = %.2f degrees%n" +
                        "  fullApexAngle         = %.2f degrees%n" +
                        "  isEquilateral         = %b%n" +
                        "  centroidHeight        = %.2f units%n" +
                        "  baseArea              = %.2f square units%n" +
                        "  lateralSurfaceArea    = %.2f square units%n" +
                        "  surfaceArea           = %.2f square units%n" +
                        "  inscribedSphereRadius = %.2f units%n" +
                        "  volume                = %.2f cubic units%n" +
                        "}",
                getName(),
                getColor(),
                getLabel().isEmpty() ? "(none)" : getLabel(),
                radius,
                height,
                slantHeight,
                getDiameter(),
                circumference,
                getHalfApexAngleDegrees(),
                getFullApexAngleDegrees(),
                isEquilateral(),
                getCentroidHeight(),
                getBaseArea(),
                getLateralSurfaceArea(),
                getSurfaceArea(),
                getInscribedSphereRadius(),
                getVolume()
        );
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Recomputes and stores the derived fields {@code slantHeight} and
     * {@code circumference} from the current values of {@code radius} and
     * {@code height}.
     *
     * <p>Must be called after any mutation of {@code radius} or {@code height}
     * to keep all fields consistent.</p>
     */
    private void updateDerivedFields() {
        this.slantHeight   = Math.sqrt(radius * radius + height * height);
        this.circumference = 2.0 * Math.PI * radius;
    }

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
