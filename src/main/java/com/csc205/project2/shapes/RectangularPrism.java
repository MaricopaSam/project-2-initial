package com.csc205.project2.shapes;


/**
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: Claude Sonnet 4.6
 * Generation Date: 2/23/26
 *
 * "Create a RectangularPrism class with Properties: length, width, height. Extends Shape 3D.
 * Implements abstract methods from ThreeDimensionalShape.
 * Include constructors with validation.
 * Override toString() with shape specific formatting and add any shape specific methods
 * if needed. Please also include proper javadoc documentation."
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
 * - Volume formula verified against: https://www.mathsisfun.com/geometry/cuboids-rectangular-prisms.html
 * - Surface area formula verified against: Same as above
 */
/**
 /**
 * Represents a three-dimensional rectangular prism (cuboid) defined by a
 * length, width, and height.
 *
 * <p>A rectangular prism has six rectangular faces arranged in three pairs of
 * congruent, parallel faces. All interior angles are right angles. When all three
 * dimensions are equal, the shape degenerates to a cube. This class extends
 * {@link Shape3D} and provides concrete implementations of the abstract calculation
 * methods required by the {@link ThreeDimensionalShape} interface. All geometric
 * formulas are based on standard Euclidean definitions:</p>
 *
 * <ul>
 *   <li><b>Total Surface Area:</b>  {@code 2 * (l*w + l*h + w*h)}</li>
 *   <li><b>Volume:</b>              {@code l * w * h}</li>
 *   <li><b>Space Diagonal:</b>      {@code √(l² + w² + h²)}</li>
 *   <li><b>Face Diagonal (lh):</b>  {@code √(l² + h²)}</li>
 *   <li><b>Face Diagonal (wh):</b>  {@code √(w² + h²)}</li>
 *   <li><b>Face Diagonal (lw):</b>  {@code √(l² + w²)}</li>
 *   <li><b>Total Edge Length:</b>   {@code 4 * (l + w + h)}</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * RectangularPrism rp = new RectangularPrism(4.0, 3.0, 5.0, "orange", "myPrism");
 * System.out.println(rp.getSurfaceArea());          // 94.0
 * System.out.println(rp.getVolume());               // 60.0
 * System.out.println(rp.getSpaceDiagonal());        // 7.071...
 * System.out.println(rp.getLargestFaceArea());      // 20.0
 * System.out.println(rp.isCube());                  // false
 * System.out.println(rp.scale(2.0));                // RectangularPrism l=8, w=6, h=10
 * System.out.println(rp);                           // formatted output
 * }</pre>
 *
 * @author  Generated
 * @version 1.0
 * @see     Shape3D
 * @see     ThreeDimensionalShape
 */
public class RectangularPrism extends Shape3D {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    /** Minimum permitted value for length, width, and height (exclusive). */
    private static final double MIN_VALUE = 0.0;

    // -------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------

    /**
     * The length of this rectangular prism.
     * Must be strictly greater than {@value #MIN_VALUE}.
     */
    private double length;

    /**
     * The width of this rectangular prism.
     * Must be strictly greater than {@value #MIN_VALUE}.
     */
    private double width;

    /**
     * The height of this rectangular prism (perpendicular distance between the
     * two length-width faces).
     * Must be strictly greater than {@value #MIN_VALUE}.
     */
    private double height;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Constructs a {@code RectangularPrism} with the specified dimensions using
     * default metadata.
     *
     * <p>The shape name is set to {@code "RectangularPrism"}, color to
     * {@code "white"}, and label to an empty string.</p>
     *
     * @param length the length of the prism; must be greater than 0
     * @param width  the width of the prism; must be greater than 0
     * @param height the height of the prism; must be greater than 0
     * @throws IllegalArgumentException if any dimension is not greater than 0
     */
    public RectangularPrism(double length, double width, double height) {
        this(length, width, height, "white", "");
    }

    /**
     * Constructs a {@code RectangularPrism} with the specified dimensions and color.
     *
     * <p>The shape name is set to {@code "RectangularPrism"} and label to an
     * empty string.</p>
     *
     * @param length the length of the prism; must be greater than 0
     * @param width  the width of the prism; must be greater than 0
     * @param height the height of the prism; must be greater than 0
     * @param color  the color of the prism; must not be {@code null}
     * @throws IllegalArgumentException if any dimension is not greater than 0,
     *                                  or if {@code color} is {@code null}
     */
    public RectangularPrism(double length, double width, double height, String color) {
        this(length, width, height, color, "");
    }

