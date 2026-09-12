plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

dependencies{
    implementation(plugin(libs.plugins.aboutLibraries.plugin))
    implementation(libs.okhttp)
    implementation(libs.aboutlibraries.compose.m3)
    implementation(libs.kotlinx.serialization.json)
}

gradlePlugin {
    plugins {
        create("buildUtils") {
            id = libs.plugins.build.utils.map{ it.pluginId }.get()
            implementationClass = "BuildUtils"
        }
    }
}

private fun plugin(plugin: Provider<PluginDependency>): String =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }.get()