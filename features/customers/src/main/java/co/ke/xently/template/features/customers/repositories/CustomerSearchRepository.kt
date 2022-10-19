package co.ke.xently.template.features.customers.repositories

import co.ke.xently.template.features.Dependencies
import co.ke.xently.template.features.search.repositories.ISearchRepository
import co.ke.xently.template.features.utils.Query
import co.ke.xently.template.libraries.data.source.Customer
import co.ke.xently.template.libraries.data.source.asUIInstance
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerSearchRepository @Inject constructor(
    private val dependencies: Dependencies,
) : ISearchRepository<Customer> {
    override fun get(query: Query) = dependencies.database.customerDao.run {
        if (query.value.isBlank()) {
            get()
        } else {
            get("%${query}%")
        }
    }.flowOn(dependencies.dispatcher.io).map { entities ->
        val instances = entities.run {
            if (query.size < 0) {
                this
            } else {
                take(query.size)
            }
        }.map {
            it.asUIInstance
        }
        Result.success(instances)
    }.flowOn(dependencies.dispatcher.computation)
}