package com.embabel.learning.common.curriculum;

import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaCall;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaCodeUnit;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.junit.jupiter.api.Test;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Learning tests may open a network socket only when the calling type or
 * method is explicitly annotated {@code @Tag("live")}.
 */
class LearningTestsDoNotOpenSocketsTest {

    private static final Set<String> SOCKET_TYPES = Set.of(
            "java.net.Socket",
            "java.net.ServerSocket",
            "java.net.DatagramSocket",
            "java.net.MulticastSocket"
    );

    private static final ArchCondition<JavaClass> NO_UNTAGGED_SOCKET = new ArchCondition<>(
            "not open a network socket unless explicitly tagged live"
    ) {
        @Override
        public void check(JavaClass item, ConditionEvents events) {
            List<JavaCall<?>> calls = new ArrayList<>();
            calls.addAll(item.getConstructorCallsFromSelf());
            calls.addAll(item.getMethodCallsFromSelf());
            for (JavaCall<?> call : calls) {
                if (explicitlyLive(call.getOrigin()) || !opensSocket(call)) {
                    continue;
                }
                events.add(SimpleConditionEvent.violated(
                        item,
                        item.getName() + " opens a network socket via " + call.getDescription()
                ));
            }
        }
    };

    private static final ArchRule RULE = classes().should(NO_UNTAGGED_SOCKET);

    @Test
    void learningTestsDoNotOpenSocketsUnlessTaggedLive() throws IOException {
        Path fixtures = compileSocketFixtures();
        JavaClasses compiled = new ClassFileImporter().importPath(fixtures);
        JavaClasses untagged = compiled.that(new com.tngtech.archunit.base.DescribedPredicate<>("untagged") {
            @Override
            public boolean test(JavaClass input) {
                return input.getSimpleName().equals("UntaggedOpensSocket");
            }
        });
        JavaClasses live = compiled.that(new com.tngtech.archunit.base.DescribedPredicate<>("live") {
            @Override
            public boolean test(JavaClass input) {
                return input.getSimpleName().equals("LiveOpensSocket");
            }
        });
        assertFalse(untagged.isEmpty(), "fixture UntaggedOpensSocket was not imported");
        assertFalse(live.isEmpty(), "fixture LiveOpensSocket was not imported");
        AssertionError blocked = assertThrows(AssertionError.class, () -> RULE.check(untagged));
        assertTrue(blocked.getMessage().contains("UntaggedOpensSocket"));
        RULE.check(live);

        JavaClasses learning = importLearningTests();
        assertFalse(learning.isEmpty(), "no learning test classes were imported");
        assertTrue(learning.stream().anyMatch(type -> type.getName().endsWith("CookbookVideoBundleTest")));
        if ("1".equals(System.getenv("RATCHET_ALL_MODULES"))) {
            assertTrue(learning.stream().anyMatch(type -> type.getPackageName().startsWith("com.embabel.learning.java")));
            assertTrue(learning.stream().anyMatch(type -> type.getPackageName().startsWith("com.embabel.learning.kotlin")));
        }
        RULE.check(learning);
    }

    private static JavaClasses importLearningTests() {
        Path root = repoRoot();
        List<Path> present = new ArrayList<>();
        for (String rel : List.of(
                "learning-common/target/test-classes",
                "java-demo/target/test-classes",
                "kotlin-demo/target/test-classes"
        )) {
            Path dir = root.resolve(rel);
            boolean required = "1".equals(System.getenv("RATCHET_ALL_MODULES")) || rel.startsWith("learning-common/");
            if (!Files.isDirectory(dir)) {
                if (required) {
                    throw new AssertionError("missing " + dir + " ; run mvn -DskipTests test-compile");
                }
                continue;
            }
            present.add(dir);
        }
        return new ClassFileImporter().importPaths(present);
    }

    private static Path compileSocketFixtures() throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new AssertionError("JDK compiler is required to prove the socket rule");
        }
        Path dir = Files.createTempDirectory("learning-socket-fixtures");
        Path sourceRoot = dir.resolve("src");
        Files.createDirectories(sourceRoot);
        Files.writeString(sourceRoot.resolve("UntaggedOpensSocket.java"), """
                public class UntaggedOpensSocket {
                    public void open() throws Exception {
                        new java.net.Socket();
                    }
                }
                """);
        Files.writeString(sourceRoot.resolve("LiveOpensSocket.java"), """
                @org.junit.jupiter.api.Tag("live")
                public class LiveOpensSocket {
                    public void open() throws Exception {
                        new java.net.Socket();
                    }
                }
                """);
        Path classes = dir.resolve("classes");
        Files.createDirectories(classes);
        int compiled = compiler.run(
                null,
                null,
                null,
                "-classpath",
                System.getProperty("java.class.path"),
                "-d",
                classes.toString(),
                sourceRoot.resolve("UntaggedOpensSocket.java").toString(),
                sourceRoot.resolve("LiveOpensSocket.java").toString()
        );
        if (compiled != 0) {
            throw new AssertionError("socket fixtures failed to compile");
        }
        return classes;
    }

    private static Path repoRoot() {
        Path cwd = Path.of("").toAbsolutePath();
        if (Files.isDirectory(cwd.resolve("learning-common"))) {
            return cwd;
        }
        return cwd.getParent();
    }

    private static boolean explicitlyLive(JavaCodeUnit unit) {
        if (hasLiveTag(unit.getAnnotations())) {
            return true;
        }
        JavaClass owner = unit.getOwner();
        while (owner != null) {
            if (hasLiveTag(owner.getAnnotations())) {
                return true;
            }
            owner = owner.getEnclosingClass().orElse(null);
        }
        return false;
    }

    private static boolean hasLiveTag(Iterable<? extends JavaAnnotation<?>> annotations) {
        for (JavaAnnotation<?> annotation : annotations) {
            if (!"org.junit.jupiter.api.Tag".equals(annotation.getRawType().getName())) {
                continue;
            }
            Object value = annotation.getProperties().get("value");
            if ("live".equals(String.valueOf(value))) {
                return true;
            }
        }
        return false;
    }

    private static boolean opensSocket(JavaCall<?> call) {
        String owner = call.getTarget().getOwner().getName();
        String name = call.getTarget().getName();
        if ("<init>".equals(name)) {
            return SOCKET_TYPES.contains(owner);
        }
        if ("java.net.URL".equals(owner) && ("openConnection".equals(name) || "openStream".equals(name))) {
            return true;
        }
        if (owner.startsWith("java.nio.channels.") && "open".equals(name)) {
            return true;
        }
        if ("java.net.http.HttpClient".equals(owner)
                && ("send".equals(name) || "sendAsync".equals(name) || "newHttpClient".equals(name))) {
            return true;
        }
        if ("java.net.Socket".equals(owner) && "connect".equals(name)) {
            return true;
        }
        return "java.net.ServerSocket".equals(owner) && "accept".equals(name);
    }
}
