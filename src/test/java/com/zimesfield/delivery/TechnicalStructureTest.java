//package com.zimesfield.delivery;
//
//import static com.tngtech.archunit.base.DescribedPredicate.alwaysTrue;
//import static com.tngtech.archunit.core.domain.JavaClass.Predicates.belongToAnyOf;
//import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
//
//import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
//import com.tngtech.archunit.junit.AnalyzeClasses;
//import com.tngtech.archunit.junit.ArchTest;
//import com.tngtech.archunit.lang.ArchRule;
//import com.zimesfield.delivery.adapter.util.Constants;
//
//@AnalyzeClasses(packagesOf = DeliveryApp.class, importOptions = DoNotIncludeTests.class)
//class TechnicalStructureTest {
//
//    // prettier-ignore
//    @ArchTest
//    static final ArchRule respectsTechnicalArchitectureLayers = layeredArchitecture()
//        .consideringAllDependencies()
//        .layer("Config").definedBy("..config..")
//        .layer("Adapter").definedBy("..adapter..")
//        .layer("Web").definedBy("..web..")
//        .optionalLayer("Service").definedBy("..service..")
//        .optionalLayer("Repository").definedBy("..repository..")
//        .layer("Security").definedBy("..security..")
//        .optionalLayer("Persistence").definedBy("..rdbms..")
//        .layer("Domain").definedBy("..domain..")
//
//        .whereLayer("Config").mayNotBeAccessedByAnyLayer()
//        .whereLayer("Adapter").mayOnlyBeAccessedByLayers("Domain", "Repository", "Service", "Security", "Web", "Config", "Adapter")
//
////        .whereLayer("Web").mayOnlyBeAccessedByLayers("Config")
////        .whereLayer("Service").mayOnlyBeAccessedByLayers("Web", "Config")
////        .whereLayer("Repository").mayOnlyBeAccessedByLa`yers("Config", "Service", "Domain")
////        .whereLayer("Security").mayOnlyBeAccessedByLayers("Config", "Service", "Web")
////        .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service", "Repository", "Security", "Web", "Config")
////        .whereLayer("Domain").mayOnlyBeAccessedByLayers("Persistence", "Repository", "Service", "Security", "Web", "Config")
//
//        .ignoreDependency(belongToAnyOf(DeliveryApp.class), alwaysTrue())
//        .ignoreDependency(alwaysTrue(), belongToAnyOf(
//            Constants.class,
//            com.zimesfield.delivery.config.ApplicationProperties.class
//        ));
//}
