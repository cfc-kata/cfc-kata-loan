package com.cfckata.layered;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class LayeredArchitectureTest {
    static JavaClasses classes;

    @BeforeAll
    static void init() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.cfckata");
    }

    @Test
    void domain_layer_depend_on_rule() {
        classes()
                .that().resideInAPackage("..domain..")
                .should().onlyDependOnClassesThat().resideInAnyPackage("java..", "..domain..", "com.github.meixuesong.aggregatepersistence..")
                .as("The domain layer should only depend on the classes in the package of " +
                        "java, domain and com.github.meixuesong.aggregatepersistence.")
                .check(classes);
    }

}
