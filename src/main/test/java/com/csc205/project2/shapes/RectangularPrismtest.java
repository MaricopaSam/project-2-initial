package com.csc205.project2.shapes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for the {@link RectangularPrism} class.
 *
 * <p>Test categories:</p>
 * <ul>
 *   <li>Constructor tests — valid and invalid inputs, copy constructor</li>
 *   <li>Getter / setter tests — normal mutations and validation</li>
 *   <li>Calculation tests — surface area, volume and prism-specific methods
 *       verified against known mathematical results</li>
 *   <li>Boundary tests — zero, very small, and very large dimension values</li>
 *   <li>Input validation tests — negative values and null inputs</li>
 *   <li>Inheritance / polymorphism tests — behaviour through {@link Shape3D}
 *       and {@link ThreeDimensionalShape} references</li>
 * </ul>
 *
 * <p><b>Boundary decision:</b> Any dimension that is zero or non-positive is
 * physically meaningless and throws {@link IllegalArgumentException}.</p>
 */
@DisplayName("RectangularPrism Tests")
class RectangularPrismTest {

    private static final double DELTA = 1e-9;

    // =========================================================================
    // Constructor Tests
    // =========================================================================

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Three-arg constructor sets dimensions and defaults")
        void threeArgConstructor() {
            RectangularPrism rp = new RectangularPrism(4.0, 3.0, 5.0);
            assertEquals(4.0,              rp.getLength(), DELTA);
            assertEquals(3.0,              rp.getWidth(),  DELTA);
            assertEquals(5.0,              rp.getHeight(), DELTA);
            assertEquals("RectangularPrism", rp.getName());
            assertEquals("white",          rp.getColor());
            assertEquals("",               rp.getLabel());
        }

        @Test
        @DisplayName("Four-arg constructor sets dimensions and color")
        void fourArgConstructor() {
            RectangularPrism rp = new RectangularPrism(4.0, 3.0, 5.0, "orange");
            assertEquals("orange", rp.getColor());
            assertEquals("",       rp.getLabel());
        }

        @Test
        @DisplayName("Five-arg constructor sets all fields")
        void fiveArgConstructor() {
            RectangularPrism rp = new RectangularPrism(4.0, 3.0, 5.0, "orange", "myPrism");
            assertEquals(4.0,      rp.getLength(), DELTA);
            assertEquals(3.0,      rp.getWidth(),  DELTA);
            assertEquals(5.0,      rp.getHeight(), DELTA);
            assertEquals("orange", rp.getColor());
            assertEquals("myPrism",rp.getLabel());
        }

        @Test
        @DisplayName("Copy constructor creates independent copy")
        void copyConstructor() {
            RectangularPrism original = new RectangularPrism(4.0, 3.0, 5.0, "blue", "orig");
            RectangularPrism copy     = new RectangularPrism(original);

            assertEquals(original.getLength(), copy.getLength(), DELTA);
            assertEquals(original.getWidth(),  copy.getWidth(),  DELTA);
            assertEquals(original.getHeight(), copy.getHeight(), DELTA);

            // Mutating copy must not affect original
            copy.setLength(99.0);
            assertEquals(4.0, original.getLength(), DELTA);
        }

