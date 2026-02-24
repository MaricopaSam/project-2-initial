package com.csc205.project2.shapes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for the {@link Cylinder} class.
 *
 * <p>Test categories:</p>
 * <ul>
 *   <li>Constructor tests — valid and invalid inputs, copy constructor</li>
 *   <li>Getter / setter tests — normal mutations and validation</li>
 *   <li>Calculation tests — surface area, volume and cylinder-specific methods
 *       verified against known mathematical results</li>
 *   <li>Boundary tests — zero, very small, and very large dimension values</li>
 *   <li>Input validation tests — negative values and null inputs</li>
 *   <li>Inheritance / polymorphism tests — behaviour through {@link Shape3D}
 *       and {@link ThreeDimensionalShape} references</li>
 * </ul>
 *
 * <p><b>Boundary decision:</b> A radius or height of zero (or any non-positive
 * value) is physically meaningless for a cylinder and therefore throws
 * {@link IllegalArgumentException}.</p>
 */
@DisplayName("Cylinder Tests")
class CylinderTest {

    private static final double DELTA = 1e-9;

    // =========================================================================
    // Constructor Tests
    // =========================================================================

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Two-arg constructor sets dimensions and defaults")
        void twoArgConstructor() {
            Cylinder c = new Cylinder(3.0, 7.0);
            assertEquals(3.0,       c.getRadius(), DELTA);
            assertEquals(7.0,       c.getHeight(), DELTA);
            assertEquals("Cylinder",c.getName());
            assertEquals("white",   c.getColor());
            assertEquals("",        c.getLabel());
        }

        @Test
        @DisplayName("Three-arg constructor sets dimensions and color")
        void threeArgConstructor() {
            Cylinder c = new Cylinder(3.0, 7.0, "green");
            assertEquals(3.0,     c.getRadius(), DELTA);
            assertEquals(7.0,     c.getHeight(), DELTA);
            assertEquals("green", c.getColor());
        }

        @Test
        @DisplayName("Four-arg constructor sets all fields")
        void fourArgConstructor() {
            Cylinder c = new Cylinder(3.0, 7.0, "green", "myCylinder");
            assertEquals(3.0,          c.getRadius(), DELTA);
            assertEquals(7.0,          c.getHeight(), DELTA);
            assertEquals("green",      c.getColor());
            assertEquals("myCylinder", c.getLabel());
        }

        @Test
        @DisplayName("Copy constructor creates independent copy")
        void copyConstructor() {
            Cylinder original = new Cylinder(3.0, 7.0, "blue", "orig");
            Cylinder copy     = new Cylinder(original);

            assertEquals(original.getRadius(), copy.getRadius(), DELTA);
            assertEquals(original.getHeight(), copy.getHeight(), DELTA);
            assertEquals(original.getColor(),  copy.getColor());

            // Mutating copy must not affect original
            copy.setRadius(15.0);
            assertEquals(3.0, original.getRadius(), DELTA);
        }

