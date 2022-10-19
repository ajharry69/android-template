package co.ke.xently.template.features.customers.di

import co.ke.xently.template.features.customers.repositories.CustomerRepository
import co.ke.xently.template.features.customers.repositories.CustomerSearchRepository
import co.ke.xently.template.features.customers.repositories.ICustomerRepository
import co.ke.xently.template.features.search.repositories.ISearchRepository
import co.ke.xently.template.libraries.data.source.Customer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(repository: CustomerRepository): ICustomerRepository

    @Binds
    abstract fun bindSearchRepository(repository: CustomerSearchRepository): ISearchRepository<Customer>
}