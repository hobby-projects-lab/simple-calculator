package hpl.apps.android.math.ui.screens.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hpl.apps.android.math.ui.screens.about.licences.Licences


enum class AboutDestinations(val screenName: String){
    DEFAULT("default"),
    ICONS("icons"),
    FONTS("fonts"),
    LIBRARIES("libraries"),
    LICENCES("licences")
}
@Composable
fun About(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
){
    val uriHandler = LocalUriHandler.current

    NavHost(
        navController = navController,
        startDestination = AboutDestinations.DEFAULT.screenName,
        modifier = modifier
    ) {
        composable(route = AboutDestinations.DEFAULT.screenName) {
            AppInfo(
                openLink = { uriHandler.openUri(it) },
                goToLicences = { navController.navigate(AboutDestinations.LICENCES.screenName) },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(route = AboutDestinations.LICENCES.screenName) {
            Licences(
                screenName = AboutDestinations.LICENCES.screenName,
                openLink = { x -> uriHandler.openUri(x) },
                navigate = { x -> navController.navigate(x) },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(route = AboutDestinations.ICONS.screenName){
            Licences(
                screenName = AboutDestinations.ICONS.screenName,
                openLink = { x -> uriHandler.openUri(x) },
                navigate = { x -> navController.navigate(x) },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(route = AboutDestinations.FONTS.screenName) {
            Licences(
                screenName = AboutDestinations.FONTS.screenName,
                openLink = { x -> uriHandler.openUri(x) },
                navigate = { x -> navController.navigate(x) },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(route = AboutDestinations.LIBRARIES.screenName) {
            Licences(
                screenName = AboutDestinations.LIBRARIES.screenName,
                openLink = { x -> uriHandler.openUri(x) },
                navigate = { x -> navController.navigate(x) },
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}