        @Test
        @DisplayName("Copy constructor with null throws IllegalArgumentException")
        void copyConstructorNullThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cylinder(null));
        }

        @Test
        @DisplayName("Name is always 'Cylinder' regardless of constructor used")
        void nameIsAlwaysCylinder() {
            assertEquals("Cylinder", new Cylinder(1.0, 1.0).getName());
        }
    }

    // =========================================================================
    // Getter and Setter Tests
    // =========================================================================

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        private Cylinder cylinder;

        @BeforeEach
        void setUp() {
            cylinder = new Cylinder(3.0, 7.0, "green", "c1");
        }

        @Test
        @DisplayName("setRadius updates the radius")
        void setRadiusUpdates() {
            cylinder.setRadius(5.0);
            assertEquals(5.0, cylinder.getRadius(), DELTA);
        }

        @Test
        @DisplayName("setRadius with zero throws IllegalArgumentException")
        void setRadiusZeroThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> cylinder.setRadius(0.0));
        }

        @Test
        @DisplayName("setRadius with negative throws IllegalArgumentException")
        void setRadiusNegativeThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> cylinder.setRadius(-1.0));
        }

        @Test
        @DisplayName("setHeight updates the height")
        void setHeightUpdates() {
            cylinder.setHeight(10.0);
            assertEquals(10.0, cylinder.getHeight(), DELTA);
        }

        @Test
        @DisplayName("setHeight with zero throws IllegalArgumentException")
        void setHeightZeroThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> cylinder.setHeight(0.0));
        }

        @Test
        @DisplayName("setHeight with negative throws IllegalArgumentException")
        void setHeightNegativeThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> cylinder.setHeight(-5.0));
        }

        @Test
        @DisplayName("setColor updates the color")
        void setColorUpdates() {
            cylinder.setColor("purple");
            assertEquals("purple", cylinder.getColor());
        }

        @Test
        @DisplayName("setColor with null throws IllegalArgumentException")
        void setColorNullThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> cylinder.setColor(null));
        }

        @Test
        @DisplayName("setLabel updates the label")
        void setLabelUpdates() {
            cylinder.setLabel("newLabel");
            assertEquals("newLabel", cylinder.getLabel());
        }

        @Test
        @DisplayName("setLabel with null throws IllegalArgumentException")
        void setLabelNullThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> cylinder.setLabel(null));
        }
    }

    // =========================================================================
    // Calculation Tests
    // =========================================================================

    @Nested
    @DisplayName("Calculation Tests")
    class CalculationTests {

        // r=3, h=7 is the reference cylinder used for known-value tests
        private final Cylinder ref = new Cylinder(3.0, 7.0);

        @Test
        @DisplayName("Total surface area: 2πr(r+h) = 2π*3*10 = 60π ≈ 188.495")
        void totalSurfaceArea() {
            double expected = 2.0 * Math.PI * 3.0 * (3.0 + 7.0);
            assertEquals(expected, ref.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Volume: πr²h = π*9*7 = 63π ≈ 197.920")
        void volume() {
            double expected = Math.PI * 9.0 * 7.0;
            assertEquals(expected, ref.getVolume(), DELTA);
        }

        @Test
        @DisplayName("getBaseArea: πr² = 9π")
        void getBaseArea() {
            assertEquals(Math.PI * 9.0, ref.getBaseArea(), DELTA);
        }

        @Test
        @DisplayName("getLateralSurfaceArea: 2πrh = 42π")
        void getLateralSurfaceArea() {
            assertEquals(2.0 * Math.PI * 3.0 * 7.0, ref.getLateralSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("getDiameter: 2r = 6")
        void getDiameter() {
            assertEquals(6.0, ref.getDiameter(), DELTA);
        }

        @Test
        @DisplayName("getBaseCircumference: 2πr = 6π")
        void getBaseCircumference() {
            assertEquals(2.0 * Math.PI * 3.0, ref.getBaseCircumference(), DELTA);
        }

        @Test
        @DisplayName("getSlantHeight: √(r²+h²) = √(9+49) = √58")
        void getSlantHeight() {
            assertEquals(Math.sqrt(58.0), ref.getSlantHeight(), DELTA);
        }

        @Test
        @DisplayName("getAxialDiagonal: √((2r)²+h²) = √(36+49) = √85")
        void getAxialDiagonal() {
            assertEquals(Math.sqrt(85.0), ref.getAxialDiagonal(), DELTA);
        }

        @Test
        @DisplayName("getAspectRatio: h/(2r) = 7/6 ≈ 1.167")
        void getAspectRatio() {
            assertEquals(7.0 / 6.0, ref.getAspectRatio(), DELTA);
        }

        @Test
        @DisplayName("getInscribedSphereRadius: min(r, h/2) = min(3, 3.5) = 3")
        void getInscribedSphereRadius() {
            assertEquals(3.0, ref.getInscribedSphereRadius(), DELTA);
        }

        @Test
        @DisplayName("getInscribedSphereRadius: min(r, h/2) = min(5, 2) = 2 when h < 2r")
        void getInscribedSphereRadiusHeightLimited() {
            Cylinder c = new Cylinder(5.0, 4.0);
            assertEquals(2.0, c.getInscribedSphereRadius(), DELTA);
        }

        @Test
        @DisplayName("canFitSphere returns true when sphere is smaller than inscribed")
        void canFitSphereFits() {
            assertTrue(ref.canFitSphere(2.9));
        }

        @Test
        @DisplayName("canFitSphere returns false when sphere is too wide for radius")
        void canFitSphereTooWide() {
            assertFalse(ref.canFitSphere(3.0));
        }

        @Test
        @DisplayName("canFitSphere returns false when sphere diameter exceeds height")
        void canFitSphereTooTall() {
            Cylinder c = new Cylinder(5.0, 4.0); // inscribed radius = 2
            assertFalse(c.canFitSphere(2.5));
        }

        @Test
        @DisplayName("Uniform scale returns new cylinder with both dimensions scaled")
        void scaleUniform() {
            Cylinder scaled = ref.scale(2.0);
            assertEquals(6.0,  scaled.getRadius(), DELTA);
            assertEquals(14.0, scaled.getHeight(), DELTA);
            // Original unchanged
            assertEquals(3.0, ref.getRadius(), DELTA);
        }

        @Test
        @DisplayName("Per-axis scale returns new cylinder with independent scaling")
        void scalePerAxis() {
            Cylinder scaled = ref.scale(2.0, 3.0);
            assertEquals(6.0,  scaled.getRadius(), DELTA);
            assertEquals(21.0, scaled.getHeight(), DELTA);
        }

        @Test
        @DisplayName("scale with zero factor throws IllegalArgumentException")
        void scaleZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> ref.scale(0.0));
        }

        @Test
        @DisplayName("Per-axis scale with zero height factor throws IllegalArgumentException")
        void scalePerAxisZeroHeightThrows() {
            assertThrows(IllegalArgumentException.class, () -> ref.scale(1.0, 0.0));
        }
    }

    // =========================================================================
    // Boundary Tests
    // =========================================================================

    @Nested
    @DisplayName("Boundary Tests")
    class BoundaryTests {

        /**
         * Decision: radius = 0 is rejected — a cylinder with zero radius is
         * degenerate (it collapses to a line segment).
         */
        @Test
        @DisplayName("Zero radius throws IllegalArgumentException (boundary decision)")
        void zeroRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder(0.0, 5.0));
        }

        /**
         * Decision: height = 0 is rejected — a cylinder with zero height is
         * degenerate (it collapses to a disc with no volume).
         */
        @Test
        @DisplayName("Zero height throws IllegalArgumentException (boundary decision)")
        void zeroHeightThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder(3.0, 0.0));
        }

        @Test
        @DisplayName("Very small dimensions (1e-10) are accepted and produce correct results")
        void verySmallDimensions() {
            double r = 1e-10, h = 1e-10;
            Cylinder c = new Cylinder(r, h);
            assertEquals(2.0 * Math.PI * r * (r + h), c.getSurfaceArea(), 1e-28);
            assertEquals(Math.PI * r * r * h,          c.getVolume(),      1e-38);
        }

        @Test
        @DisplayName("Very large dimensions (1e10) are accepted and produce correct results")
        void veryLargeDimensions() {
            double r = 1e10, h = 1e10;
            Cylinder c = new Cylinder(r, h);
            assertEquals(2.0 * Math.PI * r * (r + h), c.getSurfaceArea(), 1e10);
            assertEquals(Math.PI * r * r * h,          c.getVolume(),      1e20);
        }

        @Test
        @DisplayName("Minimum positive double dimensions are accepted")
        void minimumPositiveDouble() {
            assertDoesNotThrow(() -> new Cylinder(Double.MIN_VALUE, Double.MIN_VALUE));
        }

        @Test
        @DisplayName("Negative radius throws IllegalArgumentException")
        void negativeRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder(-1.0, 5.0));
        }

        @Test
        @DisplayName("Negative height throws IllegalArgumentException")
        void negativeHeightThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder(3.0, -5.0));
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
                    () -> new Cylinder(3.0, 7.0, null));
        }

        @Test
        @DisplayName("Null label in constructor throws IllegalArgumentException")
        void nullLabelThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder(3.0, 7.0, "red", null));
        }

        @Test
        @DisplayName("NaN radius throws IllegalArgumentException")
        void nanRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder(Double.NaN, 5.0));
        }

        @Test
        @DisplayName("NaN height throws IllegalArgumentException")
        void nanHeightThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder(3.0, Double.NaN));
        }

        @Test
        @DisplayName("canFitSphere with zero radius throws IllegalArgumentException")
        void canFitSphereZeroRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder(5.0, 10.0).canFitSphere(0.0));
        }

        @Test
        @DisplayName("canFitSphere with negative radius throws IllegalArgumentException")
        void canFitSphereNegativeRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder(5.0, 10.0).canFitSphere(-1.0));
        }
    }

    // =========================================================================
    // Inheritance and Polymorphism Tests
    // =========================================================================

    @Nested
    @DisplayName("Inheritance and Polymorphism Tests")
    class InheritanceTests {

        @Test
        @DisplayName("Cylinder is an instance of Shape3D")
        void cylinderIsShape3D() {
            assertTrue(new Cylinder(3.0, 7.0) instanceof Shape3D);
        }

        @Test
        @DisplayName("Cylinder is an instance of ThreeDimensionalShape")
        void cylinderIsThreeDimensionalShape() {
            assertTrue(new Cylinder(3.0, 7.0) instanceof ThreeDimensionalShape);
        }

        @Test
        @DisplayName("Cylinder works correctly when referenced as Shape3D")
        void cylinderAsShape3D() {
            Shape3D shape = new Cylinder(3.0, 7.0);
            assertEquals(2.0 * Math.PI * 3.0 * 10.0, shape.getSurfaceArea(), DELTA);
            assertEquals(Math.PI * 9.0 * 7.0,         shape.getVolume(),      DELTA);
        }

        @Test
        @DisplayName("Cylinder works correctly when referenced as ThreeDimensionalShape")
        void cylinderAsThreeDimensionalShape() {
            ThreeDimensionalShape shape = new Cylinder(3.0, 7.0);
            assertEquals(2.0 * Math.PI * 3.0 * 10.0, shape.getSurfaceArea(), DELTA);
            assertEquals(Math.PI * 9.0 * 7.0,         shape.getVolume(),      DELTA);
        }

        @Test
        @DisplayName("getName returns 'Cylinder' through Shape3D reference")
        void getNameThroughShape3D() {
            Shape3D shape = new Cylinder(3.0, 7.0);
            assertEquals("Cylinder", shape.getName());
        }

        @Test
        @DisplayName("toString is overridden and contains 'Cylinder'")
        void toStringOverridden() {
            String s = new Cylinder(3.0, 7.0).toString();
            assertTrue(s.contains("Cylinder"));
            assertTrue(s.contains("3.00"));
            assertTrue(s.contains("7.00"));
        }

        @Test
        @DisplayName("equals and hashCode are consistent")
        void equalsAndHashCode() {
            Cylinder a = new Cylinder(3.0, 7.0, "blue", "x");
            Cylinder b = new Cylinder(3.0, 7.0, "blue", "x");
            Cylinder c = new Cylinder(3.0, 7.0, "blue", "y");

            assertEquals(a, b);
            assertNotEquals(a, c);
            assertEquals(a.hashCode(), b.hashCode());
        }

        @Test
        @DisplayName("Cylinder does not equal a different Shape3D subclass")
        void cylinderNotEqualToCube() {
            Shape3D cylinder = new Cylinder(4.0, 4.0);
            Shape3D cube     = new Cube(4.0);
            assertNotEquals(cylinder, cube);
        }
    }
}
