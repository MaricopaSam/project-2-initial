package com.csc205.project2.shapes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for the {@link Sphere} class.
 *
 * <p>Test categories:</p>
 * <ul>
 *   <li>Constructor tests — valid and invalid inputs, copy constructor</li>
 *   <li>Getter / setter tests — normal mutations and validation</li>
 *   <li>Calculation tests — surface area, volume and sphere-specific methods
 *       verified against known mathematical results</li>
 *   <li>Boundary tests — zero, very small, and very large radius values</li>
 *   <li>Input validation tests — negative values and null inputs</li>
 *   <li>Inheritance / polymorphism tests — behaviour through {@link Shape3D}
 *       and {@link ThreeDimensionalShape} references</li>
 * </ul>
 *
 * <p><b>Boundary decision:</b> A radius of zero (or any non-positive value)
 * is considered physically meaningless for a sphere and therefore throws
 * {@link IllegalArgumentException}. This is documented on every boundary
 * test that exercises that contract.</p>
 */
@DisplayName("Sphere Tests")
class SphereTest {

    /** Delta used for all floating-point assertions. */
    private static final double DELTA = 1e-9;

    // =========================================================================
    // Constructor Tests
    // =========================================================================

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Single-arg constructor sets radius and defaults")
        void singleArgConstructor() {
            Sphere s = new Sphere(5.0);
            assertEquals(5.0,      s.getRadius(), DELTA);
            assertEquals("Sphere", s.getName());
            assertEquals("white",  s.getColor());
            assertEquals("",       s.getLabel());
        }

        @Test
        @DisplayName("Two-arg constructor sets radius and color")
        void twoArgConstructor() {
            Sphere s = new Sphere(3.0, "blue");
            assertEquals(3.0,    s.getRadius(), DELTA);
            assertEquals("blue", s.getColor());
            assertEquals("",     s.getLabel());
        }

        @Test
        @DisplayName("Three-arg constructor sets all fields")
        void threeArgConstructor() {
            Sphere s = new Sphere(7.0, "red", "testSphere");
            assertEquals(7.0,         s.getRadius(), DELTA);
            assertEquals("red",       s.getColor());
            assertEquals("testSphere",s.getLabel());
        }

        @Test
        @DisplayName("Copy constructor creates independent copy")
        void copyConstructor() {
            Sphere original = new Sphere(4.0, "green", "orig");
            Sphere copy     = new Sphere(original);

            assertEquals(original.getRadius(), copy.getRadius(), DELTA);
            assertEquals(original.getColor(),  copy.getColor());
            assertEquals(original.getLabel(),  copy.getLabel());

            // Mutating the copy must not affect the original
            copy.setRadius(10.0);
            assertEquals(4.0, original.getRadius(), DELTA);
        }