        @Test
        @DisplayName("Copy constructor with null throws IllegalArgumentException")
        void copyConstructorNullThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism(null));
        }

        @Test
        @DisplayName("Name is always 'RectangularPrism' regardless of constructor")
        void nameIsAlwaysRectangularPrism() {
            assertEquals("RectangularPrism",
                    new RectangularPrism(1.0, 1.0, 1.0).getName());
        }
    }

    // =========================================================================
    // Getter and Setter Tests
    // =========================================================================

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        private RectangularPrism rp;

        @BeforeEach
        void setUp() {
            rp = new RectangularPrism(4.0, 3.0, 5.0, "orange", "p1");
        }

        @Test
        @DisplayName("setLength updates the value")
        void setLengthUpdates() {
            rp.setLength(10.0);
            assertEquals(10.0, rp.getLength(), DELTA);
        }

        @Test
        @DisplayName("setLength with zero throws IllegalArgumentException")
        void setLengthZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> rp.setLength(0.0));
        }

        @Test
        @DisplayName("setLength with negative throws IllegalArgumentException")
        void setLengthNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> rp.setLength(-1.0));
        }

        @Test
        @DisplayName("setWidth updates the value")
        void setWidthUpdates() {
            rp.setWidth(6.0);
            assertEquals(6.0, rp.getWidth(), DELTA);
        }

        @Test
        @DisplayName("setWidth with zero throws IllegalArgumentException")
        void setWidthZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> rp.setWidth(0.0));
        }

        @Test
        @DisplayName("setHeight updates the value")
        void setHeightUpdates() {
            rp.setHeight(8.0);
            assertEquals(8.0, rp.getHeight(), DELTA);
        }

        @Test
        @DisplayName("setHeight with zero throws IllegalArgumentException")
        void setHeightZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> rp.setHeight(0.0));
        }

        @Test
        @DisplayName("setColor updates the color")
        void setColorUpdates() {
            rp.setColor("navy");
            assertEquals("navy", rp.getColor());
        }

        @Test
        @DisplayName("setColor with null throws IllegalArgumentException")
        void setColorNullThrows() {
            assertThrows(IllegalArgumentException.class, () -> rp.setColor(null));
        }

        @Test
        @DisplayName("setLabel updates the label")
        void setLabelUpdates() {
            rp.setLabel("updated");
            assertEquals("updated", rp.getLabel());
        }

        @Test
        @DisplayName("setLabel with null throws IllegalArgumentException")
        void setLabelNullThrows() {
            assertThrows(IllegalArgumentException.class, () -> rp.setLabel(null));
        }
    }

    // =========================================================================
    // Calculation Tests
    // =========================================================================

    @Nested
    @DisplayName("Calculation Tests")
    class CalculationTests {

        // l=4, w=3, h=5 reference prism
        private final RectangularPrism ref = new RectangularPrism(4.0, 3.0, 5.0);

        @Test
        @DisplayName("Surface area: 2*(l*w + l*h + w*h) = 2*(12+20+15) = 94")
        void surfaceArea() {
            assertEquals(94.0, ref.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Volume: l*w*h = 4*3*5 = 60")
        void volume() {
            assertEquals(60.0, ref.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Unit cube surface area: 6")
        void unitCubeSurfaceArea() {
            assertEquals(6.0, new RectangularPrism(1.0, 1.0, 1.0).getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Unit cube volume: 1")
        void unitCubeVolume() {
            assertEquals(1.0, new RectangularPrism(1.0, 1.0, 1.0).getVolume(), DELTA);
        }

        @Test
        @DisplayName("getBaseFaceArea: l*w = 12")
        void getBaseFaceArea() {
            assertEquals(12.0, ref.getBaseFaceArea(), DELTA);
        }

        @Test
        @DisplayName("getLengthHeightFaceArea: l*h = 20")
        void getLengthHeightFaceArea() {
            assertEquals(20.0, ref.getLengthHeightFaceArea(), DELTA);
        }

        @Test
        @DisplayName("getWidthHeightFaceArea: w*h = 15")
        void getWidthHeightFaceArea() {
            assertEquals(15.0, ref.getWidthHeightFaceArea(), DELTA);
        }

        @Test
        @DisplayName("getLargestFaceArea: max(12,20,15) = 20")
        void getLargestFaceArea() {
            assertEquals(20.0, ref.getLargestFaceArea(), DELTA);
        }

        @Test
        @DisplayName("getSmallestFaceArea: min(12,20,15) = 12")
        void getSmallestFaceArea() {
            assertEquals(12.0, ref.getSmallestFaceArea(), DELTA);
        }

        @Test
        @DisplayName("getSpaceDiagonal: √(16+9+25) = √50 ≈ 7.071")
        void getSpaceDiagonal() {
            assertEquals(Math.sqrt(50.0), ref.getSpaceDiagonal(), DELTA);
        }

        @Test
        @DisplayName("getLengthWidthFaceDiagonal: √(16+9) = 5")
        void getLengthWidthFaceDiagonal() {
            assertEquals(5.0, ref.getLengthWidthFaceDiagonal(), DELTA);
        }

        @Test
        @DisplayName("getLengthHeightFaceDiagonal: √(16+25) = √41")
        void getLengthHeightFaceDiagonal() {
            assertEquals(Math.sqrt(41.0), ref.getLengthHeightFaceDiagonal(), DELTA);
        }

        @Test
        @DisplayName("getWidthHeightFaceDiagonal: √(9+25) = √34")
        void getWidthHeightFaceDiagonal() {
            assertEquals(Math.sqrt(34.0), ref.getWidthHeightFaceDiagonal(), DELTA);
        }

        @Test
        @DisplayName("getTotalEdgeLength: 4*(4+3+5) = 48")
        void getTotalEdgeLength() {
            assertEquals(48.0, ref.getTotalEdgeLength(), DELTA);
        }

        @Test
        @DisplayName("getBasePerimeter: 2*(4+3) = 14")
        void getBasePerimeter() {
            assertEquals(14.0, ref.getBasePerimeter(), DELTA);
        }

        @Test
        @DisplayName("isCube returns false for non-equal dimensions")
        void isCubeFalse() {
            assertFalse(ref.isCube());
        }

        @Test
        @DisplayName("isCube returns true when all dimensions are equal")
        void isCubeTrue() {
            assertTrue(new RectangularPrism(5.0, 5.0, 5.0).isCube());
        }

        @Test
        @DisplayName("isSquarePrism returns false when no two dimensions are equal")
        void isSquarePrismFalse() {
            assertFalse(ref.isSquarePrism());
        }

        @Test
        @DisplayName("isSquarePrism returns true when length equals width")
        void isSquarePrismTrue() {
            assertTrue(new RectangularPrism(4.0, 4.0, 5.0).isSquarePrism());
        }

        @Test
        @DisplayName("getInscribedSphereRadius: min(4,3,5)/2 = 1.5")
        void getInscribedSphereRadius() {
            assertEquals(1.5, ref.getInscribedSphereRadius(), DELTA);
        }

        @Test
        @DisplayName("canFitSphere returns true when sphere fits")
        void canFitSphereFits() {
            assertTrue(ref.canFitSphere(1.4));
        }

        @Test
        @DisplayName("canFitSphere returns false when sphere is too large")
        void canFitSphereTooLarge() {
            assertFalse(ref.canFitSphere(1.5));
        }

        @Test
        @DisplayName("Uniform scale returns correctly scaled prism")
        void scaleUniform() {
            RectangularPrism scaled = ref.scale(2.0);
            assertEquals(8.0,  scaled.getLength(), DELTA);
            assertEquals(6.0,  scaled.getWidth(),  DELTA);
            assertEquals(10.0, scaled.getHeight(), DELTA);
            // Original unchanged
            assertEquals(4.0, ref.getLength(), DELTA);
        }

        @Test
        @DisplayName("Per-axis scale returns correctly scaled prism")
        void scalePerAxis() {
            RectangularPrism scaled = ref.scale(2.0, 3.0, 4.0);
            assertEquals(8.0,  scaled.getLength(), DELTA);
            assertEquals(9.0,  scaled.getWidth(),  DELTA);
            assertEquals(20.0, scaled.getHeight(), DELTA);
        }

        @Test
        @DisplayName("scale with zero factor throws IllegalArgumentException")
        void scaleZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> ref.scale(0.0));
        }

        @Test
        @DisplayName("Per-axis scale with zero height factor throws IllegalArgumentException")
        void perAxisScaleZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> ref.scale(1.0, 1.0, 0.0));
        }
    }

    // =========================================================================
    // Boundary Tests
    // =========================================================================

    @Nested
    @DisplayName("Boundary Tests")
    class BoundaryTests {

        /**
         * Decision: zero length is rejected — a prism with a zero dimension
         * collapses to a 2D shape and has no volume.
         */
        @Test
        @DisplayName("Zero length throws IllegalArgumentException (boundary decision)")
        void zeroLengthThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism(0.0, 3.0, 5.0));
        }

        @Test
        @DisplayName("Zero width throws IllegalArgumentException (boundary decision)")
        void zeroWidthThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism(4.0, 0.0, 5.0));
        }

        @Test
        @DisplayName("Zero height throws IllegalArgumentException (boundary decision)")
        void zeroHeightThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism(4.0, 3.0, 0.0));
        }

        @Test
        @DisplayName("Very small dimensions (1e-10) are accepted")
        void verySmallDimensions() {
            double d = 1e-10;
            RectangularPrism rp = new RectangularPrism(d, d, d);
            assertEquals(6.0 * d * d, rp.getSurfaceArea(), 1e-28);
            assertEquals(d * d * d,   rp.getVolume(),      1e-38);
        }

        @Test
        @DisplayName("Very large dimensions (1e10) are accepted")
        void veryLargeDimensions() {
            double d = 1e10;
            RectangularPrism rp = new RectangularPrism(d, d, d);
            assertEquals(6.0 * d * d, rp.getSurfaceArea(), 1e10);
            assertEquals(d * d * d,   rp.getVolume(),      1e20);
        }

        @Test
        @DisplayName("Minimum positive double dimensions are accepted")
        void minimumPositiveDouble() {
            assertDoesNotThrow(() -> new RectangularPrism(
                    Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE));
        }

        @Test
        @DisplayName("Negative dimension throws IllegalArgumentException")
        void negativeDimensionThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism(-1.0, 3.0, 5.0));
        }
    }

    // =========================================================================
    // Input Validation Tests
    // =========================================================================

    @Nested
    @DisplayName("Input Validation Tests")
    class InputValidationTests {

        @Test
        @DisplayName("Null color in constructor throws IllegalArgumentException")
        void nullColorThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism(4.0, 3.0, 5.0, null));
        }

        @Test
        @DisplayName("Null label in constructor throws IllegalArgumentException")
        void nullLabelThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism(4.0, 3.0, 5.0, "red", null));
        }

        @Test
        @DisplayName("NaN length throws IllegalArgumentException")
        void nanLengthThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism(Double.NaN, 3.0, 5.0));
        }

        @Test
        @DisplayName("NaN width throws IllegalArgumentException")
        void nanWidthThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism(4.0, Double.NaN, 5.0));
        }

        @Test
        @DisplayName("NaN height throws IllegalArgumentException")
        void nanHeightThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism(4.0, 3.0, Double.NaN));
        }

        @Test
        @DisplayName("canFitSphere with zero radius throws IllegalArgumentException")
        void canFitSphereZeroThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism(4.0, 3.0, 5.0).canFitSphere(0.0));
        }
    }

    // =========================================================================
    // Inheritance and Polymorphism Tests
    // =========================================================================

    @Nested
    @DisplayName("Inheritance and Polymorphism Tests")
    class InheritanceTests {

        @Test
        @DisplayName("RectangularPrism is an instance of Shape3D")
        void isShape3D() {
            assertTrue(new RectangularPrism(4.0, 3.0, 5.0) instanceof Shape3D);
        }

        @Test
        @DisplayName("RectangularPrism is an instance of ThreeDimensionalShape")
        void isThreeDimensionalShape() {
            assertTrue(new RectangularPrism(4.0, 3.0, 5.0) instanceof ThreeDimensionalShape);
        }

        @Test
        @DisplayName("Works correctly when referenced as Shape3D")
        void asShape3D() {
            Shape3D shape = new RectangularPrism(4.0, 3.0, 5.0);
            assertEquals(94.0, shape.getSurfaceArea(), DELTA);
            assertEquals(60.0, shape.getVolume(),      DELTA);
        }

        @Test
        @DisplayName("Works correctly when referenced as ThreeDimensionalShape")
        void asThreeDimensionalShape() {
            ThreeDimensionalShape shape = new RectangularPrism(4.0, 3.0, 5.0);
            assertEquals(94.0, shape.getSurfaceArea(), DELTA);
            assertEquals(60.0, shape.getVolume(),      DELTA);
        }

        @Test
        @DisplayName("getName returns 'RectangularPrism' through Shape3D reference")
        void getNameThroughShape3D() {
            Shape3D shape = new RectangularPrism(4.0, 3.0, 5.0);
            assertEquals("RectangularPrism", shape.getName());
        }

        @Test
        @DisplayName("toString is overridden and contains 'RectangularPrism'")
        void toStringOverridden() {
            String s = new RectangularPrism(4.0, 3.0, 5.0).toString();
            assertTrue(s.contains("RectangularPrism"));
            assertTrue(s.contains("4.00"));
            assertTrue(s.contains("3.00"));
            assertTrue(s.contains("5.00"));
        }

        @Test
        @DisplayName("equals and hashCode are consistent")
        void equalsAndHashCode() {
            RectangularPrism a = new RectangularPrism(4.0, 3.0, 5.0, "blue", "x");
            RectangularPrism b = new RectangularPrism(4.0, 3.0, 5.0, "blue", "x");
            RectangularPrism c = new RectangularPrism(4.0, 3.0, 5.0, "blue", "y");

            assertEquals(a, b);
            assertNotEquals(a, c);
            assertEquals(a.hashCode(), b.hashCode());
        }

        @Test
        @DisplayName("RectangularPrism does not equal a different Shape3D subclass")
        void notEqualToSphere() {
            Shape3D prism  = new RectangularPrism(4.0, 4.0, 4.0);
            Shape3D sphere = new Sphere(4.0);
            assertNotEquals(prism, sphere);
        }

        @Test
        @DisplayName("RectangularPrism with equal dims does not equal Cube (different type)")
        void notEqualToCubeEvenWithEqualDims() {
            // isCube() returns true, but equals() checks instanceof RectangularPrism
            Shape3D prism = new RectangularPrism(4.0, 4.0, 4.0);
            Shape3D cube  = new Cube(4.0);
            assertNotEquals(prism, cube);
        }
    }
}