    /**
     * Constructs a {@code RectangularPrism} with the specified dimensions, color,
     * and label.
     *
     * <p>This is the primary constructor; all other constructors delegate here.</p>
     *
     * @param length the length of the prism; must be greater than 0
     * @param width  the width of the prism; must be greater than 0
     * @param height the height of the prism; must be greater than 0
     * @param color  the color of the prism; must not be {@code null}
     * @param label  an optional label or tag for this instance; must not be {@code null}
     * @throws IllegalArgumentException if any dimension is not greater than 0,
     *                                  or if {@code color} or {@code label} is {@code null}
     */
    public RectangularPrism(double length, double width, double height,
                            String color, String label) {
        super("RectangularPrism", color, label);
        validateDimension(length, "length");
        validateDimension(width,  "width");
        validateDimension(height, "height");
        this.length = length;
        this.width  = width;
        this.height = height;
    }

    /**
     * Copy constructor. Creates a new {@code RectangularPrism} that is an
     * independent copy of the provided instance.
     *
     * @param other the {@code RectangularPrism} to copy; must not be {@code null}
     * @throws IllegalArgumentException if {@code other} is {@code null}
     */
    public RectangularPrism(RectangularPrism other) {
        this(
                requireNonNull(other, "other RectangularPrism must not be null").length,
                other.width,
                other.height,
                other.getColor(),
                other.getLabel()
        );
    }

    // -------------------------------------------------------------------------
    // Abstract method implementations
    // -------------------------------------------------------------------------

    /**
     * Calculates the total surface area of this rectangular prism using the formula
     * {@code 2 * (l*w + l*h + w*h)}, where {@code l} is the length, {@code w} is
     * the width, and {@code h} is the height.
     *
     * <p>This sums the areas of all three pairs of opposite rectangular faces.</p>
     * <p>Called internally by the concrete {@link Shape3D#getSurfaceArea()} method.</p>
     *
     * @return the total surface area of this prism in square units; always positive
     */
    @Override
    protected double calculateSurfaceArea() {
        return 2.0 * (length * width + length * height + width * height);
    }

    /**
     * Calculates the volume of this rectangular prism using the formula
     * {@code l * w * h}, where {@code l} is the length, {@code w} is the width,
     * and {@code h} is the height.
     *
     * <p>Called internally by the concrete {@link Shape3D#getVolume()} method.</p>
     *
     * @return the volume of this prism in cubic units; always positive
     */
    @Override
    protected double calculateVolume() {
        return length * width * height;
    }

    // -------------------------------------------------------------------------
    // RectangularPrism-specific methods
    // -------------------------------------------------------------------------

    /**
     * Returns the area of the length-width face (the base face) of this prism.
     *
     * <p>Computed as {@code l * w}.</p>
     *
     * @return the base face area in square units; always positive
     */
    public double getBaseFaceArea() {
        return length * width;
    }

    /**
     * Returns the area of the length-height face of this prism.
     *
     * <p>Computed as {@code l * h}.</p>
     *
     * @return the length-height face area in square units; always positive
     */
    public double getLengthHeightFaceArea() {
        return length * height;
    }

    /**
     * Returns the area of the width-height face of this prism.
     *
     * <p>Computed as {@code w * h}.</p>
     *
     * @return the width-height face area in square units; always positive
     */
    public double getWidthHeightFaceArea() {
        return width * height;
    }

    /**
     * Returns the area of the largest face of this rectangular prism.
     *
     * <p>Evaluates all three distinct face areas ({@code l*w}, {@code l*h},
     * {@code w*h}) and returns the greatest value.</p>
     *
     * @return the largest face area in square units; always positive
     */
    public double getLargestFaceArea() {
        return Math.max(getBaseFaceArea(),
                Math.max(getLengthHeightFaceArea(), getWidthHeightFaceArea()));
    }

    /**
     * Returns the area of the smallest face of this rectangular prism.
     *
     * <p>Evaluates all three distinct face areas ({@code l*w}, {@code l*h},
     * {@code w*h}) and returns the smallest value.</p>
     *
     * @return the smallest face area in square units; always positive
     */
    public double getSmallestFaceArea() {
        return Math.min(getBaseFaceArea(),
                Math.min(getLengthHeightFaceArea(), getWidthHeightFaceArea()));
    }

