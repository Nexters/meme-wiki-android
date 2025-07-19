package ext

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementationLibrary(
    libs: VersionCatalog,
    name: String
) {
    implementation(libs.findLibrary(name).get())
}

fun DependencyHandlerScope.implementationBundle(
    libs: VersionCatalog,
    name: String
) {
    implementation(libs.findBundle(name).get())
}

fun <T : Any> DependencyHandlerScope.implementation(
    dependency: T
) {
    "implementation"(dependency)
}

fun DependencyHandlerScope.testImplementationLibrary(
    libs: VersionCatalog,
    name: String
) {
    testImplementation(libs.findLibrary(name).get())
}

fun DependencyHandlerScope.testImplementationBundle(
    libs: VersionCatalog,
    name: String
) {
    testImplementation(libs.findBundle(name).get())
}

fun <T : Any> DependencyHandlerScope.testImplementation(
    dependency: T
) {
    "testImplementation"(dependency)
}

fun DependencyHandlerScope.androidTestImplementationLibrary(
    libs: VersionCatalog,
    name: String
) {
    androidTestImplementation(libs.findLibrary(name).get())
}

fun DependencyHandlerScope.androidTestImplementationBundle(
    libs: VersionCatalog,
    name: String
) {
    androidTestImplementation(libs.findBundle(name).get())
}

fun <T : Any> DependencyHandlerScope.androidTestImplementation(
    dependency: T
) {
    "androidTestImplementation"(dependency)
}

fun DependencyHandlerScope.debugImplementationLibrary(
    libs: VersionCatalog,
    name: String
) {
    debugImplementation(libs.findLibrary(name).get())
}

fun DependencyHandlerScope.debugImplementationBundle(
    libs: VersionCatalog,
    name: String
) {
    debugImplementation(libs.findBundle(name).get())
}

fun <T : Any> DependencyHandlerScope.debugImplementation(
    dependency: T
) {
    "debugImplementation"(dependency)
}

fun DependencyHandlerScope.coreLibraryDesugaring(
    libs: VersionCatalog,
    name: String
) {
    "coreLibraryDesugaring"(libs.findLibrary(name).get())
}

fun DependencyHandlerScope.kaptLibrary(
    libs: VersionCatalog,
    name: String
) {
    "kapt"(libs.findLibrary(name).get())
}