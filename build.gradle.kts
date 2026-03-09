import io.gitlab.arturbosch.detekt.extensions.DetektExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.detekt) apply true
}

allprojects
    .filter { it.name != "presentation" }
    .onEach { project ->
        project.afterEvaluate {
            with(project.plugins) {
                val hasKotlinAndroid = hasPlugin(libs.plugins.kotlin.android.get().pluginId)
                val hasKotlinJvm = hasPlugin(libs.plugins.jetbrains.kotlin.jvm.get().pluginId)
                if (hasKotlinAndroid || hasKotlinJvm) {
                    apply(libs.plugins.detekt.get().pluginId)
                }
            }

            project.extensions.configure<DetektExtension> {
                config.setFrom(rootProject.files("default-detekt-config.yml"))
            }
        }
    }
