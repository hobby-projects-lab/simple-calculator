import hpl.local.build.utils.libsInfo.applyBuildUtils
import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildUtils : Plugin<Project> {
    override fun apply(project: Project) {
        applyBuildUtils(project)
    }
}