    /**
     * Returns the space (main) diagonal of this rectangular prism.
     *
     * <p>The space diagonal connects two opposite vertices of the prism, passing
     * through its interior. It is computed as {@code √(l² + w² + h²)}.</p>
     *
     * @return the space diagonal length in the same units as the dimensions;
     *         always positive
     */
    public double getSpaceDiagonal() {
        return Math.sqrt(length * length + width * width + height * height);
    }

    /**
     * Returns the diagonal of the length-width face of this prism.
     *
     * <p>Computed as {@code √(l² + w²)}.</p>
     *
     * @return the length-width face diagonal in the same units as the dimensions;
     *         always positive
     */
    public double getLengthWidthFaceDiagonal() {
        return Math.sqrt(length * length + width * width);
    }

    /**
     * Returns the diagonal of the length-height face of this prism.
     *
     * <p>Computed as {@code √(l² + h²)}.</p>
     *
     * @return the length-height face diagonal in the same units as the dimensions;
     *         always positive
     */
    public double getLengthHeightFaceDiagonal() {
        return Math.sqrt(length * length + height * height);
    }

    /**
     * Returns the diagonal of the width-height face of this prism.
     *
     * <p>Computed as {@code √(w² + h²)}.</p>
     *
     * @return the width-height face diagonal in the same units as the dimensions;
     *         always positive
     */
    public double getWidthHeightFaceDiagonal() {
        return Math.sqrt(width * width + height * height);
    }

    /**
     * Returns the total length of all edges of this rectangular prism.
     *
     * <p>A rectangular prism has 12 edges: 4 of each distinct length
     * ({@code l}, {@code w}, {@code h}). The total is {@code 4 * (l + w + h)}.</p>
     *
     * @return the total edge length in the same units as the dimensions;
     *         always positive
     */
    public double getTotalEdgeLength() {
        return 4.0 * (length + width + height);
    }

    /**
     * Returns the perimeter of the base (length-width) face of this prism.
     *
     * <p>Computed as {@code 2 * (l + w)}.</p>
     *
     * @return the base perimeter in the same units as the dimensions; always positive
     */
    public double getBasePerimeter() {
        return 2.0 * (length + width);
    }

    /**
     * Determines whether this rectangular prism is a cube.
     *
     * <p>A rectangular prism is a cube when all three dimensions are exactly equal:
     * {@code l == w == h}. {@link Double#compare} is used for exact bit-for-bit
     * equality.</p>
     *
     * @return {@code true} if all three dimensions are equal; {@code false} otherwise
     */
    public boolean isCube() {
        return Double.compare(length, width) == 0
                && Double.compare(width, height) == 0;
    }

    /**
     * Determines whether this rectangular prism is a square prism (at least two
     * dimensions are equal).
     *
     * <p>A square prism has a square cross-section in at least one axis:
     * {@code l == w}, {@code l == h}, or {@code w == h}.</p>
     *
     * @return {@code true} if any two dimensions are equal; {@code false} otherwise
     */
    public boolean isSquarePrism() {
        return Double.compare(length, width)  == 0
                || Double.compare(length, height) == 0
                || Double.compare(width,  height) == 0;
    }

    /**
     * Returns a new {@code RectangularPrism} scaled uniformly by the given factor.
     *
     * <p>All three dimensions are multiplied by {@code scaleFactor}. The new prism
     * inherits the color and label of this instance. The original prism is left
     * unchanged.</p>
     *
     * @param scaleFactor the factor by which to scale all dimensions;
     *                    must be greater than 0
     * @return a new uniformly scaled {@code RectangularPrism}
     * @throws IllegalArgumentException if {@code scaleFactor} is not greater than 0
     */
    public RectangularPrism scale(double scaleFactor) {
        if (!(scaleFactor > MIN_VALUE)) {
            throw new IllegalArgumentException(
                    "scaleFactor must be greater than 0, but was: " + scaleFactor
            );
        }
        return new RectangularPrism(length * scaleFactor, width  * scaleFactor,
                height * scaleFactor, getColor(), getLabel());
    }

