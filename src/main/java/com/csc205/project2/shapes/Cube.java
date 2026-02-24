package com.csc205.project2.shapes;


/**
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: Claude Sonnet 4.6
 * Generation Date: 2/23/26
 *
 * Original Prompt:
 * "Create a Cube class with Properties: sideLength.
 * Extends Shape 3D. Implements abstract methods from ThreeDimensionalShape.
 * Include constructors with validation.
 * Override toString() with shape specific formatting and add any shape specific methods
 * if needed. Please also include proper javadoc documentation."
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
 * Represents a three-dimensional cube defined by a single side length.
 *
 * <p>A cube is a regular hexahedron — all six faces are identical squares,
 * all edges are equal in length, and all interior angles are right angles.
 * This class extends {@link Shape3D} and provides concrete implementations
 * of the abstract calculation methods required by the {@link ThreeDimensionalShape}
 * interface. All geometric formulas are based on standard Euclidean definitions:</p>
 *
 * <ul>
 *   <li><b>Surface Area:</b>   {@code 6 * a²}</li>
 *   <li><b>Volume:</b>         {@code a³}</li>
 *   <li><b>Face Diagonal:</b>  {@code a * √2}</li>
 *   <li><b>Space Diagonal:</b> {@code a * √3}</li>
 *   <li><b>Face Area:</b>      {@code a²}  (area of one square face)</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * Cube c = new Cube(4.0, "red", "myCube");
 * System.out.println(c.getSurfaceArea());  // 96.0
 * System.out.println(c.getVolume());       // 64.0
 * System.out.println(c.getFaceDiagonal()); // 5.656...
 * System.out.println(c.scale(2.0));        // Cube with sideLength 8.0
 * System.out.println(c);                   // formatted output
 * }</pre>
 *
 * @author  Generated
 * @version 1.0
 * @see     Shape3D
 * @see     ThreeDimensionalShape
 */
public class Cube extends Shape3D {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    /** Minimum permitted side length value (exclusive). */
    private static final double MIN_SIDE_LENGTH = 0.0;

    // -------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------

    /**
     * The length of one side (edge) of this cube.
     * Must be strictly greater than {@value #MIN_SIDE_LENGTH}.
     */
    private double sideLength;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Constructs a {@code Cube} with the specified side length using default metadata.
     *
     * <p>The shape name is set to {@code "Cube"}, color to {@code "white"},
     * and label to an empty string.</p>
     *
     * @param sideLength the length of one edge of the cube; must be greater than 0
     * @throws IllegalArgumentException if {@code sideLength} is not greater than 0
     */
    public Cube(double sideLength) {
        this(sideLength, "white", "");
    }

    /**
     * Constructs a {@code Cube} with the specified side length and color.
     *
     * <p>The shape name is set to {@code "Cube"} and label to an empty string.</p>
     *
     * @param sideLength the length of one edge of the cube; must be greater than 0
     * @param color      the color of the cube; must not be {@code null}
     * @throws IllegalArgumentException if {@code sideLength} is not greater than 0,
     *                                  or if {@code color} is {@code null}
     */
    public Cube(double sideLength, String color) {
        this(sideLength, color, "");
    }

    /**
     * Constructs a {@code Cube} with the specified side length, color, and label.
     *
     * <p>This is the primary constructor; all other constructors delegate here.</p>
     *
     * @param sideLength the length of one edge of the cube; must be greater than 0
     * @param color      the color of the cube; must not be {@code null}
     * @param label      an optional label or tag for this instance; must not be {@code null}
     * @throws IllegalArgumentException if {@code sideLength} is not greater than 0,
     *                                  or if {@code color} or {@code label} is {@code null}
     */
    public Cube(double sideLength, String color, String label) {
        super("Cube", color, label);
        validateSideLength(sideLength);
        this.sideLength = sideLength;
    }

    /**
     * Copy constructor. Creates a new {@code Cube} that is an independent copy
     * of the provided instance.
     *
     * @param other the {@code Cube} to copy; must not be {@code null}
     * @throws IllegalArgumentException if {@code other} is {@code null}
     */
    public Cube(Cube other) {
        this(
                requireNonNull(other, "other Cube must not be null").sideLength,
                other.getColor(),
                other.getLabel()
        );
    }

    // -------------------------------------------------------------------------
    // Abstract method implementations
    // -------------------------------------------------------------------------

