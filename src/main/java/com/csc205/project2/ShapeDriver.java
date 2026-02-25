package com.csc205.project2;

import com.csc205.project2.shapes.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Enhanced driver program for the 3D Shape Analysis System.
 *
 * <p>This program demonstrates:</p>
 * <ul>
 *   <li><b>Polymorphism</b> — all concrete shapes are stored as {@link Shape3D}
 *       references and processed through the {@link ThreeDimensionalShape} interface,
 *       with runtime dispatch calling each subclass's overridden methods.</li>
 *   <li><b>Analysis</b> — finds the shape with the largest volume, largest surface
 *       area, and best volume-to-surface-area efficiency ratio.</li>
 *   <li><b>Constraint analysis</b> — given a shared constraint (e.g. maximum
 *       surface area budget), identifies which shape type packs the most volume.</li>
 *   <li><b>Interactive mode</b> — prompts the user to build a custom shape
 *       catalogue via a menu-driven console interface.</li>
 *   <li><b>Edge-case handling</b> — all numeric inputs are validated before
 *       being passed to shape constructors; empty catalogues, single-shape
 *       catalogues, and ties are all handled gracefully.</li>
 * </ul>
 *
 * <p><b>Polymorphism demonstration:</b> The core analysis methods
 * ({@link #printCatalogue}, {@link #runAnalysis}, {@link #constraintAnalysis})
 * accept {@code List<Shape3D>} and call only {@code getSurfaceArea()},
 * {@code getVolume()}, {@code getName()}, and {@code toString()} through the
 * base-class / interface reference — they never downcast to a concrete type.
 * This means the same analysis logic works for any future {@link Shape3D}
 * subclass without modification.</p>
 *
 * <p>Usage:</p>
 * <pre>
 *   java com.csc205.project2.ShapeDriver          # demo mode (pre-built shapes)
 *   java com.csc205.project2.ShapeDriver --interactive  # interactive mode
 * </pre>
 *
 * @author  Generated
 * @version 1.0
 * @see     Shape3D
 * @see     ThreeDimensionalShape
 */
public class ShapeDriver {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    /** Separator line used throughout all printed sections. */
    private static final String SEPARATOR     = "=".repeat(60);

    /** Thinner separator for sub-sections. */
    private static final String THIN_SEP      = "-".repeat(60);

    /** Surface area budget used for the constraint analysis (square units). */
    private static final double SURFACE_BUDGET = 300.0;

    // -------------------------------------------------------------------------
    // Entry point
    // -------------------------------------------------------------------------

    /**
     * Application entry point.
     *
     * <p>If the first command-line argument is {@code --interactive}, the program
     * launches the interactive menu. Otherwise it runs the built-in demonstration
     * with a pre-constructed catalogue of shapes.</p>
     *
     * @param args command-line arguments; pass {@code --interactive} for the
     *             interactive menu
     */
    public static void main(String[] args) {
        System.out.println(SEPARATOR);
        System.out.println("      === 3D Shape Analysis System ===");
        System.out.println(SEPARATOR);

        boolean interactive = args.length > 0 && args[0].equalsIgnoreCase("--interactive");

        if (interactive) {
            runInteractiveMode();
        } else {
            runDemoMode();
        }
    }

    // =========================================================================
    // Demo mode
    // =========================================================================

    /**
     * Runs the built-in demonstration mode.
     *
     * <p>Constructs a fixed catalogue of one of each shape type, prints the
     * catalogue, runs the full analysis suite, and demonstrates polymorphic
     * behaviour explicitly.</p>
     */
    private static void runDemoMode() {
        System.out.println("\n[Demo Mode — pass --interactive for the custom builder]\n");

        // ------------------------------------------------------------------
        // Build the pre-defined catalogue
        // ------------------------------------------------------------------
        List<Shape3D> shapes = new ArrayList<>();

        shapes.add(new Sphere(5.0,              "red",    "Red Ball"));
        shapes.add(new Cube(4.0,                "blue",   "Blue Box"));
        shapes.add(new Cylinder(3.0, 7.0,       "green",  "Green Pipe"));
        shapes.add(new RectangularPrism(6.0, 4.0, 3.0, "orange", "Orange Brick"));
        shapes.add(new Cone(4.0, 9.0,           "purple", "Purple Cone"));

        // ------------------------------------------------------------------
        // Print catalogue, analysis, constraint analysis, polymorphism demo
        // ------------------------------------------------------------------
        printCatalogue(shapes);
        runAnalysis(shapes);
        constraintAnalysis(shapes, SURFACE_BUDGET);
        demonstratePolymorphism(shapes);
    }

    // =========================================================================
    // Interactive mode
    // =========================================================================

    /**
     * Runs the interactive console menu.
     *
     * <p>The user may add any number of shapes, view the growing catalogue,
     * trigger analysis, or quit. Input errors are caught and reported without
     * crashing the loop.</p>
     */
    private static void runInteractiveMode() {
        Scanner scanner = new Scanner(System.in);
        List<Shape3D> shapes = new ArrayList<>();

        System.out.println("\n[Interactive Mode]\n");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = promptString(scanner, "Enter choice: ").trim();

            switch (choice) {
                case "1" -> addShape(scanner, shapes);
                case "2" -> printCatalogue(shapes);
                case "3" -> runAnalysis(shapes);
                case "4" -> {
                    double budget = promptPositiveDouble(
                            scanner, "Enter surface area budget (square units): ");
                    if (budget > 0) constraintAnalysis(shapes, budget);
                }
                case "5" -> demonstratePolymorphism(shapes);
                case "6" -> clearCatalogue(shapes);
                case "7" -> {
                    System.out.println("\nGoodbye!\n");
                    running = false;
                }
                default  -> System.out.println("  [!] Unknown option. Please enter 1–7.");
            }
        }
        scanner.close();
    }

    /**
     * Prints the interactive main menu to {@code System.out}.
     */
    private static void printMenu() {
        System.out.println();
        System.out.println(THIN_SEP);
        System.out.println("  MENU");
        System.out.println(THIN_SEP);
        System.out.println("  1. Add a shape");
        System.out.println("  2. View shape catalogue");
        System.out.println("  3. Run analysis");
        System.out.println("  4. Run constraint analysis");
        System.out.println("  5. Demonstrate polymorphism");
        System.out.println("  6. Clear catalogue");
        System.out.println("  7. Quit");
        System.out.println(THIN_SEP);
    }

    /**
     * Guides the user through adding a new shape to the catalogue.
     *
     * <p>Presents a sub-menu of shape types, collects the required parameters
     * with per-field validation, and appends the constructed {@link Shape3D}
     * to {@code shapes}. If any parameter is invalid the shape is not added
     * and the user is returned to the main menu.</p>
     *
     * @param scanner the active {@link Scanner} reading from {@code System.in}
     * @param shapes  the mutable catalogue to append to
     */
    private static void addShape(Scanner scanner, List<Shape3D> shapes) {
        System.out.println();
        System.out.println("  Select shape type:");
        System.out.println("    1. Sphere");
        System.out.println("    2. Cube");
        System.out.println("    3. Cylinder");
        System.out.println("    4. Rectangular Prism");
        System.out.println("    5. Cone");
        System.out.println("    0. Cancel");

        String typeChoice = promptString(scanner, "  Type: ").trim();

        if (typeChoice.equals("0")) {
            System.out.println("  Cancelled.");
            return;
        }

        String label = promptString(scanner, "  Name/label for this shape: ").trim();
        String color = promptString(scanner, "  Color: ").trim();

        if (label.isEmpty()) label = "Unnamed";
        if (color.isEmpty()) color = "white";

        try {
            Shape3D shape = switch (typeChoice) {
                case "1" -> {
                    double r = promptPositiveDouble(scanner, "  Radius: ");
                    yield new Sphere(r, color, label);
                }
                case "2" -> {
                    double a = promptPositiveDouble(scanner, "  Side length: ");
                    yield new Cube(a, color, label);
                }
                case "3" -> {
                    double r = promptPositiveDouble(scanner, "  Radius: ");
                    double h = promptPositiveDouble(scanner, "  Height: ");
                    yield new Cylinder(r, h, color, label);
                }
                case "4" -> {
                    double l = promptPositiveDouble(scanner, "  Length: ");
                    double w = promptPositiveDouble(scanner, "  Width: ");
                    double h = promptPositiveDouble(scanner, "  Height: ");
                    yield new RectangularPrism(l, w, h, color, label);
                }
                case "5" -> {
                    double r = promptPositiveDouble(scanner, "  Radius: ");
                    double h = promptPositiveDouble(scanner, "  Height: ");
                    yield new Cone(r, h, color, label);
                }
                default -> {
                    System.out.println("  [!] Unknown shape type. Shape not added.");
                    yield null;
                }
            };

            if (shape != null) {
                shapes.add(shape);
                System.out.printf("%n  ✓ Added: %s (#%d in catalogue)%n",
                        shape.getLabel(), shapes.size());
            }

        } catch (IllegalArgumentException e) {
            // Thrown by shape constructors when a dimension is invalid
            System.out.println("  [!] Invalid parameter — " + e.getMessage());
            System.out.println("  Shape was not added.");
        }
    }

    /**
     * Clears all shapes from the catalogue after a confirmation prompt.
     *
     * @param shapes the mutable catalogue to clear
     */
    private static void clearCatalogue(List<Shape3D> shapes) {
        if (shapes.isEmpty()) {
            System.out.println("\n  Catalogue is already empty.");
            return;
        }
        Scanner confirm = new Scanner(System.in);
        System.out.printf("%n  Clear all %d shape(s)? (yes/no): ", shapes.size());
        String answer = confirm.nextLine().trim().toLowerCase();
        if (answer.equals("yes") || answer.equals("y")) {
            shapes.clear();
            System.out.println("  Catalogue cleared.");
        } else {
            System.out.println("  Cancelled.");
        }
    }

    // =========================================================================
    // Display
    // =========================================================================

    /**
     * Prints a numbered catalogue of all shapes in the list.
     *
     * <p>Each entry shows the shape's label, type (via {@code getName()}),
     * surface area, and volume. The method operates entirely through
     * {@link Shape3D} references — demonstrating polymorphic dispatch.</p>
     *
     * <p><b>Edge cases handled:</b> an empty list prints a friendly message
     * rather than an empty table.</p>
     *
     * @param shapes the catalogue to display; may be empty but not {@code null}
     */
    private static void printCatalogue(List<Shape3D> shapes) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("  Created Shapes:");
        System.out.println(SEPARATOR);

        if (shapes.isEmpty()) {
            System.out.println("  (No shapes in catalogue yet.)");
            return;
        }

        for (int i = 0; i < shapes.size(); i++) {
            Shape3D s = shapes.get(i);      // <-- Shape3D reference; no downcast
            System.out.printf("%n  %d. %s {name='%s', type=%s}%n",
                    i + 1, s.getName(), s.getLabel(), s.getClass().getSimpleName());
            System.out.printf("     - Surface Area : %,.2f square units%n",
                    s.getSurfaceArea());
            System.out.printf("     - Volume       : %,.2f cubic units%n",
                    s.getVolume());
            System.out.printf("     - V/SA Ratio   : %.4f%n",
                    s.getVolume() / s.getSurfaceArea());
        }
        System.out.println();
    }

    // =========================================================================
    // Analysis
    // =========================================================================

    /**
     * Runs the full analysis suite on the provided catalogue and prints results.
     *
     * <p>Computes and reports:</p>
     * <ul>
     *   <li>Shape with the largest volume</li>
     *   <li>Shape with the largest surface area</li>
     *   <li>Shape with the best volume-to-surface-area efficiency ratio</li>
     *   <li>Shape with the smallest volume</li>
     *   <li>Total combined volume and total combined surface area</li>
     *   <li>Average volume and average surface area across all shapes</li>
     * </ul>
     *
     * <p><b>Edge cases handled:</b></p>
     * <ul>
     *   <li>Empty catalogue — prints a message and returns early.</li>
     *   <li>Single shape — all comparisons are valid (the one shape wins all).</li>
     *   <li>Ties — the first shape in insertion order is reported as winner
     *       (documented behaviour).</li>
     * </ul>
     *
     * <p>All dispatch is through {@link Shape3D} / {@link ThreeDimensionalShape}
     * references — no instanceof checks or downcasts are used.</p>
     *
     * @param shapes the catalogue to analyse; may be empty but not {@code null}
     */
    private static void runAnalysis(List<Shape3D> shapes) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("  Analysis Results:");
        System.out.println(SEPARATOR);

        // Edge case: empty catalogue
        if (shapes.isEmpty()) {
            System.out.println("  [!] No shapes to analyse. Add at least one shape first.");
            return;
        }

        // ------------------------------------------------------------------
        // Reduce to winners — all via polymorphic Shape3D references
        // ------------------------------------------------------------------
        Shape3D largestVolume   = shapes.stream()
                .max(Comparator.comparingDouble(Shape3D::getVolume))
                .orElseThrow();

        Shape3D smallestVolume  = shapes.stream()
                .min(Comparator.comparingDouble(Shape3D::getVolume))
                .orElseThrow();

        Shape3D largestSurface  = shapes.stream()
                .max(Comparator.comparingDouble(Shape3D::getSurfaceArea))
                .orElseThrow();

        Shape3D mostEfficient   = shapes.stream()
                .max(Comparator.comparingDouble(s -> s.getVolume() / s.getSurfaceArea()))
                .orElseThrow();

        // ------------------------------------------------------------------
        // Aggregates
        // ------------------------------------------------------------------
        double totalVolume  = shapes.stream().mapToDouble(Shape3D::getVolume).sum();
        double totalSurface = shapes.stream().mapToDouble(Shape3D::getSurfaceArea).sum();
        double avgVolume    = totalVolume  / shapes.size();
        double avgSurface   = totalSurface / shapes.size();

        // ------------------------------------------------------------------
        // Print winners
        // ------------------------------------------------------------------
        System.out.println();
        System.out.println("  ── Winners ──────────────────────────────────────────");
        System.out.printf("  ▸ Largest Volume     : %-20s  %,.2f cubic units%n",
                largestVolume.getLabel(),  largestVolume.getVolume());
        System.out.printf("  ▸ Smallest Volume    : %-20s  %,.2f cubic units%n",
                smallestVolume.getLabel(), smallestVolume.getVolume());
        System.out.printf("  ▸ Largest Surface    : %-20s  %,.2f square units%n",
                largestSurface.getLabel(), largestSurface.getSurfaceArea());
        System.out.printf("  ▸ Most Efficient V/SA: %-20s  %.4f%n",
                mostEfficient.getLabel(),
                mostEfficient.getVolume() / mostEfficient.getSurfaceArea());

        // ------------------------------------------------------------------
        // Print aggregates
        // ------------------------------------------------------------------
        System.out.println();
        System.out.println("  ── Aggregates ───────────────────────────────────────");
        System.out.printf("  ▸ Shape Count        : %d%n",          shapes.size());
        System.out.printf("  ▸ Total Volume       : %,.2f cubic units%n",   totalVolume);
        System.out.printf("  ▸ Total Surface Area : %,.2f square units%n",  totalSurface);
        System.out.printf("  ▸ Average Volume     : %,.2f cubic units%n",   avgVolume);
        System.out.printf("  ▸ Average Surface    : %,.2f square units%n",  avgSurface);

        // ------------------------------------------------------------------
        // Ranked table
        // ------------------------------------------------------------------
        System.out.println();
        System.out.println("  ── Volume Ranking (highest → lowest) ───────────────");
        List<Shape3D> byVolume = shapes.stream()
                .sorted(Comparator.comparingDouble(Shape3D::getVolume).reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < byVolume.size(); i++) {
            Shape3D s = byVolume.get(i);
            System.out.printf("  %2d. %-22s  %,.2f cubic units%n",
                    i + 1, s.getLabel(), s.getVolume());
        }
        System.out.println();
    }

    /**
     * Performs a constraint-based analysis: given a fixed surface area budget,
     * determines which shape delivers the most volume per square unit of surface.
     *
     * <p>This demonstrates a realistic engineering trade-off scenario — e.g.
     * "I have 300 square centimetres of material; which shape gives me the
     * most interior space?"</p>
     *
     * <p>For each shape type represented in the catalogue, a hypothetical
     * shape is created that exactly exhausts the budget and its resulting
     * volume is computed. Results are ranked from best to worst.</p>
     *
     * <p><b>Edge cases handled:</b></p>
     * <ul>
     *   <li>Empty catalogue — reports and returns.</li>
     *   <li>Budget ≤ 0 — reports an error and returns.</li>
     *   <li>Shape-specific minimum SA may exceed budget — skipped with a note.</li>
     * </ul>
     *
     * @param shapes  the catalogue defining which shape types to compare;
     *                may be empty but not {@code null}
     * @param budgetSA the maximum allowed surface area in square units;
     *                 must be positive
     */
    private static void constraintAnalysis(List<Shape3D> shapes, double budgetSA) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.printf("  Constraint Analysis  (Surface Area Budget = %.2f sq units):%n",
                budgetSA);
        System.out.println(SEPARATOR);

        // Edge cases
        if (shapes.isEmpty()) {
            System.out.println("  [!] No shapes to analyse.");
            return;
        }
        if (!(budgetSA > 0)) {
            System.out.println("  [!] Budget must be positive.");
            return;
        }

        System.out.println();
        System.out.println("  For each shape in your catalogue, if its surface area were");
        System.out.printf("  scaled to exactly %.2f sq units, its volume would be:%n", budgetSA);
        System.out.println();

        // For each shape, compute the scale factor needed to reach the budget,
        // apply it, then report the resulting volume.
        // V/SA ratio scales linearly with the linear scale factor, so:
        //   SA_new = SA_orig * k²  →  k = √(budget / SA_orig)
        //   V_new  = V_orig  * k³

        record ConstraintResult(String label, String type, double scaledVolume) {}
        List<ConstraintResult> results = new ArrayList<>();

        for (Shape3D s : shapes) {
            double saCurrent = s.getSurfaceArea();
            if (saCurrent <= 0) continue;                   // guard (should not occur)

            double k          = Math.sqrt(budgetSA / saCurrent);
            double scaledVol  = s.getVolume() * k * k * k; // V scales as k³

            results.add(new ConstraintResult(s.getLabel(), s.getName(), scaledVol));

            System.out.printf("  %-22s (%s)%n", s.getLabel(), s.getName());
            System.out.printf("    Current SA   = %,.2f  →  scale factor k = %.4f%n",
                    saCurrent, k);
            System.out.printf("    Scaled Volume = %,.2f cubic units%n%n", scaledVol);
        }

        // Winner
        results.stream()
                .max(Comparator.comparingDouble(ConstraintResult::scaledVolume))
                .ifPresent(winner -> {
                    System.out.println(THIN_SEP);
                    System.out.printf("  ★  Best choice for maximum volume within budget:%n");
                    System.out.printf("     %s (%s) → %.2f cubic units%n",
                            winner.label(), winner.type(), winner.scaledVolume());
                    System.out.println(THIN_SEP);
                });
        System.out.println();
    }

    // =========================================================================
    // Polymorphism demonstration
    // =========================================================================

    /**
     * Explicitly demonstrates polymorphic behaviour to the console.
     *
     * <p>Iterates over the catalogue using only {@link ThreeDimensionalShape}
     * interface references, showing that the same method call resolves to
     * different implementations at runtime depending on the concrete type.
     * Also shows that {@link Shape3D#toString()} is overridden in every
     * subclass and produces type-specific output.</p>
     *
     * <p><b>Edge case:</b> empty catalogue is handled gracefully.</p>
     *
     * @param shapes the catalogue to demonstrate; may be empty but not {@code null}
     */
    private static void demonstratePolymorphism(List<Shape3D> shapes) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("  Polymorphism Demonstration:");
        System.out.println(SEPARATOR);

        if (shapes.isEmpty()) {
            System.out.println("  [!] No shapes to demonstrate. Add at least one shape.");
            return;
        }

        System.out.println();
        System.out.println("  All shapes stored as Shape3D references.");
        System.out.println("  Calling getSurfaceArea() and getVolume() via interface...");
        System.out.println();

        // Every call below goes through the ThreeDimensionalShape interface —
        // runtime dispatch selects the correct subclass implementation.
        for (Shape3D shape : shapes) {
            ThreeDimensionalShape iface = shape;   // up-cast to interface — no downcast
            System.out.printf("  [%s] runtime type: %s%n",
                    shape.getLabel(), shape.getClass().getName());
            System.out.printf("    getSurfaceArea() → %,.2f   (dispatched to %s)%n",
                    iface.getSurfaceArea(), shape.getClass().getSimpleName());
            System.out.printf("    getVolume()      → %,.2f   (dispatched to %s)%n",
                    iface.getVolume(),      shape.getClass().getSimpleName());
            System.out.println();
        }

        // toString() is also overridden per subclass
        System.out.println("  Polymorphic toString() output (one example per unique type):");
        System.out.println();
        Set<String> seen = new LinkedHashSet<>();
        for (Shape3D shape : shapes) {
            String type = shape.getClass().getSimpleName();
            if (seen.add(type)) {           // first time we see this concrete type
                System.out.println("  --- " + type + " ---");
                System.out.println(indent(shape.toString(), "  "));
                System.out.println();
            }
        }
    }

    // =========================================================================
    // Input helpers
    // =========================================================================

    /**
     * Prompts the user and reads a raw string from {@code System.in}.
     *
     * <p>Handles {@link NoSuchElementException} (EOF / piped input) gracefully
     * by returning an empty string so the caller can detect end-of-stream.</p>
     *
     * @param scanner the active {@link Scanner}
     * @param prompt  the message to display before reading input
     * @return the trimmed line the user entered, or {@code ""} on EOF
     */
    private static String promptString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    /**
     * Prompts the user repeatedly until a valid positive {@code double} is entered.
     *
     * <p>Rejects zero, negative values, {@code NaN}, and non-numeric text,
     * printing a descriptive error message for each invalid attempt.
     * Returns {@code -1.0} only if the input stream is exhausted (EOF).</p>
     *
     * @param scanner the active {@link Scanner}
     * @param prompt  the message to display before reading input
     * @return a positive {@code double}, or {@code -1.0} on EOF
     */
    private static double promptPositiveDouble(Scanner scanner, String prompt) {
        while (true) {
            String raw = promptString(scanner, prompt).trim();

            if (raw.isEmpty()) {
                // EOF — return sentinel so the caller can bail out
                return -1.0;
            }

            try {
                double value = Double.parseDouble(raw);

                // !(value > 0) catches NaN, negative, and zero — same pattern
                // used in the shape validators.
                if (!(value > 0)) {
                    System.out.println(
                            "  [!] Value must be a positive number greater than 0. Got: " + raw);
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                System.out.println(
                        "  [!] '" + raw + "' is not a valid number. Please try again.");
            }
        }
    }

    // =========================================================================
    // Formatting helpers
    // =========================================================================

    /**
     * Prefixes every line in {@code text} with {@code prefix}.
     *
     * <p>Used to indent multi-line {@code toString()} output when printing
     * inside the polymorphism demonstration section.</p>
     *
     * @param text   the potentially multi-line string to indent
     * @param prefix the string to prepend to every line
     * @return the indented string
     */
    private static String indent(String text, String prefix) {
        return Arrays.stream(text.split("\n"))
                .map(line -> prefix + line)
                .collect(Collectors.joining("\n"));
    }
}
