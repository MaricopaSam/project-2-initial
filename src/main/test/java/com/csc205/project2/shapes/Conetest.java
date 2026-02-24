package com.csc205.project2.shapes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for the {@link Cone} class.
 *
 * <p>Test categories:</p>
 * <ul>
 *   <li>Constructor tests — valid and invalid inputs, copy constructor</li>
 *   <li>Getter / setter tests — normal mutations, derived-field synchronisation,
 *       and validation</li>
 *   <li>Calculation tests — surface area, volume and cone-specific methods
 *       verified against known mathematical results</li>
 *   <li>Boundary tests — zero, very small, and very large dimension values</li>
 *   <li>Input validation tests — negative values and null inputs</li>
 *   <li>Inheritance / polymorphism tests — behaviour through {@link Shape3D}
 *       and {@link ThreeDimensionalShape} references</li>
 * </ul>
 *
 * <p><b>Boundary decision:</b> A radius or height of zero (or any non-positive
 * value) is physically meaningless and throws {@link IllegalArgumentException}.</p>
 *
 * <p><b>Derived-field contract:</b> {@code slantHeight} and {@code circumference}
 * are derived from {@code radius} and {@code height}. Tests verify that mutations
 * via setters keep those derived fields in sync.</p>
 */
@DisplayName("Cone Tests")
class ConeTest {

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
            Cone c = new Cone(3.0, 4.0);
            assertEquals(3.0,   c.getRadius(), DELTA);
            assertEquals(4.0,   c.getHeight(), DELTA);
            assertEquals("Cone",c.getName());
            assertEquals("white",c.getColor());
            assertEquals("",    c.getLabel());
        }

        @Test
        @DisplayName("Two-arg constructor derives slant height and circumference correctly")
        void twoArgConstructorDerivedFields() {
            Cone c = new Cone(3.0, 4.0);
            // slantHeight = √(9+16) = 5
            assertEquals(5.0, c.getSlantHeight(), DELTA);
            // circumference = 2π*3 = 6π
            assertEquals(2.0 * Math.PI * 3.0, c.getCircumference(), DELTA);
        }

        @Test
        @DisplayName("Three-arg constructor sets dimensions and color")
        void threeArgConstructor() {
            Cone c = new Cone(3.0, 4.0, "red");
            assertEquals(3.0,  c.getRadius(), DELTA);
            assertEquals(4.0,  c.getHeight(), DELTA);
            assertEquals("red",c.getColor());
            assertEquals("",   c.getLabel());
        }

        @Test
        @DisplayName("Four-arg constructor sets all fields")
        void fourArgConstructor() {
            Cone c = new Cone(3.0, 4.0, "red", "myCone");
            assertEquals(3.0,     c.getRadius(), DELTA);
            assertEquals(4.0,     c.getHeight(), DELTA);
            assertEquals("red",   c.getColor());
            assertEquals("myCone",c.getLabel());
        }

        @Test
        @DisplayName("Copy constructor creates independent copy")
        void copyConstructor() {
            Cone original = new Cone(3.0, 4.0, "blue", "orig");
            Cone copy     = new Cone(original);

            assertEquals(original.getRadius(),       copy.getRadius(),       DELTA);
            assertEquals(original.getHeight(),       copy.getHeight(),       DELTA);
            assertEquals(original.getSlantHeight(),  copy.getSlantHeight(),  DELTA);
            assertEquals(original.getCircumference(),copy.getCircumference(),DELTA);

            // Mutating copy must not affect original
            copy.setRadius(15.0);
            assertEquals(3.0, original.getRadius(), DELTA);
        }

        @Test
        @DisplayName("Copy constructor with null throws IllegalArgumentException")
        void copyConstructorNullThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cone(null));
        }

        @Test
        @DisplayName("Name is always 'Cone' regardless of constructor used")
        void nameIsAlwaysCone() {
            assertEquals("Cone", new Cone(1.0, 1.0).getName());
        }
    }

    // =========================================================================
    // Getter and Setter Tests
    // =========================================================================

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        private Cone cone;

        @BeforeEach
        void setUp() {
            cone = new Cone(3.0, 4.0, "red", "c1");
        }

        @Test
        @DisplayName("setRadius updates radius and re-derives slantHeight and circumference")
        void setRadiusUpdatesDerivedFields() {
            cone.setRadius(6.0);
            assertEquals(6.0,  cone.getRadius(), DELTA);
            // New slantHeight = √(36+16) = √52
            assertEquals(Math.sqrt(52.0), cone.getSlantHeight(),   DELTA);
            // New circumference = 2π*6 = 12π
            assertEquals(2.0 * Math.PI * 6.0, cone.getCircumference(), DELTA);
        }

        @Test
        @DisplayName("setRadius with zero throws IllegalArgumentException")
        void setRadiusZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> cone.setRadius(0.0));
        }

        @Test
        @DisplayName("setRadius with negative throws IllegalArgumentException")
        void setRadiusNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> cone.setRadius(-1.0));
        }

        @Test
        @DisplayName("setHeight updates height and re-derives slantHeight")
        void setHeightUpdatesDerivedFields() {
            cone.setHeight(8.0);
            assertEquals(8.0,  cone.getHeight(), DELTA);
            // New slantHeight = √(9+64) = √73
            assertEquals(Math.sqrt(73.0), cone.getSlantHeight(), DELTA);
            // circumference is unchanged (radius unchanged)
            assertEquals(2.0 * Math.PI * 3.0, cone.getCircumference(), DELTA);
        }

        @Test
        @DisplayName("setHeight with zero throws IllegalArgumentException")
        void setHeightZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> cone.setHeight(0.0));
        }

        @Test
        @DisplayName("setHeight with negative throws IllegalArgumentException")
        void setHeightNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> cone.setHeight(-2.0));
        }

        @Test
        @DisplayName("slantHeight is read-only (no setter exposed)")
        void slantHeightIsReadOnly() throws NoSuchMethodException {
            // Verify that no public setSlantHeight method exists
            assertThrows(NoSuchMethodException.class,
                    () -> Cone.class.getMethod("setSlantHeight", double.class));
        }

        @Test
        @DisplayName("circumference is read-only (no setter exposed)")
        void circumferenceIsReadOnly() throws NoSuchMethodException {
            assertThrows(NoSuchMethodException.class,
                    () -> Cone.class.getMethod("setCircumference", double.class));
        }

        @Test
        @DisplayName("setColor updates the color")
        void setColorUpdates() {
            cone.setColor("teal");
            assertEquals("teal", cone.getColor());
        }

        @Test
        @DisplayName("setColor with null throws IllegalArgumentException")
        void setColorNullThrows() {
            assertThrows(IllegalArgumentException.class, () -> cone.setColor(null));
        }

        @Test
        @DisplayName("setLabel updates the label")
        void setLabelUpdates() {
            cone.setLabel("newLabel");
            assertEquals("newLabel", cone.getLabel());
        }

        @Test
        @DisplayName("setLabel with null throws IllegalArgumentException")
        void setLabelNullThrows() {
            assertThrows(IllegalArgumentException.class, () -> cone.setLabel(null));
        }
    }

    // =========================================================================
    // Calculation Tests
    // =========================================================================

    @Nested
    @DisplayName("Calculation Tests")
    class CalculationTests {

        // 3-4-5 right triangle: r=3, h=4, l=5  (a Pythagorean triple)
        private final Cone ref = new Cone(3.0, 4.0);

        @Test
        @DisplayName("Slant height for 3-4-5 cone: √(9+16) = 5")
        void slantHeight345() {
            assertEquals(5.0, ref.getSlantHeight(), DELTA);
        }

        @Test
        @DisplayName("Circumference: 2π*3 = 6π")
        void circumference() {
            assertEquals(2.0 * Math.PI * 3.0, ref.getCircumference(), DELTA);
        }

        @Test
        @DisplayName("Total surface area: πr(r+l) = π*3*8 = 24π ≈ 75.398")
        void totalSurfaceArea() {
            double expected = Math.PI * 3.0 * (3.0 + 5.0);
            assertEquals(expected, ref.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Volume: (1/3)πr²h = (1/3)*π*9*4 = 12π ≈ 37.699")
        void volume() {
            double expected = (1.0 / 3.0) * Math.PI * 9.0 * 4.0;
            assertEquals(expected, ref.getVolume(), DELTA);
        }

        @Test
        @DisplayName("getBaseArea: πr² = 9π")
        void getBaseArea() {
            assertEquals(Math.PI * 9.0, ref.getBaseArea(), DELTA);
        }

        @Test
        @DisplayName("getLateralSurfaceArea: πrl = π*3*5 = 15π")
        void getLateralSurfaceArea() {
            assertEquals(Math.PI * 3.0 * 5.0, ref.getLateralSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("getDiameter: 2r = 6")
        void getDiameter() {
            assertEquals(6.0, ref.getDiameter(), DELTA);
        }

        @Test
        @DisplayName("getHalfApexAngleRadians: arctan(3/4)")
        void getHalfApexAngleRadians() {
            assertEquals(Math.atan(3.0 / 4.0), ref.getHalfApexAngleRadians(), DELTA);
        }

        @Test
        @DisplayName("getHalfApexAngleDegrees: toDegrees(arctan(3/4)) ≈ 36.87°")
        void getHalfApexAngleDegrees() {
            assertEquals(Math.toDegrees(Math.atan(3.0 / 4.0)),
                    ref.getHalfApexAngleDegrees(), DELTA);
        }

        @Test
        @DisplayName("getFullApexAngleDegrees: twice the half angle")
        void getFullApexAngleDegrees() {
            assertEquals(2.0 * Math.toDegrees(Math.atan(3.0 / 4.0)),
                    ref.getFullApexAngleDegrees(), DELTA);
        }

        @Test
        @DisplayName("getInscribedSphereRadius: (r*h)/(r+l) = 12/8 = 1.5")
        void getInscribedSphereRadius() {
            assertEquals(1.5, ref.getInscribedSphereRadius(), DELTA);
        }

        @Test
        @DisplayName("getCentroidHeight: h/4 = 1.0")
        void getCentroidHeight() {
            assertEquals(1.0, ref.getCentroidHeight(), DELTA);
        }

        @Test
        @DisplayName("isEquilateral returns false for 3-4-5 cone (l=5 ≠ 2r=6)")
        void isEquilateralFalse() {
            assertFalse(ref.isEquilateral());
        }

        @Test
        @DisplayName("isEquilateral returns true when slant height equals diameter")
        void isEquilateralTrue() {
            // For equilateral: l = 2r → √(r²+h²) = 2r → h = r*√3
            double r = 3.0;
            double h = r * Math.sqrt(3.0);
            Cone equilateral = new Cone(r, h);
            assertTrue(equilateral.isEquilateral());
        }

        @Test
        @DisplayName("canFitSphere returns true when sphere is smaller than inscribed")
        void canFitSphereFits() {
            assertTrue(ref.canFitSphere(1.4));
        }

        @Test
        @DisplayName("canFitSphere returns false when sphere equals inscribed radius")
        void canFitSphereExact() {
            assertFalse(ref.canFitSphere(1.5));
        }

        @Test
        @DisplayName("Uniform scale returns new cone with both dimensions scaled")
        void scaleUniform() {
            Cone scaled = ref.scale(2.0);
            assertEquals(6.0,  scaled.getRadius(), DELTA);
            assertEquals(8.0,  scaled.getHeight(), DELTA);
            // Slant height of scaled cone = √(36+64) = 10
            assertEquals(10.0, scaled.getSlantHeight(), DELTA);
            // Original unchanged
            assertEquals(3.0, ref.getRadius(), DELTA);
        }

        @Test
        @DisplayName("Per-axis scale returns new cone with independent scaling")
        void scalePerAxis() {
            Cone scaled = ref.scale(2.0, 3.0);
            assertEquals(6.0,  scaled.getRadius(), DELTA);
            assertEquals(12.0, scaled.getHeight(), DELTA);
        }

        @Test
        @DisplayName("scale with zero factor throws IllegalArgumentException")
        void scaleZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> ref.scale(0.0));
        }

        @Test
        @DisplayName("Per-axis scale with zero height factor throws IllegalArgumentException")
        void scalePerAxisZeroThrows() {
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
         * Decision: radius = 0 collapses the cone to a line segment.
         * {@link IllegalArgumentException} is the documented contract.
         */
        @Test
        @DisplayName("Zero radius throws IllegalArgumentException (boundary decision)")
        void zeroRadiusThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cone(0.0, 4.0));
        }

        /**
         * Decision: height = 0 collapses the cone to a flat disc with no volume.
         * {@link IllegalArgumentException} is the documented contract.
         */
        @Test
        @DisplayName("Zero height throws IllegalArgumentException (boundary decision)")
        void zeroHeightThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cone(3.0, 0.0));
        }

        @Test
        @DisplayName("Very small dimensions (1e-10) are accepted and produce correct results")
        void verySmallDimensions() {
            double r = 1e-10, h = 1e-10;
            Cone c = new Cone(r, h);
            double l = Math.sqrt(r * r + h * h);
            assertEquals(Math.PI * r * (r + l), c.getSurfaceArea(), 1e-28);
            assertEquals((1.0 / 3.0) * Math.PI * r * r * h, c.getVolume(), 1e-38);
        }

        @Test
        @DisplayName("Very large dimensions (1e10) are accepted and produce correct results")
        void veryLargeDimensions() {
            double r = 1e10, h = 1e10;
            Cone c = new Cone(r, h);
            double l = Math.sqrt(r * r + h * h);
            assertEquals(Math.PI * r * (r + l), c.getSurfaceArea(), 1e10);
            assertEquals((1.0 / 3.0) * Math.PI * r * r * h, c.getVolume(), 1e20);
        }

        @Test
        @DisplayName("Minimum positive double dimensions are accepted")
        void minimumPositiveDouble() {
            assertDoesNotThrow(() -> new Cone(Double.MIN_VALUE, Double.MIN_VALUE));
        }

        @Test
        @DisplayName("Negative radius throws IllegalArgumentException")
        void negativeRadiusThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cone(-1.0, 4.0));
        }

        @Test
        @DisplayName("Negative height throws IllegalArgumentException")
        void negativeHeightThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cone(3.0, -1.0));
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
                    () -> new Cone(3.0, 4.0, null));
        }

        @Test
        @DisplayName("Null label in constructor throws IllegalArgumentException")
        void nullLabelThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone(3.0, 4.0, "red", null));
        }

        @Test
        @DisplayName("NaN radius throws IllegalArgumentException")
        void nanRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone(Double.NaN, 4.0));
        }

        @Test
        @DisplayName("NaN height throws IllegalArgumentException")
        void nanHeightThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone(3.0, Double.NaN));
        }

        @Test
        @DisplayName("Negative infinity radius throws IllegalArgumentException")
        void negativeInfinityRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone(Double.NEGATIVE_INFINITY, 4.0));
        }

        @Test
        @DisplayName("canFitSphere with zero radius throws IllegalArgumentException")
        void canFitSphereZeroThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone(3.0, 4.0).canFitSphere(0.0));
        }

        @Test
        @DisplayName("canFitSphere with negative radius throws IllegalArgumentException")
        void canFitSphereNegativeThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone(3.0, 4.0).canFitSphere(-0.5));
        }
    }

    // =========================================================================
    // Inheritance and Polymorphism Tests
    // =========================================================================

    @Nested
    @DisplayName("Inheritance and Polymorphism Tests")
    class InheritanceTests {

        @Test
        @DisplayName("Cone is an instance of Shape3D")
        void coneIsShape3D() {
            assertTrue(new Cone(3.0, 4.0) instanceof Shape3D);
        }

        @Test
        @DisplayName("Cone is an instance of ThreeDimensionalShape")
        void coneIsThreeDimensionalShape() {
            assertTrue(new Cone(3.0, 4.0) instanceof ThreeDimensionalShape);
        }

        @Test
        @DisplayName("Cone works correctly when referenced as Shape3D")
        void coneAsShape3D() {
            Shape3D shape = new Cone(3.0, 4.0);
            double expectedSA = Math.PI * 3.0 * (3.0 + 5.0); // 24π
            double expectedV  = (1.0 / 3.0) * Math.PI * 9.0 * 4.0; // 12π
            assertEquals(expectedSA, shape.getSurfaceArea(), DELTA);
            assertEquals(expectedV,  shape.getVolume(),      DELTA);
        }

        @Test
        @DisplayName("Cone works correctly when referenced as ThreeDimensionalShape")
        void coneAsThreeDimensionalShape() {
            ThreeDimensionalShape shape = new Cone(3.0, 4.0);
            assertEquals(Math.PI * 3.0 * 8.0,              shape.getSurfaceArea(), DELTA);
            assertEquals((1.0 / 3.0) * Math.PI * 9.0 * 4.0, shape.getVolume(),    DELTA);
        }

        @Test
        @DisplayName("getName returns 'Cone' through Shape3D reference")
        void getNameThroughShape3D() {
            Shape3D shape = new Cone(3.0, 4.0);
            assertEquals("Cone", shape.getName());
        }

        @Test
        @DisplayName("toString is overridden and contains 'Cone' with key values")
        void toStringOverridden() {
            String s = new Cone(3.0, 4.0).toString();
            assertTrue(s.contains("Cone"));
            assertTrue(s.contains("3.00"));
            assertTrue(s.contains("4.00"));
            assertTrue(s.contains("5.00")); // slant height
        }

        @Test
        @DisplayName("equals and hashCode are consistent")
        void equalsAndHashCode() {
            Cone a = new Cone(3.0, 4.0, "blue", "x");
            Cone b = new Cone(3.0, 4.0, "blue", "x");
            Cone c = new Cone(3.0, 4.0, "blue", "y");

            assertEquals(a, b);
            assertNotEquals(a, c);
            assertEquals(a.hashCode(), b.hashCode());
        }

        @Test
        @DisplayName("Cone does not equal a different Shape3D subclass with same radius")
        void coneNotEqualToSphere() {
            Shape3D cone   = new Cone(3.0, 4.0);
            Shape3D sphere = new Sphere(3.0);
            assertNotEquals(cone, sphere);
        }

        @Test
        @DisplayName("Cone does not equal a Cylinder with same radius and height")
        void coneNotEqualToCylinder() {
            Shape3D cone     = new Cone(3.0, 4.0);
            Shape3D cylinder = new Cylinder(3.0, 4.0);
            assertNotEquals(cone, cylinder);
        }
    }
}