    /**
     * Returns a new {@code RectangularPrism} with independent scaling applied to
     * each dimension separately.
     *
     * <p>This is useful when stretching or compressing only one or two axes.
     * The new prism inherits the color and label of this instance.
     * The original prism is left unchanged.</p>
     *
     * @param lengthFactor the factor by which to scale the length; must be greater than 0
     * @param widthFactor  the factor by which to scale the width;  must be greater than 0
     * @param heightFactor the factor by which to scale the height; must be greater than 0
     * @return a new independently scaled {@code RectangularPrism}
     * @throws IllegalArgumentException if any factor is not greater than 0
     */
    public RectangularPrism scale(double lengthFactor, double widthFactor,
                                  double heightFactor) {
        validateDimension(lengthFactor, "lengthFactor");
        validateDimension(widthFactor,  "widthFactor");
        validateDimension(heightFactor, "heightFactor");
        return new RectangularPrism(length * lengthFactor, width  * widthFactor,
                height * heightFactor, getColor(), getLabel());
    }

    /**
     * Determines whether a sphere with the given radius fits entirely inside
     * this rectangular prism without touching any face.
     *
     * <p>A sphere of radius {@code r_s} fits inside the prism when its diameter
     * is strictly less than each of the three dimensions:
     * {@code 2 * r_s < l}, {@code 2 * r_s < w}, and {@code 2 * r_s < h}.</p>
     *
     * @param sphereRadius the radius of the sphere to test; must be greater than 0
     * @return {@code true} if the sphere fits strictly inside the prism;
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code sphereRadius} is not greater than 0
     */
    public boolean canFitSphere(double sphereRadius) {
        validateDimension(sphereRadius, "sphereRadius");
        double diameter = 2.0 * sphereRadius;
        return diameter < length && diameter < width && diameter < height;
    }

    /**
     * Returns the radius of the largest sphere that fits exactly inside this prism.
     *
     * <p>The inscribed sphere is constrained by the smallest dimension. Its radius
     * is {@code min(l, w, h) / 2}.</p>
     *
     * @return the inscribed sphere radius in the same units as the dimensions;
     *         always positive
     */
    public double getInscribedSphereRadius() {
        return Math.min(length, Math.min(width, height)) / 2.0;
    }

    // -------------------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------------------

    /**
     * Returns the length of this rectangular prism.
     *
     * @return the length; always greater than 0
     */
    public double getLength() {
        return length;
    }

    /**
     * Sets a new length for this rectangular prism.
     *
     * @param length the new length; must be greater than 0
     * @throws IllegalArgumentException if {@code length} is not greater than 0
     */
    public void setLength(double length) {
        validateDimension(length, "length");
        this.length = length;
    }

    /**
     * Returns the width of this rectangular prism.
     *
     * @return the width; always greater than 0
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets a new width for this rectangular prism.
     *
     * @param width the new width; must be greater than 0
     * @throws IllegalArgumentException if {@code width} is not greater than 0
     */
    public void setWidth(double width) {
        validateDimension(width, "width");
        this.width = width;
    }

    /**
     * Returns the height of this rectangular prism.
     *
     * @return the height; always greater than 0
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets a new height for this rectangular prism.
     *
     * @param height the new height; must be greater than 0
     * @throws IllegalArgumentException if {@code height} is not greater than 0
     */
    public void setHeight(double height) {
        validateDimension(height, "height");
        this.height = height;
    }

    // -------------------------------------------------------------------------
    // Object overrides
    // -------------------------------------------------------------------------

    /**
     * Determines whether this rectangular prism is geometrically equal to another
     * object.
     *
     * <p>Two rectangular prisms are considered equal when they have the same length,
     * width, and height (using {@link Double#compare} for exact bit-for-bit
     * equality), the same color, and the same label.</p>
     *
     * @param obj the object to compare with this prism
     * @return {@code true} if {@code obj} is a {@code RectangularPrism} with the
     *         same dimensions, color, and label; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RectangularPrism)) return false;
        RectangularPrism other = (RectangularPrism) obj;
        return Double.compare(this.length, other.length) == 0
                && Double.compare(this.width,  other.width)  == 0
                && Double.compare(this.height, other.height) == 0
                && getColor().equals(other.getColor())
                && getLabel().equals(other.getLabel());
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return a hash code value for this rectangular prism
     */
    @Override
    public int hashCode() {
        int result = Double.hashCode(length);
        result = 31 * result + Double.hashCode(width);
        result = 31 * result + Double.hashCode(height);
        result = 31 * result + getColor().hashCode();
        result = 31 * result + getLabel().hashCode();
        return result;
    }

