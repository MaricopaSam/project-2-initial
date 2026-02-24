package com.csc205.project2.shapes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for the {@link Cube} class.
 *
 * <p>Test categories:</p>
 * <ul>
 *   <li>Constructor tests — valid and invalid inputs, copy constructor</li>
 *   <li>Getter / setter tests — normal mutations and validation</li>
 *   <li>Calculation tests — surface area, volume and cube-specific methods
 *       verified against known mathematical results</li>
 *   <li>Boundary tests — zero, very small, and very large side lengths</li>
 *   <li>Input validation tests — negative values and null inputs</li>
 *   <li>Inheritance / polymorphism tests — behaviour through {@link Shape3D}
 *       and {@link ThreeDimensionalShape} references</li>
 * </ul>
 *
 * <p><b>Boundary decision:</b> A side length of zero (or any non-positive value)
 * is physically meaningless for a cube and therefore throws
 * {@link IllegalArgumentException}. Every boundary test that exercises this
 * contract documents this decision explicitly.</p>
 */
@DisplayName("Cube Tests")
class CubeTest {

    private static final double DELTA = 1e-9;

    // =========================================================================
    // Constructor Tests
    // =========================================================================

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Single-arg constructor sets sideLength and defaults")
        void singleArgConstructor() {
            Cube c = new Cube(4.0);
            assertEquals(4.0,   c.getSideLength(), DELTA);
            assertEquals("Cube", c.getName());
            assertEquals("white",c.getColor());
            assertEquals("",    c.getLabel());
        }

        @Test
        @DisplayName("Two-arg constructor sets sideLength and color")
        void twoArgConstructor() {
            Cube c = new Cube(3.0, "red");
            assertEquals(3.0,  c.getSideLength(), DELTA);
            assertEquals("red",c.getColor());
            assertEquals("",   c.getLabel());
        }

        @Test
        @DisplayName("Three-arg constructor sets all fields")
        void threeArgConstructor() {
            Cube c = new Cube(5.0, "blue", "myCube");
            assertEquals(5.0,     c.getSideLength(), DELTA);
            assertEquals("blue",  c.getColor());
            assertEquals("myCube",c.getLabel());
        }

        @Test
        @DisplayName("Copy constructor creates independent copy")
        void copyConstructor() {
            Cube original = new Cube(6.0, "green", "orig");
            Cube copy     = new Cube(original);

            assertEquals(original.getSideLength(), copy.getSideLength(), DELTA);
            assertEquals(original.getColor(),      copy.getColor());
            assertEquals(original.getLabel(),      copy.getLabel());

            // Mutating copy must not affect original
            copy.setSideLength(20.0);
            assertEquals(6.0, original.getSideLength(), DELTA);
        }