        @Test
        @DisplayName("Copy constructor with null throws IllegalArgumentException")
        void copyConstructorNullThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Sphere(null));
        }

        @Test
        @DisplayName("Name is always 'Sphere' regardless of constructor used")
        void nameIsAlwaysSphere() {
            assertEquals("Sphere", new Sphere(1.0).getName());
            assertEquals("Sphere", new Sphere(1.0, "red").getName());
            assertEquals("Sphere", new Sphere(1.0, "red", "lbl").getName());
        }
    }

    // =========================================================================
    // Getter and Setter Tests
    // =========================================================================

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        private Sphere sphere;

        @BeforeEach
        void setUp() {
            sphere = new Sphere(5.0, "blue", "s1");
        }

        @Test
        @DisplayName("setRadius updates the radius")
        void setRadiusUpdates() {
            sphere.setRadius(9.0);
            assertEquals(9.0, sphere.getRadius(), DELTA);
        }

        @Test
        @DisplayName("setRadius with zero throws IllegalArgumentException")
        void setRadiusZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> sphere.setRadius(0.0));
        }

        @Test
        @DisplayName("setRadius with negative throws IllegalArgumentException")
        void setRadiusNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> sphere.setRadius(-1.0));
        }

        @Test
        @DisplayName("setColor updates the color")
        void setColorUpdates() {
            sphere.setColor("purple");
            assertEquals("purple", sphere.getColor());
        }

        @Test
        @DisplayName("setColor with null throws IllegalArgumentException")
        void setColorNullThrows() {
            assertThrows(IllegalArgumentException.class, () -> sphere.setColor(null));
        }

        @Test
        @DisplayName("setLabel updates the label")
        void setLabelUpdates() {
            sphere.setLabel("newLabel");
            assertEquals("newLabel", sphere.getLabel());
        }

        @Test
        @DisplayName("setLabel with null throws IllegalArgumentException")
        void setLabelNullThrows() {
            assertThrows(IllegalArgumentException.class, () -> sphere.setLabel(null));
        }
    }

    // =========================================================================
    // Calculation Tests
    // =========================================================================

    @Nested
    @DisplayName("Calculation Tests")
    class CalculationTests {

        @Test
        @DisplayName("Surface area with radius 1: 4π ≈ 12.566")
        void surfaceAreaRadiusOne() {
            Sphere s = new Sphere(1.0);
            assertEquals(4.0 * Math.PI, s.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Surface area with radius 3: 4π*9 = 36π ≈ 113.097")
        void surfaceAreaRadiusThree() {
            Sphere s = new Sphere(3.0);
            assertEquals(4.0 * Math.PI * 9.0, s.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Volume with radius 3: (4/3)π*27 ≈ 113.097")
        void sphereVolumeCalculation() {
            // Volume = 4/3 * π * r³ = 4/3 * π * 27 = 113.097...
            Sphere s = new Sphere(3.0);
            double expected = (4.0 / 3.0) * Math.PI * Math.pow(3.0, 3);
            assertEquals(expected, s.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Volume with radius 1: 4π/3 ≈ 4.189")
        void volumeRadiusOne() {
            Sphere s = new Sphere(1.0);
            assertEquals((4.0 / 3.0) * Math.PI, s.getVolume(), DELTA);
        }

        @Test
        @DisplayName("getDiameter returns 2 * radius")
        void getDiameter() {
            Sphere s = new Sphere(6.0);
            assertEquals(12.0, s.getDiameter(), DELTA);
        }

        @Test
        @DisplayName("getGreatCircleCircumference returns 2πr")
        void getGreatCircleCircumference() {
            Sphere s = new Sphere(5.0);
            assertEquals(2.0 * Math.PI * 5.0, s.getGreatCircleCircumference(), DELTA);
        }

        @Test
        @DisplayName("getCrossSectionalArea returns πr²")
        void getCrossSectionalArea() {
            Sphere s = new Sphere(4.0);
            assertEquals(Math.PI * 16.0, s.getCrossSectionalArea(), DELTA);
        }

        @Test
        @DisplayName("scale returns new sphere with scaled radius")
        void scale() {
            Sphere s      = new Sphere(3.0, "blue", "orig");
            Sphere scaled = s.scale(2.0);
            assertEquals(6.0,    scaled.getRadius(), DELTA);
            assertEquals("blue", scaled.getColor());
            assertEquals("orig", scaled.getLabel());
            // Original is unchanged
            assertEquals(3.0, s.getRadius(), DELTA);
        }

        @Test
        @DisplayName("scale with zero factor throws IllegalArgumentException")
        void scaleZeroThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Sphere(3.0).scale(0.0));
        }

        @Test
        @DisplayName("scale with negative factor throws IllegalArgumentException")
        void scaleNegativeThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Sphere(3.0).scale(-1.0));
        }
    }

    // =========================================================================
    // Boundary Tests
    // =========================================================================

    @Nested
    @DisplayName("Boundary Tests")
    class BoundaryTests {

        /**
         * Decision: radius = 0 is rejected because a sphere with zero radius
         * has no volume or surface and is geometrically degenerate.
         * {@link IllegalArgumentException} is the documented contract.
         */
        @Test
        @DisplayName("Zero radius throws IllegalArgumentException (boundary decision)")
        void sphereWithZeroRadius() {
            assertThrows(IllegalArgumentException.class, () -> new Sphere(0.0));
        }

        @Test
        @DisplayName("Very small radius (1e-10) is accepted and produces correct results")
        void verySmallRadius() {
            double r = 1e-10;
            Sphere s = new Sphere(r);
            assertEquals(4.0 * Math.PI * r * r,            s.getSurfaceArea(), 1e-28);
            assertEquals((4.0 / 3.0) * Math.PI * r * r * r, s.getVolume(),      1e-38);
        }

        @Test
        @DisplayName("Very large radius (1e10) is accepted and produces correct results")
        void veryLargeRadius() {
            double r = 1e10;
            Sphere s = new Sphere(r);
            assertEquals(4.0 * Math.PI * r * r,            s.getSurfaceArea(), 1e10);
            assertEquals((4.0 / 3.0) * Math.PI * r * r * r, s.getVolume(),      1e20);
        }

        @Test
        @DisplayName("Minimum positive double is accepted")
        void minimumPositiveDouble() {
            assertDoesNotThrow(() -> new Sphere(Double.MIN_VALUE));
        }

        @Test
        @DisplayName("Negative radius throws IllegalArgumentException")
        void negativeRadiusThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Sphere(-0.001));
        }
    }

    // =========================================================================
    // Input Validation Tests
    // =========================================================================

    @Nested
    @DisplayName("Input Validation Tests")
    class InputValidationTests {

        @Test
        @DisplayName("Constructor with null color throws IllegalArgumentException")
        void nullColorThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Sphere(5.0, null));
        }

        @Test
        @DisplayName("Constructor with null label throws IllegalArgumentException")
        void nullLabelThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Sphere(5.0, "red", null));
        }

        @Test
        @DisplayName("Constructor with negative radius throws IllegalArgumentException")
        void negativeRadiusConstructor() {
            assertThrows(IllegalArgumentException.class, () -> new Sphere(-5.0));
        }

        @Test
        @DisplayName("Constructor with NaN radius throws IllegalArgumentException")
        void nanRadiusThrows() {
            // NaN <= 0.0 is false, so we check separately if implementation guards it.
            // If not guarded, document the behaviour. Here we document: NaN is not > 0.
            assertThrows(IllegalArgumentException.class, () -> new Sphere(Double.NaN));
        }

        @Test
        @DisplayName("Constructor with negative infinity throws IllegalArgumentException")
        void negativeInfinityThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Sphere(Double.NEGATIVE_INFINITY));
        }
    }

    // =========================================================================
    // Inheritance and Polymorphism Tests
    // =========================================================================

    @Nested
    @DisplayName("Inheritance and Polymorphism Tests")
    class InheritanceTests {

        @Test
        @DisplayName("Sphere is an instance of Shape3D")
        void sphereIsShape3D() {
            assertTrue(new Sphere(5.0) instanceof Shape3D);
        }

        @Test
        @DisplayName("Sphere is an instance of ThreeDimensionalShape")
        void sphereIsThreeDimensionalShape() {
            assertTrue(new Sphere(5.0) instanceof ThreeDimensionalShape);
        }

        @Test
        @DisplayName("Sphere works correctly when referenced as Shape3D")
        void sphereAsShape3D() {
            // Verify polymorphic dispatch calls the Sphere implementations
            Shape3D shape = new Sphere(3.0);
            double expectedSA = 4.0 * Math.PI * 9.0;
            double expectedV  = (4.0 / 3.0) * Math.PI * 27.0;
            assertEquals(expectedSA, shape.getSurfaceArea(), DELTA);
            assertEquals(expectedV,  shape.getVolume(),      DELTA);
        }

        @Test
        @DisplayName("Sphere works correctly when referenced as ThreeDimensionalShape")
        void sphereAsThreeDimensionalShape() {
            ThreeDimensionalShape shape = new Sphere(3.0);
            assertEquals(4.0 * Math.PI * 9.0,          shape.getSurfaceArea(), DELTA);
            assertEquals((4.0 / 3.0) * Math.PI * 27.0, shape.getVolume(),      DELTA);
        }

        @Test
        @DisplayName("getName returns 'Sphere' through Shape3D reference")
        void getNameThroughShape3D() {
            Shape3D shape = new Sphere(5.0);
            assertEquals("Sphere", shape.getName());
        }

        @Test
        @DisplayName("toString is overridden and contains 'Sphere'")
        void toStringOverridden() {
            String s = new Sphere(5.0).toString();
            assertTrue(s.contains("Sphere"));
            assertTrue(s.contains("5.00"));
        }

        @Test
        @DisplayName("equals and hashCode are consistent")
        void equalsAndHashCode() {
            Sphere a = new Sphere(4.0, "blue", "x");
            Sphere b = new Sphere(4.0, "blue", "x");
            Sphere c = new Sphere(4.0, "blue", "y");

            assertEquals(a, b);
            assertNotEquals(a, c);
            assertEquals(a.hashCode(), b.hashCode());
        }

        @Test
        @DisplayName("Sphere does not equal a different Shape3D subclass")
        void sphereNotEqualToCube() {
            Shape3D sphere = new Sphere(4.0);
            Shape3D cube   = new Cube(4.0);
            assertNotEquals(sphere, cube);
        }
    }
}