    /**
     * Calculates the total surface area of this cube using the formula
     * {@code 6 * a²}, where {@code a} is the side length.
     *
     * <p>A cube has six identical square faces, each with area {@code a²}.</p>
     * <p>Called internally by the concrete {@link Shape3D#getSurfaceArea()} method.</p>
     *
     * @return the total surface area of this cube in square units; always positive
     */
    @Override
    protected double calculateSurfaceArea() {
        return 6.0 * sideLength * sideLength;
    }

    /**
     * Calculates the volume of this cube using the formula {@code a³},
     * where {@code a} is the side length.
     *
     * <p>Called internally by the concrete {@link Shape3D#getVolume()} method.</p>
     *
     * @return the volume of this cube in cubic units; always positive
     */
    @Override
    protected double calculateVolume() {
        return sideLength * sideLength * sideLength;
    }

    // -------------------------------------------------------------------------
    // Cube-specific methods
    // -------------------------------------------------------------------------

    /**
     * Returns the area of one square face of this cube.
     *
     * <p>Computed as {@code a²}, where {@code a} is the side length.</p>
     *
     * @return the area of a single face in square units; always positive
     */
    public double getFaceArea() {
        return sideLength * sideLength;
    }

    /**
     * Returns the length of a face diagonal of this cube.
     *
     * <p>A face diagonal connects two opposite corners of one square face.
     * It is computed as {@code a * √2}, where {@code a} is the side length.</p>
     *
     * @return the face diagonal length in the same units as the side length;
     *         always positive
     */
    public double getFaceDiagonal() {
        return sideLength * Math.sqrt(2.0);
    }

    /**
     * Returns the length of the space diagonal of this cube.
     *
     * <p>A space diagonal connects two opposite corners of the cube, passing
     * through its interior. It is computed as {@code a * √3}, where {@code a}
     * is the side length.</p>
     *
     * @return the space diagonal length in the same units as the side length;
     *         always positive
     */
    public double getSpaceDiagonal() {
        return sideLength * Math.sqrt(3.0);
    }

    /**
     * Returns the total edge length (perimeter of all edges) of this cube.
     *
     * <p>A cube has 12 equal edges, so the total edge length is {@code 12 * a}.</p>
     *
     * @return the total length of all 12 edges in the same units as the side length;
     *         always positive
     */
    public double getTotalEdgeLength() {
        return 12.0 * sideLength;
    }

    /**
     * Returns a new {@code Cube} scaled by the given positive factor.
     *
     * <p>The new cube inherits the color and label of this instance but has a
     * side length equal to {@code this.sideLength * scaleFactor}. The original
     * cube is left unchanged.</p>
     *
     * @param scaleFactor the factor by which to scale the side length;
     *                    must be greater than 0
     * @return a new {@code Cube} whose side length is {@code sideLength * scaleFactor}
     * @throws IllegalArgumentException if {@code scaleFactor} is not greater than 0
     */
    public Cube scale(double scaleFactor) {
        if (!(scaleFactor > MIN_SIDE_LENGTH)) {
            throw new IllegalArgumentException(
                    "scaleFactor must be greater than 0, but was: " + scaleFactor
            );
        }
        return new Cube(sideLength * scaleFactor, getColor(), getLabel());
    }

    /**
     * Determines whether a sphere with the given radius can fit entirely inside
     * this cube without touching the faces.
     *
     * <p>The largest sphere that fits inside a cube has a radius equal to half
     * the side length (the inscribed sphere). A sphere fits if its radius is
     * strictly less than {@code sideLength / 2}.</p>
     *
     * @param radius the radius of the sphere to test; must be greater than 0
     * @return {@code true} if the sphere fits strictly inside the cube;
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code radius} is not greater than 0
     */
    public boolean canFitSphere(double radius) {
        if (!(radius > MIN_SIDE_LENGTH)) {
            throw new IllegalArgumentException(
                    "radius must be greater than 0, but was: " + radius
            );
        }
        return radius < sideLength / 2.0;
    }

    /**
     * Returns the radius of the largest sphere that fits exactly inside this cube
     * (the inscribed sphere).
     *
     * <p>For a cube with side length {@code a}, the inscribed sphere has radius
     * {@code a / 2}.</p>
     *
     * @return the inscribed sphere radius in the same units as the side length;
     *         always positive
     */
    public double getInscribedSphereRadius() {
        return sideLength / 2.0;
    }