        @Test
        @DisplayName("Copy constructor with null throws IllegalArgumentException")
        void copyConstructorNullThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cube(null));
        }

        @Test
        @DisplayName("Name is always 'Cube' regardless of constructor used")
        void nameIsAlwaysCube() {
            assertEquals("Cube", new Cube(1.0).getName());
            assertEquals("Cube", new Cube(1.0, "red").getName());
            assertEquals("Cube", new Cube(1.0, "red", "lbl").getName());
        }
    }

    // =========================================================================
    // Getter and Setter Tests
    // =========================================================================

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        private Cube cube;

        @BeforeEach
        void setUp() {
            cube = new Cube(4.0, "grey", "c1");
        }

        @Test
        @DisplayName("setSideLength updates the value")
        void setSideLengthUpdates() {
            cube.setSideLength(8.0);
            assertEquals(8.0, cube.getSideLength(), DELTA);
        }

        @Test
        @DisplayName("setSideLength with zero throws IllegalArgumentException")
        void setSideLengthZeroThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> cube.setSideLength(0.0));
        }

        @Test
        @DisplayName("setSideLength with negative throws IllegalArgumentException")
        void setSideLengthNegativeThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> cube.setSideLength(-3.0));
        }

        @Test
        @DisplayName("setColor updates the color")
        void setColorUpdates() {
            cube.setColor("yellow");
            assertEquals("yellow", cube.getColor());
        }

        @Test
        @DisplayName("setColor with null throws IllegalArgumentException")
        void setColorNullThrows() {
            assertThrows(IllegalArgumentException.class, () -> cube.setColor(null));
        }

        @Test
        @DisplayName("setLabel updates the label")
        void setLabelUpdates() {
            cube.setLabel("updatedLabel");
            assertEquals("updatedLabel", cube.getLabel());
        }

        @Test
        @DisplayName("setLabel with null throws IllegalArgumentException")
        void setLabelNullThrows() {
            assertThrows(IllegalArgumentException.class, () -> cube.setLabel(null));
        }
    }

    // =========================================================================
    // Calculation Tests
    // =========================================================================

    @Nested
    @DisplayName("Calculation Tests")
    class CalculationTests {

        @Test
        @DisplayName("Surface area with side 1: 6 * 1² = 6")
        void surfaceAreaSideOne() {
            assertEquals(6.0, new Cube(1.0).getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Surface area with side 4: 6 * 16 = 96")
        void surfaceAreaSideFour() {
            assertEquals(96.0, new Cube(4.0).getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Volume with side 1: 1³ = 1")
        void volumeSideOne() {
            assertEquals(1.0, new Cube(1.0).getVolume(), DELTA);
        }

        @Test
        @DisplayName("Volume with side 3: 3³ = 27")
        void volumeSideThree() {
            assertEquals(27.0, new Cube(3.0).getVolume(), DELTA);
        }

        @Test
        @DisplayName("Volume with side 4: 4³ = 64")
        void volumeSideFour() {
            assertEquals(64.0, new Cube(4.0).getVolume(), DELTA);
        }

        @Test
        @DisplayName("getFaceArea returns a²")
        void getFaceArea() {
            assertEquals(16.0, new Cube(4.0).getFaceArea(), DELTA);
        }

        @Test
        @DisplayName("getFaceDiagonal returns a*√2")
        void getFaceDiagonal() {
            assertEquals(4.0 * Math.sqrt(2.0), new Cube(4.0).getFaceDiagonal(), DELTA);
        }

        @Test
        @DisplayName("getSpaceDiagonal returns a*√3")
        void getSpaceDiagonal() {
            assertEquals(4.0 * Math.sqrt(3.0), new Cube(4.0).getSpaceDiagonal(), DELTA);
        }

        @Test
        @DisplayName("getTotalEdgeLength returns 12 * a")
        void getTotalEdgeLength() {
            assertEquals(48.0, new Cube(4.0).getTotalEdgeLength(), DELTA);
        }

        @Test
        @DisplayName("getInscribedSphereRadius returns a/2")
        void getInscribedSphereRadius() {
            assertEquals(2.0, new Cube(4.0).getInscribedSphereRadius(), DELTA);
        }

        @Test
        @DisplayName("getCircumscribedSphereRadius returns (a*√3)/2")
        void getCircumscribedSphereRadius() {
            double expected = (4.0 * Math.sqrt(3.0)) / 2.0;
            assertEquals(expected, new Cube(4.0).getCircumscribedSphereRadius(), DELTA);
        }

        @Test
        @DisplayName("canFitSphere returns true when sphere fits")
        void canFitSphereFits() {
            assertTrue(new Cube(10.0).canFitSphere(4.9));
        }

        @Test
        @DisplayName("canFitSphere returns false when sphere is too large")
        void canFitSphereTooLarge() {
            assertFalse(new Cube(10.0).canFitSphere(5.0));
        }

        @Test
        @DisplayName("scale returns new cube with scaled side length")
        void scale() {
            Cube c      = new Cube(3.0, "red", "orig");
            Cube scaled = c.scale(3.0);
            assertEquals(9.0,   scaled.getSideLength(), DELTA);
            assertEquals("red", scaled.getColor());
            // Original is unchanged
            assertEquals(3.0, c.getSideLength(), DELTA);
        }

        @Test
        @DisplayName("scale with zero factor throws IllegalArgumentException")
        void scaleZeroThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cube(4.0).scale(0.0));
        }
    }

    // =========================================================================
    // Boundary Tests
    // =========================================================================

    @Nested
    @DisplayName("Boundary Tests")
    class BoundaryTests {

        /**
         * Decision: sideLength = 0 is rejected because a cube with zero edge
         * length is degenerate and has no volume. {@link IllegalArgumentException}
         * is the documented contract.
         */
        @Test
        @DisplayName("Zero side length throws IllegalArgumentException (boundary decision)")
        void cubeWithZeroSideLength() {
            assertThrows(IllegalArgumentException.class, () -> new Cube(0.0));
        }

        @Test
        @DisplayName("Very small side length (1e-10) is accepted and produces correct results")
        void verySmallSideLength() {
            double a = 1e-10;
            Cube c = new Cube(a);
            assertEquals(6.0 * a * a, c.getSurfaceArea(), 1e-28);
            assertEquals(a * a * a,   c.getVolume(),      1e-38);
        }

        @Test
        @DisplayName("Very large side length (1e10) is accepted and produces correct results")
        void veryLargeSideLength() {
            double a = 1e10;
            Cube c = new Cube(a);
            assertEquals(6.0 * a * a, c.getSurfaceArea(), 1e10);
            assertEquals(a * a * a,   c.getVolume(),      1e20);
        }

        @Test
        @DisplayName("Minimum positive double is accepted")
        void minimumPositiveDouble() {
            assertDoesNotThrow(() -> new Cube(Double.MIN_VALUE));
        }

        @Test
        @DisplayName("Negative side length throws IllegalArgumentException")
        void negativeSideLengthThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cube(-1.0));
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
                    () -> new Cube(4.0, null));
        }

        @Test
        @DisplayName("Null label in constructor throws IllegalArgumentException")
        void nullLabelThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cube(4.0, "red", null));
        }

        @Test
        @DisplayName("Negative side length throws IllegalArgumentException")
        void negativeValueThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cube(-0.001));
        }

        @Test
        @DisplayName("NaN side length throws IllegalArgumentException")
        void nanSideLengthThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cube(Double.NaN));
        }

        @Test
        @DisplayName("Negative infinity side length throws IllegalArgumentException")
        void negativeInfinityThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cube(Double.NEGATIVE_INFINITY));
        }

        @Test
        @DisplayName("canFitSphere with zero radius throws IllegalArgumentException")
        void canFitSphereZeroRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cube(4.0).canFitSphere(0.0));
        }
    }

    // =========================================================================
    // Inheritance and Polymorphism Tests
    // =========================================================================

    @Nested
    @DisplayName("Inheritance and Polymorphism Tests")
    class InheritanceTests {

        @Test
        @DisplayName("Cube is an instance of Shape3D")
        void cubeIsShape3D() {
            assertTrue(new Cube(4.0) instanceof Shape3D);
        }

        @Test
        @DisplayName("Cube is an instance of ThreeDimensionalShape")
        void cubeIsThreeDimensionalShape() {
            assertTrue(new Cube(4.0) instanceof ThreeDimensionalShape);
        }

        @Test
        @DisplayName("Cube works correctly when referenced as Shape3D")
        void cubeAsShape3D() {
            Shape3D shape = new Cube(4.0);
            assertEquals(96.0, shape.getSurfaceArea(), DELTA);
            assertEquals(64.0, shape.getVolume(),      DELTA);
        }

        @Test
        @DisplayName("Cube works correctly when referenced as ThreeDimensionalShape")
        void cubeAsThreeDimensionalShape() {
            ThreeDimensionalShape shape = new Cube(3.0);
            assertEquals(54.0, shape.getSurfaceArea(), DELTA);
            assertEquals(27.0, shape.getVolume(),      DELTA);
        }

        @Test
        @DisplayName("getName returns 'Cube' through Shape3D reference")
        void getNameThroughShape3D() {
            Shape3D shape = new Cube(4.0);
            assertEquals("Cube", shape.getName());
        }

        @Test
        @DisplayName("toString is overridden and contains 'Cube'")
        void toStringOverridden() {
            String s = new Cube(4.0).toString();
            assertTrue(s.contains("Cube"));
            assertTrue(s.contains("4.00"));
        }

        @Test
        @DisplayName("equals and hashCode are consistent")
        void equalsAndHashCode() {
            Cube a = new Cube(4.0, "blue", "x");
            Cube b = new Cube(4.0, "blue", "x");
            Cube c = new Cube(4.0, "blue", "y");

            assertEquals(a, b);
            assertNotEquals(a, c);
            assertEquals(a.hashCode(), b.hashCode());
        }

        @Test
        @DisplayName("Cube does not equal a different Shape3D subclass")
        void cubeNotEqualToSphere() {
            Shape3D cube   = new Cube(4.0);
            Shape3D sphere = new Sphere(4.0);
            assertNotEquals(cube, sphere);
        }
    }
}
