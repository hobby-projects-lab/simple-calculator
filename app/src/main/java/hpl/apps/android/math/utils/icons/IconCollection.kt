package hpl.apps.android.math.utils.icons

import hpl.apps.android.math.utils.icons.collection.IconClass
import hpl.apps.android.math.utils.icons.collection.bootStrapSoundWave
import hpl.apps.android.math.utils.icons.collection.fontAwesomeCubes
import hpl.apps.android.math.utils.icons.collection.fontAwesomeHammer
import hpl.apps.android.math.utils.icons.collection.materialIconsGrain
import hpl.apps.android.math.utils.icons.collection.materialSymbolsCreditCardHeart
import hpl.apps.android.math.utils.icons.collection.tablerAngle
import hpl.apps.android.math.utils.icons.collection.tablerCrane
import hpl.apps.android.math.utils.icons.collection.tablerRuler2


enum class IconCollection (val icon: IconClass){
    Ruler(tablerRuler2),
    Cubes(fontAwesomeCubes),
    Grain(materialIconsGrain),
    Hammer(fontAwesomeHammer),
    Crane(tablerCrane),
    Angle(tablerAngle),
    SoundWave(bootStrapSoundWave),
    Card(materialSymbolsCreditCardHeart)
}