package co.ke.xently.template.features.users.di

import co.ke.xently.template.features.users.repositories.IUserRepository
import co.ke.xently.template.features.users.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(repository: UserRepository): IUserRepository
}