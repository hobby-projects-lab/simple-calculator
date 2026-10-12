package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.SdStorage
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Square
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.ui.graphics.vector.ImageVector
import hpl.apps.android.math.R
import hpl.apps.android.math.utils.Dimension
import hpl.apps.android.math.utils.MeasurementUnit
import hpl.apps.android.math.utils.icons.IconCollection

enum class DimensionItem(
    @StringRes override val id: Int,
    override val icon: ImageVector
): Dimension {
    LENGTH(R.string.length, IconCollection.Ruler.icon.vector),
    TIME(R.string.time, Icons.Default.AccessTime),
    SPEED(R.string.speed, Icons.Default.Speed),
    MASS(R.string.mass, Icons.Filled.Scale),
    AREA(R.string.area, Icons.Filled.Square),
    VOLUME(R.string.volume, IconCollection.Cubes.icon.vector),
    DENSITY(R.string.density, IconCollection.Grain.icon.vector),
    TEMPERATURE(R.string.temperature, Icons.Filled.Thermostat),
    FORCE(R.string.force, IconCollection.Hammer.icon.vector),
    ENERGY(R.string.energy, Icons.Default.ElectricBolt),
    WORK(R.string.work, IconCollection.Crane.icon.vector),
    POWER(R.string.power, Icons.Default.Lightbulb),
    PRESSURE(R.string.pressure, Icons.Default.Compress),
    ANGLE(R.string.angle, IconCollection.Angle.icon.vector),
    FREQUENCY(R.string.frequency, IconCollection.SoundWave.icon.vector),
    STORAGE(R.string.storage, Icons.Default.SdStorage)
}

val dimensionUnitsMap:  Map<Dimension, Map<String, MeasurementUnit>> = mapOf(
    DimensionItem.LENGTH to Length.entries.associateBy { it.name },
    DimensionItem.TIME to Time.entries.associateBy { it.name },
    DimensionItem.SPEED to Speed.entries.associateBy { it.name },
    DimensionItem.MASS to Mass.entries.associateBy { it.name },
    DimensionItem.AREA to Area.entries.associateBy { it.name },
    DimensionItem.VOLUME to Volume.entries.associateBy { it.name },
    DimensionItem.DENSITY to Density.entries.associateBy { it.name },
    DimensionItem.TEMPERATURE to Temperature.entries.associateBy { it.name },
    DimensionItem.FORCE to Force.entries.associateBy { it.name },
    DimensionItem.ENERGY to Energy.entries.associateBy { it.name },
    DimensionItem.WORK to Work.entries.associateBy { it.name },
    DimensionItem.POWER to Power.entries.associateBy { it.name },
    DimensionItem.PRESSURE to Pressure.entries.associateBy { it.name },
    DimensionItem.ANGLE to Angle.entries.associateBy { it.name },
    DimensionItem.FREQUENCY to Frequency.entries.associateBy { it.name },
    DimensionItem.STORAGE to Storage.entries.associateBy { it.name }
 )