    /**
     * Returns a prism-specific formatted string representation of this instance.
     *
     * <p>The output follows the pattern:</p>
     * <pre>
     * RectangularPrism {
     *   name                      = RectangularPrism
     *   color                     = orange
     *   label                     = myPrism
     *   length                    = 4.00 units
     *   width                     = 3.00 units
     *   height                    = 5.00 units
     *   isCube                    = false
     *   isSquarePrism             = false
     *   baseFaceArea              = 12.00 square units
     *   lengthHeightFaceArea      = 20.00 square units
     *   widthHeightFaceArea       = 15.00 square units
     *   largestFaceArea           = 20.00 square units
     *   smallestFaceArea          = 12.00 square units
     *   basePerimeter             = 14.00 units
     *   totalEdgeLength           = 48.00 units
     *   lengthWidthFaceDiagonal   = 5.00 units
     *   lengthHeightFaceDiagonal  = 6.40 units
     *   widthHeightFaceDiagonal   = 5.83 units
     *   spaceDiagonal             = 7.07 units
     *   inscribedSphereRadius     = 1.50 units
     *   surfaceArea               = 94.00 square units
     *   volume                    = 60.00 cubic units
     * }
     * </pre>
     *
     * @return a formatted, multi-line string summarising all properties and
     *         computed measurements of this rectangular prism
     */
    @Override
    public String toString() {
        return String.format(
                "RectangularPrism {%n" +
                        "  name                     = %s%n" +
                        "  color                    = %s%n" +
                        "  label                    = %s%n" +
                        "  length                   = %.2f units%n" +
                        "  width                    = %.2f units%n" +
                        "  height                   = %.2f units%n" +
                        "  isCube                   = %b%n" +
                        "  isSquarePrism            = %b%n" +
                        "  baseFaceArea             = %.2f square units%n" +
                        "  lengthHeightFaceArea     = %.2f square units%n" +
                        "  widthHeightFaceArea      = %.2f square units%n" +
                        "  largestFaceArea          = %.2f square units%n" +
                        "  smallestFaceArea         = %.2f square units%n" +
                        "  basePerimeter            = %.2f units%n" +
                        "  totalEdgeLength          = %.2f units%n" +
                        "  lengthWidthFaceDiagonal  = %.2f units%n" +
                        "  lengthHeightFaceDiagonal = %.2f units%n" +
                        "  widthHeightFaceDiagonal  = %.2f units%n" +
                        "  spaceDiagonal            = %.2f units%n" +
                        "  inscribedSphereRadius    = %.2f units%n" +
                        "  surfaceArea              = %.2f square units%n" +
                        "  volume                   = %.2f cubic units%n" +
                        "}",
                getName(),
                getColor(),
                getLabel().isEmpty() ? "(none)" : getLabel(),
                length,
                width,
                height,
                isCube(),
                isSquarePrism(),
                getBaseFaceArea(),
                getLengthHeightFaceArea(),
                getWidthHeightFaceArea(),
                getLargestFaceArea(),
                getSmallestFaceArea(),
                getBasePerimeter(),
                getTotalEdgeLength(),
                getLengthWidthFaceDiagonal(),
                getLengthHeightFaceDiagonal(),
                getWidthHeightFaceDiagonal(),
                getSpaceDiagonal(),
                getInscribedSphereRadius(),
                getSurfaceArea(),
                getVolume()
        );
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Validates that the provided dimension value is strictly greater than zero.
     *
     * <p>A single helper is used for all three dimensions and for scale factors,
     * with the {@code fieldName} parameter used to produce a meaningful error
     * message identifying which field failed validation.</p>
     *
     * @param value     the value to validate
     * @param fieldName the name of the field being validated, used in the exception message
     * @throws IllegalArgumentException if {@code value} is not greater than 0
     */
    private static void validateDimension(double value, String fieldName) {
        // !(value > MIN_VALUE) rejects NaN, negative values, and zero because
        // any comparison with NaN evaluates to false in Java.
        if (!(value > MIN_VALUE)) {
            throw new IllegalArgumentException(
                    fieldName + " must be greater than 0, but was: " + value
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