    /**
     * Returns the radius of the smallest sphere that completely contains this cube
     * (the circumscribed sphere).
     *
     * <p>For a cube with side length {@code a}, the circumscribed sphere has radius
     * equal to half the space diagonal: {@code (a * √3) / 2}.</p>
     *
     * @return the circumscribed sphere radius in the same units as the side length;
     *         always positive
     */
    public double getCircumscribedSphereRadius() {
        return getSpaceDiagonal() / 2.0;
    }

    // -------------------------------------------------------------------------
    // Getter and setter
    // -------------------------------------------------------------------------

    /**
     * Returns the side length of this cube.
     *
     * @return the side length; always greater than 0
     */
    public double getSideLength() {
        return sideLength;
    }

    /**
     * Sets a new side length for this cube.
     *
     * @param sideLength the new side length; must be greater than 0
     * @throws IllegalArgumentException if {@code sideLength} is not greater than 0
     */
    public void setSideLength(double sideLength) {
        validateSideLength(sideLength);
        this.sideLength = sideLength;
    }

    // -------------------------------------------------------------------------
    // Object overrides
    // -------------------------------------------------------------------------

    /**
     * Determines whether this cube is geometrically equal to another object.
     *
     * <p>Two cubes are considered equal when they have the same side length
     * (using {@link Double#compare} for exact bit-for-bit equality), the same
     * color, and the same label.</p>
     *
     * @param obj the object to compare with this cube
     * @return {@code true} if {@code obj} is a {@code Cube} with the same
     *         side length, color, and label; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cube)) return false;
        Cube other = (Cube) obj;
        return Double.compare(this.sideLength, other.sideLength) == 0
                && getColor().equals(other.getColor())
                && getLabel().equals(other.getLabel());
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return a hash code value for this cube
     */
    @Override
    public int hashCode() {
        int result = Double.hashCode(sideLength);
        result = 31 * result + getColor().hashCode();
        result = 31 * result + getLabel().hashCode();
        return result;
    }

    /**
     * Returns a cube-specific formatted string representation of this instance.
     *
     * <p>The output follows the pattern:</p>
     * <pre>
     * Cube {
     *   name                     = Cube
     *   color                    = red
     *   label                    = myCube
     *   sideLength               = 4.00 units
     *   faceArea                 = 16.00 square units
     *   faceDiagonal             = 5.66 units
     *   spaceDiagonal            = 6.93 units
     *   totalEdgeLength          = 48.00 units
     *   inscribedSphereRadius    = 2.00 units
     *   circumscribedSphereRadius= 3.46 units
     *   surfaceArea              = 96.00 square units
     *   volume                   = 64.00 cubic units
     * }
     * </pre>
     *
     * @return a formatted, multi-line string summarising all properties and
     *         computed measurements of this cube
     */
    @Override
    public String toString() {
        return String.format(
                "Cube {%n" +
                        "  name                      = %s%n" +
                        "  color                     = %s%n" +
                        "  label                     = %s%n" +
                        "  sideLength                = %.2f units%n" +
                        "  faceArea                  = %.2f square units%n" +
                        "  faceDiagonal              = %.2f units%n" +
                        "  spaceDiagonal             = %.2f units%n" +
                        "  totalEdgeLength           = %.2f units%n" +
                        "  inscribedSphereRadius     = %.2f units%n" +
                        "  circumscribedSphereRadius = %.2f units%n" +
                        "  surfaceArea               = %.2f square units%n" +
                        "  volume                    = %.2f cubic units%n" +
                        "}",
                getName(),
                getColor(),
                getLabel().isEmpty() ? "(none)" : getLabel(),
                sideLength,
                getFaceArea(),
                getFaceDiagonal(),
                getSpaceDiagonal(),
                getTotalEdgeLength(),
                getInscribedSphereRadius(),
                getCircumscribedSphereRadius(),
                getSurfaceArea(),
                getVolume()
        );
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Validates that the provided side length is strictly greater than zero.
     *
     * @param sideLength the value to validate
     * @throws IllegalArgumentException if {@code sideLength} is not greater than 0
     */
    private static void validateSideLength(double sideLength) {
        // !(sideLength > MIN_SIDE_LENGTH) rejects NaN, negative values, and zero
        // because any comparison with NaN evaluates to false.
        if (!(sideLength > MIN_SIDE_LENGTH)) {
            throw new IllegalArgumentException(
                    "sideLength must be greater than 0, but was: " + sideLength
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