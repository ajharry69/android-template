package co.ke.xently.template.libraries.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import co.ke.xently.template.libraries.data.source.local.daos.*

@Database(
    entities = [
        UserEntity::class,
        CustomerEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    RoomTypeConverters.DateConverter::class,
)
abstract class Database : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val customerDao: CustomerDao
}